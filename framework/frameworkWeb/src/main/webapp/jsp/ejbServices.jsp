<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="EJB/T3服务" action="ejbServicesAction.do"
     gridBody="id:Service ID,module:模块名,name:服务名,method:服务方法,moduleID,argmentsType:参数类型,outputType:方法返回类型,argmentsRemark:参数说明,outputRemark:输出说明,remark:备注"
     searchFieldsString="moduleID,name,method,remark"
     rightString="145689"
     needRightCheck="Y"
     customButton="{text : '方法测试',iconCls:'COBTEST',handler:testService}"
     customRight="Y"
     pageSize="30"
     formString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'name',fieldLabel:'服务名'},
				  {xtype:'textfield',width : 200,name : 'method',fieldLabel:'服务方法'},
				  {xtype:'textfield',width : 200,name : 'argmentsType',fieldLabel:'参数类型'},
				  {xtype:'textfield',width : 200,name : 'outputType',fieldLabel:'方法返回类型'},
				  {xtype:'textfield',width : 200,name : 'argmentsRemark',fieldLabel:'参数说明'},
				  {xtype:'textfield',width : 200,name : 'outputRemark',fieldLabel:'输出说明'},
				  {xtype:'textfield',width : 200,name : 'remark',fieldLabel:'备注'}
				 "
	searchFormString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'name',fieldLabel:'服务名'},
				  {xtype:'textfield',width : 200,name : 'method',fieldLabel:'服务方法'},
				  {xtype:'textfield',width : 200,name : 'remark',fieldLabel:'备注'}"			 
	customButtonImpl=" function testService(){
	                      var recs = grid.getSelectionModel().getSelection();
		                  var num=recs.length;
		                  if(num > 1){
				             Ext.MessageBox.alert('提示','每次只能测试一条记录。');
			              }else if(num ==0){
			                 Ext.MessageBox.alert('提示','请选择要测试的记录。');
			              }else{
			                 var selectID=recs[0].get('id');
                             Ext.Msg.prompt('提示','请输入测试参数',function callback(id,msg){
                                 var msgTip = Ext.MessageBox.show({
                                      title:'提示',
                                      width : 250,
                                      msg:'正在测试接口，请稍后......'
                             });
                             Ext.Ajax.request({
				                  url : 'ejbServicesAction.do?action=custom',
				                  params : {id : selectID,arg:msg},
				                  method : 'POST',
				                  success : function(response,options){
					                           msgTip.hide();
					                          var result = Ext.util.JSON.decode(response.responseText);
					                          if(result.success){
						                         Ext.Msg.alert('提示','服务测试成功,结果:'+result.message);
					                          }else{
						                         Ext.Msg.alert('提示','服务测试失败,结果:'+result.message);
					                          }
				                 },
				                failure : function(response,options){
					                      msgTip.hide();
					                      Ext.Msg.alert('提示','服务测试失败,该服务程序不存在');
				                }});              
                                },this,false);}}			

	   "			
				/>
</html>
	




