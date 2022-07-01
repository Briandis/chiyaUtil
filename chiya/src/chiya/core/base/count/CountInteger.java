package chiya.core.base.count;

/**
 * int计数对象，不保证多线程下的安全<br>
 * 该对象是用于函数式中的数值修改
 * 
 * @author brian
 */
public class CountInteger {

	/** 计数 */
	private int count = 0;

	/**
	 * 获取数值后+1
	 * 
	 * @return 自增前的数值
	 */
	public int getAndIncrement() {
		count++;
		return count - 1;
	}

	/**
	 * +1后并获取
	 * 
	 * @return 自增后的数值
	 */
	public int IncrementAndGet() {
		return ++count;
	}

	/**
	 * 重置计数器
	 */
	public void reset() {
		count = 0;
	}

	/**
	 * 获取数组
	 * 
	 * @return 计数器的数值
	 */
	public int get() {
		return count;
	}

	/**
	 * 设置一个数值
	 * 
	 * @param i 要数值的数值
	 */
	public void set(int i) {
		this.count = i;
	}

}
