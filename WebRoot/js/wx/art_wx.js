document.write("<script src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'>" + "<" + "/script>");
(function() {    
	if (typeof WeixinJSBridge == "object" && typeof WeixinJSBridge.invoke == "function") {
	        handleFontSize();
	} else {        
	if (document.addEventListener) {
	    document.addEventListener("WeixinJSBridgeReady", handleFontSize, false);
	} else if (document.attachEvent) {
	    document.attachEvent("WeixinJSBridgeReady", handleFontSize);
	    document.attachEvent("onWeixinJSBridgeReady", handleFontSize);  }
	}    
	function handleFontSize() {
        // 设置网页字体为默认大小
        WeixinJSBridge.invoke('setFontSizeCallback', { 'fontSize' : 0 });        
        // 重写设置网页字体大小的事件
        WeixinJSBridge.on('menu:setfont', function() {
            WeixinJSBridge.invoke('setFontSizeCallback', { 'fontSize' : 0 });
        });
    }
})();

	$(function(){
		
		
		$.get(pt_path+'wx/wxsign.do?url='+url,
				function(data) {

					wx.config({
						debug : false,
						appId : data.appId,
						timestamp : data.timestamp,
						nonceStr : data.nonceStr,
						signature : data.signature,
						jsApiList : ['onMenuShareTimeline',
								'onMenuShareAppMessage' ]
					});
					
					wx.ready(function() {

				
				
						var showmenus = [
								'menuItem:share:appMessage',
								'menuItem:share:timeline' ];

						wx.showMenuItems({
							menuList : showmenus
						});

						wx.showOptionMenu();
						wx.onMenuShareTimeline({
							
									// 分享标题
									title : "学管理 上到答",// 分享
									link : 'http://www.daodaketang.com/educate/jsp/wx/art/art_info.html?art_id='+art_id+"&share_id="+openid+"&share_ul="+"http://www.daodaketang.com/educate/jsp/wx/art/art_info.html", // 分享链接
									imgUrl : "http://www.daodaketang.com/educate/img/logo.jpg", // 分享图标
									type : 'link', // 分享类型,music、video或link，不填默认为link
									success : function() {
										// 用户确认分享后执行的回调函数
									},
									cancel : function() {
										// 用户取消分享后执行的回调函数
									}
								});

						//分享给朋友
						wx.onMenuShareAppMessage({
									title : '学管理 上到答', // 分享标题
									desc : "到答课堂  让管理从未如此简单", // 分享描述
									link : 'http://www.daodaketang.com/educate/jsp/wx/art/art_info.html?art_id='+art_id+"&share_id="+openid+"&share_ul="+share_ul, // 分享链接
									imgUrl : "http://www.daodaketang.com/educate/img/logo.jpg", // 分享图标
									type : 'link', // 分享类型,music、video或link，不填默认为link
									success : function() {
										// 用户确认分享后执行的回调函数
									},
									cancel : function() {
										// 用户取消分享后执行的回调函数
									}
								});
 

				}); 
				});
		
		
	});
	  
	