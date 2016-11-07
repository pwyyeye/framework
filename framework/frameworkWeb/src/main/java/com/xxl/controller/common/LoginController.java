package com.xxl.controller.common;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;

import common.bussiness.CommonLogger;
import common.controller.BaseController;
import common.exception.BaseException;
import common.exception.CommonException;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.ItModuleVO;
import common.value.PageList;
import common.vo.BaseResponseVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController {

	public static Log logger = LogFactory.getLog(LoginController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(LoginController.class);

	@Autowired
	private CommonRemote commonRemote;
	 
	@Autowired
	public AdminRemote adminRemote;
	
	@Autowired
	HelperRemote helperRemote;

	private String ip;

	private String mainUrl;

	private Integer sessionModuleID;

	private String token;

	private String theme;
	
	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("helloword1");
		try {
			String token=commonRemote.getUserToken("123");
			logger.info("token="+token);
		} catch (Exception e) {
			logger.error("home error:"+e);
			this.handleException(e, request, response);
		}
		return "login";
	}
	

	@InitBinder("userVo")
	// 传入对象参数时，需要提前配置这项
    public void initBinder(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("userVo.");    
    } 
	

	
	@RequestMapping(value = "/logon.do")
	private void logon(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		String theRole = request.getParameter("ROLE_ID");

		ip = request.getRemoteAddr();
		String moduleStr = (String) getSessionAttribute(request,
				SemWebAppConstants.SESSION_MODULE_ID);
		sessionModuleID =moduleStr==null?null: new Integer(moduleStr);
		logger.debug("start logging as the role[" + theRole + "],moduleStr["
				+ moduleStr + "],moduleID[" + sessionModuleID + "]");
		UsersVO commUser = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		SessionUserBean user = getSessionUser(request);
		if (user != null && username == null) {
			commUser = user.getCommonUsersVO();
		} else {

			try {
				commUser = commonRemote.verifyUsersVO(username, password);
			} catch (Exception e1) {
				logger.error("用户名或密码有误！,username=" + username, e1);
				throw new BaseException("用户名或密码有误");
			}
		}
		Integer roleID = null;
		if (theRole != null) {
			try {
				roleID = new Integer(theRole);
			} catch (Exception ee) {
				roleID = null;
			}
		}
		if (commUser == null || !performLogon(commUser, request, roleID)) {
			throw new BaseException("非系统合法用户");
		}

	}
	
	@RequestMapping(value = "/")
	public void defaultMethod(
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr) {
		String lastAccessUrl = request
				.getParameter(SemWebAppConstants.LAST_ACCESS_PAGE);
		logger.debug("lastAccessUrl=" + lastAccessUrl);
		String switchStr = request.getParameter("switchRole");
		String cleanSessionStr = request.getParameter("cleanSession");
		request
				.setAttribute(SemWebAppConstants.LAST_ACCESS_PAGE,
						lastAccessUrl);
		try {
			if ("y".equalsIgnoreCase(cleanSessionStr)) {
				logger.debug("clean session");
				adminRemote.logout(
						this.getSessionUser(request).getEmpIDInt());
				// this.removeSessionAttribute(request,
				// SemWebAppConstants.USER_KEY);
				return ;
			}
			if ("y".equalsIgnoreCase(switchStr)) {
				logon(request, response);
			}
			logger.debug("switch role or theme");
			
//			String input = mapping.getInput();
			String path = request.getContextPath();  
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
			String requestURI=request.getRequestURI();  
			
			logger.debug("requestURI="+requestURI);
			String split = requestURI.indexOf("?") == -1 ? "?" : "&";
			logger.debug("go to main page" +"redirect:"+requestURI+ split + "theme=" + theme);
			attr.addAttribute("theme", theme);
			response.sendRedirect(basePath+"indexController/"+ split + "theme=" + theme);
//			return "redirect:"+basePath+path+"/indexController/"+ split + "theme=" + theme;

		} catch (Exception ee) {
			this.handleException(ee, request, response);
			return ;
		}

	}
	@RequestMapping(value = "/list.do")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String msg = "{success:true}";
		try {
			logon(request, response);
			msg = "{success:true,msg:'" + token + "'}";
		} catch (BaseException ee) {
			logger.error("logon failer", ee);
			msg = "{success:false,msg:'" + ee.getMessage() + "'}";
		}
		try {
			logger.debug("reponse from action" + msg);
			response.getWriter().write(msg);
		} catch (IOException e) {
		}
		return;
	}


	public boolean performLogon(UsersVO user, HttpServletRequest request,
			Integer roleID) {
		logger.debug("start perform logon module[" + sessionModuleID + "]");
		try {
			String token = commonRemote.getUserToken((Integer) user.getId());
			if (sessionModuleID == null) {
				sessionModuleID = new Integer(SemAppConstants.COMMON_MODULE_ID);
				logger.debug("logon default module");
				setSessionAttribute(request,
						SemWebAppConstants.SESSION_MODULE_ID, ""
								+ sessionModuleID);
			}
			logger.debug("user=" + user.getCode() + "sessionModuleID="
					+ sessionModuleID + "ip=" + ip);
			SessionUserBean userBean = adminRemote.getSessionUserBean(user,
					sessionModuleID, ip, roleID, token);
			// 将moduleID还原
			ItModuleVO module = userBean.getModule();
			logger.debug("change module to [" + module.getName() + "]");
			// ItModuleVO module = adminRemote.getSystemByID(moduleID);
			this.setSessionAttribute(request,
					SemWebAppConstants.SESSION_MODULE, module);
			setSessionAttribute(request, SemWebAppConstants.SESSION_MODULE_ID,
					"" + (Integer) module.getId());
			if (userBean == null) {
				return false;// 非法用户
			}
			setSessionAttribute(request, SemWebAppConstants.USER_KEY, userBean);

			token = userBean.getToken();
			// 获取用户的theme
			theme = userBean.getProperty("USER_DEFAULT_THEME");
			if (SemWebAppUtils.isEmpty(theme)) {
				try {
					theme = helperRemote.getProperty("USER_DEFAULT_THEME");
				} catch (Exception e) {
					logger.error("get user theme fail", e);
				}
			}
			request.setAttribute("theme", theme);

			logger.debug("current user theme=[" + theme + "],deptID=["
					+ userBean.getCommonUser().getDeptid() + "]");
			CommonLogger log = new CommonLogger((Integer) user.getId(), "01",
					"以" + userBean.getRole().getName() + "登录系统", "moduleID="
							+ sessionModuleID, ip);
			dbLogger.info(log);
		} catch (BaseException e) {
			logger.error("登录失败", e);
			return false;
		} catch (Exception e) {
			logger.error("登录失败", e);
			return false;
		}
		return true;
	}

	public boolean performLogon(UsersVO user, HttpServletRequest request) {
		return performLogon(user, request, null);
	}

	

}