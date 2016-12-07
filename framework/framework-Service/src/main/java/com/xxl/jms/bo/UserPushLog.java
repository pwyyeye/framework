package com.xxl.jms.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.jms.vo.UserPushLogVO;
import common.jms.vo.UserPushVO;
import common.utils.SemAppUtils;

public class UserPushLog extends BaseBusinessObject{
	private Integer userId;
	private UserPushType type;
	private Integer result;
	private String title;
	private String content;
	private Integer createUser;
	private Calendar createDate;
	private String remark;
	private ItModule module;
	private Integer read;
	private Calendar readDate;
	private Integer bzId;
	
	public Calendar getReadDate() {
		return readDate;
	}
	public void setReadDate(Calendar readDate) {
		this.readDate = readDate;
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public ItModule getModule() {
		return module;
	}
	public void setModule(ItModule module) {
		this.module = module;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public UserPushLog(){
		
	}
	public UserPushLog(Integer userId, UserPushType type, Integer result,
			String title, String content, Integer createUser,
			Calendar createDate,String remark,ItModule module,Integer read) {
		super();
		this.userId = userId;
		this.type = type;
		this.result = result;
		this.title = title;
		this.content = content;
		this.createUser = createUser;
		this.createDate = createDate;
		this.remark=remark;
		this.module=module;
		this.read=read;
	}

	@Override
	public Object toVO() {
		UserPushLogVO vo = new UserPushLogVO();
		SemAppUtils.BO2VO(this, vo);
		vo.setModule(module.getName());
		vo.setModuleID(module.getId());
		vo.setType(type.getId());
		vo.setTypeName(type.getName());
		return vo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public UserPushType getType() {
		return type;
	}

	public void setType(UserPushType type) {
		this.type = type;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}
	public Integer getBzId() {
		return bzId;
	}
	public void setBzId(Integer bzId) {
		this.bzId = bzId;
	}

}
