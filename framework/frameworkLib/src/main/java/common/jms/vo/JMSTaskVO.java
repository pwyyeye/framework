package common.jms.vo;

import java.util.Calendar;

import common.os.vo.UsersVO;
import common.utils.SemAppUtils;
import common.value.BaseVO;

public class JMSTaskVO extends BaseVO {

	private static final long serialVersionUID = 8947834290860756136L;

	private String messageID;

	private String message;

	private String queueName;

	private Integer empID;

	private Integer status;

	private Integer closeFlag;

	private Calendar createDate;

	private String module;

	private Integer moduleID;

	private Calendar dealDate;

	private String remark;

	private String dealResult;

	private Calendar createStartDate;

	private Calendar createEndDate;

	private Calendar dealStartDate;

	private Calendar dealEndDate;

	public Calendar getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Calendar createEndDate) {
		this.createEndDate = createEndDate;
	}

	public Calendar getCreateStartDate() {
		return createStartDate;
	}

	public void setCreateStartDate(Calendar createStartDate) {
		this.createStartDate = createStartDate;
	}

	public Calendar getDealEndDate() {
		return dealEndDate;
	}

	public void setDealEndDate(Calendar dealEndDate) {
		this.dealEndDate = dealEndDate;
	}

	public Calendar getDealStartDate() {
		return dealStartDate;
	}

	public void setDealStartDate(Calendar dealStartDate) {
		this.dealStartDate = dealStartDate;
	}

	public JMSTaskVO(Integer id, String messageID, String message,
			String queueName, Integer empID, Integer status,
			Calendar createDate, String module, Integer moduleID,
			Calendar dealDate, String remark, String dealResult,Integer closeFlag) {
		super(id);
		this.messageID = messageID;
		this.message = message;
		this.queueName = queueName;
		this.empID = empID;
		this.status = status;
		this.createDate = createDate;
		this.module = module;
		this.moduleID = moduleID;
		this.dealDate = dealDate;
		this.remark = remark;
		this.dealResult = dealResult;
		this.closeFlag=closeFlag;
	}

	public JMSTaskVO(Integer id) {
		super(id);
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		if(status==null)return "";
		switch (status.intValue()) {
		case 1:
			return "成功执行";
		case -1:
			return "执行失败";
		default:
			return "执行中";
		}
	}

	public String getCloseName() {
		if(closeFlag==null){
		    return "未处理";
		}
		switch (closeFlag.intValue()) {
		case 1:
			return "已结案";
		default:
			return "未结案";
		}
	
	}

	public String getCreateDateStr() {
		return SemAppUtils.getFullTime(createDate);
	}

	public String getDealDateStr() {
		return SemAppUtils.getFullTime(dealDate);
	}

	public String getEmpName() {
//		UsersVO user = SemAppUtils.getUserInfo(empID);
//		return user == null ? ""+empID : user.getName();
		return "";
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("JMSTaskVO[queue: " + queueName);
		str.append(" | module: " + module);
		str.append(" | messageID: " + messageID);
		str.append("|message:" + message);
		str.append("]");
		return str.toString();
	}

	public Integer getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(Integer closeFlag) {
		this.closeFlag = closeFlag;
	}
}
