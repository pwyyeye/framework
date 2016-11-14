package common.task.vo;

import java.util.Calendar;


import common.value.BaseVO;

public class TaskLogVO extends BaseVO {

	private String module;

	private Integer moduleID;

	private Integer taskID;

	private String taskName;

	private Calendar beginDate;

	private Calendar endDate;

	private Integer runResult;

	private String runContent;

	public TaskLogVO(Integer id, String module, Integer moduleID,
			Integer taskID, String taskName, Calendar beginDate,
			Calendar endDate, Integer runResult, String runContent) {
		super(id);
		this.module = module;
		this.moduleID = moduleID;
		this.taskID = taskID;
		this.taskName = taskName;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.runResult = runResult;
		this.runContent = runContent;
	}

	public TaskLogVO(Integer id) {
		super(id);
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getModuleID() {
		return moduleID;
	}

	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("TaskLogVO[task: " + taskName);
		str.append(" | module: " + module);
		str.append("]");
		return str.toString();
	}

	public String getRunResultStr() {
		if (runResult == null)
			return "";
		switch (runResult.intValue()) {
		case 0:
			return "运行中";
		case 1:
			return "运行成功";
		case -1:
			return "运行失败";
		default:
			return "";
		}
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

	public Integer getTaskID() {
		return taskID;
	}

	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
