package chiya.log.handle.base;

import chiya.core.base.thread.ThreadUtil;
import chiya.core.base.time.CodeTime;
import chiya.core.base.time.NowTime;
import chiya.log.cache.LogCache;
import chiya.log.config.LogConfig;
import chiya.log.write.LogWrite;

public abstract class BaseLog implements LogLevel {

	/** 日志配置 */
	public LogConfig logConfig = new LogConfig();

	/**
	 * 默认构造方法
	 */
	public BaseLog() {
		logCache.start();
	}

	/** 日志缓存 */
	private LogCache logCache = new LogCache(log -> {
		if (logConfig.isConsoleLog()) { LogWrite.console(log); }
		if (logConfig.isFileLog()) { LogWrite.write(logConfig.getPath(), log); }
	});

	/**
	 * 系统终止，则立即写入
	 */
	public void shutdown() {
		LogWrite.writeNow(logConfig.getPath());
	}

	/**
	 * 日志等级<br>
	 * 0：检查点<br>
	 * 1：消息<br>
	 * 2：警告<br>
	 * 3：错误<br>
	 */
	private static final String LEVEL[] = { "POINT", "INFO", "WARN", "ERROR" };

	/**
	 * 获取日志等级
	 * 
	 * @param index 等级下标
	 * @return 日志等级
	 */
	protected String getLevel(int index) {
		return LEVEL[index];
	}

	/**
	 * 日志记录
	 * 
	 * @param level   日志级别
	 * @param message 消息
	 */
	public void log(String level, Object message) {
		if (logConfig.isEnableLog()) {
			// 日志是否启用
			log(level, message == null ? "" : message.toString());
		}
	}

	/** 缩进字符 */
	private static final String T = "\t";

	/**
	 * 构建日志消息
	 * 
	 * @param message 日志内容
	 * @return 日志处理
	 */
	protected String createMessage(String level, String message) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append(NowTime.getFullDateTime()).append(T)
			.append(level).append(T)
			.append(CodeTime.getRunTime()).append(T)
			.append(ThreadUtil.getThreadName()).append(T)
			.append(message).append("\n");
		return stringBuilder.toString();
	}

	/**
	 * 日志记录
	 * 
	 * @param level   级别
	 * @param message 消息
	 */
	protected void log(String level, String message) {
		String str = createMessage(level, message);
		logCache.add(str);
	}
}
