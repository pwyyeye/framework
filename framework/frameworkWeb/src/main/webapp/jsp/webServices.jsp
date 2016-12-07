<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="WEB服务" action="webServicesAction.do"
     gridBody="id:Service ID,module:模块名,name:服务名,url:服务地址,moduleID,method:方法,remark:说明,argments:服务,soapActionUrl:端口,argmentsType:参数类型,useSoap,returnType:返回类型,argmentsRemark:参数说明,returnRemark:返回值说明,useSoapStr:是否使用SOAP,log,logStr:是否LOG,logType,logTypeStr:log类型,valid,validStr:是否有效"
     searchFieldsString="moduleID,name,method,remark"
     rightString="145689"
     needRightCheck="Y"
     customButton="{text : '接口测试',iconCls:'COBTEST',handler:testService}"
     customRight="Y"
     pageSize="30"
     formHeight="450"
     searchFormHeight="300"
     searchFormString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'name',fieldLabel:'服务名'},
				  {xtype:'textfield',width : 200,name : 'link',fieldLabel:'服务地址'},
				  {xtype:'textfield',width : 200,name : 'method',fieldLabel:'方法'},
				  {xtype:'textfield',width : 200,name : 'remark',fieldLabel:'备注'}
				 "
	formString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'name',fieldLabel:'服务名'},
				  {xtype:'textfield',width : 200,name : 'url',fieldLabel:'服务地址'},
				  {xtype:'textfield',width : 200,name : 'method',fieldLabel:'方法'},
				  {xtype:'textfield',width : 200,name : 'remark',fieldLabel:'说明'},
				  {xtype:'textfield',width : 200,name : 'argments',fieldLabel:'服务'},
				   {xtype:'textfield',width : 200,name : 'soapActionUrl',fieldLabel:'端口'},
				  {xtype:'textfield',width : 200,name : 'argmentsType',fieldLabel:'参数类型'},				 
				  {xtype:'textfield',width : 200,name : 'returnType',fieldLabel:'返回值类型'},
				  {xtype:'textfield',width : 200,name : 'argmentsRemark',fieldLabel:'参数说明'},
				  {xtype:'textfield',width : 200,name : 'returnRemark',fieldLabel:'返回值说明'},
				  {xtype:'combo',width : 200,blankText : '必须选择',hiddenName : 'useSoap',
					name: 'useSoapStr',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择',
					fieldLabel:'是否使用SOAP'				   
				  },{xtype:'combo',width : 200,blankText : '必须选择',hiddenName : 'log',
					name: 'logStr',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择',
					fieldLabel:'是否记录'				   
				  },{xtype:'combo',width : 200,blankText : '必须选择',hiddenName : 'logType',
					name: 'logTypeStr',					
				    store:new Ext.data.JsonStore({data:logTypeData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择',
					fieldLabel:'LOG记录类型'				   
				  },{xtype:'combo',width : 200,blankText : '必须选择',hiddenName : 'valid',
					name: 'validStr',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'选择',
					fieldLabel:'是否有效'				   
				  }
				 "			 
				 
	customButtonImpl="	  
	    var paraform;
	    function testService(){
	        var recs = grid.getSelectionModel().getSelection();
	        
		   var num=recs.length;
		   if(num > 1){
				Ext.MessageBox.alert('提示','每次只能测试一条记录。');
			}else if(num ==0){
			    Ext.MessageBox.alert('提示','请选择要测试的记录。');
			}else{
			var selectID=recs[0].get('id');
          Ext.Ajax.request({
                async: true,
                url:'webServicesAction.do?action=cancel',   
                method:'post', 
                params:{id: selectID},
                success:function(response,opts){
                var _items = Ext.util.JSON.decode(response.responseText);
                paraform = new Ext.form.FormPanel( {
                     labelSeparator : '：',
                     border : false,
                     frame : true,
                     items : [{xtype:'hidden',width : 200,name : 'id',value:selectID}],
                     buttons:[{text : '测试', handler : invokeService}]
                 });
                var totalCount=_items.results;
                for(var i=0;i!=totalCount;i++){
                    var fieldname =_items.items[i];
                    var textField=new Ext.form.TextField({
                       width:'200',name:'paramField'+i,fieldLabel:fieldname
                    });
                   paraform.add(textField);
                 }
                 var win3 = new Ext.Window({
                 layout:'fit',
                 width:400,
                 closeAction:'hide',
                 height:300,
                 resizable : false,
                 shadow : true,
                 modal :true,
                 closable:true,
                 bodyStyle:'padding:5 5 5 5',
                 animCollapse:true,
                 items:[paraform]
                });
                 win3.show();
                 },
                 failure:function(response,opts){
                    var sf=response.responseText;
                    Ext.Msg.alert('提示',sf);
                    return;
                 }}) 
 			}
	   }
	   function invokeService(){
	    
	   	var msgTip = Ext.MessageBox.show({
           title:'提示',
           width : 250,
            msg:'正在测试该服务,请稍后......'
        });		
         var fields =paraform.items;
	     var selectID=fields.itemAt(0).getValue();
	     var parameters='';    
         for(var i = 1 ; i < fields.length ; i++){
            parameters=parameters+fields.itemAt(i).getValue()+';';
	     }	   
			    Ext.Ajax.request({
			    async: true,
				url : 'webServicesAction.do?action=custom',
				method : 'post',
				params:{sid: selectID,argments : parameters},			
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.success){
						Ext.Msg.alert('提示','服务测试成功,结果\n:'+result.message);
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
	   "			
			/>
</html>
	<script>
		var statusData=[
				{id:'0',name:'是'},
				{id:'1',name:'否'}];
		var logTypeData=[
				{id:'2',name:'失败自动redo'},
				{id:'3',name:'失败自动不redo'}];
	</script>




