package chiya.core.base.index.algorithm;

import java.util.Comparator;
import java.util.List;

import chiya.core.base.function.GenericGenericFunction;

/**
 * 二分查找
 * 
 * @author chiya
 *
 */
public class BinarySearch {

	/**
	 * 通用二分查找法
	 * 
	 * @param <T>                    查找的列表的类型
	 * @param <V>                    对比数据的类型
	 * @param list                   列表
	 * @param data                   查找的值
	 * @param genericGenericFunction 获取数据的方法
	 * @param comparator             对比方法
	 * @param equal                  值相等
	 * @param isGreate               作为大于进行判断
	 * @return -1则列表都大于该数，返回正常下标则该下标之后的都大于该数
	 */
	private static <T, V> int universal(List<T> list, V data, GenericGenericFunction<T, V> genericGenericFunction, Comparator<V> comparator, boolean equal, boolean isGreate) {
		if (list == null || list.size() == 0) { return -1; }
		int start = 0;
		int end = list.size() - 1;
		int index = 0;
		int nowResult = 0;
		while (true) {
			index = (end + start) / 2;
			nowResult = comparator.compare(genericGenericFunction.get(list.get(index)), data);
			if (nowResult == 0) {
				if (equal) { return index; }
				return isGreate ? index + 1 : index - 1;
			}
			if (start >= end) {
				if (equal) { return -1; } ;
				break;
			}
			if (nowResult < 0) {
				start = index + 1;
			} else {
				end = index - 1;
			}
		}
		nowResult = comparator.compare(genericGenericFunction.get(list.get(index)), data);
		if (nowResult == 0) { return index; }
		return nowResult > 0 ? index - (isGreate ? 0 : 1) : index + (isGreate ? 1 : 0);
	}

	/**
	 * 二分查找法，该数存在区间，需要是有序的数组<br>
	 * 如果[1, 3, 5, 8, 10]查找0，则返回-1<br>
	 * 如果[1, 3, 5, 8, 10]查找11，则返回4<br>
	 * 如果[1, 3, 5, 8, 10]查找3，则返回1<br>
	 * 如果[1, 3, 5, 8, 10]查找4，则返回1<br>
	 * 如果[1, 3, 5, 8, 10]查找6，则返回2<br>
	 * 查找的值如果比右边小，则返回该下标，故[-1:∞)
	 * 
	 * @param <T>                    查找的列表的类型
	 * @param <V>                    对比数据的类型
	 * @param list                   列表
	 * @param data                   查找的值
	 * @param genericGenericFunction 获取数据的方法
	 * @param comparator             对比方法
	 * @param isGreate               大于该数
	 * @return -1则列表都大于该数，返回正常下标则该下标之后的都大于该数
	 */
	public static <T, V> int interval(List<T> list, V data, GenericGenericFunction<T, V> genericGenericFunction, Comparator<V> comparator, boolean isGreate) {
		return universal(list, data, genericGenericFunction, comparator, false, isGreate);
	}

	/**
	 * 二分查找法，需要是有序的数组，不存在则返回-1<br>
	 * 
	 * @param <T>                    查找的列表的类型
	 * @param <V>                    对比数据的类型
	 * @param list                   列表
	 * @param data                   查找的值
	 * @param genericGenericFunction 获取数据的方法
	 * @param comparator             对比方法
	 * @return -1则列表都大于该数，返回正常下标则该下标之后的都大于该数
	 */
	public static <T, V> int find(List<T> list, V data, GenericGenericFunction<T, V> genericGenericFunction, Comparator<V> comparator) {
		return universal(list, data, genericGenericFunction, comparator, true, false);
	}
}
