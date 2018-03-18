<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <base href="<%=basePath%>">
    
    <title> 添加小课题</title>
    
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/tanchu.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath%>css/login/button.css?v=3">

  </head>
  
  <body>
     	  <div class="modal-body" style="text-align:left;">
			<div>小课题名称:&nbsp;&nbsp;&nbsp;
			
			<input class="form-control" style="width:500px;"  type="text" id="special_name" /></div><br>
			
			 
			
		
			
			 
			
			 <div   style="display: none;"  id="dis_play" >
			 <div>	
	    	<a id="idcardfaceIMGid"><img id="img2" name="nameimg2" src=""  style="width: 108px;height: 108px; visibility: hidden;"   /></a>
	   </div>
	   	<input type="hidden" id="img_url2" name="img_url2"value=""/>
	   	</div>
			 <!-- <div  style="text-align: center;">
			  <div style=" float: left;font-size:15px;" >  专题图片</div>
			  <div 	 > <a href="javascript:up_img(2);" class="sel_btn">上传图片</a></div>
			  </div><br> -->
			  
			 小课题的图片：
			  <button type="button"  id="img_but" class="btn btn-primary"  style="width: 100px;" onclick="add_img();">添加图片</button>
  				 <img  id="new_img" src="" onclick="add_img();" style="width: 300px;display: none; border: 1px solid #ddd;">
  				 <input type="hidden" id="img_input" ><br>
  				 
  				 
  				    分享的图片：
			  <button type="button"  id="img_but2" class="btn btn-primary"  style="width: 100px;" onclick="add_img2();">添加图片</button>
  				 <img  id="new_img2" src="" onclick="add_img2();" style="width: 300px;display: none; border: 1px solid #ddd;">
  				 <input type="hidden" id="img_input2" ><br>		
  				 
  				 
  				 	 购买前显示的图片：
			  <button type="button"  id="img_but3" class="btn btn-primary"  style="width: 100px;" onclick="add_img3();">添加图片</button>
  				 <img  id="new_img3" src="" onclick="add_img3();" style="width: 300px;display: none; border: 1px solid #ddd;">
  				 <input type="hidden" id="img_input3" ><br>	
  				 
			  		
			  		<div>小课题介绍:
			  		<textarea class="form-control" rows="3" style="width: 400px;" id="special_textarea1"></textarea>
			  		</div><br>
			  		
			  		
			  		<div>分享使用的摘要:
			  		<textarea   id="introduce2" class="form-control" rows="3" style="width: 400px;"  ></textarea>
			  		</div><br>
			  		
			  		
			  		
			  	<div>分享使用的标题:
			  		<input type="text" id="share_title" class='form-control' style='width: 200px;'>
			  	</div><br> 
			  		 
          			
				<div> 
			  		<button onclick="adddiv();"      class="btn btn-primary">增加视频资源</button>
			  		<div id="appen_div" style="text-align: center;">
				  		<div class='input-group' style='margin-top:10px;width: 200px;'>
				  		 	<input type='text' name='input_name'  class='form-control' style='width: 200px;' placeholder="请输入视频ID">
	  	 					<span class='input-group-addon' onclick='delete_content(this);' >删除</span>
	  	 				 </div>
					</div>
			  		 
			  		</div><br> 
			  		
			  		<!-- 	<div> 
			  		<div>
			  		<button onclick="adddiv();"      class="btn btn-primary">增加视频资源</button>
			  		</div>
			  		
			  		<div id="appen_div" style="text-align: left;">
				  	
					</div>
			  		 
			  		</div><br> -->
			  		
			  		
			  	<!-- 	<div> 
			  		<button onclick="adddiv2();"      class="btn btn-primary">增加音频资源</button>
			  		
				  	
					</div> -->
			  		 
			  		</div><br>
			  		
			  		<div >试听时长:
			  		<div class="input-group" style="width: 200px;">
					  <input type="text" class="form-control" id="special_time">
					  <span class="input-group-addon" id="basic-addon2">分钟</span>
					</div></div><br>
					
					
					<div>购买有效期:  
			  			
			  		<select id="year_c" class="form-control" style="width: 200px;">
			  		<option value="1" >1年</option>
			  		<option value="2"  >2年</option>
			  		<option value="3"  >3年</option>
			  		<option value="4"  >4年</option>
			  		<option value="5"  >5年</option>
			  		</select>
			  		</div><br>
					
					<div>标牌价格:
					<div class="input-group" style="width: 200px;">
					  <input type="text" class="form-control" id="special_price">
					  <span class="input-group-addon"  >元</span>
					</div></div><br>
			  		
			  		<!-- <div>促销价格:
			  		<div class="input-group" style="width: 200px;">
					  <input type="text" class="form-control" id="special_sale_price">
					  <span class="input-group-addon"  >元</span>
					</div></div>
					<br> -->
			  		 
			  		
			  		 
			  		<input type="hidden" id="is_free" value="1">
			  			<!-- <div>是否免费:  
			  			
			  		<select id="is_free" class="form-control" style="width: 200px;">
			  		<option value="1" >收费</option>
			  		<option value="-1"  >免费</option>
			  		</select>
			  		 
			  		</div> -->
			  	
			  		<div>主讲老师:
			  					<input type="text" id="teacher" class='form-control' style='width: 200px;'>
			  			</div><br>
			  
			  		<div>适宜人群:
			  		<textarea class="form-control" rows="3" style="width: 400px;" id="fit_people"></textarea>
			  					
			  			</div><br>
			  			
			  			<div>订购须知:
			  			<textarea class="form-control" rows="3" style="width: 400px;" id="order_notice"></textarea>
			  					
			  			</div><br>
			  			
			  			
			  			<div>基础订购人数:
			  					<input type="text" id="base_order_count" class='form-control' style='width: 200px;'>
			  			</div><br>
			  			
			  			
			  
			  <div  style="margin-top:50px;">
           
            <button type="button" class="btn btn-primary"  onclick="special_com();" style="background-color: green;border-color: green;"> 提交</button>
        </div>
			   
			   
			   
			   
	<div id="img_page" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 360px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">上传图片</h4>
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



<div id="img_page3" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width: 360px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">购买使用的图片</h4>
      </div>
      <div class="modal-body">
         	<form id="art_form3">
         		<input type="file" name="up_img_input3" id="up_img_input3">
         	</form>
      </div>
      <div style="height: 80px; line-height: 80px; text-align: center;">
      		<button type="button" class="btn btn-primary" style="width: 120px;" onclick="uploadPic3();">确定</button>
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
  	<script type="text/javascript" src="<%=basePath %>js/jquery.jplayer.min.js"></script>
  	<script type="text/javascript">
  	
  	function adddiv(){
  	  	var html= "<div class='input-group' style='margin-top:10px;width: 200px;'> <input type='text' name='input_name' placeholder='请输入视频ID' class='form-control' style='width: 200px;'>"
  	  	 	+" <span class='input-group-addon' onclick='delete_content(this);' >删除</span> </div>";
  	  			$("#appen_div").append(html);
  	  			 
  	  			 
  	}
  	
  	
  /* 	function adddiv(){
  		$.ajax({
 			type : "post", 
 			url :pt_path+"course/to_choose_course.do",			
 			dataType: 'json',  			 
 			success : function(data) 
 			{	
 				
 				var course=data[0].course_list;
 				
 				var html="";
 				
 				if(course.length>0){
 					
 					for(var i=0;i<course.length;i++){
 						
 						html+="<div><input type='checkbox' id='checkbox_a1' class='chk_1' value='"+course[i].c_no+"' name='cheackbox_1' type2='"+course[i].type+"' />"
 						+"<label for='checkbox_a1'  >"+course[i].c_name+"</label> </div>";
 					 
 					}
 					$("#appen_div").html(html);
 					
 				}
 				
 				
 				
 			},
 			
 		});	
  			
  		
  	} */
  	
  	
  	function adddiv2(){
		
  		$.ajax({
 			type : "post", 
 			url :pt_path+"course/to_choose_viedo.do",			
 			dataType: 'json',  			 
 			success : function(data) 
 			{	
 				
 				var course=data[0].course_list;
 				
 				var html="";
 				
 				if(course.length>0){
 					
 					for(var i=0;i<course.length;i++){
 						
 						html+="<div><input type='checkbox' id='checkbox_a1' class='chk_1'  name='cheackbox_1' value='"+course[i].c_no+"' type2='"+course[i].type+"' />"
 						+"<label for='checkbox_a1'  >"+course[i].c_name+"</label> </div>";
 					 
 					}
 					$("#appen_div").html(html);
 					
 				}
 				
 				
 				
 			},
 			
 		});	
  			 
  			 
  		
  	}

  	function delete_content(obj){
  		
  		$(obj).closest("div").remove();
  	}
  	
  	function special_com() {
  		 var  special_name=$("#special_name").val();
  		// var  img_url2=$("#img_url2").val();  
  		 //var  special_ziyuan=$("#special_ziyuan").val();  	 
  		 var  special_textarea1=$("#special_textarea1").val().replace(/[\r\n]/g,"");
  		 var  special_time=$("#special_time").val();
  		 var  special_price=$("#special_price").val();
  	//	 var  special_sale_price=$("#special_sale_price").val();
  		 
  			var img3 =$("#img_input3").val();
		 
  		var img2 =$("#img_input2").val();
  		var introduce2=$("#introduce2").val();
  		//var t = document.getElementById("is_free"); 
		var  is_free=$("#is_free").val();
		var tt=document.getElementById("year_c");
		var safe_year=tt.options[tt.selectedIndex].value;
  		 var teacher=$("#teacher").val();
  		 var fit_people =$("#fit_people").val().trim();
  		 var order_notice=$("#order_notice").val().trim();
  		var img =$("#img_input").val();
  	//	var check_value=document.getElementsByName('cheackbox_1');   
  		var base_order_count =$("#base_order_count").val();
  		
  		var share_title=$("#share_title").val();
  		
  		
  		
  				//var type = "";
			
			//var course_list="";
			
		/* 	for(var k=0;k<check_value.length;k++){
				if(check_value[k].checked){
					
					course_list+=check_value[k].value+',';
					type= $(check_value[k]).attr("type2");
				}
				
			} */
			
			
			 var cont_content=""; 			
	  			$("input[name='input_name']").each(function(){
	  				cont_content+=$(this).val()+",";
	  				});
		
		
			/* 
			if(course_list==null||course_list==""){
				$("#sub_butt").tips({
					side : 2,
					msg : '请添加视频或者音频',
					bg : '#ff0000',
					time : 3
				});
				return false;
			} */
  		 
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
  					msg : '请填写适宜人群',
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
  		 
  		/* 	if(img_url2==null||img_url2==""){
  				$("#img_url2").tips({
  					side : 2,
  					msg : '请上传图片',
  					bg : '#ff0000',
  					time : 3
  				});
  				return false;
  			} */
  			if(special_textarea1==null||special_textarea1==""){
  				$("#special_textarea1").tips({
  					side : 2,
  					msg : '请填写专题介绍',
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
  			
  			
  			if(base_order_count==null||base_order_count==""){
  				$("#base_order_count").tips({
  					side : 2,
  					msg : '请填写基础订阅人数',
  					bg : '#ff0000',
  					time : 3
  				});
  				return false;
  			}
  			
  			
  			$.ajax({
  				type:"POST",
  				url:getRootPath() +'/course/insert_little_special.do',
  				data:{'special_name':special_name ,'special_textarea1':special_textarea1,'special_time':special_time,'special_price':special_price,'special_sale_price':'','is_free':is_free,'teacher':teacher,'fit_people':fit_people,'order_notice':order_notice,"img":img,'safe_year':safe_year,"img2":img2,"introduce2":introduce2,"cont_content":cont_content,"type":"","base_order_count":base_order_count,'share_title':share_title,"img3":img3},
  				dataType:"json",
  				success:function(data){
  					if("success"==data[0].result){

  						 $("#special").modal("hide");
  						window.location.href=getRootPath()+'/course/show_little_special.do';
  						
  					} 
  			}
  			
  				});
  		 
  	}
  	
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
   
   
   function uploadPic3() {
		var file = $("#up_img_input3").val();
		if(file == ""){
			createBbg("图片不能为空！");
			return;
		}
		 
		var formData = new FormData($("#art_form3" )[0]);
		  
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
						
						$("#new_img3").attr("src",pt_path+data);
						$("#img_input3").val(pt_path+data);
						
						$("#img_but3").hide(); 
						$("#new_img3").show();
						
						$("#img_page3").modal({
							keyboard : false
						}); 
						$("#img_page3").modal('hide');
					}
				}
			},
			
		});	
		 
	}
   
   
   function add_img3(){
		
		$("#img_page3").modal({
			keyboard : false
		}); 
		$("#img_page3").modal('show');
		
		$("#up_img_input3").trigger('click');
	}
  	</script>
</html>
