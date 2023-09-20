package chiya.core.base.count;

/**
 * 接口计数
 * 
 * @author chiya
 */
public class InterfaceCount {
	/** 接口计数 */
	private CountMap<String> countMap = new CountMap<String>();
	/** 全部 */
	private static final String ALL = "all";
	/** 成功的 */
	private static final String SUCCESS = "success";
	/** 失败的 */
	private static final String FAIL = "fail";

	/**
	 * 数量统计
	 * 
	 * @param b 接口执行状态
	 */
	public void increment(boolean b) {
		countMap.increment(ALL);
		countMap.increment(b ? SUCCESS : FAIL);
	}

	/**
	 * 重置
	 */
	public void reset() {
		countMap.reset();
	}

	/**
	 * 获取统计信息
	 * 
	 * @return 统计信息字符串
	 */
	public String getCountMsg() {
		return "最大访问：" + countMap.get(ALL) + "\t有效访问：" + countMap.get(SUCCESS) + "\t失败访问：" + countMap.get(FAIL);
	}

	/**
	 * 获取全部次数
	 * 
	 * @return 次数
	 */
	public long getAll() {
		return countMap.get(ALL);
	}

	/**
	 * 获取成功的次数
	 * 
	 * @return 次数
	 */
	public long getSuccess() {
		return countMap.get(SUCCESS);
	}

	/**
	 * 获取失败的次数
	 * 
	 * @return 次数
	 */
	public long getFail() {
		return countMap.get(FAIL);
	}
}
