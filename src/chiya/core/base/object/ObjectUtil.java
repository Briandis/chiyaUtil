package chiya.core.base.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import chiya.core.base.number.NumberUtil;
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

	/**
	 * 根据字段名生成get方法名
	 * 
	 * @param fieldName 字段名
	 * @return get方法名
	 */
	public static String toGetMethod(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 根据字段名生成get方法名
	 * 
	 * @param fieldName 字段名
	 * @return get方法名
	 */
	public static String toSetMethod(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 递归查询该类型的所有属性
	 * 
	 * @param hashMap 存放容器
	 * @param clazz   查询的类型
	 */
	public static void getSuperField(HashMap<String, Class<?>> hashMap, Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			// key是get方法，value是属性
			hashMap.put(field.getName(), field.getType());
		}
		if (clazz.getSuperclass() != null) {
			// 递归查询
			getSuperField(hashMap, clazz.getSuperclass());
		}
	}

	/**
	 * 递归查询该类型的所有属性
	 * 
	 * @param clazz 查询的类型
	 */
	public static HashMap<String, Class<?>> getSuperField(Class<?> clazz) {
		HashMap<String, Class<?>> hashMap = new HashMap<>();
		getSuperField(hashMap, clazz);
		return hashMap;
	}

	/**
	 * 查找对象中的属性
	 * 
	 * @param classer 对象
	 * @param name    要查找的属性
	 * @return 属性对象
	 */
	public static Field findAttribute(Class<?> classer, String name) {
		if (classer == null) { return null; }
		for (Field field : classer.getDeclaredFields()) {
			if (StringUtil.eqString(field.getName(), name)) { return field; }
		}
		// 递归查找
		return findAttribute(classer.getSuperclass(), name);
	}

	/**
	 * 强制修改字段属性值
	 * 
	 * @param field  修改的字段
	 * @param object 修改的对象
	 * @param value  修改的值
	 * @return true:修改成功/false:修改失败
	 */
	public static boolean changeField(Field field, Object object, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
			return true;
		} catch (Exception e) {}
		return false;
	}

	/**
	 * 修改这个对象的属性
	 * 
	 * @param object 要修改的对象
	 * @param name   修改的字段
	 * @param value  值
	 */
	public static void setAttribute(Object object, String name, Object value) {
		// 查找属性
		Field field = findAttribute(object.getClass(), name);
		if (field == null) { return; }
		// 查找第一个找到的属性
		try {
			Method method = object.getClass().getMethod(toSetMethod(name), field.getType());
			// 存在set方法，则使用set方法进行注入
			method.invoke(object, value);
		} catch (Exception e) {}
		// 如果没有set方法
		changeField(field, object, value);
	}

	/**
	 * 修改这个对象的属性
	 * 
	 * @param object 要修改的对象
	 * @param name   修改的字段
	 * @param value  值
	 */
	public static void setConverterAttribute(Object object, String name, String value, ChiyaConverter chiyaConverter) {
		// 查找属性
		Field field = findAttribute(object.getClass(), name);
		if (field == null) { return; }
		// 查找第一个找到的属性
		try {
			Method method = object.getClass().getMethod(toSetMethod(name), field.getType());
			// 存在set方法，则使用set方法进行注入
			method.invoke(object, chiyaConverter.get(field.getType()).get(value));
		} catch (Exception e) {}
		// 如果没有set方法
		changeField(field, object, chiyaConverter.get(field.getType()).get(value));
	}

	/**
	 * 修改这个对象的属性
	 * 
	 * @param object 要修改的对象
	 * @param name   修改的字段
	 * @param value  值
	 */
	public static void setConverterAttribute(Object object, String name, String value) {
		setConverterAttribute(object, name, value, ConverterUtil.CHIYA_CONVERTER);
	}

}
