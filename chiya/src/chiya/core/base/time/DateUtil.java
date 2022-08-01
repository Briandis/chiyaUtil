package chiya.core.base.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import chiya.core.base.function.TimeFunction;
import chiya.core.base.loop.Loop;
import chiya.core.base.number.NumberUtil;
import chiya.core.base.string.StringUtil;

/*
 * -----------------------------------------
 * 如果涉及到获取该地区的起始时间，请务必考虑时区问题！！！！！
 * 时区！！！时区！！！时区！！！
 * 只有%运算的时候需要考虑，其余不用考虑
 * -----------------------------------------
 */
/**
 * 日期工具类
 * 
 * @author chiya
 */
public class DateUtil {
	/** 起始年份 */
	public static final int START_YEAR = 1970;
	/** 日期时间 */
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	/** 日期时间 */
	public static final String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 日期 */
	public static final String DATE = "yyyy-MM-dd";
	/** UTC标准时间 */
	public static final String UTC_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss'+08:00'";
	/** 日期时间的纯数字，不是时间戳 */
	public static final String NUMBER_DATE_TIME = "yyyyMMddHHmmss";

	/** 一年的天数 */
	public static final int ONE_YEAR_DAY = 365;
	/** 一天多少个小时 */
	public static final int ONE_DAY_HOUR = 24;
	/** 一秒的毫秒长度 */
	public static final long ONE_SECOND_TIME = 1000;
	/** 一分钟的时间毫秒长度 */
	public static final long ONE_MINUTE_TIME = ONE_SECOND_TIME * 60;
	/** 一小时的时间毫秒长度 */
	public static final long ONE_HOUR_TIME = ONE_MINUTE_TIME * 60;
	/** 一天的时间毫秒长度 */
	public static final long ONE_DAY_TIME = ONE_HOUR_TIME * 24;
	/** 一年时间 */
	public static final long ONE_YEAR_TIME = ONE_DAY_TIME * ONE_YEAR_DAY;
	/** 一个闰年周期的长度 */
	public static final long LEAP_YEAR_TIME = ONE_YEAR_TIME * 4 + ONE_DAY_TIME;
	/** 东8时区偏移长度 */
	public static final long EAST_8_TIME_ZONE = ONE_HOUR_TIME * 8;

	/** 时间日期格式化工具 */
	private static final ThreadLocal<SimpleDateFormat> simpleDateFormatDatetime = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_TIME);
		};
	};

	/** 每个月份的天数 */
	private static final int MONTH_DAY[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 获取现在的时间，以yyyyMMddHHmmss的方式显示
	 * 
	 * @return yyyyMMddHHmmss的字符串
	 */
	public static String getNowNumberDateTime() {
		return format(System.currentTimeMillis(), NUMBER_DATE_TIME);
	}

	/**
	 * 获取现在的时间，以yyyy-MM-dd HH:mm:ss的方式显示
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateTime() {
		return NowTime.getDateTime();
	}

	/**
	 * 获取现在的时间，以yyyy-MM-dd的方式显示
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getNowDate() {
		return NowTime.getDate();
	}

	/**
	 * 以年月日的方式格式化日期<br>
	 * yyyy-MM-dd
	 * 
	 * @param date:待格式化日期
	 * @return 格式化后的字符串
	 */
	public static String formatDateYYYYMMDD(Date date) {
		if (date == null) { return null; }
		return format(date.getTime(), DATE);
	}

	/**
	 * 以年月日 时分秒的方式格式化日期<br>
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date 待格式化日期
	 * @return 格式化后的字符串
	 */
	public static String formatDateYYYYMMDDHHMMSS(Date date) {
		if (date == null) { return null; }
		return format(date.getTime(), DATE_TIME);
//		return simpleDateFormatDatetime.get().format(date);
	}

	/**
	 * 以年月日 时分秒的方式格式化日期<br>
	 * yyyyMMddHHmmss的格式
	 * 
	 * @param date 待格式化日期
	 * @return 格式化后的字符串
	 */
	public static String formatDateNumberYYYYMMDDHHMMSS(Date date) {
		if (date == null) { return null; }
		return format(date.getTime(), NUMBER_DATE_TIME);
	}

	/**
	 * 获取一年中，该月份有几天
	 * 
	 * @param year  年 需要未偏移的年份，如1998
	 * @param month 月份 1-12间的数字
	 * @return 该月天数
	 */
	public static int getYearMonthDay(int year, int month) {
		if (month < 1 || month > 12) { return 0; }
		if (month == 2) {
			if (isLeap(year)) {
				return MONTH_DAY[1] + 1;
			} else {
				return MONTH_DAY[1];
			}
		}
		return MONTH_DAY[month - 1];
	}

	/**
	 * 根据年月日，转换成日期对象<br>
	 * 默认减去东8时区，因为Date中已经加入时区
	 * 
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 * @return 日期对象
	 */
	public static Date parseDate(int year, int month, int day) {
		return parseDate(year, month, day, EAST_8_TIME_ZONE);
	}

	/**
	 * 根据年月日，转换成日期对象
	 * 
	 * @param year  年
	 * @param month 月 1-12的范围
	 * @param day   日
	 * @param zone  时区
	 * @return 日期对象
	 */
	public static Date parseDate(int year, int month, int day, long zone) {
		long time = offsetDay(year, month, day) * ONE_DAY_TIME + ONE_DAY_TIME - zone;
		return new Date(time);
	}

	/**
	 * 获取获当前星期几，星期一返回1，星期日返回7
	 * 
	 * @return 1-7的数字
	 */
	public static int getNowWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return week == 1 ? 7 : week - 1;
	}

	/**
	 * 获取当前的小时
	 * 
	 * @return 0-24数值
	 */
	public static int getNowHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前月份，1月返回1，12返回12
	 * 
	 * @return 1-12数值
	 */
	public static int getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前年份
	 * 
	 * @return 年份
	 */
	public static int getNowYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份中的第几天
	 * 
	 * @return 当前月份的第几天
	 */
	public static int getNowDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 传入一个时间，计算过去了多少天<br>
	 * 传入时间应当小于当前的日志，该方法用于判断过去到现在过了多少天
	 * 
	 * @param date 过去的时间
	 * @return 相差天数
	 */
	public static int getNowTimeToTimeDay(Date date) {
		if (date == null) { return -1; }
		return (int) ((System.currentTimeMillis() - date.getTime()) / (ONE_DAY_TIME));
	}

	/**
	 * 将字符串的时间转成Date对象
	 * 
	 * @param string 待转换字符串
	 * @return Date
	 */
	public static Date stringToDate(String string) {
		// 如果是时间戳的情况
		if (StringUtil.match("^[0-9]+$", string)) { return new Date(Long.parseLong(string)); }
		// 谷歌游览器提交时间问题
		if (string.indexOf("T") != -1) {
			string = string.replace("T", " ");
			if (string.length() == 16) { string = string + ":00"; }
		}
		// 只有年月日的情况下
		if (string.indexOf(":") == -1) { string = string + " 00:00:00"; }
		// 斜杠问题
		if (string.indexOf("/") != -1) { string = string.replace('/', '-'); }
		try {
			return simpleDateFormatDatetime.get().parse(string);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 判断两个时间是否连续
	 * 
	 * @param y1 年
	 * @param m1 月
	 * @param d1 日
	 * @param y2 下一天的年
	 * @param m2 下一天的月
	 * @param d2 下一天的日
	 * @return true:是下一天/false:不是下一天
	 */
	public static boolean nextDay(int y1, int m1, int d1, int y2, int m2, int d2) {
		if (y1 + 1 == y2) {
			// 唯一场景 12月31日到1月1日
			return m1 == 12 && d1 == 31 && m2 == 1 && d2 == 1;
		} else {
			// 同月份下
			if (m1 == m2 && d1 + 1 == d2) {
				return true;
			} else if (m1 + 1 == m2 && d2 == 1) {
				// 跨月份
				if (getYearMonthDay(y1, m1) == d1) { return true; }
			}
		}
		return false;
	}

	/**
	 * 判断两个时间是否连续
	 * 
	 * @param date     日期
	 * @param lastDate 下一天的日期
	 * @return true:是下一天/false:不是下一天
	 */
	public static boolean nextDay(Date date, Date lastDate) {
		return nextDay(getYear(date), getMonth(date), getDay(date), getYear(lastDate), getMonth(lastDate), getDay(lastDate));
	}

	/**
	 * 获取获星期几，星期一返回1，星期日返回7
	 * 
	 * @return 1-7的数字
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return week == 1 ? 7 : week - 1;
	}

	/**
	 * 获取小时
	 * 
	 * @return 0-24数值
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取月份，1月返回1，12返回12
	 * 
	 * @return 1-12数值
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取年份
	 * 
	 * @return 年份
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取月份中的第几天
	 * 
	 * @return 当前月份的第几天
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 以UTC的方式格式化日期
	 * 
	 * @param date 待格式化日期
	 * @return 格式化后的字符串
	 */
	public static String formatDateUTCDateTime(Date date) {
		if (date == null) { return null; }
		return format(date.getTime(), UTC_DATE_TIME);
	}

	/**
	 * 以UTC的方式格式化日期
	 * 
	 * @param time 时间戳
	 * @return 格式化后的字符串
	 */
	public static String formatDateUTCDateTime(long time) {
		return formatDateUTCDateTime(new Date(time));
	}

	/**
	 * 东8时区的时间戳，生成时间对象，请确保时间戳是东8时间戳下在使用
	 * 
	 * @param time 时间戳
	 * @return Date 事件对象
	 */
	public static Date newDateBeiJingTimeZone(long time) {
		return new Date(time - EAST_8_TIME_ZONE);
	}

	/**
	 * 获取今天的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 今天的起始时间
	 */
	public static Date getStartTimeToDay() {
		return getStartTimeToDay(System.currentTimeMillis());
	}

	/**
	 * 获取这个时间戳今天的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 今天的起始时间
	 */
	public static Date getStartTimeToDay(long time) {
		return new Date(getStartTimeToDayReturnLong(time));
	}

	/**
	 * 获取这个时间戳今天的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 今天的起始时间
	 */
	public static Date getStartTimeToDay(Date time) {
		return new Date(getStartTimeToDayReturnLong(time.getTime()));
	}

	/**
	 * 获取这个时间戳今天的起始时间的数值，以东8时区为基准
	 * 
	 * @param time 时间戳
	 * @return long 今天的起始时间的数值
	 */
	public static long getStartTimeToDayReturnLong(long time) {
		return getStartTimeToDayReturnLong(time, EAST_8_TIME_ZONE);
	}

	/**
	 * 获取这个时间戳今天的起始时间的数值，其他时区
	 * 
	 * @param time     时间戳
	 * @param timeZone 时区差
	 * @return long 今天的起始时间的数值
	 */
	public static long getStartTimeToDayReturnLong(long time, long timeZone) {
		// 此处计算是以0时区柏林的作为基准，不加入时区会造成地方起始时间错误
		return time - ((time + timeZone) % (ONE_DAY_TIME));
	}

	/**
	 * 获取昨天的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 今天的起始时间
	 */
	public static Date getStartTimeYesterday() {
		return getStartTimeYesterday(System.currentTimeMillis());
	}

	/**
	 * 获取这个时间戳昨天的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 今天的起始时间
	 */
	public static Date getStartTimeYesterday(long time) {
		return new Date(getStartTimeYesterdayReturnLong(time));
	}

	/**
	 * 获取这个时间戳昨天的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 今天的起始时间
	 */
	public static Date getStartTimeYesterday(Date time) {
		return new Date(getStartTimeYesterdayReturnLong(time.getTime()));
	}

	/**
	 * 获取这个时间戳昨天的起始时间的数值，以东8时区为基准
	 * 
	 * @param time 时间戳
	 * @return long 今天的起始时间的数值
	 */
	public static long getStartTimeYesterdayReturnLong(long time) {
		return getStartTimeYesterdayReturnLong(time, EAST_8_TIME_ZONE);
	}

	/**
	 * 获取这个时间戳昨天的起始时间的数值
	 * 
	 * @param time     时间戳
	 * @param timeZone 时区
	 * @return long 今天的起始时间的数值
	 */
	public static long getStartTimeYesterdayReturnLong(long time, long timeZone) {
		// 此处计算是以0时区柏林的作为基准，不加入时区会造成地方起始时间错误
		return (time - ((time + timeZone) % (ONE_DAY_TIME))) - ONE_DAY_TIME;
	}

	/**
	 * 获取当前星期一的起始时间
	 * 
	 * @return Date 这个星期的起始时间
	 */
	public static Date getStartTimeToWeek() {
		return getStartTimeToWeek(System.currentTimeMillis());
	}

	/**
	 * 获取当前星期一的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 这个星期的起始时间
	 */
	public static Date getStartTimeToWeek(Date time) {
		return getStartTimeToWeek(time.getTime());
	}

	/**
	 * 获取这个时间戳当前星期一的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 这个星期的起始时间
	 */
	public static Date getStartTimeToWeek(long time) {
		return new Date(getStartTimeToWeekReturnLong(time));
	}

	/**
	 * 获取这个时间戳当前星期一的起始时间，以东8时区为基准
	 * 
	 * @param time 时间戳
	 * @return Date 这个星期的起始时间
	 */
	public static long getStartTimeToWeekReturnLong(long time) {
		return getStartTimeToWeekReturnLong(time, EAST_8_TIME_ZONE);
	}

	/**
	 * 获取这个时间戳当前星期一的起始时间
	 * 
	 * @param time     时间戳
	 * @param timeZone 时区
	 * @return Date 这个星期的起始时间
	 */
	public static long getStartTimeToWeekReturnLong(long time, long timeZone) {
		// 此处计算是以0时区柏林的作为基准，不加入时区会造成地方起始时间错误
		time = time + (ONE_DAY_TIME * 4);
		time = time - ((time + timeZone) % (ONE_DAY_TIME * 7));
		return time - ONE_DAY_TIME * 3;
	}

	/**
	 * 获取当前月的起始时间
	 * 
	 * @return Date 这个月的起始时间
	 */
	public static Date getStartTimeToMoon() {
		return getStartTimeToMoon(new Date());
	}

	/**
	 * 获取这个时间戳当前月的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 这个月的起始时间
	 */
	public static Date getStartTimeToMoon(long time) {
		return getStartTimeToMoon(new Date(time));
	}

	/**
	 * 获取这个时间戳当前月的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 这个月的起始时间
	 */
	public static Date getStartTimeToMoon(Date time) {
		return parseDate(getYear(time), getMonth(time), 1);
	}

	/**
	 * 获取当前年的起始时间
	 * 
	 * @return Date 这个年的起始时间
	 */
	public static Date getStartTimeToYear() {
		return getStartTimeToYear(new Date());
	}

	/**
	 * 获取这个时间戳当前年的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 这个年的起始时间
	 */
	public static Date getStartTimeToYear(long time) {
		return getStartTimeToYear(new Date(time));
	}

	/**
	 * 获取这个时间戳当前年的起始时间
	 * 
	 * @param time 时间戳
	 * @return Date 这个年的起始时间
	 */
	public static Date getStartTimeToYear(Date time) {
		return parseDate(getYear(time), 1, 1);
	}

	/**
	 * 获取昨天的时间年月日,时分秒和今天相同
	 * 
	 * @return 时间对象
	 */
	public static Date getYesterday() {
		return getYesterday(new Date());
	}

	/**
	 * 获取昨天的时间年月日，时分秒和传入的时间相同
	 * 
	 * @param time 时间
	 * @return 时间对象
	 */
	public static Date getYesterday(Date time) {
		return getTimeOffsetTime(time, -1);
	}

	/**
	 * 获取昨天的时间年月日，时分秒和传入的时间相同
	 * 
	 * @param time 时间
	 * @return 时间对象
	 */
	public static Date getYesterday(long time) {
		return new Date(getTimeOffsetTime(time, -1));
	}

	/**
	 * 获取昨天的时间年月日，时分秒和传入的时间相同
	 * 
	 * @param time 时间
	 * @return 时间戳
	 */
	public static long getYesterdayReturnLong(long time) {
		return getTimeOffsetTime(time, -1);
	}

	/**
	 * 获取这个日期偏移几天的日期，输入偏移正数则向后偏移，负数向前偏移<br>
	 * 举例，offset=1是明天，offset=-1是昨天
	 * 
	 * @param time      时间
	 * @param offsetDay 偏移的天数
	 * @return 偏移后的时间对象
	 */
	public static Date getTimeOffsetTime(Date time, int offsetDay) {
		return new Date(getTimeOffsetTime(time.getTime(), offsetDay));
	}

	/**
	 * 获取这个日期偏移几天的日期，输入偏移正数则向后偏移，负数向前偏移<br>
	 * 举例，offset=1是明天，offset=-1是昨天
	 * 
	 * @param time      时间
	 * @param offsetDay 偏移的天数
	 * @return 偏移后的时间对象
	 */
	public static long getTimeOffsetTime(long time, int offsetDay) {
		return time + ONE_DAY_TIME * offsetDay;
	}

	/**
	 * 比较两个时间是否是同一天
	 * 
	 * @param date1 待比较的时间1
	 * @param date2 待比较的时间2
	 * @return true:是同一天/false/不是同一天
	 */
	public static boolean compareSameDay(Date date1, Date date2) {
		return getStartTimeToDayReturnLong(date1.getTime()) == getStartTimeToDayReturnLong(date2.getTime());
	}

	/**
	 * 判断时间是否是昨天
	 * 
	 * @param time 时间戳
	 * @return true:是/false:不是
	 */
	public static boolean isYesterday(Date time) {
		return getStartTimeToDayReturnLong(time.getTime()) == getStartTimeYesterdayReturnLong(System.currentTimeMillis());
	}

	/**
	 * 判断时间是否是昨天
	 * 
	 * @param time 时间戳
	 * @return true:是/false:不是
	 */
	public static boolean isYesterday(long time) {
		return getStartTimeToDayReturnLong(time) == getStartTimeYesterdayReturnLong(System.currentTimeMillis());
	}

	/**
	 * 判断a是不是b的昨天
	 * 
	 * @param a 要被当作昨天的时间戳
	 * @param b 判断的时间戳
	 * @return true:是/false:不是
	 */
	public static boolean isYesterday(Date a, Date b) {
		return getStartTimeToDayReturnLong(a.getTime()) == getStartTimeYesterdayReturnLong(b.getTime());
	}

	/**
	 * 比较两个时间的大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则小于b，b为null则小于a
	 * 
	 * @param d1 日期a
	 * @param d2 日期b
	 * @return 小于0是a&lt;b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(Date d1, Date d2) {
		return NumberUtil.compareSize(d1 == null ? null : d1.getTime(), d2 == null ? null : d2.getTime());
	}

	/**
	 * 比较两个时间的大小<br>
	 * 返回值小于0是a&lt;b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则大于b，b为null则大于a
	 * 
	 * @param d1 日期a
	 * @param d2 日期b
	 * @return 小于0是a&lt;b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSizeNullIsMax(Date d1, Date d2) {
		return NumberUtil.compareSizeNullIsMax(d1 == null ? null : d1.getTime(), d2 == null ? null : d2.getTime());
	}

	/**
	 * 获取今天的偏移时间,东8时区基准
	 * 
	 * @return 偏移的时间戳
	 */
	public static long getNowDayOffset() {
		return System.currentTimeMillis() - getStartTimeToDayReturnLong(System.currentTimeMillis());
	}

	/**
	 * 获取这个时间戳在当天开始后的偏移时间,东8时区基准
	 * 
	 * @param time 时间戳
	 * @return 偏移的时间戳
	 */
	public static long getDayOffset(long time) {
		return System.currentTimeMillis() - getStartTimeToDayReturnLong(time);
	}

	/**
	 * 获取这个时间戳在当天开始后的偏移时间,东8时区基准
	 * 
	 * @param date 日期
	 * @return 偏移的时间戳
	 */
	public static long getDayOffset(Date date) {
		return System.currentTimeMillis() - getStartTimeToDayReturnLong(date.getTime());
	}

	/**
	 * 获取这个星期的起始偏移时间
	 * 
	 * @return 偏移的时间戳
	 */
	public static long getNowWeekOffset() {
		return System.currentTimeMillis() - getStartTimeToWeekReturnLong(System.currentTimeMillis());
	}

	/**
	 * 获取这个时间戳在星期一后的偏移时间,东8时区基准
	 * 
	 * @param time 时间戳
	 * @return 偏移的时间戳
	 */
	public static long getNowWeekOffset(long time) {
		return System.currentTimeMillis() - getStartTimeToWeekReturnLong(time);
	}

	/**
	 * 获取这个时间戳在星期一后的偏移时间,东8时区基准
	 * 
	 * @param date 日期
	 * @return 偏移的时间戳
	 */
	public static long getNowWeekOffset(Date date) {
		return System.currentTimeMillis() - getStartTimeToWeekReturnLong(date.getTime());
	}

	/**
	 * 获取这个月的起始偏移时间
	 * 
	 * @return 偏移的时间戳
	 */
	public static long getNowMoonOffset() {

		return System.currentTimeMillis() - getStartTimeToMoon().getTime();
	}

	/**
	 * 获取这个时间戳在当月后的偏移时间,东8时区基准
	 * 
	 * @param time 时间戳
	 * @return 偏移的时间戳
	 */
	public static long getNowMoonOffset(long time) {
		return System.currentTimeMillis() - getStartTimeToMoon(time).getTime();
	}

	/**
	 * 获取这个时间戳在当月后的偏移时间,东8时区基准
	 * 
	 * @param date 日期
	 * @return 偏移的时间戳
	 */
	public static long getNowMoonOffset(Date date) {
		return System.currentTimeMillis() - getStartTimeToMoon(date).getTime();
	}

	/**
	 * 获取今年的起始偏移时间
	 * 
	 * @return 偏移的时间戳
	 */
	public static long getNowYearOffset() {
		return System.currentTimeMillis() - getStartTimeToYear().getTime();
	}

	/**
	 * 获取这个时间戳在当年后的偏移时间,东8时区基准
	 * 
	 * @param time 时间戳
	 * @return 偏移的时间戳
	 */
	public static long getNowYearOffset(long time) {
		return System.currentTimeMillis() - getStartTimeToYear(time).getTime();
	}

	/**
	 * 获取这个时间戳在当年后的偏移时间,东8时区基准
	 * 
	 * @param date 日期
	 * @return 偏移的时间戳
	 */
	public static long getNowYearOffset(Date date) {
		return System.currentTimeMillis() - getStartTimeToYear(date).getTime();
	}

	/**
	 * 判断当前时间是不是在两个时间范围
	 * 
	 * @param oldDate    过去的时间
	 * @param futureDate 未来的时间
	 * @return true:在范围内/false:不在范围内
	 */
	public static boolean between(Date oldDate, Date futureDate) {
		return NumberUtil.between(
			System.currentTimeMillis(),
			oldDate != null ? oldDate.getTime() : null,
			futureDate != null ? futureDate.getTime() : null
		);
	}

	/**
	 * 获取年份中的月份
	 * 
	 * @param year 年
	 * @param day  天
	 * @return 月
	 */
	public static int getMonth(int year, int day) {
		int sum = 0;
		for (int i = 0; i < MONTH_DAY.length; i++) {
			sum += MONTH_DAY[i];
			if (i == 1 && isLeap(year)) { sum++; }
			if (sum > day) { return i + 1; }
		}
		return MONTH_DAY.length;
	}

	/**
	 * 获取年份中的月份
	 * 
	 * @param year 年
	 * @param day  天
	 * @return 月
	 */
	public static int getMonthDay(int year, int day) {
		for (int i = 1; i < 13; i++) {
			day = day - getYearMonthDay(year, i);
			if (day < 0) { return day + getYearMonthDay(year, i) + 1; }
		}
		return day;
	}

	/**
	 * 是否是闰年
	 * 
	 * @param year 年
	 * @return true:是/false:不是
	 */
	public static boolean isLeap(int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}

	/**
	 * 时间戳转时间数组<br>
	 * 默认使用东8时区
	 * 
	 * @return 数组，长度为7，按照[年,月,日,时,分,秒,毫秒]的分布
	 */
	public static int[] toTimeArray() {
		return toTimeArray(System.currentTimeMillis());
	}

	/**
	 * 时间戳转时间数组<br>
	 * 默认使用东8时区
	 * 
	 * @param time 时间戳
	 * @return 数组，长度为7，按照[年,月,日,时,分,秒,毫秒]的分布
	 */
	public static int[] toTimeArray(long time) {
		return toTimeArray(time, EAST_8_TIME_ZONE);
	}

	/**
	 * 时间戳转时间数组
	 * 
	 * @param time 时间戳
	 * @param zone 所在时区
	 * @return 数组，长度为7，按照[年,月,日,时,分,秒,毫秒]的分布
	 */
	public static int[] toTimeArray(long time, long zone) {
		int timeArray[] = new int[7];
		timeAnalysis(
			time,
			zone,
			(year, month, day, hour, minute, second, millisecond) -> {
				timeArray[0] = year;
				timeArray[1] = month;
				timeArray[2] = day;
				timeArray[3] = hour;
				timeArray[4] = minute;
				timeArray[5] = second;
				timeArray[6] = millisecond;
			}
		);
		return timeArray;
	}

	/**
	 * 时间戳转时间数组<br>
	 * 默认使用东8时区
	 * 
	 * @param timeFunction 时间解析后处理
	 */
	public static void timeAnalysis(TimeFunction timeFunction) {
		timeAnalysis(System.currentTimeMillis(), timeFunction);
	}

	/**
	 * 时间戳转时间数组<br>
	 * 默认使用东8时区
	 * 
	 * @param time         时间戳
	 * @param timeFunction 时间解析后处理
	 */
	public static void timeAnalysis(long time, TimeFunction timeFunction) {
		timeAnalysis(time, EAST_8_TIME_ZONE, timeFunction);
	}

	/**
	 * 时间戳转时间数组
	 * 
	 * @param time         时间戳
	 * @param zone         所在时区
	 * @param timeFunction 时间解析后处理
	 */
	public static void timeAnalysis(long time, long zone, TimeFunction timeFunction) {
		time += zone;// 时区计算
		int day = (int) (time / ONE_DAY_TIME);// 获取天数
		int year = day / ONE_YEAR_DAY; // 计算模糊的年
		day = day - ((year + 1) / 4); // 去处闰年占用的天数
		year = (day / ONE_YEAR_DAY) + START_YEAR; // 得到原始的年
		day = day - (year - START_YEAR) * ONE_YEAR_DAY;// 计算得到剩余天数
		if (isLeap(year) && day > 60) {
			// 是闰年，并且是闰月以上的时间，则需要补偿
			day++;
		}
		int month = getMonth(year, day);
		day = getMonthDay(year, day);

		time = time % ONE_DAY_TIME;
		int hour = (int) (time / ONE_HOUR_TIME);
		time = time % ONE_HOUR_TIME;
		int minute = (int) (time / ONE_MINUTE_TIME);
		time = time % ONE_MINUTE_TIME;
		int second = (int) (time / ONE_SECOND_TIME);
		int millisecond = (int) (time % ONE_SECOND_TIME);

		timeFunction.time(year, month, day, hour, minute, second, millisecond);

	}

	/** 日期格式化 */
	private static final char DATE_FORMAT[] = { 'y', 'M', 'd', 'H', 'm', 's', 'S' };

	/**
	 * 时间格式化<br>
	 * yyyy：年份<br>
	 * MM：月份<br>
	 * dd：天<br>
	 * HH：小时<br>
	 * mm：分钟<br>
	 * ss：秒<br>
	 * SSS：毫秒<br>
	 * 例如 yyyy-MM-dd HH:mm:ss等价于 2020-12-25 14:32:23
	 * 
	 * @param time       时间戳
	 * @param expression 格式化的表达式
	 * @return 格式化后的字符串
	 */
	public static String format(long time, String expression) {
		char timeChar[] = expression.toCharArray();
		int timeArray[] = toTimeArray(time);
		Loop.step(
			DATE_FORMAT.length,
			i -> StringUtil.findContinuousChar(
				timeChar,
				DATE_FORMAT[i],
				(start, end) -> StringUtil.numberUpdateCharArray(timeArray[i], timeChar, start, end)
			)
		);
		return String.valueOf(timeChar);
	}

	/**
	 * 获取这个年月日至今的天数
	 * 
	 * @param year  年份
	 * @param month 月份
	 * @param day   日
	 * @return 偏移天数
	 */
	public static int offsetDay(int year, int month, int day) {
		int offsetYear = year - START_YEAR;
		return (offsetYear * ONE_YEAR_DAY) + ((offsetYear + 1) / 4) + offsetMonthDay(year, month) + offsetInDay(day);
	}

	/**
	 * 获取这个年份到这个月的天数<br>
	 * 如：1999,2<br>
	 * 返回：31，不包含第二月
	 * 
	 * @param year  年份 未偏移的年份
	 * @param month 月份 1-12范围
	 * @return 偏移天数
	 */
	public static int offsetMonthDay(int year, int month) {
		int day = 0;
		for (int i = 1; i < month; i++) {
			day += getYearMonthDay(year, i);
		}
		return day;
	}

	/**
	 * 获取至今过去的天数
	 * 
	 * @param day 日期
	 * @return 过去的天数
	 */
	public static int offsetInDay(int day) {
		return day < 1 ? 0 : day - 1;
	}

	/**
	 * 输出时间戳<br>
	 * 默认东8时区
	 * 
	 * @param year  年份
	 * @param month 月份
	 * @param day   日期
	 * @return 时间戳
	 */
	public static long toTime(int year, int month, int day) {
		return toTime(year, month, day, EAST_8_TIME_ZONE);
	}

	/**
	 * 输出时间戳
	 * 
	 * @param year  年份
	 * @param month 月份
	 * @param day   日期
	 * @param zone  时区
	 * @return 时间戳
	 */
	public static long toTime(int year, int month, int day, long zone) {
		return offsetDay(year, month, day) * ONE_DAY_TIME - zone;
	}
}
