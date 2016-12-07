package com.xxl.controller.os;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.StructureRemote;

import common.controller.BaseController;
import common.os.vo.DepartmentVO;
import common.os.vo.OrganiseVO;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/organiseController")
public class OrganiseController extends BaseController {
	@Autowired
	private StructureRemote structureRemote;
	
	@RequestMapping("/cancel")
	public void cancel(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("id");
		Integer root = SemAppUtils.getInteger(rootStr);
		Integer currentRoot=this.getSessionUser(request).getOrganise().getId();
		logger.debug("root=" + rootStr+"current Root="+currentRoot);
		OrganiseVO vo = new OrganiseVO();
		boolean flag=false;
		if (root != null && root.intValue() == 0) {
			if (currentRoot.intValue() == 0) {
				vo.setParentId(root);
			} else {
				vo.setId(currentRoot);
				flag=true;
			}
		} else {
			
			vo.setParentId(root);
		}
		try {
			PageList plist=null;
			if(flag){
				OrganiseVO ovo=structureRemote.getOrganise(currentRoot);
				plist=new PageList(true,ovo,"");
			}else{
				 plist= structureRemote.getOrganise(vo, new Integer(0),
							new Integer(0));
			}
			logger.debug(SemWebAppUtils.getJsonFromBean(plist));
			response.getWriter().write(SemWebAppUtils.getJsonFromBean(plist));
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
}
