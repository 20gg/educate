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
    
    <title> </title>
    
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
    		<strong>专题详情 </strong>
    </div>
    <table class="table table-hover table-bordered" style="width: 90%; margin-left: 3%; margin-top:55px;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
   
    		<tr>
    		<td height="30" width="13%">课程名称:</td>
    		<td>${special.special_name}</td>
    		</tr>   		 
    		<tr>
    		<td height="30"width="14%">专题介绍:</td>
    		<td>${special.text}</td>
    		</tr>
    		<tr>
    		
    			<tr>
    		<td height="30"width="14%">专题图片:</td>
    		<td><img src="${special.img}" style="width: 100px;"></td>
    		</tr>
    		<tr>
    		
    		
    		<tr>
    		<td height="30"width="14%">分享使用的图片:</td>
    		<td><img src="${special.sharee_img}" style="width: 100px;"></td>
    		</tr>
    		<tr>
    		
    		<tr>
    		<td height="30"width="14%">分享摘要:</td>
    		<td>${special.introduce2}</td>
    		</tr>
    		<tr>
    		
    		<td height="30"width="10%">专题包含的课程:</td>
    		
    		<td>
    		<c:forEach items="${special.url}" var="s" varStatus="a">
    			${s} &nbsp;,
    			</c:forEach>
    			</td>
    		</tr>
    		<%-- <div>关联资源:
			  	 
			  		<c:forEach items="${special.url}" var="s">
			  		
			  		<div id="appen_div" style="text-align: left;">
				  		<div class='input-group' style='margin-top:10px;width: 200px;'>
				  		 	<input type='text' name='input_name'  class='form-control' style='width: 200px;' value="${s.vid}">
	  	 					<span class='input-group-addon' onclick='delete_content(this);' >删除</span>
	  	 				 </div>
					</div> --%>
			  		
			  		<%-- </c:forEach>
			  		
			  		</div>
    		 --%>
    		<tr>
    		<td height="30"width="10%">试听时长:</td>
    		<td>${special.audition}分钟</td>
    		</tr>
    		<tr>
    		<td height="30"width="10%">购买有效期:</td>
    		<td>${special.safe_year}年</td>
    		</tr>
    		
    		<tr>
    			<td height="30"width="10%">价格:</td>
    			
    			
    			<td> 
    			
    			<fmt:formatNumber type="number" value="${special.price/100}" pattern="0.00" maxFractionDigits="2"/>元	
    	
    			</td>
    		</tr>
    		<%-- <tr>
    			<td height="30"width="10%">促销价格:</td>
    			<td>
    			
    	<fmt:formatNumber type="number" value="${special.discount_price/100}" pattern="0.00" maxFractionDigits="2"/>元	
    	
    			</td>
    		</tr> --%>
    	<tr>
    			<td height="30"width="20%">是否上架: </td>
    			<td>
    			<c:if test="${special.state==0}">未上架</c:if>
    			<c:if test="${special.state==1}">已上架</c:if> 
    			</td>
    		</tr>
    		
    	<tr>
    			<td height="30"width="10%">观看人数:</td>
    			<td>${special.watch}人</td>
    	</tr>
    	<tr>
    			<td height="30"width="10%">分享的标题:</td>
    			<td>${special.share_title}</td>
    	</tr>
    	
    	<%-- <tr>
    			<td height="30"width="10%">是否置顶:</td>
    			<td> 
    			<c:if test="${special.top==0}">未置顶</c:if>
    			<c:if test="${special.top==1}">已置顶</c:if>
    			</td>
    	</tr> --%>
    	
    	<tr>
    			<td height="30"width="10%">专题日期:</td>
    			<td>${special.date}</td>
    	</tr>
    		
    	<tr>
    			<td height="30"width="10%">适合人群:</td>
    			<td>${special.fit_people}</td>
    	</tr>	
    	
    		<tr>
    			<td height="30"width="10%">订阅须知:</td>
    			<td>${special.order_notice}</td>
    	</tr>	
    	
    		<tr>
    			<td height="30"width="10%">订阅人数:</td>
    			<td>${special.order_count}</td>
    	</tr>
  		
  		 	<tr>
    			<td height="30"width="10%">广告视频ID:</td>
    			<td>${special.gg_id}</td>
    	</tr>
  		
    </table>
				<div align="center" style="margin-top: 60px;">
			<a href="<%=basePath%>course/show_special.do">
			<button style="border: 1px solid #98FB98;width:80px;height: 30px;background-color : #E6E6FA;" >返回</button>
			</a>
				</div>
    
  </body>
</html>
