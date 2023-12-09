package chiya.core.base.number;

/**
 * 比较器
 * 
 * @author chiya
 *
 */
public class CompareNumber<T extends Number> {
	/** 最小值 */
	private T min;
	/** 最大值 */
	private T max;
	/** 初始化 */
	private boolean init = true;

	/**
	 * 比较
	 * 
	 * @param number 数值
	 */
	public void compare(T number) {
		if (init) {
			min = number;
			max = number;
			init = false;
		}
		if (number.floatValue() < min.floatValue()) { min = number; }
		if (number.floatValue() > max.floatValue()) { max = number; }
	}

	/**
	 * 最小值
	 * 
	 * @return 最小值
	 */
	public T getMin() {
		return min;
	}

	/**
	 * 最大值
	 * 
	 * @return 最大值
	 */
	public T getMax() {
		return max;
	}

}
