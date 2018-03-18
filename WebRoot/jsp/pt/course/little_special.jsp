<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>小课题详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/tanchu.css" rel="stylesheet" type="text/css" />
	 

  </head>
  
  <body>
  
  	<div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  ">
  	小课题详情
  	</div>
    
      
     <div  style="width: 94%; margin-left: 3%;text-align: right; margin-top: 20px; margin-bottom: 10px;">
  			 
  			 <a  class="btn btn-primary"   onclick="insert_bb();">+新增小课题</a>
  	 </div> 
    
    <div class="input-group"  style="width: 600px; position: absolute;top:9%;left:3%;"> 
  	 		<span class="input-group-btn form-control" style="width: 80px;">小课题名称 </span>  
		  <input type="text" id="search_input" value="${input}" class="form-control"  placeholder="请输入搜索信息" maxlength="128">
		  <span class="input-group-btn"> 
		  	 <button type="button" class="btn btn-primary" style="width: 80px;" onclick="go_search();">搜索</button>
		  </span>
		</div>
     
     <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    	 
    		<td  >小课题编号</td>
    		<td   >小课题名称</td>
    		 <td >真实订阅数</td>
    		 <td  >价格</td>
    		<!--  <td>优惠价</td> -->
    		 <td >状态</td>
    		<!-- <td>网址链接</td> -->
    		<td  >操作</td>
    		</tr>    		
    		<c:forEach items="${special_list.list}" var="special" varStatus="status"> 
    				<tr>
    					 
    					<td   id="userid">${special.special_no}</td>
    					<td  >${special.special_name }</td>
    					 
    					<td>
    					<c:if test="${ empty special.base_order_count}">
    					${special.order_count}
    					</c:if>
    					
    					<c:if test="${not empty special.base_order_count}">
    					${special.order_count-special.base_order_count}
    					</c:if>
    					 </td>
    					<td  >
    			<fmt:formatNumber type="number" value="${special.price/100}" pattern="0.00" maxFractionDigits="2"/>元  
    					</td>
    					<%-- <td>
    					<fmt:formatNumber type="number" value="${special.discount_price/100}" pattern="0.00" maxFractionDigits="2"/>元  
    				
    					</td> --%>
    					<td   onclick="blacklist2('${special.special_no}','${special.state}');" style="cursor: pointer;">
    					<c:if test="${special.state==1}">已上架</c:if>
    					<c:if test="${special.state==-1}">已下架</c:if>
    					</td>
    					<%--   <td>
    					  <a href="${course.url}"  target="_blank">${course.c_name }</a>
    					  </td> --%>
    					  
    					 <%--  <td>
    					  <img alt="" src="<%=basePath%>${course.img}">
    					  </td> --%>
    		<td height="30">
    					<%--  <a href="javascript: deletebyid({special_no:'${special.special_no}',name:'${special.special_name}'})">删除</a>  --%>
    					<a href="<%=basePath %>course/show_uodate_little.do?special_no=${special.special_no}" >修改</a>
    					<a href="<%=basePath %>course/little_specialinfo.do?special_no=${special.special_no}" >详情</a>
    						<a href="javascript: to_have({c_no:'${special.special_no}'})" >小课题详情地址</a>
    						<%--  <a href="<%=basePath %>article/query_exercise.do?c_no=${special.special_no}" >课程试题</a>  --%>
    					</td>	  			  
    				</tr>
    		 <input type="hidden" id="skuid" value="${special.special_no }">
    		  <input type="hidden" id="stateid" value="${special.state }">
    		</c:forEach>
    		</table>
    
    
      		
<%--   <div style="width: 94%; margin-left: 3%;">
<ul>
 <li style="list-style: none; float: right; margin-left: 30px;">共${special_list.rowCount}条</li>

<li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="next_page();" style="color: #C90E02;">下一页</a></li>

 <li style="list-style: none;float: right;margin-left: 30px;">第<span id="p_order_num">${special_list.pageNum}</span>/<span id="p_order_count">${special.pageCount}</span>页</li>
 <li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="last_page();" style="color: #C90E02;">上一页</a></li>
 
</ul>
</div>  --%>    		

<div class="page">
<div style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 12px;">

									<span>共<b style="color:#666">${special_list.rowCount }</b>条</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
										style="color:#666">${special_list.pageNum
										}/${special_list.pageCount }页</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btns" style="color:#666"
										id="prePage">上一页</button>
									&nbsp;&nbsp;&nbsp;
									<button type="button" class="btns" style="color:#666"
										id="nextPage">下一页</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 跳转到
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<form action="<%=basePath%>course/shows_course_order.do"
										method="post" style="float:right" id="pageform">
										<input type="text" name="page" id="pninfo"
											style="width:60px;height:30px" onkeyup="priceKeyup(this)"
											maxval="${special_list.pageCount}" value="${special_list.pageNum}">
										&nbsp;&nbsp;&nbsp; <input type="hidden" id="pageCount"
											value="${special_list.pageCount}">
										 <input
											type="hidden" name="mindate" value="${map.mindate }">
										<input type="hidden" name="maxdate" value="${map.maxdate }">

										
										<input type="hidden" name="ps" value="${map.ps }">
										
										 <input type="button"
											value="GO" class="btns" id="goPage">
									</form>
								</div>
</div>

   <div class="modal fade" id="special" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 500px;">
      <div class="modal-content">
         <div class="modal-header" style="background-color: green;">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" style="color: #fff;">&times;</span>
            </button>
           
         </div>
         <div class="modal-body" style="text-align:left;">
			<div>专题名称:&nbsp;&nbsp;&nbsp;<input style="line-height:10px; padding:0;" type="text" id="special_name" /></div><br>
			 <div   style="display: none;"  id="dis_play" >
			 <div>	
	    	<a id="idcardfaceIMGid"><img id="img2" name="nameimg2" src=""  style="width: 108px;height: 108px; visibility: hidden;"   /></a>
	   </div>
	   	<input type="hidden" id="img_url2" name="img_url2"value=""/>
	   	</div>
			 <div  style="text-align: center;">
			  <div style=" float: left;font-size:15px;" >  专题图片</div>
			  <div 	 > <a href="javascript:up_img(2);" class="sel_btn">上传图片</a></div>
			  </div><br>
			  		
			  		<div>专题介绍:
			  		<textarea rows="6" cols=" 40" id="special_textarea1">
			  		
			  		</textarea>
			  		</div><br>
			  		
			  		
			  		 
          			<div id="appen_div" style="text-align: center;">
					</div>
			  		<div>关联资源:
			  		<button onclick="adddiv();"   style=" margin-top: 10px;" >增加</button>
			  		<input type="text" width="80px;" id="special_ziyuan" name="input_name">
			  		</div><br>
			  		<div  >试听时长:
			  		<input type="text"  style="width: 40px;" id="special_time">分钟
			  		</div><br>
			  		<div>标牌价格:
			  		<input type="text"   style="width: 40px;" id="special_price">元
			  		</div><br>
			  		<div>促销价格: 
			  		<input type="text"   style="width: 40px;" id="special_sale_price">元
			  		</div>
			  		<div>是否免费: 
			  		<input type="text"   style="width: 130px;" id="is_free" placeholder="-1代表免费1收费" >元
			  		</div>
			  	
			  		<div>主讲老师:
			  					<input type="text" id="teacher">
			  			</div><br>
			  
			  		<div>适宜人群:
			  					<input type="text" id="fit_people">
			  			</div><br>
			  			
			  			<div>订购须知:
			  					<input type="text" id="order_notice">
			  			</div><br>
			  
			   </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="special_com();" style="background-color: green;border-color: green;"> 提交</button>
         </div>
      </div> 
</div> 
</div>    
<!-- 添加框（操作成功） -->
<div class="modal fade" id="successdiv" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true" >
   <div class="modal-dialog" style="width: 200px;">
      <div class="modal-content">
         <div class="modal-body" style="text-align: center">
         <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
         </button>
         <br>
         <br>
			<h5 style="text-align: center;">操作成功！</h5>
			<input type="hidden" id="pageid">
         </div>
         <div class="modal-footer" style="text-align: center;" >
            <button type="button" class="btn btn-primary" onclick="successloadpage();" style="background-color: green;border-color: green;"> 确定</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>

<!-- 添加框（操作失败） -->
<div class="modal fade" id="faildiv" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true" >
   <div class="modal-dialog" style="width: 200px;">
      <div class="modal-content">
         <div class="modal-body" style="text-align: center">
         <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
         </button>
         <br>
			<h5 style="text-align: center;color: #f00;">操作失败</h5>
         </div>
         <div class="modal-footer" style="text-align: center;" >
            <button type="button" class="btn btn-default" onclick="loadpage(1);" style="background-color: #c0green0;border-color: green;">确定</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>    
    



 <!-- 显示详情页地址 -->
<div class="modal fade" id="to_show_url" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true" >
   <div class="modal-dialog" style="width: 200px;position: fixed;top:5%;left: 15%;">
      <div class="modal-content" style="width:600px;">
         <div class="modal-body"   >
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" style="color: #666;">&times;</span>
            </button> 
            <h4 class="modal-title">专题轮播链接地址</h4>
         </div>
         <div class="modal-body"  style="text-align: center;" >
            <input type="text" value=""  id="c_no_val"  style="width: 550px;">
          </div>
      </div> 
</div> 
</div>	
    
    <!-- 添加框（删除确认） -->
<div class="modal fade" id="deletediv" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true" >
   <div class="modal-dialog" style="width: 200px;">
      <div class="modal-content">
         <div class="modal-header" style="background-color: #C90E02;">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" style="color: #fff;">&times;</span>
            </button>
         <br>
			<h5 style="text-align: center; color: #fff;">确定要删除“<b id="deletespan"></b>”?</h5>
			<input type="hidden" id="deleteinputid">
         </div>
         <div class="modal-footer" style="text-align: center;" >
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="deleterole();" style="background-color: green;border-color: green;"> 删除</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>	
    <!-- 添加框（修改信息） -->
<div class="modal fade" id="updaterole" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 500px;">
      <div class="modal-content">
         <div class="modal-header" style="background-color: green';">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" style="color: green;">&times;</span>
            </button>
            <h4 class="modal-title" id="updatemyModalLabel" style="color: green;">
               修改信息
            </h4>
         </div>
         <div class="modal-body" style="text-align: center">
         
         <h5>专题编号</h5>			
		<input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text"  disabled="disabled" id="show_cno" value=" "/><br> 
   
         
			<h5>专题名称</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatec_name" /><br> 
   
			<h5>价格</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updateprice" /><br>
			
			<h5>促销价格</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatediscount_price" /><br>
			
			<h5>主讲</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updateteacher" /><br>
			
			  <h5>是否免费</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updateis_freer" placeholder="1代表收费-1代表免费" /><br>
			  
			  <h5>是否上架</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatestate" placeholder="1代表上架-1代表下架" /><br>
		  
			 <h5>是否置顶</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatetop" placeholder="1代表置顶0代表不置顶" /><br>
			  
			
			
			  
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="updatecommit" style="background-color: #c00;border-color: #c00;"> 提交</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>
    <div class="clear"></div>	
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
  	<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
  <script type="text/javascript"	src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
  <script type="text/javascript">
  
  
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
  });
  
  function last_page(){
		
		var page_num = $("#p_order_num").text();
		var page_count = $("#p_order_count").text();
		
		var this_num = Number(page_num);
		var count = Number(page_count);
	

		if(this_num <= 1){
			alert("已经是第一页了！");
			return;
		}else{
			var page = this_num -1;
			var datas = {"page":page};			
			var url = getRootPath()+"/user/show_little_special.do";
		    jspost(url, datas); 
		}
	}
function next_page(){
		
		var page_num = $("#p_order_num").text();
		var page_count = $("#p_order_count").text();
		
		var this_num = Number(page_num);
		var count = Number(page_count);
		
		
		 
		if(this_num >= count){
			alert("已经是最后一页了！");
			return;
		}else{
			var page = this_num +1;
			var datas = {"page":page};			
			var url = getRootPath()+"/user/show_little_special.do";
		    jspost(url, datas); 
		}
	}

function jspost(URL, PARAMS) {     

	    var temp = document.createElement("form");        
	    temp.action = URL;        
	    temp.method = "post";        
	    temp.style.display = "none";        
	    for (var x in PARAMS) {        
	        var opt = document.createElement("textarea");        
	        opt.name = x;        
	        opt.value = PARAMS[x];        
	        // alert(opt.name)        
	        temp.appendChild(opt);        
	    }        
	    document.body.appendChild(temp);        
	    temp.submit();        
	    return temp;        
	}  	 
	
	
function blacklist2(special_no,state) {
  	 
  	 
  
  	$.ajax({
  		type:"post",
  		url: getRootPath() +'/course/special_freeze2.do',
  		dataType:"json",
  		data:{"special_no":special_no,"state":state},
  		success: function(data){
  			if("success"==data[0].result){
  				
  				window.location.reload();
  			}else{
  				alert("专题不存在");
  			}
  			
  		}
  		
  	});

}	

	function deletebyid(obj){
	$("#deletespan").html(obj.name);
	$("#deleteinputid").val(obj.special_no);
	$("#deletediv").modal("show");
}
	function deleterole(){
		$("#deletediv").modal("hide");
		var special_nom=$("#deleteinputid").val();
		
		$.ajax({
			type: "POST",
			url: getRootPath() +'/course/delete_special_nom2.do',
			data: {"special_nom":special_nom},
			dataType: "json",
			success: function(data){
				if("success"==data[0].result){
					var pagenum="${page_count}";
					if("${count}"%50==1){
						pagenum--;
					}
					$("#pageid").val(pagenum);
					$("#successdiv").modal("show");
				}else{
					alert("专题编号为空");
				}
			}
		});
		
}

function successloadpage(){
	location.reload(false);
}

	function updaterole(obj){			
	
	
		document.getElementById('show_cno').value=obj.special_no;
		$("#updaterole").modal("show");
}
$("#updatecommit").click(function(){	
	$("#updaterole").modal("hide");
	var c_name=$("#updatec_name").val();
	 var course_cno=$("#show_cno").val(); 
	var price=$("#updateprice").val();
	 var  discount_price=$("#updatediscount_price").val();
	 var  teacher=$("#updateteacher").val();
	 var is_freer=$("#updateis_freer").val();
	 var  state=$("#updatestate").val();
	 var  top=$("#updatetop").val(); 
	
	 
		   $.ajax({
			type: "POST",
			url: getRootPath() +'/course/update_special.do',
			data: {"c_name":c_name,"price":price,"c_no":course_cno,"discount_price":discount_price,"teacher":teacher,"is_freer":is_freer,"state":state,"top":top},
			dataType: "json",
			success: function(data){
				if("success"==data[0].result){
			var pagenum="${page_count}";
								$("#pageid").val(pagenum);
								$("#successdiv").modal("show");

				}else{
					$("#faildiv").modal("show");
				}
			}
		});  
	   
});

function special_com() {
	 var  special_name=$("#special_name").val();
	 var  img_url2=$("#img_url2").val();  
	 //var  special_ziyuan=$("#special_ziyuan").val();  	 
	 var  special_textarea1=$("#special_textarea1").val().replace(/[\r\n]/g,"");
	 var  special_time=$("#special_time").val();
	 var  special_price=$("#special_price").val();
	 var  special_sale_price=$("#special_sale_price").val();
	 var is_free=$("#is_free").val();
	 var teacher=$("#teacher").val();
	 var fit_people =$("#fit_people").val();
	 var order_notice=$("#order_notice").val();
	 
	 var cont_content="";
		
		$("input[name='input_name']").each(function(){
			cont_content+=$(this).val()+",";
			});
	 
	 if(special_name==null||special_name==""){
			$("#special_name").tips({
				side : 2,
				msg : '请填写专题名',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
	 if(fit_people==null||fit_people==""){
			$("#fit_people").tips({
				side : 2,
				msg : '请填写专题名',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
	 if(order_notice==null||order_notice==""){
			$("#order_notice").tips({
				side : 2,
				msg : '请填写专题名',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
	 
		if(img_url2==null||img_url2==""){
			$("#img_url2").tips({
				side : 2,
				msg : '请上传图片',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		if(special_textarea1==null||special_textarea1==""){
			$("#special_textarea1").tips({
				side : 2,
				msg : '请填写专题介绍',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		if(cont_content==null||cont_content==""){
			$("#special_ziyuan").tips({
				side : 2,
				msg : '关联的资源不能为空',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		if(special_time==null||special_time==""){
			$("#special_time").tips({
				side : 2,
				msg : '请设置试听时间',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		if(special_price==null||special_price==""){
			$("#special_price").tips({
				side : 2,
				msg : '请填写价格',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		if(is_free==null||is_free==""){
			$("#is_free").tips({
				side : 2,
				msg : '请填写是否收费',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
	 
		$.ajax({
			type:"POST",
			url:getRootPath() +'/course/insert_little_special.do',
			data:{'special_name':special_name,'img_url2':img_url2,'special_ziyuan':cont_content,'special_textarea1':special_textarea1,'special_time':special_time,'special_price':special_price,'special_sale_price':special_sale_price,'is_free':is_free,'teacher':teacher,'fit_people':fit_people,'order_notice':order_notice},
			dataType:"json",
			success:function(data){
				if("success"==data[0].result){

					 $("#special").modal("hide");
					
				} 
		}
		
			});
	 
}
function adddiv(){
	var html= "<div class='input-group' style='margin-top:10px;'> <input type='text' name='input_name'  class='form-control' >"
 	+" <span class='input-group-addon' onclick='delete_content(this);' >删除</span> </div>";
		$("#appen_div").append(html);
		
	
}

function delete_content(obj){
	
	$(obj).closest("div").remove();
}

function  insert_bb(){
	 
	
	window.location.href=getRootPath()+"/jsp/pt/course/insert_little_special.jsp";		
	
	
}

function get_data(url_data) {
	jspost(pt_path +"/course/show_little_special.do", url_data);
}


function go_search(){
	
	 
	var name = $("#search_input").val();
	
	//if(name != ""){
		var url_data = {"name":name};
		get_data(url_data);
	//}
	 
}
function to_have(obj){
	
	 
	var html="http://www.daodaketang.com/educate/jsp/wx/course/free_special.html?c_no="+obj.c_no+"";
		
		$("#c_no_val").val(html);
		
		//alert(html);
		$("#to_show_url").modal("show");
	}
  </script>
  
</html>
