<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="common.jsp" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="./include/main.css">
<link href="css/top.css?version=201412231" rel="stylesheet" type="text/css">
<SCRIPT>

</SCRIPT>
</head>
<body>

  	<div class="wlbg">
		<div class="mtop">
			<div class="mtop_cont">
				<div class="welcome">
					<span><img src="images/ico_user.gif" width="14" height="15" align="absMiddle"> </span>
					<logic:present name="LOGON_USER"><span> <bean:write name="LOGON_USER" property="empName"/> </span>
					<span> 所属部门： <bean:write name="LOGON_USER" property="unitName"/> </span>
					<span> &nbsp;&nbsp;&nbsp;角色：</span><bean:define id="current_role",name="LOGON_USER",property="role"/><bean:write name="current_role" property="name"/></span></logic:present>
                    <logic:present name="MODULE"><span>&nbsp;&nbsp;&nbsp;系统： <bean:write name="MODULE" property="name"/></span></logic:present>
				</div>
				<div id="collapseBtn" onclick="collapseTitlebar()" title='隐藏标题栏'></div>
				<div class="oper">
   				    <a href="http://www.jingoal.com/valueadd/pcfunc_intromain.html" target="_blank"><i class="ico_mtop_client"></i>客户端</a>
					<a href="logoffAction.do" target="_top"><i class="ico_mtop_exit"></i>安全退出</a>
				</div>
			</div>
		</div>
		<div class="top">
		    <div class="top_cont">
		    	<div class="t_nametab">
		    	    <table>
		    	        <tr>
		    	        	<td>
		    	        	   &nbsp;&nbsp;<img src="images/company_logo.jpg" />
		    	        	    <span class="compname">&nbsp;&nbsp;诚信&nbsp;&nbsp;敬业&nbsp;&nbsp;创新&nbsp;&nbsp;快乐&nbsp;&nbsp;感恩</span>
		    	        	</td>
		    	        </tr>
		    	    </table>
		    	</div>
		 		<div class="t_nav_li">
		  			<ul id="bigTitleId">
		  				<!-- 应用 -->
		  				<li id="bigTitle_app" onclick="tm._showPopMenu('pop_jinapp_navli',this, 134);">
		  					<div class="imgwap"><b class="j_ico_more"></b></div>
		  					<a href="javascript:;">应用模块</a>
		  				</li>
		  				<!-- 推荐 -->
		  				<li>
		  					<div class="imgwap"><b class="j_ico_fuwu"></b></div>
		  					<a href="switchRoleAction.do" target="content">切换角色</a>
		  				</li>
		  				<!-- 企业社区 -->
		  				<li>
		  					<a class="imgwap" target="_blank" href="/mgt/community/fastLoad.jsp">
		  						<b class="j_ico_sns"></b>
			  					<div>界面风格</div>
			  				</a>
		  				</li>
		  				<!-- 客服 -->
		  				<li>
		  					<a class="imgwap" href="javascript:showOnline();">
		  						<b class="j_ico_service"></b>
			  					<div>即时通讯</div>
			  				</a>
		  				</li>
		  			</ul>
		  		</div>
		  		<div class="t_ad">
		 			<ul id="jinad" class="ad_ul">绿化美化地球，成为世界级卓越品牌公司</ul>
		 		</div>
		  		<div class="cb"></div>
		</div>
	</div>
</body>

</html>
