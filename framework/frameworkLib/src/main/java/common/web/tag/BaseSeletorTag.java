package common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseSeletorTag  extends TagSupport{

	public static Log logger = LogFactory.getLog(BaseSeletorTag.class);

	public static Log sysLogger = LogFactory.getLog("sys");
	protected String fieldName = null;
	protected String hiddenName=null;
	protected String name=null;
	protected String initValueObject = null;
	protected String width="80";
	protected String allowBlank="N";
	protected String fieldLabel=null;
	protected String otherOption=null;
	
	public String getOtherOption() {
		return otherOption;
	}
	public void setOtherOption(String otherOption) {
		this.otherOption = otherOption;
	}
	public String getAllowBlank() {
		
		return allowBlank;
	}
	public boolean isBlankAllow(){
		return "Y".equalsIgnoreCase(allowBlank)||"true".equalsIgnoreCase(allowBlank);
	}


	public void setAllowBlank(String allowBlank) {
		this.allowBlank = allowBlank;
	}


	public void release() {
		fieldName = null;
		hiddenName=null;
		name=null;
		initValueObject = null;
		width="80";
		allowBlank="N";
		super.release();
	}
	
	
	protected Object findValueOfObject(String name) throws JspException {
		if(name==null) return null;
		Object value = null;
		if (value == null)
			value = pageContext.findAttribute(name);
		if (value == null)
			value = pageContext.getAttribute(name, PageContext.PAGE_SCOPE);
		if (value == null)
			value = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
		if (value == null)
			value = pageContext.getAttribute(name, PageContext.SESSION_SCOPE);
		if (value == null)
			value = pageContext.getAttribute(name,
					PageContext.APPLICATION_SCOPE);
		return value;

	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public String getInitValueObject() {
		return initValueObject;
	}

	public void setInitValueObject(String initValueObject) {
		this.initValueObject = initValueObject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getWidth() {
		return width;
	}


	public void setWidth(String width) {
		this.width = width;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	
}
