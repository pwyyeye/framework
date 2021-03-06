    Ext.define('Ext.ux.form.HtmlPlusEditor', {
    extend: 'Ext.form.field.TextArea',
    alias: 'widget.htmlPlusEditor',//xtype名称
    initComponent: function () {
        this.html = "<textarea id='" + this.getId() + "-input' name='" + this.name + "'></textarea>";
        this.callParent(arguments);
        this.on("render", function (t) {
            this.inputEL = Ext.get(this.getId() + "-input");
            this.editor = KindEditor.create('textarea[name="' + this.name + '"]', {
                height: t.getHeight()-18,//有底边高度，需要减去
                width: t.getWidth() - 60,//宽度需要减去label的宽度
                basePath: '/portal/extjs/ux/form/htmlPlusEditor/',
                fileManagerJson :'/lieyu/lybarimagesAction.do?weblogic_jsession=1234567890&action=cancel',
                uploadJson: '/portal/fileServerAction.do?action=add',//路径自己改一下
                //fileManagerJson: '/Content/Plugin/kindeditor-4.1.5/asp.net/file_manager_json.ashx',//路径自己改一下
                resizeType: 0,
                wellFormatMode: true,
                newlineTag: 'br',
                allowFileManager: true,
                allowPreviewEmoticons: true,
                allowImageUpload: true,
                items: [
                    'source','fullscreen', '|', 'undo', 'redo', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', '|',
                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'bold',
                    'italic', 'underline', 'lineheight', '|', 'image', 'multiimage',
                    'table', 'emoticons'
                ]
            });
        });
        this.on("resize", function (t, w, h) {
            this.editor.resize(w - t.getLabelWidth(), h-18);
        });
    },
    setValue: function (value) {
        if (this.editor) {
            this.editor.html(value);
        }
    },
    reset: function () {
        if (this.editor) {
            this.editor.html('');
        }
    },
    setRawValue: function (value) {
        if (this.editor) {
            this.editor.text(value);
        }
    },
    getValue: function () {
        if (this.editor) {
            return this.editor.html();
        } else {
            return ''
        }
    },
    getRawValue: function () {
        if (this.editor) {
            return this.editor.html();
        } else {
            return ''
        }
    }
});