package com.xxl.controller.common;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.controller.BaseController;

@Controller
@RequestMapping("/portalController")
public class PortalController extends BaseController {
	public static Log logger = LogFactory.getLog(PortalController.class.getName());

	public static Log sysLogger = LogFactory.getLog("sys");

	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr) {
		logger.debug("switch role page");
		String theme=this.getSessionUser(request).getProperty("USER_DEFAULT_THEME");
		
		attr.addAttribute("theme", theme+"&");
		Map params=request.getParameterMap();
		attr.mergeAttributes(params);
		
		return  "portal";
	}

}
