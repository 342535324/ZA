package com.rs.core.za.check.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 邮箱校验
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZA_IsEMail {
	/**
	 * 错误提示信息
	 * 
	 * @return
	 */
	public String msg() default "";

}
