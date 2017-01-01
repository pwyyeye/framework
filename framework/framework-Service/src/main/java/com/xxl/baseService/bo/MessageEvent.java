package com.xxl.baseService.bo;

import java.util.*;

import common.businessObject.BaseBusinessObject;
import common.value.EventVO;

public class MessageEvent
    extends BaseBusinessObject {
  private String name;

  private int type;

  private int status;

  private List subscibes;
  private ItModule module;//���Ӧϵͳ

  public List getSubscibes() {
    return subscibes;
  }

  public void setSubscibes(List subscibes) {
    this.subscibes = subscibes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
  public ItModule getModule() {
    return module;
  }
  public void setModule(ItModule module) {
    this.module = module;
  }
  //���͵�������
  public String getTypeName(){
    switch(type){
      case 0:return "�û�����";
      case 1:return "��ʱ����";
      default: return "";
    }

  }

public Object toVO() {
	EventVO eventVO = new EventVO((Integer)getId(), module.getName(), name, type, status,(Integer)module.getId());
	return eventVO;
}

}
