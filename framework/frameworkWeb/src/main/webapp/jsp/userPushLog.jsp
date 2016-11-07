<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
	<controls:grid recordLabel="user push Log" action="userPushLogAction.do"
	    gridBody="id:ID,userId:用户,type:类型,result:结果,title:标题,content:内容,remark:说明,createDate:生成日期" 
	    searchFieldsString="userId,result" 
	    rightString="5689"
	    customButton="{text : '重新处理',iconCls:'redoPush',handler:redoPush}"
        customRight="Y"
		needRightCheck="Y" pageSize="30"
		formString="
				  {xtype:'textfield',width : 200,name : 'userId',fieldLabel:'用户名'},
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
		customButtonImpl="	   function redoPush(){
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
				url : 'userPushLogAction.do?action=custom',
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

			var statusData=[
				{id:'1',name:'成功'},
				{id:'2',name:'失败'}];
</script>
</html>





