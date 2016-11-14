package common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.utils.SemAppUtils;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

public class ImageSeletorTag extends TagSupport {

	public static Log logger = LogFactory.getLog(ImageSeletorTag.class);

	protected String fieldName = "content";
	protected String width = "300";
	protected String fieldLabel = "图片";
	protected String action = "/lieyu/lybarimagesAction.do?weblogic_jsession=1234567890&action=login";
	protected String maxCount="0";

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public int doEndTag() throws JspException {
    	JspWriter out = pageContext.getOut();
		SessionUserBean currentUser = (SessionUserBean) findValueOfObject(SemWebAppConstants.USER_KEY);
		String token = (currentUser == null) ? "" : currentUser.getToken();
		try {
		
			out.println("<link rel='stylesheet' type='text/css' href='extcss/css/animated-dataview.css' />"); 
			out.println("<link rel='stylesheet' type='text/css' href='extcss/css/examples.css' />"); 
			out.println("<script>"); 
			out.println("var needUpdateView=true;");
			out.println("var bartypeData = ["+SemAppUtils.getProperty("LIEYU_IMAGE_TYPE")+"];");
			out.println("Ext.Loader.setConfig( {	enabled : true});");
			out.println("Ext.Loader.setPath('Ext.ux', 'extjs/ux');");
			out.println("Ext.require(['Ext.form.*', 'Ext.grid.*', 'Ext.data.*', 'Ext.messageBox.*','Ext.toolbar.Toolbar', 'Ext.PagingToolbar','Ext.window.Window', 'Ext.selection.CheckboxModel','Ext.ux.form.MultiSelect','Ext.ux.PreviewPlugin','Ext.ux.form.ItemSelector','Ext.ux.exporter.Exporter']);");
			out.println("Ext.require(['Ext.util.*','Ext.view.View','Ext.ux.DataView.Animated','Ext.XTemplate']);");
			out.println("Ext.define('imageDatas', {");
			out.println("	extend : 'Ext.data.Model',");
			out.println("	idProperty : 'id',");
			out.println("	fields : [ 'id', 'imageid','lyimagename', 'introduction', 'barid', 'barname', 'linkurl',");
			out.println("			'_showurl', 'type', 'typestr', 'createdate', 'modifydate' ]");
			out.println("});");
			out.println("var imageStore = Ext.create('Ext.data.Store', {");
			out.println("	autoLoad : false,");
			out.println("	model : 'imageDatas',");
			out.println("	proxy : {");
			out.println("		type : 'ajax',method : 'POST',");
			out.println("		url : '"+action+"',");
			out.println("		reader : {");
			out.println("			type : 'json',");
			out.println("			totalProperty : 'results',");
			out.println("			root : 'items'");
			out.println("		}");
			out.println("	}");
			out.println("});");
			out.println("var selectedStore = Ext.create('Ext.data.Store', {");
			out.println("autoLoad : false,");
			out.println("model : 'imageDatas',");
			out.println("    proxy : {");
			out.println("		type : 'ajax',method : 'POST',");
			out.println("		url : '"+action+"',");
			out.println("		reader : {");
			out.println("			type : 'json',");
			out.println("			totalProperty : 'results',");
			out.println("			root : 'items'");
			out.println("		}");
			out.println("    }});");
			out.println("var typeCombo=Ext.create('Ext.form.ComboBox',{width : 200,labelWidth:80,allowBlank : true,fieldLabel:'图片类型',");
			out.println("	  hiddenName:'type',name:'type',store:new Ext.data.JsonStore({data:bartypeData,fields:['id','name']}),");
			out.println("	  editable : false,mode: 'local', triggerAction: 'all',displayField:'name',valueField : 'id',");
			out.println("	  emptyText :'请选择',");
			out.println("	  listeners: {");
			out.println("            change: {fn    : filterData}");
			out.println("  }});");
			out.println("var nameField=Ext.create('Ext.form.TextField',{width : 100,allowBlank : true,name:'iname',emptyText:'按图片名搜索'");
			out.println(",listeners: {");
			out.println("            change: {fn    : filterName}");
			out.println("  }});");
			out.println("var dataview= Ext.create('Ext.view.View', {");
			out.println("        deferInitialRefresh: false,");
			out.println("        store: imageStore,");       
			out.println("       tpl  : Ext.create('Ext.XTemplate',");
			out.println("            '<tpl for=\".\">',");
			out.println("                '<div class=phone  id={id}>',");
			out.println("                   '<img width=64 height=64 src={linkurl}?imageView2/1/w/64/h/64 />',");
			out.println("                    '<strong>{lyimagename}</strong>',");
			out.println("                    '<span>{createdate} </span>',");
			out.println("                '</div>',");
			out.println("           '</tpl>'");
			out.println("       ),");
			out.println("         plugins : [");
			out.println("            Ext.create('Ext.ux.DataView.Animated', {");
			out.println("   duration  : 550,");
			out.println("   idProperty: 'id'");
			out.println("})");
			out.println("        ],");
			out.println("        id: 'phones',");
			out.println("        itemSelector: 'div.phone',");
			out.println("        overItemCls : 'phone-hover',");
			out.println("       multiSelect : true,");
			out.println("       autoScroll  : true");
			out.println("    });");
			out.println("var selectedDataview= Ext.create('Ext.view.View', {");
			out.println("       deferInitialRefresh: true,");
			out.println("       store: selectedStore,");
			out.println("       tpl  : Ext.create('Ext.XTemplate',");
			out.println("            '<tpl for=\".\">',");
			out.println("                '<div class=phone  id={id}>',");
			out.println("                   '<img width=64 height=64 title=双击删除图片 src={linkurl}?imageView2/1/w/64/h/64 />',");
			out.println("                    '</div>',");
			out.println("           '</tpl>'");
			out.println("       ),");     
			out.println("        id: 'seleltItems',");
			out.println("        itemSelector: 'div.phone',");
			out.println("        overItemCls : 'phone-hover',");
			out.println("        autoScroll  : true");
			out.println("    });");
			out.println("selectedDataview.on('itemdblclick',function(selectedDataview){");
			out.println("	needUpdateView=false;");
			out.println("	var value=imageSelector.getValue();");
			out.println("	var first=true;");
			out.println("	var rec=selectedDataview.getSelectionModel().selected.items[0];");
			out.println("    var ids=value.split(',');");
			out.println("    var newValue='';");
			out.println("	    for(var i=0;i<ids.length;i++){    ");    	 
			out.println("	      	if(ids[i]!=rec.get('imageid')) {");
			out.println("	      		if(!first) newValue+=',';");
			out.println("	       		newValue+=ids[i];");
			out.println("	       		first=false;");
			out.println("	       	}");
			out.println("	    }");
			out.println("	    selectedStore.remove(rec);");
			out.println("	    selectedStore.commitChanges();");
			out.println("	    imageSelector.setValue(newValue);");
			out.println("	}); ");   
			out.println("dataview.on('itemdblclick', function(dataview) {");
			out.println("	selected();});");
			out.println("  function filterData(typeCombo) {");
			out.println("        var value  = typeCombo.getValue();");
			out.println("      imageStore.suspendEvents();");
			out.println("        imageStore.clearFilter();");
			out.println("        imageStore.resumeEvents();");
			out.println("        imageStore.filter([{");
			out.println("            fn: function(record) {");
			out.println("                return record.get('type')== value ;");
			out.println("            }");
			out.println("        }]);");
			out.println("        imageStore.sort('createdate', 'ASC');");
			out.println("   }");
			out.println("  function filterName(nameField) {");
			out.println("        var value  = nameField.getValue();");
			out.println("      imageStore.suspendEvents();");
			out.println("        imageStore.clearFilter();");
			out.println("        imageStore.resumeEvents();");
			out.println("        imageStore.filter([{");
			out.println("            fn: function(record) {");
			out.println("                return record.get('lyimagename').indexOf(value)!=-1;");
			out.println("            }");
			out.println("        }]);");
			out.println("        imageStore.sort('createdate', 'ASC');");
			out.println("   }");
			//out.println("  filterData(typeCombo);");
			out.println(" var firstLoad=true;");
			out.println(" function selectImage(){");
			out.println("    imageWindow.show();");
			out.println("     if(firstLoad){");
			out.println("     	imageStore.load();");
			out.println("     	firstLoad=false;");
			out.println("     }");
			    	
			   
			out.println("    }");
			//out.println("   		   function selectImage(){");
			//out.println("		   imageWindow.show();");
			//out.println("		   }");
			out.println("		   var imageWindow=");
			out.println("				 Ext.create(");
			out.println("						'Ext.window.Window',");
			out.println("						{");
			out.println("							layout : 'border',");
			out.println("							id : 'imageWindow',");
			out.println("							items : dataview,");
			out.println("							width : 500,");
			out.println("							height : 400,");
			out.println("							closable : false,");
			out.println("							modal : 'true',");
			out.println("							buttonAlign : 'right',");
			out.println("							autoScroll : false,");
			out.println("							tbar : ["); 
			out.println("							typeCombo,nameField,'->',{");
			out.println("								text : '确定',");
			out.println("								handler : function() {");
			out.println("								   selected();");            
			out.println("								}");
			out.println("							} ]");
			out.println("						}); ");
			out.println("function selected(){");
			out.println("needUpdateView=false;");
			out.println("var value=imageSelector.getValue();");
			out.println(" var first=true;");
			out.println("if(value!=''){");
			out.println("	first=false;");
			out.println("}");		   
			out.println("var recs=dataview.getSelectionModel().selected.items;");
			out.println("Ext.each(recs,function(rec){");
			out.println("   if(!first) value+=',';");
			out.println("   selectedStore.add(rec);");
			out.println("   value+=rec.get('imageid');");
			out.println("   first=false;");
			out.println("})");
			out.println("selectedStore.commitChanges();");
			out.println("imageSelector.setValue(value);");
			
			out.println("imageWindow.hide();");
			out.println("}");
			out.println("		  var imageSelector=Ext.create('Ext.form.Hidden',{width : 300,name : '"+this.fieldName+"',readOnly:true,id:'content'});");
			out.println("imageSelector.on('change', function(imageSelector,newValue,oldValue) {");
			out.println("	  if(needUpdateView){");
			out.println("          //var value=imageSelector.getValue();");
			out.println("          if(newValue!=null && newValue !='' && newValue!=0){");
			out.println("    	    selectedStore.removeAll();");
			out.println("            selectedStore.load({params:{action:'list',imageIds:newValue}});");
			out.println("         }");
			out.println("       }else{");
			out.println("    	   needUpdateView=true;");
			out.println("       }");
			out.println("  });");
			
			
			out.println("		  var imageFiled=Ext.create('Ext.form.FieldSet',{");
			out.println("			       bodyPadding:0,title:'"+this.fieldLabel+"',");
			out.println("collapsible: true,");
			out.println("			      width:'"+this.width+"',");
			out.println("				   layout: {");
			out.println("                    type : 'table',");
			out.println("                    columns : 3");
			out.println("                  },");                 
			out.println("                   layoutConfig:{columns: 3},");   
			out.println("                  items: [");
			out.println("                       imageSelector, selectedDataview,{xtype:'button', text:'+',handler:selectImage,cls:'btn'}");               
			out.println("                  ]");                   
			out.println("               })");
			out.println("	</script>");
		} catch (Exception ee) {
			logger.error("create tag content fail", ee);
			throw new JspException(ee);
		}
		return (EVAL_PAGE);
	}

	public void release() {
		fieldName = null;
		action = null;
		width = "80";
		fieldLabel = null;
		super.release();
	}

	protected Object findValueOfObject(String name) throws JspException {
		if (name == null)
			return null;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
