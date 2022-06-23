package chiya.core.base.stream;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chiya.core.base.number.NumberUitl;
import chiya.core.base.page.Page;

/**
 * 通用基础流操作
 * 
 * @author chiya
 * @param <T> 泛型参数
 * @param <E> 继承的类名
 */
@SuppressWarnings("unchecked")
public abstract class BaseStream<T, E> {

	protected Stream<T> stream;

	/**
	 * 流式操作
	 */
	public E stream(List<T> list) {
		stream = list.stream();
		return (E) this;
	}

	/**
	 * 对流进行分页
	 * 
	 * @param page 分页对象
	 * @return Stream<T> 操作流
	 */
	public E page(Page page) {
		stream = stream.skip(page.getStart()).limit(page.getCount());
		return (E) this;
	}

	/**
	 * 转换成集合
	 * 
	 * @return List<T> 集合对象
	 */
	public List<T> toList() {
		return stream.collect(Collectors.toList());
	}

	/**
	 * 转换成集合，并制动装配页数
	 * 
	 * @param page 分页对象
	 * @return List<T> 集合对象
	 */
	public List<T> toList(Page page) {
		List<T> list = stream.collect(Collectors.toList());
		if (page != null) { page.setMax(list.size()); }
		return list;
	}

	/**
	 * 获取并分页操作
	 * 
	 * @param page 分页对象
	 * @return List<T> 集合对象
	 */
	public List<T> toListPage(Page page) {
		List<T> list = stream.collect(Collectors.toList());
		if (page != null) { page.setMax(list.size()); }
		return list.stream().skip(page.getStart()).limit(page.getCount()).collect(Collectors.toList());
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sort(Function<? super T, Integer> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSize(method.apply(a), method.apply(b)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortNullIsMax(Function<? super T, Integer> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSizeNullIsMax(method.apply(a), method.apply(b)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortDesc(Function<? super T, Integer> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSizeDesc(method.apply(a), method.apply(b)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortDescNullIsMax(Function<? super T, Integer> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSizeDescNullIsMax(method.apply(a), method.apply(b)));
		return (E) this;
	}
}
