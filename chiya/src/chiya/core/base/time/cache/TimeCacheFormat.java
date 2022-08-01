package chiya.core.base.time.cache;

import chiya.core.base.thread.ThreadUtil;
import chiya.core.base.time.DateUtil;
import chiya.core.base.time.NowTime;

/**
 * 从换从中获取当前时间
 */
public abstract class TimeCacheFormat {
	/** 当前时间 */
	protected volatile String current;
	/** 当前时间的时间戳 */
	protected volatile long currentTime = 0;

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间的字符串
	 */
	public String getNowTime() {
		ThreadUtil.doubleCheckLock(
			() -> isUpdateCache(),
			NowTime.class,
			() -> {
				current = DateUtil.format(System.currentTimeMillis(), getFormatExpression());
				currentTime = System.currentTimeMillis();
			}
		);
		return current;
	}

	/**
	 * 获取时间格式化格式
	 * 
	 * @return 时间格式化的表达式
	 */
	abstract String getFormatExpression();

	/**
	 * 什么时候更新缓存
	 * 
	 * @return true:需要更新/false:不需要更新
	 */
	abstract boolean isUpdateCache();
}
