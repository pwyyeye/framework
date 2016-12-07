package com.xxl.baseService.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;

public class UserLogin extends BaseBusinessObject {
	private ItModule module;
	private String empID;
	private Role lastRole;
	private Calendar lastLoginDate;

	public UserLogin() {
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public Calendar getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Calendar lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Role getLastRole() {
		return lastRole;
	}

	public void setLastRole(Role lastRole) {
		this.lastRole = lastRole;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public Object toVO() {
		// TODO Auto-generated method stub
		return null;
	}

}
