<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>导师回复</title>
    
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
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">导师回复 </div>
     
       <c:if test="${fn:length(discuss_list) == 0 && fn:length(post_list)==0}">
     	<div style=";width: 94%; margin-left: 3%;  line-height: 30px;
     	 color: red;margin-top: 20px;">没有需回复的课程评论！</div>
     </c:if>
     
      <table class="table table-hover table-bordered"  style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:14px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;" >
  		
  		<tr style="background-color: #fcfcfc;">
  			<td>序号</td>  
  			<td>昵称</td>
  			<td>评论时间</td> 
  			<td style="width: 50%;">评论内容</td>
  			<td style="width: 10%;">回复</td>  
  		</tr>
  		
  		<c:if test="${fn:length(post_list)==0}">
  		<c:forEach items="${discuss_list}" var="d" varStatus="dd">
  			<tr>
	  			<td>${dd.index+1}</td>  
	  			<td>${d.name}</td>
	  			<td>${d.date}</td> 
	  			<td style="width: 50%; text-align: left;">${d.val}</td>
	  			<td style="width: 10%;"><a onclick="answer_show('${d._id}','${d.val}','${d.c_no }');">回复</a></td>  
  			</tr>
  		</c:forEach> 
  		</c:if>
  		
  		<c:if test="${fn:length(discuss_list) == 0}">
  		<c:forEach items="${post_list}" var="d" varStatus="dd">
  			<tr>
	  			<td>${dd.index+1}</td>  
	  			<td>${d.name}</td>
	  			<td>${d.cdate}</td> 
	  			<td style="width: 50%; text-align: left;">${d.context}</td>
	  			<td style="width: 10%;"><a onclick="answer_show2('${d._id}','${d.context}');">回复</a></td>  
  			</tr>
  			
  			<input id="post_id"  value="${d._id}" type="hidden">
  		</c:forEach> 
  		</c:if>
  		
  		
  	  </table>
  	  
  	  <div class="page">
<div style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 14px;"> 
		
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		
		<c:if test="${not empty discuss_list}">
		 
			<span>共<b style="color:#666">${count+1}</b>条</span>
	 
		 
			<span>共<b style="color:#666">${count}</b>条</span>
		
		
		</c:if>
		<c:if test="${not empty post_list}">
		
		<c:if test="${not empty recommend}">
			<span>共<b style="color:#666">${post_count+1}</b>条</span>
		</c:if>
		<c:if test="${empty recommend}">
			<span>共<b style="color:#666">${post_count}</b>条</span>
		</c:if>
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
  	
  <div id="answer_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 400px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >导师回复</h4>
      </div>
      <div class="modal-body">
	      	<div>
	      		<label>课程名称：</label>
	      		<span id="c_name">1234567</span>
	      	</div>
	      	<div>
	      		<label>课程主讲人：</label>
	      		<span id="c_man">1234567</span>
	      	</div>
	      	<div>
	      		<label>课程详情：</label>
	      		<span id="c_info">1234567</span>
	      	</div>
	      	<div>
	      		<label>评论内容：</label>
	      		<span id="c_val">1234567</span>
	      	</div>
	      	<div>
	      		   <input type="text" class="form-control" id="answer_man" maxlength="20" placeholder="请输入回复人">
	      	</div>
	      	<div style="margin-top: 10px;">
	      		 <textarea  class="form-control" id="answer_info" placeholder="请输入回复内容"></textarea>
	      	</div>
	      	 
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="commit_answer();;">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">取消</button>
      </div>
    </div> 
  </div> 
</div> 


<div id="answer_page2" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 400px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >导师回复</h4>
      </div>
      <div class="modal-body">
	      
	      	<div>
	      		   <input type="text" class="form-control" id="answer_man2" maxlength="20" placeholder="请输入回复人">
	      	</div>
	      	<div style="margin-top: 10px;">
	      		 <textarea  class="form-control" id="answer_info2" placeholder="请输入回复内容"></textarea>
	      	</div>
	      	 
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="commit_answer2();;">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">取消</button>
      </div>
    </div> 
  </div> 
</div> 

<input type="hidden" id="discuss_id" value="-1">
     
     
  </body>
<script type="text/javascript" src="<%=basePath%>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/boot/3.0.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.tips.js"></script>
<script type="text/javascript">
function check_num(input){
	//正整数
	var r = /^[1-9]*[1-9][0-9]*$/;
   
  	return r.test(input);
}

function get_data(url_data) {
	jspost(pt_path +"article/query_at_discuss.do", url_data);
}
function key_page() {
	 
	var page = $("#page_input").val();
	var page_count = Number($("#page_count").val());
	//校验page为正整数
	if(check_num(page) == true){
	 	if(page >= 1 && page <= page_count){
		 	 
	 		var url_data = {"page":page };
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
		 
		var url_data = {"page":page };
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
		 
		var url_data = {"page":page };
		get_data(url_data);
	}
  
}

function answer_show(d_id,val,c_no){
	
	$("#discuss_id").val(d_id); 
	$("#c_val").text(val);
	
	$("#answer_man").val("");
	$("#answer_info").val("");
	

	$.ajax({
 		type:"post",
 		url: pt_path +"/article/query_one_course.do",
 		dataType:"json",
 		data:{"c_no":c_no},
 		success: function(data){
 			
 			$("#c_name").text(data[0].course.c_name);
 			$("#c_man").text(data[0].course.teacher);
 			$("#c_info").text(data[0].course.introduce2);
 			
 			$("#answer_page").modal({
 				keyboard : false
 			}); 
 			$("#answer_page").modal('show'); 
 		}
	 });
	  
}

function answer_show2(d_id,val){
	
	$("#discuss_id").val(d_id); 
	$("#c_val").text(val);
	
	$("#answer_man").val("");
	$("#answer_info").val("");
	

		$("#answer_page2").modal({
			keyboard : false
		}); 
		$("#answer_page2").modal('show'); 
	  
}


function error_show(id_name,msg) {
	$("#"+id_name).tips({
			side : 2,
			msg : msg,
			bg : '#2CC159',
			time : 3
	});
}

function commit_answer(){
	
	var discuss_id =$("#discuss_id").val();
	
	var answer_man = $("#answer_man").val();
	var answer_info = $("#answer_info").val();
	
	if(answer_man == ""){
		error_show("answer_man","请输入回复人！");
		return false;
	}
	
	if(answer_info == ""){
		error_show("answer_info","请输入回复内容！");
		return false;
	}
	
	//var   is_at="0";
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/article/answer_discuss.do",
 		dataType:"json",
 		data:{"discuss_id":discuss_id,"answer_man":answer_man,"answer_info":answer_info},
 		success: function(data){
 			
 			if(data[0].back_code == 100){
 				
 				var page_num = Number($("#page_num").val());
 				var url_data = {"page":page_num };
 				get_data(url_data);
 			}
 		}
	 });
	
}




function commit_answer2(){
	
	var discuss_id =$("#post_id").val();
	
	
	
	var answer_man = $("#answer_man2").val();
	var answer_info = $("#answer_info2").val();
	
	if(answer_man == ""){
		error_show("answer_man","请输入回复人！");
		return false;
	}
	
	if(answer_info == ""){
		error_show("answer_info","请输入回复内容！");
		return false;
	}
	
	//var   is_at="0";
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/article/answer_post.do",
 		dataType:"json",
 		data:{"discuss_id":discuss_id,"answer_man":answer_man,"answer_info":answer_info},
 		success: function(data){
 			
 			if(data[0].back_code == 100){
 				
 				var page_num = Number($("#page_num").val());
 				var url_data = {"page":page_num };
 				get_data(url_data);
 			}
 		}
	 });
	
}
</script>
</html>
