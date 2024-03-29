package chiya.core.base.exception;

import java.util.Collection;
import java.util.Map;

import chiya.core.base.number.NumberUtil;
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
		if (b) { fail(message); }
	}

	/**
	 * 如果两个Integer包装类的值相等，则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isEqualInteger(Integer a, Integer b, String message) {
		isTrue(NumberUtil.equalInteger(a, b), message);
	}

	/**
	 * 如果两个Integer包装类的值不相等则抛出异常
	 * 
	 * @param a       Integer包装类
	 * @param b       Integer包装类
	 * @param message 消息
	 */
	public static void isNotEqualInteger(Integer a, Integer b, String message) {
		isFalse(NumberUtil.equalInteger(a, b), message);
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

	/**
	 * 如果字符串为空的情况，则抛出异常
	 * 
	 * @param string  待比较字符串
	 * @param message 消息
	 */
	public static void isStringEmpty(String string, String message) {
		isTrue(StringUtil.isNullOrZero(string), message);
	}

	/**
	 * 如果不符合手机号，则抛出异常
	 * 
	 * @param phone   手机号
	 * @param message 消息
	 */
	public static void isNotPhone(String phone, String message) {
		isFalse(StringUtil.matchPhone(phone), message);
	}

	/**
	 * 如果不符合邮箱格式，则抛出异常
	 * 
	 * @param mail    邮箱
	 * @param message 消息
	 */
	public static void isNotMail(String mail, String message) {
		isFalse(StringUtil.matchEmail(mail), message);
	}

	/**
	 * 如果两个字符串相同，则抛出异常
	 * 
	 * @param a       字符串a
	 * @param b       字符串b
	 * @param message 待抛出的异常的消息
	 */
	public static void isEqualString(String a, String b, String message) {
		isTrue(StringUtil.eqString(a, b), message);
	}

	/**
	 * 如果两个字符串不相同，则抛出异常
	 * 
	 * @param a       字符串a
	 * @param b       字符串b
	 * @param message 待抛出的异常的消息
	 */
	public static void isNotEqualString(String a, String b, String message) {
		isFalse(StringUtil.eqString(a, b), message);
	}

	/**
	 * 如果列表为空，抛出异常
	 * 
	 * @param list    列表
	 * @param message 消息
	 */
	public static void isEmpty(Collection<?> list, String message) {
		isNull(list, message);
		isTrue(list.size() == 0, message);
	}

	/**
	 * 如果MAP为空，抛出异常
	 * 
	 * @param list    列表
	 * @param message 消息
	 */
	public static void isEmpty(Map<?, ?> map, String message) {
		isNull(map, message);
		isTrue(map.size() == 0, message);
	}
}
