package chiya.core.base.unique.algorithm;

import java.util.concurrent.atomic.AtomicLong;

import chiya.core.base.unique.UniqueGenerate;

/**
 * 雪花算法唯一性生成器<br>
 * 单机高并发不适用，该算法适用分布式高并发下<br>
 * 警告！多线程超高并发下会重复，1毫秒仅能承受4095个
 * 
 * @author chiya
 */
public class Snowflake implements UniqueGenerate {

	/**
	 * 递增上限，12位，约4095个
	 */
	private static final int INCREMENTAL_UPPER_LIMIT = 0x0000_0FFF;

	/**
	 * 机器码所在位置
	 */
	private static final int MACHINE_CODE = 0x0000_03FF;

	/**
	 * 原子类，原子计数获取值时会导致获取的是最新的值，而不是这次自增的
	 */
	private AtomicLong atomicLong = new AtomicLong();

	/**
	 * 雪花算法<br>
	 * 符号位不使用，[1-41]位为时间戳,[42-52]是机房+机器,[53-63]为递增序列
	 * 
	 * @param machineCode 机器码
	 * @return long 唯一性id
	 */
	public long next(long machineCode) {
		long nowTime = System.currentTimeMillis();
		long increment = atomicLong.incrementAndGet() & INCREMENTAL_UPPER_LIMIT;
		long id = (nowTime << 22) & Long.MAX_VALUE;
		machineCode = (machineCode & MACHINE_CODE) << 12;
		id = id | machineCode;
		id = id | increment;
		return id;
	}

	/**
	 * 机器码默认0
	 */
	@Override
	public long generate() {
		return next(0);
	}
}
