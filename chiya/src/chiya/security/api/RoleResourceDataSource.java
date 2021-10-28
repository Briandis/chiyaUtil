package chiya.security.api;

import chiya.core.base.collection.ChiyaHashMapValueMap;

public interface RoleResourceDataSource {

	/**
	 * 查询所有下更新数据
	 * 
	 * @param chiyaHashMapValueMap 缓存数据源
	 */
	public void selectAll(ChiyaHashMapValueMap<Integer, String, Byte> chiyaHashMapValueMap);

	/**
	 * 只有一个更变下更新数据
	 * 
	 * @param chiyaHashMapValueMap 缓存数据源<角色id,URL路径,请求方式>构成
	 * @param roleId               角色id
	 */
	public void selectOne(ChiyaHashMapValueMap<Integer, String, Byte> chiyaHashMapValueMap, Integer roleId);
}
