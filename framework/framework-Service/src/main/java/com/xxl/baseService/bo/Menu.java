package com.xxl.baseService.bo;

import java.util.ArrayList;
import java.util.List;

import common.businessObject.BaseBusinessObject;
import common.value.MenuVO;

public class Menu extends BaseBusinessObject {
	private ItModule module;

	private String frame;

	private String name;

	private int parent;

	private int sortID;

	private String link;

	private List roleList;
	
	private int singleMode;

	public int getSingleMode() {
		return singleMode;
	}

	public void setSingleMode(int singleMode) {
		this.singleMode = singleMode;
	}

	public Menu() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getSortID() {
		return sortID;
	}

	public void setSortID(int sortID) {
		this.sortID = sortID;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List getRoleList() {
		return roleList;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	public void addRole(MenuRole aRole) {
		if (roleList == null)
			roleList = new ArrayList();
		aRole.setMenu(this);
		roleList.add(aRole);
	}

	public void removeRole(MenuRole aRole) {
		roleList.remove(aRole);
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public Object toVO() {
		MenuVO vo = new MenuVO((Integer)getId(), module.getName(), module.getId(), name,
				frame, parent, sortID, link,singleMode);
		return vo;
	}

}
