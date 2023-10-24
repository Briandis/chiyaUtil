package chiya.core.base.exception;

import chiya.core.base.result.Result;

/**
 * 通用异常
 * 
 * @author chiya
 */
public class ChiyaException extends RuntimeException {

	/** 序列化标识符 */
	private static final long serialVersionUID = 3482143344393119504L;

	/** 上下文传递的响应信息 */
	private Result result;

	/**
	 * 自定义异常-根据返回值枚举类型
	 * 
	 * @param result 响应信息
	 */
	public ChiyaException(Result result) {
		super(result.getMessage());
		this.result = result;
	}

	/**
	 * 异常消息构造方法
	 * 
	 * @param message 错误信息
	 */
	public ChiyaException(String message) {
		super(message);
		result = Result.fail(message);
	}

	/**
	 * 构造方法
	 * 
	 * @param cause 抛出的异常
	 */
	public ChiyaException(Throwable cause) {
		super(cause);
		result = Result.fail();
	}

	/**
	 * 构造方法
	 * 
	 * @param message 错误信息
	 * @param cause   抛出的异常
	 */
	public ChiyaException(String message, Throwable cause) {
		super(message, cause);
		result = Result.fail(message);
	}

	/**
	 * 获取异常中响应信息
	 * 
	 * @return 响应信息
	 */
	public Result getResult() {
		return result == null ? Result.fail() : result;
	}

}
