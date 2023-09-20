package chiya.core.base.cache;

import chiya.core.base.function.ListGenericNoParamFunction;
import chiya.core.base.function.VoidGenericFunction;
import chiya.core.base.function.GenericGenericFunction;
import chiya.core.base.loop.Loop;
import chiya.core.base.thread.ThreadUtil;
import chiya.core.base.time.task.TimeQueue;

/**
 * 异步写入缓存构造方法,提供一个非静态写入下的一个初始化方法
 * 
 * @author brian
 * @param <K> 唯一标识
 * @param <V> 存储对象
 */
public class DelayedWriteCache<K, V> extends MapLockCache<K, V> {

	/** 异步写入存的延时队列 */
	private TimeQueue<K, V> timeQueue;
	/** 异步写入方法 */
	private VoidGenericFunction<V> task;
	/** 异步多久后写入，单位毫秒，默认30秒 */
	private long time = 30_000;

	/**
	 * 异步写入缓存构造方法
	 * 
	 * @param valueGetFunction 获取对象的唯一标识
	 * @param task             异步写入的方法
	 */
	public DelayedWriteCache(GenericGenericFunction<V, K> valueGetFunction, VoidGenericFunction<V> task) {
		super(valueGetFunction);
		this.task = task;
		timeQueue = new TimeQueue<>(task);
		timeQueue.start();
	}

	/**
	 * 异步写入缓存构造方法
	 * 
	 * @param valueGetFunction 获取对象的唯一标识
	 */
	public DelayedWriteCache(GenericGenericFunction<V, K> valueGetFunction) {
		super(valueGetFunction);
	}

	/**
	 * 设置异步多久后写入
	 * 
	 * @param time 等待时长单位毫秒
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * 初始化构造异步写入
	 * 
	 * @param task 异步写入的方法
	 */
	public void init(VoidGenericFunction<V> task) {
		this.task = task;
		timeQueue = new TimeQueue<>(task);
		timeQueue.start();
	}

	/**
	 * 根据唯一标识，延迟写入
	 * 
	 * @param key 唯一标识
	 */
	public void change(K key) {
		change(key, get(key));
	}

	/**
	 * 根据唯一标识，延迟写入
	 * 
	 * @param key  唯一标识
	 * @param data 对象
	 */
	public void change(K key, V data) {
		change(key, data, time);
	}

	/**
	 * 根据唯一标识，延迟写入
	 * 
	 * @param key  唯一标识
	 * @param data 对象
	 * @param time 异步时长
	 */
	public void change(K key, V data, long time) {
		if (timeQueue == null) { return; }
		if (key != null && data != null) {
			// 1分钟后异步写入
			timeQueue.putOrChange(key, data, time);
		}
	}

	/**
	 * 将所有更变，直接写入
	 */
	public void changeAll() {
		if (timeQueue == null) { return; }
		Loop.forEach(timeQueue.getKeys(), key -> task.execute(get(key)));
		timeQueue.remove();
	}

	/**
	 * 重新拉取全部数据
	 * 
	 * @param function 获取数据方法
	 */
	@Override
	public void reacquire(ListGenericNoParamFunction<V> function) {
		ThreadUtil.doubleCheckLock(
			() -> isNeedReload(),
			reentrantReadWriteLock.writeLock(),
			() -> {
				changeAll();
				remove();
				super.add(function.getList());
				notReload();
			}
		);
	}

}
