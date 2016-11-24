package com.xxl.controller.temp;

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
import com.xxl.facade.TempRemote;

import common.bussiness.CommonLogger;
import common.controller.BaseController;
import common.exception.BaseException;
import common.exception.CommonException;
import common.os.vo.UsersVO;
import common.temp.vo.TempOrderVo;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.EventVO;
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
@RequestMapping("/meDaigouController")
public class MedaigouController extends BaseController {

	public static Log logger = LogFactory.getLog(MedaigouController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(MedaigouController.class);

	@Autowired
	public TempRemote tempRemote;
	
	@RequestMapping("/")
	public String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		
		return "medaigou";
	}
	
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response,TempOrderVo vo) {
		response.setContentType("text/json;charset=UTF-8");
		
		
	}
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		
	}

}