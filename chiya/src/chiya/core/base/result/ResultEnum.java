package chiya.core.base.result;

/**
 * 响应数据枚举类型
 * 
 * @author Brian
 *
 */
public enum ResultEnum {
	/**
	 * 业务执行成功
	 */
	SUCCESS(
		200, "业务执行完成"
	),
	/**
	 * 业务执行失败
	 */
	FAIL(
		-1, "业务未能执行成功"
	),
	/**
	 * 需要跳转到新路径
	 */
	REDIRECT(
		301, "需要重定向到新路径"
	),
	/**
	 * 传入参数错误
	 */
	PARAMENTER_ERROR(
		400, "传入参数错误"
	),
	/**
	 * 权限不足，拒绝执行
	 */
	AUTHORITY_ERROR(
		401, "程序拒绝执行"
	),
	/**
	 * 服务器确认之后拒绝执行
	 */
	REFUSE_ERROR(
		403, "程序确认之后拒绝执行"
	),
	/**
	 * 服务器异常
	 */
	ERROR(
		500, "程序出现异常而终止"
	),
	/**
	 * 断言响应
	 */
	ASSERTION_ERROR(
		601, "业务在执行过程中发现断言失败"
	);

	private int code;
	private String message;

	ResultEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
