package chiya.script.tree;

/**
 * 抽象语法树
 * 
 * @author chiya
 *
 */
public abstract class AbstractSyntaxTree {

	/**
	 * 当前类型
	 * 
	 * @return 类型
	 */
	public abstract String getType();

	/**
	 * 格式化输出
	 * 
	 * @param stringBuilder 容器
	 */
	public abstract void format(StringBuilder stringBuilder);
}
