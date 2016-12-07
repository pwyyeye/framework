package common.value;

import java.util.List;

import common.utils.SemAppUtils;

public class JsonResult {
	private Object data;
	private String errorcode;
	private String message;
	private String success;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
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

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public JsonResult(boolean success, Object data, String message) {
		if (data instanceof String) {
			this.data = new SimpleResult((String) data);
		} else {
			this.data = data;
		}
		this.message = message;
		this.success = success ? "true" : "false";
		this.errorcode = success ? "1" : "0";
	}

	public JsonResult(boolean success, Object data) {
		this(success, data, success ? "success" : "fail");
	}

	public JsonResult(boolean success, Object data, String message,
			boolean notLogin) {
		this(success, data, message);
		if (notLogin) {
			this.errorcode = "-1";
		}
	}

	public String toString(List list, boolean filter) {
		if (list != null) {
			// list = containList;
			return SemAppUtils.getJsonFromBean(this, list, filter);
		} else {
			return SemAppUtils.getJsonFromBean(this);
		}
	}

	public String toString() {
		return SemAppUtils.getJsonFromBean(this);
	}

}
