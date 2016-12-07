<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
  <head>
          <title></title>     
          <link rel="stylesheet" type="text/css" href="css/ext/ext-all.css"/>       
          <script type="text/javascript" src="js/ext/bootstrap.js"></script>        
          <script type="text/javascript">      
          Ext.QuickTips.init();
Ext.require([
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.tree.*'
]);


Ext.onReady(function() {
 
  Ext.define('Task', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'id',     type: 'int'},
            {name: 'name',     type: 'string'},
            {name: 'link',     type: 'string'},
            {name: 'frame', type: 'string'},
            {name: 'sortID', type: 'int'}
        ]
    });
  		var store = function(id) {
			var me = this;
			return Ext.create('Ext.data.TreeStore', {
				model : 'Task',
				defaultRootId : id,
				nodeParam : 'id',
				root : {
					id : 0,
					text : '江海苑信息化系统',
					expanded : true
				},
				proxy : {
					type : 'ajax',
					url : 'menuAction.do?action=list&root=' + id,
					method : 'POST',
					reader : {
						type : 'json'
						,totalProperty : "results"
						,root : "items"
					}
				},
				folderSort : true
			});
		}

    var tree = Ext.create('Ext.tree.TreePanel', {
        title: 'TEST',
        width: 1000,
        height: 800,
        renderTo: 'tree-example',
        collapsible: true,
        useArrows: true,
        rootVisible: false,
        store: store,
        multiSelect: true,
        singleExpand: false,
        tbar : [
			{text : '添加',iconCls:'add',scope:this,handler:function(){showAdd();}},
			{text : '修改',iconCls:'option',handler:function(){showModify();}},
			{text : '删除',iconCls:'delete',handler:function(){showDelete();}}
		],
        expanded:false,
        //the 'columns' property is now 'headers'
        columns: [{
            xtype: 'treecolumn', 
            text: 'name',
            width: 100,
            sortable: true,
            dataIndex: 'name'
        },{
            text: 'link',
            flex: 1,
            sortable: true,
            dataIndex: 'link',
            align: 'left'

        },{
            text: 'frame',
            width: 200,
            dataIndex: 'frame',
            sortable: true,
            align: 'left'
        },{
            text: 'sortID',
            width: 100,
            dataIndex: 'sortID',
            sortable: true
        }],
        listeners : {'itemdblclick': function(view, record, element, index){
            var nodeid = record.get('id');
            event.stopEvent();
            if(nodeid>0){
               showModify(); 
           }
        }}  
    });
 
 		Ext.form.Field.prototype.msgTarget = 'side';//统一指定错误信息提示方式
	    var theForm = new Ext.create('Ext.form.Panel', {
			labelSeparator : "：",
			frame:true,
			border:false,
			items : [
			   {xtype:'hidden',
					name : 'id',
					fieldLabel:'id'
				},{xtype:'textfield',
					width : 300,
					name : 'name',
					allowBlank : false,
					fieldLabel:'系统功能'
				},{xtype:'textfield',
					width : 300,
					name : 'link',
					fieldLabel:'链接'
				},{xtype:'textfield',
					width : 300,
					name : 'frame',
					fieldLabel:'客户端Frame'
				}
				,{xtype:'numberfield',
					width : 300,
				    allowBlank : false,
				    blankText:'0',
					name : 'sortID',
					fieldLabel:'排序号'
				},{xtype:'combo',width : 300,blankText : '必须选择是否单模',hiddenName : 'singleMode',
					name: 'singleModeName',					
				    store:new Ext.data.JsonStore({data:statusData,fields:['id','name']}),
					editable: false,
					mode: 'local',    
					triggerAction: 'all',
					displayField:'name',
					valueField : 'id',
					emptyText :'是否单模',
					fieldLabel:'是否单模'				   
				}
				,{xtype:'hidden',
					name : 'moduleID'
				},{xtype:'hidden',
					name : 'parentModule'
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
		var win = Ext.create('Ext.window.Window', {
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
	
    	function showAdd(){
    		selectedItem = tree.getSelectionModel().getSelection();
    		alert(selectedItem.length);
		   if(selectedItem.length!=1){
				Ext.MessageBox.alert("提示","请选择父菜单的信息");
			}else {
			   var selectedId=selectedItem[0].get('id');
			  // alert("selectedId="+	selectedId);		  
			   theForm.form.reset();
			   theForm.wType = 1;
			   win.setTitle("新增系统功能信息");
			   win.show(); 
			}			
		}

		function showModify(){
		    selectedItem = tree.getSelectionModel().getLastSelected();
		  if(selectedItem.get('id')<0) return;
		   if(!selectedItem){
				Ext.MessageBox.alert("提示","请选择要修改的信息");
			}else{		   
			   theForm.wType=3;
			   win.setTitle("修改系统/模块信息");			   
			   loadForm(selectedItem.id);
			   win.show();  		 
			}
			
		}
		function loadForm(rootId){
			theForm.form.load({
				waitMsg : '正在加载数据请稍后',
				waitTitle : '提示',//标题
				url : 'menuAction.do?action=cancel',
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
    	function showDelete(){
	       selectedItem = tree.getSelectionModel().getLastSelected( );
	      
	       if(selectedItem.id<0) return;
		   if(!selectedItem){
				Ext.MessageBox.alert("提示","请选择要功能菜单的信息");
			}else{	
			Ext.MessageBox.confirm("提示","您确定要删除所选功能菜单吗？",function(btnId){
				if(btnId == 'yes'){
					deleteMenu(selectedItem.id);
				}
			})
		}
		}
		function deleteMenu(menuId){
			var msgTip = Ext.MessageBox.show({
				title:'提示',
				width : 250,
				msg:'正在删除功能菜单请稍后......'
			});
			Ext.Ajax.request({
				url : 'menuAction.do?action=delete',
				params : {Ids : menuId},
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.success){
						selectedItem.remove();
						Ext.Msg.alert('提示','删除功能菜单信息成功。');
					}else{
						Ext.Msg.alert('提示','删除功能菜单信息失败！');
					}
				},
				failure : function(response,options){
					msgTip.hide();
					Ext.Msg.alert('提示','删除功能菜单信息请求失败！');
				}
			});
		}
		//提交表单数据
		function submitForm(){		    
			if(theForm.wType==1){
			   var id=selectedItem.id;
			   if(id<=0){
			     //document.getElementById('moduleID').value=id;
			     theForm.form.getField('moduleId').setValue(id);
			   }else{
			    // document.getElementById('parentModule').value=id; 
			     theForm.form.getField('parentModule').setValue(id);
			     var moduleID=selectedItem.id;
			     var theItem=selectedItem;
			     while(moduleID>0){
			        theItem=theItem.parentNode;
			        moduleID=theItem.id;
			     }
			     theForm.form.getField('moduleID').setValue(moduleID);
			   //  document.getElementById('moduleID').value=moduleID;
			   }	   
				//新增线别信息
				theForm.form.submit({
					clientValidation:true,//进行客户端验证
					waitMsg : '正在提交数据请稍后',//提示信息
					waitTitle : '提示',//标题
					url : 'menuAction.do?action=add',//请求的url地址
					method:'POST',//请求方式
					success:function(form,action){//加载成功的处理函数
						win.hide();
						updateRoleList(action.result.id,1);
						Ext.Msg.alert('提示','新增系统功能成功');
					},
					failure:function(form,action){//加载失败的处理函数
						Ext.Msg.alert('提示','新增系统功能失败');
					}
				});
			}else if(theForm.wType==3){//修改
				theForm.form.submit({
					clientValidation:true,//进行客户端验证
					waitMsg : '正在提交数据请稍后',//提示信息
					waitTitle : '提示',//标题
					url : 'menuAction.do?action=update',//请求的url地址
					method:'POST',//请求方式
					success:function(form,action){
						win.hide();
						updateRoleList(action.result.id,2);
						Ext.Msg.alert('提示','修改系统功能成功');
					},
					failure:function(form,action){
						Ext.Msg.alert('提示','修改系统功能失败');
					}
				});
			}
		}
		//明细数据修改后，同步更新线别列表信息
		function updateRoleList(id,type){
			var fields = getFormFieldsObj(id);
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
               selectedItem.setText(fields.name);
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
			var recs = tTree.getSelectionModel().getSelections();
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
</head> 
 <body> 
 <div  id='tree-example'  style='width:100%; height:100%;'></div> 
 <script>
		var statusData=[
				{id:'0',name:'否(默认)'},
				{id:'1',name:'是'}];
</script>
 </body> 
  </html> 