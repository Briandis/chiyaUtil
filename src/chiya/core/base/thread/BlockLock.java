package chiya.core.base.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import chiya.core.base.function.VoidNoParamFunction;

/**
 * 延时阻塞锁<br>
 * 使用场景，业务需要通过线程保证线程在某一时刻同步进行<br>
 * 例如5个线程需要在1s后执行，那么进入nextWait方法后，就会统一在1s后执行
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
	/** 当前等大的线程数量 */
	private volatile int threadCount = 0;

	/**
	 * 阻塞并等待至下一次
	 * 
	 * @param time  毫秒
	 * @param reset 重置的方法
	 * @throws InterruptedException 中断异常
	 */
	public void nextWait(long time, VoidNoParamFunction reset) throws InterruptedException {
		// 如果当前时间+等待时间小于当前时间，则全部阻塞，等待下一次
		long waitTime = (lastTime + time - System.currentTimeMillis()) * 1_000_000;
		// 先进来的，意味着要执行重置的任务，解决全部等待唤醒后的问题，
		if (tryLock.tryLock()) {
			// 重置的线程也需要等待，但是会持有两把锁，一个用于等待，等待结束后会释放，tryLock的锁回在重置后释放
			// 由于更新前需要等待，所以如果更新后，更新锁被新涌进的线程抢走锁，那么也是会在下一轮进行更新
			next(waitTime);
			try {
				lastTime = System.currentTimeMillis();
				reset.task();
			} finally {
				tryLock.unlock();
			}
		} else {
			// 其他进来的直接等待
			next(waitTime);
		}
	}

	/**
	 * 集合等待
	 * 
	 * @param count 等待多个少个线程数量后执行
	 * @throws InterruptedException
	 */
	public void gather(int count) throws InterruptedException {
		lock.lock();
		try {
			threadCount++;
			if (threadCount < count) {
				condition.await();
			} else {
				condition.signalAll();
				threadCount = 0;
			}
		} finally {
			lock.unlock();
		}

	}

	/**
	 * 等待至某一个时刻
	 * 
	 * @param time 等待多少纳秒，1毫秒=1_000_000纳秒
	 * @throws InterruptedException
	 */
	private void next(long time) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			// 乘百万得到纳秒，并且此处是等待到下一轮
			condition.awaitNanos(time);
		} finally {
			lock.unlock();
		}
	}

}
