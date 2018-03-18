<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>话题评论</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	 <link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" />
	 
<style type="text/css">
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

  </head>
  
  <body>
     <div style="margin-top: 20px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">话题评论 </div>
     
      <table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:12px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;">
  		<tr><td colspan="4">讨论话题</td></tr>
  		<tr>
  			 <td style="width: 20%;"><label>发布人</label></td>
  			 <td style="width: 30%;">${post.name}</td> 
  			 <td style="width: 20%;"><label>发布时间</label></td> 
  			 <td style="width: 30%;">${post.cdate}</td>  
  		</tr>
  		<tr>
  			 <td style="width: 20%;"><label>状态</label></td>
  			 <td style="width: 30%;"><c:if test="${post.state == 1}">使用中</c:if>
	  				<c:if test="${post.state == 0}">已封帖</c:if></td> 
  			 <td style="width: 20%;"><label>赞</label></td> 
  			 <td style="width: 30%;">${post.zan}次</td>  
  		</tr>
  		<tr>
  			 <td style="width: 20%;"><label>标题</label></td>
  			 <td style="width: 80%;text-align: left;" colspan="3">${post.title}</td> 
  		</tr>
  		<tr>
  			 <td style="width: 20%;"><label>内容</label></td>
  			 <td style="width: 80%;text-align: left;" colspan="3">${post.context}</td> 
  		</tr>
  	  </table>
  	  
  	  <table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:12px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
  			<td style="width: 5%;">序号</td> 
  			<td>发布人</td>
  			<td style="width: 15%;">发布时间</td> 
  			<td style="width: 30%;">内容</td>
  			<td>赞</td>
  			<td>状态</td>
  			<td>操作</td>  
  		</tr>
  		
  		<c:forEach items="${post_log}" var="p" varStatus="ps">
  			<tr>
	  			<td>${ps.index+1+(page_num-1)*limit}</td> 
	  			<td>${p.name}</td>
	  			<td>${p.ctime}</td> 
	  			<td style="text-align: left;">${p.context}</td>
	  			<td>${p.zan}次</td>
	  			<td>
	  				<c:if test="${p.state == 1}">使用中</c:if>
	  				<c:if test="${p.state == 0}">已封帖</c:if>
	  			</td>
	  			<td> 
					<a onclick="change_post('${p._id}');"><c:if test="${p.state == 1}">封帖</c:if><c:if test="${p.state == 0}">解封</c:if></a>
				</td>  
  			</tr>
  		</c:forEach> 
  		</table>
  	
  <%-- 	<div style="text-align: center;margin-top:50px;">
		<div class="btn-group" role="group" >
			<button type="button" class="btn btn-success" onclick="last_page()" >上一页</button>
			<div class="btn-group" role="group">
				<input type="text" class="form-control" placeholder="第（${page_num}/${page_count}）页"  id="page_input"
					style="width: 160px; text-align: center;" maxlength="8" onkeypress="key_page();">
			</div>
			<button type="button" class="btn btn-success" onclick="next_page()" >下一页</button>
		</div>
  	</div> --%>
  	
  	<div class="page">
<div style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 14px;"> 
		<span>共<b style="color:#666">${count}</b>条</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<span style="color:#666">第${page_num }/${page_count}页</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button"  class="but_page" onclick="last_page()">上一页</button>
		&nbsp;&nbsp;&nbsp;
		<button type="button" class="but_page" onclick="next_page()">下一页</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 跳转到 
			<input type="text" name="page"  id="page_input" style="width:60px;height:30px;border: 1px solid #5CB85C; text-align: center; font-size: 16px;" onkeyup="priceKeyup(this)"
				maxval="${page_count}" value="${page_num}">
			&nbsp;&nbsp;&nbsp; 
			 <input type="button" value="GO"class="but_page" onclick="key_page();">
		 
	</div>
</div>  
  	
  	<br><br><br>
	<input type="hidden" id="page_count" value="${page_count}"/>
  	<input type="hidden" id="page_num" value="${page_num}"/>
  	
  	<input type="hidden" id="post_id" value="${post._id}"/>
  	  
  	  
  	  
  </body>
<script type="text/javascript" src="<%=basePath%>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
<script type="text/javascript">
function check_num(input){
	//正整数
	var r = /^[1-9]*[1-9][0-9]*$/;
   
  	return r.test(input);
}

function get_data(url_data) {
	jspost(pt_path +"/area/get_post_log.do", url_data);
}

function key_page() {
 
	var page = $("#page_input").val();
	var page_count = Number($("#page_count").val());
	//校验page为正整数
	if(check_num(page) == true){
	 	if(page >= 1 && page <= page_count){
	 		
	 		var post_id = $("#post_id").val(); 
			var url_data = {"page":page,"post_id":post_id};
			get_data(url_data);
	 	}else{
	 		$("#page_input").val("");
	 	}
	}else{
		$("#page_input").val("");
	}
	 
	 
}

//上一页 
function last_page(){
 
 	var page_num = Number($("#page_num").val());
 	var page_count = Number($("#page_count").val());
 	
 	var page = 1;
 	
 	if(page_num > 1 && page_num <= page_count){
 		page = page_num-1;
 		 
 		var post_id = $("#post_id").val(); 
		var url_data = {"page":page,"post_id":post_id};
		get_data(url_data);
 	} 
	 
 	
 }

//下一页  
function next_page(){
	var page_num = Number($("#page_num").val());
	var page_count = Number($("#page_count").val());
	var page = 1;
 	
	if(page_num < page_count){
 		page = page_num+1;
 		
 		var post_id = $("#post_id").val(); 
		var url_data = {"page":page,"post_id":post_id};
		get_data(url_data);
 	}
	  
}

function change_post(log_id){
	
	var post_id = $("#post_id").val(); 
	var page_num = Number($("#page_num").val());
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/area/change_post_log.do",
 		dataType:"json",
 		data:{"log_id":log_id},
 		success: function(data){
 			 
 			if(data[0].back_code == 200){
 				
 				var url_data = {"page":page_num,"post_id":post_id};
 				get_data(url_data);
 			}
 		}
	});
}

</script>
</html>
