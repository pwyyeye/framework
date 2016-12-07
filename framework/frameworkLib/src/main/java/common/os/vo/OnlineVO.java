package common.os.vo;

import java.util.Calendar;

import common.value.BaseVO;

public class OnlineVO extends BaseVO {

	private String userid;

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

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public OnlineVO(Integer id) {
		this.setId(id);
	}

	public Integer getActiveNodeId() {
		return activeNodeId;
	}

	public void setActiveNodeId(Integer activeNodeId) {
		this.activeNodeId = activeNodeId;
	}

	public Integer getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(Integer activeNum) {
		this.activeNum = activeNum;
	}

	public Calendar getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Calendar activeTime) {
		this.activeTime = activeTime;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public Calendar getLastloginTime() {
		return lastloginTime;
	}

	public void setLastloginTime(Calendar lastloginTime) {
		this.lastloginTime = lastloginTime;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Calendar getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Calendar loginTime) {
		this.loginTime = loginTime;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
