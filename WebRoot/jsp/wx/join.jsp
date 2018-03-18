<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>到答课堂</title>
    
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	 
	 <link rel="stylesheet" href="<%=basePath%>css/wx/buy_online/personal_mes.css" type="text/css">
	 
	  <style type="text/css"> 
  	.div_2{
  	width:100%;
  	float:left;
  	color: blue; border-bottom: solid 2px #f4f4f4;
  	 line-height: 40px; 
  	 
  	} 
  	.button_css{
  		color:white;
  		position:fixed;bottom:0px;left:0px;
  		background-color: #4876FF;
  		width: 100%;
  		line-height: 50px;
  	}
  
  </style>

  </head>
  
  <body style="overflow-x: hidden;">
  
   
   		<div class="top_box" >
			
			<div class="row">
				<div class="col1">姓名</div>
				<div class="col2">
				<input  type="text" placeholder="请填写您的姓名" style="border: none;height: 30px; font-size: 14px;padding-top: 18px;" value="${user.u_name}"  id="name">
				
				</div>
				<div class="clearfix"></div> 
			</div>
			<div class="row" >
				<div class="col1">手机</div>
				<div class="col2">
				
				<input placeholder="请填写您的手机" style="border: none;height: 30px; font-size: 14px;padding-top: 18px;" value="${user.phone}"  id="phone">
				</div>
				<div class="clearfix"></div>
			</div>
			
			<div class="row" >
				<div style="float:left;width:15%;line-height:50px;color:#323232;text-align:left;font-size:15px;">验证码</div>
				<div class="col2">
				<div style="float: left;width: 100%;text-align: center;margin-left: 12px;">
				<input type="text" placeholder="输入验证码"   id="my_code" style="border: none;height: 40px;font-size: 14px;width: 100px;" onblur="is_true();"></input>
				</div>
				 
				</div>
				<div style="float: left;width: 26%; margin-top: 9px;">
				
				<button id="clab" type="button" onclick="send();" style="border: none;height: 30px;font-size: 10px;background-color: #2dc158;color: #fff;">获取验证码</button>
				</div>
				 
				<div class="clearfix"></div>
			</div>
			
			<div class="row">
				<div class="col1">公司</div>
				<div class="col2">
				<input   placeholder="请填写您的公司" style="border: none;height: 30px; font-size: 14px;padding-top: 18px;"  value="${user.company}" id="company">				
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="row" style="border:none;">
				<div class="col1">职称</div>
				<div class="col2">
				<input   placeholder="请填写您的职位" style="border: none;height: 30px; font-size: 14px;padding-top: 18px;" value="${user.job}" id="job">
				</div>
				<div class="clearfix"></div>
			</div>
			
		</div>
		
		<c:if test="${user.role != 1}">
			<div class="button" >
				<span id="join_button" onclick="join_vip();" >立即加入</span>
			</div>
		</c:if>
		
		
  </body>
<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/path.js?v=1.0"></script>
<script type="text/javascript" src="<%=basePath%>js/wx/wx_public.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/wx/share_wx.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/wx/wx_pay.js?v=0.2"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=basePath%>js/wx/tan_c.js"></script>
<script type="text/javascript">

var share_ul="join.jsp";

 
function join_vip() {
		
		$("#join_button").attr("disabled", true);
	
		var name=$("#name").val();
		var phone=$("#phone").val();
		var company=$("#company").val();
		var job=$("#job").val();
		var code = $("#my_code").val();
	
		 		
		if(name==null||name==""){
			$("#name").tips({
				side : 3,
				msg : '请输入姓名',
				bg : '#2dc158',
				time :3
			});
			
			$("#join_button").attr("disabled", false);
			return false;
		}	
		
		if(phone==null||phone=="" || isPhone(phone) == false){
			$("#phone").tips({
				side : 3,
				msg : '请输入手机',
				bg : '#2dc158',
				time :2
			});
			$("#join_button").attr("disabled", false);
			return false;
		}
		
		if(code==null||code=="" ){
			$("#my_code").tips({
				side : 3,
				msg : '请输入验证码',
				bg : '#2dc158',
				time :2
			});
			$("#join_button").attr("disabled", false);
			return false;
		}

		if(company==null||company==""){
			$("#company").tips({
				side : 3,
				msg : '请输入公司',
				bg : '#2dc158',
				time :2
			});
			$("#join_button").attr("disabled", false);
			return false;
		}

		if(job==null||job==""){
			$("#job").tips({
				side : 3,
				msg : '请输入职位',
				bg : '#2dc158',
				time :2
			});
			
			$("#join_button").attr("disabled", false);
			return false;
		}
		
		 $.ajax({
			type:"post",
			dataType:"json",
			url:pt_path+"wxpay/buy_vip.do",
			data : {"openid":openid,"name":name,"phone":phone,"company":company,"job":job,"code":code},
			success : function(data){
				
				if(data[0].back_code == 1){
					
					//order_id = ;
					//kind = 2;
					
					into_pay(data[0].order_id,2);
					
				}else if(data[0].back_code == -1 || data[0].back_code == 0){
					alert("验证码与手机号错误");
				}else {
					alert("已经是vip了");
				}
				
			}
		 });
	  
}

function isPhone (string) {    
	var pattern = /^1[34578]\d{9}$/;    
	if (pattern.test(string)) {        
	return true;    
	}else{
	return false;
	}  
}

function into_c_page() {
	
}

function send(){
	
	 var phone=$("#phone").val();
	 
	 $("#clab").attr("disabled",true);
	  
	  if(phone==null || phone =="" || isPhone(phone) == false){
		  $("#phone").tips({
				side : 3,
				msg : '请输入手机号',
				bg : '#2dc158',
				time :2
			});
		  $("#clab").attr("disabled",false);
		  return false;
	  }
	  
	  $.ajax({
			type:"post",
	  		url: pt_path +"/user/sendmessage_phone.do",
	  		dataType:"json",
	  		data:{"phone":phone},
	  		success: function(data){ 
	  			
	  			var  random = data[0].random;
	  			 
	  		// 短信发送成功 
				has_send = true;
				 
				$('#clab').text("已发送");
				setInterval(i);
				var num = 60;
				var i = setInterval(function() {
				 	if(num > 0){
				 		num--;
				 		$("#clab").text("已发送"+num+"s");
				 		 
				 	}else{
				 		  clearInterval(i);
				 		   
				 		 $("#clab").attr("disabled",false);
				 		 $("#clab").text("发送验证码");
				 		  
				 		  
				 	}
		              
		        }, 1000);
	  		}
	 
		});
}
</script>
</html>
