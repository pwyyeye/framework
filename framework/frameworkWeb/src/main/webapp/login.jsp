<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>

<html>
	<%@ include file="common.jsp"%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script>
	function checkIE() {
	var ver = navigator.appVersion;
	var idx = ver.indexOf("MSIE ") + 5;
	var idx1 = ver.indexOf(";", idx);
	var sVer = ver.substring(idx, idx1);
	var iVer = parseFloat(sVer);
	if (!isNaN(iVer)) {
		if (iVer < 7) {
			alert("系统提示：您的浏览器版本为IE" + iVer + "必须是IE8及其以上版本\n请升级您的浏览器，然后重新登录系统");
		}
	}
}
	window.defaultStatus = "猎娱网";
checkIE();
Ext.Loader.setConfig( {
	enabled : true
});
Ext.require( [ 'Ext.form.*', 'Ext.window.*', 'Ext.tab.Panel']);
Ext.onReady(function(){
Ext.form.Field.prototype.msgTarget = 'qtip';
Ext.QuickTips.init(); 
    var loginForm =Ext.create('Ext.FormPanel',{
                   // el: 'hello-tabs',
                    id: 'logonForm', 
                    name: 'logonForm',                   
                    autoTabs:true,
                    activeTab:0,                    
                    deferredRender:false,
                    border:false,                  
                    items: {
                       xtype:'tabpanel',
                       activeTab: 0,
                       defaults:{autoHeight:true, bodyStyle:'padding:10px'}, 
                    items:[{
                     title:'用户登录',
                    // contentEl: 'loginInfo',
                     layout:'form',                     
                      defaults: {width: 230},
                       defaultType: 'textfield',
                       labelPad:10,
                      items: [{
                      fieldLabel: '用户名',
                      name: 'username',
                      style: 'font-size: 15px',
                      allowBlank:false,
                      width:300
                    },{
                      fieldLabel: '密    码',
                      name: 'password',
                      style: 'font-size: 15px',
                      inputType: 'password',
                      allowBlank:false,
                      width:300
                }
               //     <logic:present name="SELECT_MODULE">,{xtype:'systemcombo'}</logic:present>
                //,{xtype:'panel',  border:false, 
              //  html:'<p>&nbsp;</p><p>&nbsp;</p>'
              //  }
              //   ,{xtype:'panel',  border:false, 
             //   html:'<li><font color="red"><logic:present name="TEST_FLAG">测试环境，登录名为工号</logic:present>	<logic:notPresent name="TEST_FLAG">请使用OA的用户名与密码登录</logic:notPresent></font></li>'
             //   }
              ]
            },{
                title:'信息公告栏',
                layout:'',
                html: '',
                defaults: {width: 230}
            },{
                title:'帮助',
                layout:'',
                html: '1.内部系统使用<p>2.若正式环境，使用OA的用户名与密码登录<p>3.测试环境，使用工号登录，密码为任意<p>4.若使用过程中有异常，请与猎娱网管理员联系',
                defaults: {width: 230}
            }]
            }
                });    
    var win =Ext.create('Ext.window.Window',{
                el:'hello-win',
                layout:'fit',
                width:400,
                height:280,
                closeAction:'hide',
                plain: true,
                modal:true,
                collapsible: true,
                maximizable:false,
                draggable: true,
                closable: false,
                resizable:false,
                animateTarget:document.body,
                items: 
                loginForm,
                buttons: [{
                    text:'登录',
                    handler: function(){
                        if(win.getComponent('logonForm').form.isValid()){
                        win.getComponent('logonForm').form.submit({
                            url:'logonAction.do?action=list', 
                            waitTitle:'提示',
                            method: 'POST',
                            waitMsg:'正在验证您的身份,请稍候.....',
                            success:function(form,action){
                            var loginResult = action.result.success;
                            var errmsg = action.result.msg;
                            window.location.href='logonAction.do';
                         
                            } ,
                            failure: function(form,action) {
                            var errmsg = action.result.msg;
                            Ext.Msg.alert('提示',errmsg);
                            win.getComponent('logonForm').form.reset();
                               }                            
                            });
                            }
                     }
                },{
                    text: '取消',
                    handler: function(){
                        win.hide();
                    }
                }]
            }
           
            );           
        win.show();
});

</script>

	<body>
		<div id='hello-win'></div>
	</body>



</html>
