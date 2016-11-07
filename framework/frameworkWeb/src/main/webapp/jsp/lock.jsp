<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>

	<%@ include file="common.jsp"%>
	  <script>
    var messageField;
   <logic:present name="ADMIN_RIGHT">
       messageField=Ext.create('Ext.form.TextField',{width : 200,name : 'message',fieldLabel:'message'});
   </logic:present>
     <logic:notPresent name="ADMIN_RIGHT">
       messageField=Ext.create('Ext.form.TextField',{width : 200,name : 'message',fieldLabel:'message',readOnly:true});
   </logic:notPresent>
   </script>
	<html:base />
    <controls:grid recordLabel="功能锁" action="lockAction.do"
     gridBody="id:菜单ID,menuName:菜单名,empID:工号,empName:姓名,ip:IP地址,lockDate:锁定时间" 
     searchFieldsString="empId"
     customRight="Y"
     pageSize="0"
     rightString="3569"
     renameDelete="剔除锁"
     formString="messageField"
     
    />
</html>
	



