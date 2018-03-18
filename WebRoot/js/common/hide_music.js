//var openid="ok4o7wWpLhebjcpQ5kgzSiN7j6YQ";
var music_url = "http://www.daodaketang.com/educate";

var stop_img = "http://www.daodaketang.com/educate/img/index_start.png";

var play_img = "http://www.daodaketang.com/educate/img/index_stop.png"; 

var w_img ="http://www.daodaketang.com/educate/jsp/pt/a_edit/edit/img/sanjiao.png";

var t_img = "http://www.daodaketang.com/educate/jsp/pt/a_edit/edit/img/index_erji.png";


//document.write("<audio id='music' ></audio>");

/*var music_arr = [
                 "http://1253790856.vod2.myqcloud.com/355e2b2dvodgzp1253790856/2fad8a8a9031868222952714970/f0.mp3" ,
                 "http://1253790856.vod2.myqcloud.com/355e2b2dvodgzp1253790856/2fad8a8a9031868222952714970/f0.mp3" ,
                 "http://1253790856.vod2.myqcloud.com/355e2b2dvodgzp1253790856/2fad8a8a9031868222952714970/f0.mp3"   ,
                 "http://1253790856.vod2.myqcloud.com/355e2b2dvodgzp1253790856/2fad8a8a9031868222952714970/f0.mp3" 
                ];*/


var music_arr = []; 

var audio_arr =[];

var this_code = 0; 
var music_audio;

var div_html = "";

/*$(function (){
	
	// alert(11111);
	  
});*/

//初始化播放器
function init_music(idname){
	
	if(music_arr.length > 0){
		for(var i=0;i<music_arr.length;i++){
			
			var audio = new Audio(music_arr[i].src);
			//audio.preload="auto";
			
			audio.addEventListener("ended", function(){ 
		    	 //播放结束
		    	 console.log("播放结束,播放下一个");
		    	 if((this_code+1) < music_arr.length ){
		    		 
		    		 
		    		 $(".fmusic").find("b").css("color","#999999");
		    		 this_code = this_code +1;
		    		 
		    		 audio_arr[this_code].play();
		    		 $("#word"+this_code).find("b").css("color","#2dc158");
		    		 
		    	 }else{
		    		 $(".fmusic").find("b").css("color","#999999");
		    	 }
		    	 
		     }, false);
			
			audio_arr.push(audio);
		}
	}
	
	 /* music_audio = document.getElementById(idname); 
	  this_code = 0; 
	  
	  music_audio.src = music_arr[this_code].src;
	   
	  //添加播放器结束监听 
	  music_audio.addEventListener("ended", function() {
	    	 
	    	 //播放结束
	    	 console.log("播放结束,播放下一个");
	    	 
	    	 if((this_code+1) < music_arr.length ){
	    		 
	    		 $(".fmusic").find("b").css("color","#999999");
	    		 
	    		 this_code = this_code +1;
	    		 
	    		 music_audio.src = music_arr[this_code].src;
	    		 
	    		 $("#word"+this_code).find("b").css("color","#2dc158");
	    		 
	    		 music_audio.play();
	    		 
	    		 music_add_watch(music_arr[this_code].cno);
	    		 
	    	 }else{
	    		 $(".fmusic").find("b").css("color","#999999");
	    	 }
	    	 
	     }, false);*/ 
}

function music_control(){
	
	/* if(music_audio.paused){
		 
		 $("#music_img").attr("src",play_img);
		 
		 $("#word"+this_code).find("b").css("color","#2dc158");
		 
		 music_audio.play();
		 
		 music_add_watch(music_arr[this_code].cno);
		  
		 
	 }else{
		 
		 $("#music_img").attr("src",stop_img);
		 
		 music_audio.pause();// 这个就是暂停
		  
	 }*/
	
	if(audio_arr[this_code].paused){
		
		$("#music_img").attr("src",play_img);
		
		 $(".fmusic").find("b").css("color","#999999");
		 $("#word"+this_code).find("b").css("color","#2dc158");
		
		 audio_arr[this_code].play();
		 
		 music_add_watch(music_arr[this_code].cno);
		 
	}else{
		
		$(".fmusic").find("b").css("color","#999999");
		$("#music_img").attr("src",stop_img);
		audio_arr[this_code].pause();
		 
	}
		 
}

function music_openit(it_code){
	
	if(it_code == this_code){
		if(audio_arr[this_code].paused){
			
			$("#music_img").attr("src",play_img); 
			 $(".fmusic").find("b").css("color","#999999");
			 $("#word"+this_code).find("b").css("color","#2dc158");
			 audio_arr[this_code].play();
			 
			 music_add_watch(music_arr[this_code].cno);
			 
		}else{
			$(".fmusic").find("b").css("color","#999999");
			$("#music_img").attr("src",stop_img);
			audio_arr[this_code].pause();
		}
	}else{
		
		if(audio_arr[this_code].paused){
			
			this_code = it_code;
			
			$("#music_img").attr("src",play_img); 
			 $(".fmusic").find("b").css("color","#999999");
			 $("#word"+this_code).find("b").css("color","#2dc158");
			 audio_arr[this_code].play();
			 
			 music_add_watch(music_arr[this_code].cno);
			
		}else{
			
			audio_arr[this_code].pause();
			
			this_code = it_code;
			
			$("#music_img").attr("src",play_img); 
			 $(".fmusic").find("b").css("color","#999999");
			 $("#word"+this_code).find("b").css("color","#2dc158");
			 audio_arr[this_code].play();
			 
			 music_add_watch(music_arr[this_code].cno);
		}
		 
	}
	
	/* $(".fmusic").find("b").css("color","#999999");
	 $("#word"+itcode).find("b").css("color","#2dc158");
	 
	 this_code = itcode; 
	 
	 if(music_audio.paused){
		 
		 $("#music_img").attr("src",play_img);
		 
		 music_audio.src = music_arr[itcode].src;
		 music_audio.play();
	 }else{
		 
		 music_audio.pause();// 这个就是暂停
		 
		 music_audio.src = music_arr[itcode].src;
		 music_audio.play();
	 }
	  
	 music_add_watch(music_arr[itcode].cno);*/
}


function music_add_watch(cno){
	
	var nnn = $("#fmusic_watch").text();
	
	nnn = Number(nnn)+1;
	
	$("#fmusic_watch").text(nnn);
	
	 $.ajax({
	  		type:"post",
	  		url:  getRootPath()+"/course/watched.do",
	  		dataType:"text", 
	  		data:{"id":cno},
	  		success: function(data){
	  			
	  		}
	 });
}


