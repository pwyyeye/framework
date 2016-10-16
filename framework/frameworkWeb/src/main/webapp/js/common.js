//var cb = Ext.create('Ext.selection.CheckboxModel',{ checkOnly :false });
Ext.define('idNameModel', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [ {
		sortable : true,
		name : 'id',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ]
});

//form reader
Ext.define('commonFormRead', {
	extend : 'Ext.data.reader.Json',
	totalProperty : 'results',
	rootProperty : 'items',
	idProperty : 'id'
});

Ext
		.define(
				'Ext.ux.TreePicker',
				{
					extend : 'Ext.form.field.Picker',
					xtype : 'treepicker',
					triggerCls : Ext.baseCSSPrefix + 'form-arrow-trigger',
					config : {
						displayField : null,
						columns : null,
						rootVisible : false,
						selectOnTab : true,
						firstSelected : false,
						hideHeaders : true,
						maxPickerWidth : 300,
						maxPickerHeight : 360,
						minPickerHeight : 100
					},
					editable : false,
					initComponent : function() {
						var me = this;
						me.callParent(arguments);
						this.addStateEvents('select');
						me.store.on('load', me.onLoad, me);

					},
					createPicker : function() {
						var me = this, picker = Ext.create('Ext.tree.Panel', {
							store : me.store,
							floating : true,
							hidden : true,
							hideHeaders : me.hideHeaders,
							width : me.maxPickerWidth,
							displayField : me.displayField,
							columns : me.columns,
							maxHeight : me.maxTreeHeight,
							shadow : false,
							rootVisible : me.rootVisible,
							manageHeight : false,
							//  selModel: cb,
							listeners : {
								itemclick : Ext.bind(me.onItemClick, me)
							},
							viewConfig : {
								listeners : {
									render : function(view) {
										view.getEl().on('keypress',
												me.onPickerKeypress, me);
									}
								}
							}
						}), view = picker.getView();

						view.on('render', me.setPickerViewStyles, me);
						if (Ext.isIE9 && Ext.isStrict) {
							view.on('highlightitem', me.repaintPickerView, me);
							view
									.on('unhighlightitem',
											me.repaintPickerView, me);
							view
									.on('afteritemexpand',
											me.repaintPickerView, me);
							view.on('afteritemcollapse', me.repaintPickerView,
									me);
						}
						return picker;
					},
					setPickerViewStyles : function(view) {
						view.getEl().setStyle( {
							'min-height' : this.minPickerHeight + 'px',
							'max-height' : this.maxPickerHeight + 'px'
						});
					},
					repaintPickerView : function() {
						var style = this.picker.getView().getEl().dom.style;
						style.display = style.display;
					},
					alignPicker : function() {
						var me = this, picker;

						if (me.isExpanded) {
							picker = me.getPicker();
							if (me.matchFieldWidth) {
								picker.setWidth(this.picker.getWidth());
							}
							if (picker.isFloating()) {
								me.doAlign();
							}
						}
					},
					onItemClick : function(view, record, node, rowIndex, e) {
						this.selectItem(record);
					},
					onPickerKeypress : function(e, el) {
						var key = e.getKey();

						if (key === e.ENTER
								|| (key === e.TAB && this.selectOnTab)) {
							this.selectItem(this.picker.getSelectionModel()
									.getSelection()[0]);
						}
					},
					selectItem : function(record) {
						var me = this;
						me.setValue(record.get(this.valueField || 'id'));
						me.picker.hide();
						me.inputEl.focus();
						me.fireEvent('select', me, record)
					},
					onExpand : function() {
						var me = this, picker = me.picker, store = picker.store, value = me.value;
						if (value) {
							var node = store.getNodeById(value);
							if (node)
								picker.selectPath(node.getPath());
						} else {
							var hasOwnProp = me.store
									.hasOwnProperty('getRootNode');
							if (hasOwnProp)
								picker.getSelectionModel().select(
										store.getRootNode());
						}

						Ext.defer(function() {
							picker.getView().focus();
						}, 1);
					},
					setValue : function(value) {
						var me = this, record;
						me.value = value;
						if (me.store.loading) {
							return me;
						}
						try {
							var hasOwnProp = me.store
									.hasOwnProperty('getRootNode');
							record = value ? me.store.getNodeById(value)
									: (hasOwnProp ? me.store.getRootNode()
											: null);
							me.setRawValue(record ? record
									.get(this.displayField) : '');
						} catch (e) {
							me.setRawValue('');
						}

						return me;
					},
					getValue : function() {
						return this.value;
					},
					onLoad : function(store, node, records) {
						var value = this.value;
						if (value) {
							this.setValue(value);
						} else {
							if (this.firstSelected) {
								if (records && records.length > 0) {
									var record = records[0];
									this.setValue(record.get(this.valueField));
								}
							}
						}
					},
					getSubmitData : function() {
						var me = this, data = null;
						if (!me.disabled && me.submitValue) {
							data = {};
							data[me.getName()] = '' + me.getValue();
						}
						return data;
					},
					onTriggerClick : function() {
						var me = this;
						//me.store.load();
					if (!me.readOnly && !me.disabled) {
						if (me.isExpanded) {
							me.collapse();
						} else {
							me.expand();
						}
						me.inputEl.focus();
					}
				}
				});

/**
 * system combo
 */

Ext.define('ModuleTree', {
	extend : 'Ext.data.TreeModel',
	idProperty : 'id',
	fields : [ {
		sortable : true,
		name : 'id',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ]
});
var moduleStore = function(id) {
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
			url : 'moduleAction.do?action=list&root=' + id,
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
Ext.define('Ext.ux.SystemComboBox', {
	extend : 'Ext.ux.TreePicker',
	xtype : 'systemcombo',
	initComponent : function() {
		Ext.apply(this, {
			width : 300,
			allowBlank : false,
			hideHeaders : true,
			store : moduleStore(0),
			fieldLabel : '模块',
			blankText : '必须选择模块',
			hiddenName : 'moduleID',
			name : 'moduleID',
			id : 'moduleID',
			columns : [ {
				xtype : 'treecolumn',
				width : 280,
				dataIndex : 'name'
			} ],
			displayField : 'name'
		});
		this.callParent(arguments);
	}
});

var organiseStore = function(id) {
	var me = this;
	return Ext.create('Ext.data.TreeStore', {
		model : 'ModuleTree',
		defaultRootId : id,
		nodeParam : 'id',
		root : {
			id : 0,
			text : '机构',
			expanded : true
		},
		proxy : {
			type : 'ajax',
			url : 'organiseAction.do?action=cancel&root=' + id,
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
Ext.define('Ext.ux.OrganiseComboBox', {
	extend : 'Ext.ux.TreePicker',
	xtype : 'organisecombo',
	initComponent : function() {
		Ext.apply(this, {
			width : 300,
			allowBlank : false,
			hideHeaders : true,
			store : organiseStore(0),
			fieldLabel : '机构',
			blankText : '必须选择机构',
			hiddenName : 'barid',
			name : 'barid',
			id : 'organise',
			columns : [ {
				xtype : 'treecolumn',
				width : 280,
				dataIndex : 'name'
			} ],
			displayField : 'name'
		});
		this.callParent(arguments);
	}
});




/**
 * id&name combo
 */
Ext.define('Ext.ux.idNameComBox',{
	extend:'Ext.form.field.ComboBox',
	xtype:'idNameComBox',
	store:'',
	mode:'local',
	triggerAction:'all',
	displayField:'name',
	valueField:'id',
	selectData:[],
	config:{
	  width:300,
	  selectData:[],
	  name:'name',
	  hiddenName:'id',
	  fieldLabel:'fieldLabel',
	},
	initComponent : function() {
		var me = this;
		if(me.store==''){
	       me.store=new Ext.data.JsonStore({
	    	  data:me.selectData,
	    	  model: 'idNameModel'
	      });
	    }
	    me.callParent(arguments);
	},
	setValue : function(value,doSelect) {
		               var me = this, record,i, len, dataObj;
		               valueNotFoundText = me.valueNotFoundText,
                       store = me.getStore(),
            inputEl = me.inputEl,
            matchedRecords = [],
            displayTplData = [],
            processedValue = [],
            autoLoadOnValue = me.autoLoadOnValue,
            pendingLoad = store.hasPendingLoad(),
            unloaded = autoLoadOnValue && store.loadCount === 0 && !pendingLoad;
		    if (value != null && (pendingLoad || unloaded || store.isEmptyStore)) {
               me.value = value;
               me.setHiddenValue(me.value);
               if (unloaded && store.getProxy().isRemote) {
                  store.load();
                }
                return me;
             }
		     value = Ext.Array.from(value);
            for (i = 0, len = value.length; i < len; i++) {
               record = value[i];
               if (!record || !record.isModel) {
                  record = me.findRecordByValue(record);
               }
               if (record) {
                  matchedRecords.push(record);
                  displayTplData.push(record.data);
                  processedValue.push(record.get(me.valueField));
                }else {
                if (!me.forceSelection) {
                    processedValue.push(value[i]);
                    dataObj = {};
                    dataObj[me.displayField] = value[i];
                    displayTplData.push(dataObj);
                    // TODO: Add config to create new records on selection of a value that has no match in the Store
                }
                else if (Ext.isDefined(valueNotFoundText)) {
                    displayTplData.push(valueNotFoundText);
                }
            }
        }

        me.setHiddenValue(processedValue);
        me.value = me.multiSelect ? processedValue : processedValue[0];
        if (!Ext.isDefined(me.value)) {
            me.value = undefined;
        }
        me.displayTplData = displayTplData; //store for getDisplayValue method
        me.lastSelection = me.valueModels = matchedRecords;

        if (inputEl && me.emptyText && !Ext.isEmpty(value)) {
            inputEl.removeCls(me.emptyCls);
        }

        me.setRawValue(me.getDisplayValue());
        me.checkChange();

        if (doSelect !== false) {
            me.syncSelection();
        }
        me.applyEmptyText();
        var realValue;
        try {
			if(Ext.isArray(value)){
				if(Ext.isObject(value[0])){
					realValue=value[0].id;
				}else{
				  realValue=value[0];
				  }
			}else{
				realValue=value;
			}
			
			record = me.store.getById(realValue);
			me.setRawValue(record ? record
									.get(this.displayField) : '');
			} catch (e) {
							me.setRawValue('');
			}
            me.value = realValue;
			return me;
		},
	getValue : function() {
			var me = this,
            picker = me.picker,
            rawValue = me.getRawValue(), //current value of text field
            value = me.value; //stored value from last selection or setValue() call
        /** 
        if (me.getDisplayValue() !== rawValue) {
            value = rawValue;
            me.value = me.displayTplData = me.valueModels = undefined;
            if (picker) {
                me.ignoreSelection++;
                picker.getSelectionModel().deselectAll();
                me.ignoreSelection--;
            }
        }
       */
        // Return null if value is undefined/null, not falsy.
        me.value = value == null ? null : value;
		var realValue;
			try{
			if(Ext.isArray(me.value)){
							realValue=me.value[0].id;
						}else{
							realValue=me.value;
						}
			}catch (e) {
							return '';
			}
			return realValue;
	}
})
