package com.xxl.controller.jms;


import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.JMSTaskRemote;

import common.controller.BaseController;
import common.exception.BaseException;
import common.jms.vo.JMSTaskVO;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;
@Controller
@RequestMapping("/jmsTaskController")
public class JMSTaskController extends BaseController {

	public static Log logger = LogFactory.getLog(JMSTaskController.class);

	public static Log sysLogger = SemAppUtils.getDBLog(JMSTaskController.class);

	@Autowired
	public JMSTaskRemote jmsTaskRemote;
	
	@Autowired
	public AdminRemote adminRemote;
	@RequestMapping("/list")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String empID = request.getParameter("empID");
		String message = request.getParameter("message");
		String queueName = request.getParameter("queueName");
		String messageID = request.getParameter("messageID");
		String status=request.getParameter("status");
		String dealResult=request.getParameter("dealResult");
		String createStartDate=request.getParameter("createStartDate");
		String createEndDate=request.getParameter("createEndDate");
		String dealStartDate=request.getParameter("dealStartDate");
		String dealEndDate=request.getParameter("dealEndDate");
		// 处理检索条件
		boolean filter = false;
		JMSTaskVO vo = new JMSTaskVO(null);
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			try{
			vo.setModuleID(new Integer(moduleStr));
			filter = true;}
			catch(Exception ee){}
		}
		if (SemWebAppUtils.isNotEmpty(empID)) {
			vo.setEmpID(SemAppUtils.getInteger(empID));
			filter = true;
		}
//		else{
//			vo.setEmpID(this.getSessionUser(request).getEmpIDInt());
//			filter = true;	
//		}
		if (SemWebAppUtils.isNotEmpty(message)) {
			vo.setMessage(message);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(messageID)) {
			vo.setMessageID(messageID);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(queueName)) {
			vo.setQueueName(queueName);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(status)) {
			try{
			vo.setStatus(new Integer(status));
			filter = true;}
			catch(Exception ee){}
		}else{
			vo.setStatus(null);
		}
		if (SemWebAppUtils.isNotEmpty(createStartDate)) {
			try{
			vo.setCreateStartDate(SemAppUtils.getCalendar(createStartDate));
			filter = true;}
			catch(Exception ee){}
		}
		if (SemWebAppUtils.isNotEmpty(createEndDate)) {
			try{
			vo.setCreateEndDate(SemAppUtils.getCalendar(createEndDate));
			filter = true;}
			catch(Exception ee){}
		}
		if (SemWebAppUtils.isNotEmpty(dealStartDate)) {
			try{
			vo.setDealDate(SemAppUtils.getCalendar(dealStartDate));
			filter = true;}
			catch(Exception ee){}
		}
		if (SemWebAppUtils.isNotEmpty(createEndDate)) {
			try{
			vo.setDealEndDate(SemAppUtils.getCalendar(createEndDate));
			filter = true;}
			catch(Exception ee){}
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
			
			PageList tableList = jmsTaskRemote.getJMSTaskList(module,
					filter ? vo : null, new Integer(start), new Integer(limit));
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(tableList);
			Iterator iter=tableList.getItems().iterator();
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
	}



	
	public void custom(
			HttpServletRequest request, HttpServletResponse response) {

		return ;
	} 
	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("start execute jmsAction");
		List moduleList;
		try {
			moduleList = adminRemote.getSystemList(getSessionModuleID(request));
			this.checkRightCode(request);
			request.setAttribute("module_list", moduleList);
		} catch (Exception e) {
			this.handleException(e, request, response);
		}
		
		return "jmsTask";
	}
	


}
