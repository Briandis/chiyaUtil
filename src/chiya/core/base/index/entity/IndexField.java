package chiya.core.base.index.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.function.GenericGenericFunction;
import chiya.core.base.index.algorithm.BinarySearch;

/**
 * 索引字段
 * 
 * @author chiya
 * @param <T>获取的对象类型
 * @param <V>获取的值类型
 *
 */
public abstract class IndexField<T, V> {

	/** 比较方法 */
	private Comparator<V> comparator;
	/** 获取对象值的方法 */
	private GenericGenericFunction<T, V> genericGenericFunction;
	/** 索引 */
	private List<V> indexList = new ArrayList<>();
	/** 哈希索引 */
	private HashMap<V, List<Integer>> itemListMap = new HashMap<>();

	/**
	 * 构造方法
	 * 
	 * @param valueObjectFunction 获取参数中某个值
	 * @param comparator          对比
	 */
	public IndexField(GenericGenericFunction<T, V> genericGenericFunction, Comparator<V> comparator) {
		this.comparator = comparator;
		this.genericGenericFunction = genericGenericFunction;
	}

	/**
	 * 添加索引
	 * 
	 * @param baseField 索引的字段
	 */
	public void addIndex(int itemIndex, T object) {
		itemListMap.computeIfAbsent(
			getValue(object),
			key -> new ArrayList<>()
		).add(itemIndex);
	}

	/** 排序 */
	public void sort() {
		indexList.clear();
		indexList.addAll(itemListMap.keySet());
		indexList.sort(comparator);
	}

	/**
	 * 比较两个对象
	 * 
	 * @param a 对象a
	 * @param b 对象b
	 * @return 比较的大小
	 */
	public int compare(T a, T b) {
		return comparator.compare(getValue(a), getValue(b));
	}

	/**
	 * 获取对象中的值的方法
	 * 
	 * @return 方法
	 */
	public GenericGenericFunction<T, ?> getGenericGenericFunction() {
		return genericGenericFunction;
	}

	/**
	 * 获取对象中的值
	 * 
	 * @param object 对象
	 * @return 值
	 */
	public V getValue(T object) {
		return genericGenericFunction.get(object);
	}

	/**
	 * 查找值相同的
	 * 
	 * @return 找到的列表
	 */
	public List<Integer> selectEqual(V data) {
		return itemListMap.get(data);
	}

	/**
	 * 查找值大于的
	 * 
	 * @param data值
	 * @return 找到的列表
	 */
	public List<Integer> selectGreate(V data) {
		int index = BinarySearch.interval(indexList, data, obj -> obj, comparator, true);
		List<Integer> list = new ArrayList<>();
		for (int i = index; i < indexList.size(); i++) {
			list.addAll(itemListMap.get(indexList.get(i)));
		}
		return list;
	}

	/**
	 * 查找值小于的
	 * 
	 * @param data值
	 * @return 找到的列表
	 */
	public List<Integer> selectLess(V data) {
		int index = BinarySearch.interval(indexList, data, obj -> obj, comparator, false);
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < index + 1; i++) {
			list.addAll(itemListMap.get(indexList.get(i)));
		}
		return list;
	}

	/**
	 * 输出索引排序
	 * 
	 * @return 索引排序
	 */
	public List<Integer> toIndex() {
		List<Integer> list = new ArrayList<>();
		indexList.forEach(index -> list.addAll(itemListMap.get(index)));
		return list;
	}

	/**
	 * 获取索引列表
	 * 
	 * @return 根据索引排序好的二维数据
	 */
	public List<List<Integer>> toIndexList() {
		List<List<Integer>> list = new ArrayList<>();
		indexList.forEach(index -> list.add(new ArrayList<>(itemListMap.get(index))));
		return list;
	}

	/**
	 * 在两者之间，不包含两者
	 * 
	 * @param lowerLimit 下限
	 * @param upperLimit 上限
	 * @return 索引排序
	 */
	public List<Integer> between(V lowerLimit, V upperLimit) {
		int start = BinarySearch.interval(indexList, lowerLimit, obj -> obj, comparator, true);
		int end = BinarySearch.interval(indexList, upperLimit, obj -> obj, comparator, false);
		List<Integer> list = new ArrayList<>();
		for (int i = start; i < end + 1; i++) {
			if (i < indexList.size()) { list.addAll(itemListMap.get(indexList.get(i))); }
		}
		return list;
	}

}
