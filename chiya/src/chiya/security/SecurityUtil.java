package chiya.security;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.LongAdder;

import chiya.security.api.RoleResourceDataSource;
import chiya.security.api.GroupsUserDataSource;
import chiya.security.api.GroupsRoleDataSource;
import chiya.security.code.RoleResourceRelation;
import chiya.security.code.GroupsRoleRelation;
import chiya.security.code.GroupsUserRelation;

/**
 * 鉴权基操作库
 */
public class SecurityUtil {

	/**
	 * 权限角色与权限
	 */
	private RoleResourceRelation roleResourceRelation;
	/**
	 * 用户组映射权限角色
	 */
	private GroupsRoleRelation groupsRoleRelation;
	/**
	 * 用户组中的用户
	 */
	private GroupsUserRelation groupsUserRelation;
	/**
	 * 最大访问次数
	 */
	public LongAdder maxCount = new LongAdder();
	/**
	 * 失败访问次数
	 */
	public LongAdder failCount = new LongAdder();
	/**
	 * 有效访问次数
	 */
	public LongAdder successCount = new LongAdder();

	/**
	 * 默认构造方法
	 * 
	 * @param groupsUserDataSource   组成员数据源
	 * @param groupsRoleDataSource   组角色数据源
	 * @param roleResourceDataSource 角色资源数据源
	 */
	public SecurityUtil(GroupsUserDataSource groupsUserDataSource, GroupsRoleDataSource groupsRoleDataSource, RoleResourceDataSource roleResourceDataSource) {
		// 构建组成员
		groupsUserRelation = new GroupsUserRelation(groupsUserDataSource);
		// 构建组角色
		groupsRoleRelation = new GroupsRoleRelation(groupsRoleDataSource);
		// 构建角色资源
		roleResourceRelation = new RoleResourceRelation(roleResourceDataSource);
	}

	/**
	 * 初始化执行
	 */
	public void init() {
		// 加载所有数据
		roleResourceRelation.loadAllDate();
		groupsRoleRelation.loadAllDate();
		groupsUserRelation.loadAllDate();
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
		// 根据组用户信息获取所在用户组
		Set<Integer> userGroupsSet = groupsUserRelation.getUserGropu(userId);
		Set<Integer> authorityRoleSet = new HashSet<Integer>();
		// 根据组角色迭代查找符合的角色
		userGroupsSet.forEach(i -> {
			ConcurrentSkipListSet<Integer> set = groupsRoleRelation.getUserGroups(i);
			if (set != null) { authorityRoleSet.addAll(set); }

		});
		return roleResourceRelation.contains(authorityRoleSet, url, method);
	}

	/**
	 * 直接判断权限角色是否有权限，用于公共访问
	 * 
	 * @param roleid 角色ID
	 * @param url    访问路径
	 * @param method 请求方式
	 * @return true:有权限/false:没有权限
	 */
	public boolean containsRole(Integer roleid, String url, String method) {
		return roleResourceRelation.contains(roleid, url, method);
	}

	/**
	 * 计数自增
	 * 
	 * @param b 执行成功还是失败
	 */
	public void increment(boolean b) {
		maxCount.increment();
		if (b) {
			failCount.increment();
		} else {
			successCount.increment();
		}
	}

	/**
	 * 统计清零
	 */
	public void countClaer() {
		maxCount.reset();
		failCount.reset();
		successCount.reset();
	}

}
