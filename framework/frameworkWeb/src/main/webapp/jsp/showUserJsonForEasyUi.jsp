<%@ page language="java"  pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">   
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">   
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script> 
<script type="text/javascript">
    $(function(){
        $('#dg').datagrid({    
            url:'${pageContext.request.contextPath}/user/showUserJsonForEasyUi.do',    
            columns:[[    
                {field:'userName',title:'姓名',width:100 },    
                {field:'password',title:'密码',width:100},    
                {field:'_operate',width:80,align:'center',formatter:function(value,rec){
                                                               return "<a href='' >编辑</a>";
                                                         },title:'操作'}    
            ]],
           toolbar: [{
                iconCls: 'icon-add',
                handler: function(){alert('编辑按钮')}
            },'-',{
                iconCls: 'icon-help',
                handler: function(){alert('帮助按钮')}
            }],
            fitColumns:true,
            striped:true,
            pagination:true,
            rownumbers:true,
            pageNumber:1,
            pageSize:10,
            pageList:[10,20,30,40,50]

              
        }); 
    })
    
</script>

<table id="dg"></table>