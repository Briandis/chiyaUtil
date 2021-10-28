package chiya.security.code;

import java.util.Set;

import chiya.core.base.bit.BitUtil;
import chiya.core.base.collection.ChiyaHashMapValueMap;
import chiya.security.Method;
import chiya.security.api.RoleResourceDataSource;

/**
 * 角色持有的资源
 *
 */
public class RoleResourceRelation extends BaseManageRelation {

	/**
	 * 角色持有的权限,角色ID，URL，请求方式
	 */
	private ChiyaHashMapValueMap<Integer, String, Byte> chiyaHashMapValueMap = new ChiyaHashMapValueMap<Integer, String, Byte>();

	/**
	 * 数据注入
	 */
	private RoleResourceDataSource roleResourceDataSource;

	/**
	 * 构造方法-需要传入数据源
	 * 
	 * @param roleResourceDataSource
	 */
	public RoleResourceRelation(RoleResourceDataSource roleResourceDataSource) {
		this.roleResourceDataSource = roleResourceDataSource;
	}

	/**
	 * 更新所有数据
	 */
	@Override
	protected void reloadAllData() {
		// 删除所有数据
		chiyaHashMapValueMap.remove();
		// 调用数据源装配所有数据，数据源需要开人员自行装配数据
		roleResourceDataSource.selectAll(chiyaHashMapValueMap);
	}

	/**
	 * 更新部分数据
	 * 
	 * @param roleId 权限角色id
	 */
	@Override
	protected void reloadDate(Integer roleId) {
		// 调用并由开人员装配
		roleResourceDataSource.selectOne(chiyaHashMapValueMap, roleId);
	}

	/**
	 * 比较权限信息
	 * 
	 * @param roleId 权限角色id
	 * @param url    接口地址
	 * @param method 请求方式
	 * @return true:存在/false:不存在
	 */
	public boolean contains(Integer roleId, String url, String method) {
		// 检查这个角色是否需要更新
		checkReload(roleId);
		// 检查这个角色是否持有这个路径下这个请求方式
		return BitUtil.macthBit(chiyaHashMapValueMap.get(roleId, url), Method.getOffset(method));
	}

	/**
	 * 比较权限信息
	 * 
	 * @param roleIdSet 权限角色集合
	 * @param url       接口地址
	 * @param method    请求方式
	 * @return true:存在/false:不存在
	 */
	public boolean contains(Set<Integer> roleIdSet, String url, String method) {
		// 迭代判断是否需要更新数据
		roleIdSet.forEach(i -> checkReload(i));
		// 获取偏移位置
		int offset = Method.getOffset(method);
		for (Integer roleId : roleIdSet) {
			if (BitUtil.macthBit(chiyaHashMapValueMap.get(roleId, url), offset)) {
				// 如果存在则返回
				return true;
			}
		}
		return false;
	}

}
