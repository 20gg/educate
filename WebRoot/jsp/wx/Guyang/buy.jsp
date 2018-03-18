<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>到答课堂 </title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" href="<%=basePath%>css/wx/buy_online/buy.css" type="text/css">
    
     

  </head>
  
  <body>
    <div class="top_box">
		<div class="top">
			<div class="head">
				<img src="${user.head}">
			</div>
			<div class="name">${user.name}</div>
			<c:if test="${user.role != 1}">
				<span>会员升级</span>
			</c:if> 
			<div class="lel" >
				<img id="role_img" src="<%=basePath%>/img/role/role${user.role}.png">
			</div>
			<div class="clearfix"></div>
		</div>			
	</div>
	
	<div class="top_box">
			<div class="title">
				<span></span>
				<h1>商品信息</h1>
				<span style="margin-left:0px"></span>
				<div class="clearfix"></div>			
			</div>
			<div class="row">
				<div class="col1">
				<c:if test="${kind == 1}">课程名称：</c:if>
				<c:if test="${kind == 2}">专题名称：</c:if>
				</div>
				<div class="col2" style="color:#2dc158;">
					<c:if test="${kind == 1}">
						<c:if test="${fn:length(doc.c_name)>8}">  
                         ${fn:substring(doc.c_name, 0, 8)}...  
                  		 </c:if>
                  		 <c:if test="${fn:length(doc.c_name)<=8 }">  
                         ${doc.c_name}
                  		 </c:if>
					</c:if>
					
					<c:if test="${kind == 2}">
						<c:if test="${fn:length(doc.special_name)>8 }">  
                         ${fn:substring(doc.special_name, 0, 8)}...  
                  		 </c:if>
                  		  <c:if test="${fn:length(doc.special_name)<=8 }">  
                         ${doc.special_name}
                  		 </c:if>
					</c:if>
				 	 
                  </div>
				<div class="clearfix"></div>
			</div>
			<div class="row" style="border:none;">
				<div class="col1">
					<c:if test="${kind == 1}">课程价格：</c:if>
					<c:if test="${kind == 2}">专题价格：</c:if>
				</div>
				<div class="col2" style="color:#ffa42f;">
					<fmt:formatNumber type="currency" value="${doc.price/100}" maxFractionDigits="2"/>
				</div>
				<div class="clearfix"></div> 
			</div>
		</div>
		
		<div class="top_box" >
			<div class="title">
				<span></span>
				<h1>购买信息</h1>
				<span style="margin-left:0px"></span>
				<div class="clearfix"></div>			
			</div>
			<div class="row">
				<div class="col1">姓名</div>
				<div class="col2">
				<input  type="text" placeholder="请填写您的姓名" style="border: none;height: 40px; font-size: 16px; " value="${user.u_name}"  id="name">
				
				</div>
				<div class="clearfix"></div> 
			</div>
			<div class="row" >
				<div class="col1">手机</div>
				<div class="col2">
				
				<input   placeholder="请填写您的手机" style="border: none;height: 40px;font-size: 16px;" value="${user.phone}"  id="phone" >
				</div>
				<div class="clearfix"></div>
			</div>
			
			<div class="row" >
				<div style="float:left;width:15%;line-height:50px;color:#323232;text-align:left;font-size:15px;">验证码</div>
				<div class="col2">
				
				<div style="float: left;width: 80%;text-align: center;margin-left: 12px;">
				<input type="text" placeholder="输入验证码"   id="my_code" style="border: none;height: 40px;font-size: 16px;width: 100px;" onblur="is_true();"></input>
				</div>
				 
				</div>
				<div style="float: left;width: 25%; margin-top: 9px;">
				
				<button id="clab" type="button" onclick="send();" style="border: none;height: 30px;font-size: 10px;background-color: #2dc158;color: #fff;">获取验证码</button>
				</div>
				 
				<div class="clearfix"></div>
			</div>
			
			<div class="row">
				<div class="col1">公司</div>
				<div class="col2">
				<input   placeholder="请填写您的公司" style="border: none;height: 40px;font-size: 16px;"  value="${user.company}" id="company">				
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="row" style="border:none; ">
				<div class="col1">职称</div>
				<div class="col2">
				<input   placeholder="请填写您的职位" style="border: none;height: 40px; font-size: 16px;" value="${user.job}" id="job">
				</div>
				<div class="clearfix"></div>
			</div>
			 
		</div>
		
		<c:if test="${user.role != 1 && count== 0 && not empty doc}">
			<div  style=" background-color: #fff;  height: 50px; padding-left: 4%;
			 padding-right: 4%; line-height: 50px;  color: #323232; font-size: 15px;">
			 	 使用奖学金支付 
			 	<div class="choose_yes" id="scholship" code="1" onclick="choose_it(1);" ></div>
			 	
			</div> 
			<div  style=" background-color: #fff;  height: 50px; padding-left: 4%;
			 padding-right: 4%; line-height: 50px;  color: #323232; font-size: 15px;">
			 	 使用学分支付 
			 	<div class="choose_yes" id="score" code="1" onclick="choose_it(2);" ></div>
			 	
			</div> 
			<div class="button" style="background-color: #f6f7f9;">
			<span id="join_button" onclick="join_buy();">确定</span>
			</div>
		</c:if>
			
			<input type="hidden" id="c_no" value="${c_no}">
			<input type="hidden" id="kind" value="${kind}" >
		
	
  </body>
  <script type="text/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/path.js?v=1.0"></script>
 <script type="text/javascript" src="<%=basePath%>js/wx/wx_public2.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/wx/share_wx.js"></script> 
 <script type="text/javascript" src="<%=basePath%>js/wx/wx_pay.js"></script>  
<script type="text/javascript" src="<%=basePath%>js/jquery.tips.js"></script>
<script type="text/javascript">
//var openid ="oLir8jsziazLpKT0b6JSSbQ0icR4";

var share_ul="buy";
function join_buy() {
		
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
				msg : '请输入正确手机号',
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
		
		var c_no = $("#c_no").val();
		
		var kind = $("#kind").val();
		
		var scholship =  $("#scholship").attr("code");
		
		var score =  $("#score").attr("code");
		
		//alert(scholship);
		
		  $.ajax({
			type:"post",
			dataType:"json",
			url:pt_path+"wxpay/buy_order.do",
			data : {"openid":openid,"name":name,"phone":phone,"company":company,"job":job,
				"c_no":c_no,"kind":kind,"scholship":scholship,"score":score,"code":code},
			success : function(data){
				
				if(data[0].back_code == 1){
					if(data[0].need_pay == 1){ 
						
						 my_c_no = c_no;
						 my_type = data[0].type;
						 
						// alert(data[0].order_id);
						
						into_pay(data[0].order_id,1);
						
					}else if(data[0].need_pay == 0){
						
						 if(data[0].type == 0){
							 //音频
							 localStorage.setItem("c_no", c_no );
							 window.location.href=pt_path+'jsp/wx/course/buy_voice.html';
							 
						 }else  if(data[0].type == 1){
							 //视频
							 localStorage.setItem("c_no", c_no );
							 window.location.href=pt_path+'jsp/wx/course/buy_course.html';
							 
						 }else  if(data[0].type == 2){
							 //专题 
							  localStorage.setItem("c_no", c_no );
							 
							 window.location.href=pt_path+'jsp/wx/course/af_special.html';
							 
						 }
						
					}else{
						alert("购买失败！");
					} 
					 
					//下单失败
					
					//全额奖学金支付
					
					//部分奖学金支付
				}else{
					alert("验证码与手机号错误");
				}
				
				
			}
		 });  
	  
}


 
var my_c_no = "";
var my_type = 0;

function into_c_page(){
	
	 
	
	if(my_type == 0){
		 //音频
		 localStorage.setItem("c_no", my_c_no );
		 window.location.href=pt_path+'jsp/wx/course/buy_voice.html';
		 
	 }else  if(my_type == 1){
		 //视频
		 localStorage.setItem("c_no", my_c_no );
		 window.location.href=pt_path+'jsp/wx/course/buy_course.html';
		 
	 }else  if(my_type == 2){
		 //专题 
		  localStorage.setItem("c_no", my_c_no );
		 
		 window.location.href=pt_path+'jsp/wx/course/af_special.html';
		 
	 }
}

function choose_it(n) {
	
	var scholship =  $("#scholship").attr("code");
	
	
	var score =  $("#score").attr("code");
		
	if(n==1){
		if(scholship == 1){
			
			$("#scholship").attr("class","choose_no"); 
			$("#scholship").attr("code",0);
		}else{
			$("#scholship").attr("class","choose_yes"); 
			$("#scholship").attr("code",1);
		}
		
	}else{
		
		if(score == 1){
			
			$("#score").attr("class","choose_no"); 
			$("#score").attr("code",0);
		}else{
			$("#score").attr("class","choose_yes"); 
			$("#score").attr("code",1);
		}
	}
	
	
	
}

function isPhone (string) {    
	var pattern = /^1[34578]\d{9}$/;    
	if (pattern.test(string)) {        
	return true;    
	}else{
	return false;
	}  
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
