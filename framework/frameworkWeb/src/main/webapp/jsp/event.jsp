<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
    <controls:grid recordLabel="系统事件" action="eventAction.do"
     gridBody="id:id,module:模块名,name:事件名称,moduleID,typeName:触发类型,type"  
     searchFieldsString="moduleID,name,type"
     addForInitial="Y"
     formString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'name',fieldLabel:'事件名称'},
                {xtype:'idNameComBox',width:300,name: 'type',hiddenName:'type',selectData:typeData,fieldLabel:'触发类型'},
	"/>
</html>
	<script>
			var typeData=[
				{id:'0',name:'用户触发'},
				{id:'1',name:'定时触发'}];
				
	</script>




