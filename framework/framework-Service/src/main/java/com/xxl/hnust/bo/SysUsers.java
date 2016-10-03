package com.xxl.hnust.bo;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.common.businessObject.BaseBusinessObject;
import com.common.utils.SemAppUtils;
import com.xxl.hnust.vo.SysUsersVo;

/**
 * 系统用户表
 * @author karys
 *
 */
@Entity
@Table(name = "SYS_USERS")
public class SysUsers extends BaseBusinessObject {
   
	private static final long serialVersionUID = -4388359113643721127L;

	private Integer userId; //用户ID

    private String userName; //用户名

    private String name; //用户姓名
    
    private String password; //密码
    
    private Calendar dtCreate; //创建日期
    
    private Calendar lastLogin; //最后登录日期
    
    private Calendar deadLine; //截止日期
    
    private String loginIp; //最后登录IP
    
    private String vQzjgId; //所属机构ID

    private String vQzjgMc; //所属机构名称

    private String depId; //地区编号

    private String depName; //地区名称

    private Integer enable; //是否可用

    private Integer accountNonExpired; //用户是否过期

    private Integer accountNonLock; //用户是否锁定

    private Integer credentialsNonExpired; //用户证书是否有效
    
    private Set<SysUsersRoles> sysUsersRoles = new HashSet<SysUsersRoles>(0);

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "USER_ID", unique = true, nullable = false, length = 11)
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CREATE", nullable = true)
    public Calendar getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Calendar dtCreate) {
		this.dtCreate = dtCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOGIN", nullable = true)
    public Calendar getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Calendar lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEAD_LINE", nullable = true)
    public Calendar getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Calendar deadLine) {
		this.deadLine = deadLine;
	}

	@Column(name = "LOGIN_IP", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "V_QZJGID", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getvQzjgId() {
		return vQzjgId;
	}

	public void setvQzjgId(String vQzjgId) {
		this.vQzjgId = vQzjgId;
	}

	@Column(name = "V_QZJGMC", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getvQzjgMc() {
		return vQzjgMc;
	}

	public void setvQzjgMc(String vQzjgMc) {
		this.vQzjgMc = vQzjgMc;
	}

	@Column(name = "DEP_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	@Column(name = "DEP_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	@Column(name = "ENABLED", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "ACCOUNT_NON_EXPIRED", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Column(name = "ACCOUNT_NON_LOCKED", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getAccountNonLock() {
		return accountNonLock;
	}

	public void setAccountNonLock(Integer accountNonLock) {
		this.accountNonLock = accountNonLock;
	}

	@Column(name = "CREDENTIALS_NON_EXPIRED", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUsers")
	@OrderBy("id")
	public Set<SysUsersRoles> getSysUsersRoles() {
		return sysUsersRoles;
	}

	public void setSysUsersRoles(Set<SysUsersRoles> sysUsersRoles) {
		this.sysUsersRoles = sysUsersRoles;
	}

	@Override
	public Object toVO() {
		SysUsersVo vo = new SysUsersVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}

}