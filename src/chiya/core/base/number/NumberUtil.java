package chiya.core.base.number;

import java.util.List;

/**
 * 数值工具库
 * 
 * @author chiya
 */
public class NumberUtil {

	/** 二进制 */
	public static final int BINARY = 2;
	/** 八进制 */
	public static final int OCTAL = 8;
	/** 十进制 */
	public static final int DECIMAL = 10;
	/** 十六进制 */
	public static final int HEX = 16;

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
		return compareSize(a, b, 0.00000001);
	}

	/**
	 * 比较两数大小
	 * 
	 * @param a         double类型
	 * @param b         double类型
	 * @param precision 误差精度
	 * @return 小于0是a &lt; b / 等于0是a=b / 大于0是a &gt; b
	 */
	public static int compareSize(double a, double b, double precision) {
		double number = a - b;
		return number > precision ? 1 : number < 0 - precision ? -1 : 0;
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
		for (int i = 0; i < all.length; i++) {
			if (all[i] <= number) { return false; }
		}
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
		for (int i = 0; i < all.length; i++) {
			if (all[i] >= number) { return false; }
		}
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
		for (int i = 0; i < all.length; i++) {
			if (all[i] < lower || all[i] > upper) { return false; }
		}
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
		for (Integer integer : list) {
			if (integer == i) { return true; }
		}
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
		for (int j : ints) {
			if (j == i) { return true; }
		}
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
		for (Integer i : array) {
			if (i == number) { return true; }
		}
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
		for (Integer i : array) {
			if (equalInteger(number, i)) { return true; }
		}
		return false;
	}

	/**
	 * 判断数值是否在两数之间,如果传入的值为null，则判定无穷小<br>
	 * 如果number为null，则无论如何都会返回false
	 * 
	 * @param number 待比较数值
	 * @param min    下线
	 * @param max    上限
	 * @return true:在两者之间/false:不在两者之间
	 */
	public static boolean between(Long number, Long min, Long max) {
		if (number == null || max == null) { return false; }
		if (min == null) { return number < max; }
		return min < number && number < max;
	}

	/**
	 * 十六进制的字符串长度
	 * 
	 * @param number 数值
	 * @return 长度
	 */
	public static int lengthHex(int number) {
		return lengthNumber(number, HEX);
	}

	/** 十进制位数 */
	public static final int DECIMAL_DIGITS[] = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };

	/**
	 * 十进制的字符串长度
	 * 
	 * @param number 数值
	 * @return 长度
	 */
	public static int lengthDecimal(int number) {
		if (number < 0) { number = -number; }
		int i = 0;
		while (true) {
			if (number <= DECIMAL_DIGITS[i]) { return i + 1; }
			i++;
		}
	}

	/**
	 * 八进制的字符串长度
	 * 
	 * @param number 数值
	 * @return 长度
	 */
	public static int lengthOctal(int number) {
		return lengthNumber(number, OCTAL);
	}

	/**
	 * 二进制的字符串长度
	 * 
	 * @param number 数值
	 * @return 长度
	 */
	public static int lengthBinary(int number) {
		return Integer.bitCount(number);
	}

	/**
	 * 数值的字符串长度
	 * 
	 * @param number 数值
	 * @param base   进制
	 * @return 长度
	 */
	public static int lengthNumber(int number, int base) {
		if (base < BINARY) { throw new IllegalArgumentException("base is less 2 ,must base greater 1"); }
		switch (base) {
			case BINARY:
				return lengthBinary(number);
			case DECIMAL:
				return lengthDecimal(number);
			default:
				int i = 0;
				while (number != 0) {
					number = number / base;
					i++;
				}
				return i;
		}
	}
}
