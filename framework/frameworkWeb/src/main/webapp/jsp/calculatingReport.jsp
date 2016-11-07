<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="正在运行的报表" action="calculatingReportAction.do"
     gridBody="id:报表ID,name:报表名,createTime:报表生产时间,lastAccessTime:报表最后访问时间,isCalculating:正在使用" 
     searchFieldsString="id"
     pageSize="0"
     rightString="3569"
     renameDelete="剔除报表计算"
     formString="{xtype:'textfield',width : 200,name : 'message',fieldLabel:'message'}	"
     
    />
</html>
	



