package chiya.core.base.function;

import java.util.List;

/**
 * 返回List的方法
 * 
 * @author brian
 */
@FunctionalInterface
public interface ListGenericNoParamFunction<T> {

	/**
	 * 获取并返回一个list
	 * 
	 * @return List对象
	 */
	List<T> getList();
}
