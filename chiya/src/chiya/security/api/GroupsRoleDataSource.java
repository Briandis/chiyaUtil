package chiya.security.api;

import chiya.core.base.collection.ChiyaHashMapValueSet;

public interface GroupsRoleDataSource {
	/**
	 * 查询所有
	 * 
	 * @param chiyaHashMapValueSet 用户组角色缓存
	 */
	public void selectAll(ChiyaHashMapValueSet<Integer, Integer> chiyaHashMapValueSet);

	/**
	 * 查询一个
	 * 
	 * @param chiyaHashMapValueSet 用户组角色缓存
	 * @param userGroupId          用户组id
	 */
	public void selectOne(ChiyaHashMapValueSet<Integer, Integer> chiyaHashMapValueSet, Integer userGroupId);
}
