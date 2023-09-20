package chiya.core.base.function;

/**
 * 迭代接口
 * 
 * @author brian
 * @param <T> 泛型对象
 */
@FunctionalInterface
public interface ForEachFunction<T> {

	/**
	 * 迭代下一个的方法
	 * 
	 * @param object 迭代的对象
	 * @param index  当前下标
	 */
	void next(T object, int index);
}
