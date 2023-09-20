package chiya.core.base.object;

import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.function.Converter;

/**
 * 数据类型转换工具
 * 
 * @author chiya
 *
 */
public class ChiyaConverter {

	/** 类型转换缓存 */
	private ConcurrentHashMap<Class<?>, Converter<?>> cache = new ConcurrentHashMap<>();

	/**
	 * 获取类型转换器，如果转换的类型不存在，则会返回一个转换为null的转换器
	 * 
	 * @param <T>     获取的类型转换器
	 * @param classer 类型
	 * @return 转换器
	 */
	@SuppressWarnings("unchecked")
	public <T> Converter<T> get(Class<T> classer) {
		return (Converter<T>) cache.getOrDefault(classer, str -> null);
	}

	/**
	 * 链式添加类型转换器
	 * 
	 * @param <T>       类型
	 * @param classer   类型
	 * @param converter 赚钱
	 * @return 自身
	 */
	public <T> ChiyaConverter addConverter(Class<T> classer, Converter<T> converter) {
		cache.put(classer, converter);
		return this;
	}

}
