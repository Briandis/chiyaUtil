package chiya.core.base.cache;

import chiya.core.base.function.ValueGetFunction;

/**
 * key-value结构缓存，没有回收机制，主要场景是少量不改变的数据
 * 
 * @author brain
 * @param <K> 唯一标识的类型
 * @param <V> 传入存储的对象类型
 */
public class MapCache<K, V> extends BaseKeyValueCache<K, V> {

	public MapCache(ValueGetFunction<V, K> valueGetFunction) {
		super(valueGetFunction);
	}

}
