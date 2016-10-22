package com.xxl.baseService.bo;

import java.util.List;
import java.util.ArrayList;

import common.businessObject.BaseBusinessObject;
public class Operation extends BaseBusinessObject{
  private String code; 
  private String name;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public Object toVO() {
	// TODO Auto-generated method stub
	return null;
}


}
