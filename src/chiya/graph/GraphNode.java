package chiya.graph;

/**
 * 图节点
 * 
 * @author chiya
 *
 */
public class GraphNode {
	/** X坐标 */
	private double indexX;
	/** Y坐标 */
	private double indexY;
	/** Z坐标 */
	private double indexZ;
	/** 节点名称 */
	private String name;

	/**
	 * 获取X坐标
	 * 
	 * @return X坐标
	 */
	public double getIndexX() {
		return indexX;
	}

	/**
	 * 设置X坐标
	 * 
	 * @param indexX X坐标
	 */
	public void setIndexX(double indexX) {
		this.indexX = indexX;
	}

	/**
	 * 链式添加X坐标
	 * 
	 * @param indexX X坐标
	 * @return 对象本身
	 */
	public GraphNode chainIndexX(double indexX) {
		setIndexX(indexX);
		return this;
	}

	/**
	 * 获取Y坐标
	 * 
	 * @return Y坐标
	 */
	public double getIndexY() {
		return indexY;
	}

	/**
	 * 设置Y坐标
	 * 
	 * @param indexY Y坐标
	 */
	public void setIndexY(double indexY) {
		this.indexY = indexY;
	}

	/**
	 * 链式添加Y坐标
	 * 
	 * @param indexY Y坐标
	 * @return 对象本身
	 */
	public GraphNode chainIndexY(double indexY) {
		setIndexY(indexY);
		return this;
	}

	/**
	 * 获取Z坐标
	 * 
	 * @return Z坐标
	 */
	public double getIndexZ() {
		return indexZ;
	}

	/**
	 * 设置Z坐标
	 * 
	 * @param indexZ Z坐标
	 */
	public void setIndexZ(double indexZ) {
		this.indexZ = indexZ;
	}

	/**
	 * 链式添加Z坐标
	 * 
	 * @param indexZ Z坐标
	 * @return 对象本身
	 */
	public GraphNode chainIndexZ(double indexZ) {
		setIndexZ(indexZ);
		return this;
	}

	/**
	 * 获取节点名称
	 * 
	 * @return 节点名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置节点名称
	 * 
	 * @param name 节点名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 链式添加节点名称
	 * 
	 * @param name 节点名称
	 * @return 对象本身
	 */
	public GraphNode chainName(String name) {
		setName(name);
		return this;
	}

}
