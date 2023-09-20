package chiya.core.base.object;

/**
 * 跨函数传输工具
 * 
 * @author chiya
 *
 * @param <T> 类型
 */
public class ChiayPack<T> {

	/** 暂存数据 */
	public T data;

	/**
	 * 获取数据
	 * 
	 * @return 数据
	 */
	public T getData() {
		return data;
	}

	/**
	 * 设置数据
	 * 
	 * @param data 数据
	 */
	public void setData(T data) {
		this.data = data;
	}

}
