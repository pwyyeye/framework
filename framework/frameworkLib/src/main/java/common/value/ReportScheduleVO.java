package common.value;

import java.util.Calendar;

import common.businessObject.ItModule;
import common.bussiness.Department;
import common.os.vo.DepartmentVO;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;

public class ReportScheduleVO extends BaseVO {
	private String module;

	private Integer moduleID;

	private Integer reportModuleID;

	private String reportModule;

	private Integer circleType;

	private Calendar firstExcuteDate;

	private Calendar nextExcuteDate;

	private String parameter;

	private Integer recipientsType;

	private String recipients;

	private String subject;

	private String content;

	private String reportName;

	private Integer recipientsSources;

	private String recipientsImplementMethod;

	private Integer valid;

	private String remark;

	private Integer lastExcuteResult;

	private String lastExcuteResultRemark;

	private Calendar lastExcuteDate;

	private Integer recipientsEmpID;

	private String recipientsDpno;

	public String getRecipientsDpno() {
		return recipientsDpno;
	}

	public void setRecipientsDpno(String recipientsDpno) {
		this.recipientsDpno = recipientsDpno;
	}

	public Integer getRecipientsEmpID() {
		return recipientsEmpID;
	}

	public void setRecipientsEmpID(Integer recipientsEmpID) {
		this.recipientsEmpID = recipientsEmpID;
	}

	public Calendar getLastExcuteDate() {
		return lastExcuteDate;
	}

	public void setLastExcuteDate(Calendar lastExcuteDate) {
		this.lastExcuteDate = lastExcuteDate;
	}

	public Integer getLastExcuteResult() {
		return lastExcuteResult;
	}

	public void setLastExcuteResult(Integer lastExcuteResult) {
		this.lastExcuteResult = lastExcuteResult;
	}

	public String getLastExcuteResultRemark() {
		return lastExcuteResultRemark;
	}

	public void setLastExcuteResultRemark(String lastExcuteResultRemark) {
		this.lastExcuteResultRemark = lastExcuteResultRemark;
	}

	public Integer getCircleType() {
		return circleType;
	}

	public void setCircleType(Integer circleType) {
		this.circleType = circleType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getFirstExcuteDate() {
		return firstExcuteDate;
	}

	public void setFirstExcuteDate(Calendar firstExcuteDate) {
		this.firstExcuteDate = firstExcuteDate;
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

	public Calendar getNextExcuteDate() {
		return nextExcuteDate;
	}

	public void setNextExcuteDate(Calendar nextExcuteDate) {
		this.nextExcuteDate = nextExcuteDate;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getRecipientsImplementMethod() {
		return recipientsImplementMethod;
	}

	public void setRecipientsImplementMethod(String recipientsImplementMethod) {
		this.recipientsImplementMethod = recipientsImplementMethod;
	}

	public Integer getRecipientsSources() {
		return recipientsSources;
	}

	public void setRecipientsSources(Integer recipientsSources) {
		this.recipientsSources = recipientsSources;
	}

	public Integer getRecipientsType() {
		return recipientsType;
	}

	public void setRecipientsType(Integer recipientsType) {
		this.recipientsType = recipientsType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReportModule() {
		return reportModule;
	}

	public void setReportModule(String reportModule) {
		this.reportModule = reportModule;
	}

	public Integer getReportModuleID() {
		return reportModuleID;
	}

	public void setReportModuleID(Integer reportModuleID) {
		this.reportModuleID = reportModuleID;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getCircleTypeStr() {
		switch (circleType.intValue()) {
		case 0:
			return "单次有效";
		case 1:
			return "每小时";
		case 2:
			return "每天";
		case 3:
			return "每周";
		case 4:
			return "每月";
		case 5:
			return "每年";
		default:
			return "";
		}
	}

	public String getRecipientsTypeStr() {
		switch (recipientsType.intValue()) {
		case 0:
			return "电子邮箱";
		case 1:
			return "员工工号";
		case 2:
			return "部门编号";
		default:
			return "";
		}
	}

	public String getvalidName() {
		switch (valid.intValue()) {
		case 0:
			return "有效";
		case 1:
			return "无效";
		default:
			return "";
		}
	}

	public String getRecipientsSourcesStr() {
		switch (recipientsType.intValue()) {
		case 0:
			return "来自配置";
		case 1:
			return "来自接口";
		default:
			return "";
		}
	}

	public String getLastExcuteResultStr() {
		switch (lastExcuteResult.intValue()) {
		case 0:
			return "执行成功";
		case 1:
			return "执行失败";
		default:
			return "";
		}
	}
	
	public String getRecipientsEmpName(){
		UsersVO user=SemAppUtils.getUserInfo(this.recipientsEmpID);
		return user==null?""+recipientsEmpID:user.getName();
	}
	public String getRecipientsDeptName(){
		Department dp=SemAppUtils.getDeptInfo(SemAppUtils.getInteger(recipientsDpno));
		return dp==null?""+recipientsDpno:dp.getName();
		
	}

	public ReportScheduleVO(Integer id, String module, Integer moduleID,
			Integer reportModuleID, String reportModule, Integer circleType,
			Calendar firstExcuteDate, Calendar nextExcuteDate,
			String parameter, Integer recipientsType, String recipients,
			String subject, String content, String reportName,
			Integer recipientsSources, String recipientsImplementMethod,
			Integer valid, String remark, Integer lastExcuteResult,
			String lastExcuteResultRemark, Calendar lastExcuteDate,
			Integer recipientsEmpID, String rrcipientsDpno) {
		super(id);
		this.module = module;
		this.moduleID = moduleID;
		this.reportModuleID = reportModuleID;
		this.reportModule = reportModule;
		this.circleType = circleType;
		this.firstExcuteDate = firstExcuteDate;
		this.nextExcuteDate = nextExcuteDate;
		this.parameter = parameter;
		this.recipientsType = recipientsType;
		this.recipients = recipients;
		this.subject = subject;
		this.content = content;
		this.reportName = reportName;
		this.recipientsSources = recipientsSources;
		this.recipientsImplementMethod = recipientsImplementMethod;
		this.valid = valid;
		this.remark = remark;
		this.lastExcuteDate = lastExcuteDate;
		this.lastExcuteResult = lastExcuteResult;
		this.lastExcuteResultRemark = lastExcuteResultRemark;
		this.recipientsEmpID = recipientsEmpID;
		this.recipientsDpno = recipientsDpno;
	}

	public ReportScheduleVO() {

	}

	public ReportScheduleVO(String module, Integer moduleID,
			Integer reportModuleID, String reportModule, Integer circleType,
			Calendar firstExcuteDate, Calendar nextExcuteDate,
			String parameter, Integer recipientsType, String recipients,
			String subject, String content, String reportName,
			Integer recipientsSources, String recipientsImplementMethod,
			Integer valid, String remark, Integer lastExcuteResult,
			String lastExcuteResultRemark, Calendar lastExcuteDate,
			Integer recipientsEmpI, String recipientsDpno) {
		this(null, module, moduleID, reportModuleID, reportModule, circleType,
				firstExcuteDate, nextExcuteDate, parameter, recipientsType,
				recipients, subject, content, reportName, recipientsSources,
				recipientsImplementMethod, valid, remark, lastExcuteResult,
				lastExcuteResultRemark, lastExcuteDate, recipientsEmpI,
				recipientsDpno);
	}

}
