package common.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xxl.facade.CommonRemote;

import common.exception.BaseException;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.utils.SpringUtils;
import common.value.JsonResult;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

public class SemLogonFilter implements Filter {
	public Log logger = LogFactory.getLog(this.getClass());

	protected FilterConfig filterConfig = null;

	protected boolean proxy_login = false;

	protected String[] needlessFilterPages = null;

	private String login_name;

	private String usernameField = null;

	private String passwordField = null;

	private String login_page;

	private String login_impl_class;

	private String[] filterPageSuffixs = null;

	private String[] needCheckAccess = null;

	private String check_access_class;

	private String LAST_SESSION_NAME;

	private String session_user_class;

	private String system;

	private CommonRemote  commonRemote;
	
	
	public void destroy() {
		this.proxy_login = false;
		this.filterConfig = null;

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String errorMessage = null;
		boolean notLogin = true;
		String errorCode = null;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;

		String url = new String(req.getRequestURL());
		String queryStr = req.getQueryString();

		String uri = req.getRequestURI();
		String page = getReqPage(uri);
		logger.debug("go to url->" + url + "?" + queryStr + ",page=" + page);
		HttpSession hSession = req.getSession();
		Integer systemID = null;
		if (page.equals(this.login_page)) {
			chain.doFilter(request, response);
			return;
		}
		
		String suffix = getFileSuffix(uri);
		boolean needFilter = false;
		if (filterPageSuffixs != null) {
			for (int i = 0; i < filterPageSuffixs.length && !needFilter; i++) {
				if (suffix.equalsIgnoreCase(filterPageSuffixs[i]))
					needFilter = true;
			}
		}
		if (needlessFilterPages != null && needFilter) {
			for (int i = 0; i < needlessFilterPages.length; i++) {
				if (page.equalsIgnoreCase(needlessFilterPages[i])) {
					needFilter = false;
				}
			}
		}
		Object userInstance = hSession.getAttribute(login_name);
		Map map =req.getParameterMap();
		Set keys=map.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			logger.debug("object====="+object);
			
		}
		String token = (String) req
				.getParameter(SemWebAppConstants.SEM_LOGIN_TOKEN);
		String username = (String) req.getParameter(usernameField);
		String password = (String) req.getParameter(passwordField);
		String openId = (String) req.getParameter("currentSessionId");
		logger.debug("SEM_LOGIN_TOKEN="+token+"=============="+req.getParameter("SEM_LOGIN_TOKEN"));
		if ((SemWebAppUtils.isNotEmpty(token)
				|| SemWebAppUtils.isNotEmpty(username) || SemWebAppUtils
				.isNotEmpty(openId))
				&& userInstance != null) {
			try {
				SessionUserBean user = (SessionUserBean) userInstance;
				logger.debug("current session token is[" + user.getToken()
						+ "],and request token is[" + token + "]");
				if (!user.getToken().equals(token)
						|| SemWebAppUtils.isNotEmpty(username)
						|| SemWebAppUtils.isNotEmpty(openId)) {
					logger.debug("clean current user");
					userInstance = null;// clean current user session
				}
			} catch (Exception ee) {
				logger.warn("login type is not standard!");// warn only,not
				// bussiness
			}
		}
		// if token has changed,must relogin
		UsersVO loginUser = null;
		if (userInstance == null && needFilter) {
			// logger.debug("page[" + page + "]need Fileter");
			boolean hasLogin = false;
			String ip = this.getRemortIP(req);
			// 对于COMMON模块，需要将当前moduleID放到session中
			String currentModuleStr = (String) request
					.getParameter(SemWebAppConstants.SESSION_MODULE_ID);
			int currentModule = SemAppConstants.COMMON_MODULE_ID;
			try {
				currentModule = Integer.parseInt(currentModuleStr);
			} catch (Exception ee) {
			}
			if (hSession.getAttribute(SemWebAppConstants.SESSION_MODULE_ID) == null) {
				hSession.setAttribute(SemWebAppConstants.SESSION_MODULE_ID, ""
						+ currentModule);
			}
			logger.debug("token=" + token + "&username=" + username
					+ "&openid=" + openId);
			if (token != null || username != null || openId != null) {
				// token = token.replaceAll("-", "+");
				try {
					InitialContext context = new InitialContext();
//					CommonHome home = (CommonHome) javax.rmi.PortableRemoteObject
//							.narrow(context.lookup("ejb/CommonEJB"),
//									CommonHome.class);
//					CommonRemote common = home.create();
					
					logger
							.debug("start sso token[" + token + "]ip[" + ip
									+ "]");
					commonRemote = (CommonRemote) SpringUtils.servletGetBean(req.getSession().getServletContext(), "commonRemote");
					if (token != null) {
						errorMessage = "连接超时，请重新登录!";
						notLogin = false;
						
						loginUser = commonRemote.getEofficeLoginUserVO(token, ip);

					} else if (username != null) {
						errorMessage = "用户名或密码错误";
						notLogin = false;
						try {
							loginUser = commonRemote
									.verifyUsersVO(username, password);
						} catch (BaseException ee) {
							errorMessage = ee.getMessage();
						}

					} else if (openId != null) {
						logger.debug("log in by openId[" + openId + "]");
						errorMessage = "当前用户未注册";
						notLogin = false;
						errorCode = "-2";
						loginUser = commonRemote.getEofficeLoginUserVO(openId);
					}
					logger.debug("result:loginUser=" + loginUser);
					if (loginUser == null) {// oa token unvalid
						hasLogin = false;
					} else if (this.proxy_login) {
						try {
							int systemInt = Integer.parseInt(system);
							systemID = (systemInt == SemAppConstants.COMMON_MODULE_ID) ? new Integer(
									currentModule)
									: new Integer(system);
							logger.debug("switch in module[" + systemID + "]");
						} catch (Exception ee) {
							logger.error("web-inf配置文件中filter的参数SYSTEM_ID设置有误",
									ee);
							return;
						}
						commonRemote.logonOASystem(loginUser.getId(), ip);
						SessionUserBean userBean = commonRemote.getSemSessionUser(
								loginUser, systemID, ip);

						logger.debug("userBean=" + userBean);
						if (userBean != null) {
							logger.debug("logging role id ="
									+ userBean.getRole().getId());
							hSession.setAttribute(login_name, userBean);
							hasLogin = true;
//							RedisUtil.sessionUser2Redis(userBean);
						}
					} else {
						SemLogonInterface semLogon = null;
						try {
							Class importeImpl = Class.forName(login_impl_class);
							semLogon = (SemLogonInterface) importeImpl
									.newInstance();
							hasLogin = semLogon.performLogon(loginUser, req);
						} catch (Throwable t) {
							logger.error("web-inf参数login_impl_class配置有误", t);
						}
					}

				} catch (Exception ee) {
					logger.error("TOKEN无效", ee);
					loginUser = null;
				}
			}

			// 未登录
			if (!hasLogin) {
				if (LAST_SESSION_NAME != null)
					hSession.setAttribute(LAST_SESSION_NAME, url);
				logger.debug("go to login_page=" + login_page);
				String userAgent = req.getHeader("USER-AGENT").toLowerCase();
				logger.debug("user agent" + userAgent);
				int type = SemWebAppUtils.getUserDeviceType(userAgent);
				logger.debug("user device type is" + type + "error message"
						+ errorMessage + ",notLogin=" + notLogin);
				if (SemAppUtils.isEmpty(errorMessage))
					errorMessage = "用户未登录";
				JsonResult result = new JsonResult(false, null, errorMessage,
						notLogin);
				if (SemAppUtils.isNotEmpty(errorCode)) {
					logger.debug("login by openId" + errorCode);
					result.setErrorcode(errorCode);
				}
				response.setContentType("text/json;charset=UTF-8");
				response.getWriter().write(result.toString());
				logger.debug(result.toString());
				// response.getWriter().flush();
				// response.flushBuffer();
				if (type == 0) {
					String path = req.getContextPath();  
					String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
					hres.sendRedirect(hres.encodeURL(basePath+login_page + "?"
							+ SemWebAppConstants.SESSION_MODULE_ID + "="
							+ system + "&"
							+ SemWebAppConstants.LAST_ACCESS_PAGE + "="
							+ URLEncoder.encode(url + "?" + queryStr)));
				}
				return; //
			}
		}
		// �ж��û��ķ��ʵ�ַ�Ƿ�Ϸ�
		if (needCheckAccess != null && check_access_class != null
				&& session_user_class != null && needFilter) {
			boolean need = false;
			if (needCheckAccess != null) {
				for (int i = 0; i < needCheckAccess.length && !need; i++) {
					if (suffix.equalsIgnoreCase(needCheckAccess[i]))
						need = true;
				}
			}
			if (need) {
				Class classImpl;
				CheckAccessInterface checkInstance = null;
				if (loginUser == null) {
					if (userInstance == null) {
						logger.error("session_user_class参数配置有误"
								+ session_user_class);
						return;
					} else {
						loginUser = ((SessionUserInterface) userInstance)
								.getCommonUsersVO();
					}
				}

				try {
					classImpl = Class.forName(check_access_class);
					checkInstance = (CheckAccessInterface) classImpl
							.newInstance();
					if (checkInstance == null) {
						logger.error("check_access_class参数有误"
								+ check_access_class);

						return;
					}
					if (!checkInstance.performCheck(loginUser, page)) {
						logger.error("�Ƿ�����ϵͳ,username="
								+ loginUser.getCode() + ",page=" + page);

						return;
					}
				} catch (Exception e) {
					logger.error(
							"check_access_class��session_user_class������������,check_access_class="
									+ check_access_class
									+ "session_user_class="
									+ session_user_class + ".����web.xml�ļ�.",
							e);
					return;
				}

			}
		}

		chain.doFilter(request, response);
	}

	public String getRemortIP(HttpServletRequest request) {
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

	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;
		String needlessFilterPagesStr = filterConfig
				.getInitParameter("needlessFilterPages");
		if (needlessFilterPagesStr != null) {
			this.needlessFilterPages = needlessFilterPagesStr.split(",");
		}
		String filterPageSuffixStr = filterConfig
				.getInitParameter("filterPageSuffixs");
		if (filterPageSuffixStr != null) {
			filterPageSuffixs = filterPageSuffixStr.split(",");
		}
		String lastLinkTypesStr = filterConfig
				.getInitParameter("needCheckAccess");
		if (lastLinkTypesStr != null) {
			needCheckAccess = lastLinkTypesStr.split(",");
		}
		login_name = filterConfig.getInitParameter("LOGIN_SESSION_NAME");
		if (SemWebAppUtils.isEmpty(login_name))
			login_name = SemWebAppConstants.USER_KEY;
		login_page = filterConfig.getInitParameter("LOGIN_PAGE");
		if (SemWebAppUtils.isEmpty(login_page))
			login_page = SemWebAppConstants.COMMON_LOGIN_PAGE;
		logger.debug("initialize login_page=" + login_page);
		login_impl_class = filterConfig.getInitParameter("LOGIN_IMPL_CLASS");
		LAST_SESSION_NAME = filterConfig.getInitParameter("finalURL");
		check_access_class = filterConfig
				.getInitParameter("CHECK_ACCESS_CLASS");
		session_user_class = filterConfig
				.getInitParameter("SESSION_USER_CLASS");
		system = filterConfig.getInitParameter("SYSTEM_ID");
		String value = filterConfig.getInitParameter("PROXY_LOGIN");
		this.proxy_login = (value != null)
				&& (value.equalsIgnoreCase("true") || value
						.equalsIgnoreCase("yes"));
		usernameField = filterConfig.getInitParameter("username");
		if (SemWebAppUtils.isEmpty(usernameField)) {
			usernameField = "username";
		}
		passwordField = filterConfig.getInitParameter("password");
		if (SemWebAppUtils.isEmpty(passwordField)) {
			passwordField = "password";
		}
	}

//	private String getFileSuffix(String filename) {
//		if (filename == null)
//			return null;
//		if(filename.lastIndexOf("?")!=-1){
//			filename=filename.substring(0, filename.lastIndexOf("?"));
//		}
//		int i = filename.lastIndexOf(".");
//		if (i < 0 || i >= filename.length() - 1) {
//			return "";
//		}
//		return filename.substring(i + 1);
//	}
	
	private String getFileSuffix(String filename) {
		if (filename == null)
			return null;
		if(filename.indexOf("?")!=-1){
			filename=filename.substring(0, filename.indexOf("?"));
		}
		int i = filename.lastIndexOf(".");
		if (i < 0 || i >= filename.length() - 1) {
			int j = filename.lastIndexOf("/");
			if(j>=0){
				return "do";
			}else {
				return "";
			}
			
		}
		return filename.substring(i + 1);
	}

//	private String getReqPage(String filename) {
//		if (filename == null)
//			return null;
//		int i = filename.lastIndexOf("/");
//		int j = filename.indexOf("?");
//		if (i < 0 || i >= filename.length() - 1) {
//			return "";
//		}
//		int k=filename.substring(0, i).lastIndexOf("/");
//		if(k==-1){
//			return j < i ? filename.substring(i + 1) : filename.substring(i + 1, j);
//		}else{
//			return  filename.substring(k + 1, j);
//		}
//		
//	}
	 private String getReqPage(String filename) {
			if (SemAppUtils.isEmpty(filename))
				return "";
			int i = filename.lastIndexOf("/");
			int j = filename.indexOf("?");
			if (i < 0 || i > filename.length() - 1) {
				return "";
			}else if(i == (filename.length() - 1)){
				//情况二
				String temp="";
				try {
					temp= filename.substring(0, i-1);
				} catch (Exception e) {
					return "";
				}
				filename=filename.substring(temp.lastIndexOf("/")+1, i);
				return filename;
			}
			
			/**
				例如：filename =  情况1、"/frameworkWeb/loginController/home";
								情况2、/frameworkWeb/loginController/
								情况3、/frameworkWeb/loginController/login.do
				
			*/
			
			int k=i==0?-1:filename.substring(0, i-1).lastIndexOf("/");
			if(k==-1){
				return j < i ? filename.substring(i + 1) : filename.substring(i + 1, j);
			}else{
				return filename.substring(k+1, i);
			}
			
		}

} // EOC
