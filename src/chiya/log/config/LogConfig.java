package chiya.log.config;

/**
 * 日志配置
 */
public class LogConfig {
	/** 启用 */
	private volatile boolean enableLog = true;
	/** 控制台输出 */
	private volatile boolean consoleLog = true;
	/** 文件输出 */
	private volatile boolean fileLog = false;
	/** 文件输出路径 */
	private String path = "logs/";
	/** 日志保存天数 */
	private int saveDay = 365;
	/** 自动清理日志文件 */
	private volatile boolean needAutoClaerField = false;

	/**
	 * 获取日志保存天数
	 * 
	 * @return 日志保存天数
	 */
	public int getSaveDay() {
		return saveDay;
	}

	/**
	 * 设置日志保存天数
	 * 
	 * @param saveDay 日志保存天数
	 */
	public void setSaveDay(int saveDay) {
		this.saveDay = saveDay;
	}

	/**
	 * 是否需要自动清理日志
	 * 
	 * @return true:是/false:否
	 */
	public boolean isNeedAutoClaerField() {
		return needAutoClaerField;
	}

	/**
	 * 自动清理日志文件启用
	 * 
	 * @return 自身
	 */
	public LogConfig autoClaerFieldEnable() {
		needAutoClaerField = true;
		return this;
	}

	/**
	 * 自动清理日志文件禁用
	 * 
	 * @return 自身
	 */
	public LogConfig autoClaerFieldDisabled() {
		needAutoClaerField = false;
		return this;
	}

	/**
	 * 控制日志启用
	 * 
	 * @return 自身
	 */
	public LogConfig consoleEnable() {
		consoleLog = true;
		return this;
	}

	/**
	 * 控制日志禁用
	 * 
	 * @return 自身
	 */
	public LogConfig consoleDisabled() {
		consoleLog = false;
		return this;
	}

	/**
	 * 文件日志启用
	 * 
	 * @return 自身
	 */
	public LogConfig fileLogEnable() {
		fileLog = true;
		return this;
	}

	/**
	 * 文件志禁用
	 * 
	 * @return 自身
	 */
	public LogConfig fileLogDisabled() {
		fileLog = false;
		return this;
	}

	/**
	 * 日志功能启用
	 * 
	 * @return 自身
	 */
	public LogConfig enable() {
		enableLog = true;
		return this;
	}

	/**
	 * 日志功能禁用
	 * 
	 * @return 自身
	 */
	public LogConfig disabled() {
		enableLog = false;
		return this;
	}

	/**
	 * 获取日志是否启用
	 *
	 * @return enableLog
	 */
	public boolean isEnableLog() {
		return enableLog;
	}

	/**
	 * 获取是否输出控制台日志
	 *
	 * @return consoleLog
	 */
	public boolean isConsoleLog() {
		return consoleLog;
	}

	/**
	 * 获取是否输出文件日志
	 *
	 * @return fileLog
	 */
	public boolean isFileLog() {
		return fileLog;
	}

	/**
	 * 获取文件输出路径
	 *
	 * @return 文件输出路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置文件输出路径
	 *
	 * @param path 文件输出路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
