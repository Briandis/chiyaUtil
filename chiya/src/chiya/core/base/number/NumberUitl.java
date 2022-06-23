package chiya.core.base.number;

/**
 * 数值工具库
 * 
 * @author chiya
 */
public class NumberUitl {

	/**
	 * 比较两个Integer包装类
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:相同/false:不同
	 */
	public static boolean equalInteger(Integer a, Integer b) {
		return a != null ? a.equals(b) : false;
	}

	/**
	 * 比较两个Long包装类
	 * 
	 * @param a Long包装类
	 * @param b Long包装类
	 * @return true:相同/false:不同
	 */
	public static boolean equalLong(Long a, Long b) {
		return a != null ? a.equals(b) : false;
	}

	/**
	 * 强制把字符串转成数字，失败返回0
	 * 
	 * @param str 待转换字符串
	 * @return Integer包装类
	 */
	public static Integer parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 强制把字符串转成数字，失败返回0
	 * 
	 * @param str 待转换字符串
	 * @return Long包装类
	 */
	public static Long parseLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return 0l;
		}
	}

	/**
	 * 强制把字符串转成数字，失败返回0
	 * 
	 * @param str 待转换字符串
	 * @return Double包装类
	 */
	public static Double parseDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0d;
		}
	}

	/**
	 * 强制把字符串转成数字，失败返回null
	 * 
	 * @param str 待转换字符串
	 * @return Integer包装类
	 */
	public static Integer parseIntOrNull(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 强制把字符串转成数字，失败返回null
	 * 
	 * @param str 待转换字符串
	 * @return Integer包装类
	 */
	public static Long parseLongOrNull(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断a是否小于b
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:a<b / false:a>=b
	 */
	public static boolean isLess(Integer a, Integer b) {
		return compareSize(a, b) < 0;
	}

	/**
	 * 判断a是否小于等于b
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:a<=b / false:a>b
	 */
	public static boolean isLessOrEqual(Integer a, Integer b) {
		return compareSize(a, b) <= 0;
	}

	/**
	 * 判断a是否大于b
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:a>b / false:a<=b
	 */
	public static boolean isGreater(Integer a, Integer b) {
		return compareSize(a, b) > 0;
	}

	/**
	 * 判断a是否大于b
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:a>b / false:a<=b
	 */
	public static boolean isGreaterOrEqual(Integer a, Integer b) {
		return compareSize(a, b) >= 0;
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a<b / 等于0是a=b / 大于0是a>b<br>
	 * 两数为Null则相等，a为null则小于b，b为null则小于a
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return 小于0是a<b / 等于0是a=b / 大于0是a>b
	 */
	public static int compareSize(Integer a, Integer b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return -1; }
		if (b == null) { return 1; }
		return a - b;
	}

}
