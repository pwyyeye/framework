package com.xxl.exception;

import common.exception.BaseBusinessException;

public class FrameworkBusinessException extends BaseBusinessException {
	public FrameworkBusinessException(String errMsg) {
		super(errMsg);
	}

	public FrameworkBusinessException(Throwable cause) {
		super(cause);
	}

	public FrameworkBusinessException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}
}
