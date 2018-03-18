var noUrlstr=-1;

var surl=location.href.split('#')[0];
var url=encodeURIComponent(surl);

var bpath= getRootPath();

  var openid="oLir8jsziazLpKT0b6JSSbQ0icR4";  
	// var openid=getCookie("newopenid"); 
	
	if(openid==null){
	
	var code=GetQueryString("code");
	if(code!=null&&code.trim()!=""){
	
	
	
	$.ajax({
				type:"post",
				url:bpath+"/wx/ajaxopenid.do",
				data:{"code":code},
				dataType:"text",
				success:function(data){
					if(parseInt(data)==200){
					
						openid=getCookie("newopenid");
						return;
					}else{
					alert("获取信息失败");
					window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9fb99edfa3c42006&redirect_uri="+url+"&response_type=code&scope=snsapi_base#wechat_redirect";
						return;
					
					}
						
					

				}
				
				
			});
		
			
	
	}else{
	
	window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9fb99edfa3c42006&redirect_uri="+url+"&response_type=code&scope=snsapi_base#wechat_redirect";
		

	}
	
	
	}
	
	