package com.xxl.baseService.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import com.xxl.baseService.bo.Notice;
import com.xxl.baseService.bo.SystemProperties;
import com.xxl.facade.HelperRemote;

import common.HibernateUtil;
import common.exception.CommonException;
import common.jms.vo.JMSTaskVO;
import common.utils.SemAppUtils;
import common.value.LockVO;
import common.value.NoticeVO;
import common.value.OnlineVO;
import common.value.PageList;

@Service("helperRemote")
public class HelperService implements HelperRemote{

	public Log logger = LogFactory.getLog(this.getClass());

	private final static int DEFAULT_ONLINE_VALID = 1 * 60 * 1000;

	private int onlineValidPeriod;

//	SessionContext sessionContext;

	Properties properties;

	private Map backgrounds;

	private Map currentRoles;

	private Map backgroundsErrors;

	private Map offlines;

	private Map onMethods;// 用户在执行的方法

	private List noticeList;

	private Map loginMap;// 存用户登录时间

	// private List publishMessageList;

	private Map userMessages;

	private Map onlineUsers;

	private Map singleModes;// 单模的功能
	
	private Map completeBackgrounds;
	
	private Map userTokens;   //存IM Token
	
	private Map customVars;  //存客户自定义的数值

	public Properties getProperties() {
		return properties;
	}

	public void addBackground(JMSTaskVO task) {
		if (task == null)
			return;
		Integer empID = task.getEmpID();
		Map tasks = new HashMap();
		if (backgrounds.containsKey(empID)) {
			tasks = (Map) backgrounds.get(empID);
			tasks.put(task.getId(), task);
		} else {
			tasks.put(task.getId(), task);
		}
		backgrounds.put(empID, tasks);

	}

	public void endBackground(JMSTaskVO task) {
		if (task == null)
			return;
		Integer empId = task.getEmpID();
		if (backgrounds.containsKey(empId)) {
			Map tasks = (Map) backgrounds.get(empId);
			tasks.remove(task.getId());
			List list=new ArrayList();
			if(completeBackgrounds.containsKey(empId)){
				list=(List)completeBackgrounds.get(empId);
			}

			list.add("后台任务["+task+"]已完成,请确认");
			completeBackgrounds.put(empId, list);
		}
	}

	public List getCompleteBackgrounds(Integer empId){
		List list=new ArrayList();
		if(completeBackgrounds.containsKey(empId)){
			list=(List)completeBackgrounds.get(empId);
			completeBackgrounds.remove(empId);
		}
		return list;
	
	}
	
	public void addErrorBackground(JMSTaskVO task) {
		endBackground(task);
		Integer empID = task.getEmpID();
		Map tasks = new HashMap();
		if (backgroundsErrors.containsKey(empID)) {
			tasks = (Map) backgroundsErrors.get(empID);
			tasks.put(task.getId(), task);
		} else {
			tasks.put(task.getId(), task);

		}
		backgroundsErrors.put(empID, tasks);
		List list=new ArrayList();
		if(completeBackgrounds.containsKey(empID)){
			list=(List)completeBackgrounds.get(empID);
		}

		list.add("后台任务["+task+"]出现异常，请确认");
		completeBackgrounds.put(empID, list);
	}

	public List getErrorBackgrounds(Integer empID) {
		if (backgroundsErrors != null && backgroundsErrors.containsKey(empID)) {
			Map errors = (Map) backgroundsErrors.get(empID);

			List list = new ArrayList(errors.values());
			backgroundsErrors.remove(empID);
			return list;
		} else {
			return null;
		}

	}

	public List getBackgrounds(Integer empID) {
		if (backgrounds != null && backgrounds.containsKey(empID)) {
			Map errors = (Map) backgrounds.get(empID);

			List list = new ArrayList(errors.values());
			return list;
		} else {
			return null;
		}

	}

	public List getMessage(Integer empID, int connectID, String ip) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);

		long currentTime = Calendar.getInstance().getTimeInMillis();
		OnlineVO vo = new OnlineVO(key, empID, null, ip, true, currentTime);
		onlineUsers.put(key, vo);
		// logger.debug("online user"+key);
		List results = new ArrayList();
		List messages = (List) userMessages.get(key);
		if (messages != null) {
			results.addAll(messages);
			userMessages.remove(key);
		}
		return results;
	}

	public Boolean isOffline(Integer empID, int connectID) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		if (this.offlines.containsKey(key)) {
			offlines.remove(key);
			logout(key);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public Boolean singleMode(Integer nodeID, String nodeName, Integer empID,
			int connectID) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		logger.debug("start lock nodeName" + nodeName);
		if (this.singleModes.containsKey(nodeID)) {
			logger.debug("the node[" + nodeName + "] is locked");
			return Boolean.FALSE;

		} else {
			logger.debug("lock the node[" + nodeName + "],key[" + key + "]");
			// OnlineVO online=(OnlineVO)this.onlineUsers.get(key);
			LockVO vo = new LockVO(nodeID, nodeName, key, Calendar
					.getInstance(), "", empID);
			singleModes.put(nodeID, vo);
			logger.debug("lock finish");
			return Boolean.TRUE;
		}
	}

	public void cleanSignleMode(Integer nodeID) {
		singleModes.remove(nodeID);
		logger.debug("unlock nodeID[" + nodeID + "]");
	}

	public void cleanSignleModeByKey(Integer empID, int connectID) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		Iterator it = singleModes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key1 = entry.getKey();
			LockVO value = (LockVO) entry.getValue();
			if (value.getLockConnectID().equals(key)) {
				singleModes.remove(key1);
			}
			logger.debug("unlock key[" + key + "]");
		}
	}

	public void login(Integer empID, int connectID) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		loginMap.put(key, Calendar.getInstance());
	}

	public void switchRole(Integer empID, int connectID, String module,
			String role) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		currentRoles.put(key, module + "," + role);
	}

	public void onMethod(Integer empID, int connectID, String methodName) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		onMethods.put(key, methodName);
	}

	public void logout(Integer empID, int connectID) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		logout(key);
	}

	public void logout(Integer key) {
		loginMap.remove(key);
		onlineUsers.remove(key);
	}

	public void sendMessage(Integer key, String message) {
		List messages = (List) userMessages.get(key);
		if (messages == null)
			messages = new ArrayList();
		messages.add(message);
		userMessages.put(key, messages);
	}

	public void publishMessage(String message) {
		Iterator iter = onlineUsers.keySet().iterator();
		while (iter.hasNext()) {
			Integer key = (Integer) iter.next();
			sendMessage(key, message);
		}
	}

	public void offline(int connectID, Integer empID) {
		Integer key = new Integer(("" + connectID).substring(0, 3) + empID);
		offline(key);
	}

	public void offline(Integer key) {
		offlines.put(key, "1");// 表示剔用户

	}

	public PageList getOnlineList() {
		logger.debug("get online user");
		List result = new ArrayList();
		Iterator iter = onlineUsers.values().iterator();
		long currentLong = Calendar.getInstance().getTimeInMillis();
		while (iter.hasNext()) {
			// Map.Entry entry = (Map.Entry) iter.next();
			OnlineVO vo = (OnlineVO) iter.next();
			if (currentLong - vo.getLastUpdateMillis() > this.onlineValidPeriod) {
				vo.setStatus(false);
			}
			Calendar loginDate = (Calendar) this.loginMap.get(vo.getId());
			vo.setLoginDate(loginDate);
			String role = (String) this.currentRoles.get(vo.getId());
			if (role != null) {
				String[] arrays = role.split(",");
				if (arrays.length > 1) {
					vo.setModuleName(arrays[0]);
					vo.setRolename(arrays[1]);
				}
			}
			String method = (String) onMethods.get(vo.getId());
			vo.setOnMethod(method);
			// logger.debug("get online user "+vo.getId());
			result.add(vo);
		}
		PageList pageList = new PageList();
		pageList.setResults(result.size());
		pageList.setItems(result);
		return pageList;
	}

	public PageList getLocksList() {
		List result = new ArrayList();
		Iterator it = singleModes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			LockVO value = (LockVO) entry.getValue();
			OnlineVO online = (OnlineVO) onlineUsers.get(value
					.getLockConnectID());
			value.setEmpID(online.getEmpId());
			value.setEmpName(online.getEmpName());
			value.setIp(online.getIp());
			result.add(value);
		}
		PageList pageList = new PageList();
		pageList.setResults(result.size());
		pageList.setItems(result);
		return pageList;
	}

	public void ejbCreate() {

	}
	
	public void putUserToken(Integer empId,String token){
		if(empId==null||token==null){
			return ;
		}
		userTokens.put(empId, token);
	}
	
	public String getUserToken(Integer empId){
		if(empId==null) return null;
		if(userTokens.containsKey(empId)){
			return (String)userTokens.get("empId");
		}else{
			return null;
		}
	}
	
	public Object getCustomValue(Object key){
		if(key==null) return null;
		if(customVars.containsKey(key)){
			return (String)customVars.get("empId");
		}else{
			return null;
		}
	}
	
	public void setCustomValue(Object key,Object value){
		if(key==null||value==null){
			return ;
		}
		customVars.put(key, value);
	}
	
    
	

//	public void setSessionContext(SessionContext sessionContext)
//			throws EJBException, RemoteException {
//		logger.debug("help EJB has create");
//		this.sessionContext = sessionContext;
//		properties = new Properties();
//		backgrounds = new HashMap();
//		onlineUsers = new HashMap();
//		offlines = new HashMap();
//		loginMap = new HashMap();
//		currentRoles = new HashMap();
//		singleModes = new HashMap();
//		onMethods = new HashMap();
//		this.userTokens=new HashMap();
//		this.customVars=new HashMap();
//		this.userMessages = new HashMap();
//		this.backgroundsErrors = new HashMap();
//		completeBackgrounds=new HashMap();
//		String onlineValidStr = this.getProperty("DEFAULT_ONLINE_VALID");
//		try {
//			onlineValidPeriod = Integer.parseInt(onlineValidStr);
//		} catch (Exception ee) {
//			onlineValidPeriod = DEFAULT_ONLINE_VALID;
//		}
//		setPropertiesAndNotices();
//	
//
//	}
	
	public void setPropertiesAndNotices(){
		noticeList = new ArrayList();
		Session hibernateSession;
		try {
			hibernateSession = HibernateUtil.currentSession();
			Criteria criteria = hibernateSession
					.createCriteria(SystemProperties.class);
			Iterator iter = criteria.list().iterator();
			while (iter.hasNext()) {
				SystemProperties systemProperties = (SystemProperties) iter
						.next();
				
				//logger.debug("initialize property["+systemProperties.getId()+"]=["+systemProperties.getValue()+"]");
				properties.setProperty(systemProperties.getId(),
						systemProperties.getValue());
			}
			Criteria criteria2 = hibernateSession.createCriteria(Notice.class);

			criteria2.add(Expression.le("startDate", Calendar.getInstance()));

			criteria2.add(Expression.ge("endDate", Calendar.getInstance()));
			criteria2.addOrder(Order.desc("startDate"));
			iter = criteria2.list().iterator();
			while (iter.hasNext()) {
				Notice bo = (Notice) iter.next();
				NoticeVO vo = (NoticeVO) bo.toVO();
				noticeList.add(vo);
			}
		} catch (HibernateException ee) {
			logger.error("数据库异常", ee);
			throw new CommonException("数据库异常" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
		
		//clean token
		userTokens=new HashMap();
		customVars=new HashMap();	
	}

	public String getProperty(String name) {
		return (String) properties.get(name);
	}

	public void setProperty(String name, String value) {
		String origValue = (String) properties.setProperty(name, value);
	}

	

	public List getNoticeList() {
		return noticeList;
	}

	public void addNotice(NoticeVO vo) {
		noticeList.add(vo);
	}

	public void cleanSignleModeByKey(String empID, int connectID)
			throws RemoteException {
		cleanSignleModeByKey(convertEmpID(empID),connectID);
		
	}

	public List getBackgrounds(String empID) throws RemoteException {
		return getBackgrounds(convertEmpID(empID));
	}

	public List getCompleteBackgrounds(String empId) throws RemoteException {
		return getCompleteBackgrounds(convertEmpID(empId));
	}

	public List getErrorBackgrounds(String empID) throws RemoteException {
		return getErrorBackgrounds(convertEmpID(empID));
	}

	public List getMessage(String empID, int connectID, String ip)
			throws RemoteException {
		return getMessage(convertEmpID(empID),connectID,ip);
	}

	public Boolean isOffline(String empID, int connectID)
			throws RemoteException {
	return isOffline(convertEmpID(empID),connectID);
	}

	public void login(String empID, int connectID) throws RemoteException {
		login(convertEmpID(empID),connectID);
		
	}

	public void logout(String empID, int connectID) throws RemoteException {
		logout(convertEmpID(empID),connectID);
		
	}

	public void offline(int connectID, String empID) throws RemoteException {
		offline(connectID,convertEmpID(empID));
		
	}

	public void onMethod(String empID, int connectID, String methodName)
			throws RemoteException {
		onMethod(convertEmpID(empID),connectID,methodName);
		
	}

	public Boolean singleMode(Integer nodeID, String nodeName, String empID,
			int connectID) throws RemoteException {
		return singleMode(nodeID,nodeName,convertEmpID(empID),connectID);
	}

	public void switchRole(String empID, int connectID, String module,
			String role) throws RemoteException {
		switchRole(convertEmpID(empID),connectID,module,role);
		
	}
	private Integer convertEmpID(String empID){
		return SemAppUtils.getInteger(empID);
	}

}
