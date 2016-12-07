package com.xxl.task.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.task.vo.TaskLogVO;

public class TaskLog extends BaseBusinessObject {

	private Task task;

	private Calendar beginDate;

	private Calendar endDate;

	private Integer runResult;

	private String runContent;

	private ItModule module;

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public Object toVO() {
		TaskLogVO vo = new TaskLogVO((Integer)getId(), module.getName(), module.getId(),
				(Integer)task.getId(), task.getName(), beginDate, endDate, runResult,
				runContent);
		return vo;
	}

	public Calendar getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getRunContent() {
		return runContent;
	}

	public void setRunContent(String runContent) {
		this.runContent = runContent;
	}

	public Integer getRunResult() {
		return runResult;
	}

	public void setRunResult(Integer runResult) {
		this.runResult = runResult;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
