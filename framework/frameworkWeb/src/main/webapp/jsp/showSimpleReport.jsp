<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>

<%@ page import="common.value.ReportModuleVO,org.jdom.*,java.lang.*,java.io.*"%>
<%@ include file="head.jsp"%>

<script>
    var currentUser=new Object();
    <logic:present name="LOGON_USER">
       currentUser.empID='<bean:write name="LOGON_USER" property="empID"/>';
       currentUser.empName='<bean:write name="LOGON_USER" property="empName"/>';
       currentUser.unitID='<bean:write name="LOGON_USER" property="unitID"/>';
       currentUser.unitName='<bean:write name="LOGON_USER" property="unitName"/>';
       currentUser.token='<bean:write name="LOGON_USER" property="token"/>';
      <bean:define id="currentRole" name="LOGON_USER" property="role"></bean:define>
         <logic:present name="currentRole">
         currentUser.roleID='<bean:write name="currentRole" property="id"/>';
         currentUser.roleName='<bean:write name="currentRole" property="name"/>';
         </logic:present>
    </logic:present>
</script>
<html>
	<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>
		<%request.setCharacterEncoding("GBK");
			String parameter = (String) request
					.getAttribute("report_parameter");
			ReportModuleVO report=(ReportModuleVO)request.getAttribute("CURRENT_REPORT");
			String reportParameter=(String)request.getAttribute("report_parameter");
		    reportParameter=reportParameter.replaceAll(";","&");
			String destUrl="showReportAction.do?REPORTID="+report.getId()+"&"+reportParameter;
			String saveAsName=report.getName();
			String beanName="Report_Name"+report.getId();
			String reportFileName=(String)request.getAttribute("RAQ_PATH");
%>
	   <logic:present name="CURRENT_LINE_KEY">
          <controls:lineSeletor initValueObject="CURRENT_LINE_KEY"  allowBlank="Y"/>  
	   </logic:present>
	   	   <logic:present name="CURRENT_COLOR_KEY">
          <controls:colorSeletor initValueObject="CURRENT_COLOR_KEY"  allowBlank="Y"/>  
	   </logic:present>
	   <logic:present name="CURRENT_WORKSHOP_KEY">
	        <controls:workshopSeletor initValueObject="CURRENT_WORKSHOP_KEY"  allowBlank="Y"/>  
	   </logic:present>
	   <logic:present name="CURRENT_SERIES_KEY">
            <controls:seriesSeletor initValueObject="CURRENT_SERIES_KEY"  allowBlank="Y"/> 
	   </logic:present>	 	
	   <logic:present name="CURRENT_MODEL_KEY">
            <controls:modelSeletor initValueObject="CURRENT_MODEL_KEY"  allowBlank="Y"/> 
	   </logic:present>	    
	   <logic:present name="CURRENT_CONTROLPOINT_KEY">
            <controls:controlPointSeletor initValueObject="CURRENT_CONTROLPOINT_KEY"  allowBlank="Y"/> 
	   </logic:present>

		<script language=javascript src="/servlet/com.runqian.base.util.ReadJavaScriptServlet?file=%2Fcom%2Frunqian%2Freport%2Fview%2Fhtml%2Fscroll.js"></script>
		<script>
       Ext.onReady(function(){

       <logic:present name="CURRENT_DATE_KEY">
          var searchDate=new Ext.form.DateField({
	         name : 'date',
	         width : 150,
	         format :'<bean:write name="CURRENT_REPORT" property="needDateKeyStr"/>',
	         menu:new DatetimeMenu()
	      }); 
	      searchDate.setValue('<bean:write name="CURRENT_DATE_KEY"/>');
	      <logic:present name="DATE_RANGE_FLAG">
	           var searchEndDate=new Ext.form.DateField({
	            name : 'endDate',
	            width : 150,
	           format :'<bean:write name="CURRENT_REPORT" property="needDateKeyStr"/>', 
	           menu:new DatetimeMenu()
	       });
	    searchEndDate.setValue('<bean:write name="END_DATE_KEY"/>');
	      </logic:present> 
	   </logic:present>
            <logic:present name="CUSTOM_JAVASCRIPT">
	<bean:write name="CUSTOM_JAVASCRIPT"  filter="false"/>
	</logic:present>
	var form = new Ext.FormPanel({
			labelSeparator : "：",
			frame:true,
			border:false,
			layout:'table',
			  items:[
			  <logic:present name="CURRENT_DATE_KEY">
			       {html:'日期:'},
			        searchDate,
			        <logic:present name="DATE_RANGE_FLAG">
			          {html:"至"},searchEndDate,
			        </logic:present>        
			   </logic:present>
			   <logic:present name="DATE_FIELD_HTML">
			      <bean:write name="DATE_FIELD_HTML" filter="false"/>
			   </logic:present>
			   <logic:present name="TIME_FIELD_HTML">
			      <bean:write name="TIME_FIELD_HTML" filter="false"/>
			   </logic:present>
			    <logic:present name="CURRENT_LINE_KEY">
			          {html:" 线别:"},lineField,
			    </logic:present>
			    <logic:present name="CURRENT_WORKSHOP_KEY">
			          {html:" 车间:"},workshopField,
			    </logic:present> 
			    <logic:present name="CURRENT_SERIES_KEY">
			          {html:" 车系:"},seriesField, 
			    </logic:present> 
			    <logic:present name="CURRENT_MODEL_KEY">
			          {html:" 车型:"},modelField,
			    </logic:present>
			     <logic:present name="CURRENT_COLOR_KEY">
			          {html:" 颜色:"},colorField,
			    </logic:present>
			    <logic:present name="CURRENT_CONTROLPOINT_KEY">
			          {html:" 管制点:"},controlPointField,
			    </logic:present>
			    <logic:present name="CUSTOM_KEY">
			          <bean:write name="CUSTOM_KEY" filter="false"/>,
			    </logic:present>
			    <logic:present name="CURRENT_DEPARTMENT_KEY">
                      {html:" 部门:"},
                      {xtype:'textfield',width : 100,name : 'deptID', value:'<bean:write name="CURRENT_DEPARTMENT_NAME"/>',
                        listeners:{focus  : function(empID){GetSelDept(1);},
                                   render:function(deptID){form.getForm().getEl().dom.deptID.realValue='<bean:write name="CURRENT_DEPARTMENT_KEY"/>';} }
                      },
	            </logic:present>	  
			    <logic:present name="NEED_SELECTOR">     
			         {xtype:'button', name : 'show',text:'查询',handler : function(){
						showReport();
					}},
				</logic:present>
				<logic:notPresent name="IS_REPORT_GROUP">
				{xtype:'button',	name : 'reportSubmit',text:'保存',handler : function(){
						try{_submitTable(report1);}catch(e){ _submitRowInput(report1);}

					}},
					{xtype:'button',	name : 'print',text:'打印报表',handler : function(){
						report1_print();
					}},{xtype:'button',	name : 'saveAsPDF',text:'生成PDF',handler : function(){
						report1_saveAsPdf();
					}},{xtype:'button',	name : 'saveAsExcel',text:'生成EXCEL',handler : function(){
						report1_saveAsExcel();
					}}]
				</logic:notPresent>
				<logic:present name="IS_REPORT_GROUP">
					{xtype:'button',	name : 'print',text:'打印报表',handler : function(){
						group_print();
					}},{xtype:'button',	name : 'saveAsExcel',text:'生成EXCEL',handler : function(){
						group_saveAsExcel();
					}}]
				</logic:present>
				
				
		});
		form.render('date-form');		
	<logic:present name="CUSTOM_JAVASCRIPT">
	<bean:write name="CUSTOM_JAVASCRIPT" filter="false"/>
	</logic:present>	
		
	 function showReport(){
	
	 var parameter='';
	 var deptStr='';
	  <logic:present name="CURRENT_DATE_KEY">
        var dateKey=document.getElementById("date").value;
          dateStr=dateKey;
          <logic:present name="DATE_RANGE_FLAG">
			var endDateKey=document.getElementById("endDate").value;  
			
			parameter=parameter+'&dateKey1='+dateStr;
			parameter=parameter+'&dateKey2='+endDateKey; 
			
			dateStr=dateStr+","+endDateKey;
		  </logic:present>
			  parameter=parameter+'&dateKey1='+dateStr;
		      parameter=parameter+'&dateKey='+dateStr;		  
		</logic:present>
		<logic:present name="CURRENT_DEPARTMENT_KEY">
	     var tempValue=document.getElementById('deptID').realValue;
	     // alert(document.getElementById('deptID').value+"="+tempValue);
	     deptStr='&deptName='+document.getElementById('deptID').value;
	     if(typeof(tempValue) != 'undefined' && tempValue!=''){
	       document.getElementById('deptID').value=tempValue;  
	     }         
      </logic:present>
      			<logic:present name="CUSTOM_SUBMITSCRIPT">
	<bean:write name="CUSTOM_SUBMITSCRIPT" filter="false"/>
	</logic:present>
	    var fields =form.items; 
	    for(var i = 0 ; i < fields.length ; i++){
           var item = fields.itemAt(i);
		  
		   if(item.getXType()!='panel' && item.getXType()!= 'button'){
		    var value = item.getValue();
		   if(item.getXType() == 'datefield'){
		    var itemValue = item.value;
		       if(typeof(itemValue)!='undefined'){
		      value = itemValue;
		      }else{
		       value ='';
		          }
		   }else if(item.getXType() == 'multiSelect'){
		       //alert(item.getValue());
             parameter=parameter+'&'+item.hiddenName+'='+item.getValue();
		   }
		   else if(item.getXType() == 'combo'){
					var index = item.store.find('id',value);
					if(index != -1){
						var selItem = item.store.getAt(index);
						 parameter=parameter+'&'+item.hiddenName+'='+selItem.get('id');
						 value=selItem.get('name');
					}
					
			}
			  parameter=parameter+'&'+item.name+'='+value; 
	    }
		}
		parameter=parameter+deptStr;

	
    location.href('showReportAction.do?REPORTID=<bean:write name="CURRENT_REPORT" property="id"/>'+parameter);
    }
});
</script>
		<table id=titleTable width=100% cellspacing=0 cellpadding=0 border=0>
			<tr>
				<td height="22" width=100% valign="middle" style="font-size:13px" background="../images/ta51top2.jpg">
					<table width="100%">
						<tr>
							<td align="left" valign="middle" id="date-form">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<script language=javascript src="/servlet/com.runqian.base.util.ReadJavaScriptServlet?file=%2Fcom%2Frunqian%2Freport%2Fview%2Fhtml%2Fscroll.js"></script>
		<div id=div1 style="overflow:auto;vertical-align:top" onscroll="_reportScroll( 'report1' )">
			<table id=rpt align="center" width="100%">
				<tr>
					<td colspan="2" width="100%" height="100%">
						<logic:present name="report_define">
							<oldReport:html name="report1" 
							             srcType="definebean" beanName="report_define" 
							             funcBarLocation="top" params="<%=parameter%>" 
							             saveAsName="系统报表" needPageMark="yes" 
							             width="100%" generateParamForm="no" 
							             selectText="yes" needScroll="yes" 
							             saveAsName="<%=saveAsName%>"
                                         backAndRefresh="<%=destUrl%>"
							             width="-1"
								height="-1" />
						</logic:present>
              <logic:present name="new_report_define">
                      <logic:present name="IS_REPORT_GROUP">
                        <div id="report1">
                          <report:group groupFileName="<%=reportFileName%>" 
			                 params="<%=parameter%>"
			                 backAndRefresh="<%=destUrl%>"
		                   />
		             </div>
				     </logic:present>
                     <logic:notPresent name="IS_REPORT_GROUP">
                                  <report:html name="report1"   
							             reportFileName ="<%=reportFileName%>" 
							             funcBarLocation="top" params="<%=parameter%>" 
							             saveAsName="系统报表" needPageMark="yes" 
							             width="100%" generateParamForm="no" 
							             selectText="yes" needScroll="yes"  useCache="no"
							             saveAsName="<%=saveAsName%>"
                                         backAndRefresh="<%=destUrl%>"
                                         submit=""
							             width="-1"
								height="-1" />
				     </logic:notPresent>							
				</logic:present>
					</td>
				</tr>
			</table>
		</div>
		<div id=div2 style="width:100%;height:100%">
		</div>



		<script language=javascript>
	var obj = parent.frames[1];
	window.onresize = myResize;
	var div1 = document.getElementById( "div1" );

	function myResize() {
		var scrolldiv = document.getElementById( "report1_scrollArea" );
		if( scrolldiv == null ) div1.id = "report1_contentdiv";
		if( document.all ) {
			div2.style.display = "";
			div1.style.height = document.body.offsetHeight - titleTable.offsetHeight;
			div1.style.width = div2.offsetWidth;
			div2.style.display = "none";
		}
		else {
			var div22 = document.getElementById( "div2" );
			div22.style.display = "";
			div1.style.height = div22.offsetHeight - document.getElementById( "titleTable" ).offsetHeight;
			div1.style.width = div22.offsetWidth;
			div22.style.display = "none";
		}
		if( scrolldiv != null ) {
			div1.style.overflow = "hidden";
			scrolldiv.style.width = div1.clientWidth - 15;
			var h = div1.clientHeight - 20;
			h -= getHeightX( document.body );
			var paramTable = document.getElementById( "param_tbl" );
			if( paramTable != null ) h -= paramTable.offsetHeight;
			scrolldiv.style.height = h;
			_resizeScroll();
		}
	}
	myResize();
	
</script>
	</body>
	
</html>

