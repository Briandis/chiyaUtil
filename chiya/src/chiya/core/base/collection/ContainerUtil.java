package chiya.core.base.collection;

import java.util.ArrayList;
import java.util.List;

import chiya.core.base.random.RandomUtil;

/**
 * 容器工具类
 * 
 * @author Brian
 *
 */
public class ContainerUtil {
	/**
	 * 比较所有的所有数是否大于一个数 <br>
	 * number < all
	 * 
	 * @param number 待比较的数值
	 * @param all    需要判断的数据，可以传入多个
	 * @return true:全大于/false:有的数小于、未传入比较数
	 */
	public static boolean compareAllNumbnerMoreNumber(int number, int... all) {
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
	public static boolean compareAllNumberLessNumberAndMoreZero(int number, int... all) {
		return compareAllNumberLessNumberAndMoreNumber(0, number, all);
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
	public static boolean compareAllNumberLessNumberAndMoreNumber(int lower, int upper, int... all) {
		if (all == null) { return false; }
		for (int i = 0; i < all.length; i++) {
			if (all[i] < lower || all[i] > upper) { return false; }
		}
		return true;
	}

	/**
	 * 交换列表中两元素的下标,成功返回真，失败返回假
	 * 
	 * @param <T>
	 * @param list 待交换列表
	 * @param a    下标1
	 * @param b    下标2
	 * @return 成功/失败
	 */
	public static <T> boolean swapList(List<T> list, int a, int b) {
		if (list == null) { return false; }
		if (a >= list.size() || b >= list.size()) { return false; }
		if (a < 0) { a += list.size(); }
		if (b < 0) { b += list.size(); }
		if (a < 0 || b < 0) { return false; }
		T swap = list.get(a);
		list.set(a, list.get(b));
		list.set(b, swap);
		return true;
	}

	/**
	 * 交换数组中两元素的下标,成功返回真，失败返回假
	 * 
	 * @param array:待交换数组
	 * @param a:下标1
	 * @param b:下标2
	 * @return 成功/失败
	 */
	public static boolean swapArrayInt(int array[], int a, int b) {
		if (array == null) { return false; }
		if (a >= array.length || b >= array.length) { return false; }
		if (a < 0) { a += array.length; }
		if (b < 0) { b += array.length; }
		if (a < 0 || b < 0) { return false; }
		int swap = array[a];
		array[a] = array[b];
		array[b] = swap;
		return true;
	}

	/**
	 * 对列表进行乱序
	 * 
	 * @param <T>
	 * @param list 待乱序列表
	 * @return list 待乱序列表的地址
	 */
	public static <T> List<T> upsetList(List<T> list) {
		if (list == null) { return null; }
		for (int i = 0; i < list.size(); i++) {
			swapList(list, i, RandomUtil.randInt(list.size()));
		}
		return list;
	}

	/**
	 * 对数组内容进行乱序
	 * 
	 * @param array 整形数组
	 * @return 数组起始地址
	 */
	public static int[] upsetArray(int[] array) {
		if (array == null) { return null; }
		for (int i = 0; i < array.length; i++) {
			swapArrayInt(array, i, RandomUtil.randInt(array.length));
		}
		return array;
	}

	/**
	 * 生成一个连续不重复的乱序列表，范围[start,end)
	 * 
	 * @param start 乱序起始值
	 * @param end   乱序最大值
	 * @return List[Integer]:乱序后的列表
	 */
	public static List<Integer> upsetList(int start, int end) {
		// 起始大于结束则交换数值
		if (start > end) {
			int swap = start;
			start = end;
			end = swap;
		}
		// 计算生成长度
		int init = end - start;
		if (init == 0) { return null; }
		// ArrayList初始赋值
		List<Integer> list = new ArrayList<Integer>(init > 10 ? init : 10);
		// 对list赋范围内的值
		for (int i = start; i < end; i++) {
			list.add(i);
		}
		// 对list进行乱序
		return upsetList(list);
	}

	/**
	 * 生成一个连续不重复的乱序数组，范围[start,end)
	 * 
	 * @param start 乱序起始值
	 * @param end   乱序最大值
	 * @return array:乱序后的数组
	 */
	public static int[] upsetArray(int start, int end) {
		// 起始大于结束则交换数值
		if (start > end) {
			int swap = start;
			start = end;
			end = swap;
		}
		// 计算生成长度
		int init = end - start;
		if (init == 0) { return null; }
		int array[] = new int[init];
		for (int i = start; i < end; i++) {
			array[i - start] = i;
		}
		// 对数组进行乱序
		return upsetArray(array);
	}
}