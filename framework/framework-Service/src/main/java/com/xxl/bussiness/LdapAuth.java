package com.xxl.bussiness;

import java.util.Properties;
import javax.naming.*;
import javax.naming.directory.*;

/**
 * 域登陆类
 * <p>Title:域登陆 </p>
 * <p>Description:公司域验证和一些公用方法，以下对公有和私有的变量或方法做了简单的注释</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:http://www.ccc.com </p>
 * @author zzq
 * @version 1.0
 */
public class LdapAuth {
  /**
   * 设置登陆参数，需设置的五个参数为
   * Context.INITIAL_CONTEXT_FACTORY,
   * Context.SECURITY_AUTHENTICATION,
   * Context.SECURITY_CREDENTIALS,
   * Context.SECURITY_PRINCIPAL,
   * Context.PROVIDER_URL
   */
  private Properties props = null;

  /**
   * 如果ctx不为空，则表示登陆成功
   */
  private DirContext ctx = null;

  /**
   * 默认主机地址：LDAP://www.ccc.local/
   */
  private final String LDAPHOST = "LDAP://www.ccc.local/";

  /**
   * 默认过滤条件：工号
   */
  private final String ID_FILTER = "employeeID";

  /**
   *默认过滤条件：MAIL帐号
   */
  private final String MAIL_FILTER = "sAMAccountName";

  /**
   *默认的DN字段
   */
  private final String DISTINGUISHED_NAME = "distinguishedName";

  /**
   * 默认查询目录：dc=www.ccc,dc=local
   */
  private final String SCOPE = "dc=www.ccc,dc=local";

  private final String adminDN = "createuser";

  private final String adminPS = "eoffice12";

  /**
   * 要查找的工号字段
   */
  private final String EMPLOYEE_ID = "employeeID";

  /**
   * 要查找的EMIL字段
   */
  private final String MAIL = "mail";

  /**
   * 当前登陆用户的DN
   */
  private String USER_DN = "";

  /**
   * 要查找的名字字段
   */
  private String DISPLAY_NAME = "displayName";

  /**
   * 取得登陆用户的DN
   * @return String  返回登陆用户的DN
   */
  private String getUserDN() {
    return this.USER_DN;
  }

  /**
   * 登陆到Server
   * @param ldapHost String  主机名
   * @param DN String  DN
   * @param PS String  密码
   * @throws NamingException  登陆不成功时发生异常
   */
  private void initProperties(String ldapHost, String DN, String PS) throws
      NamingException {
    props = new Properties();
    props.put(Context.INITIAL_CONTEXT_FACTORY,
              "com.sun.jndi.ldap.LdapCtxFactory");
    props.put(Context.SECURITY_AUTHENTICATION, "simple"); //use simple authentication mechanism
    props.put(Context.SECURITY_CREDENTIALS, PS);
    props.put(Context.SECURITY_PRINCIPAL, DN);
    props.put(Context.PROVIDER_URL, ldapHost);
    ctx = new InitialDirContext(props);
    this.USER_DN = DN;
  }

  /**
   * 登陆到默认的Server（LDAP://www.ccc.local/）
   * @param DN String  DN
   * @param PS String  密码
   * @throws NamingException  登陆不成功时发生异常
   */
  private void initProperties(String DN, String PS) throws
      NamingException {
    props = new Properties();
    props.put(Context.INITIAL_CONTEXT_FACTORY,
              "com.sun.jndi.ldap.LdapCtxFactory");
    props.put(Context.SECURITY_AUTHENTICATION, "simple"); //use simple authentication mechanism
    props.put(Context.SECURITY_CREDENTIALS, PS);
    props.put(Context.SECURITY_PRINCIPAL, DN);
    props.put(Context.PROVIDER_URL, this.LDAPHOST);
    ctx = new InitialDirContext(props);
    this.USER_DN = DN;
  }

  /**
   * 根据用户帐号，查找域中用户的DN，前提是当前的登陆用户有权限查询，否则发生异常
   * @param userName String  登陆名
   * @param filter String  域中与登陆名匹配的字段
   * @param scope String  查找的域目录范围
   * @param displayName String  DN字段名
   * @return String  返回查找的用户DN,没有找到或异常返回null
   */
  public String findUserDN(String userName, String filter, String scope,
                           String distinguishedName) {
    String str = null;
    SearchControls controls = new SearchControls();
    controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    //   controls.setCountLimit(200);
    String s_Filter = "(" + filter + "=" + userName + ")";
    try {
      NamingEnumeration se = ctx.search(scope, s_Filter, controls);
      if (se.hasMore()) {
        SearchResult result = (SearchResult) se.next();
        for (NamingEnumeration ae = result.getAttributes().getAll(); ae.hasMore(); ) {
          Attribute attr = (Attribute) ae.next();
          // System.out.println("id=" + attr.getID());
          if (attr.getID().equals(distinguishedName))
            for (NamingEnumeration ve = attr.getAll(); ve.hasMore(); ) {
              str = (String) ve.next();
                //System.out.println("SDN=" + str);
              return str;
            }
        }
      }
    }
    catch (NamingException e) {
      return null;
    }
    catch (Exception e) {
      return null;
    }
    return str;
  }

  /**
   * 连接默认主机，并根据工号取得该用户的DN
   * @param userName String  工号
   * @return String  返回用户DN
   */
  private String findUserDNByUserID(String userName) {
    return this.findUserDN(userName, this.ID_FILTER, this.SCOPE,
                           this.DISTINGUISHED_NAME);
  }

  /**
   * 连接默认主机，并根据AD帐号取得该用户的DN
   * @param userName String  AD帐号
   * @return String  返回用户DN
   */
  private String findUserDNByAD(String userName) {
    return this.findUserDN(userName, this.MAIL_FILTER, this.SCOPE,
                           this.DISTINGUISHED_NAME);
  }


  /**
   * 连接默认Server
   * @param DN String  DN
   * @param PS String  密码
   * @return boolean  返回是否登陆成功，true表示成功，false表示失败
   */
  public boolean connect(String DN, String PS) {
    boolean flag = false;
    try {
      this.initProperties(DN, PS);
      flag = true;
    }
    catch (NamingException ex) {
      flag = false;
    }
    return flag;
  }

  /**
   * 连接默认主机
   * @param userName String  工号或AD帐号
   * @param passWord String  AD密码
   * @return boolean  返回是否登陆成功，true表示成功，false表示失败
   */
  public String logonByUserName(String userName, String passWord) {
    String empID=null;
    if (!this.connect(this.adminDN, this.adminPS))
      return null;
      //System.out.println("logon by admin=>");
    String userDN = null;
    userDN = this.findUserDNByAD(userName);
    if (userDN == null || userDN.trim().length() <= 0)
      userDN = this.findUserDNByUserID(userName);
    try {
      this.quit();
    }
    catch (NamingException ex) {
      return null;
    }
    if (userDN == null || userDN.length() <= 0)
      return null;
    if (this.connect(userDN, passWord)) {

      try {
         empID=this.getEmployeeID();
        this.quit();
      }
      catch (NamingException ex) {
        return null;
      }
      return empID;
    }
    else
      return null;
  }
  private String getEmployeeID() throws NamingException {
     String result = null;
     result = this.getAttributeValue(this.EMPLOYEE_ID);
     return result;
   }


  /**
   * 根据属性名，取得该属性名的第一个属性值
   * @param attributeName String  字段名
   * @throws NamingException  用户还没有登陆，或其他错误发生异常。
   * @return String  属性值
   */
  public String getAttributeValue(String attributeName) throws
      NamingException {
    String result = null;
    String[] str = {
        attributeName};
    Attributes attrs = ctx.getAttributes(this.getUserDN(), str);
    for (NamingEnumeration ae = attrs.getAll(); ae.hasMore(); ) {
      Attribute attr = (Attribute) ae.next();
      //     System.out.println("Attribute : " + attr.getID());
      for (NamingEnumeration ve = attr.getAll(); ve.hasMore(); ) {
        //      System.out.println("  Value : " + ve.next());
        return (String) ve.next();
      }
    }
    return result;
  }

  /**
   * 退出域登陆
   * @throws NamingException  未知
   */
  public void quit() throws NamingException {
    if (ctx != null)
      ctx.close();
  }

  public LdapAuth() {

  }

  /**
   * 测试
   * @param args String[]
   */
  public static void main(String[] args) {

  }
}
