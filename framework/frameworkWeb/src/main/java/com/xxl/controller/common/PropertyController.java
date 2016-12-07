package com.xxl.controller.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;

import common.controller.BaseController;
import common.utils.SemAppUtils;
import common.value.NoticeVO;
import common.value.PageList;
import common.value.SystemPropertyVO;
import common.web.utils.SemWebAppUtils;
@Controller
@RequestMapping("/propertyController")
public class PropertyController extends BaseController {

	public static Log logger = LogFactory.getLog(PropertyController.class);

	public static Log sysLogger = LogFactory.getLog("sys");
	@Autowired
	public AdminRemote adminRemote;
	
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,SystemPropertyVO vo) {
		logger.debug("add action form[" + vo + "]");
		logger.debug("moduleID =" + vo.getModuleID() + "& item="
				+ vo.getName());
		try {

//			SystemPropertyVO vo = new SystemPropertyVO(theForm.getName(),
//					theForm.getName(), theForm.getValue(), theForm.getRemark(),
//					null, new Integer(theForm.getModuleID()));
//			SemAppUtils.formToVo(theForm, vo);
			String[] ids=(String[]) vo.getId();
			vo.setId(ids[0]);
			String[] parents=vo.getParent().split(",");
			vo.setParent(parents[0]);
			vo.setSetDate(Calendar.getInstance());
			vo.setSetUser("" + this.getSessionUser(request).getEmpID());
			Object noticeID = adminRemote.addPropertyVO(vo);

			response.getWriter().write("{success:true,id:'" + noticeID + "'}");
		} catch (Exception ee) {

			this.handleException(ee, request, response);
		}
	}
	protected PageList getDatas(
			HttpServletRequest request, HttpServletResponse response,
			boolean pagable) throws Exception {
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String name = request.getParameter("name");
		String remark = request.getParameter("remark");
		String value = request.getParameter("value");
		logger.debug("moduleID=" + moduleStr);
		// 处理检索条件
		boolean filter = false;
		SystemPropertyVO vo = new SystemPropertyVO();
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			try {
				vo.setModuleID(new Integer(moduleStr));
				filter = true;
			} catch (Exception ee) {
			}
		}
		if (SemWebAppUtils.isNotEmpty(name)) {
			vo.setName(name);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(remark)) {
			vo.setRemark(remark);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(value)) {
			vo.setRemark(value);
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
			
			PageList tableList = adminRemote.getProperties(module, filter ? vo
					: null, pagable ? new Integer(start) : new Integer(0),
					pagable ? new Integer(limit) : new Integer(0));
			return tableList;
		} catch (Exception ee) {
			this.handleException(ee, request, response);
			return null;
		}

	}
	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,SystemPropertyVO vo) {
		 response.setContentType("text/json;charset=UTF-8");
		try {

//			SystemPropertyVO vo = new SystemPropertyVO(theForm.getName(),
//					theForm.getName(), theForm.getValue(), theForm.getRemark(),
//					null, null);
//			SemAppUtils.formToVo(theForm, vo);
			String[] ids=(String[]) vo.getId();
			vo.setId(ids[0]);
			String[] parents=vo.getParent().split(",");
			vo.setParent(parents[0]);
			String tableID = (String) vo.getId();
			vo.setSetDate(Calendar.getInstance());
			vo.setSetUser("" + this.getSessionUser(request).getEmpID());
			adminRemote.updateProperties(vo);
			response.getWriter().write("{success:true,id:'" + tableID + "'}");
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
	}
	@RequestMapping("/custom")
	public void custom(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			
			adminRemote.resetProperties();
			response.getWriter().write("{success:true,message:'0'}");
		} catch (Exception ee) {

			this.handleException(ee, request, response);
		}
	}
	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("go to property action!");
		return "property";
	}
	@RequestMapping("/importExcel")
	public void importExcel(
			HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		if (SemWebAppUtils.isNotEmpty(name)) {
			try {
				
				String result = adminRemote.getValueOfProperty(name);
				response.getWriter().write(
						"{success:true,message:'" + result + "'}");
			} catch (Exception ee) {

				this.handleException(ee, request, response);
			}
		}
	}
	@RequestMapping("/expand")
	public void expand(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String idStr = request.getParameter("id");
		// String moduleStr = request.getParameter("moduleID");
		Integer currentModule = this.getSessionModuleID(request);
		List modules = new ArrayList();

		try {
			
			if (SemWebAppUtils.isNotEmpty(idStr)) {
				boolean isModule;
				int root = 0;

				try {
					root = Integer.parseInt(idStr);
					if (root == 0)
						root = 0 - this.getSessionModuleID(request).intValue();
					isModule = (root <= 0);// 如果ID<0表示是模块节点
					idStr = "" + root;
				} catch (Exception ee) {
					isModule = false;
				}
				if (isModule) {
					root = 0 - root;
					logger.debug("convert root=" + root);
					modules = adminRemote.getSystemList(new Integer(root),
							new Boolean(false), new Boolean(true));
					if (root != 0) {
						List menus = adminRemote.getProperties(new Integer(
								root), "0");
						modules.addAll(menus);
					}
					// jsonBuffer.append(WebAppUtils.getListJsonFromList(modules));
				} else {
					modules = adminRemote.getProperties(currentModule, idStr);
					// jsonBuffer.append(WebAppUtils.getListJsonFromList(menus));
				}
			}
			PageList pageList = new PageList(modules);
			logger.debug("modules size is"+modules.size());
			String json = SemAppUtils.getJsonFromBean(pageList);
			
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}
	}

}
