package chiya.core.base.cache;

import chiya.core.base.function.Task;
import chiya.core.base.thread.ThreadUtil;

/**
 * 自定义缓存
 * 
 * @author brain
 *
 * @param <V> 待缓存类型
 */
public class ChiyaCache<V> extends BaseCache {

	/** 缓存的数据 */
	private V data;

	/**
	 * 构造方法
	 * 
	 * @param data 作为缓存的容器
	 */
	public ChiyaCache(V data) {
		this.data = data;
	}

	/**
	 * 获取缓存的存储容器
	 * 
	 * @return 缓存容器
	 */
	public V getCache() {
		return data;
	}

	/**
	 * 重新加载缓存
	 * 
	 * @param task 更新缓存的方法
	 */
	public void reacquire(Task<V> task) {
		ThreadUtil.doubleCheckLock(
			() -> isNeedReload(),
			this,
			() -> {
				task.task(data);
				notReload();
			}
		);
	}

}
