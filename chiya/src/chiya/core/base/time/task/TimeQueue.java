package chiya.core.base.time.task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import chiya.core.base.collection.MapEnter;
import chiya.core.base.thread.ThreadUtil;

/**
 * 延时队列
 * 
 * @author chiya
 */
public class TimeQueue<K, V> {

	/** 延时队列 */
	private volatile DelayQueue<TimeKey<K>> delayQueue = new DelayQueue<TimeKey<K>>();
	/** 实际存储 */
	private ConcurrentHashMap<K, MapEnter<TimeKey<K>, V>> concurrentHashMap = new ConcurrentHashMap<K, MapEnter<TimeKey<K>, V>>();

	/** 执行自动超时列队 */
	private volatile boolean isRun = true;

	/** 处理任务 */
	private TimeTask<V> timeTask;

	/** 判断是否开始 */
	private Boolean isStart = false;

	/**
	 * 构造方法，需要传入任务体
	 * 
	 * @param timeTask 任务执法方法
	 */
	public TimeQueue(TimeTask<V> timeTask) {
		this.timeTask = timeTask;
	}

	/**
	 * 执行下一个任务后停止
	 */
	public void nextStop() {
		isRun = false;
		isStart = false;
	}

	/**
	 * 执行
	 * 
	 * @param isDaemon 是否是守护线程
	 */
	public void start(boolean isDaemon) {
		if (isStart) { throw new IllegalThreadStateException("该任务已经启动"); }
		synchronized (isStart) {
			if (isStart) { throw new IllegalThreadStateException("该任务已经启动"); }
			isStart = true;
		}
		isRun = true;
		if (isDaemon) {
			ThreadUtil.createDaemonAndStart(() -> task());
		} else {
			ThreadUtil.createAndStart(() -> task());
		}
	}

	/**
	 * 以守护进程方式启动
	 */
	public void start() {
		start(true);
	}

	/**
	 * 添加
	 * 
	 * @param key        键
	 * @param value      值
	 * @param createTime 创建时间
	 * @param timeOut    超时时间
	 */
	public void put(K key, V value, long createTime, long timeOut) {
		TimeKey<K> timeKey = new TimeKey<K>(key, createTime, timeOut);
		concurrentHashMap.put(key, new MapEnter<TimeKey<K>, V>(timeKey, value));
		delayQueue.add(timeKey);
	}

	/**
	 * 添加
	 * 
	 * @param key     键
	 * @param value   值
	 * @param timeOut 超时时间
	 */
	public void put(K key, V value, long timeOut) {
		put(key, value, System.currentTimeMillis(), timeOut);
	}

	/**
	 * 任务检测体，永远执行
	 */
	public void task() {
		while (isRun) {
			try {
				TimeKey<K> timeShell = delayQueue.take();
				timeTask.task(concurrentHashMap.remove(timeShell.getKey()).getValue());
			} catch (InterruptedException e) {
				timeTask.error(e);
			}
		}

	}

	/**
	 * 移除
	 * 
	 * @param key 键
	 * @return 信息对象
	 */
	public MapEnter<TimeKey<K>, V> remove(K key) {
		MapEnter<TimeKey<K>, V> mapEnter = concurrentHashMap.remove(key);
		if (mapEnter != null) { delayQueue.remove(mapEnter.getKey()); }
		return mapEnter;
	}

	/**
	 * 改变触发时间
	 * 
	 * @param key        键
	 * @param createTime 对象创建时间
	 * @param timeOut    延迟触发时间
	 */
	public void timeOutChange(K key, long createTime, long timeOut) {
		MapEnter<TimeKey<K>, V> mapEnter = concurrentHashMap.get(key);
		if (mapEnter != null) {
			delayQueue.remove(mapEnter.getKey());
			mapEnter.getKey().setCreateTime(createTime);
			mapEnter.getKey().setTimeOut(timeOut);
			delayQueue.add(mapEnter.getKey());
		}
	}

	/**
	 * 改变触发时间，基于当前时间戳延迟多少
	 * 
	 * @param key     键
	 * @param timeOut 延迟触发时间
	 */
	public void timeOutChange(K key, long timeOut) {
		timeOutChange(key, System.currentTimeMillis(), timeOut);
	}

	/**
	 * 添加或修改
	 * 
	 * @param key     键
	 * @param value   值
	 * @param timeOut 超时时间
	 */
	public void putOrChange(K key, V value, long timeOut) {
		MapEnter<TimeKey<K>, V> mapEnter = concurrentHashMap.get(key);
		if (mapEnter != null) {
			timeOutChange(key, timeOut);
		} else {
			put(key, value, timeOut);
		}
	}
}
