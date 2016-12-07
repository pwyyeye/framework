<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
	<script type="text/javascript">
Ext.Loader.setConfig( {
	enabled : true
});
Ext.Loader.setPath('Ext.ux', '../ux');
Ext.require( [ 'Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
		'Ext.util.*', 'Ext.state.*', 'Ext.form.*', 'Ext.ux.CheckColumn' ]);

Ext.onReady(function() {

	function formatDate(value) {
		return value ? Ext.Date.dateFormat(value, 'M d, Y') : '';
	}

	Ext.define('Plant', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'property.name',
			type : 'string'
		}, {
			name : 'property.remark',
			type : 'string'
		}, {
			name : 'value',
			type : 'string'
		} ]
	});

	var store = Ext.create('Ext.data.Store', {
		autoDestroy : true,
		autoLoad:true,
		model : 'Plant',
		proxy : {
			type : 'ajax',
			url : 'userPropertyAction.do?action=list',
			reader : {
				type : 'json',
				totalProperty : 'results',
				root : 'items'
			}
		},
		sorters : [ {
			property : 'property',
			direction : 'ASC'
		} ]
	});

	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
		clicksToEdit : 1
	});

	var grid = Ext.create(
		'Ext.grid.Panel',{
			store : store,
			columns : [
			{id : 'common',
			 header : '配置项',
			 dataIndex : 'property.name',
			  width : 120,
			 field : {allowBlank : false}
			},{
			 header : '配置值',
			 dataIndex : 'value',
			 width : 120,
			 align : 'left',
			 field : {allowBlank : false}
			},{
			 header : '备注说明',
			 dataIndex : 'availDate',
			 flex : 1,
			 field :  {allowBlank : false}
											
			}],
			selModel : {
				selType : 'cellmodel'
			},
			renderTo : 'grid-div',
			title : '个人参数设置',
			frame : true,
			tbar : [ {
				text : '保存',
				handler : function() {
				}
				} ],
			plugins : [ cellEditing ]
			});
	  store.load({params:{start:0, action: 'list'}});
	
	});
</script>
	<body>
		<div id='grid-div' style="width: 100%; height: 100%;" />
	</body>
</html>
