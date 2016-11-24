<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>	
	<controls:grid recordLabel="订单" action="tempOrderController/"
		gridBody="id:ID,name:名称,address:地址,tel:电话,amount:数量,createdate:下单时间,color:颜色,size:尺码,remark:备注"
		searchFieldsString="name" needRightCheck="Y" 
		 needUpdateAfterAdd="Y" 
		  rightString="142356"
     needUpdate="Y"
       formWidth="960"
		formHeight="600" searchFormHeight="300"
		 extContent="
			function showurl(value, p, record) {
		   return toLink(value,Ext.String.format(
　　　　　　　　　'<img src={0} width=45 height=45/>',value))}
			
			"
		formString=" " />
</html>




