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
		
//		String sql = " select t.rowid||t.ol_authkey as token from sy_onlineinfo t WHERE t.OL_USERID=?";
		String sql = " select t.ol_authkey from EIA_ONLINE t WHERE  "
				+ "t.OL_USERID=?";
		List<Map<String, Object>> list=find_sql_toMap(sql,new Integer[]{empId});
		if(list.size()==0) return token;
		Map<String, Object> map=list.get(0);
		token = (String) map.get("ol_authkey");
		return token;
		
//		return (String) getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) {
//				try {
//					String token = null;
//					String sql = " select t.ol_authkey from EIA_ONLINE t WHERE  "
//							+ "t.OL_USERID=?";
//					CallableStatement cs = session.connection()
//							.prepareCall(sql);
//
//					cs.setObject(1, empId);
//					ResultSet result = cs.executeQuery();
//					if (result.next()) {
//						token = result.getString(1);
//					}
//					cs.close();
//					return token;
//				} catch (Exception e) {
//					logger.error("获取在线用户信息失败：", e);
//					throw new RuntimeException(e);
//				}
//			}
//		});
		
		

	}

	public UsersVO getEofficeLoginUser(final String ip, final String authKey,
			final Calendar expiredTime, final int type, final boolean isTest,
			final boolean checkActive) throws OSException {
		logger.debug("authKey=" + authKey);
		return null;
//		try {
//			return (UsersVO) getHibernateTemplate().execute(
//					new HibernateCallback() {
//						public Object doInHibernate(Session session) {
//		
//							String sql = "SELECT us.US_ID, us.DE_ID, us.NAME, us.LOGINID, us.CODE, us.SEX,"
//									+ "us.EDUCATELEVEL, us.ARCHADDR, us.CREDENTIALTYPE, us.CREDENTIALNO, us.TEL, us.WECHAT, us.QQ,"
//									+ "us.WEIBO, us.EMAIL, us.MOBILE, us.HOUSETEL, us.TITLE, us.us_ID, us.MARK, us.STATUS,"
//									+ "us.ENGINEER, us.BIRTHDAY, us.BLOODTYPE, us.ISDIRECTOR,de.DE_NAME"
//									+ " FROM SY_USERS us,SY_DEPARTMENT de,EIA_ONLINE T WHERE  us.DE_ID=de.DE_ID  and   us.US_ID=T.OL_USERID ";
//
//							int i = 1;
//							if (!isTest) {
//								sql = sql
//										+ " AND T.OL_STATUS='1' AND T.OL_IP=? ";
//								i = 2;
//								if (checkActive) {
//									sql = sql + "and T.OL_ACTIVETIME >=?";
//									i = 3;
//								}
//							}
//							if (type == 0) {
//								sql = sql + " AND T.OL_AUTHKEY=?";
//							} else {
//								sql = sql + " AND us.CODE=?";
//							}
//							String[] args = new String[i];
//							int point = 0;
//							if (!isTest) {
//								args[0] = ip;
//								if (checkActive) {
//									args[1] = SemAppUtils
//											.getFullTime(expiredTime);
//									point = 2;
//								} else {
//									point = 1;
//								}
//							}
//							if (type == 0) {
//								args[point] = authKey;
//							} else {
//								args[point] = authKey;
//							}
//							CallableStatement cs = null;
//							ResultSet rs = null;
//							try {
//								logger.debug("get online sql=" + sql);
//								cs = session.connection().prepareCall(sql);
//								if (args != null) {
//
//									for (int j = 0; j < args.length; j++) {
//										cs.setObject(j + 1, args[j]);
//									}
//								}
//								rs = cs.executeQuery();
//								if (rs.next()) {
//									return this.getUserFromResultSet(rs);
//								} else {
//									logger.error("非法token");
//									return null;
//								}
//							} catch (SQLException ee) {
//								logger.error("datebase error", ee);
//								return null;
//							} finally {
//
//								try {
//									if (rs != null)
//										rs.close();
//									if(cs!=null) cs.close();
//								} catch (SQLException e) {
//									logger.error("close rs&cs fail");
//								}
//							}
//						}
//
//						private UsersVO getUserFromResultSet(ResultSet rs)
//								throws SQLException {
//							UsersVO user = new UsersVO();
//							user.setId(rs.getInt(1));
//							user.setDepartment(rs.getInt(2));
//							user.setName(rs.getString(3));
//							user.setLoginId(rs.getString(4));
//							user.setCode(rs.getString(5));
//							user.setSex(rs.getString(6));
//							// user.setEducateLevel(new
//							// Integer(rs.getString(7)));
//							user.setArchAddr(rs.getString(8));
//							// user
//							// .setCredentialType(new Integer(rs
//							// .getString(9)));
//							user.setCredentialNo(rs.getString(10));
//							user.setTel(rs.getString(11));
//							user.setWechat(rs.getString(12));
//							user.setQq(rs.getString(13));
//							user.setWeibo(rs.getString(14));
//							user.setEmail(rs.getString(15));
//							user.setMobile(rs.getString(16));
//							user.setHousetel(rs.getString(17));
//							user.setTitle(rs.getString(18));
//							// user.setLevel(new Integer(rs.getString(19)));
//							user.setMark(rs.getString(20));
//							user.setStatus(rs.getString(21));
//							user.setEngineer(rs.getString(22));
//							user.setBirthday(SemAppUtils.getFullCalendar(rs
//									.getString(23)));
//							user.setBloodType(rs.getString(24));
//							// user.setIsDirector(new
//							// Integer(rs.getString(25)));
//							user.setDepartmentName(rs.getString(26));
//							// user.setd(rs.getString(27));
//							return user;
//						}
//					});
//		} catch (Exception e) {
//			logger.error("获取在线用户信息失败：", e);
//			throw new OSException("获取在线用户信息失败：", e);
//		}
	}

}
