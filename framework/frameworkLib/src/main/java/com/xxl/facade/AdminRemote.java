package com.xxl.facade;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import common.bussiness.User;
import common.exception.BaseException;
import common.exception.BaseException;
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

public interface AdminRemote{
	public Integer addSystem(ItModuleVO system) throws BaseException,
			RemoteException;

	public ItModuleVO getSystemByID(Integer systemID) throws BaseException,
			RemoteException;

	public void updateSystemStatus(Integer systemID, Integer status)
			throws BaseException, RemoteException;

	public List getSystemList(Integer root) throws BaseException,
			RemoteException;

	public void updateSystem(ItModuleVO system) throws BaseException,
			RemoteException;

	public TreeControl getSystemTree(String rootStr) throws BaseException,
			RemoteException;

	public void updateMenu(MenuVO vo) throws BaseException, RemoteException;

	public List getSystemList() throws BaseException, RemoteException;

	public List getSystemList(Integer root, Boolean containRoot,
			Boolean renameModuleID) throws BaseException, RemoteException;

	public void deleteMenu(Integer menuID) throws BaseException,
			RemoteException;

	public Integer addMenu(MenuVO vo) throws BaseException, RemoteException;

	public MenuVO getMenuByID(Integer MenuID) throws BaseException,
			RemoteException;

	public List getSystemList(Integer root, Boolean containRoot)
			throws BaseException, RemoteException;

	public List getMenuList(Integer moduleID, Integer root)
			throws BaseException, RemoteException;

	public Integer addMessageSubscibe(ObserverVO vo) throws BaseException,
			RemoteException;

	public void deleteMessageSubscibe(Integer messageSubscibeID)
			throws BaseException, RemoteException;

	public PageList getMessageSubscibeList(Integer systemID)
			throws BaseException, RemoteException;

	public PageList getMessageSubscibeList() throws BaseException,
			RemoteException;

	public PageList getMessageSubscibeList(Integer systemID,
			ObserverVO searchVO, Integer firstResult, Integer fetchSize)
			throws BaseException, RemoteException;

	public void updateMessageSubscibe(ObserverVO vo) throws BaseException,
			RemoteException;

	public PageList getEventList(Integer systemID) throws BaseException,
			RemoteException;

	public Integer addEvent(EventVO vo) throws BaseException, RemoteException;

	public void deleteEvent(Integer eventID) throws BaseException,
			RemoteException;

	public void updateEvent(EventVO vo) throws BaseException, RemoteException;

	public PageList getEventList() throws BaseException, RemoteException;

	public PageList getEventList(Integer systemID, EventVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public SessionUserBean getSessionUserBean(UsersVO theUser,
			Integer systemID, String ip, String token) throws BaseException,
			RemoteException;

	public SessionUserBean getSessionUserBean(UsersVO theUser,
			Integer systemID, String ip, Integer roleID, String token)
			throws BaseException, RemoteException;

	public TreeControl getMenuTree(String systemID, SessionUserBean theUser)
			throws BaseException, RemoteException;

	public PageList getRoleList(Integer systemID) throws BaseException,
			RemoteException;

	public RoleVO getRoleByID(Integer roleID) throws BaseException,
			RemoteException;

	public PageList getUABindingList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws BaseException, RemoteException;

	public PageList getUABindingList(Integer systemID) throws BaseException,
			RemoteException;

	public PageList getUABindingList() throws BaseException, RemoteException;

	public Integer addUABinding(UABindingVO ub) throws BaseException,
			RemoteException;

	public void updateUABinding(UABindingVO uaBinding) throws BaseException,
			RemoteException;

	public void deleteUABinding(Integer bindingID) throws BaseException,
			RemoteException;

	public Integer addRole(RoleVO roleVO) throws BaseException,
			RemoteException;

	public PageList getRoleList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws BaseException, RemoteException;

	public void deleteRole(Integer roleID) throws BaseException,
			RemoteException;

	public void updateRole(RoleVO role) throws BaseException, RemoteException;

	public PageList getRoleList(Integer systemID, RoleVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public PageList getUABindingList(Integer systemID, UABindingVO uaVO,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public PageList getUserRoleList(Integer systemID, UsersVO user)
			throws BaseException, RemoteException;

	public PageList getUserRoleList(Integer systemID, User user)
			throws BaseException, RemoteException;

	public PageList getMenuRoleList(Integer systemID, MenuRoleVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public PageList getMenuRoleList(Integer systemID, Integer firstResult,
			Integer fetchSize) throws BaseException, RemoteException;

	public PageList getMenuRoleList(Integer systemID) throws BaseException,
			RemoteException;

	public PageList getMenuRoleList() throws BaseException, RemoteException;

	public Integer addMenuRole(MenuRoleVO vo) throws BaseException,
			RemoteException;

	public void updateMenuRole(MenuRoleVO vo) throws BaseException,
			RemoteException;

	public void deleteMenuRole(Integer id) throws BaseException,
			RemoteException;

	public PageList getNoticeList(Integer systemID, NoticeVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public void updateNotice(NoticeVO vo) throws BaseException,
			RemoteException;

	public void deleteNotice(Integer noticeID) throws BaseException,
			RemoteException;

	public Integer addNotice(NoticeVO vo) throws BaseException,
			RemoteException;

	public PageList getNoticeList(Integer systemID) throws BaseException,
			RemoteException;

	public PageList getRaBindingList(Integer systemID, RaBindingVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public Integer addRaBinding(RaBindingVO vo) throws BaseException,
			RemoteException;

	public void updateRaBinding(RaBindingVO vo) throws BaseException,
			RemoteException;

	public void deleteRaBinding(Integer id) throws BaseException,
			RemoteException;

	public PageList getFavorites(Integer systemID, Integer empID,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public void deleteFavorite(Integer noticeID) throws BaseException,
			RemoteException;

	public Integer addFavorite(FavoriteVO vo) throws BaseException,
			RemoteException;

	public Object addPropertyVO(SystemPropertyVO vo) throws BaseException,
			RemoteException;

	public void updateProperties(SystemPropertyVO vo) throws BaseException,
			RemoteException;

	public PageList getProperties(Integer systemID, SystemPropertyVO searchVO,
			Integer firstResult, Integer fetchSize) throws BaseException,
			RemoteException;

	public PageList getProperties(Integer systemID) throws BaseException,
			RemoteException;

	public List getProperties(Integer moduleID, String root)
			throws BaseException, RemoteException;

	public String getValueOfProperty(String name) throws BaseException,
			RemoteException;

	public void resetProperties() throws BaseException, RemoteException;

	public void setUserProperties(UserPropertiesVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public Map getUserProperties(Integer empId) throws OSException,
			OSBussinessException, RemoteException;

	public UserPropertiesVO getUserProperties(String id)
			throws RemoteException, OSException, OSBussinessException;

	public PageList runTask(Integer empId, String ip) throws BaseException,
			RemoteException;

	public void onMethod(Integer empId, Integer method) throws BaseException,
			RemoteException;

	public void cleanSignleMode(Integer method) throws BaseException,
			RemoteException;

	public void logout(Integer empId) throws BaseException, RemoteException;

	public SessionUserBean getSessionUserBean(User user, Integer systemID,
			String ip, Integer roleID, String token) throws BaseException,
			RemoteException;

	public SessionUserBean getSessionUserBean(User user, Integer systemID,
			String ip, String token) throws BaseException, RemoteException;

	public PageList getSubDirectorys(Integer parent, String rightCode,
			String token, String parentID) throws BaseException,
			RemoteException;

}
