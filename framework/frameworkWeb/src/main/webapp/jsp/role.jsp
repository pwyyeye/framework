<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	 <script src="extjs\ux\form\htmlPlusEditor\kindeditor-all-min.js" language="javascript"></script>
	 <script src="extjs\ux\form\htmlPlusEditor\kindeditor-min.js" language="javascript"></script>
	 <script type="text/javascript">
	 Ext.Loader.setConfig( {	enabled : true});
     Ext.Loader.setPath('Ext.ux', 'extjs/ux');
     Ext.require(['Ext.ux.form.HtmlPlusEditor']);
	 </script>
	<html:base />
    <controls:grid recordLabel="角色" action="roleController/"
     gridBody="id:角色ID,module:模块,name:角色名称,description:描述,organise,organiseName:组织" 
     searchFieldsString="name,moduleID"
     formWidth="700"
     formHeight="400"
     pageSize="0"
     rightString="123456789"
     extContent="
     	var orStore=Ext.create('Ext.data.Store', {
					model : 'idNameModel',defaultRootId : id,	nodeParam : 'id',
					proxy : {
						type : 'ajax',
						url : 'departmentAction.do?action=expand',
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					}})"
     
    formString="{xtype:'systemcombo'}, {xtype:'organisecombo'}, 

    {xtype:'textfield',width : 600,name : 'name',fieldLabel:'角色名称'},
    {xtype:'htmlPlusEditor',width : 600,name : 'description',fieldLabel:'描述',id:'description'
   
                            }	"
     
    />
</html>




