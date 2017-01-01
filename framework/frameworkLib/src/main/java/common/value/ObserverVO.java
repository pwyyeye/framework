package common.value;

import java.util.Calendar;

import common.os.vo.UsersVO;
import common.utils.SemAppUtils;
import common.web.utils.SemWebAppConstants;

public class ObserverVO extends BaseVO{
      private String module;
      private Integer moduleID;
	  private Integer empID;
	  private String event;
	  private Integer eventID;
	  private int route;
	  private Calendar beginDate;
	  private Calendar endDate;
	  private int status;
	  public Calendar getBeginDate() {
	    return beginDate;
	  }
	  public ObserverVO() {
			super();
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

	  public String getEmpName(){
		  UsersVO user=SemAppUtils.getUserInfo(empID);
		  return user==null?""+empID:user.getName();
	  }
	  public Calendar getEndDate() {
	    return endDate;
	  }

	  public void setEndDate(Calendar endDate) {
	    this.endDate = endDate;
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
	  public String getRouteStr() {
	    switch (route) {
	      case SemWebAppConstants.MESSAGE_ROUTE_MAIL:
	        return "MAIL";
	      case SemWebAppConstants.MESSAGE_ROUTE_OA:
	        return "APP推送";
	      case SemWebAppConstants.MESSAGE_ROUTE_MOBILE_NOTE:
	        return "手机短信";
	      default:
	        return " ";
	    }
	  }
	  public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	
	public String toString() {
		  StringBuffer str = new StringBuffer();
		    str.append("ObserverVO[event: "+event);
		    str.append(" | empID: "+empID);
		    str.append("]");
		    return str.toString();
	}

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getModuleID() {
		return moduleID;
	}

	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}







}
