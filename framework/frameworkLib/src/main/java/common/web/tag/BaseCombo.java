package common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

public abstract class BaseCombo extends BaseSeletorTag {
	public static Log logger = LogFactory.getLog(BaseSeletorTag.class);
	public static Log sysLogger = LogFactory.getLog("sys");

	public int doEndTag() throws JspException {
		if (SemWebAppUtils.isEmpty(fieldName))
			fieldName = this.getDefaultFieldName();
		if (SemWebAppUtils.isEmpty(hiddenName))
			hiddenName = this.getHiddenName();
		if (SemWebAppUtils.isEmpty(name))
			name = this.getDefaultName();
		if (SemWebAppUtils.isEmpty(fieldLabel))
			fieldLabel = this.getDefaultFieldLabel();
		JspWriter out = pageContext.getOut();
		SessionUserBean currentUser = (SessionUserBean) findValueOfObject(SemWebAppConstants.USER_KEY);
		String token = (currentUser == null) ? "" : currentUser.getToken();
		String initValue = (String) findValueOfObject(initValueObject);
		try {
			out.println("<script>");
			out.println("Ext.define('" +getDefaultFieldName()+
					"idNameModel', {extend : 'Ext.data.Model',idProperty : 'id',fields : [ {" +
					"sortable : true,name : 'id',type : 'string'}, {name : '" +getDisplayName()+
					"',type : 'string'} ]});");
			out.println("var "+this.getDefaultFieldName()+"comboStore =Ext.create('Ext.data.Store',{");
			out.print("autoLoad :false,");
			out.print(" model:'"+getDefaultFieldName()+"idNameModel',");
			if (initValue != null) {
				out.println("listeners: {load: function() {");
				out.println(this.getDefaultFieldName() + ".setValue("
						+ initValue + ");");
				out.println("}},");
			}
			out.print("proxy: {type : 'ajax',method : 'POST',");
			out.println("url : '" + this.getUrl() + "&SEM_LOGIN_TOKEN=" + token
					+ "',");
			out
					.println("reader : {type : 'json',totalProperty : 'results',root : 'items'");
			out.println("}");
			out.println("}});");
			out.println("var " + fieldName
					+ "=Ext.create('Ext.ux.idNameComBox',{allowBlank : "
					+ isBlankAllow() + ", width :" + width + ",");
			out.println("typeAhead:true,selectOnFocus:true,displayField:'"+getDisplayName()+"',hiddenName : '"
					+ getDefaultHiddenName() + "',fieldLabel:'" + fieldLabel + "',name : '"
					+ name + "',");
			out.println("store:"+this.getDefaultFieldName()+"comboStore,");
			out
					.println("editable : true,mode: 'local',triggerAction: 'all',valueField : 'id'});");
			out.println("</script>");
		} catch (IOException ee) {
			logger.error("create tag content fail", ee);
			throw new JspException(ee);
		}
		return (EVAL_PAGE);
	}

	public abstract String getUrl();

	public abstract String getDefaultFieldName();

	public abstract String getDefaultHiddenName();

	public abstract String getDefaultName();

	public abstract String getDefaultFieldLabel();
	
	public String getDisplayName(){
		return "name";//default
	}

}
