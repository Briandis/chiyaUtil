package chiya.log.handle.base;

public interface LogLevel {
	/**
	 * 检查点级别
	 * 
	 * @param message 消息
	 */
	public void point(Object message);

	/**
	 * 普通级别
	 * 
	 * @param message 消息
	 */
	public void info(Object message);

	/**
	 * 警告级别
	 * 
	 * @param message 消息
	 */
	public void warn(Object message);

	/**
	 * 错误
	 * 
	 * @param message 消息
	 */
	public void error(Object message);
}
