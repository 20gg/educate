<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> 商品管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/tanchu.css" rel="stylesheet" type="text/css" />

  </head>
  <style type="text/css">
  .main-table td{
		font-size: 120%;
	}
  
  </style>
  
  <body>
     	<div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  ">
  	商城商品
  	</div>
     
     
     <div  style="width: 94%; margin-left: 3%;text-align: right; margin-top: 20px; margin-bottom: 10px;">
  			 <a  class="btn btn-primary"  data-toggle="modal" href="#myModal">+新增商品</a>
  	 </div> 
     <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    		<td   id="xuhao">序号</td>
    		<td  >sku_id</td>
    		<td   >商品名称</td>
    		 <td >价格</td>
    		 <td  >优惠价</td>
    		 <td >状态</td>
    		<td  >已销售</td>
    		
    		</tr>
    		<c:forEach items="${shop_list.list}" var="shop" varStatus="status"> 
    				<tr>
    					<td   class="id"> ${status.count}</td>
    					<td   id="userid">${shop.sku_id }</td>
    					<td  >${shop.shop_name }</td>
    					 
    					<td>${shop.price }元</td>
    					<td  >${shop.discount_price }元</td>
    					<td   onclick="blacklist();" style="cursor: pointer;">
    					<c:if test="${shop.state==1}">已上架</c:if>
    					<c:if test="${shop.state==-1}">已下架</c:if>
    					</td>
    					<td  >${shop.sale }</td>
    					
    			  
    		
    				</tr>
    		 <input type="hidden" id="skuid" value="${shop.sku_id }">
    		  <input type="hidden" id="stateid" value="${shop.state }">
    		</c:forEach>
    		</table>
    		
    		
    		
  <div style="width: 94%; margin-left: 3%;">
<ul>
 <li style="list-style: none; float: right; margin-left: 30px;">共${shop_list.rowCount}条</li>

<li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="next_page();" style="color: #C90E02;">下一页</a></li>

 <li style="list-style: none;float: right;margin-left: 30px;">第<span id="p_order_num">${shop_list.pageNum}</span>/<span id="p_order_count">${shop_list.pageCount}</span>页</li>
 <li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="last_page();" style="color: #C90E02;">上一页</a></li>
 
</ul>
</div>     		
    <div class="clear"></div>	
     
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
         <div class="modal-body" style="text-align: center">
			<h5>用户名</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="line-height:30px;width:80%;padding:0;" type="text" id="uname" /><br> 
			<h5>密码</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="line-height:30px;width:80%;padding:0;" type="password" id="upasswd" /><br>
			   <h5>状态</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="line-height:30px;width:80%;padding:0;" type="password" id="ustate" placeholder="-1表示冻结0表示正常" /><br>
			  
			   </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="rolecommit" style="background-color: green;border-color: green;"> 提交</button>
         </div>
      </div> 
</div> 
</div>     
     
     
     <div class="modal fade" style=" position: fixed;top:35%;"  id="add_sgoods_upimg_page" tabindex="-1" role="dialog"
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
     
   <div class="load_tc_back" id="myload" style="display: none;">
	<div class="load_tc_page">
		<div class="load_tc_img">
		</div>
	</div>
</div>   
     
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
	  <script type="text/javascript">
	  function blacklist() {
		  	var sku_id=$("#skuid").val();
		  	var  state=$("#stateid").val();
		  	 
		  	
		  	$.ajax({
		  		type:"post",
		  		url: getRootPath() +'/shop/freeze1.do',
		  		dataType:"json",
		  		data:{"sku_id":sku_id,"state":state},
		  		success: function(data){
		  			if("success"==data[0].result){
		  				
		  				window.location.reload();
		  			}else{
		  				alert("会员不存在");
		  			}
		  			
		  		}
		  		
		  	});
		
	}	
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
	  
	  </script>
  
  
</html>
