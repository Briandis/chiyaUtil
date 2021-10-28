package com.chiya.authority.code;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.chiya.array.ChiyaHashMapValueSet;
import com.chiya.authority.api.UserData;
import com.chiya.bit.BitMap;

/**
 * 用户组
 * 
 * @author Brian
 *
 */
public class UserMap {

	/**
	 * 基础容器，<用户组id，用户>，适合连续大量的
	 */
	private ConcurrentHashMap<Integer, BitMap> userBitMap = new ConcurrentHashMap<Integer, BitMap>();
	/**
	 * 基础容器，<用户组id，用户>，适合高度离散和少量的
	 */
	private ChiyaHashMapValueSet<Integer, Integer> userHasMap = new ChiyaHashMapValueSet<Integer, Integer>();
	/**
	 * 需要更新的用户组信息
	 */
	private ConcurrentSkipListSet<Integer> updateSet = new ConcurrentSkipListSet<Integer>();
	/**
	 * 更新标志位，重新加载所有
	 */
	private volatile boolean updateFlag = true;
	/**
	 * 同步锁
	 */
	private Lock lock = new ReentrantLock();

	private UserData userData;

	/**
	 * 默认构造方法
	 * 
	 * @param userData:数据注入实现类
	 */
	public UserMap(UserData userData) {
		this.userData = userData;
	}

	/**
	 * 更新所有数据
	 */
	public void reloadAll() {
		if (updateFlag) {
			if (lock.tryLock()) {
				// 清除独立更新
				updateSet.clear();
				userHasMap.remove();
				try {
					userData.selectAll(userHasMap);
					updateFlag = false;
				} finally {
					lock.unlock();
				}

			}
		}
	}

	/**
	 * 更新部分数据
	 * 
	 * @param userGroupId 用户组Id
	 */
	public void reload(Integer userGroupId) {
		if (updateSet.contains(userGroupId)) {
			if (lock.tryLock()) {
				try {
					userData.selectOne(userHasMap, userGroupId);
					updateSet.remove(userGroupId);
				} finally {
					lock.unlock();
				}
			}
		}

	}

	/**
	 * 获取用户所在的所有用户组
	 * 
	 * @param userId 用户Id
	 * @return Set<Integer>:用户组的集合
	 */
	public Set<Integer> getUserGropu(Integer userId) {
		reloadAll();
		Set<Integer> set = new HashSet<Integer>();
		// 位图查找用户
		userBitMap.entrySet().forEach(entry -> {
			if (entry.getValue().get(userId)) { set.add(entry.getKey()); }
		});
		// 查找set存储的
		userHasMap.entrySet().forEach(entry -> {
			checkReload(entry.getKey());
			if (entry.getValue().contains(userId)) { set.add(entry.getKey()); }
		});
		return set;
	}

	/**
	 * 将用户移除用户组
	 * 
	 * @param userGroupId 用户组ID
	 * @param userId      用户ID
	 */
	public void remove(Integer userGroupId, Integer userId) {
//		if (userBitMap.contains(userGroupId)) { userBitMap.get(userGroupId).remove(userId); }
		userHasMap.remove(userGroupId, userId);
	}

	/**
	 * 添加用户至用户组
	 * 
	 * @param userGroupId 用户组ID
	 * @param userId      用户ID
	 */
	public void put(Integer userGroupId, Integer userId) {
//		if (!userBitMap.containsKey(userGroupId)) {
//			synchronized (userBitMap) {
//				if (!userBitMap.containsKey(userGroupId)) { userBitMap.put(userGroupId, new BitMap()); }
//			}
//		}
//		userBitMap.get(userGroupId).add(userId);
		userHasMap.put(userGroupId, userId);
	}

	/**
	 * 检查更新缓存数据
	 * 
	 * @param userGroupsId 用户组id
	 */
	public void checkReload(Integer userGroupsId) {
		if (updateFlag) {
			reloadAll();
		} else if (updateSet.contains(userGroupsId)) { reload(userGroupsId); }
	}

	/**
	 * 设置全部更新
	 */
	public void setUpdateFlag() {
		this.updateFlag = true;
	}

	/**
	 * 设置更新的id
	 * 
	 * @param id 要更新的id
	 */
	public void setUpdate(Integer id) {
		updateSet.add(id);
	}
}
