package chiya.core.base.unique;

/**
 * 唯一性标识生成接口
 * 
 * @author chiya
 */
public interface UniqueGenerate {

	/**
	 * 生成下一个
	 * 
	 * @return long类型唯一性标识
	 */
	public long generate();
}
