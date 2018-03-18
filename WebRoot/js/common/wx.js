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
		
		$.get(getRootPath()+'/wx/wxsign.do?url='+url,
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
									title : '船歌·商城', // 分享标题
									desc : $("body").attr("desc"), // 分享描述
									link : 'http://www.chuange.cn/', // 分享链接
//									imgUrl : '', // 分享图标
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
									title : '船歌·商城', // 分享标题
									desc : $("body").attr("desc"), // 分享描述
									link : 'http://www.chuange.cn/', // 分享链接
//									imgUrl : 'http://www.lvzhidashu.com/wx/agreen/images/logo2.png', // 分享图标
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
	  
	