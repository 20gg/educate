//视频信息(时长（秒），当前播放时间（秒），
var video_info = { "time_long":0, "time_current":0 };

//视频继续一个时间播放
var continue_obj = null; 
//{"continue_time":10,"title":"提示","text":"已经看了一部分！","button":"继续播放"}

//视频 一个时间  停止播放（试看功能）
var stop_obj = null;
//{"stop_time":30,"title":"提示","text":"试看结束，请订阅！","button":"确定"}

//商品结束播放
var end_obj = null; 
//{"title":"提示","text":"播放结束，请答卷！","button":"确定"}


var video_obj={
		 "auto_play":"0",
		 "file_id":"9031868222951683144",
		 "app_id":"1253790856",
		 "videos":null,
		 "remember":1,
		 "disable_full_screen":0,
		 "stretch_full":1,
		 "disable_drag":0,
		 "stop_time":null,
		 "hide_h5_setting":true,
		 "stretch_patch":true,
		 "height":200,
		 "https":1,
		 "WMode":"opaque",
		 "definition":230
};

var player = null;

//监听事件
var listener = {
	    "playStatus": function (status){
	        //TODO 
	        
	        if(status == "ready"){ 
	        	 console.log("状态:视频准备ok！"); 
	        	 //获取视频的时长
	        	 
	        	 	//苹果手机默认全屏
		    		if(ismobile(1) == 0){ 
		    	    	 
		    		    var v_obj = $("#id_video").find("video");
		         	
		         		var vvid = v_obj.attr("id");
		         		
		         		$("#"+vvid).removeAttr("webkit-playsinline");
		         		
		         		$("#"+vvid).removeAttr("playsinline");
		    		}
	        	  
	        	 //继续播放弹出
	        	 if(continue_obj != null){ 
	        		 video_tc_show(continue_obj.title,continue_obj.text,continue_obj.button,1);
	        		 
	        		 //继续播放不提示
	        		 //player.play(continue_obj.continue_time); 
	        		 
	        	 }else{
	        		 $("#id_video").show();
	        		 $("#video_tc").hide();
	        		 
	        		 player.play(0);
	        	 }
	        	 
	        	 
	        }else if(status == "playing"){ 
	        	
	        	console.log("状态:视频播放！");
	        	
	        	video_info.time_long = player.getDuration(); 
	        	 
	        	video_info.time_current = player.getCurrentTime();
	        	
	        	//试看结束弹出
	        	if(stop_obj != null){
	        		if(video_info.time_current > video_obj.stop_time){ 
	        			video_tc_show(stop_obj.title,stop_obj.text,stop_obj.button,2);
		        	 }
	        	}else{
	        		 $("#video_tc").hide();
	        		 $("#id_video").show();
	        		
	        	 }  
	        	
	        }else if(status == "suspended"){ 
	        	 console.log("状态:视频暂停！");
	        	 
	        	 add_study(player.getCurrentTime(),0);
	        	 
	        }else if(status == "stop"){ 
	        	
	        	console.log("状态:视频停止！");
	        	
	        	video_info.time_current = player.getCurrentTime(); 
	        	//试看结束弹出
	        	if(stop_obj != null){
	        		 player.pause();
	        		video_tc_show(stop_obj.title,stop_obj.text,stop_obj.button,2); 
	        	}else{
	        		 $("#id_video").show();
	        		 $("#video_tc").hide();
	        	 }  
	        	  
	        }else if(status == "playEnd"){ 
	        	 console.log("状态:视频结束！");
	        	 
	        	 video_info.time_current = player.getCurrentTime(); 
	        	 //视频结束弹出
	        	 if(end_obj != null){
	        		 video_tc_show(end_obj.title,end_obj.text,end_obj.button,3);
	        	 }else{
	        		 $("#id_video").show();
	        		 $("#video_tc").hide();
	        	 }
	        	 
	        	  add_study(player.getDuration(),1);
	        } 
	    }
 
};

//视频播放初始化
function video_init(fid,aid,varr,c_obj,s_obj,e_obj){
	
	video_obj.file_id = fid;
	video_obj.app_id = aid; 
	video_obj.videos = varr;
	
	continue_obj = c_obj; 
	stop_obj = s_obj; 
	end_obj = e_obj;
	 
	//（试看功能）
	if(stop_obj != null){
		video_obj.stop_time = stop_obj.stop_time;
	}
	 
	player = new qcVideo.Player("id_video", video_obj, listener);
	
}

function video_tc_show(title,text,but,code){
	
	$("#video_tc_title").text(title);
	$("#video_tc_text").text(text);
	$("#video_tc_button").text(but); 
	$("#video_tc_button").attr("code",code); 
	
	/*var l = formatSeconds(video_info.time_long);
	var c = formatSeconds(video_info.time_current);
	
	if(code == 2){
		 c = formatSeconds(stop_obj.stop_time);
	}
	
	if(l== 0){
		l="00:00:00";
	}
	if(c == 0){
		c = "00:00";
	}*/
	 
	
	$("#video_time_long").text("");
	$("#video_time_stop").html("");
	
	$("#id_video").hide();
	$("#video_tc").show();
}
//隐藏播放器，弹出黑色
function video_tc_show1() {
	
	/*var l = formatSeconds(video_info.time_long);
	var c = formatSeconds(video_info.time_current);
	
	if(code == 2){
		 c = formatSeconds(stop_obj.stop_time);
	}
	
	if(l== 0){
		l="00:00:00";
	}
	if(c == 0){
		c = "00:00";
	}*/
	 
	
	/*$("#video_time_long").text(c);
	$("#video_time_stop").html("&nbsp;/&nbsp;"+l);*/
	
	$("#id_video").hide();
	$("#video_tc_page").hide();
	$("#video_tc").hide();
}
//隐藏黑色，弹出播放器
function video_tc_close1() {
	$("#id_video").show();
	$("#video_tc").hide();
	$("#video_tc_page").show();
}

function video_tc_close() {
	$("#id_video").show();
	$("#video_tc").hide();
}
 

function video_tc_sure(obj){ 
	
	var code = $(obj).attr("code");
	 
	if(code == 1){
		//继续一个时间播放 
		video_tc_close(); 
		player.play(continue_obj.continue_time);
		
	}else if(code == 2){
		//试看结束
		video_tc_close(); 
		
		video_stop_event();
		
	}else if(code == 3){
		//播放结束 
		video_tc_close(); 
		
		video_end_event();
		
	}
}
//秒值转换为时分秒字符串
function formatSeconds(value) { 
	
	var theTime = parseInt(value);// 秒 
	var theTime1 = 0;// 分 
	var theTime2 = 0;// 小时 
	
	 
	if(theTime >= 60) { 
		theTime1 = parseInt(theTime/60); 
		theTime = parseInt(theTime%60); 
	 
		if(theTime1 >= 60) { 
			theTime2 = parseInt(theTime1/60); 
			theTime1 = parseInt(theTime1%60); 
		} 
	} 
	
	var result = ""+parseInt(theTime)+"";
	
	if(result.length == 1){
		result = "0"+result;
	}
	
	if(theTime1 >= 0) {  
		var t1 = parseInt(theTime1); 
		if(t1 < 10){
			t1 = "0"+t1;
		} 
		result = ""+t1+":"+result; 
	} 
	
	if(theTime2 > 0) { 
		
		var t2 = parseInt(theTime2);
		
		if(t2 < 10){
			t2 = "0"+t2;
		} 
		
		result = t2+":"+result; 
	} 
	return result; 
} 


function ismobile(test){
	   
	   var u = navigator.userAgent, app = navigator.appVersion;
	    if(/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))){
	     if(window.location.href.indexOf("?mobile")<0){
	      try{
	       if(/iPhone|mac|iPod|iPad/i.test(navigator.userAgent)){
	        return 0;
	       }else{
	        return 1;
	       }
	      }catch(e){}
	     }
	    }else if( u.indexOf('iPad') > -1){
	        return 0;
	    }else{
	        return 1;
	    }
}

