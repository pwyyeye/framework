package com.xxl.baseService.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.xxl.bussiness.EofficeDB;
import com.xxl.bussiness.LdapAuth;
import com.xxl.bussiness.MailSender;
import com.xxl.bussiness.MessageObservable;
import com.xxl.bussiness.MobileDB;
import com.xxl.bussiness.NewEofficeDB;
import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;
import com.xxl.facade.StructureRemote;

import common.bussiness.Department;
import common.bussiness.EncrypDes;
import common.bussiness.Message;
import common.bussiness.CommException;
import common.bussiness.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommonException;
import common.jms.vo.UserPushLogVO;
import common.os.vo.DepartmentVO;
import common.os.vo.OrganiseVO;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.MailMessage;
import common.value.MobileMessage;
import common.value.PageList;
import common.value.SemMessageObject;
import common.web.bean.SessionUserBean;

@Service("commonRemote")
public class CommonService implements CommonRemote {
	SessionContext sessionContext;

	public Log logger = LogFactory.getLog(this.getClass());

	AdminRemote adminSession;

//	AlphaUddi uddiSession;

	private QueueConnectionFactory qcFactory;

	private QueueSession qSession;

	private QueueConnection qConnection;

	private QueueSender qSender;

	private Queue queSJ;

	private HelperRemote helperRemote;

	private StructureRemote structureRemote;

//	private JMSTaskRemote jmsTaskRemote;

	private boolean isTest = true;

	private boolean isLDAP = false;

	private boolean userExternalOS = false;

	// for mobile message
	private String smsUrl;
	private String smsUsername;
	private String smsPassword;
	private String smsPort;
	private String smsAppId;
	private String smsSenderId;
	// for rongyun im config
	private String imUrl;
	private String imUsername;
	private String imPassword;
	private EncrypDes des;

	// for encrypDES
	private String secretKey;



	InitialContext context;


	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public UsersVO getDeptTopDirectorVO(String deptID) throws Exception {
		if (userExternalOS) {
			EofficeDB eofficeDB = EofficeDB.getTheInstance();
			return eofficeDB.getDeptTopDirector("" + deptID);
		} else {
			return structureRemote.getDeptTopDirector(deptID);
		}
	}

	public User getDeptTopDirector(String deptID) throws Exception {

		return SemAppUtils.vo2User(getDeptTopDirectorVO(deptID));
	}

	public DepartmentVO getDeptOfLevel(String deptID, Integer level)
			throws Exception {
		if (userExternalOS) {
			EofficeDB eofficeDB = EofficeDB.getTheInstance();
			return eofficeDB.getDeptOfLevel("" + deptID, "" + level);
		} else {
			return structureRemote.getDeptOfLevel(deptID, level);
		}
	}

	public Department getDeptOfLevel(String deptID, String level)
			throws Exception, RemoteException {
		EofficeDB eofficeDB = EofficeDB.getTheInstance();
		DepartmentVO vo = eofficeDB.getDeptOfLevel(deptID, level);
		return SemAppUtils.vo2Dept(vo);
	}

	public Department getDepartmentInfo(Integer deptID) throws Exception,
			RemoteException {
		NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
		DepartmentVO vo = eofficeDB.getDepartmentInfo("" + deptID);
		return SemAppUtils.vo2Dept(vo);
	}

	public UsersVO getUserInfo(Integer empID) throws Exception {
		if (userExternalOS) {
			if (getHRStructure()) {
				EofficeDB eofficeDB = EofficeDB.getTheInstance();
				return eofficeDB.getUserInfo("" + empID);
			} else {
				NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
				return eofficeDB.getUserInfo("" + empID);
			}
		} else {
			return structureRemote.getUserInfo(empID);
		}

	}

	public User getUserInfo(String empID) throws Exception, RemoteException {
		if (getHRStructure()) {
			EofficeDB eofficeDB = EofficeDB.getTheInstance();
			return SemAppUtils.vo2User(eofficeDB.getUserInfo(empID.trim()));
		} else {
			NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
			return SemAppUtils.vo2User(eofficeDB.getUserInfo(empID.trim()));
		}
	}

	public DepartmentVO getDepartmentInfoVO(String deptID) throws Exception {
		if (userExternalOS) {
			NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
			return eofficeDB.getDepartmentInfo("" + deptID);
		} else {
			return structureRemote.getDepartment(deptID);
		}

	}

	public Integer sendQgateInfoToOA(String title, String dpnoNo,
			String itemNo, String userCode, String strType) throws Exception {
		EofficeDB eofficeDB = EofficeDB.getTheInstance();
		return eofficeDB.sendQgateInfoToOA(title, dpnoNo, itemNo, userCode,
				strType);
	}

	public String getUserToken(Integer empID) throws Exception {
		return "test OK!";
//		if (userExternalOS) {
//			NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
//			return eofficeDB.getUserToken("" + empID);
//		} else {
//			return structureRemote.getUserToken(empID);
//		}
	}

	public String getUserToken(String empID) throws Exception, RemoteException {
		return getUserToken(SemAppUtils.getInteger(empID));
	}

	public UsersVO verifyUsersVO(String loginid, String password)
			throws BaseException {
		try {
			String empid = null;
			if (this.isLDAP) {// LDAP
				LdapAuth ldap = new LdapAuth();
				logger.debug("login on" + loginid + ",isTest=" + isTest);
				empid = ldap.logonByUserName(loginid, password);
			} else {// DB
				empid = "" + structureRemote.verifyUser(loginid, password);
				logger.debug("verify user result is " + empid);
			}
			if (empid != null) {
				UsersVO vo = getUserInfo(new Integer(empid));
				// return SemAppUtils.vo2User(vo);
				return vo;
			} else {
				throw new CommException("非法用户");
			}
		} catch (BaseException be) {
			throw be;
		} catch (Exception ee) {
			throw new BaseException(ee);
		}
	}

	public User verifyUser(String loginid, String password)
			throws BaseException {

		return SemAppUtils.vo2User(verifyUsersVO(loginid, password));
	}

	private Calendar getExpiredTime() {

		int timeout = 120; // Ĭ��Ϊ2s
		try {

			timeout = Integer.parseInt(helperRemote
					.getProperty("SYSTEM.SSO.TIMEOUT"));

		} catch (RemoteException ex) {
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -timeout); // 2����Ч
		return cal;
	}

	private boolean getCheckActive() {
		String checkActiveStr;

		try {
			checkActiveStr = helperRemote.getProperty("SYSTEM.SSO.CHECKACTIVE");
		} catch (RemoteException e) {
			return false;
		}

		return "true".equalsIgnoreCase(checkActiveStr)
				|| "yes".equalsIgnoreCase(checkActiveStr);
	}

	private boolean getCheckIP() {
		String checkIPStr;

		try {
			checkIPStr = helperRemote.getProperty("SYSTEM.SSO.CHECKIP");
		} catch (RemoteException e) {
			return false;
		}

		return "true".equalsIgnoreCase(checkIPStr)
				|| "yes".equalsIgnoreCase(checkIPStr);
	}

	public UsersVO getEofficeLoginUserVO(String openId) throws BaseException,
			BaseBusinessException, RemoteException{
		UsersVO vo = structureRemote.getEofficeLoginUser(openId);
		return vo;
	}

	public UsersVO getEofficeLoginUserVO(String key, String ip)
			throws CommException {
		// if (key == null || key.length() <= 18)
		// return null; // λ��С��18λ������;
		// String rowid = key.substring(0, 18);
		// String authKey = key.substring(18);
		String rowid = "";
		String authKey = key;
		boolean systemAuth = false;
		// 特殊处理
		try {
			String systemAuthKey = helperRemote
					.getProperty("SYSTEM.SSO.AUTHKEY");

			if (authKey.equals(systemAuthKey)) {
				logger.info("system user enter by default key[" + key + "],ip["
						+ ip + "]");
				ip = helperRemote.getProperty("SYSTEM.SSO.COMMONIP");
				rowid = helperRemote.getProperty("SYSTEM.SSO.ROWID");
				systemAuth = true;
			}
		} catch (Exception ee) {
			logger.error("系统配置错误", ee);
		}
		try {
			if (userExternalOS) {
				NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
				UsersVO vo = eofficeDB.getEofficeLoginUser(rowid, ip, authKey,
						getExpiredTime(), 0, isTest || systemAuth,
						getCheckActive(), getCheckActive());
				return vo;
			} else {
				UsersVO vo = structureRemote.getEofficeLoginUser(rowid, ip,
						authKey, getExpiredTime(), 0, isTest || systemAuth,
						getCheckActive());
				return vo;
			}
		} catch (Exception e) {
			throw new CommException("获取用户token失败", e);
		}

	}

	public User getEofficeLoginUser(String key, String ip) throws CommException {
		UsersVO vo = getEofficeLoginUserVO(key, ip);
		return SemAppUtils.vo2User(vo);

		// return EofficeDB.getTheInstance().getEofficeLoginUser(rowid, ip,
		// authKey, getExpiredTime(), 0, isTest||systemAuth, getCheckActive());
	}

	public List getUserOfDept(String deptID) throws CommException {
		if (userExternalOS) {
			return NewEofficeDB.getTheInstance().getUserOfDept(deptID);
		} else {
			return null;
		}
	}

	public UsersVO getEofficeLoginUserByEmpID(Integer empID, String ip)
			throws CommException {
		// return null;
		if (userExternalOS) {
			return EofficeDB.getTheInstance().getEofficeLoginUser("" + empID,
					ip, "", getExpiredTime(), 1, isTest, getCheckActive());
		} else {
			return null;
		}
	}

	public User getEofficeLoginUserByEmpID(String empID, String ip)
			throws CommException, RemoteException {
		UsersVO vo = getEofficeLoginUserByEmpID(SemAppUtils.getInteger(empID),
				ip);
		return SemAppUtils.vo2User(vo);
	}

	public String insertMessageToProcess(String title, String createUser,
			Calendar createDate, String content, String attachID,
			String backType, String backDate, Calendar limitDate,
			String status, Calendar scheduleDate, String addCalendar,
			String[] copyUsers, String[] dealUsers) throws Exception {
		if (userExternalOS) {
			EofficeDB eofficeDB = EofficeDB.getTheInstance();
			return ""
					+ eofficeDB.insertMessageToProcess(title, createUser,
							createDate, content, Integer.parseInt(attachID),
							backType, backDate, limitDate, status,
							scheduleDate, addCalendar, copyUsers, dealUsers);
		} else {
			return null;
		}

	}

	public String sendMessageByOA(String content, String sendUser,
			String[] receiveUsers, String type) throws CommException {
		if (userExternalOS) {
			EofficeDB eofficeDB = EofficeDB.getTheInstance();
			return ""
					+ eofficeDB.sendMessageByOA(content, sendUser,
							receiveUsers, type);
		} else {
			return null;
		}

	}

	public String sendMessageByMail(String to, String cc, String subject,
			String text, String from, String host) throws CommException {
		logger.debug("send mail from EJB:to" + to);
		// MailSender sender = MailSender.getTheInstance();
		// sender.sendMail(to, cc, subject, text, from, host);
		MailMessage message = new MailMessage(new String[] { to },
				new String[] { cc }, subject, text, from, host, null);
		this.sendMail(message);
		logger.debug("send mail end from EJB:to" + to);
		return null;
	}

	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host) throws CommException {
		logger.debug("send mail from EJB:to" + to);
		// MailSender sender = MailSender.getTheInstance();
		// sender.sendMail(to, cc, subject, text, from, host);
		MailMessage message = new MailMessage(to, cc, subject, text, from,
				host, null);
		this.sendMail(message);
		logger.debug("send mail end from EJB:to" + to);
		return null;
	}

	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host, String[] attachFile)
			throws CommException {
		logger.debug("send mail from EJB:to" + to);
		// MailSender sender = MailSender.getTheInstance();
		// sender.sendMail(to, cc, subject, text, from, host,attachFile);
		MailMessage message = new MailMessage(to, cc, subject, text, from,
				host, attachFile);
		this.sendMail(message);
		logger.debug("send mail end from EJB:to" + to);
		return null;
	}

	public String sendMessageByMail(MailMessage message) throws CommException {
		MailSender sender = MailSender.getTheInstance();
		sender.sendMail(message);
		return "0";
		// return sendMessageByMail(message);
	}

	public void sendMail(MailMessage message) throws CommException {
		logger.debug("start send mail");
		SemMessageObject messageObject = new SemMessageObject(null, message,
				new Integer(SemAppConstants.MAIL_QUEUE));
		ObjectMessage objectMessage;
		try {
			objectMessage = qSession.createObjectMessage(messageObject);
			qSender.send(objectMessage);
		} catch (JMSException e) {
			logger.error("后台发送邮件失败", e);
			throw new CommException(e);
		}
		logger.debug("start send mail end");
	}

	public String publishMessage(Message message) throws CommException {
		logger.debug("publist message,messageID:[" + message.getType() + "]");
		MessageObservable obserable = new MessageObservable();
		obserable.sendMessage(message);
		return "0";
	}

	public void sendMessageByMobile(MobileMessage message) throws CommException {
		try {
			MobileDB.init();
			MobileDB sender = MobileDB.getTheInstance();
			sender
					.sentMobileMsg(message.getDestMobiles(), message
							.getContent());
		} catch (NamingException ex) {
			throw new CommException(ex);
		} catch (SQLException ex) {
			throw new CommException(ex);
		} finally {
			MobileDB.end();
		}
	}

	public void sendMessageByMobile(String destMobile, String[] message)
			throws BaseException, BaseBusinessException {
		this.sendMessageByMobile(destMobile, message, smsSenderId);
	}

	public void sendMessageByMobile(String destMobile, String[] message,
			String customSenderId) throws BaseException, BaseBusinessException {
		if (SemAppUtils.isEmpty(destMobile)) {
			throw new BaseBusinessException("电话号码不存在或短信内容为空");
		}
//		HashMap result = null;
//		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//		restAPI.init(smsUrl, smsPort);
//		restAPI.setAccount(smsUsername, smsPassword);
//		restAPI.setAppId(smsAppId);
//		result = restAPI.sendTemplateSMS(destMobile, customSenderId, message);
//		logger.info("SDKTestGetSubAccounts result=" + result);
//		if (!"000000".equals(result.get("statusCode")))
//			throw new BaseException("错误码=" + result.get("statusCode")
//					+ " 错误信息= " + result.get("statusMsg"));
	}

	/**
	 * try { StringBuffer params=new StringBuffer(); params.append("content=");
	 * params.append(java.net.URLEncoder .encode(message, "GBK"));
	 * params.append("&user="); params.append(smsUsername);
	 * params.append("&mobile="); params.append(destMobile);
	 * params.append("&pass="); params.append(smsPassword); String u = smsUrl
	 * +(smsUrl.contains("?")?"&":"?")+ params.toString(); URL url = new URL(u);
	 * HttpURLConnection conn; conn = (HttpURLConnection) url.openConnection();
	 * conn.setDoOutput(true); conn.setRequestMethod("POST");
	 * conn.setUseCaches(false); conn.setRequestProperty("Content-type",
	 * "application/x-www-form-urlencoded");
	 * conn.setRequestProperty("Connection", "Close");
	 * conn.setRequestProperty("Content-length", String.valueOf(params
	 * .length())); conn.setDoInput(true); conn.connect(); OutputStreamWriter
	 * out = new OutputStreamWriter(conn .getOutputStream(), "utf-8");
	 * out.write(params.toString()); out.flush(); out.close(); InputStream in =
	 * conn.getInputStream(); InputStreamReader r = new InputStreamReader(in);
	 * LineNumberReader din = new LineNumberReader(r); String line = null;
	 * StringBuffer sb = new StringBuffer(); while ((line = din.readLine()) !=
	 * null) { sb.append(line + "\n"); } if ((sb.toString()) != "100") { throw
	 * new BaseException("发送短信失败,错误码["+sb.toString()+"]"); } } catch (Exception
	 * e) { this.handleException(e); }
	 */

	public SessionUserBean getSemSessionUser(UsersVO theUser, Integer systemID,
			String ip) throws CommException {
		try {

			String token = getUserToken((Integer) theUser.getId());
			// return null;
			return adminSession
					.getSessionUserBean(theUser, systemID, ip, token);
		} catch (RemoteException e) {
			logger.error("调用ADMIN EJB服务失败", e);
			throw new CommException("调用ADMIN EJB服务失败", e);
		} catch (CreateException e) {
			logger.error("调用ADMIN EJB服务失败", e);
			throw new CommException("调用ADMIN EJB服务失败", e);
		} catch (Exception e) {
			logger.error("调用ADMIN EJB服务失败", e);
			throw new CommException("调用ADMIN EJB服务失败", e);
		}
	}

	public SessionUserBean getSemSessionUser(User theUser, Integer systemID,
			String ip) throws CommException {
		UsersVO vo = SemAppUtils.user2VO(theUser);
		return getSemSessionUser(vo, systemID, ip);
	}

	public void logonOASystem(Integer empID, String ip) throws CommException {

		try {
			if (userExternalOS) {
				NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
				eofficeDB.logonOASystem("" + empID, ip);
			} else {
				structureRemote.logonOASystem(empID, ip);
			}
		} catch (Exception e) {
			throw new CommException(e);
		}
	}

	public void logonOASystem(String empID, String ip) throws CommException {

		try {
			if (userExternalOS) {
				NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
				eofficeDB.logonOASystem(empID, ip);
			} else {
				structureRemote.logonOASystem(new Integer(empID), ip);
			}
		} catch (Exception e) {
			throw new CommException(e);
		}
	}

	public Serializable invokeEjbServices(Integer serviceId,
			Serializable[] argments) throws CommonException,
			BaseBusinessException {
		try {
//			return uddiSession.invokeEjbServices(serviceId, argments);
			return null;
//		} catch (RemoteException e) {
		} catch (Exception e) {
			logger.error("调用UDDI EJB服务失败", e);
			throw new CommonException("调用UDDI EJB服务失败", e);
		}
	}

	public Boolean isTest() {
		return new Boolean(isTest);
	}

	private boolean getHRStructure() {
		String hrStr;
		try {
			hrStr = helperRemote.getProperty("USER.HR.STRUCTURE");
			return "true".equalsIgnoreCase(hrStr)
					|| "yes".equalsIgnoreCase(hrStr);
		} catch (RemoteException e) {
			return false;
		}
	}

	public String encrytor(String data) throws BaseException,
			BaseBusinessException {
		try {
			return des.encrypt(data);
		} catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	public String decrytor(String data) throws BaseException,
			BaseBusinessException {
		try {
			return des.decrypt(data);
		} catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	public String encrytor(String data, String key) throws BaseException,
			BaseBusinessException {
		try {
			EncrypDes customDes = new EncrypDes(key, false);
			return customDes.encrypt(data);
		} catch (Exception e) {
			this.handleException(e);
			return null;
		}

	}

	public String decrytor(String data, String key) throws BaseException,
			BaseBusinessException {
		try {
			EncrypDes customDes = new EncrypDes(key, false);
			return customDes.decrypt(data);
		} catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	public Integer registerUser(String username, String password)
			throws BaseException, BaseBusinessException {
		UsersVO vo = new UsersVO();
		// vo.setId(username);
		vo.setPassword(password);
		vo.setLoginId(username);
		vo.setMobile(username);
		vo.setName(username);
		vo.setCode(username);
		vo.setStatus("0");
		vo.setSex("0");
		vo.setDepartment(new Integer(888));
		try {
			return structureRemote.addUsers(vo);
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public Integer registerUser(String username, String password, Integer type,
			String openId) throws BaseException, BaseBusinessException {
		UsersVO vo = new UsersVO();
		// vo.setId(username);
		vo.setPassword(password);
		vo.setLoginId(username);
		vo.setMobile(username);
		vo.setName(username);
		vo.setCode(username);
		vo.setStatus("0");
		vo.setSex("0");
		vo.setDepartment(new Integer(888));
		logger.debug("type=" + type + ",openId=" + openId);
		if (type != null) {
			switch (type.intValue()) {
			case 1:// QQ
				vo.setQq(openId);
				break;
			case 2:// wechat
				vo.setWechat(openId);
				break;
			case 3:
				vo.setWeibo(openId);
				break;
			}
		} else {
			// default wechat
			vo.setWechat(openId);
		}
		try {
			return structureRemote.addUsers(vo);
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public void changePassword(String username, String password,
			String oldPassword) throws BaseException, BaseBusinessException {
		try {
			structureRemote.changePassword(username, password, oldPassword);
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void changeName(String username, String nick, String icon)
			throws BaseException, BaseBusinessException {
		try {
			structureRemote.changeName(username, nick, icon);
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void changeName(String username, String nick, String icon,
			String sex, Calendar birthday) throws BaseException,
			BaseBusinessException {
		try {
			structureRemote.changeName(username, nick, icon, sex, birthday);
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void handleException(Exception ee) throws BaseException,
			BaseBusinessException {
		if (ee instanceof BaseException) {
			logger.error(((BaseException) ee).getErrMsg(), ee);
			throw (BaseException) ee;
		} else if (ee instanceof BaseBusinessException) {
			throw (BaseBusinessException) ee;
		} else {
			logger.error("服务器异常", ee);
			throw new BaseException("服务器异常", ee);
		}
	}

	public Integer addOrganise(OrganiseVO vo) throws BaseException,
			BaseBusinessException {

		try {
			return structureRemote.addOrganise(vo);
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	
	public List getSubOrganises(int organise) throws BaseException {
		try {
			return structureRemote.getSubOrganises(organise);
		} catch (Exception e) {
			logger.error("get sub organise fail", e);
			throw new BaseException("get sub organise fail", e);
		}
	}
}