package chiya.core.base.time;

import chiya.core.base.time.cache.DateCache;
import chiya.core.base.time.cache.DateTimeCache;
import chiya.core.base.time.cache.FullDateTimeCache;
import chiya.core.base.time.cache.TimeCacheFormat;

/**
 * 当前时间库<br>
 * 获取当前时间，效率比日式格式化工具高10倍<br>
 * 测试100线程分别获取100万次，总共10亿获取下，该工具库平均200ms
 */
public class NowTime {
	/** 日期类 */
	public static final TimeCacheFormat date = new DateCache();
	/** 日期和时间 */
	public static final TimeCacheFormat dateTime = new DateTimeCache();
	/** 日期和时间，带毫秒 */
	public static final TimeCacheFormat fullDateTime = new FullDateTimeCache();

	/**
	 * 获取当前日期<br>
	 * 获取的时间格式为：2022-07-01
	 * 
	 * @return 日期
	 */
	public static String getDate() {
		return date.getNowTime();
	}

	/**
	 * 获取当前日期<br>
	 * 获取的时间格式为：2022-07-01 12:31:46
	 * 
	 * @return 日期时间字符串
	 */
	public static String getDateTime() {
		return dateTime.getNowTime();
	}

	/**
	 * 获取当前日期和时间，带毫秒<br>
	 * 获取的时间格式为：2022-07-01 12:31:46.233
	 * 
	 * @return 日期时间毫秒字符串
	 */
	public static String getFullDateTime() {
		return fullDateTime.getNowTime();
	}
}
