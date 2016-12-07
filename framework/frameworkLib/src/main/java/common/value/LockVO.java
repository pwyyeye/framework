package common.value;

import java.util.Calendar;

public class LockVO extends BaseVO{

private String menuName;
private Integer lockConnectID;
private Calendar lockDate;
private String ip;
private Integer empID;
private String empName;
public String getEmpName() {
	return empName;
}
public void setEmpName(String empName) {
	this.empName = empName;
}
public LockVO() {
	super();
}
public LockVO(Integer menuID,String menuName, Integer lockConnectID, Calendar lockDate) {
	super(menuID);
	// TODO Auto-generated constructor stub
	this.menuName = menuName;
	this.lockConnectID = lockConnectID;
	this.lockDate = lockDate;
}
public LockVO(Integer menuID,String menuName, Integer lockConnectID, Calendar lockDate, String ip, Integer empID) {
	super(menuID);
	this.menuName = menuName;
	this.lockConnectID = lockConnectID;
	this.lockDate = lockDate;
	this.ip = ip;
	this.empID = empID;
}
public Integer getEmpID() {
	return empID;
}
public void setEmpID(Integer empID) {
	this.empID = empID;
}
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
public Integer getLockConnectID() {
	return lockConnectID;
}
public void setLockConnectID(Integer lockConnectID) {
	this.lockConnectID = lockConnectID;
}
public Calendar getLockDate() {
	return lockDate;
}
public void setLockDate(Calendar lockDate) {
	this.lockDate = lockDate;
}
public String getMenuName() {
	return menuName;
}
public void setMenuName(String menuName) {
	this.menuName = menuName;
}

}
