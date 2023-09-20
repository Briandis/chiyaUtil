package chiya.core.base.function;

/**
 * int递增迭代
 * 
 * @author brain
 *
 */
@FunctionalInterface
public interface VoidIntegerFunction {

	/**
	 * 循环体
	 * 
	 * @param index 当前迭代下标，一般从0开始
	 */
	void loop(int index);
}
