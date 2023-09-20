package chiya.core.base.function;

/**
 * 
 * 通用泛型返回类型接口
 * 
 * @author brain
 * @param <T> 传入的对象类型
 * @param <V> 获取的值类型
 */
@FunctionalInterface
public interface GenericGenericFunction<T, V> {

	/**
	 * 
	 * @param object 要获取唯一标识的对象
	 * @return 唯一标识特征
	 */
	V get(T object);

}
