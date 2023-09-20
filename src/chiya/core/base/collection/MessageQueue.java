package chiya.core.base.collection;

import java.util.concurrent.LinkedBlockingQueue;

import chiya.core.base.function.VoidGenericFunction;
import chiya.core.base.thread.ThreadTask;

/**
 * 链表式消息队列
 * 
 * @author chiya
 * @param <T> 传入消息对象
 */
public class MessageQueue<T> extends ThreadTask<T> {
	/** 链式队列 */
	private volatile LinkedBlockingQueue<T> queue;

	/**
	 * 默认构造方法
	 * 
	 * @param task 任务
	 */
	public MessageQueue(VoidGenericFunction<T> task) {
		super(task);
		queue = new LinkedBlockingQueue<>();
	}

	/**
	 * 设置队列最大上限构造方法
	 * 
	 * @param task    任务
	 * @param maxSize 最大值
	 */
	public MessageQueue(VoidGenericFunction<T> task, int maxSize) {
		super(task);
		queue = new LinkedBlockingQueue<>(maxSize);
	}

	@Override
	public T next() throws Exception {
		return queue.take();
	}

	/**
	 * 添加传递信息
	 * 
	 * @param t 传递的对象数据
	 */
	public void put(T t) {
		queue.add(t);
	}

}
