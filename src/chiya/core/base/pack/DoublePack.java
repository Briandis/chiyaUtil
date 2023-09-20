package chiya.core.base.pack;

/**
 * 双精度包装类
 * 
 * @author chiya
 *
 */
public class DoublePack extends ObjectPack<Double> {

	public DoublePack() {
		setData(0.0);
	}

	/**
	 * 初始化方法
	 * 
	 * @param data 默认值
	 */
	public DoublePack(double data) {
		setData(data);
	}

	/**
	 * 获取并增加多少
	 * 
	 * @param value 值
	 * @return 增加前的值
	 */
	public double getAndAdd(double value) {
		double temp = getData();
		setData(temp + value);
		return temp;
	}

	/**
	 * 增加并获取值
	 * 
	 * @param value 增加的值
	 * @return 增加后的值
	 */
	public double addAndGet(double value) {
		double temp = getData() + value;
		setData(temp);
		return temp;
	}

}
