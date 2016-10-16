Ext.ux.IconCombo = Ext.extend(Ext.form.ComboBox, {
    initComponent:function() {
 
				Ext.apply(this, {
            tpl:  '<tpl for=".">'
                + '<div class="x-combo-list-item ux-icon-combo-item ">'
                + '<img src="{' + this.iconClsField + '}" height="14" width="18" />'
                + '{' + this.displayField + '}'
                + '</div></tpl>'
        });
 
        // call parent initComponent
        Ext.ux.IconCombo.superclass.initComponent.call(this);
 
    }, // end of function initComponent
 
    onRender:function(ct, position) {
        // call parent onRender
        Ext.ux.IconCombo.superclass.onRender.call(this, ct, position);
 
        // adjust styles
        this.wrap.applyStyles({position:'relative'});
        this.el.addClass('ux-icon-combo-input');
 
        // add div for icon
        this.icon = Ext.DomHelper.append(this.el.up('div.x-form-field-wrap'), {
            tag: 'img', style:'position:absolute;height:14px;width:18px;left:5px;top:3px;'
        });
    }, // end of function onRender
 
    setIconCls:function() {
        var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
        if(rec) {
            this.icon.src = rec.get(this.iconClsField);
        }
    }, // end of function setIconCls
 
    setValue: function(value) {
        Ext.ux.IconCombo.superclass.setValue.call(this, value);
        this.setIconCls();
    } // end of function setValue
});
 
// register xtype
Ext.reg('iconcombo', Ext.ux.IconCombo);