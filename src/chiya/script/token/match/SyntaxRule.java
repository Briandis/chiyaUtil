package chiya.script.token.match;

/**
 * 语法规则
 * 
 * @author chiya
 *
 */
public class SyntaxRule {

	/** 解析规则 */
	private ParserConfig config;
	/** 匹配规则 */
	private SyntaxMatch syntaxMatch;

	/**
	 * 获取解析规则
	 * 
	 * @return 解析规则
	 */
	public ParserConfig getConfig() {
		return config;
	}

	/**
	 * 设置解析规则
	 * 
	 * @param config 解析规则
	 */
	public void setConfig(ParserConfig config) {
		this.config = config;
	}

	/**
	 * 链式添加解析规则
	 * 
	 * @param config 解析规则
	 * @return 对象本身
	 */
	public SyntaxRule chainConfig(ParserConfig config) {
		setConfig(config);
		return this;
	}

	/**
	 * 获取匹配规则
	 * 
	 * @return 匹配规则
	 */
	public SyntaxMatch getSyntaxMatch() {
		return syntaxMatch;
	}

	/**
	 * 设置匹配规则
	 * 
	 * @param syntaxMatch 匹配规则
	 */
	public void setSyntaxMatch(SyntaxMatch syntaxMatch) {
		this.syntaxMatch = syntaxMatch;
	}

	/**
	 * 链式添加匹配规则
	 * 
	 * @param syntaxMatch 匹配规则
	 * @return 对象本身
	 */
	public SyntaxRule chainSyntaxMatch(SyntaxMatch syntaxMatch) {
		setSyntaxMatch(syntaxMatch);
		return this;
	}

}
