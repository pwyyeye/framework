package common.value;

import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

public class MenuRoleVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String module;

	private Integer moduleID;
	
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
	private String role;

	private Integer roleID;

	private String menu;

	private Integer menuID;

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

	public MenuRoleVO() {
		super();
	}

	public MenuRoleVO(Integer id) {
		super(id);
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Event[module: " + module);
		str.append(" | role: " + role);
		str.append(" | menu: " + menu);
		str.append("]");
		return str.toString();
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Integer getMenuID() {
		return menuID;
	}

	public MenuRoleVO(Integer id, String module, Integer moduleID, String role,
			Integer roleID, String menu, Integer menuID, String rightCode) {
		super(id);
		this.module = module;
		this.moduleID = moduleID;
		this.role = role;
		this.roleID = roleID;
		this.menu = menu;
		this.menuID = menuID;
		this.rightCode = rightCode;
	}

	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
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
	
	public boolean isDeleteAllRight(){
		return SemWebAppUtils.hasDeleteAllRight(rightCode);
	}
	public String getDeleteAllRightStr(){
		return (isDeleteAllRight()) ? "有" : "没有";
		//return SemWebAppUtils.hasDeleteAllRight(rightCode);
	}

}
