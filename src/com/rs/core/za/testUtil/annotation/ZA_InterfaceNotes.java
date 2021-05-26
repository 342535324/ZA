/**
 * 
 */
package com.rs.core.za.testUtil.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rs.core.za.testUtil.common.ZAParameter;

/**
 * 接口注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZA_InterfaceNotes {
	/**
	 * 方法名称
	 */
	public String name();

	/**
	 * 方法类型
	 */
	public int type() default ZAParameter.InterfaceTYPE_SELECT;

	/**
	 * 返回内容描述
	 */
	public String returnContent() default "成功code返回1";

	/**
	 * 返回实体类URL
	 */
	public String returnObjectUrl() default "";

	public String time();
}
