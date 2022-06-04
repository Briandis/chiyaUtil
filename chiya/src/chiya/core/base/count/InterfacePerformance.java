package chiya.core.base.count;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.thread.ThreadUtil;
import chiya.core.base.time.DateUtil;

/**
 * 接口性能统计
 * 
 * @author brain
 *
 */
public class InterfacePerformance {
	/** 计数器 */
	private ConcurrentHashMap<String, ServiceCount> count = new ConcurrentHashMap<String, ServiceCount>();

	/**
	 * 添加接口这次的运行时间
	 * 
	 * @param url  接口
	 * @param time 运行时间
	 */
	public void put(String url, int time) {
		ThreadUtil.conditionLock(
			() -> !count.containsKey(url),
			count,
			() -> count.put(url, new ServiceCount())
		);
		count.get(url).add(time);
	}

	/**
	 * 情况数据内容
	 */
	public void clear() {
		count.clear();
	}

	/**
	 * 重置当前所有统计
	 */
	public void reset() {
		count.forEach((k, v) -> v.clear());
	}

	/**
	 * 获取接口数据报表
	 * 
	 * @return List<InterfaceReport> 性能报告列表
	 */
	public List<InterfaceReport> getReport() {
		return report(count);
	}

	/**
	 * 获取当前接口统计，并且清空<br>
	 * 可以无缝导出并且不受导入时长影响后续统计
	 * 
	 * @return List<InterfaceReport> 性能报告列表
	 */
	public List<InterfaceReport> getReportAndClear() {
		ConcurrentHashMap<String, ServiceCount> old = count;
		// 使用新的替代
		count = new ConcurrentHashMap<String, ServiceCount>();
		return report(old);
	}

	/**
	 * 生成报表
	 * 
	 * @param concurrentHashMap 迭代的存储序列
	 * @return List<InterfaceReport> 性能报告列表
	 */
	public static List<InterfaceReport> report(ConcurrentHashMap<String, ServiceCount> concurrentHashMap) {
		String time = DateUtil.getNowDate();
		List<InterfaceReport> list = new ArrayList<InterfaceReport>();
		// 使用新的替代
		concurrentHashMap.forEach((k, v) -> {
			InterfaceReport interfaceReport = new InterfaceReport();
			interfaceReport.setName(k);
			interfaceReport.setReportTime(time);
			interfaceReport.setAllTime(v.getAllTime().intValue());
			interfaceReport.setCount(v.getCount().intValue());
			interfaceReport.setAverageTime(v.averageTime());
			list.add(interfaceReport);
		});
		return list;
	}

}
