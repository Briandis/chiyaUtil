package chiya.log;

import chiya.log.config.LogConfig;
import chiya.log.handle.HandleLog;

/**
 * 日志工具
 */
public class ChiyaLog {

	/** 开发输出 */
	public static final HandleLog HANDLE_LOG = new HandleLog();

	static {
		Runtime.getRuntime().addShutdownHook(
			new Thread(() -> {
				HANDLE_LOG.shutdown();
			})
		);
	}

	/**
	 * 检查点级别
	 * 
	 * @param message 消息
	 */
	public static void point(Object message) {
		HANDLE_LOG.point(message);
	}

	/**
	 * 消息级别
	 * 
	 * @param message 消息
	 */
	public static void info(Object message) {
		HANDLE_LOG.point(message);

	}

	/**
	 * 警告级别
	 * 
	 * @param message 消息
	 */
	public static void warn(Object message) {
		HANDLE_LOG.point(message);

	}

	/**
	 * 错误级别
	 * 
	 * @param message 消息
	 */
	public static void error(Object message) {
		HANDLE_LOG.point(message);
	}

	/**
	 * 获取日志配置
	 * 
	 * @return 日志配置
	 */
	public static LogConfig getConfig() {
		return HANDLE_LOG.logConfig;
	}
}
