package chiya.core.base.collection.recursion;

/**
 * 递归方法接口
 * 
 * @author chiya
 * @param <T>
 *
 */
@FunctionalInterface
public interface RecursionMethod<T> {
	/**
	 * 递归方法
	 * 
	 * @param <T>            递归传入的数据类型
	 * @param recursionStack 迭代时当前迭代器信息
	 * @param data           当前递归传入的数据
	 */
	void recursion(RecursionStack<T> recursionStack, T data);
}
