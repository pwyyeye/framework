package com.common.businessObject;

import java.io.Serializable;
import com.common.utils.SemAppUtils;

public abstract class BaseAbstractBo implements Serializable {
	private String displayLabel;
	private String description;

	public abstract Object toVO();

	public String toString() {
		return SemAppUtils.object2String(this);
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
