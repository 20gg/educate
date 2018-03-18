var surl=location.href.split('#')[0];
var url=encodeURIComponent(surl);
function getCookie(name) {  
    //取出cookie   
    var strCookie = document.cookie;  
    //cookie的保存格式是 分号加空格 "; "  
    var arrCookie = strCookie.split("; ");  
    for ( var i = 0; i < arrCookie.length; i++) {  
        var arr = arrCookie[i].split("=");  
        if (arr[0] == name&&arr[1]!="") {  
            return decodeURIComponent(arr[1]);  
        }  
    }  
    return null;  
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if ( r != null ){
       return unescape(r[2]);
    }else{
       return null;
    } 
 }
 
var openid =getCookie("newopenid");

//var openid = null;
 

 if(openid==null || openid == "" || openid == "null"){

 
	var code=GetQueryString("code"); 
	if(code!=null&&code.trim()!=""){
		
		//alert(code);
		
		$.ajax({
			type:"post",
			url:"http://www.daodaketang.com/educate/wx/ajax_info.do",
			data:{"code":code},
			dataType:"json",
			success:function(data){
				 
				openid = data[0].openid;
				setCookie_openid("newopenid",openid);
				wx_page_init();
				if(data[0].back_code == "200"){
					 
				}else{
					//alert("获取信息失败！");  
					
					return;
				}
				
				 
			}
		});
		
	}else{
		window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8f3d3f6beade3091&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
		
	} 
}else{
	
	
	$.ajax({
		type:"post",
 		url: 'http://www.daodaketang.com/educate/user/show_my_info.do',
 		dataType:"json",
 		data:{"open_id":openid},
 		success: function(data){ 


 			if(data[0].user.head != null && data[0].user.head != ""){ 
  				 	 
  			}else{
  				
  				setCookie_openid("newopenid",null);
  window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8f3d3f6beade3091&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
  				
  		//location.reload();
  	  				 
  			}
 			 
 		}
 		
	});
	
} 
 
//写cookies
 function setCookie_openid(name,value)
 {
	 var Days = 30;
	 var exp = new Date();
	 exp.setTime(exp.getTime() + Days*24*60*60*1000);
	 document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
 }
