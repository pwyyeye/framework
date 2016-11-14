package com.xxl.jms.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.jms.vo.JMSTaskVO;
import common.utils.SemAppConstants;

public class JMSTask extends BaseBusinessObject {
	private String messageID;

	private String message;

	private String queueName;

	private Integer empID;

	private Integer status;

	private Calendar createDate;

	private ItModule module;

	private Calendar dealDate;
	private String anCar;

	private String remark;

	private String dealResult;
	
	private Integer closeFlag;

	public Integer getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(Integer closeFlag) {
		this.closeFlag = closeFlag;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Calendar getDealDate() {
		return dealDate;
	}

	public void setDealDate(Calendar dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Object toVO() {
		JMSTaskVO vo = new JMSTaskVO((Integer)this.getId());
		vo.setModuleID(module.getId());
		vo.setModule(module.getName());
		vo.setCreateDate(createDate);
		vo.setDealDate(dealDate);
		vo.setDealResult(dealResult);
		vo.setEmpID(empID);
		vo.setMessage(message);
		vo.setMessageID(messageID);
		vo.setQueueName(queueName);
		vo.setRemark(remark);
		vo.setStatus(status);
		vo.setCloseFlag(closeFlag);
		return vo;
	}



}
