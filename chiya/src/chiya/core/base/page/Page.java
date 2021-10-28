package chiya.core.base.page;

/**
 * 分页相关类
 * 
 * @author Brian
 * @version 1.0.0
 * 
 */
public class Page {

	/**
	 * 起始数据行数
	 */
	private int start = 0;
	/**
	 * 默认分页大小
	 */
	private int count = 10;
	/**
	 * 最大数据行数
	 */
	private int max;
	/**
	 * 当前页数
	 */
	private int page = 1;
	/**
	 * 最大页数
	 */
	private int maxPage;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

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

	public int getMaxPage() {
		return maxPage;
	}

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
}
