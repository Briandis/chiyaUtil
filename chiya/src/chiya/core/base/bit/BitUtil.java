package chiya.core.base.bit;

/**
 * 二进制运算工具库
 * 
 * @author Brian
 *
 */
public class BitUtil {

	/**
	 * 判断二进制位是否为1
	 * 
	 * @param b      待判断二进制
	 * @param offset 偏移值0-7
	 * @return true:存在/false:不存在
	 */
	public static boolean macthBit(Byte b, int offset) {
		return b != null ? macthBit(b.byteValue(), offset) : false;
	}

	/**
	 * 判断二进制位是否为1
	 * 
	 * @param b      待判断二进制
	 * @param offset 偏移值0-7
	 * @return true:存在/false:不存在
	 */
	public static boolean macthBit(byte b, int offset) {
		offset = offset < 0 ? 0 : offset > 7 ? 7 : offset;
		return ((b >> offset) & 1) == 1;
	}

	/**
	 * 判断二进制位是否为1
	 * 
	 * @param c      待判断字符
	 * @param offset 偏移值0-31
	 * @return true:存在/false:不存在
	 */
	public static boolean macthBit(Character c, int offset) {
		return c != null ? macthBit(c.charValue(), offset) : false;
	}

	/**
	 * 判断二进制位是否为1
	 * 
	 * @param c      待判断字符
	 * @param offset 偏移值0-31
	 * @return true:存在/false:不存在
	 */
	public static boolean macthBit(char c, int offset) {
		offset = offset < 0 ? 0 : offset > 31 ? 31 : offset;
		return ((c >> offset) & 1) == 1;
	}

	/**
	 * 判断二进制位是否为1
	 * 
	 * @param i      待判断字符
	 * @param offset 偏移值0-31
	 * @return true:存在/false:不存在
	 */
	public static boolean macthBit(Integer i, int offset) {
		return i != null ? macthBit(i.intValue(), offset) : false;
	}

	/**
	 * 判断二进制位是否为1
	 * 
	 * @param i      待判断字符
	 * @param offset 偏移值0-31
	 * @return true:存在/false:不存在
	 */
	public static boolean macthBit(int i, int offset) {
		offset = offset < 0 ? 0 : offset > 31 ? 31 : offset;
		return ((i >> offset) & 1) == 1;
	}

	/**
	 * 统计其中二进制位数
	 * 
	 * @param b 待统计二进制
	 * @return 二进制1的数量
	 */
	public static int countBit(byte b) {
		return countBit32(b & 0xFF);
	}

	/**
	 * 统计其中二进制位数
	 * 
	 * @param c 待统计字符
	 * @return 二进制1的数量
	 */
	public static int countBit(char c) {
		return countBit32(c & 0xFFFF);
	}

	/**
	 * 统计其中二进制位数
	 * 
	 * @param i 32位整形
	 * @return 二进制1的数量
	 */
	public static int countBit(int i) {
		return countBit32(i);
	}

	/**
	 * 统计其中二进制位数
	 * 
	 * @param l 64位整形
	 * @return 二进制1的数量
	 */
	public static int countBit(long l) {
		return countBit32((int) (l & 0xFFFF_FFFFL)) + countBit32((int) (l >> 32 & 0xFFFF_FFFFL));
	}

	/**
	 * 统计其中二进制位数
	 * 
	 * @param i 32位整形
	 * @return 二进制1的数量
	 */
	public static int countBit32(int i) {
		i = (i & 0x55555555) + ((i >> 1) & 0x55555555);
		i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
		i = (i & 0x0F0F0F0F) + ((i >> 4) & 0x0F0F0F0F);
		return (i * (0x01010101) >> 24);
	}

	/**
	 * 固定偏移量的位置设置1
	 * 
	 * @param b      二进制
	 * @param offset 偏移量
	 * @return 新的byte
	 */
	public static byte modifySetBit(byte b, int offset) {
		offset = offset < 0 ? 0 : offset > 7 ? 7 : offset;
		return (byte) (b | (1 << offset));
	}

	/**
	 * 固定偏移量的位置设置1
	 * 
	 * @param c      字符
	 * @param offset 偏移量
	 * @return 新的byte
	 */
	public static char modifySetBit(char c, int offset) {
		offset = offset < 0 ? 0 : offset > 31 ? 31 : offset;
		return (char) (c | (1 << offset));
	}

	/**
	 * 固定偏移量的位置设置1
	 * 
	 * @param i      整形
	 * @param offset 偏移量
	 * @return 新的byte
	 */
	public static int modifySetBit(int i, int offset) {
		offset = offset < 0 ? 0 : offset > 7 ? 7 : offset;
		return i | (1 << offset);
	}

	/**
	 * 固定偏移量的位置设置0
	 * 
	 * @param b      二进制
	 * @param offset 偏移量
	 * @return 新的byte
	 */
	public static byte modifyRemoveBit(byte b, int offset) {
		offset = offset < 0 ? 0 : offset > 7 ? 7 : offset;
		return (byte) (b & ~(1 << offset));
	}

	/**
	 * 固定偏移量的位置设置0
	 * 
	 * @param c      字符
	 * @param offset 偏移量
	 * @return 新的byte
	 */
	public static char modifyRemoveBit(char c, int offset) {
		offset = offset < 0 ? 0 : offset > 31 ? 31 : offset;
		return (char) (c & ~(1 << offset));
	}

	/**
	 * 固定偏移量的位置设置0
	 * 
	 * @param i      整形
	 * @param offset 偏移量
	 * @return 新的byte
	 */
	public static int modifyRemoveBit(int i, int offset) {
		offset = offset < 0 ? 0 : offset > 7 ? 7 : offset;
		return i & ~(1 << offset);
	}

}
