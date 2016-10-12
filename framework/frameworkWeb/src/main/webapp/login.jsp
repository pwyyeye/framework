<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
</head>

<body>
	<div>
		<header> </header>
		<div id="container_demo">
			<div id="wrapper">
				<div id="login" class="animate form">
					<!--  <form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'> -->
					<h1>用户登录</h1>
					<form id='loginForm' action="/frameworkWeb/securityUser/toLogin" method="POST">
						<p>
							<label for="" data-icon="u">用户名</label> 
							<input id="username"
								name="username" required="required" type="text"
								placeholder="myusername or mymail@mail.com">
						</p>
						<p>
							<label for="" data-icon="p">密码&nbsp;</label> 
							<input id="password"
								name="password" required="required" type="password"
								placeholder="eg. X8df!90EO">
						</p>
						<!-- <input name="_csrf" type="hidden" value="6829blae-0a14-4920-aac4-5abbd7eeb9ee" />
						<input name="${_csrf.parameterName}" type="hidden" value="${_crsf.token}" /> -->
						<sec:csrfInput/>
						<input id="remember_me" name="remember_me" type="checkbox">
						<label for="remember_me">30天免登录(没有效果)</label>
						<p class="login button">
							<input type="submit" id="submitId" value="登录" onclick="javascript:consume();">
						</p>
					</form>
					
					<br/>
					MD5密码：<input id="passwordMd5" name="passwordMd5" type="text" size="50"><br/><br/>
					<input type="button" id="getHash" value="获取MD5加密后的密码" onclick="getHash();" />
					
					<br/><br/>
					<span style="font-family:华文中宋; color:red;">
						${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
					</span>
					
				</div>
			</div>
		</div>
</body>
</html>
<script>
    // 密码经MD5加密后，再传到后台
	function encryptionPassword() { 
    	var hash = MD5(document.getElementById("password").value);  
    	document.getElementById("password").value = hash;
   	}
    // 表单验证
	function consume() {
    	// 如果spring security配置MD5密码转换，则传到后台密码不加密
		//encryptionPassword();
	}
    // 获取MD5加密后的密码
    function getHash() { 
    	var hash = MD5(document.getElementById("passwordMd5").value);  
    	document.getElementById("passwordMd5").value = hash;
   	}
</script>
