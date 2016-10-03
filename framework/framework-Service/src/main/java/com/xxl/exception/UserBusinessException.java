package com.xxl.exception;

import com.common.exception.BaseBusinessException;

public class UserBusinessException extends BaseBusinessException {
	public UserBusinessException(String errMsg) {
		super(errMsg);
	}

	public UserBusinessException(Throwable cause) {
		super(cause);
	}

	public UserBusinessException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}
}
