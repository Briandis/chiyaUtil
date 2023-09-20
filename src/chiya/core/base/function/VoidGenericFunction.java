package chiya.core.base.function;

/**
 * 任务处理
 * 
 * @author chiya
 * @param <V> 处理的类
 */
@FunctionalInterface
public interface VoidGenericFunction<V> {

	/**
	 * 任务体
	 * 
	 * @param value 传入的对象
	 */
	void execute(V value);

	/**
	 * 错误处理
	 * 
	 * @param e 错误信息
	 */
	default void error(Exception e) {}
}
