package com.xxl.controller.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xxl.facade.AdminRemote;

import common.controller.BaseController;
import common.utils.SemAppUtils;
import common.value.FavoriteVO;
import common.value.PageList;
import common.value.TreeControl;
import common.value.TreeControlNode;
import common.value.TreeNode;
import common.web.utils.SemWebAppUtils;
@Controller
@RequestMapping("/favoriteController")
public class FavoriteController extends BaseController {

	public static Log logger = LogFactory.getLog(FavoriteController.class);

	public static Log dbLogger = SemAppUtils.getDBLog(FavoriteController.class);

	@Autowired
	public AdminRemote adminRemote;
	
	@RequestMapping("/add")
	public void add(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/json;charset=UTF-8");
			String menuID = request.getParameter("menuid");
			String menuName = request.getParameter("menuname");
			logger.debug("add favorite id[" + menuID + "],name[" + menuName
					+ "]");
			FavoriteVO vo = new FavoriteVO(null, menuID, new Integer(1),
					Calendar.getInstance(), this.getSessionUser(request)
							.getEmpIDInt(), this.getSessionModuleID(request),
					null, menuName);

		
			Integer result = adminRemote.addFavorite(vo);
			response.getWriter().write("{success:true,id:" + result + "}");
		} catch (Exception ee) {
			this.handleException(ee, request, response);
		}
	}

	
	protected PageList getDatas(
			HttpServletRequest request, HttpServletResponse response,
			boolean pagable) throws Exception {
		try {
			
			PageList tableList = adminRemote.getFavorites(this
					.getSessionModuleID(request), this.getSessionUser(request)
					.getEmpIDInt(), new Integer(0), new Integer(0));
			return tableList;
		} catch (Exception ee) {
			this.handleException(ee, request, response);
			return null;
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

				adminRemote.deleteFavorite(new Integer(ids[i]));

				this.doDBLogger(dbLogger, request, "11", "删除收藏完成", "id["
						+ ids[i] + "]");
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
	@RequestMapping("/")
	public String defaultMethod(
			HttpServletRequest request, HttpServletResponse response) {
		return "favorite";
	}

	@RequestMapping("/custom")
	public void custom(
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json;charset=UTF-8");
		String rootStr = request.getParameter("root");
		TreeControl control = null;
		HttpSession session = request.getSession();

		try {
			control = (TreeControl) session.getAttribute("menu_control_test");
			List nodeList = new ArrayList();
			if (SemAppUtils.isEmpty(rootStr) || "0".equals(rootStr)) {
				PageList favorites = this.getDatas(request,
						response, false);
				Iterator iter = favorites.getItems().iterator();

				while (iter.hasNext()) {
					FavoriteVO vo = (FavoriteVO) iter.next();
					String nodeID = vo.getMenuID();
					TreeControlNode node = control.findNode(nodeID);
					if (node != null) {
						TreeNode treeNode = new TreeNode();
						treeNode.setId(node.getId());
						treeNode.setText(node.getName());
						treeNode.setHref(node.getAction());
						treeNode.setLeaf(node.isLeaf());
						treeNode.setHrefTarget("content");
						nodeList.add(treeNode);
					}

				}
			} else {
				TreeControlNode[] nodes = control.findNode(rootStr)
						.findChildren();
				for (int i = 0; i < nodes.length; i++) {
					TreeControlNode node = nodes[i];
					TreeNode treeNode = new TreeNode();
					treeNode.setId(node.getId());
					treeNode.setText(node.getName());
					treeNode.setHref(node.getAction());
					treeNode.setLeaf(node.isLeaf());
					treeNode.setHrefTarget("content");
					nodeList.add(treeNode);
				}
			}
			String json = SemWebAppUtils.getListJsonFromList(nodeList);
			logger.debug("json=" + json);
			response.getWriter().write(json);
		} catch (Exception ee) {
			this.handleException(ee, request, response);
		}

	}

}
