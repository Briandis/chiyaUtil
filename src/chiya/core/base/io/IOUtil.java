package chiya.core.base.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * IO工具类
 * 
 * @author chiya
 */
public class IOUtil {
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

	/**
	 * 读取全部的字符串
	 * 
	 * @param inputStream 输入流
	 * @return 字符串
	 */
	public static String readAllString(InputStream inputStream) {
		StringBuilder stringBuilder = new StringBuilder();
		try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
			String line = "";
			while ((line = bufferedReader.readLine()) != null) { stringBuilder.append(line); }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return stringBuilder.toString();
	}
}
