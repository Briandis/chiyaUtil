package chiya.core.base.index.entity;

/**
 * 基础字段信息
 * 
 * @author chiya
 *
 */
public class BaseField<T> {

	/** 存储的数据 */
	private T data;
	/** 数据所在行数 */
	private int index;

	/**
	 * 获取存储的数据
	 * 
	 * @return 存储的数据
	 */
	public T getData() {
		return data;
	}

	/**
	 * 设置存储的数据
	 * 
	 * @param data 存储的数据
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 链式添加存储的数据
	 * 
	 * @param data 存储的数据
	 * @return 对象本身
	 */
	public BaseField<T> chainData(T data) {
		setData(data);
		return this;
	}

	/**
	 * 获取数据所在行数
	 * 
	 * @return 数据所在行数
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置数据所在行数
	 * 
	 * @param index 数据所在行数
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 链式添加数据所在行数
	 * 
	 * @param index 数据所在行数
	 * @return 对象本身
	 */
	public BaseField<T> chainIndex(int index) {
		setIndex(index);
		return this;
	}

}
