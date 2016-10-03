package com.common.exception;

public class BaseException extends Exception {
	private String errMsg;

	public BaseException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;

	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String errMsg, Throwable cause) {
		super(errMsg, cause);
		this.errMsg = errMsg;
	}

	public BaseException(String errMsg, String message) {

		super(errMsg);
		this.errMsg = errMsg;

	}

	public BaseException(Throwable cause, String message) {
		super(cause);
		this.errMsg = message;

	}

	public BaseException(String errMsg, Throwable cause, String message) {
		super(errMsg, cause);
		this.errMsg = errMsg;

	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
