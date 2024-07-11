package chiya.core.base.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * IO工具类
 * 
 * @author chiya
 */
public class IOUtil {
	/**
	 * 读取全部的字节内容<br>
	 * 
	 * @param inputStream 输入流
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[8192]; // 缓冲区大小
			int bytesRead;
			// 读取并写入数据
			while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, bytesRead);
			}
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return stringBuilder.toString();
	}
}
