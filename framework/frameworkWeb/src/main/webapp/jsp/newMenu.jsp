<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
<html>
	<%@ include file="common.jsp"%>
	<html:base />
	<script>
Ext.onReady(function(){   
	var toolbar = Ext.create('Ext.toolbar.Toolbar', {
		items:[
          {text : '切换',iconCls:'switchMenu',handler:switchMenu,icon:'images/expand-members.gif'},
          {text : '展开',iconCls:'expandAll',handler:expandAll,icon:'images/expand-all.gif'},
          {text : '闭合',iconCls:'collapseAll',handler:collapseAll,icon:'images/collapse-all.gif'}
         ]
	});
	var centerTabPanel=Ext.create('Ext.container.Viewport', {
         layout: 'border',
         autoScroll:false ,
         animScroll:true,
         renderTo:'main_window',
         bbar:toolbar,
         defaults: {
           autoScroll:true},
           tabMargin:0,
          items:[{

         region: 'center',
        html: '<div id="menu_grid">',
        split: false,
 
            }]

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
					url : 'menuAction.do?action=custom&root=' + id,
					method : 'POST',
					reader : {
						type : 'json'
						,totalProperty : "results"
						,root : "items"
					}
				},
				folderSort : true
			});
		}
   
		var colTree = Ext.create('Ext.tree.Panel', {
			loadMask : true,
			rootVisible : false,
			animate : true,
		    border:false,
			autoScroll : false,
			hideHeaders : true,
		    layout: 'fit',
			renderTo : 'menu_grid',
			//height : document.documentElement.clientHeigh-25,
			autoFill : true,
			store : treeStore(0),
			columns : [ {
				xtype : 'treecolumn', //this is so we know which column will show the tree
				text : '组织',
				width : 180,
				sortable : true,
				dataIndex : 'text',
				locked : true
			} ]
		});
	


      colTree.on('beforeload', function(node){ 
            load.dataUrl='menuAction.do?action=custom&root='+node.id;
        
    }); 
      colTree.on('click', function(node){
         
         if(node.attributes.href!=''){             
            //Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + node.attributes.text + '"');
           loadingPage(); 
         }
        
    }); 

    
    
    
  //  centerTabPanel.render();
  //  colTree.render();
 //   treeRoot.expand();
     colTree.on("contextmenu",function(node,e)
        {
            node.select();
            e.preventDefault(); 
            var treeMenu = Ext.create('Ext.menu.Menu',{items:[
                {xtype:"button",text:"收藏",
                icon:'images/attach.gif'
       
                ,pressed:true,handler:function(){
                   Ext.Msg.confirm('提示：','确认收藏['+node.text+']功能菜单',function(btn){
                      if(btn=="yes"){
                           Ext.Ajax.request({
                               url:'favoriteAction.do?action=add&menuid='+node.id+'&menuname='+node.text,
                                success:function(request){
                                   treeRoot2.appendChild(node);  
                                    Ext.Msg.alert('结果：',"执行结果：收藏成功");
                                },
                               failure:function(){
                                     Ext.Msg.alert("结果：","执行结果：收藏失败");
                               }
                          })
                    }
                   });
                
                
                
                }}
                           
            ]});
            //定位菜单的显示位置
            treeMenu.showAt(e.getPoint());
        });
   /** 
   var load2= new Ext.tree.TreeLoader({
            dataUrl : 'favoriteAction.do?action=custom'
     
   });
   var treeRoot2=new Ext.tree.AsyncTreeNode({
                   text:'收藏夹',
                   id:'0',
                   loader:load2
        });
   var colTree2 = new Ext.tree.TreePanel({
        loadMask:true, 
        rootVisible:true,
        animate: true,
        autoScroll:true,
        el:'favorite-div',
        loader:load,
        root: treeRoot2
    });
      colTree2.on('beforeload', function(node){ 
            load2.dataUrl='favoriteAction.do?action=custom&root='+node.id;
        
    }); 
    
       colTree2.render();
    treeRoot2.expand();*/
    
    function switchMenu(){
    }
       
   function expandAll(){
      colTree.expandAll();
   }
   function loadingPage(){
     // parent.hidetree();
      parent.addNode();
   
   }
   
   function collapseAll(){ 
     colTree.collapseAll();
   }
    
    
   });
</script>

	<body>
		<div id='main_window' style='width: 100%; height: 100%;' />
	</body>
</html>

