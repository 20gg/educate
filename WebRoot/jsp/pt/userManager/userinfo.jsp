<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户</title>
    
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />

  </head>
  <style type="text/css">
  .main-table td{
		font-size: 120%;
	}
  
  </style>
  <body>
      <div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  ">
  	用户详情
  	</div>
  	
  	<table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    		 		
    		<td  >会员编号</td>
    		<td   >微信昵称</td>
    		 
    		 <td  >会员等级</td>
    		 <td >真实姓名</td>
    		<td  >手机号</td>
    		<td  >公司</td>
    		<td  >职务</td>
    		 
    		<td  >奖学金</td>
    		<td  >学分</td>
    		<td  >同学圈</td>
   			 <td  >黑名单</td>
    		</tr>
    		
    				<tr>
    				
 				<td   id="userid">${userinfo.u_no }</td>
 						
    					<td  >${userinfo.name }</td>
    					<td>
    					<c:if test="${userinfo.role==0}">普通用户</c:if>
    					<c:if test="${userinfo.role==1}">VIP会员</c:if>
    					<c:if test="${userinfo.role==3}">会员用户</c:if>
    					</td>
    					<td  >${userinfo.u_name }</td>
    					<td   id="phoneid">${userinfo.phone }</td>
    					<td  >${userinfo.company }</td>
    					<td  >${userinfo.job }</td>
 						<%-- 	<td  >
 	  <fmt:formatNumber type="number" value="${userinfo.count_scholarship/100}" pattern="0.00" maxFractionDigits="2"/>元 	
 										
    					</td> --%>
    					<td   id="phoneid">
    				 <fmt:formatNumber type="number" value="${userinfo.scholarship/100}" pattern="0.00" maxFractionDigits="2"/>元	
    					     					
    					</td>
    					<td  >${userinfo.score }分
    					
    					</td>
    					<td >${c_num}人
    					
    					</td>
    					
    			<td  onclick="blacklist();"    style="cursor:pointer;">
    					
    				
    				<c:choose>
    				<c:when test='${userinfo.state  eq  0}'>加入黑名单</c:when>
    				<c:when test='${userinfo.state  eq  -1}' >移除黑名单</c:when>
    				</c:choose>
    			
    			<input  type="hidden"  value="${userinfo.state}" id="inputstate">		
    					</td>
    		
    				</tr>
    				<tr>
    					<td style="visibility: hidden;"></td>
    					<td style="visibility: hidden;"></td>
    					<td style="visibility: hidden;"></td>
    					<td style="visibility: hidden;"> </td>
    					<td style="visibility: hidden;"></td>
    					<td style="visibility: hidden;"></td>	
    					<td style="visibility: hidden;"></td>
    					<%-- <td  >
    					<a href="<%=basePath %>user/show_course_order_jsp.do?open_id=${userinfo.open_id}">明细</a>
    					</td> --%>
    					<td  >
    					<a href="<%=basePath %>user/show_scholaership_jsp.do?open_id=${userinfo.open_id}">记录</a>
    					</td>
    					<td  >
    					<a href="<%=basePath %>user/show_score_jsp.do?open_id=${userinfo.open_id}">详情</a>
    					</td>
    				 
    					
    				</tr>
    				
    		</table>
    		<input  type="hidden"  value="${userinfo.u_no}" id="inputuno">
    	
    			
    			
    		
    		
    		<div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  text-decoration: underline;">会员订单信息</div>
    
    		<table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
    			
    			<tr  style="background-color: #fcfcfc;">
    			<td>课程编号</td>
    			<td>课程</td>
    			<td>分类</td>
    			<td>支付金额</td>
    			<td>有效期</td>
    		</tr>
    		 
    			
    			<c:forEach items="${orderinfo}" var="orderinfo" varStatus="status"> 
    		
    			<tr>
    			<td>${orderinfo.c_no}</td>
    			<td>${orderinfo.c_name}</td>
    			<td>
    			<c:if test="${orderinfo.type ==1}">课程</c:if>
    			<c:if test="${orderinfo.type ==2}">专题</c:if>
    			<c:if test="${orderinfo.type ==0}">课程</c:if>
    			 </td>
    			<td>
    	<fmt:formatNumber type="number" value="${orderinfo.pay_money/100}" pattern="0.00" maxFractionDigits="2"/>元 			
    			 </td>
    			<td>${orderinfo.date_1}</td>
    		</tr>
    		  	</c:forEach>
    		</table>
	
	
    <c:if test="${ not empty vip_log}">
    <div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  text-decoration: underline;">VIP购买订单</div>
    
		<table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
    		 
    		<tr  style="background-color: #fcfcfc;">
    			<td>订单号</td>
    			<td>类型</td>   			 
    			<td>支付金额</td>
    			<td>有效期</td>
    			<td>是否有效</td>
    		</tr>
    		 
    			<tr>
    			<td>${vip_log.order_id}</td>
    			<td>${vip_log.c_name}</td>
    			<td><fmt:formatNumber type="number" value="${vip_log.pay_money/100}" pattern="0.00" maxFractionDigits="2"/>元 			
    	</td>
    			<td>${vip_log.safe_date}</td>
    			  
    			 <td>
    			 <c:if test="${vip_log.is_over ==0}">有效</c:if>
    			<c:if test="${vip_log.type ==-1}">失效</c:if>
    			 </td>
    		</tr>
    		  
    		</table>
</c:if>
  	 
  		<div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  text-decoration: underline;">同学圈列表</div> 
   		<table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
    		<tr  style="background-color: #fcfcfc;">
    			 
    			<td>微信昵称</td>
    			<td>会员等级</td>
    			<td>关系</td>
    			<td>锁定期</td>
    			 
    		</tr>
    		 
    			<tr>
    			
    			<td>${user.name}</td>
    			<td>
    			
    			
    					<c:if test="${user.role ==1}">VIP会员</c:if>
    					<c:if test="${user.role ==0}">普通用户</c:if>
    					<c:if test="${user.role ==3}">会员用户</c:if>
    			</td>
    			<td> 我自己</td>
    		 
    			 <td>${user.p1_date}</td>    	
    			 		 
    		</tr>       
    		<c:if test="${not empty shangji_map}">
    			<tr>
    			
    			<td>${shangji_map.name}</td>
    			<td>
    			
    			
    					<c:if test="${shangji_map.role ==1}">VIP会员</c:if>
    					<c:if test="${shangji_map.role ==0}">普通用户</c:if>
    					<c:if test="${shangji_map.role ==3}">会员用户</c:if>
    			</td>
    			<td> 我的上级</td>
    		 
    			 <td>${shangji_map.p1_date}</td>    			 
    		</tr>
    		</c:if>
    		
    		<c:if test="${not empty xiaji_user}">
    		  <c:forEach items="${xiaji_user}" var="xia_ji">
    				<tr>
    			
    			<td>${xia_ji.name}</td>
    			<td>
    			
    			
    					<c:if test="${xia_ji.role ==1}">VIP会员</c:if>
    					<c:if test="${xia_ji.role ==0}">普通用户</c:if>
    					<c:if test="${xia_ji.role ==3}">会员用户</c:if>
    			</td>
    			<td> 我的下级</td>
    		 
    			 <td>${xia_ji.p1_date}</td>    			 
    		</tr>
    			
    		
    		</c:forEach> 
    		 </c:if>
    		</table> 
     
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
  <script type="text/javascript">
  	
  function blacklist() {
	  	var u_no=$("#inputuno").val();
	  	var  state=$("#inputstate").val();
	  	 
	  	
	  	$.ajax({
	  		type:"post",
	  		url: getRootPath() +'/user/blacklist1.do',
	  		dataType:"json",
	  		data:{"u_no":u_no,"state":state},
	  		success: function(data){
	  			if("success"==data[0].result){
	  				
	  				window.location.reload();
	  			}else{
	  				alert("会员不存在");
	  			}
	  			
	  		}
	  		
	  	});
	
}
  
  
  </script>
</html>
