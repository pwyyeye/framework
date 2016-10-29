package com.xxl.exception;

import common.exception.BaseException;

public class FrameworkException extends BaseException {
	public FrameworkException(String errMsg) {
		super(errMsg);
	}

	public FrameworkException(Throwable cause) {
		super(cause);
	}

	public FrameworkException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}
}
