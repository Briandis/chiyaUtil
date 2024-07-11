package chiya.core.base.object;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * 标准jar文件字节数组类加载器<br>
 * 
 * @author chiya
 *
 */
public class JarClassLoader extends ClassLoader {
	/** 类加载器缓存对象 */
	private static final ConcurrentHashMap<String, byte[]> CLASS_MAP = new ConcurrentHashMap<>();

	/**
	 * 构造方法
	 * 
	 * @param jarBytes jar文件字节数组
	 */
	public JarClassLoader(byte[] jarBytes) {
		try (JarInputStream jarInputStream = new JarInputStream(new ByteArrayInputStream(jarBytes))) {
			readZipClass(jarInputStream);
		} catch (IOException e) {
			throw new RuntimeException("读取jar文件时发生错误，可能是非标准的jar", e);
		}
	}

	/**
	 * 构造方法
	 * 
	 * @param jarBytes jar文件路径
	 */
	public JarClassLoader(String jarPath) {
		try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(jarPath))) {
			readZipClass(jarInputStream);
		} catch (IOException e) {
			throw new RuntimeException("读取jar文件时发生错误，可能是非标准的jar", e);
		}

	}

	/**
	 * 读取JAR文件中的class
	 * 
	 * @param jarInputStream JAR的输入流
	 * @throws IOException IO异常
	 */
	private void readZipClass(JarInputStream jarInputStream) throws IOException {
		ZipEntry entry;
		while ((entry = jarInputStream.getNextEntry()) != null) {
			System.out.println(entry.getName());
			if (entry.getName().endsWith(".class")) {
				String className = entry.getName().replace("/", ".").substring(0, entry.getName().length() - 6);
				byte[] classBytes = jarInputStream.readAllBytes();
				CLASS_MAP.put(className, classBytes);
			}
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] bytes = CLASS_MAP.get(name);
		if (bytes == null) { throw new ClassNotFoundException(name); }
		return defineClass(name, bytes, 0, bytes.length);
	}
}
