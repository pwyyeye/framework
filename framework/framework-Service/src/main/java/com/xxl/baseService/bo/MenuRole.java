package com.xxl.baseService.bo;

import com.xxl.os.bo.SyOrganise;
import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.value.MenuRoleVO;

public class MenuRole extends BaseBusinessObject {
	private Role role;
	 private ItModule module;

	private Menu menu;

	private String rightCode;

	private SyOrganise organise;

	public SyOrganise getOrganise() {
		return organise;
	}

	public void setOrganise(SyOrganise organise) {
		this.organise = organise;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public MenuRole() {
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Object toVO() {
		MenuRoleVO vo = new MenuRoleVO((Integer)getId(),module.getName(),
				module.getId(), role.getRolename(), (Integer)role.getId(),
				menu.getName(), (Integer)menu.getId(), rightCode);
//		vo.setOrganise(organise.getId());
//		vo.setOrganiseName(organise.getName());
		return vo;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

}
