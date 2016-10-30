package com.xxl.os.bo;

import java.util.Calendar;

import common.os.vo.DutyVO;
import common.os.vo.OnlineVO;
import common.utils.SemAppUtils;

public class EiaOnline extends common.businessObject.BaseBusinessObject {


	private Calendar loginTime;

	private Calendar activeTime;

	private Calendar lastloginTime;

	private Integer activeNum;

	private Integer loginNum;

	private java.lang.String ip;

	private String authkey;

	private Integer activeNodeId;

	private String rightType;

	private String rightId;

	private String desc;

	private String status;
	
	private String rowId;
	
	private SyUsers users;

	

	public SyUsers getUsers() {
		return users;
	}

	public void setUsers(SyUsers users) {
		this.users = users;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public Calendar getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Calendar loginTime) {
		this.loginTime = loginTime;
	}

	public Calendar getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Calendar activeTime) {
		this.activeTime = activeTime;
	}

	public Calendar getLastloginTime() {
		return lastloginTime;
	}

	public void setLastloginTime(Calendar lastloginTime) {
		this.lastloginTime = lastloginTime;
	}

	public Integer getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(Integer activeNum) {
		this.activeNum = activeNum;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public Integer getActiveNodeId() {
		return activeNodeId;
	}

	public void setActiveNodeId(Integer activeNodeId) {
		this.activeNodeId = activeNodeId;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object toVO() {
		OnlineVO vo = new OnlineVO(this.getId());
		SemAppUtils.beanCopy(this, vo);
		return vo;
	}

	public EiaOnline() {

	}

	public EiaOnline(Integer id) {
		this.setId(id);
	}

	public EiaOnline(Integer id,  Calendar loginTime,
			Calendar activeTime, Calendar lastloginTime, Integer activeNum,
			Integer loginNum, String ip, String authkey, Integer activeNodeId,
			String rightType, String rightId, String desc, String status,String rowId) {
		super();
		this.setId(id);

		this.loginTime = loginTime;
		this.activeTime = activeTime;
		this.lastloginTime = lastloginTime;
		this.activeNum = activeNum;
		this.loginNum = loginNum;
		this.ip = ip;
		this.authkey = authkey;
		this.activeNodeId = activeNodeId;
		this.rightType = rightType;
		this.rightId = rightId;
		this.desc = desc;
		this.status = status;
		this.rowId=rowId;
	}
}
