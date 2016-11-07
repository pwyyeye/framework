<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link rel="stylesheet" type="text/css" href="../css/common.css">
		<script type="text/javascript" src="../extjs/include-ext.js">
</script>
		<link href="../css/top.css" rel="stylesheet" type="text/css">
</script>
	<%
    String path = request.getContextPath();
    
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
    
    
	<base href="<%=basePath%>">
	</head>

	<script language="javascript">
resizeWin();
/*操作主窗口最大化 */
function resizeWin() {
	var sw = screen.width;
	var sh = screen.height;

	parent.window.moveTo(0, 0);
	parent.window.resizeTo(sw, sh);
}
function checkIE() {
	var ver = navigator.appVersion;
	var idx = ver.indexOf("MSIE ") + 5;
	var idx1 = ver.indexOf(";", idx);
	var sVer = ver.substring(idx, idx1);
	var iVer = parseFloat(sVer);
	if (!isNaN(iVer)) {
		if (iVer < 7) {
			alert("系统提示：您的浏览器版本为IE" + iVer + "必须是IE8及其以上版本\n请升级您的浏览器，然后重新登录系统");
		}
	}
}
function collapseTitlebar() {
	Ext.getCmp('titlebar').collapse(false);
}
window.defaultStatus = "猎娱网";

Ext.onReady(function() {
	//监听浏览器关闭事件，如果窗口关闭或者页面刷新，删除草稿
		Ext.EventManager.on(window, 'beforeunload', function() {
			if (cleanFlag) {
				Ext.Msg.alert('提示', '退出系统');
				Ext.Ajax.request( {
					url : 'logoffController/?cleanSession=y',
					success : function(response, opts) {

					}
				})
			}
		})
		var toolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				text : '切换',
				iconCls : 'switchMenu',
				handler : switchMenu,
				icon : 'images/expand-members.gif'
			}, {
				text : '展开',
				iconCls : 'expandAll',
				handler : expandAll,
				icon : 'images/expand-all.gif'
			}, {
				text : '闭合',
				iconCls : 'collapseAll',
				handler : collapseAll,
				icon : 'images/collapse-all.gif'
			} ]
		});
		Ext.define('MenuTree', {
			extend : 'Ext.data.TreeModel',
			idProperty : 'id',
			fields : [ {
				name : 'id',
				type : 'string'
			}, {
				name : 'text',
				type : 'string'
			}, {
				name : 'href',
				type : 'string'
			}, {
				name : 'hrefTarget',
				type : 'string'
			}, {
				name : 'leaf',
				type : 'boolean'
			} ]
		});
		var treeStore = function(id) {
			var me = this;
			return Ext.create('Ext.data.TreeStore', {
				model : 'MenuTree',
				defaultRootId : id,
				nodeParam : 'id',
				root : {
					id : 0,
					text : '信息化系统',
					expanded : true
				},
				proxy : {
					type : 'ajax',
					url : 'menuController/custom.do?root=' + id,
					method : 'POST',
					reader : {
						type : 'json',
						totalProperty : "results",
						root : "items"
					}
				},
				folderSort : true
			});
		}

		var colTree = Ext.create('Ext.tree.Panel', {
			region : 'center',
			rootVisible : false,
			animate : false,
			border : 0,
			autoScroll : true,
			hideHeaders : true,
			layout : 'absolute',
			autoFill : true,
			store : treeStore(0),
			columns : [ {
				xtype : 'treecolumn', //this is so we know which column will show the tree
				text : '组织',
				width : 200,
				sortable : true,
				dataIndex : 'text',
				locked : true
			} ]
		});

		var centerTabPanel = Ext.create('Ext.panel.Panel', {
			title : '系统菜单',
			region : 'west',
			border : true,
			layout : 'border',
			autoScroll : false,
			collapsible : true,
			animScroll : true,
			width : 203,
			bbar : toolbar,
			defaults : {
				autoScroll : true
			},
			tabMargin : 0,
			items : [ colTree ]

		});
		function getQueryParam(name) {
			var regex = RegExp('[?&]' + name + '=([^&]*)');

			var match = regex.exec(location.search);
			return match && decodeURIComponent(match[1]);
		}
		function addTheme(link) {
			var split = link.indexOf("?") == -1 ? "?" : "&";
			var theme = getQueryParam("theme");
			return link + split + "theme=" + theme;
		}

		var tbPanel = Ext
				.create(
						'Ext.tab.Panel',
						{
							region : 'center',
							border : false,
							xtype : 'tabpanel',
							activeTab : 0,
							deferredRender : false,
							items : [ {
								itemid : 0,
								title : '工作台',
								html : '<IFRAME frameBorder="0" id="content" name="content" scrolling="auto" src="<c:out value="${main_page}"/>"  style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"   marginwidth="0" marginheight="0"></IFRAME>'
							} ],
							listeners : {
								tabchange : function(tabPanel, newCard,
										oldCard, eOpts) {
									var newCardId = newCard.getId();
									// alert(newCardId);
									colTree.getSelectionModel().select(
											newCardId, false, true);

									//update online user
									tabChangeStore.load( {
										params : {
											action : 'save',
											method : newCardId
										},
										scope : this
									})
								},
								remove : function(tabPanel, oldCard, eOpts) {
									var itemId = oldCard.getId();
									tabChangeStore.load( {
										params : {
											action : 'cancel',
											method : itemId
										},
										scope : this
									})
								}
							}
						})
		var mainPanel = Ext.create('Ext.container.Viewport', {
			layout : 'border',
			renderTo : 'grid-div',
			//defaults : {
			//	autoScroll : true
			//},
			items : [ {
				region : 'north',
				contentEl : 'header',
				id : 'titlebar',
				//html : '<IFRAME frameBorder="0" id="top" name="top" scrolling="no" src="top.jsp" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"   marginwidth="0" marginheight="0"></IFRAME> ',
				border : false,
				height : 100,
				collapseMode : 'mini',
				collapsible : true,
				autoScroll : false,
				split : false,
				frame : false,
				header : false,
				footer : false,
				border : false,
				margin : '0 0 0 0'
			}, centerTabPanel, tbPanel ]
		});

		colTree.getSelectionModel().on('select', function(selModel, record) {
			try {
				record.expand();
				currentId = record.getId();
				//if(tbPanel){
				//    tbPanel.setActiveTab(currentId);
				//  }else{

				if (record.get("action") != "") {

					var link = addTheme(record.get("action"));
					var currentTitle = record.get('text');
					openTbPanel(currentId,currentTitle,link);

				}
			} catch (e) {
			}
			//   }

		});
		openTbPanel=function(currentId, currentTitle, link) {
			var exist_panel = tbPanel.getComponent(currentId);
			if (!exist_panel) {
				tbPanel
						.add( {
							title :currentTitle,
							id : currentId,
							closable : true,
							html : '<IFRAME frameBorder="0" id="content" name="content" scrolling="auto" src=' + link
							//	+ split
							//	+ 'theme='
							//	+ getQueryParam("theme")
							+ ' style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"   marginwidth="0" marginheight="0"></IFRAME>'
						});
			}
			tbPanel.setActiveTab(currentId);
		}
		function switchMenu() {
		}

		function expandAll() {
			colTree.expandAll();
		}
		function loadingPage() {
			addNode();
		}

		function collapseAll() {
			colTree.collapseAll();
		}

		var themeWindow = Ext
				.create(
						'Ext.window.Window',
						{
							title : "请选择一个界面风格(双击切换)",
							layout : 'border',
							id : 'themeWindow',
							modal : 'true',
							items : [ {
								region : 'center',
								html : '<IFRAME frameBorder="0" width="100%" height="100%"  src="uithemes.jsp" > </IFRAME>'
							} ],
							width : 480,
							height : 400,
							closable : false,
							autoScroll : false,
							buttonAlign : 'center',
							bbar : [ '->', {
								text : '关闭',
								handler : function() {
									themeWindow.hide();
								}
							} ]
						});

		var roleWindow = Ext
				.create(
						'Ext.window.Window',
						{
							//title:"请选择一个界面风格",
							layout : 'border',
							id : 'roleOpenWinder',
							items : [ {
								region : 'center',
								html : '<IFRAME frameBorder="0" width="100%" height="100%"  src="switchRoleController/" > </IFRAME>'
							} ],
							width : 500,
							height : 400,
							closable : false,
							modal : 'true',
							buttonAlign : 'right',
							autoScroll : false,
							bbar : [ '->', {
								text : '关闭',
								handler : function() {
									roleWindow.hide();
								}
							} ]
						});
		var usersWindow = Ext
				.create(
						'Ext.window.Window',
						{
							//title:"请选择一个界面风格",
							layout : 'border',
							id : 'usersWindow',
							items : [ {
								region : 'center',
								html : '<IFRAME frameBorder="0" width="100%" height="100%"  src="userPropertiesController/" > </IFRAME>'
							} ],
							width : 500,
							height : 400,
							closable : false,
							modal : 'true',
							buttonAlign : 'right',
							autoScroll : false,
							bbar : [ '->', {
								text : '关闭',
								handler : function() {
									usersWindow.hide();
								}
							} ]
						});

		//定时器
		var tabChangeStore = new Ext.data.JsonStore( {
			storeId : 'tabChangeStore',
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : 'noticeController/save.do',
				reader : {
					type : 'json',
					totalProperty : 'results',
					root : 'items'
				}
			},
			fields : [ 'message' ]
		});
		var taskStore = new Ext.data.JsonStore( {
			storeId : 'taskStore',
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : 'noticeController/custom.do',
				reader : {
					type : 'json',
					totalProperty : 'results',
					root : 'items'
				}
			},
			fields : [ 'message' ]
		});

		var updateClock = function() {
			//alert('clock');
			taskStore.load( {
				params : {
					action : 'custom'
				},
				callback : function(records, operation, success) {
					if (records.length > 0) {
						//var returnMessage = records[0].get('message');
						if (typeof (records[0].get('message')) != undefined) {
							if (records[0].get('message').indexOf(
									'USER_OFFLINE_COMMAND') > -1) {
								alert('当前用户已注销，请重新登录!');
								window.location.href = 'logoutAction.do';
							} else {
								Ext.Msg.alert('系统信息提示', records[0]
										.get('message'));
							}
						}

					}
				},
				scope : this
			})
		};
		var runner = new Ext.util.TaskRunner(), task = runner.start( {
			run : updateClock,
			interval : 60 * 1000 * 3000  //1分钟，临时关闭
		});

		//clean online message
		var closeSystem = function() {
			tabChangeStore.load( {
				params : {
					action : 'importExcel'
				},
				scope : this
			})

		}

	});

function showRoleMenu() {
	cleanFlag = false;
	Ext.getCmp('roleOpenWinder').show();
}
function showThemeMenu() {
	cleanFlag = false;
	Ext.getCmp('themeWindow').show();
}
function showUserProperty() {
	Ext.getCmp('usersWindow').show();
}

function setFlag(flag) {
	cleanFlag = flag;
}
function showMantis(){
	openTbPanel('-99','系统问题点反馈','http://semhq23/mantis/bug_report_page.php');
}
function setParam(param) {
	var queryString = Ext.Object.toQueryString(Ext.apply(Ext.Object
			.fromQueryString(location.search), param));
	location.search = queryString;
}
</SCRIPT>
	</head>
	<BODY scroll='no' style="MARGIN: 0px" id='all'>
		<div id="header" class="wlbg">
			<div class="mtop">
				<div class="mtop_cont">
					<div class="welcome">
						<span><img src="images/ico_user.gif" width="14" height="15"
								align="absMiddle"> </span>
						<c:if test="${LOGON_USER!=null}">					
							<span> 
							<c:out value="${LOGON_USER.empName}"/>
							</span>
							<c:set var="LOGON_ORGANISE" scope="page" value="${LOGON_USER.organise}"/>
							<span> 机构： <c:out value="${LOGON_ORGANISE.name}"/></span>
							<span> &nbsp;&nbsp;&nbsp;角色：</span>
							
							<c:set var="current_role" scope="page" value="${LOGON_USER.role}"/>
							<c:out value="${current_role.name}"/>
							</span>
						</c:if>
						<c:if test="${MODULE!=null}">	
							<span>&nbsp;&nbsp;&nbsp;系统： <c:out value="${MODULE.name}"/> </span>
						</c:if>
					</div>
					<div id="collapseBtn" onclick="collapseTitlebar()" title='隐藏标题栏'></div>
					<div class="oper">
						<a href="/portal/semerpClient.jnlp" target="_blank"><i
							class="ico_mtop_client"></i>APP下载</a>
						<a href="logoffController/" target="_top"><i
							class="ico_mtop_exit"></i>安全退出</a>
					</div>
				</div>
			</div>
			<div class="top">
				<div class="top_cont">
					<div class="t_nametab">
						<table>
							<tr>
								<td>
									&nbsp;&nbsp;
									<c:if test="${LOGON_USER.organise!=null}">	
									    <c:set var="CURRENT_ORGANISE" scope="page" value="${LOGON_USER.organise}"/>
										<img src='<c:out value="${CURRENT_ORGANISE.region}"/>'/>
			     						<span class='compname'>
			     							<c:out value="${CURRENT_ORGANISE.regionabbr}"/>
			     						</span>
									</c:if>
									<c:if test="${LOGON_USER.organise==null}">
									<img src="images/company_logo.jpg" />
									<span class="compname">&nbsp;&nbsp;诚信&nbsp;&nbsp;人本&nbsp;&nbsp;创新&nbsp;&nbsp;品质</span>
								    </c:if>
								</td>
							</tr>
						</table>
					</div>
					<div class="t_nav_li">
						<ul id="bigTitleId">
							<li onclick="showRoleMenu(this, 134);">
								<div class="imgwap">
									<b class="j_ico_fuwu"></b>
								</div>
								<a href="javascript:;">切换角色</a>
							</li>

							<li onclick="showThemeMenu(this, 134);">
								<div class="imgwap">
									<b class="j_ico_sns"></b>
								</div>
								<a href="javascript:;">界面风格</a>
							</li>

							<li onclick="showUserProperty(this, 134);">
								<div class="imgwap">
									<b class="j_ico_user"></b>
								</div>
								<a href="javascript:;">个人参数</a>
							</li>

							<li>
								<a class="imgwap" onclick="showMantis();"> <b
									class="j_ico_service"></b>
									<div>
									<a href="javascript:;">问题反馈</a>
									</div> </a>
							</li>
						</ul>
					</div>
					<div class="t_ad">
						<ul id="jinad" class="ad_ul">
						<c:if test="${LOGON_USER.organise!=null}">	
									    <c:set var="CURRENT_ORGANISE" scope="page" value="${LOGON_USER.organise}"/>
			     							<c:out value="${CURRENT_ORGANISE.remark}"/>
						</c:if>	
						</ul>
					</div>
					<div class="cb"></div>
				</div>
			</div>
			<div id='grid-div' style='width: 100%; height: 100%;' /></div>
	</BODY>
</html>
