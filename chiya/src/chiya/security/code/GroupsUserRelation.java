package chiya.security.code;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.bit.BitMap;
import chiya.core.base.collection.ChiyaHashMapValueSet;
import chiya.security.api.GroupsUserDataSource;

/**
 * 用户组持有的用户
 */
public class GroupsUserRelation extends BaseManageRelation {

	/**
	 * 基础容器，<用户组id，用户>，适合连续大量的
	 */
	private ConcurrentHashMap<Integer, BitMap> userBitMap = new ConcurrentHashMap<Integer, BitMap>();
	/**
	 * 基础容器，<用户组id，用户>，适合高度离散和少量的
	 */
	private ChiyaHashMapValueSet<Integer, Integer> userHasMap = new ChiyaHashMapValueSet<Integer, Integer>();

	/**
	 * 组成员数据源
	 */
	private GroupsUserDataSource groupsUserDataSource;

	/**
	 * 默认构造方法
	 * 
	 * @param groupsUserDataSource 组成员数据源
	 */
	public GroupsUserRelation(GroupsUserDataSource groupsUserDataSource) {
		this.groupsUserDataSource = groupsUserDataSource;
	}

	/**
	 * 更新所有数据
	 */
	@Override
	protected void reloadAllData() {
		userHasMap.remove();
		groupsUserDataSource.selectAll(userHasMap);
	}

	/**
	 * 更新部分数据
	 * 
	 * @param groupId 组Id
	 */
	@Override
	protected void reloadDate(Integer groupId) {
		groupsUserDataSource.selectOne(userHasMap, groupId);
	}

	/**
	 * 获取用户所在的所有用户组
	 * 
	 * @param userId 用户Id
	 * @return Set<Integer>:用户组的集合
	 */
	public Set<Integer> getUserGropu(Integer userId) {
		Set<Integer> set = new HashSet<Integer>();
		// 位图查找用户
		userBitMap.entrySet().forEach(entry -> {
			if (entry.getValue().get(userId)) { set.add(entry.getKey()); }
		});
		// 查找set存储的
		userHasMap.entrySet().forEach(entry -> {
			checkReload(entry.getKey());
			if (entry.getValue().contains(userId)) { set.add(entry.getKey()); }
		});
		return set;
	}

	/**
	 * 将用户移除用户组
	 * 
	 * @param userGroupId 用户组ID
	 * @param userId      用户ID
	 */
	public void remove(Integer userGroupId, Integer userId) {
//		if (userBitMap.contains(userGroupId)) { userBitMap.get(userGroupId).remove(userId); }
		userHasMap.remove(userGroupId, userId);
	}

	/**
	 * 添加用户至用户组
	 * 
	 * @param userGroupId 用户组ID
	 * @param userId      用户ID
	 */
	public void put(Integer userGroupId, Integer userId) {
//		if (!userBitMap.containsKey(userGroupId)) {
//			synchronized (userBitMap) {
//				if (!userBitMap.containsKey(userGroupId)) { userBitMap.put(userGroupId, new BitMap()); }
//			}
//		}
//		userBitMap.get(userGroupId).add(userId);
		userHasMap.put(userGroupId, userId);
	}

}
