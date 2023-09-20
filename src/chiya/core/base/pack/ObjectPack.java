package chiya.core.base.pack;

/**
 * 对象对象类型封装
 * 
 * @author chiya
 *
 */
public class ObjectPack<T> {

	/** 对象封装类型 */
	public T data;

	/**
	 * 获取对象封装类型
	 * 
	 * @return 对象封装类型
	 */
	public T getData() {
		return data;
	}

	/**
	 * 设置对象封装类型
	 * 
	 * @param data 对象封装类型
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 链式添加对象封装类型
	 * 
	 * @param data 对象封装类型
	 * @return 对象本身
	 */
	public ObjectPack<T> chainData(T data) {
		setData(data);
		return this;
	}

	/**
	 * 获取并改变
	 * 
	 * @param data 值
	 * @return 改变前的值
	 */
	public T getAndChange(T data) {
		T temp = getData();
		setData(data);
		return temp;
	}

}
