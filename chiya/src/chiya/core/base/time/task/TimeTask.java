package chiya.core.base.time.task;

/**
 * 延时任务处理
 * 
 * @author chiya
 * @param <V> 处理的类
 */
@FunctionalInterface
public interface TimeTask<V> {

	/**
	 * 任务体
	 * 
	 * @param value 传入的对象
	 */
	void task(V value);

	/**
	 * 错误处理
	 * 
	 * @param e 错误信息
	 */
	default void error(InterruptedException e) {}
}
