package chiya.core.base.thread;

/**
 * 线程存储空间工具库
 * 
 * @author chiya
 */
public class ThreadSpace {
	/** 私有化构造方法 */
	private ThreadSpace() {}

	/** 存储空间 */
	private final static ChiyaSpace<String, Object> CHIYA_SPACE = new ChiyaSpace<String, Object>();

	/**
	 * 获取当前map中key的值
	 * 
	 * @param key 键
	 * @return Object
	 */
	public static Object get(String key) {
		return CHIYA_SPACE.get(key);
	}

	/**
	 * 当前线程Map中添加一个key、value
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public static void put(String key, Object value) {
		CHIYA_SPACE.put(key, value);
	}

	/**
	 * 如果存在则获取，不存在则插入
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 持有的值/新插入的值
	 */
	public static Object computeIfAbsent(String key, Object value) {
		return CHIYA_SPACE.computeIfAbsent(null, CHIYA_SPACE);
	}

	/**
	 * 删除当前Map中对应key
	 * 
	 * @param key 键
	 */
	public static void remove(String key) {
		CHIYA_SPACE.remove(key);
	}

	/**
	 * 清除当前线程Map中所有的数据
	 */
	public static void clear() {
		CHIYA_SPACE.clear();
	}

}
