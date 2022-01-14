package chiya.core.base.page;

/**
 * 分页相关类
 * 
 * @author chiya
 */
public class Page {

	/** 起始数据行数 */
	private int start = 0;
	/** 默认分页大小 */
	private int count = 10;
	/** 最大数据行数 */
	private int max;
	/** 当前页数 */
	private int page = 1;
	/** 最大页数 */
	private int maxPage;

	/**
	 * 获取起始位置下标
	 * 
	 * @return 起始位置
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 设置起始位置
	 * 
	 * @param start 起始位置
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 获取每页数量
	 * 
	 * @return 每页数量
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置每页数量
	 * 
	 * @param count 每页数量
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public int getMax() {
		return max;
	}

	/**
	 * 根据传入的总数量，自动计算总页数
	 * 
	 * @param max 总记录数
	 */
	public void setMax(int max) {
		this.max = max;
		this.maxPage = maxToMaxPage(max, count);
	}

	/**
	 * 获取当前页数
	 * 
	 * @return 当前页数
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 根据传入的当前页数，自动计算起始行数
	 * 
	 * @param page 当前页数
	 */
	public void setPage(int page) {
		this.page = page > 0 ? page : 1;
		this.start = this.count * (this.page - 1);
	}

	/**
	 * 获取最大页数
	 * 
	 * @return 最大页数
	 */
	public int getMaxPage() {
		return maxPage;
	}

	/**
	 * 设置最大页数
	 * 
	 * @param maxPage 最大页数
	 */
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"start\":");
		builder.append(start);
		builder.append(", \"count\":");
		builder.append(count);
		builder.append(", \"max\":");
		builder.append(max);
		builder.append(", \"page\":");
		builder.append(page);
		builder.append(", \"maxPage\":");
		builder.append(maxPage);
		builder.append("}");
		return builder.toString();
	}

	/**
	 * 以10为分页大小，计算总页数
	 * 
	 * @param max 总计数量
	 * @return maxPage:计算后的总页数
	 */
	public static int maxToMaxPage(int max) {
		return maxToMaxPage(max, 10);
	}

	/**
	 * 根据传入的总数量和分页大小，计算总页数<br>
	 * 如果分页大小小于1则取默认值10
	 * 
	 * @param max   总记录数
	 * @param count 分页大小
	 * @return maxPage:计算后的总页数
	 */
	public static int maxToMaxPage(int max, int count) {
		count = count < 1 ? 10 : count;
		return (max / count) + (max % count == 0 ? 0 : 1);
	}

	/**
	 * 链式操作设置起始位置
	 * 
	 * @param start 起始位置
	 * @return 自身
	 */
	public Page chainStart(int start) {
		setStart(start);
		return this;
	}

	/**
	 * 链式操作设置每页数量
	 * 
	 * @param count 每页数量
	 * @return 自身
	 */
	public Page chainCount(int count) {
		setCount(count);
		return this;
	}

	/**
	 * 链式操作根据传入的总数量，自动计算总页数
	 * 
	 * @param max 总记录数
	 * @return 自身
	 */
	public Page chainMax(int max) {
		setMax(max);
		return this;
	}

	/**
	 * 链式操作根据传入的当前页数，自动计算起始行数
	 * 
	 * @param page 当前页数
	 * @return 自身
	 */
	public Page chainPage(int page) {
		setPage(page);
		return this;
	}

	/**
	 * 链式操作设置最大页数
	 * 
	 * @param maxPage 最大页数
	 * @return 自身
	 */
	public Page chainMaxPage(int maxPage) {
		setMaxPage(maxPage);
		return this;
	}
}
