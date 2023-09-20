package chiya.script.check;

import java.util.List;

import chiya.script.token.ChiyaToken;

/**
 * 递归检查接口
 * 
 * @author chiya
 *
 */
@FunctionalInterface
public interface RecursionCheck {

	/**
	 * 递归检查
	 * 
	 * @param chiyaToken    当前token
	 * @param needRecursion 进一步递归检查的token
	 * @param errorMessage  错误信息
	 * @return true:检查通过/false:存在语法问题
	 */
	boolean check(ChiyaToken chiyaToken, List<ChiyaToken> needRecursion, List<String> errorMessage);

}
