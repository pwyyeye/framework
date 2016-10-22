package com.xxl.baseService.bo;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;
import common.value.UABindingVO;

public class UABinding extends BaseBusinessObject {
	private ItModule module;
	private SyOrganise organise;

	public SyOrganise getOrganise() {
		return organise;
	}

	public void setOrganise(SyOrganise organise) {
		this.organise = organise;
	}

	private Role role;
	private Integer empID;
	private Integer deptID;
	private Integer levelID;
	private String empName;
	private String deptName;

	public String getDeptName() {
		return deptName;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public Integer getDeptID() {
		return deptID;
	}

	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

	public Integer getLevelID() {
		return levelID;
	}

	public void setLevelID(Integer levelID) {
		this.levelID = levelID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public Object toVO() {
		UABindingVO vo = new UABindingVO((Integer) getId(), module.getName(),
				module.getId(), role.getRolename(), (Integer) role.getId(),
				empID, deptID, levelID);
		vo.setOrganise(organise.getId());
		vo.setOrganiseName(organise.getName());
		return vo;
	}

}
