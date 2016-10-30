package com.xxl.os.bo;

import java.util.Calendar;

import common.os.vo.DepartmentVO;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;


public  class SyUsers extends common.businessObject.BaseBusinessObject
		{

	
	private SyDepartment syDepartment;
	private SyOrganise syOrganise;
		public SyOrganise getSyOrganise() {
		return syOrganise;
	}


	public void setSyOrganise(SyOrganise syOrganise) {
		this.syOrganise = syOrganise;
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


	public SyUsers(Integer id) {
		this.setId(id);
	}


	public SyDepartment getSyDepartment() {
		return syDepartment;
	}


	public void setSyDepartment(SyDepartment syDepartment) {
		this.syDepartment = syDepartment;
	}





	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public Integer getParty() {
		return party;
	}


	public void setParty(Integer party) {
		this.party = party;
	}


	public String getPeoples() {
		return peoples;
	}


	public void setPeoples(String peoples) {
		this.peoples = peoples;
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


	public String getWedLock() {
		return wedLock;
	}


	public void setWedLock(String wedLock) {
		this.wedLock = wedLock;
	}


	public Integer getEducateLevel() {
		return educateLevel;
	}


	public void setEducateLevel(Integer educateLevel) {
		this.educateLevel = educateLevel;
	}


	public String getArchAddr() {
		return archAddr;
	}


	public void setArchAddr(String archAddr) {
		this.archAddr = archAddr;
	}


	public Integer getCredentialType() {
		return credentialType;
	}


	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}


	public String getCredentialNo() {
		return credentialNo;
	}


	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getParentId() {
		return parentId;
	}


	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	public Integer getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getHousetel() {
		return housetel;
	}


	public void setHousetel(String housetel) {
		this.housetel = housetel;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public String getMark() {
		return mark;
	}


	public void setMark(String mark) {
		this.mark = mark;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Calendar getRegisterDate() {
		return registerDate;
	}


	public void setRegisterDate(Calendar registerDate) {
		this.registerDate = registerDate;
	}


	public Calendar getLogoutDate() {
		return logoutDate;
	}


	public void setLogoutDate(Calendar logoutDate) {
		this.logoutDate = logoutDate;
	}


	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}


	public String getEngineer() {
		return engineer;
	}


	public void setEngineer(String engineer) {
		this.engineer = engineer;
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


	public Integer getIsDirector() {
		return isDirector;
	}


	public void setIsDirector(Integer isDirector) {
		this.isDirector = isDirector;
	}


	public Integer getIsAd() {
		return IsAd;
	}


	public void setIsAd(Integer isAd) {
		IsAd = isAd;
	}


	public String getIsOpen() {
		return isOpen;
	}


	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Object toVO() {
		UsersVO vo=new UsersVO(this.getId());
		SemAppUtils.BO2VO(this, vo);
		vo.setDepartment(syDepartment.getId());
		vo.setDepartmentName(syDepartment.getName());	
		vo.setMark(this.getUrl(mark));
		return vo;
	}

	public SyUsers(){
		
	}


	public SyUsers(Integer id,SyDepartment syDepartment, 
			String name, String password, String loginId, String code,
			String sex, Integer party, String peoples, String nationality,
			String nativePlace, String wedLock, Integer educateLevel,
			String archAddr, Integer credentialType, String credentialNo,
			String tel, String wechat, String qq, String weibo,
			String email, Integer parentId, Integer orderNo, String mobile,
			String housetel, String title, Integer level, String mark,
			String status, Calendar registerDate, Calendar logoutDate,
			Integer timeType, String engineer, Calendar birthday,
			String bloodType, Integer isDirector, Integer isAd, String isOpen,
			String remark) {
		super();
		this.setId(id);
		this.syDepartment = syDepartment;
		this.name = name;
		this.password = password;
		this.loginId = loginId;
		this.code = code;
		this.sex = sex;
		this.party = party;
		this.peoples = peoples;
		this.nationality = nationality;
		this.nativePlace = nativePlace;
		this.wedLock = wedLock;
		this.educateLevel = educateLevel;
		this.archAddr = archAddr;
		this.credentialType = credentialType;
		this.credentialNo = credentialNo;
		this.tel = tel;
		this.wechat = wechat;
		this.qq = qq;
		this.weibo = weibo;
		this.email = email;
		this.parentId = parentId;
		this.orderNo = orderNo;
		this.mobile = mobile;
		this.housetel = housetel;
		this.title = title;
		this.level = level;
		this.mark = mark;
		this.status = status;
		this.registerDate = registerDate;
		this.logoutDate = logoutDate;
		this.timeType = timeType;
		this.engineer = engineer;
		this.birthday = birthday;
		this.bloodType = bloodType;
		this.isDirector = isDirector;
		this.IsAd = isAd;
		this.isOpen = isOpen;
		this.remark = remark;
	}
	public String toString() {
		return "SyUsers [IsAd=" + IsAd + ", qq=" + qq + ", archAddr="
				+ archAddr + ", birthday=" + birthday + ", bloodType="
				+ bloodType + ", code=" + code + ", credentialNo="
				+ credentialNo + ", credentialType=" + credentialType
				+ ", educateLevel=" + educateLevel + ", email=" + email
				+ ", engineer=" + engineer + ", housetel=" + housetel
				+ ", isDirector=" + isDirector + ", isOpen=" + isOpen
				+ ", level=" + level + ", loginId=" + loginId + ", logoutDate="
				+ logoutDate + ", mark=" + mark + ", mobile=" + mobile
				+ ", name=" + name + ", nationality=" + nationality
				+ ", nativePlace=" + nativePlace + ", orderNo=" + orderNo
				+ ", parentId=" + parentId + ", party=" + party + ", password="
				+ password + ", peoples=" + peoples + ", wechat=" + wechat
				+ ", registerDate=" + registerDate + ", remark=" + remark
				+ ", weibo=" + weibo + ", sex=" + sex + ", status=" + status
				+ ", syDepartment=" + syDepartment + ", tel=" + tel
				+ ", timeType=" + timeType + ", title=" + title + ", wedLock="
				+ wedLock + "]";
	}


}
