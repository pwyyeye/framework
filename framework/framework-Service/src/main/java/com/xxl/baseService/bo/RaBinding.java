package com.xxl.baseService.bo;

import com.xxl.os.bo.SyOrganise;
import common.businessObject.BaseBusinessObject;
import common.value.RaBindingVO;

public class RaBinding extends BaseBusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4521184985754368673L;
	private Role role;
	 private ItModule module;
	  private SyOrganise organise;

	public SyOrganise getOrganise() {
		return organise;
	}

	public void setOrganise(SyOrganise organise) {
		this.organise = organise;
	}

	private ReportModule report;

	private String rightCode;



	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public RaBinding() {
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	public Object toVO() {
		RaBindingVO vo = new RaBindingVO((Integer)getId(),module.getName(),
				module.getId(), role.getRolename(), (Integer)role.getId(),
				report.getName(), (Integer)report.getId(), rightCode);

		return vo;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public ReportModule getReport() {
		return report;
	}

	public void setReport(ReportModule report) {
		this.report = report;
	}

}
