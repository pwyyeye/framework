<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>

<%@ page import="common.value.ReportModuleVO,org.jdom.*,java.lang.*,java.io.*"%>
<%@ include file="common.jsp"%>
<script src="js/report.js" language="javascript"></script>
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


	<logic:present name="CURRENT_LINE_KEY">
		<controls:lineCombo initValueObject="CURRENT_LINE_KEY" allowBlank="Y" />
	</logic:present>
	<logic:present name="CURRENT_COLOR_KEY">
		<controls:colorCombo initValueObject="CURRENT_COLOR_KEY" allowBlank="Y" />
	</logic:present>
	<logic:present name="CURRENT_WORKSHOP_KEY">
		<controls:workshopCombo initValueObject="CURRENT_WORKSHOP_KEY" allowBlank="Y" />
	</logic:present>
	<logic:present name="CURRENT_SERIES_KEY">
		<controls:seriesCombo initValueObject="CURRENT_SERIES_KEY" allowBlank="Y" />
	</logic:present>
	<logic:present name="CURRENT_MODEL_KEY">
		<controls:modelCombo initValueObject="CURRENT_MODEL_KEY" allowBlank="Y" />
	</logic:present>
	<logic:present name="CURRENT_CONTROLPOINT_KEY">
		<controls:controlPointCombo initValueObject="CURRENT_CONTROLPOINT_KEY" allowBlank="Y" />
	</logic:present>

	<script language=javascript src="/servlet/com.runqian.base.util.ReadJavaScriptServlet?file=%2Fcom%2Frunqian%2Freport%2Fview%2Fhtml%2Fscroll.js"></script>
	<script>
       Ext.onReady(function(){
          <logic:present name="CUSTOM_JAVASCRIPT">
	<bean:write name="CUSTOM_JAVASCRIPT"  filter="false"/>
	</logic:present>
       <logic:present name="CURRENT_DATE_KEY">
          var searchDate=Ext.create('Ext.form.DateField',{
             fieldLabel:'日期',
	         name : 'date',
	         id : 'startDateID',
	         width : 150,
	         format :'<bean:write name="CURRENT_REPORT" property="needDateKeyStr"/>',
	         //menu:new DatetimeMenu()
	      }); 
	      searchDate.setValue('<bean:write name="CURRENT_DATE_KEY"/>');
	      <logic:present name="DATE_RANGE_FLAG">
	           var searchEndDate=Ext.create('Ext.form.DateField',{
	            name : 'endDate',
	             id : 'endDateID',
	              fieldLabel:'至',
	            width : 150,
	           format :'<bean:write name="CURRENT_REPORT" property="needDateKeyStr"/>', 
	           //menu:new DatetimeMenu()
	       });
	    searchEndDate.setValue('<bean:write name="END_DATE_KEY"/>');
	      </logic:present> 
	   </logic:present>
  
	var form =Ext.create('Ext.FormPanel',{
			labelSeparator : "：",
			frame:true,
			border:false,
			layout:'form',
			  items:[
			  <logic:present name="CURRENT_DATE_KEY">			       
			        searchDate,
			        <logic:present name="DATE_RANGE_FLAG">
			          searchEndDate,
			        </logic:present>        
			   </logic:present>
			   <logic:present name="DATE_FIELD_HTML">
			      <bean:write name="DATE_FIELD_HTML" filter="false"/>
			   </logic:present>
			   <logic:present name="TIME_FIELD_HTML">
			      <bean:write name="TIME_FIELD_HTML" filter="false"/>
			   </logic:present>
			    <logic:present name="CURRENT_LINE_KEY">
			          lineField,
			    </logic:present>
			    <logic:present name="CURRENT_WORKSHOP_KEY">
			          workshopField,
			    </logic:present> 
			    <logic:present name="CURRENT_SERIES_KEY">
			         seriesField, 
			    </logic:present> 
			    <logic:present name="CURRENT_MODEL_KEY">
			          modelField,
			    </logic:present>
			     <logic:present name="CURRENT_COLOR_KEY">
			          colorField,
			    </logic:present>
			    <logic:present name="CURRENT_CONTROLPOINT_KEY">
			         controlPointField,
			    </logic:present>
			    <logic:present name="CUSTOM_KEY">
			          <bean:write name="CUSTOM_KEY" filter="false"/>,
			    </logic:present>
			    <logic:present name="CURRENT_DEPARTMENT_KEY">
                      {xtype:'textfield',fieldLabel:"部门",width : 100,name : 'deptID',id:'deptID', value:'<bean:write name="CURRENT_DEPARTMENT_NAME"/>',
                        listeners:{focus  : function(empID){GetSelDept(1);},
                                   render:function(deptID){form.getForm().getEl().dom.deptID.realValue='<bean:write name="CURRENT_DEPARTMENT_KEY"/>';} }
                      },
	            </logic:present>	  

					{html:" "}]
		});
		//form.render('date-form');
		
		   var win =Ext.create('Ext.Window',{
                el:'hello-win',
                layout:'fit',
                width:500,
                height:400,
                closeAction:'hide',
                plain: true,
                modal:true,
                collapsible: true,
                maximizable:false,
                draggable: true,
                closable: false,
                resizable:false,
                items:form,
                buttons: [{
                    text:'报表查看',
                    handler: function(){
                        if(form.form.isValid()){
                                showReport();
                               
                     }
                     }
                }]
            }
           
            );           
        win.show();
     	
	 function showReport(){

	 var parameter='';
	 var deptStr='';
	  <logic:present name="CURRENT_DATE_KEY">
        var dateKey=searchDate.getRawValue();
          dateStr=dateKey;
          <logic:present name="DATE_RANGE_FLAG">
			var endDateKey=searchEndDate.getRawValue();  
			
			parameter=parameter+'&dateKey1='+dateStr;
			parameter=parameter+'&dateKey2='+endDateKey; 
			
			dateStr=dateStr+","+endDateKey;
		  </logic:present>
		      parameter=parameter+'&date='+dateStr;	 
			  parameter=parameter+'&dateKey1='+dateStr;
		      parameter=parameter+'&dateKey='+dateStr;		       
		</logic:present>
		<logic:present name="CURRENT_DEPARTMENT_KEY">
	     var tempValue=Ext.getCmp('deptID').realValue;
	     // alert(document.getElementById('deptID').value+"="+tempValue);
	     deptStr='&deptName='+Ext.getCmp('deptID').value;
	     if(typeof(tempValue) != 'undefined' && tempValue!=''){
	       Ext.getCmp('deptID').value=tempValue;  
	     }         
      </logic:present>
      		<logic:present name="CUSTOM_SUBMITSCRIPT">
	           <bean:write name="CUSTOM_SUBMITSCRIPT"  filter="false"/>
	    </logic:present>
	    var fields =form.items.items; 
	    for(var i = 0 ; i < fields.length ; i++){
           var item = fields[i];
		  
		   if(item.getXType()!='panel' && item.getXType()!= 'button'){
		    var value = item.getValue();
		   if(item.getXType() == 'datefield'){
		     var itemValue = item.getRawValue();
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
			   var ids='';
			   var comboValue='';
			   for(var j=0;j<item.lastSelection.length;j++){
					
						if(j!=0) {ids=ids+',';comboValue=comboValue+',';}
						ids=ids+item.lastSelection[j].data.id;
						comboValue=comboValue+item.lastSelection[j].data.name;
					
				}
			   	 parameter=parameter+'&'+item.hiddenName+'='+ids;
				 //value=selItem.get('name');				
			     value=comboValue;
			}
			  parameter=parameter+'&'+item.name+'='+value; 
			
	    }
		}
		parameter=parameter+deptStr;
	    window.location.href='showReportAction.do?action=list&REPORTID=<bean:write name="CURRENT_REPORT" property="id"/>'+parameter;
    }
});
</script>

	<body>
		<div id='hello-win'></div>
	</body>



</html>

