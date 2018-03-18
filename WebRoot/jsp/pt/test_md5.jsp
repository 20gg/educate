<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
     
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
	 <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
  <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="<%=basePath%>js/path.js" ></script>
    <script type="text/javascript" src="<%=basePath%>js/qq_video/qq_video.js?v=1"></script>
    <script src="https://qzonestyle.gtimg.cn/open/qcloud/video/h5/h5connect.js" charset="utf-8"></script>
  <script type="text/javascript" src="<%=basePath%>js/MD5.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/to_md5.js?v=0.3"></script>

  </head>
  
  <body>
   <!--播放器 封装  -->
   <div  class="vodeo_box">
   		<!-- qq_播放器 -->
   		<div id="id_video" style="width:100%; height:auto; "></div> 
   		<!-- 播放器_弹出 -->
   		<div id="video_tc" class="video_tc" >
   			<div id="video_tc_page" class="video_tc_page">
   				<div class="video_tc_title" id="video_tc_title">提示</div>
   				<div class="video_tc_text"  id="video_tc_text"></div>
   				<div class="video_tc_button"  id="video_tc_button" onclick="video_tc_sure(this);" code="1"></div>
   			</div> 
   	 	 	<div class="video_tc_foot" >
   	 	 		<span id="video_time_long" class="video_time_long">00:00:00</span>
   	 	 		<span id="video_time_stop"  class="video_time_stop">&nbsp;/&nbsp;00:00</span>
   	 	 	</div>
   		</div> 
   </div>
  </body>
 
  <script type="text/javascript">

  $(function(){
	  
	  
	  
	  
	  var c_no="1507794389780";
	  var openid="ok4o7wWpLhebjcpQ5kgzSiN7j6YQ";
	  
	  $.ajax({
	  		type:"post",
	  		url: pt_path +'/course/course_dt.do',
	  		dataType:"json",
	  		data:{"c_no":c_no,"open_id":openid},
	  		success: function(data){
	  			var course=data[0].ccourse_list;
	  		    var video_arr= [];
	  		 video_arr.push(back_url(course[0].trans_list));																		
	 
	  			alert(video_arr);
	  			video_init(course[0].url,"1253819261",video_arr,null,null,null);	
	  			
	  		}
    
	  });  
  });
  	
    	
  </script>
  
  
  
  
</html>
