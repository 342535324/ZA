package com.rs.core.za.testUtil.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.rs.core.za.testUtil.annotation.ZA_InterfaceNotes;
import com.rs.core.za.testUtil.annotation.ZA_InterfaceNotesParameter;

/**
 * 接口文档专用的接口对象，不会进行参数类型判断等复杂操作
 *
 */
public class ZAInterfaceNotesSimpleEntity implements Comparable<ZAInterfaceNotesSimpleEntity> {
	private ZA_InterfaceNotes interfaceNotes;
	private ZA_InterfaceNotesParameter[] interfaceNotesParameters;
	private String url;
	 
	public ZAInterfaceNotesSimpleEntity(ZA_InterfaceNotes interfaceNotes,
			ZA_InterfaceNotesParameter[] interfaceNotesParameters,String url) {
		super();
		this.interfaceNotes = interfaceNotes;
		this.interfaceNotesParameters = interfaceNotesParameters;
		this.url = url;
	}

	public ZA_InterfaceNotes getInterfaceNotes() {
		return interfaceNotes;
	}

	public void setInterfaceNotes(ZA_InterfaceNotes interfaceNotes) {
		this.interfaceNotes = interfaceNotes;
	}

	public ZA_InterfaceNotesParameter[] getInterfaceNotesParameters() {
		return interfaceNotesParameters;
	}

	public void setInterfaceNotesParameters(ZA_InterfaceNotesParameter[] interfaceNotesParameters) {
		this.interfaceNotesParameters = interfaceNotesParameters;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int compareTo(ZAInterfaceNotesSimpleEntity o) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return new Long(sdf.parse(getInterfaceNotes().time()).getTime()).compareTo(new Long(sdf.parse(o.getInterfaceNotes().time()).getTime()));
		} catch (ParseException e) {
		}
		return 0;
	}

}
