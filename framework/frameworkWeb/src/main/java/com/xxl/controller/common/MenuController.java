package com.xxl.controller.common;

import java.io.IOException;
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
import com.xxl.facade.HelperRemote;

import common.bussiness.CommonLogger;
import common.controller.BaseController;
import common.exception.BaseException;
import common.exception.CommonException;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.ItModuleVO;
import common.value.MenuVO;
import common.value.PageList;
import common.value.TreeControl;
import common.value.TreeControlNode;
import common.value.TreeNode;
import common.vo.BaseResponseVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Controller
@RequestMapping("/menuController")
public class MenuController extends BaseController {

	public static Log logger = LogFactory.getLog(MenuController.class);
	public static Log dbLogger = SemAppUtils.getDBLog(MenuController.class);

	@Autowired
	private CommonRemote commonRemote;
	 
	@Autowired
	public AdminRemote adminRemote;
	
	@Autowired
	public HelperRemote helperRemote;
	
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response,MenuVO vo) {
		response.setContentType("text/json;charset=UTF-8");

		logger.debug("add,module id =" + vo.getModuleID()
				+ ",parentModule" + vo.getParentModule() + ",name"
				+ vo.getName());

		try {
			int currentModuleId = 0 - vo.getModuleID();
			if (currentModuleId == 0) {
				currentModuleId = this.getSessionModuleID(request).intValue();
			}
		
			vo.setModuleID(new Integer(currentModuleId));// 模块ID转正
//			if (vo.getSingleMode() == null) {
//				vo.setSingleMode(0);
//			} else {
//				vo.setSingleMode(new Integer(vo.getSingleMode())
//						.intValue());
//			}
//			String parent = vo.getParentModule();
//			int parentInt=SemWebAppUtils.isEmpty(parent) ?0:Integer
//					.parseInt(parent);
//			if(parentInt<0) parentInt=0;
//			vo.setParentModule(parentInt);
//			vo.setSortID(Integer.parseInt(vo.getSortID()));
			Integer moduleID = adminRemote.addMenu(vo);
			response.getWriter().write("{success:true,id:" + moduleID + "}");
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
		}
	}

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("root");
		String idStr = request.getParameter("id");
		String moduleStr = request.getParameter("moduleID");
		List modules = new ArrayList();
		logger.debug("root=" + rootStr + ",id=" + idStr + ",module="
				+ moduleStr);
		try {
			if (SemWebAppUtils.isNotEmpty(idStr)) {
				int root = Integer.parseInt(idStr);
				if (root == 0)
					root = 0 - this.getSessionModuleID(request).intValue();
				boolean isModule = (root <= 0);// 如果ID<0表示是模块节点
				if (isModule) {
					root = 0 - root;
					logger.debug("convert root=" + root);
					modules = adminRemote.getSystemList(new Integer(root),
							new Boolean(false), new Boolean(true));
					List menus = adminRemote.getMenuList(new Integer(root),
							new Integer(0));
					modules.addAll(menus);
					// jsonBuffer.append(WebAppUtils.getListJsonFromList(modules));
				} else {
					modules = adminRemote.getMenuList(null, new Integer(root));
					// jsonBuffer.append(WebAppUtils.getListJsonFromList(menus));
				}

			}
			if (SemWebAppUtils.isNotEmpty(moduleStr)) {
				int moduleID = Integer.parseInt(rootStr);
				if (moduleID == 0) {
					moduleID = this.getSessionModuleID(request).intValue();
				}
				List menus = adminRemote.getMenuList(new Integer(moduleID),
						new Integer(0));
				modules.addAll(menus);
			}
			PageList pageList = new PageList(modules);
			String json = SemWebAppUtils.getJsonFromBean(pageList);
			logger.debug("json=" + json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}
	}

	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response,MenuVO vo) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			
//			MenuVO vo = new MenuVO(new Integer(theForm.getId()));
//			vo.setName(theForm.getName());
//			vo.setLink(theForm.getLink());
//			vo.setFrame(theForm.getFrame());
//			vo.setSortID(Integer.parseInt(theForm.getSortID()));
//			if (theForm.getSingleMode() == null) {
//				vo.setSingleMode(0);
//			} else {
//				vo.setSingleMode(new Integer(theForm.getSingleMode())
//						.intValue());
//			}
			logger.debug("-----------------------------"+vo.getName());
			adminRemote.updateMenu(vo);
			response.getWriter().write(
					"{success:true,id:" + vo.getId() + "}");
		} catch (Exception ee) {
			try {
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
			}
			logger.error("业务逻辑层异常", ee);
		}
	}

	@RequestMapping("/importExcel")
	public void importExcel(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("root");
		String moduleStr = request.getParameter("moduleID");
		logger.debug("root=" + rootStr);
		List list = new ArrayList();
		PageList pageList = new PageList();
		int count = 0;
		try {
			
			if (SemWebAppUtils.isNotEmpty(rootStr)) {
				int root = Integer.parseInt(rootStr);
				if (root == 0)
					root = 0 - this.getSessionModuleID(request).intValue();
				boolean isModule = (root <= 0);// 如果ID<0表示是模块节点
				if (isModule) {
					root = 0 - root;
					List modules = adminRemote.getSystemList(
							new Integer(root), new Boolean(false), new Boolean(
									true));
					List menus = adminRemote.getMenuList(new Integer(root),
							new Integer(0));
					modules.addAll(menus);
					count = count + modules.size();
					list.addAll(modules);
				} else {
					List menus = adminRemote.getMenuList(null, new Integer(
							root));
					// jsonBuffer.append(WebAppUtils.getListJsonFromList(menus));
					count = count + menus.size();
					list.addAll(menus);
				}
			}
			if (SemWebAppUtils.isNotEmpty(moduleStr)) {
				int moduleID = Integer.parseInt(rootStr);
				if (moduleID == 0) {
					moduleID = this.getSessionModuleID(request).intValue();
				}
				List menus = adminRemote.getMenuList(new Integer(moduleID),
						new Integer(0));
				count = count + menus.size();
				list.addAll(menus);
			}
			pageList.setResults(count);
			pageList.setItems(list);
			String json = SemWebAppUtils.getJsonFromBean(pageList, true);
			response.getWriter().write(json);
			logger.debug("json:" + json);
		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}

	}

	@RequestMapping("/delete")
	public void delete(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String lineIds = request.getParameter("Ids");
		String[] ids = lineIds.split("-");
		try {
			
			for (int i = 0; i < ids.length; i++) {
				adminRemote.deleteMenu(new Integer(ids[i]));
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

	@RequestMapping("/cancel")
	public void cancel(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("root");
		logger.debug("root=" + rootStr);
		Integer root = null;
		try {
			root = new Integer(rootStr);
		
			MenuVO menu = adminRemote.getMenuByID(root);
			String json;
			if (menu != null) {
				json = "{success:true,data:"
						+ SemWebAppUtils.getJsonFromBean(menu) + "}";
			} else {
				json = "{success:false}";
			}
			logger.debug(json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}
	}

	@RequestMapping("/")
	public String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			TreeControl moduleTree = adminRemote.getSystemTree("0");
			request.setAttribute("MODULE_TREE", moduleTree);
		} catch (Exception ee) {
			logger.error("业务逻辑层异常", ee);
		}
		return "menu";
	}

	@RequestMapping("/expand")
	public void expand(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("id");
		String moduleStr = request.getParameter("moduleID");
		logger.debug("root=" + rootStr);
		List list = new ArrayList();
		PageList pageList = new PageList();
		int count = 0;
		try {
			
			if (SemWebAppUtils.isNotEmpty(rootStr)) {
				int root = Integer.parseInt(rootStr);
				if (root == 0)
					root = 0 - this.getSessionModuleID(request).intValue();
				boolean isModule = (root <= 0);// 如果ID<0表示是模块节点
				logger.debug("rootStr:"+rootStr+",root"+root+",isModule"+isModule);
				if (isModule) {
					root = 0 - root;
					List modules = adminRemote.getSystemList(
							new Integer(root), new Boolean(false), new Boolean(
									true));
					List menus = adminRemote.getMenuList(new Integer(root),
							new Integer(0));
					modules.addAll(menus);
					count = count + modules.size();
					list.addAll(modules);
				} else {
					List menus = adminRemote.getMenuList(null, new Integer(
							root));
					// jsonBuffer.append(WebAppUtils.getListJsonFromList(menus));
					count = count + menus.size();
					list.addAll(menus);
				}
			}
			if (SemWebAppUtils.isNotEmpty(moduleStr)) {
				int moduleID = Integer.parseInt(rootStr);
				if (moduleID == 0) {
					moduleID = this.getSessionModuleID(request).intValue();
				}
				List menus = adminRemote.getMenuList(new Integer(moduleID),
						new Integer(0));
				count = count + menus.size();
				list.addAll(menus);
			}
			pageList.setResults(count);
			pageList.setItems(list);
			String json = SemWebAppUtils.getJsonFromBean(pageList, true);
			response.getWriter().write(json);
			logger.debug("json:" + json);
		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}
	}

	@RequestMapping("/custom")
	public void custom(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("id");
		TreeControl control = null;
		logger.debug("root=" + rootStr);
		HttpSession session = request.getSession();

		List nodeList = new ArrayList();
		// boolean isDocList=false;
		try {
			control = (TreeControl) session.getAttribute("menu_control_test");
			TreeControlNode[] nodes = null;
			String url = helperRemote.getProperty("COMMON_DOC_LINK");
			String split = url.indexOf("?") != -1 ? "&" : "?";
			if (SemAppUtils.isEmpty(rootStr) || "0".equals(rootStr)||"6".equals(rootStr)) {
				TreeControlNode root = control.getRoot();
				nodes = root.findChildren();
			} else {
				TreeControlNode root = control.findNode(rootStr);
				String action = null;
				boolean isDirectroy = false;
				if (root == null) {
					if (rootStr.startsWith("D")) {// 表示文件夹
						int point = rootStr.indexOf("R");
						logger.debug("point=" + point + "rootStr" + rootStr);
						action = url
								+ split
								+ "dirId="
								+ SemAppUtils.getInteger(rootStr.substring(1,
										point)) + "&"
								+ SemWebAppConstants.RIGHT_CODE + "="
								+ rootStr.substring(point + 1);
						isDirectroy = true;
					}
				} else {
					action = root.getAction();
				}
				//
				// String
				// action=(root==null?url+split+"id="+rootStr:root.getAction());
				// logger.debug("get current URL="+url+"action="+action);
				if (SemAppUtils.isNotEmpty(action) && action.startsWith(url)) {
					// isDocList=true;//表示为文件夹的目录
					String docRoot = SemAppUtils.getUrlQuery(action, "dirId");
					String rightCode = SemAppUtils.getUrlQuery(action,
							SemWebAppConstants.RIGHT_CODE);
					String token = isDirectroy ? this.getSessionUser(request)
							.getToken() : SemAppUtils.getUrlQuery(action,
							SemWebAppConstants.SEM_LOGIN_TOKEN);
					String moduleID = isDirectroy ? ""
							+ this.getCurrentModuleVO(request).getId()
							: SemAppUtils.getUrlQuery(action,
									SemWebAppConstants.SESSION_MODULE_ID);
					logger.debug("root=" + docRoot + ",rightCode=" + rightCode
							+ ",token=" + token);
					PageList pageList = adminRemote.getSubDirectorys(
							SemAppUtils.getInteger(docRoot), rightCode, token,
							moduleID);
					Iterator iter = pageList.getItems().iterator();
					while (iter.hasNext()) {
						TreeControlNode node = (TreeControlNode) iter.next();
						TreeNode vo = this.convertNode(node, false);
						nodeList.add(vo);
					}

				}
				if (root != null)
					nodes = root.findChildren();
			}

			for (int i = 0; nodes != null && i < nodes.length; i++) {
				TreeControlNode node = nodes[i];
				boolean docFlag = SemAppUtils.isNotEmpty(node.getAction())
						&& node.getAction().startsWith(url);
				TreeNode vo = this.convertNode(node, !docFlag);
				nodeList.add(vo);
			}
			PageList pageList = new PageList(nodeList);
			String json = SemWebAppUtils.getJsonFromBean(pageList);
			logger.debug("json=" + json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			logger.error("逻辑层异常:", ee);
		}

	}

	private TreeNode convertNode(TreeControlNode node, boolean isLeaf) {
		TreeNode vo = new TreeNode();
		vo.setId(node.getId());
		vo.setText(node.getName());
		// vo.setHref(node.getAction());
		vo.setAction(node.getAction());
		vo.setLeaf(isLeaf && node.isLeaf());
		vo.setHrefTarget("content");
		return vo;

	}
	

}