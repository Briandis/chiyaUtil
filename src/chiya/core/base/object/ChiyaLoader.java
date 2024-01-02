package chiya.core.base.object;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import chiya.core.base.loop.Loop;

/**
 * 类加载工具
 * 
 * @author brian
 */
public class ChiyaLoader {
	/**
	 * 加载这个包下的所有类
	 * 
	 * @param pack 要扫描的包名
	 * @return List<Class> 加载的类集合
	 * @throws IOException
	 */
	public List<Class<?>> loadAllClass(String pack) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> enumeration = classLoader.getResources(pack.replace(".", "/"));
		List<String> listPack = new ArrayList<String>();
		List<Class<?>> listClass = new ArrayList<Class<?>>();
		while (enumeration.hasMoreElements()) {
			URL url = (URL) enumeration.nextElement();
			listPack.addAll(scanerAllClass(url.getFile(), pack));
		}
		listPack.forEach(
			o -> {
				try {
					listClass.add(classLoader.loadClass(o));
				} catch (ClassNotFoundException e) {}
			}
		);

		return listClass;
	}

	/**
	 * 获取所有的class文件
	 * 
	 * @param path     文件路径
	 * @param packRoot 当前包名
	 * @return 文件列表
	 */
	public List<String> scanerAllClass(String path, String packRoot) {
		List<String> listFile = new ArrayList<String>();
		File file = new File(path);
		ArrayDeque<String> stack = new ArrayDeque<>();
		ArrayDeque<String> pack = new ArrayDeque<>();
		if (file.isDirectory()) {
			Loop.forEach(
				file.list(),
				f -> {
					stack.push(file.getPath() + "\\" + f);
					pack.push(f);
				}
			);
		}
		while (stack.size() != 0) {
			// 全路径
			String root = stack.pop();
			// 包
			String nowPack = pack.pop();
			File nowFile = new File(root);
			if (nowFile.isFile()) { listFile.add(packRoot + "." + nowPack.replace(".class", "")); }
			if (nowFile.isDirectory()) {
				Loop.forEach(
					nowFile.list(),
					f -> {
						stack.push(nowFile.getPath() + "\\" + f);
						pack.push(nowPack + "." + f);
					}
				);
			}
		}
		return listFile;
	}
}
