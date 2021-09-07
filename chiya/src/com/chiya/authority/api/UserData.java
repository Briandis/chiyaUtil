package com.chiya.authority.api;

import com.chiya.array.ChiyaHashMapValueSet;

public interface UserData {
	/**
	 * 更新所有数据
	 * 
	 * @param ChiyaHashMapValueSet 用户组缓存
	 */
	public void selectAll(ChiyaHashMapValueSet<Integer, Integer> chiyaHashMapValueSet);

	/**
	 * 更新一条数据
	 * 
	 * @param ChiyaHashMapValueSet 用户组缓存
	 * @param userGroupId          用户组id
	 */
	public void selectOne(ChiyaHashMapValueSet<Integer, Integer> chiyaHashMapValueSet, Integer userGroupId);
}
