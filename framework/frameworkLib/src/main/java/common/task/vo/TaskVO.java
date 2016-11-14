package common.task.vo;

import java.util.Calendar;

import common.utils.SemAppConstants;
import common.value.BaseVO;

public class TaskVO extends BaseVO {

	private String name;

	private Integer type;

	private String method;

	private String argments;

	private Calendar runTime;

	private Calendar lastRunDate;

	private Integer lastRunResult;

	private String lastRunRemark;

	private String module;

	private Integer moduleID;

	private String remark;

	private Integer status;

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public TaskVO(Integer id, String name, Integer type, String method,
			String argments, Calendar runTime, Calendar lastRunDate,
			Integer lastRunResult, String lastRunRemark, String module,
			Integer moduleID, String remark,Integer status) {
		super(id);
		// TODO Auto-generated constructor stub
		this.name = name;
		this.type = type;
		this.method = method;
		this.argments = argments;
		this.runTime = runTime;
		this.lastRunDate = lastRunDate;
		this.lastRunResult = lastRunResult;
		this.lastRunRemark = lastRunRemark;
		this.module = module;
		this.moduleID = moduleID;
		this.remark = remark;
		this.status=status;
	}

	public TaskVO(Integer id) {
		super(id);
	}
	public TaskVO() {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("JMSTaskVO[name: " + name);
		str.append(" | method: " + method);
		str.append("]");
		return str.toString();
	}

	public String getArgments() {
		return argments;
	}

	public void setArgments(String argments) {
		this.argments = argments;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public String getTypeStr(){
		if(type==null)return "";
		switch(type.intValue()){
		   case SemAppConstants.TASK_TYPE_DAY: return "每天";
		   case SemAppConstants.TASK_TYPE_HOUR: return "每小时";
		   case SemAppConstants.TASK_TYPE_WEEK: return "每周";
		   case SemAppConstants.TASK_TYPE_MONTH:return "每月";
		   default:return "";
		}
	}
	public String getLastRunResultStr(){
		if(this.lastRunResult==null)return "";
		return (lastRunResult.intValue()==0?"正常":"异常");
	}
	
	public String getStatusStr(){
		if(status==null) return "";
		return status.intValue()==0?"正常":"已停止";
	}

}
