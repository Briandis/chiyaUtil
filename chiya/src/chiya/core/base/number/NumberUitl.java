package chiya.core.base.number;

import java.util.List;

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
	 * @return true:a &lt; b / false:a &gt; =b
	 */
	public static boolean isLess(Integer a, Integer b) {
		return compareSize(a, b) < 0;
	}

	/**
	 * 判断a是否小于等于b
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:a &lt; =b / false:a>b
	 */
	public static boolean isLessOrEqual(Integer a, Integer b) {
		return compareSize(a, b) <= 0;
	}

	/**
	 * 判断a是否大于b
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:a &gt; b / false:a &lt; =b
	 */
	public static boolean isGreater(Integer a, Integer b) {
		return compareSize(a, b) > 0;
	}

	/**
	 * 判断a是否大于b
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:a &gt; b / false:a &lt; =b
	 */
	public static boolean isGreaterOrEqual(Integer a, Integer b) {
		return compareSize(a, b) >= 0;
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则小于b，b为null则小于a
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(Integer a, Integer b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return -1; }
		if (b == null) { return 1; }
		return a - b;
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则大于b，b为null则大于a
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSizeNullIsMax(Integer a, Integer b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return 1; }
		if (b == null) { return -1; }
		return a - b;
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则小于b，b为null则小于a
	 * 
	 * @param a Long包装类
	 * @param b Long包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(Long a, Long b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return -1; }
		if (b == null) { return 1; }
		return compareSize(a.longValue(), b.longValue());
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则大于b，b为null则大于a
	 * 
	 * @param a Long包装类
	 * @param b Long包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSizeNullIsMax(Long a, Long b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return -1; }
		if (b == null) { return 1; }
		return compareSize(a.longValue(), b.longValue());
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则小于b，b为null则小于a
	 * 
	 * @param a Double包装类
	 * @param b Double包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(Double a, Double b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return -1; }
		if (b == null) { return 1; }
		return compareSize(a.doubleValue(), b.doubleValue());
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则大于b，b为null则大于a
	 * 
	 * @param a Double包装类
	 * @param b Double包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSizeNullIsMax(Double a, Double b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return -1; }
		if (b == null) { return 1; }
		return compareSize(a.doubleValue(), b.doubleValue());
	}

	/**
	 * 比较两数大小
	 * 
	 * @param a long类型
	 * @param b long类型
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(long a, long b) {
		long l = a - b;
		return l > 0 ? 1 : l == 0 ? 0 : -1;
	}

	/**
	 * 比较两数大小
	 * 
	 * @param a double类型
	 * @param b double类型
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(double a, double b) {
		double number = a - b;
		return number > 0 ? 1 : number < 0 ? -1 : 0;
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则小于b，b为null则小于a
	 * 
	 * @param a Number包装类
	 * @param b Number包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(Number a, Number b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return -1; }
		if (b == null) { return 1; }
		// 未知的数据类型，选择双浮点数处理
		return compareSize(a.doubleValue(), b.doubleValue());
	}

	/**
	 * 比较两数大小<br>
	 * 返回值小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b<br>
	 * 两数为Null则相等，a为null则大于b，b为null则大于a
	 * 
	 * @param a Number包装类
	 * @param b Number包装类
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSizeNullIsMax(Number a, Number b) {
		if (a == null && b == null) { return 0; }
		if (a == null) { return 1; }
		if (b == null) { return -1; }
		// 未知的数据类型，选择双浮点数处理
		return compareSizeNullIsMax(a.doubleValue(), b.doubleValue());
	}

	/**
	 * 比较所有的所有数是否大于一个数 <br>
	 * number < all
	 * 
	 * @param number 待比较的数值
	 * @param all    需要判断的数据，可以传入多个
	 * @return true:全大于/false:有的数小于、未传入比较数
	 */
	public static boolean compareAllNumbnerGreaterNumber(int number, int... all) {
		if (all == null) { return false; }
		for (int i = 0; i < all.length; i++) { if (all[i] <= number) { return false; } }
		return true;
	}

	/**
	 * 比较所有的数是否小于一个数 <br>
	 * all < number
	 * 
	 * @param number 待比较的数值
	 * @param all    需要判断的数据，可以传入多个
	 * @return true:全小于/false:有的数大于、未传入比较数
	 */
	public static boolean compareAllNumberLessNumber(int number, int... all) {
		if (all == null) { return false; }
		for (int i = 0; i < all.length; i++) { if (all[i] >= number) { return false; } }
		return true;
	}

	/**
	 * 比较所有的数是否小于一个数，所有数必须大于0 <br>
	 * 0 <= all <= number
	 * 
	 * @param number 待比较的数值
	 * @param all    需要判断的数据，可以传入多个
	 * @return true:全在范围/false:有的数大于、小于、未传入比较数
	 */
	public static boolean compareAllNumberLessNumberAndGreaterZero(int number, int... all) {
		return compareAllNumberLessNumberAndGreaterNumber(0, number, all);
	}

	/**
	 * 所有数都要满足大于上限，小于下限<br>
	 * lower <= all <= upper
	 * 
	 * @param lower 下限
	 * @param upper 上限
	 * @param all   需要判断的数据，可以传入多个
	 * @return true:全在范围/false:有的数大于、小于、未传入比较数
	 */
	public static boolean compareAllNumberLessNumberAndGreaterNumber(int lower, int upper, int... all) {
		if (all == null) { return false; }
		for (int i = 0; i < all.length; i++) { if (all[i] < lower || all[i] > upper) { return false; } }
		return true;
	}

	/**
	 * 判断数字是否在列表中出现过
	 * 
	 * @param i    数字
	 * @param list 列表
	 * @return true:存在/false:不存在
	 */
	public static boolean intInList(int i, List<Integer> list) {
		for (Integer integer : list) { if (integer == i) { return true; } }
		return false;
	}

	/**
	 * 判断数字是否在列表中出现过
	 * 
	 * @param i    数字
	 * @param list 列表
	 * @return true:存在/false:不存在
	 */
	public static boolean intInArray(int i, int[] ints) {
		for (int j : ints) { if (j == i) { return true; } }
		return false;
	}

	/**
	 * 如果number等于数组中任意一个值，则返回true
	 * 
	 * @param number 待比较数值
	 * @param array  候选的比较值
	 * @return true:有值相等/false:没有相等
	 */
	public static boolean intEqualToAny(int number, int... array) {
		if (array == null) { return false; }
		for (Integer i : array) { if (i == number) { return true; } }
		return false;
	}

	/**
	 * 如果number等于数组中任意一个值，则返回true
	 * 
	 * @param number 待比较数值
	 * @param array  候选的比较值
	 * @return true:有值相等/false:没有相等
	 */
	public static boolean intEqualToAny(int number, Integer... array) {
		if (array == null) { return false; }
		for (Integer i : array) { if (equalInteger(number, i)) { return true; } }
		return false;
	}

}
