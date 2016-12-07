package common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

public class ModelSeletorTag  extends BaseSeletorTag{

	public final static String LINE_URL = "../PPWeb/carModelBaseAction.do?action=list";
	public static Log logger = LogFactory.getLog(LineSeletorTag.class);
	public static Log sysLogger = LogFactory.getLog("sys");
	
	public String noEndFlag;

	

	public int doEndTag() throws JspException {
		boolean noEnd=false;
		if(SemWebAppUtils.isNotEmpty(noEndFlag)){
			if("Y".equals(noEndFlag)||"TRUE".equals(noEndFlag)){
				noEnd=true;
			}
		}
		if(SemWebAppUtils.isEmpty(fieldName)) fieldName = "modelField";
		if(SemWebAppUtils.isEmpty(hiddenName)) hiddenName="modelID";
		if(SemWebAppUtils.isEmpty(fieldLabel)) fieldLabel="车型";
		if(SemWebAppUtils.isEmpty(name)) name="modelName";
		JspWriter out = pageContext.getOut();
		SessionUserBean currentUser=(SessionUserBean)findValueOfObject(SemWebAppConstants.USER_KEY);
		String token = (currentUser==null)?"":currentUser.getToken();
		String  initValue=(String)findValueOfObject(initValueObject);
		try {
			out.println("<script>");
			out.println("var " + fieldName
					+ "=new Ext.form.ComboBox({allowBlank : "+isBlankAllow()+", width :"+width+",");
			out
					.println("blankText : '车型',typeAhead:true,selectOnFocus:true, hiddenName : '"+hiddenName+"',fieldLabel:'"+fieldLabel+"',name : '"+name+"',");
			out
					.println("store:new Ext.data.Store({autoLoad :true,reader : new Ext.data.JsonReader({");
			out
					.println("totalProperty: 'results',root: 'items',id:'id'},['id','name']),");
			if(initValue!=null){
			out.println("listeners: {load: function() {");
			out
					.println("modelField.setValue("+initValue+");");
			out.println("}}," );
			}		
			out.println("proxy: new Ext.data.HttpProxy({");
			out.println("url : '" + LINE_URL + "&SEM_LOGIN_TOKEN=" + token+(noEnd?"":("&endcode=3"))
					+ "'");
			out.println("})");
			out.println("}),");
			out
					.println("editable : true,mode: 'local',triggerAction: 'all',displayField:'name',valueField : 'id',emptyText :'全部'});");
			out.println("</script>");
		} catch (IOException ee) {
			logger.error("create tag content fail", ee);
			throw new JspException(ee);
		}
		return (EVAL_PAGE);
	}



	public String getNoEndFlag() {
		return noEndFlag;
	}



	public void setNoEndFlag(String noEndFlag) {
		this.noEndFlag = noEndFlag;
	}

}