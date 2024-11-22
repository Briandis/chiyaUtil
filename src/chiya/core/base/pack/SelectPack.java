package chiya.core.base.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.page.Page;

/**
 * 查询包装
 * 
 * @author chiya
 *
 * @param <T> 任意对象
 */
public class SelectPack<T> {

	/** 大于 */
	private T greater;
	/** 小于 */
	private T less;
	/** 大于等于 */
	private T greaterEqual;
	/** 小于等于 */
	private T lessEqual;
	/** 等于 */
	private T equal;
	/** 拼接SQL */
	private String splicingSQL;

	/** 分页 */
	private Page page;

	/** 在区间 */
	private HashMap<String, List<Object>> fieldIn = new HashMap<>();
	/** 模糊查询 */
	private HashMap<String, String> like = new HashMap<>();;

	/** 排序 */
	private List<OrderBy> orderBy = new ArrayList<>();

	/**
	 * 获取大于
	 * 
	 * @return 大于
	 */
	public T getGreater() {
		return greater;
	}

	/**
	 * 设置大于
	 * 
	 * @param greater 大于
	 */
	public void setGreater(T greater) {
		this.greater = greater;
	}

	/**
	 * 链式添加大于
	 * 
	 * @param greater 大于
	 * @return 对象本身
	 */
	public SelectPack<T> chainGreater(T greater) {
		setGreater(greater);
		return this;
	}

	/**
	 * 获取小于
	 * 
	 * @return 小于
	 */
	public T getLess() {
		return less;
	}

	/**
	 * 设置小于
	 * 
	 * @param less 小于
	 */
	public void setLess(T less) {
		this.less = less;
	}

	/**
	 * 链式添加小于
	 * 
	 * @param less 小于
	 * @return 对象本身
	 */
	public SelectPack<T> chainLess(T less) {
		setLess(less);
		return this;
	}

	/**
	 * 获取大于等于
	 * 
	 * @return 大于等于
	 */
	public T getGreaterEqual() {
		return greaterEqual;
	}

	/**
	 * 设置大于等于
	 * 
	 * @param greaterEqual 大于等于
	 */
	public void setGreaterEqual(T greaterEqual) {
		this.greaterEqual = greaterEqual;
	}

	/**
	 * 链式添加大于等于
	 * 
	 * @param greaterEqual 大于等于
	 * @return 对象本身
	 */
	public SelectPack<T> chainGreaterEqual(T greaterEqual) {
		setGreaterEqual(greaterEqual);
		return this;
	}

	/**
	 * 获取小于等于
	 * 
	 * @return 小于等于
	 */
	public T getLessEqual() {
		return lessEqual;
	}

	/**
	 * 设置小于等于
	 * 
	 * @param lessEqual 小于等于
	 */
	public void setLessEqual(T lessEqual) {
		this.lessEqual = lessEqual;
	}

	/**
	 * 链式添加小于等于
	 * 
	 * @param lessEqual 小于等于
	 * @return 对象本身
	 */
	public SelectPack<T> chainLessEqual(T lessEqual) {
		setLessEqual(lessEqual);
		return this;
	}

	/**
	 * 获取等于
	 * 
	 * @return 等于
	 */
	public T getEqual() {
		return equal;
	}

	/**
	 * 设置等于
	 * 
	 * @param equal 等于
	 */
	public void setEqual(T equal) {
		this.equal = equal;
	}

	/**
	 * 链式添加等于
	 * 
	 * @param equal 等于
	 * @return 对象本身
	 */
	public SelectPack<T> chainEqual(T equal) {
		setEqual(equal);
		return this;
	}

	/**
	 * 获取分页
	 * 
	 * @return 分页
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * 设置分页
	 * 
	 * @param page 分页
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * 链式添加分页
	 * 
	 * @param page 分页
	 * @return 对象本身
	 */
	public SelectPack<T> chainPage(Page page) {
		setPage(page);
		return this;
	}

	/**
	 * 获取拼接SQL
	 * 
	 * @return 拼接SQL
	 */
	public String getSplicingSQL() {
		return splicingSQL;
	}

	/**
	 * 设置拼接SQL
	 * 
	 * @param splicingSQL 拼接SQL
	 */
	public void setSplicingSQL(String splicingSQL) {
		this.splicingSQL = splicingSQL;
	}

	/**
	 * 链式添加拼接SQL
	 * 
	 * @param splicingSQL 拼接SQL
	 * @return 对象本身
	 */
	public SelectPack<T> chainSplicingSQL(String splicingSQL) {
		setSplicingSQL(splicingSQL);
		return this;
	}

	/**
	 * in 添加
	 * 
	 * @param key  字段
	 * @param list 列表
	 * @return 对象本身
	 */
	public SelectPack<T> in(String key, List<String> list) {
		fieldIn.computeIfAbsent(key, k -> new ArrayList<Object>()).addAll(list);
		return this;
	}

	/**
	 * in 添加
	 * 
	 * @param key   字段
	 * @param value 字段
	 * @return 对象本身
	 */
	public SelectPack<T> in(String key, String value) {
		fieldIn.computeIfAbsent(key, k -> new ArrayList<Object>()).add(value);
		return this;
	}

	/**
	 * in 添加
	 * 
	 * @param key   字段
	 * @param value 字段
	 * @return 对象本身
	 */
	public SelectPack<T> like(String key, String value) {
		like.put(key, value);
		return this;
	}

	/**
	 * 倒序
	 * 
	 * @param name 字段
	 * @return 对象本身
	 */
	public SelectPack<T> desc(String name) {
		for (OrderBy obj : orderBy) {
			if (obj.getFieldName().equals(name)) {
				obj.desc();
				return this;
			}
		}
		orderBy.add(OrderBy.desc(name));
		return this;
	}

	/**
	 * 升序
	 * 
	 * @param name 字段
	 * @return 对象本身
	 */
	public SelectPack<T> asc(String name) {
		for (OrderBy obj : orderBy) {
			if (obj.getFieldName().equals(name)) {
				obj.asc();
				return this;
			}
		}
		orderBy.add(OrderBy.asc(name));
		return this;
	}
}
