<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
    <controls:grid recordLabel="报表角色权限" action="raBindingAction.do"
     gridBody="module:模块名,report:报表名,role:角色名,rightCode,otherRightCode:rightCode,moduleID,roleID,reportID,listRight,addRight,updateRight,deleteRight,exportRight,listRightStr:查询权限,addRightStr:新增权限,updateRightStr:修改权限,deleteRightStr:删除权限,exportRightStr:导出权限,importRightStr:导入权限,customRight,customRightStr:自定义功能权限"
     searchFieldsString="moduleID,roleID,reprotID"
     pageSize="30"
     formString="{xtype:'systemcombo'},{xtype:'treepicker',fieldLabel:'父报表菜单',name:'reportID',hideHeaders : true,width : 300,store:reportTreeStore(0),
				columns : [{xtype : 'treecolumn',width : 280,dataIndex : 'name'} ],displayField:'name'},
	,{xtype:'combo',
					width : 200,
					allowBlank : false,
				    forceSelection: true,
					blankText : '必须选择角色',
					hiddenName : 'roleID',
					name : 'role',
				    store:roleStore,
					editable : false,
					displayField:'name',
					valueField : 'id',
					emptyText :'请选择角色',
					triggerAction: 'all',
					fieldLabel:'角色'
				},{xtype: 'fieldset',                 
                   title: '权限列表',  
                   defaultType:'checkbox',
                    defaults: {
                        bodyStyle:'padding:20px'
                   },                   
                   layoutConfig:{columns: 7},   
                   layout:'table',
                   items: [
                      {boxLabel: '查询', name: 'listRight' ,inputValue:'5' ,checked:true},
                      {boxLabel: '新增', name: 'addRight' ,inputValue:'1',checked:true},
                      {boxLabel: '修改', name: 'updateRight' ,inputValue:'4',checked:true},
                      {boxLabel: '删除', name: 'deleteRight' ,inputValue:'3',checked:true},
                       {boxLabel: '删除全部', name: 'deleteAllRight' ,inputValue:'0',checked:true},
                      {boxLabel: '导出', name: 'exportRight' ,inputValue:'6',checked:true},
                      {boxLabel: '导入', name: 'importRight' ,inputValue:'8',checked:true},
                      {boxLabel: '自定义', name: 'customRight' ,inputValue:'9',checked:true},
                      {xtype:'textfield',width : 100,name:'otherRightCode',fieldLabel:'其它',colspan:3}
                   ]                   
                }"
				/>
</html>
	<script>
		var reportTreeStore = function(id) {
				var me = this;
				return Ext.create('Ext.data.TreeStore', {
					model : 'ModuleTree',
					defaultRootId : id,
					nodeParam : 'id',
					root : {
						id : 0,
						text : '信息化系统',
						expanded : true
					},
					proxy : {
						type : 'ajax',
						url : 'reportModuleAction.do?action=cancel&root=' + id,
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					},
					folderSort : true
				});
			}
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
				url : 'roleAction.do?action=list'
			})
		  }); 	
				


	</script>




