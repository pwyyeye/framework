<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<title>用户管理</title>
	<script>
var searchname = '';
var searchremark = '';
var searchtype = '';
var recond = 20;
var currentId = 0;
Ext.onReady(function() {
	Ext.define('datas', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ 'id', 'name', 'loginId', 'code', 'sex', 'party', 'peoples',
				'nationality', 'nativePlace', 'wedLock', 'educateLevel',
				'archAddr', 'credentialType', 'credentialNo', 'tel', 'room',
				'addr', 'postcode', 'email', 'parentId', 'orderNo', 'mobile',
				'housetel', 'title', 'level', 'mark', 'status', 'registerDate',
				'logoutDate', 'timeType', 'engineer', 'birthday', 'departmentCode',
				'isDirector', 'IsAd', 'isOpen', 'remark','departmentName' ]
	});
	var detailForm=Ext.create('Ext.form.Panel',{
		    title : '&nbsp;&nbsp;&nbsp;组织概况',
			labelSeparator : '：',
			autoLoad : false,
			layout:'form',
			reader:Ext.create('Ext.data.reader.Json',{totalProperty: 'results',rootProperty: 'items',idProperty:'id'},
		    	['id','name','leadId','parentId','deptType','remark','abbr']
			),
			frame : false,
			border : true,
			listeners : {
                    activate : function(){
		    	       if(currentId>0 &detailForm.sType!=1){
		    	    	   detailForm.sType=2;
		    	    	   alert(currentId);
					       detailForm.form.load({url:'departmentController/cancel&id='+currentId});
					   }
                    }					
			},
			items : [ {
				xtype : 'hidden',
				width : 300,
				
				border : 0,
				name : 'id',
				fieldLabel : '部门ID'
			}, {
				xtype : 'textfield',
				width : 300,
				border : 0,
				name : 'name',
				fieldLabel : '部门名称'
			}, {
				xtype : 'textfield',
				width : 300,
				border : 0,
				name : 'abbr',
				fieldLabel : '部门简称'
			}, {
				xtype : 'textarea',
				width : 300,
				border : 0,
				name : 'remark',
				fieldLabel : '备注'
			}, {
				xtype : 'hidden',
				name : 'parentId'
			},],
			buttons : [  {
				text : '保存',
				name : 'detailSubmitButton',
				handler : submitDetailForm
			} ]
		});
	var tbPanel=Ext.create('Ext.tab.Panel',{
		region : 'center',
			border : false,
			xtype : 'tabpanel',
			activeTab : 0,	
			deferredRender: false,
			items : [ {
				title : '&nbsp;&nbsp;&nbsp;单位人员',
				html : '<div id="users_grid">'
			}, detailForm ]
	 })
	 		Ext.define('Task', {
			extend : 'Ext.data.TreeModel',
			idProperty : 'id',
			fields : [ {
				name : 'id',
				type : 'string'
			}, {
				name : 'name',
				type : 'string'
			}, {
				name : 'leaderId',
				type : 'string'
			}, {
				name : 'leaf',
				type : 'boolean'
			} ]
		});
		var treeStore = function(id) {
			var me = this;
			return Ext.create('Ext.data.TreeStore', {
				model : 'Task',
				defaultRootId : id,
				nodeParam : 'id',
				root : {
					id : 0,
					name : '组织架构',
					expanded : true
				},
				proxy : {
					type : 'ajax',
					url : 'departmentController/list?root=' + id,
					method : 'POST',
					reader : {
						type : 'json',
						totalProperty : "results",
						root : "items"
					}
				},

				folderSort : true
			});
		}
		var departStore=treeStore(0);
	    function refreshDepartment(){
	    	departStore.reload();
	    }
		var colTree = Ext.create('Ext.tree.Panel', {
			region : 'center',
			loadMask : true,
			rootVisible : false,
			hideHeaders : true,
			animate : true,
			 autoScroll: true, 
			
	        layout : 'absolute',
	         //border:false,
	        //height : document.documentElement.clientHeigh,
			store : departStore,
			columns : [ {
				xtype : 'treecolumn', //this is so we know which column will show the tree
				text : '组织',
				width : 200,
				sortable : true,
				dataIndex : 'name',
				locked : true
			} ]
		});
		var toolbar2 = Ext.create('Ext.toolbar.Toolbar', {
				items : [ {
					text : '新增部门',
					iconCls : 'addDepartment',
					handler : showAddDepartment,
					icon : 'images/expand-members.gif'
	},{
					text : '删除部门',
					iconCls : 'deleteDepartment',
					handler : deleteDepartment,
					icon : 'images/expand-members.gif'
	},{
					text : '刷新',
					handler : refreshDepartment,
	}]});
	 var centerTabPanel = Ext.create('Ext.panel.Panel', {
				// renderTo:'menu_panel',
				title : '组织架构',
				region : 'west',
				border : true,
				layout : 'border',
				//autoScroll : false,
				collapsible : false,
				animScroll : true,
				width : 203,
				bbar : toolbar2,
				defaults : {
					autoScroll : true
				},
				tabMargin : 0,
				items : [ colTree ]

			});
	 
	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		renderTo : 'grid-div',
		items : [ centerTabPanel, tbPanel ]
	});

	
	var store = Ext.create('Ext.data.Store', {
		autoLoad : false,
		model : 'datas',
		proxy : {
			type : 'ajax',
			url : 'usersController/list',
			method : 'POST',
			reader : {
				type : 'json',
				totalProperty : "results",
				root : "items"
			}
		}
	});
	//创建工具栏组件
		var toolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				text : '添加',
				iconCls : 'add',
				handler : showAdd
			}, {
				text : '修改',
				iconCls : 'option',
				handler : showModify
			}, {
				text : '删除',
				iconCls : 'remove',
				handler : showDelete
			}, {
				text : '显示全部',
				iconCls : 'showAll',
				handler : showAll,
				hideOnClick : true
			} , {
				text : '重置密码',
				iconCls : 'changePassword',
				handler : changePassword
			},'->',
			{xtype : 'textfield',
			name : 'filterName',
			id : 'userName'
		}, {
			xtype : 'button',
			text : '查询',
			handler : function() {
				var un = Ext.getCmp('userName').getValue();
				store.load( {
				params : {
					start : 0,
					limit : recond,
					action : 'list',
					searchValue : un,
				}
			});
				
		}} ]
		});

		var combo = Ext.create('Ext.form.ComboBox', {
			store : new Ext.data.SimpleStore( {
				fields : [ 'abbr', 'state' ],
				data : [ [ '10', '10' ], [ '20', '20' ], [ '30', '30' ],
						[ '40', '40' ], [ '50', '50' ], [ '0', '全部' ] ]
			}),
			valueField : 'abbr',
			displayField : 'state',
			mode : 'local',
			editable : true,
			triggerAction : 'all',
			width : 40,
			emptyText : recond,
			selectOnFocus : true
		});
		var cb = Ext.create('Ext.selection.CheckboxModel',{ checkOnly :false });
		   var pageBar=new Ext.PagingToolbar({
             store: store,
             dock : 'bottom',
             pageSize: recond,
             displayInfo: true,
             displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
             emptyMsg: '没有记录',
             items:['-',combo,'每页']
      })
		var grid = Ext.create('Ext.grid.Panel', {
			autoLoad : true,
			selModel: cb,
			renderTo : users_grid,
			frame : false,
			autoExpandColumn : 8,
			stripeRows : true,
			autoScroll : true,
			columnLines : false,
			height : document.documentElement.clientHeight - 25,
			tbar : toolbar,
			viewConfig : {
				columnsText : '显示的列',
				autoFill : true
			},
			columns : [
				
					new Ext.grid.RowNumberer( {
						header : 'No',
						width : 20
					}),
					{
						header : '部门',
						dataIndex : 'departmentName',
						renderer : formatQtip,
						sortable : true
					},
					{
						header : '工号',
						dataIndex : 'id',
						renderer : formatQtip,
						sortable : true
					},
					
					{
						header : '姓名',
						dataIndex : 'name',
						renderer : formatQtip,
						sortable : true
					},
					{
						header : '登录名',
						dataIndex : 'loginId',
						renderer : formatQtip,
						sortable : true
					},
					{
						header : '电话',
						dataIndex : 'tel',
						renderer : formatQtip,
						sortable : true
					},
					{
						header : 'Email',
						dataIndex : 'email',
						renderer : function(value) {
							return Ext.String.format(
									'<a href="mailto:{0}">{1}</a>', value,
									value);
						},
						sortable : true
					}, {
						header : '手机',
						dataIndex : 'mobile',
						renderer : formatQtip,
						sortable : true
					}, {
						header : '状态',
						dataIndex : 'status',
						renderer : formatQtip,
						sortable : true
					}, {
						header : '短讯',
						dataIndex : 'message',
						renderer : formatQtip,
						sortable : true
					} ],
			store : store,
			stripeRows : true,
			autoScroll : true,
			autoFill : true,
			//sm : cb,
			dockedItems :pageBar
		})
        grid.on('rowdblclick', function(grid) {showModify();});

		//var cb = new Ext.grid.CheckboxSelectionModel()
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'qtip';//统一指定错误信息提示方式
		function formatQtip(data, metadata) {
			var title = '';
			var tip = data;
			metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip
					+ '"';
			return data;
		}
		function getLink(value, rec, attachID) {
			var attachRealID = rec.get(attachID);
			return toLink('/portal/showAttachAction.do?FILE_ID=' + attachRealID
					+ '&FILENAME=' + value, value);
		}
		function toLink(hyperLink, value) {
			return "<a target='_blank' href='" + encodeURI(hyperLink) + "')>"
					+ value + "</a>";
		}
		var gridHeadField = Ext.create('Ext.form.Hidden', {
			name : 'excelHead'
		});
var sexStore = Ext.create('Ext.data.Store', {
    fields: ['id', 'name'],
    data : [
        {"id":"0", "name":"男"},
        {"id":"1", "name":"女"}
    ]
});
var timeTypeStore = Ext.create('Ext.data.Store', {
    fields: ['id', 'name'],
    data : [
        {"id":"0", "name":"总部时间类型"},
        {"id":"1", "name":"项目时间类型A"}       
    ]
});
var dutyStore=Ext.create('Ext.data.Store', {
					model : 'idNameModel',
					defaultRootId : id,
					//nodeParam : 'id',
				
					proxy : {
						type : 'ajax',
						url : 'dutyController/list',
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					}})

		var form = new Ext.create('Ext.form.Panel', {
			labelSeparator : '：',
			frame : true,
			id : 'semForm',
			border : false,

			items : [ {xtype: "container",layout: "hbox",items:[
				           {xtype : 'textfield',width : 200,name : 'loginId',allowBlank : false,fieldLabel : '登录名'}, 
			               {xtype : 'textfield',width : 200,name : 'name',allowBlank : false,fieldLabel : '姓名'}
			          ]},{xtype: "container",layout: "hbox",items:[
			        	   {xtype : 'textfield',width : 200,name : 'mobile',fieldLabel : '手机号码'}, 
			        	   {xtype : 'textfield',width : 200,name : 'tel',fieldLabel : '分机号'}
			          ]},
			               {xtype : 'textfield',width : 500,name : 'email',fieldLabel : 'email'}, 
                           {xtype: "textareafield",width : 500,name: "remark",fieldLabel: "备注",height: 50},
				           {xtype : 'hidden',name : 'id',fieldLabel : 'id'}, 
				           {xtype : 'hidden',name : 'department'},gridHeadField ],
			buttons : [ {
				text : '关闭',
				name : 'detailCloseButton',
				handler : function() {
					win.hide();
				}
			}, {
				text : '提交',
				name : 'detailSubmitButton',
				handler : submitForm
			} ]
		});
		var pswForm = new Ext.create('Ext.form.Panel', {
			labelSeparator : '：',
			frame : true,
			id : 'pwForm',
			border : false,
			items : [{xtype : 'textfield',width : 300,name : 'newpassword',fieldLabel : '输入密码',inputType: 'password',}, 
                     {xtype: "textfield",width : 500,name: "checkPassword",fieldLabel: "确认密码",inputType: 'password',},
				     {xtype : 'hidden',name : 'id',fieldLabel : 'id'}],
			buttons : [{
				text : '关闭',
				name : 'detailCloseButton',
				handler : function() {
					pswWin.hide();
				}
			}, {
				text : '提交',
				name : 'passwordButton',
				handler : submitPswForm
			} ]
		});
		var pswWin = Ext.create('Ext.window.Window', {
			layout : 'fit',
			width : 320,
			closeAction : 'hide',
			height : 240,
			resizable : false,
			shadow : true,
			modal : true,
			closable : true,
			bodyStyle : 'padding:5 5 5 5',
			animCollapse : true,
			items : [ pswForm ]
		});
		
		gridHeadField
				.setValue('id:工号,loginId:登录名,code:编号,sex:性别,peoples:社会关系,nativePlace:出生地,nationality:籍贯,remark:备注');
		var win = Ext.create('Ext.window.Window', {
			layout : 'fit',
			width : 600,
			closeAction : 'hide',
			height : 300,
			resizable : false,
			shadow : true,
			modal : true,
			closable : true,
			bodyStyle : 'padding:5 5 5 5',
			animCollapse : true,
			items : [ form ]
		});
		
	



        colTree.getSelectionModel().on('select', function(selModel, record) {  
            
            record.expand();  
           tbPanel.setActiveTab(0)
           currentId=record.getId();
            loadingPage(record.getId() );  
        });  
	
        function showAddDepartment(){
		     if(currentId==0){
		    	 Ext.MessageBox.alert('提示', '请选择部门母节点');
		     }else{
		    	 	detailForm.form.reset();
		    	 	//currentId=-1;
		    	 	detailForm.sType=1;
		    	 	tbPanel.setActiveTab(1);
			       // detailWin.setTitle('新增部门');
			       // detailWin.show();
		     }
        }

		//detailForm.render('department_grid');
		function loadingPage(id) {
			store.load( {
				params : {
					start : 0,
					'root' : id,
					limit : recond,
					action : 'list'
				}
			});
		}

		function PageSize(recond) {
			pageBar.pageSize = recond;
			store.pageSize = recond;
			this.recond = recond;
			store
					.load( {
						params : {
							start : 0,
	   					'name' : searchname,
							'remark' : searchremark,
							'type' : searchtype,
							limit : recond,
							action : 'list'
						}
					});
		}
		;
		combo.on('select', function(a, b, c) {
			PageSize(parseInt(a.getValue()));
		});
		store
				.load( {
					params : {
						first : 'Y',
						start : 0,
					'name' : searchname,
						'remark' : searchremark,
						'type' : searchtype,
						limit : recond,
						action : 'list'
					}
				});
		store
				.on(
						'beforeload',
						function(thiz, options) {
							Ext
									.apply(
											thiz.baseParams,
											{
												limit : recond,
												action : 'list',
												'name' : searchname,
												'remark' : searchremark,
												'type' : searchtype
											});
						});
		function showAdd() {
			if(currentId<=0 ){
				Ext.MessageBox.alert('提示', '请先从组织架构树选择部门');
			}else{
			form.form.reset();
			form.wType = 1;
			win.setTitle('新增用户信息');
			win.show();
			}
		}
	
		function refresh() {
			store.reload();
		}
		function showAll() {
			searchname = '';
			searchremark = '';
			searchtype = '';
			store.load( {
				params : {
					start : 0,
					limit : recond,
					action : 'list',
					root:currentId
				}
			});
		}
		function showSearch() {
			form.form.reset();
			form.wType = 2;
			win.setTitle('查询');
			win.show();
		}
		function changePassword(){
			var recs = grid.getSelectionModel().getSelection();
			var num = recs.length;
			if (num > 1) {
				Ext.MessageBox.alert('提示', '每次只能修改一条信息。');
			} else if (num == 0) {
				Ext.MessageBox.alert('提示', '请选择要修改的信息。');
			} else {
                var rec = recs[0];
				pswForm.getForm().loadRecord(rec);
				pswWin.show();			   
			}
		}
		
		function showModify() {
			win.show();
			win.hide();
			var recs = grid.getSelectionModel().getSelection();
			var num = recs.length;
			if (num > 1) {
				Ext.MessageBox.alert('提示', '每次只能修改一条用户信息。');
			} else if (num == 0) {
				Ext.MessageBox.alert('提示', '请选择要修改的用户信息。');
			} else {
				form.wType = 3;
				win.setTitle('修改用户信息');
				var rec = recs[0];
				form.getForm().loadRecord(rec);
				win.show();
			}
		}
		function removeAll() {
			Ext.MessageBox.confirm('信息', '请确认删除所有记录', function(btnId) {
				if (btnId == 'yes') {
					var idList = getAllIdList();
					var Ids = idList.join('-');
					var msgTip = Ext.MessageBox.show( {
						width : 250,
						msg : '正在删除全部记录，请稍候....'
					});
					Ext.Ajax.request( {
						url : 'usersController/delete',
						params : {
							Ids : Ids
						},
						method : 'POST',
						success : function(response, action) {
							msgTip.hide();
							var result = Ext.util.JSON
									.decode(response.responseText);
							if (result.success) {
								store.removeAll();
								Ext.Msg.alert('提示', '删除所有用户记录成功');
							} else {
								if (typeof (result.message) != 'undefined') {
									Ext.Msg.alert('提示',
											'删除所有用户记录失败:' + result.message);
								} else {
									Ext.Msg.alert('提示', '删除所有用户记录失败:');
								}
							}
						},
						failure : function(response, action) {
							msgTip.hide();
							if (typeof (result.message) != 'undefined') {
								Ext.Msg.alert('提示',
										'删除所有用户记录失败:' + result.message);
							} else {
								Ext.Msg.alert('提示', '删除所有用户记录失败:');
							}
						}
					});
				}
			})
		}
		function getAllIdList() {
			var list = [];
			var recs = store.getRange();
			for ( var i = 0; i < recs.length; i++) {
				var rec = recs[i];
				list.push(rec.get('id'))
			}
			return list;
		}
		function showDelete() {
			var idList = getIdList();
			var num = idList.length;
			if (num == 0) {
				return;
			}
			Ext.MessageBox.confirm('提示', '您确定要删除所选用户吗？', function(btnId) {
				if (btnId == 'yes')
					deleteRecord(idList);
			})
		}
		function deleteRecord(idList) {
			var Ids = idList.join('-');
			var msgTip = Ext.MessageBox.show( {
				title : '提示',
				width : 250,
				msg : '正在删除用户信息请稍后......'
			});
			Ext.Ajax
					.request( {
						url : 'usersController/delete',
						params : {
							Ids : Ids,
						},
						method : 'POST',
						success : function(response, action) {
							msgTip.hide();
							var result = Ext.util.JSON
									.decode(response.responseText);

							if (result.success) {
								for ( var i = 0; i < idList.length; i++) {
									var index = store.find('id', idList[i]);
									if (index != -1) {
										var rec = store.getAt(index)
										store.remove(rec);
									}
								}
								Ext.Msg.alert('提示', '删除用户信息成功。');
							} else {
								if (typeof (result.message) != 'undefined') {
									Ext.Msg.alert('提示',
											'删除所有用户记录失败:' + result.message);
								} else {
									Ext.Msg.alert('提示', '删除用户信息失败！');
								}
							}
						},
						failure : function(response, action) {
							msgTip.hide();
							if (typeof (result.message) != 'undefined') {
								Ext.Msg.alert('提示',
										'删除所有用户记录失败:' + result.message);
							} else {
								Ext.Msg.alert('提示', '删除用户信息请求失败！');
							}
						}
					});
		}
		
		function deleteDepartment () {
			if (currentId < 1) {
				Ext.Msg.alert('提示', '不能删除组织');
				return;
			}
			Ext.MessageBox.confirm('提示', '您确定要删除所选部门吗？', function(btnId) {
				if (btnId == 'yes')
					deleteDp(currentId);
			})
		}
		function deleteDp(Ids) {
			var msgTip = Ext.MessageBox.show( {
				title : '提示',
				width : 250,
				msg : '正在删除所选部门，请稍候......'
			});
			Ext.Ajax
					.request( {
						url : 'departmentController/delete',
						params : {
							Ids : Ids,
						},
						method : 'POST',
						success : function(response, action) {
							msgTip.hide();
							var result = Ext.util.JSON
									.decode(response.responseText);
							if (result.success) {
								//colTree.e	
								Ext.Msg.alert('提示', '删除部门信息成功。');
								var index = departStore.find('id', Ids);
									if (index != -1) {
										var rec = departStore.getAt(index)
										departStore.remove(rec);
									}
							} else {
								if (typeof (result.message) != 'undefined') {
									Ext.Msg.alert('提示',
											'删除部门信息失败:' + result.message);
								} else {
									Ext.Msg.alert('提示', '删除部门信息失败！');
									
								}
							}
						},
						failure : function(response, action) {
							msgTip.hide();
							if (typeof (result.message) != 'undefined') {
								Ext.Msg.alert('提示',
										'删除所有用户记录失败:' + result.message);
							} else {
								Ext.Msg.alert('提示', '删除用户信息请求失败！');
							}
						}
					});
		}
		
		
		function inputExcel() {
			var importForm = Ext.create('Ext.form.Panel', {
				fileUpload : true,
				frame : true,
				waitMsgTarget : true,
				labelAlign : 'right',
				baseCls : 'x-plain',
				labelWidth : 140,
				labelHeight : 23,
				defaultType : 'textfield',
				items : [ {
					fieldLabel : '请选择导入文件(.xls)',
					id : 'importFile',
					name : 'importFile',
					itemCls : 'float-left',
					clearCls : 'allow-float',
					inputType : 'file',
					readOnly : false,
					width : 310,
					height : 22,
					allowBlank : false,
					validator : function(value) {
						var start = value.lastIndexOf('.');
						var end = value.length;
						var excelVal = value.substring((start + 1), end);
						if (excelVal == 'xls') {
							return true;
						} else {
							return '导入必须是Excel文件类型';
						}
					}
				} ]
			});
			if (inputWindow != null) {
				inputWindow.destroy();
			}
			var inputWindow = Ext
					.create(
							'Ext.Window',
							{
								width : 500,
								height : 105,
								minWidth : 200,
								minHeight : 200,
								layout : 'fit',
								plain : true,
								buttonAlign : 'center',
								bodyStyle : 'padding:5px;',
								items : importForm,
								buttons : [
										{
											text : '导入',
											handler : function() {
												if (importForm.form.isValid()) {
													importForm.form
															.submit( {
																url : 'usersController/importExcel',
																method : 'POST',
																waitMsg : '正在上传文件，请稍后',
																success : function(
																		form,
																		action) {
																	inputWindow
																			.destroy();
																	Ext.MessageBox
																			.alert(
																					'提示成功',
																					action.result.message);
																	showAll();
																},
																failure : function(
																		form,
																		action) {
																	Ext.MessageBox
																			.alert(
																					'提示失败',
																					'导入Excel文件失败:' + action.result.message);
																}
															});
												} else {
													Ext.MessageBox.alert(
															'提交失败',
															'导入必须是Excel文件类型!');
												}
											}
										}, {
											text : '取消',
											handler : function() {
												inputWindow.destroy();
											}
										} ]
							});
			inputWindow.show();
		}
		;
		
		function submitDetailForm(){
			if(detailForm.sType==1){
			detailForm.form.findField('parentId').setValue(currentId);
			detailForm.form.submit({
				clientValidation : true,//进行客户端验证
							waitMsg : '正在提交数据请稍后',//提示信息
							waitTitle : '提示',//标题
							url : 'departmentController/add',//请求的url地址
							method : 'POST',//请求方式
							params : {
								'action' : 'add',
							},

							success : function(form, action) {//加载成功的处理函数
								win.hide();
								//updateList(action.result.id, 0);
								detailForm.form.findField('id').setValue(action.result.id);
								//update tree
								//colTree.getChecked[0].appendChild({
								//	id:action.result.id,
								//	name:detailForm.form.findField('name').getValue(),
								//	leaf:false,
								//	leadId:detailForm.form.findField('leadId').getValue()
								//});
								//colTreee.getChecked[0].expand();
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '新增部门成功');
									
								}
								departStore.reload();
							},
							failure : function(form, action) {//加载失败的处理函数
								//var result = Ext.util.JSON
							//			.decode(response.responseText);
							//	alert(result);
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '新增部门失败');
								}
							}
			});
		}else{//modify
			detailForm.form.findField('id').setValue(currentId);
			detailForm.form.submit({
							clientValidation : true,//进行客户端验证
							waitMsg : '正在提交数据请稍后',//提示信息
							waitTitle : '提示',//标题
							url : 'departmentController/update',//请求的url地址
							method : 'POST',//请求方式
							success : function(form, action) {//加载成功的处理函数
								departStore.reload();
          					if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '修改部门信息成功');
								}
							},
							failure : function(form,action){//加载失败的处理函数
								departStore.reload();
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '修改部门信息失败');
								}
							}
							
			});
		}
	}
		function submitPswForm(){
			var password1=pswForm.form.findField('newpassword').getValue();
			var password2=pswForm.form.findField('checkPassword').getValue();
			if(password1!=password2){
				Ext.Msg.alert('提示', '两次输入的密码不一致!');
				return;
			}
			pswForm.form.submit({
							clientValidation : true,//进行客户端验证
							waitMsg : '正在提交数据请稍后',//提示信息
							waitTitle : '提示',//标题
							url : 'usersController/custom',//请求的url地址
							method : 'POST',//请求方式
							success : function(pswForm, action) {//加载成功的处理函数
								pswWin.hide();
								if (typeof (action.result) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '修改密码成功');
								}
							},
							failure : function(pswForm, action) {//加载失败的处理函数
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '修改密码失败');
								}
							}
			});
		}
		function submitForm() {
			form.form.findField('department').setValue(currentId);
			if (form.wType == 1) {
				form.form
						.submit( {
							clientValidation : true,//进行客户端验证
							waitMsg : '正在提交数据请稍后',//提示信息
							waitTitle : '提示',//标题
							url : 'usersActionController/?action=add',//请求的url地址
							method : 'POST',//请求方式
							params : {
								'action' : 'add',
							},

							success : function(form, action) {//加载成功的处理函数
								win.hide();
							    showAll();
								//updateList(action.result.id, 0);
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '新增用户成功');
								}
							},
							failure : function(form, action) {//加载失败的处理函数
								//var result = Ext.util.JSON
							//			.decode(response.responseText);
							//	alert(result);
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '新增用户失败');
								}
							}
						});
			} else if (form.wType == 3) {//修改
				form.form
						.submit( {
							clientValidation : true,//进行客户端验证
							waitMsg : '正在提交数据请稍后',//提示信息
							waitTitle : '提示',//标题
							url : 'usersController/update',//请求的url地址
							method : 'POST',//请求方式
							params : {
								'action' : 'update',
							},
							success : function(form, action) {
								win.hide();
								//updateList(action.result.id, 1);
								showAll();
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '修改用户资料成功');
								}
							},
							failure : function(form, action) {
								if (typeof (action.result.message) != 'undefined') {
									Ext.Msg.alert('提示', action.result.message);
								} else {
									Ext.Msg.alert('提示', '修改用户失败');
								}
							}
						});
			} else {//过滤
				searchname = document.getElementById('name').value;
				searchremark = document.getElementById('remark').value;
				searchtype = document.getElementById('type').value;
				store
						.load( {
							params : {
								start : 0,
								'name' : searchname,
								'remark' : searchremark,
								'type' : searchtype,
								limit : recond,
								action : 'list'
							}
						});
				win.hide();
			}
		}
		function updateList(selectID, type) {
			var fields = getFormFieldsObj(selectID);
			var index = store.find('id', fields.id);
			if (type == 1) {
				var item = store.getAt(index);
				for ( var fieldName in fields) {
					item.set(fieldName, fields[fieldName]);
				}
				store.commitChanges();
			} else {
				var rec = new Ext.data.Record(fields);
				store.insert(0, rec);
			}
		}
		var obj = {};
			//取得表单数据
		function getFormFieldsObj(roleID){
			var fields =form.items;
			var obj = {};
			for(var i = 0 ; i < fields.length ; i++){
				var item = 	fields.items[i];
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
		function getIdList() {
			var recs = grid.getSelectionModel().getSelection();
			var list = [];
			if (recs.length == 0) {
				Ext.MessageBox.alert('提示', '请选择要进行操作的用户！');
			} else {
				for ( var i = 0; i < recs.length; i++) {
					var rec = recs[i];
					list.push(rec.get('id'))
				}
			}
			return list;
		}
		null
		function exportExcel() {
			Ext.ux.Grid2Excel.Save2Excel(grid);
		}
		function exportExcel2() {
			win.show();
			form.getForm().getEl().dom.action = 'usersController/exportExcel&javax.servlet.forward.query_string=weblogic_jsession=null&SEM_LOGIN_TOKEN=AAANZUAAFAAAEpVAAZsst2biexngsvf52utbjb5z45&MODULE_ID=2&javax.servlet.forward.request_uri=/PMWeb/vendorAction.do&weblogic_jsession=null&javax.servlet.forward.servlet_path=/vendorAction.do&javax.servlet.forward.context_path=/PMWeb&weblogic.servlet.forward.target_servlet_path=/vendor.jsp&weblogic.servlet.jsp=true&name='
					+ searchname
					+ '&remark='
					+ searchremark
					+ '&type='
					+ searchtype
			form.getForm().getEl().dom.submit()
			win.hide();
		}
		function exportExcel3() {
			win.show();
			form.getForm().getEl().dom.action = 'usersController/exportExcel2&javax.servlet.forward.query_string=weblogic_jsession=null&SEM_LOGIN_TOKEN=AAANZUAAFAAAEpVAAZsst2biexngsvf52utbjb5z45&MODULE_ID=2&javax.servlet.forward.request_uri=/PMWeb/vendorAction.do&weblogic_jsession=null&javax.servlet.forward.servlet_path=/vendorAction.do&javax.servlet.forward.context_path=/PMWeb&weblogic.servlet.forward.target_servlet_path=/vendor.jsp&weblogic.servlet.jsp=true&name='
					+ searchname
					+ '&remark='
					+ searchremark
					+ '&type='
					+ searchtype
			form.getForm().getEl().dom.submit()
			win.hide();
		}

	});
</script>
	<body>
		<div id='grid-div' style="width: 100%; height: 100%;" />
	</body>


</html>
<script>

</script>




