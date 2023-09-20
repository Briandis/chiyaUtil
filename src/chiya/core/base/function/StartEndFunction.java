package chiya.core.base.function;

/**
 * 起始和结束的值传递的类
 */
@FunctionalInterface
public interface StartEndFunction {

	/**
	 * 传递起始和结束的值
	 * 
	 * @param start 起始值
	 * @param end   结束的值
	 */
	void apply(int start, int end);
}
