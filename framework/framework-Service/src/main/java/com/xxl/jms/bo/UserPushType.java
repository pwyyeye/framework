package com.xxl.jms.bo;

import com.xxl.baseService.bo.ItModule;
import common.businessObject.BaseBusinessObject;

public class UserPushType extends BaseBusinessObject{
	private String name;
	private String remark;
	private ItModule module;
	public ItModule getModule() {
		return module;
	}
	public void setModule(ItModule module) {
		this.module = module;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public Object toVO() {
		// TODO Auto-generated method stub
		return null;
	}

}
