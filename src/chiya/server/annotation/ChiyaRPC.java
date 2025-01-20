package chiya.server.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * RPC配置
 * 
 * @author brain
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ChiyaRPC {
	/**
	 * RPC地址
	 * 
	 * @return 配置RPC地址
	 */
	String value();
}
