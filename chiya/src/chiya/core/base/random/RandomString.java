package chiya.core.base.random;

import java.util.UUID;

import chiya.core.base.string.StringUtil;

/**
 * 随机字符串工具
 * 
 * @author brian
 */
public class RandomString {
	/** 大写字母 */
	public static final String UPPER_WORD = "ABCDEFGHIJKLMNOPQRsTUVWXYZ";
	/** 小写字母 */
	public static final String LOWER_WORD = "abcdefghijklmnopqrstuvwxyz";
	/** 数字 */
	public static final String NUMBER = "0123456789";
	/** 大小写字母 */
	public static final String ALL_WORD = UPPER_WORD + LOWER_WORD;
	/** 全部单个字符 */
	public static final String ALL_CHARS = UPPER_WORD + LOWER_WORD + NUMBER;
	/** 验证码字符串，去除了难以识别的相近字符 */
	public static final String CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";

	/**
	 * 基于UUID随机生成一个去掉[-]的字符串
	 * 
	 * @return UUID字符串
	 */
	public static String randomStringByUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 基于UUID生成一个去掉[-]的字符串，并按照一定长度截取<br>
	 * 如果超出字符串生成长度，则自动去UUID最终长度<br>
	 * <b>该方法会最低限度保证唯一性</b>
	 * 
	 * @param length 生成字符串的长度
	 * @return UUID字符串
	 */
	public static String randomStringByUUID(int length) {
		String data = randomStringByUUID();
		length = length < 1 ? data.length() : length;
		return data.length() > length ? data.substring(0, length) : data;
	}

	/**
	 * 随机生成一个长度为8的字符串
	 * 
	 * @return 生成的字符串
	 */
	public static String randomString() {
		return randomString(8);
	}

	/**
	 * 随机生成一个指定的字符串
	 * 
	 * @param length 生成的长度
	 * @return 生成的字符串
	 */
	public static String randomString(int length) {
		return randomString(ALL_CHARS, length);
	}

	/**
	 * 生成长度为6的验证码
	 * 
	 * @return 生成的验证码字符串
	 */
	public static String randomStringCode() {
		return randomStringCode(6);
	}

	/**
	 * 生成指定长度的验证码
	 * 
	 * @param length 生成长度
	 * @return 生成的验证码字符串
	 */
	public static String randomStringCode(int length) {
		return randomString(CODE_CHARS, length);
	}

	/**
	 * 根据传入的字符串和长度，从字符串中抽取字符串，生成指定长度的字符串<br>
	 * 如果生成长度小于1，则最低生成4个长度的字符串<br>
	 * 如果要包含的字符串为空，则默认当作A-Ba-b0-9范围内的字符
	 * 
	 * @param chars  要包含字符的字符串
	 * @param length 生成长度
	 * @return 生成后的字符串
	 */
	public static String randomString(String chars, int length) {
		length = length < 1 ? 4 : length;
		if (chars == null || chars.length() == 0) { chars = ALL_CHARS; }
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) { stringBuilder.append(chars.charAt(RandomUtil.randInt(chars.length()))); }
		return stringBuilder.toString();
	}

	/**
	 * 语法规则，采用替换逻辑<br>
	 * *代表全部符合的字符<br>
	 * #代表数字<br>
	 * $代表字母<br>
	 * _下划线代码自增数值，默认按照位数放<br>
	 * 如：DSCJHD***-***___<br>
	 * 则生成：DSCJHDbdw-azx001 DSCJHDwvg-zqw002 .....的券码
	 * 
	 * @param str 规则字符串
	 * @return 生成的随机字符串
	 */
	public static String create(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case '*':
				stringBuilder.append(RandomString.randomString(1));
				break;
			case '#':
				stringBuilder.append(RandomUtil.randInt(10));
				break;
			case '$':
				stringBuilder.append(RandomString.randomString(ALL_WORD, 1));
				break;
			case '[':
				int next = StringUtil.findChar(str, ']', i);
				// 如果没找到结束，先跳过
				if (next == -1) { break; }
				stringBuilder.append(RandomString.randomString(str.substring(i + 1, next), 1));
				i = next;
				break;
			default:
				stringBuilder.append(str.charAt(i));
				break;
			}
		}
		return stringBuilder.toString();
	}

}
