package com.chiya.bit;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用户组持有用户信息
 * 
 * @author Brian
 *
 */
public class BitMap {

	/**
	 * 位图记录用户信息,1K空间可记录8192个单位
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
	private AtomicInteger atomicInteger = new AtomicInteger();

	/**
	 * 默认1k,可记录8192个单位
	 */
	private static final int DEF_INIT_SIZE = 1024;

	public BitMap() {
		allBit = new byte[DEF_INIT_SIZE];
	}

	public BitMap(int initSize) {
		if (initSize < 1) {
			allBit = new byte[DEF_INIT_SIZE];
		} else {
			allBit = new byte[initSize];
		}
	}

	/**
	 * 对应位置
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
				atomicInteger.incrementAndGet();
				b = true;
			}

			readWriteLock.writeLock().unlock();
			return b;
		}
		return false;
	}

	/**
	 * 移除用户id
	 * 
	 * @param location 对应下标位置
	 * @return true:移除成功/false:移除失败
	 */
	public boolean remove(int location) {
		if (location > -1) {

			int offset = location % 8;
			int index = location / 8;
			boolean b = false;
			readWriteLock.writeLock().lock();
			if (BitUtil.macthBit(allBit[index], offset)) {
				allBit[index] = BitUtil.modifyRemoveBit(allBit[index], offset);
				atomicInteger.decrementAndGet();
				b = true;
			}
			readWriteLock.writeLock().unlock();
			return b;
		}
		return false;
	}

	/**
	 * 获取该用户存不存在
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
	 * @return
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
		return atomicInteger.get();
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

}
