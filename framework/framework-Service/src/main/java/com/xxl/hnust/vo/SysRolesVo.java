package com.xxl.hnust.vo;

import com.common.value.BaseVO;

public class SysRolesVo extends BaseVO {

	private static final long serialVersionUID = 9044205345887205118L;

	private Integer roleId;

	private String roleName;

	private String roleDesc;

	private Integer enable;

	private Integer isSys;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}
	
}
