package com.xxl.controller.file;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.baidu.ueditor.ActionEnter;
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
@RequestMapping("/ueditorController")
public class UEditorController extends BaseController {

	public static Log logger = LogFactory.getLog(UEditorController.class);

	@RequestMapping("/")
	public void  defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		try {
//			request.setCharacterEncoding( "utf-8" );
			response.setHeader("Content-Type" , "text/html");
			 String rootPath = request.getRealPath("/");
			 logger.debug("rootPath="+rootPath);
			 String ret= new ActionEnter( request, rootPath).exec();
			 logger.debug("rootPath="+ret);
			 response.getWriter().write(ret);
		}catch (Exception e) {
			logger.error(e);
		}

	}
	
}
