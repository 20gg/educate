<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
     
    
    
    <title>订单管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
 
<script type="text/javascript"
	src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
	<link rel="stylesheet" href="<%=basePath%>css/login/button.css?v=3">
	
	<style type="text/css">
.btns {
	background: #fff;
	text-align: center;
	width: 40px;
	height: 30px;
	padding: 0;
	line-height: 30px;
	margin: 0;
}

table td {
	font-size: 11px;
}

table th {
	font-size: 13px;
	font-weight: bold;
}

#fromgo td {
	height: 40px;
}

#fromgo input {
	height: 26px;
	font-size: 12px;
	border: 1px solid #ddd;
}

#fromgo select {
	height: 26px;
	font-size: 12px;
	border: 1px solid #ddd;
}

#fromgo .leftname {
	text-align: center;
	font-weight: 600;
	font-size: 12px;
	color: #333;
}

.choose {
	width: 120px;
	height: 30px;
}

.btn {
	min-width: 50px;
	height: 35px;
	margin-top: 3px;
	font-size: 12px;
	line-height: 12px;
}

.pagebtn {
	float: left;
	width: 55px;
	max-width: 100px;
	line-height: 20px;
	text-align: center;
	margin-left: 5px;
	font-size: 11px;
}

.green {
	color: #07CC3F
}

#goodsmenu td {
	height: 25px
}

.textli {
	margin-top: 6px;
	margin-bottom: 6px;
	display: inline-block;
	 
	height:auto; 
	
}

.textli input,.textli select{
	width:200px
}
.input_csss{
	margin-top: 50px;
	margin-left:50px;
	background-color: green;
	color: #fff;
	border-radius:20px;
	width: 100px;
	height: 40px;
	line-height: 40px;
}

.but_page{ 
	background-color: #5CB85C;
	color: #fff;
	border-radius:15px;
	width: 80px;
	height: 30px;
	line-height: 30px;
	border: none;
}
</style>
<style type="text/css" mce_bogus="1">
table td {
	white-space: nowrap;
}

table tr {
	height: 38px;
}
</style>

<link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css"
	type="text/css"></link>
  </head>
  
  <body>
      
  	 <div class="row" style="padding-top: 10px;">

	<div class="col-xs-12">
		<form action="<%=basePath%>course/shows_course_order.do"
			method="post" id="formhead">

			<div class="tools tooldiv">



				<ul class="toolbar3" style="width: 96%;">
						 
						<li class="textli" > 日期： </li>
						<li class="textli">
						<input type="text"
							value="${map.mindate }"  name="mindate" id="ordermindate"
							readonly="readonly" placeholder="最小时间"
							class="choose laydate-icon layui-input"
							onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</li>
						<li class="textli">
						<input type="text"
							value="${map.maxdate }" name="maxdate" id="ordermaxdate"
							readonly="readonly" placeholder="最大时间"
							class="choose laydate-icon layui-input"
							onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						</li>

					 

					<li class="textli">每页：<input type="text" name="ps"
						class="choose" value="${map.ps}" style="width:40px;">
						行</li>

					
					
					<%-- <li style="float: right;" onclick="downloadexcel()" class="btn"><span><img
							src="<%=basePath%>css/login/fz.png"> </span>导出</li> --%>
					
					<li style="float: right;" onclick="chongzhiss();" class="btn"><span><img
							src="<%=basePath%>css/login/s3.png"> </span>重置</li>
						
					<li style="float: right;" onclick="gosearch();" class="btn"><span><img
							src="<%=basePath%>css/login/s2.png"> </span>搜索</li>
				</ul>



			</div>
 
		</form>

	</div>
								 
  	  <div style="margin-top: 10px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">课程订单 </div>
  	
  	
    <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    		<td height="30" width="5%" id="xuhao">序号</td>
    		<td height="30" width="13%">昵称</td>    		
    		<td height="30" width="13%">订单课程</td>
    		  	
    		 <td height="30"width="14%">付费</td>  	
    		<td  height="30" width="13%">支付日期</td>
    		<td height="30"width="14%">有效期</td>
    		</tr>
    		
    		
    		<c:forEach items="${course_order.list}" var="course_order" varStatus="status"> 
    				<tr>
    			
    					<td height="30" class="id"> ${status.count}</td>
    					<td>${course_order.name}</td>
    					<td height="30" id="userid">${course_order.c_name}</td>
    					
    					
    					<td height="30">
    					<fmt:formatNumber type="number" value="${course_order.pay_money/100}" pattern="0.00" maxFractionDigits="2"/>
    				 
    					</td>					
    					<td height="30">
    			
    					${course_order.date}
    					</td>
    		<td height="30">${course_order.safe_year}</td>
    				</tr>
    		
    		</c:forEach>
    		</table>
    		
  <div class="page">
<div style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 14px;">

									<span>共<b style="color:#666">${course_order.rowCount }</b>条</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
										style="color:#666">第${course_order.pageNum
										}/${course_order.pageCount }页</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button"  class="but_page"
										id="prePage">上一页</button>
									&nbsp;&nbsp;&nbsp;
									<button type="button" class="but_page"  
										id="nextPage">下一页</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 跳转到
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<form action="<%=basePath%>course/shows_course_order.do"
										method="post" style="float:right" id="pageform">
										<input type="text" name="page" id="pninfo"
											style="width:60px;height:30px;border: 1px solid #5CB85C; text-align: center; font-size: 16px;" onkeyup="priceKeyup(this)"
											maxval="${course_order.pageCount}" value="${course_order.pageNum}">
										&nbsp;&nbsp;&nbsp; <input type="hidden" id="pageCount"
											value="${course_order.pageCount}">
										 <input
											type="hidden" name="mindate" value="${map.mindate }">
										<input type="hidden" name="maxdate" value="${map.maxdate }">

										
										<input type="hidden" name="ps" value="${map.ps }">
										
										 <input type="button"
											value="GO"class="but_page" id="goPage">
									</form>
								</div>
</div>  		
 </div>   		
   
    <div class="clear"></div>
    
   <!--  <div class="isearch">
	 		<div class="search-but"><a><input  type="button" value="EXCEL导出" class="input_csss" style="border: none;" onclick="downloadexcel()"/></a></div>
	 </div> -->
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"></script>
   <script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/url.js"></script>
    <script type="text/javascript">
    $(function(){
    	$("#prePage").click(function(){
    		var pn=parseInt($("#pninfo").val());
    		pn--;
    		if(pn<1){
    			alert("已是第一页");
    			return;
    		}
    		$("#pninfo").val(pn);
    		$("#pageform").submit();
    	});
    	
    	$("#nextPage").click(function(){
    		var pn=parseInt($("#pninfo").val());
    		var pc=parseInt($("#pageCount").val());
    		pn++;
    		if(pn>pc){
    			alert("已是最后一页");
    		return;
    		}
    		$("#pninfo").val(pn);
    		$("#pageform").submit();
    	});
    	
    	$("#goPage").click(function(){
    		$("#pageform").submit();
    	});

    	
    	
    	
    	$("#chooseAll").click(function(){

    $(".oneorder").prop("checked",this.checked);
    });
    });
    

    function gosearch(){
    	$("#formhead").submit();
     
    }

    function chongzhiss(){
     	window.location.href="<%=basePath%>course/shows_course_order.do";
     }
    
    
    function downloadexcel() {
  	  
		 var mindate = $("#ordermindate").val();
		var maxdate = $("#ordermaxdate").val(); 
		   
		var datas = {"page":1,"mindate":mindate,"maxdate":maxdate};			
		var url = getRootPath()+ '/course/downloadexcel.do';
		 
		jspost(url, datas); 

  }
    </script>
    
</html>
