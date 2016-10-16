package com.xxl.hnust.vo;

import common.value.BaseVO;

public class SysModulesVo extends BaseVO {

	private static final long serialVersionUID = -3638638957859853011L;
	
	private Integer moduleId;

    private String moduleName;

    private String moduleDesc;
    
    private String moduleType;
    
    private String parent;
    
    private String moduleUrl;
    
    private Integer iLevel;
    
    private Integer left;

    private String application;
    
    private String controller;
    
    private Integer enable;
    
    private Integer priority;

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public Integer getiLevel() {
		return iLevel;
	}

	public void setiLevel(Integer iLevel) {
		this.iLevel = iLevel;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
