package chiya.core.base.collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * KEY VALUE结构体
 * 
 * @author chiya
 *
 */
public class MapData extends ConcurrentHashMap<String, Object> {
	/** 随机序列化ID */
	private static final long serialVersionUID = 4182650951884365792L;

	/**
	 * 链式添加键值对
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 自身
	 */
	public MapData chainPut(String key, Object value) {
		put(key, value);
		return this;
	}

}
