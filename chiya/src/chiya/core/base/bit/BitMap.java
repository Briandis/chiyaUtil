package chiya.core.base.bit;

import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 位图
 * 
 * @author chiya
 *
 */
public class BitMap {

	/**
	 * 位图容器数组,1K空间可记录8192个单位
	 */
	private byte[] allBit = null;
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	/**
	 * 位图名称
	 */
	private String name;
	/**
	 * 计数器
	 */
	private LongAdder longAdder = new LongAdder();

	/**
	 * 默认1k,可记录8192个单位
	 */
	private static final int DEF_INIT_SIZE = 1024;

	/**
	 * 默认构造方法，创建一个1K存储容量的数组
	 */
	public BitMap() {
		allBit = new byte[DEF_INIT_SIZE];
	}

	/**
	 * 指定数组大小的构造方法
	 * 
	 * @param initSize 数组容量大小
	 */
	public BitMap(int initSize) {
		if (initSize < 1) {
			allBit = new byte[DEF_INIT_SIZE];
		} else {
			allBit = new byte[initSize];
		}
	}

	/**
	 * 标记对应位置数据
	 * 
	 * @param location 对应下标位置
	 * @return true:写入成功/false:写入失败
	 */
	public boolean add(int location) {
		if (location > -1) {
			int offset = location % 8;
			int index = location / 8;
			readWriteLock.writeLock().lock();
			if (allBit.length <= index) { expansion(location); }
			boolean b = false;
			if (!BitUtil.macthBit(allBit[index], offset)) {
				allBit[index] = BitUtil.modifySetBit(allBit[index], offset);
				longAdder.increment();
				b = true;
			}

			readWriteLock.writeLock().unlock();
			return b;
		}
		return false;
	}

	/**
	 * 移除对应位置数据
	 * 
	 * @param location 对应下标位置
	 * @return true:移除成功/false:移除失败
	 */
	public boolean remove(int location) {
		if (location > -1 && location < allBit.length * 8) {
			int offset = location % 8;
			int index = location / 8;
			boolean b = false;
			readWriteLock.writeLock().lock();
			if (BitUtil.macthBit(allBit[index], offset)) {
				allBit[index] = BitUtil.modifyRemoveBit(allBit[index], offset);
				longAdder.decrement();
				b = true;
			}
			readWriteLock.writeLock().unlock();
			return b;
		}
		return false;
	}

	/**
	 * 获取该位置存不存在数据
	 * 
	 * @param location 对应下标位置
	 * @return true:存在/false:不存在
	 */
	public boolean get(int location) {
		if (location > -1) {
			int offset = location % 8;
			int index = location / 8;
			readWriteLock.readLock().lock();;
			boolean b = BitUtil.macthBit(allBit[index], offset);
			readWriteLock.readLock().unlock();
			return b;
		}
		return false;
	}

	/**
	 * 空间占用长度
	 * 
	 * @return 数组长度
	 */
	public int length() {
		return allBit.length;
	}

	/**
	 * 最大支持容量
	 * 
	 * @return 当前位图最支持大容量
	 */
	public int maxSize() {
		return allBit.length * 8;
	}

	/**
	 * 当前位图实际使用数量
	 * 
	 * @return 实际使用数量
	 */
	public int size() {
		return longAdder.intValue();
	}

	/**
	 * 扩容
	 * 
	 * @param maxSize 扩容大小
	 */
	private void expansion(int maxSize) {
		synchronized (allBit) {
			int i = 0;
			while (maxSize != 0) {
				maxSize = maxSize >> 1;
				i++;
			}
			i = i > 31 ? 31 : i;
			int size = 1 << i;
			if (size < allBit.length) { size = size << 1; }
			allBit = Arrays.copyOf(allBit, size);
		}
	}

	/**
	 * 获取当前数组下标的值
	 * 
	 * @param index 下标
	 * @return byte
	 */
	public byte getByte(int index) {
		index = index < 0 ? 0 : index;
		index = index >= allBit.length ? allBit.length - 1 : index;
		return allBit[index];
	}

	/**
	 * 指定一个数组下标并设置值
	 * 
	 * @param index 下标
	 * @param byte
	 */
	public void setByte(int index, byte b) {
		index = index < 0 ? 0 : index;
		index = index >= allBit.length ? allBit.length - 1 : index;
		allBit[index] = b;
	}

	/**
	 * 获取位图名称
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置位图名称
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 把位图中所有1的下标汇聚成数组，返回所有的下标
	 * 
	 * @return 所有存储的下标数组
	 */
	public int[] valueToIntArray() {
		synchronized (this) {
			int[] all = new int[longAdder.intValue()];
			int count = 0;
			// 不使用get的方法，减少锁获取
			for (int i = 0; i < allBit.length; i++) {
				for (int j = 0; j < 8; j++) {
					if (BitUtil.macthBit(allBit[i], j)) {
						all[count] = i * 8 + j;
						count++;
					}
				}
			}
			return all;
		}
	}

}
