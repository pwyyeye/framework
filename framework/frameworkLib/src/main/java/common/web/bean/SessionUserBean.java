package common.web.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import common.bussiness.User;
import common.filter.SessionUserInterface;
import common.os.vo.OrganiseVO;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;
import common.value.ItModuleVO;
import common.value.RoleVO;

public class SessionUserBean implements SessionUserInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoleVO role;
	private String userDesc;
	private Integer empID;
	private String unitID;
	private String unitName;
	private String empName;
	private String ip;
	private String token;
	private ItModuleVO module;
	private Map properties;
	private OrganiseVO organise;
	private User commonUser;
	
	private UsersVO CommonUsersVO;

	public OrganiseVO getOrganise() {
		return organise;
	}

	public void setOrganise(OrganiseVO organise) {
		this.organise = organise;
	}

	public RoleVO getRole() {
		return role;
	}

	public void setRole(RoleVO role) {
		this.role = role;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Integer getEmpIDInt() {
		return empID;
	}
	public String getEmpID() {
		return ""+empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}
	public void setEmpID(String empID) {
		this.empID = SemAppUtils.getInteger(empID);
	}

	public String getUnitID() {
		return unitID;
	}

	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public SessionUserBean() {
		properties = new HashMap();
	}

	public SessionUserBean(RoleVO role, Integer empID, String empName,
			String unitID, String unitName, String userDesc, String ip,
			String token, ItModuleVO module) {
		this.ip = ip;
		this.role = role;
		this.userDesc = userDesc;
		this.empID = empID;
		this.empName = empName;
		this.unitID = unitID;
		this.unitName = unitName;
		this.token = token;
		this.module = module;
		properties = new HashMap();
	}

	public SessionUserBean(RoleVO role, Integer empID, String empName,
			String unitID, String unitName, String userDesc, String ip,
			String token, ItModuleVO module, Map properties) {
		this.ip = ip;
		this.role = role;
		this.userDesc = userDesc;
		this.empID = empID;
		this.empName = empName;
		this.unitID = unitID;
		this.unitName = unitName;
		this.token = token;
		this.module = module;
		this.properties = properties;
	}

	public UsersVO getCommonUsersVO() {
//		return SemAppUtils.getUserInfo(empID);
		return this.CommonUsersVO;
	}
	public User getCommonUser() {
//		return SemAppUtils.getUserInfo(""+empID);
		return this.commonUser;
		
	}
	
	public void setCommonUser(User commonUser) {
		this.commonUser = commonUser;
	}

	public void setCommonUsersVO(UsersVO commonUsersVO) {
		CommonUsersVO = commonUsersVO;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map getProperties() {
		return properties;
	}

	public void setProperties(Map properties) {
		this.properties = properties;
	}

	public ItModuleVO getModule() {
		return module;
	}

	public void setModule(ItModuleVO module) {
		this.module = module;
	}

	public String getProperty(String name) {
		if (properties.containsKey(name)) {
			return (String) properties.get(name);
		} else {
			return null;
		}
	}
	public boolean setProperty(String name,String value) {
		boolean exist=(properties.containsKey(name));
		properties.put(name, value);
		return exist;
	}

}
