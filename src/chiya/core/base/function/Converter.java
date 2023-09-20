package chiya.core.base.function;

/**
 * 类型转换接口
 * 
 * @author chiya
 *
 * @param <T> 参数类型
 */
public interface Converter<T> {

	/**
	 * 转换对象未对应类型
	 * 
	 * @param string 字符串原始数据
	 * @return 对应的对象类型数据
	 */
	T get(String string);
}
