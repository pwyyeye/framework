<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
    <controls:grid recordLabel="角色权限" action="menuRoleController/"
     gridBody="module:模块名,menu:菜单名,role:角色名,rightCode,otherRightCode:rightCode,moduleID,roleID,menuID,listRight,addRight,updateRight,deleteRight,exportRight,listRightStr:查询权限,addRightStr:新增权限,updateRightStr:修改权限,deleteRightStr:删除权限,deleteAllRightStr:删除全部权限,exportRightStr:导出权限,importRightStr:导入权限,customRight,customRightStr:自定义功能权限"
     searchFieldsString="moduleID,roleID,menuID"
     pageSize="30"

    extContent="Ext.define('MenuTree', {extend : 'Ext.data.TreeModel',idProperty : 'id',
				fields : [ {sortable : true,name : 'id',type : 'string'}, {name : 'text',type : 'string'},
				           {name : 'href',type : 'string'}, {name : 'leaf',	type : 'boolean'} ]
			});
			var roleStore=Ext.create('Ext.data.Store', {
					model : 'idNameModel',
					defaultRootId : id,
					nodeParam : 'id',
					autoLoad : true,
					proxy : {
						type : 'ajax',
						url : 'roleController/list',
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					}})
			var treeStore = function(id) {
				var me = this;
				return Ext.create('Ext.data.TreeStore', {
					model : 'MenuTree',
					defaultRootId : id,
					nodeParam : 'id',
					root : {
						id : 0,
						text : '信息化系统',
						expanded : true
					},
					proxy : {
						type : 'ajax',
						url : 'menuAction.do?action=custom&root=' + id,
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					},
					folderSort : true
				});
			}"
     formString="{xtype:'systemcombo'},{xtype:'treepicker',fieldLabel:'菜单',name:'menuID',hideHeaders : true,width : 300,store:treeStore(0),columns : [ {
					xtype : 'treecolumn',width : 280,dataIndex : 'text'} ],displayField:'text'},
					  {xtype:'idNameComBox',width : 300,store:roleStore,name : 'roleID',fieldLabel:'角色'},{xtype: 'fieldset',                 
                   title: '权限列表',  
                   defaultType:'checkbox',
                    defaults: {
                        bodyStyle:'padding:20px'
                   },                   
                   layoutConfig:{columns: 6},   
                   layout: {
                     type : 'table',
                     columns : 6
                   },
                   items: [
                      {boxLabel: '查询', name: 'listRight' ,inputValue:'5' ,checked:true},
                      {boxLabel: '新增', name: 'addRight' ,inputValue:'1',checked:true},
                      {boxLabel: '修改', name: 'updateRight' ,inputValue:'4',checked:true},
                      {boxLabel: '删除', name: 'deleteRight' ,inputValue:'3',checked:true},
                       {boxLabel: '删除全部', name: 'deleteAllRight' ,inputValue:'0',checked:true},
                      {boxLabel: '导出', name: 'exportRight' ,inputValue:'6',checked:true},
                      {boxLabel: '导入', name: 'importRight' ,inputValue:'8',checked:true},
                      {boxLabel: '自定义', name: 'customRight' ,inputValue:'9',checked:true},
                      {xtype:'textfield',width : 50,name:'otherRightCode',colspan:4}
                   ]                   
                }"
				/>
</html>
	<script>

	
			var statusData=[
				{id:'0',name:'是'},
				{id:'1',name:'否'}];
			var roleStore = new Ext.data.Store({
		     autoLoad :false,                 
             reader : new Ext.data.JsonReader({
			    totalProperty: 'results',
			    root: 'items'}, 
			    ['id','name']
			 ),	
			proxy: new Ext.data.HttpProxy({
				url : 'roleAController/list'
			})
		  });  		
				


	</script>




