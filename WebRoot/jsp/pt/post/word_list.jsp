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
    
    <title>敏感字管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	 <link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" />

  </head>
  
  <body>
     <div style="margin-top: 20px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">敏感字管理 </div>
     
      <div style="margin-top: 20px;width: 94%; margin-left: 3%; ">
     		<button type="button" class="btn btn-primary"  onclick="add_show();">添加敏感字</button>
     		 
     </div>
     
      <table class="table table-hover table-bordered" style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:12px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;">
  			<tr style="background-color: #fcfcfc;">
  			<td ><label>序号</label></td> 
  			<td style="width: 80%; "><label>敏感字</label></td> 
  			<td ></td>
  			</tr>
  			
  			<c:forEach items="${word_list}" var="w" varStatus="ws">
  			<tr>
	  			<td >${ws.index+1}</td> 
	  			<td style="width: 80%; text-align: left;">${w.word}</td> 
	  			<td ><a onclick="delete_it('${w._id}');">删除</a></td>
  			</tr>
  			</c:forEach>
  			  
  	</table>
     <br><br><br>
      
     <div id="add_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 360px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">添加敏感字</h4>
      </div>
      <div class="modal-body">
         	  <input type="text" class="form-control" id="add_word" maxlength="32"  placeholder="请输入敏感字">
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="commit_word();;">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">关闭</button>
      </div>
    </div> 
  </div> 
</div> 
     
  </body>
<script type="text/javascript" src="<%=basePath%>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/boot/3.0.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.tips.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
<script type="text/javascript">
function add_show() {
	
	$("#add_word").val();
	
	$("#add_page").modal({
		keyboard : false
	}); 
	$("#add_page").modal('show');
}

function commit_word(){
	
	var word = $("#add_word").val();
	
	if(word == ""){
		$("#add_word").tips({
			side : 2,
			msg : "请输入敏感字！",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	

	$.ajax({
 		type:"post",
 		url: pt_path +"/area/add_word.do",
 		dataType:"json",
 		data:{"word":word },
 		success: function(data){
 			
 			if(data[0].back_code == 200){
 				 
 				location.href = pt_path +"/area/word_list.do"; 
 			}else{
 				 
 					$("#add_word").tips({
 						side : 2,
 						msg : "该敏感字已存在！",
 						bg : '#2CC159',
 						time : 3
 					});
 					 
 			}
 		}
	 });
}

function delete_it(w_id){
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/area/delete_word.do",
 		dataType:"json",
 		data:{"word_id":w_id },
 		success: function(data){
 			
 			if(data[0].back_code == 200){
 				 
 				location.href = pt_path +"/area/word_list.do"; 
 			} 
 		}
	 });
}

</script>
</html>
