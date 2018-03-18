<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增文章</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" />

  </head>
  
  <body>
     <div style="margin-top: 20px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">编辑文章</div>
     
     <input type="hidden" id="art_id" value="${art._id}">
     
     
     <table class="table " style="width: 94%; margin-left: 3%; margin-top: 20px; font-size:14px; cursor: hand;
  		border: 1px solid #eee;  border-left-width: 5px; border-radius: 5px;border-left-color: #4CAF50;">
     	<tr>
     		<td style="width: 10%;"><label>类别：</label></td>
     		<td style="width: 90%;">
     			<select id="sort" class="form-control" style="width: 400px;">
     				<option <c:if test="${art.sort == 1}">selected="selected"</c:if>  value="1">干货</option>
     				<option <c:if test="${art.sort == 2}">selected="selected"</c:if>  value="2">精选</option>
     				<option <c:if test="${art.sort == 3}">selected="selected"</c:if>  value="3">原创</option>
     			</select>
     		</td>
     	</tr> 
     	<tr>
     		<td style="width: 10%;"><label>热荐：</label></td>
     		<td style="width: 90%;">
     			 <select id="recommend" class="form-control" style="width: 400px;">
     				<option <c:if test="${art.recommend == 1}">selected="selected"</c:if>  value="1">热荐</option>
     				<option <c:if test="${art.recommend == 0}">selected="selected"</c:if> value="0">不热荐</option> 
     			</select>
     		</td>
     	</tr>
     	<tr>
     		<td style="width: 10%;"><label>标题：</label></td>
     		<td style="width: 90%;">
     			 <input type="text" id="title" class="form-control" style="width: 400px;" value="${art.title}" >
     		</td>
     	</tr>
     	<tr>
     		<td style="width: 10%;"><label>图片：</label></td>
     		<td style="width: 90%; "> 
  				 <img  id="new_img" src="<%=basePath%>${art.img}" onclick="add_img();" style="width: 300px;border: 1px solid #ddd;">
  				 <input type="hidden" id="img_input" value="${art.img}" >
     		</td>
     	</tr>
     	<tr>
     		<td style="width: 10%;"><label>文章内容：</label></td>
     		<td style="width: 90%;">
     			 
     			 <textarea id="context" name="content1" style="width: 800px" type="text/plain">${art.context}</textarea>
		<script type="text/javascript" src="<%=basePath%>js/sku/ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->								
		<script type="text/javascript" src="<%=basePath%>js/sku/ueditor.all.js"></script>
		<!-- 实例化编辑器 -->
		<script type="text/javascript">
			/* var ue3 = UE.getEditor("context");
			//对编辑器的操作最好在编辑器ready之后再做
			ue3.ready(function() {
				//设置编辑器的内容
				//获取html内容，返回: <p>hello</container2p>
				var html = ue3.getContent();
				//获取纯文本内容，返回: hello
				var txt = ue3.getContentTxt();
			}); */
			
			var editor = new UE.ui.Editor({ initialFrameWidth:'900', initialFrameHeight:'900'});
		    editor.render("context");
			
				//var ue1 = UE.getEditor("container1");
				//对编辑器的操作最好在编辑器ready之后再做
				var htmlstr="";
				editor.ready(function() {
					//设置编辑器的内容
					
					//获取html内容，返回: <p>hello</container2p>
					 htmlstr = editor.getContent();
					//获取纯文本内容，返回: hello
					var txt = editor.getContentTxt();
				});
		</script>
     		</td>
     	</tr> 
     </table>
     
     <div style="margin-top: 20px;text-align: center;">
     		 <button type="button" class="btn btn-primary"  style="width: 100px; " onclick="commit();">提交</button>
     </div>
     <br><br><br>
     
     
  <div id="img_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 360px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">上传图片</h4>
      </div>
      
      <div style="margin-top: 10px;color: red;">
       				上传图片规格：750像素x320像素效果最佳，同等比例尺寸亦可。格式只支持jpg,png,JPG,PNG,JPEG,jpeg.
       				
       			</div>
      <div class="modal-body">
         	<form id="art_form">
         		<input type="file" name="up_img_input" id="up_img_input">
         	</form>
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="uploadPic();">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">关闭</button>
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
//上传图片：确定上传
function uploadPic() {
	var file = $("#up_img_input").val();
	if(file == ""){
		createBbg("图片不能为空！");
		return;
	}
	 
	var formData = new FormData($("#art_form" )[0]);
	  
	$.ajax({
		type : "post", 
		url :pt_path+"article/upload_artimg.do",
		data : formData,
		dataType: 'text',  
		cache: false,  
		processData: false,  
		contentType: false,
		success : function(data) 
		{	
			if(data.length>0)
			{
				if(data=="error"){
					alert('失败！');
				}else{ 
					
					$("#new_img").attr("src",data);
					$("#img_input").val(data);
					
					$("#img_but").hide(); 
					$("#new_img").show();
					
					$("#img_page").modal({
						keyboard : false
					}); 
					$("#img_page").modal('hide');
				}
			}
		},
		
	});	
	 
}

function add_img(){
	
	$("#img_page").modal({
		keyboard : false
	}); 
	$("#img_page").modal('show');
	
	$("#up_img_input").trigger('click');
}

function commit(){
	
	var sort = $("#sort").val();
	
	var recommend = $("#recommend").val();
	
	var title = $("#title").val();
	
	if(title == ""){
		$("#title").tips({
			side : 2,
			msg : "请输入文章标题！",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	
	var img = $("#img_input").val();
	if(img == ""){
		$("#title").tips({
			side : 2,
			msg : "请上传图片！",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	 
	var context = editor.getContent();
	
	if(context == ""){
		$("#context").tips({
			side : 2,
			msg : "请输入文章内容！",
			bg : '#2CC159',
			time : 3
		});
		
		return false;
	}
	
	var art_id = $("#art_id").val();
	
	$.ajax({
 		type:"post",
 		url: pt_path +"/article/edit_art.do",
 		dataType:"json",
 		data:{"art_id":art_id,"sort":sort,"recommend":recommend,"title":title,"img":img,"context":context},
 		success: function(data){
 			
 			if(data[0].back_code == 200){
 				 
 				location.href = pt_path +"/article/get_art_list.do"; 
 			}
 		}
	 });
}



</script>
</html>
