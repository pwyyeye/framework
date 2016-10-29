package com.xxl.bussiness;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import common.HibernateUtil;

import common.filter.CheckAccessInterface;
import common.os.vo.UsersVO;

public class CheckAccessAction implements CheckAccessInterface {
	public static Log dLogger = LogFactory.getLog("debug");

	public static Log sysLogger = LogFactory.getLog("sys");

	public boolean performCheck(UsersVO user, String actionName) {
		boolean result=false;
		String empID = user.getCode();
		dLogger.debug("perform check access empID=" + empID + ",action="
				+ actionName);
		try {
//			Session session = HibernateUtil.currentSession();
//			CallableStatement statement = session.connection()
//					.prepareCall(
//							"{call PKG_COMMON.checkAccess(?,?,?)}");
//			statement.setString(1, empID);
//			statement.setString(2, actionName);
//			statement.registerOutParameter(3, Types.INTEGER);
//			statement.execute();
//			int allow = statement.getInt(3);
//			result=(allow==1);
//			statement.close();
//		} catch (HibernateException e) {
//			dLogger.error("��ݿ��ѯʧ��", e);
//		} catch (SQLException e) {
//			dLogger.error("��ݿ��ѯʧ��", e);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}

		return result;
	}

}
