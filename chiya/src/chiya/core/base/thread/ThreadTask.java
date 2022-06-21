package chiya.core.base.thread;

import chiya.core.base.function.Task;

/**
 * 线程任务
 * 
 * @author chiya
 * @param <T> 处理的任务
 */
public abstract class ThreadTask<T> {
	/** 是否执行 */
	private volatile boolean isRun = true;
	/** 判断是否开始 */
	private Boolean isStart = false;
	/** 处理任务 */
	private Task<T> task;
	/** 默认线程数量 */
	private int threadSize = 1;

	/**
	 * 默认构造方法
	 * 
	 * @param task 任务
	 */
	public ThreadTask(Task<T> task) {
		this.task = task;
	}

	/**
	 * 设置使用的线程数量的构造方法
	 * 
	 * @param task    任务
	 * @param maxSize 最大值
	 */
	public ThreadTask(Task<T> task, int threadSize) {
		this.task = task;
		setThreadSize(threadSize);
	}

	/**
	 * 设置使用的线程数量
	 * 
	 * @param count 数量
	 */
	public void setThreadSize(int count) {
		threadSize = count < 0 ? 1 : count;
	}

	/**
	 * 执行
	 * 
	 * @param isDaemon 是否是守护线程
	 */
	public void start(boolean isDaemon) {
		ThreadUtil.doubleCheckLock(
			() -> {
				if (isStart) { throw new IllegalThreadStateException("该任务已经启动"); }
				return isStart;
			},
			isStart,
			() -> isStart = true
		);
		isRun = true;
		for (int i = 0; i < threadSize; i++) {
			if (isDaemon) {
				ThreadUtil.createDaemonAndStart(() -> task());
			} else {
				ThreadUtil.createAndStart(() -> task());
			}
		}
	}

	/**
	 * 以守护进程方式启动
	 */
	public void start() {
		start(true);
	}

	/**
	 * 执行下一个任务后停止
	 */
	public void nextStop() {
		isRun = false;
		isStart = false;
	}

	/**
	 * 迭代获取下一个对象
	 * 
	 * @return 迭代对象
	 * @exception 迭代时发送的异常
	 */
	public abstract T next() throws Exception;

	/**
	 * 任务体
	 */
	private final void task() {
		while (isRun) {
			try {
				T t = next();
				task.task(t);
			} catch (Exception e) {
				task.error(e);
			}
		}
	}

}
