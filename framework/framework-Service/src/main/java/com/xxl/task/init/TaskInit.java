package com.xxl.task.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xxl.baseService.service.ITaskService;

import common.utils.SpringUtils;

/**
 * 定时任务初始化
 * @author karys
 * 2016-09-25
 *
 */
public class TaskInit extends HttpServlet {

	private static final long serialVersionUID = 2992500555742955500L;

	private static final Log logger = LogFactory.getLog(TaskInit.class);
	
	private ITaskService taskService;

	public void init(ServletConfig config) throws ServletException {
		
		logger.info("－－－－－－开始添加定时任务 TaskInit－－－－－－");
		super.init(config);  
	    taskService = (ITaskService) SpringUtils.servletGetBean(this.getServletContext(), "taskService");
	    taskService.initTask();
		
	}

}
