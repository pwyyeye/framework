package com.xxl.baseService.vo;

import java.util.Calendar;

import common.value.BaseVO;

public class PersistentLoginsVo extends BaseVO {
	
	private static final long serialVersionUID = -3025773599069070742L;

	private String series;

    private String userName;

    private String token;

    private Calendar last_used;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getLast_used() {
		return last_used;
	}

	public void setLast_used(Calendar last_used) {
		this.last_used = last_used;
	}
    
}
