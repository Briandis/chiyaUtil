package chiya.core.base.cache;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import chiya.core.base.function.ReturnListFunction;
import chiya.core.base.function.ValueGetFunction;
import chiya.core.base.thread.ThreadUtil;

/**
 * key-value结构缓存，没有回收机制，主要场景是少量不改变的数据<br>
 * 包含一个读写锁
 * 
 * @author brain
 * @param <K> 唯一标识的类型
 * @param <V> 传入存储的对象类型
 */
public class MapLockCache<K, V> extends MapCache<K, V> {

	/** 读写锁 */
	protected ReentrantReadWriteLock reentrantReadWriteLock = null;

	/**
	 * 构造方法
	 * 
	 * @param valueGetFunction 获取对象唯一标识
	 */
	public MapLockCache(ValueGetFunction<V, K> valueGetFunction) {
		super(valueGetFunction);
		reentrantReadWriteLock = new ReentrantReadWriteLock();
	}

	/**
	 * 移除缓存中数据
	 * 
	 * @param kye 键
	 */
	@Override
	public void remove(K key) {
		ThreadUtil.lock(reentrantReadWriteLock.writeLock(), () -> super.remove(key));
	}

	/**
	 * 删除所有缓存
	 */
	@Override
	public void remove() {
		ThreadUtil.lock(reentrantReadWriteLock.writeLock(), () -> super.remove());
	}

	/**
	 * 添加对象
	 * 
	 * @param value 缓存数据
	 */
	@Override
	public void add(V value) {
		ThreadUtil.lock(reentrantReadWriteLock.writeLock(), () -> super.add(value));
	}

	/**
	 * 添加对象
	 * 
	 * @param key   缓存的key
	 * @param value 缓存数据
	 */
	@Override
	public void add(K key, V value) {
		ThreadUtil.lock(reentrantReadWriteLock.writeLock(), () -> super.add(key, value));
	}

	/**
	 * 添加列表进行缓存
	 * 
	 * @param list 列表
	 */
	@Override
	public void add(List<V> list) {
		ThreadUtil.lock(reentrantReadWriteLock.writeLock(), () -> super.add(list));
	}

	/**
	 * 获取一个对象
	 * 
	 * @param key 键
	 * @return Object 缓存的数据
	 */
	@Override
	public V get(K key) {
		return ThreadUtil.lock(reentrantReadWriteLock.readLock(), () -> super.get(key));
	}

	/**
	 * 获取全部缓存数据
	 * 
	 * @return List<V> 全部缓存的对象
	 */
	@Override
	public List<V> get() {
		return ThreadUtil.lock(reentrantReadWriteLock.readLock(), () -> super.get());
	}

	/**
	 * 获取指定范围的数据
	 * 
	 * @param list 唯一标识列表
	 * @return List<V> 找到的缓存对象
	 */
	@Override
	public List<V> get(Collection<K> list) {
		return ThreadUtil.lock(reentrantReadWriteLock.readLock(), () -> super.get(list));
	}

	/**
	 * 获取指定范围的数据
	 * 
	 * @param array 唯一标识列表
	 * @return List<V> 找到的缓存对象
	 */
	@Override
	public List<V> get(K[] array) {
		return ThreadUtil.lock(reentrantReadWriteLock.readLock(), () -> super.get(array));
	}

	/**
	 * 重新拉取全部数据
	 * 
	 * @param function 获取数据方法
	 */
	public void reacquire(ReturnListFunction<V> function) {
		ThreadUtil.doubleCheckLock(
			() -> isNeedReload(),
			reentrantReadWriteLock.writeLock(),
			() -> {
				remove();
				super.add(function.getList());
				notReload();
			}
		);
	}
}
