package com.common.web.utils;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import jxl.DateCell;
import jxl.NumberCell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.common.exception.BaseException;
import com.common.exception.CommonException;
import com.common.utils.JsonValueProcessorImpl;
import com.common.utils.SemAppUtils;
import com.common.value.BaseVO;
import com.common.value.PageList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * <p>
 * Utility methods used by web applications.
 * </p>
 * 
 * @author Copyright (c) 2004 by BEA Systems. All Rights Reserved.
 */
public class SemWebAppUtils {
	private static final char S = 0;

	public static Log logger = LogFactory.getLog(SemWebAppUtils.class);

	/**
	 * 通过List生成XML数据
	 * 
	 * @param recordTotal
	 *            记录总数，不一定与beanList中的记录数相等
	 * @param beanList
	 *            包含bean对象的集合
	 * @return 生成的XML数据
	 * 
	 * 
	 */
	public static String getXmlFromList(long recordTotal, List beanList) {
		// Total total = new Total();
		// total.setResults(recordTotal);
		List results = new ArrayList();
		results.add(new Long(recordTotal));
		results.addAll(beanList);
		XStream sm = new XStream(new DomDriver());
		for (int i = 0; i < results.size(); i++) {
			Class c = results.get(i).getClass();
			String b = c.getName();
			String[] temp = b.split("\\.");
			sm.alias(temp[temp.length - 1], c);
		}
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ sm.toXML(results);
		return xml;
	}

	public static String getListJsonFromList(List list) {
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl());
		JSONArray JsonObject = JSONArray.fromObject(list, cfg);
		return JsonObject.toString();
	}

	/**
	 * 通过List生成XML数据
	 * 
	 * @param beanList
	 *            包含bean对象的集合
	 * @return 生成的XML数据
	 */
	public static String getXmlFromList(List beanList) {
		return getXmlFromList(beanList.size(), beanList);
	}

	/**
	 * 通过List生成JSON数据
	 * 
	 * @param recordTotal
	 *            记录总数，不一定与beanList中的记录数相等
	 * @param beanList
	 *            包含bean对象的集合
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromList(long recordTotal, List beanList) {
		PageList total = new PageList();
		total.setResults(recordTotal);
		total.setItems(beanList);
		return getJsonFromBean(total);
	}

	/**
	 * 通过List生成JSON数据
	 * 
	 * @param beanList
	 *            包含bean对象的集合
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromList(List beanList) {
		return getJsonFromList(beanList.size(), beanList);
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
	public static String getJsonFromBean(Object bean) {
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl());
		JSONObject JsonObject = JSONObject.fromObject(bean, cfg);
		return JsonObject.toString();
	}
	public static String getJsonFromBean(Object bean,JsonConfig jsonConfig){
		JSONObject JsonObject = JSONObject.fromObject(bean, jsonConfig);
		return JsonObject.toString();
	}
	public static String getJsonFromBean(Object bean,final List filterField){ 
		return getJsonFromBean(bean,filterField,true);
	}
	// 带子对象集转成JSON,casade为“Y”表示关联也要显示
	public static String getJsonFromBean(Object bean,final List filterField,final boolean isFilter){ 
        JSONObject jsonObject = new JSONObject();  
        JsonConfig jsonConfig = new JsonConfig(); 
        jsonConfig.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl());
       PropertyFilter filter = new PropertyFilter() { 
           public boolean apply(Object source, String name, Object value) { 
               boolean isFiltered=false;
               Iterator iter=filterField.iterator();
               
        //       System.out.println("source["+source+"name["+name+"]"+"value["+value+"]");
               while(iter.hasNext()){
            	   String property=(String)iter.next();
            	   String parent=null;
            	   String child=null;
            	   Class temp=null;
            	   if(property.contains(".")){
            		   String[] strs=property.split("#");
            		   parent=strs[0];
            		   try{
            		   temp=Class.forName(parent);
            		   //temp.
            		   if(strs.length>1){
            			   child=strs[1];
            			  
            		   }
            		   }catch(Exception ee){
            			   logger.error("指定的类找不到",ee);
            		   }
            	   }else{
            		   child=property;
            	   }
                   if((temp==null||source.getClass().getName() == temp.getName()) && name.equals(child)){
                       isFiltered=true;
                   }
               }
               if (isFiltered) { 
                   return isFilter; 
               } 
               return !isFilter; 
           } 
       }; 
       jsonConfig.setJsonPropertyFilter(filter);  
       
       jsonObject=JSONObject.fromObject(bean, jsonConfig);

      return jsonObject.toString();
  }
	public static String getJsonFromBean(Object bean,final List filterField,String dateFormat){ 
	       JSONObject jsonObject = new JSONObject();  
	        JsonConfig jsonConfig = new JsonConfig(); 
	        jsonConfig.registerJsonValueProcessor(Calendar.class,
					new JsonValueProcessorImpl(dateFormat));
	      //  jsonConfig.registerJsonValueProcessor(Calendar.class,
		//			new JsonValueProcessorImpl());
	       PropertyFilter filter = new PropertyFilter() { 
	           public boolean apply(Object source, String name, Object value) { 
	               boolean isFiltered=false;
	               Iterator iter=filterField.iterator();
	        //       System.out.println("source["+source+"name["+name+"]"+"value["+value+"]");
	               while(iter.hasNext()){
	            	   String property=(String)iter.next();
	            	   String parent=null;
	            	   String child=null;
	            	   Class temp=null;
	            	   if(property.contains(".")){
	            		   String[] strs=property.split("#");
	            		   parent=strs[0];
	            		   try{
	            		   temp=Class.forName(parent);
	            		   //temp.
	            		   if(strs.length>1){
	            			   child=strs[1];
	            			  
	            		   }
	            		   }catch(Exception ee){
	            			   logger.error("指定的类找不到",ee);
	            		   }
	            	   }else{
	            		   child=property;
	            	   }
	                   if((temp==null||source.getClass().getName() == temp.getName()) && name.equals(child)){
	                       isFiltered=true;
	                   }
	               }
	               if (isFiltered) { 
	                   return true; 
	               } 
	               return false; 
	           } 
	       }; 
	       jsonConfig.setJsonPropertyFilter(filter);  
	       
	       jsonObject=JSONObject.fromObject(bean, jsonConfig);

	      return jsonObject.toString();
  }

	public static String getJsonFromBean(Object bean, String dateFormat) {
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl(dateFormat));
		JSONObject JsonObject = JSONObject.fromObject(bean, cfg);
		return JsonObject.toString();
	}

	public static String getJsonFromBean(Object bean, boolean longFormat) {
		String format = longFormat ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd";
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Calendar.class,
				new JsonValueProcessorImpl(format));
		JSONObject JsonObject = JSONObject.fromObject(bean, cfg);
		return JsonObject.toString();
	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/**
	 * 从json数组中解析出java字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/**
	 * 从json数组中解析出javaLong型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = new Long(jsonArray.getLong(i));

		}
		return longArray;
	}

	private static class StringBean {
		private String value;

		public String getValue() {
			return value;
		}

		public StringBean() {

		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getId() {
			return value;
		}

	}

	private static List StringList2StringBeanList(List stringList) {
		if (stringList == null)
			return null;
		List stringBeanList = new ArrayList();
		Iterator iter = stringList.iterator();
		while (iter.hasNext()) {
			String value = (String) iter.next();
			StringBean stringBean = new StringBean();
			stringBean.setValue(value);
			stringBeanList.add(stringBean);
		}
		return stringBeanList;
	}

	public static String convertString(String content) {
		if (content == null) {
			return "";
		} else {
			return content.trim();
		}
	}

	public static Locale getLocaleFromCookie(HttpServletRequest request) {
		Locale locale = null;
		Cookie cookie = null;
		String country = null;
		String language = null;
		Cookie[] cookies = request.getCookies();

		// Start locale processing.
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if ("Language".equals(cookie.getName())) {
					language = cookie.getValue();
					logger.debug("Found language cookie with value = "
							+ language);
				} else if (cookie.getName().equals("Country")) {
					country = cookie.getValue();
					logger
							.debug("Found country cookie with value = "
									+ country);
				}
			}
		}

		if (country != null && language != null) {
			locale = new Locale(language, country);
		}

		return locale;
	}

	/**
	 * <p>
	 * String null check.
	 * </p>
	 * 
	 * @param locale
	 * @return boolean
	 */
	public static boolean isValidLocale(Locale locale) {
		return (locale.getLanguage().equals("ja")
				|| locale.getLanguage().equals("en") || locale.getLanguage()
				.equals("es"));
	}

	// S T R I N G M A N I P U L A T I O N
	/**
	 * <p>
	 * String null check.
	 * </p>
	 * 
	 * @param str
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
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
	 * <p>
	 * Return trim, non-null representation of object.
	 * </p>
	 * 
	 * @param obj
	 * @return String
	 */
	public static String cleanParam(Object obj) {
		String str = String.valueOf(obj);
		if (str == null || str.equals("null"))
			str = "";
		return str.trim();
	}

	/**
	 * <p>
	 * Check for valid string, no special chars including:
	 * "#","&","^","%","*","/","\\","(",")"
	 * </p>
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isValidString(String str) {
		boolean valid = true;
		if (valid) {
			String invalid[] = { "#", "&", "^", "%", "*", "/", "\\", "(", ")" };
			for (int i = 0; i < invalid.length; i++) {
				if (str.indexOf(invalid[i]) >= 0) {
					valid = false;
				}
			}
		}
		return valid;
	}

	/**
	 * <p>
	 * Trim textbox to 40 chars. Remaining display with elipse.
	 * </p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String trimToSummaryStr(String str) {
		int endLength = 40;

		if (isEmpty(str))
			return str;

		if (str.length() > endLength) {
			String tmpStr = str.substring(0, str.indexOf(" ", endLength));
			tmpStr += "...";
			return tmpStr;
		} else {
			return str;
		}
	}

	// D A T E M A N I P U L A T I O N
	/**
	 * <p>
	 * String representation of a calendar. Format: MM/DD/YYYY
	 * </p>
	 * 
	 * @param pCalendar
	 * @return String
	 */
	public static String getDisplayDate(Calendar pCalendar) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		if (pCalendar != null)
			return format.format(pCalendar.getTime());
		else
			return "";
	}

	/**
	 * <p>
	 * Convert string representation of a date to calendar.
	 * </p>
	 * 
	 * @param str
	 * @return Calendar
	 */
	public static Calendar str2Calendar(String str) {
		Calendar cal = null;
		if (!isEmpty(str)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date d = sdf.parse(str);
				cal = Calendar.getInstance();
				cal.setTime(d);
			} catch (ParseException e) {
			}
		}
		return cal;
	}

	/**
	 * <p>
	 * String representation of current date. Format: MM/DD/YYYY
	 * </p>
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		return getDisplayDate(GregorianCalendar.getInstance());
	}

	// M I S C E L L A N E O U S
	/**
	 * <p>
	 * Print collection contents.
	 * </p>
	 * 
	 * @param col
	 * @param type
	 * @return String
	 */
	public static String col2Str(Collection col, String type) {
		StringBuffer str = new StringBuffer();
		if (col != null && !col.isEmpty()) {
			str.append(" Num of " + type + ": " + col.size() + " |");
			Iterator itr = col.iterator();
			while (itr.hasNext()) {
				Object obj = itr.next();
				str.append(" " + obj.toString());
			}
		} else {
			str.append(type + ": [null]");
		}
		return str.toString();
	}

	/**
	 * <p>
	 * Get HTPP method- POST or GET. This is used to debug the query string.
	 * </p>
	 * 
	 * @return String
	 */
	public static String getHttpMethod() {
		if (logger.isDebugEnabled())
			return "GET";
		else
			return "POST";
	}

	/**
	 * <p>
	 * Get full url path, i.e. http://localhost:port/<webapp context>
	 * </p>
	 * 
	 * @param request
	 * @return String
	 */
	public static String getUrlPath(HttpServletRequest request) {
		return getUrlRoot(request) + request.getContextPath();
	}

	/**
	 * <p>
	 * Get localhost and port url root, i.e. http://localhost:port
	 * </p>
	 * 
	 * @param request
	 * @return String
	 */
	public static String getUrlRoot(HttpServletRequest request) {
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String nextPage = "http://" + serverName + ":" + serverPort;
		logger.debug("URL root: " + nextPage);
		return nextPage;
	}

	/**
	 * <p>
	 * Get servlet name from given mapping.
	 * </p>
	 * 
	 * @param mapping
	 * @param name
	 * @return String
	 */
//	public static String getFullUrlPath(HttpServletRequest request,
//			ActionMapping mapping, String name) {
//		return getUrlPath(request) + "/" + getServletName(mapping, name);
//	}

	/**
	 * <p>
	 * Get servlet name from given mapping.
	 * </p>
	 * 
	 * @param mapping
	 * @param name
	 * @return String
	 */
//	public static String getServletName(ActionMapping mapping, String name) {
//		ActionForward forward = mapping.findForward(name);
//		String path = forward.getPath();
//		return path.substring(path.indexOf("/") + 1);
//	}

	/**
	 * <p>
	 * Parse exception stack finding the orignial exception message.
	 * </p>
	 * 
	 * @param th
	 * @return String
	 */
	public static String getRootErrMsg(Throwable th) {
		if (th.getCause() != null)
			return getRootError(th.getCause()).getLocalizedMessage();
		else
			return th.getLocalizedMessage();
	}

	/**
	 * <p>
	 * Parse exception stack finding the orignial exception.
	 * </p>
	 * 
	 * @param th
	 * @return Throwable
	 */
	public static Throwable getRootError(Throwable th) {
		if (th instanceof javax.ejb.EJBException) {
			Throwable nested = ((javax.ejb.EJBException) th)
					.getCausedByException();
			if (nested != null)
				return getRootError(nested);
			else
				return th;
		}
		if (th instanceof java.rmi.RemoteException) {
			Throwable nested = th.getCause();
			if (nested != null)
				return getRootError(nested);
			else
				return th;
		}
		if (th instanceof java.rmi.AccessException) {
			Throwable nested = th.getCause();
			if (nested != null)
				return getRootError(nested);
			else
				return th;
		}
		if (th instanceof java.sql.SQLException) {
			Throwable nested = th.getCause();
			if (nested != null)
				return getRootError(nested);
			else
				return th;
		}
		if (th instanceof BaseException) {
			Throwable nested = th.getCause();
			if (nested != null)
				return getRootError(nested);
			else
				return th;
		} else
			return th;
	}

	public static boolean hasAddRight(String rightCode) {
		return rightCode != null
				&& rightCode.indexOf(SemWebAppConstants.RIGHT_CODE_ADD) != -1;
	}

	public static boolean hasDeleteRight(String rightCode) {
		return rightCode != null
				&& rightCode.indexOf(SemWebAppConstants.RIGHT_CODE_DELETE) != -1;
	}

	public static boolean hasListRight(String rightCode) {
		return rightCode != null
				&& rightCode.indexOf(SemWebAppConstants.RIGHT_CODE_LIST) != -1;
	}

	public static boolean hasUpdateRight(String rightCode) {
		return rightCode != null
				&& rightCode.indexOf(SemWebAppConstants.RIGHT_CODE_UPDATE) != -1;
	}

	public static boolean hasExprotRight(String rightCode) {
		return rightCode != null
				&& rightCode.indexOf(SemWebAppConstants.RIGHT_CODE_EXPORT) != -1;
	}

	public static boolean hasImportRight(String rightCode) {
		return rightCode != null
				&& rightCode.indexOf(SemWebAppConstants.RIGHT_CODE_IMPORT) != -1;
	}

	public static boolean hasCustomRight(String customCode) {
		return customCode != null
				&& customCode.indexOf(SemWebAppConstants.RIGHT_CODE_CUSTOM) != -1;
	}

	public static boolean hasDeleteAllRight(String customCode) {
		return customCode != null
				&& customCode.indexOf(SemWebAppConstants.RIGHT_CODE_DELETE_ALL) != -1;
	}

	/**
	 * 根据输入的报表类型，返回当前报表的datakey,如当前为2006-11-12，若报表类型为月报，
	 * 则返回200610（注11月数据未结转），若报表类型为季报，则返回20063;
	 * 
	 * @param type
	 *            int 输入的报表类型
	 * @param realTime
	 *            是否为实时性报表
	 * @return int 返回的dateKey
	 */

	public static String getCurrentDateKey(int type, boolean realTime,
			int importedDate) {
		Calendar cal = Calendar.getInstance();
		switch (type) {
		case (SemWebAppConstants.DAILY_REPORT): {
			if (!realTime)
				cal.add(Calendar.DATE, -1); // 减少一天
			return getStdDate(cal);
		}
		case (SemWebAppConstants.MONTHLY_REPORT): {
			if (!realTime) {
				cal.add(Calendar.DATE, -8); // 每月十二日为EIS切换日
				cal.add(Calendar.MONTH, -1); // 减少一月
			}
			return getStdDate(cal).substring(0, 7);
		}

		case (SemWebAppConstants.YEARLY_REPORT): {
			// 年份的KEY较为特殊，按减少一个月为dataKey,因有些月报使用年度推移或汇总
			if (!realTime)
				cal.add(Calendar.MONTH, -1);
			// cal.add(Calendar.YEAR, -1); //减少一年
			return getStdDate(cal).substring(0, 4);
		}
			/**
			 * 暂不处理
			 */
		case (SemWebAppConstants.WEEKLY_REPORT): {
			if (!realTime)
				cal.add(Calendar.DATE, -1); // 减少一天
			return getStdDate(cal);
		}
			/**
			 * 季报
			 */
		case (SemWebAppConstants.QUARTERLY_REPORT): {
			int lastQuarter = getQuarter(cal.get(cal.MONTH) + 1);
			int year = cal.get(cal.YEAR);
			if (!realTime)
				lastQuarter--;
			if (lastQuarter < 0) {
				lastQuarter = lastQuarter + 4;
				year--;
			}
			return year + "-" + lastQuarter;
		}
		case SemWebAppConstants.TIME_REPORT: {
			return getFullTime(cal);
		}
		}
		return "";

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

	public static int getQuarter(int month) {
		if (month > 0 && month <= 3)
			return 1;
		if (month > 3 && month <= 6)
			return 2;
		if (month > 6 && month <= 9)
			return 3;
		if (month > 9 && month <= 12)
			return 4;
		return -1;
	}

	public static String getDateKeyName(int dateKey, int type) {
		String result = "";
		switch (type) {
		case (SemWebAppConstants.DAILY_REPORT): {
			int year = dateKey / 10000;
			int remain = dateKey % 10000;
			int month = remain / 100;
			int day = remain % 100;
			result = year + "年" + month + "月" + day + "日";
			break;

		}
		case (SemWebAppConstants.MONTHLY_REPORT): {
			int year = dateKey / 100;
			int month = dateKey % 100;
			result = year + "年" + month + "月";
			break;
		}

		case (SemWebAppConstants.YEARLY_REPORT): {
			result = dateKey + "年";
			break;
		}
			// 周报
		case (SemWebAppConstants.WEEKLY_REPORT): {
			break;
		}
			// 季报
		case (SemWebAppConstants.QUARTERLY_REPORT): {
			int year = dateKey / 10;
			int quar = dateKey % 10;
			result = year + "年" + quar + "季";
			break;

		}

		}
		return result;
	}

	public static int dateKeyAdd(int type, int dateKey, int amount) {
		Calendar cal = Calendar.getInstance();
		switch (type) {
		case (SemWebAppConstants.DAILY_REPORT): {
			int year = dateKey / 10000;
			int remain = dateKey % 10000;
			int month = remain / 100;
			int day = remain % 100;
			cal.set(year, month - 1, day);
			cal.add(Calendar.DATE, amount); // 减少一天
			return Integer.parseInt(getStdDateInt(cal));
		}
		case (SemWebAppConstants.MONTHLY_REPORT): {
			int year = dateKey / 100;
			int month = dateKey % 100;
			cal.set(year, month - 1, 1); // 减少一月
			cal.add(Calendar.MONTH, amount);
			return Integer.parseInt(getStdDateInt(cal).substring(0, 6));
		}

		case (SemWebAppConstants.YEARLY_REPORT): {
			return dateKey + amount;
		}

			/**
			 * 季报
			 */
		case (SemWebAppConstants.QUARTERLY_REPORT): {
			int year = dateKey / 10;
			int quarter = dateKey % 10;
			quarter = quarter + amount;
			if (quarter > 4) {
				year++;
				quarter = quarter - 4;
			}
			if (quarter < 0) {
				year--;
				quarter = quarter + 4;
			}
			return Integer.parseInt(year + "" + quarter);
		}
		}
		return 0;
	}

	public static String getFullTime(Calendar cal) {
		String result = "";
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

	public static String getOtherRightCode(String rightCode) {
		if (rightCode == null)
			return null;
		StringBuffer sb = new StringBuffer();
		char[] array = rightCode.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != SemWebAppConstants.RIGHT_CODE_ADD.charAt(0)
					&& array[i] != SemWebAppConstants.RIGHT_CODE_ADD.charAt(0)
					&& array[i] != SemWebAppConstants.RIGHT_CODE_DELETE
							.charAt(0)
					&& array[i] != SemWebAppConstants.RIGHT_CODE_UPDATE
							.charAt(0)
					&& array[i] != SemWebAppConstants.RIGHT_CODE_LIST.charAt(0)
					&& array[i] != SemWebAppConstants.RIGHT_CODE_EXPORT
							.charAt(0)
					&& array[i] != SemWebAppConstants.RIGHT_CODE_IMPORT
							.charAt(0)
					&& array[i] != SemWebAppConstants.RIGHT_CODE_CUSTOM
							.charAt(0)) {
				sb.append(array[i]);
			}
		}
		return new String(sb);
	}

	public static void list2Excel(List list, OutputStream os) {
		try {
			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
			WritableSheet wsheet = wbook.createSheet("vo", 0); // 工作表名称
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			WritableCellFormat titleFormat = new WritableCellFormat(wfont);

			int c = 1; // 用于循环时Excel的行号

			Iterator it = list.iterator();
			boolean createTitle = true;
			List fieldList = new ArrayList();
			while (it.hasNext()) {
				logger.debug("add row[" + c + "]");
				BaseVO vo = (BaseVO) it.next();
				Class voClass = vo.getClass();
				Field[] fs2 = voClass.getDeclaredFields();
				if (createTitle) {
					createTitle = false;
					int j = 0;
					for (int i = 0; i < fs2.length; i++) {
						String name = fs2[i].getName();
						if (!isIgnoreField(name)) {
							Label excelTitle = new Label(j, 0, name,
									titleFormat);
							wsheet.addCell(excelTitle);
							fieldList.add(fs2[i]);
						}
						j++;

					}
				}
				Iterator iter = fieldList.iterator();
				int i = 0;
				while (iter.hasNext()) {
					Field field = (Field) iter.next();
					String name = field.getName();
					String firstLetter = name.substring(0, 1).toUpperCase();
					
					String getMethodName = "get" + firstLetter
							+ name.substring(1);
					Method getMethod = voClass.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(vo, new Object[] {});
					logger.debug(name + "->" + value);
					if (field.getType() == Integer.class) {

						jxl.write.Number labelN = new jxl.write.Number(i, c,
								value == null ? 0 : ((Integer) value)
										.intValue());
						wsheet.addCell(labelN);
					}
					if (field.getType() == Double.class) {
						jxl.write.Number labelN = new jxl.write.Number(i, c,
								value == null ? 0.0 : ((Double) value)
										.doubleValue());
						wsheet.addCell(labelN);
					}
					if (field.getType() == Float.class) {
						jxl.write.Number labelN = new jxl.write.Number(i, c,
								value == null ? 0.0 : ((Float) value)
										.floatValue());
						wsheet.addCell(labelN);
					}
					if (field.getType() == Long.class) {
						jxl.write.Number labelN = new jxl.write.Number(i, c,
								value == null ? 0 : ((Long) value).longValue());
						wsheet.addCell(labelN);
					}
					if (field.getType() == Boolean.class) {
						jxl.write.Boolean labelB = new jxl.write.Boolean(i, c,
								value == null ? false : ((Boolean) value)
										.booleanValue());
						wsheet.addCell(labelB);
					}
					if (field.getType() == Calendar.class) {
						jxl.write.DateTime labelDT = null;
						if (value != null) {
							labelDT = new jxl.write.DateTime(i, c,
									((Calendar) value).getTime());
							wsheet.addCell(labelDT);
						}

					}
					if (field.getType() == Date.class) {
						jxl.write.DateTime labelDT = new jxl.write.DateTime(i,
								c, ((Date) value));
						wsheet.addCell(labelDT);
					}
					if (field.getType() == String.class) {
						Label content1 = new Label(i, c, (String) value);
						wsheet.addCell(content1);
					}
					i++;
				}
				c++;
			}

			wbook.write(); // 写入文件
			wbook.close();
		} catch (Exception e) {
			logger.error("导出文件出错", e);
			throw new CommonException("导出文件出错", e);

		}
	}

	private static boolean isIgnoreField(String fieldName) {
		return "serialVersionUID".equals(fieldName);
	}

	public static void list2Excel(List list, OutputStream os, String gridBody) {
		if (list == null || gridBody == null)
			return;
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet=wb.createSheet("data");
			XSSFRow titleRow = sheet.createRow((short) 0);
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			String[] heads = gridBody.split(",");
			List headList = new ArrayList();
			List fieldList = new ArrayList();
			for (int i = 0; i < heads.length; i++) {
				String[] subArray = heads[i].split(":");
				// headList.add(subArray[0]);
				if (subArray.length > 1) {
					fieldList.add(subArray[0]);
					headList.add(subArray[1]);
				}
			}
			WritableCellFormat titleFormat = new WritableCellFormat(wfont);
			titleRow.setHeightInPoints(20);//20像素
			int titleCount = headList.size();// 列数
			
			Iterator iter = headList.iterator();
			//写表头
			int j = 0;
			while (iter.hasNext()) {
				XSSFCell cell = titleRow.createCell((short) j); 
				String head = (String) iter.next();
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(head);
				j++;
			}
			//写表体
			int c = 1; // 用于循环时Excel的行号
			Iterator it = list.iterator();
			XSSFRow contentRow ;
			while (it.hasNext() && c < 5000) {
				contentRow = sheet.createRow((short) c);
				BaseVO vo = (BaseVO) it.next();
				Class voClass = vo.getClass();
				Iterator iter2 = fieldList.iterator();
				int i = 0;
				while (iter2.hasNext()) {
					XSSFCell cell = contentRow.createCell((short) i); 
					String field = (String) iter2.next();
					String parnetField=field;
					String subField=null;
					if(field.contains(".")){
						String[] subFields=field.split("\\.");
						if(subFields.length >1){
							parnetField=subFields[0];
							subField=subFields[1];
							//String fl=subFields[1].substring(0,1).toUpperCase();
							//field=subFields[0]+"().get"+fl+subFields[1].substring(1);
						}
					}
					String firstLetter = parnetField.substring(0, 1).toUpperCase();
					String getMethodName = "get" + firstLetter
							+ parnetField.substring(1);
					Method getMethod = voClass.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(vo, new Object[] {});
					if(isNotEmpty(subField)){
						 firstLetter = subField.substring(0, 1).toUpperCase();
						 getMethodName = "get" + firstLetter
								+ subField.substring(1);
						 Class valueClass = value.getClass();
						getMethod = valueClass.getMethod(getMethodName,
								new Class[] {});
						value = getMethod.invoke(value, new Object[] {});
					}
					String text;
					if (value == null) {
						text = "";
					} else if (value instanceof Calendar) {
						text = SemAppUtils.getFullTime((Calendar) value);
						//cell.setCellType(XSSFCell.)
					} else {
						text = value.toString();
					}
					cell.setCellValue(text);
					i++;
				}
				c++;
			}
			wb.write(os);
			//os.flush();
			//wbook.write(); // 写入文件
			//wbook.close();
		} catch (Exception e) {
			logger.error("导出文件出错", e);
			throw new CommonException("导出文件出错", e);

		}

	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * 
	 * @param s
	 *            原文件名
	 * @return 重新编码后的文件名
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static Date convertDate4JXL(java.util.Date jxlDate) {
		if (jxlDate == null)
			return null;
		try {
			TimeZone gmt = TimeZone.getTimeZone("GMT");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			dateFormat.setTimeZone(gmt);
			String str = dateFormat.format(jxlDate);

			TimeZone local = TimeZone.getDefault();
			dateFormat.setTimeZone(local);

			return dateFormat.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String object2String(Class voClass) {
		// Class voClass = getClass();

		Field[] fields = voClass.getDeclaredFields();
		String text = "";
		StringBuffer sb = new StringBuffer();
		System.out.println("fields.length" + fields.length);
		sb.append("Event[");
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

				value = getMethod.invoke(voClass, new Object[] {});
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
					text = (String) value;
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

	public static Integer getInteger(String str) {
		try {
			return new Integer(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		logger.debug("ip1=" + ip);
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.debug("ip2=" + ip);
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
			logger.debug("ip3=" + ip);
		}
		return ip;
	}
	
	public static int getUserDeviceType(String userAgent){
		String androidReg = "\\bandroid|Nexus\\b";
		String iosReg = "ip(hone|od|ad)";
		Pattern androidPat = Pattern.compile(androidReg, Pattern.CASE_INSENSITIVE);
		Pattern iosPat = Pattern.compile(iosReg, Pattern.CASE_INSENSITIVE);
		Matcher matcherAndroid = androidPat.matcher(userAgent);
		Matcher matcherIOS = iosPat.matcher(userAgent);
		if(matcherAndroid.find()){
			return 1;//android 
		}else if(matcherIOS.find()){
			return 2;//ios
		}else{
			return 0;//pc
		}
		
	}



}