package chiya.core.base.cache;

/**
 * 基础缓存抽象类
 * 
 * @author brain
 */
public abstract class BaseCache {
	/** 缓存更新状态位 */
	private volatile boolean NEED_RELOAD = true;

	/**
	 * 是否需要更新
	 * 
	 * @return true：需要更新/false：不需要更新
	 */
	protected final boolean isNeedReload() {
		return NEED_RELOAD;
	}

	/**
	 * 需要更新
	 */
	public final void needReload() {
		NEED_RELOAD = true;
	}

	/**
	 * 不需要更新了
	 */
	protected final void notReload() {
		NEED_RELOAD = false;
	}

}
