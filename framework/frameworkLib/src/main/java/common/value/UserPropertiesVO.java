package common.value;

import java.util.Calendar;

public class UserPropertiesVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer usId;
	private SystemPropertyVO property;
	private String value;
	private Calendar setDate;
	private String setUser;
	private Integer timeLimit;
	private Calendar startTime;
	private Calendar endTime;
	public UserPropertiesVO(Integer id) {
		super(id);
	}
	public UserPropertiesVO() {
		super();

	}

	public Integer getUsId() {
		return usId;
	}
	public void setUsId(Integer usId) {
		this.usId = usId;
	}

	public SystemPropertyVO getProperty() {
		return property;
	}
	public void setProperty(SystemPropertyVO property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Calendar getSetDate() {
		return setDate;
	}
	public void setSetDate(Calendar setDate) {
		this.setDate = setDate;
	}
	public String getSetUser() {
		return setUser;
	}
	public void setSetUser(String setUser) {
		this.setUser = setUser;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public Calendar getStartTime() {
		return startTime;
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

}
