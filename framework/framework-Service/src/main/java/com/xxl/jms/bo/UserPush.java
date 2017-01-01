package com.xxl.jms.bo;

import com.xxl.baseService.bo.ItModule;
import common.businessObject.BaseBusinessObject;
import common.jms.vo.UserPushVO;
import common.utils.SemAppUtils;

public class UserPush extends BaseBusinessObject {

	private Integer userId;
	private UserPushType type;
	private Integer on;
	private ItModule module;
	
	
	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public UserPush(Integer id) {
		//super();
		setId(id);
	}

	public UserPush() {
		//super();

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

	public Integer getOn() {
		return on;
	}

	public void setOn(Integer on) {
		this.on = on;
	}

	@Override
	public Object toVO() {
		UserPushVO vo = new UserPushVO(null);
		SemAppUtils.BO2VO(this, vo);
		vo.setModule(module.getName());
		vo.setModuleID(module.getId());
		vo.setType(type.getId());
		vo.setTypeName(type.getName());
		return vo;
	}

}
