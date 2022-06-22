package chiya.core.base.function;

/**
 * 泛型迭代接口
 * 
 * @author brain
 * @param <T> 要支持的泛型
 *
 */
public interface GenericityFunction<T> {

	/**
	 * 迭代下一个的方法
	 * 
	 * @param t 迭代参数
	 */
	void next(T t);
}
