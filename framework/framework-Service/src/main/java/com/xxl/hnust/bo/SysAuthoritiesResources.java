package com.xxl.hnust.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import com.common.businessObject.BaseBusinessObject;
import com.common.utils.SemAppUtils;
import com.xxl.hnust.vo.SysAuthoritiesResourcesVo;

/**
 * 系统权限资源中间表
 * @author karys
 *
 */
@Entity
@Table(name = "SYS_AUTHORITIES_RESOURCES")
public class SysAuthoritiesResources extends BaseBusinessObject {

	private static final long serialVersionUID = -9093466520062748295L;

	private Integer id;

    private SysResources sysResources; //资源ID

    private SysAuthorities sysAuthorities; //权限ID

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 11)
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "RESOURCE_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 11)
    public SysResources getSysResources() {
		return sysResources;
	}

	public void setSysResources(SysResources sysResources) {
		this.sysResources = sysResources;
	}

	@Column(name = "AUTHORITY_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 11)
    public SysAuthorities getSysAuthorities() {
		return sysAuthorities;
	}

	public void setSysAuthorities(SysAuthorities sysAuthorities) {
		this.sysAuthorities = sysAuthorities;
	}

	@Override
	public Object toVO() {
		SysAuthoritiesResourcesVo vo = new SysAuthoritiesResourcesVo();
		SemAppUtils.BO2VO(this, vo);
		if(this.getSysAuthorities()!=null) {
			vo.setAuthorityId(this.getSysAuthorities().getAuthorityId());
			vo.setAuthorityName(this.getSysAuthorities().getAuthorityName());
		}
		if(this.getSysResources()!=null) {
			vo.setResourceId(this.getSysResources().getResourceId());
			vo.setResourceName(this.getSysResources().getResourceName());
		}
		return vo;
	}
	
}