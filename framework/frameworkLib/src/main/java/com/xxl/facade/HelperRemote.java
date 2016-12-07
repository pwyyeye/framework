package com.xxl.facade;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;
import common.jms.vo.JMSTaskVO;
import common.value.NoticeVO;
import common.value.PageList;

public interface HelperRemote{
	public Properties getProperties() throws RemoteException;

	public void addBackground(JMSTaskVO task) throws RemoteException;

	public void endBackground(JMSTaskVO task) throws RemoteException;

	public void addErrorBackground(JMSTaskVO task) throws RemoteException;

	public List getErrorBackgrounds(Integer empID) throws RemoteException;
	
	public List getErrorBackgrounds(String empID) throws RemoteException;

	public List getBackgrounds(Integer empID) throws RemoteException;
	
	public List getBackgrounds(String empID) throws RemoteException;

	public String getProperty(String name) throws RemoteException;

	public void setProperty(String name, String value) throws RemoteException;

	public List getNoticeList() throws RemoteException;

	public void setPropertiesAndNotices() throws RemoteException;

	public void addNotice(NoticeVO vo) throws RemoteException;

	public PageList getOnlineList() throws RemoteException;

	public void offline(int connectID, Integer empID) throws RemoteException;
	public void offline(int connectID, String empID) throws RemoteException;

	public void offline(Integer key) throws RemoteException;

	public void sendMessage(Integer key, String message) throws RemoteException;

	public void publishMessage(String message) throws RemoteException;

	public List getMessage(Integer empID, int connectID, String ip)
			throws RemoteException;
	public List getMessage(String empID, int connectID, String ip)
	throws RemoteException;

	public Boolean singleMode(Integer nodeID, String nodeName, Integer empID,
			int connectID) throws RemoteException;
	public Boolean singleMode(Integer nodeID, String nodeName, String empID,
			int connectID) throws RemoteException;

	public void cleanSignleModeByKey(Integer empID, int connectID)
			throws RemoteException;
	public void cleanSignleModeByKey(String empID, int connectID)
	throws RemoteException;

	public PageList getLocksList() throws RemoteException;

	public void cleanSignleMode(Integer nodeID) throws RemoteException;

	public Boolean isOffline(Integer empID, int connectID)
			throws RemoteException;
	public Boolean isOffline(String empID, int connectID)
	throws RemoteException;

	public void logout(Integer empID, int connectID) throws RemoteException;
	public void logout(String empID, int connectID) throws RemoteException;

	public void logout(Integer key) throws RemoteException;

	public void login(Integer empID, int connectID) throws RemoteException;
	public void login(String empID, int connectID) throws RemoteException;

	public void switchRole(Integer empID, int connectID, String module,
			String role) throws RemoteException;
	public void switchRole(String empID, int connectID, String module,
			String role) throws RemoteException;

	public void onMethod(Integer empID, int connectID, String methodName)
			throws RemoteException;
	public void onMethod(String empID, int connectID, String methodName)
	throws RemoteException;

	public List getCompleteBackgrounds(Integer empId)throws RemoteException;
	public List getCompleteBackgrounds(String empId)throws RemoteException;
	
	
	public void putUserToken(Integer empId,String token)throws RemoteException;
	
	public String getUserToken(Integer empId)throws RemoteException;
	
	public Object getCustomValue(Object key)throws RemoteException;
	
	public void setCustomValue(Object key,Object value)throws RemoteException;
	
}
