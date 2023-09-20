package chiya.core.base.collection.recursion;

/**
 * 递归配置
 * 
 * @author chiya
 *
 */
public class RecursionInfo {
	/** 当前递归的下标 */
	private int index = 0;
	/** 是否执行过判断 */
	private boolean judgeFlag = true;
	/** 是否执行过前置处理 */
	private boolean beforeFlag = true;
	/** 是否执行过后置方法处理 */
	private boolean afterFlag = true;
	/** 是否执行过递归任务 */
	private boolean recursionFlag = true;

	/** 递归至下一个 */
	public void next() {
		index += 1;
		judgeFlag = true;
		beforeFlag = true;
		afterFlag = true;
		recursionFlag = true;
	}

	/**
	 * 获取当前递归的下标
	 * 
	 * @return 当前递归的下标
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置当前递归的下标
	 * 
	 * @param index 当前递归的下标
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 链式添加当前递归的下标
	 * 
	 * @param index 当前递归的下标
	 * @return 对象本身
	 */
	public RecursionInfo chainIndex(int index) {
		setIndex(index);
		return this;
	}

	/**
	 * 获取是否执行过判断
	 * 
	 * @return 是否执行过判断
	 */
	public boolean getJudgeFlag() {
		return judgeFlag;
	}

	/**
	 * 设置是否执行过判断
	 * 
	 * @param judgeFlag 是否执行过判断
	 */
	public void setJudgeFlag(boolean judgeFlag) {
		this.judgeFlag = judgeFlag;
	}

	/**
	 * 链式添加是否执行过判断
	 * 
	 * @param judgeFlag 是否执行过判断
	 * @return 对象本身
	 */
	public RecursionInfo chainJudgeFlag(boolean judgeFlag) {
		setJudgeFlag(judgeFlag);
		return this;
	}

	/**
	 * 获取是否执行过前置处理
	 * 
	 * @return 是否执行过前置处理
	 */
	public boolean getBeforeFlag() {
		return beforeFlag;
	}

	/**
	 * 设置是否执行过前置处理
	 * 
	 * @param beforeFlag 是否执行过前置处理
	 */
	public void setBeforeFlag(boolean beforeFlag) {
		this.beforeFlag = beforeFlag;
	}

	/**
	 * 链式添加是否执行过前置处理
	 * 
	 * @param beforeFlag 是否执行过前置处理
	 * @return 对象本身
	 */
	public RecursionInfo chainBeforeFlag(boolean beforeFlag) {
		setBeforeFlag(beforeFlag);
		return this;
	}

	/**
	 * 获取是否执行过后置方法处理
	 * 
	 * @return 是否执行过后置方法处理
	 */
	public boolean getAfterFlag() {
		return afterFlag;
	}

	/**
	 * 设置是否执行过后置方法处理
	 * 
	 * @param afterFlag 是否执行过后置方法处理
	 */
	public void setAfterFlag(boolean afterFlag) {
		this.afterFlag = afterFlag;
	}

	/**
	 * 链式添加是否执行过后置方法处理
	 * 
	 * @param afterFlag 是否执行过后置方法处理
	 * @return 对象本身
	 */
	public RecursionInfo chainAfterFlag(boolean afterFlag) {
		setAfterFlag(afterFlag);
		return this;
	}

	/**
	 * 获取是否执行过递归任务
	 * 
	 * @return 是否执行过递归任务
	 */
	public boolean getRecursionFlag() {
		return recursionFlag;
	}

	/**
	 * 设置是否执行过递归任务
	 * 
	 * @param recursionFlag 是否执行过递归任务
	 */
	public void setRecursionFlag(boolean recursionFlag) {
		this.recursionFlag = recursionFlag;
	}

	/**
	 * 链式添加是否执行过递归任务
	 * 
	 * @param recursionFlag 是否执行过递归任务
	 * @return 对象本身
	 */
	public RecursionInfo chainRecursionFlag(boolean recursionFlag) {
		setRecursionFlag(recursionFlag);
		return this;
	}

}
