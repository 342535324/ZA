package com.rs.core.za.testUtil.entity;

import com.rs.core.za.testUtil.annotation.ZA_InterfaceNotes;
import com.rs.core.za.testUtil.annotation.ZA_InterfaceNotesParameter;
import com.rs.core.za.testUtil.common.ZAParameter;

/**
 * 接口对象，每一个对应控制器中的一个接口(方法)
 */
public class ZAInterfaceNotesEntity implements Comparable<ZAInterfaceNotesEntity> {
	/**
	 * 参数数组
	 */
	private ZAInterfaceNotesParameterEntity[] interfaceNotesParameter;
	/**
	 * 接口路径
	 */
	private String url;
	/**
	 * 接口名称
	 */
	private String name;
	/**
	 * 接口类型描述
	 */
	private String type;
	/**
	 * 接口类型
	 */
	private int typeValue;

	/**
	 * 初始化参数并进行参数识别
	 */
	public ZAInterfaceNotesEntity(ZA_InterfaceNotes interfaceNotes,
			ZA_InterfaceNotesParameter[] interfaceNotesParameters, String url) {
		this.name = interfaceNotes.name();
		this.typeValue = interfaceNotes.type();
		this.url = url;
		interfaceNotesParameter = new ZAInterfaceNotesParameterEntity[interfaceNotesParameters.length];
		for (int i = 0; i < interfaceNotesParameters.length; i++) {
			// 参数识别
			interfaceNotesParameter[i] = new ZAInterfaceNotesParameterEntity(interfaceNotesParameters[i].name(),
					ZAParameter.map.get(interfaceNotesParameters[i].type()), interfaceNotesParameters[i].describe(),
					interfaceNotesParameters[i]);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ZAInterfaceNotesParameterEntity[] getInterfaceNotesParameter() {
		return interfaceNotesParameter;
	}

	public void setInterfaceNotesParameter(ZAInterfaceNotesParameterEntity[] interfaceNotesParameter) {
		this.interfaceNotesParameter = interfaceNotesParameter;
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

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public int compareTo(ZAInterfaceNotesEntity o) {
		return new Integer(getTypeValue()).compareTo(o.getTypeValue());
	}

}
