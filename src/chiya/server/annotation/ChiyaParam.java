package chiya.server.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 参数标记
 * 
 * @author chiya
 *
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ChiyaParam {
	/**
	 * 参数名称
	 * 
	 * @return 参数名称
	 */
	String value() default "";
}