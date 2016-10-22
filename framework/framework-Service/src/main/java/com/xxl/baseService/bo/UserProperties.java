package com.xxl.baseService.bo;

import java.math.BigDecimal;
import java.util.Calendar;

import common.businessObject.BaseBusinessStringObject;
import common.utils.SemAppUtils;
import common.value.SystemPropertyVO;
import common.value.UserPropertiesVO;

public class UserProperties extends BaseBusinessStringObject {


	private Integer usId;
	private SystemProperties name;
	private String value;
	private Calendar setDate;
	private String setUser;
	private Integer timeLimit;
	private Calendar startTime;
	private Calendar endTime;
	
	public UserProperties() {
	}

	public UserProperties(Integer usId, SystemProperties name, String value,
			Calendar setDate, String setUser, Integer timeLimit, Calendar startTime,
			Calendar endTime) {
		this.usId = usId;
		this.name = name;
		this.value = value;
		this.setDate = setDate;
		this.setUser = setUser;
		this.timeLimit = timeLimit;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getUsId() {
		return this.usId;
	}

	public void setUsId(Integer usId) {
		this.usId = usId;
	}

	public SystemProperties getName() {
		return this.name;
	}

	public void setName(SystemProperties name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Calendar getSetDate() {
		return this.setDate;
	}

	public void setSetDate(Calendar setDate) {
		this.setDate = setDate;
	}

	public String getsetUser() {
		return this.setUser;
	}

	public void setsetUser(String setUser) {
		this.setUser = setUser;
	}

	public Integer getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	

	public String getSetUser() {
		return setUser;
	}

	public void setSetUser(String setUser) {
		this.setUser = setUser;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Object toVO() {
		UserPropertiesVO vo=new UserPropertiesVO();
		vo.setId(getId());
		SemAppUtils.beanCopy(this, vo);
		SystemPropertyVO spVO=(SystemPropertyVO)name.toVO();
		vo.setProperty(spVO);
		return vo;
	}

}