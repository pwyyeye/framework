package com.xxl.controller.common;

import javax.servlet.http.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import common.controller.BaseController;
import common.utils.SemAppUtils;
import common.value.MenuRoleVO;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;

@Controller
@RequestMapping("/menuRoleController")
public class MenuRoleController extends BaseController {
	public static Log logger = LogFactory.getLog(MenuRoleController.class);

	public static Log sysLogger = LogFactory.getLog("sys");

	@Autowired
	public AdminRemote adminRemote;
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,MenuRoleVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			logger.debug("add,module id =" + vo.getModuleID());
//			MenuRoleVO vo = new MenuRoleVO(null, null, new Integer(theForm.getModuleID()),
//					null, new Integer(theForm.getRoleID()),null, new Integer(
//							theForm.getMenuID()),theForm.getRightCode());
			vo.setOrganise(this.getSessionUser(request).getOrganise().getId());
			vo.setRightCode(getRightCode(request));
			Integer roleID = adminRemote.addMenuRole(vo);
			response.getWriter().write("{success:true,id:" + roleID + "}");
		} catch (Exception ee) {
			logger.error("?????????????????????", ee);
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
		}
	}
	@RequestMapping("/list")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("get the role list");
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
        String roleID=request.getParameter("roleID");
        String menuID=request.getParameter("menuID");
    	String organise = request.getParameter("organise");
		// ??????????????????
		boolean filter = false;
		MenuRoleVO vo = new MenuRoleVO();
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			vo.setModuleID(new Integer(moduleStr));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(organise)) {
			vo.setOrganise(new Integer(organise));
			filter = true;
		} else {
			vo.setOrganise(this.getSessionUser(request).getOrganise()
					.getId());
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(roleID)) {
			vo.setRoleID(new Integer(roleID));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(menuID)) {
			vo.setMenuID(new Integer(menuID));
			filter = true;
		}
		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			logger.error("??????????????????",ee);
			start = 0;
			limit = 0;
		}
		logger.debug("start start=" + startStr + ",limit=" + limitStr+"["+limit+"]");
		Integer module = null;
		if (moduleStr != null) {
			try {
				module = new Integer(moduleStr);
			} catch (Exception ee) {
				
			}
		}
		if(module==null)  module=this.getSessionModuleID(request);
		try {
			
			PageList mrList = adminRemote.getMenuRoleList(module,
					filter ? vo : null, new Integer(start), new Integer(
							limit));
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(mrList);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("?????????????????????", ee);
		}
	}
	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,MenuRoleVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			Integer id = vo.getId();
			logger.debug("update id="+id+",role");
//			MenuRoleVO vo = new MenuRoleVO(new Integer(id), null, null, null,
//					new Integer(theForm.getRoleID()), null, new Integer(theForm
//							.getMenuID()), theForm.getRightCode());
			vo.setRightCode(getRightCode(request));
			adminRemote.updateMenuRole(vo);
			response.getWriter().write("{success:true,id:" + id + "}");
		} catch (Exception ee) {
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
			logger.error("?????????????????????", ee);
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
				adminRemote.deleteMenuRole(new Integer(ids[i]));
			}
			response.getWriter().write("{success:true}");
		} catch (Exception ee) {
			logger.error("?????????????????????", ee);
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
			logger.error("?????????????????????", ee);
		}
		return "menuRole";
	}

	
	public String getRightCode(HttpServletRequest request) {
		StringBuffer rightCodeSB=new StringBuffer();
		if((SemAppUtils.isNotEmpty(request.getParameter("addRight")))){
			rightCodeSB.append(request.getParameter("addRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("listRight"))){
			rightCodeSB.append(request.getParameter("listRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("updateRight"))){
			rightCodeSB.append(request.getParameter("updateRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("deleteRight"))){
			rightCodeSB.append(request.getParameter("deleteRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("exportRight"))){
			rightCodeSB.append(request.getParameter("exportRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("importRight"))){
			rightCodeSB.append(request.getParameter("importRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("customRight"))){
			rightCodeSB.append(request.getParameter("customRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("deleteAllRight"))){
			rightCodeSB.append(request.getParameter("deleteAllRight"));
		}
		if(SemAppUtils.isNotEmpty(request.getParameter("otherRightCode"))){
			rightCodeSB.append(request.getParameter("otherRightCode"));		
		}
		return new String(rightCodeSB);
	}
}
