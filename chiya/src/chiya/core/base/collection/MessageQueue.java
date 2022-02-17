package chiya.core.base.collection;

import java.util.concurrent.LinkedBlockingQueue;

import chiya.core.base.other.Task;
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
	public MessageQueue(Task<T> task) {
		super(task);
		queue = new LinkedBlockingQueue<>();
	}

	/**
	 * 设置队列最大上限构造方法
	 * 
	 * @param task    任务
	 * @param maxSize 最大值
	 */
	public MessageQueue(Task<T> task, int maxSize) {
		super(task);
		queue = new LinkedBlockingQueue<>(maxSize);
	}

	@Override
	public T next() throws Exception {
		return queue.take();
	}

}
