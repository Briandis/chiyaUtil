package chiya.core.base.unique.algorithm;

import java.util.concurrent.atomic.AtomicLong;

import chiya.core.base.thread.BlockLock;
import chiya.core.base.unique.UniqueGenerate;

/**
 * 阻塞的原子自增
 * 
 * @author brain
 */
public class BlockAtomicLong implements UniqueGenerate {

	/** 自增的值 */
	private AtomicLong atomicLong = new AtomicLong();
	/** 自增上限，默认Integer类型上限 */
	public long length = Integer.MAX_VALUE;
	/** 重复间隔，毫秒，默认1秒 */
	public long time = 1_000;
	/** 阻塞锁 */
	private BlockLock blockLock = new BlockLock();

	/** 无参构造 */
	public BlockAtomicLong() {}

	/***
	 * 构造方法，
	 * 
	 * @param length 递增长度
	 * @param time   超时时间
	 */
	public BlockAtomicLong(long length, long time) {
		this.length = length;
		this.time = time;
	}

	@Override
	public long generate() {
		long value = 0;
		try {
			while (true) {
				value = atomicLong.getAndIncrement();
				if (value < length) { return value; }
				blockLock.nextWait(
					time,
					() -> {
						if (atomicLong.longValue() >= length) { atomicLong.set(0); }
					}
				);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取递增长度
	 *
	 * @return 递增长度
	 */
	public long getLength() {
		return length;
	}

	/**
	 * 设置递增长度
	 *
	 * @param length 要设置的递增长度
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/**
	 * 获取超出上限后的重置时间
	 *
	 * @return time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * 设置超出上限后的重置时间
	 *
	 * @param time 超出上限后的重置时间,毫秒
	 */
	public void setTime(long time) {
		this.time = time;
	}

}
