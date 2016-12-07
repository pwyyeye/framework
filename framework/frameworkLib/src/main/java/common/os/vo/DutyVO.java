package common.os.vo;

import common.value.BaseVO;


public class DutyVO    extends BaseVO
{
 
	public DutyVO(Integer id){
		this.setId(id);
	}
	public DutyVO(){
		
	}
    private String name;
    private Integer status;
   
    public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	private String parentId;

    private Integer level;

     private String note;

    private String mark;

    private Integer isDirector;

    private String dutyNum;

	public String getDutyNum() {
		return dutyNum;
	}

	public void setDutyNum(String dutyNum) {
		this.dutyNum = dutyNum;
	}

	public Integer getIsDirector() {
		return isDirector;
	}

	public void setIsDirector(Integer isDirector) {
		this.isDirector = isDirector;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

  
 
}
