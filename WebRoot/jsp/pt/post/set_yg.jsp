<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预告设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" />
	<link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css"
	type="text/css"></link>

  </head>
  
  <body>
     <div style="margin-top: 20px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">预告设置 </div>
     
     
     <div style="padding-left: 40px; margin-top: 20px;">
     
      <div style="height: 40px; line-height: 40px;">
      		<input type="text" class="form-control" style="width: 500px;" id="name" maxlength="32" value="${yg.name}"  placeholder="请输入预告标题">
      </div>
       <div style="height: 40px; line-height: 40px;">
      		<input type="text" class="form-control" style="width: 500px;" id="time_note" maxlength="8" value="${yg.time_note}"  placeholder="请输入预告时间描述">
      </div>
      <div style="height: 40px; line-height: 40px;">
      		<input type="text" class="form-control" style="width: 500px;" id="link" value="${yg.link}" placeholder="请输入链接地址">
      </div>
      <div style="height: 40px; line-height: 40px;">
      		<input type="text" class="form-control" style="width: 500px;" id="time" readonly="readonly" value="${yg.time}"
      		onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
      		 placeholder="请选择播放时间">
      </div>
     
     <div style="height: 40px; line-height: 40px;">
     	<button type="button" class="btn btn-primary" style="width: 120px;" onclick="commit_set();;">提交</button>
     </div>
     
     </div>
     
     <input type="hidden" id="yg_id" value="${yg._id}">
     
  </body>
 <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
 <script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/url.js"></script>
 <script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
 <script type="text/javascript" src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
 <script type="text/javascript" src="<%=basePath %>js/jquery.jplayer.min.js"></script>
  	
<script type="text/javascript">
function get_data(url_data) {
	jspost(pt_path +"/area/aaa_yg.do", url_data);
}

function commit_set(){
	
	var id = $("#yg_id").val();
	var name = $("#name").val();
	var time_note = $("#time_note").val();
	var link = $("#link").val();
	var time = $("#time").val();
	
	if(name == ""){
		$("#name").tips({
			side : 2,
			msg : "请输入预告标题！",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	
	if(time_note == ""){
		$("#time_note").tips({
			side : 2,
			msg : "请输入预告时间描述",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	
	if(link == ""){
		$("#link").tips({
			side : 2,
			msg : "请输入链接地址！",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	
	if(time == ""){
		$("#time").tips({
			side : 2,
			msg : "请选择播放时间",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	
	var url_data = {"id":id,"name":name,"link":link,"time":time,"time_note":time_note};
	get_data(url_data);
}


</script>
</html>
