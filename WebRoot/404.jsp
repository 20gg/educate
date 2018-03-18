<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>404</title>
    
	 <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
    <meta name="content-type" content="text/html; charset=UTF-8">
	 

  </head>
  
  <body id="my_body">
     
  </body>
  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  <script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
  <script type="text/javascript">
  $(function() {
	  $.ajax({
			type:"post",
			url: getRootPath() +"/article/get_the.do",
			dataType:"json",
			 
			success: function(data){
				
			/* 	$("#my_body").html(data[0].set_page.html); */
			window.location.href=data[0].set_page.html;
			}
	  });
});
  </script>
</html>
