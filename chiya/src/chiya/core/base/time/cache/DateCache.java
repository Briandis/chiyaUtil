package chiya.core.base.time.cache;

import chiya.core.base.time.DateUtil;

/**
 * 日期缓存对象<br>
 * 获取的时间格式为：2022-07-01
 */
public class DateCache extends TimeCacheFormat {

	@Override
	String getFormatExpression() {
		return DateUtil.DATE;
	}

	@Override
	boolean isUpdateCache() {
		// 如果天数不同，则更新
		return System.currentTimeMillis() / DateUtil.ONE_DAY_TIME != currentTime / DateUtil.ONE_DAY_TIME;
	}

}
