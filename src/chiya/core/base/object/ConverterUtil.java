package chiya.core.base.object;

import java.util.Date;

import chiya.core.base.function.Converter;
import chiya.core.base.number.NumberUtil;
import chiya.core.base.string.StringUtil;
import chiya.core.base.time.DateUtil;

/**
 * 类型转换工具
 * 
 * @author chiya
 *
 */
public class ConverterUtil {
	/** 私有化构造方法 */
	private ConverterUtil() {}

	/** 类型转换器 */
	public static final ChiyaConverter CHIYA_CONVERTER = new ChiyaConverter();

	static {
		CHIYA_CONVERTER
			// 整数
			.addConverter(int.class, str -> NumberUtil.parseInt(str))
			.addConverter(long.class, str -> NumberUtil.parseLong(str))
			.addConverter(Integer.class, str -> NumberUtil.parseIntOrNull(str))
			.addConverter(Long.class, str -> NumberUtil.parseLongOrNull(str))
			// 字符串
			.addConverter(String.class, str -> str)
			// 小数
			.addConverter(float.class, str -> NumberUtil.parseDouble(str).floatValue())
			.addConverter(double.class, str -> NumberUtil.parseDouble(str))
			.addConverter(Float.class, str -> NumberUtil.parseDouble(str).floatValue())
			.addConverter(Double.class, str -> NumberUtil.parseDouble(str))
			// 布尔类型
			.addConverter(boolean.class, str -> StringUtil.eqIgnoreCase(str, "true"))
			.addConverter(Boolean.class, str -> StringUtil.eqIgnoreCase(str, "true"))
			// 日期类型
			.addConverter(Date.class, str -> DateUtil.stringToDate(str));
	}

	/**
	 * 链式添加类型转换器
	 * 
	 * @param <T>       类型
	 * @param classer   类型
	 * @param converter 赚钱
	 * @return 自身
	 */
	public static <T> void addConverter(Class<T> classer, Converter<T> converter) {
		CHIYA_CONVERTER.addConverter(classer, converter);
	}

	/**
	 * 转换数据
	 * 
	 * @param <T>     转换类型
	 * @param classer 转换的类型
	 * @param string  转换的字符串
	 * @return 转换后的对象
	 */
	public static <T> T get(Class<T> classer, String string) {
		return CHIYA_CONVERTER.get(classer).get(string);
	}

}
