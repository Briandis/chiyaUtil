package chiya.script.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import chiya.core.base.collection.recursion.RecursionStack;
import chiya.core.base.pack.IntegerPack;
import chiya.core.base.string.StringUtil;

/**
 * 基础token信息
 * 
 * @author chiya
 *
 */
public class ChiyaToken {

	/** 内部嵌套的token */
	private List<ChiyaToken> tokenTree = new ArrayList<>();

	/** token类型 */
	private String type;

	/** 起始字符 */
	private String start;

	/** 中间字符 */
	private String data;

	/** 结尾字符 */
	private String end;

	/** 字符所在原本的位置 */
	private int charIndex = -1;

	/** 字符原本所在行 */
	private int charline = -1;

	/**
	 * 检查树类型
	 * 
	 * @param index 下标
	 * @param type  类型
	 * @return true:是这个类型/false:不是
	 */
	public boolean checkTreeType(int index, String type) {
		return index > tokenTree.size() ? false : StringUtil.eqString(tokenTree.get(index).getType(), type);
	}

	/**
	 * 检查树类型
	 * 
	 * @param index 下标
	 * @param type  类型
	 * @return true:是这个类型/false:不是
	 */
	public boolean checkTreeInType(int index, String... type) {
		return index > tokenTree.size() ? false : StringUtil.inString(tokenTree.get(index).getType(), type);
	}

	/**
	 * 获取树
	 * 
	 * @param index 下标
	 * @return 树节点
	 */
	public ChiyaToken getTree(int index) {
		return tokenTree.get(index);
	}

	/**
	 * 获取树的data
	 * 
	 * @param index 下标
	 * @return 树节点的data数据
	 */
	public String getTreeData(int index) {
		return tokenTree.get(index).getData();
	}

	/**
	 * 获取树的start
	 * 
	 * @param index 下标
	 * @return 树节点的start数据
	 */
	public String getTreeStart(int index) {
		return tokenTree.get(index).getStart();
	}

	/**
	 * 添加树
	 * 
	 * @param chiyaToken token
	 */
	public void addTree(ChiyaToken chiyaToken) {
		tokenTree.add(chiyaToken);
	}

	/**
	 * 添加树
	 * 
	 * @param chiyaToken token集合
	 */
	public void addTree(Collection<ChiyaToken> collection) {
		if (collection.isEmpty()) { return; }
		tokenTree.addAll(collection);
	}

	/**
	 * 获取内部嵌套的token
	 * 
	 * @return 内部嵌套的token
	 */
	public List<ChiyaToken> getTokenTree() {
		return tokenTree;
	}

	/**
	 * 设置内部嵌套的token
	 * 
	 * @param tokenTree 内部嵌套的token
	 */
	public void setTokenTree(List<ChiyaToken> tokenTree) {
		this.tokenTree = tokenTree;
	}

	/**
	 * 链式添加内部嵌套的token
	 * 
	 * @param tokenTree 内部嵌套的token
	 * @return 对象本身
	 */
	public ChiyaToken chainTokenTree(List<ChiyaToken> tokenTree) {
		setTokenTree(tokenTree);
		return this;
	}

	/**
	 * 获取token类型
	 * 
	 * @return token类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置token类型
	 * 
	 * @param type token类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 链式添加token类型
	 * 
	 * @param type token类型
	 * @return 对象本身
	 */
	public ChiyaToken chainType(String type) {
		setType(type);
		return this;
	}

	/**
	 * 获取起始字符
	 * 
	 * @return 起始字符
	 */
	public String getStart() {
		return start;
	}

	/**
	 * 设置起始字符
	 * 
	 * @param start 起始字符
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * 链式添加起始字符
	 * 
	 * @param start 起始字符
	 * @return 对象本身
	 */
	public ChiyaToken chainStart(String start) {
		setStart(start);
		return this;
	}

	/**
	 * 获取中间字符
	 * 
	 * @return 中间字符
	 */
	public String getData() {
		return data;
	}

	/**
	 * 设置中间字符
	 * 
	 * @param data 中间字符
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 链式添加中间字符
	 * 
	 * @param data 中间字符
	 * @return 对象本身
	 */
	public ChiyaToken chainData(String data) {
		setData(data);
		return this;
	}

	/**
	 * 获取结尾字符
	 * 
	 * @return 结尾字符
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * 设置结尾字符
	 * 
	 * @param end 结尾字符
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * 链式添加结尾字符
	 * 
	 * @param end 结尾字符
	 * @return 对象本身
	 */
	public ChiyaToken chainEnd(String end) {
		setEnd(end);
		return this;
	}

	/**
	 * 获取字符所在原本的位置
	 * 
	 * @return 字符所在原本的位置
	 */
	public int getCharIndex() {
		return charIndex;
	}

	/**
	 * 设置字符所在原本的位置
	 * 
	 * @param charIndex 字符所在原本的位置
	 */
	public void setCharIndex(int charIndex) {
		this.charIndex = charIndex;
	}

	/**
	 * 链式添加字符所在原本的位置
	 * 
	 * @param charIndex 字符所在原本的位置
	 * @return 对象本身
	 */
	public ChiyaToken chainCharIndex(int charIndex) {
		setCharIndex(charIndex);
		return this;
	}

	/**
	 * 获取字符原本所在行
	 * 
	 * @return 字符原本所在行
	 */
	public int getCharline() {
		return charline;
	}

	/**
	 * 设置字符原本所在行
	 * 
	 * @param charline 字符原本所在行
	 */
	public void setCharline(int charline) {
		this.charline = charline;
	}

	/**
	 * 链式添加字符原本所在行
	 * 
	 * @param charline 字符原本所在行
	 * @return 对象本身
	 */
	public ChiyaToken chainCharline(int charline) {
		setCharline(charline);
		return this;
	}

	/**
	 * 获取debug后的token列表
	 * 
	 * @param listToken token列表
	 * @return 字符串
	 */
	public static String debug(List<ChiyaToken> listToken) {
		StringBuilder stringBuilder = new StringBuilder();
		RecursionStack<ChiyaToken> recursionStack = new RecursionStack<>();
		IntegerPack integerPack = new IntegerPack();
		int codeFormat[] = new int[] { 25, 1, 15, 1, 15, 1, 25, 1, 5 };
		String T_CHAR = "\t";
		recursionStack.setJudgeMethod((stack, token) -> {
			// 控制层级
			for (int i = 0; i < integerPack.getData(); i++) {
				stringBuilder.append("  ");
			}
			// 格式化
			stringBuilder.append(
				StringUtil.formatPadding(
					codeFormat,
					" ",
					token.getType(),
					T_CHAR,
					token.getCharIndex(),
					T_CHAR,
					token.getStart(),
					T_CHAR,
					token.getData(),
					T_CHAR,
					token.getEnd()
				)
			).append("\n");
			// 进入递归
			if (token != null && !token.getTokenTree().isEmpty()) {
				integerPack.incrementAndGet();
				stack.varSet("data", token);
				stack.push(token.getTokenTree());
			}
		});
		recursionStack.setAfterMethod((stack, data) -> {
			if (stack.varGet("data") == data) {
				// 将层级减少
				integerPack.decrementAndGet();
			}
		});
		recursionStack.push(listToken);
		recursionStack.recursion();
		return stringBuilder.toString();
	}

	/**
	 * 格式化自身
	 * 
	 * @return 自身
	 */
	public String format() {
		StringBuilder stringBuilder = new StringBuilder();
		format(stringBuilder);
		return stringBuilder.toString();
	}

	/**
	 * 格式化自身
	 * 
	 * @param stringBuilder 拼接器
	 */
	public void format(StringBuilder stringBuilder) {
		if (start != null) { stringBuilder.append(start); }
		if (data != null) { stringBuilder.append(data); }
		if (end != null) { stringBuilder.append(end); }
	}

	/**
	 * 递归访问每个子项
	 * 
	 * @param listToken 迭代列表
	 * @param action    每个token的访问方法
	 */
	public static void recursion(List<ChiyaToken> listToken, Consumer<ChiyaToken> action) {
		RecursionStack<ChiyaToken> recursionStack = new RecursionStack<>();
		recursionStack.setJudgeMethod((stack, data) -> {
			if (data != null) {
				action.accept(data);
				if (data.getTokenTree() != null && !data.getTokenTree().isEmpty()) {
					// 有子项才继续迭代
					stack.push(data.getTokenTree());
				}
			}
		});
		recursionStack.push(listToken);
		recursionStack.recursion();
	}

	/**
	 * 递归访问每个子项
	 * 
	 * @param listToken 迭代列表
	 * @param action    每个token的访问方法
	 */
	public String formatAll() {
		StringBuilder stringBuilder = new StringBuilder();
		format(stringBuilder);
		recursion(tokenTree, token -> token.format(stringBuilder));
		return stringBuilder.toString();
	}

	/**
	 * 更改token类型
	 * 
	 * @param listToken token列表
	 * @param type      类型
	 * @param newType   新类型
	 */
	public static void changeType(List<ChiyaToken> listToken, String type, String newType) {
		recursion(listToken, token -> {
			if (StringUtil.eqString(token.getType(), newType)) { token.setType(newType); }
		});
	}

}
