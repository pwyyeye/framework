package com.xxl.controller.common;


import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

import common.controller.BaseController;
import common.value.PageList;
import common.value.RoleVO;
import common.web.utils.SemWebAppUtils;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {
	public static Log logger = LogFactory.getLog(RoleController.class);

	public static Log sysLogger = LogFactory.getLog("sys");

	@Autowired
	public AdminRemote adminRemote;
	
	@RequestMapping(value = "/add.do")
	public void add(
			HttpServletRequest request, HttpServletResponse response,RoleVO roleVO) {
		response.setContentType("text/json;charset=UTF-8");
		try {
		
			logger.debug("add,module id =" + roleVO.getModuleID()+"organise="+roleVO.getOrganise());
			
			if(roleVO.getOrganise()==null){
				roleVO.setOrganise(this.getSessionUser(request).getOrganise().getId());
			}
			Integer roleID = adminRemote.addRole(roleVO);
			response.getWriter().write("{success:true,id:" + roleID + "}");
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
		}
	}
	@RequestMapping(value = "/list.do")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("get the role list");
		String moduleStr = request.getParameter("MODULE_ID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String name = request.getParameter("name");
		String organise = request.getParameter("organise");
		String description = request.getParameter("description");
		logger.debug("name=" + name + ",module" + moduleStr + ",descp="
				+ description);
		// 处理检索条件
		boolean filter = false;
		RoleVO roleVO = new RoleVO();
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			roleVO.setModuleID(new Integer(moduleStr));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(organise)) {
			roleVO.setOrganise(new Integer(organise));
			filter = true;
		} else {
			roleVO.setOrganise(this.getSessionUser(request).getOrganise()
					.getId());
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(name)) {
			roleVO.setName(name);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(description)) {
			roleVO.setName(description);
			filter = true;
		}
		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			start = 0;
			limit = 0;
		}
		logger.debug("start start=" + start + ",limit=" + limit);
		Integer module = null;
		if (moduleStr != null) {
			try {
				module = new Integer(moduleStr);
			} catch (Exception ee) {

			}
		}
		if (module == null)
			module = this.getSessionModuleID(request);
		try {
			
			PageList roleList = adminRemote.getRoleList(module,
					filter ? roleVO : null, new Integer(start), new Integer(
							limit));
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(roleList);
			// logger.debug(json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
	}
	@RequestMapping(value = "/update.do")
	public void update(HttpServletRequest request, HttpServletResponse response,RoleVO roleVO) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
		
			Integer roleID = roleVO.getId();
			if(roleVO.getOrganise()==null){
				roleVO.setOrganise(this.getSessionUser(request).getOrganise().getId());
			}
			adminRemote.updateRole(roleVO);
			response.getWriter().write("{success:true,id:" + roleID + "}");
		} catch (Exception ee) {
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
			logger.error("业务逻辑层异常", ee);
		}
	}
	@RequestMapping(value = "/delete.do")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String lineIds = request.getParameter("Ids");
		String[] ids = lineIds.split("-");
		try {
			
			for (int i = 0; i < ids.length; i++) {
				adminRemote.deleteRole(new Integer(ids[i]));
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
	@RequestMapping(value = "/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			
			List moduleList = adminRemote
					.getSystemList(getSessionModuleID(request));
			request.setAttribute("module_list", moduleList);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
		return "role";
	}

}
