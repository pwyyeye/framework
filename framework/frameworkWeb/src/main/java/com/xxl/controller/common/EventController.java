package com.xxl.controller.common;



import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import common.value.EventVO;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xxl.facade.AdminRemote;

import common.controller.BaseController;
@Controller
@RequestMapping("/eventController")
public class EventController extends BaseController {
	public static Log logger = LogFactory.getLog(EventController.class);

	public static Log sysLogger = LogFactory.getLog("sys");

	@Autowired
	public AdminRemote adminRemote;
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,EventVO event) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			logger.debug("add,event id =" + event.getName());
//			EventVO event = new EventVO(null, null, eventForm.getName(),
//					Integer.parseInt(eventForm.getType()), 0,
//					new Integer(eventForm.getModuleID()));
			Integer eventID = adminRemote.addEvent(event);
			response.getWriter().write("{success:true,id:" + eventID + "}");
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
		logger.debug("get the event list");
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			start = 0;
			limit = 0;
		}
		logger.debug("para start=" + start + ",limit=" + limit+ ",moduleStr=" + moduleStr+ ",name=" + name+",type="+type);
		Integer module = null;
		if (moduleStr != null) {
			try {
				module = new Integer(moduleStr);
			} catch (Exception ee) {
				
			}
		}
		if(module==null)  module=this.getSessionModuleID(request);
		boolean filter = false;
		EventVO eventVO = new EventVO();
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			eventVO.setModuleID(new Integer(moduleStr));
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(name)) {
			eventVO.setName(name);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(type)) {
			try {
				int typeInt = Integer.parseInt(type);
				eventVO.setType(typeInt);
				filter = true;
			} catch (Exception ee) {
			}
		}
		try {
			
			PageList eventList = adminRemote.getEventList(module,
					eventVO, new Integer(start), new Integer(limit));
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(eventList);
			//logger.debug(json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
	}
	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,EventVO eventVO) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
//			EventVO eventVO = new EventVO(new Integer(eventForm.getId()), "",
//					eventForm.getName(), Integer.parseInt(eventForm.getType()), 0,
//					new Integer(eventForm.getModuleID()));
			adminRemote.updateEvent(eventVO);
			response.getWriter().write("{success:true}");
		} catch (Exception ee) {
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
			logger.error("业务逻辑层异常", ee);
		}
	}
	@RequestMapping("/importExcel")
	public void importExcel(
			HttpServletRequest request, HttpServletResponse response,EventVO eventVO) {
		
//		FormFile uploadFile=eventForm.getImportFile();
//		logger.debug("upload excel size="+(uploadFile==null?"null":(uploadFile.getFileName()+uploadFile.getFileSize())));
//		response.setContentType("text/json;charset=UTF-8");
//		try {
//			response.getWriter().write("{success:true}");
//		} catch (IOException e1) {
//			try {
//				response.getWriter().write("{success:false}");
//			} catch (IOException e) {
//			}
//		}
	}
	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String ids = request.getParameter("Ids");
		String[] idArray = ids.split("-");
		try {
			
			for (int i = 0; i < idArray.length; i++) {
				adminRemote.deleteEvent(new Integer(idArray[i]));
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
		return "event";
	}
}
