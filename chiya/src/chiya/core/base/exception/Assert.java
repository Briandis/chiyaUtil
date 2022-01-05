package chiya.core.base.exception;

import chiya.core.base.result.ResultEnum;
import chiya.core.base.string.StringUtil;

/**
 * 断言处理类，用于抛出各种API异常
 */
public class Assert {
	/**
	 * 失败错误
	 * 
	 * @param message 失败消息
	 */
	public static void fail(String message) {
		throw new ChiyaException(message);
	}

	/**
	 * 断言-自定义异常类型
	 * 
	 * @param resultEnum 返回类型枚举值
	 */
	public static void fail(ResultEnum resultEnum) {
		throw new ChiyaException(resultEnum);
	}

	/**
	 * 参数断言-自定义消息
	 * 
	 * @param message 自定义消息
	 */
	public static void notParamFail(String message) {
		ResultEnum resultEnum = ResultEnum.PARAMENTER_ERROR;
		throw new ChiyaException(resultEnum, message);
	}

	/**
	 * 检查对象如果不为null，则抛出异常
	 * 
	 * @param object  待检查对象
	 * @param message 消息
	 */
	public static void isNotNull(Object object, String message) {
		isTrue(object != null, message);
	}

	/**
	 * 检查对象如果为null，则抛出异常
	 * 
	 * @param object  待检查对象
	 * @param message 消息
	 */
	public static void isNull(Object object, String message) {
		isTrue(object == null, message);
	}

	/**
	 * 如果是假，则抛出异常
	 * 
	 * @param b       判断的值
	 * @param message 消息
	 */
	public static void isFalse(boolean b, String message) {
		isTrue(!b, message);
	}

	/**
	 * 如果是真，则抛出异常
	 * 
	 * @param b       判断的值
	 * @param message 消息
	 */
	public static void isTrue(boolean b, String message) {
		if (b) { throw new ChiyaException(message); }
	}

	/**
	 * 如果两个Integer包装类的值相等，则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isEqualInteger(Integer a, Integer b, String message) {
		isTrue(StringUtil.eqInteger(a, b), message);
	}

	/**
	 * 如果两个Integer包装类的值不相等则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isNotEqualInteger(Integer a, Integer b, String message) {
		isFalse(StringUtil.eqInteger(a, b), message);
	}

	/**
	 * 如果是a>b，则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isGreater(Integer a, Integer b, String message) {
		isNull(a, message);
		isNull(b, message);
		isTrue(a.intValue() > b.intValue(), message);
	}

	/**
	 * 如果是a>=b，则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isGreaterOrEqual(Integer a, Integer b, String message) {
		isNull(a, message);
		isNull(b, message);
		isTrue(a.intValue() >= b.intValue(), message);
	}

	/**
	 * 如果是a < b，则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isLess(Integer a, Integer b, String message) {
		isNull(a, message);
		isNull(b, message);
		isTrue(a.intValue() < b.intValue(), message);
	}

	/**
	 * 如果是a<=b，则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isLessOrEqual(Integer a, Integer b, String message) {
		isNull(a, message);
		isNull(b, message);
		isTrue(a.intValue() <= b.intValue(), message);
	}
}
