
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> 课程管理</title>
    
	 
	 
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/tanchu.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath%>css/login/button.css?v=3">

  </head>
  <style type="text/css">
  .main-table td{
		font-size: 120%;
	}
	
	.sel_btn{
            height: 21px;
            line-height: 21px;
            padding: 0 11px;
            background: #02bafa;
            border: 1px #26bbdb solid;
            border-radius: 3px;
        
            display: inline-block;
            text-decoration: none;
            font-size: 12px;
            outline: none;
        }
        
 
.but_page{ 
	background-color: #5CB85C;
	color: #fff;
	border-radius:15px;
	width: 80px;
	height: 30px;
	line-height: 30px;
	border: none;
}

 .styled-select select {
      background: transparent;
      width: 268px;
      padding: 5px;
      font-size: 16px;
      border: 1px solid #ccc;
      height: 34px;
      -webkit-appearance: none; /*for chrome*/
    }
</style>
  
 
  
  <body>
     	<div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  ">
  	课程详情
  	</div>
     
     
     <div  style="width: 94%; margin-left: 3%;text-align: right; margin-top: 20px; margin-bottom: 10px;">
  			 <a  class="btn btn-success"      onclick="insert_aa();"  >+新增课程</a>
  		 
  	 </div> 
  	  
  		<div class="input-group"  style="width: 600px; position: absolute;top:9%;left:3%;height: 36px;"> 
  			
  	 		 
  	 	<span class="input-group-btn form-control"  style="width: 120px;height: 36px; ">
  	 		<select  id="choose_id">
  	 		
  				<option value="3" >课程名称</option>
  				<option value="1" >视频课程</option>
  				<option value="0" >语音课程</option>
  				<option value="-1" >免费课程</option>
  				<%-- <c:if test='${course_list.list.type=="1"}'>selected</c:if> --%>
  			</select>
  	 	
  	 	 </span>
		  <input type="text" id="search_input" value="${input}" class="form-control"  placeholder="请输入搜索信息" maxlength="128"  style="height: 36px; " >
		  
		  
		  <span class="input-group-btn" style="height: 36px; "> 
		  	 <button type="button" class="btn btn-primary" style="width: 80px;height: 36px; " onclick="go_search();">搜索</button>
		  </span>
		</div>
  	 
  	 
     <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    	 
    		<td  >课程编号</td>
    		<td   >课程名称</td>
    		 <td >分类</td>
    		  <td >主讲</td> 
    		 <td>真实点击量</td>
    		 <td >状态</td>
    		<!-- <td>网址链接</td> -->
    		<td  >操作</td>
    		</tr>
    		<c:forEach items="${course_list.list}" var="course" varStatus="status"> 
    				<tr>
    					 
    					<td   id="userid">${course.c_no }</td>
    					<td  >${course.c_name }</td>
    					 
    					<td>
    					<c:if test="${course.type==0}">音频</c:if>
    					<c:if test="${course.type==1}">视频</c:if>
    					 </td>
    					 <td  >${course.teacher }</td>
    					<%-- <td  >
    					
    				<fmt:formatNumber type="number" value="${course.price/100}" pattern="0.00" maxFractionDigits="2"/>元
    				 	
    					</td> --%>
    					
    					<td  >
    					<c:if test="${ not empty course.base_watch}" >
    					
    					${course.watch-course.base_watch}
    					</c:if> 
    					 
    					 <c:if test="${empty course.base_watch}" >
    					
    					${course.watch}
    					</c:if>
    					
    					
    					</td>
    					<td   onclick="blacklist('${course.c_no}','${course.state}');" style="cursor: pointer;">
    					<c:if test="${course.state==1}">已上架</c:if>
    					<c:if test="${course.state==-1}">已下架</c:if>
    					</td>
    					<%--   <td>
    					  <a href="${course.url}"  target="_blank">${course.c_name }</a>
    					  </td> --%>
    				<td height="30">
    					<%--  <a href="javascript: deletebyid({c_no:'${course.c_no}',name:'${course.c_name}'})">删除</a> --%> 
    					<%-- <a href="javascript: updaterole({c_no:'${course.c_no}',img:'${course.img}'})" >修改</a> --%>
    					<a href="<%=basePath %>course/courseinfo.do?c_no=${course.c_no}" >详情</a>
    					<a href="<%=basePath %>course/update_cc.do?c_no=${course.c_no}" >修改</a>
    					 <a href="<%=basePath %>article/query_exercise.do?c_no=${course.c_no}" >课程试题</a> 
    					<%-- <a href="javascript: to_have({c_no:'${course.c_no}'})" >课程详情地址</a> --%>
    					</td>	  
    					  
    				</tr>
    		 <input type="hidden" id="skuid" value="${course.c_no }">
    		  <input type="hidden" id="stateid" value="${course.state }">
    		 <%--  <input type="hidden" id="course_id" value="${course}"> --%>
    		</c:forEach>
    		</table>
  
  
    <div class="page">
<div style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 14px;">

									<span>共<b style="color:#666">${course_list.rowCount}</b>条</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
										style="color:#666">第${course_list.pageNum
										}/${course_list.pageCount}页</span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button"  class="but_page"
										id="prePage">上一页</button>
									&nbsp;&nbsp;&nbsp;
									<button type="button" class="but_page"  
										id="nextPage">下一页</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 跳转到
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<form action="<%=basePath%>/course/show_course.do"
										method="post" style="float:right" id="pageform">
										<input type="text" name="page" id="pninfo"
											style="width:60px;height:30px;border: 1px solid #5CB85C; text-align: center; font-size: 16px;" onkeyup="priceKeyup(this)"
											maxval="${course_list.pageCount}" value="${course_list.pageNum}">
										&nbsp;&nbsp;&nbsp; <input type="hidden" id="pageCount"
											value="${course_list.pageCount}">
										 <%-- <input
											type="hidden" name="mindate" value="${map.mindate }">
										<input type="hidden" name="maxdate" value="${map.maxdate }">

										
										<input type="hidden" name="ps" value="${map.ps }"> --%>
										
										 <input type="button"
											value="GO"class="but_page" id="goPage">
									</form>
								</div>
</div>  		
    		
   
    <div class="clear"></div>  		
    		
    		
 
    
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
            <h4 class="modal-title">课程轮播链接地址</h4>
         </div>
         <div class="modal-body"  style="text-align: center;" >
            <input type="text" value=""  id="c_no_val"  style="width: 550px;">
          </div>
      </div> 
</div> 
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
         
         <h5>课程编号</h5>			
		<input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text"  disabled="disabled" id="show_cno" value=" "/><br> 
   				
   				
   				 <h5>图片</h5>
			  <button type="button"  id="img_but" class="btn btn-primary"  style="width: 100px;" onclick="add_img();">添加图片</button>
  				 <img  id="new_img" src="" onclick="add_img();" style="width: 300px;display: none; border: 1px solid #ddd;">
  				 <input type="hidden" id="img_input"  value=""><br>
         
			<h5>课程名称</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatec_name"  value=" "/><br> 
   
			<h5>价格</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updateprice" value=" "/><br>
			
			<h5>促销价格</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatediscount_price" value=" " /><br>
			
			<h5>主讲</h5>
			 <input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updateteacher" value=" "/><br>
			  
			  
			  <div>是否免费:  
			  			
			  		<select id="updateis_freer" class="form-control" style="width: 200px;">
			  		<option value="1" >收费</option>
			  		<option value="-1"  >免费</option>
			  		</select>
			  		<!-- <input type="text"   style="width: 200px;" id="is_free2" placeholder="-1代表免费1收费"  class="form-control">  -->
			  		</div>
			 
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
     
<!-- 添加框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
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
			<div>课程名称:&nbsp;&nbsp;&nbsp;<input style="line-height:10px; padding:0;" type="text" id="uname" /></div><br>
			
			<h5>课程分类</h5>
			<div >
			<div style="float: left;"> <input style="  padding:0;" type="radio"  value="1"  name="radion" checked="checked"/>视频</div>
			<div  > <input style=" padding:0;margin-left: 20px;" type="radio"   value="0" name="radion"/>音频</div>
			 </div><br>
			 <div style="display: none;" id="dis_play1">
			 <div>
	    	<a id="idcardfaceIMGid"><img id="img1" name="nameimg1" src=""  style="width: 108px;height: 108px; visibility: hidden;"   /></a>
	   </div>
	   	<input type="hidden" id="img_url1" name="img_url1"value=""/>
	   	</div>
	   	
	   	
			 
			   <div style="display: none;" id="dis_play2">
			 <div>
			
	    	<div id="idcardfaceIMGid"  >
	    	
	    
      
	    	<video id="vv"   width="160"   controls="controls">
	    		<source src="" id="img3"   type="video/mp4">
	    	</video>
	    	 
	    	 
	    	 
	    	</div>
	   </div>
	   	<input type="hidden" id="img_url3" name="img_url3"value=""/>
	   	</div>
	   		<div  style="text-align: center; display: none;" >
			  <div style=" float: left;font-size:15px;" >  课程视频/音频</div>
			  <div 	 > <a href="javascript:up_view(3);" class="sel_btn">上传视频/音频</a></div>
			  </div><br>
	   	 
			  		 <div   style="display: none;"  id="dis_play" >
			 <div>	
	    	<a id="idcardfaceIMGid"><img id="img2" name="nameimg2" src=""  style="width: 108px;height: 108px; visibility: hidden;"   /></a>
	   </div>
	   	<input type="hidden" id="img_url2" name="img_url2"value=""/>
	   	</div>
			  		<div  style="text-align: center;">
			  <div style=" float: left;font-size:15px;" >  图片</div>
			  <div 	 > <a href="javascript:up_img(2);" class="sel_btn">上传图片</a></div>
			  </div><br>
			  		<div >一句话简介:
			  			<input type="text"  id="introduce">
			  		</div><br>
			  		
			  		<div>第二句介绍:
			  			<input type="text" id="introduce2">
			  		</div><br>
			  			
			  			<div>主讲老师:
			  					<input type="text" id="teacher">
			  			</div><br>
			  			
			  			<div style="display: none;">视频/音频时长:
			  			<input type="text" id="time"> 
			  			
			  			</div><br>
			  			
			  			
			  				
			  		<div>课文介绍:
			  		<textarea rows="6" cols=" 40" id="textarea1">
			  		
			  		</textarea>
			  		</div><br>
			  		
			  		<div>视频id:
			  		<input type="text" width="80px;" id="ziyuan">
			  		</div><br>
			  		<div  >试听时长:
			  		<input type="text"  style="width: 40px;" id="course_time">分钟
			  		</div><br>
			  		<div>标牌价格:
			  		<input type="text"   style="width: 40px;" id="price">元
			  		</div><br>
			  		<div>促销价:&nbsp;&nbsp;
			  		<input type="text"   style="width: 40px;" id="sale_price">元
			  		</div>
			  		<div>是否免费: 
			  		<input type="text"   style="width: 130px;" id="is_free2" placeholder="-1代表免费1收费" > 
			  		</div>
			  
			   </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="rolecommit" style="background-color: green;border-color: green;"> 提交</button>
         </div>
      </div> 
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
			 
			  		<div>专题介绍:
			  		<textarea rows="6" cols=" 40" id="special_textarea1">
			  		
			  		</textarea>
			  		</div><br>
			  		
			  		
			  		 
          			<div id="appen_div" style="text-align: center;">
					</div>
			  		<div>视频ID:
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
     
     <div class="modal fade" style=" position: fixed; "  id="add_sgoods_upimg_page" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" align="center" aria-hidden="false">
		<div class="modal-dialog" role="document">
			<div class="modal-content"
				style="width:400px;margin:10px ;"align="center">
				<div class="modal-header"> 
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">上传图片</h4>
				</div> 
				<div class="modal-body" >
					<form id="add_sgoods_img_form"> 
					 <div class="form-group" > 
					 	<div class="form-group" align="center"> 
					 		
							  <input type='file' name="add_sgoods_upimg" 
									    id="add_sgoods_upimg"/><br/>
									    <span>(请上传不超过3M的文件)</span><br/>
									     <span>(最佳尺寸450*450，格式支持:jpg/jpeg/png)</span><br/>
									    <span>(点击确定按钮后请耐心等待系统处理图片)</span>
							
						</div> 
					</div>  
					 <div class="form-group" align="center" > 
							<input  type="button" style="width: 120px;" 
							class="btn btn-primary" value="确定" onclick="add_sgoods_upimg_yes();"/> 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input  type="button" style="width: 120px;" 
							class="btn btn-primary" value="取消" data-dismiss="modal"/>
					</div>
					 </form>
				</div>
			</div>
		</div> 
		</div>
     
     <!-- 上传视频模态框 -->
     <div class="modal fade" style=" position: fixed; "  id="add_sgoods_upimg_page2" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" align="center" aria-hidden="false">
		<div class="modal-dialog" role="document">
			<div class="modal-content"
				style="width:400px;margin:10px ;"align="center">
				<div class="modal-header"> 
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">上传视频</h4>
				</div> 
				<div class="modal-body" >
					<form id="add_sgoods_img_form2"> 
					 <div class="form-group" > 
					 	<div class="form-group" align="center"> 
					 		
							  <input type='file' name="add_sgoods_upimg" 
									    id="add_sgoods_upimg2"/><br/>
									    <!-- <span>(请上传不超过3M的文件)</span><br/>
									     <span>(最佳尺寸450*450，格式支持:jpg/jpeg/png)</span><br/>
									    <span>(点击确定按钮后请耐心等待系统处理图片)</span> -->
							
						</div> 
					</div>  
					 <div class="form-group" align="center" > 
							<input  type="button" style="width: 120px;" 
							class="btn btn-primary" value="确定" onclick="add_sgoods_upimg_yes2();"/> 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input  type="button" style="width: 120px;" 
							class="btn btn-primary" value="取消" data-dismiss="modal"/>
					</div>
					 </form>
				</div>
			</div>
		</div> 
		</div>
     
     
     
   <div class="load_tc_back" id="myload" style="display: none;">
	<div class="load_tc_page">
		<div class="load_tc_img">
		</div>
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
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
  	<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.jplayer.min.js"></script>
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
	 
	  function up_img(code){
			
			upimg_code = code;
			
			 
			
			var change=document.getElementById("dis_play");
			 if (change.style.display == "none") {
				 change.style.display = "inline";
		        }
			 
			 var change2=document.getElementById("dis_play1");
			 if (change2.style.display == "none") {
				 change2.style.display = "inline";
		        }
			 
			$('#add_sgoods_upimg_page').modal({
				keyboard : false
			}); 
			$('#add_sgoods_upimg_page').modal('show'); 
			
		}
	  
	  
	  
	  
	  function add_sgoods_upimg_yes() {
			
			var file = $("#add_sgoods_upimg").val();
			
			if(file == ""){
				alert("图片不能为空！");
				 return;
			}
			 
			var imgmaxsize=3*1024*1024;
			var f=document.getElementById("add_sgoods_upimg");
			var imgsize=f.files[0].size;
			 
			if(imgsize>imgmaxsize){
			    alert('上传照片大小请控制在3M以内');
			    return false;
			}else{
				
				$("#add_sgoods_upimg_page").modal({
					keyboard : false
				}); 
				$("#add_sgoods_upimg_page").modal('hide');
				
				 $("#myload").show();  
			   var code = upimg_code;
				
				var formData = new FormData($( "#add_sgoods_img_form" )[0]);
				
				$.ajax({
					type : "post", 
					url : getRootPath() + '/course/up_img.do',
					data : formData,
					dataType: 'text',  
					cache: false,  
					processData: false,  
					contentType: false,
					success : function(data) 
					{	
						 $("#myload").hide();  
						
						if(data=="error"){
							alert('失败！');
						}else if(data=="not_img"){
							alert('请上传图片！');
						}else{
							 //显示
							 
							 if(code != 5){
								 	document.getElementById("img"+code).src = getRootPath() +"/upload/img/"+data;
									document.getElementById("img_url"+code).value = data; 
									document.getElementById("img"+code).style.visibility='visible';
								
							 }else{
								 
								 save_ht_img(data);
							 
							 }
							
						}
					}
				});
			}
		  	
		}
	  
	  function up_view(code){
			
			upimg_code = code;
			
			var change=document.getElementById("dis_play2");
			 if (change.style.display == "none") {
				 change.style.display = "inline";
		        }
			 
			 
			$('#add_sgoods_upimg_page2').modal({
				keyboard : false
			}); 
			$('#add_sgoods_upimg_page2').modal('show'); 
			
		}

var myVid=document.getElementById("vv");
	  
	  function add_sgoods_upimg_yes2() {
			
			var file = $("#add_sgoods_upimg2").val();
			
			if(file == ""){
				alert("视频不能为空！");
				 return;
			}
			
				$("#add_sgoods_upimg_page2").modal({
					keyboard : false
				}); 
				$("#add_sgoods_upimg_page2").modal('hide');
				
				 $("#myload").show();  
			   var code = upimg_code;
			  
				
				var formData = new FormData($( "#add_sgoods_img_form2" )[0]);
				
				$.ajax({
					type : "post", 
					url : getRootPath() + '/course/up_view.do',
					data : formData,
					dataType: 'text',  
					cache: false,  
					processData: false,  
					contentType: false,
					success : function(data) 
					{	
						 $("#myload").hide();  
						
						if(data=="error"){
							alert('失败！');
						}else if(data=="not_img"){
							alert('请上传图片！');
						}else{
							 //显示
							 
							 if(code != 5){
								 
								 myVid.src = getRootPath() +"/upload/view/"+data;  
							       myVid.controls = true;  
							        
								 //$("#img"+code).attr("src",getRootPath() +"/upload/view/"+data);
								 
								//document.getElementById("img"+code).src = getRootPath() +"/upload/view/course/"+data;
								document.getElementById("img_url"+code).value = data; 
									 
								
							 }
							
						}
					}
				});
			
		  	
		}
	  
	  
	  function blacklist(c_no,state) {
		  
		  
		  	$.ajax({
		  		type:"post",
		  		url: getRootPath() +'/course/freeze2.do',
		  		dataType:"json",
		  		data:{"c_no":c_no,"state":state},
		  		success: function(data){
		  			if("success"==data[0].result){
		  				
		  				window.location.reload();
		  			}else{
		  				alert("课程不存在");
		  			}
		  			
		  		}
		  		
		  	});
		
	}	
	  
	 /*  function last_page(){
			
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
				var url = getRootPath()+"/user/show_user.do";
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
				var url = getRootPath()+"/user/show_user.do";
			    jspost(url, datas); 
			}
		}
	   */
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
	  
	  
	  $(function(){
			$("#rolecommit").click(function(){
				var c_name=$("#uname").val();
				var type="";
				
				var radio2=document.getElementsByName("radion");
					
					for(var i=0;i<radio2.length;i++){
						
						if(radio2[i].checked){
							 type=radio2[i].value;
						}
					}
				
				var introduce=$("#introduce").val();
				var introduce2=$("#introduce2").val();
				var teacher=$("#teacher").val();
			 
				 
				var text=$("#textarea1").val().replace(/[\r\n]/g,"");
					 
				
				var ziyuan=$("#ziyuan").val();
				 
				var audition=$("#course_time").val();  
				//var img =$("#img2").val();
				 
				var price=$("#price").val();
				var discount_price=$("#sale_price").val();
				
				var is_free2=$("#is_free2").val();
				if(is_free2==null||is_free2==""){
					$("#is_free2").tips({
						side : 2,
						msg : '是否免费不能为空',
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
				 
				
				if(introduce==null||introduce==""){
					$("#introduce").tips({
						side : 2,
						msg : '请填写课程简介',
						bg : '#ff0000',
						time : 3
					});
					return false;
				}
				if(introduce2==null||introduce2==""){
					$("#introduce2").tips({
						side : 2,
						msg : '请填写课程第二段简介',
						bg : '#ff0000',
						time : 3
					});
					return false;
				}
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
				if(audition==null||audition==""){
					$("#course_time").tips({
						side : 2,
						msg : '请设置试听时间',
						bg : '#ff0000',
						time : 3
					});
					return false;
				}
				if(price==null||price==""){
					$("#price").tips({
						side : 2,
						msg : '请填写价格',
						bg : '#ff0000',
						time : 3
					});
					return false;
				}
				 
				$.ajax({
					type:"POST",
					url:getRootPath() +'/course/insertcourse.do',
					data:{'c_name':c_name,'type':type,'text':text,'ziyuan':ziyuan,'audition':audition,'price':price,'discount_price':discount_price,'introduce':introduce,'introduce2':introduce2,'teacher':teacher,'is_free':is_free2},
					dataType:"json",
					success:function(data){
						if("success"==data[0].result){
		
							window.location.reload(); 
							
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
	});
	  
	  function special_com() {
		 var  special_name=$("#special_name").val();
		// var  img_url2=$("#img_url2").val();  
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
		 
			/* if(img_url2==null||img_url2==""){
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
				url:getRootPath() +'/course/insertspecial.do',
				data:{'special_name':special_name,'special_ziyuan':cont_content,'special_textarea1':special_textarea1,'special_time':special_time,'special_price':special_price,'special_sale_price':special_sale_price,'is_free':is_free,'teacher':teacher,'fit_people':fit_people,'order_notice':order_notice},
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
		
		
		
		function updaterole(obj){			
		
		
			document.getElementById('show_cno').value=obj.c_no;
			document.getElementById('img_input').value=obj.img;
			$("#updaterole").modal("show");
		}
		$("#updatecommit").click(function(){	
			$("#updaterole").modal("hide");
			var c_name=$("#updatec_name").val();
			 var course_cno=$("#show_cno").val();
			 
			 var  discount_price=$("#updatediscount_price").val();
			 var  teacher=$("#updateteacher").val();
			 var t = document.getElementById("updateis_freer");
			 var is_freer=t.options[t.selectedIndex].value;
			 var  state=$("#updatestate").val();
			 var  top=$("#updatetop").val();
	
			var price=$("#updateprice").val();
			 
				  $.ajax({
					type: "POST",
					url: getRootPath() +'/course/update_course.do',
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
		
		
		function to_have(obj){
			
			 
		var html="http://www.daodaketang.com/educate/jsp/wx/course/course_Detail.html?c_no="+obj.c_no+"";
			
			$("#c_no_val").val(html);
			
			//alert(html);
			$("#to_show_url").modal("show");
		}
		
		function deletebyid(obj){
			$("#deletespan").html(obj.name);
		$("#deleteinputid").val(obj.c_no);
			 
			$("#deletediv").modal("show");
		}
		
		
		
		function deleterole(){
			$("#deletediv").modal("hide");
			var c_no=$("#deleteinputid").val();
			
			$.ajax({
				type: "POST",
				url: getRootPath() +'/course/delete_c_no.do',
				data: {"c_no":c_no},
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
						alert("课程编号为空");
					}
				}
			});
			
			
		}
		
		
		function successloadpage(){
			location.reload(false);
		}
		
	
	function  insert_aa(){
		 
		window.location.href=getRootPath()+"/jsp/pt/course/insert_course.jsp";		
		
		
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
  
  function add_img(){
		
		$("#img_page").modal({
			keyboard : false
		}); 
		$("#img_page").modal('show');
		
		$("#up_img_input").trigger('click');
	}
  
  function get_data(url_data) {
		jspost(pt_path +"/course/show_course.do", url_data);
	}
  
  
  function go_search(){
		
		
	  var state=$("#choose_id").val();
	  
	 
		var name = $("#search_input").val();
		
		//if(name != ""){
			var url_data = {"name":name,"state":state};
			get_data(url_data);
		//}
		 
	}
	  
	  </script>
  <!--  <script type="text/javascript">
  
  var  scaleFun=function(){
	var h=  this.height;
	var w=  this.weight; 
	
	
	if(!hasClass(this,"flag")){
		
		this.height=h*2;
		this.weight=w*2;
	}else{
		removeClass(this,"flag");
		
		this.height=h/2;
		this.weight=w/2;
	}
	  
  };
  //判断是否包含该class
  function hasClass(obj,str){
	  var c=obj.className;
	  if(c.indexOf(str)>0){
		  return true;
	  }
	  
	  
	  return false;
  }
  //去除某个class
  function removeClass(obj,str){
	  obj.className=obj.className.replace(str,"");
  }
  
  var imgs=document.getElementsByTagName("img");
  for(var i=0,len=imgs.length;i<len;i++){
	  var img=imgs[i];
	  img.onclick=scaleFun;
  }
  
  
  </script>  -->
  
</html>
