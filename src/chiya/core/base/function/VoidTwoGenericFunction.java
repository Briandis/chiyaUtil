package chiya.core.base.function;

/**
 * 任务处理
 * 
 * @author chiya
 * @param <V> 泛型1
 * @param <T> 泛型2
 */
@FunctionalInterface
public interface VoidTwoGenericFunction<V, T> {

	/**
	 * 任务体
	 * 
	 * @param value 传入的对象
	 */
	void execute(V value, T data);

	/**
	 * 错误处理
	 * 
	 * @param e 错误信息
	 */
	default void error(Exception e) {
		throw new RuntimeException(e);
	}
}
