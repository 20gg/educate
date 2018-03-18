<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>404编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	 <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
     <script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>

  </head>
  
  <body style="background-color: #fff;">
    <div style="margin-top: 20px;width: 94%; margin-left: 3%;  line-height: 30px;
     height: 30px; border-left: 4px solid #4CAF50;padding-left: 10px; ">编辑404</div>
     
     <div style="width: 600px; margin-top: 30px; margin-left: 40px;">
     	<textarea id="container5" name="content1" type="text/plain">${set_page.html}</textarea>
		<script type="text/javascript" src="<%=basePath%>js/sku/ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->								
		<script type="text/javascript" src="<%=basePath%>js/sku/ueditor.all.js"></script>
		<!-- 实例化编辑器 -->
		<script type="text/javascript">
			var ue3 = UE.getEditor("container5");
			//对编辑器的操作最好在编辑器ready之后再做
			ue3.ready(function() {
				//设置编辑器的内容
				//获取html内容，返回: <p>hello</container2p>
				var html = ue3.getContent();
				//获取纯文本内容，返回: hello
				var txt = ue3.getContentTxt();
			});
		</script>
     </div>
     
     <button style="width: 100px; height: 40px; background-color: #4CAF50; color: #fff;
     border: none; margin-left: 50px; margin-top: 30px;" onclick="save_page();">保存</button>
     
  </body>
  <script type="text/javascript">
  
  function save_page() {
	  
	  var html = ue3.getContent();
	  
		jspost(getRootPath() +"/article/edit_404.do", {"html":html} );
}
  </script>
  
</html>
