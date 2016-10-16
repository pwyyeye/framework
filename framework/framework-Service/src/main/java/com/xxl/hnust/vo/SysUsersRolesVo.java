package com.xxl.hnust.vo;

import common.value.BaseVO;

public class SysUsersRolesVo extends BaseVO {

	private static final long serialVersionUID = -8055093888124387514L;

	private Integer id;

    private Integer userId;
    
    private String userName;

    private Integer roleId;
    
    private String roleName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
