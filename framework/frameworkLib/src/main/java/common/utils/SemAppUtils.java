package common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.cglib.beans.BeanCopier;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;

import common.bussiness.Department;
import common.bussiness.User;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.os.vo.DepartmentVO;
import common.os.vo.UsersVO;
import common.utils.JsonValueProcessorImpl;
import common.utils.SemAppUtils;

public class SemAppUtils {

	public static Log logger = LogFactory.getLog(SemAppUtils.class);
	
	@Autowired
	public static CommonRemote commonRemote;

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
			ConvertUtils.register(new DateConverter(null),
					java.util.Calendar.class);
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
	 * 
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

	/**
	 * 字符串转Integer
	 * 
	 * @return
	 */
	public static Integer getInteger(String str) {
		return SemAppUtils.isEmpty(str) ? null : new Integer(str);
	}

	public static String getStdDate(Calendar cal) {
		if (cal != null) {
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			DecimalFormat nf = new DecimalFormat("00");
			String dayStr = nf.format(day);
			String monthStr = nf.format(month + 1);
			String yearStr = String.valueOf(year);
			return yearStr + "-" + monthStr + "-" + dayStr;
		} else {
			return "";
		}
	}

	public static String getStdDateInt(Calendar cal) {
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		DecimalFormat nf = new DecimalFormat("00");
		String dayStr = nf.format(day);
		String monthStr = nf.format(month + 1);
		String yearStr = String.valueOf(year);
		return yearStr + monthStr + dayStr;
	}

	public static String getStdTime(Calendar cal) {
		int year = cal.get(Calendar.HOUR_OF_DAY);
		int month = cal.get(Calendar.MINUTE);
		int day = cal.get(Calendar.SECOND);
		DecimalFormat nf = new DecimalFormat("00");
		String dayStr = nf.format(day);
		String monthStr = nf.format(month);
		String yearStr = String.valueOf(year);
		return yearStr + ":" + monthStr + ":" + dayStr;
	}

	public static String[] objectToString(Object[] obj) {
		String[] result = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			result[i] = (String) obj[i];
		}
		return result;
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

	public static String getFullTimeInt(Calendar cal) {
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
		result = yearStr + monthStr + dayStr + hourStr + minStr + secondStr;
		return result;
	}

	public static Calendar getCalendar(String date) {
		if (date == null)
			return null;
		String[] strs = date.split("-");
		if (strs.length != 3)
			return null;
		Calendar cale = Calendar.getInstance();
		cale.set(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]) - 1,
				Integer.parseInt(strs[2]));
		return cale;
	}

	public static Calendar getFullCalendar(String dateStr) {
		if (dateStr == null)
			return null;
		SimpleDateFormat sdf = null;
		if (dateStr.indexOf(":") != -1) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		Calendar result = null;
		try {
			Date date = sdf.parse(dateStr);
			result = Calendar.getInstance();
			result.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;

	}

	public static UsersVO getUserInfo(Integer empID) {
		UsersVO user = null;
		try {
			
			user = commonRemote.getUserInfo(empID);
		} catch (Exception ee) {
			logger.error("访问业务逻辑层失败", ee);
		}
		return user;
	}
	
	public static User getUserInfo(String empID) {
		User user = null;
		try {
			
			user = commonRemote.getUserInfo(empID);
		} catch (Exception ee) {
			logger.error("访问业务逻辑层失败", ee);
		}
		return user;
	}

	public static Department getDeptInfo(Integer deptID) {
		Department deptment = null;
		try {
			
			deptment = commonRemote.getDepartmentInfo(deptID);
		} catch (Exception ee) {
			logger.error("访问业务逻辑层失败", ee);
		}
		return deptment;
	}

	public static List getSubOrganises(int organise) throws BaseException,
			BaseBusinessException {
		try {
			
			return commonRemote.getSubOrganises(organise);
		} catch (Exception ex3) {
			handleException(ex3);
			return null;
		}
	}

	public static void handleException(Exception ee) throws BaseException,
			BaseBusinessException {
		if (ee instanceof BaseException) {
			throw (BaseException) ee;
		} else if (ee instanceof BaseBusinessException) {
			throw (BaseBusinessException) ee;
		} else {
			logger.error("服务器异常", ee);
			throw new BaseException("服务器异常", ee);
		}
	}

	public static Calendar convertCalendar(Calendar sou, boolean before) {
		if (sou == null)
			return null;
		sou.set(Calendar.HOUR_OF_DAY, before ? 0 : 23);
		sou.set(Calendar.MINUTE, before ? 0 : 59);
		sou.set(Calendar.SECOND, before ? 0 : 59);
		return sou;
	}

	public static UsersVO user2VO(User user) {
		if (user == null)
			return null;
		UsersVO vo = new UsersVO();
		beanCopy(vo, user);
		vo.setId(new Integer(user.getId()));
		vo.setName(user.getName());
		vo.setDepartmentName(user.getAbbr());
		vo.setDepartment(user.getDeptid());
		vo.setDepartmentCode(user.getDeptnum());
		vo.setLevel(SemAppUtils.getInteger(user.getLevel()==null?"0":user.getLevel()));
		return vo;
	}
	
	public static Log getDBLog(Class theClass) {
		return LogFactory.getLog("db." + theClass.getName());
	}
	
	public static String getLogonToken(Integer empID) {
		try {
			return commonRemote.getUserToken(empID);
			// home.remove(common);
		}  catch (Exception ex2) {
			ex2.printStackTrace();
			return null;

		}
	}
	
	public static String getProperty(String name) throws Exception {
		String property = null;
		try {
			HelperRemote helperRemote =null;

			property = helperRemote.getProperty(name);

		} catch (Exception e) {
			throw e;
		}
		return property;
	}
	
	public static User vo2User(UsersVO vo) {
		if (vo == null)
			return null;
		User user = new User();
		beanCopy(vo, user);
		user.setId(vo.getId().intValue());
		user.setName(vo.getName());
		user.setAbbr(vo.getDepartmentName());
		// user.setDeptid(、vo.getDepartment().intValue());
		user.setDeptnum(vo.getDepartmentCode());
		user.setLevel("" + vo.getLevel());
		// user.setAddr(vo.getAddr());
		user.setArchaddr(vo.getArchAddr());
		user.setBirthday(SemAppUtils.getFullTime(vo.getBirthday()));
		return user;

	}
	
	public static Department vo2Dept(DepartmentVO vo) {
		if (vo == null)
			return null;
		Department dept = new Department();
		dept.setId("" + vo.getId());
		dept.setLeadID("" + vo.getLeaderId());
		dept.setLevel("" + vo.getDeptLevel());
		dept.setName(vo.getName());
		dept.setParentID(vo.getParentId());
		return dept;
	}
	
	public static String getReqPage(String filename) {
		if (filename == null)
			return null;
		int i = filename.lastIndexOf("/");
		// int j = filename.indexOf("?");
		if (i < 0) {
			return filename;
		} else {
			return filename.substring(i + 1);
		}
		// return j < i ? filename.substring(i + 1) : filename.substring(i + 1,
		// j);
	}

	public static String getFullPath(String filename) {
		try {
			String newFilename = getReqPage(filename);
			newFilename = URLEncoder.encode(newFilename, "UTF-8");
			if (!filename.startsWith("http")) {
				return getProperty("FILESERVER_URL") + newFilename;

			} else {
				return filename.substring(0, filename.lastIndexOf("/") + 1)
						+ newFilename;
			}

		} catch (Exception ee) {
			System.out.println("convert filename fail");
			return filename;
		}
	}
	
	
	// encrytor & decrytor Data
//		public static String encrytor(String data) throws BaseException,
//				BaseBusinessException {
//			try {
//				
//				return commonRemote.encrytor(data);
//			} catch (Exception ex3) {
//				handleException(ex3);
//				return null;
//			}
//
//		}
//
//		public static String decrytor(String data) throws BaseException,
//				BaseBusinessException {
//			try {
//				
//				return commonRemote.decrytor(data);
//			} catch (Exception ex3) {
//				handleException(ex3);
//				return null;
//			}
//		}
//
//		public static String encrytor(String data, String key)
//				throws BaseException, BaseBusinessException {
//			try {
//				
//				return commonRemote.encrytor(data, key);
//			} catch (Exception ex3) {
//				handleException(ex3);
//				return null;
//			}
//
//		}
//
//		public static String decrytor(String data, String key)
//				throws BaseException, BaseBusinessException {
//			try {
//				
//				return commonRemote.decrytor(data, key);
//			} catch (Exception ex3) {
//				handleException(ex3);
//				return null;
//			}
//		}
		
		/**
		 * 随机产生字符串
		 * 
		 * @param length
		 *            长度
		 * @return 随机字符串
		 */
		public static String getRandomString(int length) { // length表示生成字符串的长度
			String base = "abcdefghijklmnopqrstuvwxyz0123456789";
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				int number = random.nextInt(base.length());
				sb.append(base.charAt(number));
			}
			return sb.toString();
		}
		
		public static String getUrlQuery(String url, String name) {
			// String
			// url="http://semhq20/DocWeb/fileAction.do?id=80&token=sdfwerw&parentID=";
			String result = null;
			Map queryMap = new HashMap();
			try {
				URI urlO = new URI(url);
				String query = urlO.getQuery();
				String[] temps = query.split("&");
				for (int i = 0; i < temps.length; i++) {
					String[] querys = temps[i].split("=");
					if (querys.length > 1) {
						System.out.println(querys[0] + "=" + querys[1]);
						queryMap.put(querys[0], querys[1]);
					}
				}
				result = (String) queryMap.get(name);
			} catch (Exception e) {
				logger.error("查询参数失败", e);
			}
			return result;
		}
		
		public static boolean isSystemModule(int systemID) {
			return systemID == 0 || systemID == SemAppConstants.COMMON_MODULE_ID;
		}

}
