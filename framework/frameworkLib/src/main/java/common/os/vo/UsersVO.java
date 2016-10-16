package common.os.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.utils.SemAppUtils;
import common.value.BaseVO;


public  class UsersVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getImUserId() {
//		try {
//			return SemAppUtils.encrytor(""+getId());
//		} catch (Exception e) {
//		    return ""+getId();
//		}
		return this.imUserId;
	}

	public void setImUserId(String imUserId) {
		this.imUserId = imUserId;
	}

	public UsersVO() {
			
	}

	public UsersVO(Integer id) {
		setId(id);
	}
	
	private Integer department;
	
	private String departmentName;
	
	private String departmentCode;

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}


	private String name;

	private String password;
 
	private String loginId;
  
	private String code;

	private String sex;

	private Integer party;

	private String peoples;

	private String nationality;

	private String nativePlace;

	private String wedLock;

	private Integer educateLevel;

	private String archAddr;

	private Integer credentialType;

	private String credentialNo;

	private String tel;

	private String wechat;

	private String qq;

	private String weibo;

	private String email;

	private Integer parentId;

	private Integer orderNo;

	private String mobile;

	private String housetel;

	private String title;

	private Integer level;

	private String mark;

	private String status;

	private Calendar registerDate;

	private Calendar logoutDate;

	private Integer timeType;

	private String engineer;

	private Calendar birthday;

	private String bloodType;

	private Integer isDirector;

	private Integer IsAd;

	private String isOpen;

	private String remark;
	
	private String searchValue;

	private String imUserId;
	
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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}



	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getArchAddr() {
		return archAddr;
	}

	public void setArchAddr(String archAddr) {
		this.archAddr = archAddr;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCredentialNo() {
		return credentialNo;
	}

	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}

	public Integer getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}

	






	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getEducateLevel() {
		return educateLevel;
	}

	public void setEducateLevel(Integer educateLevel) {
		this.educateLevel = educateLevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getHousetel() {
		return housetel;
	}

	public void setHousetel(String housetel) {
		this.housetel = housetel;
	}

	public Integer getIsAd() {
		return IsAd;
	}

	public void setIsAd(Integer isAd) {
		IsAd = isAd;
	}

	public Integer getIsDirector() {
		return isDirector;
	}

	public void setIsDirector(Integer isDirector) {
		this.isDirector = isDirector;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Calendar getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Calendar logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParty() {
		return party;
	}

	public void setParty(Integer party) {
		this.party = party;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPeoples() {
		return peoples;
	}

	public void setPeoples(String peoples) {
		this.peoples = peoples;
	}



	public Calendar getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Calendar registerDate) {
		this.registerDate = registerDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimetype(Integer timeType) {
		this.timeType = timeType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWedLock() {
		return wedLock;
	}
	
	public String getSexStr(){
		if("1".equals(sex)){
			return "男";
		}else if("0".equals(sex)){
			return "女";
		}else{
			return "-";
		}
	}
	
	public String getNameAndTitle(){
		return name+"("+title+")";
	}

	public void setWedLock(String wedLock) {
		this.wedLock = wedLock;
	}
	private List userTag;

	public List getUserTag() {
		return userTag;
	}

	public void setUserTag(List userTag) {
		this.userTag = userTag;
	}
	

}
