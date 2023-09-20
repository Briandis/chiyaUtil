package chiya.core.base.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import chiya.core.base.function.VoidGenericFunction;
import chiya.core.base.number.NumberUtil;
import chiya.core.base.object.ObjectUtil;
import chiya.core.base.page.Page;
import chiya.core.base.random.RandomUtil;
import chiya.core.base.string.StringUtil;
import chiya.core.base.time.DateUtil;

/**
 * 容器工具类
 * 
 * @author chiya
 */
public class ContainerUtil {

	/**
	 * 交换列表中两元素的下标,成功返回真，失败返回假
	 * 
	 * @param <T>  泛型对象
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
	 * @param <T>  泛型对象
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

	/**
	 * 根据字段进行分组，获得分组后对象
	 * 
	 * @param source 数据源
	 * @param key    lambda表达式，要分组的字段
	 * @return Map<?, List<T>>
	 */
	public static <T, K> Map<K, List<T>> streamGroup(List<T> source, Function<T, K> key) {
		return source.stream().collect(Collectors.groupingBy(key));
	}

	/**
	 * 将List根据关键字分组
	 * 
	 * @param <K>  索引类型
	 * @param <V>  对象类型
	 * @param list 列表
	 * @param key  获取索引属性的方法
	 * @return HashMap
	 */
	public static <K, V> HashMap<K, V> streamGroupToHashMap(List<V> list, Function<V, K> key) {
		HashMap<K, V> hashMap = new HashMap<>();
		list.forEach(obj -> hashMap.put(key.apply(obj), obj));
		return hashMap;
	}

	/**
	 * 根据MAP装配List
	 * 
	 * @param source List数据源
	 * @param map    需要封装的Map
	 * @param get    获取的字段
	 * @param set    设置的字段
	 */
	public static <T, K, U> void streamAssemblyMap(List<T> source, Map<K, U> map, Function<T, K> get, BiConsumer<T, U> set) {
		source.forEach(obj -> {
			set.accept(obj, map.get(get.apply(obj)));
		});
	}

	/**
	 * 根据List自动装配另一个list的外键信息
	 * 
	 * @param <T>        源List的泛型
	 * @param <C>        另一方List的泛型
	 * @param <K>        外键类型
	 * @param source     数据源列表
	 * @param child      装配方列表
	 * @param foreignKey 装配方外键,lambda表达式
	 * @param get        数据源所匹配的外键
	 * @param set        装配的目标位置
	 */
	public static <T, C, K> void streamAssemblyList(List<T> source, List<C> child, Function<C, K> foreignKey, Function<T, K> get, BiConsumer<T, List<C>> set) {
		Map<K, List<C>> map = child.stream().collect(Collectors.groupingBy(foreignKey));
		source.forEach(obj -> {
			K key = get.apply(obj);
			set.accept(obj, map.get(key));
		});
	}

	/**
	 * 根据List自动装配另一个list的外键信息，该方法是针对于一对一
	 * 
	 * @param <T>        源List的泛型
	 * @param <V>        另一方List的泛型
	 * @param <K>        外键类型
	 * @param source     数据源列表
	 * @param child      装配方列表
	 * @param foreignKey 装配方外键,lambda表达式
	 * @param get        数据源所匹配的外键
	 * @param set        装配的目标位置
	 */
	public static <T, V, K> void streamAssemblyListLinkOne(List<T> source, List<V> child, Function<V, K> foreignKey, Function<T, K> get, BiConsumer<T, V> set) {
		Map<K, V> map = streamGroupToHashMap(child, foreignKey);
		source.forEach(obj -> set.accept(obj, map.get(get.apply(obj))));
	}

	/**
	 * 提取列表中某个字段
	 * 
	 * @param <T>    列表泛型
	 * @param <R>    获取的字段类型
	 * @param list   传入的列表
	 * @param mapper lambda表达式
	 * @return List<R> 获取的字段列表
	 */
	public static <T, R> List<R> streamExtractField(List<T> list, Function<? super T, ? extends R> mapper) {
		if (list == null) { return null; }
		return list.stream().map(mapper).collect(Collectors.toList());
	}

	/**
	 * 获取某个List中某个字段的列表并去重
	 * 
	 * @param <T>    列表泛型
	 * @param <R>    获取的字段类型
	 * @param list   传入的列表
	 * @param mapper lambda表达式
	 * @return List<R> 获取的字段列表
	 */
	public static <T, R> Set<R> streamExtractFieldToSet(List<T> list, Function<? super T, ? extends R> mapper) {
		if (list == null) { return null; }
		return list.stream().map(mapper).collect(Collectors.toSet());
	}

	/**
	 * 根据字段过滤
	 * 
	 * @param <T>       列表泛型
	 * @param list      传入的列表
	 * @param predicate lambda表达式
	 * @return List<R> 过滤后的列表
	 */
	public static <T> List<T> streamFilter(List<T> list, Predicate<? super T> predicate) {
		if (list == null) { return null; }
		return list.stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * 获取某个List某个类型
	 * 
	 * @param <T>    列表泛型
	 * @param list   传入的列表
	 * @param mapper lambda表达式
	 * @return Set<R> 过滤后的列表
	 */
	public static <T> Set<T> streamFilterSet(List<T> list, Predicate<? super T> predicate) {
		if (list == null) { return null; }
		return list.stream().filter(predicate).collect(Collectors.toSet());
	}

	/**
	 * 从MAP中获取对应值，如果没有，则创建
	 * 
	 * @param <K>     键的泛型
	 * @param <V>     值的泛型
	 * @param map     获取的MAP
	 * @param key     键
	 * @param classes 值的实例化对象
	 * @return MAP中对象或者这个实例化的对象
	 */
	public static <K, V> V getValueOrPut(Map<K, V> map, K key, Class<V> classes) {
		return map.computeIfAbsent(key, k -> ObjectUtil.newObject(classes));
	}

	/**
	 * 判断字符串是否存在List中
	 * 
	 * @param string 需要查找的字符串
	 * @param list   带查找集合
	 * @return true:存在/false:不存在
	 */
	public static boolean stringInList(String string, List<String> list) {
		if (list != null) {
			for (String str : list) {
				if (StringUtil.eqString(string, str)) { return true; }
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否存在数组中
	 * 
	 * @param string 需要查找的字符串
	 * @param str[]  待查找数组
	 * @return true:存在/false:不存在
	 */
	public static boolean stringInArray(String string, String str[]) {
		if (str != null) {
			for (String st : str) {
				if (StringUtil.eqString(string, st)) { return true; }
			}
		}
		return false;
	}

	/**
	 * 如果数组中的字符串，出现在字符串中，则返回true，否则返回false<br>
	 * 不区分大小写
	 * 
	 * @param arr  要查找的多个字符串
	 * @param data 待校验的字符串
	 * @return true:存在/false:不存在
	 */
	public static boolean stringArrayInString(String arr[], String data) {
		// 如果数组为空，则要匹配的不存在，所以返回空
		if (arr == null) { return true; }
		if (data == null) { return false; }
		data = data.toLowerCase();
		for (String str : arr) {
			if (data.indexOf(str) != -1) { return true; }
		}
		return false;
	}

	/**
	 * 如果列表中的字符串，出现在字符串中，则返回true，否则返回false<br>
	 * 不区分大小写
	 * 
	 * @param list 要查找的多个字符串
	 * @param data 待校验的字符串
	 * @return true:存在/false:不存在
	 */
	public static boolean listInString(List<String> list, String data) {
		if (list == null) { return true; }
		if (data == null) { return false; }
		data = data.toLowerCase();
		for (String str : list) {
			if (data.indexOf(str) != -1) { return true; }
		}
		return false;
	}

	/**
	 * 判断key释放在列表中出现，key可以为Null，如果为Null,，则查找null<br>
	 * 此方法仅适合基础包装类型
	 * 
	 * @param key  要查找的唯一标识
	 * @param list 列表
	 * @return true:存在/false:不存在
	 */
	public static boolean objectInList(Object key, List<Object> list) {
		if (list == null) { return false; }
		// 查找的key为Null的情况
		if (key == null) {
			for (Object object : list) {
				if (object == null) { return true; }
			}
		} else {
			for (Object object : list) {
				if (key.equals(object)) { return true; }
			}
		}
		return false;
	}

	/**
	 * 判断key释放在数组中出现，key可以为Null，如果为Null,，则查找null<br>
	 * 此方法仅适合基础包装类型
	 * 
	 * @param key   要查找的唯一标识
	 * @param array 对象数组
	 * @return true:存在/false:不存在
	 */
	public static boolean objectInArray(Object key, Object... array) {
		if (array == null) { return false; }
		// 查找的key为Null的情况
		if (key == null) {
			for (Object object : array) {
				if (object == null) { return true; }
			}
		} else {
			for (Object object : array) {
				if (key.equals(object)) { return true; }
			}
		}
		return false;
	}

	/**
	 * 根据传入的List进行分页
	 * 
	 * @param <T>  泛型对象
	 * @param list 传入的List
	 * @param page 分页数据
	 * @return 分页后的数据
	 */
	public static <T> List<T> listPage(List<T> list, Page page) {
		if (page == null) { return list; }
		return listPage(list, page.getStart(), page.getCount());
	}

	/**
	 * 根据传入的List进行分页
	 * 
	 * @param <T>   泛型对象
	 * @param list  传入的List
	 * @param start 获取的起始位置
	 * @param count 获取的数量
	 * @return 分页后的List
	 */
	public static <T> List<T> listPage(List<T> list, int start, int count) {
		if (list == null) { return null; }
		return list.stream().skip(start).limit(count).collect(Collectors.toList());
	}

	/**
	 * 升序排序
	 * 
	 * @param <T>        泛型对象
	 * @param list       传入的List
	 * @param comparator 比较大小的方法
	 * @return 升序后的list
	 */
	public static <T> List<T> listSort(List<T> list, Comparator<? super T> comparator) {
		if (list == null) { return null; }
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}

	/**
	 * 降序排序
	 * 
	 * @param <T>        泛型对象
	 * @param list       传入的List
	 * @param comparator 比较大小的方法
	 * @return 升序后的list
	 */
	public static <T> List<T> listSortDesc(List<T> list, Comparator<? super T> comparator) {
		if (list == null) { return null; }
		return list.stream().sorted(comparator.reversed()).collect(Collectors.toList());
	}

	/**
	 * 分页迭代列表
	 * 
	 * @param <T>泛型对象
	 * @param list     迭代的列表
	 * @param count    每次迭代对象的数量
	 * @param function 获取每次迭代列表的函数式对象
	 */
	public static <T> void forEachPage(List<T> list, int count, VoidGenericFunction<List<T>> function) {
		if (list != null) {
			if (count < 0) { count = 10; }
			for (int start = 0; start < list.size(); start += count) {
				function.execute(ContainerUtil.listPage(list, start, count));
			}
		}
	}

	/**
	 * 分页迭代列表
	 * 
	 * @param <T>泛型对象
	 * @param list     迭代的列表
	 * @param function 获取每次迭代列表的函数式对象
	 */
	public static <T> void forEachPage(List<T> list, VoidGenericFunction<List<T>> function) {
		forEachPage(list, 10, function);
	}

	/**
	 * 根据字段升序
	 * 
	 * @param <T>类型
	 * @param list          列表
	 * @param attributeName 字段名称
	 * @return 自身
	 */
	public static <T> List<T> listSort(List<T> list, String attributeName) {
		if (list.size() > 0) {
			Field field = ObjectUtil.findAttribute(list.get(0).getClass(), attributeName);
			if (field == null) { return list; }
			field.setAccessible(true);
			return ContainerUtil.listSort(list, (a, b) -> {
				try {
					Object dataA = field.get(a);
					Object dataB = field.get(b);
					if (dataA instanceof Number) { return NumberUtil.compareSize((Number) dataA, (Number) dataB); }
					if (dataA instanceof String) { return StringUtil.compareSize((String) dataA, (String) dataB); }
					if (dataA instanceof Date) { return DateUtil.compareSize((Date) dataA, (Date) dataB); }
				} catch (Exception e) {}
				return 0;
			});
		}
		return list;
	}

	/**
	 * 根据字段升序
	 * 
	 * @param <T>类型
	 * @param list          列表
	 * @param attributeName 字段名称
	 * @return 自身
	 */
	public static <T> List<T> listSortDesc(List<T> list, String attributeName) {
		if (list.size() > 0) {
			Field field = ObjectUtil.findAttribute(list.get(0).getClass(), attributeName);
			if (field == null) { return list; }
			field.setAccessible(true);
			return ContainerUtil.listSortDesc(list, (a, b) -> {
				try {
					Object dataA = field.get(a);
					Object dataB = field.get(b);
					if (dataA instanceof Number) { return NumberUtil.compareSize((Number) dataA, (Number) dataB); }
					if (dataA instanceof String) { return StringUtil.compareSize((String) dataA, (String) dataB); }
					if (dataA instanceof Date) { return DateUtil.compareSize((Date) dataA, (Date) dataB); }
				} catch (Exception e) {}
				return 0;
			});
		}
		return list;
	}

	/**
	 * 获取列表的最后一个
	 * 
	 * @param <T>  泛型
	 * @param list 列表
	 * @return 最后一个值
	 */
	public static <T> T getLast(List<T> list) {
		return list.isEmpty() ? null : list.get(list.size() - 1);
	}

	/**
	 * 移除列表的最后一个
	 * 
	 * @param <T>  泛型
	 * @param list 列表
	 * @return 最后一个值
	 */
	public static <T> T removeLast(List<T> list) {
		return list.isEmpty() ? null : list.remove(list.size() - 1);
	}
}
