
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
    
    <title>添加特定用户</title>
    
	 
	 
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
  	列表信息
  	</div>
     
     
     <div  style="width: 94%; margin-left: 3%;text-align: right; margin-top: 20px; margin-bottom: 10px;">
  			 <a  class="btn btn-success"      data-toggle="modal"   href="#myModal"  >+新增特定用户</a>
  		 
  	 </div> 
  	  
  		  
    	<c:if test="${not empty  phone}">
    	
    	 <table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    	 
    		<!-- <td  >姓名</td> -->
    		<td   >手机号</td>
    		<td  >操作</td>
    		</tr>
    	
    		<c:forEach items="${phone}" var="p" varStatus="status"> 
    				<tr>    					 
    					 
    				<%-- 	<td  >${p.name }</td> --%>
    					 
    					<td  >${p.phone }</td>
    					
    				
    				<td height="30">
    					<a href="<%=basePath %>course/dele_phone.do?phone=${p.phone}" >删除</a>
    					</td>	  
    					  
    				</tr>
    		  
    		 
    		</c:forEach>
    	</table>
    	</c:if>
    	
    	<c:if test="${empty  phone}">
    	
    		<div style="font-size: 30px;margin-left: 2%;">暂无信息
			
			 </div><br>
    	
    		
    	</c:if>
    	
     
  
     
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
			<!-- <div>姓名:
			<input type="text" id="name" class="form-control" style="width: 200px;" placeholder="请输入姓名">
			 </div><br> -->
    	
    		<div>手机号:
			 <input type="text" id="phone" class="form-control" style="width: 200px;"placeholder="请输入手机号">
			 </div><br>
			  
			   </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="rolecommit" style="background-color: green;border-color: green;"> 提交</button>
         </div>
      </div> 
</div> 
</div>     
     
      
    <div class="load_tc_back" style="display: none;" id="myload">
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
  	<script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.jplayer.min.js"></script>
	  <script type="text/javascript">
	  
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
			   
			    $("#myload").show();
			/* 	var name=$("#name").val(); */
				var phone=$("#phone").val();
				 
				/* if(name==null||name==""){
					$("#rolecommit").tips({
						side : 3,
						msg : '请输入姓名',
						bg : '#2dc158',
						time :3
					});
					  
					return false;
				}	 */
				
				
				if(phone==null||phone=="" || isPhone(phone) == false){
					$("#rolecommit").tips({
						side : 3,
						msg : '请输入手机',
						bg : '#2dc158',
						time :2
					});
				 
					return false;
				}
			
				 
				$.ajax({
					type:"POST",
					url:getRootPath() +'/course/insertphone.do',
					data:{'name':"",'phone':phone},
					dataType:"json",
					success:function(data){
						
						$("#myload").hide();
						if("success"==data[0].result){
								alert("添加成功");
							window.location.href=getRootPath()+'/course/one_price.do';
							
						}else {
							alert("手机号已存在");
						 
						}
				}
				
					});
				
		});
	  
		   
	   });
			
		
		
		
		
function inert(){
	
	/* var name=$("#name").val(); */
	var phone=$("#phone").val();
	 
	/* if(name==null||name==""){
		$("#rolecommit").tips({
			side : 3,
			msg : '请输入姓名',
			bg : '#2dc158',
			time :3
		});
		  
		return false;
	}	 */
	
	
	if(phone==null||phone=="" || isPhone(phone) == false){
		$("#rolecommit").tips({
			side : 3,
			msg : '请输入手机',
			bg : '#2dc158',
			time :2
		});
	 
		return false;
	}

	  $("#myload").show();
	$.ajax({
		type:"POST",
		url:getRootPath() +'/course/insertphone.do',
		data:{'name':"",'phone':phone},
		dataType:"json",
		success:function(data){
			$("#myload").hide();
			if("success"==data[0].result){

				window.location.reload(); 
				
			}else {
				alert("手机号已存在");
			 
			}
	}
	
		});
	
}	
		
   
  function isPhone (string) {    
  	var pattern = /^1[34578]\d{9}$/;    
  	if (pattern.test(string)) {        
  	return true;    
  	}else{
  	return false;
  	}  
  }
	  </script>
  
  
</html>
