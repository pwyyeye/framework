package com.xxl.controller.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xxl.facade.AdminRemote;

import common.controller.BaseController;
import common.web.utils.SemWebAppConstants;

@Controller
@RequestMapping("/logoffController")
public class LogoffController extends BaseController {
	public static Log logger = LogFactory.getLog(LogoffController.class.getName());

	public static Log sysLogger = LogFactory.getLog("sys");
	@Autowired
	public AdminRemote adminRemote;
	
	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr) {
		try {
			adminRemote.logout(
					this.getSessionUser(request).getEmpIDInt());

		} catch (Exception e) {
			logger.error("clean online user fail,",e);
		}

		this.removeSessionAttribute(request, SemWebAppConstants.USER_KEY);
		return  "redirect:/loginController/home";
	}

}
