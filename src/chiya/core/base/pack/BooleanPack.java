package chiya.core.base.pack;

/**
 * 布尔类型封装
 * 
 * @author chiya
 *
 */
public class BooleanPack extends ObjectPack<Boolean> {

	public BooleanPack() {
		setData(false);
	}

	/**
	 * 初始化方法
	 * 
	 * @param data 默认值
	 */
	public BooleanPack(boolean data) {
		setData(data);
	}

	/**
	 * 获取并取反
	 * 
	 * @return 值
	 */
	public boolean getAndNot() {
		boolean temp = getData();
		setData(!temp);
		return temp;
	}

}
