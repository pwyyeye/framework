package com.xxl.facade;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

import common.bussiness.Department;
import common.bussiness.User;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.os.vo.OrganiseVO;
import common.os.vo.UsersVO;
import common.value.MailMessage;
import common.web.bean.SessionUserBean;

public interface  CommonRemote {

	public User verifyUser(String loginid, String password)
			throws BaseException;
	
//
	public User getDeptTopDirector(String deptID) throws Exception,
			RemoteException;

	public Department getDeptOfLevel(String deptID, String level)
			throws Exception;

	public Department getDepartmentInfo(Integer deptID) throws Exception,
			RemoteException;

	public UsersVO getUserInfo(Integer empID) throws Exception;

	public User getUserInfo(String empID) throws Exception;


	public UsersVO verifyUsersVO(String loginid, String password)
			throws BaseException;

	public User getEofficeLoginUser(String key, String ip)
			throws Exception;

	public UsersVO getEofficeLoginUserVO(String key, String ip)
			throws Exception;
	
	public UsersVO getEofficeLoginUserVO(String openId)
	 throws BaseException, BaseBusinessException, RemoteException;

	public UsersVO getEofficeLoginUserByEmpID(Integer empID, String ip)
			throws Exception;

	public User getEofficeLoginUserByEmpID(String empID, String ip)
			throws Exception;

	public String sendMessageByOA(String content, String sendUser,
			String[] receiveUsers, String type) throws Exception;

	public String sendMessageByMail(String to, String cc, String subject,
			String text, String from, String host) throws Exception;

	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host) throws Exception;

	public void sendMessageByMobile(String destMobile, String[] content)
			throws BaseException, BaseBusinessException;

//	public void sendMessageByMobile(MobileMessage mobileMessage)
//			throws Exception;

	public List getUserOfDept(String deptID) throws Exception;

//	public String publishMessage(Message message) throws Exception,
//			RemoteException;

	public String getUserToken(Integer empID) throws Exception;

	public String getUserToken(String empID) throws Exception;

	public SessionUserBean getSemSessionUser(UsersVO theUser, Integer systemID,
			String ip) throws Exception;

	public Boolean isTest() throws Exception;

	public Serializable invokeEjbServices(Integer serviceId,
			Serializable[] argments) throws Exception,
			BaseBusinessException;

	public void logonOASystem(Integer empID, String ip) throws Exception;

	public void logonOASystem(String empID, String ip) throws Exception;

	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host, String[] attachFile)
			throws Exception;

	public String sendMessageByMail(MailMessage message) throws Exception,
			RemoteException;

	public void sendMail(MailMessage message) throws Exception,
			RemoteException;

	public Integer registerUser(String username, String password)
			throws BaseException, BaseBusinessException;
	
	public Integer registerUser(String username, String password,Integer type,String openId)
	throws BaseException, BaseBusinessException;


	public void changePassword(String username, String password,
			String oldPassword) throws BaseException, BaseBusinessException;

	public String encrytor(String data) throws BaseException,
			BaseBusinessException;

	public String decrytor(String data) throws BaseException,
			BaseBusinessException;

	public String encrytor(String data, String key) throws BaseException,
			BaseBusinessException;

	public String decrytor(String data, String key) throws BaseException,
			BaseBusinessException;
	
	
	public void sendMessageByMobile(String destMobile, String[] message,
			String customSenderId) throws BaseException, BaseBusinessException;

	public Integer addOrganise(OrganiseVO vo) throws BaseException,
			BaseBusinessException;

	public void changeName(String username, String nick, String icon)
			throws BaseException, BaseBusinessException;;

	public void changeName(String username, String nick, String icon,
			String sex, Calendar birthday) throws BaseException,
			BaseBusinessException;

//	public Integer addFriend(FriendsVO vo) throws BaseException,
//			BaseBusinessException;

//	public void sendAppPushMessage(Integer type, String users, String title,
//			String message,Integer bzId) throws BaseException ;

//	public void sendAppPushMessage(PushMessage message) throws BaseException,
//			RemoteException;

//	public void sendAppMessage(Integer type, String users, String title,
//			String message,Integer bzId) throws Exception;

	public List getSubOrganises(int organise) throws BaseException,
			Exception;
	
//	public void sendAppBroadcast(String title, String message)
//	throws BaseException;
//	
//	public Boolean isFriend(Integer userId,Integer friendId) throws BaseException,
//	BaseBusinessException ;

}
