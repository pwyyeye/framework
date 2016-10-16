package com.xxl.baseService.vo;

import common.value.BaseVO;

public class SysRolesAuthoritiesVo extends BaseVO {

	private static final long serialVersionUID = -9126689709386176369L;
	
	private Integer id;

    private Integer authorityId;
    
    private String authorityName;

    private Integer roleId;
    
    private String roleName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
