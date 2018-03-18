<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>模板消息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	    <link rel="stylesheet" href="<%=basePath%>css/tanchu.css" /> 
  </head> 
  <body>
  
  	<div style="margin-top: 20px; width: 90%;margin-left: 5%;margin-right: 5%">
  	
		  	<ul class="nav nav-tabs" role="tablist">
		    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">课程(文章)更新提醒</a></li>
		    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">上课提醒</a></li>
		    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">课程开课通知（直播专栏）</a></li>
		    <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">活动即将开始提醒</a></li>
		  </ul>
		
		  <!-- Tab panes -->
		  <div class="tab-content">
		    <div role="tabpanel" class="tab-pane active" id="home">
		    	<br>
	    		<div>
				<input type="text" id="frist_5" class="form-control" style="width: 500px;" placeholder="模板消息标题" >
				</div><br>
				<div>
				<input type="text" id="key1_5" class="form-control" style="width: 500px;" placeholder="课程名称" >
				</div><br>
				<div>
				<input type="text" id="key2_5" class="form-control" style="width: 500px;" placeholder="课程类别" >
				</div><br>
				<div>
				<input type="text" id="key3_5" class="form-control" style="width: 500px;" placeholder="课程老师" >
				</div><br>
				<div>
				<input type="text" id="key4_5" class="form-control" style="width: 500px;" placeholder="课程时间" >
				</div><br>
				<div>
				<input type="text" id="remark_5" class="form-control" style="width: 500px;" placeholder="备注" >
				</div><br>
				
				<div>
				<input type="text" id="url_5" class="form-control" style="width: 500px;" placeholder="详情链接地址" >
				</div><br>
				
				
				 <button type="button" class="btn btn-primary"   style="background-color: green;border-color: green; width: 160px;" onclick="go_send(5);"> 发送</button>
		    	
		    	
		    </div>
		    <div role="tabpanel" class="tab-pane" id="profile">
		    	<br>
	    		<div>
				<input type="text" id="frist_6" class="form-control" style="width: 500px;" placeholder="模板消息标题" >
				</div><br>
				<div>
				<input type="text" id="key1_6" class="form-control" style="width: 500px;" placeholder="课程名称" >
				</div><br>
				<div>
				<input type="text" id="key2_6" class="form-control" style="width: 500px;" placeholder="时间" >
				</div><br>
				
				<div>
				<input type="text" id="remark_6" class="form-control" style="width: 500px;" placeholder="备注" >
				</div><br>
				
				<div>
				<input type="text" id="url_6" class="form-control" style="width: 500px;" placeholder="详情链接地址" >
				</div><br>
				 
				<button type="button" class="btn btn-primary"   style="background-color: green;border-color: green; width: 160px;" onclick="go_send(6);"> 发送</button>
		    	
		    </div>
		    <div role="tabpanel" class="tab-pane" id="messages">
		    	
		    	<br>
	    		<div>
				您好：<input type="text" id="userName"  style="width: 500px;" placeholder="消息标题" >
				</div><br>
				
				<br>
	    		<div>
				您报名参加的<input type="text" id="courseName"   style="width: 200px;" placeholder="课程名称" >
				将于<input type="text" id="date"   style="width: 200px;" placeholder="时间" >开课，特此通知。
				</div><br>
				
				<div>
				<input type="text" id="remark_7" class="form-control" style="width: 500px;" placeholder="备注" >
				</div><br>
				
				<div>
				<input type="text" id="url_7" class="form-control" style="width: 500px;" placeholder="详情链接地址" >
				</div><br>
				 
				 <button type="button" class="btn btn-primary"   style="background-color: green;border-color: green; width: 160px;" onclick="go_send(7);"> 发送</button>
		    	
		    </div>
		    <div role="tabpanel" class="tab-pane" id="settings">
		    	<br>
	    		<div>
				<input type="text" id="frist_12" class="form-control" style="width: 500px;" placeholder="模板消息标题" >
				</div><br>
				<div>
				<input type="text" id="key1_12" class="form-control" style="width: 500px;" placeholder="主题" >
				</div><br>
				<div>
				<input type="text" id="key2_12" class="form-control" style="width: 500px;" placeholder="时间" >
				</div><br>
				
				<div>
				<input type="text" id="remark_12" class="form-control" style="width: 500px;" placeholder="备注" >
				</div><br>
				
				<div>
				<input type="text" id="url_12" class="form-control" style="width: 500px;" placeholder="详情链接地址" >
				</div><br>
				 
				<button type="button" class="btn btn-primary"   style="background-color: green;border-color: green; width: 160px;" onclick="go_send(12);"> 发送</button>
		    	
		    </div>
		  </div>
		  	
  	</div>
  
   <div class="load_tc_back" style="display: none;" id="myload">
<div class="load_tc_page">
	<div class="load_tc_img">
	</div>
</div>
</div> 

<div class="tc_back_div" style="display: none;" id="myshare" onclick="close_share();">
	<div style="width: 70%; height: 100px; float: right;">
		 <img src="../../../img/post/share.png" style="width: 100%; height: 100%;">
	</div>
</div>
  </body>
  
   <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
  	<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
  	 
<script type="text/javascript">
function go_send(msg_type){
	 $("#myload").show();
	var data_info;
  		 
	if(msg_type == 5){
		
		var frist = $("#frist_5").val();
		var keyword1= $("#key1_5").val();
		var keyword2= $("#key2_5").val();
		var keyword3= $("#key3_5").val();
		var keyword4= $("#key4_5").val();
		var remark= $("#remark_5").val(); 
		var url = $("#url_5").val();
		
		if(frist==null||frist==""){
			$("#frist_5").tips({
				side : 2,
				msg : "请填写模板标题",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(keyword1==null||keyword1==""){
			$("#key1_5").tips({
				side : 2,
				msg : "请填写课程名称",
				bg : 'green',
				time : 3
			});
			return false;
		}
		if(keyword2==null||keyword2==""){
			$("#key2_5").tips({
				side : 2,
				msg : "请填写课程类别",
				bg : 'green',
				time : 3
			});
			return false;
		}
		if(keyword3==null||keyword3==""){
			$("#key3_5").tips({
				side : 2,
				msg : "请填写课程老师",
				bg : 'green',
				time : 3
			});
			return false;
		}
		if(keyword4==null||keyword4==""){
			$("#key4_5").tips({
				side : 2,
				msg : "请填写课程时间",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(remark==null||remark==""){
			$("#remark_5").tips({
				side : 2,
				msg : "请填写备注",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		data_info = {"frist":frist,"keyword1":keyword1,"keyword2":keyword2,"keyword3":keyword3,"keyword4":keyword4,"remark":remark,"url":url,"msg_type":5};
	}else if(msg_type == 6){
		
		var frist = $("#frist_6").val();
		var keyword1= $("#key1_6").val();
		var keyword2= $("#key2_6").val();
		var remark= $("#remark_6").val(); 
		var url = $("#url_6").val();
		
		if(frist==null||frist==""){
			$("#frist_6").tips({
				side : 2,
				msg : "请填写模板标题",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(keyword1==null||keyword1==""){
			$("#key1_6").tips({
				side : 2,
				msg : "请填写课程名称",
				bg : 'green',
				time : 3
			});
			return false;
		}
		if(keyword2==null||keyword2==""){
			$("#key2_6").tips({
				side : 2,
				msg : "请填写时间",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(remark==null||remark==""){
			$("#remark_6").tips({
				side : 2,
				msg : "请填写备注",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		data_info = {"frist":frist,"keyword1":keyword1,"keyword2":keyword2,"remark":remark,"url":url,"msg_type":6};
		
	}else if(msg_type == 7){
		
		var userName = $("#userName").val();
		var courseName= $("#courseName").val();
		var date= $("#date").val();
		var remark= $("#remark_7").val(); 
		var url = $("#url_7").val();
		
		if(userName==null||userName==""){
			$("#userName").tips({
				side : 2,
				msg : "请填写模板标题",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(courseName==null||courseName==""){
			$("#courseName").tips({
				side : 2,
				msg : "请填写课程名称",
				bg : 'green',
				time : 3
			});
			return false;
		}
		if(date==null||date==""){
			$("#date").tips({
				side : 2,
				msg : "请填写时间",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(remark==null||remark==""){
			$("#remark_7").tips({
				side : 2,
				msg : "请填写备注",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		data_info = {"userName":userName,"courseName":courseName,"date":date,"remark":remark,"url":url,"msg_type":7};
		
	}else if(msg_type == 12){
		
		var frist = $("#frist_12").val();
		var keyword1= $("#key1_12").val();
		var keyword2= $("#key2_12").val();
		var remark= $("#remark_12").val(); 
		var url = $("#url_12").val();
		
		if(frist==null||frist==""){
			$("#frist_12").tips({
				side : 2,
				msg : "请填写模板标题",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(keyword1==null||keyword1==""){
			$("#key1_12").tips({
				side : 2,
				msg : "请填写课程名称",
				bg : 'green',
				time : 3
			});
			return false;
		}
		if(keyword2==null||keyword2==""){
			$("#key2_12").tips({
				side : 2,
				msg : "请填写时间",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		if(remark==null||remark==""){
			$("#remark_12").tips({
				side : 2,
				msg : "请填写备注",
				bg : 'green',
				time : 3
			});
			return false;
		}
		
		data_info = {"frist":frist,"keyword1":keyword1,"keyword2":keyword2,"remark":remark,"url":url,"msg_type":12};
		
	}
	  
   	
 	$.ajax({
 		
 		type:"POST",
 		url:getRootPath() +"/course/to_send.do",
 		data:data_info,
		dataType:"json",
		success:function(data){
			 $("#myload").hide();
			alert("消息发送成功！");
			 
			location.href = getRootPath() +"/jsp/pt/mould/course_mould.jsp"; 
		}
 	});
  		 
  		
  		
}
  	
  	
   
</script>
</html>
