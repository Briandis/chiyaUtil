package chiya.core.base.stream;

import java.util.List;
import java.util.stream.Stream;

/**
 * 默认BaseStream的实现类
 * 
 * @author brain
 *
 * @param <T> 要进行流式操作的类型
 */
public class ChiyaStream<T> extends BaseStream<T, ChiyaStream<T>> {

	/** 无参构造 */
	public ChiyaStream() {}

	/**
	 * 传入list直接构造流操作对象
	 * 
	 * @param list 流式操作对象
	 */
	public ChiyaStream(List<T> list) {
		stream(list);
	}

	/**
	 * 传入流进行构造
	 * 
	 * @param stream 流
	 */
	public ChiyaStream(Stream<T> stream) {
		stream(stream);
	}
}
