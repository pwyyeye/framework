<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="Background任务管理" action="jmsTaskAction.do"
     gridBody="id:ID,module:模块名,queueName:队列名,messageID:messageID,moduleID,message:message,status,statusName:状态,empName:所有者,empID,createDateStr:产生日期,dealResult:处理结果,dealDateStr:处理日期"
     searchFieldsString="moduleID,empID,message,queueName,status,messageID,dealResult,createStartDate,createEndDate,dealStartDate,dealEndDate"
     formHeight="500"
     userFiledsString="empID"
     pageSize="30"
     rightString="5"
     renameAdd="新增任务"
     renameUpdate="修改任务"
     renameDelete="删除任务"
	formString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'empID',fieldLabel:'所有者',
				   listeners:{focus : function(empID){    
                      GetSelUser(1);     
                    }} },
				{xtype:'textfield',width : 200,name : 'queueName',fieldLabel:'队列名'},
				{xtype:'textfield',width : 200,name : 'message',fieldLabel:'message'},
				{xtype:'textfield',width : 200,name : 'messageID',fieldLabel:'messageID'},
				  {xtype:'combo',width : 200,blankText : '必须选择状态',hiddenName : 'status',
					name: 'statusName',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择状态',
					fieldLabel:'状态'				   
				},
				 {xtype:'textfield',width : 200,name : 'dealResult',fieldLabel:'处理结果'}
				,{xtype:'datefield',format :'Y-m-d',width : 200,name : 'createStartDate',fieldLabel:'产生起始时间'}
				,{xtype:'datefield',format :'Y-m-d',width : 200,name : 'createEndDate',fieldLabel:'至结束时间'}
				,{xtype:'datefield',format :'Y-m-d',width : 200,name : 'dealStartDate',fieldLabel:'处理起始时间'}
				,{xtype:'datefield',format :'Y-m-d',width : 200,name : 'dealEndDate',fieldLabel:'至结束时间'}"			
	
      />
</html>
	<script>
	      var moduleData=[
				     <logic:present  name="module_list">
                        <logic:iterate id="module_item" name="module_list">
					{id:'<bean:write name="module_item" property="id"/>',name:'<bean:write name="module_item" property="name"/>'},
					   </logic:iterate>
                   </logic:present>
				{id:'',name:''}];
				var statusData=[
				{id:'0',name:'待执行'},
				{id:'1',name:'执行成功'},
				{id:'-1',name:'执行失败'}];
	</script>




