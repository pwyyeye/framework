package com.xxl.controller.temp;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.TempRemote;

import common.controller.BaseController;
import common.temp.vo.TempOrderVo;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/tempOrderController")
public class TempOrderController extends BaseController {

	public static Log logger = LogFactory.getLog(TempOrderController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(TempOrderController.class);

	@Autowired
	public TempRemote tempRemote;
	
	@RequestMapping("/")
	public String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		
		return "TempOrder";
	}
	
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response,TempOrderVo vo) {
//		response.setContentType("text/json;charset=UTF-8");
		try {
			vo.setCreatedate(Calendar.getInstance());
			Integer id=tempRemote.addTempOrder(vo);
//			response.getWriter().write("{success:true,id:" + id + "}");
			String path = request.getContextPath();  
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
			response.sendRedirect(basePath+"finish.html");
		} catch (Exception e) {
//			this.handleException(e, request, response);
			try {
				response.getWriter().write("抱歉已无货，订单失败！");
			} catch (IOException e1) {
				
			}
		}
		
	}
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String name = request.getParameter("name");
		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			start = 0;
			limit = 0;
		}
		logger.debug("para start=" + start + ",limit=" + limit+ ",moduleStr=" + moduleStr+ ",name=" + name);
		
		TempOrderVo searchVO = new TempOrderVo();
		searchVO.setName(name);
		try {
			
			PageList eventList = tempRemote.getTempOrderList(searchVO, start, limit);
			String json = SemWebAppUtils.getJsonFromBean(eventList);
			logger.debug(json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
		
	}

}