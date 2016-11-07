<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="进行中的报表" action="runningReportAction.do"
     gridBody="id:报表ID号,report:报表名" 
     searchFieldsString="id"
     customRight="N"
     pageSize="0"
     rightString="356"
     renameDelete="结束报表"
     formString="{xtype:'textfield',width : 200,name : 'report',fieldLabel:'message'}	"
     
    />
</html>
	