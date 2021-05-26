package com.rs.core.za.testUtil.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 接口参数注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ZA_parameters.class)
public @interface ZA_InterfaceNotesParameter {
	/**
	 * 参数名
	 */
	public String name();

	/**
	 * 参数类型
	 */
	public int type();

	/**
	 * 说明
	 */
	public String describe() default "";

	/**
	 * 默认值
	 */
	public String defaultValue() default "";
}
