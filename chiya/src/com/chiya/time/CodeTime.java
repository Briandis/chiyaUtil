package com.chiya.time;

/**
 * 程序性能时间库
 * 
 * @author Brian
 *
 */
public class CodeTime {
	private long startTime;
	private long endTime;

	/**
	 * 记录开始时间
	 */
	public void start() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * 记录结束时间
	 */
	public long end() {
		endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	/**
	 * 结束并打印
	 */
	public void endAndPrint() {
		endTime = System.currentTimeMillis();
		print();
	}

	/**
	 * 打印
	 */
	public void print() {
		System.out.println("start:" + startTime + "\tned:" + endTime + "\tuse:" + (endTime - startTime));
	}

	/**
	 * 获取代码所用时间
	 * 
	 * @return long:所用毫秒
	 */
	public long getUse() {
		return endTime - startTime;
	}
}
