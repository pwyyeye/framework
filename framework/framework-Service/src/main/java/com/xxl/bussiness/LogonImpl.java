package com.xxl.bussiness;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;

import common.bussiness.CommonLogger;
import common.filter.SemLogonInterface;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.utils.SpringUtils;
import common.value.ItModuleVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;

public class LogonImpl implements SemLogonInterface{
	public Log logger = LogFactory.getLog(this.getClass());

	public static Log sysLogger = LogFactory.getLog("sys");
	protected InitialContext context = null;
	private AdminRemote adminSession;
	
	public boolean performLogon(UsersVO user, HttpServletRequest request,Integer roleID) {
		logger.debug("start perform logon...");
		try {
			CommonRemote commonRemote = (CommonRemote) SpringUtils.servletGetBean(request.getSession().getServletContext(), "commonRemote");
			String token=commonRemote.getUserToken((Integer)user.getId());
			String moduleStr=(String)request.getSession().getAttribute(SemWebAppConstants.SESSION_MODULE_ID);
			Integer moduleID=null;
			try{
				moduleID=new Integer(moduleStr);			
			}catch(Exception ee){}
			if(moduleID==null){
				moduleID=new Integer(SemAppConstants.COMMON_MODULE_ID);
				logger.debug("logon default module");
				request.getSession().setAttribute(SemWebAppConstants.SESSION_MODULE_ID, "" + moduleID);
			}
			adminSession = (AdminRemote) SpringUtils.servletGetBean(request.getSession().getServletContext(), "adminSession");
			SessionUserBean userBean = adminSession.getSessionUserBean(user,
					moduleID, "",roleID,token);
			ItModuleVO module = adminSession.getSystemByID(moduleID);
			request.setAttribute(SemWebAppConstants.SESSION_MODULE, module);
			if (userBean == null) {
				return false;// 非法用户
			}
			request.setAttribute(SemWebAppConstants.USER_KEY, userBean);

			
			CommonLogger log = new CommonLogger((Integer)user.getId(), "01", "以"
					+ userBean.getRole().getName() + "登录系统", "moduleID="
					+ moduleID, "");
			logger.info(log);
		} catch (Exception e) {
			logger.error("登录失败", e);
			return false;
		} 
		return true;
	}

	
	private InitialContext initContext() throws NamingException{
//		 String url=(String) servlet.getServletConfig()
//		.getInitParameter("ejbServer");
//		 if(url!=null){
//			String weblogicuser = null;
//			String password = null;
//			Properties properties = null;
//			properties = new Properties();
//			properties.put(Context.INITIAL_CONTEXT_FACTORY,
//					"weblogic.jndi.WLInitialContextFactory");
//			properties.put(Context.PROVIDER_URL, url);
//			if (weblogicuser != null) {
//				properties.put(Context.SECURITY_PRINCIPAL, weblogicuser);
//				properties.put(Context.SECURITY_CREDENTIALS,
//						password == null ? "" : password);
//			}
//			return new InitialContext(properties);
//		 }else{
			 return new InitialContext();
//		 }
	}

	public boolean performLogon(UsersVO user, HttpServletRequest request) {
		return performLogon(user,request,null);
	}

	
}
