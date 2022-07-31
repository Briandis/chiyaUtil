package chiya.core.base.function;

/**
 * 泛型获取对象
 * 
 * @author brain
 * @param <T> 泛型
 *
 */
@FunctionalInterface
public interface GenericityFunction<T> {

	/**
	 * 获取下一个泛型对象
	 * 
	 * @param t 泛型对象
	 */
	void next(T t);
}
