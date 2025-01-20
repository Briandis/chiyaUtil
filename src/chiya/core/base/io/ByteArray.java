package chiya.core.base.io;

/**
 * 字节数组缓存
 * 
 * @author chiya
 *
 */
public class ByteArray {
	/** 字节数组 */
	private byte buffer[];
	/** 起始位置 */
	private int start;
	/** 结束位置 */
	private int end;
	/** 默认16字节 */
	public static final int DEF_INIT_SIZE = 128;
	/** 当前大小 */
	private int size = 0;

	/** 默认构造方法16个字节大小 */
	public ByteArray() {
		buffer = new byte[DEF_INIT_SIZE];
	}

	/**
	 * 构建字节数组缓存
	 * 
	 * @param bufferSize 缓存大小
	 */
	public ByteArray(int bufferSize) {
		buffer = new byte[bufferSize];
	}

	/**
	 * 重新映射字节数组，使之恢复0索引下标
	 * 
	 * @param newSize
	 */
	public void resetByte(int newSize) {
		byte newData[] = new byte[newSize];
		if (start > end) {
			System.arraycopy(buffer, start, newData, 0, buffer.length - start);
			System.arraycopy(buffer, 0, newData, buffer.length - start, end);
		} else {
			System.arraycopy(buffer, 0, newData, start, buffer.length);
		}
		buffer = newData;
		start = 0;
		end = size;
	}

	/**
	 * 添加数组
	 * 
	 * @param data 字节数组
	 */
	public void append(byte data[]) {
		if (data != null) { append(data, 0, data.length); }
	}

	/**
	 * 添加数组
	 * 
	 * @param data   字节数组
	 * @param offset 便宜
	 */
	public void append(byte data[], int offset) {
		if (data != null) { append(data, offset, data.length - offset); }
	}

	/**
	 * 追加数组
	 * 
	 * @param data     字节数组
	 * @param offset   偏移
	 * @param readSize 读取大小
	 */
	public void append(byte data[], int offset, int readSize) {
		if (readSize <= 0) { return; }
		if (data != null) {
			if (readSize > data.length - offset) { readSize = data.length - offset; }
			// 如果大于容量，则扩容
			if (buffer.length < size + readSize) {
				byte newData[] = new byte[size + readSize + (int) (buffer.length * 1.6)];
				if (start > end) {
					System.arraycopy(buffer, start, newData, 0, buffer.length - start);
					System.arraycopy(buffer, 0, newData, buffer.length - start, end);
				} else {
					System.arraycopy(buffer, start, newData, 0, size);
				}
				buffer = newData;
				start = 0;
				end = size;
			}
			// 计算尾部剩余空间是否充足
			int lastSize = buffer.length - end;
			size += readSize;
			if (lastSize >= readSize) {
				System.arraycopy(data, offset, buffer, end, readSize);
				end += readSize;
			} else {
				System.arraycopy(data, offset, buffer, end, lastSize);
				System.arraycopy(data, offset + lastSize, buffer, 0, readSize - lastSize);
				end = readSize - lastSize;
			}
		}
	}

	/**
	 * 改变起始位置索引
	 * 
	 * @param moveSize 移动大小
	 */
	private void changeStart(int moveSize) {
		start += moveSize;
		if (start >= buffer.length) { start = start - buffer.length; }
	}

	/**
	 * 获取真实索引
	 * 
	 * @param index 逻辑下标
	 * @return 真实映射下标
	 */
	private int getIndex(int index) {
		index = start + index;
		if (index >= buffer.length) { index = index - buffer.length; }
		return index;
	}

	/**
	 * 获取当前字节
	 * 
	 * @param end 结尾下标
	 * @return 当前下标字节
	 */
	public byte[] getByte(int end) {
		return getByte(0, end);
	}

	/**
	 * 获取当前字节
	 * 
	 * @param index 当前下标
	 * @param end   结尾下标
	 * @return 当前下标字节
	 */
	public byte[] getByte(int index, int end) {
		int count = end;
		if (count > size) { count = size; }
		byte result[] = new byte[count];
		if (start > end) {
			int endDataSize = buffer.length - start;
			if (count > endDataSize) {
				System.arraycopy(buffer, getIndex(index), result, 0, endDataSize);
				System.arraycopy(buffer, getIndex(endDataSize), result, endDataSize, end);
			} else {
				System.arraycopy(buffer, getIndex(index), result, 0, count);
			}
		} else {
			System.arraycopy(buffer, getIndex(index), result, 0, count);
		}
		return result;
	}

	/**
	 * 移除某个字节数组
	 * 
	 * @param count 数量
	 * @return 字节数组
	 */
	public byte[] remove(int count) {
		if (count > size) { count = size; }
		byte result[] = new byte[count];

		if (start > end) {
			int endDataSize = buffer.length - start;
			if (count > endDataSize) {
				System.arraycopy(buffer, getIndex(0), result, 0, endDataSize);
				System.arraycopy(buffer, getIndex(endDataSize), result, endDataSize, count - endDataSize);
			} else {
				System.arraycopy(buffer, getIndex(0), result, 0, count);
			}
		} else {
			System.arraycopy(buffer, getIndex(0), result, 0, count);
		}
		changeStart(count);
		size -= count;
		return result;
	}

	/**
	 * 输出字节数组
	 * 
	 * @return 字节数
	 */
	public byte[] toByte() {
		if (size == 0) { return null; }
		byte result[] = new byte[size];
		if (start >= end) {
			System.arraycopy(buffer, start, result, 0, buffer.length - start);
			System.arraycopy(buffer, 0, result, buffer.length - start, end);
		} else {
			System.arraycopy(buffer, start, result, 0, size);
		}
		return result;
	}

	/** 获取大小 */
	public int getSize() {
		return size;
	}

	/** 获取缓存分配大小 */
	public int getBufferSize() {
		return buffer.length;
	}

	/**
	 * 查找字节数组内的相同的值
	 * 
	 * @param findData  查找内容
	 * @param findStart 从哪里开始
	 * @param findEnd   到哪里结束
	 * @return 找到的下标
	 */
	public int find(byte findData[], int findStart, int findEnd) {
		int index = -1;
		if (size != 0 && size > findData.length) {
			int j = 0;
			boolean isFind = true;
			findEnd = findEnd - findData.length;
			while (findStart < findEnd) {
				isFind = true;
				for (j = 0; j < findData.length; j++) {
					if (buffer[getIndex(findStart + j)] != findData[j]) {
						isFind = false;
						break;
					}
				}
				if (isFind) {
					index = findStart;
					break;
				}
				findStart++;
			}

		}

		return index;
	}

	/**
	 * 查找字节数组内的相同的值
	 * 
	 * @param findData 查找的字节数组
	 * @return 找到的下标
	 */
	public int find(byte findData[]) {
		return find(findData, 0, size);
	}

	/**
	 * 查找字节数组内的相同的值
	 * 
	 * @param findData  查找的字节数组
	 * @param findStart 从哪里开始
	 * @return 找到的下标
	 */
	public int find(byte findData[], int findStart) {
		return find(findData, findStart, size);
	}
}
