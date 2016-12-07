package common.jms.vo;

import common.value.BaseVO;

public class UserPushVO extends BaseVO{
	Integer userId;
	Integer type;
	String typeName;
	Integer on;
	private String module;

	private Integer moduleID;
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
	public UserPushVO(Integer id) {
		super(id);
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getOn() {
		return on;
	}
	public void setOn(Integer on) {
		this.on = on;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
