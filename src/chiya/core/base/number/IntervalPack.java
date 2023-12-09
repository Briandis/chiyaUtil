package chiya.core.base.number;

/**
 * 区间
 * 
 * @author chiya
 *
 */
public class IntervalPack {

	/** 开始 */
	private int start = 0;
	/** 结束 */
	private int end = 0;

	/**
	 * 获取开始
	 * 
	 * @return 开始
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 设置开始
	 * 
	 * @param start 开始
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 链式添加开始
	 * 
	 * @param start 开始
	 * @return 对象本身
	 */
	public IntervalPack chainStart(int start) {
		setStart(start);
		return this;
	}

	/**
	 * 获取结束
	 * 
	 * @return 结束
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * 设置结束
	 * 
	 * @param end 结束
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * 链式添加结束
	 * 
	 * @param end 结束
	 * @return 对象本身
	 */
	public IntervalPack chainEnd(int end) {
		setEnd(end);
		return this;
	}

}
