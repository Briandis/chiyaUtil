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
	 * 对象数组转字符
	 * 
	 * @param message 对象数组
	 * @return 字符串
	 */
	private static String messageString(Object... message) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Object object : message) {
			if (object != null) { stringBuilder.append(object.toString()); }
		}
		return stringBuilder.toString();
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
	 * 检查点级别
	 * 
	 * @param message 消息
	 */
	public static void point(Object... message) {
		HANDLE_LOG.point(messageString(message));
	}

	/**
	 * 消息级别
	 * 
	 * @param message 消息
	 */
	public static void info(Object message) {
		HANDLE_LOG.info(message);

	}

	/**
	 * 消息级别
	 * 
	 * @param message 消息
	 */
	public static void info(Object... message) {
		HANDLE_LOG.info(messageString(message));

	}

	/**
	 * 警告级别
	 * 
	 * @param message 消息
	 */
	public static void warn(Object message) {
		HANDLE_LOG.warn(message);

	}

	/**
	 * 警告级别
	 * 
	 * @param message 消息
	 */
	public static void warn(Object... message) {
		HANDLE_LOG.warn(messageString(message));

	}

	/**
	 * 错误级别
	 * 
	 * @param message 消息
	 */
	public static void error(Object message) {
		HANDLE_LOG.error(message);
	}

	/**
	 * 错误级别
	 * 
	 * @param message 消息
	 */
	public static void error(Object... message) {
		HANDLE_LOG.error(messageString(message));
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
