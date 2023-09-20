package chiya.script.tree;

import chiya.script.parser.ChiyaSyntaxTreeParser;
import chiya.script.token.ChiyaToken;

/**
 * 构建标准树
 * 
 * @author chiya
 *
 */
public interface BuildAbstractTree {

	/**
	 * 构建树
	 * 
	 * @param treeParser 语法树解析器
	 * @param chiyaToken 当前token
	 * @return 标准语法树
	 */
	AbstractSyntaxTree buildTree(ChiyaSyntaxTreeParser treeParser, ChiyaToken chiyaToken);

}
