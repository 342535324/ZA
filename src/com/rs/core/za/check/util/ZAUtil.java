package com.rs.core.za.check.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.rs.common.utils.CheckUtil;
import com.rs.core.exception.ZACheckException;
import com.rs.core.za.check.annotation.ZA_IsAccount;
import com.rs.core.za.check.annotation.ZA_IsEMail;
import com.rs.core.za.check.annotation.ZA_IsIdCard;
import com.rs.core.za.check.annotation.ZA_IsInt;
import com.rs.core.za.check.annotation.ZA_IsPassword;
import com.rs.core.za.check.annotation.ZA_IsPhone;
import com.rs.core.za.check.annotation.ZA_NotNull;

/**
 * 校验工具类
 */
public class ZAUtil {

	/**
	 * 根据自定义注解，校验实体类中的特定属性
	 * 
	 * @param obj
	 *            实体类
	 * @param attributes
	 *            属性名字
	 * @throws ZACheckException
	 *             自定义异常
	 */
	public static void checkModel(Object obj, List<String> attributes) throws ZACheckException {
		Field[] fields = obj.getClass().getDeclaredFields();
		String msg = null;
		for (Field field : fields) {
			if (attributes.contains(field.getName())) {
				boolean b = field.isAccessible();
				field.setAccessible(true);
				CheckALL(field, msg, obj);
				field.setAccessible(b);
			}
		}
	}

	/**
	 * 校验整个实体类
	 * 
	 * @param obj
	 * @throws ZACheckException
	 */
	public static void checkModel(Object obj) throws ZACheckException {
		Field[] fields = obj.getClass().getDeclaredFields();
		String msg = null;
		for (Field field : fields) {
			boolean b = field.isAccessible();
			field.setAccessible(true);
			CheckALL(field, msg, obj);
			field.setAccessible(b);
		}
	}

	private static void CheckALL(Field field, String msg, Object obj) throws ZACheckException {
		NotNull(field, msg, obj);
		IsPassword(field, msg, obj);
		IsAccount(field, msg, obj);
		IsInt(field, msg, obj);
		IsIdCard(field, msg, obj);
		IsEMail(field, msg, obj);
		IsPhone(field, msg, obj);
	}

	/**
	 * 非空校验
	 */
	private static void NotNull(Field field, String msg, Object obj) throws ZACheckException {
		ZA_NotNull n = field.getAnnotation(ZA_NotNull.class);
		if (n != null) {
			try {
				Object value = field.get(obj);
				if (value == null || value.toString().length() < 1) {
					msg = n.msg().toString();
					throw new ZACheckException(msg);
				}
			} catch (Exception e) {
				throw new ZACheckException(e.getMessage());
			}
		}
	}

	/**
	 * 整数校验
	 */
	private static void IsInt(Field field, String msg, Object obj) throws ZACheckException {
		ZA_IsInt n = field.getAnnotation(ZA_IsInt.class);
		if (n != null) {
			try {
				Object value = field.get(obj);
				if (value == null || value.toString().length() < 1 || !value.toString().matches("[0-9]+")) {
					msg = n.msg().toString();
					throw new ZACheckException(msg);
				}
			} catch (Exception e) {
				throw new ZACheckException(e.getMessage());
			}
		}
	}

	/**
	 * 密码校验
	 */
	private static void IsPassword(Field field, String msg, Object obj) throws ZACheckException {
		ZA_IsPassword n = field.getAnnotation(ZA_IsPassword.class);
		if (n != null) {
			try {
				Object value = field.get(obj);
				if (value == null || value.toString().length() < 1 || !value.toString().matches("[a-zA-Z0-9]{1,16}")
						|| value.toString().length() < n.minlength() || value.toString().length() >= n.maxlength()) {
					msg = n.msg().toString();
					throw new ZACheckException(msg);
				}
			} catch (Exception e) {
				throw new ZACheckException(e.getMessage());
			}
		}
	}

	/**
	 * 身份证号校验
	 */
	private static void IsIdCard(Field field, String msg, Object obj) throws ZACheckException {
		ZA_IsIdCard n = field.getAnnotation(ZA_IsIdCard.class);
		if (n != null) {
			try {
				Object value = field.get(obj);
				if (value == null || value.toString().length() < 1 || !CheckUtil.IDCardValidate(value.toString())) {
					msg = n.msg().toString();
					throw new ZACheckException(msg);
				}
			} catch (Exception e) {
				throw new ZACheckException(e.getMessage());
			}
		}
	}

	/**
	 * 是否是邮箱校验
	 */
	private static void IsEMail(Field field, String msg, Object obj) throws ZACheckException {
		ZA_IsEMail n = field.getAnnotation(ZA_IsEMail.class);
		if (n != null) {
			try {
				Object value = field.get(obj);
				if (value == null || value.toString().length() < 1 || !CheckUtil.isEMail(value.toString())) {
					msg = n.msg().toString();
					throw new ZACheckException(msg);
				}
			} catch (Exception e) {
				throw new ZACheckException(e.getMessage());
			}
		}
	}

	/**
	 * 账号校验
	 */
	private static void IsAccount(Field field, String msg, Object obj) throws ZACheckException {
		ZA_IsAccount n = field.getAnnotation(ZA_IsAccount.class);
		if (n != null) {
			try {
				Object value = field.get(obj);
				if (value == null || value.toString().length() < 1 || !CheckUtil.isAccount(value.toString())) {
					msg = n.msg().toString();
					throw new ZACheckException(msg);
				}
			} catch (Exception e) {
				throw new ZACheckException(e.getMessage());
			}
		}
	}

	/**
	 * 手机号码校验
	 */
	private static void IsPhone(Field field, String msg, Object obj) throws ZACheckException {
		ZA_IsPhone n = field.getAnnotation(ZA_IsPhone.class);
		if (n != null) {
			try {
				Object value = field.get(obj);
				if (value == null || value.toString().length() < 1 || !CheckUtil.isMobile(value.toString())) {
					msg = n.msg().toString();
					throw new ZACheckException(msg);
				}
			} catch (Exception e) {
				throw new ZACheckException(e.getMessage());
			}
		}
	}

	/**
	 * 读取类所有属性 将其基本属性进行初始化赋值(不会覆盖已有的值,仅针值为null的属性进行操作)
	 */
	public static final <T> T initObject(Object obj) throws IllegalArgumentException, IllegalAccessException {

		Field[] df = obj.getClass().getDeclaredFields();
		for (Field field : df) {
			boolean b = field.isAccessible();
			field.setAccessible(true);
			if (field.get(obj) == null) {
				if (field.getType().equals(String.class)) {
					field.set(obj, "");
				} else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
					field.set(obj, 0);
				} else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
					field.set(obj, 0.0);
				} else if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
					field.set(obj, (short) 0);
				} else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
					field.set(obj, (float) 0);
				} else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
					field.set(obj, (long) 0);
				} else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
					field.set(obj, false);
				} else if (field.getType().equals(Timestamp.class)) {
					field.set(obj, new Timestamp(System.currentTimeMillis()));
				} else if (field.getType().equals(Date.class)) {
					field.set(obj, new Date());
				} else if (field.getType().equals(java.sql.Date.class)) {
					field.set(obj, new java.sql.Date(System.currentTimeMillis()));
				}
			}
			field.setAccessible(b);
		}
		return (T) obj;
	}

	/**
	 * 读取2个obj的所有属性 将属性的值转成字符串,然后进行对比 如果所有属性一致则返回true
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static final boolean ObjectContrast(Object obj1, Object obj2) throws Exception {

		// 2个对象必须是一个类型的
		if (!obj1.getClass().equals(obj2.getClass())) {
			return false;
		}

		Field[] df = obj1.getClass().getDeclaredFields();
		for (Field field : df) {
			boolean b = field.isAccessible();
			field.setAccessible(true);
			if (field.get(obj1).toString().equals(field.get(obj2).toString()) == false) {
				return false;
			}
			field.setAccessible(b);
		}
		return true;
	}

}
