<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls" %>



<html:html >

<!-- Standard Content -->

<head>
  <html:base/>

  <link rel="stylesheet" type="text/css" href="css/StylesPurple.css">
  <link rel="stylesheet" type="text/css" href="tree-control-test.css">
  <link rel="stylesheet" type="text/css" href="css/eis.css">
  <STYLE>

 BODY {
	BORDER-RIGHT: #999999 0px solid;
	BORDER-TOP: #999999 0px solid;
	SCROLLBAR-FACE-COLOR: #B3BDDC; /*滚动条前景色*/
	FONT-SIZE: 12px;
	MARGIN: 0px;
	BORDER-LEFT: #999999 0px solid;
	SCROLLBAR-SHADOW-COLOR: #B3BDDC; /*滚动条阴影色*/
	SCROLLBAR-3DLIGHT-COLOR: #B3BDDC; /*滚动条边框色*/
	LINE-HEIGHT: 18px;
	SCROLLBAR-ARROW-COLOR: #ffffff; /*滚动条上下箭头色*/
	SCROLLBAR-TRACK-COLOR: #E2E6F5; /*滚动条背景色*/
	BORDER-BOTTOM: #999999 0px solid;
	FONT-FAMILY: Arial,宋体,Arial,Sans-serif;
	SCROLLBAR-DARKSHADOW-COLOR: #ffffff; /*不管它了*/
	BACKGROUND-COLOR: #F4F6FD
}
  </STYLE>
</head>

<!-- Body -->
<logic:present name="CURRENT_MENU">
   <body onload="location.href='#menu<bean:write name="CURRENT_MENU"/>';">
</logic:present>
<logic:notPresent name="CURRENT_MENU">
  <body>
</logic:notPresent>



<p align=center>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
<tr>
<td align=left>


    <controls:tree tree="menu_control_test"
               owenFrame="tree-control-Test.jsp"
  />
</td>
</tr>
</table>

</p>
</body>

</html:html>
