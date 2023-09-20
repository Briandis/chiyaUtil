package chiya.script.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.collection.recursion.RecursionStack;
import chiya.core.base.function.VoidGenericFunction;
import chiya.script.token.ChiyaToken;
import chiya.script.tree.AbstractSyntaxTree;
import chiya.script.tree.AssignmentSyntaxTree;
import chiya.script.tree.BuildAbstractTree;

/**
 * 标准语法树解析
 * 
 * @author chiya
 *
 */
public class ChiyaSyntaxTreeParser {

	/** 构建规则的方法 */
	private HashMap<String, BuildAbstractTree> ruleMethod = new HashMap<>();
	/** 需要判断的队列 */
	private List<AssignmentSyntaxTree> needJudge = new ArrayList<>();
	/** 上下文 */
	private HashMap<String, Object> context = new HashMap<>();

	/**
	 * 生成一个上下文
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public void contextPush(String key, Object value) {
		context.put(key, value);
	}

	/**
	 * 获取上下文
	 * 
	 * @param key 键
	 * @return 值
	 */
	public Object contextGet(String key) {
		return context.get(key);
	}

	/**
	 * 获取上下文并移除
	 * 
	 * @param key 键
	 * @return 值
	 */
	public Object contextPop(String key) {
		return context.remove(key);
	}

	/**
	 * 绑定需要解析的token类型
	 * 
	 * @param tokenType token类型
	 * @param method    解析方法
	 */
	public void register(String tokenType, BuildAbstractTree method) {
		ruleMethod.put(tokenType, method);
	}

	/**
	 * 递归访问每个子项
	 * 
	 * @param listToken 迭代列表
	 * @param action    每个token的访问方法
	 */
	private void recursion(List<AssignmentSyntaxTree> listMethod) {
		RecursionStack<AssignmentSyntaxTree> recursionStack = new RecursionStack<>();
		recursionStack.copyEnable();
		// 先执行解析
		recursionStack.setBeforeMethod((stack, method) -> {
			ChiyaToken token = method.getNextToken();
			if (token != null) {
				BuildAbstractTree function = ruleMethod.get(token.getType());
				if (function != null) {
					AbstractSyntaxTree abstractSyntaxTree = function.buildTree(this, token);
					stack.varSet("ref", abstractSyntaxTree);
				}
			}
		});
		// 迭代
		recursionStack.setAfterMethod((stack, method) -> method.assignment(stack.varPop("ref")));
		
		// 判断是否需要继续递归
		recursionStack.setJudgeMethod((stack, method) -> {
			if (!needJudge.isEmpty()) {
				recursionStack.push(needJudge);
				needJudge.clear();
			}
		});

		recursionStack.push(listMethod);
		needJudge.clear();
		recursionStack.recursion();
		context.clear();
	}

	/**
	 * 传递语法树解析
	 * 
	 * @param setValue  要设置的值，允许为null
	 * @param nextToken 要进行解析的token
	 */
	public void nextParser(VoidGenericFunction<AbstractSyntaxTree> setValue, ChiyaToken nextToken) {
		needJudge.add(new AssignmentSyntaxTree().chainAssignmentFunction(setValue).chainNextToken(nextToken));
	}

	/**
	 * 递归访问每个子项
	 * 
	 * @param listToken 迭代列表
	 * @param action    每个token的访问方法
	 * @return 抽象语法树
	 */
	public List<AbstractSyntaxTree> parser(List<ChiyaToken> listToken) {
		List<AbstractSyntaxTree> listTree = new ArrayList<>();
		listToken.forEach(token -> {
			nextParser(listTree::add, token);
		});
		recursion(needJudge);
		return listTree;
	}
}
