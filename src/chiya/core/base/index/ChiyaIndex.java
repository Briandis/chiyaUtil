package chiya.core.base.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.index.entity.IndexField;

public class ChiyaIndex<T> {
	/** 全部数据按照插入位置进行的存储 */
	private List<T> listdata = new ArrayList<>();
	/** 索引列 */
	private HashMap<String, IndexField<T, ?>> indexMap = new HashMap<>();

	/**
	 * 添加数据
	 * 
	 * @param data 数据
	 */
	public void addData(T data) {
		indexMap.forEach((indexName, index) -> index.addIndex(listdata.size(), data));
		listdata.add(data);
	}

	/**
	 * 添加数据
	 * 
	 * @param data 数据列表
	 */
	public void addData(Collection<T> data) {
		data.forEach(obj -> addData(obj));
	}

	/**
	 * 添加索引
	 * 
	 * @param name
	 * @param function
	 */
	public void setIndex(String name, IndexField<T, ?> indexField) {
		indexMap.put(name, indexField);
	}

	/** 准备索引 */
	public void ready() {
		indexMap.forEach((k, v) -> v.sort());
	}

	/**
	 * 查找值相同的列
	 * 
	 * @param <V>       查找的类型
	 * @param indexName 索引名称
	 * @param data      要查找的数据
	 * @return 找到的列表
	 */
	@SuppressWarnings("unchecked")
	public <V> List<T> selectEqule(String indexName, V data) {
		IndexField<T, ?> indexField = indexMap.get(indexName);
		List<T> list = new ArrayList<>();
		if (indexField != null) {
			List<Integer> itemList = ((IndexField<T, V>) indexField).selectEqual(data);
			if (itemList != null) { itemList.forEach(index -> list.add(listdata.get(index))); }
		}
		return list;
	}

	/**
	 * 获取某一个索引的排序
	 * 
	 * @param indexName 索引名称
	 * @return 找到的列表
	 */
	public List<T> toIndexSort(String indexName) {
		IndexField<T, ?> indexField = indexMap.get(indexName);
		List<T> list = new ArrayList<>();
		if (indexField != null) { indexField.toIndex().forEach(index -> list.add(listdata.get(index))); }
		return list;
	}

	/**
	 * 获取某一个索引的排序
	 * 
	 * @param indexName 索引名称
	 * @return 找到的列表
	 */
	public List<T> toIndexSort(String... indexName) {
		List<List<T>> indexSort = new ArrayList<>();
		indexSort.add(new ArrayList<>(listdata));
		for (String name : indexName) {
			IndexField<T, ?> indexField = indexMap.get(name);
			if (indexField != null) {
				// 每个array都进行脾虚
				indexSort.forEach(list -> list.sort(indexField::compare));
				// 拆分更细的array
				List<List<T>> tempList = new ArrayList<>();
				indexSort.forEach(list -> {
					List<T> data = new ArrayList<>();
					T previous = list.get(0);
					for (T next : list) {
						if (indexField.compare(next, previous) != 0) {
							tempList.add(data);
							data = new ArrayList<>();
						}
						data.add(next);
						previous = next;
					}
					tempList.add(data);
				});
				indexSort = tempList;
			}
		}
		// 合并输出
		List<T> result = new ArrayList<>();
		indexSort.forEach(list -> result.addAll(list));
		return result;
	}

	/**
	 * 查找值大于的
	 * 
	 * @param <V>       查找的类型
	 * @param indexName 索引名称
	 * @param data      要查找的数据
	 * @return 找到的列表
	 */
	@SuppressWarnings("unchecked")
	public <V> List<T> selectGreate(String indexName, V data) {
		IndexField<T, ?> indexField = indexMap.get(indexName);
		List<T> list = new ArrayList<>();
		if (indexField != null) {
			List<Integer> itemList = ((IndexField<T, V>) indexField).selectGreate(data);
			formIndexToList(itemList, list);
		}
		return list;
	}

	/**
	 * 查找值小于的
	 * 
	 * @param <V>       查找的类型
	 * @param indexName 索引名称
	 * @param data      要查找的数据
	 * @return 找到的列表
	 */
	@SuppressWarnings("unchecked")
	public <V> List<T> selectLess(String indexName, V data) {
		IndexField<T, ?> indexField = indexMap.get(indexName);
		List<T> list = new ArrayList<>();
		if (indexField != null) {
			List<Integer> itemList = ((IndexField<T, V>) indexField).selectLess(data);
			formIndexToList(itemList, list);
		}
		return list;
	}

	/**
	 * 查找值小于的
	 * 
	 * @param <V>        查找的类型
	 * @param indexName  索引名称
	 * @param lowerLimit 要查找的数据的下限
	 * @param upperLimit 要查找的数据的上限
	 * @return 找到的列表
	 */
	@SuppressWarnings("unchecked")
	public <V> List<T> selectBetween(String indexName, V lowerLimit, V upperLimit) {
		IndexField<T, ?> indexField = indexMap.get(indexName);
		List<T> list = new ArrayList<>();
		if (indexField != null) {
			List<Integer> itemList = ((IndexField<T, V>) indexField).between(lowerLimit, upperLimit);
			formIndexToList(itemList, list);
		}
		return list;
	}

	/**
	 * 根据索引添加列表
	 * 
	 * @param indexList 索引集合
	 * @return 索引列表
	 */
	public List<T> formIndexToList(List<Integer> indexList) {
		List<T> list = new ArrayList<>();
		if (indexList != null) { indexList.forEach(index -> list.add(listdata.get(index))); }
		return list;
	}

	/**
	 * 根据索引添加列表
	 * 
	 * @param indexList 索引集合
	 * @param list      现拥有的列表
	 */
	public void formIndexToList(List<Integer> indexList, List<T> list) {
		if (indexList != null) { indexList.forEach(index -> list.add(listdata.get(index))); }
	}

}
