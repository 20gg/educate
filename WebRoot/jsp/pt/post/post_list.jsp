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
    
    <title>讨论区管理</title>
    
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
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">讨论区管理 </div>
     
     <div style="padding: 20px;  border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;
  	border-left-color: 1b809e; width: 94%; margin-left: 3%; margin-top: 20px;">
  	
  		<div class="input-group"  style="width: 600px;  "> 
  	 		<span class="input-group-btn">
		  		<select id="search_kind" class="form-control"  style="width: 120px;"> 
			  		<option <c:if test="${kind == 1}">selected="selected"</c:if> value="1">发布人</option>
			  		<option <c:if test="${kind == 2}">selected="selected"</c:if> value="2">标题</option>
		  		</select>
		  	</span>
		  <input type="text" id="search_input" value="${input}" class="form-control"  placeholder="请输入搜索信息" maxlength="128">
		  <span class="input-group-btn"> 
		  	 <button type="button" class="btn btn-primary" style="width: 80px;" onclick="go_search();">搜索</button>
		  </span>
		</div>
  	</div>
     
     <table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:12px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
  			<td>序号</td> 
  			<td>发布人</td>
  			<td>发布时间</td> 
  			<td>标题</td>
  			<td style="width: 30%;">内容</td>
  			<td>赞</td>
  			<td>状态</td>
  			<td>操作</td>  
  		</tr>
  		
  		<c:forEach items="${post_list}" var="p" varStatus="ps">
  			<tr>
	  			<td>${ps.index+1+(page_num-1)*limit}</td> 
	  			<td>${p.name}</td>
	  			<td>${p.cdate}</td> 
	  			<td style="text-align: left;">${p.title}</td>
	  			<td style="text-align: left;">${p.context}</td>
	  			<td>${p.zan}次</td>
	  			<td>
	  				<c:if test="${p.state == 1}">使用中</c:if>
	  				<c:if test="${p.state == 0}">已封帖</c:if>
	  			</td>
	  			<td>
					<a href="<%=basePath%>area/get_post_log.do?post_id=${p._id}">评论</a>
					<a onclick="change_post('${p._id}');"><c:if test="${p.state == 1}">封帖</c:if><c:if test="${p.state == 0}">解封</c:if></a>
				</td>  
  			</tr>
  		</c:forEach> 
  		</table>
  		
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
	jspost(pt_path +"/area/get_post_list.do", url_data);
}

function key_page() {
	 
		var page = $("#page_input").val();
		var page_count = Number($("#page_count").val());
		//校验page为正整数
		if(check_num(page) == true){
		 	if(page >= 1 && page <= page_count){
			 	 
		 		var kind = $("#search_kind").val();
		 		var input = $("#search_input").val();
		 		
		 		var url_data = {"page":page,"kind":kind,"input":input};
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
 		 
 		var kind = $("#search_kind").val();
 		var input = $("#search_input").val();
 		
 		var url_data = {"page":page,"kind":kind,"input":input};
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
 		
 		var kind = $("#search_kind").val();
 		var input = $("#search_input").val();
 		
 		var url_data = {"page":page,"kind":kind,"input":input};
		get_data(url_data);
 	}
	  
}

function go_search(){
	
	var kind = $("#search_kind").val();
	var input = $("#search_input").val();
	
	if(input != ""){
		var url_data = {"kind":kind,"input":input};
		get_data(url_data);
	}
	 
}

function change_post(post_id){
	
	var page_num = Number($("#page_num").val());
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/area/change_post.do",
 		dataType:"json",
 		data:{"post_id":post_id},
 		success: function(data){
 			 
 			if(data[0].back_code == 200){
 				
 				var url_data = {"page":page_num};
 				get_data(url_data);
 			}
 		}
	});
}

</script>
</body>
</html>
