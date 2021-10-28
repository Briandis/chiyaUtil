package chiya.core.base.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import chiya.core.base.string.StringUtil;

/**
 * 日期工具类
 * 
 * @author Brian
 *
 */
public class DateUtil {
	// 日期时间
	private static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	// 日期
	private static final String DATE = "yyyy-MM-dd";
	private static final ThreadLocal<SimpleDateFormat> simpleDateFormatDatetime = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_TIME);
		};
	};

	private static final ThreadLocal<SimpleDateFormat> simpleDateFormatDate = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE);
		};
	};
	// 每个月份的天数
	private static final int MONTH_DAY[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 获取现在的时间，以yyyy-MM-dd HH:mm:ss的方式显示
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateTime() {
		return simpleDateFormatDatetime.get().format(new Date());
	}

	/**
	 * 获取现在的时间，以yyyy-MM-dd的方式显示
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getNowDate() {
		return simpleDateFormatDate.get().format(new Date());
	}

	/**
	 * 以年月日的方式格式化日期
	 * 
	 * @param date:待格式化日期
	 * @return 格式化后的字符串
	 */
	public static String formatDateYYYYMMDD(Date date) {
		if (date == null) { return null; }
		return simpleDateFormatDate.get().format(date);
	}

	/**
	 * 以年月日 时分秒的方式格式化日期
	 * 
	 * @param date 待格式化日期
	 * @return 格式化后的字符串
	 */
	public static String formatDateYYYYMMDDHHMMSS(Date date) {
		if (date == null) { return null; }
		return simpleDateFormatDatetime.get().format(date);
	}

	/**
	 * 获取一年中，该月份有几天
	 * 
	 * @param year  年
	 * @param month 月份
	 * @return 该月天数
	 */
	public static int getYearMonthDay(int year, int month) {
		if (month < 1 || month > 12) { return 0; }
		if (month == 2) {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				return MONTH_DAY[1] + 1;
			} else {
				return MONTH_DAY[1];
			}
		}
		return MONTH_DAY[month - 1];
	}

	/**
	 * 根据年月日，转换成日期对象
	 * 
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 * @return 日期对象
	 */
	public static Date parseDate(int year, int month, int day) {
		try {
			return simpleDateFormatDate.get().parse(year + "-" + month + "-" + day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
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
		return (int) ((System.currentTimeMillis() - date.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 将字符串的时间转成Date对象
	 * 
	 * @param string 待转换字符串
	 * @return Date
	 */
	public static Date stringToDate(String string) {
		// 如果是时间戳的情况
		if (StringUtil.match("^[0-9]*$", string)) { return new Date(Long.parseLong(string)); }
		// 谷歌游览器提交时间问题
		if (string.indexOf("T") != -1) {
			string = string.replace("T", " ");
			if (string.length() == 16) { string = string + ":00"; }
		}
		// 只有年月日的情况下
		if (string.indexOf(":") == -1) { string = string + " 00:00:00"; }
		try {
			return simpleDateFormatDatetime.get().parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
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

}
