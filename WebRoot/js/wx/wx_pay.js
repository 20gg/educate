
var order_id = "";
var kind = 2;

//调用微信支付
function into_pay(o,k){ 
	
	order_id = o;
	kind = k; 
	
	// 仅微信支付
	$.ajax({
		type : "post",
		url : pt_path+"wxpay/ajaxWXpay.do",
		data : {"order_id": order_id,"kind":kind},
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
						pay_result();

					} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
						alert("用户取消支付!");
						window.location.href=pt_path+'jsp/pt/a_edit/show/1495714795.html';
					} else {
						alert("支付失败!");
						window.location.href=pt_path+'jsp/pt/a_edit/show/1495714795.html';
					}
				});
}

//查询支付结果
function pay_result(){
	$.ajax({
		type : "post",
		url : pt_path+"wxpay/goPayResult.do",
		data : {"order_id": order_id,"kind":kind},
		dataType : "json",
		success : function(data) {
			 
			if(kind == 2||kind==3){
				//vip 
				
				 
				window.location.href=pt_path+'jsp/wx/art/new_file.html?v=0.1';
			}else{
				 
					if(data[0].pay_back == 1){
						into_c_page();
				}else{
					
					window.location.href=pt_path+'jsp/pt/a_edit/show/1495714795.html';
				}
				
			} 
			 
		}
	});
}
