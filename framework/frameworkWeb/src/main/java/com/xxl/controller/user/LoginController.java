package com.xxl.controller.user;

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

import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;

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

	private String ip;

	private String mainUrl;

	private Integer sessionModuleID;

	private String token;

	private String theme;
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public String login(HttpServletRequest request,HttpServletResponse response) {
		String userIdStr = request.getParameter("id");
		if (userIdStr != null && !userIdStr.equals("")) {
			int userId = Integer.parseInt(userIdStr);
//			userVo = this.userService.findById(userId);
		
		}
		System.out.println("helloword1");
		return "123";
	}

	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("helloword1");
		try {
			commonRemote.getUserToken("123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "login";
	}
	
	// easyui列表分页测试
//	public void login(
//			HttpServletRequest request,
//			HttpServletResponse response,
//			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
//			@RequestParam(required = false, defaultValue = "10") Integer rows, // 页数大小
//			@RequestParam(required = false, defaultValue = "") String paramName,
//			@RequestParam(required = false, defaultValue = "") String createTime) {
//		try {
//			response.setContentType("text/json;charset=UTF-8");
//			PageList pageList = this.userService.findByPage(new UserVo(),
//					(page - 1) * rows, rows);
//			/*
//			 * Map<String,Object> map = new HashMap<String,Object>();
//			 * map.put("rows", pageList.getItems()); 
//			 * map.put("total", pageList.getResults());
//			 */
//			List b1 = new ArrayList();
//			b1.add("com.xxl.hnust.vo.UserVo#password");
//			b1.add("com.xxl.hnust.vo.UserVo#age");
//			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1",
//					"获取用户列表成功", pageList), b1, "yyyy-MM-dd HH:mm:ss");
//			response.getWriter().write(json);
//		} catch (IOException e) {
//			logger.error("获取用户列表发生异常!", e);
//			this.handleException(new IOException("获取用户列表发生异常!"), request, response);
//		}
//	}

	@InitBinder("userVo")
	// 传入对象参数时，需要提前配置这项
    public void initBinder(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("userVo.");    
    } 
	

	
	@RequestMapping(value = "/logon")
	private void logon(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		String theRole = request.getParameter("ROLE_ID");

		ip = request.getRemoteAddr();
		String moduleStr = (String) getSessionAttribute(request,
				SemWebAppConstants.SESSION_MODULE_ID);
		sessionModuleID = new Integer(moduleStr);
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
//				commUser = SemAppUtils.verifyUsersVO(username, password);
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
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
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
				return null;
			}
			if ("y".equalsIgnoreCase(switchStr)) {
				logon(request, response);
			}
			logger.debug("switch role or theme");
			
//			String input = mapping.getInput();
//			String path = request.getContextPath();  
//			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
			String requestURI=request.getRequestURI();  
			
			logger.debug("requestURI="+requestURI);
			String split = requestURI.indexOf("?") == -1 ? "?" : "&";
//			ActionForward forward = new ActionForward(mapping.findForward(
//					"main").getPath()
//					+ split + "theme=" + theme);
//			forward.setRedirect(true);
			logger.debug("go to main page" +"redirect:"+requestURI+ split + "theme=" + theme);
			return "redirect:"+requestURI+ split + "theme=" + theme;

		} catch (Exception ee) {
			this.handleException(ee, request, response);
			return null;
		}

	}
	@RequestMapping(value = "/list")
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

	// public ActionForward custom(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	// ItModuleVO
	// module=(ItModuleVO)this.getSessionAttribute(request,SemWebAppConstants.SESSION_MODULE);
	// SessionUserBean
	// userBean=(SessionUserBean)this.getSessionAttribute(request,SemWebAppConstants.USER_KEY);
	// if (module != null) {
	// mainUrl = module.getIndexPage();
	// if (mainUrl == null)
	// mainUrl = "switchRoleAction.do";
	// //String token = TheUtility.getLogonToken(user.getCode());
	// String split = mainUrl.indexOf("?") != -1 ? "&" : "?";
	// mainUrl = mainUrl + split + SemWebAppConstants.SEM_LOGIN_TOKEN
	// + "=" +userBean.getToken() ;
	// }
	// request.setAttribute("main_page", response.encodeURL(mainUrl));
	// logger.debug("get to main page"+mainUrl);
	// return mapping.findForward("main");
	// }

	public boolean performLogon(UsersVO user, HttpServletRequest request,
			Integer roleID) {
		logger.debug("start perform logon module[" + sessionModuleID + "]");
		try {
			String token = SemAppUtils.getLogonToken((Integer) user.getId());
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
					theme = SemAppUtils.getProperty("USER_DEFAULT_THEME");
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
		} catch (RemoteException e) {
			logger.error("登录失败", e);
			return false;
		}
		return true;
	}

	public boolean performLogon(UsersVO user, HttpServletRequest request) {
		return performLogon(user, request, null);
	}

	

}