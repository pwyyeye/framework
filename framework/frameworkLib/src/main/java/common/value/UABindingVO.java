package common.value;

import common.os.vo.DepartmentVO;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;

public class UABindingVO extends BaseVO {

	private String module;
	private Integer moduleID;
	private String role;
	private Integer roleID;
	private Integer empID;
	private Integer deptID;
	private Integer levelID;
	private Integer organise;
	private String organiseName;

	public Integer getOrganise() {
		return organise;
	}

	public void setOrganise(Integer organise) {
		this.organise = organise;
	}

	public String getOrganiseName() {
		return organiseName;
	}

	public void setOrganiseName(String organiseName) {
		this.organiseName = organiseName;
	}

//	public String getDeptName() {
//		if (deptID != null) {
//			Department dept = SemAppUtils.getDeptInfo(deptID);
//			return dept == null ? deptID.toString() : dept.getName();
//		} else {
//			return null;
//		}
//	}
//
//	public String getEmpName() {
//		if (empID != null) {
//			UsersVO user = SemAppUtils.getUserInfo(empID);
//			return user == null ? "" + empID : user.getName();
//		} else {
//			return null;
//		}
//	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public UABindingVO() {
		super();
	}

	public UABindingVO(Integer id, String module, Integer moduleID,
			String role, Integer roleID, Integer empID, Integer deptID,
			Integer levelID) {
		super(id);
		this.module = module;
		this.moduleID = moduleID;
		this.role = role;
		this.roleID = roleID;
		this.empID = empID;
		this.deptID = deptID;
		this.levelID = levelID;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public Integer getDeptID() {
		return deptID;
	}

	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

	public Integer getLevelID() {
		return levelID;
	}

	public void setLevelID(Integer levelID) {
		this.levelID = levelID;
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Event[module: " + module);
		str.append(" | role: " + role);
		str.append(" | empID: " + empID);
		str.append("]");
		return str.toString();
	}

}
