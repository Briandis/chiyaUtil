package chiya.core.base.number;

/**
 * 比较器
 * 
 * @author chiya
 *
 */
public class CompareInteger {
	/** 最小值 */
	private int min = 0;
	/** 最大值 */
	private int max = 0;
	/** 初始化 */
	private boolean init = true;

	/**
	 * 比较
	 * 
	 * @param number 数值
	 */
	public void compare(int number) {
		if (init) {
			min = number;
			max = number;
			init = false;
		}
		if (number < min) { min = number; }
		if (number > max) { max = number; }
	}

	/**
	 * 最小值
	 * 
	 * @return 最小值
	 */
	public int getMin() {
		return min;
	}

	/**
	 * 最大值
	 * 
	 * @return 最大值
	 */
	public int getMax() {
		return max;
	}

	/**
	 * 差值
	 * 
	 * @return max-min的值
	 */
	public int difference() {
		return max - min;
	}
}
