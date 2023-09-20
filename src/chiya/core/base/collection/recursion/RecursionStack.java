package chiya.core.base.collection.recursion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.collection.ContainerUtil;

/**
 * 迭代工具
 * 
 * @author chiya
 * @param <T>
 *
 */
public class RecursionStack<T> {

	/** 迭代容器 */
	private ArrayList<RecursionInfo> recursionStack = new ArrayList<>();
	/** 迭代判断方法 */
	private RecursionMethod<T> judgeMethod;
	/** 迭代前方法 */
	private RecursionMethod<T> beforeMethod;
	/** 迭代后方法 */
	private RecursionMethod<T> afterMethod;
	/** 迭代时方法 */
	private RecursionMethod<T> recursionMethod;

	/** 迭代时项目栈 */
	private ArrayList<List<T>> itemStack = new ArrayList<>();
	/** 迭代时变量栈 */
	private ArrayList<HashMap<String, Object>> varStack = new ArrayList<>();

	/** 是否进行了入栈操作 */
	private boolean pushFlag = false;
	/** 是否进行复制迭代 */
	private boolean copyFlag = false;
	/** 退出递归判断 */
	private volatile boolean exitFlag = false;

	/** 复制启用 */
	public void copyEnable() {
		copyFlag = true;
	}

	/** 复制禁用 */
	public void copyDisable() {
		copyFlag = false;
	}

	/** 重置 */
	private void reset() {
		recursionStack.clear();
		itemStack.clear();
		varStack.clear();
		pushFlag = false;
		exitFlag = false;
	}

	/**
	 * 局部变量设置
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public void varSet(String key, Object value) {
		ContainerUtil.getLast(varStack).put(key, value);
	}

	/**
	 * 局部变量值获取
	 * 
	 * @param key 获取的变量
	 * @return 值
	 */
	public Object varGet(String key) {
		return ContainerUtil.getLast(varStack).get(key);
	}

	/**
	 * 局部变量值获取
	 * 
	 * @param key 获取的变量
	 * @return 值
	 */
	public Object varPop(String key) {
		return ContainerUtil.getLast(varStack).remove(key);
	}

	/**
	 * 添加迭代数据
	 * 
	 * @param listData 迭代的列表
	 */
	public void push(List<T> listData) {
		varStack.add(new HashMap<>());
		// 根据是否递归原列表决定是否进行复制
		itemStack.add(copyFlag ? new ArrayList<>(listData) : listData);
		recursionStack.add(new RecursionInfo());
		pushFlag = true;
	}

	/** 开始进行迭代 */
	public void recursion() {
		while (!recursionStack.isEmpty()) {
			// 退出
			if (exitFlag) {
				reset();
				return;
			}
			RecursionInfo recursionInfo = ContainerUtil.getLast(recursionStack);
			if (recursionInfo.getIndex() < ContainerUtil.getLast(itemStack).size()) {
				T data = ContainerUtil.getLast(itemStack).get(recursionInfo.getIndex());
				// 递归前执行方法
				if (beforeMethod != null && recursionInfo.getBeforeFlag()) {
					beforeMethod.recursion(this, data);
					recursionInfo.setBeforeFlag(false);
				}
				// 递归执行方法
				if (recursionMethod != null && recursionInfo.getRecursionFlag()) {
					recursionMethod.recursion(this, data);
					recursionInfo.setRecursionFlag(false);
				}
				// 判断是否可以进行递归
				if (judgeMethod != null && recursionInfo.getJudgeFlag()) {
					judgeMethod.recursion(this, data);
					recursionInfo.setJudgeFlag(false);
					// 根据是否成功进行了添加迭代的操作判断是否要进行深层递归
					if (pushFlag) {
						pushFlag = false;
						continue;
					}
				}
				// 递归后执行方法
				if (afterMethod != null && recursionInfo.getAfterFlag()) {
					afterMethod.recursion(this, data);
					recursionInfo.setAfterFlag(false);
				}

				recursionInfo.next();
			} else {
				ContainerUtil.removeLast(itemStack);
				ContainerUtil.removeLast(recursionStack);
				ContainerUtil.removeLast(varStack);
			}
		}
		reset();
	}

	/**
	 * 获取迭代容器
	 * 
	 * @return 迭代容器
	 */
	public ArrayList<RecursionInfo> getRecursionStack() {
		return recursionStack;
	}

	/**
	 * 获取迭代判断方法
	 * 
	 * @return 迭代判断方法
	 */
	public RecursionMethod<T> getJudgeMethod() {
		return judgeMethod;
	}

	/**
	 * 设置迭代判断方法
	 * 
	 * @param judgeMethod 迭代判断方法
	 */
	public void setJudgeMethod(RecursionMethod<T> judgeMethod) {
		this.judgeMethod = judgeMethod;
	}

	/**
	 * 链式添加迭代判断方法
	 * 
	 * @param judgeMethod 迭代判断方法
	 * @return 对象本身
	 */
	public RecursionStack<T> chainJudgeMethod(RecursionMethod<T> judgeMethod) {
		setJudgeMethod(judgeMethod);
		return this;
	}

	/**
	 * 获取迭代前方法
	 * 
	 * @return 迭代前方法
	 */
	public RecursionMethod<T> getBeforeMethod() {
		return beforeMethod;
	}

	/**
	 * 设置迭代前方法
	 * 
	 * @param beforeMethod 迭代前方法
	 */
	public void setBeforeMethod(RecursionMethod<T> beforeMethod) {
		this.beforeMethod = beforeMethod;
	}

	/**
	 * 链式添加迭代前方法
	 * 
	 * @param beforeMethod 迭代前方法
	 * @return 对象本身
	 */
	public RecursionStack<T> chainBeforeMethod(RecursionMethod<T> beforeMethod) {
		setBeforeMethod(beforeMethod);
		return this;
	}

	/**
	 * 获取迭代后方法
	 * 
	 * @return 迭代后方法
	 */
	public RecursionMethod<T> getAfterMethod() {
		return afterMethod;
	}

	/**
	 * 设置迭代后方法
	 * 
	 * @param afterMethod 迭代后方法
	 */
	public void setAfterMethod(RecursionMethod<T> afterMethod) {
		this.afterMethod = afterMethod;
	}

	/**
	 * 链式添加迭代后方法
	 * 
	 * @param afterMethod 迭代后方法
	 * @return 对象本身
	 */
	public RecursionStack<T> chainAfterMethod(RecursionMethod<T> afterMethod) {
		setAfterMethod(afterMethod);
		return this;
	}

	/**
	 * 获取迭代时方法
	 * 
	 * @return 迭代时方法
	 */
	public RecursionMethod<T> getRecursionMethod() {
		return recursionMethod;
	}

	/**
	 * 设置迭代时方法
	 * 
	 * @param recursionMethod 迭代时方法
	 */
	public void setRecursionMethod(RecursionMethod<T> recursionMethod) {
		this.recursionMethod = recursionMethod;
	}

	/**
	 * 链式添加迭代时方法
	 * 
	 * @param recursionMethod 迭代时方法
	 * @return 对象本身
	 */
	public RecursionStack<T> chainRecursionMethod(RecursionMethod<T> recursionMethod) {
		setRecursionMethod(recursionMethod);
		return this;
	}

	/**
	 * 获取迭代时项目栈
	 * 
	 * @return 迭代时项目栈
	 */
	public ArrayList<List<T>> getItemStack() {
		return itemStack;
	}

	/**
	 * 获取迭代时变量栈
	 * 
	 * @return 迭代时变量栈
	 */
	public ArrayList<HashMap<String, Object>> getVarStack() {
		return varStack;
	}

	/** 退出全部递归，只有当前递归允许结束时才会 */
	public void exit() {
		exitFlag = true;
	}

}
