<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="XML服务" action="xmlServicesAction.do"
     gridBody="id:Service ID,module:模块名,name:服务名,link:服务地址,moduleID,parameter:参数,remark:备注"
     searchFieldsString="moduleID,name,link,paramter,remark"
     rightString="145689"
     needRightCheck="Y"
     customButton="{text : '接口测试',iconCls:'COBTEST',handler:testService}"
     customRight="Y"
     pageSize="30"
     formString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'name',fieldLabel:'服务名'},
				  {xtype:'textfield',width : 200,name : 'link',fieldLabel:'服务地址'},
				  {xtype:'textfield',width : 200,name : 'parameter',fieldLabel:'参数'},
				  {xtype:'textfield',width : 200,name : 'remark',fieldLabel:'备注'}
				 "
	customButtonImpl="	   function testService(){
	        var recs = grid.getSelectionModel().getSelections();
		   var num=recs.length;
		   if(num > 1){
				Ext.MessageBox.alert('提示','每次只能测试一条记录。');
			}else if(num ==0){
			    Ext.MessageBox.alert('提示','请选择要测试的记录。');
			}else{
			var inputArgment;
			Ext.MessageBox.prompt('参数', '请输入调用参数，多参数使用&隔开:', function(btn, text){
            if (btn == 'ok'){
               inputArgment=text; 
            }else{
               return;
            }
           });
           
			
			var msgTip = Ext.MessageBox.show({
                  title:'提示',
                  width : 250,
                  msg:'正在测试该服务,请稍后......'
               });			   
			   var selectID=recs[0].get('id');	
			    Ext.Ajax.request({
				url : 'xmlServicesAction.do?action=custom',
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
	      var moduleData=[
				     <logic:present  name="module_list">
                        <logic:iterate id="module_item" name="module_list">
					{id:'<bean:write name="module_item" property="id"/>',name:'<bean:write name="module_item" property="name"/>'},
					   </logic:iterate>
                   </logic:present>
				{id:'',name:''}];
	</script>




