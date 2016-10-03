package com.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.utils.SemAppUtils;
import com.common.utils.JsonValueProcessorImpl;

public class SemAppUtils {

	public static Log logger = LogFactory.getLog(SemAppUtils.class);

	public static String object2String(Object obj) {
		Class voClass = obj.getClass();

		Field[] fields = voClass.getDeclaredFields();
		String text = "";
		StringBuffer sb = new StringBuffer();
		// System.out.println("fields.length" + fields.length);
		sb.append("[");
		for (int i = 0; i < fields.length; i++) {
			sb.append(fields[i].getName());

			String firstLetter = fields[i].getName().substring(0, 1)
					.toUpperCase();
			String getMethodName = "get" + firstLetter
					+ fields[i].getName().substring(1);

			Method getMethod = null;
			Object value = new Object();
			try {
				Field field;

				getMethod = voClass.getMethod(getMethodName, new Class[] {});

				value = getMethod.invoke(obj, new Object[] {});
				if (value == null) {
					text = "";
				} else if (value instanceof Calendar) {
					text = SemAppUtils.getFullTime((Calendar) value);
				} else if (value instanceof Date) {
					Calendar cal = Calendar.getInstance();
					cal.setTime((Date) value);
					text = SemAppUtils.getFullTime(cal);
				} else if (value instanceof Integer || value instanceof Boolean
						|| value instanceof Double || value instanceof Float) {
					text = value.toString();
				} else if (value.getClass().isPrimitive()) {
					text = "" + value;
				} else {
					text = value.getClass().getName();
				}
			} catch (SecurityException e) {

			} catch (NoSuchMethodException e1) {

			} catch (IllegalArgumentException e) {

			} catch (IllegalAccessException e) {

			} catch (InvocationTargetException e) {

			}
			// System.out.println("value:" + text);
			sb.append(":" + text + ",");
		}
		sb.append("]");
		return new String(sb);
	}

	public static String getFullTime(Calendar cal) {
		String result = "";
		if (cal == null)
			return "";
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		DecimalFormat nf = new DecimalFormat("00");
		String dayStr = nf.format(day);
		String monthStr = nf.format(month + 1);
		String yearStr = String.valueOf(year);
		String hourStr = nf.format(hour);
		String minStr = nf.format(min);
		String secondStr = nf.format(second);
		result = yearStr + "-" + monthStr + "-" + dayStr + " " + hourStr + ":"
				+ minStr + ":" + secondStr;
		return result;
	}

	/**
	 * 取得“getter”函數的函數名
	 * 
	 * @param field
	 * @return
	 */
	public final static String getGetMethodName(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	/**
	 * 判断一个对象的所有变量是否都为null，待判断对象所属类所有变量必须有getter方法，否则该方法无法做出判断且会抛出异常
	 * 注意：使用该方法必须保证o中的所有变量均有getter方法，否则方法将抛出异常
	 * 
	 * @param object
	 *            目标对象
	 */
	public static boolean isAllFieldsNull(Object object) {
		Class c = object.getClass();
		Method[] methods = c.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("get")) {
				try {
					Object returnValue = methods[i].invoke(object, null);
					if (returnValue != null) {
						return false;
					}
				} catch (IllegalArgumentException e) {
					logger.error("判断对象" + object + "的所有变量是否都为null失败", e);
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					logger.error("判断对象" + object + "的所有变量是否都为null失败", e);
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					logger.error("判断对象" + object + "的所有变量是否都为null失败", e);
					throw new RuntimeException(e);
				} catch (SecurityException e) {
					logger.error("判断对象" + object + "的所有变量是否都为null失败", e);
					throw new RuntimeException(e);
				}
			}
		}
		return true;
	}

	/**
	 * 将数据库pojo对象转化成对应的VO类对象
	 * 注意：使用该方法必须保证o中的所有变量均有getter方法，否则方法将抛出异常,同时pojo必须和vo对应字段同名
	 */
	public static Object BO2VO(Object pojo, Object vo) {
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Calendar.class);   
			BeanUtils.copyProperties(vo, pojo); 
		} catch (IllegalAccessException e) {
			logger.error("创建VO类" + vo + "VO失败", e);
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			logger.error("复制pojo值到VO类" + vo + "VO失败", e);
			throw new RuntimeException(e);
		}
		return vo;
	}

	public static void beanCopy(Object source, Object target) {
		BeanCopier copier = BeanCopier.create(source.getClass(),
				target.getClass(), false);
		copier.copy(source, target, null);
	}

	// 带子对象集转成JSON,casade为“Y”表示关联也要显示
	// SemWebAppUtils
	public static String getJsonFromBean(Object bean, final List filterField,
			final boolean isFilter) {
		JSONObject jsonObject = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl());
		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				boolean isFiltered = false;
				Iterator iter = filterField.iterator();

				// System.out.println("source["+source+"name["+name+"]"+"value["+value+"]");
				while (iter.hasNext()) {
					String property = (String) iter.next();
					String parent = null;
					String child = null;
					Class temp = null;
					if (property.contains(".")) {
						String[] strs = property.split("#");
						parent = strs[0];
						try {
							temp = Class.forName(parent);
							// temp.
							if (strs.length > 1) {
								child = strs[1];
							}
						} catch (Exception ee) {
							logger.error("指定的类找不到", ee);
						}
					} else {
						child = property;
					}
					if ((temp == null || source.getClass().getName() == temp
							.getName()) && name.equals(child)) {
						isFiltered = true;
					}
				}
				if (isFiltered) {
					return isFilter;
				}
				return !isFilter;
			}
		};
		jsonConfig.setJsonPropertyFilter(filter);

		jsonObject = JSONObject.fromObject(bean, jsonConfig);

		return jsonObject.toString();
	}

	/**
	 * 通过bean生成JSON数据
	 * 
	 * @param bean
	 *            bean对象
	 * @return 生成的JSON数据
	 */
	/**
	 * 通过bean生成JSON数据
	 * 
	 * @param bean
	 *            bean对象
	 * @return 生成的JSON数据
	 */
	// SemWebAppUtils
	public static String getJsonFromBean(Object bean) {
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl());
		JSONObject JsonObject = JSONObject.fromObject(bean, cfg);
		return JsonObject.toString();
	}

	// SemWebAppUtils
	public static String getJsonFromBean(Object bean, final List filterField,
			String dateFormat) {
		JSONObject jsonObject = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl(dateFormat));
		// jsonConfig.registerJsonValueProcessor(Calendar.class,
		// new JsonValueProcessorImpl());
		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				boolean isFiltered = false;
				Iterator iter = filterField.iterator();
				// System.out.println("source["+source+"name["+name+"]"+"value["+value+"]");
				while (iter.hasNext()) {
					String property = (String) iter.next();
					String parent = null;
					String child = null;
					Class temp = null;
					if (property.contains(".")) {
						String[] strs = property.split("#");
						parent = strs[0];
						try {
							temp = Class.forName(parent);
							// temp.
							if (strs.length > 1) {
								child = strs[1];

							}
						} catch (Exception ee) {
							logger.error("指定的类找不到", ee);
						}
					} else {
						child = property;
					}
					if ((temp == null || source.getClass().getName() == temp
							.getName()) && name.equals(child)) {
						isFiltered = true;
					}
				}
				if (isFiltered) {
					return true;
				}
				return false;
			}
		};
		jsonConfig.setJsonPropertyFilter(filter);

		jsonObject = JSONObject.fromObject(bean, jsonConfig);

		return jsonObject.toString();
	}

	// SemWebAppUtils
	public static String getJsonFromBean(Object bean, boolean longFormat) {
		String format = longFormat ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd";
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl(format));
		JSONObject JsonObject = JSONObject.fromObject(bean, cfg);
		return JsonObject.toString();
	}

	/**
	 * <p>
	 * String null check.
	 * </p>
	 * 
	 * @param str
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.trim().length() > 0;
	}
	
	/**
	 * <p>
	 * String null check.
	 * </p>
	 * 
	 * @param str
	 */
	public static boolean isEmpty(String str) {
		return !(isNotEmpty(str));
	}
	
	/**
	 * 转换字符串编码
	 * @return
	 */
	public static String convertCharacterEncoding(String str) {
		try {
			str = new String(str.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("转换字符串编码出现异常！");
		}
		return str;
	}
	
}
