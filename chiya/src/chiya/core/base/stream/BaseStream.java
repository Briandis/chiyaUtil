package chiya.core.base.stream;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chiya.core.base.function.ValueObjectFunction;
import chiya.core.base.number.NumberUitl;
import chiya.core.base.page.Page;
import chiya.core.base.time.DateUtil;

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
	 * 构建流
	 * 
	 * @param list list参数构建流
	 * @return 对象自身
	 */
	public E stream(List<T> list) {
		stream = list.stream();
		return (E) this;
	}

	/**
	 * 构建流
	 * 
	 * @param stream 已有流式操作
	 * @return 对象自身
	 */
	public E stream(Stream<T> stream) {
		this.stream = stream;
		return (E) this;
	}

	/**
	 * 获取流
	 * 
	 * @return Stream<T> 存储的流
	 */
	public Stream<T> getStream() {
		return stream;
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

	/* Number类型 */
	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sort(Function<? super T, ? extends Number> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSize(method.apply(a), method.apply(b)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortNullIsMax(Function<? super T, ? extends Number> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSizeNullIsMax(method.apply(a), method.apply(b)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortDesc(Function<? super T, ? extends Number> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSize(method.apply(b), method.apply(a)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortDescNullIsMax(Function<? super T, ? extends Number> method) {
		stream = stream.sorted((a, b) -> NumberUitl.compareSizeNullIsMax(method.apply(b), method.apply(a)));
		return (E) this;
	}

	/* Date类型 */
	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sort(ValueObjectFunction<? super T, ? extends Date> method) {
		stream = stream.sorted((a, b) -> DateUtil.compareSize(method.get(a), method.get(b)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortNullIsMax(ValueObjectFunction<? super T, ? extends Date> method) {
		stream = stream.sorted((a, b) -> DateUtil.compareSizeNullIsMax(method.get(a), method.get(b)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortDesc(ValueObjectFunction<? super T, ? extends Date> method) {
		stream = stream.sorted((a, b) -> DateUtil.compareSize(method.get(b), method.get(a)));
		return (E) this;
	}

	/**
	 * 根据传入字段排序
	 * 
	 * @param method 排序的字段
	 * @return Stream<T> 操作流
	 */
	public E sortDescNullIsMax(ValueObjectFunction<? super T, ? extends Date> method) {
		stream = stream.sorted((a, b) -> DateUtil.compareSizeNullIsMax(method.get(b), method.get(a)));
		return (E) this;
	}

	/**
	 * 过滤条件
	 * 
	 * @param predicate 过滤条件
	 * @return Stream<T> 操作流
	 */
	public E filter(Predicate<? super T> predicate) {
		stream = stream.filter(predicate);
		return (E) this;
	}

}
