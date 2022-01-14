package chiya.core.base.collection;

import java.util.HashMap;

/**
 * 计数工具
 * 
 * @author chiya
 */
public class CountMap<T> {

	/** 计数器 */
	private HashMap<T, Integer> count = new HashMap<T, Integer>();

	/**
	 * 数量+1
	 * 
	 * @param key 键
	 */
	public void add(T key) {
		count.put(key, count.containsKey(key) ? count.get(key) + 1 : 1);
	}

	/**
	 * 数量-1
	 * 
	 * @param key 键
	 */
	public void reduce(T key) {
		count.put(key, count.containsKey(key) ? count.get(key) - 1 : -1);
	}

	/**
	 * 获取计数器
	 *
	 * @return HashMap<T, Integer> 计数器
	 */
	public HashMap<T, Integer> getCount() {
		return count;
	}

}
