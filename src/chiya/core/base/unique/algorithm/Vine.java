package chiya.core.base.unique.algorithm;

import java.util.concurrent.atomic.AtomicLong;

import chiya.core.base.unique.UniqueGenerate;

/**
 * 基于雪花算法的魔改版，取消了机器码<br>
 * 1毫秒可承受约400万个
 * 
 * @author chiya
 */
public class Vine implements UniqueGenerate {
	/**
	 * 递增上限，22位，约400万个
	 */
	private static final int INCREMENTAL_UPPER_LIMIT = 0x003F_FFFF;

	/**
	 * 原子类，原子计数获取值时会导致获取的是最新的值，而不是这次自增的
	 */
	private AtomicLong atomicLong = new AtomicLong();

	/**
	 * 符号位不使用，[1-41]位为时间戳,[42-63]为递增序列
	 * 
	 * @return long 唯一性id
	 */
	@Override
	public long generate() {
		long increment = atomicLong.incrementAndGet() & INCREMENTAL_UPPER_LIMIT;
		long id = (System.currentTimeMillis() << 22) & Long.MAX_VALUE;
		return id | increment;
	}
}
