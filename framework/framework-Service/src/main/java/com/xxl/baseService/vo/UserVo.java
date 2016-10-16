package com.xxl.baseService.vo;

import common.value.BaseVO;

public class UserVo extends BaseVO {

	private static final long serialVersionUID = -4456658785094560597L;

	private Integer id;

    private String userName;

    private String password;

    private Integer age;
    
    private String roles;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
    
}
