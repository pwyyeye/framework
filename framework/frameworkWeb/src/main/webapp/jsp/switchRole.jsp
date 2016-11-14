<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<script>

	Ext.onReady(function(){
			Ext.EventManager.on(window, 'beforeunload', function() {
	     parent.setFlag(true);
	})
		  Ext.define('roles',{
        extend: 'Ext.data.Model',
        idProperty: 'id',
        fields: [
            'id',
            'name',
            'description',
            'module',
            'moduleID',
            'organiseName'
        ]
    });
			var store = Ext.create('Ext.data.Store',{
			autoLoad :true,
			model: 'roles',
		    proxy :{
				type:'ajax',
				url : 'switchRoleController/list',
				method : 'POST',
				reader:{
					type:'json',
					totalProperty: "results",
					root: "items"
				}
			}
		});
		//创建Grid表格组件
		//var cb = new Ext.grid.CheckboxSelectionModel()
		var roleGrid = Ext.create('Ext.grid.GridPanel',{
		    title :'角色切换(双击切换角色)',
			renderTo : 'grid-div',
			frame:true,
			store: store,
			layout:'fit',
			stripeRows : true,
			height:document.documentElement.clientHeight,
			autoScroll : true,
		//	sm : cb,
			columns: [//配置表格列
			//	new Ext.grid.RowNumberer({
			//		header : 'No',
			//		width : 30
			//	}),//表格行号组件
			//	cb,
				{header:"id",width:30,dataIndex:'id',sortable:false},
				{header: "模块", width: 100, dataIndex: 'module', sortable: true},
				{header: "角色名称", width: 100, dataIndex: 'name', sortable: true},
				{header: "单位", width: 100, dataIndex: 'organiseName', sortable: true},
				{header: "描述", width: 100, dataIndex: 'description', sortable: true,flex : 1}
			],			
			viewConfig : {
			    columnsText: '显示的列',
				autoFill : true
			}
		})
		roleGrid.on("rowdblclick", function(roleGrid) {
			switchRole();
		});
		function switchRole(){
		   var rec =  roleGrid.getSelectionModel().getSelection();
		   Ext.Msg.confirm('确认', '请确认切换至\n模块:'+rec[0].get('module')+',角色:'+rec[0].get('name'), function(btn,text){
               if (btn == 'yes'){
                  parent.location.href='loginController/?switchRole=y&MODULE_ID='+rec[0].get('moduleID')+'&ROLE_ID='+rec[0].get('id');
                }
          });

		}
	});
</script>

	<body>
		<div id='grid-div' style="width:100%; height:100%;" />
	</body>
</html>




