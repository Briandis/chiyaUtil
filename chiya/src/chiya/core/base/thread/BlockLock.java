package chiya.core.base.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import chiya.core.base.function.Function;

/**
 * 阻塞锁
 * 
 * @author brian
 */
public class BlockLock {
	/** 用于延时的同步锁 */
	private ReentrantLock lock = new ReentrantLock();
	/** 用于确保只有一个更新 */
	private ReentrantLock tryLock = new ReentrantLock();

	/** 锁通知 */
	private Condition condition = lock.newCondition();
	/** 最后一次同步时间，毫秒 */
	private volatile long lastTime = System.currentTimeMillis();

	/**
	 * 阻塞并等待至下一次
	 * 
	 * @param time     纳秒，需要1000*1000*1得到毫秒
	 * @param function 方等待结束后执行的方法
	 * @throws InterruptedException 中断异常
	 */
	public void nextWait(long time, Function Function) throws InterruptedException {
		long now = System.currentTimeMillis();
		long end = lastTime + time / 1_000_000;
		// 如果当前时间+等待时间小于当前时间，则全部阻塞
		if (end > now) {
			// 进来的全部等待下一个批次
			try {
				lock.lockInterruptibly();
				condition.awaitNanos((end - now) * 1_000_000);
			}
			finally {
				lock.unlock();
			}

		}

		// 只需要一个线程重置
		if (tryLock.tryLock()) {
			try {
				lastTime = now;
				Function.task();
			}
			finally {
				tryLock.unlock();
			}
		}

	}
}
