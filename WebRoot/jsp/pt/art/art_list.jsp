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
    
    <title>精选好文</title>
    
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
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">精选好文 </div>
     
      <div style="padding: 20px;  border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;
  		border-left-color: 1b809e; width: 94%; margin-left: 3%; margin-top: 20px;"> 
  			<button type="button"
  				 <c:if test="${sort == 1}"> class="btn btn-primary" </c:if>
  				 <c:if test="${sort != 1}">  class="btn btn-default" </c:if>
  				  style="width: 100px;  " onclick="sort(1);">干货</button>
     		<button type="button" <c:if test="${sort == 2}"> class="btn btn-primary" </c:if>
  				 <c:if test="${sort != 2}">  class="btn btn-default" </c:if>
  				  style="width: 100px;  "  onclick="sort(2);">精选</button>
     		<button type="button" <c:if test="${sort == 3}"> class="btn btn-primary" </c:if>
  				 <c:if test="${sort != 3}">  class="btn btn-default" </c:if> 
  				 style="width: 100px; " onclick="sort(3);">原创</button>
  				 
  				
  				 
  				 <button type="button"  class="btn btn-primary"  
  				 style="width: 100px; float: right;" onclick="add_page();">新增文章</button>
  				 
  		 <div class="input-group"  style="width: 300px; margin-top: 10px;"> 
  	 		 
		  <input type="text" id="search_input" value="${input}" class="form-control"  placeholder="请输入标题信息" maxlength="128">
		  <span class="input-group-btn"> 
		  	 <button type="button" class="btn btn-primary" style="width: 80px;" onclick="go_search();">搜索</button>
		  </span>
		</div>
  				 
  	</div>
  	
  	<input type="hidden" id="sort" value="${sort}">
  	
  	
  	<table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:12px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
  			<td>序号</td>  
  			<td>标题</td>
  			<td>发布时间</td> 
  			<td>状态</td>
  			<td>是否热荐</td>
  			<td style="width: 20%;">操作</td>  
  		</tr>
  		<c:if test="${not empty recommend  &&  empty input}">
  			<tr>
	  			<td>1</td> 
	  			<td style="text-align: left;">${recommend.title}</td>
	  			<td>${recommend.ctime}</td> 
	  			<td>
	  				<c:if test="${recommend.state == 1}">已发布</c:if>
	  				<c:if test="${recommend.state == 0}">未发布</c:if>
	  			</td>
	  			<td style="color: red;">热荐</td>
				<td>
					<a href="<%=basePath%>article/edit_art_show.do?art_id=${recommend._id}">编辑</a>
					<a onclick="recommend_it('${recommend._id}',0);">取消热荐</a>
					<a onclick="state_it('${recommend._id}');"><c:if test="${recommend.state == 1}">取消发布</c:if>
	  				<c:if test="${recommend.state == 0}">发布</c:if></a>
				</td> 
  			</tr>
  		</c:if>
  		<c:forEach items="${art_list}" var="a" varStatus="ps">
  			<tr>
	  			<td>${ps.index+2}</td> 
	  			<td style="text-align: left;">${a.title}</td>
	  			<td>${a.ctime}</td> 
	  			<td>
	  				<c:if test="${a.state == 1}">已发布</c:if>
	  				<c:if test="${a.state == 0}">未发布</c:if>
	  			</td>
	  			<td>不热荐</td>
				<td><a href="<%=basePath%>article/edit_art_show.do?art_id=${a._id}">编辑</a>
					<a onclick="recommend_it('${a._id}',1);">热荐</a>
					<a onclick="state_it('${a._id}');"><c:if test="${a.state == 1}">取消发布</c:if>
	  				<c:if test="${a.state == 0}">发布</c:if></a>
	  			</td> 
  			</tr>
  		</c:forEach> 
  		</table>
  	
  	<%-- <div style="text-align: center;margin-top:50px;">
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
		
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<c:if test="${not empty recommend}">
			<span>共<b style="color:#666">${count+1}</b>条</span>
		</c:if>
		<c:if test="${empty recommend}">
			<span>共<b style="color:#666">${count}</b>条</span>
		</c:if>
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
  	
  </body>
<script type="text/javascript" src="<%=basePath%>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
<script type="text/javascript">
function add_page() {
	location.href = pt_path +"/jsp/pt/art/add_art.jsp"; 
}

function check_num(input){
	//正整数
	var r = /^[1-9]*[1-9][0-9]*$/;
   
  	return r.test(input);
}

function get_data(url_data) {
	jspost(pt_path +"article/get_art_list.do", url_data);
}

function sort(code) {
	var url_data = {"sort":code};
	get_data(url_data);
}

function key_page() {
	var input = $("#search_input").val();
		var page = $("#page_input").val();
		var page_count = Number($("#page_count").val());
		//校验page为正整数
		if(check_num(page) == true){
		 	if(page >= 1 && page <= page_count){
			 	 
		 		var sort = $("#sort").val();
		 		
		 		var url_data = {"page":page,"sort":sort,"input":input};
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
	var input = $("#search_input").val();
 	var page_num = Number($("#page_num").val());
 	var page_count = Number($("#page_count").val());
 	
 	var page = 1;
 	
 	if(page_num > 1 && page_num <= page_count){
 		page = page_num-1;
 		 
		var sort = $("#sort").val();
 		
 		var url_data = {"page":page,"sort":sort,"input":input};
		get_data(url_data);
 	} 
	 
 	
 }

//下一页  
function next_page(){
	var page_num = Number($("#page_num").val());
	var page_count = Number($("#page_count").val());
	var page = 1;
	var input = $("#search_input").val();
	if(page_num < page_count){
 		page = page_num+1;
 		
 		var sort = $("#sort").val();
 		
 		var url_data = {"page":page,"sort":sort,"input":input};
		get_data(url_data);
 	}
	  
}

function recommend_it(art_id,kind){
	var input = $("#search_input").val();
	var page_num = Number($("#page_num").val());
	var sort = $("#sort").val();
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/article/recommend_one.do",
 		dataType:"json",
 		data:{"art_id":art_id,"kind":kind},
 		success: function(data){
 			
 			if(data[0].back_code == 200){
 				 
 				var url_data = {"page":page_num,"sort":sort,"input":input};
 				get_data(url_data);
 			}
 		}
	 });
}

function state_it(art_id){
	
	var page_num = Number($("#page_num").val());
	var sort = $("#sort").val();
	var input = $("#search_input").val();
	$.ajax({
 		type:"post",
 		url: pt_path +"/article/state_art.do",
 		dataType:"json",
 		data:{"art_id":art_id},
 		success: function(data){
 			
 			if(data[0].back_code == 200){
 				 
 				var url_data = {"page":page_num,"sort":sort,"input":input};
 				get_data(url_data);
 			}
 		}
	 });
}
function go_search(){
	
	var sort = $("#sort").val();
	var input = $("#search_input").val();
	
	 
		var url_data = {"input":input,"sort":sort};
		get_data(url_data);
	 
	 
}
</script>
</html>
