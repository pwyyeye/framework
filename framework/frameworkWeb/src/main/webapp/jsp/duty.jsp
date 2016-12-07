<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base/>
    <controls:grid recordLabel="职务层级" action="dutyAction.do"
     gridBody="id:id,name:职务名称,level:层级,note:备注,isDirector:是否权责主管"
     searchFieldsString="name,level,note"
     needRightCheck="Y"
     pageSize="20"
     formHeight="300"
 	formString="{
				xtype : 'textfield',
				width : 300,
				name : 'name',
				fieldLabel : '名称'
			}, {
				xtype : 'numberfield',
				width : 300,
				name : 'level',
				id:'name',
				fieldLabel : '层级代码'
			}, {
				xtype : 'textfield',
				width : 300,
				name : 'isDirector',
				fieldLabel : '是否权责主管'
			}, {
				xtype : 'textarea',
				width : 300,
				name : 'tel',
				grow: true,
				fieldLabel : '备注'
			}"		 	
/>


</html>





