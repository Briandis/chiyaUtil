package chiya.script.tree;

/**
 * 任意类型默认实现类
 * 
 * @author chiya
 *
 */
public class AbstractValue extends AbstractSyntaxTree {

	/** 值 */
	private String value;
	/** 默认支持类型 */
	private String type = "any";

	/** 默认构造方法 */
	public AbstractValue() {}

	/** 默认构造方法 */
	public AbstractValue(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return type;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value 值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 链式添加值
	 * 
	 * @param value 值
	 * @return 对象本身
	 */
	public AbstractValue chainValue(String value) {
		setValue(value);
		return this;
	}

	@Override
	public void format(StringBuilder stringBuilder) {
		stringBuilder.append(value);
	}
}
