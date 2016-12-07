package com.xxl.bussiness;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.bussiness.CommException;
import common.os.vo.UsersVO;

public class MobileDB {

  private static MobileDB theInstance;

  private Connection conn;

  private Statement stmt;
  private PreparedStatement pstmt;

  private ResultSet rs;

  private PreparedStatement pstm;

  private DataSource ds = null;
  public static Log dLogger = LogFactory.getLog(MobileDB.class);

public static Log sysLogger = LogFactory.getLog("sys");

  public static void init() throws SQLException, NamingException {

    if (theInstance == null) {
      theInstance = new MobileDB();
    }
  }

  public static void end() {

    theInstance = null;

  }

  public MobileDB() throws SQLException, NamingException {
    Context initCtx = new InitialContext();
    ds = (DataSource) initCtx.lookup("jdbc/mobile");
  }

  public static MobileDB getTheInstance() {
    return theInstance;
  }
  public boolean sentMobileMsg(String destMobile,String content) throws CommException{
	  return sentMobileMsg(new String[]{destMobile},content);
  }
  

  public boolean sentMobileMsg(String[] destMobile,String content) throws CommException {
    UsersVO resultUser=null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();      
      for(int i=0;i<destMobile.length;i++){
    	  dLogger.debug("开始往手机["+destMobile[i]+"]发送短信，内容["+content+"]");
      CallableStatement statement = conn.prepareCall(
      "insert into tbl_SMSendTask(CreatorID,OperationType,SuboperationType,SendType,OrgAddr,DestAddr,SM_Content,SendTime,NeedStateReport,ServiceID,FeeType,FeeCode)"+
      "values ('8888','COMMON','66',1,'1065753200068888',?,?,getdate(),0,'semit','01','0')");
      statement.setString(1,destMobile[i]);
      statement.setString(2, content);
      statement.execute();
      dLogger.debug("完成往手机["+destMobile+"]发送短信，内容["+content+"]");
      }
      return true;
    }
    catch (SQLException ex2) {
      dLogger.error("发送手机短信失败",ex2);
    }
    catch (Exception ex2) {
    	dLogger.error("发送手机短信失败",ex2);
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          if (stmt != null) {
            stmt.close();
          }
          if (conn != null) {
            conn.close();
          }
        }
      }
      catch (SQLException ex1) {
      }
    }
    return false;
  }
}
