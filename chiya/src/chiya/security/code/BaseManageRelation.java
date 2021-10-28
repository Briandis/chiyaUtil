package chiya.security.code;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象的公共信息内容
 */
public abstract class BaseManageRelation {

	/**
	 * 需要更新的缓存信息
	 */
	protected final ConcurrentSkipListSet<Integer> updateSet = new ConcurrentSkipListSet<Integer>();

	/**
	 * 全部数据需要更新标记
	 */
	protected volatile boolean updateAllFlag = true;
	/**
	 * 同步锁
	 */
	protected final Lock lock = new ReentrantLock();

	/**
	 * 设置更新全部信息
	 */
	public final void updateAll() {
		updateAllFlag = true;
	}

	/**
	 * 设置更新的id
	 * 
	 * @param id 要更新的id
	 */
	public final void updateOne(Integer id) {
		updateSet.add(id);
	}

	/**
	 * 加载数据
	 */
	public final void loadAllDate() {
		if (updateAllFlag) {
			// 在多线程中，只要有一个线程更新数据即可，其他正常默认访问
			if (lock.tryLock()) {
				// 清空所有更新列队中的数据
				updateSet.clear();
				// 删除所有数据
				try {
					// 由子类负责加载数据
					reloadAllData();
					// 取消更新所有的标记
					updateAllFlag = false;
				} finally {
					lock.unlock();
				}
			}
		}
	}

	/**
	 * 更新部分数据
	 * 
	 * @param id 要更新的id
	 */
	public final void loadDate(Integer id) {
		// 如果要更新的数据在列队中
		if (updateSet.contains(id)) {
			if (lock.tryLock()) {
				try {
					// 由子类负责加载数据
					reloadDate(id);
					// 移除列队
					updateSet.remove(id);
				} finally {
					lock.unlock();
				}
			}
		}
	}

	/**
	 * 加载所有数据
	 */
	protected abstract void reloadAllData();

	/**
	 * 重写加载该id数据
	 * 
	 * @param id
	 */
	protected abstract void reloadDate(Integer id);

	/**
	 * 检查该id是否需要加载
	 * 
	 * @param id
	 */
	public final void checkReload(Integer id) {
		if (updateAllFlag) {
			// 如果需要全局更新，则直接全局更新
			loadAllDate();
		} else if (updateSet.contains(id)) {
			// 检查的部分id在更新列队中，则更新该id的数据
			loadDate(id);
		}
	}
}
