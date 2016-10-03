package com.common.value;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import com.common.utils.SemAppUtils;

public class BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	

	public BaseVO() {
	}

	public BaseVO(Integer id) {
		this.id = id;
	}

	public BaseVO(int id) {
		this.id = new Integer(id);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setId(int id) {
		this.id = new Integer(id);
	}

	public void setId(String id) {
		this.id = str2Integer(id);
	}

	protected String getDisplayDate(java.util.Calendar pCalendar) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		if (pCalendar != null)
			return format.format(pCalendar.getTime());
		else
			return "";
	}

	protected String toStr(Object obj) {
		if (obj != null)
			return obj.toString();
		else
			return null;
	}

	protected Integer str2Integer(String str) {
		Integer tempInt = null;
		try {
			if (str != null)
				tempInt = Integer.valueOf(str);
		} catch (NumberFormatException e) {
		}
		return tempInt;
	}

	public String toString() {
	  return SemAppUtils.object2String(this);
	}
}
