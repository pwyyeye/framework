package com.xxl.os.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.xxl.os.dao.OnlineDAO;

import common.dao.impl.BaseDAOImpl;
import common.os.vo.UsersVO;
import common.os.vo.exception.OSException;
import common.utils.SemAppUtils;

@Repository("onlineDAO")
public class OnlineDAOImpl extends BaseDAOImpl implements OnlineDAO {

	public Log logger = LogFactory.getLog(this.getClass());

	public String getUserToken(final Integer empId) {
		
		String token = null;
		String sql = " select t.ol_authkey from EIA_ONLINE t WHERE  "
				+ "t.OL_USERID=?";
		List<Map<String, Object>> list=find_sql_toMap(sql,new Integer[]{empId});
		if(list.size()==0) return token;
		Map<String, Object> map=list.get(0);
		token = (String) map.get("ol_authkey");
		return token;
	}

	public UsersVO getEofficeLoginUser(final String ip, final String authKey,
			final Calendar expiredTime, final int type, final boolean isTest,
			final boolean checkActive) throws OSException {
		logger.debug("authKey=" + authKey);
		UsersVO user = null;
		try {
			
			String sql = "SELECT us.US_ID, us.DE_ID, us.NAME, us.LOGINID, us.CODE, us.SEX,"
					+ "us.EDUCATELEVEL, us.ARCHADDR, us.CREDENTIALTYPE, us.CREDENTIALNO, us.TEL, us.WECHAT, us.QQ,"
					+ "us.WEIBO, us.EMAIL, us.MOBILE, us.HOUSETEL, us.TITLE, us.us_ID, us.MARK, us.STATUS,"
					+ "us.ENGINEER, us.BIRTHDAY, us.BLOODTYPE, us.ISDIRECTOR,de.DE_NAME"
					+ " FROM SY_USERS us,SY_DEPARTMENT de,EIA_ONLINE T WHERE  us.DE_ID=de.DE_ID  and   us.US_ID=T.OL_USERID ";

			int i = 1;
			if (!isTest) {
				sql = sql
						+ " AND T.OL_STATUS='1' AND T.OL_IP=? ";
				i = 2;
				if (checkActive) {
					sql = sql + "and T.OL_ACTIVETIME >=?";
					i = 3;
				}
			}
			if (type == 0) {
				sql = sql + " AND T.OL_AUTHKEY=?";
			} else {
				sql = sql + " AND us.CODE=?";
			}
			String[] args = new String[i];
			int point = 0;
			if (!isTest) {
				args[0] = ip;
				if (checkActive) {
					args[1] = SemAppUtils
							.getFullTime(expiredTime);
					point = 2;
				} else {
					point = 1;
				}
			}
			if (type == 0) {
				args[point] = authKey;
			} else {
				args[point] = authKey;
			}
			
			List<Map<String, Object>> list=find_sql_toMap(sql,args);
			Map<String, Object> map=list.get(0);
			if(map.size()==0){
				logger.error("非法token");
				return null;
			}
			user=getUserFromMap(map);
			return user;
		} catch (Exception e) {
			logger.error("获取在线用户信息失败：", e);
			throw new OSException("获取在线用户信息失败：", e);
		}
	}
	private UsersVO getUserFromMap(Map rs) throws Exception {
		UsersVO user = new UsersVO();
		user.setId((Integer)rs.get("US_ID"));
		user.setDepartment((Integer)rs.get("DE_ID"));
		user.setName((String)rs.get("NAME"));
		user.setLoginId((String)rs.get("LOGINID"));
		user.setCode((String)rs.get("CODE"));
		user.setSex((String)rs.get("SEX"));
		user.setEducateLevel((Integer)rs.get("EDUCATELEVEL"));
		user.setArchAddr((String)rs.get("ARCHADDR"));
		user.setCredentialType((Integer)rs.get("US_CREDENTIALTYPE"));
		user.setCredentialNo((String)rs.get("CREDENTIALNO"));
		user.setTel((String)rs.get("TEL"));
		user.setWechat((String)rs.get("WECHAT"));
		user.setQq((String)rs.get("QQ"));
		user.setWeibo((String)rs.get("EMAIL"));
		user.setEmail((String)rs.get("WEIBO"));
		user.setMobile((String)rs.get("MOBILE"));
		user.setHousetel((String)rs.get("HOUSETEL"));
		user.setTitle((String)rs.get("TITLE"));
		user.setMark((String)rs.get("MARK"));
		user.setStatus((String)rs.get("STATUS"));
		user.setEngineer((String)rs.get("ENGINEER"));
		Calendar cal=Calendar.getInstance();
		cal.setTime((new java.util.Date(((java.sql.Date)rs.get("BIRTHDAY")).getTime())));
		user.setBirthday(cal);
		user.setBloodType((String)rs.get("BLOODTYPE"));
		user.setDepartmentName((String)rs.get("DE_NAME"));
		user.setLevel((Integer)(rs.get("level")==null?0:rs.get("level")));
		return user;
	}

}
