package chiya.core.base.result;

import chiya.core.base.page.Page;

/**
 * HTTP默认装配的相应信息
 * 
 * @author Brian
 */
public class Result {

	/**
	 * 业务状态码
	 */
	private int code;

	/**
	 * 响应数据
	 */
	private Object data;

	/**
	 * 分页信息
	 */
	private Page page;

	/**
	 * 响应消息
	 */
	private String message;

	/**
	 * 无参构造
	 */
	private Result() {}

	/**
	 * 全参构造
	 * 
	 * @param code    业务状态码
	 * @param data    数据
	 * @param message 消息
	 */
	private Result(int code, Object data, String message, Page page) {
		this.code = code;
		this.data = data;
		this.message = message;
		this.page = page;
	}

	/**
	 * 枚举构造
	 * 
	 * @param resultEnum 返回类型枚举值
	 */
	private Result(ResultEnum resultEnum) {
		this.code = resultEnum.getCode();
		this.message = resultEnum.getMessage();
	}

	/**
	 * 枚举有值构造
	 * 
	 * @param resultEnum 返回类型枚举值
	 * @param data       数据
	 */
	private Result(ResultEnum resultEnum, Object data, Page page) {
		this.code = resultEnum.getCode();
		this.data = data;
		this.page = page;
		this.message = resultEnum.getMessage();
	}

	/**
	 * 获取状态码
	 * 
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 获取数据体
	 * 
	 * @return data体
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 获取消息
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置状态码
	 * 
	 * @param code 状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 设置内容
	 * 
	 * @param data 内容
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 设置消息
	 * 
	 * @param message 消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取分页
	 * 
	 * @return 分页对象
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * 设置分页
	 * 
	 * @param page 分页对象
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "{\"code\":\"" + code + "\", \"data\":\"" + data + "\", \"page\":\"" + page + "\", \"message\":\"" + message + "\"}";
	}

	/**
	 * 执行成功
	 * 
	 * @return Result 消息对象
	 */
	public static Result success() {
		return new Result(ResultEnum.SUCCESS);
	}

	/**
	 * 执行成功
	 * 
	 * @param data 数据
	 * @return Result 消息对象
	 */
	public static Result success(Object data) {
		return new Result(ResultEnum.SUCCESS, data, null);
	}

	/**
	 * 执行成功
	 * 
	 * @param data 数据
	 * @param page 分页对象
	 * @return Result 消息对象
	 */
	public static Result success(Object data, Page page) {
		return new Result(ResultEnum.SUCCESS, data, page);
	}

	/**
	 * 执行失败
	 * 
	 * @return Result 消息对象
	 */
	public static Result fail() {
		return new Result(ResultEnum.FAIL);
	}

	/**
	 * 参数错误
	 * 
	 * @return Result 消息对象
	 */
	public static Result paramentError() {
		return new Result(ResultEnum.PARAMENTER_ERROR);
	}

	/**
	 * 重定向
	 * 
	 * @param path 新的路径
	 * @return Result 消息对象
	 */
	public static Result redirect(String path) {
		return new Result(ResultEnum.REDIRECT, path, null);
	}

	/**
	 * 根据业务是否执行成功响应
	 * 
	 * @param isSuccess 是否成功
	 * @return Result 消息对象
	 */
	public static Result judge(boolean isSuccess) {
		return isSuccess ? success() : fail();
	}

	/**
	 * 根据业务是否执行成功响应
	 * 
	 * @param isSuccess 是否成功
	 * @param data      数据
	 * @return Result 消息对象
	 */
	public static Result judge(boolean isSuccess, Object data) {
		return isSuccess ? success(data) : fail();
	}

	/**
	 * 根据业务是否执行成功响应
	 * 
	 * @param isSuccess 是否成功
	 * @param data      数据
	 * @param page      分页对象
	 * @return Result 消息对象
	 */
	public static Result judge(boolean isSuccess, Object data, Page page) {
		return isSuccess ? success(data, page) : fail();
	}

	/**
	 * 根据枚举值响应
	 * 
	 * @param resultEnum 枚举值
	 * @return Result 消息对象
	 */
	public static Result enums(ResultEnum resultEnum) {
		return new Result(resultEnum);
	}

}
