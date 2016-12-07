<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	 <script src="Ext\ux\form\ueditor\ueditor.config.js" language="javascript"></script>
	 <script src="Ext\ux\form\ueditor\ueditor.all.min.js" charset="utf-8" language="javascript"></script>
	 <script src="Ext\ux\form\UEditorExtPlugin.js" charset="utf-8" language="javascript"></script>	
	
	<controls:grid recordLabel="产品" action="medaigouController/"
		gridBody="id:ID,barname:酒吧名称,bartype,bartypename:酒吧类型"
		searchFieldsString="barname" needRightCheck="Y" pageSize="30"
		 needUpdateAfterAdd="Y" fileField="imageUrl" 
		  rightString="142356"
     needUpdate="Y"
       formWidth="960"
		formHeight="600" searchFormHeight="300"
		 extContent="
			function showurl(value, p, record) {
		   return toLink(value,Ext.String.format(
　　　　　　　　　'<img src={0} width=45 height=45/>',value))}
			
			"
		formString="
	 			{
				    id: 'articleContent',
				    xtype: 'ueditor',
				    name: 'content',
				    fieldLabel: '内容',
				    width: 900,
				    height: 359,
				    listeners: {
				        'change': function () {
				            me.isChanged = true;
				        }
				    }
				}
				   " />
</html>




