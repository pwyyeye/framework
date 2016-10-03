package com.xxl.hnust.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import com.common.businessObject.BaseBusinessObject;
import com.common.utils.SemAppUtils;
import com.xxl.hnust.vo.SysRolesAuthoritiesVo;

/**
 * 系统角色权限中间表
 * @author karys
 *
 */
@Entity
@Table(name = "SYS_ROLES_AUTHORITIES")
public class SysRolesAuthorities extends BaseBusinessObject {

	private static final long serialVersionUID = -9093466520062748295L;

	private Integer id;

    private SysAuthorities sysAuthorities; //权限ID

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

	@Column(name = "AUTHORITY_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 11)
    public SysAuthorities getSysAuthorities() {
		return sysAuthorities;
	}

	public void setSysAuthorities(SysAuthorities sysAuthorities) {
		this.sysAuthorities = sysAuthorities;
	}

	@Column(name = "ROLE_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 11)
    public SysRoles getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(SysRoles sysRoles) {
		this.sysRoles = sysRoles;
	}

	@Override
	public Object toVO() {
		SysRolesAuthoritiesVo vo = new SysRolesAuthoritiesVo();
		SemAppUtils.BO2VO(this, vo);
		if(this.getSysAuthorities()!=null) {
			vo.setAuthorityId(this.getSysAuthorities().getAuthorityId());
			vo.setAuthorityName(this.getSysAuthorities().getAuthorityName());
		}
		if(this.getSysRoles()!=null) {
			vo.setRoleId(this.getSysRoles().getRoleId());
			vo.setRoleName(this.getSysRoles().getRoleName());
		}
		return vo;
	}
	
}