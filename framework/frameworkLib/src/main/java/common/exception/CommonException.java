package common.exception;

public class CommonException extends RuntimeException {

	public CommonException(String errMsg) {
		super(errMsg);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

	public CommonException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}

}
