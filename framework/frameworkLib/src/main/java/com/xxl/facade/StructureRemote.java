package com.xxl.facade;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.os.vo.DepartmentVO;
import common.os.vo.DutyVO;
import common.os.vo.OrganiseVO;
import common.os.vo.UsersVO;
import common.os.vo.exception.OSBussinessException;
import common.os.vo.exception.OSException;
import common.value.PageList;
import common.value.UserPropertiesVO;
import common.web.bean.SessionUserBean;

public interface StructureRemote {
	public UsersVO getUserInfo(Integer empId) throws OSException,
			OSBussinessException, RemoteException;

	public void delUsers(Integer id) throws OSException, OSBussinessException,
			RemoteException;

	public Integer addUsers(UsersVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public void updateUsers(final UsersVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public PageList getUsers(UsersVO vo, Integer firstResult, Integer fetchSize)
			throws OSException, OSBussinessException, RemoteException;

	public PageList getDepartment(Integer organiseID, Integer parentId)
			throws OSException, OSBussinessException, RemoteException;

	public PageList getDepartment(DepartmentVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException,
			RemoteException;

	public void delDepartment(final Integer id) throws OSException,
			OSBussinessException, RemoteException;

	public String addDepartment(DepartmentVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public void updateDepartment(final DepartmentVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public DepartmentVO getDepartment(String deptID) throws OSException,
			OSBussinessException, RemoteException;

	public UsersVO getDeptTopDirector(String deptID) throws OSException,
			OSBussinessException, RemoteException;

	public DepartmentVO getDeptOfLevel(String deptID, Integer level)
			throws OSException, OSBussinessException, RemoteException;

	public String getUserToken(Integer empID) throws OSException,
			OSBussinessException, RemoteException;

	public UsersVO getEofficeLoginUser(String key, String ip, String authKey,
			Calendar expiredTime, int type, boolean isTest, boolean checkActive)
			throws BaseException, BaseBusinessException, RemoteException;

	public UsersVO getEofficeLoginUser(String openId) throws BaseException,
			BaseBusinessException, RemoteException;

	public void updateUsers(final UsersVO vo, String captchas)
			throws BaseException, BaseBusinessException, RemoteException;

	public void logonOASystem(Integer empid, String ip) throws OSException,
			OSBussinessException, RemoteException;

	public Integer verifyUser(String username, String password)
			throws BaseException, BaseBusinessException, RemoteException;

	public PageList getDuty(String organiseID, String parentId)
			throws OSException, OSBussinessException, RemoteException;

	public PageList getDuty(DutyVO vo, Integer firstResult, Integer fetchSize)
			throws OSException, OSBussinessException, RemoteException;

	public void delDuty(final Integer id) throws OSException,
			OSBussinessException, RemoteException;

	public Integer addDuty(DutyVO vo) throws OSException, OSBussinessException,
			RemoteException;

	public void updateDuty(final DutyVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public DutyVO getDuty(Integer id) throws OSException, OSBussinessException,
			RemoteException;

	public PageList getUserProperties(UserPropertiesVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException,
			RemoteException;

	public Map getUserProperties(Integer empId) throws OSException,
			OSBussinessException, RemoteException;

	public void delUserProperties(final Integer id) throws OSException,
			OSBussinessException, RemoteException;

	public String addUserProperties(UserPropertiesVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public void updateUserProperties(final UserPropertiesVO vo)
			throws OSException, OSBussinessException, RemoteException;

	public UserPropertiesVO getUserProperties(String id) throws OSException,
			OSBussinessException, RemoteException;

	public void changePassword(String username, String password,
			String oldPassword) throws BaseException, BaseBusinessException,
			RemoteException;

	public void changeName(String username, String name, String icon)
			throws OSException, OSBussinessException, RemoteException;

	public void changeName(String username, String nick, String icon,
			String sex, Calendar birthday) throws OSException,
			OSBussinessException, RemoteException;

	public String getToken(String userId, String userName, String portraitUri)
			throws BaseBusinessException, BaseException, RemoteException;

	public String getToken(String userId) throws BaseBusinessException,
			BaseException, RemoteException;

	public String checkOnline(String userId) throws BaseBusinessException,
			BaseException, RemoteException;

//	public String publishSystemMessage(String fromUserId, List toUserIds,
//			Message msg, String pushContent, String pushData)
//			throws BaseBusinessException, BaseException, RemoteException;
//
//	public String publishMessage(String fromUserId, List toUserIds,
//			Message msg, String pushContent, String pushData)
//			throws BaseBusinessException, BaseException, RemoteException;
//
//	public String publishMessage(String fromUserId, List toUserIds, Message msg)
//			throws BaseBusinessException, BaseException, RemoteException;
	
//	public String createChatroom(String chatroomId, String chatroomName) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String destroyChatroom(String chatroomId) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String queryChatroomUser(List<String> chatroomIds) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String chatroomUserBLockAdd(String userId, String chatroomId, String minute) throws BaseBusinessException,
//			BaseException, RemoteException;

//	public PageList getFriends(Integer empId, Integer firstResult,
//			Integer fetchSize) throws OSException, OSBussinessException,
//			RemoteException;


	public String getCaptchas(String mobile, final SessionUserBean user)
			throws Exception;

//	public Integer registerUser(final LyuserVO vo, final String captchas)
//			throws Exception;
//
//	public Integer updateregisterUser(final LyuserVO vo, final String captchas)
//			throws Exception;
//
//	public Integer updateLyUser(final LyuserVO vo) throws Exception;

	public OrganiseVO getOrganise(final Integer orId) throws BaseException,
			BaseBusinessException, RemoteException;

	public PageList getOrganise(OrganiseVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException,
			RemoteException;

	public PageList getOrganises(final Integer orId) throws BaseException,
			BaseBusinessException, RemoteException;

	public void delOrganise(final Integer id) throws BaseException,
			BaseBusinessException, RemoteException;

	public Integer addOrganise(OrganiseVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public void updateOrganise(final OrganiseVO vo) throws BaseException,
			BaseBusinessException, RemoteException;

	public void logoutOASystem(final Integer empId) throws OSException,
			OSBussinessException, RemoteException;

	public PageList getLieyuUsers(UsersVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException,
			RemoteException;

	public List getSubOrganises(final int organise) throws OSException,
			RemoteException;
//
//	public String syncGroup(String userId, String[] groupIds, String[] groupNames) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String createGroup(List<String> userIds, String groupId, String groupName) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String joinGroup(String userId, String groupId, String groupName) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String quitGroup(String userId, String groupId) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String dismissGroup(String userId, String groupId) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String refreshGroupInfo(String groupId, String groupName) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String queryGroupUserList(String groupId) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String groupUserGagAdd(String userId, String groupId, String minute) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String groupUserGagRollback(String userId, String groupId) throws BaseBusinessException,
//			BaseException, RemoteException;
//	
//	public String groupUserGagList(String groupId) throws BaseBusinessException,
//			BaseException, RemoteException;
	
}
