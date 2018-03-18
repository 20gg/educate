<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>轮播管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	 
	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" />

  </head>
  
  <body>
     <div style="margin-top: 20px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">轮播管理
     	 <c:if test="${fn:length(lunbo_list) < 3}">
     	 	<button type="button"  class="btn btn-success"  
  				 style="width: 100px; float: right;" onclick="add_show();">新增轮播</button>
     	 </c:if> 
     </div>
     
      <c:if test="${fn:length(lunbo_list) == 0}">
     	<div style=";width: 94%; margin-left: 3%;  line-height: 30px;
     	 color: red;margin-top: 20px;">还未添加轮播图片，请及时添加！</div>
      </c:if>
      
       <table class="table table-hover table-bordered"  style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:14px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50; text-align: center;" >
  			<tr style="background-color: #fcfcfc;">
	  			<td style="width: 10%;">序号</td> 
	  			<td>轮播信息</td>
	  			<td style="width: 10%;">操作</td>
  			</tr>
  			<c:forEach items="${lunbo_list}" var="l" varStatus="ls">
  			<tr >
	  			<td style="width: 10%;">${ls.index+1}</td> 
	  			<td style="text-align: left;">
	  				<div>名称：${l.name}</div>
	  				<div>上传时间：${l.cdate}</div>
	  				<img  src="<%=basePath%>${l.img}" style="width: 320px; height: 160px;">
	  				<c:if test="${not empty l.link}">
	  				<div>轮播链接：${l.link}</div>
	  				</c:if>
	  				
	  			</td>
	  			<td style="width: 10%;"><a onclick="edit_show('${l._id}','${l.name}','${l.link}');">修改</a></td>
  			</tr>
  			</c:forEach>
  			
  		</table>
  		
 <div id="add_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 500px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="tc_title">新增轮播</h4>
      </div>
      <div class="modal-body"  >
       
       		<form id="lunbo_form">
       			<div>
       				<input type="text" class="form-control" name="l_name" id="l_name" placeholder="请输入轮播名称">
       			</div>
       			<div style="margin-top: 10px;">
       			 <span>请上传轮播图片：</span><input type="file" name="up_img" id="up_img">
       			</div>
       			
       			<div style="margin-top: 10px;color: red;">
       				上传图片规格：750像素x320像素效果最佳，同等比例尺寸亦可。格式只支持jpg,png,JPG,PNG,JPEG,jpeg.
       				
       			</div>
         		<div style="margin-top: 10px;">
       				<input type="text" class="form-control" name="l_link" id="l_link" placeholder="请输入轮播链接">
       			</div>
       			<input type="hidden" name="l_id"  id="l_id" value="-1">
         	</form>
       
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="sure_ok();;">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">取消</button>
      </div>
    </div> 
  </div> 
</div> 
  		 
  </body>
<script type="text/javascript" src="<%=basePath%>/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/boot/3.0.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.tips.js"></script>
<script type="text/javascript">

function add_show(){
	
	$("#tc_title").text("新增轮播");
	
	$("#l_name").val("");
	$("#up_img").val("");
	$("#l_link").val(""); 
	$("#l_id").val("-1");
	
	$("#add_page").modal({
		keyboard : false
	}); 
	$("#add_page").modal('show');
}

function edit_show(id,name,link){
	
	$("#tc_title").text("修改轮播");
	
	$("#l_name").val(name);
	$("#up_img").val("");
	$("#l_link").val(link); 
	$("#l_id").val(id);
	
	$("#add_page").modal({
		keyboard : false
	}); 
	$("#add_page").modal('show');
}

function sure_ok(){
	
	var l_name = $("#l_name").val();
	if(l_name == ""){
		error_show("l_name","请输入轮播名称");
		return false;
	}
	
	var l_id = $("#l_id").val();
	
	if(l_id == -1){
		var up_img = $("#up_img").val();
		if(up_img == ""){
			error_show("up_img","请选择图片！");
			return false;
		}
		
	}
	
	var formData = new FormData($("#lunbo_form" )[0]);
	  
	$.ajax({
		type : "post", 
		url :pt_path+"article/upload_lunbo.do",
		data : formData,
		dataType: 'text',  
		cache: false,  
		processData: false,  
		contentType: false,
		success : function(data) 
		{	
			location.href = pt_path +"/article/query_lunbo.do"; 
		},
		
	});	
	 
}

function error_show(id_name,msg) {
	$("#"+id_name).tips({
			side : 2,
			msg : msg,
			bg : '#2CC159',
			time : 3
	});
}

</script>
</html>
