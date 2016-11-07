package com.xxl.controller.common;

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
@RequestMapping("/moduleController")
public class ModuleController extends BaseController {

	public static Log logger = LogFactory.getLog(ModuleController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(ModuleController.class);
	 
	@Autowired
	public AdminRemote adminRemote;

	@RequestMapping("/add.do")
	public void add(
			HttpServletRequest request, HttpServletResponse response,ItModuleVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		String parentID=request.getParameter("parentID");
		vo.setParentModule(parentID==null?0:Integer.parseInt(parentID));
		Integer moduleID;
		try {
			moduleID = adminRemote.addSystem(vo);
			response.getWriter().write("{success:true,id:" + moduleID + "}");
		} catch (Exception e) {
			this.handleException(e, request, response);
		}
		
	}

	@RequestMapping("/cancel.do")
	public void cancel(
			HttpServletRequest request, HttpServletResponse response,ItModuleVO vo) {		
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("root");
		logger.debug("root="+rootStr);
	    Integer root=null;
	    try{
	    	root=new Integer(rootStr);
	    
	    	ItModuleVO modules=adminRemote.getSystemByID(root);
			String json ;
			if(modules != null){
				json = "{success:true,data:"+SemWebAppUtils.getJsonFromBean(modules)+"}";
			}else{
				json = "{success:false}";
			}
			logger.debug(json);
			response.getWriter().write(json);			
	    }catch(Exception ee){
	    	logger.error("逻辑层异常:",ee);
	    } 
		
	}
	

	@RequestMapping("/list.do")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("id");
		logger.debug("root="+rootStr);
		boolean contianRoot=SemWebAppUtils.isEmpty(rootStr);
		int root=0;
		try{
           root=Integer.parseInt(rootStr);
		}catch(Exception ee){
			root=0;          
        }
		if(root==0){
			int sessionRoot=this.getSessionModuleID(request).intValue();
			if(!SemAppUtils.isSystemModule(sessionRoot)){
				root=sessionRoot;
				contianRoot=true;
			}
		}
		logger.debug("convert root="+root);
        try{
        	
        	List modules=new ArrayList();
        	if(root==-1){
   
        		int currentModuleID=getSessionModuleID(request).intValue();
        		modules=adminRemote.getSystemList(new Integer(currentModuleID),new Boolean(currentModuleID!=0));
        		//modules.add(getSessionModule(request));
        	}else{
        		modules=adminRemote.getSystemList(new Integer(root),new Boolean(contianRoot));	
        	}
        	String json = SemWebAppUtils.getListJsonFromList(modules);
			logger.debug("module json"+json);
			response.getWriter().write(json);        	
        }catch(Exception ee){
        	logger.error("逻辑层异常:",ee);
        }
	}

	@RequestMapping("/update.do")
	public void update(HttpServletRequest request, HttpServletResponse response,ItModuleVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {

			//vo.setParentModule(Integer.parseInt(theForm.getParentID()));
			adminRemote.updateSystem(vo);
			response.getWriter().write(
					"{success:true,id:" + vo.getId() + "}");
		} catch (Exception ee) {
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
			logger.error("业务逻辑层异常", ee);
		}
	}

	
	@RequestMapping("/")
	public String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		
		return "switchRole";
	}

}