package com.xxl.baseService.bo;

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

import com.xxl.baseService.vo.SysRolesVo;

import common.businessObject.BaseBusinessObject;
import common.utils.SemAppUtils;

/**
 * 系统角色表
 * 
 * @author karys
 * 
 */
@Entity
@Table(name = "SYS_ROLES")
public class SysRoles extends BaseBusinessObject {

	private static final long serialVersionUID = 4240711133153997628L;

	private Integer roleId; //角色ID

	private String roleName; //角色名称

	private String roleDesc; //角色说明

	private Integer enable; //是否可用

	private Integer isSys; //是否系统权限

	//private String moduleId; //模块ID
	
	private Set<SysUsersRoles> sysUsersRoles = new HashSet<SysUsersRoles>(0);
	
	private Set<SysRolesAuthorities> sysRolesAuthorities = new HashSet<SysRolesAuthorities>(0);
	
	private Set<SysRolesModules> sysRolesModules = new HashSet<SysRolesModules>(0);

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", unique = true, nullable = false, length = 11)
    public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "ROLE_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
    public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(name = "ENABLE", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "ISSYS", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	/*@Column(name = "MODULE_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}*/

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRoles")
	@OrderBy("id")
	public Set<SysUsersRoles> getSysUsersRoles() {
		return sysUsersRoles;
	}

	public void setSysUsersRoles(Set<SysUsersRoles> sysUsersRoles) {
		this.sysUsersRoles = sysUsersRoles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRoles")
	@OrderBy("id")
	public Set<SysRolesAuthorities> getSysRolesAuthorities() {
		return sysRolesAuthorities;
	}

	public void setSysRolesAuthorities(Set<SysRolesAuthorities> sysRolesAuthorities) {
		this.sysRolesAuthorities = sysRolesAuthorities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRoles")
	@OrderBy("id")
	public Set<SysRolesModules> getSysRolesModules() {
		return sysRolesModules;
	}

	public void setSysRolesModules(Set<SysRolesModules> sysRolesModules) {
		this.sysRolesModules = sysRolesModules;
	}

	@Override
	public Object toVO() {
		SysRolesVo vo = new SysRolesVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}

}