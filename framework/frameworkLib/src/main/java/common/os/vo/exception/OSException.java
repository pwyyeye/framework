package common.os.vo.exception;

import common.exception.BaseException;

public class OSException extends BaseException {

//	 Constructors
	  public OSException(String errMsg) { super(errMsg); }
	  public OSException(Throwable cause) { super(cause); }
	  public OSException(String errMsg, Throwable cause) {
	    super(errMsg, cause);
	  }

}
