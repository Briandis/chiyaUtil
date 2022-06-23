package chiya.core.base.function;

/**
 * 布尔类型返回值的函数函数式方法
 * 
 * @author brain
 *
 */
@FunctionalInterface
public interface BooleanFunction {
	/**
	 * 执行某个方法，返回true/false
	 * 
	 * @return true/false
	 */
	boolean task();
}
