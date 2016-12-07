package com.xxl.controller.common;

//Created by MyEclipse Struts
//XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.BaseController;
import common.value.ObserverVO;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;



/**
 * MyEclipse Struts Creation date: 04-23-2007
 * 
 */
@Controller
@RequestMapping("/observerController")
public class ObserverController extends BaseController {
	public static Log dLogger = LogFactory.getLog("debug");

	public static Log sysLogger = LogFactory.getLog("sys");

	@Autowired
	public AdminRemote adminRemote;
	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/json;charset=UTF-8");
		String ids = request.getParameter("Ids");
		String[] idArray = ids.split("-");
		try {
			
			for (int i = 0; i < idArray.length; i++) {
				adminRemote.deleteMessageSubscibe(new Integer(idArray[i]));
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
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,ObserverVO vo)
			throws Exception {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			logger.debug("add,event id =" + vo.getEventID());
	
			Integer id = adminRemote.addMessageSubscibe(vo);
			response.getWriter().write("{success:true,id:" + id + "}");
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
		}
	}
	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,ObserverVO vo)
			throws Exception {
		response.setContentType("text/json;charset=UTF-8");
		
		try {
//			vo.setBeginDate(SemWebAppUtils.getCalendar(obForm.getBeginDate()));
//			vo.setEndDate(SemWebAppUtils.getCalendar(obForm.getEndDate()));
			adminRemote.updateMessageSubscibe(vo);
			response.getWriter().write("{success:true,id:" + vo.getId() + "}");
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			
			List moduleList = adminRemote.getSystemList(getSessionModuleID(request));
			request.setAttribute("module_list", moduleList);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
		return "observer";
	}
	@RequestMapping("/list")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String eventID = request.getParameter("eventID");
		String empID = request.getParameter("empID");
		String routeID = request.getParameter("route");
		String beginDate=request.getParameter("beginDate");
		logger.debug("sear beginDate="+beginDate+"sear eventID="+eventID);
		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			start = 0;
			limit = 0;
		}
		logger.debug("para start=" + start + ",limit=" + limit + ",moduleStr="
				+ moduleStr + ",eventID=" + eventID + ",empID=" + empID + ",routeID"
				+ routeID);
		Integer module = null;
		if (moduleStr != null) {
			try {
				module = new Integer(moduleStr);
			} catch (Exception ee) {
				
			}
		}
		if(module==null)  module=this.getSessionModuleID(request);
		boolean filter = false;
		ObserverVO vo = new ObserverVO();

		if (SemWebAppUtils.isNotEmpty(eventID)) {
			vo.setEventID(new Integer(eventID));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(empID)) {
			vo.setEmpID(SemWebAppUtils.getInteger(empID));
			filter = true;

		}
		if (SemWebAppUtils.isNotEmpty(routeID)) {
				vo.setRoute(Integer.parseInt(routeID));
				filter = true;
		}
		
		try {
		
			PageList eventList = adminRemote.getMessageSubscibeList(module,
					filter?vo:null, new Integer(start), new Integer(limit));
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(eventList);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
	}
}
