package com.common.vo;

public class BaseResponseVO {
	public String errorcode;
	public String message;
	public Object data;

	public BaseResponseVO() {
		super();

	}

	public BaseResponseVO(String errorcode, String message, Object data) {
		super();
		this.errorcode = errorcode;
		this.message = message;
		this.data = data;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
