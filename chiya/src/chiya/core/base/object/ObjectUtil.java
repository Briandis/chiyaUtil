package chiya.core.base.object;

import chiya.core.base.string.StringUtil;

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
		return StringUtil.parseInt(toString(object));
	}
}
