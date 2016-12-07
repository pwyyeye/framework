<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="报表模板" action="reportModuleAction.do"
     gridBody="id:ID,module:模块名,name:报表名称,report:报表模板,validName:报表状态,valid,moduleID,needDateKey,needSeries,needTimekey,needDepartment,dateKeyType,needControlPoint,needLog,needWorkshop,needColor,needModel,needLine,needDateKeyStr,needTimekeyStr,needLineStr,needSeriesStr,needModelStr,needWorkshopStr,needColorStr,needDepartmentStr,dateKeyTypeStr,needLogStr,otherDatekeyMode,timekeyMode,customKey,parent,remark:备注,sortID,oldVersion,oldVersionName,parameterModule,parameterModuleStr,exportType,exportTypeStr" 
     searchFieldsString="moduleID,name,report"
     formWidth="700"
     formHeight="600"
     searchFormWidth="400"
     searchFormHeight="300"
     pageSize="30"
     customRight="Y"
     customButton="{text : '打开报表系统',iconCls:'COBTEST',handler:openReports},{text : '关闭报表系统',iconCls:'COBTEST',handler:closeReports}"
     searchFormString="{xtype:'systemcombo'},{xtype:'textfield',width : 300,name : 'name',fieldLabel:'报表名称'},
				  {xtype:'textfield',width : 300,name : 'report',fieldLabel:'报表模板'},{xtype:'textfield',width : 300,name : 'remark',fieldLabel:'备注'}"
     formString="{xtype: 'fieldset',  
                    defaults: {bodyStyle:'padding:40px'},                   
                    layout: {type : 'table',columns : 2},
                   items: [
                  {xtype:'textfield',width : 300,name : 'id',fieldLabel:'ID(Allow null)'},{xtype:'systemcombo'},
                  {xtype:'textfield',width : 300,name : 'name',fieldLabel:'报表名称'},
				  {xtype:'textfield',width : 300,name : 'report',fieldLabel:'报表模板'},
				  {xtype:'idNameComBox',name: 'needDateKey',hiddenName:'needDateKey',selectData:dateKeyData,fieldLabel:'是否需要日期框'},
 		     	  {xtype:'idNameComBox',name: 'dateKeyType',hiddenName:'dateKeyType',selectData:dateKeyTypeData,fieldLabel:'日期框类型'},
       			{xtype:'treepicker',fieldLabel:'父报表菜单',name:'parent',hideHeaders : true,width : 300,store:reportTreeStore(0),
				columns : [{xtype : 'treecolumn',width : 280,dataIndex : 'name'} ],displayField:'name'},
				{xtype:'numberfield',width : 300,value:0,name : 'sortID',fieldLabel:'排序号'},
		     	{xtype:'idNameComBox',name: 'valid',hiddenName:'valid',selectData:statusData,fieldLabel:'报表状态'},			 
			    {xtype:'idNameComBox',name: 'oldVersion',hiddenName:'oldVersion',selectData:versionData,fieldLabel:'报表版本'},			 
				{xtype:'textfield',width : 300,name : 'remark',fieldLabel:'备注'},				
			    {xtype:'idNameComBox',name: 'parameterModule',hiddenName:'parameterModule',selectData:paraData,fieldLabel:'参数模板类型'},			 
			    {xtype:'idNameComBox',name: 'exportType',hiddenName:'exportType',selectData:exportTypeDate,fieldLabel:'报表版本'}]},			 
				{xtype: 'fieldset',                 
                   title: '选择框',  
                   defaultType:'checkbox',
                    defaults: {
                        bodyStyle:'padding:40px'
                   },                   
                   layoutConfig:{columns: 5},   
                   layout: {
                     type : 'table',
                     columns : 5
                },
                   items: [
                      {boxLabel: '线别选择框', name: 'needLine' , inputValue:'1'},
                      {boxLabel: '车系选择框', name: 'needSeries' , inputValue:'1'},
                      {boxLabel: '车型选择框', name: 'needModel' , inputValue:'1'},
                      {boxLabel: '车间选择框', name: 'needWorkshop' , inputValue:'1'},
                      {boxLabel: '颜色选择框', name: 'needColor' , inputValue:'1'},
                      {boxLabel: '管制点选择框', name: 'needControlPoint' , inputValue:'1'},
                      {boxLabel: '部门选择框', name: 'needDepartment' , inputValue:'1'},  
                      {boxLabel: '时间选择框', name: 'needTimekey' , inputValue:'1'},
                      {boxLabel: '是否需要LOG', name: 'needLog' , inputValue:'1'}                 
                   ]                   
                },				
				{xtype:'textfield',width : 700,name : 'otherDatekeyMode',fieldLabel:'自定义日期样式'},
				{xtype:'textfield',width : 700,name : 'timekeyMode',fieldLabel:'自定义时间样式'},
				{xtype:'textfield',width : 700,name : 'customKey',fieldLabel:'自定义筛选框'},
				{xtype:'textarea',height:30,width : 700,name : 'javascript',fieldLabel:'自定义脚本'},
				{xtype:'textarea',height:30,width : 700,name : 'submitScript',fieldLabel:'提交脚本'}"
			customButtonImpl="
			function openReports(){
			   execute(0);
			}
			function closeReports(){
			   execute(1);
			}
           function execute(oper){
	          var str='关闭';
	          if(oper==0) str='打开';
	          Ext.MessageBox.confirm('信息','请确认'+str+'所有记录',function(btnId){
                 if(btnId == 'yes'){
			       var msgTip = Ext.MessageBox.show({
                     title:'提示',
                     width : 250,
                     msg:'正在'+str+'报表系统请稍候......'
                    });			   
			    Ext.Ajax.request({
				url : 'reportModuleAction.do?action=save&oper='+oper,
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.success){
					    store.load();
						Ext.Msg.alert('提示',str+'报表成功'+result.message);
					}else{
						Ext.Msg.alert('提示',str+'报表失败,'+result.message);
					}
				},
				failure : function(response,options){
					msgTip.hide();
					Ext.Msg.alert('提示',str+'报表失败');
				}
			}); 
			}});
	   }"/>
</html>
	<script>
			var typeData=[
				{id:'0',name:'否'},
				{id:'1',name:'是'}];
			var paraData=[
				{id:'0',name:'参数嵌入到报表'},
				{id:'1',name:'参数和报表分离'}];
			var statusData=[
				{id:'0',name:'打开'},
				{id:'1',name:'关闭'}];
			var dateKeyTypeData=[
				{id:'0',name:'正常日期型 eg:200901'},
				{id:'1',name:'日期范围型 eg:200901-200903'}];
			var	versionData=[
			    {id:'0',name:'新版本(4.0)'},
				{id:'1',name:'旧版本(3.5)'}
			];
			var	exportTypeDate=[
			    {id:'0',name:'输出HTML'},
				{id:'1',name:'输出Excel'},
				{id:'2',name:'输出PDF'},
				{id:'3',name:'输出WORD'},
				{id:'4',name:'输出TXT'}
			];
			
			
			var dateKeyData=[
			    {id:'0',name:'无'},
			    {id:'1',name:'Y-m-d'},
			    {id:'2',name:'Y-m'},
			    {id:'3',name:'Y'},
			    {id:'4',name:'Y-W'},
			    {id:'5',name:'Y-Q'},
			    {id:'6',name:'Y-m-d H'},
			    {id:'7',name:'Y-m-d H:i'},
			    {id:'8',name:'Y-m-d H:i:s'},
			    {id:'9',name:'Ym'},
			    {id:'10',name:'Ymd'}
			    ];
		var reportTreeStore = function(id) {
				var me = this;
				return Ext.create('Ext.data.TreeStore', {
					model : 'ModuleTree',
					defaultRootId : id,
					nodeParam : 'id',
					root : {
						id : 0,
						text : '信息化系统',
						expanded : true
					},
					proxy : {
						type : 'ajax',
						url : 'reportModuleAction.do?action=cancel&root=' + id,
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					},
					folderSort : true
				});
			}

	
	</script>




