package chiya.core.base.function;

/**
 * 通用泛型返回类型接口
 * 
 * @author brain
 *
 * @param <V> 需要的泛型
 */
@FunctionalInterface
public interface GetUniqueFunction<V, O> {

	/**
	 * 
	 * @param object 要获取唯一标识的对象
	 * @return 唯一标识特征
	 */
	V task(O object);

}
