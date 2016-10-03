package com.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.common.exception.BaseBusinessException;
import com.common.exception.BaseException;
import com.common.value.JsonResult;

public class BaseController {
	
	public Log logger = LogFactory.getLog(BaseController.class);;
	
	protected String handleException(Throwable th,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		if (th instanceof BaseException) {
			logger.error("系统发生不可预料之异常:", th);
			BaseException ex = (BaseException) th;
			//publishSystemMessage(request, "系统发生异常,请及时解决",
			//		th.getLocalizedMessage());
			try {
//				response.getWriter()
//				.write(
//						"{\"success\":false,\"errorcode\":0,\"message\":\"" + ex.getErrMsg()
//								+ "\"}");
				JsonResult result = new JsonResult(false, null, ex.getErrMsg());
				response.getWriter().write(result.toString());
			} catch (Exception ee) {
				logger.error("get response error", ee);
			}
			return null;
		}
		if (th instanceof BaseBusinessException) {
			BaseBusinessException ex = (BaseBusinessException) th;
			String message = ex.getErrMsg();
			try {
//				response.getWriter().write(
//				"{\"success\":false,\"errorcode\":0,\"message\":\"" + message + "\"}");
				JsonResult result = new JsonResult(false, null, ex.getErrMsg());
				response.getWriter().write(result.toString());
			} catch (Exception ee) {
			}
			return null;
		} else {
			logger.error("系统发生不可预料之异常，已记录:", th);
			//publishSystemMessage(request, "系统发生异常,请及时解决",
			//		th.getLocalizedMessage());
			try {
//				response.getWriter().write(
//				"{\"success\":false,\"errorcode\":0,\"message\":\"" + th.getMessage()
//						+ "\"}");
				JsonResult result = new JsonResult(false, null, th.getMessage());
				response.getWriter().write(result.toString());
			} catch (Exception ee) {
			}
			return null;
		}
	}
	
	// 发送通知
	/*protected boolean publishSystemMessage(HttpServletRequest request,
			String subject, String content) {
		ItModuleVO module = getCurrentModuleVO(request);
		if (module == null || module.getMessageID() == 0) {
			logger.error("get Session module failure" + module);
			return false;
		}

		return SemAppUtils.publishMessage(module.getMessageID(), subject
				+ ",系统:" + module.getName(), content);
	}*/
	
	
}
