package common.value;

import java.util.Calendar;

import common.utils.SemAppUtils;

public class OnlineVO extends BaseVO {
	private Integer empId;

	private String empName;

	private String ip;

	private boolean status;
	
	private Calendar loginDate; 
	
	private String onMethod;  
	
	private String rolename;  
	
	private String moduleName;

	private long lastUpdateMillis;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		if(SemAppUtils.isEmpty(empName)){
			empName=SemAppUtils.getUserInfo(empId).getName();
		}
		
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



	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getStatusStr(){
		return status?"<img src='images/green-ball.gif'  hspace='1'><font color='GREEN'> online</fone>":"<img src='images/red-ball.gif'  hspace='1'><font color='RED'> offline</font>";
	}

	public OnlineVO(Integer id,Integer empId, String empName, String ip, boolean status,
			long lastUpdateMillis) {
		super(id);
		this.empId = empId;
		this.empName = empName;
		this.ip = ip;
		this.status = status;
		this.lastUpdateMillis = lastUpdateMillis;
	}

	public long getLastUpdateMillis() {
		return lastUpdateMillis;
	}

	public void setLastUpdateMillis(long lastUpdateMillis) {
		this.lastUpdateMillis = lastUpdateMillis;
	}
	
	public Calendar getLastUpdateDate(){
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(lastUpdateMillis);
		return cal;
	}

	public Calendar getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Calendar loginDate) {
		this.loginDate = loginDate;
	}

	public String getOnMethod() {
		return onMethod;
	}

	public void setOnMethod(String onMethod) {
		this.onMethod = onMethod;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
