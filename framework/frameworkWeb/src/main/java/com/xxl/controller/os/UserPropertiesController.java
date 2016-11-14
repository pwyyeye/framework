package com.xxl.controller.os;

import java.io.IOException;
import java.io.Serializable;
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
import com.xxl.facade.StructureRemote;

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
import common.value.SystemPropertyVO;
import common.value.TreeControl;
import common.value.TreeControlNode;
import common.value.TreeNode;
import common.value.UserPropertiesVO;
import common.vo.BaseResponseVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/userPropertiesController")
public class UserPropertiesController extends BaseController {

	public static Log logger = LogFactory.getLog(UserPropertiesController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(UserPropertiesController.class);

	@Autowired
	private StructureRemote structureRemote;
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,UserPropertiesVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			Serializable id = structureRemote.addUserProperties(vo);
			response.getWriter().write("{success:true,message:" + id + "}");
			logger.debug("{success:true,message:" + id + "}");
		} catch (Exception e) {
			logger.error("add a new record failer",e);
			this.handleException(e, request, response);
		}
	}

	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response,UserPropertiesVO vo ) {
		try {

			structureRemote.updateUserProperties(vo);
			response.getWriter().write("{success:true}");
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}

	
	@RequestMapping("/")
	public String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		
		return "userProperty";
	}
	
	
	@RequestMapping("/custom")
	public String custom(
			HttpServletRequest request, HttpServletResponse response) {
		String theme=request.getParameter("theme");
		SessionUserBean sessionUser = getSessionUser(request);
		UserPropertiesVO vo=new UserPropertiesVO();
		
		vo.setUsId(sessionUser.getEmpIDInt());
		vo.setId("USER_DEFAULT_THEME"+sessionUser.getEmpID());
		SystemPropertyVO pVO=new SystemPropertyVO();
		pVO.setId("USER_DEFAULT_THEME");
		vo.setProperty(new SystemPropertyVO());
		vo.setValue(theme);
		vo.setSetUser(""+sessionUser.getEmpID());
		//vo.set
		try{
		if(sessionUser.setProperty("USER_DEFAULT_THEME", theme)){//if exist
			structureRemote.updateUserProperties(vo);
		}else{
			structureRemote.addUserProperties(vo);
		}
		this.setSessionAttribute(request, SemWebAppConstants.USER_KEY, sessionUser);
		}catch(Exception ee){
			this.handleException(ee, request, response);
		}
		
		return "uithemes";
	}
	
	@RequestMapping("/save")
	public void save(
			HttpServletRequest request, HttpServletResponse response,UserPropertiesVO vo) {
		try {

			String id;
			if (vo.getId() == null) {
				id =structureRemote.addUserProperties(vo);;
			} else {
				logger.debug("update  dcp[" + vo.getId() + "]");
				structureRemote.updateUserProperties(vo);
				id = vo.getId()+"";
			}
			response.getWriter().write("{success:true,id:" + id + "}");
			
		} catch (Exception e) {
			this.handleException(e, request, response);
			
		}

	}
	
	protected PageList getDatas(
			HttpServletRequest request, HttpServletResponse response,
			boolean pagable) throws Exception {
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String parentId = request.getParameter("id");
		String id=request.getParameter("root");
		String organiseId = request.getParameter("organiseId");
		logger.debug("root="+parentId+",id="+id+",organiseId");
		UserPropertiesVO vo = new UserPropertiesVO();
		
		if (SemAppUtils.isNotEmpty(id)) {
			vo.setId(new  Integer(id));
		}
		if (SemAppUtils.isNotEmpty(organiseId)) {
			vo.setId(new Integer(organiseId));
		}
		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			start = 0;
			limit = 0;
		}
		try {
			PageList plist = structureRemote.getUserProperties(vo,
					pagable ? new Integer(start) : new Integer(0),
					pagable ? new Integer(limit) : new Integer(0));
			 //logger.debug("get dcp data" + plist.getResults());
			return plist;
		} catch (Exception e) {
			this.handleException(e, request, response);
			
		}
		return null;

	}

}