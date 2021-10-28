package com.chiya.authority.code;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.chiya.array.ChiyaHashMapValueMap;
import com.chiya.authority.Method;
import com.chiya.authority.api.AuthorityRoleData;
import com.chiya.bit.BitUtil;

/**
 * 角色持权限
 * 
 * @author Brian
 *
 */
public class AuthorityRoleMap {

	/**
	 * 角色持有的权限,权限角色ID，URL，请求方式
	 */
	private ChiyaHashMapValueMap<Integer, String, Byte> chiyaHashMapValueMap = new ChiyaHashMapValueMap<Integer, String, Byte>();
	/**
	 * 需要更新的缓存信息
	 */
	private ConcurrentSkipListSet<Integer> updateSet = new ConcurrentSkipListSet<Integer>();
	/**
	 * 更新状态标志位
	 */
	private volatile boolean updateFlag = true;
	/**
	 * 同步锁
	 */
	private Lock lock = new ReentrantLock();
	/**
	 * 数据注入
	 */
	private AuthorityRoleData authorityRoleData;

	public AuthorityRoleMap(AuthorityRoleData authorityRoleData) {
		this.authorityRoleData = authorityRoleData;
	}

	/**
	 * 更新所有数据
	 */
	public void reloadAll() {
		if (updateFlag) {
			if (lock.tryLock()) {
				updateSet.clear();
				chiyaHashMapValueMap.remove();
				try {
					authorityRoleData.selectAll(chiyaHashMapValueMap);
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
	 * @param authorityRoleId 权限角色id
	 */
	public void reload(Integer authorityRoleId) {
		if (updateSet.contains(authorityRoleId)) {
			if (lock.tryLock()) {
				try {
					authorityRoleData.selectOne(chiyaHashMapValueMap, authorityRoleId);
					updateSet.remove(authorityRoleId);
				} finally {
					lock.unlock();
				}
			}
		}
	}

	/**
	 * 检查更新缓存数据
	 * 
	 * @param authorityRoleId 权限角色id
	 */
	public void checkReload(Integer authorityRoleId) {
		if (updateFlag) {
			reloadAll();
		} else if (updateSet.contains(authorityRoleId)) { reload(authorityRoleId); }
	}

	/**
	 * 比较权限信息
	 * 
	 * @param authorityRoleId 权限角色id
	 * @param url             接口地址
	 * @param method          请求方式
	 * @return true:存在/false:不存在
	 */
	public boolean contains(Integer authorityRoleId, String url, String method) {
		checkReload(authorityRoleId);
		return BitUtil.macthBit(chiyaHashMapValueMap.get(authorityRoleId, url), Method.getOffset(method));
	}

	/**
	 * 比较权限信息
	 * 
	 * @param set    权限角色集合
	 * @param url    接口地址
	 * @param method 请求方式
	 * @return true:存在/false:不存在
	 */
	public boolean contains(Set<Integer> set, String url, String method) {
		set.forEach(i -> checkReload(i));
		int offset = Method.getOffset(method);
		for (Integer integer : set) {
			if (BitUtil.macthBit(chiyaHashMapValueMap.get(integer, url), offset)) { return true; }
		}
		return false;
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
