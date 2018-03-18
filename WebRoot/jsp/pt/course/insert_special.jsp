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
    
    <title> 添加专题</title>
    
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/tanchu.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath%>css/login/button.css?v=3">

  </head>
  
  <style type="text/css">
  
  .chk_1 + label { 
    background-color: #FFF; 
    border: 1px solid #C1CACA; 
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05); 
    padding: 9px; 
    border-radius: 5px; 
    display: inline-block; 
    position: relative; 
    margin-right: 30px; 
} 
  
  </style>
  
  <body>
     	  <div class="modal-body" style="text-align:left;">
			<div>专题名称:&nbsp;&nbsp;&nbsp;
			
			<input class="form-control" style="width: 500px;"  type="text" id="special_name" /></div><br>
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
			  
			    专题的图片：
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
  				 
			  		
			  		<div>专题介绍:
			  		<textarea class="form-control" rows="3" style="width: 400px;" id="special_textarea1"></textarea>
			  		</div><br>
			  		
			  		
			  		<div>分享使用的摘要:
			  		<textarea   id="introduce2" class="form-control" rows="3" style="width: 400px;"  ></textarea>
			  		</div><br>
			  		
			  		
			  		 <div>分享使用的标题:
			  		<input type="text" id="share_title" class='form-control' style='width: 200px;'>
			  	</div><br> 
          			
			  		<div> 
			  		<div>
			  		<button onclick="adddiv();"      class="btn btn-default" id="btt_1">增加视频资源</button>
			  		<button onclick="adddiv2();"      class="btn btn-default" id="btt_2">增加音频资源</button>
			  		</div>
			  		
			  		<div id="appen_div" style="text-align: left;">
				  	
					</div>
			  		 
			  		</div><br>
			  		
			  		
			  		<!-- <div> 
			  		
			  		
				  	<button onclick="adddiv2();"      class="btn btn-primary">增加音频资源</button>
					</div>
			  		 
			  		</div><br> -->
			  		
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
			  		
			  	<!-- 	<div>促销价格:
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
			  		<textarea   id="fit_people" class="form-control" rows="3" style="width: 400px;"  ></textarea>
			  					
			  			</div><br>
			  			
			  			<div>订购须知:
			  			<textarea   id="order_notice" class="form-control" rows="3" style="width: 400px;"  ></textarea>
			  					
			  			</div><br>
			  			
			  				<div>基础订阅人数:
			  					<input type="text" id="base_order_count" class='form-control' style='width: 200px;'>
			  			</div><br>
			  			
			  			<div>广告视频ID:
			  		<input type="text" id="gg_id" class='form-control' style='width: 200px;'>
			  		</div><br>
			  		
			  		
			  		<div>是否更新完成:  
			  			
			  		<select id="is_wanc" class="form-control" style="width: 200px;">
			  		<option value="-1" >否</option>
			  		<option value="1" >是</option>
			  		
			  		</select>
			  		 
			  		</div> 		
			  			
			  
			  <div  style="margin-top:50px;">
           
            <button type="button" class="btn btn-primary"  onclick="special_com();" style="background-color: green;border-color: green;" id="sub_butt"> 提交</button>
        </div>
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

<!-- 添加框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 500px;">
      <div class="modal-content">
         <div class="modal-header" style="background-color: #428bca;">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" style="color: #fff;">&times;</span>
            </button>
           
         </div>
         <div class="modal-body" style="text-align:left;">		
			
			<h5>课程类型</h5>
			<div  id='to_type'>						
			 </div>
		 
			  
			   </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button"  id="book_1"  class="btn btn-primary" onclick="sub_coursetp();" style="background-color: #428bca;border-color: #428bca;"> 提交</button>
           <button type="button"  id="book_2" class="btn btn-primary" onclick="sub_coursetp2();" style="background-color: #428bca;border-color: #428bca;"> 提交</button>
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
  	
  	 
  	
  	$(function (){
  		
  		$("#book_1").hide();
  		$("#book_2").hide();
  		
  	});
  	
  	function adddiv(){
  	/* 	var html= "<div class='input-group' style='margin-top:10px;width: 200px;'> <input type='text' name='input_name' placeholder='请输入视频ID' class='form-control' style='width: 200px;'>"
  	 	+" <span class='input-group-addon' onclick='delete_content(this);' >删除</span> </div>";
  			$("#appen_div").append(html);
  			 */
  			 
  			 
  			$("#btt_2").attr("class","btn btn-default");
  			$("#btt_1").attr("class","btn btn-primary"); 
  			
  			 
  			$.ajax({
  	 			type : "post", 
  	 			url :pt_path+"course/show_course_type.do",			
  	 			dataType: 'json',  			 
  	 			success : function(data) 
  	 			{	
  	 				
  	 				var course_type=data[0].course_type_list;
  	 				
  	 				var html="";
  	 				
  	 				if(course_type.length>0){
  	 					
  	 					for(var i=0;i<course_type.length;i++){
  	 						
  	 		html+="<div > <input style='padding:0;margin-left: 20px;' type='radio' value='"+course_type[i].type+"' name='radion'/>"+course_type[i].type+"</div>";
  	 					 
  	 					}
  	 					$("#to_type").html(html);
  	 					
  	 				}
  	 				 
  	 			},
  	 			
  	 		});	
  			$("#book_1").show();
  			$("#book_2").hide();
  			$("#myModal").modal({
  				keyboard : false
  			}); 
  			$("#myModal").modal('show');
  			 
  	}
  	
  	
	function adddiv2(){
		
		$("#btt_1").attr("class","btn btn-default");
			$("#btt_2").attr("class","btn btn-primary"); 
			
			
			$.ajax({
  	 			type : "post", 
  	 			url :pt_path+"course/show_course_type_voice.do",			
  	 			dataType: 'json',  			 
  	 			success : function(data) 
  	 			{	
  	 				
  	 				var course_type=data[0].course_type_list;
  	 				
  	 				var html="";
  	 				
  	 				if(course_type.length>0){
  	 					
  	 					for(var i=0;i<course_type.length;i++){
  	 						
  	 		html+="<div > <input style='padding:0;margin-left: 20px;' type='radio' value='"+course_type[i].type+"' name='radion'/>"+course_type[i].type+"</div>";
  	 					 
  	 					}
  	 					$("#to_type").html(html);
  	 					
  	 				}
  	 				 
  	 			},
  	 			
  	 		});	
  			
			$("#book_2").show();
			$("#book_1").hide();
  			$("#myModal").modal({
  				keyboard : false
  			}); 
  			$("#myModal").modal('show');
			
		 
	  	}

  	function delete_content(obj){
  		
  		$(obj).closest("div").remove();
  	}
  	
  	function special_com() {
  		 var  special_name=$("#special_name").val();
  		  
  		 var  special_textarea1=$("#special_textarea1").val().replace(/[\r\n]/g,"");
  		 var  special_time=$("#special_time").val();
  		 var  special_price=$("#special_price").val();
  		// var  special_sale_price=$("#special_sale_price").val();
  		 
  		var img2 =$("#img_input2").val();
  		
  		var img3 =$("#img_input3").val();
  		var introduce2=$("#introduce2").val();
  		//var t = document.getElementById("is_free"); is_wanc
  		var   wancheng=document.getElementById("is_wanc");
  		var is_wc=wancheng.options[wancheng.selectedIndex].value;
		var  is_free=$("#is_free").val();
		var tt=document.getElementById("year_c");
		var safe_year=tt.options[tt.selectedIndex].value;
  		 var teacher=$("#teacher").val();
  		 var fit_people =$("#fit_people").val().trim();
  		 var order_notice=$("#order_notice").val().trim();
  		var img =$("#img_input").val();
  		var base_order_count =$("#base_order_count").val();
  		
  		var gg_id=$("#gg_id").val().trim();
  		
  		var share_title=$("#share_title").val();
  		
  		
  /* 		 var cont_content=""; 			
  			$("input[name='input_name']").each(function(){
  				cont_content+=$(this).val()+",";
  				}); */
  			
  				//var type=$("input").attr("type");
  			
  			var check_value=document.getElementsByName('cheackbox_1');
  			
  			var number_1=document.getElementsByName('number_1');
  			
  			
  			
  			
  				var course_list="";
  				var type = "";
  				
  				for(var k=0;k<check_value.length;k++){
  					if(check_value[k].checked){
  						
  						course_list+=check_value[k].value+',';
  						type= $(check_value[k]).attr("type2");
  					}
  					
  				}
  			
  				
  				var no_list=""; 				
  				
  				for(var kk=0;kk<number_1.length;kk++){
  					if(number_1[kk].value==null||number_1[kk].value==""){
  						
  						alert("请输入排序");
  						return false;
  					}  	
  					no_list+=number_1[kk].value+','; 		
  				
  				}
  			
  				
  				
  				if(no_list==null||no_list==""){
  	  				$("#sub_butt").tips({
  	  					side : 2,
  	  					msg : '请输入顺序',
  	  					bg : '#ff0000',
  	  					time : 3
  	  				});
  	  				return false;
  	  			}
  				
  				if(base_order_count==null||base_order_count==""){
  	  				$("#sub_butt").tips({
  	  					side : 2,
  	  					msg : '请输入基础订阅人数',
  	  					bg : '#ff0000',
  	  					time : 3
  	  				});
  	  				return false;
  	  			}
  				
  				
  				if(course_list==null||course_list==""){
  	  				$("#sub_butt").tips({
  	  					side : 2,
  	  					msg : '请添加视频或者音频',
  	  					bg : '#ff0000',
  	  					time : 3
  	  				});
  	  				return false;
  	  			}
  			
  		 
  				if(gg_id==null||gg_id==""){
  	  				$("#sub_butt").tips({
  	  					side : 2,
  	  					msg : '广告视频ID不能为空!',
  	  					bg : '#ff0000',
  	  					time : 3
  	  				});
  	  				return false;
  	  			}
  				
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
  		 
  			$.ajax({
  				type:"POST",
  				url:getRootPath() +'/course/insertspecial.do',
  				data:{'special_name':special_name ,'special_textarea1':special_textarea1,'special_time':special_time,'special_price':special_price,'special_sale_price':'','is_free':is_free,'teacher':teacher,'fit_people':fit_people,'order_notice':order_notice,"img":img,'safe_year':safe_year,"img2":img2,"introduce2":introduce2,"course_list":course_list,"type":type,"base_order_count":base_order_count,"gg_id":gg_id,"share_title":share_title,"no_list":no_list,"img3":img3,"is_wc":is_wc},
  				dataType:"json",
  				success:function(data){
  					if("success"==data[0].result){

  						 $("#special").modal("hide");
  						window.location.href=getRootPath()+'/course/show_special.do';
  						
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
   
   
   function add_img3(){
		
		$("#img_page3").modal({
			keyboard : false
		}); 
		$("#img_page3").modal('show');
		
		$("#up_img_input3").trigger('click');
	}
   
   function  sub_coursetp(){
	   
	   var type="";
		
		var radio2=document.getElementsByName("radion");
			
			for(var i=0;i<radio2.length;i++){
				
				if(radio2[i].checked){
					 type=radio2[i].value;
				}
			}
		
			
 	 	$.ajax({
			type : "post", 
			url :pt_path+"course/to_choose_course.do",		
			data:{"c_type":type},
			dataType: 'json',  			 
			success : function(data) 
			{	
				
				$("#myModal").modal({
	  				keyboard : false
	  			}); 
	  			$("#myModal").modal('hide');
	  			
				var course=data[0].course_list;
				
				var html="";
				
				if(course.length>0){
					
					for(var i=0;i<course.length;i++){
						
						html+="<div><input type='checkbox' id='checkbox_a1' class='chk_1' value='"+course[i].c_no+"' name='cheackbox_1' type2='"+course[i].type+"' />"
						+"<label for='checkbox_a1'  >"+course[i].c_name+"&nbsp;"+course[i].date+"</label> <input type='text'  name='number_1' style='width:30px;height: 34px;border: 1px solid #cccccc;border-radius: 4px;' placeholder='NO.?'></div>";
					 
					}
					$("#appen_div").html(html);
					
				}else{
 					
 					html+="<div>"
						+" 该类型暂无视频</div>";
 					$("#appen_div").html(html);
 				}
				  
			},
			
		});	 
	   
   }
   
   
function  sub_coursetp2(){
	   
	   var type="";
		
		var radio2=document.getElementsByName("radion");
			
			for(var i=0;i<radio2.length;i++){
				
				if(radio2[i].checked){
					 type=radio2[i].value;
				}
			}
		
			
			$.ajax({
	 			type : "post", 
	 			url :pt_path+"course/to_choose_viedo.do",		
	 			data:{'c_type':type},
	 			dataType: 'json',  			 
	 			success : function(data) 
	 			{	
	 				
	 				$("#myModal").modal({
		  				keyboard : false
		  			}); 
		  			$("#myModal").modal('hide');
	 				
	 				var course=data[0].course_list;
	 				
	 				var html="";
	 				
	 				if(course.length>0){
	 					
	 					for(var i=0;i<course.length;i++){
	 						
	 						html+="<div><input type='checkbox' id='checkbox_a1' class='chk_1'  name='cheackbox_1' value='"+course[i].c_no+"' type2='"+course[i].type+"' />"
	 						+"<label for='checkbox_a1'  >"+course[i].c_name+"&nbsp;"+course[i].date.substring(0,10)+"</label><input type='text'  name='number_1' style='width:30px;height: 34px;border: 1px solid #cccccc;border-radius: 4px;' placeholder='NO.?'> </div>";
	 					 
	 					}
	 					$("#appen_div").html(html);
	 					
	 				}else{
	 					
	 					html+="<div>"
 						+" 该类型暂无视频</div>";
	 					$("#appen_div").html(html);
	 				}
	 					 				 
	 			},
	 			
	 		});	
	   
   }
   
   
  	</script>
</html>
