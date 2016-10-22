package com.xxl.baseService.bo;

import common.businessObject.BaseBusinessObject;
import common.businessObject.ItModule;
import common.value.RoleVO;

public class Role extends BaseBusinessObject{
  private ItModule module;
  private SyOrganise organise;
  private String rolename;
  public SyOrganise getOrganise() {
	return organise;
}
public void setOrganise(SyOrganise organise) {
	this.organise = organise;
}
private Integer valid;
 
  public Role() {
  }
  public String getRolename() {
    return rolename;
  }
  public void setRolename(String rolename) {
    this.rolename = rolename;
  }
  public String toString(){
	  return module+"->"+rolename;
  }
public ItModule getModule() {
	return module;
}
public void setModule(ItModule module) {
	this.module = module;
}
public Integer getValid() {
	return valid;
}
public void setValid(Integer valid) {
	this.valid = valid;
}
public Object toVO() {
	RoleVO vo=new RoleVO((Integer)getId(),module.getName(), rolename, valid, getDescription(),(Integer)module.getId());
	vo.setOrganise(organise.getId());
	vo.setOrganiseName(organise.getName());
	return vo;
}


}
