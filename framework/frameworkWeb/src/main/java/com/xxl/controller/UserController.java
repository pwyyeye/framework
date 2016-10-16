package com.xxl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.baseService.service.IUserService;
import com.xxl.baseService.vo.UserVo;

import common.controller.BaseController;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.vo.BaseResponseVO;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	public static Log logger = LogFactory.getLog(UserController.class);

	@Resource
	private IUserService userService;
	
	/*@RequestMapping(value = "/toLogin")
	public String toLogin(HttpServletRequest request) {
		return "../login";
	}*/
	
	/*@RequestMapping(value = "/login")
	public String login(HttpServletRequest request) {
		return "../login";
	}*/

	@RequestMapping(value = "/testHibernate")
	@ResponseBody
	public UserVo testHibernate(HttpServletRequest request) {
		String userIdStr = request.getParameter("id");
		UserVo userVo = null;
		if (userIdStr != null && !userIdStr.equals("")) {
			int userId = Integer.parseInt(userIdStr);
			userVo = this.userService.findById(userId);
		}
		return userVo;
	}

	@RequestMapping(value = "/testHibernate2")
	@ResponseBody
	// easyui列表分页测试
	public void testHibernate2(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "10") Integer rows, // 页数大小
			@RequestParam(required = false, defaultValue = "") String paramName,
			@RequestParam(required = false, defaultValue = "") String createTime) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			PageList pageList = this.userService.findByPage(new UserVo(),
					(page - 1) * rows, rows);
			/*
			 * Map<String,Object> map = new HashMap<String,Object>();
			 * map.put("rows", pageList.getItems()); 
			 * map.put("total", pageList.getResults());
			 */
			List b1 = new ArrayList();
			b1.add("com.xxl.hnust.vo.UserVo#password");
			b1.add("com.xxl.hnust.vo.UserVo#age");
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1",
					"获取用户列表成功", pageList), b1, "yyyy-MM-dd HH:mm:ss");
			response.getWriter().write(json);
		} catch (IOException e) {
			logger.error("获取用户列表发生异常!", e);
			this.handleException(new IOException("获取用户列表发生异常!"), request, response);
		}
	}

	@InitBinder("userVo")
	// 传入对象参数时，需要提前配置这项
    public void initBinder(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("userVo.");    
    } 
	
	@RequestMapping(value = "/addUser")
	@ResponseBody
	// 传UserVo参数时的用法
	// http://127.0.0.1:8081/BaseProject/user/addUser
	// ?userVo.id=20&&userVo.userName=ddd&&userVo.password=ddd&&userVo.age=20
	public void addUser(@ModelAttribute UserVo userVo, HttpServletRequest request,
			HttpServletResponse response) {
		/*UserVo vo = new UserVo();
		vo.setId(18);
		vo.setUserName("bbb");
		vo.setPassword("bbb");
		vo.setAge(18);*/
		try {
			response.setContentType("text/json;charset=UTF-8");
			String id = this.userService.addUser(userVo);
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "添加用户成功", id), false);
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("添加用户发生异常!", e);
			this.handleException(new IOException("添加用户发生异常!"), request, response);
		}
	}

	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public void updateUser(HttpServletRequest request) {
		UserVo vo = new UserVo();
		vo.setId(25);
		vo.setUserName("abc");
		vo.setPassword("abc");
		vo.setAge(25);
		this.userService.updateUser(vo);
	}

	@RequestMapping(value = "/deleteUser")
	@ResponseBody
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response) {
		String userIdStr = request.getParameter("id");
		response.setContentType("text/json;charset=UTF-8");
		if (userIdStr != null && !userIdStr.equals("")) {
			Integer userId = Integer.parseInt(userIdStr);
			System.out.println("userId==" + userId);
			try {
				this.userService.deleteUser(userId);
				String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "删除用户成功", ""),false);
				response.getWriter().write(json);
			} catch (Exception e) {
				logger.error("删除用户发生异常!", e);
				this.handleException(new Exception("删除用户发生异常!"), request, response);
			}
		}
	}

	@RequestMapping(value = "/findUser")
	@ResponseBody
	public UserVo findUser(HttpServletRequest request) {
		String userIdStr = request.getParameter("id");
		UserVo vo = null;
		if (userIdStr != null && !userIdStr.equals("")) {
			Integer userId = Integer.parseInt(userIdStr);
			System.out.println("findUser userId==" + userId);
			vo = this.userService.findUser(userId);
		}
		return vo;
	}

}