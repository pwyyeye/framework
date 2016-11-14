package com.xxl.controller.os;

import java.io.IOException;
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

import com.xxl.facade.CommonRemote;
import com.xxl.facade.StructureRemote;

import common.bussiness.CommonLogger;
import common.controller.BaseController;
import common.exception.BaseBusinessException;
import common.os.vo.UsersVO;
import common.os.vo.exception.OSBussinessException;
import common.utils.SemAppUtils;
import common.value.BaseVO;
import common.value.JsonResult;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/usersController")
public class UsersController extends BaseController {
	public static Log logger = LogFactory.getLog(UsersController.class);

	public static Log dbLogger = SemAppUtils.getDBLog(UsersController.class);

	@Autowired
	private StructureRemote structureRemote;
	
	@Autowired
	private CommonRemote commonRemote;
	
	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		return "users";
	}


	@RequestMapping("/update")
	public void update(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			
			structureRemote.updateUsers(vo);
			response.getWriter().write("{'success':true}");
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/save")
	public void save(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		try {
			String id=null;
			logger.debug("user vo" + vo);
			if (vo.getId() == null) {
				id = "" + structureRemote.addUsers(vo);
			} else {
				logger.debug("update  dcp[" + vo.getId() + "]");
				structureRemote.updateUsers(vo);
				id = "" + vo.getId();
			}
			response.getWriter().write("{success:true,id:" + id + "}");
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		try {
			vo.setOrganise(this.getSessionUser(request).getOrganise().getId());
			Serializable id = structureRemote.addUsers(vo);
			response.getWriter().write("{success:true,message:" + id + "}");
			// logger.debug("{success:true,message:" + id + "}");
			dbLogger.info(new CommonLogger(this.getSessionUser(request)
					.getEmpIDInt(), "1", "新增用户" + vo, "新增用户" + vo, this
					.getSessionUser(request).getIp()));
		} catch (Exception e) {
			logger.error("add a new record failer", e);
			this.handleException(e, request, response);
		}

	}

	protected String getVoClass() {
		return UsersVO.class.getName();
	}

	protected PageList getDatas(
			HttpServletRequest request, HttpServletResponse response,
			boolean pagable) throws Exception {
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String department = request.getParameter("root");
		Integer departId = SemAppUtils.getInteger(department);
		String remark = request.getParameter("remark");
		String searchValue = request.getParameter("searchValue");
		String id = request.getParameter("id");
		String organise = request.getParameter("organise");
		UsersVO vo = new UsersVO();
		logger.debug("searchValue=" + searchValue);
		// vo.setStatus("0");
		if (SemAppUtils.isNotEmpty(id)) {
			vo.setId(new Integer(id));
		}
		if (SemWebAppUtils.isNotEmpty(organise)) {
			vo.setOrganise(new Integer(organise));

		} else {
			Integer currentOrganise = getSessionUser(request).getOrganise()
					.getId();
			if (currentOrganise != null && currentOrganise.intValue() != 0) {
				vo.setOrganise(this.getSessionUser(request).getOrganise()
						.getId());
			}

		}
		if (departId != null) {
			if (departId.intValue() > 0) {
				vo.setDepartment(departId);
			} else if (departId.intValue() < 0) {
				vo.setOrganise(new Integer(0 - departId.intValue()));
			}
		}
		if (SemAppUtils.isNotEmpty(name)) {
			vo.setName(name);
		}

		if (SemAppUtils.isNotEmpty(remark)) {
			vo.setRemark(remark);
		}

		if (SemAppUtils.isNotEmpty(searchValue)) {
			vo.setSearchValue(searchValue);
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
			PageList plist = structureRemote.getUsers(vo,
					pagable ? new Integer(start) : new Integer(0),
					pagable ? new Integer(limit) : new Integer(0));
			logger.debug("get dcp data" + plist.getResults());
			return plist;
		} catch (Exception e) {
			this.handleException(e, request, response);
			
		}
		return null;

	}

	@RequestMapping("/cancel")
	public void cancel(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		
		Integer firstResult = vo.getFirstResult();
		if (firstResult == null)
			firstResult = new Integer(0);
		Integer fetchSize = vo.getFetchSize();
		if (fetchSize == null)
			fetchSize = new Integer(0);
		try {
			PageList list = structureRemote.getLieyuUsers(vo, firstResult,
					fetchSize);
			List fieldList = new ArrayList();
			// fieldList.add("data");
			// fieldList.add("errorcode");
			// fieldList.add("message");
			// fieldList.add("common.value.PageList#items");
			// fieldList.add("common.value.PageList#results");
			// fieldList.add("message");
			fieldList.add("common.os.vo.UsersVO#password");
			fieldList.add("common.os.vo.UsersVO#code");
			fieldList.add("common.os.vo.UsersVO#party");
			fieldList.add("common.os.vo.UsersVO#peoples");
			fieldList.add("common.os.vo.UsersVO#nationality");
			fieldList.add("common.os.vo.UsersVO#nativePlace");
			fieldList.add("common.os.vo.UsersVO#wedLock");
			fieldList.add("common.os.vo.UsersVO#educateLevel");
			fieldList.add("common.os.vo.UsersVO#archAddr");
			fieldList.add("common.os.vo.UsersVO#credentialType");
			fieldList.add("common.os.vo.UsersVO#credentialNo");
			fieldList.add("common.os.vo.UsersVO#tel");
			fieldList.add("common.os.vo.UsersVO#room");
			fieldList.add("common.os.vo.UsersVO#addr");
			fieldList.add("common.os.vo.UsersVO#postcode");
			fieldList.add("common.os.vo.UsersVO#parentId");
			fieldList.add("common.os.vo.UsersVO#orderNo");
			fieldList.add("common.os.vo.UsersVO#housetel");
			fieldList.add("common.os.vo.UsersVO#title");
			fieldList.add("common.os.vo.UsersVO#level");
			fieldList.add("common.os.vo.UsersVO#status");
			fieldList.add("common.os.vo.UsersVO#logoutDate");
			fieldList.add("common.os.vo.UsersVO#timeType");
			fieldList.add("common.os.vo.UsersVO#engineer");
			fieldList.add("common.os.vo.UsersVO#bloodType");
			fieldList.add("common.os.vo.UsersVO#isDirector");
			fieldList.add("common.os.vo.UsersVO#IsAd");
			fieldList.add("common.os.vo.UsersVO#isOpen");
			fieldList.add("common.os.vo.UsersVO#remark");
			fieldList.add("common.os.vo.UsersVO#searchValue");
			fieldList.add("common.os.vo.UsersVO#organise");
			fieldList.add("common.os.vo.UsersVO#organiseName");
			fieldList.add("common.os.vo.UsersVO#department");
			fieldList.add("common.os.vo.UsersVO#departmentCode");
			fieldList.add("common.os.vo.UsersVO#departmentName");
			fieldList.add("common.os.vo.UsersVO#nameAndTitle");
			// fieldList.add("common.os.vo.UsersVO#sexStr");
			fieldList.add("common.os.vo.UsersVO#isAd");
			fieldList.add("common.os.vo.UsersVO#email");
			// JsonResult result=new JsonResult(true,token);
			JsonResult result = new JsonResult(true, list);
			// result.setFilterList(fieldList);
			response.getWriter().write(result.toString(fieldList, true));// false表示列表字段
		} catch (Exception ee) {
			logger.error("search user fail", ee);
			this.handleException(ee, request, response);

		}
		

	}
	@RequestMapping("/login")
	public void login(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		
		Integer firstResult = vo.getFirstResult();
		if (firstResult == null)
			firstResult = new Integer(0);
		Integer fetchSize = vo.getFetchSize();
		if (fetchSize == null)
			fetchSize = new Integer(0);
		try {
			// UsersVO vo = this.formToVo(theForm, request);
			// PageList list = getDmFacade().getUsers(vo, firstResult,
			// fetchSize);
			Integer id;
			try {
				String imUserId = vo.getImUserId();
				id = SemAppUtils.getInteger(commonRemote.decrytor(imUserId));
			} catch (Exception ee) {
				throw new BaseBusinessException("用户不存在");
			}
			if (id == null)
				throw new BaseBusinessException("用户不存在");
			vo = structureRemote.getUserInfo(id);
			List fieldList = new ArrayList();
			// fieldList.add("data");
			// fieldList.add("errorcode");
			// fieldList.add("message");
			// fieldList.add("common.value.PageList#items");
			// fieldList.add("common.value.PageList#results");
			// fieldList.add("message");
			fieldList.add("common.os.vo.UsersVO#password");
			fieldList.add("common.os.vo.UsersVO#code");
			fieldList.add("common.os.vo.UsersVO#party");
			fieldList.add("common.os.vo.UsersVO#peoples");
			fieldList.add("common.os.vo.UsersVO#nationality");
			fieldList.add("common.os.vo.UsersVO#nativePlace");
			fieldList.add("common.os.vo.UsersVO#wedLock");
			fieldList.add("common.os.vo.UsersVO#educateLevel");
			fieldList.add("common.os.vo.UsersVO#archAddr");
			fieldList.add("common.os.vo.UsersVO#credentialType");
			fieldList.add("common.os.vo.UsersVO#credentialNo");
			fieldList.add("common.os.vo.UsersVO#tel");
			fieldList.add("common.os.vo.UsersVO#room");
			fieldList.add("common.os.vo.UsersVO#addr");
			fieldList.add("common.os.vo.UsersVO#postcode");
			fieldList.add("common.os.vo.UsersVO#parentId");
			fieldList.add("common.os.vo.UsersVO#orderNo");
			fieldList.add("common.os.vo.UsersVO#housetel");
			fieldList.add("common.os.vo.UsersVO#title");
			fieldList.add("common.os.vo.UsersVO#level");
			fieldList.add("common.os.vo.UsersVO#status");
			fieldList.add("common.os.vo.UsersVO#logoutDate");
			fieldList.add("common.os.vo.UsersVO#timeType");
			fieldList.add("common.os.vo.UsersVO#engineer");
			fieldList.add("common.os.vo.UsersVO#bloodType");
			fieldList.add("common.os.vo.UsersVO#isDirector");
			fieldList.add("common.os.vo.UsersVO#IsAd");
			fieldList.add("common.os.vo.UsersVO#isOpen");
			fieldList.add("common.os.vo.UsersVO#remark");
			fieldList.add("common.os.vo.UsersVO#searchValue");
			fieldList.add("common.os.vo.UsersVO#organise");
			fieldList.add("common.os.vo.UsersVO#organiseName");
			fieldList.add("common.os.vo.UsersVO#department");
			fieldList.add("common.os.vo.UsersVO#departmentCode");
			fieldList.add("common.os.vo.UsersVO#departmentName");
			fieldList.add("common.os.vo.UsersVO#nameAndTitle");
			fieldList.add("common.os.vo.UsersVO#userTag");
			fieldList.add("common.os.vo.UsersVO#isAd");
			fieldList.add("common.os.vo.UsersVO#email");
			// JsonResult result=new JsonResult(true,token);
			JsonResult result = new JsonResult(true, vo);
			// result.setFilterList(fieldList);
			response.getWriter().write(result.toString(fieldList, true));// false表示列表字段
		} catch (Exception ee) {
			logger.error("search user fail", ee);
			this.handleException(ee, request, response);

		}

	}

	@RequestMapping("/custom")
	public void custom(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			String username = vo.getId()+"";
			if (SemAppUtils.isEmpty(username)) {
				username = vo.getMobile();
			}
			if (SemAppUtils.isEmpty(username)) {
				username = vo.getLoginId();
			}
			if (SemAppUtils.isEmpty(username)) {
				username = vo.getMobile();
			}
			structureRemote.changePassword(username, vo.getNewpassword(),
					vo.getPassword());
			response.getWriter().write("{'success':true,'message':'修改密码成功'}");
		} catch (Exception ee) {
			this.handleException(ee, request, response);
		}
	}
	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String ids = request.getParameter("Ids");
		String[] idArray = ids.split("-");
		try {

			for (int i = 0; i < idArray.length; i++) {
				structureRemote.delUsers(new Integer(idArray[i]));
			}
			response.getWriter().write("{success:true}");
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
		}
	}
	@RequestMapping("/logout")
	public void logout(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			String captchas = vo.getCaptchas();
			logger.debug("update  dcp[" + vo.getId() + "]");
			structureRemote.updateUsers(vo, captchas);
			JsonResult result = new JsonResult(true, null, "success");
			response.getWriter().write(result.toString());// false表示列表字段
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/expand")
	public void expand(
			HttpServletRequest request, HttpServletResponse response,UsersVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			logger.debug("user vo" + vo);
			structureRemote.updateUsers(vo);
			JsonResult result = new JsonResult(true, null, "success");
			response.getWriter().write(result.toString());// false表示列表字段
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}

}
