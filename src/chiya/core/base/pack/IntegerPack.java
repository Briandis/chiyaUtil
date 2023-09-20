package chiya.core.base.pack;

/**
 * 数值包装
 * 
 * @author chiya
 *
 */
public class IntegerPack extends ObjectPack<Integer> {

	public IntegerPack() {
		setData(0);
	}

	/**
	 * 初始化方法
	 * 
	 * @param data 默认值
	 */
	public IntegerPack(int data) {
		setData(data);
	}

	/**
	 * 获取并增加多少
	 * 
	 * @param value 值
	 * @return 增加前的值
	 */
	public int getAndAdd(int value) {
		int temp = getData();
		setData(temp + value);
		return temp;
	}

	/**
	 * 增加并获取值
	 * 
	 * @param value 增加的值
	 * @return 增加后的值
	 */
	public int addAndGet(int value) {
		int temp = getData() + value;
		setData(temp);
		return temp;
	}

	/**
	 * 获取并自增
	 * 
	 * @return 自增前的值
	 */
	public int getAndIncrement() {
		return getAndAdd(1);
	}

	/**
	 * 自增并获取
	 * 
	 * @return 自增后的值
	 */
	public int incrementAndGet() {
		return addAndGet(1);
	}

	/**
	 * 获取并自减
	 * 
	 * @return 自减前的值
	 */
	public int getAndDecrement() {
		return getAndAdd(-1);
	}

	/**
	 * 自减并获取
	 * 
	 * @return 自减后的值
	 */
	public int decrementAndGet() {
		return addAndGet(-1);
	}

}
