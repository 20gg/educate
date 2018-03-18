<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>奖学金记录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css" />

  </head>
  
  <body>
     <div class="main-right">
  	<div style="margin-top: 20px;">
  	<h2 style="color: green; font-weight: bold;">奖学金记录</h2>
  	</div>
  	 
  	<div  class="main-table">
    <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    		<td height="30" width="5%" id="xuhao">序号</td>
    		 
    		<td height="30" width="13%">课程名</td>
    		<td height="30"width="14%">消费金额</td>  
    		 
    		<!-- <td  height="30" width="13%">操作</td> -->
    		<td  height="30" width="13%">日期</td>
    		</tr>
    		<c:forEach items="${log_list}" var="log_list" varStatus="status"> 
    				<tr>
    					<td height="30" class="id"> ${status.count}</td>
    				 
    					<td height="30" id="userid">${log_list.c_name}</td>
    					<td height="30">
    		 <fmt:formatNumber type="number" value="${log_list.rebate/100}" pattern="0.00" maxFractionDigits="2"/>元	
    					
    					</td>
    					 				
    					<td height="30">${log_list.date}</td>
    		
    				</tr>
    		
    		</c:forEach>
    		</table>
    		
    		
    		
    		
  <div class="page">
<ul>
 <li style="list-style: none;">共${count}条</li>
 <li style="list-style: none;">第<span id="p_order_num">${page_num}</span>/<span id="p_order_count">${page_count}</span>页</li>
<li style="list-style: none;"><a   href="javaScript:" onclick="last_page();" style="color: #C90E02;">上一页</a></li>
 <li style="list-style: none;"><a   href="javaScript:" onclick="next_page();" style="color: #C90E02;">下一页</a></li>
 
</ul>
</div>  		
 </div>   		
   </div> 		
    <div class="clear"></div>
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"></script>
   <script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript">
function last_page(){
		
		var page_num = $("#p_order_num").text();
		var page_count = $("#p_order_count").text();
		
		var this_num = Number(page_num);
		var count = Number(page_count);
	

		if(this_num <= 1){
			alert("已经是第一页了！");
			return;
		}else{
			var page = this_num -1;
			var datas = {"page":page};			
			var url = getRootPath()+"/user/show_all_user_page.do";
		    jspost(url, datas); 
		}
	}
function next_page(){
		
		var page_num = $("#p_order_num").text();
		var page_count = $("#p_order_count").text();
		
		var this_num = Number(page_num);
		var count = Number(page_count);
		
		
		 
		if(this_num >= count){
			alert("已经是最后一页了！");
			return;
		}else{
			var page = this_num +1;
			var datas = {"page":page};			
			var url = getRootPath()+"/user/show_all_user_page.do";
		    jspost(url, datas); 
		}
	}
    </script>
    
</html>
