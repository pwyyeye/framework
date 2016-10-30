package com.xxl.os.bo;

import common.os.vo.DutyVO;
import common.utils.SemAppUtils;

public class SyDuty  extends common.businessObject.BaseBusinessObject
{

    private java.util.Set syUserdutySet;
    private String name;

    
    private Integer parentId;

    private Integer level;

     private String note;

    private String mark;
    
    public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	private Integer status;;

    private Integer isDirector;

    private String dutyNum;
	public java.util.Set getSyUserdutySet() {
		return syUserdutySet;
	}
	public void setSyUserdutySet(java.util.Set syUserdutySet) {
		this.syUserdutySet = syUserdutySet;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getIsDirector() {
		return isDirector;
	}
	public void setIsDirector(Integer isDirector) {
		this.isDirector = isDirector;
	}
	public String getDutyNum() {
		return dutyNum;
	}
	public void setDutyNum(String dutyNum) {
		this.dutyNum = dutyNum;
	}
	public Object toVO() {
		DutyVO vo=new DutyVO(this.getId());
		SemAppUtils.beanCopy(this, vo);
		return vo;
	}
	public SyDuty(){
		
	}
	public SyDuty(Integer id){
		setId(id);
	}
	public SyDuty(Integer id,String name, Integer parentId, Integer level, String note,
			String mark, Integer isDirector, String dutyNum) {
		super();
		setId(id);
		this.name = name;
		this.parentId = parentId;
		this.level = level;
		this.note = note;
		this.mark = mark;
		this.isDirector = isDirector;
		this.dutyNum = dutyNum;
	}

  
}
