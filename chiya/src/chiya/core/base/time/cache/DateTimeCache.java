package chiya.core.base.time.cache;

import chiya.core.base.time.DateUtil;

/**
 * 标准时间对象<br>
 * 获取的时间格式为：2022-07-01 12:31:46
 */
public class DateTimeCache extends TimeCacheFormat {

	@Override
	String getFormatExpression() {
		return DateUtil.DATE_TIME;
	}

	@Override
	boolean isUpdateCache() {
		// 秒不同则更新
		return System.currentTimeMillis() / 1000 != currentTime / 1000;
	}

}
