package chiya.core.base.loop;

import chiya.core.base.function.IntegerFunction;

/**
 * 循环工具库
 * 
 * @author brain
 *
 */
public class Loop {

	/**
	 * 循环固定次数方法
	 * 
	 * @param max             最大次数
	 * @param integerFunction integer类型迭代方法
	 */
	public static void step(int max, IntegerFunction integerFunction) {
		step(max, 1, integerFunction);
	}

	/**
	 * 
	 * @param max             最大次数
	 * @param step            每次迭代出来的步长
	 * @param integerFunction integer类型迭代方法
	 */
	public static void step(int max, int step, IntegerFunction integerFunction) {
		step(0, max, step, integerFunction);
	}

	/**
	 * 
	 * @param start           起始值
	 * @param max             最大次数
	 * @param step            每次迭代出来的步长
	 * @param integerFunction integer类型迭代方法
	 */
	public static void step(int start, int max, int step, IntegerFunction integerFunction) {
		for (int i = start; i < max; i += step) {
			integerFunction.loop(i);
		}
	}

}
