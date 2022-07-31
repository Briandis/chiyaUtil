package chiya.core.base.function;

/**
 * 时间戳解析调用专用类
 */
@FunctionalInterface
public interface TimeFunction {

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
	void time(int year, int month, int day, int hour, int minute, int second, int millisecond);
}
