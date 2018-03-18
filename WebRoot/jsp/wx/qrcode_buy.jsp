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
    
    <title>管理者必学的价值千万的薪酬设计课程</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	 <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
	<meta name="content-type" content="text/html; charset=UTF-8">
	
	<style type="text/css"> 
	body,h1,h2,h3,h4,h5{padding:0;margin:0;font-weight:normal;font-family:"微软雅黑";}
:-webkit-scrollbar{width:0px;height:0px}
body{margin:0;padding:0;}
.clearfix{clear:both;}

.top_01{
	width:100%;
	margin:auto auto;
}
.top_01 img{
	display:block;
	width:100%;
}
.tl{
	width:100%;
	margin:auto auto;
}
.tl .title{
	width:94%;
	margin:auto auto;
	border-bottom:1px solid #444444;
	padding-top:10px;
	padding-bottom:10px;
}
.tl .title span{
	float:left;
	width:3px;
	height:20px;
	background-color: #2DC158;
}
.tl .title h1{
	float:left;
	margin-left:2%;
	font-size:15px;
	line-height:20px;
	text-align:center;
	color:#323232;
}
.tl .title h2{
	float:left;
	margin-left:2%;
	font-size:12px;
	line-height:20px;
	text-align:center;
	color:#777777;
}
.top_02{
	width:94%;
	margin:auto auto;
	border-bottom:1px solid #dcdcdc;
}
.top_02 img{
	display:block;
	width:100%;
}
.tl .text{
	width:94%;
	margin:auto auto;
	margin-bottom:10px;
}
.tl .text h1{
	color:#2DC158;
	font-size:14px;
	text-align:left;
	line-height:26px;
}
.tl .text h2{
	color:#646464;
	font-size:14px;
	text-align:left;
	line-height:26px;
}
.tl .text2{
	width:94%;
	margin:auto auto;
	border-top:1px solid #DCDCDC;
	margin-bottom:10px;
}
.tl .text2 h1{
	color:#2DC158;
	font-size:14px;
	text-align:left;
	line-height:26px;
}
.tl .text2 h2{
	color:#646464;
	font-size:14px;
	text-align:left;
	line-height:26px;
}
.tl .text1{
	width:94%;
	margin:auto auto;
	border-top:1px solid #DCDCDC;
	margin-bottom:10px;
}
.tl .text1 h1{
	color:#646464;
	font-size:14px;
	text-align:left;
	line-height:26px;
}
.tl .text1 h2{
	color:#646464;
	font-size:14px;
	text-align:left;
	line-height:26px;
}
.foot{
	width:100%;
	background:#fff;
	border-top:1px solid #DCDCDC;
	position:fixed;
	bottom:0px;
}
.foot .pr{
	float:left;
	width:50%;
	line-height:50px;
	font-size:15px;
	text-align:center;
	color:#2DC158;
}

.foot .pr img{
	height:14px;
	margin-top:10px;
}
.foot .gm{
	background:#2DC158;
	float:left;
	width:50%;
	line-height:50px;
	font-size:15px;
	text-align:center;
	color:#fff;
}
	
		.tc_back{ width:100%; height: 100%; position: fixed; top:0; left: 0;
			  z-index: 1000;   overflow:hidden; display:-webkit-flex;
			-webkit-justify-content:center; -webkit-align-items:center; background:rgba(0,0,0,0.6);
		}
		
		.tc_page_info{width: 88%; padding: 10px;background-color: #fff;} 
		.tc_page_line{height: 50px;line-height:50px; border-bottom: 1px solid #ddd; }
		.tc_page_a{height: 50px;line-height:50px; width: 30%; float: left;font-size: 16px; color: #323232;}
		.tc_page_b{height: 50px;line-height:50px; width: 70%; float: left;}
		.input_b {height: 46px;outline: none;-moz-appearance:none;  -webkit-appearance:none; border: none; font-size: 16px; width: 95%; } 
		.tc_page_c{height: 50px;line-height:50px; width: 40%; float: left;} 
		.send_code{border: none;height: 30px;font-size: 12px;background-color: #2dc158;color: #fff; margin-top: 10px;} 
		.sure_button{display:block;margin:auto auto;width: 120px; height: 40px;background:#2dc158;border-radius:6px;font-size:16px;color:#fff;text-align:center;line-height:40px; margin-top: 5px;}
	</style> 
  </head>
  
  <body>
    
    <div class="top_01">
			<img src="<%=basePath%>img/ys/img1.png">
		</div>
		<div class="tl">
			<div class="title">
				<span></span>
				<h1>课程简介</h1>
				<h2>INTRODUCTION</h2>
				<div class="clearfix"></div>
			</div>
			<div class="top_02">
				<img src="<%=basePath%>img/ys/img2.png">
			</div>
			<div class="text">
				<h1>第一讲  六种简单的分配模式（上）</h1>
				<h2>周朝凭什么统治中国800年？商鞅变法背后的逻辑是什么？</h2>
			</div>
			<div class="text">
				<h1>第二讲  六种简单的分配模式（下）</h1>
				<h2>如何抓住人性设计企业薪酬，解决产品与销售的矛盾？</h2>
			</div>
			<div class="text">
				<h1>第三讲 如何避免薪酬的11大陷进？</h1>
				<h2>薪酬设计的11坑，一个都不能跳！</h2>
			</div>
			<div class="text">
				<h1>第四讲 如何设计营销团队的三岗七级？</h1>
				<h2>员工不想当经理，经理不想带团队，设计一套机制，药到病除！</h2>
			</div>
			<div class="text">
				<h1>第五讲 如何设计首次高额提成制？</h1>
				<h2>公司缺客户，员工没收入？把提成机制改一下引爆公司业绩！</h2>
			</div>
			<div class="text">
				<h1>第六讲 薪酬设计——职能体系薪酬！</h1>
				<h2>打破死工资，低收入的薪酬乱象，让职能团队给企业创造利润！</h2>
			</div>
			<div class="text">
				<h1>第七讲 薪酬设计——团队薪酬设计！</h1>
				<h2>工资没少发，有结果的却寥寥无几，多种薪酬组合方式让你玩转团队！</h2>
			</div>
			<div class="text">
				<h1>第八讲 薪酬设计——高管薪酬设计！</h1>
				<h2>科学设计高管薪酬，打造“兽医越多，操心越多”的薪酬文化！</h2>
			</div>
			<div class="text">
				<h1>第九讲 如何设计分子公司的裂变模式？</h1>
				<h2>打造事业平台，设计人才和业绩双裂变的子公司裂变模式！</h2>
			</div>
			<div class="text2">
				<h1>适合人群</h1>
				<h2>企业负责人、董事长、总经理</h2>
			</div>
		</div>	
		<div class="tl" style="background:#fceed3;">
			<div class="title">
				<span></span>
				<h1>讲师介绍</h1>
				<h2>LECTURER</h2>
				<div class="clearfix"></div>
			</div>
			<div class="top_02" style="border:none;">
				<img src="<%=basePath%>img/ys/img3.png">
			</div>
		</div>
		<div class="tl">
			<div class="title">
				<span></span>
				<h1>注意事项</h1>
				<h2>PRECAUTIONS</h2>
				<div class="clearfix"></div>
			</div>
			<div class="text1" style="border:none;" >
				<h1>1)权限范围</h1>
				<h2>本课程一经购买将不支持退款或转让，请慎重选择，每个账号只可报名一次。</h2>
			</div>
			<div class="text1">
				<h1>2)版权声明</h1>
				<h2>本课程包括：视频、语音、文字、图片等，版权全部归到到答课堂所有！</h2>
			</div>
		</div>
		<div class="top_01">
			<img src="<%=basePath%>img/ys/img4.png">
		</div>
		<div class="top_01" style="margin-bottom:50px;">
			<img src="<%=basePath%>img/ys/img5.jpg">
		</div>
		<div class="foot">
			<div class="pr">
				限时优惠：￥99
				<img src="<%=basePath%>img/ys/123.jpg">
			</div>
			<div class="gm" onclick="buy_qrcode();">立即购买</div>
			<div class="clearfix"></div>
		</div>
      
     <!-- 弹出页面 --> 
    <div class="tc_back" id="tc_back" style="display: none;">
    	<div class="tc_page_info" id="user_page" style="display: none;">
    		<div class="tc_page_line" style="height: 40px;line-height:40px;border-bottom: none;font-size: 18px; color: #323232; ">请完善用户信息</div>
    		<div class="tc_page_line">
    			<div class="tc_page_a">姓名</div>
    			<div class="tc_page_b"><input type="text" class="input_b" id="my_name" placeholder="请填写您的姓名"></div>
    		</div>
    		<div class="tc_page_line">
    			<div class="tc_page_a">手机</div>
    			<div class="tc_page_b"><input type="text" class="input_b" id="my_phone" placeholder="请填写您的手机"></div>
    		</div>
    		<div class="tc_page_line">
    			<div class="tc_page_a">验证码</div>
    			<div class="tc_page_c"><input type="text" class="input_b" id="yzm_code" placeholder="请填写验证码"></div>
    			<div class="tc_page_a"><button id="clab" type="button" class="send_code" onclick="send();" >获取验证码</button></div>
    		</div>
    		<div class="tc_page_line">
    			<div class="tc_page_a">公司</div>
    			<div class="tc_page_b"><input type="text" class="input_b" id="my_company" placeholder="请填写您的公司"></div>
    		</div>
    		<div class="tc_page_line">
    			<div class="tc_page_a">职称</div>
    			<div class="tc_page_b"><input type="text" class="input_b" id="my_job" placeholder="请填写您的职称"></div>
    		</div>
    		<div class="tc_page_line" style="text-align: center;border-bottom: none;">
    			<span  class="sure_button" onclick="commit_userinfo();">确定</span>
    		</div>
    	</div>
    	
    	<div class="tc_page_info" id="ts_page" style="display: none;" >
    		<div  style="font-size: 18px; color: #323232; text-align: center; margin-top: 10px; margin-bottom: 10px;" id="ts_msg">提示信息</div>
    		<div class="tc_page_line" style="text-align: center;border-bottom: none;">
    			<span class="sure_button" onclick="into_has_pay();">确定</span>
    		</div>
    	</div>
    </div>
  </body>
  <script type="text/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>  
  <script type="text/javascript" src="<%=basePath%>js/path.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/jquery.tips.js"></script>
 <script type="text/javascript" src="<%=basePath%>js/wx/wx_sq2.js"></script>
<script type="text/javascript" src="<%=basePath%>js/wx/qrcode_pay.js"></script> 
 <script type="text/javascript" src="<%=basePath%>js/wx/share_a.js?v=1.0"></script> 
 <script type="text/javascript" src="<%=basePath%>js/wx/tan_c.js"></script>
<script type="text/javascript">

//var openid = "ok4o7waQyK5dri1qmHs9fIxhJY58";

$(function() {
	wx_page_init();
});

var my_order_id ="";

var has_phone = false;

function wx_page_init(){
		
	if(openid != null && openid!="null" && openid!=""){
		//下单，获取订单号，填写用户信息，已支付购买 
		
		$.ajax({
  			type:"post",
	  		url: pt_path +"/wxpay/check_qrcode.do",
	  		dataType:"json",
	  		data:{"openid":openid},
	  		success: function(data){ 
	  			
	  			if(data[0].user == null){
	  				setCookie_openid("openid","");
	  				openid = null;
	  				
	  				window.location.href=pt_path+"wxpay/into_qrcode.do";	
	  				
	  				return false;
	  			} 
	  			 
	  			if(data[0].buy_num > 0){
	  				show_ts("你已经购买《管理者必学的价值千万的薪酬设计课程》！");
	  			}else{
	  				
	  				my_order_id = data[0].order.order_id;
	  				 
	  				if(data[0].user.phone == null){
	  					
	  					//show_info();
	  					has_phone = true;
	  				}
	  				
	  			}
	  			
	  		}
		});
		 
	}
		 
}

function show_info(){ 
	$("#ts_page").hide();
	$("#user_page").show();
	$("#tc_back").show(); 
}

function show_ts(msg){
	
	$("#ts_msg").text(msg);
	
	$("#user_page").hide();
	$("#ts_page").show(); 
	$("#tc_back").show(); 
}

function close_tc() {
	$("#ts_page").hide();
	$("#user_page").hide();
	$("#tc_back").hide(); 
}

function into_has_pay() {
	window.location.href=pt_path+"jsp/wx/ys_success.html";	
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
	
	 var phone=$("#my_phone").val();
	 
	 $("#clab").attr("disabled",true);
	  
	  if(phone==null || phone =="" || isPhone(phone) == false){
		  $("#my_phone").tips({
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

function commit_userinfo(){
	 
	var name=$("#my_name").val();
	var phone=$("#my_phone").val();
	var company=$("#my_company").val();
	var job=$("#my_job").val();
	var yzm_cc=$("#yzm_code").val();
	
	if(name==null||name==""){
		$("#my_name").tips({
			side : 3,
			msg : '请输入姓名',
			bg : '#2dc158',
			time :3
		}); 
		 
		return false;
	}
	

	if(phone==null||phone=="" || isPhone(phone) == false){
		$("#my_phone").tips({
			side : 3,
			msg : '请输入手机',
			bg : '#2dc158',
			time :2
		});
		 
		return false;
	}
	
	if(yzm_cc==null||yzm_cc==""){
		$("#yzm_code").tips({
			side : 3,
			msg : '请输入验证码',
			bg : '#2dc158',
			time :3
		});
		 
		return false;
	}
	
	$.ajax({
			type:"post",
			dataType:"json",
			url:pt_path+"user/update_my_info.do",
			data : {"phone":phone,"company":company,"job":job,"open_id":openid,"name":name,"yzm_cc":yzm_cc},
			success : function(data){
					if("success"==data[0].result){
						//close_tc();
						if(my_order_id != ""){
							into_pay(my_order_id);
						} 
						
					}else if("have_phone" == data[0].result){
						
						$("#my_phone").tips({
							side : 3,
							msg : '手机号已存在',
							bg : '#2dc158',
							time :2
						});
					}else{
						
						$("#my_phone").tips({
							side : 3,
							msg : '手机号或验证码不匹配',
							bg : '#2dc158',
							time :2
						});
					}
			} 
			
		}); 
	
}

function buy_qrcode(){
	
	if(has_phone == false){
		if(my_order_id != ""){
			into_pay(my_order_id);
		}
	}else{
		show_info();
	}
	
	
	
}


</script>
</html>
