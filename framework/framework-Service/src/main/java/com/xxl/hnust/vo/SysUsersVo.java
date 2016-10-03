package com.xxl.hnust.vo;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.common.value.BaseVO;

public class SysUsersVo extends BaseVO implements UserDetails {
	
	private static final long serialVersionUID = -6016261688962890417L;

	private Integer userId;

    private String userName;

    private String name;
    
    private String password;
    
    private Calendar dtCreate;
    
    private Calendar lastLogin;
    
    private Calendar deadLine;
    
    private String loginIp;
    
    private String vQzjgId;

    private String vQzjgMc;

    private String depId;

    private String depName;

    private Integer enable;

    private Integer accountNonExpired;

    private Integer accountNonLock;

    private Integer credentialsNonExpired;
    
    private Collection<GrantedAuthority> authorities;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Calendar dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Calendar getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Calendar lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Calendar getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Calendar deadLine) {
		this.deadLine = deadLine;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getvQzjgId() {
		return vQzjgId;
	}

	public void setvQzjgId(String vQzjgId) {
		this.vQzjgId = vQzjgId;
	}

	public String getvQzjgMc() {
		return vQzjgMc;
	}

	public void setvQzjgMc(String vQzjgMc) {
		this.vQzjgMc = vQzjgMc;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Integer getAccountNonLock() {
		return accountNonLock;
	}

	public void setAccountNonLock(Integer accountNonLock) {
		this.accountNonLock = accountNonLock;
	}

	public Integer getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		if(this.accountNonExpired!=null && this.accountNonExpired.intValue()==0) return false;
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if(this.accountNonLock!=null && this.accountNonLock.intValue()==0) return false;
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if(this.credentialsNonExpired!=null && this.credentialsNonExpired.intValue()==0) return false;
		return true;
	}

	@Override
	public boolean isEnabled() {
		if(this.enable!=null && this.enable.intValue()==0) return false;
		return true;
	}
    
}
