package com.xxl.springsecurity;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 自定义没有权限处理类
 * @author karys
 *
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
	
	private String accessDeniedUrl;

	public MyAccessDeniedHandler() {
	}

	public MyAccessDeniedHandler(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		System.out.println("===MyAccessDeniedHandler===");
		request.getSession().setAttribute("SPRING_SECURITY_403_EXCEPTION", accessDeniedException.getMessage());
		response.sendRedirect(request.getContextPath()+"/403.jsp");
	}

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}
	
}