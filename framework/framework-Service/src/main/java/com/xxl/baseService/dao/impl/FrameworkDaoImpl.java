package com.xxl.baseService.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xxl.baseService.dao.IFrameworkDao;
import common.dao.impl.BaseDAOImpl;
import common.exception.CommException;
import common.os.vo.DepartmentVO;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;

@Repository("frameworkDAO")
public class FrameworkDaoImpl extends BaseDAOImpl<Object, java.lang.String> implements IFrameworkDao {
	public Log logger = LogFactory.getLog(this.getClass());
	
	private Connection conn;

	private Statement stmt;

	private PreparedStatement pstmt;

	private ResultSet rs;

	private PreparedStatement pstm;

	private DataSource ds = null;

	/**
	 * ??????????????????????????????????????????????
	 * 
	 * @param deptID
	 *            String ???????????????????? ??????A510
	 * @throws Exception
	 *             ??????????????????????????
	 * @return String ????????????????????????????
	 */

	public UsersVO getDeptTopDirector(String deptID) throws Exception {
		UsersVO resultUser = null;
				
//		try {
//			conn = session.connection();
//			stmt = conn.createStatement();
//			CallableStatement statement = conn
//					.prepareCall("{call PACK_API.API_GET_DEPT_DIRECTOR(?,?)}");
//			statement.registerOutParameter(1, OracleTypes.CURSOR);
//			statement.setString(2, deptID);
//			statement.execute();
//			ResultSet rs = (ResultSet) statement.getObject(1);
//			if (rs.next()) {
//				resultUser = getUserFromResultSet(rs);
//			}
//		} catch (SQLException ex2) {
//			logger.error("?????????????????????", ex2);
//			throw new CommException("?????????????????????");
//		} catch (Exception ex2) {
//			logger.error("?????????????????????", ex2);
//			throw new CommException("?????????????????????");
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (stmt != null) {
//					stmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException ex1) {
//				logger.error("?????????????????????", ex1);
//			}
//		}
		return resultUser;
	}

	public DepartmentVO getDeptOfLevel(String deptID, String level)
			throws Exception {
		DepartmentVO department = null;
		try {
			System.out.println("start query!");
			conn = ds.getConnection();
			stmt = conn.createStatement();
			CallableStatement statement = conn
					.prepareCall("{call PACK_API.API_GET_LEVEL_DEPARTMENT(?,?,?)}");
			statement.registerOutParameter(1,
					oracle.jdbc.driver.OracleTypes.CURSOR);
			statement.setString(2, deptID);
			statement.setString(3, level);
			statement.execute();
			ResultSet rs = (ResultSet) statement.getObject(1);

			if (rs.next()) {
				department = new DepartmentVO();
				System.out.println("has a record");
				department.setId(rs.getString(2));
				department.setName(rs.getString(1));
				department.setLeaderId(SemAppUtils.getInteger(rs.getString(3)));
				//department.setParentId(rs.getString(4));
			}
		} catch (SQLException ex2) {
			logger.error("?????????????????????", ex2);
			throw new CommException("?????????????????????");
		} catch (Exception ex2) {
			logger.error("?????????????????????", ex2);
			throw new CommException("?????????????????????");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex1) {
				logger.error("?????????????????????", ex1);
				throw new CommException("?????????????????????");
			}
		}
		return department == null ? getDepartmentInfo(deptID) : department;
	}

	public void logonOASystem(String empid, String ip) throws Exception {

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			CallableStatement statement = conn
					.prepareCall("{call PACK_API.API_LOGON(?,?,?)}");
			statement.registerOutParameter(1,
					oracle.jdbc.driver.OracleTypes.INTEGER);
			statement.setString(2, formatEmpID(empid));
			statement.setString(3, ip);
			statement.execute();
		} catch (SQLException ex2) {
			logger.error("?????????????????????", ex2);
			throw new CommException("?????????????????????");
		} catch (Exception ex2) {
			logger.error("?????????????????????", ex2);
			throw new CommException("?????????????????????");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex1) {
				logger.error("?????????????????????", ex1);
				throw new CommException("?????????????????????");
			}
		}
	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param userID
	 *            String ???????????????????????????????
	 * @return User ??????????????????????????????
	 */
	public DepartmentVO getDepartmentInfo(String deptID) {
		DepartmentVO department = null;
		try {
			conn = ds.getConnection();
			// String sql =
			// "SELECT DE.DE_ABBR,DE.DE_DEPTNUM,DE.DE_LEADERID,DE.DE_PARENTID"
			// + " FROM VW_SY_DEPARTMENT_AVAI DE WHERE DE.DE_DEPTNUM=?";
			String sql = "SELECT T.NAME,T.ORGCODE,T.GRADE,T.FID FROM LBORGANIZATION T WHERE T.ORGCODE=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				department = new DepartmentVO();
				department.setId(deptID);
				department.setName(rs.getString(1));
				department.setLeaderId(new Integer(rs.getInt(3)));
			//	department.setParentId(rs.getString(4));
			}
		} catch (SQLException ex2) {
			logger.error("?????????????????????", ex2);

		} catch (Exception ex2) {
			logger.error("?????????????????????", ex2);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex1) {
				logger.error("?????????????????????", ex1);
			}
		}

		return department;

	}

	public List getUserOfDept(String deptID) throws CommException {
		List resultList = new ArrayList();
		UsersVO user = null;
		try {
			conn = ds.getConnection();
			String sql = "SELECT T.ID,T.ORGID,T.USERNAME,T.ADLOGIN,T.USERCODE,US.US_SEX,US.US_EDUCATELEVEL, "
					+ "US.US_ARCHADDR, US.US_CREDENTIALTYPE, US.US_CREDENTIALNO, US.US_TEL, US.US_ROOM, "
					+ "US.US_ADDR,US.US_POSTCODE, US.US_EMAIL, US.US_MOBILE, US.US_HOUSETEL, US.US_TITLE, "
					+ "US.US_LEVEL, US.US_MARK, T.USERSTATUS,US.US_ENGINEER, US.US_BIRTHDAY, US.US_BLOODTYPE, "
					+ "US.US_ISDIRECTOR,T.ORGNAME,T.ORGCODE FROM SEM_V_USER T,TUSERINFO US "
					+ "WHERE T.USERCODE=US.US_CODE AND T.ORGCODE=?";
			logger.debug("sql=" + sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptID);
			ResultSet rs = pstmt.executeQuery();
			// System.out.println(deptID);
			while (rs.next()) {
//				user = getUserFromResultSet(rs);
				// System.out.println(user.getName());
				resultList.add(user);
			}

		} catch (SQLException ex2) {
			logger.error("?????????????????????", ex2);
			throw new CommException("?????????????????????");

		} catch (Exception ex2) {
			logger.error("?????????????????????", ex2);
			throw new CommException("?????????????????????");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex1) {
				logger.error("?????????????????????", ex1);
				throw new CommException("?????????????????????");
			}
		}

		return resultList;

	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param userID
	 *            String ???????????????????????????????
	 * @return User ??????????????????????????????
	 * @throws Exception 
	 */
	public UsersVO getUserInfo(String userID) throws Exception {
		UsersVO user = null;
//		String sql = "SELECT T.ID,T.ORGID,T.USERNAME,T.ADLOGIN,T.USERCODE,US.US_SEX,US.US_EDUCATELEVEL, "
//				+ "US.US_ARCHADDR, US.US_CREDENTIALTYPE, US.US_CREDENTIALNO, US.US_TEL, US.US_ROOM, US.US_ADDR,"
//				+ "US.US_POSTCODE, US.US_EMAIL, US.US_MOBILE, US.US_HOUSETEL, US.US_TITLE, t.rolelevel , US.US_MARK, "
//				+ "T.USERSTATUS,US.US_ENGINEER, US.US_BIRTHDAY, US.US_BLOODTYPE, US.US_ISDIRECTOR,T.ORGNAME,"
//				+ "T.ORGCODE FROM SEM_V_USER_ALL T,SEM_V_USERINFO_ALL US WHERE T.USERCODE=US.US_CODE "
//				+ "AND GROUPFLAG='A' " + "AND T.USERCODE=?";
		
		String sql = "SELECT us.US_ID, us.DE_ID, us.NAME, us.LOGINID, us.CODE, us.SEX,"
		+ "us.EDUCATELEVEL, us.ARCHADDR, us.CREDENTIALTYPE, us.CREDENTIALNO, us.TEL, us.WECHAT, us.QQ,"
		+ "us.WEIBO, us.EMAIL, us.MOBILE, us.HOUSETEL, us.TITLE, us.us_ID, us.MARK, us.STATUS,"
		+ "us.ENGINEER, us.BIRTHDAY, us.BLOODTYPE, us.ISDIRECTOR,us.level,de.DE_NAME"
		+ " FROM SY_USERS us,SY_DEPARTMENT de,EIA_ONLINE T WHERE  us.DE_ID=de.DE_ID  and   us.US_ID=T.OL_USERID ";
		sql = sql + " AND us.CODE=?";
		
		List<Map<String, Object>> list=find_sql_toMap(sql,new String[]{userID});
		Map<String, Object> map=list.get(0);
		user=getUserFromMap(map);
		return user;

	}

	public String getUserToken(String empID) {
		String token = null;
		
//		String sql = " select t.rowid||t.ol_authkey as token from sy_onlineinfo t WHERE t.OL_USERID=?";
		String sql = " select t.ol_authkey from EIA_ONLINE t WHERE  "
				+ "t.OL_USERID=?";
		List<Map<String, Object>> list=find_sql_toMap(sql,new String[]{empID});
		Map<String, Object> map=list.get(0);
		token = (String) map.get("token");
//		token = token.replaceAll("[+]", "-");
		return token;

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
	
	

	private String formatEmpID(String empID) {
		return (empID.length() == 5) ? "0" + empID : empID;
	}

	


}
