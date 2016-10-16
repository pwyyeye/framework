<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    	登录成功！ <br>
    	当前登录的用户为：<sec:authentication property="name"/> <br>
    	由于没加token这个链接会发生CRSF拦截<a href="logout"> Logout </a>
    	<form id='logoutForm' action="/frameworkWeb/logout" method="POST">
    		<sec:csrfInput/>
    		<input type="submit" value="退出"/>
    	</form>
  </body>
</html>
