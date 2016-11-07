package com.xxl.controller.common;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;

import common.controller.BaseController;
import common.value.ItModuleVO;
import common.value.SystemPropertyVO;
import common.value.TreeControl;
import common.value.UserPropertiesVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/indexController")
public class IndexController extends BaseController {
	public Log logger = LogFactory.getLog(IndexController.class);
	
	@Autowired
	public AdminRemote adminRemote;
	
	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		logger.debug("start enter index page");
		String mainUrl = null;
		ItModuleVO module = (ItModuleVO) this.getSessionAttribute(request,
				SemWebAppConstants.SESSION_MODULE);
		SessionUserBean userBean = (SessionUserBean) this.getSessionAttribute(
				request, SemWebAppConstants.USER_KEY);
		String lastAccessUrl = request
				.getParameter(SemWebAppConstants.LAST_ACCESS_PAGE);

		if (SemWebAppUtils.isNotEmpty(lastAccessUrl)) {
			// lastAccessUrl = lastAccessUrl.substring(1,
			// lastAccessUrl.length() - 1);z
			String split = lastAccessUrl.indexOf("?") != -1 ? "&" : "?";
			lastAccessUrl = lastAccessUrl + split
					+ SemWebAppConstants.SEM_LOGIN_TOKEN + "="
					+ userBean.getToken();
			lastAccessUrl = URLDecoder.decode(lastAccessUrl);
			logger.debug("lastAccessUrl=" + lastAccessUrl);
			response.sendRedirect(lastAccessUrl);
			// return new ActionForward(lastAccessUrl,true);
		}

		if (module != null) {
			mainUrl = module.getIndexPage();
			if (mainUrl == null)
				mainUrl = "switchRoleController/";
			// String token = TheUtility.getLogonToken(user.getCode());
			String split = mainUrl.indexOf("?") != -1 ? "&" : "?";
			mainUrl = URLDecoder.decode(mainUrl + split
					+ SemWebAppConstants.SEM_LOGIN_TOKEN + "="
					+ userBean.getToken());

		}
		/**
		 * handle theme
		 */
		HttpSession httpSession = request.getSession();
		SessionUserBean currentUser = getSessionUser(request);
		String theme = request.getParameter("theme");
		if (SemWebAppUtils.isNotEmpty(theme)) {
			SessionUserBean sessionUser = getSessionUser(request);
			UserPropertiesVO vo = new UserPropertiesVO();
			vo.setUsId(sessionUser.getEmpIDInt());
			//if(!sessionUser.getProperties().containsKey("USER_DEFAULT_THEME"))
			//{
			   vo.setId("USER_DEFAULT_THEME" + sessionUser.getEmpID());//update
			//}
			SystemPropertyVO pVO = new SystemPropertyVO();
			pVO.setId("USER_DEFAULT_THEME");
			pVO.setName("USER_DEFAULT_THEME");
			vo.setProperty(pVO);
			vo.setValue(theme);
			vo.setSetUser("" + sessionUser.getEmpID());
			vo.setSetDate(Calendar.getInstance());
			adminRemote.setUserProperties(vo);
			currentUser.setProperty("USER_DEFAULT_THEME",theme);
			this.setSessionAttribute(request, SemWebAppConstants.USER_KEY, currentUser);//update theme
		}
         
		request.setAttribute("main_page", response.encodeURL(mainUrl));
		logger.debug("get to main page" + mainUrl);
		logger.debug("start setup tree");		
		
		
		String moduleID = (String) getSessionAttribute(request,
				SemWebAppConstants.SESSION_MODULE_ID);
		logger.debug("moduleID=" + moduleID);
		// TreeControl treeControl = (TreeControl) httpSession
		// .getAttribute("menu_control_test");
		// if (treeControl == null)
		TreeControl treeControl = adminRemote.getMenuTree(moduleID,
				currentUser);
		logger.debug("treeControl width="
				+ (treeControl == null ? 0 : treeControl.getWidth()));
		httpSession.setAttribute("menu_control_test", treeControl);
		//
		
		return "main";
	}

}
