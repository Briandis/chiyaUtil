package chiya.core.base.result;

import chiya.core.base.page.Page;

/**
 * 可以反序列化的result包装类
 * 
 * @author chiya
 *
 * @param <T> 返回的数据类型
 */
public class ResultPack<T> {
	/** 业务状态码 */
	private int code;

	/** 响应数据 */
	private T data;

	/** 分页信息 */
	private Page page;

	/** 响应消息 */
	private String message;

	/**
	 * 获取业务状态码
	 * 
	 * @return 业务状态码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置业务状态码
	 * 
	 * @param code 业务状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 链式添加业务状态码
	 * 
	 * @param code 业务状态码
	 * @return 对象本身
	 */
	public ResultPack<T> chainCode(int code) {
		setCode(code);
		return this;
	}

	/**
	 * 获取响应数据
	 * 
	 * @return 响应数据
	 */
	public T getData() {
		return data;
	}

	/**
	 * 设置响应数据
	 * 
	 * @param data 响应数据
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 链式添加响应数据
	 * 
	 * @param data 响应数据
	 * @return 对象本身
	 */
	public ResultPack<T> chainData(T data) {
		setData(data);
		return this;
	}

	/**
	 * 获取分页信息
	 * 
	 * @return 分页信息
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * 设置分页信息
	 * 
	 * @param page 分页信息
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * 链式添加分页信息
	 * 
	 * @param page 分页信息
	 * @return 对象本身
	 */
	public ResultPack<T> chainPage(Page page) {
		setPage(page);
		return this;
	}

	/**
	 * 获取响应消息
	 * 
	 * @return 响应消息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置响应消息
	 * 
	 * @param message 响应消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 链式添加响应消息
	 * 
	 * @param message 响应消息
	 * @return 对象本身
	 */
	public ResultPack<T> chainMessage(String message) {
		setMessage(message);
		return this;
	}

	/**
	 * 请求是否成功
	 * 
	 * @return true:是/false:否
	 */
	public boolean isSuccess() {
		return code == 200;
	}

}
