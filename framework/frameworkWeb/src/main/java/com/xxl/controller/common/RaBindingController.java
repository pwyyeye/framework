package com.xxl.controller.common;

import javax.servlet.http.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import common.controller.BaseController;
import common.value.MenuRoleVO;
import common.value.PageList;
import common.value.RaBindingVO;
import common.web.utils.SemWebAppUtils;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;
@Controller
@RequestMapping("/raBindingController")
public class RaBindingController extends BaseController {
	public static Log logger = LogFactory.getLog(RaBindingController.class);

	public static Log sysLogger = LogFactory.getLog("sys");
	@Autowired
	public AdminRemote adminRemote;
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,RaBindingVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		
		logger.debug("report id="+vo.getReportID());
		try {
			
			logger.debug("add,module id =" + vo.getModuleID());
//			RaBindingVO vo = new RaBindingVO(null, null, new Integer(theForm.getModuleID()),
//					null, new Integer(theForm.getRoleID()),null, new Integer(
//							theForm.getReportID()),theForm.getRightCode());
			Integer roleID = adminRemote.addRaBinding(vo);
			response.getWriter().write("{success:true,id:" + roleID + "}");
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
		logger.debug("get the role list");
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
        String roleID=request.getParameter("roleID");
        String reportID=request.getParameter("reportID");
		// 处理检索条件
		boolean filter = false;
		RaBindingVO vo = new RaBindingVO();
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			vo.setModuleID(new Integer(moduleStr));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(roleID)) {
			vo.setRoleID(new Integer(roleID));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(reportID)) {
			vo.setReportID(new Integer(reportID));
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
		if(module==null)  module=this.getSessionModuleID(request);
		try {
			
			PageList mrList = adminRemote.getRaBindingList(module,
					filter ? vo : null, new Integer(start), new Integer(
							limit));
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(mrList);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
	}
	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,RaBindingVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			Integer id = vo.getId();
			logger.debug("update id="+id+",role");
//			RaBindingVO vo = new RaBindingVO(new Integer(id), null, null, null,
//					new Integer(theForm.getRoleID()), null, new Integer(theForm
//							.getReportID()), theForm.getRightCode());
			
			adminRemote.updateRaBinding(vo);
			response.getWriter().write("{success:true,id:" + id + "}");
		} catch (Exception ee) {
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
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
				adminRemote.deleteRaBinding(new Integer(ids[i]));
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
//		try {
//			if (adminRemote == null) {
//				adminRemote = this.getAdminSession();
//			}
//			List moduleList = adminRemote.getSystemList(getSessionModuleID(request));
//			request.setAttribute("module_list", moduleList);
//		} catch (Exception ee) {
//			logger.error("业务逻辑层异常", ee);
//		}
		return "raBinding";
	}

}
