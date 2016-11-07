<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls" %>

<html:html locale="true">

<!-- Standard Content -->

<%@ include file="head.jsp" %>
<SCRIPT>
   function hidetree()
  {
      var oTable = parent.document.all("menuTd");
      oTable.style.display = "none";
  }
</SCRIPT>

<!-- Body -->

<body bgcolor="white">
<p align=center>
<!-- Tree Component -->

<table border="1" width="100%" cellspacing="0" cellpadding="0">
<tr width="100%">
     <td bgcolor="#0A246A">
     <table border="0" width="100%" cellspacing="0" cellpadding="0"><tr><td><font color='white'>系统菜单</font></td><td align="right"><a onclick="hidetree();"><img alt="关闭菜单" src="images/leftmain-close.gif"/></a></td></tr></table></td>
</tr>
<tr>
<td align=left>
  <controls:tree tree="menu_control_test"
               owenFrame="menuTree.jsp"
                style="tree-control"
        styleSelected="tree-control-selected"
      styleUnselected="tree-control-unselected"
  />
</td>
</tr></table>
</body>

</html:html>
