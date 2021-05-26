package com.rs.core.za.testUtil.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import com.rs.core.za.testUtil.annotation.ZA_InterfaceNotesParameter;
import com.rs.core.za.testUtil.common.AutoRecognition;

/**
 * 参数生成器
 *
 */
public class ZACreateParameterUtil {
	public static final String KEY = "";

	public static final Class<ZACreateParameterUtil> createParameterUtilClass = com.rs.core.za.testUtil.util.ZACreateParameterUtil.class;
	public static final ZACreateParameterUtil createParameterUtil = new ZACreateParameterUtil();

	public static String createName() {
		int r = (int) (Math.random() * 5);
		return new String[] { "张三", "李四", "王五", "赵六", "孙七", "刘八" }[r];
	}

	/**
	 * 参数生成，在参数对象(ZAInterfaceNotesParameterEntity)被初始化时调用
	 */
	public static String createParameter(ZA_InterfaceNotesParameter interfaceNotesParameters) {
		String methodName = null;
		if (AutoRecognition.ParameterNameAutoRecognitionMap.containsKey(interfaceNotesParameters.name())) {
			methodName = AutoRecognition.ParameterNameAutoRecognitionMap.get(interfaceNotesParameters.name());
		} else if (AutoRecognition.ParameterTypeAutoRecognitionMap
				.containsKey(Integer.valueOf(interfaceNotesParameters.type()))) {
			methodName = AutoRecognition.ParameterTypeAutoRecognitionMap
					.get(Integer.valueOf(interfaceNotesParameters.type()));
		}
		if (methodName == null) {
			return null;
		}
		try {
			Method med = createParameterUtilClass.getMethod(methodName);
			return med.invoke(createParameterUtil).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String createString() {
		// return UUID.randomUUID().toString().replaceAll("\\W",
		// "").substring(0, 10);
		return "";
	}

	public static Integer[] createIntegers() {
		return new Integer[] { new Random().nextInt() };
	}

	public static int[] createInts() {
		return new int[] { new Random().nextInt() };
	}

	public static Integer createInteger() {
		return new Random().nextInt();
	}

	public static Double createDouble() {
		return new Random().nextDouble();
	}

	public static Date createDate() {
		return new Date();
	}

	public static String createAccount() {
		return UUID.randomUUID().toString().replaceAll("\\W", "").substring(0, 8);
	}

	public static String createPassword() {
		return UUID.randomUUID().toString().replaceAll("\\W", "").substring(0, 11);
	}

	public static String createPhone() {
		int[] n = new int[] { 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 187, 188, 130, 131, 132, 155,
				156, 185, 186, 133, 153, 180, 189 };
		return n[new Random().nextInt(n.length)] + "" + (int) (Math.random() * 100000000d) + "";
	}

	public static String createId() {
		return UUID.randomUUID().toString().replaceAll("\\W", "").substring(0, 9);
	}

	public static String createKey() {
		return KEY;
	}
}
