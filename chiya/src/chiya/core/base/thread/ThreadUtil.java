package chiya.core.base.thread;

import chiya.core.base.function.BooleanFunction;
import chiya.core.base.function.Function;

/**
 * 线程工具
 * 
 * @author chiya
 */
public class ThreadUtil {
	/**
	 * 创建一个守护线程，并执行
	 * 
	 * @param runnable 任务体
	 * @return Thread 创建后并执行的线程
	 */
	public static Thread createDaemonAndStart(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();
		return thread;
	}

	/**
	 * 创建一个线程并启动
	 * 
	 * @param runnable 线程任务体
	 * @return 执行后的线程对象
	 */
	public static Thread createAndStart(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
		return thread;
	}

	/**
	 * 获取线程名字
	 * 
	 * @return String:线程名字
	 */
	public static String getThreadName() {
		return Thread.currentThread().getName();
	}

	/**
	 * 更改线程名称
	 * 
	 * @param name 新的名字
	 */
	public static void setThreadName(String name) {
		Thread.currentThread().setName(name);
	}

	/**
	 * 获取当前线程ID
	 * 
	 * @return long:线程ID
	 */
	public static long getThreadId() {
		return Thread.currentThread().getId();
	}

	/**
	 * 双重条件锁
	 * 
	 * @param booleanFunction 布尔类型返回值检测方法
	 * @param lock            同步的锁
	 * @param function        执行的方法
	 */
	public static void doubleCheckLock(BooleanFunction booleanFunction, Object lock, Function function) {
		if (booleanFunction.task()) {
			synchronized (lock) {
				if (booleanFunction.task()) { function.task(); }
			}
		}
	}

	/**
	 * 让当前线程睡眠多少秒
	 * 
	 * @param second 秒
	 */
	public static void sleep(int second) {
		try {
			Thread.sleep(1000 * second);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
