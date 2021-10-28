package chiya.security.code;

import java.util.concurrent.ConcurrentSkipListSet;

import chiya.core.base.collection.ChiyaHashMapValueSet;
import chiya.security.api.GroupsRoleDataSource;

/**
 * 用户组映射管理工具
 * 
 * @author Brian
 *
 */
public class GroupsRoleRelation extends BaseManageRelation {

	/**
	 * 用户组与角色映射
	 */
	private ChiyaHashMapValueSet<Integer, Integer> chiyaHashMapValueSet = new ChiyaHashMapValueSet<Integer, Integer>();
	/**
	 * 用户组角色数据源
	 */
	private GroupsRoleDataSource groupsRoleDataSource;

	/**
	 * 默认构造方法
	 * 
	 * @param groupsRoleDataSource 组角色数据源
	 */
	public GroupsRoleRelation(GroupsRoleDataSource groupsRoleDataSource) {
		this.groupsRoleDataSource = groupsRoleDataSource;
	}

	/**
	 * 更新所有数据
	 */
	@Override
	protected void reloadAllData() {
		chiyaHashMapValueSet.remove();
		groupsRoleDataSource.selectAll(chiyaHashMapValueSet);
	}

	/**
	 * 更新部分数据
	 * 
	 * @param groupId 组Id
	 */
	@Override
	protected void reloadDate(Integer groupId) {
		groupsRoleDataSource.selectOne(chiyaHashMapValueSet, groupId);
	}

	/**
	 * 判断用户是否存在用户组中
	 * 
	 * @param groupId 组id
	 * @param roleId  角色id
	 * @return true:存在/false:不存在
	 */
	public boolean contains(Integer groupId, Integer roleId) {
		checkReload(groupId);
		return chiyaHashMapValueSet.contains(groupId, roleId);
	}

	/**
	 * 用户组所有的角色
	 * 
	 * @param groupId 组id
	 * @return Set<Integer>
	 */
	public ConcurrentSkipListSet<Integer> getUserGroups(Integer groupId) {
		checkReload(groupId);
		return chiyaHashMapValueSet.get(groupId);
	}

	/**
	 * 将权限角色移除用户组
	 * 
	 * @param groupId         组ID
	 * @param authorityRoleId 权限角色组id
	 */
	public void remove(Integer groupId, Integer roleId) {
		chiyaHashMapValueSet.remove(groupId, roleId);
	}

	/**
	 * 添加权限角色至用户组
	 * 
	 * @param groupId 组ID
	 * @param roleId  角色id
	 */
	public void put(Integer groupId, Integer roleId) {
		chiyaHashMapValueSet.put(groupId, roleId);
	}

}
