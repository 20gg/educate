function checkTimeall(){

	/*var obj=document.getElementById("checktimeone");
	
	if(obj!=null){
		
		var timestr=$(obj).attr("stime");
		var stime = new Date(timestr);
		var nowtime = new Date();
		
		
		if(nowtime>=stime){
			$(obj).html("<span style='color:green'>直播进行中</span>");
		}else{
			$(obj).html("直播未开始");
		}
		
		
		
	}*/
	
}

function checkTimeall2(timestr) {
	
	var obj=document.getElementById("checktimeone");
	
	  var a = timestr.split(" "); 
	  var arr = a[0].split('-');
	  var arr2 = a[1].split(':');
	
	var stime = new Date(arr[0],parseInt(arr[1]-1),arr[2],arr2[0],arr2[1],arr2[2]).getTime();
	 
	var nowtime = new Date().getTime();
	
	
	if(nowtime>=stime){
		$(obj).html("<span style='color:green'>直播进行中</span>");
	}else{
		$(obj).html("直播未开始");
	}
	
}