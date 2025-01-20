package chiya.core.base.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import chiya.core.base.function.GenericNoParamFunction;

/**
 * 阻塞队列
 * 
 * @author chiya
 *
 * @param <T>
 */
public class BlockQueue<T> {
	/** 阻塞队列 */
	private ArrayBlockingQueue<T> queue;
	/** 当前队列实际数量 */
	private AtomicInteger objectCount = new AtomicInteger();
	/** 队列上线 */
	private int queueMaxSize;

	/**
	 * 构建阻塞队列
	 * 
	 * @param count 数量
	 */
	public BlockQueue(int count) {
		this.queueMaxSize = count;
		queue = new ArrayBlockingQueue<>(count);
	}

	/**
	 * 获取一个队列中的或者构建或者等待
	 * 
	 * @return 对象
	 */
	public T getOrNewOrWait(GenericNoParamFunction<T> function) {
		T t = queue.poll();
		if (t != null) { return t; }
		// 双重检测锁
		if (queueMaxSize > objectCount.intValue()) {
			synchronized (queue) {
				if (queueMaxSize > objectCount.intValue()) {
					objectCount.incrementAndGet();
					return function.getValue();
				}
			}
		}
		return take();

	}

	/**
	 * 添加新元素
	 * 
	 * @param t 对象
	 */
	public void add(T t) {
		queue.add(t);
		objectCount.incrementAndGet();
	}

	/**
	 * 放回对象
	 * 
	 * @param t 对象
	 */
	public void putBack(T t) {
		queue.add(t);
	}

	/**
	 * 获取队首，没有返回null
	 * 
	 * @return 对象
	 */
	public T poll() {
		return queue.poll();
	}

	/**
	 * 获取队首，没有则阻塞
	 * 
	 * @return 对象
	 */
	public T take() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/** 废弃当前对象 */
	public void decrement() {
		objectCount.decrementAndGet();
	}

}
