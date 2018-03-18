var openid="ok4o7wWpLhebjcpQ5kgzSiN7j6YQ";
var top_openid="";
var share_ul="";
$(function(){
		 
		
	$.ajax({
		type:"post",
  		url: getRootPath()+'/course/show_tanchu.do',
  		dataType:"json", 		
  		success: function(data){ 
  		
  			var list=data[0].tanchu;
  			
  			var html="";
  			
  			for(var i=0;i<list.length;i++){
  				
  				if(list[i].state==1){
  					
  					html="<div id='t_c2' style='position:fixed;top:0px;width:100%;height:100%;z-index:1000;background:rgba(0,0,0,0.6);'><a href='"+list[i].link+"'><img src='"+getRootPath()+"/"+list[i].img+"' style='width:60%;margin-left:20%;margin-right:20%;margin-top:40%'></a>" +
  							" <div onclick='hide_tc2();'><img src='"+getRootPath()+"/images/buy/1.png' style='width:6%;margin-left:47%;margin-right:47%;margin-top:10px;'></div></div>";
  					
  					$('body').append(html);
  				}
  				
  			}
  			 
  			
  			}	
		});
	
	
	});


function  hide_tc2(){
	
	$("#t_c2").hide();
	
}



//专题
 //var openid="oLir8jsziazLpKT0b6JSSbQ0icR4";
 //var top_openid="";
function gointoGoodsInfo(gid){
	
	$.ajax({
		type:"post",
  		url: getRootPath()+'/course/special_detail.do',
  		dataType:"json",
  		data:{"c_no":gid,"open_id":openid},
  		success: function(data){ 
  			var special=data[0].special;
  			var user=data[0].user;
  			var dy=data[0].dy;
  			
  			
  			
  				if(user.role==1){
  					dy=1;
  				}
  			
  				
  				if(dy>0){
  					
  					 if(special.kind==1){
  						 if(special.type==1){
  							localStorage.setItem("c_no", gid );		
    	  		  			document.location.href=getRootPath()+"/jsp/wx/course/af_special.html?v=1";
  						 }else{
  							 
  							localStorage.setItem("c_no", gid );		
    	  		  			document.location.href=getRootPath()+"/jsp/wx/course/af_special_buy_voice.html?v=2";
  						 }
    						
    					 }else{
    						 
    						localStorage.setItem("c_no", gid );		
    	  		  			document.location.href=getRootPath()+"/jsp/wx/course/af_little_special.html?v=3";	 
    					 }
   					
  					
  				}else{
  					
  					 if(special.kind==1){
    						localStorage.setItem("c_no", gid );		
    	  		  			document.location.href=getRootPath()+"/jsp/wx/course/free_special.html?v=123";
    					 }else{
    						 
    						localStorage.setItem("c_no", gid );		
    	  		  			document.location.href=getRootPath()+"/jsp/wx/course/free_little_special.html?v=0.1";	 
    					 }
   					
  					
  				}
  				 
  		}
			
			
		});
	 
		
	
	//document.location.href=getRootPath()+"/goods/queryOneDetail.do?goodsId="+gid+"&osid="+oid;
	
	
	
}


//课程
function goCurser(cno){
		
	
	  
	$.ajax({
		type:"post",
  		url: getRootPath()+'/course/course_detail.do',
  		dataType:"json",
  		data:{"c_no":cno},
  		success: function(data){ 
  			var course=data[0].course;
  			if(course!=null){
  				
  				if(course.type==1&&course.is_show==1){
  					 	  						
  					  if(course.is_free==-1){   //限时免费的	
  						
  						localStorage.setItem("c_no", cno );		
  			document.location.href=getRootPath()+"/jsp/wx/course/buy_course.html";
  					
  				}else{
  					
  					localStorage.setItem("c_no", cno );		
  		  			document.location.href=getRootPath()+"/jsp/wx/course/course_Detail.html";
  				}
  			
  			}else if(course.type==0&&course.is_show==1){
  			
  				if(course.is_free==-1){
  					localStorage.setItem("c_no", c_no );
  					
  					document.location.href=getRootPath()+"/jsp/wx/course/buy_voice.html";	
  				}else{
  					
  						localStorage.setItem("c_no", c_no );
  					
  					document.location.href=getRootPath()+"/jsp/wx/course/bf_voice.html";
  				}
  				
  			}
  			
  			
  		}
  	}
  		
	});
	
}


//排行
function goPh(id,kind){
	if(parseInt(kind)==1){
		//专题
		gointoGoodsInfo(id);
		
	}else{
		
		goCurser(id);
	}
}

var yg_time ="";
var yg_link ="";

function into_yg(obj){
	 window.location.href = yg_link;
}

//替換名字和頭像
 
(function find_user(){
	
	funaaaa();
	 
	
})();

function wx_page_init(){
	 
	 if(top_openid == null){
		 top_openid = openid;
	 }
	
	$.ajax({
		type:"post",
 		url: getRootPath()+'/top/set_top.do',
 		dataType:"json",
 		data:{"openid":openid,"top_openid":top_openid,"share_ul":share_ul},
 		success: function(data){ 
 				
 			 
 			
 			if(data[0].user.head != null && data[0].user.head != ""){ 
  				$("#head_img").attr("src",data[0].user.head);
  			}else{ 
  				 
				
 				 window.location.href=getRootPath()+"/jsp/wx/art/authority.html?v=0.3";
  				
  				$("#head_img").attr("src",getRootPath()+"/images/buy/231_03.png" );
  			}
 			
 			  
  			
  			$("#role_img").attr("src",getRootPath()+"/img/role/role"+data[0].user.role+".png");
  			$("#my_name").text(data[0].user.name);
  			
  			if(data[0].user.role!=1){ 
				$("#my_name").after("<button class='top_span' onclick='go_vip();'>会员升级</button>"); 
			} 
 
 		}
 		
	});
}



function funaaaa(){
	
	
	 
	 if(openid != null){
		 wx_page_init();
	 }
	
	 $.ajax({
	  		type:"post",
	  		url:  getRootPath()+"/study/get_free_music.do",
	  		dataType:"json", 
	  		success: function(data){
	  			
	  			///alert(2222);
	  			
	  			yg_time = data[0].yg.time;
				yg_link = data[0].yg.link;
				
				checkTimeall2(data[0].yg.time);
				
				var y_n = data[0].yg.time_note+","+data[0].yg.name;
				if(y_n.length>27){
					y_n = y_n.substring(0,27)+"..."; 
				} 
	  			
				$("#yg_link").html("<p>"+y_n+"</p>");
	  			
	  			var list = data[0].list;
	  			
	  			if(list.length > 0){
	  				
	  				var word_div="";
	  				
	  				var watch = 0;
	  				
	  				for( var i=0;i<list.length;i++ ){
	  					
	  					var z_obj={"src":list[i].sourceurl,"cno":list[i].c_no}; 
	  					music_arr.push(z_obj); 
	  					
	  					var c_name = list[i].c_name; 
	  					
	  					watch = watch + list[i].watch; 
	  					
	  					if(c_name.length > 15){
	  						c_name = c_name.substring(0,19)+""; 
	  					} 

	  					var w = "<div class='courseone fmusic' id='word"+i+"' onclick='music_openit("+i+");'> <img src='"+w_img+"'> <b>"+c_name+"</b></div>";

	  					//var w = "<div class='courseone'  id='word"+i+"' > <img src='"+w_img+"'> <b>"+c_name+"</b></div>";

	  					 
	  					word_div = word_div+w;
	  				}
	  				
	  				var m_html ="<div class='leftcorse'>"+word_div+"</div><div class='rightcorse'> <img class='startVoice' id='music_img' src='"+
	  				stop_img+"' onclick='music_control();' > <b style='text-align: center;'><img class='icon' src='"+t_img+"'> <span id='fmusic_watch' style='font-family:\"微软雅黑\"; font-weight:none; color:#646464;text-align: center;'>"+watch+"</span></b> </div>";
	  				
	  				//var m_html ="<div class='leftcorse'>"+word_div+"</div><div class='rightcorse'>  </div>";
	  				
	  				$("#music_page").html(m_html); 
	  				$("#music_page").show();
	  				
	  				init_music("music");
	  				
	  			}else{
	  				$("#music_page").hide();
	  			}
	  			
	  			var free_video = data[0].free_video;
	  			
	  			if(free_video.length > 0){
	  				
	  				var v_html ="";
	  				
	  				for(var i=0;i<free_video.length;i++){
	  					
	  					var c_name = free_video[i].c_name;
	  					if(c_name.length > 7){
	  						c_name = c_name.substring(0,6)+""; 
	  					} 
	  					
	  					var zzz =i+1;
	  					
	  					var v = "<div   class='couponone img"+zzz+"' w='"+free_video[i].watch+"' cno='"+free_video[i].c_no+"'  style='width:30%;' onclick='goin_curser(this);' >"
	  					+"<div class='imgdiv'> <img src='"+free_video[i].img+"' > <img class='videoicon' src='http://www.daodaketang.com/educate/jsp/pt/a_edit/edit/img/hot/video.png' ></div>"
	  					+"<b>"+c_name+"</b></div>";
	  					
	  					v_html = v_html +v;
	  					 
	  				}
	  				
	  				$("#free_page").html(v_html);
	  				$("#free_page").show();
	  				
	  			}else{
	  				$("#free_page").hide();
	  			}
	  			
	  		}
	 });
	 
	 huan_huan(1);
	 
	 ph_page();
	 
	 show_little_special(1);
	 
} 

function ph_page(){
	
	$.ajax({
		type:"post",
		url: getRootPath() +"/article/query_host.do",
		dataType:"json", 
		success: function(data){
			
			var s_list = data[0].s_list;
			var c_list = data[0].c_list;
			
			var html ="";
			
			if(s_list.length > 0){
				
				var sc = s_list.length;
				
				if(sc>= 3){
					sc = 3;
				}
				
				for(var i=0; i< sc; i++){
					
					var n = i+1;
					
					var n1 = s_list[i].special_name;
					if(n1.length >5){
						n1 = n1+"";
					} 
					
					var h1 = "<div class='hotone img"+n+"' style='width:30%;' onclick='into_s(this);' sid='"+s_list[i].special_no+"' >"
					+"<div class='imgdiv'><img src='"+s_list[i].img+"' /> "
					+ "<img class='videoicon' src='http://www.daodaketang.com:80/educate/jsp/pt/a_edit/edit/img/hot/video.png'>"
					+ "</div>"
					+ "<span style='font-family:\"微软雅黑\"; font-weight:none;  line-height: 20px;' class='pmspan' >"+n+ "</span>"
					+ "<b>"+n1+"</b> </div>";
					
					html = html + h1;
				}
			}
			
			$("#hot_page").html(html);
			 
		}
	});
}
 

function into_s(obj){
	var special_no=$(obj).attr("sid");
	 
	localStorage.setItem("c_no", special_no );
	
	window.location.href=getRootPath()+'/jsp/wx/course/free_specials.html';
	
}



function go_vip(){
	
	document.location.href=getRootPath()+'/jsp/wx/Guyang/dinggou.html?v=0.3';
}


function goin_curser(obj){
	
	var c = $(obj).attr("cno");
	var w = $(obj).attr("w");
	
	localStorage.setItem("c_no", c );	
	localStorage.setItem("watch", w );	
	
	document.location.href=getRootPath()+"/jsp/wx/course/buy_course.html";
}

function Get_Stringsssss(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if ( r != null ){
       return unescape(r[2]);
    }else{
       return null;
    } 
}

function go_special(){
	
	document.location.href=getRootPath()+'/jsp/wx/course/special.html';
	
}

function go_hot(){
	document.location.href=getRootPath()+'/jsp/wx/Guyang/hot_list.html?v=2';
	
}

function go_live(){
	
	document.location.href=getRootPath()+'/jsp/wx/Title.html';
	
}

 
function get_more(){
   	
   	var page = page_num+1;
   	
   	huan_huan(page);
   	
   //	
  }

	function huan_huan(page){
		// var page_num =1;
		  //var page_count =1;
	 
		
		$.ajax({
			type:"post",
	  		url: getRootPath()+'/course/huan_huan.do',
	  		dataType:"json",
	  		data:{"page":page,"open_id":openid},
	  		success: function(data){ 
	  			

	  		 
	  			  var user=data[0].user;			 
	  			var list=data[0].list;
	  			var html="";
	  			var html2="";
	   			var vid_size=data[0].vid_size;
	   			var special_all=data[0].special_all;
	   			var special=data[0].special;   
	   			
	   			
	  			if(list!=null){
	  			
	  				if(list.length>0){
	  					
	  					for(var  i = 0;i<list.length;i++){
	  						var gid=list[i].special_no;
	  						
	  						var ssn=list[i].special_name;
	  						
	  						   
	  						
	  						var cc="<p class='goods-title toutitle ' style='padding-right:16%;line-height:18px;' >" 
									+""+ssn+""
									+"<span class='goods-title price' style='width:16%;margin:none;font-size:14px;position:absolute;text-align:right;top:0px;right:0px;font-family:微软雅黑'>已购买</span>"
										+"</p>";
	  						if(ssn.length > 10){
	  							ssn = ssn.substring(0,10);
	  		  				}
	  						 
	  						if(special!=null){
	  							
	  							for(var  z=0;z<special.list.length;z++){
	  							 
	  								if(special.list[z].isBuy!=1&&special.list[z].special_no==gid && user.role!=1){
		  								 
	  									cc="<p class='goods-title toutitle ' style='padding-right:22%;line-height:18px;' >" 
	  										+""+ssn+""
	  										+"<span class='goods-title price' style='width:22%;margin:none;font-size:14px;position:absolute;top:0px;right:0px;font-family:微软雅黑'>￥"+toDecimal2(list[i].price/100)+"/年</span>"
	  											+"</p>";
		  							
	  								
	  								}
		  						}
	  								
	  							}
	  							
	  					var tett=list[i].text;
	  							if(tett.length>34){
	  								
	  								tett=tett.substring(0,32)+"...";
	  							}
	  						
	  					html+="<li class='goods-card card small-pic'>"
	  						+"<a onclick='gointoGoodsInfo("+gid+");' class='js-goods clearfix'>"
	  						+"<div class='photo-block'>"
	  						+"<img class='goods-photo js-goods-lazy' tmp=\"\" src=\"/educate/img/logo.png\"data-echo=\""+list[i].img+"\"><img class='videoicon' src='"+getRootPath()+"/jsp/pt/a_edit/edit/img/hot/video.png'></div>"
	  						+"<div class='info  btn1 info-price info-no-price'>"
	  						/*+"<p class='goods-title toutitle ' style='padding-right:22%;line-height:18px;' >"+ssn+"    "+cc+"</p>"*/
	  						+""+cc+""
	  						
	  						+"<p class='goods-title' style='margin-top:14px;'>"+tett+"</p>"
	  						
	  						+"<p class='goods-title1 jie' style='margin-top:10px;font-size:13px;color:#888;border-radius:14px;line-height:16px;border:1px solid #dcdcdc;border-radius:20px;margin-left:120px;margin-right:40px;line-height:22px;padding-left:10px;'>已更新"+list[i].url.length+"集，持续更新中...</p>"
	  						+"<p class='goods-title weizhujiang'>主讲:"+list[i].teacher+"</p>"
	  					+"</div></a>"
	  					+"<label class='goodsbtn'>"+list[i].order_count+"人订阅</label>	</li>";
	  					
	  			 		
	  					}
	  				//"+special_all.length+"
	  				html2="<b class='commonb' id='numsshow'>查看全部</b>";
	  					$("#quan_bu").html(html2);
	  				
	  				$("#sj_special").html(html);
	  					
	  				echo.init({
						offset : 150,
						throttle : 200
					});
	  					
	  					
	  				}else{
	  					html="";
	  					$("#sj_special").html(html);
	  					html2="<b class='commonb' id='numsshow'>查看全部</b>";
	  					$("#quan_bu").html(html2);
	  					
	  					
	  				}
	  				 	  			
	  				}
	  				
	  			}
	   			
	   		 
				
			});
		
		
	}
	
	
	
	//制保留2位小数，如：2，会在2后面补上00.即2.00    
	function toDecimal2(x) {    
	    var f = parseFloat(x);    
	    if (isNaN(f)) {    
	        return false;    
	    }    
	    var f = Math.round(x*100)/100;    
	    var s = f.toString();    
	    var rs = s.indexOf('.');    
	    if (rs < 0) {    
	        rs = s.length;    
	        s += '.';    
	    }    
	    while (s.length <= rs + 2) {    
	        s += '0';    
	    }    
	    return s;    
	}
	
	function head_str(){
		
		var date = new Date();
		
		var h = date.getHours();
		
		var head_str = "";
		
		if(h<11){
			head_str = ",上午好";
		}else if(h<14){
			head_str = ",中午好";
		}else if(h<17){
			head_str = ",下午好";
		}else{
			head_str = ",晚上好";
		}
		
		return head_str;
	}
	
	
	function show_little_special(page){
		
	 
		
		$.ajax({
			type:"post",
	  		url: getRootPath()+'/course/huan_huan2.do',
	  		dataType:"json",
	  		data:{"page":page,"open_id":openid},
	  		success: function(data){ 
	  			
	  			 
	  			 	var user=data[0].user; 
	  			var list=data[0].list;
	  			var html="";
	  			var html2="";
	   			//var time=data[0].time;
	   			 
	   			var special=data[0].special;   
	  			if(list!=null){
	  				
	  				for(var  i = 0;i<list.length;i++){
	  						var gid=list[i].special_no;
	  						
	  						var ssn=list[i].special_name;
	  						
	  						var  cc="";
	  						
	  						if(ssn.length > 10){
	  							ssn = ssn.substring(0,10)+"";
	  		  				}
	  						 
	  						if(special!=null){
	  							alert(ssn);
	  							for(var  z=0;z<special.list.length;z++){
	  								 
	  								if(user.role==1){
		  								 
	  									cc="<p class='goods-title toutitle ' style='padding-right:16%;line-height:18px;'>" 
	  									+""+ssn+""
	  									+"<span class='goods-title price' style='width:16%;margin:none;font-size:14px;position:absolute;text-align:right;top:0px;right:0px;font-family:微软雅黑'>已购买</span></p>";
		  							}else{
		  								
		  								cc="<p class='goods-title toutitle ' style='padding-right:16%;line-height:18px;'>"
	  									+""+ssn+""
	  									+"<span class='goods-title price' style='width:22%;margin:none;font-size:14px;position:absolute;top:0px;right:0px;font-family:微软雅黑'>￥"+toDecimal2(list[i].price/100)+"/年</span></p>";
		  						
		  							}
		  						}
	  								
	  							}
	  							
	  						var time_list=list[i].time_list;
	  						
	  						var time="";
	  						
	  						if(time_list.length>0){
	  							
	  								time=time_list[0].time;
	  							
	  						}else{
	  							time=2400;
	  						}
	  						
	  					html+="<li class='goods-card card small-pic'>"
	  						+"<a onclick='gointoGoodsInfo("+gid+");' class='js-goods clearfix'>"
	  						+"<div class='photo-block'>"
	  						+"<img class='goods-photo js-goods-lazy' src='/educate/img/logo.png' data-echo='"+list[i].img+"'><img class='videoicon' src=''></div>"
	  						+"<div class='info  btn1 info-price info-no-price'>"
	  						+" "+cc+" "
	  						+"<p class='goods-title' style='margin-top:14px;'>"+list[i].text.substring(0,15)+"</p>"
	  						+"<p class='goods-title weizhujiang'>主讲:"+list[i].teacher+"</p>"
	  						 
	  					+"</div></a>"
	  					+"<label class='goodsbtn'>"+list[i].order_count+"人订阅</label>	</li>";
	  			 		
	  					}
	  				
	  				/*html2="查看全部<b style='font-family:\"微软雅黑\"; font-weight:none; width:100%;text-align:center;display:block;margin-bottom:5PX '></b>";
	  					$("#little_special").html(html2);*/
	  				html+="<b class='commonb' onclick='go_little();'>查看全部</b>";
	  				$("#sj_special2").html(html);
	  					
	  				echo.init({
						offset : 150,
						throttle : 200
					});
	  				}
	  				 
	  			}
	   			 
			});
		 
	}
	
	//分钟转化成时分秒

	function formatSeconds(value) {

	 	    var theTime = parseInt(value);// 秒

	 	    var theTime1 = 0;// 分

	 	    var theTime2 = 0;// 小时

	 	    if(theTime > 60) {

	 	        theTime1 = parseInt(theTime/60);

	 	        theTime = parseInt(theTime%60);

	 	            if(theTime1 > 60) {

	 	            theTime2 = parseInt(theTime1/60);

	 	            theTime1 = parseInt(theTime1%60);

	 	            }

	 	    }

	 	        var result = ""+parseInt(theTime)+"秒";

	 	        if(theTime1 > 0) {

	 	        result = ""+parseInt(theTime1)+"分"+result;

	 	        }

	 	        if(theTime2 > 0) {

	 	        result = ""+parseInt(theTime2)+"小时"+result;

	 	        }

	 	    return result;

	 	}
	
	function go_little(){
		
		document.location.href=getRootPath()+'/jsp/wx/course/little_special_list.html';
	}
	
	function go_course_list(){
		document.location.href=getRootPath()+'/jsp/wx/course/course_list.html';
	}
