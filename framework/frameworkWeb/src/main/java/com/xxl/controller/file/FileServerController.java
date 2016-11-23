package com.xxl.controller.file;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.xxl.facade.FileRemote;
import com.xxl.facade.HelperRemote;

import common.controller.BaseController;
import common.exception.BaseBusinessException;
import common.utils.SemAppUtils;

@Controller
@RequestMapping("/fileServerController")
public class FileServerController extends BaseController {

	public static Log logger = LogFactory.getLog(FileServerController.class);

	public static Log sysLogger = LogFactory.getLog("sys");

	@Autowired
	public FileRemote fileRemote;
	
	@Autowired
	public HelperRemote hlperRemote;
	public void list(
			HttpServletRequest request, HttpServletResponse response) {
//		String moduleStr = request.getParameter("moduleID");
//		String startStr = request.getParameter("start");
//		String limitStr = request.getParameter("limit");
//		String name = request.getParameter("name");
//		String link = request.getParameter("link");
//		String parameter = request.getParameter("parameter");
//		String remark = request.getParameter("remark");
//		// 处理检索条件
//		boolean filter = false;
//		XmlServicesVO vo = new XmlServicesVO(null);
//		if (SemWebAppUtils.isNotEmpty(moduleStr)) {
//			try {
//				vo.setModuleID(new Integer(moduleStr));
//				filter = true;
//			} catch (Exception ee) {
//			}
//		}
//		if (SemWebAppUtils.isNotEmpty(name)) {
//			vo.setName(name);
//			filter = true;
//		}
//		if (SemWebAppUtils.isNotEmpty(link)) {
//			vo.setLink(link);
//			filter = true;
//		}
//		if (SemWebAppUtils.isNotEmpty(parameter)) {
//			vo.setParameter(parameter);
//			filter = true;
//		}
//		if (SemWebAppUtils.isNotEmpty(remark)) {
//			vo.setRemark(remark);
//			filter = true;
//		}
//		int start = 0, limit = 0;
//		try {
//			start = Integer.parseInt(startStr);
//			limit = Integer.parseInt(limitStr);
//		} catch (Exception ee) {
//			start = 0;
//			limit = 0;
//		}
//		Integer module = null;
//		if (moduleStr != null) {
//			try {
//				module = new Integer(moduleStr);
//			} catch (Exception ee) {
//
//			}
//		}
//		if (module == null)
//			module = this.getSessionModuleID(request);
//		try {
//			PageList tableList = fileRemote.getXmlServicesList(module,
//					filter ? vo : null, new Integer(start), new Integer(limit));
//			response.setContentType("text/json;charset=UTF-8");
//			String json = WebAppUtils.getJsonFromBean(tableList);
//			response.getWriter().write(json);
//		} catch (Exception ee) {
//			logger.error("业务逻辑层异常", ee);
//		}
//		
	}

	public void update(
			HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("text/json;charset=UTF-8");
//		XmlServicesForm theForm = (XmlServicesForm) form;
//		try {
//			
//			XmlServicesVO vo = new XmlServicesVO(new Integer(theForm.getId()),
//					theForm.getName(), theForm.getLink(), theForm
//							.getParameter(), null, new Integer(theForm
//							.getModuleID()), theForm.getRemark());
//
//			String tableID = theForm.getId();
//			fileRemote.updateXmlServicesTable(vo);
//			response.getWriter().write("{success:true,id:'" + tableID + "'}");
//		} catch (Exception ee) {
//			try {
//				response.getWriter().write("{success:false}");
//			} catch (IOException e) {
//			}
//			logger.error("业务逻辑层异常", ee);
//		}
//		
	}


	public void custom(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
//		String filename = request.getParameter("key");
//		String hash = request.getParameter("hash");
//		String endUser = request.getParameter("endUser");
//		String actionId = request.getParameter("actionId");
//		String args = request.getParameter("args");
//		logger.debug("request arg filename[" + filename + "],actionId["
//				+ actionId + "],endUser[" + endUser + "],hash[" + hash
//				+ "],args[" + args + "]");
//		try {
//
//			this.getAdminSession().saveUploadFile(getInteger(actionId),
//					filename, hash);
////			response
////					.getWriter()
////					.write(
////							"{\"success\":true,\"errorcode\":1,\"message\":\"保存文件成功\"}");
//			JsonResult result=new JsonResult(true,null,"保存文件成功");
//			response.getWriter().write(result.toString());
//		} catch (Exception ee) {
//			this.handleException(ee, request, response);
//		}
	}

	public void defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
//		List moduleList = getSystemList(getSessionModuleID(request));
//		request.setAttribute("module_list", moduleList);
//		logger.debug("go to main page" + mapping.getInput());
//		return new void(mapping.getInput());
	}
	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			String token = fileRemote.getUploadFileTokenForWeb();
			response.getWriter().write("{\"uptoken\":\"" + token + "\"}");
		} catch (Exception ee) {

		}
	}
	@RequestMapping("/cancel")
	public void cancel(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
//		try {
//			String token = fileRemote.getUploadFileToken();
//			response.getWriter().write(
//					"{\"errorcode\": \"1\",\"message\":\"success\",\"data\":\""
//							+ token + "\"}");
//		} catch (Exception ee) {
//
//		}
		
	}
	
	public void login(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
//		try {
//			
//			String token = fileRemote.getUploadMediaToken();
//			response.getWriter().write(
//					"{\"errorcode\": \"1\",\"message\":\"success\",\"data\":\""
//							+ token + "\"}");
//		} catch (Exception ee) {
//
//		}
		
	}

	public void save(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
			String token = fileRemote.getUploadFileTokenForWeb();
			response.getWriter().write("{\"uptoken\":\"" + token + "\"}");
		} catch (Exception ee) {

		}
		
	}
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		try {
//			FileItemFactory factory = new DiskFileItemFactory();
//			ServletFileUpload upload = new ServletFileUpload(factory);
//			upload.setHeaderEncoding("UTF-8");
//			List items = upload.parseRequest(request);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            List<MultipartFile> fileList = multipartRequest.getFiles("file");  
			logger.debug("items="+fileList.size() +",fileList.getClass()="+fileList.getClass());
			JSONObject obj = new JSONObject();
			String newFileName=addQiNiuFile(fileList);
			obj.put("error", 0);
			obj.put("url", hlperRemote.getProperty("FILESERVER_URL")
					+ newFileName);
			logger.debug("upload result" + obj.toString());
			response.getWriter().write(obj.toString());
		} catch (Exception ee) {
			this.handleException(ee, request, response);
		}
		
	}
	
	public String addQiNiuFile(List<MultipartFile> Fileitems) throws BaseBusinessException {
		try {   ///////////////////////指定上传的Zone的信息//////////////////
		    //第一种方式: 指定具体的要上传的zone
		    //注：该具体指定的方式和以下自动识别的方式选择其一即可
		    //要上传的空间(bucket)的存储区域为华东时
		    // Zone z = Zone.zone0();
		    //要上传的空间(bucket)的存储区域为华北时
		    // Zone z = Zone.zone1();
		    //要上传的空间(bucket)的存储区域为华南时
		    // Zone z = Zone.zone2();
		    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
		    Zone z = Zone.autoZone();
		    Configuration c = new Configuration(z);
			Iterator<MultipartFile> itr = Fileitems.iterator();
			UploadManager uploadManager = new UploadManager(c);
			while (itr.hasNext()) {
				MultipartFile item = itr.next();
				String fileName = item.getOriginalFilename();
				logger.debug("fileName="+fileName+",getOriginalFilename="+item.getOriginalFilename());
				long fileSize = item.getSize();
				if (!item.isEmpty()) {
					// 检查文件大小
					if (item.getSize() > 10 * 1024 * 1024) {
						logger.info("上传文件大小超过限制");
						throw new BaseBusinessException("上传文件大小超过限制");
					}
					// 检查扩展名
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = SemAppUtils.getFilename(fileName)
							+ df.format(new Date()) + "." + fileExt;
					Response res = uploadManager.put(item.getBytes(), newFileName,
							fileRemote.getUploadFileTokenForWeb());
					if (res.isOK()) {
						logger
								.info("upload file success,[" + newFileName
										+ "]");
						return  newFileName;
					} else {
						logger.info("upload file fail,[" + newFileName
								+ "],return [" + res.bodyString() + "]");//
						return null;
					}
				
				}
			}
		} catch (Exception ee) {
			logger.error(ee);
			throw new BaseBusinessException(ee);
		}
		return null;
		
	}

}
