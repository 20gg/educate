<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> 添加课程</title>
    
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/tanchu.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath%>css/login/button.css?v=3">
	<link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css"
	type="text/css"></link>

  </head>
  
  <body>
  	
  	  <div class="modal-body" style="text-align:left;">
			<div>课程名称:&nbsp;&nbsp;&nbsp;
			<input   type="text" id="uname" class="form-control" style="width: 500px;" /></div><br>
			
			<h5>课程分类</h5>
			<div >
			<div style="float: left;"> <input style="  padding:0;" type="radio"  value="1"  name="radion" checked="checked"/>视频</div>
			<div  > <input style=" padding:0;margin-left: 20px;" type="radio"   value="0" name="radion"/>音频</div>
			 </div><br>
			<!--  <div style="display: none;" id="dis_play1">
			 <div>
	    	<a id="idcardfaceIMGid"><img id="img1" name="nameimg1" src=""  style="width: 108px;height: 108px; visibility: hidden;"   /></a>
	   </div>
	   	<input type="hidden" id="img_url1" name="img_url1"value=""/>
	   	</div>
	   	
	   	<div  style="text-align: center;">
			  <div style=" float: left;font-size:15px;" >  图片</div>
			  <div 	 > <a href="javascript:up_img(2);" class="sel_btn">上传图片</a></div>
			  </div><br>
			 
			   <div style="display: none;" id="dis_play">
		 
	   	<input type="hidden" id="img_url3" name="img_url3"value=""/>
	   	</div> -->
	   		<div  style="text-align: center; display: none;" >
			  <div style=" float: left;font-size:15px;" >  课程视频/音频</div>
			  <div 	 > <a href="javascript:up_view(3);" class="sel_btn">上传视频/音频</a></div>
			  </div><br>
	   	
			  课程图片：
			  <button type="button"  id="img_but" class="btn btn-primary"  style="width: 100px;" onclick="add_img();">添加图片</button>
  				 <img  id="new_img" src="" onclick="add_img();" style="width: 300px;display: none; border: 1px solid #ddd;">
  				 <input type="hidden" id="img_input" ><br>
			  		
			  		
			    分享使用的图片：
			  <button type="button"  id="img_but2" class="btn btn-primary"  style="width: 100px;" onclick="add_img2();">添加图片</button>
  				 <img  id="new_img2" src="" onclick="add_img2();" style="width: 300px;display: none; border: 1px solid #ddd;">
  				 <input type="hidden" id="img_input2" ><br>		
			  		<!-- <div >课程简介:
			  			<input type="text"  id="introduce" class="form-control" style="width: 200px;">
			  		</div><br> -->
			  		
			  		
			  		<div>分享使用的摘要:
			  		<textarea   id="introduce2" class="form-control" rows="3" style="width: 400px;"  ></textarea>
			  		</div><br>
			  		
			  		<div>课程类型:
			  					<input type="text" id="cs_type"  placeholder='比如:酬薪,金融' class="form-control" style="width: 200px;">
			  			</div><br>
			  			
			  			<div>主讲老师:
			  					<input type="text" id="teacher" class="form-control" style="width: 200px;">
			  			</div><br>
			  			
			  			<div>基础点击量:
			  					<input type="text" id="base_watch" class="form-control" style="width: 200px;">
			  			</div><br>
			  			
			  			<div style="display: none;">视频/音频时长:
			  			<input type="text" id="time" class="form-control" style="width: 200px;"> 
			  			
			  			</div><br>
			  			
			  			<div>课程简介:
			  			 
     			 <textarea id="textarea1" name="content1" style="width: 800px" type="text/plain">${art.context }</textarea>
		<script type="text/javascript" src="<%=basePath%>js/sku/ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->								
		<script type="text/javascript" src="<%=basePath%>js/sku/ueditor.all.js"></script>
		<!-- 实例化编辑器 -->
		<script type="text/javascript">
			var ue3 = UE.getEditor("textarea1");
			//对编辑器的操作最好在编辑器ready之后再做
			ue3.ready(function() {
				//设置编辑器的内容
				//获取html内容，返回: <p>hello</container2p>
				var html = ue3.getContent();
				//获取纯文本内容，返回: hello
				var txt = ue3.getContentTxt();
			});
		</script>
			  			</div><br>
			  			 
			  		
			  		<div>视频id:
			  		<input type="text" width="80px;" id="ziyuan" class="form-control" style="width: 200px;"> 
			  		</div><br>
			  		
			  		<div>视频有效期:
			  		 <input   type="text" id="yx_xx" readonly="readonly"  style="width: 200px;height: 35px;"
			 	class="choose laydate-icon layui-input"
				onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD'})"/>
			  		
			  		</div><br>
			  		
			  		<div>购买有效期:  
			  			
			  		<select id="year_c" class="form-control" style="width: 200px;">
			  		<option value="1" >1年</option>
			  		<option value="2"  >2年</option>
			  		<option value="3"  >3年</option>
			  		<option value="4"  >4年</option>
			  		<option value="5"  >5年</option>
			  		</select>
			  		</div><br>
			  		
			  		<div  >试听时长:
			  		
			  		<div class="input-group" style="width: 200px;">
					  <input type="text" class="form-control" id="course_time">
					  <span class="input-group-addon" id="basic-addon2">分钟</span>
					</div>
			  		 
			  		</div><br>
			  		
			  		
			  	<!-- 	<div>标牌价格:
			  		  
			  		<div class="input-group" style="width: 200px;">
					  <input type="text" class="form-control" id="price">
					  <span class="input-group-addon" id="basic-addon2">元</span>
					</div></div><br>
			  		
			  		<div>促销价:&nbsp;&nbsp;
			  		<div class="input-group" style="width: 200px;">
					  <input type="text" class="form-control" id="sale_price">
					  <span class="input-group-addon" id="basic-addon2">元</span>
					</div>
			  		
			  		</div> -->
			  		<div>是否免费:  
			  			
			  		<select id="select_id" class="form-control" style="width: 200px;">
			  		<option value="1" >收费</option>
			  		<option value="-1"  >免费</option>
			  		</select>
			  		</div>
			  
			  
			   <div  style="margin-top:30px;" >
             
            <button type="button" class="btn btn-primary" id="rolecommit" style="background-color: green;border-color: green;"> 提交</button>
         </div>
			   </div>
  
  
    
    
    
    <div id="img_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 360px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">课程图片</h4>
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


<div id="img_page2" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 360px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">分享使用的图片</h4>
      </div>
      <div class="modal-body">
         	<form id="art_form2">
         		<input type="file" name="up_img_input2" id="up_img_input2">
         	</form>
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="uploadPic2();">确定</button>
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<button type="button" class="btn btn-default" style="width: 120px;" 
      		data-dismiss="modal" aria-label="Close">关闭</button>
      </div>
    </div> 
  </div> 
</div> 
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
  	<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
  		<script type="text/javascript"
	src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.jplayer.min.js"></script>
  <script type="text/javascript">
  
  $("#rolecommit").click(function(){
	   
		var c_name=$("#uname").val();
		var type="";
		
		var radio2=document.getElementsByName("radion");
			
			for(var i=0;i<radio2.length;i++){
				
				if(radio2[i].checked){
					 type=radio2[i].value;
				}
			}
		
		//var introduce=$("#introduce").val();
		var introduce2=$("#introduce2").val();
		var teacher=$("#teacher").val();
	 	//var img =$("#img1").val();
		 
		
		var text = ue3.getContent(); 
		
		var text_text= ue3.getContentTxt();
		
		var cs_type=$("#cs_type").val().trim();
		
		var ziyuan=$("#ziyuan").val().trim();
		 
		var audition=$("#course_time").val();  
		var t = document.getElementById("select_id"); 
		var  is_free2=t.options[t.selectedIndex].value;
		 
		var tt=document.getElementById("year_c");
		var safe_year=tt.options[tt.selectedIndex].value;
		
		var base_watch=$("#base_watch").val().trim();
		
		var price=0;
		var discount_price=0;
		var safe_date=$("#yx_xx").val();
		
		//var is_free2=$("#is_free2").val();
		var img =$("#img_input").val();
		
		var img2 =$("#img_input2").val();
		if(is_free2==null||is_free2==""){
			$("#is_free2").tips({
				side : 2,
				msg : '是否免费不能为空',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		
		if(safe_date==null||safe_date==""){
			$("#yx_xx").tips({
				side : 2,
				msg : '有效期不能为空',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		
		if(c_name==null||c_name==""){
			$("#uname").tips({
				side : 2,
				msg : '请填写课程名',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		
		if(cs_type==null||cs_type==""){
			$("#cs_type").tips({
				side : 2,
				msg : '请填课程类型',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		 
		
		/* if(introduce==null||introduce==""){
			$("#introduce").tips({
				side : 2,
				msg : '请填写课程简介',
				bg : '#ff0000',
				time : 3
			});
			return false;
		} */
		
		if(teacher==null||teacher==""){
			$("#teacher").tips({
				side : 2,
				msg : '请填写主讲',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		/* if(time==null||time==""){
			$("#time").tips({
				side : 2,
				msg : '请填写视频时长',
				bg : '#ff0000',
				time : 3
			});
			return false;
		} */
		if(text==null||text==""){
			$("#textarea1").tips({
				side : 2,
				msg : '请填写课程介绍',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		if(ziyuan==null||ziyuan==""){
			$("#ziyuan").tips({
				side : 2,
				msg : '关联的资源不能为空',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		
		if(base_watch==null||base_watch==""){
			$("#base_watch").tips({
				side : 2,
				msg : '基础点击量不能为空',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		
		if(audition==null||audition==""){
			$("#course_time").tips({
				side : 2,
				msg : '请设置试听时间',
				bg : '#ff0000',
				time : 3
			});
			return false;
		}
		/* if(price==null||price==""){
			$("#price").tips({
				side : 2,
				msg : '请填写价格',
				bg : '#ff0000',
				time : 3
			});
			return false;
		} */
		  
		$.ajax({
			type:"POST",
			url:getRootPath() +'/course/insertcourse.do',
			data:{'c_name':c_name,'type':type,'text':text,'ziyuan':ziyuan,'audition':audition,'price':price,'discount_price':discount_price,'introduce':"",'introduce2':introduce2,'teacher':teacher,'is_free':is_free2,'img':img,'img2':img2,'safe_date':safe_date,'safe_year':safe_year,"text_text":text_text,'cs_type':cs_type,"base_watch":base_watch},
			dataType:"json",
			success:function(data){
				 
				if("success"==data[0].result){
				 
					window.location.href=getRootPath()+'/course/show_course.do';
					
				}else {
					$("#rolecommit").tips({
						side : 2,
						msg : '请填写价格',
						bg : '#ff0000',
						time : 3
					});
				 
				}
		}
		
			});
		
});
  
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
						
						$("#new_img").attr("src",pt_path+data);
						$("#img_input").val(pt_path+data);
						
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
  
  
  function uploadPic2() {
		var file = $("#up_img_input2").val();
		if(file == ""){
			createBbg("图片不能为空！");
			return;
		}
		 
		var formData = new FormData($("#art_form2" )[0]);
		  
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
						
						$("#new_img2").attr("src",pt_path+data);
						$("#img_input2").val(pt_path+data);
						
						$("#img_but2").hide(); 
						$("#new_img2").show();
						
						$("#img_page2").modal({
							keyboard : false
						}); 
						$("#img_page2").modal('hide');
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
  
  
  function add_img2(){
		
		$("#img_page2").modal({
			keyboard : false
		}); 
		$("#img_page2").modal('show');
		
		$("#up_img_input2").trigger('click');
	}
  </script>
</html>
