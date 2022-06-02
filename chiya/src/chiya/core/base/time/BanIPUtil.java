package chiya.core.base.time;

import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.gc.GarbageCollection;

/**
 * IP锁工具库
 * 
 * @author chiya
 */
public class BanIPUtil {

	/** 计数工具，1s种超过25次则封禁 */
	private CountTimeUtil countTimeUtil;
	/** 封禁的IP */
	private ConcurrentHashMap<String, Long> banIPMap;
	/** 默认封禁时间 */
	private int banTime = 1000 * 60 * 60 * 24;
	/** 垃圾回收期 */
	private GarbageCollection garbageCollection;

	/**
	 * 构造方法
	 */
	public BanIPUtil() {
		garbageCollection = new GarbageCollection(() -> {
			long nowTime = System.currentTimeMillis();
			banIPMap.entrySet().removeIf(entry -> entry.getValue() + banTime < nowTime);
		});
		banIPMap = new ConcurrentHashMap<String, Long>();
		countTimeUtil = new CountTimeUtil(25);
	}

	/**
	 * 检查IP是规定次数内，以及是否封禁
	 * 
	 * @param ip 待测IP
	 * @return true:被封禁/false:未被封禁
	 */
	public boolean check(String ip) {
		long nowTime = System.currentTimeMillis();
		// 优先执行回收任务
		garbageCollection.recycle();

		if (banIPMap.get(ip) != null) {
			// 如果IP封禁已过期则移除，否则直接返回
			if (banIPMap.get(ip) + banTime < nowTime) {
				banIPMap.remove(ip);
			} else {
				return true;
			}
		}
		// 次数超出取反
		boolean b = !countTimeUtil.put(ip);
		// 如果超出次数，则直接封禁IP
		if (b) {
			// 已被封禁则直接移除IP计数器，节省空间，提高效率
			countTimeUtil.remove(ip);
			banIp(ip);
		}
		return b;

	}

	/**
	 * 移除封禁的IP
	 * 
	 * @param ip 待移除IP
	 */
	public void remove(String ip) {
		banIPMap.remove(ip);
	}

	/**
	 * 以固定时间封禁某IP
	 * 
	 * @param ip   待封禁IP
	 * @param time 封禁时间
	 */
	public void banIP(String ip, long time) {
		banIPMap.put(ip, time);
	}

	/**
	 * 以默认时间封禁IP
	 * 
	 * @param ip 待封禁IP
	 */
	public void banIp(String ip) {
		banIPMap.put(ip, (long) banTime);
	}

}
