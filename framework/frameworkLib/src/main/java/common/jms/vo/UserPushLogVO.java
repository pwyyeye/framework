package common.jms.vo;

import java.util.Calendar;

import common.value.BaseVO;

public class UserPushLogVO extends BaseVO{
	private Integer userId;
	private Integer type;
	private Integer result;
	private String title;
	private String content;
	private Integer createUser;
	private Calendar createDate;
	private String remark;
	private String module;
	private Integer read;
	private Calendar readDate;
	private String typeName;
	private Integer bzId;

	private Integer moduleID;
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getModuleID() {
		return moduleID;
	}
	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	public Calendar getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}
	public UserPushLogVO(Integer id,Integer userId, Integer type, Integer result,
			String title, String content, Integer createUser,
			Calendar createDate,String remark,Integer bzId) {
		super(id);
		this.userId = userId;
		this.type = type;
		this.result = result;
		this.title = title;
		this.content = content;
		this.createUser = createUser;
		this.createDate = createDate;
		this.remark=remark;
		this.bzId=bzId;
	}
	public UserPushLogVO() {
		super();
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public Calendar getReadDate() {
		return readDate;
	}
	public void setReadDate(Calendar readDate) {
		this.readDate = readDate;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getBzId() {
		return bzId;
	}
	public void setBzId(Integer bzId) {
		this.bzId = bzId;
	}
	
}
