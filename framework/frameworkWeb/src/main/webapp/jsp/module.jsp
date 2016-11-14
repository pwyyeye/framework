<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="head.jsp"%>
	<html:base />
	<script>
	Ext.onReady(function(){   
		//创建工具栏组件
		var selectedItem;	
		var toolbar = new Ext.Toolbar([
			{text : '添加',iconCls:'add',handler:showAddRole},
			{text : '修改',iconCls:'option',handler:showModifyRole}
		]);

		//创建Grid表格组件
		var cb = new Ext.grid.CheckboxSelectionModel()
       var store = new Ext.ux.maximgb.treegrid.AdjacencyListStore({
			autoLoad :true,
		    reader : new Ext.data.JsonReader({
			    totalProperty: "results",
			    root: "items",
			    id: "id"}, 
			    ['id','name','indexPage','rowid','messageID','parentModule'
			    ]
			 ),			
			proxy : new Ext.data.HttpProxy({
				url : 'moduleController/list&root='+id,
				baseParams : {'root' : '0'},
				method : 'POST'
			})
		});
     var grid = new Ext.ux.maximgb.treegrid.GridPanel({
      store: store,
      tbar : toolbar,
      master_column_id : 'name',
      expandNode: function(rc){
        alert (rc.id);
      } ,
      columns: [
		{id:'name',header: "模块名称",  sortable: true, dataIndex: 'name'},
        {header: "indexPage",  sortable: true, renderer: 'indexPage', dataIndex: 'indexPage'},
        {header: "系统信息定制ID",  sortable: true,  dataIndex: 'messageID'}],
        stripeRows: true,
        autoExpandColumn: 'name',
        loadMask: true,
        viewConfig : {
				autoFill : true,
				enableRowBody:true,
                sortAscText:'升序',
                sortDescText:'降序',
                columnsText:'列定义',
                getRowClass : function(record, rowIndex, p, store){
                   return 'x-grid3-row-collapsed';
               }
	  },
      title: '系统/模块维护',
      root_title: '模块名称',
      el:'grid-div'
    });	
    grid.render();


 
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';//统一指定错误信息提示方式
		var theForm = new Ext.FormPanel({
			labelSeparator : "：",
			frame:true,
			border:false,
			items : [
			   {xtype:'hidden',
					name : 'id',
					fieldLabel:'id'
				},{xtype:'textfield',
					width : 200,
					name : 'name',
					fieldLabel:'模块名称'
				},{xtype:'textfield',
					width : 200,
					name : 'indexPage',
					fieldLabel:'首页'
				},{xtype:'hidden',
					name : 'parentID',
					fieldLabel:'parentID'
				}
			],
			buttons:[
				{text : '关闭',
					handler : function(){
						win.hide();
					}
				},{
					text : '提交',
					handler : submitForm
				}
			]
		});
		//创建弹出窗口
		var win = new Ext.Window({
			layout:'fit',
		    width:380,
		    closeAction:'hide',
		    height:240,
			resizable : false,
			shadow : true,
			modal :true,
		    closable:true,
		    bodyStyle:'padding:5 5 5 5',
		    animCollapse:true,
			items:[theForm]
		});
	
    	function showAddRole(){
    		selectedItem = colTree.getSelectionModel().getSelectedNode();
    		alert(selectedItem.id);
		   if(!selectedItem){
				Ext.MessageBox.alert("提示","只能选择一条父系统信息。");
			}else {	   
			   theForm.form.reset();
			   theForm.wType = 1;
			   win.setTitle("新增角色信息");
			   win.show(); 
			}			
		}

		function showModifyRole(){
		  selectedItem = colTree.getSelectionModel().getSelectedNode();
		   if(!selectedItem){
				Ext.MessageBox.alert("提示","每次只能修改一条用户信息。");
			}else{		   
			   theForm.wType=3;
			   win.setTitle("修改用户信息");			   
			   loadForm(selectedItem.id);
			   win.show();  		 
			}
			
		}
		function loadForm(rootId){
			theForm.form.load({
				waitMsg : '正在加载数据请稍后',
				waitTitle : '提示',//标题
				url : 'moduleAction.do?action=cancel',
				params : {root:rootId},
				method:'GET',//请求方式
				success:function(form,action){//加载成功的处理函数
					//Ext.Msg.alert('提示','数据加载成功');
				},
				failure:function(form,action){//加载失败的处理函数
					Ext.Msg.alert('提示','数据加载失败');
				}
			});
		}
  
		function showDeleteRole(){
			var roleList = getRoleIdList();
			var num = roleList.length;
			if(num == 0){
				return;
			}
			Ext.MessageBox.confirm("提示","您确定要删除所选角色吗？",function(btnId){
				if(btnId == 'yes'){
					deleteRole(roleList);
				}
			})
		}

		function submitForm(){
		    document.getElementById('parentID').value=selectedItem.id;
			if(theForm.wType==1){
				//新增线别信息
				theForm.form.submit({
					clientValidation:true,//进行客户端验证
					waitMsg : '正在提交数据请稍后',//提示信息
					waitTitle : '提示',//标题
					url : 'moduleAction.do?action=add',//请求的url地址
					method:'POST',//请求方式
					success:function(form,action){//加载成功的处理函数
						win.hide();
						updateRoleList(action.result.roleID,1);
						Ext.Msg.alert('提示','新增角色成功');
					},
					failure:function(form,action){//加载失败的处理函数
						Ext.Msg.alert('提示','新增角色失败');
					}
				});
			}else if(theForm.wType==3){//修改
				theForm.form.submit({
					clientValidation:true,//进行客户端验证
					waitMsg : '正在提交数据请稍后',//提示信息
					waitTitle : '提示',//标题
					url : 'moduleAction.do?action=update',//请求的url地址
					method:'POST',//请求方式
					success:function(form,action){
						win.hide();
						updateRoleList(action.result.roleID,2);
						Ext.Msg.alert('提示','修改角色成功');
					},
					failure:function(form,action){
						Ext.Msg.alert('提示','修改角色失败');
					}
				});
			}
		}
		//明细数据修改后，同步更新线别列表信息
		function updateRoleList(roleID,type){
			var fields = getFormFieldsObj(roleID);
            if(type==1){
            var newNode = new Ext.tree.TreeNode({ 
             text:fields.name,
             id:fields.id,
             menu_url: fields.id, 
             leaf: false, 
            uiProvider: Ext.tree.ColumnNodeUI 
            }); 
            selectedItem.leaf= false; 
            selectedItem.appendChild(newNode);
            }else{
              selectedItem.text=fields.name; 
            } 
		}

		//取得表单数据
		function getFormFieldsObj(roleID){
			var fields =theForm.items;
			var obj = {};
			for(var i = 0 ; i < fields.length ; i++){
				var item = 	fields.itemAt(i);
				var value = item.getValue();
				if(item.getXType() == 'combo'){
					var index = item.store.find('id',value);
					if(index != -1){
						var selItem = item.store.getAt(index);
					}
					value = selItem.get('name');
				}
					
				obj[item.name] = value;
			}
			if(Ext.isEmpty(obj['id'])){
				obj['id'] = roleID;
			}
			return obj;
		}
		//取得所选角色
		function getRoleIdList(){
			var recs = colTree.getSelectionModel().getSelections();
			var list = [];
			if(recs.length == 0){
				Ext.MessageBox.alert('提示','请选择要进行操作的角色！');
			}else{
				for(var i = 0 ; i < recs.length ; i++){
					var rec = recs[i];
					list.push(rec.get('id'))
				}
			}
			return list;
		}       
   });
    
</script>
	<body>
		<div id='grid-div' style="width:100%; height:100%;" />
	</body>
</html>




