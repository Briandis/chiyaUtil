package chiya.script.tree;

import chiya.core.base.function.VoidGenericFunction;
import chiya.script.token.ChiyaToken;

/**
 * 赋值树
 * 
 * @author chiya
 *
 */
public class AssignmentSyntaxTree {
	/** 语法树赋值方法 */
	private VoidGenericFunction<AbstractSyntaxTree> assignmentFunction;
	/** 需要进一步解析的token */
	private ChiyaToken nextToken;

	/**
	 * 赋值
	 * 
	 * @param abstractSyntaxTree 抽象语法树
	 */
	public void assignment(Object object) {
		if (object != null && assignmentFunction != null) {
			if (object instanceof AbstractSyntaxTree) {
				// 对应类型才转换
				assignmentFunction.execute((AbstractSyntaxTree) object);
			}
		}
	}

	/**
	 * 赋值
	 * 
	 * @param abstractSyntaxTree 抽象语法树
	 */
	public void assignment(AbstractSyntaxTree abstractSyntaxTree) {
		if (assignmentFunction != null) { assignmentFunction.execute(abstractSyntaxTree); }
	}

	/**
	 * 获取语法树赋值方法
	 * 
	 * @return 语法树赋值方法
	 */
	public VoidGenericFunction<AbstractSyntaxTree> getAssignmentFunction() {
		return assignmentFunction;
	}

	/**
	 * 设置语法树赋值方法
	 * 
	 * @param assignmentFunction 语法树赋值方法
	 */
	public void setAssignmentFunction(VoidGenericFunction<AbstractSyntaxTree> assignmentFunction) {
		this.assignmentFunction = assignmentFunction;
	}

	/**
	 * 链式添加语法树赋值方法
	 * 
	 * @param assignmentFunction 语法树赋值方法
	 * @return 对象本身
	 */
	public AssignmentSyntaxTree chainAssignmentFunction(VoidGenericFunction<AbstractSyntaxTree> assignmentFunction) {
		setAssignmentFunction(assignmentFunction);
		return this;
	}

	/**
	 * 获取需要进一步解析的token
	 * 
	 * @return 需要进一步解析的token
	 */
	public ChiyaToken getNextToken() {
		return nextToken;
	}

	/**
	 * 设置需要进一步解析的token
	 * 
	 * @param nextToken 需要进一步解析的token
	 */
	public void setNextToken(ChiyaToken nextToken) {
		this.nextToken = nextToken;
	}

	/**
	 * 链式添加需要进一步解析的token
	 * 
	 * @param nextToken 需要进一步解析的token
	 * @return 对象本身
	 */
	public AssignmentSyntaxTree chainNextToken(ChiyaToken nextToken) {
		setNextToken(nextToken);
		return this;
	}

}
