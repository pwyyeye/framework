<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<%@ include file="uploadFile.jsp"%>
	<script>

var routeData = [ {
	id : '0',
	name : '正常公告'
}, {
	id : '1',
	name : '重要公告'
} ];

</script>
	<body>
		<controls:grid recordLabel="系统公告" action="noticeAction.do"
			gridBody="module:模块名,subject:主题,content:内容,moduleID,attachName:附件:attach,startDate:有效开始日期,endDate:有效截止日期:showReport.do?id=%s&value=%v,validStr:类型,valid"
			searchFieldsString="moduleID,subject,content" needRightCheck="Y"
			fileUpload="Y" pageSize="30" formHeight="450" searchFormHeight="300"
			extContent="
			setUploadFile=function(value){
	           uploadFileValue=uploadFileValue+value.name+','
	           Ext.getCmp('content').setValue(uploadFileValue)}
			var uploadWindow=
				 Ext.create(
						'Ext.window.Window',
						{
							layout : 'border',
							id : 'uploadWindow',
							items : [ {
								region : 'center',
								html : '<IFRAME frameBorder=0 width=100% height=100%  src=/portal/qiniuTest.jsp > </IFRAME>'
							} ],
							width : 500,
							height : 400,
							closable : false,
							modal : 'true',
							buttonAlign : 'right',
							autoScroll : false,
							bbar : [ '->',{
								text : '关闭',
								handler : function() {
									uploadWindow.hide();
									
								}
							} ]
						});
						function uploadFile(){
		
					uploadWindow.show();
			}"
			searchFormString="{xtype:'systemcombo'},{xtype:'textfield',width : 200,name : 'subject',fieldLabel:'主题'},
				  {xtype:'textarea',width : 350,name : '内容',fieldLabel:'content'}"
			formString="{xtype:'systemcombo'},
			
				  {xtype:'datefield',width : 350,format:'Y-m-d',name : 'startDate',fieldLabel:'有效开始日期'},
				  {xtype:'datefield',width : 350,format:'Y-m-d',name : 'endDate',fieldLabel:'有效截止日期'},
				  {xtype:'idNameComBox',width:350,name: 'validStr',hiddenName:'valid',selectData:routeData,fieldLabel:'类型'},
				  {xtype: 'fieldset',
				   layout: {
                     type : 'table',
                     columns : 3
                   },                 
                   layoutConfig:{columns: 3},   
                  items: [
                        {xtype:'textfield',width : 300,name : 'content',id:'content',fieldLabel:'附件'},{xtype:'button', text:'上传',handler:uploadFile}               
                   ]                   
                },		
				" />

	</body>
</html>
