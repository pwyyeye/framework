package com.xxl.baseService.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import com.xxl.baseService.vo.SysUsersRolesVo;

import common.businessObject.BaseBusinessObject;
import common.utils.SemAppUtils;

/**
 * 系统用户角色中间表
 * @author karys
 *
 */
@Entity
@Table(name = "SYS_USERS_ROLES")
public class SysUsersRoles extends BaseBusinessObject {

	private static final long serialVersionUID = 1906491854210674414L;

	private Integer id;

    private SysUsers sysUsers; //用户ID

    private SysRoles sysRoles; //角色ID

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 11)
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USER_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public SysUsers getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(SysUsers sysUsers) {
		this.sysUsers = sysUsers;
	}

	@Column(name = "ROLE_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
    public SysRoles getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(SysRoles sysRoles) {
		this.sysRoles = sysRoles;
	}

	@Override
	public Object toVO() {
		SysUsersRolesVo vo = new SysUsersRolesVo();
		SemAppUtils.BO2VO(this, vo);
		if(this.getSysRoles()!=null) {
			vo.setRoleId(this.getSysRoles().getRoleId());
			vo.setRoleName(this.getSysRoles().getRoleName());
		}
		if(this.getSysUsers()!=null) {
			vo.setUserId(this.getSysUsers().getUserId());
			vo.setUserName(this.getSysUsers().getUserName());
		}
		return vo;
	}
	
}