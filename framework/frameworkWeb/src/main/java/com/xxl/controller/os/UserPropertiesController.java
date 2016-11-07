package com.xxl.controller.os;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import common.bussiness.CommonLogger;
import common.controller.BaseController;
import common.exception.BaseException;
import common.exception.CommonException;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.ItModuleVO;
import common.value.MenuVO;
import common.value.PageList;
import common.value.TreeControl;
import common.value.TreeControlNode;
import common.value.TreeNode;
import common.vo.BaseResponseVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/userPropertiesController")
public class UserPropertiesController extends BaseController {

	public static Log logger = LogFactory.getLog(UserPropertiesController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(UserPropertiesController.class);


	@RequestMapping("/add.do")
	public void add(
			HttpServletRequest request, HttpServletResponse response,MenuVO vo) {
		response.setContentType("text/json;charset=UTF-8");

	}

	@RequestMapping("/list.do")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		SessionUserBean currentUser = this.getSessionUser(request);
		try {
		
		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}
	}

	@RequestMapping("/update.do")
	public String update(HttpServletRequest request, HttpServletResponse response,MenuVO vo) {
		try {
		
		} catch (Exception ee) {

		}
		return "main";
	}

	
	@RequestMapping("/")
	public String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		
		return "userProperty";
	}

}