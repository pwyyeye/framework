package common.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

public class BasePanel extends TagSupport {
	public Log logger = LogFactory.getLog(this.getClass());

	protected String recordLabel = "";

	protected String action = null; // 后台处理的action

	protected String gridBody = null;// 定义表格中要显示的表头及内容：eg：id:,name:角色名称,module:模块名,moduleID,empID:姓名

	protected String formWidth = "400";// 默认弹出框宽度为400；

	protected String formHeight = "300";// 默认弹出框高度为300;

	protected String searchFormWidth = null;// 默认弹出框宽度为400；

	protected String searchFormHeight = null;// 默认弹出框高度为300;

	protected String formString = null;

	protected String searchFormString = null;

	protected String searchFieldsString = null;//

	protected String userFiledsString = null;// OA用户选择框的FORM NAME

	protected String rightString = null;// 按钮权限

	protected String gridTitle = null;

	protected String needRightCheck = null;

	protected String customButtonImpl = null;

	private List itemList = null;

	private String complexHead = null;

	private List headList = null;

	private List itemMapList = null;

	protected String listRight = null;

	protected String addRight = null;

	protected String deleteAllRight = null;

	protected String deleteRight = null;

	protected String updateRight = null;

	protected String exportRight = null;

	protected String importRight = null;

	protected String customRight = null;

	protected String customButton = null;// 自定义的按钮 例 "{text :

	// '导入Excel',iconCls:'exportExcel',handler:importExcel}"
	protected String columnAutoFillStr = null;

	protected String pageSize = null;

	private boolean columnAutoFill = true;

	protected String customGridHead = null;

	protected String customModifyImpl = null;

	protected String customAddImpl = null;

	protected String needUpdate = null;

	protected String needUpdateAfterAdd = null;

	protected String addForInitial = null;
	
	protected String searchForInitial = null;
	
	protected String extContent = null;//自定义一些javascript的类或方法
	
	protected String submitContent = null; //定义提交前对数据的操作
	
	protected String scriptBeforeDelete=null;

	protected String renameAdd = null;

	protected String renameUpdate = null;

	protected String renameDelete = null;

	protected String fileUpload = null;

	protected String showAllRecord = "Y";// 默认为Y
	
	protected String continuous="N";//连续新增的标志位，

	public String getSubmitContent() {
		return submitContent;
	}

	public void setSubmitContent(String submitContent) {
		this.submitContent = submitContent;
	}

	public String getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getRenameAdd() {
		return renameAdd;
	}

	public void setRenameAdd(String renameAdd) {
		this.renameAdd = renameAdd;
	}

	public String getRenameDelete() {
		return renameDelete;
	}

	public void setRenameDelete(String renameDelete) {
		this.renameDelete = renameDelete;
	}

	public String getRenameUpdate() {
		return renameUpdate;
	}

	public void setRenameUpdate(String renameUpdate) {
		this.renameUpdate = renameUpdate;
	}

	public String getAddForInitial() {
		return addForInitial;
	}

	public String getSearchForInitial() {
		return searchForInitial;
	}

	public void setSearchForInitial(String searchForInitial) {
		this.searchForInitial = searchForInitial;
	}

	public void setAddForInitial(String addForInitial) {
		this.addForInitial = addForInitial;
	}

	public String getNeedUpdateAfterAdd() {
		return needUpdateAfterAdd;
	}

	public void setNeedUpdateAfterAdd(String needUpdateAfterAdd) {
		this.needUpdateAfterAdd = needUpdateAfterAdd;
	}

	public String getCustomAddImpl() {
		return customAddImpl;
	}

	public void setCustomAddImpl(String customAddImpl) {
		this.customAddImpl = customAddImpl;
	}

	public String getCustomModifyImpl() {
		return customModifyImpl;
	}

	public void setCustomModifyImpl(String customModifyImpl) {
		this.customModifyImpl = customModifyImpl;
	}

	public int doEndTag() throws JspException {
		convertGridBody();
		if (isNeedCheck())
			convertRightString();
		JspWriter out = pageContext.getOut();
		String[] searchFields = null;
		try {
			out.println("<script>");
			boolean otherSearchForm = SemWebAppUtils
					.isNotEmpty(searchFormString);
			if (SemWebAppUtils.isNotEmpty(searchFieldsString)) {
				searchFields = searchFieldsString.split(",");
			} else {
				if (otherSearchForm) {// 单独的searchForm窗口
					searchFields = getSearchFields(searchFormString);
				} else {
					searchFields = getSearchFields(formString);
				}
			}
			String searchParameters = null;

			StringBuffer pageParameterSB = new StringBuffer();
			StringBuffer currentParameterSB = new StringBuffer();
			Enumeration enum1 = pageContext
					.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
			while (enum1.hasMoreElements()) {
				String attributeName = (String) enum1.nextElement();
				try {
					String value = (String) pageContext.getAttribute(
							attributeName, PageContext.REQUEST_SCOPE);
					pageParameterSB.append(",'" + attributeName + "':'" + value
							+ "'");
					currentParameterSB
							.append("&" + attributeName + "=" + value);
				} catch (Exception ee) {
				}
			}

			if (searchFields != null) {
				StringBuffer parameterSB = new StringBuffer();
				parameterSB.append(pageParameterSB);

				for (int i = 0; i < searchFields.length; i++) {
					out.println("var search" + searchFields[i] + "='';");
					parameterSB.append(",'" + searchFields[i] + "':search"
							+ searchFields[i]);
					currentParameterSB.append((i > 0 ? "+'" : "") + "&"
							+ searchFields[i] + "='+search" + searchFields[i]);
				}
				searchParameters = new String(parameterSB);
			}
			logger.debug("search parameters=" + searchParameters);
			int recond = 20;
			try {
				recond = Integer.parseInt(pageSize);
			} catch (Exception ee) {
				recond = 20;
			}
			out.println("var recond=" + recond + ";");
			out.println("Ext.Loader.setConfig( {	enabled : true});");
			out.println("Ext.Loader.setPath('Ext.ux', 'extjs/ux');");
			out.println("Ext.require(['Ext.form.*', 'Ext.grid.*', 'Ext.data.*', 'Ext.messageBox.*','Ext.toolbar.Toolbar', 'Ext.PagingToolbar','Ext.window.Window', 'Ext.selection.CheckboxModel','Ext.ux.form.MultiSelect','Ext.ux.form.ItemSelector']);");
			out.println("Ext.onReady(function(){   ");
			if(this.extContent!=null) out.println(extContent);
			if (otherSearchForm) {// 单独的searchForm窗口
				out
						.println("var searForm=new Ext.create('Ext.form.Panel', {labelSeparator : '：',frame:true,border:false,");
				out.println("items :[" + searchFormString + "],");
				out
						.println("buttons:[{text : '关闭',handler : function(){win2.hide();}},{text : '提交',handler : submitSearch}]});");
			}
			out.println("Ext.define('datas', {extend : 'Ext.data.Model',idProperty : 'id',fields : ['id'");
			Iterator iter = itemList.iterator();
			while (iter.hasNext()) {
				out.println(",'" + iter.next() + "'");
			}
			iter = itemMapList.iterator();
			while (iter.hasNext()) {
				out.println("," + iter.next());
			}
			out.println("]});");
			//define store
			out.println("var store = Ext.create('Ext.data.Store',{");
			out.println("autoLoad :false,model: 'datas',");
			out.println("proxy :{type:'ajax',method : 'POST',");
			out.println("url : '" + this.convertHref(action) + "action=list',reader:{type:'json',totalProperty: 'results',root: 'items'");
			out.println("}}});");
			//define toolbar
			out.println("var toolbar = Ext.create('Ext.toolbar.Toolbar', {items:[");
			boolean first = true;
			if (!isNeedCheck() || this.hasAddRight()) {
				first = false;

				if (SemWebAppUtils.isEmpty(renameAdd)) {
					renameAdd = "添加" + recordLabel;
				}
				out.println("{text : '" + renameAdd
						+ "',iconCls:'add',handler:showAdd}");
			}
			if (!isNeedCheck() || this.hasUpdateRight()) {
				if (!first)
					out.println(",");
				if (SemWebAppUtils.isEmpty(renameUpdate)) {
					renameUpdate = "修改" + recordLabel;
				}

				out.println("{text : '" + renameUpdate
						+ "',iconCls:'option',handler:showModify}");
				first = false;
			}
			if (!isNeedCheck() || this.hasDeleteRight()) {
				if (!first)
					out.println(",");
				if (SemWebAppUtils.isEmpty(renameDelete)) {
					renameDelete = "删除" + recordLabel;
				}

				out.println("{text : '" + renameDelete
						+ "',iconCls:'remove',handler:showDelete}");
				first = false;
			}

			if (!isNeedCheck() || this.hasListRight()) {
				if (!first)
					out.println(",");
				out
						.println("{text : '查找',   iconCls:'search',handler:showSearch},");
				out
						.println("{text : '显示全部',iconCls:'showAll',handler:showAll},");
				out.println("{text : '刷新',iconCls:'refresh',handler:refresh}");
				first=false;
			}
			if(!isNeedCheck() || this.hasExportRight()){
				if (!first)
					out.println(",");
				out
						.println("{text : '导出Excel',iconCls:'exportExcel',handler:exportExcel2},");
				out
				        .println("{text : '导出Excel2',iconCls:'exportExcel',handler:exportExcel3},");
				out
						.println("{text : '导出页面',iconCls:'exportExcel',handler:exportExcel}");
				first = false;
			}
			if (!isNeedCheck() || this.hasDeleteAllRight()) {
				if (!first)
					out.println(",");
				out
						.println("{text : '删除全部',iconCls:'removeAll',handler:removeAll}");
				first = false;
			}
			if (!isNeedCheck() || hasImportRight()) {
				if (!first)
					out.println(",");
				out
						.println("{text :'导入Excel',iconCls:'option',handler:inputExcel}");
				first = false;
			}
			if (customButton != null
					&& (!isNeedCheck() || this.hasCustomRight())) {
				logger.debug("custom button ,first flase is"+first);
				if (!first)
					out.println(",");
				out.println(customButton);
			}
			out.println("]});");
			
			out.println("var combo = Ext.create('Ext.form.ComboBox', {");
			out.println("   store: new Ext.data.SimpleStore({");
			out.println("        fields: ['abbr', 'state'],");
			out
					.println("        data :[['10','10'],['20','20'],['30','30'],['40','40'],['50','50']");
			if (this.isShowAll()) {
				out.println(",['0','全部']");
			}
			out.print("]");
			out.println("    }),");
			out.println("    valueField:'abbr',");
			out.println("    displayField:'state',");
			out.println("    mode: 'local',");
			out.println("    editable: true,");
			out.println("    triggerAction: 'all',");
			out.println("    width:40,");
			out.println("    emptyText:recond,");
			out.println("    selectOnFocus:true");
			out.println("});");
			out.println("var pageBar=new Ext.PagingToolbar({");
			out.println("    store: store,");
			out.println("   dock : 'bottom',");
			out.println("    pageSize: recond,");
			out.println("    displayInfo: true,");
			out.println("    displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',");
			out.println("     emptyMsg: '没有记录',");
			out.println("     items:['-',combo,'每页']");
			out.println("      })");
			//out.println("var cb = new Ext.grid.CheckboxSelectionModel()");
			out.println("var colM=new Ext.grid.ColumnModel([");
			if (SemWebAppUtils.isNotEmpty(customGridHead)) {
				out.println(customGridHead);
			} else {
				out.println("new Ext.grid.RowNumberer({");
				out.println("header : '行号',");
				out.println("width : 40");
				out.println("}),");
				//out.println("cb,");
				iter = headList.iterator();
				int i = 0;
				while (iter.hasNext()) {
					String[] content = (String[]) iter.next();
					int length = content.length;
					out.println((i == 0 ? "" : ",") + "{header: '" + content[1]
							+ "', dataIndex: '" + content[0]
							+ "', sortable: true");
					if (length >= 4) {
						out.println(",mtext:'" + content[3] + "'");
						out.println(",mcol:" + content[2]);
					}
					out.println("}");
					i++;
				}
			}
			out.println("]);");
			//grid
			out.println("var cb = Ext.create('Ext.selection.CheckboxModel',{ checkOnly :false })");
			out.println("var grid = Ext.create('Ext.grid.GridPanel', {");
			if (gridTitle != null) {
				//out.println("title :'" + gridTitle + "',");
			} else {
				if (recordLabel != null) {
			//		out.println("title :'" + recordLabel + "管理',");
				}
			}
			columnAutoFill = (columnAutoFillStr == null)
					|| !("N".equalsIgnoreCase(columnAutoFillStr));
			out.println("renderTo : 'grid-div',");
			out.println("frame:true,");
			out.println("selModel: cb,");
			out.println("height : document.documentElement.clientHeight,");
			out.println("tbar : toolbar,");
			if (isHeadComplex()) {
				out.println("view:new MyGridView(viewConfig),");
				out.println("autoExpandColumn:3,");
				out.println("layout:fit,");
				out.println("cm:colM,");
			} else {
				out.println("viewConfig : {columnsText: '显示的列',");
				out.println("autoFill : " + columnAutoFill + "},");
				out.println("columns:[");
				if (SemWebAppUtils.isNotEmpty(customGridHead)) {
					out.println(customGridHead);
				} else {
					out.println("new Ext.grid.RowNumberer({");
					out.println("header : '行号',");
					out.println("width : 40");
					out.println("}),");
					//out.println("cb,");
					iter = headList.iterator();
					int i = 0;
					while (iter.hasNext()) {
						String[] content = (String[]) iter.next();

						out.println((i == 0 ? "" : ",") + "{header: '"
								+ content[1] + "', dataIndex: '" + content[0]
								+ "'");
						if (content.length == 3) {// 带有超级链接
							logger.debug(content[2]+","+content[2].startsWith("_"));
							if(content[2].startsWith("_")){//user defined renderer
								out
								.println(",renderer:function link(value,row,rec){return "+content[2].substring(1)+";}");
							}else if(content[2].startsWith("@")){
								out
								.println(",renderer:function(value){ return  "+content[2].substring(1)+".getById(value).get('name');}");								
							}
							else if (content[2].indexOf("%") != -1) {//hyp link
								String link = content[2].replaceAll("%s",
										"'+rec.get(\"id\")+'").replaceAll("%v",
										"'+value+'");
								out
										.println(",renderer:function link2(value,row,rec){return toLink('"
												+ link + "',value);}");
							} else {//attach
								out
										.println(",renderer:function link(value,row,rec){return getLink(value,rec,'"
												+ content[2] + "');}");
							}
						}else{
							out
							.println(",renderer:formatQtip,flex:1 ");
						}
						out.println(", sortable: true}");
						i++;
					}
				}
				out.println("],");
			}

			out.println("store: store,");
			out.println("stripeRows : true,");
			out.println("autoScroll : true,");
			//out.println("sm : cb,");

			out.println("dockedItems : [pageBar]");
			out.println("})");
			out.println("Ext.QuickTips.init();");
			out
					.println("Ext.form.Field.prototype.msgTarget = 'qtip';//统一指定错误信息提示方式");
			if (!isNeedCheck() || this.hasUpdateRight()) {
				out
						.println("grid.on('rowdblclick', function(grid) {showModify();});");
			}
			out.println("function formatQtip(data,metadata){");
			out.println("  var title =''; var tip =data; metadata.attr = 'ext:qtitle=\"' + title + '\"' + ' ext:qtip=\"' + tip + '\"';");
			// out.println(" return \"<a
			// href='/commonWeb/downloadFileAction.do?FILE_ID=\"+attachRealID+\"'>\"+value+\"</a>\";");
			out
					.println("return data;");
			out.println("}");
			
			
			out.println("function getLink(value,rec,attachID){");
			out.println(" var attachRealID=rec.get(attachID);");
			// out.println(" return \"<a
			// href='/commonWeb/downloadFileAction.do?FILE_ID=\"+attachRealID+\"'>\"+value+\"</a>\";");
			out
					.println("return toLink('/portal/showAttachAction.do?FILE_ID='+attachRealID+'&FILENAME='+value,value);");
			out.println("}");
			out.println("function toLink(hyperLink,value){");
			out
					.println(" return \"<a target='_blank' href='\"+encodeURI(hyperLink)+\"')>\"+value+\"</a>\";");
			out.println("}");
            out.println("var gridHeadField=Ext.create('Ext.form.Hidden', {name:'excelHead'});");
			out.println("var form = new Ext.create('Ext.form.Panel', {");
			out.println("labelSeparator : '：',");
			out.println("frame:true,");
			out.println("id:'semForm',");
			out.println("border:false,");
			out.println(hasFileUpload() ? "fileUpload:true," : "");
			out.println("items : [");
			out.println(formString);
			out.println(",{xtype:'hidden', name : 'id',fieldLabel:'id'},");
			out.println("gridHeadField],");
			out.println("buttons:[");
			out.println("{text : '关闭',name:'detailCloseButton',");
			out.println(" handler : function(){win.hide();}");
			out.println("},{text : '提交',name:'detailSubmitButton',");
			out.println(" handler : submitForm}");
			out.println("]");
			out.println("});");
			out.println("gridHeadField.setValue('"+gridBody+"');");
			out.println("var win = Ext.create('Ext.window.Window', {");
			out.println("    layout:'fit',");
			out.println("    width:" + formWidth + ",");
			out.println("    closeAction:'hide',");
			out.println("    height:" + formHeight + ",");
			out.println("    resizable : false,");
			out.println("    shadow : true,");
			out.println("    modal :true,");
			out.println("    closable:true,");
			out.println("    bodyStyle:'padding:5 5 5 5',");
			out.println("    animCollapse:true,");
			out.println("items:[form]");
			out.println("});");
			// out.print("win.show();");
			// out.println("win.hide();");
			out.println("function PageSize(recond){");
			out.println("pageBar.pageSize=recond;");
			out.println("store.pageSize=recond;");
			out.println("this.recond=recond;");
			out.println("store.load({params:{start:0" + searchParameters
					+ ",limit:recond, action: 'list'}});");
			out.println("};");
			out
					.println("combo.on('select',function(a,b,c){PageSize(parseInt(a.getValue()));});");
			out.println("store.load({params:{first:'Y',start:0"
					+ searchParameters + ",limit:recond, action: 'list'}});");
			out.println("store.on('beforeload', function(thiz,options) { ");
			out
					.println("Ext.apply(thiz.baseParams, {limit:recond,action: 'list' "
							+ searchParameters + "}); ");
			out.println("}); ");
	

			out.println("function showAdd(){");
			out.println("form.reset();");
			out.println("form.wType = 1;");
			out.println("win.setTitle('新增" + recordLabel + "信息');");
			out.println("win.show();");
			if (customAddImpl != null) {
				out.println(customAddImpl);
			}
			out.println("}");
			out.println("function refresh(){");
			out.println("store.reload();");
			// out.println("store.load({params:{start:0" + searchParameters
			// + ",limit:recond, action: 'list'}});");
			out.println("}");
			out.println("function showAll(){");
			for (int i = 0; i < searchFields.length; i++) {
				out.println("search" + searchFields[i] + "='';");
			}
			out
					.println("    store.load({params:{start:0, limit:recond, action: 'list'}});");
			out.println("}");
			if (otherSearchForm) {
				out
						.println("var win2 = Ext.create('Ext.Window',{layout:'fit',width:"
								+ (SemWebAppUtils.isNotEmpty(searchFormWidth) ? searchFormWidth
										: this.formWidth)
								+ ",closeAction:'hide',");
				out
						.println("height:"
								+ (SemWebAppUtils
										.isNotEmpty(this.searchFormHeight) ? searchFormHeight
										: this.formHeight)
								+ ",resizable : false,shadow : true,modal :true,closable:true,bodyStyle:'padding:5 5 5 5',"
								+ "animCollapse:true,items:[searForm]});");
			}
			out.println("function showSearch(){");

			if (otherSearchForm) {
				out.println("searForm.reset();");
				out.println("win2.setTitle('查询');");
				out.println("win2.show();");
			} else {
				out.println(" form.reset();");
				out.println("form.wType = 2;");
				out.println("win.setTitle('查询');");
				out.println("win.show();");
			}
			out.println("}");
			out.println("function showModify(){");
			out.println("   win.show(); ");
			out.println("   win.hide(); ");
			out
					.println("   var recs = grid.getSelectionModel().getSelection();");
			out.println("   var num=recs.length;");
			out.println("   if(num > 1){");
			out.println("Ext.MessageBox.alert('提示','每次只能修改一条" + recordLabel
					+ "信息。');");
			out.println("}else if(num ==0){");
			out.println("    Ext.MessageBox.alert('提示','请选择要修改的" + recordLabel
					+ "信息。');");
			out.println("}else{   ");
			out.println("   form.wType=3;");
			out.println("   win.setTitle('修改" + recordLabel + "信息');");
			out.println("   var rec = recs[0];");
			out.println("   form.getForm().loadRecord(rec);");
			out.println("   win.show(); ");
			if (customModifyImpl != null) {
				out.println(customModifyImpl);
			}
			out.println("}");
			out.println("}");
			out.println("function removeAll(){");
			out
					.println("Ext.MessageBox.confirm('信息','请确认删除所有记录',function(btnId){");
			out.println(" if(btnId == 'yes'){");
			out.println("var idList=getAllIdList();");
			out.println(" var Ids = idList.join('-');");
			out.println(" var msgTip = Ext.MessageBox.show({");
			out.println("width : 250,");
			out.println("msg:'正在删除全部记录，请稍候....'  });");
			out.println("  Ext.Ajax.request({");
			out.println(" url : '" + this.convertHref(action) + "action=delete',");
			out.println("params : {Ids : Ids},");
			out.println("  method : 'POST',");
			out.println(" success : function(response,action){");
			out.println(" msgTip.hide();");
			out
					.println("var result = Ext.util.JSON.decode(response.responseText);");
			out.println("if(result.success){store.removeAll();");
			out.println(" Ext.Msg.alert('提示','删除所有" + recordLabel
					+ "记录成功');}else{");
			out.println("if(typeof(result.message) != 'undefined'){");
			out.println(" Ext.Msg.alert('提示','删除所有" + recordLabel
					+ "记录失败:'+result.message);");
			out.println("}else{");
			out.println(" Ext.Msg.alert('提示','删除所有" + recordLabel + "记录失败:');");
			out.println("}");
			out.println(" } },");
			out.println(" failure : function(response,action){ msgTip.hide();");
			out.println("if(typeof(result.message) != 'undefined'){");
			out.println(" Ext.Msg.alert('提示','删除所有" + recordLabel
					+ "记录失败:'+result.message);");
			out.println("}else{");
			out.println(" Ext.Msg.alert('提示','删除所有" + recordLabel + "记录失败:');");
			out.println("}");
			out.println("  }}); }})}");
			out.println("function getAllIdList(){");
			out.println("var list = [];");
			out.println("var recs = store.getRange();");
			out.println("for(var i = 0 ; i < recs.length ; i++){");
			out.println("var rec = recs[i];");
			out.println("list.push(rec.get('id'))");
			out.println("}");
			out.println("return list;");
			out.println("}");
			out.println("function showDelete(){");
			out.println("var idList = getIdList();");
			out.println("var num = idList.length;");
			if(SemWebAppUtils.isNotEmpty(this.scriptBeforeDelete)){
				out.println(scriptBeforeDelete);
			}
			out.println("if(num == 0){return;}");
			out.println("Ext.MessageBox.confirm('提示','您确定要删除所选" + recordLabel
					+ "吗？',function(btnId){");
			out.println("  if(btnId == 'yes')deleteRecord(idList);");
			out.println("})");
			out.println("}");
			out.println("function deleteRecord(idList){");
			out.println("var Ids = idList.join('-');");
			out.println("var msgTip = Ext.MessageBox.show({");
			out.println("title:'提示',");
			out.println("width : 250,");
			out.println("msg:'正在删除" + recordLabel + "信息请稍后......'");
			out.println("});");
			out.println("Ext.Ajax.request({");
			out.println("url : '" + this.convertHref(action) + "action=delete',");
			out.println("params : {Ids : Ids" + pageParameterSB + "},");
			out.println("method : 'POST',");
			out.println("success : function(response,action){");
			out.println("msgTip.hide();");
			out.println("var result = Ext.util.JSON.decode(response.responseText);");
			out.println("if(result.success){");
			out.println("for(var i = 0 ; i < idList.length ; i++){");
			out.println("var index = store.find('id',idList[i]);");
			out.println("if(index != -1){");
			if (isNeedUpdateAfterModify()) {
				out.println("refresh();");
			} else {
				out.println("var rec = store.getAt(index)");
				out.println("store.remove(rec);");
			}

			out.println("}");
			out.println("}");
			out.println("Ext.Msg.alert('提示','删除" + recordLabel + "信息成功。');");
			out.println("}else{");
			out.println("if(typeof(result.message) != 'undefined'){");
			out.println(" Ext.Msg.alert('提示','删除所有" + recordLabel
					+ "记录失败:'+result.message);");
			out.println("}else{");
			out.println("Ext.Msg.alert('提示','删除" + recordLabel + "信息失败！');");
			out.println("}");
			out.println("}");
			out.println("},");
			out.println("failure : function(response,action){");
			out.println("msgTip.hide();");
			out.println("if(typeof(result.message) != 'undefined'){");
			out.println(" Ext.Msg.alert('提示','删除所有" + recordLabel
					+ "记录失败:'+result.message);");
			out.println("}else{");
			out.println("Ext.Msg.alert('提示','删除" + recordLabel + "信息请求失败！');");
			out.println("}");
			out.println("}");
			out.println("});");
			out.println("}");
			out.println("function inputExcel(){");
			out.println("var importForm = Ext.create('Ext.form.Panel', {");
			out.println("    fileUpload : true,    ");
			out.println("    frame: true,   ");
			out.println("    waitMsgTarget: true,   ");
			out.println("    labelAlign: 'right',   ");
			out.println("    baseCls: 'x-plain',   ");
			out.println("    labelWidth: 140, ");
			out.println("    labelHeight: 23,  ");
			out.println("    defaultType: 'textfield',   ");
			out.println("    items: [{fieldLabel :'请选择导入文件(.xls)',    ");
			out.println("             id : 'importFile',   ");
			out.println("             name : 'importFile',       ");
			out.println("             itemCls:'float-left',   ");
			out.println("             clearCls : 'allow-float',   ");
			out.println("             inputType: 'file',     ");
			out.println("             readOnly : false,    ");
			out.println("             width : 310,");
			out.println("             height: 22,   ");
			out.println("             allowBlank : false ,");
			out.println("             validator :  function (value){   ");
			out
					.println("                           var start = value.lastIndexOf('.');   ");
			out
					.println("                           var end = value.length;   ");
			out
					.println("                          var excelVal = value.substring((start+1),end);   ");
			out.println("                            if(excelVal=='xls'){   ");
			out.println("                              return true;   ");
			out.println("                              }else{   ");
			out
					.println("                               return '导入必须是Excel文件类型';   ");
			out.println("                                }}}]});");
			out.println("   if(inputWindow!=null){   ");
			out.println("    inputWindow.destroy();   } ");
			out.println("    var inputWindow =Ext.create('Ext.window.Window',{   ");
			out.println("    width: 500,   ");
			out.println("    height: 105,   ");
			out.println("    minWidth: 200,   ");
			out.println("    minHeight: 200, ");
			out.println("    layout: 'fit',   ");
			out.println("    plain:true,     ");
			out.println("    buttonAlign:'center',");
			out.println("    bodyStyle:'padding:5px;',      ");
			out.println("    items: importForm,    ");
			out.println("    buttons: [{   ");
			out.println("        text: '导入',   ");
			out
					.println("        handler: function() {                          ");
			out.println("            if (importForm.form.isValid()) {   ");
			out.println("                importForm.form.submit({   ");
			out.println("                    url:'" + this.convertHref(action)
					+ "action=importExcel',");
			out.println("method : 'POST',");
			out.println("waitMsg : '正在上传文件，请稍后',");
			out
					.println("                    success: function(form,action){   ");
			out.println("                        inputWindow.destroy();   ");
			out
					.println("                        Ext.MessageBox.alert('提示成功',action.result.message);    ");
			out.println("showAll();");
			out.println("                           },   ");
			out.println("                    failure: function(form, action){   ");
			out.println("                        Ext.MessageBox.alert('提示失败', '导入Excel文件失败:' + action.result.message);     ");
			//out.println("Ext.MessageBox.alert('提示失败', '导入Excel文件失败');");
			//out.println("                      Ext.MessageBox.alert('提示失败',action.result.message);     ");
			
			out.println("                           }       ");
			out.println("                });         ");
			out.println("            } else {    ");
			out
					.println("                Ext.MessageBox.alert('提交失败', '导入必须是Excel文件类型!');        ");
			out.println("            }   ");
			out.println("        }       ");
			out.println("    },{text: '取消',   ");
			out.println("        handler: function() {   ");
			out.println("               inputWindow.destroy();    ");
			out.println("        }}]});   ");
			out.println("inputWindow.show();};");
			out.println("function submitForm(){");
			if(this.submitContent!=null) out.println(submitContent);
			if (SemWebAppUtils.isNotEmpty(userFiledsString)) {
				String[] fields = userFiledsString.split(",");
				for (int j = 0; j < fields.length; j++) {
					out.println("var tempValue=form.getForm().findField('"
							+ fields[j] + "').getEl().dom.realValue;");
					out
							.println("if(typeof(tempValue)!='undefined'&&tempValue!=''){");
					out.println("form.getForm().findField('" + fields[j]
							+ "').setValue(tempValue);");
					out.println("}");
				}

			}

			out.println("if(form.wType==1){");
			out.println("form.form.submit({");
			out.println("clientValidation:true,//进行客户端验证");
			out.println("waitMsg : '正在提交数据请稍后',//提示信息");
			out.println("waitTitle : '提示',//标题");
			out.println("url : '" + this.convertHref(action) + "action=add',//请求的url地址");
			out.println("method:'POST',//请求方式");
			out.println("params  :{'action':'add'" + pageParameterSB + "},");
			out.println("success:function(form,action){//加载成功的处理函数");
			out.println("win.hide();");
			if(this.isContinuous()){
			     out.println("showAdd();");
			}
			if (isNeedUpdateAfterAdd()) {
				out.println("refresh();");
			} else {
				out.println("updateList(action.result.id,0);");
			}
			out.println("if(typeof(action.result.message) != 'undefined'){");
			out.println("Ext.Msg.alert('提示',action.result.message);");
			out.println("}else{");
			out.println("Ext.Msg.alert('提示','新增" + recordLabel + "成功');");
			out.println("}},");
			out.println("failure:function(form,action){//加载失败的处理函数");
			out.println("if(typeof(action.result.message) != 'undefined'){");
			out.println("Ext.Msg.alert('提示',action.result.message);");
			out.println("}else{");
			out.println("Ext.Msg.alert('提示','新增" + recordLabel + "失败');");
			out.println("}");
			out.println("}");
			out.println("});");
			out.println("}else if(form.wType==3){//修改");
			// out.println("var fields =form.items;");
			// out.println("for(var i = 0 ; i < fields.length ; i++){");
			// out.println("var item = fields.itemAt(i);");
			// out.println("var value = item.getValue();");
			// out.println("alert(item.getNea+'->'+value);");
			// out.println("}");

			out.println("form.form.submit({");
			out.println("clientValidation:true,//进行客户端验证");
			out.println("waitMsg : '正在提交数据请稍后',//提示信息");
			out.println("waitTitle : '提示',//标题");
			out.println("url : '" + this.convertHref(action) + "action=update',//请求的url地址");
			out.println("method:'POST',//请求方式");
			out.println("params  :{'action':'update'" + pageParameterSB + "},");
			out.println("success:function(form,action){");
			out.println("win.hide();");
			if (isNeedUpdateAfterModify()) {
				out.println("refresh();");
			} else {
				out.println("updateList(action.result.id,1);");
			}
			out.println("if(typeof(action.result.message) != 'undefined'){");
			out.println("Ext.Msg.alert('提示',action.result.message);");
			out.println("}else{");
			out.println("Ext.Msg.alert('提示','修改" + recordLabel + "成功');");
			out.println("}},");
			out.println("failure:function(form,action){");
			out.println("if(typeof(action.result.message) != 'undefined'){");
			out.println("Ext.Msg.alert('提示',action.result.message);");
			out.println("}else{");
			out.println("Ext.Msg.alert('提示','修改" + recordLabel + "失败');");
			out.println("}");
			out.println("}});");
			out.println("}else{//过滤");
			if (searchFields != null) {
				for (int j = 0; j < searchFields.length; j++) {
					out.println("if(form.getForm().findField('" + searchFields[j]
					      					               					 + "').xtype=='datefield'){");
					      					out.println("search" + searchFields[j]
					      					               					 + "=form.getForm().findField('" + searchFields[j]
					      					               					 + "').value;");
					      					out.println("}else{");
					      					out.println("search" + searchFields[j]
					      					 + "=form.getForm().findField('" + searchFields[j]
					      					 + "').getValue();");
					      					out.println("}");
					//out.println("search" + searchFields[j]
					//		+ "=document.getElementById('" + searchFields[j]
					//		+ "').value;");
				}
			}
			out.println("store.load({params:{start:0" + searchParameters
					+ ",limit:recond, action: 'list'}});");
			out.println("win.hide();");
			out.println("}");
			out.println("}");
			out.println("function updateList(selectID,type){");
			out.println("var fields = getFormFieldsObj(selectID);");
			out.println("var index = store.find('id',fields.id);");
			out.println("if(type == 1){");
			out.println("var item = store.getAt(index);");
			out.println("for(var fieldName in fields){");
			out.println("item.set(fieldName,fields[fieldName]);");
			out.println("}");
			out.println("store.commitChanges();");
			out.println("}else{");
		//	out.println("var rec =Ext.data.Record.create(fields);");
			// out.println("store.add(rec);");
			out.println("form.getForm().findField('id').setValue(selectID);");
			out.println("store.insert(0,form.getValues());");
			out.println("}");
			out.println("}");
			out.println("var obj = {};");
			out.println("function getFormFieldsObj(selectID){");
			out.println("   var fields =form.items;    ");
			out.println("   for(var i = 0 ; i < fields.length ; i++){");
			//out.println("    var item = fields.itemAt(i);");
			out.println("    var item = fields.items[i];");
			out.println("	  render(item);");
			out.println("	}");
			out.println("    if(Ext.isEmpty(obj['id'])){");
			out.println("            obj['id'] = selectID;");
			out.println("     }");
			out.println("     return obj;");
			out.println("  }");
			out.println("  function render(item){");
			out
					.println("     if(item.getXType()=='panel'  ||item.getXType()=='fieldset'){");
			out.println("	   var subItems=item.items;");
			out.println("	   if(typeof(subItems) != 'undefined'){");
			out.println("	     for(var i=0;i<subItems.length;i++){");
			//out.println("	      var subItem = subItems.itemAt(i);");
			out.println("	      var subItem = subItems.items[i];");
			out.println("	      render(subItem);");
			out.println("	     }");
			out.println("		}");
			out.println("	 }else{");
			out.println("	   var value = item.getValue();");
			out.println("       if(item.getXType() == 'combo'||item.getXType()=='systemcombo'){");
			// out
			// .println(" var index = item.store.find('id',value);");
			out
					.println("             var index = item.store.indexOfId(value);");
			out
					.println("             if(index != -1){var selItem = item.store.getAt(index);");
			out
					.println("             value = selItem.get('name');obj[item.name]=value; obj[item.hiddenName] = selItem.get('id');}");
			out.println("        }");
			out.println("if(item.getXType()=='datefield'){");
			out.println("    value=item.value;");
			out.println("}");
			out.println("        obj[item.name] = value;");
			out.println("	 }");
			out.println("  }");

			out.println("function getIdList(){");
			out.println("var recs = grid.getSelectionModel().getSelection();");
			out.println("var list = [];");
			out.println("if(recs.length == 0){");
			out.println("Ext.MessageBox.alert('提示','请选择要进行操作的" + recordLabel
					+ "！');");
			out.println("}else{");
			out.println("for(var i = 0 ; i < recs.length ; i++){");
			out.println("var rec = recs[i];");
			out.println("list.push(rec.get('id'))");
			out.println("}");
			out.println("}");
			out.println("return list;");
			out.println("}");
			out.println(customButtonImpl);
			out.println("function exportExcel(){");
			out.println("   Ext.ux.Grid2Excel.Save2Excel(grid);");
			out.println("}");
			out.println("function exportExcel2(){");
			out.println("win.show();");
			//out.println("form.getForm().url=\""+action+"?action=exportExcel"+ currentParameterSB +"\";");
			// out.println("form.getForm().submit();");  
			//out.println("	 form.form.submit({");
			out.println("form.getForm().getEl().dom.action='"+this.convertHref(action)+"action=exportExcel"+ currentParameterSB);
			out.println("form.getForm().getEl().dom.submit()");
			//out.println("url:'"+action+"?action=exportExcel"+ currentParameterSB +",");
			//out.println(" waitMsg : '正在导出EXCEL(最大值1000条记录)',");
			//out.println("  clientValidation:false,");
			//out.println("method:'POST',//请求方式");
			//out.println(" success: function(form,action){Ext.MessageBox.alert('提示','导出Excel成功');}, ");
			//out.println("failure: function(){Ext.MessageBox.alert('提示', '导出Excel失败');}});");
			out.println("   win.hide(); ");
			out.println("}");
			out.println("function exportExcel3(){");
			out.println("win.show();");
				out.println("form.getForm().getEl().dom.action='"+this.convertHref(action)+"action=exportExcel2"+ currentParameterSB);
			out.println("form.getForm().getEl().dom.submit()");
		
			out.println("   win.hide(); ");
			out.println("}");
			if (isAddForInitial()) {
				out.println("showAdd();");
			}
			
			if (isSearchForInitial()) {
				out.println("showSearch();");
			}
		
			
			//out.println("window.open('" + action + "?action=exportExcel&gridBody="+gridBody
		//			+ currentParameterSB + ");");
			// out.println("Ext.Ajax.request({");
			// out.println("url : 'reportModuleAction.do?action=exportExcel',");
			// out.println("params : {weblogicRe :
			// '12343'"+searchParameters+"},");
			// out.println("method : 'POST'");
			// out.println("});");
			

			if (otherSearchForm) {
				out.println("function submitSearch(){");
				if (SemWebAppUtils.isNotEmpty(userFiledsString)) {
					String[] fields = userFiledsString.split(",");
					for (int j = 0; j < fields.length; j++) {
						out
								.println("var tempValue=searForm.getForm().findField('"
										+ fields[j]
										+ "').getEl().dom.realValue;");
						out.println("if(typeof(tempValue)!='undefined'&&tempValue!=''){");
						out.println("searForm.getForm().findField('"
								+ fields[j] + "').setValue(tempValue);");
						out.println("}");
					}
				}
				if (searchFields != null) {
					for (int j = 0; j < searchFields.length; j++) {
						out.println("if(searForm.getForm().findField('" + searchFields[j]
						      					               					 + "').xtype=='datefield'){");
						      					out.println("search" + searchFields[j]
						      					               					 + "=searForm.getForm().findField('" + searchFields[j]
						      					               					 + "').value;");
						      					out.println("}else{");
						      					out.println("search" + searchFields[j]
						      					 + "=searForm.getForm().findField('" + searchFields[j]
						      					 + "').getValue();");
						      					out.println("}");
						//out.println("search" + searchFields[j]
						//		+ "=document.getElementById('"
						//		+ searchFields[j] + "').value;");
					}
				}
				out.println("store.load({params:{start:0" + searchParameters
						+ ",limit:recond, action: 'list'}});");
				out.println("win2.hide();");
				out.println("}");
			}
			out.println("});");
			out.println("</script>");
			out
					.println("<body><div id='grid-div' style='width:100%; height:100%;' /></body>");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return (EVAL_PAGE);

	}

	private void convertRightString() {
		String currentRight=(String) pageContext.getAttribute(
				SemWebAppConstants.RIGHT_CODE, PageContext.REQUEST_SCOPE);
		logger.debug("currentRight["+currentRight+"],rightString["+rightString+"]");
		if(rightString==null){
			rightString=currentRight;//null is all pri
		}

		/**
		 * int length=rightString.length();
		 * logger.debug("rightString="+rightString);
		 * if(this.listRight==null&&length>0)
		 * listRight=rightString.substring(0,1);
		 * if(this.addRight==null&&length>1)
		 * addRight=rightString.substring(1,2);
		 * if(this.updateRight==null&&length>2)
		 * updateRight=rightString.substring(2,3);
		 * if(this.deleteRight==null&&length>3)
		 * deleteRight=rightString.substring(3,4);
		 * if(this.customRight==null&&length>4)
		 * customRight=rightString.substring(4,5);
		 */
		if (SemWebAppUtils.hasAddRight(rightString)&&SemWebAppUtils.hasAddRight(currentRight))
			addRight = "Y";
		if (SemWebAppUtils.hasUpdateRight(rightString)&&SemWebAppUtils.hasUpdateRight(currentRight))
			updateRight = "Y";
		if (SemWebAppUtils.hasDeleteRight(rightString)&&SemWebAppUtils.hasDeleteRight(currentRight))
			deleteRight = "Y";
		if (SemWebAppUtils.hasDeleteAllRight(rightString)&&SemWebAppUtils.hasDeleteAllRight(currentRight))
			deleteAllRight = "Y";
		if (SemWebAppUtils.hasListRight(rightString)&&SemWebAppUtils.hasListRight(currentRight))
			listRight = "Y";
		if (SemWebAppUtils.hasExprotRight(rightString)&&SemWebAppUtils.hasExprotRight(currentRight))
			exportRight = "Y";
		if (SemWebAppUtils.hasImportRight(rightString)&&SemWebAppUtils.hasImportRight(currentRight))
			importRight = "Y";
		if (SemWebAppUtils.hasCustomRight(rightString)&&SemWebAppUtils.hasImportRight(currentRight))
			customRight = "Y";
		if (SemWebAppUtils.hasDeleteAllRight(rightString)&&SemWebAppUtils.hasImportRight(currentRight))
			deleteAllRight = "Y";
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFormHeight() {
		return formHeight;
	}

	public void setFormHeight(String formHeight) {
		this.formHeight = formHeight;
	}

	public String getFormString() {
		return formString;
	}

	public void setFormString(String formString) {
		this.formString = formString;
	}

	public String getFormWidth() {
		return formWidth;
	}

	public void setFormWidth(String formWidth) {
		this.formWidth = formWidth;
	}

	public String getGridBody() {
		return gridBody;
	}

	public void setGridBody(String gridBody) {
		this.gridBody = gridBody;
	}

	public void release() {
		recordLabel = "";
		action = null;
		gridBody = null;
		formWidth = "400";
		formHeight = "300";
		formString = null;
		itemList = null;
		headList = null;
		itemMapList = null;
		searchFieldsString = null;
		userFiledsString = null;
		customButton = null;
		listRight = null;
		addRight = null;
		updateRight = null;
		deleteRight = null;
		deleteAllRight = null;
		customRight = null;
	}

	public void convertGridBody() {
		if (gridBody == null)
			return;
		headList = new ArrayList();
		itemList = new ArrayList();
		itemMapList = new ArrayList();
		String[] array = gridBody.split(",");
		for (int i = 0; i < array.length; i++) {
			String sub = array[i];
			String[] subArray = sub.split(":");
			String item = subArray[0];
			int length = subArray.length;
			if (item.indexOf(".") != -1) {
				String alials = "Alials" + i;
				itemMapList.add("{name:'" + alials + "',mapping:'" + item
						+ "'}");
				if (length > 1)
					subArray[1] = alials;
			} else {
				itemList.add(subArray[0]);
			}
			if (length > 1) {
				headList.add(subArray);
			}
			if (length > 2) {
				itemList.add(subArray[2]);
			}

		}
	}

	public String getRecordLabel() {
		return recordLabel;
	}

	public void setRecordLabel(String recordLabel) {
		this.recordLabel = recordLabel;
	}

	private String[] getSearchFields(String sourceString) {
		String tempStr = "[" + sourceString + "]";
		String str = tempStr.replaceAll("store:", "store:\"");
		str = str.replaceAll("\\),", "\\)\",");
		JSONArray ja = JSONArray.fromObject(str);
		String[] results = new String[ja.size()];
		for (int i = 0; i < ja.size(); i++) {
			JSONObject jb = ja.getJSONObject(i);
			String type = (String) jb.get("xtype");
			if ("combo".equalsIgnoreCase(type)) {
				results[i] = jb.getString("hiddenName");
			} else {
				results[i] = jb.getString("name");
			}
			logger.debug("result[" + i + "]=" + results[i]);
		}
		return results;
	}

	public String getSearchFormString() {
		return searchFormString;
	}

	public void setSearchFormString(String searchFormString) {
		this.searchFormString = searchFormString;
	}

	public String getSearchFieldsString() {
		return searchFieldsString;
	}

	public void setSearchFieldsString(String searchFieldsString) {
		this.searchFieldsString = searchFieldsString;
	}

	public String getSearchFormWidth() {
		return searchFormWidth;
	}

	public void setSearchFormWidth(String searchFormWidth) {
		this.searchFormWidth = searchFormWidth;
	}

	public String getSearchFormHeight() {
		return searchFormHeight;
	}

	public void setSearchFormHeight(String searchFormHeight) {
		this.searchFormHeight = searchFormHeight;
	}

	public String getUserFiledsString() {
		return userFiledsString;
	}

	public void setUserFiledsString(String userFiledsString) {
		this.userFiledsString = userFiledsString;
	}

	public String getCustomButton() {
		return customButton;
	}

	public void setCustomButton(String customButton) {
		this.customButton = customButton;
	}

	public boolean hasAddRight() {
		return "Y".equalsIgnoreCase(addRight);
	}

	public boolean hasDeleteRight() {
		return "Y".equalsIgnoreCase(deleteRight);
	}

	public boolean hasDeleteAllRight() {
		return "Y".equalsIgnoreCase(deleteAllRight);
	}

	public List getHeadList() {
		return headList;
	}

	public void setHeadList(List headList) {
		this.headList = headList;
	}

	public boolean hasUpdateRight() {
		return "Y".equalsIgnoreCase(updateRight);

	}

	public boolean hasFileUpload() {
		return "Y".equalsIgnoreCase(this.fileUpload);
	}

	public boolean isShowAll() {
		return "Y".equalsIgnoreCase(this.showAllRecord);
	}

	public boolean hasImportRight() {
		return "Y".equalsIgnoreCase(importRight);

	}

	public boolean hasListRight() {
		return "Y".equalsIgnoreCase(listRight);
	}

	public boolean isHeadComplex() {
		return "Y".equalsIgnoreCase(this.complexHead);
	}

	public boolean hasCustomRight() {
		return "Y".equalsIgnoreCase(customRight);
	}

	public boolean hasExportRight() {
		return "Y".equalsIgnoreCase(exportRight);
	}

	public String getAddRight() {
		return addRight;
	}

	public void setAddRight(String addRight) {
		this.addRight = addRight;
	}

	public String getCustomRight() {
		return customRight;
	}

	public void setCustomRight(String customRight) {
		this.customRight = customRight;
	}

	public String getDeleteRight() {
		return deleteRight;
	}

	public void setDeleteRight(String deleteRight) {
		this.deleteRight = deleteRight;
	}

	public String getListRight() {
		return listRight;
	}

	public void setListRight(String listRight) {
		this.listRight = listRight;
	}

	public String getRightString() {
		return rightString;
	}

	public void setRightString(String rightString) {
		this.rightString = rightString;
	}

	public String getUpdateRight() {
		return updateRight;
	}

	public void setUpdateRight(String updateRight) {
		this.updateRight = updateRight;
	}

	public String getGridTitle() {
		return gridTitle;
	}

	public void setGridTitle(String gridTitle) {
		this.gridTitle = gridTitle;
	}

	public String getExportRight() {
		return exportRight;
	}

	public void setExportRight(String exportRight) {
		this.exportRight = exportRight;
	}

	public String getNeedRightCheck() {
		return needRightCheck;
	}

	public void setNeedRightCheck(String needRightCheck) {
		this.needRightCheck = needRightCheck;
	}

	public boolean isNeedCheck() {
		return !"N".equalsIgnoreCase(needRightCheck);
	}

	public String getColumnAutoFillStr() {
		return columnAutoFillStr;
	}

	public void setColumnAutoFillStr(String columnAutoFillStr) {
		this.columnAutoFillStr = columnAutoFillStr;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getCustomGridHead() {
		return customGridHead;
	}

	public void setCustomGridHead(String customGridHead) {
		this.customGridHead = customGridHead;
	}

	public String getCustomButtonImpl() {
		return customButtonImpl;
	}

	public void setCustomButtonImpl(String customButtonImpl) {
		this.customButtonImpl = customButtonImpl;
	}

	public String getComplexHead() {
		return complexHead;
	}

	public void setComplexHead(String complexHead) {
		this.complexHead = complexHead;
	}

	public String getImportRight() {
		return importRight;
	}

	public void setImportRight(String importRight) {
		this.importRight = importRight;
	}

	public String getNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(String needUpdate) {
		this.needUpdate = needUpdate;
	}

	private boolean isNeedUpdateAfterModify() {
		return this.needUpdate != null && "Y".equalsIgnoreCase(needUpdate);
	}

	private boolean isNeedUpdateAfterAdd() {
		return this.needUpdateAfterAdd != null
				&& "Y".equalsIgnoreCase(needUpdateAfterAdd);
	}

	private boolean isAddForInitial() {
		return this.addForInitial != null
				&& "Y".equalsIgnoreCase(addForInitial);
	}

	private boolean isSearchForInitial() {
		return this.searchForInitial != null
				&& "Y".equalsIgnoreCase(searchForInitial);
	}
	
	public String getDeleteAllRight() {
		return deleteAllRight;
	}

	public void setDeleteAllRight(String deleteAllRight) {
		this.deleteAllRight = deleteAllRight;
	}

	public String getShowAllRecord() {
		return showAllRecord;
	}

	public void setShowAllRecord(String showAllRecord) {
		this.showAllRecord = showAllRecord;
	}
	
	private String convertHref(String href){
		String result;
		if(href.indexOf("?")!=-1){
			result=href+"&";
		}else{
			result=href+"?";
		}
		return result;
	}

	public String getExtContent() {
		return extContent;
	}

	public void setExtContent(String extContent) {
		this.extContent = extContent;
	}

	public String getContinuous() {
		return continuous;
	}

	public void setContinuous(String continuous) {
		this.continuous = continuous;
	}
	private boolean isContinuous() {
		return this.continuous != null
				&& "Y".equalsIgnoreCase(continuous);
	}

	public String getScriptBeforeDelete() {
		return scriptBeforeDelete;
	}

	public void setScriptBeforeDelete(String scriptBeforeDelete) {
		this.scriptBeforeDelete = scriptBeforeDelete;
	}
	

}
