package chiya.core.base.collection;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import chiya.core.base.function.VoidGenericFunction;

/**
 * 资源池
 * 
 * @author chiya
 *
 * @param <T>
 */
public class ResourcesPool<T> {
	/** 任务队列 */
	public ArrayBlockingQueue<T> taskQueue = new ArrayBlockingQueue<>(128);
	/** 读写偏向锁 */
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 使用资源 直至使用完
	 * 
	 * @param function 方法
	 */
	public void use(VoidGenericFunction<T> function) {
		T resource = null;
		try {
			lock.readLock().lock();
			// 获取资源
			resource = taskQueue.take();
			function.execute(resource);
		} catch (Exception e) {
			function.error(e);
		} finally {
			// 将资源放回阻塞队列
			taskQueue.add(resource);
			lock.readLock().unlock();
		}

	}

	/**
	 * 添加
	 * 
	 * @param data
	 */
	public void add(T data) {
		try {
			lock.writeLock().lock();
			taskQueue.add(data);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 重载
	 * 
	 * @param list 重载的资源
	 */
	public void reload(List<T> list) {
		try {
			lock.writeLock().lock();
			taskQueue.clear();
			taskQueue.addAll(list);
		} finally {
			lock.writeLock().unlock();
		}

	}

}
