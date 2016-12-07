<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
	<controls:grid recordLabel="报表调度器" action="reportScheduleAction.do"
		gridBody="id:ID,module:模块名,reportModule:报表名称,parameter:报表参数,circleTypeStr:定时类型,firstExcuteDate:首次执行时间,nextExcuteDate:下次执行时间,recipients:收件人,recipientsEmpID,recipientsEmpName:收件人(工号),recipientsDpno,recipientsDeptName:收件人(部门),subject:邮件主题,content:邮件内容,reportName:邮件附件名称,recipientsSourcesStr:收件人来源,recipientsImplementMethod:参数收件人实现类,remark:备注,validName:调度器状态,lastExcuteResultStr:上次执行结果,lastExcuteResultRemark:结果说明,lastExcuteDate:上次执行时间,recipientsSources,circleType,reportModuleID,lastExcuteResult,valid,moduleID"
		searchFieldsString="moduleID,reportModuleID,reportName,remark,valid,lastExcuteResult"
		userFiledsString="recipientsEmpID,recipientsDpno"
		searchFormString="{xtype:'systemcombo'},{xtype:'treepicker',fieldLabel:'选择报表',name:'reportID',hideHeaders : true,width : 300,store:reportTreeStore(0),
				columns : [{xtype : 'treecolumn',width : 280,dataIndex : 'name'} ],displayField:'name'},{xtype:'textfield',width : 300,name : 'reportName',fieldLabel:'邮件附件'},
				  {xtype:'textfield',width : 300,name : 'remark',fieldLabel:'备注'}"
		formString="{xtype:'systemcombo'},{xtype:'treepicker',fieldLabel:'父报表菜单',name:'reportID',hideHeaders : true,width : 300,store:reportTreeStore(0),
				columns : [{xtype : 'treecolumn',width : 280,dataIndex : 'name'} ],displayField:'name'},{xtype:'textfield',width : 300,name : 'parameter',fieldLabel:'报表参数'},
				 {xtype:'idNameComBox',width : 300,selectData:circleTypeData,name : 'circleType',fieldLabel:'类型'}, 
				{xtype:'datefield',allowBlank:false,format :'Y-m-d H:i:s ',emptyText :'请选择',width : 300,name : 'firstExcuteDate',fieldLabel:'首次执行时间'},
     			{xtype:'datefield',allowBlank:false,format :'Y-m-d H:i:s ',emptyText :'请选择',width : 300,name : 'nextExcuteDate',fieldLabel:'下次执行时间'},
    			{xtype:'idNameComBox',width : 300,selectData:recipientsTypeData,name : 'valid',fieldLabel:'类型'},
    			{xtype:'textfield',width : 300,name : 'recipients',fieldLabel:'收件人'},
				{xtype:'textfield',width : 300,name : 'recipientsEmpID',fieldLabel:'员工工号',
					listeners:{focus  : function(recipientsEmpID){    
                      GetSelUser();    
                    }}
                  },
                  {xtype:'textfield',width : 300,name : 'recipientsDpno',fieldLabel:'部门',
					listeners:{focus  : function(recipientsDpno){    
                       GetSelDept();      
                    }}
                  } , 
                  {xtype:'textfield',width : 300,name : 'subject',fieldLabel:'邮件主题'},   
				{xtype:'textfield',width : 300,name : 'content',fieldLabel:'邮件内容'},
				{xtype:'textfield',width : 300,name : 'reportName',fieldLabel:'邮件附件名称'},
				{xtype:'idNameComBox',width : 300,selectData:recipientsSourcesData,name : 'recipientsSources',fieldLabel:'类型'}, 
		
				{xtype:'textfield',width : 300,name : 'recipientsImplementMethod',fieldLabel:'参数收件人实现类'},
				{xtype:'textfield',width : 300,name : 'remark',fieldLabel:'备注'},
				{xtype:'idNameComBox',width : 300,selectData:statusData,name : 'valid',fieldLabel:'类型'}
			
			"
		formWidth="400" formHeight="600" searchFormWidth="400"
		searchFormHeight="400" pageSize="30" customRight="Y"
		customButton="{text : '手动执行',iconCls:'COBTEST',handler:customExcute}"
		customButtonImpl="
	
			
           function customExcute(oper){
	          var idList = getIdList();
              var num = idList.length;
              if(num == 0){return;}
              Ext.MessageBox.confirm('提示','您确定手动执行该报表调度器？',function(btnId){
                   if(btnId == 'yes')excuteRecord(idList);
              })
           }
          function excuteRecord(idList){
             var Ids = idList.join('-');
             var msgTip = Ext.MessageBox.show({
                 title:'提示',width : 250,msg:'正在手动执行报表调度器，请稍后......'
             });
             Ext.Ajax.request({
                url : 'reportScheduleAction.do?action=save',
                params : {Ids : Ids},method : 'POST',
                success : function(response,action){
                   msgTip.hide();
                   var result = Ext.util.JSON.decode(response.responseText);
                   if(result.success){
                      Ext.Msg.alert('提示','手动执行报表调度器成功。');
                   }else{
                      if(typeof(action.result.message) != 'undefined'){
                         Ext.Msg.alert('提示','手动执行报表调度器失败:'+action.result.message);
                      }else{
                         Ext.Msg.alert('提示','手动执行报表调度器失败！');
                      }
                   }},
                failure : function(response,action){
                   msgTip.hide();
                   if(typeof(action.result.message) != 'undefined'){
                     Ext.Msg.alert('提示','删除所有车辆注记维护记录失败:'+action.result.message);
                   }else{
                     Ext.Msg.alert('提示','删除车辆注记维护信息请求失败！');
                   }
                }
              });
	   }" />
</html>
<script>
		
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
			
			
	     
			var circleTypeData=[
				{id:'0',name:'单次有效'},
				{id:'1',name:'每小时'},
				{id:'2',name:'每天'},
				{id:'3',name:'每周'},
				{id:'4',name:'每月'},
				{id:'5',name:'每年'}];
			var recipientsTypeData=[
				{id:'0',name:'电子邮箱'},
				{id:'1',name:'员工工号'},
				{id:'2',name:'部门编号'}];
			var statusData=[
				{id:'0',name:'有效'},
				{id:'1',name:'无效'}];
			var recipientsSourcesData=[
				{id:'0',name:'来自配置'},
				{id:'1',name:'来自接口'}];
	


	
	</script>




