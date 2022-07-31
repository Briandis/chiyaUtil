package chiya.core.base.time;

import chiya.core.base.string.StringUtil;
import chiya.core.base.thread.ThreadUtil;

/**
 * 当前时间库<br>
 * 获取当前时间，效率比日式格式化工具高10倍<br>
 * 测试100线程分别获取10000次下，该工具库只需要平均40ms
 */
public class NowTime {

	/** 当前时间 */
	private volatile static String current;
	/** 当前时间的时间戳 */
	private volatile static long currentTime = 0;

	/**
	 * 获取当前格式化的时间
	 * 
	 * @return 例如：2022-07-01 12:31:46.233
	 */
	public static String nowTime() {
		ThreadUtil.doubleCheckLock(
			() -> currentTime != System.currentTimeMillis(),
			NowTime.class,
			() -> DateUtil.timeAnalysis(NowTime::change)
		);
		return current;
	}

	/**
	 * 解析时间后的操作
	 * 
	 * @param year        年
	 * @param month       月
	 * @param day         日
	 * @param hour        时
	 * @param minute      分
	 * @param second      秒
	 * @param millisecond 毫秒
	 */
	private static void change(int year, int month, int day, int hour, int minute, int second, int millisecond) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(year).append('-')
			.append(StringUtil.zeroPadding(2, month)).append('-')
			.append(StringUtil.zeroPadding(2, day)).append(' ')
			.append(StringUtil.zeroPadding(2, hour)).append(':')
			.append(StringUtil.zeroPadding(2, minute)).append(':')
			.append(StringUtil.zeroPadding(2, second)).append('.')
			.append(StringUtil.zeroPadding(3, millisecond));
		current = stringBuilder.toString();
		currentTime = System.currentTimeMillis();
	}

}
