
var yf_order_id = "";
 

//调用微信支付
function into_pay(o){ 
	
	yf_order_id = o; 
	
	// 仅微信支付
	$.ajax({
		type : "post",
		url : pt_path+"wxpay/ajaxnewpay.do",
		data : {"order_id": yf_order_id},
		dataType : "text",
		success : function(data) {
			//document.getElementById('loading').style.display = "none";

			if (typeof WeixinJSBridge == "undefined") {
				if (document.addEventListener) {
					document.addEventListener(
							'WeixinJSBridgeReady',
							onBridgeReady, false);
				} else if (document.attachEvent) {
					document.attachEvent(
							'WeixinJSBridgeReady',
							onBridgeReady);
					document.attachEvent(
							'onWeixinJSBridgeReady',
							onBridgeReady);
				}
			} else {
				onBridgeReady(JSON.parse("{" + data + "}"));
			}

		}

	});
}

function onBridgeReady(datas) {

	WeixinJSBridge.invoke(
				'getBrandWCPayRequest',
				datas,
				function(res) {

					WeixinJSBridge.log(res.err_msg);
					if (res.err_msg == "get_brand_wcpay_request:ok") { 
						alert("支付成功!");
						pay_result(1);

					} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
						alert("用户取消支付!");
						pay_result(2);
					} else {
						alert("支付失败!");
						pay_result(2);
					}
				});
}

//查询支付结果
function pay_result(code){
	$.ajax({
		type : "post",
		url : pt_path+"wxpay/goPayResult.do",
		data : {"order_id":yf_order_id,"kind":1},
		dataType : "json",
		success : function(data) {
			
			if(code == 1){
				window.location.href=pt_path+"jsp/wx/ys_success.html";
			}else{
				window.location.href=pt_path+"wxpay/into_qrcode.do";
			}
			  
		}
	});
}
