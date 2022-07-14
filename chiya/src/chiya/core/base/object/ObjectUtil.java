package chiya.core.base.object;

import chiya.core.base.number.NumberUtil;

/**
 * 对象转换工具
 * 
 * @author chiya
 */
public class ObjectUtil {

	/**
	 * 类型转换
	 * 
	 * @param object 基础对象
	 * @return 字符串
	 */
	public static String toString(Object object) {
		if (object != null && object instanceof String) { return (String) object; }
		return null;
	}

	/**
	 * 对象转整型
	 * 
	 * @param object 基础对象
	 * @return Integer包装类
	 */
	public static Integer toInteger(Object object) {
		if (object != null && object instanceof Integer) { return (Integer) object; }
		return null;
	}

	/**
	 * 对象转整型
	 * 
	 * @param object 基础对象
	 * @return Integer包装类
	 */
	public static Integer StringtoInteger(Object object) {
		return NumberUtil.parseIntOrNull(toString(object));
	}

	/**
	 * 构建对象
	 * 
	 * @param <O>     构建的泛型
	 * @param classes 对象的class
	 * @return 对象
	 */
	public static <O> O newObject(Class<O> classes) {
		try {
			return classes.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
