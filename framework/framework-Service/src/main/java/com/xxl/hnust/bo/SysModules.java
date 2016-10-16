package com.xxl.hnust.bo;

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

import com.xxl.hnust.vo.SysModulesVo;
import common.businessObject.BaseBusinessObject;
import common.utils.SemAppUtils;

/**
 * 系统模块表
 * @author karys
 *
 */
@Entity
@Table(name = "SYS_MODULES")
public class SysModules extends BaseBusinessObject {
   
	private static final long serialVersionUID = -3863712244057390442L;

	private Integer moduleId; //模块ID

    private String moduleName; //模块名称

    private String moduleDesc; //模块描述
    
    private String moduleType; //模块类型
    
    private String parent; //模块上级
    
    private String moduleUrl; //模块地址
    
    private Integer iLevel; //菜单级别
    
    private Integer left; //最下级

    private String application; //应用名称
    
    private String controller; //控制器名称
    
    private Integer enable; //否可用
    
    private Integer priority; //优先级
    
    private Set<SysRolesModules> sysRolesModules = new HashSet<SysRolesModules>(0);
    
	private Set<SysAuthorities> sysAuthorities = new HashSet<SysAuthorities>(0); //模块和权限对应
	
	private Set<SysResources> sysResources = new HashSet<SysResources>(0); //模块和资源对应

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "MODULE_ID", unique = true, nullable = false, length = 11)
    public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "MODULE_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
    public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "MODULE_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
    public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	@Column(name = "MODULE_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	@Column(name = "PARENT", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Column(name = "MODULE_URL", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	@Column(name = "I_LEVEL", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getiLevel() {
		return iLevel;
	}

	public void setiLevel(Integer iLevel) {
		this.iLevel = iLevel;
	}

	@Column(name = "LEAF", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	@Column(name = "APPLICATION", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	@Column(name = "CONTROLLER", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	@Column(name = "ENABLE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "PRIORITY", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
    public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysModules")
	@OrderBy("id")
	public Set<SysRolesModules> getSysRolesModules() {
		return sysRolesModules;
	}

	public void setSysRolesModules(Set<SysRolesModules> sysRolesModules) {
		this.sysRolesModules = sysRolesModules;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysModules")
	@OrderBy("authorityId")
	public Set<SysAuthorities> getSysAuthorities() {
		return sysAuthorities;
	}

	public void setSysAuthorities(Set<SysAuthorities> sysAuthorities) {
		this.sysAuthorities = sysAuthorities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysModules")
	@OrderBy("resourceId")
	public Set<SysResources> getSysResources() {
		return sysResources;
	}

	public void setSysResources(Set<SysResources> sysResources) {
		this.sysResources = sysResources;
	}

	@Override
	public Object toVO() {
		SysModulesVo vo = new SysModulesVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}
	
}