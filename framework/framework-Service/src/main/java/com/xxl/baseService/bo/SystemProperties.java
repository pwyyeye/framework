package com.xxl.baseService.bo;

import java.io.Serializable;
import java.util.Calendar;

import common.value.SystemPropertyVO;

public class SystemProperties extends
		common.businessObject.BaseBusinessStringObject {

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Serializable id) {
		super.setId(id);
		this.name=""+id;
	}

	private String value;

	private String remark;

	private ItModule module;

	private Calendar startDate;

	private Calendar endDate;

	private Integer parType;

	private Integer timeLimit;

	private String parent;

	private String setUser;

	private Calendar setDate;

	private String defaultValue;

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Integer getParType() {
		return parType;
	}

	public void setParType(Integer parType) {
		this.parType = parType;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getSetUser() {
		return setUser;
	}

	public void setSetUser(String setUser) {
		this.setUser = setUser;
	}

	public Calendar getSetDate() {
		return setDate;
	}

	public void setSetDate(Calendar setDate) {
		this.setDate = setDate;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public String getName() {
		return getId();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Object toVO() {
		SystemPropertyVO vo = new SystemPropertyVO(getId(), getId(), value,
				remark, module.getName(), startDate, endDate, parType,
				timeLimit, parent, setUser, setDate, module.getId(),
				defaultValue);
		return vo;
	}
}
