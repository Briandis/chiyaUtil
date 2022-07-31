package chiya.core.base.function;

/**
 * 获取自定义类型的值
 * 
 * @author brain
 * @param <V> 要获取的类型泛型
 */
@FunctionalInterface
public interface GetValueFunction<V> {

	/**
	 * 获取值
	 * 
	 * @return 泛型值
	 */
	V getValue();

}
