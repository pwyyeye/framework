<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="定时任务执行结果" action="taskLogAction.do"
     gridBody="module:模块名,taskName:任务名称,taskID,runResultStr:运行结果,beginDate:开始运行时间,endDate:结束运行时间,moduleID,runResult,runContent:结果说明" 
     searchFieldsString="moduleID,taskID,beginDate,endDate,runResult"
     pageSize="30"
     needRightCheck="Y"
     rightString="5"
     searchFormString="{xtype:'systemcombo'},
                     {xtype:'combo',width : 200,allowBlank : true,blankText : '',hiddenName : 'runResult',
					name: 'runResultStr',					
				    store:new Ext.data.JsonStore({data:typeData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择运行结果',
					fieldLabel:'运行结果'				   
				  },{xtype:'datefield',format :'Y-m-d H:i:s ',emptyText :'请选择',width : 200,name : 'beginDate',fieldLabel:'开始运行时间'},
				  {xtype:'datefield',format :'Y-m-d H:i:s ',emptyText :'请选择',width : 200,name : 'endDate',fieldLabel:'结束运行时间'}
				  "
     formString="{xtype:'textfield',width : 200,name : 'name',fieldLabel:'任务名称'}"/>
</html>
	<script>

			var typeData=[
				{id:'0',name:'进行中'},
				{id:'1',name:'运行成功'},
				{id:'-1',name:'运行失败'}];	
	</script>




