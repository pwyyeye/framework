<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html><%@ include file="common.jsp"%>
 <script src="js/GetUserAndDept.js" language="javascript"></script>
 <script src="js/GetUser.js" language="javascript"></script>
	<html:base/>
    <controls:grid recordLabel="用户" action="initUserRoleAction.do"
     gridBody="moduleID,module:模块,role:角色,empID:工号,empName:姓名,deptID:部门ID,deptName:部门名称,levelID:层级,roleID" 
     searchFieldsString="moduleID,empID,roleID" 
     needUpdateAfterAdd="Y"
     needUpdate="Y"
     gridTitle="用户角色管理"
     extContent="
	
			var roleStore=Ext.create('Ext.data.Store', {
					model : 'idNameModel',defaultRootId : id,	nodeParam : 'id',autoLoad:true,
					proxy : {
						type : 'ajax',
						url : 'roleAction.do?action=list',
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					}})
			var userStore=Ext.create('Ext.data.Store', {
					model : 'idNameModel',defaultRootId : id,autoLoad:true,
					proxy : {
						type : 'ajax',
						url : 'usersAction.do?action=list&start=0&limit=0',
						method : 'POST',
						reader : {
							type : 'json',
							totalProperty : 'results',
							root : 'items'
						}
					}})


"				

     formString="{xtype:'systemcombo',name:'moduleID'},
                 {xtype:'idNameComBox',width : 300,store:userStore,name : 'empID',fieldLabel:'选择人员'}, 
              {xtype:'idNameComBox',width : 300,store:roleStore,name : 'role',fieldLabel:'角色'}
               "/>
</html>





