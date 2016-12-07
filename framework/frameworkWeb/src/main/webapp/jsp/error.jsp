<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<html:html locale="true">
<BODY bgCOLOR="white" TOPMARGIN="0" LEFTMARGIN="0" RIGHTMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0">
<!-- Title Bar Start -->
<table width="100%" cellpadding=0 cellspacing=0 border=0>
  <tr>
    <td bgcolor="#669900" align=left><img src="images/admin_top_banner3.gif"></td>
  </tr>
  <tr>
    <td bgcolor="#D1FFFE"><img src="images/admin_banner_photo.jpg"></td>
    <td bgcolor="#D1FFFE">&nbsp;</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" height=1 colspan=2><img src="images/clear.gif" width="100" height="1" alt="" border="0"></td>
  </tr>
</table>
<!-- Title Bar End -->

<!-- START Content  -->
<p class="pagetitle-md">系统发生异常</p>
<BR>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="5">
<tr><td><html:errors/></td></tr>
<TR>
  <TD>
  <logic:present name="errorBean">
  <bean:write name="errorBean" property="errMessage"/>
  
     <br><br>
    &nbsp;<a href='<bean:write name="errorBean" property="link"/>'>返回前一个页面</a>
    </logic:present>
  </TD>
</TR>
</TABLE>
<!-- END Content  -->

</td></tr></table>
<!-- END Padding for Content -->

</BODY>
</html:html>