package com.xxl.hnust.bo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Column;

import com.common.businessObject.BaseBusinessObject;
import com.common.utils.SemAppUtils;
import com.xxl.hnust.vo.SysAuthoritiesVo;

/**
 * 系统权限表
 * @author karys
 *
 */
@Entity
@Table(name = "SYS_AUTHORITIES")
public class SysAuthorities extends BaseBusinessObject {
   
	private static final long serialVersionUID = -3863712244057390442L;

	private Integer authorityId; //权限ID

    private String authorityMark; //权限标识

    private String authorityName; //权限名称
    
    private String authorityDesc; //权限说明
    
    private String message; //提示信息
    
    private Integer enable; //是否可用
    
    private Integer isSys; //是否系统权限

    private SysModules sysModules; //模块ID
    
    private Set<SysRolesAuthorities> sysRolesAuthorities = new HashSet<SysRolesAuthorities>(0);
    
    private Set<SysAuthoritiesResources> sysAuthoritiesResources = new HashSet<SysAuthoritiesResources>(0);

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "AUTHORITY_ID", unique = true, nullable = false, length = 11)
    public Integer getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	@Column(name = "AUTHORITY_MARK", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getAuthorityMark() {
		return authorityMark;
	}

	public void setAuthorityMark(String authorityMark) {
		this.authorityMark = authorityMark;
	}

	@Column(name = "AUTHORITY_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
    public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	@Column(name = "AUTHORITY_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getAuthorityDesc() {
		return authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	@Column(name = "MESSAGE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "ENABLE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "ISSYS", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODULE_ID")
	public SysModules getSysModules() {
		return sysModules;
	}

	public void setSysModules(SysModules sysModules) {
		this.sysModules = sysModules;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysAuthorities")
	@OrderBy("id")
	public Set<SysRolesAuthorities> getSysRolesAuthorities() {
		return sysRolesAuthorities;
	}

	public void setSysRolesAuthorities(Set<SysRolesAuthorities> sysRolesAuthorities) {
		this.sysRolesAuthorities = sysRolesAuthorities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysAuthorities")
	@OrderBy("id")
	public Set<SysAuthoritiesResources> getSysAuthoritiesResources() {
		return sysAuthoritiesResources;
	}

	public void setSysAuthoritiesResources(
			Set<SysAuthoritiesResources> sysAuthoritiesResources) {
		this.sysAuthoritiesResources = sysAuthoritiesResources;
	}

	@Override
	public Object toVO() {
		SysAuthoritiesVo vo = new SysAuthoritiesVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}
	
}