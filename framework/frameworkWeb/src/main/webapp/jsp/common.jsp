    <head>
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <link rel="stylesheet" type="text/css" href="../css/common.css">
  
  
  <script type="text/javascript" src="../extjs/include-ext.js" ></script>
    <script src="../js/common.js" language="javascript"></script>
    </head>
    
    <%
    String path = request.getContextPath();
    
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    %>
     
	<base href="<%=basePath%>">