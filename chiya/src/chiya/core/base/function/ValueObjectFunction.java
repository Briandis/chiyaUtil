package chiya.core.base.function;

/**
 * 泛型接口，返回日期类型
 * 
 * @author brain
 *
 */
@FunctionalInterface
public interface ValueObjectFunction<T, V> {

	/**
	 * 获取并返回
	 * 
	 * @param object 传入的对象
	 * @return 该对象返回的值
	 */
	V get(T object);
}
