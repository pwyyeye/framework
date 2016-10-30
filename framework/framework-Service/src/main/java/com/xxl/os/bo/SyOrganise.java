package com.xxl.os.bo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import common.os.vo.OrganiseVO;
import common.utils.SemAppUtils;


public  class SyOrganise   extends common.businessObject.BaseBusinessObject
   
{
 
    private java.util.Set syDepartmentSet;
	private String code;

	private String name;

	private String abbr;

	private Integer parentId;

	private String tel;

	private String fax;

	private String addr;

	private String postcode;

	private Integer orgalevel;

	private String remark;

	private String region;

	private String regionabbr;

	public java.util.Set getSyDepartmentSet() {
		return syDepartmentSet;
	}
	public void setSyDepartmentSet(java.util.Set syDepartmentSet) {
		this.syDepartmentSet = syDepartmentSet;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public Integer getOrgalevel() {
		return orgalevel;
	}
	public void setOrgalevel(Integer orgalevel) {
		this.orgalevel = orgalevel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegionabbr() {
		return regionabbr;
	}
	public void setRegionabbr(String regionabbr) {
		this.regionabbr = regionabbr;
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
	public Calendar getLogoutdate() {
		return logoutdate;
	}
	public void setLogoutdate(Calendar logoutdate) {
		this.logoutdate = logoutdate;
	}
	private String status;

	private Calendar registerDate;

	private Calendar logoutdate;
	public Object toVO() {
		OrganiseVO vo=new OrganiseVO(this.getId());
		SemAppUtils.beanCopy(this, vo);
		return vo;
	}
	public SyOrganise(){
		
	}
	public SyOrganise(Integer id){
		this.setId(id);
	}
	public SyOrganise(Integer id, String code, String name,
			String abbr, Integer parentId, String tel, String fax, String addr,
			String postcode, Integer orgalevel, String remark, String region,
			String regionabbr, String status, Calendar registerDate,
			Calendar logoutdate) {
		this.setId(id);
		this.code = code;
		this.name = name;
		this.abbr = abbr;
		this.parentId = parentId;
		this.tel = tel;
		this.fax = fax;
		this.addr = addr;
		this.postcode = postcode;
		this.orgalevel = orgalevel;
		this.remark = remark;
		this.region = region;
		this.regionabbr = regionabbr;
		this.status = status;
		this.registerDate = registerDate;
		this.logoutdate = logoutdate;
	}


}
