<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
	<controls:grid recordLabel="webservices Log" action="webServicesLogAction.do"
	    gridBody="id:ID,module:模块名,webservices:webservices,webservicesID,parameter:参数,resultName:处理结果,result,moduleID,remark:说明,invokeDate:处理日期,createDate:生成日期" 
	    searchFieldsString="moduleID,remark,invokeDate,webservicesID,result" 
	    rightString="5689"
	    customButton="{text : '重新处理',iconCls:'RedoWebservices',handler:redoWebservices}"
        customRight="Y"
		needRightCheck="Y" pageSize="30"
		formString="{xtype:'systemcombo'},{xtype:'combo',
					width : 200,
					allowBlank : false,
				    forceSelection: true,
					blankText : '选择webservices',
					hiddenName : 'webservicesID',
					name : 'webservices',
				    store:webservicesStore,
					editable : false,
					displayField:'name',
					valueField : 'id',
					emptyText :'请选择角色',
					triggerAction: 'all',
					fieldLabel:'角色'
				},
				  {xtype:'textfield',width : 200,name : 'parameter',fieldLabel:'参数'},				  
				  {xtype:'textfield',width : 200,name : 'remark',fieldLabel:'备注'},
				  {xtype:'combo',width : 200,blankText : '必须选择',hiddenName : 'result',
					name: 'resultStr',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择',
					fieldLabel:'执行结果'				   
				  }
				 " 
		customButtonImpl="	   function redoWebservices(){
	        var recs = grid.getSelectionModel().getSelection();
		   var num=recs.length;
	
			var idList = getIdList();
            var num = idList.length;
             if(num == 0){Ext.MessageBox.alert('提示','请选择要Redo的记录。');return;}
            var Ids = idList.join('-');
			var msgTip = Ext.MessageBox.show({
                  title:'提示',
                  width : 250,
                  msg:'正在执行,请稍后......'
               });			   
			    Ext.Ajax.request({
				url : 'webServicesLogAction.do?action=custom',
				params : {Ids : Ids},
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.success){
						Ext.Msg.alert('提示','Redo执行成功,结果:'+result.message);
					}else{
						Ext.Msg.alert('提示','Redo执行失败');
					}
				},
				failure : function(response,options){
					msgTip.hide();
					Ext.Msg.alert('提示','Redo执行失败');
				}
			}); 
			refresh();
			//}
	   }"		 
				 />
<script>	
 var webservicesStore = new Ext.data.Store({
		     autoLoad :false,                 
             reader : new Ext.data.JsonReader({
			    totalProperty: 'results',
			    root: 'items'}, 
			    ['id','name']
			 ),	
			proxy: new Ext.data.HttpProxy({
				url : 'webServicesAction.do?action=list'
			})
		});
			var statusData=[
				{id:'1',name:'成功'},
				{id:'2',name:'失败'}];
</script>
</html>





