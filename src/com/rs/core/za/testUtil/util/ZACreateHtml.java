package com.rs.core.za.testUtil.util;

import java.util.List;

import com.rs.core.za.testUtil.annotation.ZA_InterfaceNotesParameter;
import com.rs.core.za.testUtil.common.ZAParameter;
import com.rs.core.za.testUtil.entity.ZAInterfaceNotesSimpleEntity;

/**
 * 生成HTML的工具类
 */
public class ZACreateHtml {

	/**
	 * 根据传入的接口对象数组生成接口文档
	 */
	public static String createInterfaceHTML(List<ZAInterfaceNotesSimpleEntity> list) {
		StringBuilder html = new StringBuilder();
		for (ZAInterfaceNotesSimpleEntity zaInterfaceNotesSimpleEntity : list) {
			html.append(zaInterfaceNotesSimpleEntity.getInterfaceNotes().name());
			html.append("</br>");
			html.append("URL：" + zaInterfaceNotesSimpleEntity.getUrl());
			html.append("</br>");
			html.append("参数：");
			for (ZA_InterfaceNotesParameter za_InterfaceNotesParameter : zaInterfaceNotesSimpleEntity
					.getInterfaceNotesParameters()) {
				html.append("</br>");
				html.append("&nbsp;&nbsp;&nbsp;");
				html.append(za_InterfaceNotesParameter.name());
				html.append(" ");
				html.append(ZAParameter.map.get(za_InterfaceNotesParameter.type()));
				html.append(za_InterfaceNotesParameter.describe());
			}
			html.append("</br>");
			html.append("返回：");
			html.append(zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnContent());
			if (zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnObjectUrl() != null
					&& zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnObjectUrl().length() > 0) {
				html.append("</br>");
				html.append("返回实体类参照：");
				if (zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnObjectUrl().indexOf("Abstract") > -1) {
					html.append("<a  target='_blank'  href='doc/com/rs/core/entity/entityAbstract/");
				} else if (zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnObjectUrl().indexOf("V") > -1) {
					html.append("<a  target='_blank'  href='doc/com/rs/core/entity/view/");
				}
				html.append(zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnObjectUrl());
				if (zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnObjectUrl().indexOf("html") < 0) {
					html.append(".html");
				}
				html.append("' > " + zaInterfaceNotesSimpleEntity.getInterfaceNotes().returnObjectUrl() + " 点击查看</a>");
			}
			html.append("</br>");
			html.append("<a  target='_blank'  href='testUtil/gotoIndex.html?interfaceUrl="
					+ zaInterfaceNotesSimpleEntity.getUrl());
			html.append("' >测试跳转</a>");

			html.append("</br>");
			html.append("---------------------------------------------");
			html.append("</br>");
		}
		return html.toString();
	}
}
