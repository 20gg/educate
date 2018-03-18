<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>boot/3.0.1/css/bootstrap.css" /> 
	  <link rel="stylesheet" href="<%=basePath%>css/tanchu.css" /> 
	<link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css"
	type="text/css"></link>

  </head>
  
  <style type="text/css">
  .float_width{
  	float: left;
  	width: 33%;
    
  }
  
  .float_top{
  
  width: 80px;
  float: left;
  
  }
  
  
  
  </style>
  
  
  <body style="width: 100%;margin: 0px;padding: 0px;">
    
   
   <div style="width: 100%; margin-top: 30px;">
   <div class="float_width"> 
	   <div  class="float_top btn btn-primary" id="but1" style="margin-left: 30px;" onclick="show_today();" >日</div>
	   <div  class="float_top btn btn-default" id="but2" onclick="show_week();">周</div>
	   <div  class="float_top btn btn-default " id="but3"  onclick="show_month();">月</div>
   </div>
   
   <div class="float_width"> 
   <input   type="text" id="yx_xx" readonly="readonly" value=""  style="width: 200px;height: 35px;"
			 	class="choose laydate-icon layui-input"
				onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD'})"/>
		 
		<input  type="text" id="yx_yy" readonly="readonly" value=""  style="width: 200px;height: 35px;"
			 	class="choose laydate-icon layui-input"
				onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM'})"/>	
				
			
		<input   type="text" id="yx_zz" readonly="readonly" value=""  style="width: 200px;height: 35px;"
			 	class="choose laydate-icon layui-input"
				onclick="layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM'})"/>			
   </div>
   
   
   <div class="float_width btn btn-primary" style="width: 150px;"   onclick="show_tj();">
   	查看统计数据
   </div>
   
   </div>
   
   <div style="padding-top: 120px;border: 1px;margin-top: 50px; display: none;">
        <div id="container" style="min-width:400px;height:400px;" ></div>
   
   </div>
   <div style="clear: both;"></div>
   <br> <br>
   <table id="table"  class="table table-hover table-bordered"
    style="width: 94%; margin-left: 3%; margin-right: 3%; text-align: center; font-size: 12px; ">
   	 
   	
   </table>
   
   <br> <br> <br> <br>
   
     <div class="load_tc_back" style="display: none;" id="myload">
<div class="load_tc_page">
	<div class="load_tc_img">
	</div>
</div>
</div> 
   
  </body>
  	  <script src="<%=basePath %>js/jquery-2.1.1.min.js"type="text/javascript"></script>
  	  	<script type="text/javascript"
	src="<%=basePath%>js/layui/lay/dest/layui.all.js"> </script>
  	    <script src="<%=basePath %>js/highcharts/highcharts.js"type="text/javascript"></script>
  	      	<script type="text/javascript" src="<%=basePath %>js/jquery.tips.js"></script>
  	    <script type="text/javascript" src="<%=basePath%>/js/url.js" ></script>
  	<script type="text/javascript">
  	var co=0;
  	
  	$(function(){
  		var a =getNowFormatDate();
  		
  		$("#yx_xx").val(a);
  		
  		show_today(); 
  	});
  	
   
  	//统计图数据
  	
  	function init_table(data_x,data_1,data_2,data_3,data_4,data_5,my_title,my_title2){
  		
  		var chart = new Highcharts.Chart('container', {
  	  	    title: {
  	  	        text: my_title,
  	  	        x: -20
  	  	    },
  	  	    
  	  	credits: {
  	      enabled: false
  	  },
  	  	     subtitle: {
  	  	        text: my_title2,
  	  	        x: -20
  	  	    }, 
  	  	    xAxis: {
  	  	        categories: data_x
  	  	    },
  	  	    yAxis: {
  	  	        title: {
  	  	            text: '人/次'
  	  	        },
  	  	        plotLines: [{
  	  	            value: 0,
  	  	            width: 1,
  	  	            color: '#808080'
  	  	        }]
  	  	    },
  	  	    tooltip: {
  	  	        valueSuffix: ''
  	  	    },
  	  	    legend: {
  	  	        layout: 'vertical',
  	  	        align: 'right',
  	  	        verticalAlign: 'middle',
  	  	        borderWidth: 0
  	  	    },
  	  	    series: [{
  	  	        name: '新增用户',
  	  	        data: data_1
  	  	    }, {
  	  	        name: '普通会员',
  	  	        data: data_2
  	  	    }, {
  	  	        name: 'vip会员',
  	  	        data: data_3
  	  	    }, {
  	  	        name: '新增课程',
  	  	        data: data_4
  	  	    }, {
  	  	        name: '新增订单',
  	  	        data: data_5
  	  	    }]
  	  	});
	}
  	
  	 
  	function init_tb(data_x,data_1,data_2,data_3,data_4,data_5,my_title,my_title2){
		
  		var table_html ="";
  		
  		var len = data_x.length +1;
  		
  		var table_top ="<tr> <td colspan='"+len+"'><span style='font-size: 16px; '>"+my_title+"</span><br><span>"+my_title2+"</span></td> </tr>";
  		
  		var table_x ="";
  		
  		for(var i=0;i<data_x.length;i++){ 
  			var xx = "<td>"+data_x[i]+"</td>"; 
  			table_x = table_x+xx;
  		}
  		
  		table_x = "<tr><td>人&nbsp;<span style='color:blue;'>/</span>&nbsp;时间</td>"+table_x+"</tr>";
  		
  		var y1 ="";
  		var y2 ="";
  		var y3 ="";
  		var y4 ="";
  		var y5 ="";
  		
  		for(var n=0;n<data_1.length;n++){
  			var yy1 = "<td>"+data_1[n]+"</td>"; 
  			var yy2 = "<td>"+data_2[n]+"</td>"; 
  			var yy3 = "<td>"+data_3[n]+"</td>"; 
  			var yy4 = "<td>"+data_4[n]+"</td>"; 
  			var yy5 = "<td>"+data_5[n]+"</td>"; 
  			
  			 y1 =y1+yy1;
  	  		 y2 =y2+yy2;
  	  		 y3 =y3+yy3;
  	  		 y4 =y4+yy4;
  	  		 y5 =y5+yy5;
  		}
  		
  		 y1 ="<tr><td>新增用户</td>"+y1+"</tr>";
  		 y2 ="<tr><td>普通会员</td>"+y2+"</tr>";
  		 y3 ="<tr><td>vip会员</td>"+y3+"</tr>";
  		 y4 ="<tr><td>新增课程</td>"+y4+"</tr>";
  		 y5 ="<tr><td>新增订单</td>"+y5+"</tr>";
  		 
  		 
  		table_html = table_top + table_x + y1 + y2 + y3 + y4 + y5;
  		
  		$("#table").html(table_html);
	}
  	 
  	
  	
  	function show_today(){
  		
	  	var a=	getNowFormatDate();
	  	
	  	co = 0;
	  	
	  	$("#but1").attr("class","float_top btn btn-primary");
		$("#but2").attr("class","float_top btn btn-default");
		$("#but3").attr("class","float_top btn btn-default");
	  	
	  	$("#yx_xx").val(a);
	  	
	  	$("#yx_yy").hide();
	  	$("#yx_zz").hide();
		$("#yx_xx").show();
	  	 
	  	show_tj();
   
  	}
  	
  	
  	function show_tj(){
  		
  		 $("#myload").show();
  		var date=$("#yx_xx").val();
  		 
  		var date2=$("#yx_yy").val();
  		 
  		var date3=$("#yx_zz").val();
  		  
		 
  		if(co==0){
  			 
  			$.ajax({
  		  		type:"post",
  		  		url: getRootPath() +'/tongji/show_today.do',
  		  		dataType:"json",
  		  		data:{"date":date},
  		  		success: function(data){
  		  		 $("#myload").hide();
  		  			var create_date=data[0].create_date;
  		  			var vip_date=data[0].vip_date;
  		  			var member_date=data[0].member_date;
  		  			var course=data[0].course;
  		  			var course_order=data[0].course_order;
  		  		var	 data_x=['00:00-04:00','04:00-08:00','08:00-12:00','12:00-16:00','16:00-20:00','20:00-24:00'];
  		  	 
  		  			//init_table(data_x,create_date,member_date,vip_date,course,course_order,"日统计图","日期："+date);
  		  			
  		  			init_tb(data_x,create_date,member_date,vip_date,course,course_order,"日统计图","日期："+date);
  		  		}
  		  		
  		  	});
  	  		
  			
  		}else if(co==1){
  			 
  			$.ajax({
  		  		type:"post",
  		  		url: getRootPath() +'/tongji/show_month.do',
  		  		dataType:"json",
  		  		data:{"date":date2},
  		  		success: function(data){
  		  		 $("#myload").hide();
  		  			var create_date=data[0].create_date;
  		  			var vip_date=data[0].vip_date;
  		  			var member_date=data[0].member_date;
  		  			var course=data[0].course;
  		  			var course_order=data[0].course_order;
  		  			var data_x=data[0].size;
  		  				 
  		  		 
  		  			//init_table(data_x,create_date,member_date,vip_date,course,course_order,"月统计图","日期："+date2);
  		  			
  		  			init_tb(data_x,create_date,member_date,vip_date,course,course_order,"月统计图","日期："+date2);
  		  			
  		  			
  		  		
  		  			
  		  		}
  		  		
  		  	});
  		}else if(co==2){
  			 
  			
  			$.ajax({
  		  		type:"post",
  		  		url: getRootPath() +'/tongji/show_week.do',
  		  		dataType:"json",
  		  		data:{"date":date3},
  		  		success: function(data){
  		  		 $("#myload").hide();
  		  			var create_date=data[0].create_date;
  		  			var vip_date=data[0].vip_date;
  		  			var member_date=data[0].member_date;
  		  			var course=data[0].course;
  		  			var course_order=data[0].course_order;
  		  		    var	 data_x=['第一周','第二周','第三周','第四周','第五周','第六周'];
  		  		    
  		  		    var data_x2 =[];
  		  		    
  		  		    for(var i=0;i<data[0].course_order.length;i++){
  		  		    	data_x2.push(data_x[i]);
  		  		    }
  		  				 
  		  	 
  		  			//init_table(data_x,create_date,member_date,vip_date,course,course_order,"周统计图","日期："+date3);
  		  			
  		  			init_tb(data_x2,create_date,member_date,vip_date,course,course_order,"周统计图","日期："+date3);
  		  			
  		  		}
  		  		
  		  	});
  		}
  		
  		
  		
  	}
  	
  	function  show_month(){
  		$("#yx_xx").hide();
  		 
  		$("#yx_zz").hide();
  		$("#yx_yy").show();
  		
		co = 1;
	  	
	  	$("#but1").attr("class","float_top btn btn-default");
		$("#but2").attr("class","float_top btn btn-default");
		$("#but3").attr("class","float_top btn btn-primary");
  		
  		var dada=	$("#yx_yy").val();
  		var date="";
  		if(dada==null||dada==""){
  			
  			date=getNowFormatmonth();
  			var date1="";
  			if (date!=null&&date!=""){
  				date1=	date.substring(0, 7);
  				$("#yx_yy").val(date1);
  			}
  			
  		}else{
  			
  			$("#yx_yy").val(dada);
  			
  		}
  		
	 
  		 
  		 
  		
  		show_tj();
  		 
  	}
  	
  	 
  	
  	function show_week(){
  		
  		$("#yx_xx").hide();
  		 
  		$("#yx_yy").hide();
  		$("#yx_zz").show();
  		
		co = 2;
	  	
	  	$("#but1").attr("class","float_top btn btn-default");
		$("#but2").attr("class","float_top btn btn-primary");
		$("#but3").attr("class","float_top btn btn-default");
  		
  		var dada=	$("#yx_zz").val();
  		var date="";
  		if(dada==null||dada==""){
  			
  			date=getNowFormatmonth();
  			var date1="";
  			if (date!=null&&date!=""){
  				date1=	date.substring(0, 7);
  				$("#yx_zz").val(date1);
  			}
  			
  		}else{
  			
  			$("#yx_zz").val(dada);
  			
  		}
  		 
  		 
  		show_tj();
  	}
  	
  	
  	
  	
  	
  	
  //获取当前时间，格式YYYY-MM-DD
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
  
    //获取当前时间，格式YYYY-MM-DD
    function getNowFormatmonth() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
 
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        
        var currentdate = year + seperator1 + month + seperator1;
        return currentdate;
    }
    
    
    
  	</script>
  
</html>
