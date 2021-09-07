package com.chiya.authority.code;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.chiya.array.ChiyaHashMapValueSet;
import com.chiya.authority.api.UserGroupsData;

/**
 * 用户组映射管理工具
 * 
 * @author Brian
 *
 */
public class UserGroupsMap {

	/**
	 * 用户组与权限角色
	 */
	private ChiyaHashMapValueSet<Integer, Integer> chiyaHashMapValueSet = new ChiyaHashMapValueSet<Integer, Integer>();
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
	/**
	 * 用户组角色数据源
	 */
	private UserGroupsData userGroupsData;

	/**
	 * 添加要更新的权限角色ID
	 * 
	 * @param authorityRoleId 权限角色组id
	 */
	public void updateSetAdd(Integer authorityRoleId) {
		updateSet.add(authorityRoleId);
	}

	/**
	 * 下次更新全部信息
	 */
	public void updateAll() {
		updateFlag = true;
	}

	/**
	 * 设置新的注入原方式
	 * 
	 * @param userGroupsData 数据注入实现类
	 */
	public void setUserGroupsData(UserGroupsData userGroupsData) {
		this.userGroupsData = userGroupsData;
	}

	/**
	 * 默认构造方法
	 * 
	 * @param userGroupsData 数据注入实现类
	 */
	public UserGroupsMap(UserGroupsData userGroupsData) {
		this.userGroupsData = userGroupsData;
	}

	/**
	 * 更新所有数据
	 */
	public void reloadAll() {
		if (updateFlag) {
			if (lock.tryLock()) {
				// 清除独立更新
				updateSet.clear();
				chiyaHashMapValueSet.remove();
				try {
					userGroupsData.selectAll(chiyaHashMapValueSet);
					updateFlag = false;
				} catch (Exception e) {
					e.printStackTrace();
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
					userGroupsData.selectOne(chiyaHashMapValueSet, userGroupId);
					updateSet.remove(userGroupId);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}

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
	 * 判断用户是否存在用户组中
	 * 
	 * @param userGroupsId    用户组id
	 * @param authorityRoleId 权限角色组id
	 * @return true:存在/false:不存在
	 */
	public boolean contains(Integer userGroupsId, Integer authorityRoleId) {
		checkReload(userGroupsId);
		return chiyaHashMapValueSet.contains(userGroupsId, authorityRoleId);
	}

	/**
	 * 用户组所有的权限角色
	 * 
	 * @param userGroupsId 用户组id
	 * @return Set<Integer>
	 */
	public ConcurrentSkipListSet<Integer> getUserGroups(Integer userGroupsId) {
		checkReload(userGroupsId);
		return chiyaHashMapValueSet.get(userGroupsId);
	}

	/**
	 * 将权限角色移除用户组
	 * 
	 * @param userGroupId     用户组ID
	 * @param authorityRoleId 权限角色组id
	 */
	public void remove(Integer userGroupId, Integer authorityRoleId) {
		chiyaHashMapValueSet.remove(userGroupId, authorityRoleId);
	}

	/**
	 * 添加权限角色至用户组
	 * 
	 * @param userGroupId     用户组ID
	 * @param authorityRoleId 权限角色组id
	 */
	public void put(Integer userGroupId, Integer authorityRoleId) {
		chiyaHashMapValueSet.put(userGroupId, authorityRoleId);
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
