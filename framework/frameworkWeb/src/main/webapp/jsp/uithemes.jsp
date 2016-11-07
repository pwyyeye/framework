<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<head>
		<style type="text/css">
ul.templates {
	margin: .8em 0;
	padding: 0;
	list-style-type: none;
}

ul.templates li {
	float: left;
	margin: 5px;
}

ul.templates li a {
	display: block;
	padding: 2px;
	border: 3px solid #eee;
	text-decoration: none;
}

ul.templates li a:hover {
	display: block;
	border: 3px solid #cdf;
}

ul.templates li a img {
	width: 200px;
	height: 140px;
	border: none;
}

ul.templates span {
	display: block;
	color: #000;
}

ul.templates a span {
	display: none;
}
</style>
	</head>
	<body class='x-window-mc'>
		<ul class="templates">
			<li>
				<a href="javascript:"
					onclick="changeTheme('classic');">
					<span>经典蓝色</span> <img
						src="extjs/packages/ext-theme-classic/resources/images/theme.jpg"
						alt="经典蓝色" title="经典蓝色" />
				</a>
			</li>

			<li>
				<a href="javascript:"
					onclick="changeTheme('gray'); ">
					<span>灰色轨迹</span> <img
						src="extjs/packages/ext-theme-gray/resources/images/theme.jpg"
						alt="灰色轨迹" title="灰色轨迹" />
				</a>
			</li>

			<li>
				<a href="javascript:"
					onclick="changeTheme('crisp');">
					<span>经典蓝色</span> <img
						src="extjs/packages/ext-theme-crisp/resources/images/theme.jpg"
						alt="白色蓝色" title="经典蓝色" />
				</a>
			</li>

			<li>
				<a href="javascript:"
					onclick="changeTheme('neptune');">
					<span>灰蓝色</span> <img
						src="extjs/packages/ext-theme-neptune/resources/images/theme.jpg"
						alt="灰蓝色" title="灰蓝色" />
				</a>
			</li>




		</ul>
	</body>
	<script>
		Ext.EventManager.on(window, 'beforeunload', function() {
	parent.setFlag(true);
		})
function changeTheme(name) {
	var p = window;
	try {
		while (true) {
			if (p.parent && p != p.parent && p.parent.document) {
				p = p.parent;
			} else
				break;
		}
	} catch (e) {
	}
	window.parent.setParam({ theme: name })
}
</script>
</html>
