package com.xxl.controller.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import common.value.NoticeVO;
import common.value.PageList;
import common.value.TreeControl;
import common.value.TreeControlNode;
import common.value.TreeNode;
import common.vo.BaseResponseVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/noticeController")
public class NoticeController extends BaseController {

	public static Log logger = LogFactory.getLog(NoticeController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(NoticeController.class);

	@Autowired
	private CommonRemote commonRemote;
	 
	@Autowired
	public AdminRemote adminRemote;

	@RequestMapping("/add.do")
	public void add(
			HttpServletRequest request, HttpServletResponse response,NoticeVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		logger.debug("moduleID=["+vo.getModuleID()+"],valid=["+vo.getValid()+"]");
//		FormFile attachFile = vo.getImportFile();
		Integer attachID = new Integer(0);
		String attachName = "";
		try {
//			if (attachFile != null) {
//				attachName = attachFile.getFileName();
//				logger.debug("uploading file to server,文件名  is[" + attachName
//						+ "]");
//				attachID = this.uploadFileToServer(request, attachFile);
//
//			}
//
//			NoticeVO vo = new NoticeVO(null, null, new Integer(theForm
//					.getModuleID()), theForm.getSubject(), new Integer(0),
//					theForm.getContent(), SemWebAppUtils.getCalendar(theForm
//							.getStartDate()), SemWebAppUtils
//							.getCalendar(theForm.getEndDate()), attachID,
//					attachName);
			Integer noticeID = adminRemote.addNotice(vo);

			response.getWriter().write("{success:true,id:'" + noticeID + "'}");
		} catch (Exception ee) {

			this.handleException(ee, request, response);
		}
	}

	@RequestMapping("/list.do")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		SessionUserBean currentUser = this.getSessionUser(request);
		try {
			PageList roleList = adminRemote.getUserRoleList(
					getSessionModuleID(request), currentUser.getCommonUser());
			response.setContentType("text/json;charset=UTF-8");
			String json = SemWebAppUtils.getJsonFromBean(roleList);
			//logger.debug("json="+json);
			response.getWriter().write(json);

		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}
	}
	
	protected PageList getDatas(
			HttpServletRequest request, HttpServletResponse response,
			boolean pagable) throws Exception {
		String moduleStr = request.getParameter("moduleID");
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String content = request.getParameter("content");
		String subject = request.getParameter("subect");

		// 处理检索条件
		boolean filter = false;
		NoticeVO vo = new NoticeVO();
		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
			try {
				vo.setModuleID(new Integer(moduleStr));
				filter = true;
			} catch (Exception ee) {
			}
		}
		if (SemWebAppUtils.isNotEmpty(subject)) {
			vo.setSubject(subject);
			filter = true;
		}
		if (SemWebAppUtils.isNotEmpty(content)) {
			vo.setContent(content);
			filter = true;
		}

		int start = 0, limit = 0;
		try {
			start = Integer.parseInt(startStr);
			limit = Integer.parseInt(limitStr);
		} catch (Exception ee) {
			start = 0;
			limit = 0;
		}
		Integer module = null;
		if (moduleStr != null) {
			try {
				module = new Integer(moduleStr);
			} catch (Exception ee) {

			}
		}
		if (module == null)
			module = this.getSessionModuleID(request);
		try {
			PageList tableList = adminRemote.getNoticeList(module, filter ? vo
					: null, pagable ? new Integer(start) : new Integer(0),
					pagable ? new Integer(limit) : new Integer(0));
			return tableList;
		} catch (Exception ee) {
			this.handleException(ee, request, response);
			return null;
		}

	}
	

	@RequestMapping("/update.do")
	public void update(HttpServletRequest request, HttpServletResponse response,NoticeVO vo) {
//		FormFile attachFile = theForm.getImportFile();
		Integer attachID = new Integer(0);
		logger.debug("moduleID=["+vo.getModuleID()+"],valid=["+vo.getValid()+"]");
		String attachName = "";
		try {
//			if (attachFile != null) {
//				attachID = this.uploadFileToServer(request, attachFile);
//				attachName = attachFile.getFileName();
//				logger.debug("attachName=" + attachName);
//				// attachName=SemWebAppUtils.toUtf8String(attachName);
//			}

//			NoticeVO vo = new NoticeVO(new Integer(theForm.getId()), null,
//					new Integer(theForm.getModuleID()), theForm.getSubject(),
//					new Integer(theForm.getValid()), theForm.getContent(),
//					SemWebAppUtils.getCalendar(theForm.getStartDate()),
//					SemWebAppUtils.getCalendar(theForm.getEndDate()), attachID,
//					attachName);

			String tableID = vo.getId()+"";
			adminRemote.updateNotice(vo);
			response.getWriter().write("{success:true,id:'" + tableID + "'}");
		} catch (Exception ee) {
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
			logger.error("业务逻辑层异常", ee);
		}
	}
	@RequestMapping("/delete.do")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String lineIds = request.getParameter("Ids");
		String[] ids = lineIds.split("-");
		try {
			
			for (int i = 0; i < ids.length; i++) {
				adminRemote.deleteNotice(new Integer(ids[i]));
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
	@RequestMapping("/custom.do")
	public void custom(
			HttpServletRequest request, HttpServletResponse response) {
		// logger.debug("get online date");
		try {
			Integer userId = this.getSessionUser(request).getEmpIDInt();
			String ip = SemWebAppUtils.getRemortIP(request);
			PageList pl =adminRemote.runTask(userId, ip);
			StringBuffer sb = new StringBuffer();
			sb.append("{results:");
			sb.append(pl.getResults());
			sb.append(",items:[");
			Iterator iter = pl.getItems().iterator();
			StringBuffer msb = new StringBuffer();
			int i = 0;
			while (iter.hasNext()) {
				msb.append("<p>" + i + "." + iter.next() + "</p>");
				i++;
			}
			if (i > 0) {
				sb.append("{message:'");
				sb.append(new String(msb) + "'}");
			}
			sb.append("]}");
			response.setContentType("text/json;charset=UTF-8");
			// String json = SemWebAppUtils.getJsonFromBean(pl);
			String json = new String(sb);
			logger.debug("json=" + json);
			response.getWriter().write(json);
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}

	
	@RequestMapping("/")
	public String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		
		return "notice";
	}
	
	

	public void importExcel(
			HttpServletRequest request, HttpServletResponse response) {
		// response.setContentType("text/json;charset=UTF-8");
//		NoticeForm theForm = (NoticeForm) form;
//		FormFile excelFile = theForm.getImportFile();
//		Workbook workBook;
//		logger.debug("file size=" + excelFile.getFileSize());
//		try {
//			workBook = Workbook.getWorkbook(excelFile.getInputStream());
//			List excelList = new ArrayList();
//			String model = "";
//			String color = "";
//			Sheet sheet = workBook.getSheet(0);// 这里就不用数组了,因为只是第一页写数据.
//			int rowNum = sheet.getRows();
//			for (int iRow = 0; iRow < rowNum; iRow++) {
//				Cell[] cells = sheet.getRow(iRow);
//				if (cells != null || cells.length > 0) {
//					model = cells[0].getContents();
//					color = cells[1].getContents();
//					excelList.add(model + "," + color);
//				}
//			}
//			logger.debug("共导入" + excelList.size() + "条记录");
//
//			response.getWriter().write(
//					"{success:true,msg:'共导入" + excelList.size() + "条记录'}");
//		} catch (BiffException e) {
//			logger.error("Excel文件操作失败", e);
//			try {
//				response.getWriter().write("{success:false,msg:'Excel文件操作失败'}");
//			} catch (IOException e1) {
//			}
//		} catch (FileNotFoundException e) {
//			logger.error("Excel文件操作失败", e);
//			try {
//				response.getWriter().write("{success:false,msg:'Excel文件操作失败'}");
//			} catch (IOException e1) {
//			}
//		} catch (Exception e) {
//			logger.error("Excel文件操作失败", e);
//			try {
//				response.getWriter().write(
//						"{success:false,msg:'Excel文件操作失败'" + e.getMessage()
//								+ "}");
//			} catch (IOException e1) {
//			}
//		}

	}

	public String convertISO2UTF(String value) {
		try {
			return new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ee) {
			logger.error("convert bytes failer ,", ee);
			return value;

		}

	}
	@RequestMapping("/save.do")
	public void save(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("change online method");
		try {
			Integer userId = this.getSessionUser(request).getEmpIDInt();
			String methodStr = request.getParameter("method");
			logger.debug("method id=" + methodStr);
			
			adminRemote.onMethod(userId, convertMethod(methodStr));
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	@RequestMapping("/cancel.do")
	public void cancel(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("close online method");
		try {
			String methodStr = request.getParameter("method");
			logger.debug("method id=" + methodStr);			
			adminRemote.cleanSignleMode( convertMethod(methodStr));
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}
	
	private Integer convertMethod(String methodStr){
		Integer method=new Integer(0);
		if(methodStr.startsWith("Report")){
			methodStr="-"+methodStr.substring(6);
		}
		try{
			method=SemAppUtils.getInteger(methodStr);
		}catch(Exception ee){
			method=new Integer(0);
		}
		return method;
	}
	
	public void logout(
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("logout and clean online");
		try {
			Integer userId = this.getSessionUser(request).getEmpIDInt();		
			adminRemote.logout(userId);
		} catch (Exception e) {
			this.handleException(e, request, response);
		}

	}

}