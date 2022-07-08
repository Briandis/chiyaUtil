package chiya.core.base.cache;

import chiya.core.base.function.GenericityFunction;
import chiya.core.base.thread.ThreadUtil;

/**
 * 自定义缓存
 * 
 * @author brian
 */
public class ChiyaCache<T> {

	/** 缓存更新状态位 */
	public volatile boolean NEED_UPDATE = true;
	/** 缓存的数据 */
	private T data;

	/**
	 * 构造方法
	 * 
	 * @param data 作为缓存的容器
	 */
	public ChiyaCache(T data) {
		this.data = data;
	}

	/**
	 * 获取缓存的存储容器
	 * 
	 * @return 缓存容器
	 */
	public T getCache() {
		return data;
	}

	/**
	 * 重新加载缓存
	 * 
	 * @param genericityFunction 更新缓存的方法
	 */
	public void reacquire(GenericityFunction<T> genericityFunction) {
		ThreadUtil.doubleCheckLock(
			() -> NEED_UPDATE,
			this,
			() -> {
				genericityFunction.next(data);
				NEED_UPDATE = false;
			}
		);
	}

}
