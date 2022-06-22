package chiya.core.base.loop;

import chiya.core.base.function.GenericityFunction;
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
	 * 循环固定次数方法
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

	/**
	 * 迭代一个Integer的数组
	 * 
	 * @param array           数组
	 * @param integerFunction 迭代方法
	 */
	public static void forEach(int array[], IntegerFunction integerFunction) {
		if (array == null) { return; }
		for (int i = 0; i < array.length; i++) {
			integerFunction.loop(array[i]);
		}
	}

	/**
	 * 迭代任意对象数组
	 * 
	 * @param <T>                对象数组类型
	 * @param array              对象数组
	 * @param genericityFunction 泛型迭代方法
	 */
	public static <T> void forEach(T array[], GenericityFunction<T> genericityFunction) {
		if (array == null) { return; }
		for (int i = 0; i < array.length; i++) {
			genericityFunction.next(null);
		}
	}

}
