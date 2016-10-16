package common.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页专用的LIST，用于存放查询记录总数、该页返回的记录集
 * 
 * @author curry
 * 
 */
public class PageList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000001L;
	private long results;// 查询记录总数
	private List items;// 返回的记录集
	private String errorcode="1";
	private String message="success";
	private String success="1";

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

	public PageList(List items, long results) {
		this.results = results;
		this.items = items;
		this.setErrorcode("1");

	}

	public PageList(List items) {
		this.results = items.size();
		this.items = items;
		this.setErrorcode("1");

	}

	public PageList() {

	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public long getResults() {
		return results;
	}

	public void setResults(long results) {
		this.results = results;
	}

	public PageList(boolean success, Object data, String message) {
		// this.data = data;
		if(data!=null){
			if(data instanceof List){
				this.items=(List)data;
				this.results=items.size();
			}else{
				this.items=new ArrayList();
				this.items.add(data);
				this.results=items.size();
			}
		}
		this.message = message;
		this.success = success ? "true" : "false";
		this.errorcode = success ? "1" : "0";
	}

	public PageList(boolean success, Object data) {
		this(success, data, success ? "success" : "fail");
	}

	public PageList(boolean success, Object data, String message,
			boolean notLogin) {
		this(success, data, message);
		if (notLogin) {
			this.errorcode = "-1";
		}
	}

}
