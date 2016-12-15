package com.xxl.facade;

import java.rmi.RemoteException;

import common.bussiness.Message;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommonException;
import common.jms.vo.JMSTaskVO;
import common.jms.vo.UserPushLogVO;
import common.jms.vo.UserPushVO;
import common.os.vo.exception.OSBussinessException;
import common.os.vo.exception.OSException;
import common.value.MailMessage;
import common.value.MobileMessage;
import common.value.PageList;
import common.value.PageMap;

public interface JMSTaskRemote{
	public PageList getJMSTaskList(Integer systemID, JMSTaskVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException,
			RemoteException;

	public Integer addJMSTask(JMSTaskVO vo) throws CommonException,
			RemoteException;

	public void updateJMSTask(JMSTaskVO vo) throws CommonException,
			RemoteException;

	public PageList getDoingTaskList(Integer systemID, Integer empID)
			throws CommonException, RemoteException;

	public PageList getDoingTaskList(Integer systemID, String empID)
			throws CommonException, RemoteException;

	public void closeJMSTask(Integer id) throws CommonException,
			RemoteException;

	public void delUserPush(final Integer id) throws OSException,
			OSBussinessException, RemoteException;

	public Integer addUserPush(UserPushVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public void updateUserPush(final UserPushVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public Integer addUserPushLog(UserPushLogVO vo) throws OSException,
			OSBussinessException, RemoteException;

	public PageList getUserPush(UserPushVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException,
			RemoteException;

	public PageList getUserPushLog(UserPushLogVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException,
			RemoteException;

	public void updateUserPushLog(final UserPushLogVO vo) throws BaseException,
			BaseBusinessException, RemoteException;

	public Boolean isOpenUserPush(Integer userId, Integer type)
			throws BaseException, BaseBusinessException, RemoteException;

//	public void sendAppBroadcast(String title, String message)
//			throws BaseException, BaseBusinessException, RemoteException;

	public void sendAppPushMessage(String users, String title, String message)
			throws BaseException, BaseBusinessException, RemoteException;

	public void resendUserPush(final Integer id) throws BaseException,
			BaseBusinessException, RemoteException;

	public PageMap getUnReadPush(Integer userId) throws BaseException,
			BaseBusinessException, RemoteException;
	
	
	public String sendMessageByMail(String to, String cc, String subject,
			String text, String from, String host) throws Exception;

	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host) throws Exception;
	
	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host, String[] attachFile)
			throws Exception;

	public void sendMessageByMail(MailMessage message) throws Exception,
			RemoteException;

	public void sendMail(MailMessage message) throws Exception,
			RemoteException;
	
	public String publishMessage(Message message) throws Exception,
			RemoteException;
	
	public void sendMessageByMobile(String destMobile, String[] content)
			throws BaseException, BaseBusinessException;

	public void sendMessageByMobile(MobileMessage mobileMessage)
			throws Exception;
	
	public void sendMessageByMobile(String destMobile, String[] message,
			String customSenderId) throws BaseException, BaseBusinessException;
	
}
