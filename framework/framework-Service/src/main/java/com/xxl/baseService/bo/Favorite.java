package com.xxl.baseService.bo;

import java.util.Calendar;

import common.businessObject.BaseBusinessObject;
import common.value.FavoriteVO;

public class Favorite  extends BaseBusinessObject{
	private String menuID;
	private Integer type;
	private Calendar createDate;
	private Integer empID;
	private String name;
	private ItModule module;

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public Object toVO() {
	
		return new FavoriteVO((Integer)getId(),menuID,type,createDate,empID,module.getId(),module.getName(),name);
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public String getMenuID() {
		return menuID;
	}

	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
