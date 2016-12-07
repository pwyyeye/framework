package com.xxl.baseService.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.value.ReportModuleVO;
import common.value.ReportScheduleVO;

public class ReportSchedule extends BaseBusinessObject {
	private ItModule module;

	private ReportModule reportModule;

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

	public Object toVO() {
		return new ReportScheduleVO((Integer)getId(), module.getName(), module.getId(),
				(Integer)reportModule.getId(), reportModule.getName(), circleType,
				firstExcuteDate, nextExcuteDate, parameter, recipientsType,
				recipients, subject, content, reportName, recipientsSources,
				recipientsImplementMethod, valid, remark, lastExcuteResult,
				lastExcuteResultRemark, lastExcuteDate, recipientsEmpID,
				recipientsDpno);

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

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
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

	public ReportModule getReportModule() {
		return reportModule;
	}

	public void setReportModule(ReportModule reportModule) {
		this.reportModule = reportModule;
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

}
