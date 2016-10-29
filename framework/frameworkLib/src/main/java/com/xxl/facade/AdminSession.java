package com.xxl.facade;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import common.bussiness.User;
import common.exception.CommonException;
import common.os.vo.UsersVO;
import common.os.vo.exception.OSBussinessException;
import common.os.vo.exception.OSException;
import common.value.EventVO;
import common.value.FavoriteVO;
import common.value.ItModuleVO;
import common.value.MenuRoleVO;
import common.value.MenuVO;
import common.value.NoticeVO;
import common.value.ObserverVO;
import common.value.PageList;
import common.value.RaBindingVO;
import common.value.RoleVO;
import common.value.SystemPropertyVO;
import common.value.TreeControl;
import common.value.UABindingVO;
import common.value.UserPropertiesVO;
import common.web.bean.SessionUserBean;

public interface AdminSession{
	public Integer addSystem(ItModuleVO system) throws CommonException,
			RemoteException;

	public ItModuleVO getSystemByID(Integer systemID) throws CommonException,
			RemoteException;

	public void updateSystemStatus(Integer systemID, Integer status)
			throws CommonException, RemoteException;

	public List getSystemList(Integer root) throws CommonException,
			RemoteException;

	public void updateSystem(ItModuleVO system) throws CommonException,
			RemoteException;

	public TreeControl getSystemTree(String rootStr) throws CommonException,
			RemoteException;

	public void updateMenu(MenuVO vo) throws CommonException, RemoteException;

	public List getSystemList() throws CommonException, RemoteException;

	public List getSystemList(Integer root, Boolean containRoot,
			Boolean renameModuleID) throws CommonException, RemoteException;

	public void deleteMenu(Integer menuID) throws CommonException,
			RemoteException;

	public Integer addMenu(MenuVO vo) throws CommonException, RemoteException;

	public MenuVO getMenuByID(Integer MenuID) throws CommonException,
			RemoteException;

	public List getSystemList(Integer root, Boolean containRoot)
			throws CommonException, RemoteException;

	public List getMenuList(Integer moduleID, Integer root)
			throws CommonException, RemoteException;

	public Integer addMessageSubscibe(ObserverVO vo) throws CommonException,
			RemoteException;

	public void deleteMessageSubscibe(Integer messageSubscibeID)
			throws CommonException, RemoteException;

	public PageList getMessageSubscibeList(Integer systemID)
			throws CommonException, RemoteException;

	public PageList getMessageSubscibeList() throws CommonException,
			RemoteException;

	public PageList getMessageSubscibeList(Integer systemID,
			ObserverVO searchVO, Integer firstResult, Integer fetchSize)
			throws CommonException, RemoteException;

	public void updateMessageSubscibe(ObserverVO vo) throws CommonException,
			RemoteException;

	public PageList getEventList(Integer systemID) throws CommonException,
			RemoteException;

	public Integer addEvent(EventVO vo) throws CommonException, RemoteException;

	public void deleteEvent(Integer eventID) throws CommonException,
			RemoteException;

	public void updateEvent(EventVO vo) throws CommonException, RemoteException;

	public PageList getEventList() throws CommonException, RemoteException;

	public PageList getEventList(Integer systemID, EventVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public SessionUserBean getSessionUserBean(UsersVO theUser,
			Integer systemID, String ip, String token) throws CommonException,
			RemoteException;

	public SessionUserBean getSessionUserBean(UsersVO theUser,
			Integer systemID, String ip, Integer roleID, String token)
			throws CommonException, RemoteException;

	public TreeControl getMenuTree(String systemID, SessionUserBean theUser)
			throws CommonException, RemoteException;

	public PageList getRoleList(Integer systemID) throws CommonException,
			RemoteException;

	public RoleVO getRoleByID(Integer roleID) throws CommonException,
			RemoteException;

	public PageList getUABindingList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws CommonException, RemoteException;

	public PageList getUABindingList(Integer systemID) throws CommonException,
			RemoteException;

	public PageList getUABindingList() throws CommonException, RemoteException;

	public Integer addUABinding(UABindingVO ub) throws CommonException,
			RemoteException;

	public void updateUABinding(UABindingVO uaBinding) throws CommonException,
			RemoteException;

	public void deleteUABinding(Integer bindingID) throws CommonException,
			RemoteException;

	public Integer addRole(RoleVO roleVO) throws CommonException,
			RemoteException;

	public PageList getRoleList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws CommonException, RemoteException;

	public void deleteRole(Integer roleID) throws CommonException,
			RemoteException;

	public void updateRole(RoleVO role) throws CommonException, RemoteException;

	public PageList getRoleList(Integer systemID, RoleVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public PageList getUABindingList(Integer systemID, UABindingVO uaVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public PageList getUserRoleList(Integer systemID, UsersVO user)
			throws CommonException, RemoteException;

	public PageList getUserRoleList(Integer systemID, User user)
			throws CommonException, RemoteException;

	public PageList getMenuRoleList(Integer systemID, MenuRoleVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public PageList getMenuRoleList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws CommonException, RemoteException;

	public PageList getMenuRoleList(Integer systemID) throws CommonException,
			RemoteException;

	public PageList getMenuRoleList() throws CommonException, RemoteException;

	public Integer addMenuRole(MenuRoleVO vo) throws CommonException,
			RemoteException;

	public void updateMenuRole(MenuRoleVO vo) throws CommonException,
			RemoteException;

	public void deleteMenuRole(Integer id) throws CommonException,
			RemoteException;

	public PageList getNoticeList(Integer systemID, NoticeVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public void updateNotice(NoticeVO vo) throws CommonException,
			RemoteException;

	public void deleteNotice(Integer noticeID) throws CommonException,
			RemoteException;

	public Integer addNotice(NoticeVO vo) throws CommonException,
			RemoteException;

	public PageList getNoticeList(Integer systemID) throws CommonException,
			RemoteException;

	public PageList getRaBindingList(Integer systemID, RaBindingVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public Integer addRaBinding(RaBindingVO vo) throws CommonException,
			RemoteException;

	public void updateRaBinding(RaBindingVO vo) throws CommonException,
			RemoteException;

	public void deleteRaBinding(Integer id) throws CommonException,
			RemoteException;

	public PageList getFavorites(Integer systemID, Integer empID,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public void deleteFavorite(Integer noticeID) throws CommonException,
			RemoteException;

	public Integer addFavorite(FavoriteVO vo) throws CommonException,
			RemoteException;

	public Object addPropertyVO(SystemPropertyVO vo) throws CommonException,
			RemoteException;

	public void updateProperties(SystemPropertyVO vo) throws CommonException,
			RemoteException;

	public PageList getProperties(Integer systemID, SystemPropertyVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public PageList getProperties(Integer systemID) throws CommonException,
			RemoteException;

	public List getProperties(Integer moduleID, String root)
			throws CommonException, RemoteException;

	public String getValueOfProperty(String name) throws CommonException,
			RemoteException;

	public void resetProperties() throws CommonException, RemoteException;

	public void setUserProperties(UserPropertiesVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public Map getUserProperties(Integer empId) throws OSException,
			OSBussinessException, RemoteException;

	public UserPropertiesVO getUserProperties(String id)
			throws RemoteException, OSException, OSBussinessException;

	public PageList runTask(Integer empId, String ip) throws CommonException,
			RemoteException;

	public void onMethod(Integer empId, Integer method) throws CommonException,
			RemoteException;

	public void cleanSignleMode(Integer method) throws CommonException,
			RemoteException;

	public void logout(Integer empId) throws CommonException, RemoteException;

	public SessionUserBean getSessionUserBean(User user, Integer systemID,
			String ip, Integer roleID, String token) throws CommonException,
			RemoteException;

	public SessionUserBean getSessionUserBean(User user, Integer systemID,
			String ip, String token) throws CommonException, RemoteException;

	public PageList getSubDirectorys(Integer parent, String rightCode,
			String token, String parentID) throws CommonException,
			RemoteException;

}
