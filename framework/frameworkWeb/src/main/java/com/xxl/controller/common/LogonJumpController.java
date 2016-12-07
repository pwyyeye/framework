package com.xxl.controller.common;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;

import common.bussiness.CommonLogger;
import common.controller.BaseController;
import common.exception.BaseException;
import common.exception.CommonException;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.ItModuleVO;
import common.value.PageList;
import common.vo.BaseResponseVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/logonJumpController")
public class LogonJumpController extends BaseController {

	public static Log logger = LogFactory.getLog(LogonJumpController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(LogonJumpController.class);

	@RequestMapping(value = "/")
	public void defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		try{
			response.setContentType("text/html;charset=UTF-8");
//			response.getWriter().write("<%@ page contentType=\"text/html; charset=UTF-8\"%>");
			response.getWriter().write("<html>");
			response.getWriter().write("<script>var theLocation;if(window.parent!=window){");
			response.getWriter().write("theLocation=window.parent.location;}else{");
			response.getWriter().write("theLocation=window.location;}"); 
			String path = request.getContextPath(); 
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
			response.getWriter().write("theLocation.href=\""+basePath+"loginController/home\";</script>");
			response.getWriter().write("</html>");
		}catch(Exception ee){			
		}


	}
	
	

}