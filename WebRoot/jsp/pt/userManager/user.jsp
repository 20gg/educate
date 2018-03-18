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
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	
<link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css"
	type="text/css"></link>
	

  </head>
  <style type="text/css">
  .main-table td{
		font-size: 120%;
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
  </style>
  <body>
    <div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  ">
  	用户管理
  	</div>
    
     <div class="input-group"  style="width: 600px; position: absolute;top:10%;left:3%;"> 
  	 		<span class="input-group-btn form-control" style="width: 80px;">昵称 </span>  
		  <input type="text" id="search_input222" value="${input}" class="form-control"  placeholder="请输入搜索信息" maxlength="128">
		  <span class="input-group-btn"> 
		  	 <button type="button" class="btn btn-primary" style="width: 80px;" onclick="go_search();">搜索</button>
		  </span>
		</div>
    		
    	<table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%; margin-top:90px;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr style="width: 100%">
    		<td   id="xuhao" style="width: 2%">序号</td>
    		<td style="width: 5%">微信头像</td>
    		<!-- <td  style="width: 8%">会员编号</td> -->
    		<td  style="width: 5%" >微信昵称</td>
    		 
    		 <td style="width: 8%" >会员等级</td>
    		 <td style="width: 5%">真实姓名</td>
    		<td style="width: 10%" >手机号</td>
    		<td  style="width: 8%">公司</td>
    		<td style="width: 5%" >职务</td>
    		<td style="width: 5%">状态</td>
    		<td style="width: 5%"> 有效期</td>
    		<td style="width: 10%">操作</td>
    		</tr>
    		<c:forEach items="${user_list.list}" var="user" varStatus="status"> 
    				<tr style="width: 100%">
    					<td   class="id" style="width: 2%"> ${status.count}</td>
    					<td   style="width: 5%">
    						<c:if test="${not empty user.head}">
    						<img src="${user.head}" style="width: 40px; height: 40px;">
    						</c:if>
    					</td>
    					<%-- <td   id="userid" style="width: 8%">${user.u_no}</td> --%>
    					<td  style="width: 5%">${user.name }</td>
    					 
    					<td style="width: 2%"> 
    					<c:if test="${user.role ==1}">VIP会员</c:if>
    					<c:if test="${user.role ==0}">普通用户</c:if>
    					<c:if test="${user.role ==3}">会员用户</c:if>
    					</td>
    					<td  style="width: 5%">${user.u_name }</td>
    					<td   id="phoneid" style="width: 10%">${user.phone }</td>
    					<td  style="width: 5%">${user.company }</td>
    					<td  style="width: 5%">${user.job }</td>
    					
    					<%-- <td   >  
    					<c:if test="${user.role==0}">普通书生</c:if>
    					<c:if test="${user.role==1 }">会员</c:if>
    					
    					</td> --%>
    					<td style="width: 5%" > 
    					<c:if test="${user.state ==0}">正常</c:if>
    					<c:if test="${user.state ==1}">冻结</c:if>
    					</td> 
    					<td  onclick="myModal('${user.open_id}');"  style="cursor: pointer;">${user.p1_date}</td>
    					
    					<td style="width: 30%">
    					<%-- <a href="javascript: updaterole({mobile:'${user.phone}'})" >修改</a> --%>
    					<a href="<%=basePath %>user/userinfo.do?no=${user.u_no }&c_id=${user.classmate_id }&open_id=${user.open_id}" >详情</a>
    					 
    					 &nbsp; &nbsp; &nbsp;
    					<!-- <a  href="javascript: onclick="myModal('${user.open_id}');" >延迟锁定期</a> -->
    					 	
    					</td>
    		
    				</tr>
    		 <input type="hidden" id="updateid" value="${user.u_no }">
    		 
    		</c:forEach>
    		</table>
    		
    		
    		
 <%--  <div style="width: 94%; margin-left: 3%;">
<ul>
 <li style="list-style: none; float: right; margin-left: 30px;">共${user_list.rowCount}条</li>

<li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="next_page();" style="color: #C90E02;">下一页</a></li>

 <li style="list-style: none;float: right;margin-left: 30px;">第<span id="p_order_num">${user_list.pageNum}</span>/<span id="p_order_count">${user_list.pageCount}</span>页</li>
 <li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="last_page();" style="color: #C90E02;">上一页</a></li>
 
</ul>
</div>     		
    <div class="clear"></div> --%>

<div class="page">
<div style="font-size:15px;text-align:right;width:100%;color:#888;padding-right:100px;font-size: 14px;"> 
		<span>共<b style="color:#666">${user_list.rowCount}</b>条</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<span style="color:#666">第<span id="p_order_num">${user_list.pageNum}</span>/<span id="p_order_count">${user_list.pageCount}</span>页</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button"  class="but_page" onclick="last_page()">上一页</button>
		&nbsp;&nbsp;&nbsp;
		<button type="button" class="but_page" onclick="next_page()">下一页</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 跳转到 
			<input type="text" name="page"  id="page_input" style="width:60px;height:30px;border: 1px solid #5CB85C; text-align: center; font-size: 16px;" onkeyup="priceKeyup(this)"
				maxval="${user_list.pageCount}" value="${user_list.pageNum}">
			&nbsp;&nbsp;&nbsp; 
			 <input type="button" value="GO"class="but_page" onclick="key_page();">
		 
	</div>
</div>  		
    
  	<br><br><br>	
   
  
   <!-- 添加框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel"   aria-hidden="true">
   <div class="modal-dialog" style="width: 360px; ">
      <div class="modal-content">
         <div class="modal-header"  >
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" >&times;</span>
            </button>
            <h4   id="myModalLabel"  > 新增天数 </h4>
         </div>
         <div class="modal-body" style="text-align: center">
			 
			 	<div class="input-group" style="width: 200px;">
					  <input type="text" class="form-control" id="uname"  placeholder="请输入多少天">
					  <span class="input-group-addon" id="basic-addon2">天</span>
					</div></div><br>
			 
			 
			 
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-success" id="rolecommit" > 提交</button>
         </div>
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
			<h5>微信昵称</h5>
			<b style="color:#fff;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatename" /><br> 
            
           <input  type="hidden" id="updatemobile"  /><br>
			<h5>会员等级</h5>
			<b style="color:#fff;">*&nbsp;</b>
			<input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatelevel" /><br>		
			<h5>手机号</h5>
			<b style="color:#fff;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatephone" /><br>
			<h5>公司</h5>
			<b style="color:#fff;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatecompany" /><br>
			<h5>职务</h5>
			<b style="color:#fff;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatejob" /><br>
			<h5>状态</h5>
			<b style="color:#fff;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatestate"  placeholder="0表示正常1表示冻结" /><br>
			
			
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="updatecommit" style="background-color: #c00;border-color: #c00;"> 提交</button>
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
      </div> 
</div> 
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
      </div> 
</div> 
</div>
 
    		
  </body>
  
 	<script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
 	<script type="text/javascript"
	src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
 <script type="text/javascript" src="<%=basePath%>/js/path.js" ></script>
  	
	  <script type="text/javascript">
	 
	 var p2_openid=""; 
	  
function check_num(input){
	//正整数
	var r = /^[1-9]*[1-9][0-9]*$/;
   
  	return r.test(input);
}

function key_page() {
	 
	var page = $("#page_input").val();
	var page_count = Number($("#p_order_count").text());
	//校验page为正整数
	if(check_num(page) == true){
	 	if(page >= 1 && page <= page_count){
		 	 
	 		var kind = $("#search_kind").val();
	 		var input = $("#search_input").val();
	 		
	 		var datas = {"page":page};			
			var url = getRootPath()+"/user/show_user.do";
		    jspost(url, datas); 
			
	 	}else{
	 		$("#page_input").val("");
	 	}
	}else{
		$("#page_input").val("");
	}
	  
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
	  
	  function getRootPath() {
			// 获取当前网址，如：
			// http://localhost:8080/labms_s/navigation/files/sysmanagement/main.jsp?url=sys_equi_number.jsp
			var curWwwPath = window.document.location.href;
			// 获取主机地址之后的目录，如： /ems/navigation/files/sysmanagement/main.jsp
			var pathName = window.document.location.pathname;
			var pos = curWwwPath.indexOf(pathName);
			// 获取主机地址，"http://localhost:8080"
			var localhostPaht = curWwwPath.substring(0, pos);
			// 获取带"/"的项目名，如：/jquery
			var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
			// 返回"/ems/navigation/files/sysmanagement/"
			var forwordPath = pathName.substring(0, pathName.length - 8);
			// return (localhostPaht + forwordPath);
			return (localhostPaht + projectName);
		}
		function loadpage(pagenum){
			
			location.reload(false);
		}
		function successloadpage(){
			location.reload(false);
		}
	  function updaterole(obj){
			
			$("#updatemobile").val(obj.mobile);
			$("#updaterole").modal("show");
		}	  
	  $("#updatecommit").click(function(){	
			
			var updatename  =$("#updatename").val();
			var updatelevel =$("#updatelevel").val();
			var updatephone =$("#updatephone").val();
			var updatecompany =$("#updatecompany").val();
			var updatejob =$("#updatejob").val();
			var updatestate =$("#updatestate").val();
			 var     userid=document.getElementById("updateid").value;
			  
			 
			if(checkupdate()){
				$.ajax({
					type: "POST",
					url: getRootPath() +'/user/updateuser.do',
					data: {"updatename":updatename,"updatelevel":updatelevel,"updatephone":updatephone,"updatecompany":updatecompany,"updatejob":updatejob,"updatestate":updatestate,"userid":userid},
					dataType: "json",
					success: function(data){
						if("success"==data[0].result){
							$("#updaterole").modal("hide");
					var pagenum="${page_count}";
										$("#pageid").val(pagenum);
										$("#successdiv").modal("show");
										window.location.reload();
						}else if("error"==data[0].result){
							$("#updatename").tips({
								side : 2,
								msg : '会员不存在存在',
								bg : '#ff0000',
								time : 3
							});
						}else if("errphone"==data[0].result){
							$("#updatephone").tips({
								side : 2,
								msg : '手机号已存在',
								bg : '#ff0000',
								time : 3
							});
							return false;
						}else{
							$("#faildiv").modal("show");
						}
					}
				});
			}
		});
	  function checkupdate(){
			var updatephone=$("#updatephone").val();
			if(updatephone==null||updatephone==""){
				$("#updatephone").tips({
					side : 2,
					msg : '请填写手机号',
					bg : '#ff0000',
					time : 3
				});
				return false;
			}
			
		
			return true;
		} 
	  
	  function myModal(obj){
		  
		  p2_date=obj;
		  
		  
		  $("#myModal").modal({
				keyboard : false
			}); 
			$("#myModal").modal('show');
		}
	$("#rolecommit").click(function(){
				var date=$("#uname").val();
					if(trim(date)==""||date==null){
						$("#uname").tips({
							side : 2,
							msg : '请填写天数',
							bg : '#ff0000',
							time : 1
						});
						return false;
						
					}
					
					$.ajax({
						type:"POST",
						url:getRootPath() +'/user/insertP2.do',
						data:{'date':date,'open_id':p2_date},
						dataType:"json",
						success:function(data){
							if("success"==data[0].result){
								$("#myModal").modal('hide');
								var pagenum="${page_count}";
								if("${count}"%50==0){
									pagenum++;
								}
								$("#pageid").val(pagenum);
								$("#successdiv").modal("show");
								
							}else{
								alert("上级为空，不能延长！");
							} 
					}
					
				});
				
			 
			
		});
	  
			
		 
	  
	  function trim(str) {
		  return str.replace(/(^\s+)|(\s+$)/g, "");
		}
	  
	  
	  function get_data(url_data) {
			jspost(pt_path +"/user/show_user.do", url_data);
		}


		function go_search(){
			
			 
			var name = $("#search_input222").val();
			
			//if(name != ""){
				var url_data = {"name":name};
				get_data(url_data);
			//}
			 
		}
	  
	  </script>
</html>
