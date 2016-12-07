<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<controls:grid recordLabel="功能" action="menuController/"
		gridBody="name:功能,link:链接地址,frame:客户端,id,sortID:排序号,singleMode:单模"
		searchFieldsString="name" needRightCheck="Y" needUpdateAfterAdd="Y"
		customRight="Y" rightString="1349" formHeight="450" formWidth="400"
		treeGrid="Y" searchFormHeight="300"
		formString="{xtype:'textfield',width : 300,name : 'name',fieldLabel:'功能',allowBlank:false},
		{xtype:'textfield',width : 300,name : 'link',fieldLabel:'链接地址',allowBlank:true},
		{xtype:'textfield',width : 300,name : 'frame',fieldLabel:'客户端',allowBlank:true},
		{xtype:'numberfield',width : 300,name : 'sortID',fieldLabel:'排序号',allowBlank:false},
		{xtype: 'checkboxgroup',fieldLabel: '是否单模',columns: 3,vertical: true,
          items: [{ boxLabel: '单模', name: 'singleMode', inputValue: '1' ,checked:false}]
        },{xtype:'hidden',name : 'moduleID'}
		" 
		submitContent="var selectedItem = grid.getSelectionModel().getSelection(); id = selectedItem[0].id;
					if (id <= 0) {
						form.form.findField('moduleID').setValue(id);
					} else {
						var moduleID = selectedItem[0].id;
						var theItem = selectedItem[0];
						while (moduleID > 0) {
							theItem = theItem.parentNode;
							moduleID = theItem.id;
						}
						  form.form.findField('moduleID').setValue(moduleID);
					}"/>

</html>





