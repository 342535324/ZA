package com.rs.core.za.testUtil.entity;

import com.rs.core.za.testUtil.annotation.ZA_InterfaceNotesParameter;
import com.rs.core.za.testUtil.util.ZACreateParameterUtil;

/**
 * 接口对象中的参数项，每一个参数对应一个类
 *
 */
public class ZAInterfaceNotesParameterEntity {
	/**
	 * 参数名
	 */
	private String name;
	/**
	 * 参数类型
	 */
	private String type;
	/**
	 * 参数说明
	 */
	private String describe;
	/**
	 * 页面上的默认值
	 */
	private String value = "请输入参数值";
	/**
	 * 参数类型
	 */
	private int typeValue;
	
	/**
	 * 数据初始化并进行参数生成
	 */
	public ZAInterfaceNotesParameterEntity(String name, String type, String describe, ZA_InterfaceNotesParameter interfaceNotesParameters) {
		super();
		this.name = name;
		this.type = type;
		this.typeValue = interfaceNotesParameters.type();
		this.describe = describe;
		if (interfaceNotesParameters.defaultValue().length()<1) {
			//生成参数
			String v = ZACreateParameterUtil.createParameter(interfaceNotesParameters);
			if (v!=null){
				this.value=v;
			}
		}else{
			this.value = interfaceNotesParameters.defaultValue();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}

}
