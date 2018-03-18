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
    
    <title>课程试题</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" />

  </head>
  
  <body>
   <div style="margin-top: 20px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">课程试题
     	<button type="button"  class="btn btn-success"  
  				 style="width: 100px; float: right;" onclick="add_show();">新增试题</button>
     </div>
     
     <c:if test="${fn:length(exercise_list) == 0}">
     	<div style=";width: 94%; margin-left: 3%;  line-height: 30px;
     	 color: red;margin-top: 20px;">还未添加试题，请及时添加！</div>
     </c:if>
     
     <table class="table table-hover table-bordered"  style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:14px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;" >
  		
  		<c:forEach items="${exercise_list}" var="e" varStatus="ee">
  		<tr>
  			<td style="width: 5%; "><b>${ee.index+1}.</b></td>
  			<td style="width: 95%; text-align: left; " colspan="3"> 
  				<button type="button"  class="btn btn-danger" style="width: 60px; float: right;" onclick="delete_show('${e._id}');" >删除</button>
  				<button type="button"  class="btn btn-default" style="width: 60px; float: right; margin-right: 20px;"
  				onclick="edit_show('${e._id}','${e.problem}','${e.A}','${e.B}','${e.C}','${e.D}','${e.answer}');">修改</button> 
  				${e.problem} <span style="margin-left: 30px;">答案：<b style="color: red;">${e.answer}</b></span>
  				 
  			</td>
  		</tr>
  		<tr>
  			<td style="width: 5%; "><b>A:</b></td>
  			<td style="width: 45%; text-align: left; ">${e.A}</td>
  			<td style="width: 5%; "><b>B:</b></td>
  			<td style="width: 45%; text-align: left; ">${e.B}</td>
  		</tr>
  		<tr>
  			<td style="width: 5%; "><b>C:</b></td>
  			<td style="width: 45%; text-align: left; ">${e.C}</td>
  			<td style="width: 5%; "><b>D:</b></td>
  			<td style="width: 45%; text-align: left; ">${e.D}</td>
  		</tr>
  		</c:forEach>
  	</table>
  	
  	<br><br>
  	
 <div id="add_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 500px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="add_title">新增试题</h4>
      </div>
      <div class="modal-body" style="text-align: center;">
      		<div>
      			<textarea  class="form-control" id="problem" placeholder="请输入题目内容"></textarea>
      		</div>
      		<div class="input-group" style="margin-top: 10px;">
			  <span class="input-group-addon">A</span>
			  <input type="text" class="form-control" id="answer_a" placeholder="请输入A选项答案">
			</div>
			<div class="input-group" style="margin-top: 10px;">
			  <span class="input-group-addon">B</span>
			  <input type="text" class="form-control" id="answer_b" placeholder="请输入B选项答案">
			</div>
			<div class="input-group" style="margin-top: 10px;">
			  <span class="input-group-addon">C</span>
			  <input type="text" class="form-control" id="answer_c" placeholder="请输入C选项答案">
			</div>
			<div class="input-group" style="margin-top: 10px;">
			  <span class="input-group-addon">D</span>
			  <input type="text" class="form-control" id="answer_d" placeholder="请输入D选项答案">
			</div> 
			<div class="input-group" style="margin-top: 10px;" >
			  <span class="input-group-addon">正确答案</span>
			  <select class="form-control" id="answer">
			  	<option value="A">A</option>
			  	<option value="B">B</option>
			  	<option value="C">C</option>
			  	<option value="D">D</option>
			  </select>
			</div>  
         	  
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="commit();;">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">关闭</button>
      </div>
    </div> 
  </div> 
</div> 

 <div id="delete_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 360px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >删除试题</h4>
      </div>
      <div class="modal-body" style="text-align: center;"><label>确认删除试题？</label></div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="delete_ok();;">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">取消</button>
      </div>
    </div> 
  </div> 
</div> 

<input type="hidden" id="exercise_id" value="-1">
<input type="hidden" id="c_no" value="${c_no}">
     
  </body>
  <script type="text/javascript" src="<%=basePath%>/js/jquery-2.1.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>/boot/3.0.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.tips.js"></script>
<script type="text/javascript">
function add_show() {
	
	$("#add_title").text("新增试题");
	
	$("#problem").val("");
	$("#answer_a").val("");
	$("#answer_b").val("");
	$("#answer_c").val("");
	$("#answer_d").val("");
	
	$("#exercise_id").val(-1);
	 
	$("#add_page").modal({
		keyboard : false
	}); 
	$("#add_page").modal('show');
}

function edit_show(e_id,e_problem,e_a,e_b,e_c,e_d,e_answer){
	
	$("#add_title").text("修改试题");
	
	$("#problem").val(e_problem);
	$("#answer_a").val(e_a);
	$("#answer_b").val(e_b);
	$("#answer_c").val(e_c);
	$("#answer_d").val(e_d);
	
	$("#answer").val(e_answer);
	
	$("#exercise_id").val(e_id);
	
	$("#add_page").modal({
		keyboard : false
	}); 
	$("#add_page").modal('show');
	 
}

function error_show(id_name,msg) {
	$("#"+id_name).tips({
			side : 2,
			msg : msg,
			bg : '#2CC159',
			time : 3
	});
}

function commit(){
	
	var problem = $("#problem").val();
	if(problem == ""){
		error_show("problem","请输入题目内容！");
		return false;
	}
	var answer_a = $("#answer_a").val();
	if(answer_a == ""){
		error_show("answer_a","请输入选项A答案！");
		return false;
	}
	var answer_b = $("#answer_b").val();
	if(answer_b == ""){
		error_show("answer_b","请输入选项B答案！");
		return false;
	}
	var answer_c = $("#answer_c").val();
	if(answer_c == ""){
		error_show("answer_c","请输入选项C答案！");
		return false;
	}
	var answer_d = $("#answer_d").val(); 
	if(answer_d == ""){
		error_show("answer_d","请输入选项D答案！");
		return false;
	}
	var answer = $("#answer").val();
	
	var exercise_id = $("#exercise_id").val();
	
	var c_no = $("#c_no").val();
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/article/commit_exercise.do",
 		dataType:"json",
 		data:{"problem":problem,"answer_a":answer_a,"answer_b":answer_b,"answer_c":answer_c,
 			"answer_d":answer_d,"answer":answer,"exercise_id":exercise_id,"c_no":c_no},
 		success: function(data){
 			
 			if(data[0].back_code == 100){
 				 
 				location.href = pt_path +"/article/query_exercise.do?c_no="+c_no; 
 			}
 		}
	 });
	
}

function delete_show(e_id){
	
	$("#exercise_id").val(e_id);
	
	$("#delete_page").modal({
		keyboard : false
	}); 
	$("#delete_page").modal('show');
}

function delete_ok() {
	
	var exercise_id = $("#exercise_id").val();
	var c_no = $("#c_no").val();
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/article/delete_exercise.do",
 		dataType:"json",
 		data:{"exercise_id":exercise_id},
 		success: function(data){
 			
 			if(data[0].back_code == 100){
 				 
 				location.href = pt_path +"/article/query_exercise.do?c_no="+c_no; 
 			}
 		}
	 });
}

</script>
</html>
