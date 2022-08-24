package chiya.log.write;

import chiya.core.base.io.FileUtil;
import chiya.core.base.string.ChiyaString;
import chiya.core.base.time.NowTime;
import chiya.log.config.LogConfig;

/**
 * 日志写入
 */
public class LogWrite {

	/** 写入的缓存 */
	private static ChiyaString chiyaString = new ChiyaString();
	/** 最后一次写入时间 */
	private static long lastWrite = System.currentTimeMillis();
	/** 当然累计日志行数 */
	private static long writeSize = 0;

	/**
	 * 写入日志
	 * 
	 * @param logConfig 日志配置
	 * @param log       日志
	 */
	public static void write(LogConfig logConfig, String log) {
		chiyaString.append(log);
		writeSize++;
		// 累计一定数量或者超出一定时间后就将日志写入文件
		if (writeSize > 1000 || lastWrite + 1000 < System.currentTimeMillis()) {
			FileUtil.appendText(chiyaString.toString(), logConfig.getPath(), NowTime.getDate() + ".log");
			chiyaString.clear();
			writeSize = 0;
			lastWrite = System.currentTimeMillis();
		}
	}

	/**
	 * 立即写入缓存的的数据
	 * 
	 * @param path 路径
	 */
	public static void writeNow(String path) {
		FileUtil.appendText(chiyaString.toString(), path, NowTime.getDate() + ".log");
		chiyaString.clear();
		lastWrite = System.currentTimeMillis();
	}

	/**
	 * 控制台输出
	 * 
	 * @param log 日志
	 */
	public static void console(String log) {
		System.out.print(log);
	}

}
