package com.rs.core.za.check.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 密码校验
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZA_IsPassword {
	/**
	 * 错误提示信息
	 * 
	 * @return
	 */
	public String msg() default "";

	public int minlength() default 6;

	public int maxlength() default 16;
}
