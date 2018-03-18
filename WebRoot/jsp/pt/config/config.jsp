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
    
     <title>系统配置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />

  </head>
   <style type="text/css">
  .main-table td{
		font-size: 120%;
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
  <body>
  <div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  ">
  	系统配置
  	</div>
  
  	 <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    	 
    		<td  >上下级关系有效期</td>
    		<td   >上级关系比例提成</td>
    		 <td >签到得分</td>
    		 <td  >看视频得分</td>
    		 <td> 邀请别人成为会员奖励的奖学金</td>
    		   <td>VIP费用</td>
    		 <td>VIP有效期</td>
    		 <td> 分享获得学分</td>
    		 <td>分享获得奖学金</td>
    		
    		<td  >操作</td>
    		</tr>    		
    		 
    				<tr>
    					 
    					<td   id="userid">${config.safe_date }天</td>
    					<td  >${config.percentage }%</td>
    					 
    					<td>
    					${config.sign_score }分
    					 </td>
    					<td  >${config.video_score}分</td>
    					
    					
    					 <td  >
    					   <fmt:formatNumber type="number" value="${config.scholarship/100}" pattern="0.00" maxFractionDigits="2"/>
    					  元
    					  
    					 </td>
    					 <td  >
    					   <fmt:formatNumber type="number" value="${config.vip_money/100}" pattern="0.00" maxFractionDigits="2"/>
    					  元
    					  
    					 </td>
    					  <td  >${config.vip_date}天</td>
    					   <td  >${config.share_score}分</td>
    					   <td  >
    					   <fmt:formatNumber type="number" value="${config.share_scholarship/100}" pattern="0.00" maxFractionDigits="2"/>
    					  元</td>
    		<td height="30">
    					  
    					<a href="javascript: updaterole()" >修改</a>
    					 
    					</td>	  			  
    				</tr>
    		 
    		</table>
  	
    <!-- 添加框（修改信息） -->
<div class="modal fade" id="updaterole" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 500px;">
      <div class="modal-content">
         <div class="modal-header" style="background-color: green';">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" style="color: green;">&times;</span>
            </button>
            <h4 class="modal-title" id="updatemyModalLabel" style="color: green;">
               修改信息
            </h4>
         </div>
         <div class="modal-body" style="text-align: center">
         
         <h5>上下级关系有效期</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="safe_date_1"  value="${config.safe_date }"/>天<br> 
   
			<h5>上级关系比例提成</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="percentage_1"  value="${config.percentage }"/>%<br> 
   
			<h5>签到得分</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="sign_score"  value="${config.sign_score }"/>分<br>
			
			<h5>看视频得分</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="video_score_1"  value="${config.video_score}" />分<br>
			
			<h5>邀请别人成为会员奖励的奖学金</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="scholarship"  value="<fmt:formatNumber type='number' value='${config.scholarship/100}' pattern='0.00' maxFractionDigits='0'/>" />元<br>
		 <h5>VIP有效期</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="vip_date" value="${config.vip_date}"/>天<br>
		 
			   <h5>分享获得学分</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="share_ss" value="${config.share_score}"/>分<br>
		 
		  <h5>分享获得奖学金</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="share_scp"   value="<fmt:formatNumber type='number' value='${config.share_scholarship/100}' pattern='0.00' maxFractionDigits='0'/>"/>元<br>
		 <h5>VIP费用</h5>
		  <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="vip_money"   value="<fmt:formatNumber type='number' value='${config.vip_money/100}' pattern='0.00' maxFractionDigits='0'/>"/>元<br>
		 
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="updatecommit" style="background-color: #c00;border-color: #c00;"> 提交</button>
         </div>
      </div> 
</div> 
</div> 
    
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
  <script type="text/javascript"	src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
  <script type="text/javascript">
  
  function updaterole(obj){			
 
	 
		$("#updaterole").modal("show");
}
$("#updatecommit").click(function(){	
	$("#updaterole").modal("hide");
	var safe_date=$("#safe_date_1").val();
	 var percentage=$("#percentage_1").val(); 
	var sign_score=$("#sign_score").val();
	 var  video_score=$("#video_score_1").val();
	 var  scholarship=$("#scholarship").val();
	 var vip_date=$("#vip_date").val();
	var share_ss=$("#share_ss").val();
	var share_scp=$("#share_scp").val();
	var vip_money=$("#vip_money").val();
		   $.ajax({
			type: "POST",
			url: getRootPath() +'/config_1/update_config.do',
			data: {"safe_date":safe_date,"vip_money":vip_money,"percentage":percentage,"sign_score":sign_score,"video_score":video_score,"scholarship":scholarship,"vip_date":vip_date,"share_ss":share_ss,"share_scp":share_scp},
			dataType: "json",
			success: function(data){
				if("success"==data[0].result){
					 alert("修改成功");
				
					 window.location.reload();

				}else{
					alert("修改失败");
				}
			}
		});  
	   
});
  </script>
</html>
