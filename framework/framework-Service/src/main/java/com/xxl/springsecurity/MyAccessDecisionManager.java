package com.xxl.springsecurity;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 自定义accessDecisionManager访问控制器
 * @author karys
 *
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		System.out.println("===MyAccessDecisionManager===");
		
		if(authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();
			System.out.println("===访问资源的用户为==="+user.getUsername());
		}
        
		if (configAttributes == null) {
			return;
		}
		// 所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			// 访问所请求资源所需要的权限
			//String needPermission = configAttribute.getAttribute();
			String needPermission = configAttribute.toString();
			System.out.println("needPermission is " + needPermission);
			// 用户所拥有的权限authentication
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needPermission.contains((ga.getAuthority()))
						|| needPermission.contains("permitAll")) {
					return;
				}
			}
		}
		// 没有权限让我们去捕捉
		throw new AccessDeniedException("对不起，您没有权限访问！");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}