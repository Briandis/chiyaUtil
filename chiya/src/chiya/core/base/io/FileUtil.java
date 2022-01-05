package chiya.core.base.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件工具库 最后修改时间 2021-08-04
 * 
 * @author Brian
 * @version 1.0.1
 */
public class FileUtil {

	/**
	 * 根据文件路径创建父级文件夹
	 * 
	 * @param fileName 文件路径
	 */
	public static void createParentDir(String fileName) {
		File file = new File(fileName);
		if (!file.getParentFile().exists()) { file.getParentFile().mkdirs(); }
	}

	/**
	 * 判断是否是windows系统
	 * 
	 * @return true/false
	 */
	public static boolean isWindowsOS() {
		return System.getProperty("os.name").toLowerCase().startsWith("win");
	}

	/**
	 * 保存文件
	 * 
	 * @param data     二进制
	 * @param fileName 文件名全路径
	 * @return true:成功/false:失败
	 */
	public static boolean saveFile(byte[] data, String fileName) {
		createParentDir(fileName);
		boolean b = false;
		try (OutputStream outputStream = new FileOutputStream(fileName); BufferedOutputStream bufferedOutputStrea = new BufferedOutputStream(outputStream)) {
			bufferedOutputStrea.write(data);
			bufferedOutputStrea.flush();
			b = true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return b;
	}

	/**
	 * 保存文件
	 * 
	 * @param data     二进制
	 * @param dirPath  保存的文件夹
	 * @param fileName 文件名
	 * @return true:成功/false:失败
	 */
	public static boolean saveFile(byte[] data, String dirPath, String fileName) {
		return saveFile(data, dirPath + fileName);
	}

	/**
	 * 保存文件
	 * 
	 * @param inputStream inputSream流
	 * @param fileName    文件全路径
	 * @return true:成功/false:失败
	 */
	public static boolean saveFile(InputStream inputStream, String fileName) {
		try {
			return saveFile(readAllBytes(inputStream), fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 保存文件
	 * 
	 * @param inputStream inputSream流
	 * @param dirPath     保存的文件夹
	 * @param fileName    文件名
	 * @return true:成功/false:失败
	 */
	public static boolean saveFile(InputStream inputStream, String dirPath, String fileName) {
		try {
			return saveFile(readAllBytes(inputStream), dirPath, fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param fileName 文件完整路径
	 * @return byte[]:字节数组
	 */
	public static byte[] readFile(String fileName) {
		byte[] bytes = null;
		try (InputStream inputStream = new FileInputStream(fileName); BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
			bytes = readAllBytes(bufferedInputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bytes;
	}

	/**
	 * 读取文件
	 * 
	 * @param dirPath  文件路径
	 * @param fileName 文件名称
	 * @return byte[]:字节数组
	 */
	public static byte[] readFile(String dirPath, String fileName) {
		return readFile(dirPath + fileName);
	}

	/**
	 * 追加文件内容
	 * 
	 * @param data     待追加二进制
	 * @param fileName 文件名全路径
	 * @return true:成功/false:失败
	 */
	public static boolean appendFile(byte[] data, String fileName) {
		createParentDir(fileName);
		boolean b = false;
		// 在括号中的对象实现了AutoCloseable接口，会自动关闭
		try (OutputStream outputStream = new FileOutputStream(fileName, true);
				BufferedOutputStream bufferedOutputStrea = new BufferedOutputStream(outputStream)) {
			bufferedOutputStrea.write(data);
			bufferedOutputStrea.flush();
			b = true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return b;
	}

	/**
	 * 追加文件内容
	 * 
	 * @param data     待追加二进制
	 * @param dirPath  保存的文件夹
	 * @param fileName 文件名
	 * @return true:成功/false:失败
	 */
	public static boolean appendFile(byte[] data, String dirPath, String fileName) {
		return appendFile(data, dirPath + fileName);
	}

	/**
	 * 追加文件内容
	 * 
	 * @param inputStream inputSream流
	 * @param dirPath     保存的文件夹
	 * @param fileName    文件名
	 * @return true:成功/false:失败
	 */
	public static boolean appendFile(InputStream inputStream, String dirPath, String fileName) {
		try {
			return appendFile(readAllBytes(inputStream), dirPath, fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 追加文件内容
	 * 
	 * @param inputStream inputSream流
	 * @param fileName    文件全路径
	 * @return true:成功/false:失败
	 */
	public static boolean appendFile(InputStream inputStream, String fileName) {
		try {
			return appendFile(readAllBytes(inputStream), fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 读取文本
	 * 
	 * @param dirPath  文件路径
	 * @param fileName 文件名称
	 * @return String[]:字节数组
	 */
	public static String readText(String dirPath, String fileName) {
		return readText(dirPath + fileName);
	}

	/**
	 * 读取文本
	 * 
	 * @param fileName 文件路径
	 * @return String
	 */
	public static String readText(String fileName) {
		try (Reader reader = new FileReader(fileName); BufferedReader bufferedReader = new BufferedReader(reader)) {
			char c[] = new char[8192];
			int index = 0;
			StringBuilder stringBuilder = new StringBuilder();
			while ((index = bufferedReader.read(c)) != -1) {
				if (index != 8192) {
					for (int i = 0; i < index; i++) { stringBuilder.append(c[i]); }
				} else {
					stringBuilder.append(c);
				}
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 写入文本
	 * 
	 * @param text     写入的文本
	 * @param fileName 文件名称
	 * @return true:成功/false:失败
	 */
	public static boolean writeText(String text, String fileName) {
		return saveFile(text.getBytes(), fileName);
	}

	/**
	 * 写入文本
	 * 
	 * @param text     写入的文本
	 * @param dirPath  保存的文件夹
	 * @param fileName 文件名称
	 * @return true:成功/false:失败
	 */
	public static boolean writeText(String text, String dirPath, String fileName) {
		return saveFile(text.getBytes(), dirPath + fileName);
	}

	/**
	 * 追加文件内容
	 * 
	 * @param text     待追加文本
	 * @param fileName 文件名全路径
	 * @return true:成功/false:失败
	 */
	public static boolean appendText(String text, String fileName) {
		createParentDir(fileName);
		boolean b = false;
		try (Writer writer = new FileWriter(fileName, true); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
			bufferedWriter.write(text);
			bufferedWriter.flush();
			b = true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return b;
	}

	/**
	 * 写入文本
	 * 
	 * @param text     写入的文本
	 * @param dirPath  保存的文件夹
	 * @param fileName 文件名称
	 * @return true:成功/false:失败
	 */
	public static boolean appendText(String text, String dirPath, String fileName) {
		return appendText(text, dirPath + fileName);
	}

	/**
	 * 读取全部的字节内容<br>
	 * JDK15中，读取全部字节的源码，适配低于15的内容准备
	 * 
	 * @param inputStream 输入流
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
		int len = Integer.MAX_VALUE;
		if (len < 0) { throw new IllegalArgumentException("len < 0"); }

		List<byte[]> bufs = null;
		byte[] result = null;
		int total = 0;
		int remaining = len;
		int n;
		do {
			byte[] buf = new byte[Math.min(remaining, 8192)];
			int nread = 0;

			while ((n = inputStream.read(buf, nread, Math.min(buf.length - nread, remaining))) > 0) {
				nread += n;
				remaining -= n;
			}
			if (nread > 0) {
				if (8192 - total < nread) { throw new OutOfMemoryError("Required array size too large"); }
				if (nread < buf.length) { buf = Arrays.copyOfRange(buf, 0, nread); }
				total += nread;
				if (result == null) {
					result = buf;
				} else {
					if (bufs == null) {
						bufs = new ArrayList<>();
						bufs.add(result);
					}
					bufs.add(buf);
				}
			}
		} while (n >= 0 && remaining > 0);
		if (bufs == null) {
			if (result == null) { return new byte[0]; }
			return result.length == total ? result : Arrays.copyOf(result, total);
		}
		result = new byte[total];
		int offset = 0;
		remaining = total;
		for (byte[] b : bufs) {
			int count = Math.min(b.length, remaining);
			System.arraycopy(b, 0, result, offset, count);
			offset += count;
			remaining -= count;
		}
		return result;
	}
}