package common.os.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import common.value.BaseStringVO;
import common.value.BaseVO;

public class DepartmentVO extends BaseVO {

	private Integer organiseId;
  
	private Integer organiseName;

	public Integer getOrganiseId() {
		return organiseId;
	}


	public void setOrganiseId(Integer organiseId) {
		this.organiseId = organiseId;
	}


	public Integer getOrganiseName() {
		return organiseName;
	}


	public void setOrganiseName(Integer organiseName) {
		this.organiseName = organiseName;
	}


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

	private String status;

	private Calendar registerDate;

	private Calendar logoutDate;

	private String deptType;

	private String deptNum;
	
	private List children;
	
	private boolean leaf;


	public boolean isLeaf() {
		return false;
	}


	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}


	public List getChildren() {
		return children;
	}


	public void setChildren(List children) {
		this.children = children;
	}


	public DepartmentVO() {
	}


	public DepartmentVO(String id) {
		this.setId(id);
	}


	public String getAbbr() {
		return abbr;
	}


	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}


	public Integer getChiefId() {
		return chiefId;
	}


	public void setChiefId(Integer chiefId) {
		this.chiefId = chiefId;
	}


	public Integer getDeptLevel() {
		return deptLevel;
	}


	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}


	public String getDeptNum() {
		return deptNum;
	}


	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}


	public String getDeptType() {
		return deptType;
	}


	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
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


	public Integer getLeaderId() {
		return leaderId;
	}


	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
