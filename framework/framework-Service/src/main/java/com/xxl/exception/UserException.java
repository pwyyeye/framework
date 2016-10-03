package com.xxl.exception;

import com.common.exception.BaseException;

public class UserException extends BaseException {
	public UserException(String errMsg) {
		super(errMsg);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	public UserException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}
}
