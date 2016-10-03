package com.common.utils;

import javax.servlet.ServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtils {

	public static Object getBean(String beanName) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object object = (Object) wac.getBean(beanName);
		return object;
	}
	
	public static Object servletGetBean(ServletContext servletContext, String beanName) {
	    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
	    Object object = (Object) wac.getBean(beanName); //Spring 配置中的 bean id
	    return object;
	}
	
}
