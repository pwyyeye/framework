<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
    <controls:grid recordLabel="系统配置" action="propertyController/"
     gridBody="id,name:配置项,value:值,moduleID,remark:说明,defaultValue:默认值,leaf,parType,parTypeStr:类型,setUser:修改用户,setDate:修改日期,timeLimit:时间限制,startDate:生效日期,endDate:结束日期"
     searchFieldsString="moduleID,name,value,remark"
     needRightCheck="Y"
     customRight="Y"
     rightString="145689"
     customButton="{text : '配置在线更新',iconCls:'resetP',handler:resetProperties},{text : '读当前值',iconCls:'readP',handler:getValue}"
     formHeight="450"
     formWidth="400"
     treeGrid="Y"
     searchFormHeight="300"
	formString="{xtype:'systemcombo'},{xtype:'textfield',width : 300,id:'id',name : 'id',fieldLabel:'配置项',allowBlank:false},
				  {xtype:'textfield',width : 300,name : 'value',fieldLabel:'值',allowBlank:false},
				  {xtype:'textfield',width : 300,name : 'remark',fieldLabel:'说明',allowBlank:false},
				  {xtype:'textfield',width : 300,name : 'defaultValue',fieldLabel:'默认值'},
				  {xtype:'idNameComBox',width : 300,selectData:parTypeData,name : 'parType',fieldLabel:'类型'},
				   {xtype:'textfield',width : 300,name : 'timeLimit',fieldLabel:'是否有时间限制'},
				  {xtype:'datefield',width : 300,name : 'startDate',fieldLabel:'生效日期'},
				  {xtype:'datefield',width : 300,name : 'endDate',fieldLabel:'失效日期'},
				  {xtype:'textfield',width : 300,name : 'parent',fieldLabel:'目录'}"

	customButtonImpl="	        	
			function getValue(){
	            var recs = grid.getSelectionModel().getSelection();
		        var num=recs.length;
		        if(num > 1){
				    Ext.MessageBox.alert('提示','每次只能读取一条记录。');
			     }else if(num ==0){
			        Ext.MessageBox.alert('提示','请选择要读取的记录。');
			     }else{
			        var selectID=recs[0].get('name');
                     Ext.Ajax.request({
				         url : 'propertyController/importExcel',
				             params : {name: selectID},
				             method : 'POST',
				             success : function(response,options){
					              var result = Ext.util.JSON.decode(response.responseText);
					               if(result.success){
						               Ext.Msg.alert('提示','变量读取成功,结果:'+result.message);
					                }else{
						               Ext.Msg.alert('提示','变量读取失败,结果:'+result.message);
					                 }
				              },
				                failure : function(response,options){
					                      msgTip.hide();
					                      Ext.Msg.alert('提示','变量读取失败,该变量不存在');
				                }});              
                              }}
	
           function resetProperties(){
	          Ext.MessageBox.confirm('信息','请确认更新系统的配置值',function(btnId){
                 if(btnId == 'yes'){
			       var msgTip = Ext.MessageBox.show({
                     title:'提示',
                     width : 250,
                     msg:'正在更新系统的配置值,请稍候......'
                    });			   
			    Ext.Ajax.request({
				url : 'propertyController/custom',
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.success){
					    store.load();
						Ext.Msg.alert('提示','更新系统配置值成功');
					}else{
						Ext.Msg.alert('提示','更新系统配置值失败');
					}
				},
				failure : function(response,options){
					msgTip.hide();
					Ext.Msg.alert('提示','更新系统配置值失败');
				}
			}); 
			}});
	   }"
			/>

</html>
	<script>
			var parTypeData=[
				{id:'0',name:'配置值'},
				{id:'2',name:'目录'}];
	</script>





