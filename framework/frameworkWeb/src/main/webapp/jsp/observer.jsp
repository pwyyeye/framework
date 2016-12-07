<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	 <script src="js/GetUser.js" language="javascript"></script>
		<script>
		
	      var moduleData=[
				     <logic:present  name="module_list">
                        <logic:iterate id="module_item" name="module_list">
					{id:'<bean:write name="module_item" property="id"/>',name:'<bean:write name="module_item" property="name"/>'},
					   </logic:iterate>
                   </logic:present>
				{id:'',name:''}];
			var routeData=[
				{id:'0',name:'APP推送'},
				{id:'1',name:'EMail'},
				{id:'2',name:'手机短信'}];
			var childStore =Ext.create('Ext.data.Store',{
		     autoLoad :false,    
		     model:'idNameModel',
 				proxy: {
					type : 'ajax',
					method : 'POST',
					url : 'eventAction.do?action=list',
					reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
					}
			}}); 

	</script>
	<html:base />
    <controls:grid recordLabel="定制信息" action="observerController/"
     gridBody="module:模块名,moduleID,event:事件名称,empID:工号,empName:姓名,routeStr:提醒方式,beginDate:生效时间,endDate:失效时间" 
     searchFieldsString="moduleID,eventID,empID,route,beginDate,endDate"
     userFiledsString="empID"
     formString="{xtype:'systemcombo'},{xtype:'idNameComBox',
 					hiddenName : 'eventID',
					name : 'eventID',
	                width:300,
				    store:childStore,
					fieldLabel:'事件'
				},{xtype:'textfield',width : 300,name : 'empID',fieldLabel:'工号' },
				{xtype:'idNameComBox',width : 300,
				   hiddenName : 'route',
					name: 'route',					
				    selectData:routeData,
					fieldLabel:'提醒方式'				   
				},{xtype:'datefield',allowBlank:false,format :'Y-m-d',emptyText :'请选择',width : 300,name : 'beginDate',fieldLabel:'生效日期'}
				,{xtype:'datefield',allowBlank:false,format :'Y-m-d',emptyText :'请选择', width : 300,name : 'endDate',fieldLabel:'失效日期'}"/>
</html>





