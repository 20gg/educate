<%@ page language="java"
	import="java.util.*"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML >
<html>
<head>
<base href="<%=basePath%>">

<title>全部订单</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="<%=basePath%>css/bootstrap.min.css"
	type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>css/login/button.css?v=3">

<script type="text/javascript"
	src="<%=basePath%>js/jquery-1.11.2.min.js"></script>

<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>

<script type="text/javascript"
	src="<%=basePath%>js/city/jquery.cxselect.js"></script>

<script type="text/javascript"
	src="<%=basePath%>assets/layui/lay/dest/layui.all.js"> </script>


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
	font-size: 11px;
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

.btn {
	min-width: 50px;
	height: 35px;
	margin-top: 3px;
	font-size: 12px;
	line-height: 12px;
}

.pagebtn {
	float: left;
	width: 55px;
	max-width: 100px;
	line-height: 20px;
	text-align: center;
	margin-left: 5px;
	font-size: 11px;
}

.green {
	color: #07CC3F
}

#goodsmenu td {
	height: 25px
}

.textli {
	margin-top: 6px;
	margin-bottom: 6px;
	display: inline-block;
	width: 150px;
	height:auto;
	min-height:50px;
	
}

.textli input,.textli select{
	width:150px
}
</style>
<style type="text/css" mce_bogus="1">
table td {
	white-space: nowrap;
}

table tr {
	height: 38px;
}
</style>

<link rel="stylesheet" href="<%=basePath%>assets/layui/css/layui.css"
	type="text/css"></link>
<script type="text/javascript" src="<%=basePath%>js/url.js"></script>

<script>
var sss=null;

function orderinfo(id){


layer.open({
        type: 2 //此处以iframe举例
        ,title: '订单详情'
        ,area: ['900px', '460px']
        ,shade: 0
        ,maxmin: true
        ,offset: [ //为了演示，随机坐标
        /*   Math.random()*($(window).height()-300)
          ,Math.random()*($(window).width()-390) */
          50,$(window).width()/2-390
        ] 
        ,content: '<%=basePath%>trade/queryOrderDetail.do?id='+id
//         ,btn: [/* '全部关闭' */] //只是为了演示
        ,yes: function(){
          $(that).click(); 
        }
        ,btn2: function(){
          layer.closeAll();
        }
        
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
          layer.setTop(layero); //重点2
        }
      });


}




//添加备注
function addOrderNote(){

		var doms=$(".oneorder:checked");
	if(doms!=null&&doms.length==1){
	
	
	
	
	layer.prompt({title: '添加订单备注', formType: 2}, function(pass, index){
			$.ajax({
				type:"post",
				url:"<%=basePath%>trade/addOrdernote.do",
				data:{pass:pass,id:doms.val()},
				dataType:"json",
				success:function(data){
					
				layer.close(index);
				var msg="";
				if(data=="200"){
					msg="添加成功";
				}else{
					msg="添加失败";
				
				}
				
				
				//询问框
layer.confirm(msg, {
  btn: ['确定'] ,//按钮,
  icon: 1,
  offset: [ 50,$(window).width()/2-160
        ] 
}, function(){
$("#formhead").submit();
  
});
				
				
				
				
				}
				
			
			
			})
  
 
	});
	
	}else{
	
	layer.msg('请选择一个订单',{


time:2000,
icon: 2
}); 
	
	}

}


function yanchifh(){
	


		var doms=$(".oneorder:checked");
	if(doms!=null&&doms.length==1){
	
	
	
	
	layer.prompt({title: '延迟发货备注', formType: 2}, function(pass, index){
			$.ajax({
				type:"post",
				url:"<%=basePath%>trade/addOrdernoteYC.do",
				data:{pass:pass,id:doms.val()},
				dataType:"json",
				success:function(data){
					
				layer.close(index);
				var msg="";
				if(data=="200"){
					msg="添加成功";
				}else{
					msg="添加失败";
				
				}
				
				
				//询问框
layer.confirm(msg, {
  btn: ['确定'] ,//按钮,
  icon: 1,
  offset: [ 50,$(window).width()/2-160
        ] 
}, function(){
$("#formhead").submit();
  
});
				
				
				
				
				}
				
			
			
			})
  
 
	});
	
	}else{
	
	layer.msg('请选择一个订单',{


time:2000,
icon: 2
}); 
	
	}

}






function dkorder(){
 	window.location.href="<%=basePath%>mongodb/addItem.do";
}

function gosearch(){
	$("#formhead").submit();
 
}

function chongzhiss(){
 	window.location.href="<%=basePath%>cmanageorder/queryorderlist.do";
 }
 
 
 
function selectshow(obj,value){

	$.ajax({
		type:"POST",
		url:'<%=basePath%>mongo/querySelect.do',
		data:{"type":value},
		dataType:"json",
		success:function(data){
		var html="";
		if(data!=null){
		html+="<option></option>";
		if(value=="sid"){
		for(var i=0;i<data.length;i++){
		
			html+="<option value='"+data[i].sid+"'>"+data[i].name+"</option>";
		
			};
		}else{
		
		for(var i=0;i<data.length;i++){
		
			html+="<option value='"+data[i]+"'>"+data[i]+"</option>";
		
			};
		};
		
		
		}
		
		$(obj).html(html);
		
		}
	
	});
}


function priceKeyup(obj){
	$(obj).keyup(function(){
		var max=parseInt($(this).attr("maxval"));
		var str=$(this).val();
		var reg=/[^0-9.]/g;
		if(reg.test(str)){
			$(this).val(1);
		}else{
			if(parseInt($(this).val())>max){
				$(this).val(max);
			}else if(parseInt($(this).val())<1){
				$(this).val(1);
			};
		};
	});
}


function checkpass(){

	var doms=$(".oneorder:checked");
	if(doms!=null&&doms.length>0){
	
		

	
 var ids=[];
		doms.each(function(){
		
			ids.push($(this).val());
		
		
		});
		layer.load(0, {shade: false}); 
		
		$.ajax({
			type:'post',
			url:'<%=basePath%>trade/confirmChaiInfo.do',
			data:{id:JSON.stringify(ids)},
			dataType:"json",
			success:function(data){
				  layer.closeAll('loading');
				var mmm='';
				for(var i=0,len=data.length;i<len;i++){
					var one=data[i];
					
					if(one.res=='200'){
					mmm+="<div style='color:red'><input type='checkbox' class='oneod oneodsuc' value='"+one.id+"'>订单：<small >"+one.ono+"</small>,已拆分发货单</div>";
					}else{
					mmm+="<div style='color:green'><input type='checkbox' class='oneod oneoderror' value='"+one.id+"'>发货单：<small >"+one.ono+"</small>,未拆分发货单</div>";
					}
				}
				
				mmm+="<br><div style='font-size:15px;font-weight:600'><input type='checkbox' class='allod'>全部&nbsp;&nbsp;<input type='checkbox' class='allodsuc'>已拆分&nbsp;&nbsp; <input type='checkbox' class='alloderror'>未拆分 </div>";
				
				
			
			
			confirmPass(mmm);
			}
	
		});
		
		
		

	
	}else{
	
	layer.msg('请选择一个订单',{


time:2000,
icon: 2
}); 
	
	}

}


//审核


function confirmPass(mmm){
	

	
	
	layer.confirm(mmm, {

	  
	offset: [ 
	          50,$(window).width()/2-260
	        ],
	       area: ['590px','550px'],
	       maxmin: true,
	         btn: ['确认拆分','取消']//按钮
	}, function(){
		
	 		var ids=[];
			$(".oneod:checked").each(function(){
			
				ids.push($(this).val());
			
			
			});
			layer.load(0, {shade: false}); 
			$.ajax({
					type:'post',
					url:'<%=basePath%>trade/manyCheckPass.do',
					data:{id:JSON.stringify(ids)},
					dataType:"json",
					success:function(data){
					
						  layer.closeAll('loading');
					
					//询问框
		layer.confirm(data, {
		btn: ['确定'] ,//按钮,
		icon: 1,
		area: ['600px', '560px'],
		offset: [ 
		      50,$(window).width()/2-360
		    ] 
		}, function(){
			$("#pageform").submit();

		});
				
					
					
					}
			
				});
			
			
	}, function(){
	  
	});
	
	
	
	
	
	
}






//添加赠品

function addGoods(){

var doms=$(".oneorder:checked");
	if(doms!=null&&doms.length>0){
	
		
	
var sss2=layer.confirm('你确定添加赠品吗？',{
  btn: ['确定','取消'],offset: [ 50,$(window).width()/2-160] }, function(){

var ids=[];
		doms.each(function(){
		
			ids.push($(this).val());
		
		
		});
		layer.close(sss2);
	
		ajaxgoods();
		chooseIds=ids;
		 
}, function(){
  
});
	
	}else{
	
	layer.msg('请选择一个订单',{


time:2000,
icon: 2
}); 
	
	}



}




//取消订单
function cancelOrder(){

var doms=$(".oneorder:checked");
if(doms!=null&&doms.length==1){

	

layer.confirm('你确定取消该订单吗？', {
btn: ['确定','取消'],//按钮

offset: [ 
      50,$(window).width()/2-160
    ] 
}, function(){
var ids=doms.val();
	
	$.ajax({
			type:'post',
			url:'<%=basePath%>trade/cancelOrder.do',
			data:{id:ids},
			dataType:"json",
			success:function(data){
			var msg="取消失败";
				if(data=="200"){
	
				msg='取消成功';
				}
			
			
			//询问框
layer.confirm(msg, {
btn: ['确定'] ,//按钮,
icon: 1,
offset: [ 
      50,$(window).width()/2-160
    ] 
}, function(){
$("#pageform").submit();

});
		
			
			
			}
	
		});
}, function(){

});

}else{

layer.msg('请选择一个订单',{


time:2000,
icon: 2
}); 

}

}


//更新订单
function updateOrder(){


var doms=$(".oneorder:checked");
if(doms!=null&&doms.length>0){

	

 sss=layer.confirm('你确定通过更新订单吗？', {
btn: ['确定','取消'],//按钮

offset: [ 
      50,$(window).width()/2-160
    ] 
}, function(){
var ids=[];
	doms.each(function(){
	
		ids.push($(this).val());
	
	
	});
	layer.close(sss);
	layer.load(0, {shade: false}); 
	$.ajax({
			type:'post',
			url:'<%=basePath%>trade/updateOrderLine.do',
			data:{id:JSON.stringify(ids)},
			dataType:"json",
			success:function(data){
			  layer.closeAll('loading');
			var msg="更新异常";
				if(data=="200"){
	
				msg='更新成功';
				}else if(data=="404"){
				msg='更新失败';
				}else if(data=="505"){
				msg='获取数据失败';
				}
			
			
			//询问框
layer.confirm(msg, {
btn: ['确定'] ,//按钮,
icon: 1,
offset: [ 
      50,$(window).width()/2-160
    ] 
}, function(){
$("#pageform").submit();

});
		
			
			
			}
	
		});
}, function(){

});

}else{

layer.msg('请选择订单',{


time:2000,
icon: 2
}); 

};

}



function userCatch(){

var start = {
istime:true,
format: 'YYYY-MM-DD hh:mm:ss',max: getpFormatDate(),istoday: false};
var html="";
html+='开始时间:<input type="text" id="stime" class="laydate-icon layui-input" readonly="readonly" style="width:60%;margin-left:100px;" >';
html+='结束时间：<input type="text" id="etime" class="laydate-icon layui-input"  readonly="readonly"  style="width:60%;margin-left:100px;" >';

sss=layer.open({
type: 1,
skin: 'layui-layer-lan', //加上边框
area: ['420px', '240px'], //宽高
offset: [ 10,$(window).width()/2-160 ] ,
title:'选择时间范围',
content: html,
btn:['确认抓取'],
yes: function(){

if(counttime($("#stime").val(),$("#etime").val())){
		layer.close(sss);
	layer.load(0, {shade: false}); 
	
	$.ajax({
		type:"post",
		url:"<%=basePath%>trade/userUpdateOrder.do",
		data:{min:$("#stime").val(),max:$("#etime").val()},
		dataType:"json",
		success:function(data){
		
		  layer.closeAll('loading');
			layer.msg('抓取订单结束', {
time: 2000, //20s后自动关闭
offset: [150,$(window).width()/2-160]
});
			$("#pageform").submit();
		
		
		}
	
	
	
	});
		



}else{




layer.msg('时间间隔不能超过一天', {
time: 2000, //20s后自动关闭
offset: [ 
     150,$(window).width()/2-160
    ]
});

}
}
});

$("#stime").val(getpFormatDate(600000));
$("#etime").val(getpFormatDate());	
document.getElementById('stime').onclick = function(){
start.elem = this;
laydate(start);

};		
 
document.getElementById('etime').onclick = function(){
start.elem = this;
laydate(start);

};
}
</script>
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

				<div class="row">
					<div class="col-xs-12">

						<div class="row">
							<div class="col-xs-12">
								<div class="row">

									<div class="col-xs-12">
										<form action="<%=basePath%>cmanageorder/queryorderlist.do"
											method="post" id="formhead">

											<div class="tools tooldiv">



												<ul class="toolbar3">
													
													<li class="textli">外部订单号： <input type="text"
														name="eorderno" class="choose" value="${map.eorderno }">
													</li>
									<li class="textli">订单号：<input type="text"
														name="orderno" class="choose" value="${map.orderno}">
													</li>
													<li class="textli">订单状态： <select name="state"
														class="choose">
															<option value=""></option>

															<%-- <option value="0"  <c:if test="${map.state eq 0}">selected</c:if>>待支付</option> --%>
															<option value="1"
																<c:if test="${map.state eq 1}">selected</c:if>>待支付</option>
															<option value="2"
																<c:if test="${map.state eq 2}">selected</c:if>>等待发货</option>
															<option value="3"
																<c:if test="${map.state eq 3}">selected</c:if>>已发货，待签收</option>
															<option value="4"
																<c:if test="${map.state eq 4}">selected</c:if>>订单完成</option>
															<option value="5"
																<c:if test="${map.state eq 5}">selected</c:if>>订单取消</option>
															<option value="6"
																<c:if test="${map.state eq 6}">selected</c:if>>退单中</option>
															<option value="7"
																<c:if test="${map.state eq 7}">selected</c:if>>退单完成</option>

													</select>
													</li>

													<li class="textli">订单来源： <select name="ofrom"
														class="choose">
															<option value=""></option>

															<c:forEach items="${mapofrom}" var="mp">

																<option value='${mp.key}'
																	<c:if test="${map.ofrom eq mp.key}">selected</c:if>>${mp.value}</option>
															</c:forEach>


													</select>
													</li>
													


													<li class="textli">收货人： <input type="text"
														name="consignee" class="choose" value="${map.consignee }">
													</li>
<li class="textli">商品名称： <input type="text"
														name="likegoodsname" class="choose"
														value="${map.likegoodsname}" placeholder="模糊搜索"></li>
													<li class="textli">地址： <input type="text"
														name="likeaddress" class="choose"
														value="${map.likeaddress }" placeholder="省\市\区\地址">
													</li>

													
														<li class="textli">
														支付时间：
														<input type="text"
															value="${map.mindate }"  name="mindate" id="ordermindate"
															readonly="readonly" placeholder="最小时间"
															class="choose laydate-icon layui-input"
															onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														<input type="text"
															value="${map.maxdate }" name="maxdate" id="ordermaxdate"
															readonly="readonly" placeholder="最大时间"
															class="choose laydate-icon layui-input"
															onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
														</li>

													<li class="textli" id="adress">地区： <!-- <div id="adress"> -->
														<select class="province cxselect choose"
														data-value="${map.province}" data-first-title="选择省"
														name="province" style="width:120px">
													</select> <select class="city cxselect choose"
														data-value="${map.city}" data-first-title="选择市"
														name="city" style="width:120px">
													</select> <select class="county cxselect choose"
														data-value="${map.county}" data-first-title="选择地区"
														name="county" style="width:120px">
													</select> <!-- </div> -->
													</li>
													<%--<li class="textli">订单金额： <input type="text"
														value="${map.minordersum }" name="minordersum"
														class="choose">&nbsp;~ <input type="text"
														value="${map.maxordersum }" name="maxordersum"
														class="choose">
													</li>

													--%><li class="textli">快递单号： <input type="text"
														name="courierno" class="choose" value="${map.courierno}">
													</li>

													<li class="textli">商品编号： <input type="text"
														name="goodsno" class="choose" value="${map.goodsno }">
													</li>
<li class="textli">发货仓库： <select name="whid"
														class="choose" value="${map.whid}">
															<option value="">选择</option>
															<c:forEach items="${whlist}" var="wh">
																<option value="${wh.id}"
																	<c:if test="${map.whid eq wh.id}">selected</c:if>>${wh.name}</option>
															</c:forEach>

													</select></li>
													

													<li class="textli">每页：<input type="text" name="ps"
														class="choose" value="${map.ps}" style="width:40px;">
														行</li>

													<li onclick="gosearch();" class="btn"><span><img
															src="<%=basePath%>css/login/s2.png"> </span>搜索</li>
													<li onclick="chongzhiss();" class="btn"><span><img
															src="<%=basePath%>css/login/s3.png"> </span>重置</li>
												</ul>



											</div>





										</form>

									</div>
								</div>
							</div>

							<%-- 表格  --%>
							<ul class="toolbar" style="width: 94%;margin-left: 3%;">

								<li onclick="checkpass();"
									style="margin-right:10px;margin-bottom: 10px;"><span><img
										src="<%=basePath%>css/login/dui.png"> </span><span
									style="color:red;margin: 0">审核通过</span></li>

								<li onclick="addGoods();"
									style="margin-right:10px;margin-bottom: 10px;"><span><img
										src="<%=basePath%>css/login/dui.png"> </span><span
									style="color:red;margin: 0">添加赠品</span></li>

								<li onclick="addOrderNote();"><span><img
										src="<%=basePath%>css/login/dui.png"> </span><span
									style="color:red;margin: 0">添加订单备注</span>
								</li>
								<li onclick="updateOrder();"><span><img
										src="<%=basePath%>css/login/dui.png"> </span><span
									style="color:red;margin: 0">更新订单详情</span>
								</li>
								<li onclick="cancelOrder();"
									style="margin-right:10px;margin-bottom: 10px;"><span><img
										src="<%=basePath%>css/login/dui.png"> </span><span
									style="color:red;margin: 0">取消订单</span></li>

								<li onclick="userCatch();"><span><img
										src="<%=basePath%>css/login/dui.png"> </span><span
									style="color:red;margin: 0">手动抓取订单</span>
								</li>

								<li onclick="yanchifh();"><span><img
										src="<%=basePath%>css/login/dui.png"> </span><span
									style="color:red;margin: 0">订单延迟发货</span>
								</li>
							</ul>

							<div style="margin-top: 10px;">

								<div style=" overflow:auto;width: 94%;margin-left: 3%">
									<table id="tableone"
										class="table table-striped table-bordered table-hover">
										<thead>
											<tr style="background: #F0F5F7;">
												<td align="center" style="width:70px"><input
													type="checkbox" id="chooseAll">序号</td>
												<td align="center">订单编号</td>
												<td align="center">订单来源</td>

												<td align="center">外部订单号</td>
												<td align="center">订单类型</td>
												<td align="center">订单状态</td>
												<td align="center">订单商品</td>
												<td align="center">收货地址</td>
												<td align="center">下单时间</td>
												<td align="center">支付时间</td>

												<td align="center">发货时间</td>

												<td align="center">总金额</td>
												<td align="center">应付金额</td>
												<td align="center">订单优惠</td>
												<td align="center">运费</td>



											</tr>
										</thead>

										<tbody>
											<c:choose>
												<c:when
													test="${pborder.list !=null &&pborder.list.size()>0 }">
													<c:forEach items="${pborder.list}" var="o" varStatus="os">
														<tr style="font-size:12px">
															<td align="center" style="width:70px"><input
																type="checkbox" class="oneorder" value="${o.id}">
																${os.index+1+(pborder.pageNum-1)*pborder.pageSize}</td>
															<td align="center"><a onclick="orderinfo(${o.id});"
																style="color:#1296DB">${o.orderno}</a>
															</td>
															<td align="center"><c:forEach items="${mapofrom}"
																	var="mp">
																	<c:if test="${o.ofrom eq mp.key}">${mp.value}</c:if>


																</c:forEach></td>
															<td align="center">${o.eorderno}</td>
															<td align="center">
																<%-- 	<c:choose>
																	<c:when test="${o.paytype==1}">在线支付</c:when>
																	<c:when test="${o.paytype==2}">货到付款</c:when>
																	<c:otherwise>无此类型：${o.paytype}</c:otherwise>
																</c:choose> --%> <c:choose>
																	<c:when test="${o.ordertype eq -1}">外部订单</c:when>
																	<c:when test="${o.ordertype eq 1}">正常下单</c:when>
																	<c:when test="${o.ordertype eq 10}">礼品卡兑换订单</c:when>
																	<c:when test="${o.ordertype eq 11}">线下门店订单</c:when>
																	<c:when test="${o.ordertype eq 12}">补录订单</c:when>
																	<c:otherwise>无此类型：${o.ordertype}</c:otherwise>
																</c:choose></td>
															<td align="center"><c:choose>
																	<c:when test="${o.state==0}">未完成支付</c:when>
																	<c:when test="${o.state==1}">已支付，未接单</c:when>
																	<c:when test="${o.state==2}">等待发货</c:when>
																	<c:when test="${o.state==3}">已发货，待用户签收</c:when>
																	<c:when test="${o.state==4}">订单完成</c:when>
																	<c:when test="${o.state==5}">订单取消</c:when>
																	<c:when test="${o.state==6}">退单中</c:when>
																	<c:when test="${o.state==7}">退单完成</c:when>
																	<c:otherwise>无此类型：${o.state}</c:otherwise>
																</c:choose>
															</td>

															<td><c:forEach items="${o.ordergoods}" var="og">
																	<span class="label  label-success"
																		style="font-size:11px;">${og.seller_nick}${og.goodsnum}${og.unit}
																	</span>
																	<br>

																</c:forEach></td>
															<td>${o.useraddress.province
																}${o.useraddress.city}${o.useraddress.county
																}${o.useraddress.address }</td>



															<td align="center">${o.ctime}</td>
															<td align="center">${o.paytime}</td>

															<td align="center">${o.wmstime}</td>

															<td align="right">${o.order_sum/100}</td>
															<td align="right">${o.ammount_sum/100}</td>
															<td align="right">${o.discount_sum/100}</td>
															<td align="right">${o.freight/100}</td>
															<%-- <td align="center"><c:choose>
																	<c:when test="${o.paystate==1}">待支付</c:when>
																	<c:when test="${o.paystate==2}">部分支付</c:when>
																	<c:when test="${o.paystate==3}">已支付</c:when>
																	<c:when test="${o.paystate==4}">申请退款</c:when>
																	<c:when test="${o.paystate==5}">退款成功</c:when>
																	<c:when test="${o.paystate==6}">退款异常</c:when>
																	<c:otherwise>无此类型：${o.paystate}</c:otherwise>
																</c:choose></td> --%>


														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="10" style="color:red;font-size:12px;">&nbsp;&nbsp;未找到订单</td>
													</tr>

												</c:otherwise>
											</c:choose>

										</tbody>
									</table>
								</div>
								<br>
								<div style="clear:both"></div>
								<%--									<c:if test="${sspb.list !=null &&sspb.list.size()>0 }">--%>
								<div
									style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 12px;">

									<span>共<b style="color:#666">${pborder.rowCount }</b>条</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
										style="color:#666">${pborder.pageNum
										}/${pborder.pageCount }页</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btns" style="color:#666"
										id="prePage">上一页</button>
									&nbsp;&nbsp;&nbsp;
									<button type="button" class="btns" style="color:#666"
										id="nextPage">下一页</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 跳转到
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<form action="<%=basePath%>cmanageorder/queryorderlist.do"
										method="post" style="float:right" id="pageform">
										<input type="text" name="page" id="pninfo"
											style="width:60px;height:30px" onkeyup="priceKeyup(this)"
											maxval="${pborder.pageCount}" value="${pborder.pageNum}">
										&nbsp;&nbsp;&nbsp; <input type="hidden" id="pageCount"
											value="${pborder.pageCount}"> <input type="hidden"
											name="orderno" value="${map.orderno}"> <input
											type="hidden" name="state" value="${map.state }"> <input
											type="hidden" name="mindate" value="${map.mindate }">
										<input type="hidden" name="maxdate" value="${map.maxdate }">

										<input type="hidden" name="consignee"
											value="${map.consignee }"> <input type="hidden"
											name="eorderno" value="${map.eorderno }"> <input
											type="hidden" name="beforeorderno"
											value="${map.beforeorderno }"> <input type="hidden"
											name="courierno" value="${map.courierno }"> <input
											type="hidden" name="goodsno" value="${map.goodsno }">
										<input type="hidden" name="minordersum"
											value="${map.minordersum }"> <input type="hidden"
											name="maxordersum" value="${map.maxordersum }"> <input
											type="hidden" name="ofrom" value="${map.ofrom }"> <input
											type="hidden" name="province" value="${map.province }">
										<input type="hidden" name="city" value="${map.city }">
										<input type="hidden" name="county" value="${map.county }">
										<input type="hidden" name="paytype" value="${map.paytype }">
										<input type="hidden" name="whid" value="${map.whid}">
										<input type="hidden" name="ps" value="${map.ps }"> <input
											type="hidden" name="other" value="${map.other}"> <input
											type="hidden" name="likeaddress" value="${map.likeaddress}">
										<input type="hidden" name="likegoodsname"
											value="${map.likegoodsname}"> <input type="button"
											value="GO" class="btns" id="goPage">
									</form>
								</div>
								<%--									</c:if>--%>


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
	<!-- </div> -->
	<!-- /.main-content -->



	<div id="choosegoods" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content" style="border-radius:0">


				<div class="modal-body no-padding"
					style="max-height:500px;overflow:auto;">
					<table
						class="table table-striped table-hover no-margin-bottom no-border-top">
						<tr>
							<td>
								<div class="input-group" style="width:70%;margin-left:15%">
									<div class="input-group-btn">

										<select class="form-control" id="search_kind"
											style="width: 120px; font-family: 黑体; font-size: 12px;">
											<option value="1" selected>按商品名称</option>
											<option value="2">按商品货号</option>
										</select>
									</div>
									<input type="text" class="form-control" id="search_goods_info"
										placeholder="搜索"> <span class="input-group-btn">
										<button class="btn btn-primary" type="button" id="searchgoods">搜索</button>
									</span>
								</div>
							</td>
						</tr>

					</table>

					<table
						class="table table-striped table-bordered table-hover no-margin-bottom no-border-top"
						id="goodsmenu">


					</table>
				</div>

				<div class="modal-footer no-margin-top">

					<button class="btn btn-sm btn-danger pull-left"
						data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i> 取消
					</button>

					<button class="btn btn-sm btn-info pull-left" type="button"
						id="addU">
						<i class="ace-icon fa fa-times"></i> 添加
					</button>
					<label id="error" style="color:red;font-size:12px"></label>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- PAGE CONTENT ENDS -->

	<script type="text/javascript"
		src="<%=basePath%>js/cg/order/searchGoods.js?v=1.0"></script>

	<script type="text/javascript">
var chooseIds=[];
function changeCH(){

	$("#otherchoose").toggleClass("hidden");
	
	if(	$("#otherchoose").hasClass("hidden")){
 		$("#other").val("-1");
 	}else{
 		$("#other").val("1");
 	}
 
 
}

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
	

	$(document).on("click", ".allod", function(){
		   $(".oneod").prop("checked",this.checked);
		});
		
		$(document).on("click", ".allodsuc", function(){
			   $(".oneodsuc").prop("checked",this.checked);
			});

		$(document).on("click", ".alloderror", function(){
			   $(".oneoderror").prop("checked",this.checked);
			});
		
});
 
 
</script>

	<script type="text/javascript">
$.cxSelect.defaults.url ='<%=basePath%>/js/city/cityData.min.json';
$('#adress').cxSelect({
	selects: ['province', 'city', 'county'],
	required:false,
	nodata:"none",
});
</script>
</body>

</html>

