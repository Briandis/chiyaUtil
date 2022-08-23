package chiya.log.cache;

import java.util.concurrent.ArrayBlockingQueue;

import chiya.core.base.function.Task;
import chiya.core.base.thread.ThreadTask;

/**
 * 日志缓存
 */
public class LogCache extends ThreadTask<String> {
	/** 数组列队 */
	private volatile ArrayBlockingQueue<String> queue = null;
	/** 默认缓存大小 */
	private static final int CACHE_SIZE = 65535;

	/**
	 * 默认构造方法
	 * 
	 * @param task 消息处理
	 */
	public LogCache(Task<String> task) {
		super(task);
		queue = new ArrayBlockingQueue<>(CACHE_SIZE);
	}

	/**
	 * 自定义大小构造方法
	 * 
	 * @param task      消息处理
	 * @param cacheSize 缓存大小
	 */
	public LogCache(Task<String> task, int cacheSize) {
		super(task);
		queue = new ArrayBlockingQueue<>(cacheSize);
	}

	@Override
	public String next() throws Exception {
		return queue.take();
	}

	/**
	 * 阻塞添加日志消息
	 * 
	 * @param message 日志消息
	 */
	public void add(String message) {
		try {
			queue.put(message);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
