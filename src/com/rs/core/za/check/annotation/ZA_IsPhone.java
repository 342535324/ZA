package com.rs.core.za.check.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 手机号码校验
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZA_IsPhone {
	/**
	 * 错误提示信息
	 * 
	 * @return
	 */
	public String msg() default "";

}
