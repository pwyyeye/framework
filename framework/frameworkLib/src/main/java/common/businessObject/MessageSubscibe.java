package common.businessObject;

import java.util.*;


import common.os.vo.UsersVO;
import common.utils.SemAppUtils;
import common.value.ObserverVO;



public class MessageSubscibe
    extends BaseBusinessObject {
  private Integer empID;

  private UsersVO user;

  private MessageEvent event;
  private ItModule module;

  private int route; //����;����0��ʾOA���ţ�1��ʾMAIL,2��ʾ�ֻ����

  private Calendar beginDate;
  private String beginDateStr;
  private Calendar endDate;
  private String endDateStr;
  private String routeStr;
  private int status; //״̬
  public Calendar getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Calendar beginDate) {
    this.beginDate = beginDate;
  }

  public Integer getEmpID() {
    return empID;
  }

  public void setEmpID(Integer empID) {
    this.empID = empID;
  }

  public Calendar getEndDate() {
    return endDate;
  }

  public void setEndDate(Calendar endDate) {
    this.endDate = endDate;
  }

  public MessageEvent getEvent() {
    return event;
  }

  public void setEvent(MessageEvent event) {
    this.event = event;
  }

  public int getRoute() {
    return route;
  }

  public void setRoute(int route) {
    this.route = route;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getBeginDateStr() {
    return SemAppUtils.getStdDate(beginDate);
  }

  public void setBeginDateStr(String beginDateStr) {
    this.beginDateStr = beginDateStr;
  }

  public String getEndDateStr() {
    return SemAppUtils.getStdDate(endDate);
  }

  public void setEndDateStr(String endDateStr) {
    this.endDateStr = endDateStr;
  }

 

  public void setRouteStr(String routeStr) {
    this.routeStr = routeStr;
  }

public UsersVO getUser() {
	return user;
}

public void setUser(UsersVO user) {
	this.user = user;
}

public Object toVO() {
	ObserverVO vo=new ObserverVO();
	vo.setId(getId());
	vo.setEmpID(empID);
	vo.setEvent(event.getName());
	vo.setBeginDate(beginDate);
	vo.setEndDate(endDate);
	vo.setRoute(route);
    vo.setEventID((Integer)event.getId());
    vo.setModule(module.getName());
    vo.setModuleID((Integer)module.getId());
	return vo;
	
}

public ItModule getModule() {
	return module;
}

public void setModule(ItModule module) {
	this.module = module;
}

 

}
