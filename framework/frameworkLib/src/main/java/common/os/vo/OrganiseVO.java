package common.os.vo;

import java.util.Calendar;

import common.value.BaseVO;

public class OrganiseVO extends BaseVO {

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

	private String status;

	private Calendar registerDate;

	private Calendar logoutdate;

	public OrganiseVO() {
	}

	public OrganiseVO(java.lang.Integer id) {
		this.setId(id);
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Calendar getLogoutdate() {
		return logoutdate;
	}

	public void setLogoutdate(Calendar logoutdate) {
		this.logoutdate = logoutdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrgalevel() {
		return orgalevel;
	}

	public void setOrgalevel(Integer orgalevel) {
		this.orgalevel = orgalevel;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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

}
