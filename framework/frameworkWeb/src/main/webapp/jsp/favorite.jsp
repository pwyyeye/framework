<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
    <controls:grid recordLabel="功能收藏" action="favoriteController/"
     gridBody="empID:工号,menuID:菜单ID,name:菜单名,createDate:产生时间"
     searchFieldsString="empId"
     showAllRecord="N"
     pageSize="0"
     rightString="3569"
     renameDelete="删除"
     formString="{xtype:'textfield',width : 200,name : 'message',fieldLabel:'message'}"     
    />
</html>
	



