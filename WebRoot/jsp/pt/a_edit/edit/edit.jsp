<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";



%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>布局</title>
		
		<script src="<%=basePath %>jsp/pt/a_edit/edit/js/jquery-1.11.3.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
		
		
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/post.css" />

	<link rel="stylesheet" href="<%=basePath %>boot/3.0.1/css/bootstrap.min.css"
	type="text/css"></link>
		<script type="text/javascript" src="<%=basePath %>boot/3.0.1/js/bootstrap.min.js"></script>
	
		<link rel="stylesheet" href="<%=basePath %>jsp/pt/a_edit/edit/css/common.css" />
		<link href="<%=basePath %>jsp/pt/a_edit/edit/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>jsp/pt/a_edit/edit/css/shop.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath %>jsp/pt/a_edit/edit/css/swiper.min.css" />

		<link rel="stylesheet" href="<%=basePath %>jsp/pt/a_edit/edit/css/star-list.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>jsp/pt/a_edit/edit/css/app-content.css" />
<%--		<link rel="stylesheet" type="text/css" href="<%=basePath %>jsp/pt/a_edit/edit/css/ad.css" />--%>
		
		
		<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/swiper-3.2.5.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/banner-swiper.js"></script>
		<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/echo.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/nav.js"></script>
<%--		<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/ad.js"></script>--%>

		<script src="<%=basePath %>jsp/pt/a_edit/edit/js/edit/dargFlex.js" type="text/javascript" charset="utf-8"></script>
	
			<%--			图片--%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>jsp/pt/a_edit/edit/js/imgutil/imgutil.css" />
	<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/imgutil/imgUtil.js"></script>
<%--图片--%>
		
		
		<!-- 链接 -->
		<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/urlUtil.js"></script>
			<!-- 链接 -->
			
		<!-- 商品 -->
				
	<link rel="stylesheet" type="text/css" href="<%=basePath %>jsp/pt/a_edit/edit/js/goodutil/gutil.css" />
	<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/goodutil/goodsUtil.js"></script>
		
			<!--  商品-->
			
				<script type="text/javascript" src="<%=basePath %>jsp/pt/a_edit/edit/js/edit/edit.js"></script>
		
		


			
<%--			layui--%>
	<link rel="stylesheet" href="<%=basePath %>js/layui/css/layui.css" />
<script type="text/javascript" src="<%=basePath %>js/layui/lay/dest/layui.all.js"></script>
			
<%--			检查时间--%>
<script type="text/javascript" src="<%=basePath %>js/common/checktime.js"></script>
			

		<style type="text/css">
			* {
				margin: 0;
				padding: 0;
			}
			
			body {
				position: relative;
			}
			
			#phone {
				border: 1px solid #999;
				width: 360px;
				background: #F9F9F9;
				margin-left: 50px;
				height: auto;
				display: block;
				position: absolute;
				padding-bottom: 50px;
			}
			
			#title {
				width: 360px;
				position: relative;
				text-align: center;
			}
			
			#title h3 {
				position: absolute;
				bottom: 0;
				text-align: center;
				color: #FFFFFF;
				width: 100%;
				z-index: 1;
				padding-bottom: 10px;
			}
			
			#target {
				border: 1px solid #888;
				border: none;
				position: relative;
				width: 360px;
				margin-top: 10px;
				min-height: 400px;
				padding-bottom: 100px;
			}
			
			#editarea {
				margin-top: 50px;
				width: 360px;
				position: relative;
				border: 1px solid #599BDC;
			}
			
			.editbtn {
				position: relative;
				width: 60px;
				height: 60px;
				line-height: 60px;
				display: block;
				float: left;
				background: #FFFFFF;
				border: 1px solid #CCCCCC;
				font-size: 12px;
				margin-top: 8px;
				margin-bottom: 10px;
				margin-left: 7px;
				color: #3C8DDE;
			}
			
			.editbtn:hover {
				background: #808080;
				color: #DC5F59;
			}
			b.dragdo{
				background: url("<%=basePath %>jsp/pt/a_edit/edit/img/common/tz1.png") no-repeat;
				background-repeat: no-repeat;
				background-size:30px 30px;
				width: 30px;
				height: 30px;
				position: absolute;
				right: -30px;
				
				display: none;
				z-index: 99;
				top:10%
			}
			.drag b.dragdo{
				display: block;
				
			}
			.neededit{
				position: relative;
			}
			.editbtns{
				display:none;
				position: absolute;
				bottom: 0;
				right: 0;
				width:auto;
				text-align: right;
				z-index: 999999999;
				
			}
			.editbtns span{
				border: 1px solid #333333;
				font-size: 12px;
				margin-left: 5px;
				padding: 2px;
				width: 100px;
				color:#FFFFFF;
				
				padding-left: 10px;
				padding-right: 10px;
				background: #000;
				opacity:0.9
			}
			.neededit:hover{
				border:2px dashed red;
			}
			.neededit.checkin{
				border:2px dashed red;
			}
			.neededit:hover .editbtns{
				display: block;
			}
			
		
			
			.editbtns span:hover{
				color:red;
				border-color: red;
			}
			#dobtn{
				position:fixed;
				bottom:10px;
				right:15%;
			}
				#dobtn button{
					width:100px;
					
					color: #FFFFFF;
					border-radius: 3px;
					margin-left: 10px;
					padding: 5px;
				}
				#dobtn .btnsus{
					background: limegreen;
				}
				#dobtn .btnyl{
					background: dodgerblue;
				}
				#dobtn button:hover{
					opacity: 0.8;
				}
				.arrow-left {
    width:0; 
    height:0; 
    border-top:5px solid transparent;
    border-bottom:5px solid transparent; 
    border-right:5px solid #999; 
    position: absolute;
    left:-5px;
    top:5px;
    opacity:0.9
}
#editDiv{
display:none;
left:30%;
position: absolute;
}
#divmain{
	position:relative;
	width:100%;
	
	text-align: center;
	
}

#colordiv{
	width:50px;
	height:50px;
	display: block;
	float:left;
	border:2px solid #360000;
}
.icondiv{
	width:30px;
	height:28px;
	display: inline-block;
	float:left;
	border:1px solid #360000;
}
.cdnameedit{
width:150px;
height:30px;
line-height:30px;
display: inline-block;
	
}
#divmain input{
background: #FFFFFF;
border:1px solid #f3f3f3;
}
#divmain .row{
line-height: 30px;
padding: 10px;


font-size: 13px;
position: relative;
margin: 10px;
border-radius:8px;
}
#divmain .row .col-xs-3{
	color:#000000;
	font-weight: 600;
	
}
#divmain .row img{

border:1px solid green;
}
.chooseicon{
	width:30px;
	height:28px;
	margin-left: 10px;
	margin-top: 10px;
	float:left;
	padding:3px;
	border:1px solid #360000;
}
.chooseicon.checkon{
	border:2px solid red;
}

.pagebtn{
		float:left;
		width:55px;
		max-width:100px;
		line-height: 20px;
		text-align: center;
		margin-left: 5px;
		font-size: 11px;
		background: #FFFFFF;
		color:#555
		
}
.neededit.nav{
			position:absolute;
		}
		
		.imgBTN{
			position: absolute;
			right:-10%;
			top:20%;
			display:none;
		}
		.imgBTN button{
			position:relative;
			width:50px;
			height:25px;
			line-height: 25px;
			font-size: 11px;
			border:1px solid #f0f0f0;
			background-color: #FFFFFF;
			border-radius:5px;
			color:#333;
			float:left;
			display: block
		}
		
		button.sureBTN:hover{
			background-color: green;
			color:#FFFFFF;
		}
		button.cancelBTN:hover{
			background-color: red;
			color:#FFFFFF;
		}
		.addImgDIV{
			width:50px;
			height:50px;
			background-color: #FFFFFF;
			border-radius:5px;
			font-size: 50px;
			line-height: 50px;
			display: block;
			position: relative;
			bottom: 10PX;
			left:30%;
			margin-bottom: 10px;
		}
		.addImgDIV:hover{
		
			opacity:0.9;
			color:red;
			border: 2px solid red;
		}
		#title.checkin{
			border:2px dashed red;
		}
		
		.soneg{
		position:relative;
		width:60px;
		height:60px;
		display: inline-block;
		float:left;
	
		border: 1px solid green;
		}
		.soneg:hover{
			opacity:0.9;
			border-color: red;
			color:red;
		}
		.onegood .imgg{
			position:absolute;
			width:100%;
			max-height:100%;
			margin: 0;
			padding:0;
			top:0;
			left:0;
		}
		.onegood .delg{
		position:absolute;
		width:12px;
		top:0;
		right:0;
		display:none;
		}
		.onegood:hover .delg{
		display:block;
		}
		.addonegood{
		background:#FFFFFF;
		margin-left: 5px;
		color:green;
		font-size: 30px;
		font-weight: 600;
		line-height: 60px;
		}
	.imgicon{
	width:30px
	
	}
		</style>
	
		<script type="text/javascript">
		var pid='${pid}';
		var datastr=${data};
			$(function() {
				if(datastr!=null){
					ED.data=datastr;
				}
				if(pid!=null&&pid!=""){
					ED.pid=pid;
				}
			
				
				ED.init();

				$(target).dargFlex('dragdo');
				
				$("#target").on("click",".delbtn",function(e){
				
					if(window.confirm("你确定删除这个元素吗?")){
						ED.deleteIndex($(this).closest(".neededit").index());
						
					
					}
					
				
				});
				
				$(".chooseicon").click(function(){
					var icon=$(this).attr("icon");
					var yicon=$(ED.nowObj).attr("icon");
					if(yicon!=null&&parseInt(yicon)>0){
						$(ED.nowObj).removeClass("icon"+yicon);
					}
					$(ED.nowObj).attr("icon",icon);
					$(ED.nowObj).toggleClass("icon"+icon);
					ED.changeCDInfo();
					$("#chooseIcon").modal("hide");
				});
				
				$("#editarea").on("click",".editbtn",function(){
					var type=parseInt($(this).attr("data"));
					ED.createModel(type,ED.data.length);
				
				});
			});
		</script>

	</head>

	<body  style="position:absolute; padding-bottom: 100px;padding-left: 5%;" id="bodydiv">
		<div id="phone">
			<div id="title">
				<img src="<%=basePath %>jsp/pt/a_edit/edit/images/title.jpg" style="width: 100%;">
				<h3></h3>
			</div>
			<div id="target" class="editdiv" style="margin-top: 0"> 

				

			</div>
			

			<div id="editarea">
				
			</div>
		</div>
		
		<div id="editDiv" >
		<div class="arrow-left"></div>
		<div id="divmain"></div>
		
		</div>
		<input style="position:absolute;left:3000px;" type="color" id="color" onchange="ED.chooseColor(this);"/>
	<div id="dobtn"><button onclick="ED.createPage()" class="btnsus">保存</button><button  class="btnyl" onclick="ED.ylPage()">预览</button></div>
	
	
	
	
	
	
		
		
	
		

		
	</body>

</html>
