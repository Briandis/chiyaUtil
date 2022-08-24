package chiya.core.base.thread;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;

import chiya.core.base.function.BooleanFunction;
import chiya.core.base.function.Function;
import chiya.core.base.function.GetValueFunction;
import chiya.core.base.string.StringUtil;

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
	 * 创建N个守护线程，并执行
	 * 
	 * @param count    线程数量
	 * @param runnable 任务体
	 * @return Thread 创建后并执行的线程
	 */
	public static Thread[] createDaemonAndStart(int count, Runnable runnable) {
		Thread[] threads = createThread(count, runnable);
		for (int i = 0; i < threads.length; i++) {
			threads[i].setDaemon(true);
		}
		start(threads);
		return threads;
	}

	/**
	 * 创建N个线程并启动
	 * 
	 * @param count    线程数量
	 * @param runnable 线程任务体
	 * @return 执行后的线程对象
	 */
	public static Thread[] createAndStart(int count, Runnable runnable) {
		Thread[] threads = createThread(count, runnable);
		start(threads);
		return threads;
	}

	/**
	 * 创建线程，但是不执行
	 * 
	 * @param count    线程数量
	 * @param runnable 线程任务体
	 * @return 线程数组
	 */
	public static Thread[] createThread(int count, Runnable runnable) {
		if (count < 1) { count = 1; }
		Thread[] threads = new Thread[count];
		for (int i = 0; i < threads.length; i++) {
			Thread thread = new Thread(runnable);
			threads[i] = thread;
		}
		return threads;
	}

	/**
	 * 开启线程，如果线程未执行
	 * 
	 * @param thread 线程对象
	 */
	public static void start(Thread thread) {
		if (thread != null && !thread.isAlive()) { thread.start(); }
	}

	/**
	 * 开启线程，如果线程未执行
	 * 
	 * @param thread 线程对象
	 */
	public static void start(Thread thread[]) {
		if (thread != null) {
			for (int i = 0; i < thread.length; i++) {
				start(thread[i]);
			}
		}
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
	 * 双重条件锁
	 * 
	 * @param booleanFunction 布尔类型返回值检测方法
	 * @param lock            lock锁对象
	 * @param function        执行的方法
	 */
	public static void doubleCheckLock(BooleanFunction booleanFunction, Lock lock, Function function) {
		if (booleanFunction.task()) {
			lock.lock();
			try {
				if (booleanFunction.task()) { function.task(); }
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 执行任务并自动获取和释放锁
	 * 
	 * @param lock     Lock锁对象
	 * @param function 执行的方法
	 */
	public static void lock(Lock lock, Function function) {
		lock.lock();
		try {
			function.task();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 执行任务并自动获取和释放锁，携带返回值
	 * 
	 * @param <T>      泛型类型
	 * @param lock     Lock锁对象
	 * @param function 执行的方法
	 */
	public static <T> T lock(Lock lock, GetValueFunction<T> function) {
		lock.lock();
		try {
			return function.getValue();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 让当前线程睡眠多少毫秒
	 * 
	 * @param millisecond 毫秒
	 */
	public static void sleep(long millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据线程获取所对应的方法栈信息
	 * 
	 * @param thread 线程对象
	 * @return 方法栈信息
	 */
	public static StackTraceElement[] getStackTrace(Thread thread) {
		return Thread.getAllStackTraces().get(thread);
	}

	/**
	 * 获取当前方法栈信息
	 * 
	 * @return 方法栈信息
	 */
	public static StackTraceElement[] getStackTrace() {
		return getStackTrace(Thread.currentThread());
	}

	/**
	 * 获取调用者的信息
	 * 
	 * @return 调用者的信息
	 */
	public static String getTransfer() {
		StackTraceElement[] stackTraceElement = getStackTrace();
		String className = stackTraceElement[stackTraceElement.length - 1].getClassName();
		int i = 0;
		for (i = className.length() - 1; i > 0; i--) {
			if (className.charAt(i) == '.') { break; }
		}
		return className.substring(0, i);
	}

	/**
	 * 获取特定包方法栈信息
	 * 
	 * @param packages          包路径
	 * @param stackTraceElement 方法栈数组
	 * @return 方法栈信息
	 */
	public static StackTraceElement[] getPackageStackTrace(String packages, StackTraceElement[] stackTraceElement) {
		StackTraceElement[] returnData = new StackTraceElement[stackTraceElement.length];
		int i = 0;
		for (StackTraceElement stackTrace : stackTraceElement) {
			if (StringUtil.findString(stackTrace.getClassName(), packages) != -1) {
				// 如果这个包存在，就加入字符串中
				returnData[i] = stackTrace;
				i++;
			}
		}
		return Arrays.copyOf(returnData, i);
	}

	/**
	 * 获取当前线程的方法栈信息
	 * 
	 * @return 方法栈信息
	 */
	public static StackTraceElement[] getPackageStackTrace() {
		// 获取栈信息
		StackTraceElement[] stackTraceElement = getStackTrace();
		String className = stackTraceElement[stackTraceElement.length - 1].getClassName();
		int i = 0;
		for (i = className.length() - 1; i > 0; i--) {
			if (className.charAt(i) == '.') { break; }
		}
		// 获取配置信息
		String packages = className.substring(0, i);
		return getPackageStackTrace(packages, stackTraceElement);
	}

	/**
	 * 获取特定包方法栈信息
	 * 
	 * @param packages 包路径
	 * @return 方法栈信息
	 */
	public static StackTraceElement[] getPackageStackTrace(String packages) {
		return getPackageStackTrace(packages, getStackTrace());
	}
}
