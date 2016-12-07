package common.value;

import java.io.Serializable;
import java.util.Calendar;

public class SystemPropertyVO extends BaseStringVO {

	//private Object id;
	private String name;
	private String value;
	private String remark;
	private String module;

	private String defaultValue;
	
	private boolean leaf;

	public boolean isLeaf() {
		try {
			switch (parType.intValue()) {
			case 0: {
				return true;
			}
			case 1: {
				return false;
			}
			default: {
				return false;
			}
			}
		} catch (Exception e) {
			return false;
		}
	}



	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}



	public String getDefaultValue() {
		return defaultValue;
	}
	


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	private Calendar startDate;

	private Calendar endDate;

	private Integer parType;

	private Integer timeLimit;

	private String parent;

	private String setUser;

	private Calendar setDate;

	
	public SystemPropertyVO() {
		super();
	}
	public SystemPropertyVO(String id, String name, String value,
			String remark, String module, Calendar startDate, Calendar endDate,
			Integer parType, Integer timeLimit, String parent, String setUser,
			Calendar setDate, Integer moduleID, String defaultValue) {
		super(id);
	
		this.name = name;
		this.value = value;
		this.remark = remark;
		this.module = module;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parType = parType;
		this.timeLimit = timeLimit;
		this.parent = parent;
		this.setUser = setUser;
		this.setDate = setDate;
		this.moduleID = moduleID;
		this.defaultValue = defaultValue;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Integer getParType() {
		return parType;
	}

	public void setParType(Integer parType) {
		this.parType = parType;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getSetUser() {
		return setUser;
	}

	public void setSetUser(String setUser) {
		this.setUser = setUser;
	}

	public Calendar getSetDate() {
		return setDate;
	}

	public void setSetDate(Calendar setDate) {
		this.setDate = setDate;
	}

	private Integer moduleID;

	public SystemPropertyVO(Serializable id) {
		super();
		setId((String) id);
	}

	public SystemPropertyVO(Serializable id, String name, String value,
			String remark, String module, Integer moduleID) {
		super();

		this.setId((String) id);
		this.name = name;
		this.value = value;
		this.remark = remark;
		this.module = module;
		this.moduleID = moduleID;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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


	public String getParTypeStr() {
		try {
			switch (parType.intValue()) {
			case 0: {
				return "系统参数";
			}
			case 1: {
				return "目录";
			}
			default: {
				return "-";
			}
			}
		} catch (Exception e) {
			return "-";
		}
	}
}
