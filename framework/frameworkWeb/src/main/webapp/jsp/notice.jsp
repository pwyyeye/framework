<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>

	<%@ include file="common.jsp"%>
	<%@ include file="uploadFile.jsp"%>
	<script type="text/javascript" src="extjs/ux/export-all.js" ></script>;
	<script>

var routeData = [ {
	id : '0',
	name : '正常公告'
}, {
	id : '1',
	name : '重要公告'
} ];

</script>
	<body>
		<script>
var searchmoduleID='';
var searchsubject='';
var searchcontent='';
var recond=30;
Ext.Loader.setConfig( {	enabled : true});
Ext.Loader.setPath('Ext.ux', 'extjs/ux');
Ext.require(['Ext.form.*', 'Ext.grid.*', 'Ext.data.*', 'Ext.messageBox.*','Ext.toolbar.Toolbar', 'Ext.PagingToolbar','Ext.window.Window', 'Ext.selection.CheckboxModel','Ext.ux.form.MultiSelect','Ext.ux.PreviewPlugin','Ext.ux.form.ItemSelector','Ext.ux.exporter.Exporter']);
Ext.require(['Ext.util.*','Ext.view.View','Ext.ux.DataView.Animated','Ext.XTemplate']);
Ext.onReady(function(){
	        setUploadFile=function(value){
	           uploadFileValue=uploadFileValue+value.name+','
	           Ext.getCmp('content').setValue(uploadFileValue)}
			var uploadWindow=
				 Ext.create(
						'Ext.window.Window',
						{
							layout : 'border',
							id : 'uploadWindow',
							items : [ {
								region : 'center',
								html : '<IFRAME frameBorder=0 width=100% height=100%  src=/portal/qiniuTest.jsp > </IFRAME>'
							} ],
							width : 500,
							height : 400,
							closable : false,
							modal : 'true',
							buttonAlign : 'right',
							autoScroll : false,
							bbar : [ '->',{
								text : '关闭',
								handler : function() {
									uploadWindow.hide();
									
								}
							} ]
						});
						function uploadFile(){
		
					uploadWindow.show();
			}
var searForm=new Ext.create('Ext.form.Panel', {labelSeparator : '：',frame:true,border:false,
items :[{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'subject',fieldLabel:'主题'},
				  {xtype:'textarea',width : 350,name : '内容',fieldLabel:'content'}],
buttons:[{text : '关闭',handler : function(){win2.hide();}},{text : '提交',handler : submitSearch}]});
Ext.define('datas', {extend : 'Ext.data.Model',idProperty : 'id',fields : ['id'
,'module'
,'subject'
,'content'
,'moduleID'
,'attachName'
,'attach'
,'startDate'
,'endDate'
,'showReport.do?id=%s&value=%v'
,'validStr'
,'valid'
]});
var store = Ext.create('Ext.data.Store',{
autoLoad :false,model: 'datas',
proxy :{type:'ajax',method : 'POST',
url : 'noticeAction.do?weblogic_jsession=1234567890&action=list',reader:{type:'json',totalProperty: 'results',root: 'items'
}}});
var toolbar = Ext.create('Ext.toolbar.Toolbar', {items:[
{text : '添加系统公告',iconCls:'add',handler:showAdd}
,
{text : '删除系统公告',iconCls:'remove',handler:showDelete}
,
{text : '修改系统公告',iconCls:'option',handler:showModify}
,
{text : '查找',   iconCls:'search',handler:showSearch},
{text : '显示全部',iconCls:'showAll',handler:showAll},
{text : '刷新',iconCls:'refresh',handler:refresh}
,
{text : '导出Excel',iconCls:'exportExcel',handler:exportExcel2},
{text :'导入Excel',iconCls:'option',handler:inputExcel}

,
{text : '删除全部',iconCls:'removeAll',handler:removeAll}
]});
var combo = Ext.create('Ext.form.ComboBox', {
   store: new Ext.data.SimpleStore({
        fields: ['abbr', 'state'],
        data :[['10','10'],['20','20'],['30','30'],['40','40'],['50','50']
,['0','全部']
]    }),
    valueField:'abbr',
    displayField:'state',
    mode: 'local',
    editable: true,
    triggerAction: 'all',
    width:40,
    emptyText:recond,
    selectOnFocus:true
});
var pageBar=new Ext.PagingToolbar({
    store: store,
   dock : 'bottom',
    pageSize: recond,
    displayInfo: true,
    displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
     emptyMsg: '没有记录',
     items:['-',combo,'每页'
]
      })
var colM=new Ext.grid.ColumnModel([
]);
var cb = Ext.create('Ext.selection.CheckboxModel',{ checkOnly :false })
var grid = Ext.create('Ext.grid.GridPanel', {
id:'grid',	
renderTo : 'grid-div',
frame:true,
selModel: cb,
height : document.documentElement.clientHeight,
tbar : toolbar,
viewConfig : {columnsText: '显示的列',
autoFill : true},
columns:[
new Ext.grid.RowNumberer({
header : '行号',
width : 40
}),
{header: '模块名', dataIndex: 'module'
,renderer:formatQtip
, sortable: true}
,{header: '主题', dataIndex: 'subject'
,renderer:formatQtip
, sortable: true}
,{header: '内容', dataIndex: 'content'
,renderer:formatQtip
, sortable: true}
,{header: '附件', dataIndex: 'attachName'
,renderer:function link(value,row,rec){return getLink(value,rec,'attach');}
, sortable: true}
,{header: '有效开始日期', dataIndex: 'startDate'
,renderer:formatQtip
, sortable: true}
,{header: '有效截止日期', dataIndex: 'endDate'
,renderer:function link2(value,row,rec){return toLink('showReport.do?id='+rec.get("id")+'&value='+value+'',value);}
, sortable: true}
,{header: '类型', dataIndex: 'validStr'
,renderer:formatQtip
, sortable: true}
],
store: store,
dockedItems : [pageBar],
stripeRows : true,
autoScroll : true
})
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'qtip';//统一指定错误信息提示方式
grid.on('rowdblclick', function(grid) {showModify();});
function formatQtip(data,metadata){
  var title =''; var tip =data; metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';
return data;
}
function getLink(value,rec,attachID){
 var attachRealID=rec.get(attachID);
return toLink('/portal/showAttachAction.do?FILE_ID='+attachRealID+'&FILENAME='+value,value);
}
function toLink(hyperLink,value){
 return "<a target='_blank' href='"+encodeURI(hyperLink)+"')>"+value+"</a>";
}
var gridHeadField=Ext.create('Ext.form.Hidden', {name:'excelHead'});
var form = new Ext.create('Ext.form.Panel', {
labelSeparator : '：',
frame:true,
id:'semForm',
border:false,
fileUpload:true,
items : [
{xtype:'systemcombo'},
			
				  {xtype:'datefield',width : 350,format:'Y-m-d',name : 'startDate',fieldLabel:'有效开始日期'},
				  {xtype:'datefield',width : 350,format:'Y-m-d',name : 'endDate',fieldLabel:'有效截止日期'},
				  {xtype:'idNameComBox',width:350,name: 'valid',hiddenName:'valid',selectData:routeData,fieldLabel:'类型'},
				  {xtype: 'fieldset',
				   layout: {
                     type : 'table',
                     columns : 3
                   },                 
                   layoutConfig:{columns: 3},   
                  items: [
                        {xtype:'textfield',width : 300,name : 'content',id:'content',fieldLabel:'附件'},{xtype:'button', text:'上传',handler:uploadFile}               
                   ]                   
                },		
				
,{xtype:'hidden', name : 'id',fieldLabel:'id'},
gridHeadField],
buttons:[
{text : '关闭',name:'detailCloseButton',
 handler : function(){win.hide();}
},{text : '提交',name:'detailSubmitButton',
 handler : submitForm}
]
});
gridHeadField.setValue('module:模块名,subject:主题,content:内容,moduleID,attachName:附件:attach,startDate:有效开始日期,endDate:有效截止日期:showReport.do?id=%s&value=%v,validStr:类型,valid');
var win = Ext.create('Ext.window.Window', {
    layout:'fit',
    width:400,
    closeAction:'hide',
    height:450,
    resizable : false,
    shadow : true,
    modal :true,
    closable:true,
    bodyStyle:'padding:5 5 5 5',
    animCollapse:true,
items:[form]
});
function PageSize(recond){
pageBar.pageSize=recond;
store.pageSize=recond;
this.recond=recond;
store.load({params:{start:0,'javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true','moduleID':searchmoduleID,'subject':searchsubject,'content':searchcontent,limit:recond, action: 'list'}});
};
combo.on('select',function(a,b,c){PageSize(parseInt(a.getValue()));});
store.load({params:{first:'Y',start:0,'javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true','moduleID':searchmoduleID,'subject':searchsubject,'content':searchcontent,limit:recond, action: 'list'}});
store.on('beforeload', function(thiz,options) { 
Ext.apply(thiz.baseParams, {limit:recond,action: 'list' ,'javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true','moduleID':searchmoduleID,'subject':searchsubject,'content':searchcontent}); 
}); 
function showAdd(){
form.reset();
form.wType = 1;
win.setTitle('新增系统公告信息');
win.show();
}
function refresh(){
store.reload();
}
function showAll(){
searchmoduleID='';
searchsubject='';
searchcontent='';
    store.load({params:{start:0, limit:recond, action: 'list'}});
}
var win2 = Ext.create('Ext.Window',{layout:'fit',width:400,closeAction:'hide',
height:300,resizable : false,shadow : true,modal :true,closable:true,bodyStyle:'padding:5 5 5 5',animCollapse:true,items:[searForm]});
function showSearch(){
searForm.reset();
win2.setTitle('查询');
win2.show();
}
function showModify(){
   win.show(); 
   win.hide(); 
   var recs = grid.getSelectionModel().getSelection();
   var num=recs.length;
   if(num > 1){
Ext.MessageBox.alert('提示','每次只能修改一条系统公告信息。');
}else if(num ==0){
    Ext.MessageBox.alert('提示','请选择要修改的系统公告信息。');
}else{   
   form.wType=3;
   win.setTitle('修改系统公告信息');
   var rec = recs[0];
   form.getForm().loadRecord(rec);
   win.show(); 
}
}
function removeAll(){
Ext.MessageBox.confirm('信息','请确认删除所有记录',function(btnId){
 if(btnId == 'yes'){
var idList=getAllIdList();
 var Ids = idList.join('-');
 var msgTip = Ext.MessageBox.show({
width : 250,
msg:'正在删除全部记录，请稍候....'  });
  Ext.Ajax.request({
 url : 'noticeAction.do?weblogic_jsession=1234567890&action=delete',
params : {Ids : Ids},
  method : 'POST',
 success : function(response,action){
 msgTip.hide();
var result = Ext.util.JSON.decode(response.responseText);
if(result.success){store.removeAll();
 Ext.Msg.alert('提示','删除所有系统公告记录成功');}else{
if(typeof(result.message) != 'undefined'){
 Ext.Msg.alert('提示','删除所有系统公告记录失败:'+result.message);
}else{
 Ext.Msg.alert('提示','删除所有系统公告记录失败:');
}
 } },
 failure : function(response,action){ msgTip.hide();
if(typeof(result.message) != 'undefined'){
 Ext.Msg.alert('提示','删除所有系统公告记录失败:'+result.message);
}else{
 Ext.Msg.alert('提示','删除所有系统公告记录失败:');
}
  }}); }})}
function getAllIdList(){
var list = [];
var recs = store.getRange();
for(var i = 0 ; i < recs.length ; i++){
var rec = recs[i];
list.push(rec.get('id'))
}
return list;
}
function showDelete(){
var idList = getIdList();
var num = idList.length;
if(num == 0){return;}
Ext.MessageBox.confirm('提示','您确定要删除所选系统公告吗？',function(btnId){
  if(btnId == 'yes')deleteRecord(idList);
})
}
function deleteRecord(idList){
var Ids = idList.join('-');
var msgTip = Ext.MessageBox.show({
title:'提示',
width : 250,
msg:'正在删除系统公告信息请稍后......'
});
Ext.Ajax.request({
url : 'noticeAction.do?weblogic_jsession=1234567890&action=delete',
params : {Ids : Ids,'javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true'},
method : 'POST',
success : function(response,action){
msgTip.hide();
var result = Ext.util.JSON.decode(response.responseText);
if(result.success){
for(var i = 0 ; i < idList.length ; i++){
var index = store.find('id',idList[i]);
if(index != -1){
var rec = store.getAt(index)
store.remove(rec);
}
}
Ext.Msg.alert('提示','删除系统公告信息成功。');
}else{
if(typeof(result.message) != 'undefined'){
 Ext.Msg.alert('提示','删除所有系统公告记录失败:'+result.message);
}else{
Ext.Msg.alert('提示','删除系统公告信息失败！');
}
}
},
failure : function(response,action){
msgTip.hide();
if(typeof(result.message) != 'undefined'){
 Ext.Msg.alert('提示','删除所有系统公告记录失败:'+result.message);
}else{
Ext.Msg.alert('提示','删除系统公告信息请求失败！');
}
}
});
}
function inputExcel(){
var importForm = Ext.create('Ext.form.Panel', {
    fileUpload : true,    
    frame: true,   
    waitMsgTarget: true,   
    labelAlign: 'right',   
    baseCls: 'x-plain',   
    labelWidth: 140, 
    labelHeight: 23,  
    defaultType: 'textfield',   
    items: [{fieldLabel :'请选择导入文件(.xls)',    
             id : 'importFile',   
             name : 'importFile',       
             itemCls:'float-left',   
             clearCls : 'allow-float',   
             inputType: 'file',     
             readOnly : false,    
             width : 310,
             height: 22,   
             allowBlank : false ,
             validator :  function (value){   
                           var start = value.lastIndexOf('.');   
                           var end = value.length;   
                          var excelVal = value.substring((start+1),end);   
                            if(excelVal=='xls'){   
                              return true;   
                              }else{   
                               return '导入必须是Excel文件类型';   
                                }}}]});
   if(inputWindow!=null){   
    inputWindow.destroy();   } 
    var inputWindow =Ext.create('Ext.window.Window',{   
    width: 500,   
    height: 105,   
    minWidth: 200,   
    minHeight: 200, 
    layout: 'fit',   
    plain:true,     
    buttonAlign:'center',
    bodyStyle:'padding:5px;',      
    items: importForm,    
    buttons: [{   
        text: '导入',   
        handler: function() {                          
            if (importForm.form.isValid()) {   
                importForm.form.submit({   
                    url:'noticeAction.do?weblogic_jsession=1234567890&action=importExcel',
method : 'POST',
waitMsg : '正在上传文件，请稍后',
                    success: function(form,action){   
                        inputWindow.destroy();   
                        Ext.MessageBox.alert('提示成功',action.result.message);    
showAll();
                           },   
                    failure: function(form, action){   
                        Ext.MessageBox.alert('提示失败', '导入Excel文件失败:' + action.result.message);     
                           }       
                });         
            } else {    
                Ext.MessageBox.alert('提交失败', '导入必须是Excel文件类型!');        
            }   
        }       
    },{text: '取消',   
        handler: function() {   
               inputWindow.destroy();    
        }}]});   
inputWindow.show();};
function submitForm(){
if(form.wType==1){
form.form.submit({
clientValidation:true,//进行客户端验证
waitMsg : '正在提交数据请稍后',//提示信息
waitTitle : '提示',//标题
url : 'noticeAction.do?weblogic_jsession=1234567890&action=add',//请求的url地址
method:'POST',//请求方式
params  :{'action':'add','javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true'},
success:function(form,action){//加载成功的处理函数
win.hide();
updateList(action.result.id,0);
if(typeof(action.result.message) != 'undefined'){
Ext.Msg.alert('提示',action.result.message);
}else{
Ext.Msg.alert('提示','新增系统公告成功');
}},
failure:function(form,action){//加载失败的处理函数
if(typeof(action.result.message) != 'undefined'){
Ext.Msg.alert('提示',action.result.message);
}else{
Ext.Msg.alert('提示','新增系统公告失败');
}
}
});
}else if(form.wType==3){//修改
form.form.submit({
clientValidation:true,//进行客户端验证
waitMsg : '正在提交数据请稍后',//提示信息
waitTitle : '提示',//标题
url : 'noticeAction.do?weblogic_jsession=1234567890&action=update',//请求的url地址
method:'POST',//请求方式
params  :{'action':'update','javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true'},
success:function(form,action){
win.hide();
updateList(action.result.id,1);
if(typeof(action.result.message) != 'undefined'){
Ext.Msg.alert('提示',action.result.message);
}else{
Ext.Msg.alert('提示','修改系统公告成功');
}},
failure:function(form,action){
if(typeof(action.result.message) != 'undefined'){
Ext.Msg.alert('提示',action.result.message);
}else{
Ext.Msg.alert('提示','修改系统公告失败');
}
}});
}else{//过滤
if(form.getForm().findField('moduleID').xtype=='datefield'){
searchmoduleID=form.getForm().findField('moduleID').value;
}else{
searchmoduleID=form.getForm().findField('moduleID').getValue();
}
if(form.getForm().findField('subject').xtype=='datefield'){
searchsubject=form.getForm().findField('subject').value;
}else{
searchsubject=form.getForm().findField('subject').getValue();
}
if(form.getForm().findField('content').xtype=='datefield'){
searchcontent=form.getForm().findField('content').value;
}else{
searchcontent=form.getForm().findField('content').getValue();
}
store.load({params:{start:0,'javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true','moduleID':searchmoduleID,'subject':searchsubject,'content':searchcontent,limit:recond, action: 'list'}});
win.hide();
}
}
function updateList(selectID,type){
var fields = getFormFieldsObj(selectID);
var index = store.find('id',fields.id);
if(type == 1){
var item = store.getAt(index);
for(var fieldName in fields){
item.set(fieldName,fields[fieldName]);
}
store.commitChanges();
}else{
form.getForm().findField('id').setValue(selectID);
store.insert(0,form.getValues());
}
}
var obj = {};
function getFormFieldsObj(selectID){
   var fields =form.items;    
   for(var i = 0 ; i < fields.length ; i++){
    var item = fields.items[i];
	  render(item);
	}
    if(Ext.isEmpty(obj['id'])){
            obj['id'] = selectID;
     }
     return obj;
  }
  function render(item){
     if(item.getXType()=='panel'  ||item.getXType()=='fieldset'){
	   var subItems=item.items;
	   if(typeof(subItems) != 'undefined'){
	     for(var i=0;i<subItems.length;i++){
	      var subItem = subItems.items[i];
	      render(subItem);
	     }
		}
	 }else{
	   var value = item.getValue();
       if(item.getXType() == 'combo'||item.getXType()=='systemcombo'){
             var index = item.store.indexOfId(value);
             if(index != -1){var selItem = item.store.getAt(index);
             value = selItem.get('name');obj[item.name]=value; obj[item.hiddenName] = selItem.get('id');}
        }
if(item.getXType()=='datefield'){
    value=item.value;
}
        obj[item.name] = value;
	 }
  }
function getIdList(){
var recs = grid.getSelectionModel().getSelection();
var list = [];
if(recs.length == 0){
Ext.MessageBox.alert('提示','请选择要进行操作的系统公告！');
}else{
for(var i = 0 ; i < recs.length ; i++){
var rec = recs[i];
list.push(rec.get('id'))
}
}
return list;
}
		var excelForm = new Ext.create('Ext.form.Panel', {
			labelSeparator : '：',
			frame : true,
			id : 'excelForm',
			border : false,
			items : [ {
            xtype      : 'fieldcontainer',
            fieldLabel : '导出类型',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
                {
                    boxLabel  : 'Excel2003(.xls)',
                    name      : 'exportType',
                    inputValue: '0'
                }, {
                    boxLabel  : 'Excel2007(.xlsx)',
                    name      : 'exportType',
                    inputValue: '1'
                }
            ]
        }],
			buttons : [{
				text : '关闭',
				name : 'colseButton',
				handler : function() {
					excelWindow.hide();
				}
			}, {
				text : '导出',
				name : 'exportButton',
				handler : exportToFile
			} ]
		});
	


function exportExcel(){
   Ext.ux.Grid2Excel.Save2Excel(grid);
}
function exportExcel2(){
	//var excelWindow = Ext.create('Ext.window.Window', {
	//		items : [ {html:'<IFRAME  src=noticeAction.do?action=exportExcel&gridBody='+gridHeadField.getValue()+'&moduleID='+searchmoduleID+'&subject='+searchsubject+'&content='+searchcontent+'></IFRAME>'} ]
	//});
   //excelWindow.show(); 
   window.open('noticeAction.do?action=exportExcel&gridBody='+gridHeadField.getValue()+'&moduleID='+searchmoduleID+'&subject='+searchsubject+'&content='+searchcontent);
   //excelWindow.close();
}

function exportToFile(){
			var exportType=excelForm.form.findField('exportType').getValue();
			excelForm.form.url='noticeAction.do?action=exportExcel&type='+exportType+'gridBody='+gridHeadField.getValue()+'&moduleID='+searchmoduleID+'&subject='+searchsubject+'&content='+searchcontent;
			excelForm.form.submit();
			excelWindow.hide(); 
}

function submitSearch(){
if(searForm.getForm().findField('moduleID').xtype=='datefield'){
searchmoduleID=searForm.getForm().findField('moduleID').value;
}else{
searchmoduleID=searForm.getForm().findField('moduleID').getValue();
}
if(searForm.getForm().findField('subject').xtype=='datefield'){
searchsubject=searForm.getForm().findField('subject').value;
}else{
searchsubject=searForm.getForm().findField('subject').getValue();
}
if(searForm.getForm().findField('content').xtype=='datefield'){
searchcontent=searForm.getForm().findField('content').value;
}else{
searchcontent=searForm.getForm().findField('content').getValue();
}
store.load({params:{start:0,'javax.servlet.forward.query_string':'weblogic_jsession=1234567890&SEM_LOGIN_TOKEN=7dx29og4iejtplo7wvqeisd5&MODULE_ID=6&theme=gray','javax.servlet.forward.request_uri':'/portal/noticeAction.do','weblogic_jsession':'1234567890','javax.servlet.forward.servlet_path':'/noticeAction.do','javax.servlet.forward.context_path':'/portal','weblogic.servlet.forward.target_servlet_path':'/notice.jsp','weblogic.servlet.jsp':'true','moduleID':searchmoduleID,'subject':searchsubject,'content':searchcontent,limit:recond, action: 'list'}});
win2.hide();
}
});
</script>
<body><div id='grid-div' style='width:100%; height:100%;' /></body>	</body>
</html>
