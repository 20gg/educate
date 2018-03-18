
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


//var openid=1;  
var openid=getCookie("newopenid");

if(openid==null){
	 
	var code=GetQueryString("code");
	 
	
	if(code!=null&&code.trim()!=""){
		$.ajax({
			type:"post",
			url:"http://www.daodaketang.com/educate/wx/ajaxopenid.do",
			data:{"code":code},
			dataType:"text",
			success:function(data){
				 
				
				if(parseInt(data)==200){
				
					openid=getCookie("newopenid");
				
				}else{
					alert("获取信息失败");
					window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8f3d3f6beade3091&redirect_uri="+url+"&response_type=code&scope=snsapi_base#wechat_redirect";
					return;
				}
				
			//	alert(openid);
			}
		});
	
	}else{
		window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8f3d3f6beade3091&redirect_uri="+url+"&response_type=code&scope=snsapi_base#wechat_redirect";
	
	}
}
	

	
	
	
	