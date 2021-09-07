package com.chiya.authority.api;

import com.chiya.array.ChiyaHashMapValueMap;

public interface AuthorityRoleData {

	/**
	 * 查询所有下更新数据
	 * 
	 * @param chiyaHashMapValueMap 缓存数据源
	 */
	public void selectAll(ChiyaHashMapValueMap<Integer, String, Byte> chiyaHashMapValueMap);

	/**
	 * 只有一个更变下更新数据
	 * 
	 * @param chiyaHashMapValueMap 缓存数据源
	 * @param authorityRoleid      权限角色id
	 */
	public void selectOne(ChiyaHashMapValueMap<Integer, String, Byte> chiyaHashMapValueMap, Integer authorityRoleid);
}
