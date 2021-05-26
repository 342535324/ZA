package com.rs.core.za.check.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 整数校验
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZA_IsInt {
	/**
	 * 错误提示信息
	 * 
	 * @return
	 */
	public String msg() default "必须为整数";

}
