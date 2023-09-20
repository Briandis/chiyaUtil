package chiya.core.base.time.cache;

import chiya.core.base.time.DateUtil;

/**
 * 完整时间对象<br>
 * 获取的时间格式为：2022-07-01 12:31:46.233
 */
public class FullDateTimeCache extends TimeCacheFormat {

	@Override
	String getFormatExpression() {
		return DateUtil.FULL_DATE_TIME;
	}

	@Override
	boolean isUpdateCache() {
		// 不等于当前时间则更新
		return System.currentTimeMillis() != currentTime;
	}
}
