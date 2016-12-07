<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=7">

<title>您的订单已经提交成功，感谢你的支持！</title>
<%@ include file="common.jsp"%>	
<meta name="Author" content="goingta,QQ860180810"> 
<meta name="Copyright" content="zzfhw.taobao.com,yun.not3.com,All Rights Reserved"> 
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=0.3,maximum-scale=1.0">
<link href="./finish/main.css" rel="stylesheet" type="text/css">
<link href="./finish/index.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="./finish/notorder.js"></script>
<link rel="stylesheet" href="./finish/progressbar.css">
<script src="./finish/jquery-1.9.1.min.js"></script>
</head>

<%@ page import="common.temp.vo.TempOrderVo"%>
<% TempOrderVo vo=(TempOrderVo)request.getAttribute("vo") ;%>
<body>
<div class="okDiv">
<span id="jdt" style="display: none;">
<section class="container" style="display: none;">
<div class="progress"><span class="orange" style="width: 100%;"><span style="display: inline-block; width: 100%;">订单已经处理成功&nbsp;&nbsp;&nbsp;&nbsp;</span></span> </div>
</section>
</span>
<script type="text/javascript">
	function loading(percent){
		$('.progress span').animate({width:percent},1000,function(){
			$(this).children().html(percent);
            if(percent=='100%'){
                $(this).children().html('订单已经处理成功&nbsp;&nbsp;&nbsp;&nbsp;');
                $('.container').fadeOut();
                document.getElementById("jdt").style.display='none';
                document.getElementById("t0").style.display='none';
                document.getElementById("t1").style.display='block';
                document.getElementById("t2").style.display='block';
            }
		})
	}
</script>
<script type="text/javascript">loading('20%');</script> 
    <div class="top">
      <div class="chenggong">
      <div id="t0" style="display: none;">订单处理中，请耐心等待……</div>
      <div id="t1" style="display: block;">恭喜<span style="color:#FF8000;"><%=vo.getName() %></span>，提交成功</div>
      <div id="t2" style="display: block;">我们将马上安排发货，很快将可以送到你的手上。祝您生活愉快！</div>
      </div>
    </div>
      <div class="content">
        <!-- 返回订单信息 -->
       <script type="text/javascript">loading('40%');</script> 
        订单号：no.201611<%=vo.getId()%><br>
姓名：<%=vo.getName() %><br>
电话：<%=vo.getTel() %><br>
产品：英伦风/马丁靴、<%="尺码："+vo.getSize()+", 颜色："+vo.getColor()+", 共"+vo.getNum()+"双。" %><br>

付款方式：
          <img src="./finish/payb.gif" border="0">
          <br>

地址：<%=vo.getAddress()%><br>
      </div>
      <div class="bottom">
        <!-- 返回支付方式 -->
        <script type="text/javascript">loading('60%');</script> 
        
      <div class="btnDiv">
      
      <a href="index.html"><img src="./finish/btn5.gif" alt="" border="0/"></a>
      
      </div>
<script>document.title="您的订单已经提交成功，感谢你的支持！";</script>

      </div>
</div>

<script type="text/javascript">
var t=65;
var kaishi;
kaishi=setInterval("testTime()",500);
function testTime() {   
 if(t < 101){   
     loading(t+'%');
     t=t+5;   //   计数器 
 }
} 
</script>

</body></html>