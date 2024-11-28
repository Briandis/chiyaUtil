package chiya.script.factory;

import chiya.script.token.match.LexicalFactor;
import chiya.script.token.match.ParserConfig;
import chiya.script.token.match.SyntaxFactor;

/**
 * 语法模板
 * 
 * @author chiya
 *
 */
public class SyntaxComponent {

	/**
	 * 需要递归
	 * 
	 * @return
	 */
	public static ParserConfig recursion() {
		return new ParserConfig().chainNeedRecursion(true);
	}

	/**
	 * 成对的
	 * 
	 * @param name  类型名称
	 * @param start 开始
	 * @param end   结束
	 * @return 语法
	 */
	public static SyntaxFactor paired(String name, String start, String end) {
		return new SyntaxFactor(name)
			.addLexical(new LexicalFactor().chainType("token").chainStart(start))
			.addLexical(new LexicalFactor().chainType("token").chainStart(end))
			.chainNeedMatch(true)
			.chainNeedPaired(true);
	}

	/**
	 * 合并
	 * 
	 * @param name  名称
	 * @param types 类型
	 * @return 语法
	 */
	public static SyntaxFactor merge(String name, String... types) {
		return new SyntaxFactor(name)
			.chainNeedMerge(true)
			.chainFatherIndex(0)
			.addLexical(new LexicalFactor().chainType(types).chainType(name))
			.addLexicalType(types);
	}

}
