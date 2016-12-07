package com.xxl.bussiness;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.bussiness.CommException;
import common.os.vo.DepartmentVO;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class EofficeDB {
	public Log logger = LogFactory.getLog(this.getClass());

	private static EofficeDB theInstance;

	private Connection conn;

	private Statement stmt;

	private PreparedStatement pstmt;

	private ResultSet rs;

	private PreparedStatement pstm;

	private DataSource ds = null;

	public static void init() throws SQLException, NamingException {

		if (theInstance == null) {
			theInstance = new EofficeDB();
		}
	}

	public static void end() {
		theInstance = null;

	}

	public EofficeDB() throws SQLException, NamingException {
		Context initCtx = new InitialContext();
		ds = (DataSource) initCtx.lookup("jdbc/oa");
	}

	public static EofficeDB getTheInstance() {
		return theInstance;
	}

	/**
	 * ȡ�ò��ŵ������ܵĹ���
	 * 
	 * @param deptID
	 *            String ���Ŵ��� ��A510
	 * @throws Exception
	 *             ��ݿ�����쳣
	 * @return String �����ܵ���Ϣ
	 */

	public UsersVO getDeptTopDirector(String deptID) throws Exception {
		UsersVO resultUser = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			CallableStatement statement = conn
					.prepareCall("{call PACK_API.API_GET_DEPT_DIRECTOR(?,?)}");
			statement.registerOutParameter(1,
					oracle.jdbc.driver.OracleTypes.CURSOR);
			statement.setString(2, deptID);
			statement.execute();
			ResultSet rs = (ResultSet) statement.getObject(1);
			if (rs.next()) {
				resultUser = getUserFromResultSet(rs);
			}
		} catch (SQLException ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
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
				logger.error("数据库操作失败", ex1);
			}
		}
		return resultUser;
	}

	/**
	 * ȡ��ָ�����ŵ��ϼ�ָ��LEVEL�Ĳ��ţ�������A510��20�õ�A500
	 * 
	 * @param deptID
	 *            String ���Ŵ��� ��A510
	 * @param level
	 *            Stirng Ŀ�겿�ŵĲ㼶
	 * @throws Exception
	 *             ��ݿ�����쳣
	 * @return String �����ܵ���Ϣ
	 */

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
			//	department.setParentId(rs.getString(4));
			}
		} catch (SQLException ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
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
				logger.error("数据库操作失败", ex1);
				throw new CommException("数据库操作失败");
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
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
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
				logger.error("数据库操作失败", ex1);
				throw new CommException("数据库操作失败");
			}
		}
	}

	/**
	 * ���ز�����ϸ��Ϣ��
	 * 
	 * @param userID
	 *            String ����Ĳ�ѯ����
	 * @return User ��Ա����ϸ��Ϣ
	 */
	public DepartmentVO getDepartmentInfo(String deptID) {
		DepartmentVO department = null;
		try {
			conn = ds.getConnection();
			String sql = "SELECT DE.DE_ABBR,DE.DE_DEPTNUM,DE.DE_LEADERID,DE.DE_PARENTID"
					+ " FROM VW_SY_DEPARTMENT_AVAI DE WHERE DE.DE_DEPTNUM=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				department = new DepartmentVO();
				department.setId(deptID);
				department.setName(rs.getString(1));
				department.setLeaderId(SemAppUtils.getInteger(rs.getString(3)));
				//department.setParentId(rs.getString(4));
			}
		} catch (SQLException ex2) {
			logger.error("数据库操作失败", ex2);

		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);

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
				ex1.printStackTrace(System.out);
			}
		}

		return department;

	}

	public List getUserOfDept(String deptID) throws CommException {
		List resultList = new ArrayList();
		UsersVO user = null;
		try {
			conn = ds.getConnection();
			String sql = "SELECT us.US_ID, us.DE_ID, us.US_NAME, us.US_LOGINID, us.US_CODE, us.US_SEX,"
					+ "us.US_EDUCATELEVEL, us.US_ARCHADDR, us.US_CREDENTIALTYPE, us.US_CREDENTIALNO, us.US_TEL, us.US_ROOM, us.US_ADDR,"
					+ "us.US_POSTCODE, us.US_EMAIL, us.US_MOBILE, us.US_HOUSETEL, us.US_TITLE, us.US_LEVEL, us.US_MARK, us.US_STATUS,"
					+ "us.US_ENGINEER, us.US_BIRTHDAY, us.US_BLOODTYPE, us.US_ISDIRECTOR,us.DE_Abbr,us.de_deptnum"
					+ " FROM VW_SY_USERS us WHERE   us.de_deptnum=? AND us.US_STATUS='1'";
            logger.debug("sql="+sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptID);
			ResultSet rs = pstmt.executeQuery();
			// System.out.println(deptID);
			while (rs.next()) {

				user = getUserFromResultSet(rs);
				// System.out.println(user.getName());
				resultList.add(user);
			}

		} catch (SQLException ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");

		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
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
				logger.error("数据库操作失败", ex1);
				throw new CommException("数据库操作失败");
			}
		}

		return resultList;

	}

	/**
	 * ������Ա��ϸ��Ϣ��
	 * 
	 * @param userID
	 *            String ����Ĳ�ѯ����
	 * @return User ��Ա����ϸ��Ϣ
	 */
	public UsersVO getUserInfo(String userID) {
		UsersVO user = null;
		try {
			conn = ds.getConnection();
			String sql = "SELECT us.US_ID, us.DE_ID, us.US_NAME, us.US_LOGINID, us.US_CODE, us.US_SEX,"
					+ "us.US_EDUCATELEVEL, us.US_ARCHADDR, us.US_CREDENTIALTYPE, us.US_CREDENTIALNO, us.US_TEL, us.US_ROOM, us.US_ADDR,"
					+ "us.US_POSTCODE, us.US_EMAIL, us.US_MOBILE, us.US_HOUSETEL, us.US_TITLE, v.du_level, us.US_MARK, us.US_STATUS,"
					+ "us.US_ENGINEER, us.US_BIRTHDAY, us.US_BLOODTYPE, us.US_ISDIRECTOR,us.DE_Abbr,us.de_deptnum"
					+ " FROM VW_SY_USERS us ,vw_sy_userduty v WHERE us.US_ID=v.us_id and us.DE_ID=v.de_id and us.US_CODE=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, formatEmpID(userID));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user = getUserFromResultSet(rs);
			}else{
				logger.error("该用户在公司组织架构中不存在["+userID+"]");
			}

		} catch (SQLException ex2) {
			logger.error("数据库操作失败["+userID+"]",ex2);
		} catch (Exception ex2) {
			logger.error("数据库操作失败["+userID+"]",ex2);
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
				logger.error("数据库操作失败", ex1);

			}
		}

		return user;

	}

	public String getUserToken(String empID) {
		String token = null;
		try {
			conn = ds.getConnection();
			String sql = " select t.rowid||t.ol_authkey from sy_onlineinfo t,VW_SY_USERS us WHERE  "
					+ "t.ol_userid=us.US_ID and us.US_CODE=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, formatEmpID(empID));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				token = rs.getString(1);
			}

		} catch (SQLException ex2) {
			ex2.printStackTrace();
		} catch (Exception ex2) {
			ex2.printStackTrace();
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
				logger.error("数据库操作失败", ex1);
			}
		}

		return token;

	}

	/**
	 * 
	 * @param rowid
	 *            String ��Ӧ��rowid
	 * @param ip
	 *            String ��Ӧ��ip��ַ
	 * @param type
	 *            String key�����ͣ�0��ʾrowid��1��ʾempNo
	 * @param authKey
	 *            String ��Ӧ��authKey
	 * @param expiredTime
	 *            Calendar ��������
	 * @throws CommException
	 *             ��ݿ�����쳣ʱ
	 * @return User ���ص�¼�ߵ���Ϣ
	 */
	public UsersVO getEofficeLoginUser(String key, String ip, String authKey,
			Calendar expiredTime, int type, boolean isTest, boolean checkActive)
			throws CommException {
        logger.debug("start write date"+System.currentTimeMillis());
        UsersVO currentUser = null;
		try {
			conn = ds.getConnection();
			String sql = "SELECT us.US_ID, us.DE_ID, us.US_NAME, us.US_LOGINID, us.US_CODE, us.US_SEX,"
					+ "us.US_EDUCATELEVEL, us.US_ARCHADDR, us.US_CREDENTIALTYPE, us.US_CREDENTIALNO, us.US_TEL, us.US_ROOM, us.US_ADDR,"
					+ "us.US_POSTCODE, us.US_EMAIL, us.US_MOBILE, us.US_HOUSETEL, us.US_TITLE, v.du_level, us.US_MARK, us.US_STATUS,"
					+ "us.US_ENGINEER, us.US_BIRTHDAY, us.US_BLOODTYPE, us.US_ISDIRECTOR,us.DE_Abbr,us.de_deptnum"
					+ " FROM VW_SY_USERS us,SY_ONLINEINFO T,vw_sy_userduty v WHERE us.US_ID=v.us_id and us.DE_ID=v.de_id  and   us.US_ID=T.OL_USERID ";
			if (!isTest) {
				sql = sql + " AND T.OL_STATUS='1' AND T.OL_IP=? ";
				if (checkActive) {
					sql = sql
							+ "and T.OL_ACTIVETIME >= to_date(?,'YYYY-MM-DD hh24:mi:ss') ";
				}
			}
			if (type == 0) {
				sql = sql + " AND T.OL_AUTHKEY=? AND T.ROWID='" + key + "'";
			} else {
				sql = sql + " AND us.US_CODE=?";
			}
			pstmt = conn.prepareStatement(sql);
			int point = 1;
			if (!isTest) {
				pstmt.setString(1, ip);
				if (checkActive) {
				   pstmt.setString(2, SemAppUtils.getFullTime(expiredTime));
				   point = 3;
				}else{
				   point = 2;
				}
				logger.debug("ip=" + ip + ",time="
						+ SemAppUtils.getFullTime(expiredTime) + ",authKey="
						+ authKey + ",key=" + key);
			}
			if (type == 0) {
				pstmt.setString(point, authKey);
			} else {
				pstmt.setString(point, formatEmpID(key));
			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				currentUser = getUserFromResultSet(rs);

			}

		} catch (SQLException ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
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
				logger.error("数据库操作失败", ex1);
				throw new CommException("数据库操作失败");
			}
		}

		return currentUser;

	}

	public int insertMessageToProcess(String title, String createUser,
			Calendar createDate, String content, int attachID, String backType,
			String backDate, Calendar limitDate, String status,
			Calendar scheduleDate, String addCalendar, String[] copyUsers,
			String[] dealUsers) throws Exception {
		int returnId = -1;
		float transcatID = -1;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();

			CallableStatement statement = conn
					.prepareCall("{call PACK_API.API_SENDTO_TRANSACTAFFAIR(?,?,?,?,?,?,?,?,?,?,?,?)}");
			statement.registerOutParameter(1, Types.FLOAT);
			statement.setString(2, title);
			statement.setString(3, createUser);
			statement.setTimestamp(4, new Timestamp(createDate
					.getTimeInMillis()));
			statement.setString(5, content);
			statement.setInt(6, attachID);
			statement.setString(7, backType);
			statement.setString(8, backDate);
			statement.setTimestamp(9,
					new Timestamp(limitDate.getTimeInMillis()));
			statement.setString(10, status);
			statement.setTimestamp(11, new Timestamp(scheduleDate
					.getTimeInMillis()));
			statement.setString(12, addCalendar);
			statement.execute();
			ResultSet set = statement.getResultSet();
			// �������ID��
			transcatID = statement.getInt(1);
			returnId = 0;
			for (int i = 0; copyUsers != null && i < copyUsers.length; i++) {
				statement = conn
						.prepareCall("{call PACK_API.API_SENDTO_TRANUSER(?,?,?,?,?,?,?)}");
				statement.registerOutParameter(1, Types.FLOAT);
				statement.setFloat(2, transcatID);
				statement.setString(3, "");
				statement.setString(4, "");
				statement.setString(5, createUser);
				statement.setInt(6, 2); // ����
				statement.setString(7, copyUsers[i]);
				returnId++;
				statement.execute();
			}
			for (int i = 0; dealUsers != null && i < dealUsers.length; i++) {
				statement = conn
						.prepareCall("{call PACK_API.API_SENDTO_TRANUSER(?,?,?,?,?,?,?)}");
				statement.registerOutParameter(1, Types.FLOAT);
				statement.setFloat(2, transcatID);
				statement.setString(3, "");
				statement.setString(4, "");
				statement.setString(5, createUser);
				returnId++;
				statement.setInt(6, 1); // ����
				statement.setString(7, dealUsers[i]);
				statement.execute();
			}
			conn.commit();

		} catch (SQLException ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
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
				logger.error("数据库操作失败", ex1);
				throw new CommException("数据库操作失败");
			}
		}

		return returnId;
	}

	public int sendMessageByOA(String content, String sendUser,
			String[] receiveUsers, String type) throws CommException {
		int returnId = -1;
		float transcatID = -1;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();

			CallableStatement statement = conn
					.prepareCall("{call PACK_API.API_SEND_MESSAGE(?,?,?,?,?)}");
			statement.registerOutParameter(1, Types.FLOAT);
			statement.setString(2, content);
			statement.setString(3, sendUser);
			statement.setString(4, type);
			statement.setInt(5, 99); // 99��ʾ4Դ���ⲿ�ӿڵ���
			statement.execute();
			ResultSet set = statement.getResultSet();
			// �������ID��
			transcatID = statement.getInt(1);
			returnId = 0;
			for (int i = 0; receiveUsers != null && i < receiveUsers.length; i++) {
				statement = conn
						.prepareCall("{call PACK_API.API_MESSAGE_RECEIVE(?,?,?,?)}");
				statement.registerOutParameter(1, Types.FLOAT);
				statement.setFloat(2, transcatID);
				statement.setString(3, receiveUsers[i]);
				statement.setString(4, type);
				returnId++;
				statement.execute();
			}

		} catch (SQLException ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
		} catch (Exception ex2) {
			logger.error("数据库操作失败", ex2);
			throw new CommException("数据库操作失败");
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
				logger.error("数据库操作失败", ex1);
				throw new CommException("数据库操作失败");
			}
		}
		return returnId;
	}

	private UsersVO getUserFromResultSet(ResultSet rs) throws SQLException {
		UsersVO user = new UsersVO();
		user.setId(rs.getInt(1));
		//user.setDepartment(rs.getString(2));
		user.setDepartment(rs.getInt(27));
		user.setName(rs.getString(3));
		user.setLoginId(rs.getString(4));
		user.setCode(rs.getString(5));
		user.setSex(rs.getString(6));
		user.setEducateLevel(SemAppUtils.getInteger(rs.getString(7)));
		user.setArchAddr(rs.getString(8));
		user.setCredentialType(SemAppUtils.getInteger(rs.getString(9)));
		user.setCredentialNo(rs.getString(10));
		user.setTel(rs.getString(11));
		//user.setRoom(rs.getString(12));
		//user.setAddr(rs.getString(13));
		//user.setPostcode(rs.getString(14));
		user.setEmail(rs.getString(15));
		user.setMobile(rs.getString(16));
		user.setHousetel(rs.getString(17));
		user.setTitle(rs.getString(18));
		user.setLevel(SemAppUtils.getInteger(rs.getString(19)));
		user.setMark(rs.getString(20));
		user.setStatus(rs.getString(21));
		user.setEngineer(rs.getString(22));
		//user.setBirthday(SemAppUtils.getCalendar(rs.getString(23)));
		user.setBloodType(rs.getString(24));
		user.setIsDirector(SemAppUtils.getInteger(rs.getString(25)));
		user.setDepartmentName(rs.getString(26));
		user.setDepartmentCode(rs.getString(27));
		return user;
	}
	private String formatEmpID (String empID){
		return (empID.length()==5)?"0"+empID:empID;
	}
	
	public Integer sendQgateInfoToOA(String title, String dpnoNo,
			String itemNo, String userCode, String strType) throws Exception {
		logger.info("here come in Send");
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			String sql = "{call EOFFICE.p_qgate(?,?,?,?,?,?,?,?)}";
			CallableStatement cs = conn.prepareCall(sql);
			logger.debug(dpnoNo + "--" + itemNo + "--" + userCode);
			cs.setString(1, title);
			cs.setString(2, userCode);
			cs.setString(3, dpnoNo);
			cs.setString(4, strType);
			cs.setString(5, itemNo);
			cs.registerOutParameter(6, Types.INTEGER);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.registerOutParameter(8, Types.VARCHAR);
			cs.execute();
			logger.info("sendQgateInfoToOA done");
			Integer sErrorCode = new Integer(cs.getInt(6));
			return sErrorCode;
		} catch (Exception e) {
			logger.error("sendQgateInfoToOA error!", e);
			throw e;
		}
	}

}
