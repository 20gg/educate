<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML >
<html>
<head>
<base href="<%=basePath%>">

<title>微页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.min.css"
	type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>css/login/button.css"
	type="text/css"></link>

<script type="text/javascript"
	src="<%=basePath%>js/jquery-2.1.1.min.js"></script>

<script type="text/javascript" src="<%=basePath%>boot/3.0.1/js/bootstrap.min.js"></script>



<style type="text/css">
.btns {
	background: #fff;
	text-align: center;
	width: 40px;
	height: 30px;
	padding: 0;
	line-height: 30px;
	margin: 0;
}

table td {
	font-size: 12px;
}

table th {
	font-size: 13px;
	font-weight: bold;
}

#fromgo td {
	height: 40px;
}

#fromgo input {
	height: 26px;
	font-size: 12px;
	border: 1px solid #ddd;
}

#fromgo select {
	height: 26px;
	font-size: 12px;
	border: 1px solid #ddd;
}

#fromgo .leftname {
	text-align: center;
	font-weight: 600;
	font-size: 12px;
	color: #333;
}

.choose {
	width: 120px;
	height: 30px;
}

.danger {
	color: red;
}

small {
	font-size: 11px;
}

.btn {
	min-width: 50px;
	height: 35px;
	margin-top: 3px;
	font-size: 12px;
	line-height: 12px;
}

.green {
	color: #07CC3F
}


</style>
<script type="text/javascript" src="<%=basePath%>js/url.js"></script>


<script type="text/javascript">
var delinfo={};
$(function(){

	$("#prePage").click(function(){

	var pn=parseInt($("#pninfo").val());
	pn--;
	if(pn<1){
	alert("已是第一页");
	return;
	}
	$("#pninfo").val(pn);
	$("#pageform").submit();
	});
	$("#nextPage").click(function(){

	var pn=parseInt($("#pninfo").val());
	var pc=parseInt($("#pageCount").val());
	pn++;
	if(pn>pc){
	alert("已是最后一页");
	return;
	}
	$("#pninfo").val(pn);
	$("#pageform").submit();


	});
	$("#goPage").click(function(){

	$("#pageform").submit();


	});




$("#chooseAll").click(function(){

$(".oneorder").prop("checked",this.checked);
});

/* $("#fmoney").keyup(function () {
                var reg = $(this).val().match(/\d+\.?\d{0,2}/);
                var txt = '';
                if (reg != null) {
                    txt = reg[0];
                }
                $(this).val(txt);
            }).change(function () {
                $(this).keyup();
            }); */

/* $("#addadd").click(function(){




}); */


});



 
 
 function changeCH(){
 	$("#otherchoose").toggleClass("hidden");
 	
 
 }
 
 
 function gosearch(){
 $("#formhead").submit();
 
 }
 function chongzhiss(){
 
 
 	window.location.href="<%=basePath%>aedit/querySjPages.do";
 }
 
 
 



function priceKeyup(obj,ma){

$(obj).keyup(function(){

var max=parseInt(ma);
var str=$(this).val();
var reg=/[^0-9.]/g;

if(reg.test(str)){

	$(this).val(1);

}else{

if(parseInt($(this).val())>max){
$(this).val(max);
}else if(parseInt($(this).val())<1){
$(this).val(1);
}
}



});

}





//--------------------- 导出



	
</script>

<script type="text/javascript">

function createPage(){

$("#addtype").val(1);
$("#addone,#addtwo").removeClass("hidden");
	$("#addtwo").toggleClass("hidden");
	$("#choosegoods").modal("show");
}
$(function(){
	$("#addtype").change(function(){
	var  pagetype=parseInt($("#pagetype").val());
	var addtype=parseInt($(this).val());
	if(pagetype<1){
		alert("请选择页面类型");
		$("#addtype").val(1);
		return;
	}else if(pagetype==1){
		$("#addone,#addtwo").removeClass("hidden");
		if(addtype==1){
			$("#addtwo").toggleClass("hidden");
		}else{
			$.ajax({
				type:"post",
				url:"<%=basePath%>ashop/queryPageModels.do",
				dataType:"json",
				success:function(data){
				var html="";
				html+="<div class='onemodel' data='0'>";
						html+="<img src='<%=basePath%>a_edit/edit/img/kb.jpg'>";
						html+="<div class='mname'>自定义模版</div>";
						html+="</div>";
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						var one=data[i];
						
						html+="<div class='onemodel' data='"+one.data+"'>";
						html+="<img src='<%=basePath%>"+one.img+"'>";
						html+="<div class='mname'>"+one.name+"</div>";
						html+="</div>";
					
					
					}
				
				
				}
				
				
				$("#allmodels").html(html);
				
				}
			
			})
		
		
		$("#addone").toggleClass("hidden");
		}
		
	
	
	
	}else{
		$("#addone,#addtwo").removeClass("hidden");
		if(addtype==1){
			$("#addtwo").toggleClass("hidden");
		}else{
			alert("仅首页有编辑模式");
					$("#addtype").val(1);
					$("#addtwo").toggleClass("hidden");
		return;
		}
	}
	
	
	});
	
	
	$("#allmodels").on("click",".onemodel",function(){
	
		var data=$(this).attr("data");
		jspost("<%=basePath%>a_edit/edit/edit.jsp",{model:data});
	
	});
	
	$("#addU").click(function(){
		var name=$("#pname").val();
		var desc=$("#pdesc").val();
		var url=$("#purl").val();
		var  pagetype=parseInt($("#pagetype").val());
	jspost("<%=basePath%>ashop/addOnePage.do",{typeid:pagetype,realpath:url,name:name+"("+desc+")"});
	
	})

});

function delPages(id){
if(window.confirm("你确定删除这个页面吗?")){
	$.ajax({
		type:"post",
		url:"<%=basePath%>aedit/delthisPage.do",
		data:{id:id},
		dataType:"text",
		success:function(data){
			
			error("删除成功");
			$("#pageform").submit();
		}
	})
	
}


}

function updatePagesUse(id,state,obj){
	$.ajax({
		type:"post",
		url:'<%=basePath%>aedit/updatePagesUse.do',
		data:{id:id,use:state},
		dataType:"json",
		success:function(data){
			if(data=="200"){
			$("#pageform").submit();
			
			
				
			}else{
			alert("修改状态失败");
			}
		}
	
	
	})
	
	


};

function mainpage(id){
	
$.ajax({
		type:"post",
		url:'<%=basePath%>aedit/mainpage.do',
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(data=="200"){
			
			$("#pageform").submit();
			
			
				
			}else{
			alert("修改状态失败");
			}
		}
	
	
	});
}

function editPageInfo(id,kind,name,path,type){


	if(parseInt(kind)==1){
		$("#pid2").val(id);
		$("#pagetype2").val(type);
		
		 if(name!=null&&name.length>1){
		var n1=name.split("(");
		
		$("#pname2").val(n1[0]);
		$("#pdesc2").val(n1[1].split(")")[0]);
		} 
		
		$("#purl2").val(path);
		
		$("#updateMo").modal("show");
	}else{
		//进入编辑页面
		window.location.href='<%=basePath%>aedit/edit2page.do?id='+id;
	
	}
	
	

}

function updatePageDetail(){
	
	
$.ajax({
		type:"post",
		url:'<%=basePath%>aedit/updatePageDetail.do',
		data:{id:$("#pid2").val(),name:$("#pname2").val()+"("+$("#pdesc2").val()+")",url:$("#purl2").val()},
		dataType:"json",
		success:function(data){
			if(data=="200"){
			
			$("#pageform").submit();
			
			
				
			}else{
			alert("修改状态失败");
			}
		}
	
	
	});

}
</script>


<style type="text/css">
.edit{

width:60%;
border:1px solid #f0f0f0;
min-height:35px;
line-height: 35px;

}

.onemodel{

float:left;
margin-left: 20px;
display: inline-block;
width:150px;
height:200px;
margin-top: 10px;
position: relative;
}
.onemodel .mname{
	position: absolute;
	width:100%;
	bottom: 0;
	left:0;
	height:35px;
	background: #F8F8F8;
	color:#333;
	font-size: 12px;
	line-height: 35px;
	
	
}
.onemodel img{
width:100%;

}
.onemodel:hover{

border:1px solid red;
}
</style>

</head>

<body style="background: #fff">

	<!-- /section:basics/sidebar -->
	<div class="main-content"
		style="background: transparent;margin: 0;width:100%;">


		<!-- /section:basics/content.breadcrumbs -->
		<div class="page-content"
			style="background: transparent;width:98%;margin-left: 1%;margin-bottom: 50px">




			<div class="page-content-area"
				style="min-height: 300px;padding:0;margin-top: 20px;">
				<!-- 	<div class="page-header">
							<h1>
								用户
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									管理用户
								</small>
							</h1>
						</div>/.page-header -->

				<div class="row">
					<div class="col-xs-12">



						<div class="row">
							<div class="col-xs-12">
								<div class="row">

									<div class="col-xs-12">


										<form action="<%=basePath%>aedit/querySjPages.do"
											method="post" id="formhead">

											<%-- <a href="<%=basePath%>user/updateJuanzi.do">scs</a> --%>
											<div class="tools">

												<div class="right">

													<ul class="toolbar right" style="margin-bottom: 10px;">




														<li onclick="gosearch();"><span><img
																src="<%=basePath%>css/login/s2.png"> </span>搜索</li>
														<li onclick="chongzhiss();"><span><img
																src="<%=basePath%>css/login/s3.png"> </span>重置</li>


														<%-- <li onclick="changeCH();"><span><img
																src="<%=basePath%>css/login/s5.png"> </span>高级</li> --%>
													</ul>


													<ul class="toolbar3">

														<li class="textli">页面类型： <select name="type"
															class="choose" value="${map.type}">
																<option value="">选择</option>

																<option value="1"
																	<c:if test="${map.type eq 1}">selected</c:if>>首页</option>
																<option value="2"
																	<c:if test="${map.type eq 2}">selected</c:if>>商品列表</option>
																<option value="3"
																	<c:if test="${map.type eq 3}">selected</c:if>>商品分类</option>
																<option value="4"
																	<c:if test="${map.type eq 4}">selected</c:if>>商品详情</option>
																<option value="5"
																	<c:if test="${map.type eq 5}">selected</c:if>>购物车</option>
																<option value="6"
																	<c:if test="${map.type eq 6}">selected</c:if>>会员主页</option>
																<option value="7"
																	<c:if test="${map.type eq 7}">selected</c:if>>抢购页面</option>
																<option value="8"
																	<c:if test="${map.type eq 8}">selected</c:if>>秒杀页面</option>
																<option value="10"
																	<c:if test="${map.type eq 10}">selected</c:if>>营销页面</option>
														</select></li>
														<li class="textli">每页：<input type="text" name="ps"
															class="choose" value="${map.ps}" style="width:40px;">
															行</li>









													</ul>
												</div>

											</div>

											<%-- <ul id="otherchoose" class="hidden toolbar3"
												style="margin-bottom: 10px">
												<c:if test="${!(sessionScope.manager.type eq 2)}">
													<li class="textli">所属仓库： <select name="whid"
														class="choose" value="${map.whid}">
															<option value="">选择</option>
															<c:forEach items="${whlist}" var="wh">
																<option value="${wh.id}"
																	<c:if test="${map.whid eq wh.id}">selected</c:if>>${wh.name}</option>
															</c:forEach>

													</select></li>
												</c:if>
												
												
												<li class="textli">收货人手机：<input type="text"
													name="mobile" class="choose" value="${map.mobile}">
												</li>
												


											</ul> --%>




										</form>








									</div>
								</div>



								<!-- 				<a class="btn btn-info" style="height: 24px;" href="javascript:" onclick="fh_show('${fh.did}','${fh.orderno}');"></a> -->


								<ul class="toolbar" style="width: 96%;margin-left: 2%;">
									<li
										onclick="createPage()"
										style="margin-bottom: 10px;"><span><img
											src="<%=basePath%>css/login/index.png"> </span><span
										style="color:red;margin: 0">创建页面</span>
									</li>




								</ul>


								<div style="margin-top: 10px;width: 96%;margin-left: 2%;">
									<table id="tableone"
										class="table table-striped table-bordered table-hover">
										<thead>
											<tr style="background: #F0F5F7;">

												<th style="width:80px;"><input type="checkbox"
													id="chooseAll">序号</th>

												<td align="center" style="width:120px;">类型</td>
												<td align="center">描述</td>
												<td align="center" style="width:90px;">是否使用</td>
											<td align="center" style="width:90px;">类型</td>

												<td align="center" style="width:120px;">时间</td>
												<td align="center" style="width:250px;">操作</td>


											</tr>
										</thead>

										<tbody>
											<c:choose>
												<c:when test="${pb.list !=null &&pb.list.size()>0 }">
													<c:forEach items="${pb.list}" var="ap" varStatus="os">
														<tr style="font-size:12px">

															<td class="center"><input type="checkbox"
																class="oneorder" value="${ap.id}">
																${os.index+1+(pb.pageNum-1)*pb.pageSize}</td>
															<td>${ap.typename }</td>
															<td>${ap.name }</td>
															
															<td class="usetype"><c:choose>
																	<c:when test="${ap.isUse eq 1}">使用</c:when>
																	<c:otherwise>
																不使用
																</c:otherwise>
																</c:choose></td>
																
																<td class="usetype"><c:choose>
																	<c:when test="${ap.kind eq 1}">链接</c:when>
																	<c:otherwise>
																模版创建
																</c:otherwise>
																</c:choose></td>
															<td>${ap.ctime}</td>
															<td>
															<a class="btn btn-danger" onclick="delPages('${ap._id}')">删除</a>
															
															<a class="btn btn-info"  onclick="editPageInfo('${ap._id}',${ap.kind},'${ap.name }','${ap.realpath}',${ap.typeid })" >编辑</a>
															
																<c:choose>
																	<c:when test="${ap.isUse eq 1}">
																	<a class="btn btn-primary" onclick="updatePagesUse('${ap._id}',-1,this)">取消</a>
																	</c:when>
																	<c:otherwise>
																<a class="btn btn-info" onclick="updatePagesUse('${ap._id}',1,this)">使用</a>
																</c:otherwise>
																</c:choose>
															<c:if test="${ap.isMain eq 0 && ap.typeid  eq 1}">
															<a class="btn btn-success" onclick="mainpage('${ap._id}')">设为主页</a>
															</c:if>
															
															</td>
														</tr>


													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="10" style="color:red;font-size:12px;">&nbsp;&nbsp;未找到数据</td>
													</tr>


												</c:otherwise>
											</c:choose>

										</tbody>
									</table>
									<%-- <form id="downLoad" action="#" method="get" style="width:300px;float:left">
									<input type="checkbox" id="all_none" name="all_none"> <label style="color:#333;font-size: 12px">全选</label>
										<button  style="height:25px;line-height:10px;font-size:12px;" class="btn btn-success btn-sm" type="submit" onclick="return testCK()" formaction="<%=basePath%>download/export_specified_orderInfo.do">下载选中订单</button>
										<button style="height:25px;line-height:10px;font-size:12px" class="btn btn-success btn-sm" type="submit" formaction="<%=basePath%>download/export_all_orderInfo.do">下载全部</button>
										</form> --%>
									<c:if test="${pb.list !=null &&pb.list.size()>0 }">
										<div
											style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 12px;">

											<span>共<b style="color:#666">${pb.rowCount }</b>条</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span style="color:#666">${pb.pageNum }/${pb.pageCount
												}页</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button type="button" class="btns " style="color:#666"
												id="prePage"><</button>
											&nbsp;&nbsp;&nbsp;
											<button type="button" class="btns" style="color:#666"
												id="nextPage">></button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											跳转到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<form action="<%=basePath%>aedit/querySjPages.do"
												method="post" style="float:right" id="pageform">
												<input type="text" name="page"
													style="width:60px;height:30px" id="pninfo"
													onkeyup="priceKeyup(this,${pb.pageCount})"
													value="${pb.pageNum}"> &nbsp;&nbsp;&nbsp; <input
													type="hidden" name="whid" value="${map.whid}"> <input
													type="hidden" name="mobile" value="${map.mobile}">

												<input type="hidden" name="state" value="${map.state}">
												<input type="hidden" name="ps" value="${map.ps}"> <input
													type="hidden" name="mindate" value="${map.mindate }">
												<input type="hidden" name="maxdate" value="${map.maxdate }">
												<input type="hidden" name="isChange"
													value="${map.isChange }"> <input type="button"
													value="GO" class="btns" id="goPage">
											</form>




										</div>
									</c:if>
								</div>
							</div>
						</div>


					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content-area -->
		</div>
		<!-- /.page-content -->
	</div>
	<!-- /.main-content -->




	<div id="choosegoods" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content" style="border-radius:0">


				<div class="modal-body no-padding"
					style="max-height:600px;overflow:auto;">
					<table
						class="table table-striped table-hover no-margin-bottom no-border-top">
						<tr>
							<td>页面类型:</td>
							<td><select id="pagetype" class="choose">
									

									<option value="1">首页</option>
									<option value="2">商品列表</option>
									<option value="3">商品分类</option>
									<option value="4">商品详情</option>
									<option value="5">购物车</option>
									<option value="6">会员主页</option>
									<option value="7">抢购页面</option>
									<option value="8">秒杀页面</option>
									<option value="10">营销页面</option>
							</select></td>
						</tr>
						<tr>
							<td>添加方式:</td>
							<td><select id="addtype" class="choose">
								

									<option value="1" selected>添加链接</option>
									<option value="2">编辑微页面</option>

							</select></td>
						</tr>
						
					</table>

					<table
						class="table table-striped table-bordered table-hover no-margin-bottom no-border-top"
						id="addone">
					<tr><td>页面名称:</td><td><input type="text" placeholder="页面名称" id="pname" class="edit"></td></tr>
					<tr><td>页面描述:</td><td><textarea placeholder="页面描述" id="pdesc" class="edit"></textarea></td></tr>
					<tr><td>页面链接:</td><td><input type="text" class="edit" placeholder="页面链接,如:http://www.chuange.cn" id="purl"></td></tr>
					<tr><td colspan="2">
						
						<button class="btn btn-sm btn-info pull-left" type="button"
						id="addU">
						<i class="ace-icon fa fa-times"></i> 添加
					</button>
					<label id="error" style="color:red;font-size:12px"></label>
						</td></tr>
					</table>
					
					<table
						class="table no-margin-bottom no-border-top hidden"
						id="addtwo">
					<tr><td id="allmodels" style="position:relative">
					
					</td>
					</tr>
					</table>
				</div>

				
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- PAGE CONTENT ENDS -->
	
	
	<div id="updateMo" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content" style="border-radius:0">


				<div class="modal-body no-padding"
					style="max-height:600px;overflow:auto;">
					<input type="hidden" id="pid2">
					<table
						class="table table-striped table-hover no-margin-bottom no-border-top">
						<tr>
							<td>页面类型:</td>
							<td><select id="pagetype2" class="choose">
									

									<option value="1">首页</option>
									<option value="2">商品列表</option>
									<option value="3">商品分类</option>
									<option value="4">商品详情</option>
									<option value="5">购物车</option>
									<option value="6">会员主页</option>
									<option value="7">抢购页面</option>
									<option value="8">秒杀页面</option>
									<option value="10">营销页面</option>
							</select></td>
						</tr>
						<tr>
							<td>类型:</td>
							<td>链接</td>
						</tr>
						
					</table>

					<table
						class="table table-striped table-bordered table-hover no-margin-bottom no-border-top"
						id="addone">
					<tr><td>页面名称:</td><td><input type="text" placeholder="页面名称" id="pname2" class="edit"></td></tr>
					<tr><td>页面描述:</td><td><textarea placeholder="页面描述" id="pdesc2" class="edit"></textarea></td></tr>
					<tr><td>页面链接:</td><td><input type="text" class="edit" placeholder="页面链接,如:http://www.chuange.cn" id="purl2"></td></tr>
					<tr><td colspan="2">
						
						<button  onclick="updatePageDetail();" class="btn btn-sm btn-info pull-left" type="button">
						<i class="ace-icon fa fa-times"></i> 修改
					</button>
					<label id="error" style="color:red;font-size:12px"></label>
						</td></tr>
					</table>
					
					
				</div>

				
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- PAGE CONTENT ENDS -->


</body>

</html>
