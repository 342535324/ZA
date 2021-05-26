package com.rs.core.za.check.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 非空校验
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZA_NotNull {
	/**
	 * 错误提示信息
	 * 
	 * @return
	 */
	public String msg() default "不能为空";

}
