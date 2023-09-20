package chiya.core.base.collection;

import java.util.ArrayList;

/**
 * 增强的Array，线程不安全
 * 
 * @author chiya
 *
 * @param <E>
 */
public class ChiyaArray<E> extends ArrayList<E> {

	/** 序列号标记符 */
	private static final long serialVersionUID = -7700225158829003550L;

	/**
	 * 根据传入下标支持负数下标
	 */
	@Override
	public E get(int index) {
		if (index < 0) { index = size() + index; }
		return super.get(index);
	}

	@Override
	public E remove(int index) {
		if (index < 0) { index = size() + index; }
		return super.remove(index);
	}

	/**
	 * 获取最后一个
	 * 
	 * @return 最后一个元素
	 */
	public E getLast() {
		if (isEmpty()) { return null; }
		return get(-1);
	}

	/**
	 * 获取第一个
	 * 
	 * @return 第一个元素
	 */
	public E getFirst() {
		if (isEmpty()) { return null; }
		return get(0);
	}

	/**
	 * 出栈
	 * 
	 * @return 栈顶元素
	 */
	public E pop() {
		return remove(-1);
	}

	/**
	 * 获取当前下标
	 * 
	 * @return 当前下标位置
	 */
	public int nowIndex() {
		return size() - 1;
	}
}
