
//音频地址
var music_src = "";

//继续一个时间播放
var continue_obj = null; 
//{"continue_time":10,"title":"提示","text":"已经看了一部分！","button":"继续播放"}

// 一个时间  停止播放（试看功能）
var stop_obj = null;
//{"stop_time":30,"title":"提示","text":"试看结束，请订阅！","button":"确定"}

//结束播放
var end_obj = null; 
//{"title":"提示","text":"播放结束，请答卷！","button":"确定"}

var audio;

var cc=0;

//初始化播放
function init_audio(id_name,src,c_obj,s_obj,e_obj,c_name){
	
	audio = document.getElementById(id_name); 
	music_src = src; 
	continue_obj = c_obj; 
	stop_obj = s_obj; 
	end_obj = e_obj;
	
	var info = c_name;
	
	
	
	$("#audio_info").text(info);
	
	audio.src = music_src;
	
	 audio.addEventListener('loadeddata', function() {
         log('loadeddata');
     }, false);
     
     audio.addEventListener('play', function() {
         log('play');
         
       //继续播放弹出
    	 if(continue_obj != null){ 
    		 video_tc_show(continue_obj.title,continue_obj.text,continue_obj.button,1);
    		 
    		 audio.currentTime = continue_obj.continue_time;
    		 
    		 cc = continue_obj.continue_time;
    		 audio.pause(); 
          	 
    	 }
         
     }, false);
     audio.addEventListener('playing', function() {
         log('playing');
         
         setInterval(function() {
             var currentTime = audio.currentTime;
             
             console.log("时间："+currentTime);
             //试看结束
             if(stop_obj != null && currentTime > stop_obj.stop_time){
             	
             	audio.pause(); 
             	audio.currentTime = stop_obj.stop_time;
                
             	video_tc_show(stop_obj.title,stop_obj.text,stop_obj.button,2); 
             }
              
         }, 1000);
         
     }, false);
     audio.addEventListener('pause', function() {
         log('pause');
         
         add_study(audio.currentTime,0);
         
     }, false);
     audio.addEventListener("ended", function() {
    	 log('ended');
    	 //播放结束
    	 if(end_obj != null){
    		 video_tc_show(end_obj.title,end_obj.text,end_obj.button,3); 
    	 } 
    	 add_study(audio.duration,1);
    	 
     }, false);
 
}

function log(info) {
    console.log(info);
    
}

function video_tc_show(title,text,but,code){
	
	$("#video_tc_title").text(title);
	$("#video_tc_text").text(text);
	$("#video_tc_button").text(but); 
	$("#video_tc_button").attr("code",code); 
	 
	
	$("#id_video").hide();
	$("#video_tc").show();
}


function video_tc_close() {
	$("#id_video").show();
	$("#video_tc").hide();
}

function video_tc_sure(obj){ 
	
	var code = $(obj).attr("code");
	 
	if(code == 1){
		//继续一个时间播放 
		continue_obj = null;
		video_tc_close(); 
		audio.load();
        audio.play();//开始播放  
        
        audio.currentTime = cc;
		
	}else if(code == 2){
		//试看结束
		video_tc_close(); 
		
		audio_stop_event();
		
	}else if(code == 3){
		//播放结束 
		video_tc_close(); 
		
		audio_end_event();
		
	}
}
 
 