package com.xxl.os.bo;

public class SyUserdutyKey extends common.businessObject.BaseBusinessObject{
	private SyDepartment department;
	private SyDuty duty;
	private SyUsers users;

	public SyDepartment getDepartment() {
		return department;
	}

	public void setDepartment(SyDepartment department) {
		this.department = department;
	}

	public SyDuty getDuty() {
		return duty;
	}

	public void setDuty(SyDuty duty) {
		this.duty = duty;
	}

	public SyUsers getUsers() {
		return users;
	}

	public void setUsers(SyUsers users) {
		this.users = users;
	}

	@Override
	public Object toVO() {
		// TODO Auto-generated method stub
		return null;
	}
}
