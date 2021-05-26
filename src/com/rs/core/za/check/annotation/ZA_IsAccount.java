package com.rs.core.za.check.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 账号校验
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZA_IsAccount {
	/**
	 * 错误提示信息
	 * 
	 * @return
	 */
	public String msg() default "账号不能为空";

	public int minlength() default 1;

	public int maxlength() default 16;

}
