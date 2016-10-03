package com.common.businessObject;

/**
 * An abstract super class that many business objects will extend.
 */
public abstract class BaseBusinessStringObject extends BaseAbstractBo {
	private static final long serialVersionUID = 1L;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(java.io.Serializable id) {
		this.id = "" + id;
	}

}
