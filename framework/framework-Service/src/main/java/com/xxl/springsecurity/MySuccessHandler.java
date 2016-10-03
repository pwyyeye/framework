package com.xxl.springsecurity;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.xxl.hnust.service.ISysUserService;
import com.xxl.hnust.vo.SysUsersVo;

/**
 * 自定义登录成功后的处理类
 * @author karys
 *
 */
//public class MySuccessHandler implements AuthenticationSuccessHandler {
public class MySuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private ISysUserService sysUserService;
	
	public MySuccessHandler(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException {
		System.out.println("===MySuccessHandler===");
		// 跳转到先前链接地址
		/*try {
			super.onAuthenticationSuccess(request, response, authentication);
		} catch (ServletException e) {
			throw new IOException();
		}*/
		
		saveLoginInfo(request, authentication);
		
		// 登录成功后跳转到首页
		redirectStrategy.sendRedirect(request, response, "/index.jsp");
	}
	
    public void saveLoginInfo(HttpServletRequest request,Authentication authentication){  
        SysUsersVo userVo = (SysUsersVo)authentication.getPrincipal();  
        try {  
            String ip = this.getIpAddress(request);  
            userVo.setLastLogin(Calendar.getInstance());  
            userVo.setLoginIp(ip);  
            sysUserService.saveSysUsers(userVo);  
        } catch (Exception e) {  
            if(logger.isWarnEnabled()){  
                logger.info("无法更新用户登录信息至数据库");  
            }  
        }  
    }  
      
    public String getIpAddress(HttpServletRequest request){    
        String ip = request.getHeader("x-forwarded-for");    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();    
        }    
        return ip;    
    } 

}
