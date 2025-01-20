package chiya.core.base.collection;

import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.function.BooleanGenericFunction;
import chiya.core.base.function.GenericGenericFunction;
import chiya.core.base.thread.ThreadUtil;

/**
 * 对象管理池
 * 
 * @author chiya
 *
 */
public class ObjectPool<K, V> {
	/** 任务队列 */
	private ConcurrentHashMap<K, BlockQueue<V>> map = new ConcurrentHashMap<>();
	/** 队列大小 */
	private int queueSize = 128;

	/**
	 * 使用资源
	 * 
	 * @param key            key
	 * @param function       使用方法，需要返回是否返回对象
	 * @param createFunction 生成对象方法
	 */
	public void use(K key, BooleanGenericFunction<V> function, GenericGenericFunction<K, V> createFunction) {
		ThreadUtil.doubleCheckLock(
			() -> !map.containsKey(key),
			map,
			() -> map.put(key, new BlockQueue<V>(queueSize))
		);

		BlockQueue<V> queue = map.get(key);
		V value = queue.getOrNewOrWait(() -> createFunction.get(key));
		boolean b = function.execute(value);
		if (b) {
			queue.putBack(value);
		} else {
			// 该对象废弃
			queue.decrement();
		}
	}

	/**
	 * 使用资源
	 * 
	 * @param key            key
	 * @param function       使用方法，需要返回是否返回对象
	 * @param createFunction 生成对象方法
	 */
	public void tryUse(K key, BooleanGenericFunction<V> function, GenericGenericFunction<K, V> createFunction) {
		ThreadUtil.doubleCheckLock(
			() -> !map.containsKey(key),
			map,
			() -> map.put(key, new BlockQueue<V>(queueSize))
		);

		BlockQueue<V> queue = map.get(key);
		V value = queue.getOrNewOrWait(() -> createFunction.get(key));
		boolean b = function.execute(value);
		if (b) {
			queue.putBack(value);
		} else {
			// 该对象废弃
			queue.decrement();

			// 尝试二次调用
			value = queue.getOrNewOrWait(() -> createFunction.get(key));
			b = function.execute(value);
			if (b) {
				queue.putBack(value);
			} else {
				// 该对象废弃
				queue.decrement();
			}
		}
	}

}
