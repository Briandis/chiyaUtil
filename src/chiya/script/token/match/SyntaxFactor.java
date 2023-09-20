package chiya.script.token.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chiya.script.token.ChiyaToken;

/**
 * 语法因子
 * 
 * @author chiya
 *
 */
public class SyntaxFactor {

	/** 语法因子集合 */
	private List<LexicalFactor> syntaxList = new ArrayList<>();
	/** 自身标记类型 */
	private String type;
	/** 需要匹配 */
	private boolean needMatch = false;
	/** 匹配时需要左右成对 */
	private boolean needPaired = false;
	/** 将子项对应位置替换成对应token类型 */
	private HashMap<Integer, Object> tokenType = new HashMap<>();
	/** 集成类型位特定位置上的类型 */
	private Integer extendType = null;
	/** 匹配到的值充当父级下标 */
	private Integer fatherIndex = null;

	/**
	 * 获取词法长度
	 * 
	 * @return 长度
	 */
	public int getSyntaxLength() {
		return syntaxList.size();
	}

	/**
	 * 判断token是否和第一个词法相同
	 * 
	 * @param index 词法下标
	 * @param token token
	 * @return 相同/不同
	 */
	public boolean tokenSameFirst(ChiyaToken token) {
		return LexicalFactor.tokenSame(getLexicalFirst(), token);
	}

	/**
	 * 判断token是否和最后一个词法相同
	 * 
	 * @param token token
	 * @return 相同/不同
	 */
	public boolean tokenSameLast(ChiyaToken token) {
		return LexicalFactor.tokenSame(getLexicalLast(), token);
	}

	/**
	 * 判断token是否和当前词法规则相同
	 * 
	 * @param index 词法下标
	 * @param token token
	 * @return 相同/不同
	 */
	public boolean tokenSame(int index, ChiyaToken token) {
		return LexicalFactor.tokenSame(getLexical(index), token);
	}

	/**
	 * 获取一个位置上的词法
	 * 
	 * @param index 下标
	 * @return 词法
	 */
	public LexicalFactor getLexical(int index) {
		if (syntaxList.isEmpty() || index >= syntaxList.size() || index < 0) { return null; }
		return syntaxList.get(index);
	}

	/**
	 * 获取第一个词法
	 * 
	 * @return
	 */
	public LexicalFactor getLexicalFirst() {
		if (syntaxList.isEmpty()) { return null; }
		return syntaxList.get(0);
	}

	/**
	 * 获取最后的词法
	 * 
	 * @return 自身
	 */
	public LexicalFactor getLexicalLast() {
		if (syntaxList.isEmpty()) { return null; }
		return syntaxList.get(syntaxList.size() - 1);
	}

	/**
	 * 构建语法，并标记自身类型
	 * 
	 * @param type 类型
	 */
	public SyntaxFactor(String type) {
		this.type = type;
	}

	/**
	 * 添加语法
	 * 
	 * @param lexicalFactor
	 * @return 自身
	 */
	public SyntaxFactor addLexical(LexicalFactor lexicalFactor) {
		syntaxList.add(lexicalFactor);
		return this;
	}

	/**
	 * 添加类型
	 * 
	 * @param index 下标
	 * @param data  替换的类型
	 * @return 自身
	 */
	public SyntaxFactor addType(int index, String data) {
		tokenType.put(index, data);
		return this;
	}

	/**
	 * 添加类型
	 * 
	 * @param index 下标
	 * @param map   替换的类型
	 * @return 自身
	 */
	public SyntaxFactor addType(int index, Map<String, String> map) {
		tokenType.put(index, map);
		return this;
	}

	/**
	 * 获取语法因子集合
	 * 
	 * @return 语法因子集合
	 */
	public List<LexicalFactor> getSyntaxList() {
		return syntaxList;
	}

	/**
	 * 设置语法因子集合
	 * 
	 * @param syntaxList 语法因子集合
	 */
	public void setSyntaxList(List<LexicalFactor> syntaxList) {
		this.syntaxList = syntaxList;
	}

	/**
	 * 链式添加语法因子集合
	 * 
	 * @param syntaxList 语法因子集合
	 * @return 对象本身
	 */
	public SyntaxFactor chainSyntaxList(List<LexicalFactor> syntaxList) {
		setSyntaxList(syntaxList);
		return this;
	}

	/**
	 * 获取自身标记类型
	 * 
	 * @return 自身标记类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置自身标记类型
	 * 
	 * @param type 自身标记类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 链式添加自身标记类型
	 * 
	 * @param type 自身标记类型
	 * @return 对象本身
	 */
	public SyntaxFactor chainType(String type) {
		setType(type);
		return this;
	}

	/**
	 * 获取需要匹配
	 * 
	 * @return 需要匹配
	 */
	public boolean getNeedMatch() {
		return needMatch;
	}

	/**
	 * 设置需要匹配
	 * 
	 * @param needMatch 需要匹配
	 */
	public void setNeedMatch(boolean needMatch) {
		this.needMatch = needMatch;
	}

	/**
	 * 链式添加需要匹配
	 * 
	 * @param needMatch 需要匹配
	 * @return 对象本身
	 */
	public SyntaxFactor chainNeedMatch(boolean needMatch) {
		setNeedMatch(needMatch);
		return this;
	}

	/**
	 * 获取匹配时需要左右成对
	 * 
	 * @return 匹配时需要左右成对
	 */
	public boolean getNeedPaired() {
		return needPaired;
	}

	/**
	 * 设置匹配时需要左右成对
	 * 
	 * @param needPaired 匹配时需要左右成对
	 */
	public void setNeedPaired(boolean needPaired) {
		this.needPaired = needPaired;
	}

	/**
	 * 链式添加匹配时需要左右成对
	 * 
	 * @param needPaired 匹配时需要左右成对
	 * @return 对象本身
	 */
	public SyntaxFactor chainNeedPaired(boolean needPaired) {
		setNeedPaired(needPaired);
		return this;
	}

	/**
	 * 获取将子项对应位置替换成对应token类型
	 * 
	 * @return 将子项对应位置替换成对应token类型
	 */
	public HashMap<Integer, Object> getTokenType() {
		return tokenType;
	}

	/**
	 * 设置将子项对应位置替换成对应token类型
	 * 
	 * @param tokenType 将子项对应位置替换成对应token类型
	 */
	public void setTokenType(HashMap<Integer, Object> tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * 链式添加将子项对应位置替换成对应token类型
	 * 
	 * @param tokenType 将子项对应位置替换成对应token类型
	 * @return 对象本身
	 */
	public SyntaxFactor chainTokenType(HashMap<Integer, Object> tokenType) {
		setTokenType(tokenType);
		return this;
	}

	/**
	 * 获取集成类型位特定位置上的类型
	 * 
	 * @return 集成类型位特定位置上的类型
	 */
	public Integer getExtendType() {
		return extendType;
	}

	/**
	 * 设置集成类型位特定位置上的类型
	 * 
	 * @param extendType 集成类型位特定位置上的类型
	 */
	public void setExtendType(Integer extendType) {
		this.extendType = extendType;
	}

	/**
	 * 链式添加集成类型位特定位置上的类型
	 * 
	 * @param extendType 集成类型位特定位置上的类型
	 * @return 对象本身
	 */
	public SyntaxFactor chainExtendType(Integer extendType) {
		setExtendType(extendType);
		return this;
	}

	/**
	 * 获取匹配到的值充当父级下标
	 * 
	 * @return 匹配到的值充当父级下标
	 */
	public Integer getFatherIndex() {
		return fatherIndex;
	}

	/**
	 * 设置匹配到的值充当父级下标
	 * 
	 * @param fatherIndex 匹配到的值充当父级下标
	 */
	public void setFatherIndex(Integer fatherIndex) {
		this.fatherIndex = fatherIndex;
	}

	/**
	 * 链式添加匹配到的值充当父级下标
	 * 
	 * @param fatherIndex 匹配到的值充当父级下标
	 * @return 对象本身
	 */
	public SyntaxFactor chainFatherIndex(Integer fatherIndex) {
		setFatherIndex(fatherIndex);
		return this;
	}

}
