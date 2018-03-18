<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath %>js/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css" />

  </head>
    <style type="text/css">
  		body{
  		width: 100%;
  		height: 100%;
  		margin: 0;
  		background-color: #fff;
  		}
  		.main-right{
  		
  		}
  </style>
  <body>
  
   <div class="main-right">课程管理 >
    		<strong>课程详情 </strong>
    </div>
   
    
    <div  class="main-table" style="margin-top:50px;">
    <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		
    		<tr>
    		<td height="30" width="13%">课程名称:</td>
    		<td>${course.c_name}</td>
    		</tr>  
    		<tr>
    			<td>课程分类:</td>
    			<td>
    			<c:if test="${course.type==0}">音频</c:if>
	    		<c:if test="${course.type==1}">视频</c:if>
    			</td>
    		</tr>	 		 
    		<tr>
    		<td height="30"width="14%">课程介绍:</td>
    		<td>${course.text}</td>
    		</tr>
    		<tr>
    		<td height="30"width="10%">视频ID:</td>
    		<td>${course.url}</td>
    		</tr>
    		
    		<tr>
    		<td height="30"width="10%">视频有效期:</td>
    		<td>${course.safe_date}</td>
    		</tr>
    		
    		<tr>
    		<td height="30"width="10%">购买有效期:</td>
    		<td>${course.safe_year}</td>
    		</tr>
    		<tr>
    		<td height="30"width="10%">试听时长:</td>
    		<td>${course.audition}分钟</td>
    		</tr>
    	<%-- 	<tr>
    			<td height="30"width="10%">价格:</td>
    			<td> 
    			<fmt:formatNumber type="number" value="${course.price/100}" pattern="0.00" maxFractionDigits="2"/>元	
    		
    			</td>
    		</tr>
    		<tr>
    			<td height="30"width="10%">促销价格:</td>
    			<td> 
    		<fmt:formatNumber type="number" value="${course.discount_price/100}" pattern="0.00" maxFractionDigits="2"/>元	
    			</td>
    		</tr> --%>
    	<tr>
    			<td height="30"width="20%">是否上架: </td>
    			<td>
    			<c:if test="${course.state==0}">未上架</c:if>
    			<c:if test="${course.state==1}">已上架</c:if> 
    			</td>
    		</tr>
    		
    	<tr>
    			<td height="30"width="10%">观看人数:</td>
    			<td>${course.watch}人</td>
    	</tr>
    	
    	<%-- <tr>
    			<td height="30"width="10%">是否置顶:</td>
    			<td> 
    			<c:if test="${course.top==0}">未置顶</c:if>
    			<c:if test="${course.top==1}">已置顶</c:if>
    			</td>
    	</tr> --%>
    	
    	<tr>
    			<td height="30"width="10%">课程日期:</td>
    			<td>${course.date}</td>
    	</tr>
    		
    	 	
    	
    	<tr>
    			<td height="30"width="10%">是否免费:</td>
    			<td> 
    			<c:if test="${course.is_free==-1}">限时免费</c:if>
    			<c:if test="${course.is_free==1}">收费
    		</c:if>
    			</td>
    	</tr>
    		
    		
    		 
    		</table>
    		</div>
    
    
		<div align="center" style="margin-top: 60px;">
			<a href="<%=basePath%>course/show_course.do">
			<button style="border: 1px solid #98FB98;width:80px;height: 30px;background-color : #E6E6FA;" >返回</button>
			</a>
				</div>
    
  </body>
</html>
