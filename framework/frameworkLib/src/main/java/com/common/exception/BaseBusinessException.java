package com.common.exception;

public class BaseBusinessException extends Exception {
	private String errMsg;

	public BaseBusinessException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;

	}

	public BaseBusinessException(Throwable cause) {
		super(cause);
	}

	public BaseBusinessException(String errMsg, Throwable cause) {
		super(errMsg, cause);
		this.errMsg = errMsg;
	}

	public BaseBusinessException(String errMsg, String message) {

		super(errMsg);
		this.errMsg = errMsg;

	}

	public BaseBusinessException(Throwable cause, String message) {
		super(cause);
		this.errMsg = message;

	}

	public BaseBusinessException(String errMsg, Throwable cause, String message) {
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
