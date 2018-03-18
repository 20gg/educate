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
    
    <title>管理员管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	<link href="<%=basePath %>css/login2.css" rel="stylesheet" type="text/css" />
	

  </head>
  <style type="text/css">
  .main-table td{
		font-size: 120%;
	}
  
  </style>
  <body>
    <div style="margin-top: 20px;width: 94%; margin-left: 3%; line-height: 30px; height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px;  ">
  	管理员管理
  	</div>
    		
    		 <div  style="width: 94%; margin-left: 3%;text-align: right; margin-top: 20px; margin-bottom: 10px;">
  			 <a  class="btn btn-primary"  data-toggle="modal" href="#myModal">+新增管理员</a>
  	 </div> 
    		
    	<table  class="table table-hover table-bordered" style="width: 94%; margin-left: 3%;
  	border: 1px solid #eee;  border-left-width: 5px; border-radius: 3px;border-left-color: #4CAF50; text-align: center;">
  		<tr style="background-color: #fcfcfc;">
    		<tr>
    		<td   id="xuhao">序号</td>
    		<td  >用户名</td>
    		<td   >密码</td>
    		 <td >状态</td>
    		<td >操作</td>
    		</tr>
    		<c:forEach items="${user_list}" var="user" varStatus="status"> 
    				<tr>
    					<td   class="id"> ${status.count}</td>
    					<td   id="userid">${user.name }</td>
    					<td  >${user.passwd }</td>

    					
    					 <td   >  
    					<c:if test="${user.state==0}">正常</c:if>
    					<c:if test="${user.state==-1 }">冻结</c:if>
    					
    					</td> 
    					
    					<td height="30">
    					
    					 <a href="javascript: updaterole({name:'${user.name}',id:'${user._id}'})" >修改</a>  	&nbsp;&nbsp;&nbsp;
    					 
    						<a href="javascript: deletebyid({name:'${user.name}'})" >删除</a> &nbsp;&nbsp;&nbsp;
    					
    					<a onclick="blacklist('${user.name}','${user.state}');" style="cursor: pointer;" >&nbsp;&nbsp;&nbsp;
    							<c:if test="${user.state==-1}">解冻</c:if>
    							<c:if test="${user.state==0}">冻结</c:if>
    					</a>&nbsp;&nbsp;&nbsp;
    						
    						<a href="javascript: resetpwd({name:'${user.name}'})" >重置密码</a>  	
    					</td>
    		
    				</tr>
    				
    		<input type="hidden" id="updatestate" value="${user.state }">	
    		 <input type="hidden" id="updateid" value="${user.name }">
    		</c:forEach>
    		</table>
    		
    		
    		
  <%-- <div style="width: 94%; margin-left: 3%;">
<ul>
 <li style="list-style: none; float: right; margin-left: 30px;">共${user_list.rowCount}条</li>

<li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="next_page();" style="color: #C90E02;">下一页</a></li>

 <li style="list-style: none;float: right;margin-left: 30px;">第<span id="p_order_num">${user_list.pageNum}</span>/<span id="p_order_count">${user_list.pageCount}</span>页</li>
 <li style="list-style: none;float: right; margin-left: 30px;"><a   href="javaScript:" onclick="last_page();" style="color: #C90E02;">上一页</a></li>
 
</ul>
</div>     		
    <div class="clear"></div> --%>	
   
   
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
            <h4 class="modal-title" id="myModalLabel" style="color: fff;">
               新增管理员
            </h4>
         </div>
         <div class="modal-body" style="text-align: center">
			<h5>用户名</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="line-height:30px;width:80%;padding:0;" type="text" id="uname" /><br> 
			<h5>密码</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="line-height:30px;width:80%;padding:0;" type="password" id="upasswd" /><br>
			  <!--  <h5>状态</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="line-height:30px;width:80%;padding:0;" type="password" id="ustate" placeholder="-1表示冻结0表示正常" /><br> -->
			  
			   </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="rolecommit" style="background-color: green;border-color: green;"> 提交</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>
 
<!-- 添加框（重置密码） -->
<div class="modal fade" id="resetpwd" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 450px;">
      <div class="modal-content">
         <div class="modal-header" style="background-color: #C90E02;">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  <span aria-hidden="true" style="color: #fff;">&times;</span>
            </button>
            <h4 class="modal-title" id="resetpwdmyModalLabel" style="color: #fff;">
               重置密码
            </h4>
         </div>
         <div class="modal-body" style="text-align: center">
        	 <h5>用户名</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:75%;padding:0;" type="text" id="inputname" /><br>
			
			<h5>新密码</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:75%;padding:0;" type="password" id="inputresetpwd" /><br>
			<h5>确认密码</h5>
			<b style="color:#f00;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:75%;padding:0;" type="password" id="reinputresetpwd" /><br>
			<input type="hidden" id="resetpwdid">
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="resetpwdcommit" style="background-color: #c00;border-color: #c00;"> 提交</button>
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
			<h5>用户名</h5>
			<b style="color:#fff;">*&nbsp;</b><input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatename" /><br> 
            
           <input  type="hidden" id="updatemobile"  /><br>
			<h5>密码</h5>
			<b style="color:#fff;">*&nbsp;</b>
			<input style="border-radius:3px;min-height:30px;line-height:30px;width:80%;padding:0;" type="text" id="updatelevel" /><br>		
			
			
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="updatecommit" style="background-color: #c00;border-color: #c00;"> 提交</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
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
			<h5 style="text-align: center; color: #fff;">确定要删除“<b id="deletespan"></b>”账户?</h5>
			<input type="hidden" id="deleteinputid">
         </div>
         <div class="modal-footer" style="text-align: center;" >
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="deleterole();" style="background-color: #c00;border-color: #c00;"> 删除</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>

    		
  </body>
  
 	<script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath %>js/url.js"></script>
	  <script type="text/javascript">
	 var user_id="";
	  $(function(){
			$("#rolecommit").click(function(){
				var name=$("#uname").val();
				
				//var ustate=$("#ustate").val();
				
				var passwd=$("#upasswd").val();
				
				if(checkadduser()){
					$.ajax({
						type:"POST",
						url:getRootPath() +'/user/insertuser.do',
						data:{'name':name,'passwd':passwd},
						dataType:"json",
						success:function(data){
							if("success"==data[0].result){
								var pagenum="${page_count}";
								if("${count}"%50==0){
									pagenum++;
								}
								$("#pageid").val(pagenum);
								$("#successdiv").modal("show");
								
							}else {
								
								if("errname"==data[0].result){
								alert("名字已存在");
							} if("errmobile"==data[0].result){
						/* 		$("#umobile").tips({
									side : 2,
									msg : '此手机号已存在',
									bg : '#ff0000',
									time : 6
								}); */
								alert("手机号已存在");
						}
							$("#faildiv").modal("show");
							}
					}
					
				});
				
			}
			
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
		function resetpwd(obj){
		
			$("#resetpwd").modal("show");
		}
		
	  function updaterole(obj){
			user_id=obj.id;
			$("#updatemobile").val(obj.name);
			$("#updaterole").modal("show");
		}	  
	  $("#updatecommit").click(function(){	
			
			var updatename  =$("#updatename").val();
			var updatepasswd =$("#updatelevel").val();
			 
			 
			  
			 
			if(checkupdate()){
				$.ajax({
					type: "POST",
					url: getRootPath() +'/user/updateuser.do',
					data: {"updatename":updatename,"updatepasswd":updatepasswd,"id":user_id},
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
								msg : '管理员不存在',
								bg : '#ff0000',
								time : 3
							});
						}else if("errname"==data[0].result){
							$("#updatename").tips({
								side : 2,
								msg : '用户名已存在',
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
			var updatename=$("#updatename").val();
			var updatepasswd=$("#updatelevel").val();
			if(updatename==null||updatename==""){
				$("#updatename").tips({
					side : 2,
					msg : '请填写用户名',
					bg : '#ff0000',
					time : 3
				});
				return false;
			}
			if(updatepasswd==null||updatepasswd==""){
				$("#updatelevel").tips({
					side : 2,
					msg : '请填写密码',
					bg : '#ff0000',
					time : 3
				});
				return false;
			}
			
		
			return true;
		} 
	  
	  $("#resetpwdcommit").click(function(){
			var inputname=$("#inputname").val();
			var pwd=$("#inputresetpwd").val();
			var repwd=$("#reinputresetpwd").val();
	
			
			  if(inputname==null||inputname==""){
				  $("#inputname").tips({
						side : 2,
						msg : '用户名不能为空',
						bg : '#ff0000',
						time : 6
					});
				}
			if(pwd==null||pwd==""||!(/^\w{6,}$/).test(pwd)){
				$("#inputresetpwd").tips({
					side : 2,
					msg : '密码最短为6位，不能含有特殊符号',
					bg : '#ff0000',
					time : 6
				});
			}else if(pwd!=repwd){
				$("#reinputresetpwd").tips({
					side : 2,
					msg : '两次密码不一致',
					bg : '#ff0000',
					time : 6
				});
			}else{
				
				$.ajax({
					type: "POST",
					url: getRootPath() +'/user/resetpwd.do',
					data: {'inputname':inputname,'pwd':pwd},
					dataType: "json",
					success: function(data){
						if("success"==data[0].result){
							var pagenum="${rolesinfo.pageNum}";
							$("#pageid").val(pagenum);
							$("#successdiv").modal("show");
						}else if("errname"==data[0].result){
							alert("用户名不存在");
						}else{
							$("#faildiv").modal("show");
						}
					}
				});
			}
		});
	  
	  function checkadduser(){
			
			var name=$("#uname").val();
			
			if(name==null||name==""){
				$("#uname").tips({
					side : 2,
					msg : '请填写用户名',
					bg : '#ff0000',
					time : 6
				});
				return false;
			}
			if(/^0?1[3|4|5|8][0-9]\d{8}$/.test(uname)){
				$("#uname").tips({
					side : 2,
					msg : '用户名不能为手机号格式',
					bg : '#ff0000',
					time : 6
				});
				return false;
			}
			
			/* var ustate=$("#ustate").val();
			if(ustate==null||ustate==""){
				$("#ustate").tips({
					side : 2,
					msg : '状态不能为空',
					bg : '#ff0000',
					time : 6
				});
				return false;
			}
			 */
			var passwd=$("#upasswd").val();
			if(passwd!=null&&passwd!=""&&!(/^\w{6,}$/).test(passwd)){
				
				$("#upasswd").tips({
					side : 2,
					msg : '密码最短为6位，不能含有特殊符号',
					bg : '#ff0000',
					time : 6
				});
				return false;
			}
			
			return true;
		}
	  
	  var name="";
	  function deletebyid(obj){
		  $("#deletespan").html(obj.name);
		  
		  name=obj.name;
			$("#deletediv").modal("show");
		}
		function deleterole(){
			//var datas={id:id};
			//jspost(getRootPath()+'/platformrole/deletebyid.do', datas);
			 
			
			$.ajax({
				
				type: "POST",
				url: getRootPath() +'/user/deletebyname.do',
				data: {"name":name},
				dataType: "json",
				success: function(data){
					if("success"==data[0].result){
						$("#deletediv").modal("hide");
						
						$("#successdiv").modal("show");
					}else{
						$("#faildiv").modal("show");
					}
				}
			});
		}
		
		function blacklist(name,state) {
			 
		   
		  	$.ajax({
		  		type:"post",
		  		url: getRootPath() +'/user/freeze1.do',
		  		dataType:"json",
		  		data:{"name":name,"state":state},
		  		success: function(data){
		  			if("success"==data[0].result){
		  				
		  				window.location.reload();
		  			}else{
		  				alert("管理员不存在");
		  			}
		  			
		  		}
		  		
		  	});
		
	}	
	  
	  </script>
</html>
