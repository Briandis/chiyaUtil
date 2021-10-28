package chiya.core.base.time;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * IP锁工具库
 * 
 * @author Brian
 *
 */
public class BanIPUtil {

	/**
	 * 全局统一
	 */
	private BanIPUtil() {}

	/**
	 * 1s种超过25次则封禁
	 */
	private static CountTimeUtil countTimeUtil = new CountTimeUtil(25);
	private static ConcurrentHashMap<String, Long> banIPMap = new ConcurrentHashMap<String, Long>();
	/**
	 * 默认封禁时间
	 */
	private static int banTime = 1000 * 60 * 60 * 24;
	/**
	 * 过期自动回收锁
	 */
	private static Lock lock = new ReentrantLock();
	/**
	 * 最后清理时间
	 */
	private volatile static long lastTIme = System.currentTimeMillis();
	/**
	 * 默认间隔，回收机制才用某个业务线程中断回收后执行业务
	 */
	private static int timeInterval = 1000 * 60 * 5;

	/**
	 * 检查IP是规定次数内，以及是否封禁
	 * 
	 * @param ip 待测IP
	 * @return true:被封禁/false:未被封禁
	 */
	public static boolean check(String ip) {
		long nowTime = System.currentTimeMillis();
		// 优先执行回收任务
		if (lastTIme + timeInterval > nowTime) {
			if (lock.tryLock()) {
				autoRemoveBanIp();
				lock.unlock();
			}
		}
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
	 * 自动扫描移除过期封禁IP
	 */
	public static void autoRemoveBanIp() {
		long nowTime = System.currentTimeMillis();
		banIPMap.entrySet().removeIf(entry -> entry.getValue() + banTime < nowTime);
		lastTIme = System.currentTimeMillis();
	}

	/**
	 * 移除封禁的IP
	 * 
	 * @param ip 待移除IP
	 */
	public static void remove(String ip) {
		banIPMap.remove(ip);
	}

	/**
	 * 以固定时间封禁某IP
	 * 
	 * @param ip   待封禁IP
	 * @param time 封禁时间
	 */
	public static void banIP(String ip, long time) {
		banIPMap.put(ip, time);
	}

	/**
	 * 以默认时间封禁IP
	 * 
	 * @param ip 待封禁IP
	 */
	public static void banIp(String ip) {
		banIPMap.put(ip, (long) banTime);
	}

}
