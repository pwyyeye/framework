package com.xxl.springsecurity;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;

import com.common.utils.SemAppUtils;
import com.xxl.hnust.service.ISysUserService;
import com.xxl.hnust.vo.SysUsersVo;

public class SecurityUserService implements UserDetailsService {
	
	public static Log logger = LogFactory.getLog(SecurityUserService.class);

	private final ISysUserService sysUserService;
	
	private MessageSource messageSource;
	
	public SecurityUserService(ISysUserService sysUserService, MessageSource messageSource) {
		this.sysUserService = sysUserService;
		this.messageSource = messageSource;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		SysUsersVo sysUsersVo = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		try {
			sysUsersVo = sysUserService.getByUserName(SemAppUtils.convertCharacterEncoding(username));
			//得到用户的权限
			authorities = sysUserService.loadUserAuthorities(SemAppUtils.convertCharacterEncoding(username));
			sysUsersVo.setAuthorities(authorities);
		} catch (Exception e) {
			sysUsersVo = null;
		}
		if(sysUsersVo != null) {
			return sysUsersVo;
		}
		//String message = messageSource.getMessage("UserDetails.isLocked",null,null);  //用户已被锁定
		String message = messageSource.getMessage("UserDetails.userNotFound", 
				new Object[]{"\""+username+"\""}, null);  //用户username不存在
		message = SemAppUtils.convertCharacterEncoding(message);
		throw new UsernameNotFoundException(message);
	}
	
}
