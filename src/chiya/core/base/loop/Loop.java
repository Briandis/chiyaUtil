package chiya.core.base.loop;

import java.util.function.Consumer;

import chiya.core.base.count.CountInteger;
import chiya.core.base.function.ForEachFunction;
import chiya.core.base.function.VoidGenericFunction;
import chiya.core.base.function.VoidIntegerFunction;

/**
 * 循环工具库
 * 
 * @author brain
 */
public class Loop {

	/**
	 * 循环固定次数方法
	 * 
	 * @param max             最大次数
	 * @param integerFunction integer类型迭代方法
	 */
	public static void step(int max, VoidIntegerFunction integerFunction) {
		step(max, 1, integerFunction);
	}

	/**
	 * 循环固定次数方法
	 * 
	 * @param max             最大次数
	 * @param step            每次迭代出来的步长
	 * @param integerFunction integer类型迭代方法
	 */
	public static void step(int max, int step, VoidIntegerFunction integerFunction) {
		step(0, max, step, integerFunction);
	}

	/**
	 * @param start           起始值
	 * @param max             最大次数
	 * @param step            每次迭代出来的步长
	 * @param integerFunction integer类型迭代方法
	 */
	public static void step(int start, int max, int step, VoidIntegerFunction integerFunction) {
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
	public static void forEach(int array[], VoidIntegerFunction integerFunction) {
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
	public static <T> void forEach(T array[], VoidGenericFunction<T> genericityFunction) {
		if (array == null) { return; }
		for (int i = 0; i < array.length; i++) {
			genericityFunction.execute(array[i]);
		}
	}

	/**
	 * 迭代任意对象数组
	 * 
	 * @param <T>             对象数组类型
	 * @param array           对象数组
	 * @param forEachFunction 泛型迭代方法
	 */
	public static <T> void forEach(T array[], ForEachFunction<T> forEachFunction) {
		if (array == null) { return; }
		for (int i = 0; i < array.length; i++) {
			forEachFunction.next(array[i], i);
		}
	}

	/**
	 * 迭代任意对象集合
	 * 
	 * @param <T>      对象数组类型
	 * @param iterable 可迭代的集合
	 * @param action   泛型迭代方法
	 */
	public static <T> void forEach(Iterable<T> iterable, Consumer<? super T> action) {
		if (iterable == null) { return; }
		iterable.forEach(action);
	}

	/**
	 * 迭代任意对象集合
	 * 
	 * @param <T>             对象数组类型
	 * @param collection      可迭代的集合
	 * @param forEachFunction 泛型迭代方法
	 */
	public static <T> void forEach(Iterable<T> iterable, ForEachFunction<T> forEachFunction) {
		if (iterable == null) { return; }
		CountInteger countInteger = new CountInteger();
		iterable.forEach(o -> forEachFunction.next(o, countInteger.getAndIncrement()));
	}

	/**
	 * 迭代字符串
	 * 
	 * @param data            原始字符串
	 * @param forEachFunction 迭代方法
	 */
	public static void forEach(String data, ForEachFunction<Character> forEachFunction) {
		if (data == null) { return; }
		for (int i = 0; i < data.length(); i++) {
			forEachFunction.next(data.charAt(i), i);
		}
	}

}
