package chiya.server;

import chiya.core.base.random.RandomString;

/**
 * RPC头部报文
 * 
 * @author chiya
 *
 */
public class ChiyaRpcHead {
	/** 报文类型 */
	private String type = "request";
	/** 访问的URL */
	private String url;
	/** 追踪标识 */
	private String key = RandomString.randomStringByUUID();
	/** 响应回调端口 */
	private int port;
	/** 是否需要回应 */
	private boolean result = false;
	/** JSON交互形式 */
	private boolean json = true;

	/**
	 * 封包
	 * 
	 * @return
	 */
	public String toPack() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("type=")
			.append(type)
			.append("\r\nurl=")
			.append(url)
			.append("\r\nkey=")
			.append(key)
			.append("\r\nport=")
			.append(port)
			.append("\r\nresult=")
			.append(result)
			.append("\r\njson=")
			.append(json);
		return stringBuilder.toString();
	}

	/**
	 * 获取报文类型
	 * 
	 * @return 报文类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置报文类型
	 * 
	 * @param type 报文类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 链式添加报文类型
	 * 
	 * @param type 报文类型
	 * @return 对象本身
	 */
	public ChiyaRpcHead chainType(String type) {
		setType(type);
		return this;
	}

	/**
	 * 获取访问的URL
	 * 
	 * @return 访问的URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置访问的URL
	 * 
	 * @param url 访问的URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 链式添加访问的URL
	 * 
	 * @param url 访问的URL
	 * @return 对象本身
	 */
	public ChiyaRpcHead chainUrl(String url) {
		setUrl(url);
		return this;
	}

	/**
	 * 获取追踪标识
	 * 
	 * @return 追踪标识
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置追踪标识
	 * 
	 * @param key 追踪标识
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 链式添加追踪标识
	 * 
	 * @param key 追踪标识
	 * @return 对象本身
	 */
	public ChiyaRpcHead chainKey(String key) {
		setKey(key);
		return this;
	}

	/**
	 * 获取响应回调端口
	 * 
	 * @return 响应回调端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置响应回调端口
	 * 
	 * @param port 响应回调端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 链式添加响应回调端口
	 * 
	 * @param port 响应回调端口
	 * @return 对象本身
	 */
	public ChiyaRpcHead chainPort(int port) {
		setPort(port);
		return this;
	}

	/**
	 * 获取是否需要回应
	 * 
	 * @return 是否需要回应
	 */
	public boolean getResult() {
		return result;
	}

	/**
	 * 设置是否需要回应
	 * 
	 * @param result 是否需要回应
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * 链式添加是否需要回应
	 * 
	 * @param result 是否需要回应
	 * @return 对象本身
	 */
	public ChiyaRpcHead chainResult(boolean result) {
		setResult(result);
		return this;
	}

	/**
	 * 获取JSON交互形式
	 * 
	 * @return JSON交互形式
	 */
	public boolean getJson() {
		return json;
	}

	/**
	 * 设置JSON交互形式
	 * 
	 * @param json JSON交互形式
	 */
	public void setJson(boolean json) {
		this.json = json;
	}

	/**
	 * 链式添加JSON交互形式
	 * 
	 * @param json JSON交互形式
	 * @return 对象本身
	 */
	public ChiyaRpcHead chainJson(boolean json) {
		setJson(json);
		return this;
	}

}
