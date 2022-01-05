package chiya.core.base.exception;

import chiya.core.base.result.ResultEnum;

/**
 * 通用异常
 * 
 * @author Brian
 */
public class ChiyaException extends RuntimeException {

	/** 序列化标识符 */
	private static final long serialVersionUID = 3482143344393119504L;
	/**
	 * 枚举类型
	 */
	private ResultEnum resultEnum;;

	/**
	 * 自定义异常-根据返回值枚举类型
	 * 
	 * @param errorCode 返回值枚举类型
	 */
	public ChiyaException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.resultEnum = resultEnum;
	}

	/**
	 * 自定义异常-根据返回值枚举类型，和自定义消息
	 * 
	 * @param resultEnum 错误枚举类型
	 * @param message    消息
	 */
	public ChiyaException(ResultEnum resultEnum, String message) {
		super(message);
		this.resultEnum = resultEnum;
	}

	public ChiyaException(String message) {
		super(message);
	}

	public ChiyaException(Throwable cause) {
		super(cause);
	}

	public ChiyaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResultEnum getResultEnum() {
		return resultEnum;
	}

}
