package com.xxl.controller.os;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.StructureRemote;

import common.controller.BaseController;
import common.os.vo.DepartmentVO;
import common.os.vo.OrganiseVO;
import common.os.vo.exception.OSBussinessException;
import common.utils.SemAppUtils;
import common.value.BaseVO;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;
@Controller
@RequestMapping("/departmentController")
public class DepartmentController extends BaseController {
	public static Log logger = LogFactory.getLog(DepartmentController.class);

	public static Log dbLogger = SemAppUtils.getDBLog(DepartmentController.class);
	
	@Autowired
	private StructureRemote structureRemote;
	
	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		return "department";
	}


	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,DepartmentVO vo) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			structureRemote.updateDepartment(vo);
			response.getWriter().write("{success:true}");
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/save")
	public void save(
			HttpServletRequest request, HttpServletResponse response,DepartmentVO vo) {
		try {
			String id=null;
			if (vo.getId() == null) {
				id = ""+ structureRemote.addDepartment(vo);
			} else {
				logger.debug("update  dcp[" + vo.getId() + "]");
				this.structureRemote.updateDepartment(vo);
				id = ""+ vo.getId();
			}
			response.getWriter().write("{success:true,id:" + id + "}");
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,DepartmentVO vo) {
		try {
			vo.setOrganiseId(this.getSessionUser(request).getOrganise().getId());
			Serializable id =structureRemote.addDepartment(vo);
			response.getWriter().write("{success:true,message:" + id + "}");
			logger.debug("{success:true,message:" + id + "}");
		} catch (Exception e) {
			logger.error("add a new record failer",e);
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			response.setContentType("text/json;charset=UTF-8");
			String id=request.getParameter("Ids");		
			this.structureRemote.delDepartment(SemAppUtils.getInteger(id));
			response.getWriter().write("{'success':true,'message':'" + id + "'}");
		} catch (Exception e) {
			logger.error("delete a new record failer",e);
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/list")
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
		super.list(request, response);
	}
	
	protected PageList getDatas(
			HttpServletRequest request, HttpServletResponse response,
			boolean pagable) throws Exception {
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String parentId = request.getParameter("id");

		OrganiseVO organise=this.getSessionUser(request).getOrganise();
		logger.debug("root="+parentId+",organise="+organise);
		DepartmentVO vo = new DepartmentVO();
		if (SemAppUtils.isNotEmpty(parentId)) {
			vo.setParentId(SemAppUtils.getInteger(parentId));
		}
	
		if (organise!=null&&organise.getId().intValue()!=0) {
			vo.setOrganiseId(organise.getId());
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
			PageList plist = this.structureRemote.getDepartment(vo,
					pagable ? new Integer(start) : new Integer(0),
					pagable ? new Integer(limit) : new Integer(0));
			logger.debug("get dcp data" + plist.getResults());
			return plist;
		} catch (Exception e) {
			this.handleException(e, request, response);
			return null;
		}

	}
	@RequestMapping("/cancel")
	public void cancel(
			HttpServletRequest request, HttpServletResponse response)  {
		response.setContentType("text/json;charset=UTF-8");
		String id = request.getParameter("id");
		DepartmentVO vo = new DepartmentVO();
		vo.setId(SemAppUtils.getInteger(id));
		try {
			PageList plist = this.structureRemote.getDepartment(vo,
					new Integer(0), new Integer(0));
			DepartmentVO result=(DepartmentVO)plist.getItems().get(0);
			logger.debug("depart json="+SemWebAppUtils.getJsonFromBean(result));
			List list=new ArrayList();
			list.add(result);
			response.getWriter().write(SemWebAppUtils.getJsonFromList(list));
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/expand")
	public void expand(
			HttpServletRequest request, HttpServletResponse response)  {
		response.setContentType("text/json;charset=UTF-8");
		Integer orId=this.getSessionUser(request).getOrganise().getId();
		try {
			PageList plist = this.structureRemote.getOrganises(orId);
			String json = SemWebAppUtils.getJsonFromBean(plist);
			response.getWriter().write(json);
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
}
