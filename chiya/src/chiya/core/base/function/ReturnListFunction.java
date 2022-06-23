package chiya.core.base.function;

import java.util.List;

/**
 * 返回List的方法
 * 
 * @author brian
 */
public interface ReturnListFunction {

	/**
	 * 获取并返回一个list
	 * 
	 * @param <T> 泛型
	 * @return List对象
	 */
	<T> List<T> getList();
}
