package com.rs.core.za.testUtil.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数识别，在接口对象(ZAInterfaceNotesEntity)被初始化时调用
 */
public class ZAParameter {

	public final static Map<Integer, String> map = new HashMap<Integer, String>() {
		{
			put(TYPE_byte, "byte 字节");
			put(TYPE_Byte, "Byte 字节");
			put(TYPE_short, "short 短整");
			put(TYPE_Short, "Short 短整");
			put(TYPE_int, "int 整型");
			put(TYPE_Integer, "Integer 整型");
			put(TYPE_long, "long 长整型");
			put(TYPE_Long, "Long 长整型");
			put(TYPE_String, "String 字符串");

			put(TYPE_float, "float 浮点");
			put(TYPE_Float, "Float 浮点");

			put(TYPE_double, "double 双精浮点");
			put(TYPE_Double, "Double 双精浮点");

			put(TYPE_BigDecimal, "BigDecimal API类 用来对超过16位有效位的数进行精确的运算");

			put(TYPE_boolean, "boolean 布尔值");
			put(TYPE_Boolean, "Boolean 布尔值");

			put(TYPE_Date, "Date 日期格式");
			put(TYPE_Timestamp, "Timestamp 时间戳格式");

			put(TYPE_File, "file 文件");
			put(TYPE_Files, "file 文件数组");

			put(TYPE_ints, "int 数组");
			put(TYPE_Integers, "Integer 数组");
		}
	};

	public static final int TYPE_byte = 0;
	public static final int TYPE_Byte = 100;

	public static final int TYPE_short = 200;
	public static final int TYPE_Short = 300;

	public static final int TYPE_int = 400;
	public static final int TYPE_Integer = 450;

	public static final int TYPE_ints = 401;
	public static final int TYPE_Integers = 451;

	public static final int TYPE_long = 500;
	public static final int TYPE_Long = 600;

	public static final int TYPE_String = 700;

	public static final int TYPE_float = 800;
	public static final int TYPE_Float = 900;

	public static final int TYPE_double = 1000;
	public static final int TYPE_Double = 1100;

	public static final int TYPE_BigDecimal = 1200;

	public static final int TYPE_boolean = 1300;
	public static final int TYPE_Boolean = 1400;

	public static final int TYPE_Date = 1500;
	public static final int TYPE_Timestamp = 1600;

	public static final int TYPE_File = 1700;
	public static final int TYPE_Files = 1800;

	public static final int InterfaceTYPE_ADD = 1001;
	public static final int InterfaceTYPE_MODIFY = 1002;
	public static final int InterfaceTYPE_SELECT = 1003;
	public static final int InterfaceTYPE_DELETE = 1004;
	public static final int InterfaceTYPE_ADDorDelete = 1005;
	public static final int InterfaceTYPE_LOGIN = 1006;

	@Override
	public String toString() {
		return "ZAParameter [toString()=" + super.toString() + "]";
	}

}
