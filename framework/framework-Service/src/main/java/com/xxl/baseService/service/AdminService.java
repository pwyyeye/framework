package com.xxl.baseService.service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxl.baseService.bo.Favorite;
import com.xxl.baseService.bo.ItModule;
import com.xxl.baseService.bo.Menu;
import com.xxl.baseService.bo.MenuRole;
import com.xxl.baseService.bo.MessageEvent;
import com.xxl.baseService.bo.MessageSubscibe;
import com.xxl.baseService.bo.Notice;
import com.xxl.baseService.bo.RaBinding;
import com.xxl.baseService.bo.ReportModule;
import com.xxl.baseService.bo.Role;
import com.xxl.baseService.bo.SystemProperties;
import com.xxl.baseService.bo.UABinding;
import com.xxl.baseService.bo.UserLogin;
import com.xxl.baseService.dao.IFrameworkDao;
import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;
import com.xxl.facade.ReportRemote;
import com.xxl.facade.StructureRemote;
import com.xxl.os.bo.SyOrganise;
import common.bussiness.User;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommonException;
import common.os.vo.OrganiseVO;
import common.os.vo.UsersVO;
import common.os.vo.exception.OSBussinessException;
import common.os.vo.exception.OSException;
import common.service.BaseService;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.EventVO;
import common.value.FavoriteVO;
import common.value.ItModuleVO;
import common.value.MenuRoleVO;
import common.value.MenuVO;
import common.value.NoticeVO;
import common.value.ObserverVO;
import common.value.PageList;
import common.value.RaBindingVO;
import common.value.ReportModuleVO;
import common.value.RoleVO;
import common.value.SystemPropertyVO;
import common.value.TreeControl;
import common.value.TreeControlNode;
import common.value.UABindingVO;
import common.value.UserPropertiesVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

@Service("adminRemote")
public class AdminService extends BaseService implements AdminRemote{
	public Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private IFrameworkDao frameworkDAO;
	
//	Session hibernateSession;
	
	@Autowired
	private HelperRemote helperRemote;
	
	@Autowired
	private CommonRemote commonRemote; 
	
	@Autowired
	private StructureRemote structureRemote;
	
	@Autowired
	private ReportRemote reportRemote;
	private final static int IT_SUPER_ADMIN_ID = 1;//表示超级管理，拥有所有系统的所有权限

	private final static int UNALLOW_USER_ID = 2;//表示未授权登录其它系统的用户

	private final static int WEB_USER_ID = 888;
	
	private final static String REPORT_URL_HEAD = "/commonWeb/showReportAction.do?";
	
	public Integer addSystem(ItModuleVO vo) throws BaseException  {
		Integer result = null;
		logger.debug("add it system" + vo);
		try {
			
			ItModule system = new ItModule();
			system.setCreateDate(Calendar.getInstance());
			system.setIndexPage(vo.getIndexPage());
			system.setName(vo.getName());
			system.setParentModule(vo.getParentModule());
			system.setStatus(vo.getStatus());
			system.setSortID(vo.getSortID());
			system.setServiceJndi(vo.getServiceJndi());
			system.setMessageID(vo.getMessageID());
			system.setDeployServer(vo.getDeployServer());
			system.setServiceHome(vo.getServiceHome());
			system.setServiceRemote(vo.getServiceRemote());
			system.setDirID(vo.getDirID());
			frameworkDAO.save(system);
			result = system.getId();
		} catch (Exception ee) {
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		}
		return result;
	}

	public Integer addMenu(MenuVO vo) throws BaseException {
		Integer result = null;
		logger.debug("add a menu" + vo);
		try {
		
			Menu menu = new Menu();
			menu.setFrame(vo.getFrame());
			menu.setLink(vo.getLink());
			ItModule rootModule = (ItModule) frameworkDAO.loadBoById(vo.getModuleID(), ItModule.class);
			menu.setModule(rootModule);
			menu.setName(vo.getName());
			menu.setParent(vo.getParentModule());
			menu.setSortID(vo.getSortID());
			menu.setSingleMode(vo.getSingleMode());
			frameworkDAO.save(menu);
			result = (Integer) menu.getId();
		} catch (Exception ee) {
			throw new BaseException("数据库操作失败" + ee.getMessage());

		} 
		return result;
	}

	public List getSystemList(Integer root) throws BaseException {
		return getSystemList(root, new Boolean(true));
	}

	/**
	 * 
	 * @param root
	 *            根目录ID
	 * @param containRoot
	 *            是否需要包含根目录本身
	 * @param renameModuleID
	 *            是否需要重命名MODULE的ID号，避免与MENU或者其它的ID号重复
	 * @return
	 * @throws CommonException
	 */
	public List getSystemList(Integer root, Boolean containRoot,
			Boolean renameModuleID) throws BaseException {
		List systems = new ArrayList();
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ItModule.class);
			criteria.add(Expression.eq("status", new Integer(0)));
			boolean rename = renameModuleID.booleanValue();
			
			if (root != null) {
				ItModule rootModule = (ItModule) frameworkDAO.loadBoById(
						root,ItModule.class);
				if (containRoot.booleanValue()) {
					ItModuleVO vo = (ItModuleVO) rootModule.toVO();
					if (rename){
						vo.setId(0 - ((Integer) vo.getId()).intValue());
					}
					systems.add(vo);
				}

				criteria.add(Expression.eq("parentModule", root));
			}
			criteria.addOrder(Order.asc("parentModule"));
			criteria.addOrder(Order.asc("sortID"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, 0, 0);
			Iterator iter = pageList.getItems().iterator();
			while (iter.hasNext()) {
				ItModuleVO vo = (ItModuleVO) iter.next();
				if (rename)
					vo.setId(0 - ((Integer) vo.getId()).intValue());
				systems.add(vo);
			}
			return systems;
		} catch (Exception ee) {
			logger.error(ee);
			throw new BaseException("getSystem失败" + ee.getMessage());

		}
	}

	public List getSystemList(Integer root, Boolean containRoot)
			throws BaseException {
		return this.getSystemList(root, containRoot, new Boolean(false));
	}

	public List getMenuList(Integer moduleID, Integer root)
			throws BaseException {
		List menus = new ArrayList();
		try {
			//hibernateSession = HibernateUtil.currentSession();
			//Criteria criteria = hibernateSession.createCriteria(Menu.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
			if (moduleID != null) {
				
				ItModule rootModule = (ItModule)frameworkDAO.loadBoById(
						ItModule.class, moduleID);
				criteria.add(Expression.eq("module", rootModule));
			}
			if (root == null)
				root = new Integer(0);// 0?????????????????????
			criteria.add(Expression.eq("parent", root));
			criteria.addOrder(Order.asc("sortID"));
			List list=frameworkDAO.findByCriteria(criteria, 0, 0);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Menu menu = (Menu) iter.next();
				MenuVO vo = (MenuVO) menu.toVO();
				vo.setLeaf(false);
				menus.add(vo);
			}
			return menus;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库异常" + ee.getMessage());

		} finally {
			try {
				
			} catch (HibernateException e) {
			}
		}
	}

	public List getSystemList() throws BaseException {
		return getSystemList(null);
	}

	public ItModuleVO getSystemByID(Integer systemID) throws BaseException {
		ItModuleVO moduleVO = null;
		try {
			//hibernateSession = HibernateUtil.currentSession();
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					systemID);
			moduleVO = (ItModuleVO) module.toVO();
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库异常", ee);

		} finally {
			try {
				
			} catch (HibernateException e) {
			}
		}
		return moduleVO;
	}

	public MenuVO getMenuByID(Integer MenuID) throws BaseException {
		MenuVO menuVO = null;
		try {
			//hibernateSession = HibernateUtil.currentSession();
			Menu menu = (Menu)frameworkDAO.loadBoById(Menu.class, MenuID);
			menuVO = (MenuVO) menu.toVO();
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库异常" + ee.getMessage());
		} 
		return menuVO;
	}

	public void updateSystem(ItModuleVO vo) throws BaseException {
		logger.debug("update it system" + vo);
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			
			ItModule system = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getId());
			system.setIndexPage(vo.getIndexPage());
			system.setName(vo.getName());
			system.setStatus(vo.getStatus());
			system.setSortID(vo.getSortID());
			system.setServiceJndi(vo.getServiceJndi());
			system.setMessageID(vo.getMessageID());
			system.setDeployServer(vo.getDeployServer());
			system.setServiceHome(vo.getServiceHome());
			system.setServiceRemote(vo.getServiceRemote());
			system.setDirID(vo.getDirID());
			frameworkDAO.saveOrUpdate(system);
		} catch (HibernateException ee) {
			logger.error("数据库操作失败", ee);
			throw new BaseException("数据库操作失败:" + ee.getMessage());

		}
	}

	public void updateMenu(MenuVO vo) throws BaseException {
		logger.debug("update it system" + vo);
		try {
			//hibernateSession = HibernateUtil.currentSession();
			Menu menu = (Menu)frameworkDAO.loadBoById(Menu.class, vo.getId());
			menu.setFrame(vo.getFrame());
			menu.setLink(vo.getLink());
			menu.setName(vo.getName());
			menu.setSingleMode(vo.getSingleMode());
			menu.setSortID(vo.getSortID());
			frameworkDAO.save(menu);
		} catch (HibernateException ee) {
			throw new BaseException("数据库操作失败:" + ee.getMessage());

		}
	}

	public void deleteMenu(Integer menuID) throws BaseException {
		try {
			Menu menu = (Menu)frameworkDAO.loadBoById(Menu.class, menuID);
			frameworkDAO.delete(menu);

		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());
		} 
	}

	public void updateSystemStatus(Integer systemID, Integer status)
			throws BaseException {
		logger.debug("update it system status" + systemID);
		try {

			ItModule system = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					systemID);
			system.setStatus(status.intValue());
			frameworkDAO.saveOrUpdate(system);
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());
		}
	}

	public TreeControl getSystemTree(String rootStr) throws BaseException {
		TreeControl treeControl = null;
		TreeControlNode treeRoot = new TreeControlNode("0", "SEM系统ϵͳ", null,
				true);
		treeControl = new TreeControl(treeRoot);
		createNode(treeRoot, rootStr, false, null, false);
		return treeControl;
	}

	private boolean isSuperAdmin(SessionUserBean theUser) {
		int currentRoleID = ((Integer) theUser.getRole().getId()).intValue();
		return currentRoleID == IT_SUPER_ADMIN_ID;
	}

	public TreeControl getMenuTree(String systemID, SessionUserBean theUser)
			throws  BaseException, RemoteException {
		TreeControl treeControl = null;
		try {
			//hibernateSession = HibernateUtil.currentSession();
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					new Integer(systemID));
			TreeControlNode treeRoot = new TreeControlNode("Module"
					+ module.getId(), module.getName(), module.toVO(), true);
			treeControl = new TreeControl(treeRoot);

			createNode(treeRoot, systemID, true, theUser, isSuperAdmin(theUser));
			createMenuNode(treeRoot, 0, module.getId().toString(), theUser,
					isSuperAdmin(theUser), null);

			// createReport(treeRoot, module.getId(), theUser);
			TreeControlNode reportNode = new TreeControlNode("ModuleReport"
					+ module.getId(), "系统报表", null, null, false, null,
					"content");
			treeRoot.addChild(reportNode);
			createReportNode(reportNode, 0, module.getId().toString(), theUser,
					isSuperAdmin(theUser), null);
			// create common menu
			String commonRootStr=helperRemote.getProperty("COMMON_ROOT_ID");
			int commonRootInt;
			try{
				commonRootInt=Integer.parseInt(commonRootStr);
			}catch(Exception ee){
				commonRootInt=888;
			}
			createMenuNode(treeRoot,commonRootInt,"0",theUser,true,null);
			clearNode(treeRoot);

			logger.debug("menu tree create finish");
		} catch (HibernateException ee) {
			logger.error("数据库操作失败", ee);
		} catch(RemoteException ee){
			logger.error("数据库操作失败", ee);
		}
		return treeControl;
	}

	private void clearNode(TreeControlNode node) {
		TreeControlNode[] children = node.findChildren();
		for (int i = 0; i < children.length; i++) {
			clearNode(children[i]);
		}
		if (node.getAction() == null && node.isLeaf()
				&& node.getFrame() == null) {
			node.remove();
		}
	}

	private void createReport(TreeControlNode treeRoot, Integer moduleID,
			SessionUserBean theUser) throws BaseException {
		TreeControlNode reportNode = new TreeControlNode("ModuleReport"
				+ moduleID, "系统报表", null, null, false, null, "content");
		treeRoot.addChild(reportNode);
		try {
			List list = reportRemote.getReportModuleList(moduleID).getItems();
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				ReportModuleVO vo = (ReportModuleVO) iter.next();
				StringBuffer sb = new StringBuffer();
				sb.append(REPORT_URL_HEAD);
				sb.append(SemWebAppConstants.REQUEST_REPORT_ID);
				sb.append("=");
				sb.append(vo.getId());
				sb.append("&");
				sb.append(SemWebAppConstants.RIGHT_CODE);
				sb.append("=");
				sb.append(vo.getModule());
				sb.append("&");
				sb.append(SemWebAppConstants.SEM_LOGIN_TOKEN);
				sb.append("=");
				sb.append(theUser.getToken());
				TreeControlNode childNode = new TreeControlNode("Report"
						+ vo.getId().toString(), vo.getName(), new String(sb),
						vo, false, null, "content");
				reportNode.addChild(childNode);
			}
		} catch (RemoteException e) {
			logger.error("调用REPORT ADMIN对象失败", e);
		}

	}

	private void createNode(TreeControlNode node, String parentID,
			boolean containMenu, SessionUserBean theUser, boolean allow) {
		try {
			//hibernateSession = HibernateUtil.currentSession();
			//Criteria criteria = hibernateSession.createCriteria(ItModule.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(ItModule.class);
			criteria.add(Expression.eq("parentModule", new Integer(parentID)));
			criteria.add(Expression.eq("status", new Integer(0)));// ???????????????????????????????????????
			criteria.addOrder(Order.asc("sortID"));
			List childList =frameworkDAO.findByCriteria(criteria, 0, 0);
			Iterator iterator = childList.iterator();
			while (iterator.hasNext()) {
				ItModule module = (ItModule) iterator.next();
				String moduleStr = module.getId().toString();
				TreeControlNode childNode = new TreeControlNode(
						containMenu ? "-" + moduleStr : moduleStr, module
								.getName(), module.toVO(), false);
				try {
					node.addChild(childNode);
				} catch (Exception ee) {
				}
				if (containMenu) {// 如果需要包含功能菜单
					createMenuNode(childNode, 0, module.getId().toString(),
							theUser, allow, null, false);
				}
				createNode(childNode, module.getId().toString(), containMenu,
						theUser, allow);
			}

		} catch (HibernateException ee) {
			logger.error("数据库操作失败", ee);
		} finally {
			try {
				
			} catch (HibernateException e) {
			}
		}
	}

	private void createMenuNode(TreeControlNode node, int MenuParent,
			String parentID, SessionUserBean theUser, boolean allow,
			String rightCode) {
		createMenuNode(node, MenuParent, parentID, theUser, allow, rightCode,
				true);
	}

	private void createMenuNode(TreeControlNode node, int MenuParent,
			String parentID, SessionUserBean theUser, boolean allow,
			String rightCode, boolean containAdminModule) {
		try {
			//hibernateSession = HibernateUtil.currentSession();
			//Criteria criteria = hibernateSession.createCriteria(Menu.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					new Integer(parentID));
			if (containAdminModule) {
				ItModule managerModule = (ItModule)frameworkDAO.loadBoById(
						ItModule.class, new Integer(
								SemAppConstants.COMMON_MODULE_ID));
				criteria.add(Expression.or(Expression.eq("module", module),
						Expression.eq("module", managerModule)));
			} else {
				criteria.add(Expression.eq("module", module));
			}
			criteria.add(Expression.eq("parent", new Integer(MenuParent)));// ???
			criteria.addOrder(Order.asc("sortID"));
			List childList = frameworkDAO.findByCriteria(criteria, 0, 0);
			Iterator iterator = childList.iterator();
			boolean leaf = true;
			while (iterator.hasNext()) {
				leaf = false;
				Menu menu = (Menu) iterator.next();
				boolean subAllow = allow;
				String link = menu.getLink();
				if (!allow) {//  如果还未授权访问
					//hibernateSession = HibernateUtil.currentSession();
					DetachedCriteria criteria2 = DetachedCriteria.forClass(MenuRole.class);
//					Criteria criteria2 = hibernateSession
//							.createCriteria(MenuRole.class);
					RoleVO vo = theUser.getRole();
					Role currentRole = (Role)frameworkDAO.loadBoById(Role.class,
							vo.getId());
					criteria2.add(Expression.eq("role", currentRole));
					criteria2.add(Expression.eq("menu", menu));
					List list2=frameworkDAO.findByCriteria(criteria2, 0, 0);
					Iterator iter =list2.iterator();
					while (iter.hasNext()) {
						MenuRole mr = (MenuRole) iter.next();

						rightCode = mr.getRightCode();

						subAllow = true;
					}
				} else {
					//hibernateSession = HibernateUtil.currentSession();
//					Criteria criteria2 = hibernateSession
//							.createCriteria(MenuRole.class);
					DetachedCriteria criteria2 = DetachedCriteria.forClass(MenuRole.class);
					RoleVO vo = theUser.getRole();
					Role currentRole = (Role)frameworkDAO.loadBoById(Role.class,
							vo.getId());
					criteria2.add(Expression.eq("role", currentRole));
					criteria2.add(Expression.eq("menu", menu));
					List list2=frameworkDAO.findByCriteria(criteria2, 0, 0);
					Iterator iter = list2.iterator();
					while (iter.hasNext()) {
						MenuRole mr = (MenuRole) iter.next();
						String subRightCode = mr.getRightCode();
						if (SemWebAppUtils.isNotEmpty(subRightCode)) {
							rightCode = subRightCode;
						}
					}

				}
				if (link != null) {
					String split = link.indexOf("?") != -1 ? "&" : "?";
					StringBuffer sb = new StringBuffer();
					sb.append(link);
					sb.append(split);
					sb.append(SemWebAppConstants.RIGHT_CODE);
					sb.append("=");
					sb.append(rightCode);
					sb.append("&");
					sb.append(SemWebAppConstants.SEM_LOGIN_TOKEN);
					sb.append("=");
					sb.append(theUser.getToken());
					sb.append("&");
					sb.append(SemWebAppConstants.SESSION_MODULE_ID);
					sb.append("=");
					sb.append(parentID);
					link = new String(sb);
				}
				// logger.debug(menu.getName() + "->" + linek);
				TreeControlNode childNode = new TreeControlNode(menu.getId()
						.toString(), menu.getName(), subAllow ? link : null,
						subAllow ? menu.getFrame() : null, rightCode, null,
						false, null, "content", menu.getSingleMode() == 1);
				try {
					node.addChild(childNode);
					createMenuNode(childNode, ((Integer) menu.getId())
							.intValue(), parentID, theUser, subAllow, rightCode);
				} catch (Exception ee) {
				}
			}
			if (leaf && node.getFrame() == null && node.getAction() == null) {
				node.remove();
			}

		} catch (HibernateException ee) {
			logger.error("数据库操作失败", ee);
		} finally {
			try {
				
			} catch (HibernateException e) {
			}
		}
	}

	private void createReportNode(TreeControlNode node, int MenuParent,
			String parentID, SessionUserBean theUser, boolean allow,
			String rightCode) {
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			Criteria criteria = hibernateSession
//					.createCriteria(ReportModule.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(ReportModule.class);
			if (!isSystemModule(Integer.parseInt(parentID))) {
				ItModule module = (ItModule)frameworkDAO.loadBoById(
						ItModule.class, new Integer(parentID));
				criteria.add(Expression.eq("module", module));
			}
			criteria.add(Expression.eq("parent", new Integer(MenuParent)));// ???
			criteria.addOrder(Order.asc("sortID"));
			List childList =frameworkDAO.findByCriteria(criteria, 0, 0);
			Iterator iterator = childList.iterator();
			while (iterator.hasNext()) {
				ReportModule report = (ReportModule) iterator.next();
				if (report.getValid().intValue() == 0) {// ??????????????????
					boolean subAllow = allow;
					String link = null;
					String raq = report.getReport();
					if (!allow) {// ????????????????????????
						//hibernateSession = HibernateUtil.currentSession();
//						Criteria criteria2 = hibernateSession
//								.createCriteria(RaBinding.class);
						DetachedCriteria criteria2 = DetachedCriteria.forClass(RaBinding.class);
						RoleVO vo = theUser.getRole();
						Role currentRole = (Role)frameworkDAO.loadBoById(
								Role.class, vo.getId());
						criteria2.add(Expression.eq("role", currentRole));
						criteria2.add(Expression.eq("report", report));
						List list2=frameworkDAO.findByCriteria(criteria2, 0, 0);
						Iterator iter =list2.iterator();
						while (iter.hasNext()) {
							RaBinding mr = (RaBinding) iter.next();

							rightCode = mr.getRightCode();

							subAllow = true;
						}
					} else {
						//hibernateSession = HibernateUtil.currentSession();
//						Criteria criteria2 = hibernateSession
//								.createCriteria(RaBinding.class);
						DetachedCriteria criteria2 = DetachedCriteria.forClass(RaBinding.class);
						RoleVO vo = theUser.getRole();
						Role currentRole = (Role)frameworkDAO.loadBoById(
								Role.class, vo.getId());
						criteria2.add(Expression.eq("role", currentRole));
						criteria2.add(Expression.eq("report", report));
						List list2=frameworkDAO.findByCriteria(criteria2, 0, 0);
						Iterator iter = list2.iterator();
						while (iter.hasNext()) {
							RaBinding mr = (RaBinding) iter.next();
							String subRightCode = mr.getRightCode();
							if (SemWebAppUtils.isNotEmpty(subRightCode)) {
								rightCode = subRightCode;
							}
						}

					}
					if (subAllow && raq != null) {
						StringBuffer sb = new StringBuffer();
						sb.append(REPORT_URL_HEAD);
						sb.append(SemWebAppConstants.REQUEST_REPORT_ID);
						sb.append("=");
						sb.append(report.getId());
						sb.append("&");
						sb.append(SemWebAppConstants.RIGHT_CODE);
						sb.append("=");
						sb.append(rightCode);
						sb.append("&");
						sb.append(SemWebAppConstants.SEM_LOGIN_TOKEN);
						sb.append("=");
						sb.append(theUser.getToken());
						link = new String(sb);
					}
					TreeControlNode childNode = new TreeControlNode("Report"
							+ report.getId().toString(), report.getName(),
							link, null, false, null, "content");
					try {
						// logger.debug("name="+report.getName()+",link"+link);
						node.addChild(childNode);
						createReportNode(childNode, ((Integer) report.getId())
								.intValue(), parentID, theUser, subAllow,
								rightCode);
					} catch (Exception ee) {
						logger.error("create report id " + report.getId(), ee);
					}
				}

			}

		} catch (HibernateException ee) {
			logger.error("数据库操作失败", ee);
		} 
	}

	public Integer addMessageSubscibe(ObserverVO vo) throws BaseException {
		Integer result = null;
//		
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			
			MessageSubscibe subscibe = new MessageSubscibe();
			subscibe.setEmpID(vo.getEmpID());
			MessageEvent event = (MessageEvent)frameworkDAO.loadBoById(
					MessageEvent.class, vo.getEventID());
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getModuleID());
			subscibe.setEvent(event);
			subscibe.setRoute(vo.getRoute());
			subscibe.setBeginDate(vo.getBeginDate());
			subscibe.setEndDate(vo.getEndDate());
			subscibe.setModule(module);
			frameworkDAO.saveOrUpdate(subscibe);
			result = (Integer) subscibe.getId();
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库操作错误" + ee.getMessage());
		}
		return result;
	}

	public void updateMessageSubscibe(ObserverVO vo) throws BaseException {
		logger.debug("update  role" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			MessageSubscibe msBO = (MessageSubscibe)frameworkDAO.loadBoById(
					MessageSubscibe.class, vo.getId());
			msBO.setEmpID(vo.getEmpID());
			msBO.setRoute(vo.getRoute());
			msBO.setBeginDate(vo.getBeginDate());
			msBO.setEndDate(vo.getEndDate());
			frameworkDAO.saveOrUpdate(msBO);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("�޸�ITϵͳ数据库操作失败" + ee.getMessage());

		} 
	}

	public void deleteMessageSubscibe(Integer messageSubscibeID)
			throws BaseException {
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			MessageSubscibe subscibe = (MessageSubscibe)frameworkDAO.loadBoById(
					MessageSubscibe.class, messageSubscibeID);
			frameworkDAO.delete(subscibe);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("数据库操作错误" + ee.getMessage());
		} 
	}

	public PageList getMessageSubscibeList(Integer systemID,
			ObserverVO searchVO, Integer firstResult, Integer fetchSize)
			throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			Criteria criteria = hibernateSession
//					.createCriteria(MessageSubscibe.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(MessageSubscibe.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;//系统管理模块可以管理所有模块的权限
			}
			if (searchVO != null) {

				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();

				}

				if (searchVO.getEmpID() != null) {
					criteria.add(Expression.eq("empID", searchVO.getEmpID()));
				}
				if (searchVO.getEventID() != null) {
					MessageEvent event = (MessageEvent)frameworkDAO.loadBoById(
							MessageEvent.class, searchVO.getEventID());
					criteria.add(Expression.eq("event", event));
				}
				if (searchVO.getRoute() != 0) {
					criteria.add(Expression.eq("route", new Integer(searchVO
							.getRoute())));
				}
			}

			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));
			}

//			Integer rowCount = (Integer) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("event"));
//			List subscibeList = new ArrayList();
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//			Iterator iter = list.iterator();
//			while (iter.hasNext()) {
//				MessageSubscibe po = (MessageSubscibe) iter.next();
//				ObserverVO vo = (ObserverVO) po.toVO();
//				logger.debug("vo->" + vo);
//				subscibeList.add(vo);
//			}
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(subscibeList);
			return pageList;
		} catch (HibernateException ee) {
			throw new BaseException("数据库操作异常", ee);
		}
	}

	public PageList getMessageSubscibeList() throws BaseException {
		PageList subscibes = getMessageSubscibeList(null, null, new Integer(0),
				new Integer(0));
		return subscibes;
	}

	public PageList getMessageSubscibeList(Integer systemID)
			throws BaseException {
		PageList subscibes = getMessageSubscibeList(systemID, null,
				new Integer(0), new Integer(0));
		return subscibes;
	}

	public PageList getEventList(Integer systemID, EventVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			Criteria criteria = hibernateSession
//					.createCriteria(MessageEvent.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(MessageEvent.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// ???????????????????????????????????????????????????
			}
			if (searchVO != null) {

				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}

				if (searchVO.getName() != null) {
					criteria.add(Expression.like("name", "%"
							+ searchVO.getName() + "%"));
				}
				if (searchVO.getType() != 0) {
					criteria.add(Expression.eq("type", new Integer(searchVO
							.getType())));
				}
			}

			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}

//			Integer rowCount = (Integer) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//			List eventList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				MessageEvent event = (MessageEvent) iter.next();
//				EventVO roleVO = (EventVO) event.toVO();
//				eventList.add(roleVO);
//			}
//
//			logger.debug("rowCount=" + rowCount);
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(eventList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error("数据库查询失败", ee);
			throw new BaseException(ee.getMessage());

		} 
	}

	public PageList getEventList(Integer systemID) throws BaseException {
		return getEventList(systemID, null, new Integer(0), new Integer(0));
	}

	public PageList getEventList() throws BaseException {
		return getEventList(new Integer(0));
	}

	public void updateEvent(EventVO vo) throws BaseException {
		logger.debug("update  role" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			MessageEvent eventBO = (MessageEvent)frameworkDAO.loadBoById(
					MessageEvent.class, vo.getId());
			eventBO.setName(vo.getName());
			eventBO.setType(vo.getType());
			eventBO.setStatus(vo.getStatus());
			frameworkDAO.saveOrUpdate(eventBO);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		} 
	}

	public void deleteEvent(Integer eventID) throws BaseException {
		
		try {

			//hibernateSession = HibernateUtil.currentSession();
			MessageEvent event = (MessageEvent)frameworkDAO.loadBoById(
					MessageEvent.class, eventID);
			
			frameworkDAO.delete(event);
			
		} catch (HibernateException ee) {

			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		}
	}

	public Integer addEvent(EventVO vo) throws BaseException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			MessageEvent event = new MessageEvent();
			event.setId((Integer) vo.getId());
			event.setName(vo.getName());
			event.setType(vo.getType());
			event.setStatus(vo.getStatus());
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getModuleID());
			event.setModule(module);
			frameworkDAO.saveOrUpdate(event);
			
			result = (Integer) event.getId();
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		}
		return result;
	}

	public SessionUserBean getSessionUserBean(UsersVO theUser,
			Integer systemID, String ip, String token) throws BaseException {
		return getSessionUserBean(theUser, systemID, ip, null, token);
	}

	public SessionUserBean getSessionUserBean(User user, Integer systemID,
			String ip, Integer roleID, String token) throws BaseException {
		return getSessionUserBean(SemAppUtils.user2VO(user), systemID, ip,
				null, token);
	}

	private boolean isSystemModule(int systemID) {
		return systemID == 0 || systemID == SemAppConstants.COMMON_MODULE_ID;
	}

	public SessionUserBean getSessionUserBean(UsersVO theUser,
			Integer systemID, String ip, Integer roleID, String token)
			throws BaseException {
		if (theUser == null)
			throw new BaseException("参数不能为空");
		logger.debug("getSeesionUserBeam arg is systemID=" + systemID
				+ ",roleID=" + roleID + ",token=" + token + ",userCode="
				+ theUser.getCode() + ",unitid=" + theUser.getDepartment());
		System.out.println("getSeesionUserBeam arg is systemID=" + systemID
				+ ",roleID=" + roleID + ",token=" + token + ",userCode="
				+ theUser.getCode() + ",unitid=" + theUser.getDepartment());
		SessionUserBean currentUser = null;
		RoleVO roleVO = null;
		// Integer lastRoleID = null;
		UserLogin login = null;
//		
		Iterator iter;
		try {
//			//hibernateSession = HibernateUtil.currentSession();
//			
//			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
//					systemID);
			ItModule module = (ItModule) frameworkDAO.loadBoById(systemID,ItModule.class
					);
			if (roleID == null) {
//				Criteria criteria = hibernateSession
//						.createCriteria(UserLogin.class);
				// if (!isSystemModule(systemID.intValue()))
				DetachedCriteria criteria = DetachedCriteria.forClass(UserLogin.class);
				criteria.add(Expression.eq("module", module));
				criteria.add(Expression.eq("empID", ""+theUser.getId()));
				criteria.addOrder(Order.desc("lastLoginDate"));
//				iter = criteria.list().iterator();
				iter=frameworkDAO.findByCriteria(criteria, 0, 0).iterator();
				if (iter.hasNext()) {
					login = (UserLogin) iter.next();
					// if (isSystemModule(systemID.intValue())) {
					// module = login.getModule();// ?????????????????????0?????????
					// }
					// lastRoleID = login.getLastRole().getId();
					// if (roleID == null)
					// roleID = lastRoleID;
					roleID = (Integer) login.getLastRole().getId();
					logger.debug("lase login role is" + roleID);
				}
			}
			boolean hasFoundRole = false;
			boolean last = (roleID != null && roleID.intValue() == UNALLOW_USER_ID);
			;
			while (!hasFoundRole && !last) {
				logger.debug("check user right,module=" + module + ",empID="
						+ theUser.getCode() + ",deptID="
						+ theUser.getDepartment() + "levelID="
						+ theUser.getLevel());
//				Criteria criteria2 = hibernateSession
//						.createCriteria(UABinding.class);
				DetachedCriteria criteria2 = DetachedCriteria.forClass(UABinding.class);
				if (!isSystemModule(module.getId().intValue()))
					criteria2.add(Expression.eq("module", module));
				criteria2.add(Expression.or(Expression.or(Expression.eq(
						"empID", theUser.getId()), Expression.eq("deptID",
						theUser.getDepartment())), Expression.eq("levelID",
						theUser.getLevel())));
				if (roleID != null) {
//					Role role = (Role) hibernateSession
//							.load(Role.class, roleID);
					Role role = (Role) frameworkDAO.loadBoById(roleID,Role.class
							);
					criteria2.add(Expression.eq("role", role));
				} else {
					last = true;
				}
//				iter = criteria2.list().iterator();
				iter=frameworkDAO.findByCriteria(criteria2, 0, 0).iterator();
				if (iter.hasNext()) {
					hasFoundRole = true;
					UABinding ua = (UABinding) iter.next();
					Role role = ua.getRole();
					roleVO = (RoleVO) role.toVO();
					logger.debug("create new role=" + roleVO.getId());
					if (login != null) {
						login.setLastRole(role);
						login.setModule(module);
						login.setLastLoginDate(Calendar.getInstance());
//						frameworkDAO.saveOrUpdate(login);
						frameworkDAO.saveOrUpdate(login);
					} else {
						login = new UserLogin();
						login.setEmpID(""+theUser.getId());
						login.setModule(module);
						login.setLastRole(role);
						login.setLastLoginDate(Calendar.getInstance());
//						frameworkDAO.saveOrUpdate(login);
						frameworkDAO.saveOrUpdate(login);
					}
					// logon OA system
					commonRemote.logonOASystem((Integer) theUser.getId(), ip);
					// logon
//					
				} else {
					if (roleID != null) {
						// if (lastRoleID == null
						// || roleID.intValue() == lastRoleID.intValue()) {
						roleID = null;
						// } else {
						// roleID = lastRoleID;
						// }
					}
				}
			}
			if (!hasFoundRole) {
				logger.debug("has not found role,enter the unallow role");
//				Role unallowRole = (Role)frameworkDAO.loadBoById(Role.class,
//						new Integer(UNALLOW_USER_ID));
				
				Role unallowRole = (Role) frameworkDAO.loadBoById(new Integer(UNALLOW_USER_ID),Role.class
						);
				roleVO = (RoleVO) unallowRole.toVO();
				module = unallowRole.getModule();
				// to unallow User
				// throw new BaseException("??????????????????[" + theUser.getCode()
				// + "],roleID=[" + roleID + "],systemID=["
				// + systemID.intValue() + "]");
			}

			if (roleVO != null) {
				currentUser = new SessionUserBean(roleVO, (Integer) theUser
						.getId(), theUser.getName(), theUser
						.getDepartmentCode(), theUser.getDepartmentName(), "",
						ip, token == null ? commonRemote
								.getUserToken((Integer) theUser.getId())
								: token, (ItModuleVO) module.toVO(), this
								.getUserProperties((Integer) theUser.getId()));
				Integer organiseID=roleVO.getOrganise();
				OrganiseVO organise=structureRemote.getOrganise(organiseID);
				//????????????
				String image=organise.getRegion();
				//if(!image.startsWith("http")){
				
				String fullImage=helperRemote.getProperty("FILESERVER_URL")+image;
				logger.debug("fullImage="+fullImage);
				organise.setRegion(fullImage);
				//}
				//currentUser.setOrganise(roleVO.getOrganise());
				currentUser.setOrganise(organise);
//				currentUser.setCommonUsersVO(theUser);
				UsersVO uservo=frameworkDAO.getUserInfo(theUser.getId()+"");
				currentUser.setCommonUsersVO(uservo);

				currentUser.setCommonUser(SemAppUtils.vo2User(uservo));
				logger.debug("update online user info" + theUser.getId()
						+ ",role name" + roleVO.getName()+"organise Name="+organise.getName());
				helperRemote.login((Integer) theUser.getId(), WEB_USER_ID);
				helperRemote.switchRole((Integer) theUser.getId(), WEB_USER_ID,
						module.getName(), roleVO.getName());
				helperRemote.onMethod((Integer) theUser.getId(), WEB_USER_ID,
						"工作台");
				helperRemote.cleanSignleModeByKey((Integer) theUser.getId(),
						this.WEB_USER_ID);
			}

		} catch (Exception ee) {
			logger.error("数据库操作失败", ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());
		}
		return currentUser;

	}

	public PageList getRoleList(Integer systemID) throws BaseException {
		return getRoleList(systemID, new Integer(0), new Integer(0));
	}

	public PageList getRoleList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws BaseException {
		return getRoleList(systemID, null, firstResult, fetchSize);
	}

	public PageList getRoleList(Integer systemID, RoleVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
			//Criteria criteria = hibernateSession.createCriteria(Role.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// 系统管理模块可以管理所有模块的权限
			}
			
			if (searchVO != null) {
				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if(searchVO.getOrganise()!=null&&searchVO.getOrganise().intValue()!=0){
					//SyOrganise organise=new SyOrganise(searchVO.getOrganise());
					//criteria.add(Expression.eq("organise",organise));
					List organiseList=commonRemote.getSubOrganises(searchVO.getOrganise().intValue());
					criteria.add(Expression.in("organise",organiseList));
				}
				if (searchVO.getName() != null) {
					criteria.add(Expression.like("rolename", "%"
							+ searchVO.getName() + "%"));
				}
				if (searchVO.getDescription() != null) {
					criteria.add(Expression.like("description", "%"
							+ searchVO.getDescription() + "%"));
				}
			}
			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}

//			Long rowCount = (Long) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//			List roleList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				Role role = (Role) iter.next();
//				RoleVO roleVO = (RoleVO) role.toVO();
//				roleList.add(roleVO);
//			}
//
//			logger.debug("rowCount=" + rowCount);
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(roleList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		}catch (Exception ee) {
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		} finally {
			try {
				
			} catch (HibernateException e) {
			}
		}
	}

	public RoleVO getRoleByID(Integer roleID) throws BaseException {
		RoleVO roleVO = null;
		try {
			//hibernateSession = HibernateUtil.currentSession();
			Role role = (Role)frameworkDAO.loadBoById(Role.class, roleID);
			roleVO = (RoleVO) role.toVO();
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("getRoleByID???" + ee.getMessage());

		} 
		return roleVO;
	}

	public PageList getUserRoleList(Integer systemID, User user)
			throws BaseException {
		return getUserRoleList(systemID, SemAppUtils.user2VO(user));
	}

	public PageList getUserRoleList(Integer systemID, UsersVO user)
			throws BaseException {
		if (user == null || systemID == null)
			throw new BaseException("调用参数有误");
		//hibernateSession = HibernateUtil.currentSession();
		//Criteria criteria = hibernateSession.createCriteria(UABinding.class);
		// if (systemID.intValue() != 0) {
		// ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
		// systemID);
		// criteria.add(Expression.eq("module", module));
		// }
		
		DetachedCriteria criteria = DetachedCriteria.forClass(UABinding.class);
		criteria.add(Expression.or(Expression.or(Expression.eq("empID",
				(Integer) user.getId()), Expression.eq("deptID", user
				.getDepartment())), Expression.eq("levelID", user.getLevel())));
		criteria.addOrder(Order.asc("module"));
		List resultList = new ArrayList();
//		Long rowCount = (Long) criteria.setProjection(
//				Projections.rowCount()).uniqueResult();
//		criteria.setProjection(null);
		List list=frameworkDAO.findByCriteria(criteria, 0, 0);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			UABinding ub = (UABinding) iter.next();
			resultList.add(ub.getRole().toVO());
		}
		Role role = (Role)frameworkDAO.loadBoById(Role.class, new Integer(
				UNALLOW_USER_ID));
		resultList.add(role.toVO());
		PageList pageList = new PageList();
		pageList.setResults(resultList.size());
		pageList.setItems(resultList);
		return pageList;
	}

	public PageList getUABindingList(Integer systemID, UABindingVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			Criteria criteria = hibernateSession
//					.createCriteria(UABinding.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(UABinding.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;//系统管理模块可以管理所有模块的权限
			}

			if (searchVO != null) {
				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if(searchVO.getOrganise()!=null&&searchVO.getOrganise().intValue()!=0){
					SyOrganise organise=new SyOrganise(searchVO.getOrganise());
					criteria.add(Expression.eq("organise",organise));
				}
				if (searchVO.getRoleID() != null) {
					Role role = (Role)frameworkDAO.loadBoById(Role.class,
							searchVO.getRoleID());
					criteria.add(Expression.eq("role", role));
				}
				if (searchVO.getEmpID() != null) {
					criteria.add(Expression.eq("empID", searchVO.getEmpID()));
				}
				if (searchVO.getDeptID() != null) {
					criteria.add(Expression.like("deptID", "%"
							+ searchVO.getDeptID() + "%"));
				}
				if (searchVO.getLevelID() != null) {
					criteria.add(Expression.eq("levelID", searchVO.getLevelID()));
				}
			}
			if (system != 0) {
			//	List modules = SemAppUtils.getSubmodules(system,
			//			hibernateSession);
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
			criteria.addOrder(Order.desc("module"));
//			Long rowCount = (Long) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);

	
			if (system != 0) {
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}

//			List uaList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				UABinding po = (UABinding) iter.next();
//				uaList.add(po.toVO());
//			}
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(uaList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("getUABindingList??????" + ee.getMessage());

		} 
	}

	public PageList getUABindingList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws BaseException {
		return getUABindingList(systemID, null, firstResult, fetchSize);
	}

	public PageList getUABindingList(Integer systemID) throws BaseException {
		return getUABindingList(systemID, new Integer(0), new Integer(0));
	}

	public PageList getUABindingList() throws BaseException {
		return getUABindingList(new Integer(0), new Integer(0), new Integer(0));
	}

	public Integer addUABinding(UABindingVO ubVO) throws BaseException {
		Integer result = null;
		logger.debug("add it UABinding" + ubVO);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			UABinding ub = new UABinding();
			ub.setId((Integer) ubVO.getId());
			ub.setEmpID(ubVO.getEmpID());
			ub.setDeptID(ubVO.getDeptID());
			ub.setLevelID(ubVO.getLevelID());
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					ubVO.getModuleID());
			ub.setModule(module);
		
			Role role = (Role)frameworkDAO.loadBoById(Role.class, ubVO
					.getRoleID());
			ub.setRole(role);
			SyOrganise organise =role.getOrganise();
			//	(SyOrganise)frameworkDAO.loadBoById(SyOrganise.class,ubVO.getOrganise());
			ub.setOrganise(organise);
			frameworkDAO.saveOrUpdate(ub);
			
			result = (Integer) ub.getId();
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		}
		return result;

	}

	public void updateUABinding(UABindingVO uaBinding) throws BaseException {
		logger.debug("update it system" + uaBinding);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			UABinding uab = (UABinding)frameworkDAO.loadBoById(UABinding.class,
					uaBinding.getId());
			uab.setEmpID(uaBinding.getEmpID());
			uab.setLevelID(uaBinding.getLevelID());
			Role role = (Role)frameworkDAO.loadBoById(Role.class, uaBinding
					.getRoleID());
			uab.setRole(role);
			frameworkDAO.saveOrUpdate(uab);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("IT数据库操作失败" + ee.getMessage());

		}
	}

	public void deleteUABinding(Integer bindingID) throws BaseException {
		
		try {
			
			//hibernateSession = HibernateUtil.currentSession();
			UABinding uaBinding = (UABinding)frameworkDAO.loadBoById(
					UABinding.class, bindingID);
			
			frameworkDAO.delete(uaBinding);
			
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		} 
	}

	public void deleteRole(Integer roleID) throws BaseException {
		
		try {

			//hibernateSession = HibernateUtil.currentSession();
			Role role = (Role)frameworkDAO.loadBoById(Role.class, roleID);
			
			frameworkDAO.delete(role);
			
		} catch (HibernateException ee) {

			logger.error("delete role fail",ee);
			throw new BaseException("删除角色失败" + ee.getMessage());

		} 
	}

	public Integer addRole(RoleVO roleVO) throws BaseException {
		Integer result = null;
		logger.debug("add it UABinding" + roleVO);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			Role role = new Role();
			role.setId((Integer) roleVO.getId());
			role.setRolename(roleVO.getName());
			role.setDescription(roleVO.getDescription());
			role.setValid(roleVO.getValid());
			//role.setOrganise(organise)
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					roleVO.getModuleID());
			role.setModule(module);
			SyOrganise organise = (SyOrganise)frameworkDAO.loadBoById(SyOrganise.class,
					roleVO.getOrganise());
			role.setOrganise(organise);
			frameworkDAO.saveOrUpdate(role);
			
			result = (Integer) role.getId();
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("新增角色失败" + ee.getMessage());

		} 
		return result;

	}

	public void updateRole(RoleVO role) throws BaseException {
		logger.debug("update  role" + role);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			Role roleBO = (Role)frameworkDAO.loadBoById(Role.class, role.getId());
			roleBO.setRolename(role.getName());
			roleBO.setDescription(role.getDescription());
			frameworkDAO.saveOrUpdate(roleBO);
			
		} catch (HibernateException ee) {
			
			throw new BaseException("更新角色失败" + ee.getMessage());

		}
	}

	public PageList getMenuRoleList(Integer systemID, MenuRoleVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
			//Criteria criteria = hibernateSession.createCriteria(MenuRole.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(MenuRole.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// ???????????????????????????????????????????????????
			}				
			if(searchVO.getOrganise()!=null&&searchVO.getOrganise().intValue()!=0){
				SyOrganise organise=new SyOrganise(searchVO.getOrganise());
				criteria.add(Expression.eq("organise",organise));
			}
			if (searchVO != null) {
				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}

				if (searchVO.getRoleID() != null) {
					Role role = (Role)frameworkDAO.loadBoById(Role.class,
							searchVO.getRoleID());
					criteria.add(Expression.eq("role", role));
				}
				if (searchVO.getMenuID() != null) {
					Menu menu = (Menu)frameworkDAO.loadBoById(Menu.class,
							searchVO.getMenuID());
					criteria.add(Expression.eq("menu", menu));
				}
			}
			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
			
			
//			Long rowCount = (Long) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			logger.debug("size=" + size + ",start=" + firstResult);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//
//			List uaList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				MenuRole po = (MenuRole) iter.next();
//				uaList.add(po.toVO());
//			}
//			logger.debug("report admin EJB rowCount=" + rowCount);
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(uaList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("获取菜单角色失败" + ee.getMessage());

		}
	}

	public PageList getMenuRoleList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws BaseException {
		return getMenuRoleList(systemID, null, firstResult, fetchSize);
	}

	public PageList getMenuRoleList(Integer systemID) throws BaseException {
		return getMenuRoleList(systemID, new Integer(0), new Integer(0));
	}

	public PageList getMenuRoleList() throws BaseException {
		return getMenuRoleList(new Integer(0), new Integer(0), new Integer(0));
	}

	public Integer addMenuRole(MenuRoleVO vo) throws BaseException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			MenuRole po = new MenuRole();
			po.setId((Integer) vo.getId());
			po.setRightCode(vo.getRightCode());
			Menu menu = (Menu) frameworkDAO.loadBoById(Menu.class, vo.getMenuID());
			po.setMenu(menu);
			Role role = (Role) frameworkDAO.loadBoById(Role.class, vo.getRoleID());
			po.setRole(role);
			
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getModuleID());
			po.setModule(module);
			SyOrganise organise=role.getOrganise();
			//SyOrganise organise = (SyOrganise)frameworkDAO.loadBoById(SyOrganise.class,
			//		vo.getOrganise());
			po.setOrganise(organise);
			frameworkDAO.saveOrUpdate(po);
			
			result = (Integer) po.getId();
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("addMenuRole失败" + ee.getMessage());

		}
		return result;

	}

	public void updateMenuRole(MenuRoleVO vo) throws BaseException {
		logger.debug("update it system" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			MenuRole po = (MenuRole)frameworkDAO.loadBoById(MenuRole.class, vo
					.getId());
			Menu menu = (Menu)frameworkDAO.loadBoById(Menu.class, vo.getMenuID());
			po.setMenu(menu);
			Role role = (Role) frameworkDAO.loadBoById(Role.class, vo.getRoleID());
			po.setRole(role);
			po.setRightCode(vo.getRightCode());
			frameworkDAO.saveOrUpdate(po);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("addMenuRole失败" + ee.getMessage());

		}
	}

	public void deleteMenuRole(Integer id) throws BaseException {
		try {
			
			//hibernateSession = HibernateUtil.currentSession();
			MenuRole po = (MenuRole)frameworkDAO.loadBoById(MenuRole.class, id);
			
			frameworkDAO.delete(po);
			
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("addMenuRole失败" + ee.getMessage());

		}
	}

	public PageList getNoticeList(Integer systemID) throws BaseException {
		NoticeVO searchVO = new NoticeVO();
		searchVO.setStartDate(Calendar.getInstance());
		searchVO.setEndDate(Calendar.getInstance());
		return getNoticeList(systemID, searchVO, new Integer(0), new Integer(0));

	}

	public PageList getNoticeList(Integer systemID, NoticeVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
			//Criteria criteria = hibernateSession.createCriteria(Notice.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(Notice.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// ???????????????????????????????????????????????????
			}
			if (searchVO != null) {
				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getSubject() != null) {
					criteria.add(Expression.like("subject", "%"
							+ searchVO.getSubject() + "%"));
				}
				if (searchVO.getContent() != null) {
					criteria.add(Expression.like("content", "%"
							+ searchVO.getContent() + "%"));
				}
				if (searchVO.getStartDate() != null) {
					criteria.add(Expression.le("startDate", searchVO
							.getStartDate()));
				}
				if (searchVO.getEndDate() != null) {
					criteria.add(Expression
							.ge("endDate", searchVO.getEndDate()));
				}
			}
			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
//			Integer rowCount = (Integer) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);

//			List resultList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				Notice bo = (Notice) iter.next();
//				NoticeVO vo = (NoticeVO) bo.toVO();
//				resultList.add(vo);
//			}
//
//			// logger.debug("rowCount=" + rowCount);
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(resultList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error("getNoticeList失败", ee);
			throw new BaseException(ee.getMessage());

		}
	}

	public void updateNotice(NoticeVO vo) throws BaseException {
		logger.debug("update  role" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			Notice bo = (Notice)frameworkDAO.loadBoById(Notice.class, vo.getId());
			bo.setContent(vo.getContent());
			bo.setSubject(vo.getSubject());
			bo.setStartDate(SemAppUtils
					.convertCalendar(vo.getStartDate(), true));
			bo.setEndDate(SemAppUtils.convertCalendar(vo.getEndDate(), false));
			bo.setValid(vo.getValid());
			bo.setAttach(vo.getAttach());
			bo.setAttachName(vo.getAttachName());
			frameworkDAO.saveOrUpdate(bo);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("getNoticeList失败" + ee.getMessage());

		} 
	}

	public void deleteNotice(Integer noticeID) throws BaseException {
		
		try {

			//hibernateSession = HibernateUtil.currentSession();
			Notice bo = (Notice)frameworkDAO.loadBoById(Notice.class, noticeID);
			
			frameworkDAO.delete(bo);
			
		} catch (HibernateException ee) {

			logger.error(ee);
			throw new BaseException("deleteNotice失败" + ee.getMessage());

		} 
	}

	public Integer addNotice(NoticeVO vo) throws BaseException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			Notice bo = new Notice();
			bo.setId((Integer) vo.getId());
			bo.setContent(vo.getContent());
			bo.setSubject(vo.getSubject());
			bo.setStartDate(SemAppUtils
					.convertCalendar(vo.getStartDate(), true));
			bo.setEndDate(SemAppUtils.convertCalendar(vo.getEndDate(), false));
			bo.setValid(vo.getValid());
			bo.setAttach(vo.getAttach());
			bo.setAttachName(vo.getAttachName());
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getModuleID());
			bo.setModule(module);
			frameworkDAO.saveOrUpdate(bo);
			
			result = (Integer) bo.getId();
			vo.setId(result);
			try {
				helperRemote.addNotice(vo);
			} catch (RemoteException ee) {
				logger.error("addNotice失败", ee);
			}
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("addNotice失败" + ee.getMessage());

		}
		return result;
	}

	public PageList getProperties(Integer systemID) throws BaseException {
		return getProperties(systemID, null, new Integer(0), new Integer(0));

	}

	public List getProperties(Integer systemID, String root)
			throws BaseException {
		SystemPropertyVO vo = new SystemPropertyVO();
		vo.setParent(root);
		return getProperties(systemID, vo, new Integer(0), new Integer(0))
				.getItems();

	}

	public PageList getProperties(Integer systemID, SystemPropertyVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			Criteria criteria = hibernateSession
//					.createCriteria(SystemProperties.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(SystemProperties.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			// if (this.isSystemModule(system)) {
			// system = 0;// 系统管理模块可以管理所有模块的权限
			// }
			if (searchVO != null) {
			//	logger.debug("name="+searchVO.getName()+"id="+searchVO.getId());
				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getId() != null) {
			//		logger.debug("name="+searchVO.getName()+"id2="+searchVO.getId());
					criteria.add(Expression.like("id", "%" + searchVO.getId()
							+ "%"));
				}
				if (searchVO.getValue() != null) {
					criteria.add(Expression.like("value", "%"
							+ searchVO.getValue() + "%"));
				}
				if (searchVO.getRemark() != null) {
					criteria.add(Expression.like("remark", "%"
							+ searchVO.getRemark() + "%"));
				}
				if (searchVO.getParType() != null) {
					criteria.add(Expression
							.eq("parType", searchVO.getParType()));
				}
				if (searchVO.getParent() != null) {
					criteria.add(Expression.eq("parent", searchVO.getParent()));
				}

			}
			if (system != 0) {
			//	List modules = SemAppUtils.getSubmodules(system,
			//			hibernateSession);
				ItModule itModule=new ItModule();
				itModule.setId(new Integer(system));
				criteria.add(Expression.eq("module", itModule));
			}
//			Long rowCount = (Long) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//			List resultList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				SystemProperties bo = (SystemProperties) iter.next();
//				SystemPropertyVO vo = (SystemPropertyVO) bo.toVO();
//				resultList.add(vo);
//			}
//		logger.debug("rowCount=" + rowCount);
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(resultList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error("数据库查询失败", ee);
			throw new BaseException(ee.getMessage());

		} 
	}

	public void resetProperties() throws BaseException {
		try {
			helperRemote.setPropertiesAndNotices();
		} catch (RemoteException ee) {
			logger.error("更新系统配置参数失败", ee);
			throw new BaseException("更新系统配置参数失败", ee);
		}
	}

	public String getValueOfProperty(String name) throws BaseException {
		try {
			return helperRemote.getProperty(name);
		} catch (RemoteException ee) {
			logger.error("获取变量[" + name + "]属性值失败", ee);
			throw new BaseException("获取变量[" + name + "]属性值失败", ee);
		}
	}

	public void updateProperties(SystemPropertyVO vo) throws BaseException {
		logger.debug("update  role" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			SystemProperties bo = (SystemProperties)frameworkDAO.loadBoById(
					SystemProperties.class, vo.getId());
			SemAppUtils.beanCopy(vo, bo);
			frameworkDAO.saveOrUpdate(bo);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("数据库操作失败" + ee.getMessage());

		} 
	}

	public Object addPropertyVO(SystemPropertyVO vo) throws BaseException {
		java.io.Serializable result = null;
		logger.debug("add it UABinding" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			SystemProperties bo = new SystemProperties();
			SemAppUtils.beanCopy(vo, bo);
			bo.setId(vo.getId());
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getModuleID());
			bo.setModule(module);
			frameworkDAO.saveOrUpdate(bo);
			
			result = bo.getId();
			vo.setId(result);
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("addPropertyVO失败" + ee.getMessage());

		} 
		return result;
	}

	public PageList getFavorites(Integer systemID, Integer empID,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Favorite.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// ???????????????????????????????????????????????????
			}
			if (empID != null) {
				criteria.add(Expression.eq("empID", empID));
			}

			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
//			Integer rowCount = (Integer) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//			List resultList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				Favorite bo = (Favorite) iter.next();
//				FavoriteVO vo = (FavoriteVO) bo.toVO();
//				resultList.add(vo);
//			}
//
//			// logger.debug("rowCount=" + rowCount);
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(resultList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error("getFavorites失败", ee);
			throw new BaseException(ee.getMessage());

		}
	}

	public void deleteFavorite(Integer noticeID) throws BaseException {
		
		try {

			//hibernateSession = HibernateUtil.currentSession();
			Favorite bo = (Favorite)frameworkDAO.loadBoById(Favorite.class,
					noticeID);
			
			frameworkDAO.delete(bo);
			
		} catch (HibernateException ee) {

			logger.error(ee);
			throw new BaseException("deleteFavorite:" + ee.getMessage());

		} 
	}

	public Integer addFavorite(FavoriteVO vo) throws BaseException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			Favorite bo = new Favorite();
			bo.setId((Integer) vo.getId());
			bo.setEmpID(vo.getEmpID());
			bo.setCreateDate(vo.getCreateDate());
			bo.setMenuID(vo.getMenuID());
			bo.setName(vo.getName());
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getModuleID());
			bo.setModule(module);
			frameworkDAO.saveOrUpdate(bo);
			
			result = (Integer) bo.getId();
			vo.setId(result);
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("addFavorite失败" + ee.getMessage());

		}
		return result;
	}

	public PageList getRaBindingList(Integer systemID, RaBindingVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException {
		try {
			//hibernateSession = HibernateUtil.currentSession();
//			Criteria criteria = hibernateSession
//					.createCriteria(RaBinding.class);
			DetachedCriteria criteria = DetachedCriteria.forClass(RaBinding.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// 系统管理模块可以管理所有模块的权限
			}
			if (searchVO != null) {
				if (searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getRoleID() != null) {
					Role role = (Role)frameworkDAO.loadBoById(Role.class,
							searchVO.getRoleID());
					criteria.add(Expression.eq("role", role));
				}
				if (searchVO.getReportID() != null) {
					Menu menu = (Menu)frameworkDAO.loadBoById(Menu.class,
							searchVO.getReportID());
					criteria.add(Expression.eq("report", menu));
				}
			}
			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
//			Integer rowCount = (Integer) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			List uaList = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				RaBinding po = (RaBinding) iter.next();
//				uaList.add(po.toVO());
//			}
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(uaList);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("getRaBindingList失败" + ee.getMessage());

		} 
	}

	public Integer addRaBinding(RaBindingVO vo) throws BaseException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			RaBinding po = new RaBinding();
			po.setId((Integer) vo.getId());
			po.setRightCode(vo.getRightCode());
			ReportModule report = (ReportModule)frameworkDAO.loadBoById(
					ReportModule.class, vo.getReportID());
			po.setReport(report);
			Role role = (Role)frameworkDAO.loadBoById(Role.class, vo.getRoleID());
			po.setRole(role);
			ItModule module = (ItModule)frameworkDAO.loadBoById(ItModule.class,
					vo.getModuleID());
			po.setModule(module);
			frameworkDAO.saveOrUpdate(po);
			
			result = (Integer) po.getId();
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("addRaBinding失败" + ee.getMessage());

		} 
		return result;

	}

	public void updateRaBinding(RaBindingVO vo) throws BaseException {
		logger.debug("update it system" + vo);
		
		try {
			//hibernateSession = HibernateUtil.currentSession();
			
			RaBinding po = (RaBinding)frameworkDAO.loadBoById(RaBinding.class,
					vo.getId());
			ReportModule report = (ReportModule)frameworkDAO.loadBoById(
					ReportModule.class, vo.getReportID());
			po.setReport(report);
			Role role = (Role)frameworkDAO.loadBoById(Role.class, vo.getRoleID());
			po.setRole(role);
			po.setRightCode(vo.getRightCode());
			frameworkDAO.saveOrUpdate(po);
			
		} catch (HibernateException ee) {
			
			logger.error(ee);
			throw new BaseException("updateRaBinding失败" + ee.getMessage());

		}
	}

	public void deleteRaBinding(Integer id) throws BaseException {
		try {
			
			//hibernateSession = HibernateUtil.currentSession();
			RaBinding po = (RaBinding)frameworkDAO.loadBoById(RaBinding.class,
					id);
			
			frameworkDAO.delete(po);
			
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("deleteRaBinding删除失败" + ee.getMessage());

		} 
	}

	public void setUserProperties(UserPropertiesVO vo) throws
			 OSException, OSBussinessException, RemoteException {
		structureRemote.addUserProperties(vo);

	}

	public UserPropertiesVO getUserProperties(String id)
			throws  RemoteException, OSException, OSBussinessException {
		return structureRemote.getUserProperties(id);

	}

	public Map getUserProperties(Integer empId) throws OSException, OSBussinessException, RemoteException  {
		return structureRemote.getUserProperties(empId);
	}

	public PageList runTask(Integer empId, String ip) throws BaseException,
			RemoteException {
		// refresh online user
		try {
			structureRemote.logonOASystem(empId, ip);
		} catch (Exception e) {
			logger.error("update online user fail", e);
			throw new BaseException("update online user fail", e);
		}
		List list = new ArrayList();
		if (helperRemote.isOffline(empId, this.WEB_USER_ID).booleanValue()) {
			// offline user
			list.add(helperRemote.getProperty("USER_OFFLINE_COMMAND"));
		} else {
			// get system message from property
			list = helperRemote.getMessage(empId, WEB_USER_ID, ip);

			// return null;
			// get background
			List bgList = helperRemote.getCompleteBackgrounds(empId);
			list.addAll(bgList);

		}
		return new PageList(list);

	}

	public void onMethod(Integer empId, Integer method) throws BaseException,
			RemoteException {
		// refresh online user
		String name = "";
		if (method.intValue() >= 0) {
			MenuVO mvo = this.getMenuByID(method);
			name = mvo.getName();
			if (mvo.getSingleMode() == 1) {
				helperRemote.singleMode((Integer) mvo.getId(), mvo.getName(),
						empId, this.WEB_USER_ID);
			}
		} else {// open report

			ReportModuleVO rvo = reportRemote.getReportModuleByID(new Integer(
					0 - method.intValue()));
			name = rvo.getName();
		}
		helperRemote.onMethod(empId, WEB_USER_ID, name);

	}

	public SessionUserBean getSessionUserBean(User user, Integer systemID,
			String ip, String token) throws BaseException {
		return getSessionUserBean(user, systemID, ip, null, token);
	}

	public void cleanSignleMode(Integer method) throws BaseException,
			RemoteException {
		helperRemote.cleanSignleMode(method);
	}

	public void logout(Integer empId) throws BaseException, RemoteException{
		helperRemote.cleanSignleModeByKey(empId, this.WEB_USER_ID);
		helperRemote.logout(empId, this.WEB_USER_ID);
	}

	public PageList getSubDirectorys(Integer parent, String rightCode,
			String token, String parentID) throws BaseException{
		try {
			return (PageList)invokeEjbServices(new Integer(2241), new Serializable[]{parent,rightCode,token,parentID});
		} catch (BaseBusinessException e) {
			 throw new BaseException("调用文件服务器服务失败",e);
		}
	}
}
