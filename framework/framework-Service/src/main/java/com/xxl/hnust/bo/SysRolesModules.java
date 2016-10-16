package com.xxl.hnust.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import com.xxl.hnust.vo.SysRolesModulesVo;
import common.businessObject.BaseBusinessObject;
import common.utils.SemAppUtils;

/**
 * 系统角色模块中间表
 * 控制角色对模块的访问权，主要用于生成菜单
 * @author karys
 *
 */
@Entity
@Table(name = "SYS_ROLES_MOUDLES")
public class SysRolesModules extends BaseBusinessObject {

	private static final long serialVersionUID = 1906491854210674414L;

	private Integer id;

    private SysModules sysModules; //模块ID

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

	@Column(name = "MODULE_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public SysModules getSysModules() {
		return sysModules;
	}

	public void setSysModules(SysModules sysModules) {
		this.sysModules = sysModules;
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
		SysRolesModulesVo vo = new SysRolesModulesVo();
		SemAppUtils.BO2VO(this, vo);
		if(this.getSysModules()!=null) {
			vo.setModuleId(this.getSysModules().getModuleId());
			vo.setModuleName(this.getSysModules().getModuleName());
		}
		if(this.getSysRoles()!=null) {
			vo.setRoleId(this.getSysRoles().getRoleId());
			vo.setRoleName(this.getSysRoles().getRoleName());
		}
		return vo;
	}
	
}