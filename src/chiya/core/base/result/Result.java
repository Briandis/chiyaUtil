package chiya.core.base.result;

import chiya.core.base.page.Page;

/**
 * HTTP默认装配的相应信息
 * 
 * @author chiya
 */
public class Result {
	/** 成功状态码 */
	public static final int SUCCESS_CODE = 200;
	/** 成功响应消息 */
	public static final String SUCCESS_MESSAGE = "业务执行完成";

	/** 失败状态码 */
	public static final int FAIL_CODE = -1;
	/** 失败响应消息 */
	public static final String FAIL_MESSAGE = "业务未能执行完成";

	/** 业务状态码 */
	private int code;
	/** 响应数据 */
	private Object data;
	/** 分页信息 */
	private Page page;
	/** 响应消息 */
	private String message;

	/** 无参构造 */
	private Result() {}

	/**
	 * 全参构造
	 * 
	 * @param code    业务状态码
	 * @param data    数据
	 * @param message 消息
	 * @param page    分页信息
	 */
	private Result(int code, Object data, String message, Page page) {
		this.code = code;
		this.data = data;
		this.message = message;
		this.page = page;
	}

	/**
	 * 构造方法
	 * 
	 * @param code    业务状态码
	 * @param data    数据
	 * @param message 消息
	 */
	private Result(int code, String message) {
		this.code = code;
		this.message = message;
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

	/**
	 * 执行成功
	 * 
	 * @return Result 消息对象
	 */
	public static Result success() {
		return new Result(SUCCESS_CODE, SUCCESS_MESSAGE);
	}

	/**
	 * 执行成功
	 * 
	 * @param data 数据
	 * @return Result 消息对象
	 */
	public static Result success(Object data) {
		return new Result(SUCCESS_CODE, data, SUCCESS_MESSAGE, null);
	}

	/**
	 * 执行成功
	 * 
	 * @param data 数据
	 * @param page 分页对象
	 * @return Result 消息对象
	 */
	public static Result success(Object data, Page page) {
		return new Result(SUCCESS_CODE, data, SUCCESS_MESSAGE, page);
	}

	/**
	 * 执行失败
	 * 
	 * @return Result 消息对象
	 */
	public static Result fail() {
		return new Result(FAIL_CODE, FAIL_MESSAGE);
	}

	/**
	 * 执行失败
	 * 
	 * @param message 失败消息
	 * @return Result 消息对象
	 */
	public static Result fail(String message) {
		return new Result(FAIL_CODE, message);
	}

	/**
	 * 参数错误
	 * 
	 * @return Result 消息对象
	 */
	public static Result paramentError() {
		return new Result(FAIL_CODE, "参数错误");
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
	 * 设置状态码
	 * 
	 * @param code 状态码
	 * @return Result 自身
	 */
	public Result chainCode(int code) {
		this.code = code;
		return this;
	}

	/**
	 * 设置内容
	 * 
	 * @param data 内容
	 * @return Result 自身
	 */
	public Result chainData(Object data) {
		this.data = data;
		return this;
	}

	/**
	 * 设置消息
	 * 
	 * @param message 消息
	 * @return Result 自身
	 */
	public Result chainMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * 设置分页
	 * 
	 * @param page 分页对象
	 * @return Result 自身
	 */
	public Result chainPage(Page page) {
		this.page = page;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"code\":\"");
		builder.append(code);
		builder.append("\", \"data\":\"");
		builder.append(data);
		builder.append("\", \"page\":\"");
		builder.append(page);
		builder.append("\", \"message\":\"");
		builder.append(message);
		builder.append("\"} ");
		return builder.toString();
	}
}
