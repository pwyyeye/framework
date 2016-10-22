package common.value;

import common.web.utils.SemWebAppUtils;

public class RaBindingVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String module;

	private Integer moduleID;

	private String role;

	private Integer roleID;

	private String report;

	private Integer reportID;

	private String rightCode;

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

	public RaBindingVO() {
		super();
	}

	public RaBindingVO(Integer id) {
		super(id);
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Event[module: " + module);
		str.append(" | role: " + role);
		str.append(" | report: " + report);
		str.append("]");
		return str.toString();
	}


	public RaBindingVO(Integer id, String module, Integer moduleID, String role,
			Integer roleID, String report, Integer reportID, String rightCode) {
		super(id);
		this.module = module;
		this.moduleID = moduleID;
		this.role = role;
		this.roleID = roleID;
		this.report = report;
		this.reportID = reportID;
		this.rightCode = rightCode;
	}

	
	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public Integer getReportID() {
		return reportID;
	}

	public void setReportID(Integer reportID) {
		this.reportID = reportID;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public String getAddRightStr() {
		return (isAddRight()) ? "有" : "没有";
	}

	public String getUpdateRightStr() {
		return (isUpdateRight()) ? "有" : "没有";
	}

	public String getDeleteRightStr() {
		return (isDeleteRight()) ? "有" : "没有";
	}

	public String getListRightStr() {
		return (isListRight()) ? "有" : "没有";
	}

	public String getExportRightStr() {
		return (isExportRight()) ? "有" : "没有";
	}

	public String getImportRightStr() {
		return (isImportRight()) ? "有" : "没有";
	}

	public String getCustomRightStr() {
		return (isCustomRight()) ? "有" : "没有";
	}

	public boolean isAddRight() {
		return SemWebAppUtils.hasAddRight(rightCode);
	}

	public boolean isUpdateRight() {
		return SemWebAppUtils.hasUpdateRight(rightCode);
	}

	public boolean isDeleteRight() {
		return SemWebAppUtils.hasDeleteRight(rightCode);
	}

	public boolean isListRight() {
		return SemWebAppUtils.hasListRight(rightCode);
	}

	public boolean isExportRight() {
		return SemWebAppUtils.hasExprotRight(rightCode);
	}

	public boolean isImportRight() {
		return SemWebAppUtils.hasImportRight(rightCode);
	}

	public boolean isCustomRight() {
		return SemWebAppUtils.hasCustomRight(rightCode);
	}

	public String getOtherRightCode() {
		return SemWebAppUtils.getOtherRightCode(rightCode);
	}

}
