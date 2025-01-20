package chiya.server.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * RPC参数注解
 * 
 * @author chiya
 *
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ChiyaRpcParam {

	/**
	 * RPC参数名称
	 * 
	 * @return 参数名称
	 */
	String value();
}
