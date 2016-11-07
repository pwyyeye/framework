<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>

	<%@ include file="common.jsp"%>
	<script type="text/javascript" src="js/DateTimePicker.js"></script>
    <script type="text/javascript" src="js/Datetime2.js"></script>
	<html:base />
    <controls:grid recordLabel="定时任务" action="taskAction.do"
     gridBody="module:模块名,name:任务名称,method:方法,argments:参数,status,statusStr:状态,moduleID,type,typeStr:定时类型,runTime:定时时间,remark:说明,lastRunDate:最后运行时间,lastRunResult,lastRunResultStr:最后运行结果,lastRunRemark:最后运行说明" 
     searchFieldsString="moduleID,name,type,remark,status"
     formWidth="400"
     formHeight="400"
     searchFormWidth="400"
     searchFormHeight="300"
     customRight="Y"
     pageSize="30"
      customButton="{text : '手工执行',iconCls:'COBTEST',handler:excuteTask}"
     searchFormString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'name',fieldLabel:'任务名称'}, {xtype:'combo',width : 200,allowBlank : false,blankText : '必须选择类型',hiddenName : 'type',
					name: 'type',					
				    store:new Ext.data.JsonStore({data:typeData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择类型',
					fieldLabel:'类型'				   
				  },
				  {xtype:'textfield',width : 200,name : 'remark',fieldLabel:'说明'},
				  	{xtype:'combo',width : 200,allowBlank : false,blankText : '必须选择类型',hiddenName : 'status',
					name: 'status',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择状态',
					fieldLabel:'状态'				   
				}"
     formString="{xtype:'systemcombo'},{xtype:'textfield',width : 300,name : 'name',fieldLabel:'任务名称'},
				  {xtype:'combo',width : 300,allowBlank : false,blankText : '必须选择类型',hiddenName : 'type',
					name: 'type',					
				    store:new Ext.data.JsonStore({data:typeData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择类型',
					fieldLabel:'类型'				   
				},{xtype:'textfield',width : 300,name : 'method',fieldLabel:'接口方法'},
				{xtype:'textfield',width : 300,name : 'argments',fieldLabel:'参数类型'},			
				{xtype:'datetimefield',allowBlank:false,format :'Y-m-d H:i:s ',emptyText :'请选择',width : 300,name : 'runTime',fieldLabel:'运行时间'},
				{xtype:'combo',width : 300,allowBlank : false,blankText : '必须选择类型',hiddenName : 'status',
					name: 'status',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name', 
					valueField : 'id',
					emptyText :'选择状态',
					fieldLabel:'状态'		   
				},
				{xtype:'textfield',width : 300,name : 'remark',fieldLabel:'说明'}"
		customButtonImpl="function excuteTask(){
	       var recs = grid.getSelectionModel().getSelection();
		   var num=recs.length;
		   if(num > 1){
				Ext.MessageBox.alert('提示','每次只能测试一条记录。');
			}else if(num ==0){
			    Ext.MessageBox.alert('提示','请选择要测试的记录。');
			}else{
			   var msgTip = Ext.MessageBox.show({
                  title:'提示',
                  width : 250,
                  msg:'正在执行该后台任务,请稍候......'
               });			   
			   var selectID=recs[0].get('id');	
			    Ext.Ajax.request({
				url : 'taskAction.do?action=custom',
				params : {id : selectID},
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.success){
						Ext.Msg.alert('提示','服务测试成功,结果:'+result.message);
					}else{
						Ext.Msg.alert('提示','服务测试失败,该服务程序不存在');
					}
				},
				failure : function(response,options){
					msgTip.hide();
					Ext.Msg.alert('提示','服务测试失败,该服务程序不存在');
				}
			});        
			
           }
         }"	
				/>
</html>
	<script>
			var typeData=[
				{id:'0',name:'每天'},
				{id:'1',name:'每小时'},
				{id:'2',name:'每周'},
				{id:'3',name:'每月'}];	
			var statusData=[
				{id:'0',name:'正常'},
				{id:'-1',name:'已停止'}];
	</script>




