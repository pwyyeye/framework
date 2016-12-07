package common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

public class MaterialSeletorTag  extends BaseSeletorTag{

	public final static String LINE_URL = "../commonWeb/materialAction.do?action=list";
	public static Log logger = LogFactory.getLog(LineSeletorTag.class);
	public static Log sysLogger = LogFactory.getLog("sys");
	private int count=20;//row count what will display,default 20.


	

	public int getCount() {
		return count;
	}




	public void setCount(int count) {
		this.count = count;
	}




	public int doEndTag() throws JspException {
		boolean noEnd=false;
		if(SemWebAppUtils.isEmpty(fieldName)) fieldName = "materialName";
		if(SemWebAppUtils.isEmpty(hiddenName)) hiddenName="materialNo";
		if(SemWebAppUtils.isEmpty(fieldLabel)) fieldLabel="件     号";
		if(SemWebAppUtils.isEmpty(name)) name="materielNo";
		JspWriter out = pageContext.getOut();
		SessionUserBean currentUser=(SessionUserBean)findValueOfObject(SemWebAppConstants.USER_KEY);
		String token = (currentUser==null)?"":currentUser.getToken();
		String  initValue=(String)findValueOfObject(initValueObject);
		try {
			out.println("<script>");
			out.println("var " + fieldName
					+ "=new Ext.form.ComboBox({allowBlank : "+isBlankAllow()+", width :"+width+",");
			out
					.println("blankText : '件号',typeAhead:true,enableKeyEvents:true ,selectOnFocus:true, hiddenName : '"+hiddenName+"',fieldLabel:'"+fieldLabel+"',name : '"+name+"',");
			out
					.println("store:new Ext.data.Store({autoLoad :true,reader : new Ext.data.JsonReader({");
			out
					.println("totalProperty: 'results',root: 'items',id:'id'},['id','materielNo','name']),");
			out.println("listeners: {" );
			if(initValue!=null){
				out.println("load: function() {");
			out
					.println(fieldName+".setValue("+initValue+");");
			out.println("},");
			}
			out.println("'beforequery' : function(e){var combo = e.combo;var keyText=e.query;alert(keyText);"+fieldName+".store.reload({params:{materialNo:keyText}});combo.expand();}}," );	
			out.println("proxy: new Ext.data.HttpProxy({");
			out.println("url : '" + LINE_URL + "&start=0&limit="+count+"&SEM_LOGIN_TOKEN=" + token
					+ "'");
			out.println("})");
			out.println("}),");
			out
					.println("editable : true,mode: 'local',triggerAction: 'all',displayField:'name',valueField : 'materielNo',emptyText :'全部'});");
			out.println("</script>");
		} catch (IOException ee) {
			logger.error("create tag content fail", ee);
			throw new JspException(ee);
		}
		return (EVAL_PAGE);
	}



	

}