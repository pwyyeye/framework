package com.xxl.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * spring security 登录时使用
 * @author karys
 *
 */
@Controller
@RequestMapping("/securityUser")
public class SecurityUserController {

	@RequestMapping(value = "/toLogin")
	public String login(HttpServletRequest request) {
		return "../login";
	}

}