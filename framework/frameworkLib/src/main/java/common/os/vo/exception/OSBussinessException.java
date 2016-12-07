package common.os.vo.exception;

import common.exception.BaseBusinessException;
import common.exception.BaseException;

public class OSBussinessException extends BaseBusinessException {

//	 Constructors
	  public OSBussinessException(String errMsg) { super(errMsg); }
	  public OSBussinessException(Throwable cause) { super(cause); }
	  public OSBussinessException(String errMsg, Throwable cause) {
	    super(errMsg, cause);
	  }

}
