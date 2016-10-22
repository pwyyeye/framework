package common.value;

import java.util.Calendar;

public class FavoriteVO extends BaseVO {

	private String menuID;

	private Integer type;

	private Calendar createDate;

	private Integer empID;

	private String module;

	private String menuName;

	private Integer moduleID;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public FavoriteVO(Integer id, String menuID, Integer type,
			Calendar createDate, Integer empID, Integer moduleID, String module,String name) {
		super(id);
		this.menuID = menuID;
		this.type = type;
		this.createDate = createDate;
		this.empID = empID;
		this.moduleID = moduleID;
		this.module = module;
		this.name=name;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}
