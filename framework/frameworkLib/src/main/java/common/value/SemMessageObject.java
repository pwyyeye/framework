package common.value;

import common.web.bean.SessionUserBean;

public class SemMessageObject extends BaseVO{

	private SessionUserBean user; 
	private Object contentObj;
	private Integer subQueueID;
	public Integer getSubQueueID() {
		return subQueueID;
	}
	public void setSubQueueID(Integer subQueueID) {
		this.subQueueID = subQueueID;
	}
	public SemMessageObject(SessionUserBean user, Object contentObj,Integer subQueueID) {
		super();
		this.user = user;
		this.contentObj = contentObj;
		this.subQueueID=subQueueID;
	}
	
	public SemMessageObject() {
		super();
	}
	public Object getContentObj() {
		return contentObj;
	}
	public void setContentObj(Object contentObj) {
		this.contentObj = contentObj;
	}
	public SessionUserBean getUser() {
		return user;
	}
	public void setUser(SessionUserBean user) {
		this.user = user;
	}

}
