package com.xxl.hnust.vo;

import com.common.value.BaseStringVO;
import com.common.value.BaseVO;

public class SysRolesModulesVo extends BaseVO {

	private static final long serialVersionUID = -8055093888124387514L;

	private Integer id;

    private Integer moduleId;
    
    private String moduleName;

    private Integer roleId;
    
    private String roleName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
