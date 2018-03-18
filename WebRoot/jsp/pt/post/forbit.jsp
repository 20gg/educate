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
    
    <title>禁言管理</title>
    
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
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">禁言管理</div>
     
     <div style="padding: 20px;  border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;
  	border-left-color: 1b809e; width: 94%; margin-left: 3%; margin-top: 20px;">
  		<div style="width: 100px; float: left;" >
			 <input type="checkbox" <c:if test="${forbit == 1}">checked="checked"</c:if> 
			  style="width: 20px; height: 20px;" id="forbit" value="1">&nbsp; 已禁言
		</div>
		
		<div class="input-group"  style="width: 480px; float: left; margin-left: 20px;"> 
  	 		<span class="input-group-btn">
		  		<select id="search_kind" class="form-control"  style="width: 120px;"> 
			  		<option <c:if test="${kind == 1}">selected="selected"</c:if> value="1">姓名</option>
			  		<option <c:if test="${kind == 2}">selected="selected"</c:if> value="2">手机号</option>
		  		</select>
		  	</span>
		  <input type="text" id="search_input" value="${input}" class="form-control"  placeholder="请输入搜索信息" maxlength="32" >
		  <span class="input-group-btn"> 
		  	 <button type="button" class="btn btn-primary" style="width: 80px;" onclick="go_search();">搜索</button>
		  </span>
		</div>
		
		<div style="clear: both;"></div> 
  	</div>
  	
  	 <div style="margin-top: 20px;width: 94%; margin-left: 3%; ">
     		<button type="button" class="btn btn-default" style="width: 120px;" code="0" onclick="choose_all(this);">全选</button>
     		<button type="button" class="btn btn-danger" style="width: 120px;" onclick="forbit_all(1);">禁言</button>
     		<button type="button" class="btn btn-success" style="width: 120px;" onclick="forbit_all(0);">解禁</button>
     </div>
     
      <table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:12px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
  			<td></td>
  			<td>头像</td> 
  			<td>昵称</td>
  			<td>手机号</td> 
  			<td>状态</td> 
  		</tr>
  		
  		<c:forEach items="${user_list}" var="u" varStatus="us">
  			<tr>
	  			<td><input type="checkbox" style="width: 20px; height: 20px;" name="aaa" value="${u._id}"></td>
	  			<td>
	  				<c:if test="${not empty u.head}">
	  					<img src="${u.head}" style="width: 36px; height: 36px; border-radius:18px;">
	  				</c:if>
	  			</td> 
	  			<td>${u.name}</td>
	  			<td>${u.phone}</td>  
	  			<td>
	  				<c:if test="${u.forbit == 1}">已禁言</c:if>
	  				<c:if test="${u.forbit == 0}">未禁言</c:if>
	  				<c:if test="${empty u.forbit}">未禁言</c:if>
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
  	
	<input type="hidden" id="page_count" value="${page_count}"/>
  	<input type="hidden" id="page_num" value="${page_num}"/>
     
     
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
	jspost(pt_path +"/area/forbit_list.do", url_data);
}

function go_search(){
	
	var forbit = 0; 
	var a=document.getElementById("forbit"); 
	if(a.checked == true){
		forbit = 1;
	} 
	var kind = $("#search_kind").val(); 
	var input = $("#search_input").val();
	
	var url_data = {"page":1,"forbit":forbit,"kind":kind,"input":input};
	get_data(url_data);
}

function key_page() {
	 
		var page = $("#page_input").val();
		var page_count = Number($("#page_count").val());
		//校验page为正整数
		if(check_num(page) == true){
		 	if(page >= 1 && page <= page_count){
		 		
		 		var forbit = 0; 
		 		var a=document.getElementById("forbit"); 
		 		if(a.checked == true){
		 			forbit = 1;
		 		} 
		 		var kind = $("#search_kind").val(); 
		 		var input = $("#search_input").val();
		 		
		 		var url_data = {"page":page,"forbit":forbit,"kind":kind,"input":input};
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
 		 
 		var forbit = 0; 
 		var a=document.getElementById("forbit"); 
 		if(a.checked == true){
 			forbit = 1;
 		} 
 		var kind = $("#search_kind").val(); 
 		var input = $("#search_input").val();
 		
 		var url_data = {"page":page,"forbit":forbit,"kind":kind,"input":input};
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
 		
 		var forbit = 0; 
 		var a=document.getElementById("forbit"); 
 		if(a.checked == true){
 			forbit = 1;
 		} 
 		var kind = $("#search_kind").val(); 
 		var input = $("#search_input").val();
 		
 		var url_data = {"page":page,"forbit":forbit,"kind":kind,"input":input};
 		get_data(url_data);
 	}
	  
}

function choose_all(obj){
	
	var code = $(obj).attr("code");
	
	var arr =  document.getElementsByName("aaa");
	
	for(var i=0;i<arr.length;i++){
		if(code == 0){
			arr[i].checked = true;
		}else{
			arr[i].checked = false;
		}
		
	}
	
	if(code == 0){
		$(obj).text("全不选");
		$(obj).attr("code",1);
	}else{
		$(obj).text("全选");
		$(obj).attr("code",0);
	}
	 
}

function forbit_all(f){
	
	var arr =  document.getElementsByName("aaa");
	
	var id_str ="";
	var num = 0;
	
	for(var i=0;i<arr.length;i++){
		if(arr[i].checked == true){
			
			id_str = id_str + $(arr[i]).val()+",";
			
			num ++;
		}  
	}
	
	if(num < 1){
		alert("请选择用户！");
		return false;
	}else{
		
		$.ajax({
	 		type:"post",
	 		url: pt_path +"/area/forbit_all.do",
	 		dataType:"json",
	 		data:{"id_str":id_str,"forbit":f},
	 		success: function(data){
	 			 
	 			if(data[0].back_code == 200){
	 				
	 				var url_data = {"page":1};
	 				get_data(url_data);
	 			}
	 		}
		});
	} 
}

 

</script>
</html>
