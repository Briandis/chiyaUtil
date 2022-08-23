package chiya.log.handle;

import chiya.log.handle.base.BaseLog;

/**
 * 日志处理器
 */
public class HandleLog extends BaseLog {

	@Override
	public void point(Object message) {
		log(getLevel(0), message);

	}

	@Override
	public void info(Object message) {
		log(getLevel(1), message);

	}

	@Override
	public void warn(Object message) {
		log(getLevel(2), message);

	}

	@Override
	public void error(Object message) {
		log(getLevel(3), message);
	}

}
