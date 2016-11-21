package com.xxl.controller.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.HelperRemote;

import common.controller.BaseController;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;
@Controller
@RequestMapping("/lockController")
public class LockController extends BaseController {

	public static Log logger = LogFactory.getLog(LockController.class);

	public static Log dbLogger = SemAppUtils.getDBLog(LockController.class);
	@Autowired
	HelperRemote helperRemote;
	
	public void add(
			HttpServletRequest request, HttpServletResponse response) {

	}

	protected PageList getDatas(
			HttpServletRequest request, HttpServletResponse response,
			boolean pagable) throws Exception {
		try {
			
			PageList tableList = helperRemote.getLocksList();
			return tableList;
		} catch (Exception ee) {
			this.handleException(ee, request, response);
			return null;
		}
	}
	
	@RequestMapping("/list")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			PageList roleList = getDatas(request, response, true);
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(roleList,true);
			response.getWriter().write(json);
		} catch (Exception ee) {
			this.handleException(ee, request, response);
		}
	}


	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String lineIds = request.getParameter("Ids");
		String[] ids = lineIds.split("-");
		
		try {

			for (int i = 0; i < ids.length; i++) {
				
				helperRemote.cleanSignleMode(new Integer(ids[i]));
				
				this.doDBLogger(dbLogger,request,"11","清除进程完成","id["+ids[i]+"]");
			}
			response.getWriter().write("{success:true}");
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
		}
	}


	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		if(this.getRightCode(request).contains("A")){//表示有管理员权限
			request.setAttribute("ADMIN_RIGHT", "Y");
		}
		return "lock";
	}

}
