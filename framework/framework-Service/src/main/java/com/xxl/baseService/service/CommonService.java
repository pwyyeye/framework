package com.xxl.baseService.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxl.baseService.dao.IFrameworkDao;
import com.xxl.bussiness.LdapAuth;
import com.xxl.bussiness.NewEofficeDB;
import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;
import com.xxl.facade.StructureRemote;

import common.bussiness.Department;
import common.bussiness.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommException;
import common.exception.CommonException;
import common.os.vo.DepartmentVO;
import common.os.vo.OrganiseVO;
import common.os.vo.UsersVO;
import common.security.EncrypDes;
import common.utils.SemAppUtils;
import common.web.bean.SessionUserBean;

@Service("commonRemote")
public class CommonService implements CommonRemote {

	public Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	AdminRemote adminRemote;


	@Autowired
	private HelperRemote helperRemote;

	@Autowired
	private StructureRemote structureRemote;

//	private JMSTaskRemote jmsTaskRemote;
	
	@Autowired
	private IFrameworkDao frameworkDAO;

	private boolean isTest = true;

	private boolean isLDAP = false;

	private boolean userExternalOS = false;

	private EncrypDes des;

	// for encrypDES
	private String secretKey;

	public UsersVO getDeptTopDirectorVO(String deptID) throws Exception {
		return structureRemote.getDeptTopDirector(deptID);
	}

	public User getDeptTopDirector(String deptID) throws Exception {

		return SemAppUtils.vo2User(getDeptTopDirectorVO(deptID));
	}

	public DepartmentVO getDeptOfLevel(String deptID, Integer level)
			throws Exception {
		return structureRemote.getDeptOfLevel(deptID, level);
	}

	public Department getDeptOfLevel(String deptID, String level)
			throws Exception, RemoteException {
		DepartmentVO vo = getDeptOfLevel(deptID, Integer.parseInt(level));
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
			return frameworkDAO.getUserInfo(empID+"");
		} else {
			return structureRemote.getUserInfo(empID);
		}

	}

	public User getUserInfo(String empID) throws Exception, RemoteException {
		return SemAppUtils.vo2User(frameworkDAO.getUserInfo(empID+""));
	}

	public DepartmentVO getDepartmentInfoVO(String deptID) throws Exception {
		if (userExternalOS) {
			NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
			return eofficeDB.getDepartmentInfo("" + deptID);
		} else {
			return structureRemote.getDepartment(deptID);
		}

	}


	public String getUserToken(Integer empID) throws Exception {
		if (userExternalOS) {
			NewEofficeDB eofficeDB = NewEofficeDB.getTheInstance();
			return eofficeDB.getUserToken("" + empID);
		} else {
			return structureRemote.getUserToken(empID);
		}
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
				logger.debug(SemAppUtils.getJsonFromBean(vo));
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

		int timeout = 120;
		try {

			timeout = Integer.parseInt(helperRemote
					.getProperty("SYSTEM.SSO.TIMEOUT"));

		} catch (RemoteException ex) {
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -timeout);
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
	}

	public List getUserOfDept(String deptID) throws Exception {
		if (userExternalOS) {
			return NewEofficeDB.getTheInstance().getUserOfDept(deptID);
		} else {
			return null;
		}
	}

	public SessionUserBean getSemSessionUser(UsersVO theUser, Integer systemID,
			String ip) throws CommException {
		try {

			String token = getUserToken((Integer) theUser.getId());
			return adminRemote
					.getSessionUserBean(theUser, systemID, ip, token);
		} catch (RemoteException e) {
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

	public EncrypDes initDes(EncrypDes des){
		if(des!=null) return des;
		try {
			secretKey = helperRemote.getProperty("DES_SECRET_KEY");
			des = new EncrypDes(secretKey);
			return des;
		} catch (Exception e) {
			logger.error("initDes失败", e);
		} 
		return des;
	}
	public String encrytor(String data) throws BaseException,
			BaseBusinessException {
		try {
			des=initDes(des);
			return des.encrypt(data);
		} catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	public String decrytor(String data) throws BaseException,
			BaseBusinessException {
		try {
			des=initDes(des);
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
