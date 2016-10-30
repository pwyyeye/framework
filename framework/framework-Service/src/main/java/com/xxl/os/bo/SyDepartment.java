package com.xxl.os.bo;

import java.util.Calendar;
import java.util.Set;

import common.os.vo.DepartmentVO;
import common.utils.SemAppUtils;


public  class SyDepartment     extends common.businessObject.BaseBusinessObject
{
   
    private SyOrganise syOrganise;

    private java.util.Set syUserdutySet;

   private java.util.Set syUsersSet;


	private Integer orderNo;

	private String name;

	private String abbr;

	private String groupName;

	private String kind;

	private String tel;

	private String fax;

	private Integer leaderId;

	private Integer chiefId;

	private String remark;

	private Integer parentId;

	private Integer deptLevel;

	private String mark;

	private Integer status;

	private Calendar registerDate;

	private Calendar logoutDate;

	private String deptType;

	private String deptNum;

	public SyOrganise getSyOrganise() {
		return syOrganise;
	}

	public void setSyOrganise(SyOrganise syOrganise) {
		this.syOrganise = syOrganise;
	}

	public java.util.Set getSyUserdutySet() {
		return syUserdutySet;
	}

	public void setSyUserdutySet(java.util.Set syUserdutySet) {
		this.syUserdutySet = syUserdutySet;
	}

	public java.util.Set getSyUsersSet() {
		return syUsersSet;
	}

	public void setSyUsersSet(java.util.Set syUsersSet) {
		this.syUsersSet = syUsersSet;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getChiefId() {
		return chiefId;
	}

	public void setChiefId(Integer chiefId) {
		this.chiefId = chiefId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	public Object toVO() {
		DepartmentVO vo=new DepartmentVO();
		vo.setId(this.getId());
		SemAppUtils.beanCopy(this, vo);
		return vo;
	}

	public SyDepartment(Integer id){
		this.setId(id);
	}
	
	public SyDepartment(){
		
	}
	
	public SyDepartment(String id,SyOrganise syOrganise, Set syUserdutySet,
			Set syUsersSet, Integer orderNo, String name, String abbr,
			String groupName, String kind, String tel, String fax,
			Integer leaderId, Integer chiefId, String remark, Integer parentId,
			Integer deptLevel, String mark, Integer status,
			Calendar registerDate, Calendar logoutDate, String deptType,
			String deptNum) {
		this.setId(id);
		this.syOrganise = syOrganise;
		this.syUserdutySet = syUserdutySet;
		this.syUsersSet = syUsersSet;
		this.orderNo = orderNo;
		this.name = name;
		this.abbr = abbr;
		this.groupName = groupName;
		this.kind = kind;
		this.tel = tel;
		this.fax = fax;
		this.leaderId = leaderId;
		this.chiefId = chiefId;
		this.remark = remark;
		this.parentId = parentId;
		this.deptLevel = deptLevel;
		this.mark = mark;
		this.status = status;
		this.registerDate = registerDate;
		this.logoutDate = logoutDate;
		this.deptType = deptType;
		this.deptNum = deptNum;
	}


}
