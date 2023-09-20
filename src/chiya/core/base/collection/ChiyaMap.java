package chiya.core.base.collection;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * 简单的键值对，用于封装业务返回对象
 * 
 * @author brain
 *
 */
public class ChiyaMap {

	private HashMap<String, Object> data = new HashMap<>();

	/**
	 * 链式添加键值对
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 自身
	 */
	public ChiyaMap chainPut(String key, Object value) {
		data.put(key, value);
		return this;
	}

	/**
	 * 获取值
	 * 
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
		return data.get(key);
	}

	/**
	 * 删除键
	 * 
	 * @param key 键
	 * @return 值
	 */
	public Object remove(String key) {
		return data.remove(key);
	}

	/**
	 * 获取存储的hashMap
	 * 
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getData() {
		return data;
	}

	/**
	 * 迭代方法
	 * 
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(BiConsumer<String, Object> action) {
		data.forEach(action);
	}

}
