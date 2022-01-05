package chiya.core.base.string;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import chiya.core.base.random.RandomUtil;

/**
 * 字符串工具库 最后修改时间 2021-08-04
 * 
 * @author Brian
 * @version 1.0.0
 */
public class StringUtil {

	/** 默认用户名使用的正则表达式 */
	private static final String EL_USER_NAME = "^\\w{4,16}$";
	/** 默认秘密使用的正则表达式 */
	private static final String EL_PASSWORD = "^\\w{4,16}$";
	/** 默认邮箱使用的正则表达式 */
	private static final String EL_EMAIL = "^\\w+@\\w+\\.\\w+$";
	/** 默认邮手机使用的正则表达式 */
	private static final String EL_PHONE = "^1[345789]\\d{9}$";
	/** 全部单个字符 */
	private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	/** 验证码字符串，去除了难以识别的相近字符 */
	private static final String CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
	/** 图片文件名后缀 */
	private static final String[] ARRAY_IMG = { ".jpg", ".png", ".bmp", ".webp", ".jpge" };
	/** 空字符,对应编码：32、12288、9 */
	private static final char[] SPACE_CHARS = { '　', '	', ' ' };
	/** 空字符串 */
	private static final String STRING_NULL = "";
	/** 空替换正则，对应编码：32、12288、9 */
	private static final String EL_SPACE = "[ 　	]";
	/** 字符集编码 */
	private static final String CHAR_CODE = "UTF-8";

	/**
	 * 判断字符串是否满足正则表达式
	 * 
	 * @param el  正则表达式
	 * @param str 待判断字符串
	 * @return 真/假
	 */
	public static boolean match(String el, String str) {
		if (str == null) { return false; }
		return Pattern.compile(el).matcher(str).matches();
	}

	/**
	 * 判断用户名是否满足规则
	 * 
	 * @param userName 用户名字符串
	 * @return 真/假
	 */
	public static boolean matchUserName(String userName) {
		return userName != null ? match(EL_USER_NAME, userName) : false;
	}

	/**
	 * 判断密码是否满足规则
	 * 
	 * @param password 密码字符串
	 * @return 真/假
	 */
	public static boolean matchPassword(String password) {
		return password != null ? match(EL_PASSWORD, password) : false;
	}

	/**
	 * 判断邮箱是否满足规则
	 * 
	 * @param email 邮箱字符串
	 * @return 真/假
	 */
	public static boolean matchEmail(String email) {
		return email != null ? match(EL_EMAIL, email) : false;
	}

	/**
	 * 判断手机号是否满足规则
	 * 
	 * @param phone 手机号字符串
	 * @return 真/假
	 */
	public static boolean matchPhone(String phone) {
		return phone != null ? match(EL_PHONE, phone) : false;
	}

	/**
	 * 强制把字符串转成数字，失败返回0
	 * 
	 * @param str 待转换字符串
	 * @return Integer包装类
	 */
	public static Integer parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 判断字符串是否是全为空格,或者为Null,或者长度为0<br>
	 * 全为空返回真，不为空返回假
	 * 
	 * @param str 待判断字符串
	 * @return 真/假
	 */
	public static boolean matchStringAllSpaceOrNULL(String str) {
		if (str == null || str.length() == 0) { return true; }
		boolean b = true;
		for (int i = 0; i < str.length(); i++) {
			b = true;
			for (char c : SPACE_CHARS) { b = b && str.charAt(i) != c; }
			if (b) { return false; }
		}
		return true;
	}

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
		return randomString(null, length);
	}

	/**
	 * 生成长度为0的验证码
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
	 * 获取文件后缀名，不存在则返回null<br>
	 * 
	 * @param fileName 文件名
	 * @return 后缀/null
	 */
	public static String getFileFormat(String fileName) {
		int i = fileName.lastIndexOf(".");
		return i != -1 ? fileName.substring(i, fileName.length()) : null;
	}

	/**
	 * 截取图片后缀，并统一小写化<br>
	 * 如果是图片，则会返回后缀，如果不是返回null
	 * 
	 * @param fileName 文件名
	 * @return 图片后缀/null
	 */
	public static String extractFileNameIsImg(String fileName) {
		String s = getFileFormat(fileName);
		for (int i = 0; i < ARRAY_IMG.length; i++) { if (ARRAY_IMG[i].equalsIgnoreCase(s)) { return ARRAY_IMG[i]; } }
		return null;
	}

	/**
	 * 获取SHA256加密<br>
	 * 如果失败返回原字符串
	 * 
	 * @param string 待加密字符串
	 * @return SHA256加密后字符串/原字符串
	 */
	public static String encoderSHA256(String string) {
		if (string == null) { return null; }
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String hashtext = new BigInteger(1, md.digest(string.getBytes())).toString(16);
			while (hashtext.length() < 32) { hashtext = "0" + hashtext; }
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			return string;
		}
	}

	/**
	 * 获取BASE64加密
	 * 
	 * @param string 待加密字符串
	 * @return BASE64字符串
	 */
	public static String encoderBASE64(String string) {
		return Base64.getEncoder().encodeToString(string.getBytes());
	}

	/**
	 * 对BASE64加密进行解密
	 * 
	 * @param string 待解密字符串
	 * @return 解密后字符串
	 */
	public static String decoderBASE64(String string) {
		return new String(Base64.getDecoder().decode(string));
	}

	/**
	 * 替换字符串中全部的空格
	 * 
	 * @param str 待替换字符串
	 * @return 替换后的字符串
	 */
	public static String replaceStringSpace(String str) {
		if (str != null) {
			str.replaceAll(EL_SPACE, STRING_NULL);
			str = str.length() > 0 ? str : null;
		}
		return str;
	}

	/**
	 * 清除字符串收尾空格，会清除掉\t，需要注意
	 * 
	 * @param str 待替换字符串
	 * @return 替换后字符串
	 */
	public static String trim(String str) {
		if (str != null) {
			int start = 0;
			int end = str.length() - 1;
			// 正向查找
			while (true) {
				if (!charInArray(str.charAt(start), SPACE_CHARS)) { break; }
				start++;
				// 如果没有匹配到其他字符，直接返回null
				if (start > end) { return null; }
			}
			// 反向查找
			while (true) {
				if (!charInArray(str.charAt(end), SPACE_CHARS)) { break; }
				end--;
				// 反向查找如何小于正向查找，直接终止
				if (start > end) { break; }
			}
			str = str.substring(start, end + 1);
			str = str.length() > 0 ? str : null;
		}
		return str;
	}

	/**
	 * 用*隐藏邮箱，默认显示3位<br>
	 * 
	 * @param mail 待隐藏油箱字符串
	 * @return 用户*隐藏的邮箱
	 */
	public static String mailHide(String mail) {
		return mailHide(mail, 3);
	}

	/**
	 * 隐藏邮箱，根据自定义位数
	 * 
	 * @param mail  待隐藏油箱字符串
	 * @param index 显示的字符数量
	 * @return 用户*隐藏的邮箱
	 */
	public static String mailHide(String mail, int index) {
		// 默认显示字符数量为3
		index = index < 1 ? 3 : index;
		if (matchEmail(mail)) {
			int end = mail.indexOf("@");
			String less = mail.substring(end);
			String head = mail.substring(0, end);
			if (index > head.length()) { index = head.length() - 1; }
			int l = head.length() - index;
			StringBuilder stringBuilder = new StringBuilder();
			// 截取前index个显示字符
			stringBuilder.append(head.substring(0, index));
			for (int i = 0; i < l; i++) { stringBuilder.append("*"); }
			stringBuilder.append(less);
			mail = stringBuilder.toString();
		}
		return mail;
	}

	/**
	 * 隐藏手机号
	 * 
	 * @param phone 待隐藏的手机号
	 * @return 隐藏后的手机号
	 */
	public static String phoneHide(String phone) {
		if (matchPhone(phone)) { phone = phone.substring(0, 3) + "*****" + phone.substring(8, 11); }
		return phone;
	}

	/**
	 * 比较两个字符串，允许传入Null
	 * 
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return true:相同/false:不同
	 */
	public static boolean eqString(String str1, String str2) {
		return str1 != null ? str1.equals(str2) : false;
	}

	/**
	 * 不区分大小写比较两个字符串，允许null
	 * 
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return true:相同/false:不同
	 */
	public static boolean eqIgnoreCase(String str1, String str2) {
		return str1 != null ? str1.equalsIgnoreCase(str2) : false;
	}

	/**
	 * 比较两个Object，允许为Null,Object自动调用toString方法
	 * 
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return true:相同/false:不同
	 */
	public static boolean eqIgnoreCase(Object obj1, Object obj2) {
		return eqIgnoreCase(obj1 == null ? null : obj1.toString(), obj2 == null ? null : obj2.toString());
	}

	/**
	 * 比较两个Integer包装类
	 * 
	 * @param a Integer包装类
	 * @param b Integer包装类
	 * @return true:相同/false:不同
	 */
	public static boolean eqInteger(Integer a, Integer b) {
		return a != null ? a.equals(b) : false;
	}

	/**
	 * 判断字符串是否存在List中
	 * 
	 * @param string 需要查找的字符串
	 * @param list   带查找集合
	 * @return true:存在/false:不存在
	 */
	public static boolean stringInList(String string, List<String> list) {
		if (list != null) { for (String str : list) { if (eqString(string, str)) { return true; } } }
		return false;
	}

	/**
	 * 判断字符串是否存在数组中
	 * 
	 * @param string 需要查找的字符串
	 * @param str[]  待查找数组
	 * @return true:存在/false:不存在
	 */
	public static boolean stringInArray(String string, String str[]) {
		if (str != null) { for (String st : str) { if (eqString(string, st)) { return true; } } }
		return false;
	}

	/**
	 * 如果数组中的字符串，出现在字符串中，则返回true，否则返回false<br>
	 * 不区分大小写
	 * 
	 * @param arr  要查找的多个字符串
	 * @param data 待校验的字符串
	 * @return true:存在/false:不存在
	 */
	public static boolean stringArrayInString(String arr[], String data) {
		// 如果数组为空，则要匹配的不存在，所以返回空
		if (arr == null) { return true; }
		if (data == null) { return false; }
		data = data.toLowerCase();
		for (String str : arr) { if (data.indexOf(str) != -1) { return true; } }
		return false;
	}

	/**
	 * 如果列表中的字符串，出现在字符串中，则返回true，否则返回false<br>
	 * 不区分大小写
	 * 
	 * @param list 要查找的多个字符串
	 * @param data 待校验的字符串
	 * @return true:存在/false:不存在
	 */
	public static boolean listInString(List<String> list, String data) {
		if (list == null) { return true; }
		if (data == null) { return false; }
		data = data.toLowerCase();
		for (String str : list) { if (data.indexOf(str) != -1) { return true; } }
		return false;
	}

	/**
	 * 判断字符是在字符数组中出现过
	 * 
	 * @param chr   待判断字符
	 * @param chars 要判断在其中出现的数组
	 * @return true:存在/false:不存在
	 */
	public static boolean charInArray(char chr, char[] chars) {
		if (chars != null) { for (char c : chars) { if (c == chr) { return true; } } }
		return false;
	}

	/**
	 * 判断字符串是否等于Null或空
	 * 
	 * @param str 待比较字符串
	 * @return true:符合/false:不符合
	 */
	public static boolean isNullOrZero(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 对未编码的字符串进行编码，如果失败返回原串
	 * 
	 * @param url 字符串
	 * @return 编码后的字符串
	 */
	public static String encoderURL(String url) {
		try {
			return URLEncoder.encode(url, CHAR_CODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * 对编码的字符串进行解密，如果失败返回原串
	 * 
	 * @param url 字符串
	 * @return 解码后的字符串
	 */
	public static String decoderURL(String url) {
		try {
			return URLDecoder.decode(url, CHAR_CODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * 将多个字符拼接成一个，默认\t区分
	 * 
	 * @param all 多个参数
	 * @return 拼接后的字符串
	 */
	public static String spliceString(Object... all) {
		if (all == null || all.length == 0) { return null; }
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < all.length; i++) {
			if (i != all.length - 1) {
				stringBuilder.append(all[i]).append("\t");
			} else {
				stringBuilder.append(all[i]);
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 链接多个字符串，第一个参数为连接符
	 * 
	 * @param joiner 连接的字符
	 * @param all    拼接的字符串
	 * @return 拼接后的字符串
	 */
	public static String spliceStringJoiner(String joiner, Object... all) {
		if (all == null || all.length == 0) { return null; }
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < all.length; i++) {
			if (i != all.length - 1) {
				stringBuilder.append(all[i]).append(joiner);
			} else {
				stringBuilder.append(all[i]);
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 打印所有对象
	 * 
	 * @param all 多个参数对象
	 */
	public static void print(Object... all) {
		System.out.println(spliceString(all));
	}

	/**
	 * 首字母大写
	 * 
	 * @param string 待转换字符串
	 * @return 新字符串
	 */
	public static String upperFirst(String string) {
		if (string != null && string.length() > 0) {
			if (string.length() == 1) { return upperFirst(string.charAt(0)); }
			return upperFirst(string.charAt(0)) + string.substring(1);
		}
		return null;
	}

	/**
	 * 单个字符串是否位大写
	 * 
	 * @param c 待判断字符
	 * @return true:是/false:不是
	 */
	public static boolean charIsUpper(char c) {
		return c >= 'A' && c <= 'Z';
	}

	/**
	 * 单个字符串是否位小写
	 * 
	 * @param c 待判断字符
	 * @return true:是/false:不是
	 */
	public static boolean charIsLower(char c) {
		return c >= 'a' && c <= 'z';
	}

	/**
	 * 首字母大写
	 * 
	 * @param char 待转换字符
	 * @return 大写字符/非字母则返回原值
	 */
	public static String upperFirst(char c) {
		return String.valueOf(charIsLower(c) ? c + 32 : c);
	}

	/**
	 * 首字母小写
	 * 
	 * @param char 待转换字符
	 * @return 小写字符/非字母则返回原值
	 */
	public static String lowerFirst(char c) {
		return String.valueOf(charIsUpper(c) ? c - 32 : c);
	}

	/**
	 * 首字母小写
	 * 
	 * @param string 待转换字符串
	 * @return 新字符串
	 */
	public static String lowerFirst(String string) {
		if (!isNullOrZero(string)) {
			if (string.length() == 1) { return lowerFirst(string.charAt(0)); }
			return lowerFirst(string.charAt(0)) + string.substring(1);
		}
		return null;
	}

	/**
	 * 下划线转小驼峰
	 * 
	 * @param string 下划线规则字符串
	 * @return 小驼峰字符串
	 */
	public static String underscoreToSmallHump(String string) {
		if (!isNullOrZero(string)) {
			String s[] = string.split("_");
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < s.length; i++) { stringBuilder.append(i == 0 ? lowerFirst(s[i]) : upperFirst(s[i])); }
			return stringBuilder.toString();
		}
		return null;
	}

	/**
	 * 下划线转大驼峰
	 * 
	 * @param string 下划线规则字符串
	 * @return 大驼峰字符串
	 */
	public static String underscoreToGigHump(String string) {
		if (!isNullOrZero(string)) {
			String s[] = string.split("_");
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < s.length; i++) { stringBuilder.append(upperFirst(s[i])); }
			return stringBuilder.toString();
		}
		return null;
	}

	/**
	 * 驼峰转下划线
	 * 
	 * @param string 驼峰字符串
	 * @return 下划线规则字符串
	 */
	public static String humpToUnderscore(String string) {
		if (!isNullOrZero(string)) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < string.length(); i++) {
				if (charIsUpper(string.charAt(i))) {
					stringBuilder.append("_");
					stringBuilder.append(lowerFirst(string.charAt(i)));
				} else {
					stringBuilder.append(string.charAt(i));
				}
			}
			return stringBuilder.toString();
		}
		return null;
	}

	/**
	 * 移除字符串后缀
	 * 
	 * @param string 原串
	 * @param prefix 后缀
	 * @return 移除后缀的字符串
	 */
	public static String removePrefix(String string, String prefix) {
		if (isNullOrZero(string)) { return null; }
		if (isNullOrZero(prefix) || prefix.length() > string.length()) { return string; }
		String s = string.substring(string.length() - prefix.length(), string.length());
		if (eqString(s, prefix)) { return string.substring(0, string.length() - prefix.length()); }
		return string;
	}

	/**
	 * 移除字符串后缀，不区分大小写
	 * 
	 * @param string 原串
	 * @param prefix 后缀
	 * @return 移除后缀的字符串
	 */
	public static String removePrefixNoIgnoreCase(String string, String prefix) {
		if (isNullOrZero(string)) { return null; }
		if (isNullOrZero(prefix) || prefix.length() > string.length()) { return string; }
		String s = string.substring(string.length() - prefix.length(), string.length());
		if (eqIgnoreCase(s, prefix)) { return string.substring(0, string.length() - prefix.length()); }
		return string;
	}

	/**
	 * 移除字符串前缀
	 * 
	 * @param string 原串
	 * @param prefix 前缀
	 * @return 移除前缀的字符串
	 */
	public static String removeTail(String string, String tail) {
		if (isNullOrZero(string)) { return null; }
		if (isNullOrZero(tail) || tail.length() > string.length()) { return string; }
		String s = string.substring(0, tail.length());
		if (eqString(s, tail)) { return string.substring(string.length(), -string.length()); }
		return string;
	}

	/**
	 * 移除字符串前缀，不区分大小写
	 * 
	 * @param string 原串
	 * @param prefix 前缀
	 * @return 移除前缀的字符串
	 */
	public static String removeTailNoIgnoreCase(String string, String tail) {
		if (isNullOrZero(string)) { return null; }
		if (isNullOrZero(tail) || tail.length() > string.length()) { return string; }
		String s = string.substring(0, tail.length());
		if (eqIgnoreCase(s, tail)) { return string.substring(string.length(), -string.length()); }
		return string;
	}

	/**
	 * 隐藏字符串，前后个展示几位
	 *
	 * @param str   源字符串
	 * @param start 起始字符串
	 * @param end   结束字符串
	 * @return 隐藏后的字符串
	 */
	public static String hide(String str, int start, int end) {
		return hide(str, start, end, 0);
	}

	/**
	 * 隐藏字符串，前后个展示几位
	 *
	 * @param str 源字符串
	 * @return 隐藏后的字符串
	 */
	public static String hide(String str) {
		return hide(str, 1, 1);
	}

	/***
	 * 隐藏字符串，不够就有最小隐藏长度
	 * 
	 * @param str   源字符串
	 * @param start 起始数量
	 * @param end   结束数量
	 * @param hide  隐藏最小间隔
	 * @return 匿名后的字符串
	 */
	public static String hide(String str, int start, int end, int hide) {
		if (isNullOrZero(str)) { return null; }
		// 默认显示字符数量为3
		start = start < 0 ? 1 : start;
		end = end < 0 ? 1 : end;
		hide = hide < 0 ? 0 : hide;
		// 开头和结束所需要字符串相等则返回原串
		if (start + end >= str.length()) {
			start = str.length();
			end = 0;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(str.substring(0, start));
		for (int i = 0; i < str.length() - (start + end) + hide; i++) { stringBuilder.append("*"); }
		stringBuilder.append(str.substring(str.length() - end));
		return stringBuilder.toString();
	}

	/***
	 * 隐藏字符串，任何字符串都是同样的隐藏长度
	 * 
	 * @return 匿名后的字符串
	 */
	public static String hideSafety(String str) {
		return hideSafety(str, 1, 0, 7);
	}

	/***
	 * 隐藏字符串，任何字符串都是同样的隐藏长度
	 * 
	 * @param str   源字符串
	 * @param start 起始数量
	 * @param end   结束数量
	 * @param hide  隐藏最小间隔
	 * @return 匿名后的字符串
	 */
	public static String hideSafety(String str, int start, int end, int hide) {
		if (isNullOrZero(str)) { return null; }
		// 默认显示字符数量为3
		start = start < 0 ? 1 : start;
		end = end < 0 ? 1 : end;
		hide = hide < 0 ? 0 : hide;
		// 开头和结束所需要字符串相等则返回原串
		if (start + end >= str.length()) {
			start = str.length();
			end = 0;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(str.substring(0, start));
		for (int i = 0; i < hide; i++) { stringBuilder.append("*"); }
		stringBuilder.append(str.substring(str.length() - end));
		return stringBuilder.toString();
	}
}
