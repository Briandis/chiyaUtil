package chiya.security.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 角色的权限设置
 * 
 * @author brain
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ChiyaSecurity {

	/** 该接口支持的角色 */
	int[] value();
}
