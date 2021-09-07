package com.chiya.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具库
 * 
 * @author Brian
 *
 */
public class RandomUtil {
	private static ThreadLocalRandom random = ThreadLocalRandom.current();

	/**
	 * 随机生成一个0到max的数，[0,max)
	 * 
	 * @param max 生成的最大值
	 * @return 随机整数
	 */
	public static int randInt(int max) {
		return random.nextInt(max);
	}

	/**
	 * 随机生成从m到n的数据,不建议数值大于2^30<br>
	 * n、m不受顺序影响
	 * 
	 * @param min 最小值
	 * @param max 最大值
	 * @return 随机整数
	 */
	public static int randInt(int min, int max) {
		int a;
		if (min > max) {
			a = min;
			min = max;
			max = a;
		}
		a = max - min;
		return random.nextInt(a) + min;
	}

}
