package chiya.core.base.cache;

import chiya.core.base.function.GenericNoParamFunction;

/**
 * 时间缓存器
 * 
 * @author chiya
 *
 */
public class ChiyaTimeCache<T> {
	/** 最后一次更新时间 */
	private long lastTime = 0;
	/** 缓存的数据 */
	private T data;
	/** 超时时间，毫秒 */
	private int timeOut = 30 * 60 * 1000;
	/** 重新获取时间 */
	private int updateTime = 10 * 60 * 1000;
	/** 刷新方法 */
	private GenericNoParamFunction<T> refreshFunction;

	/** 刷新数据 */
	public void refresh() {
		if (refreshFunction != null) { setData(refreshFunction.getValue()); }
	}

	/**
	 * 获取数据
	 * 
	 * @return 数据
	 */
	public T getData() {
		long expired = lastTime + timeOut;
		long nowTime = System.currentTimeMillis();

		if (expired > nowTime) {
			// 未过期，进一步检查是否需要刷新
			if (refreshFunction != null && expired - nowTime < updateTime) {
				// 需要更新
				setData(refreshFunction.getValue());
			}
			return this.data;
		}
		// 超时了，则刷新
		if (refreshFunction != null) {
			setData(refreshFunction.getValue());
		} else {
			// 没有刷新，则为null
			this.data = null;
		}
		return this.data;
	}

	/**
	 * 设置缓存的数据
	 * 
	 * @param data 缓存的数据
	 */
	public void setData(T data) {
		this.data = data;
		this.lastTime = System.currentTimeMillis();
	}

	/**
	 * 链式添加缓存的数据
	 * 
	 * @param data 缓存的数据
	 * @return 对象本身
	 */
	public ChiyaTimeCache<T> chainData(T data) {
		setData(data);
		return this;
	}

	/**
	 * 获取超时时间，毫秒
	 * 
	 * @return 超时时间，毫秒
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * 设置超时时间，毫秒
	 * 
	 * @param timeOut 超时时间，毫秒
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * 链式添加超时时间，毫秒
	 * 
	 * @param timeOut 超时时间，毫秒
	 * @return 对象本身
	 */
	public ChiyaTimeCache<T> chainTimeOut(int timeOut) {
		setTimeOut(timeOut);
		return this;
	}

	/**
	 * 获取重新获取时间
	 * 
	 * @return 重新获取时间
	 */
	public int getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置重新获取时间
	 * 
	 * @param updateTime 重新获取时间
	 */
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 链式添加重新获取时间
	 * 
	 * @param updateTime 重新获取时间
	 * @return 对象本身
	 */
	public ChiyaTimeCache<T> chainUpdateTime(int updateTime) {
		setUpdateTime(updateTime);
		return this;
	}

	/**
	 * 获取刷新方法
	 * 
	 * @return 刷新方法
	 */
	public GenericNoParamFunction<T> getRefreshFunction() {
		return refreshFunction;
	}

	/**
	 * 设置刷新方法
	 * 
	 * @param refreshFunction 刷新方法
	 */
	public void setRefreshFunction(GenericNoParamFunction<T> refreshFunction) {
		this.refreshFunction = refreshFunction;
	}

	/**
	 * 链式添加刷新方法
	 * 
	 * @param refreshFunction 刷新方法
	 * @return 对象本身
	 */
	public ChiyaTimeCache<T> chainRefreshFunction(GenericNoParamFunction<T> refreshFunction) {
		setRefreshFunction(refreshFunction);
		return this;
	}
}
