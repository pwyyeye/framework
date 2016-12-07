<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="在线用户" action="onlineController/"
     gridBody="empId:工号,empName:姓名,ip:IP地址,loginDate:登录时间,onMethod:当前功能,moduleName:当前模块,rolename:当前角色,lastUpdateDate:最后更新时间,statusStr:状态" 
     searchFieldsString="empId"
     customRight="Y"
     pageSize="0"
     rightString="3569"
     renameDelete="剔除在线用户"
    customButton="{text : '发送信息',iconCls:'sendMessage',handler:sendMessage}
                 ,{text : '发布通知',iconCls:'publish',handler:publish}"
     formString="{xtype:'textfield',width : 200,name : 'message',fieldLabel:'message'}	"
	customButtonImpl="	
	    function publish(){
	       dealMessage('onlineController/custom');
	    } 
	    function sendMessage(){
	       var idList = getIdList();
           var num = idList.length;
           if(num == 0){return;}
           var Ids = idList.join('-');
           dealMessage('onlineController/update&Ids='+Ids);
	    } 
	    function dealMessage(url){
	       Ext.Msg.prompt('提示','请输入要发布的信息',function callback(id,msg){
	       if(msg!=''){
			   var msgTip = Ext.MessageBox.show({
                  title:'提示',
                  width : 250,
                  msg:'正在执行该通知发布,请稍候......'
               });			   
			    Ext.Ajax.request({
				url : url,
				params : {message:msg},
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.success){
						Ext.Msg.alert('提示','发送信息成功');
					}else{
						Ext.Msg.alert('提示','发送信息失败');
					}
				},
				failure : function(response,options){
					msgTip.hide();
					Ext.Msg.alert('提示','发送信息失败');
				}
			}); }        
         });
         }"	
				/>
</html>
	



