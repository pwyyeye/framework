package com.xxl.controller.common;


import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

import common.utils.SemAppUtils;
import common.value.PageList;
import common.value.UABindingVO;
import common.web.utils.SemWebAppUtils;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;

import common.controller.BaseController;

@Controller
@RequestMapping("/initUserRoleController")
public class InitUserRoleController extends BaseController {
	public static Log logger = LogFactory.getLog(InitUserRoleController.class);

	public static Log sysLogger = LogFactory.getLog("sys");
	@Autowired
	public AdminRemote adminRemote;
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,UABindingVO ubVO) {
		response.setContentType("text/json;charset=UTF-8");
		try {
		
			String roleStr = ubVO.getRole();
			ubVO.setRoleID(new Integer(roleStr));
			ubVO.setOrganise(this.getSessionUser(request).getOrganise().getId());
			Integer uaBindingID = adminRemote.addUABinding(ubVO);
			response.getWriter().write(
					"{success:true,id:" + uaBindingID + "}");
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
		}
	}
	@RequestMapping("/list")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("get the UABINDING LIST");
		String moduleStr = request.getParameter("moduleID");		
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String roleID = request.getParameter("roleID");
		String empID = request.getParameter("empID");
		String levelID = request.getParameter("levelID");
		String deptID = request.getParameter("deptID");
		String organise = request.getParameter("organise");
		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			start = 0;
			limit = 0;
		}
		logger.debug("start="+start+",limit="+limit);
		Integer module = null;
		if (moduleStr != null) {
			try {
				module = new Integer(moduleStr);
			} catch (Exception ee) {
				
			}
		}
	
		if(module==null)  module=this.getSessionModuleID(request);
		boolean filter = false;
		UABindingVO ubVO = new UABindingVO();
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			ubVO.setModuleID(new Integer(moduleStr));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(roleID)) {
			ubVO.setRoleID(new Integer(roleID));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(empID)) {
			ubVO.setEmpID(SemAppUtils.getInteger(empID));
			filter = true;

		}
		if (SemWebAppUtils.isNotEmpty(organise)) {
			ubVO.setOrganise(new Integer(organise));
			filter = true;
		} else {
			ubVO.setOrganise(this.getSessionUser(request).getOrganise()
					.getId());
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(deptID)) {
				ubVO.setDeptID(SemAppUtils.getInteger(deptID));
				filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(levelID)) {
			ubVO.setLevelID(SemAppUtils.getInteger(levelID));
			filter = true;
	}
		try {
			
			PageList eventList = adminRemote.getUABindingList(module,
					ubVO, new Integer(start), new Integer(limit));
			response.setContentType("text/json;charset=UTF-8");
			String json = SemAppUtils.getJsonFromBean(eventList);
			// logger.debug(json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
	}
	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,UABindingVO uaVO ) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			uaVO.setRoleID(new Integer(uaVO.getRole()));
			adminRemote.updateUABinding(uaVO);
			response.getWriter().write(
					"{success:true,id:" + uaVO.getId() + "}");
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
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
				adminRemote.deleteUABinding(new Integer(ids[i]));
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
		try {
			
			List moduleList = adminRemote.getSystemList(getSessionModuleID(request));
			request.setAttribute("module_list", moduleList);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
		return "showUserRole";
	}
}
