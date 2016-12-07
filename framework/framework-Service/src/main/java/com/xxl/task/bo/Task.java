package com.xxl.task.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.task.vo.TaskVO;

public class Task extends BaseBusinessObject {

	private String name;

	private Integer type;

	private String method;

	private String argments;

	private Calendar runTime;

	private Calendar lastRunDate;

	private Integer lastRunResult;

	private String lastRunRemark;
	private String remark;
	private ItModule module;
	private Integer status;
	



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}



	public Object toVO() {
		TaskVO vo = new TaskVO((Integer)getId(), name, type, method,
				argments, runTime, lastRunDate,
				lastRunResult, lastRunRemark, module.getName(),
				module.getId(), remark,status);
		return vo;
	}

	public String getArgments() {
		return argments;
	}

	public void setArgments(String argments) {
		this.argments = argments;
	}

	public Calendar getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Calendar lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	public String getLastRunRemark() {
		return lastRunRemark;
	}

	public void setLastRunRemark(String lastRunRemark) {
		this.lastRunRemark = lastRunRemark;
	}

	public Integer getLastRunResult() {
		return lastRunResult;
	}

	public void setLastRunResult(Integer lastRunResult) {
		this.lastRunResult = lastRunResult;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Calendar getRunTime() {
		return runTime;
	}

	public void setRunTime(Calendar runTime) {
		this.runTime = runTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
