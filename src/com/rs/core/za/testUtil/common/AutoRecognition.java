package com.rs.core.za.testUtil.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动识别参数生成值规则类
 */
public class AutoRecognition {

	/**
	 * 参数名生成参数 key为参数名,value为ZACreateParameterUtil的生成方法
	 */
	public static final Map<String, String> ParameterNameAutoRecognitionMap = new HashMap<String, String>() {
		{
			put("account", "createAccount");
			put("password", "createPassword");
			put("phone", "createPhone");
			put("id", "createId");
			put("key", "createKey");
			// -----------------------------------------
		}
	};

	/**
	 * 根据类型生成参数 key为参数类型,value为ZACreateParameterUtil的生成方法
	 */
	public static final Map<Integer, String> ParameterTypeAutoRecognitionMap = new HashMap<Integer, String>() {
		{
			put(ZAParameter.TYPE_String, "createString");
			put(ZAParameter.TYPE_int, "createInteger");
			put(ZAParameter.TYPE_Integer, "createInteger");
			put(ZAParameter.TYPE_double, "createDouble");
			put(ZAParameter.TYPE_Double, "createDouble");
			put(ZAParameter.TYPE_Date, "createDate");
			put(ZAParameter.TYPE_Integers, "createIntegers");
			put(ZAParameter.TYPE_int, "createInts");
			// -----------------------------------------
		}
	};

}
