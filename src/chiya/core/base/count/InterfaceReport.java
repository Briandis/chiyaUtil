package chiya.core.base.count;

/**
 * 接口性能报告实体
 * 
 * @author brain
 *
 */
public class InterfaceReport {

	/** 接口名称 */
	private String name;
	/** 接口访问次数 */
	private int count;
	/** 接口平均响应时间 */
	private double averageTime;
	/** 总业务响应时间 */
	private int allTime;
	/** 业务记录时间 */
	private String reportTime;

	/**
	 * 获取接口名称
	 * 
	 * @return 接口名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置接口名称
	 * 
	 * @param name 接口名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取统计的数量
	 * 
	 * @return 统计数量
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置访问次数
	 * 
	 * @param count 访问次数
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 获取业务平均响应时间
	 * 
	 * @return 平均响应时间
	 */
	public double getAverageTime() {
		return averageTime;
	}

	/**
	 * 设置业务平均响应时间
	 * 
	 * @param averageTime 平均响应时间
	 */
	public void setAverageTime(double averageTime) {
		this.averageTime = averageTime;
	}

	/**
	 * 获取全部业务响应时间
	 * 
	 * @return 全部业务响应时间
	 */
	public int getAllTime() {
		return allTime;
	}

	/**
	 * 设置业务总响应时间
	 * 
	 * @param allTime 总响应时间
	 */
	public void setAllTime(int allTime) {
		this.allTime = allTime;
	}

	/**
	 * 获取报告时间
	 * 
	 * @return 报告时间
	 */
	public String getReportTime() {
		return reportTime;
	}

	/**
	 * 设置报告时间
	 * 
	 * @param reportTime 报告时间
	 */
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("接口名称:").append(name)
			.append(" 访问次数:").append(count)
			.append(" 平均响应时间:").append(averageTime).append("ms")
			.append("累计总耗时:").append(allTime).append("ms")
			.append("报告时间").append(reportTime);
		return stringBuilder.toString();
	}
}
