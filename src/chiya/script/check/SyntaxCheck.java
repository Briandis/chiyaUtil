package chiya.script.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.collection.recursion.RecursionStack;
import chiya.script.token.ChiyaToken;

/**
 * 语法检查
 * 
 * @author chiya
 *
 */
public class SyntaxCheck {

	/** 类型检查方法 */
	private HashMap<String, RecursionCheck> hashMap = new HashMap<>();
	/** 根节点检查 */
	private RecursionCheck rootCheck;

	/**
	 * 注册每一种类型的检查方式
	 * 
	 * @param type
	 * @param function
	 */
	public void register(String type, RecursionCheck function) {
		hashMap.put(type, function);
	}

	/**
	 * 注册根节点检查方式
	 * 
	 * @param function 根节点检查
	 */
	public void registerRoot(RecursionCheck function) {
		rootCheck = function;
	}

	/**
	 * 检查语法错误
	 * 
	 * @param list token列表
	 * @return 错误信息
	 */
	public List<String> check(List<ChiyaToken> list) {
		List<String> errorMessage = new ArrayList<>();
		RecursionStack<ChiyaToken> recursionStack = new RecursionStack<>();
		List<ChiyaToken> needRecursion = new ArrayList<>();
		// 检查根节点
		if (rootCheck != null) { rootCheck.check(new ChiyaToken().chainTokenTree(list), needRecursion, errorMessage); }

		recursionStack.setJudgeMethod((stack, data) -> {
			if (data != null) {
				RecursionCheck recursionCheck = hashMap.get(data.getType());
				if (recursionCheck != null) {
					// 当前判断方法交给下一个来进行判断
					needRecursion.clear();
					boolean b = recursionCheck.check(data, needRecursion, errorMessage);
					if (b) { stack.push(needRecursion); }
				} else {
					stack.push(data.getTokenTree());
				}
			}
		});

		recursionStack.push(needRecursion);
		recursionStack.recursion();
		return errorMessage;
	}

}
