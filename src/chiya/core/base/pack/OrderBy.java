package chiya.core.base.pack;

/**
 * 
 * @author chiya
 *
 */
public class OrderBy {

	/** 字段名称 */
	private String fieldName;
	/** 排序类型 */
	private String type;
	/** 降序 */
	public static final String DESC = "DESC";
	/** 升序 */
	public static final String ASC = "ASC";

	/** 私有化构造方法 */
	private OrderBy() {}

	/**
	 * 更改降序
	 * 
	 * @return 对象
	 */
	public OrderBy desc() {
		return chainType(DESC);
	}

	/**
	 * 更改升序
	 * 
	 * @return 对象
	 */
	public OrderBy asc() {
		return chainType(ASC);
	}

	/**
	 * 降序
	 * 
	 * @param fieldName 字段
	 * @return 对象
	 */
	public static OrderBy desc(String fieldName) {
		return new OrderBy().chainFieldName(fieldName).chainType(DESC);
	}

	/**
	 * 升序
	 * 
	 * @param fieldName 字段
	 * @return 对象
	 */
	public static OrderBy asc(String fieldName) {
		return new OrderBy().chainFieldName(fieldName).chainType(ASC);
	}

	/**
	 * 获取字段名称
	 * 
	 * @return 字段名称
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置字段名称
	 * 
	 * @param fieldName 字段名称
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 链式添加字段名称
	 * 
	 * @param fieldName 字段名称
	 * @return 对象本身
	 */
	public OrderBy chainFieldName(String fieldName) {
		setFieldName(fieldName);
		return this;
	}

	/**
	 * 获取排序类型
	 * 
	 * @return 排序类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置排序类型
	 * 
	 * @param type 排序类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 链式添加排序类型
	 * 
	 * @param type 排序类型
	 * @return 对象本身
	 */
	public OrderBy chainType(String type) {
		setType(type);
		return this;
	}

}
