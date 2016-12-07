package com.xxl.baseService.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.value.NoticeVO;
import common.value.RoleVO;

public class Notice extends BaseBusinessObject {
	private ItModule module;

	private String subject;

	private Integer valid;

	private String content;

	private Calendar startDate;

	private Calendar endDate;

	private Integer attach;
	
	private String attachName;

	public Integer getAttach() {
		return attach;
	}

	public void setAttach(Integer attach) {
		this.attach = attach;
	}

	public Notice() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Object toVO() {
		NoticeVO vo = new NoticeVO((Integer)getId(), module.getName(), module.getId(),
				subject, valid, content, startDate, endDate, attach,attachName);
		return vo;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

}
