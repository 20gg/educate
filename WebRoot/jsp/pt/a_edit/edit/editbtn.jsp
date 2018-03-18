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
		<script src="<%=basePath %>a_edit/edit/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
	<link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css"
	type="text/css"></link>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
	
		<link rel="stylesheet" href="<%=basePath %>a_edit/edit/css/common.css" />
		<link href="<%=basePath %>a_edit/edit/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>a_edit/edit/css/shop.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath %>a_edit/edit/css/swiper.min.css" />

		<link rel="stylesheet" href="<%=basePath %>a_edit/edit/css/star-list.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>a_edit/edit/css/app-content.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>a_edit/edit/css/ad.css" />
		
		
		<script type="text/javascript" src="<%=basePath %>a_edit/edit/js/swiper-3.2.5.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>a_edit/edit/js/banner-swiper.js"></script>
		<script type="text/javascript" src="<%=basePath %>a_edit/edit/js/echo.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>a_edit/edit/js/nav.js"></script>
		<script type="text/javascript" src="<%=basePath %>a_edit/edit/js/ad.js"></script>

		<script src="<%=basePath %>a_edit/edit/js/edit/dargFlex.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath %>a_edit/edit/js/edit/edit2.js"></script>
		
		<!-- 链接 -->
		<script type="text/javascript" src="<%=basePath %>js/common/urlUtil.js"></script>
			<!-- 链接 -->
			
				<!-- 商品 -->
				
		<script type="text/javascript" src="<%=basePath %>js/common/goods.js"></script>
		
			<!--  商品-->
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
				text-align: center;
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
				background: url(<%=basePath %>a_edit/edit/img/common/tz1.png) no-repeat;
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
	
		</style>
	
		<script type="text/javascript">
		var timestamp = Date.parse(new Date());
			timestamp = timestamp / 1000;
			$(function() {
				
				$.getJSON(
			getRootPath()+"/a_edit/edit/json/btn.json?v="+timestamp,
			function(data) {
				ED.data=data;
				ED.init();
				$(target).dargFlex('dragdo');
			});
			
				
				

				
				
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

	<body  style="position:absolute; padding-bottom: 100px;padding-left: 15%;" id="bodydiv">
		<div id="phone">
			<div id="title">
				<img src="<%=basePath %>a_edit/edit/images/title.jpg" style="width: 100%;">
				<h3></h3>
			</div>
			<div id="target" class="editdiv" style="min-height:200px">

				

			</div>
			

			<div id="editarea" style="height:100px;border:none">
				<div onclick="ED.updateBTNS()" class="btn btn-info" style="width:100px;margin-top: 30px;">修改</div>
			</div>
		</div>
		
		<div id="editDiv" >
		<div class="arrow-left"></div>
		<div id="divmain"></div>
		
		</div>
		<input style="position:absolute;left:3000px;" type="color" id="color" onchange="ED.chooseColor(this);"/>

	
	<!-- 上传 -->
	
	
	<!-- 图标 -->
	<div class="modal fade" id="chooseIcon" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content"
				style="width:400px;margin:10px auto;min-height:200px">
				<div class="modal-header"> 
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modaltitle">选择图标</h4>
				</div> 
				<div class="modal-body" >
				
					 <div class="form-group" > 
					 	<div class="form-group" align="center"> 
					 		 
							<span class="chooseicon icon1 checkon"  icon="1">
							</span>
							<span class="chooseicon icon2" icon="2">
							</span>
							<span class="chooseicon icon3" icon="3">
							</span>
							<span class="chooseicon icon4" icon="4">
							</span>
							<span class="chooseicon icon5" icon="5">
							</span>
							<span class="chooseicon icon6" icon="6">
							</span>
							<span class="chooseicon icon7" icon="7">
							</span>
							<span class="chooseicon icon8" icon="8">
							</span>
							<span class="chooseicon icon9" icon="9">
							</span>
							<span class="chooseicon icon10" icon="10">
							</span>
									    
						</div> 
					</div>  
					 
					
				</div>
			</div>
		</div> 
		</div>
		
		
			
	<!-- 选择链接 -->
	
<div id="chooseUrls" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content" style="border-radius:0">
				

				<div class="modal-body no-padding"
					style="max-height:500px;overflow:auto;">
					<table
						class="table table-striped table-hover no-margin-bottom no-border-top">
						<tr>
							<td>
								<div class="input-group" style="width:70%;margin-left:15%">
								<span class="input-group-btn" style="width:100px;font-size: 12px;">选择类型</span>
									<div class="input-group-btn">
											
										<select class="form-control" id="searchUrlType"
											style="width: 120px; font-family: 黑体; font-size: 12px;" onchange="ED.changeTypeURL(this)">
											<option value="" selected=true >链接类型</option>
											<option value="999">自定义外链接</option>
											<option value="1">首页</option>
											<option value="2">商品列表</option>
											<option value="3">商品分类</option>
											<option value="4">商品详情</option>
											<option value="5">购物车</option>
											<option value="6">会员主页</option>
											<option value="7">抢购页面</option>
											<option value="8">秒杀页面</option>
											<option value="9">团购页面</option>
											<option value="10">营销页面</option>
											
										</select>
									</div>
									
								</div></td>
						</tr>

					</table>

					<table
						class="table table-striped table-bordered table-hover no-margin-bottom no-border-top"
						id="urllist">


					</table>
				</div>

				<div class="modal-footer no-margin-top">

					<button class="btn btn-sm btn-danger pull-left"
						data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i> 取消
					</button>

					
					<label id="error" style="color:red;font-size:12px"></label>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- PAGE CONTENT ENDS -->
		

				
	<!-- 弹窗：剪切区域 -->		
	<div class="modal fade" id="tempModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="margin:0;width: 300px;height: 300px;;overflow: hidden;margin-left: 300px">
    	<div class="modal-content" id="locationbar" style="width:100%;height: 100%; margin: 0;padding:0">
         	<div class="modal-body" style="margin:0;padding:0">
         		 <div class="app_img">
                        <input type="file"   style="display:none" id="app_upload" name="image" accept="image/*" onchange="showModal(this);">
             			<div id="clipArea"   style="width:100%;top: 0;"></div>
                  </div>
		               <div style="position:fixed;width:100%;height:10%;bottom: 0px;overflow:hidden; ">
		             	  <div style="width:40%;float:left;text-align:left;height:100%;">  <img style="vertical-align:middle;height:100%"> <a id="cancelBtn" style="margin-left:30%; ">取消</a>  </div>
		             	  <div style="width:20%;float:left; height:100%;text-align:center;"><img style="vertical-align:middle;height:100%"><img style=" height:50%" class="leftbtn" src="<%=basePath%>a_edit/edit/img/rotating.png"></div>
		             	  <div style="width:40%;float:left;text-align:right;height:100%;">   <img style="vertical-align:middle;height:100%"><a id="clipBtn" style="margin-right:30%; ">选取</a> </div>
		      			</div>
      		</div>
		</div>
	</div>
</div>	
	<!-- 弹窗：剪切区域完毕 -->	
			<div id="xxview"  style="display:none; "  >
							</div>
							
							
							<script src="<%=basePath%>js/common/photoClip/iscroll-zoom.js"></script>
<script src="<%=basePath%>js/common/photoClip/hammer.js"></script>
<script src="<%=basePath%>js/common/photoClip/lrz.all.bundle.js"></script>
<script src="<%=basePath%>js/common/photoClip/PhotoClip.js"></script>
	</body>
<script type="text/javascript">


	//剪切代码
				
				$("#clipArea").css("height", 200);
				var clipArea = new PhotoClip("#clipArea", {
					size: [100,100],
					outputSize: [100, 100],
					file: "#app_upload",
					view: "#xxview",
					ok: "#clipBtn",
					loadStart: function() {
						console.log("图片读取中");
				    $("#clipArea").removeClass("hidden");
				  	$("#clipBtn").removeClass("hidden");
					},
					loadComplete: function() {
						console.log("图片读取完毕！");
						$(".leftbtn").click(function(){
				          clipArea.rotateCW(90);
				         });
				            $(".rightbtn").click(function(){
				          clipArea.rotateCCW(90);
				         });
					},
					clipFinish: function(dataURL) {
						console.log("图片截取完毕！");
				  	$("#tempModal").modal("hide");
				   	var str=$("#xxview")[0].style.backgroundImage;
				   	console.log(str);
					var  tem=str.split("(")[1].split(")")[0];
					var url="<%=basePath%>wx/imgUpload.do";
					$.ajax({
						type:"post",
						url:url,
						data:{"v":tem},
						dataType:"json",
						success:function(data){
						$(ED.nowObj).attr("src",getRootPath()+data);
						$(ED.nowObj).attr("icon",data);
						var i=$(ED.nowObj).closest('.row').index();
						
						ED.data.models[0].btns[i].icon=data;
// 						alert(data);
					ED.showPage();
						
						}
					})
					
					
					
					
					
					
					
					}
				});
				function showModal(obj){
				 	if($(obj).val()!=null&&$(obj).val().length>0){
				 		$("#tempModal").modal("show");
				 	}else{
				 		$("#tempModal").modal("hide");
				 	}
				 }
				$(function(){
					$("#cancelBtn").click(function(){
						$("#tempModal").modal("hide");
						$("#app_upload").val("");
					})
					
				})
	//剪切代码完毕
				
</script>
</html>
