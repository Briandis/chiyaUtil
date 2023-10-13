package chiya.core.base.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 集合工具
 * 
 * @author chiya
 *
 */
public class SetUtil {

	/**
	 * 求A与B的交集
	 * 
	 * @param <T>类型
	 * @param a     集合A
	 * @param b     集合B
	 * @return 交集
	 */
	public static <T> HashSet<T> intersection(HashSet<T> a, HashSet<T> b) {
		HashSet<T> hashSet = new HashSet<>();
		a.forEach(item -> {
			if (b.contains(item)) { hashSet.add(item); }
		});
		return hashSet;
	}

	/**
	 * 求A与B的交集
	 * 
	 * @param <T>类型
	 * @param a     集合A
	 * @param b     集合B
	 * @return 交集
	 */
	public static <T> List<T> intersection(List<T> a, List<T> b) {
		HashSet<T> hashSet = intersection(new HashSet<>(a), new HashSet<>(b));
		return new ArrayList<T>(hashSet);
	}

	/**
	 * 求A与B的差集
	 * 
	 * @param <T>类型
	 * @param a     集合A
	 * @param b     集合B
	 * @return 交集
	 */
	public static <T> HashSet<T> difference(HashSet<T> a, HashSet<T> b) {
		HashSet<T> hashSet = new HashSet<>();
		a.forEach(item -> {
			if (!b.contains(item)) { hashSet.add(item); }
		});
		b.forEach(item -> {
			if (!a.contains(item)) { hashSet.add(item); }
		});
		return hashSet;
	}

	/**
	 * 求A与B的差集
	 * 
	 * @param <T>类型
	 * @param a     集合A
	 * @param b     集合B
	 * @return 交集
	 */
	public static <T> List<T> difference(List<T> a, List<T> b) {
		HashSet<T> hashSet = difference(new HashSet<>(a), new HashSet<>(b));
		return new ArrayList<T>(hashSet);
	}
}
