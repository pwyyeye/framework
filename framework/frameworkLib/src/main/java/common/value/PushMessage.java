package common.value;

import java.io.Serializable;

public class PushMessage  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer type;
	private Integer bzId;
	private String users;//split by ","
    private String title; 
    private String text;
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getTitle() {
		return title;
	}
	public PushMessage(Integer type,String users, String title, String text,Integer bzId) {
		super();
		this.type=type;
		this.users = users;
		this.title = title;
		this.text = text;
		this.bzId=bzId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getBzId() {
		return bzId;
	}
	public void setBzId(Integer bzId) {
		this.bzId = bzId;
	}
    
    
    
}
