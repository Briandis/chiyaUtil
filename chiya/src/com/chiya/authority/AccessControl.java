package com.chiya.authority;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.LongAdder;

import com.chiya.authority.api.AuthorityRoleData;
import com.chiya.authority.api.UserData;
import com.chiya.authority.api.UserGroupsData;
import com.chiya.authority.code.AuthorityRoleMap;
import com.chiya.authority.code.UserGroupsMap;
import com.chiya.authority.code.UserMap;

/**
 * 权限控制器
 * 
 * @author Brian
 *
 */
public class AccessControl {

	/**
	 * 权限角色与权限
	 */
	private AuthorityRoleMap authorityRoleMap;
	/**
	 * 用户组映射权限角色
	 */
	private UserGroupsMap userGroupsMap;
	/**
	 * 用户组中的用户
	 */
	private UserMap userMap;
	/**
	 * 最大访问次数
	 */
	public LongAdder maxCount = new LongAdder();
	/**
	 * 失败访问次数
	 */
	public LongAdder fCount = new LongAdder();
	/**
	 * 有效访问次数
	 */
	public LongAdder tCount = new LongAdder();

	/**
	 * 默认构造方法
	 * 
	 * @param userData          用户组与用户注入实现类
	 * @param userGroupsData    用户组与权限角色实现类
	 * @param authorityRoleData 权限角色与权限实现类
	 */
	public AccessControl(UserData userData, UserGroupsData userGroupsData, AuthorityRoleData authorityRoleData) {
		authorityRoleMap = new AuthorityRoleMap(authorityRoleData);
		userGroupsMap = new UserGroupsMap(userGroupsData);
		userMap = new UserMap(userData);
	}

	/**
	 * 初始化执行
	 */
	public void init() {
		authorityRoleMap.reloadAll();
		userGroupsMap.reloadAll();
		userMap.reloadAll();
	}

	/**
	 * 判断用户是否有权限
	 * 
	 * @param userId 用户id
	 * @param url    访问路径
	 * @param method 请求方式
	 * @return true:有权限/false:没有权限
	 */
	public boolean contains(Integer userId, String url, String method) {
		Set<Integer> userGroupsSet = userMap.getUserGropu(userId);
		Set<Integer> authorityRoleSet = new HashSet<Integer>();
		userGroupsSet.forEach(i -> {
			ConcurrentSkipListSet<Integer> set = userGroupsMap.getUserGroups(i);
			if (set != null) { authorityRoleSet.addAll(set); }

		});
		return authorityRoleMap.contains(authorityRoleSet, url, method);
	}

	/**
	 * 直接判断权限角色是否有权限，用于公共访问
	 * 
	 * @param authorityRoleId 权限角色ID
	 * @param url             访问路径
	 * @param method          请求方式
	 * @return true:有权限/false:没有权限
	 */
	public boolean containsRole(Integer authorityRoleId, String url, String method) {
		return authorityRoleMap.contains(authorityRoleId, url, method);
	}

	/**
	 * 记录
	 * 
	 * @param b
	 */
	public void log(boolean b) {
		maxCount.increment();
		if (b) {
			tCount.increment();
		} else {
			fCount.increment();
		}
	}

	/**
	 * 统计清零
	 */
	public void countClaer() {
		maxCount.reset();
		tCount.reset();
		fCount.reset();
	}

	/**
	 * 获取权限角色缓存
	 * 
	 * @return AuthorityRoleMap
	 */
	public AuthorityRoleMap getAuthorityRoleMap() {
		return authorityRoleMap;
	}

	/**
	 * 获取权限用户组用户缓存
	 * 
	 * @return UserGroupsMap
	 */
	public UserGroupsMap getUserGroupsMap() {
		return userGroupsMap;
	}

	/**
	 * 获取用户组用户缓存
	 * 
	 * @return UserMap
	 */
	public UserMap getUserMap() {
		return userMap;
	}

	/**
	 * 获取总次数
	 * 
	 * @return long
	 */
	public long getMaxCount() {
		return maxCount.longValue();
	}

	/**
	 * 获取拦截次数
	 * 
	 * @return long
	 */
	public long getfCount() {
		return fCount.longValue();
	}

	/**
	 * 获取放行次数
	 * 
	 * @return long
	 */
	public long gettCount() {
		return tCount.longValue();
	}

}
