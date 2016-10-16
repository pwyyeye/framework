package com.xxl.baseService.bo;

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

import com.xxl.baseService.vo.SysResourcesVo;

import common.businessObject.BaseBusinessObject;
import common.utils.SemAppUtils;

/**
 * 系统资源表
 * 
 * @author karys
 * 
 */
@Entity
@Table(name = "SYS_RESOURCES")
public class SysResources extends BaseBusinessObject {

	private static final long serialVersionUID = -3863712244057390442L;

	private Integer resourceId; //资源ID

	private String resourceType; //资源类型

	private String resourceName; //资源名称

	private String resourceDesc; //资源描述

	private String resourcePath; //资源路径

	private String priority; //优先级

	private Integer enable; //是否可用

	private Integer isSys; //是否系统权限

	private Set<SysAuthoritiesResources> sysAuthoritiesResources = new HashSet<SysAuthoritiesResources>(0);
	
	private SysModules sysModules; //模块ID

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RESOURCE_ID", unique = true, nullable = false, length = 11)
    public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "RESOURCE_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "RESOURCE_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "RESOURCE_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
    public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	@Column(name = "RESOURCE_PATH", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
    public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Column(name = "PRIORITY", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysResources")
	@OrderBy("id")
	public Set<SysAuthoritiesResources> getSysAuthoritiesResources() {
		return sysAuthoritiesResources;
	}

	public void setSysAuthoritiesResources(
			Set<SysAuthoritiesResources> sysAuthoritiesResources) {
		this.sysAuthoritiesResources = sysAuthoritiesResources;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODULE_ID")
	public SysModules getSysModules() {
		return sysModules;
	}

	public void setSysModules(SysModules sysModules) {
		this.sysModules = sysModules;
	}

	@Override
	public Object toVO() {
		SysResourcesVo vo = new SysResourcesVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}

}