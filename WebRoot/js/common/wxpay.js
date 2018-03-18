$(function() {

	$("#sub").on('click',
					function() {

						// 仅微信支付
						$
								.ajax({
									type : "post",
									url : "<%=basePath%>cg/pay/goPayByWX.do",
									data : {
										order_id : orderid
									},
									dataType : "text",
									success : function(data) {
										document.getElementById('loading').style.display = "none";

										if (typeof WeixinJSBridge == "undefined") {
											if (document.addEventListener) {
												document.addEventListener(
														'WeixinJSBridgeReady',
														onBridgeReady, false);
											} else if (document.attachEvent) {
												document.attachEvent(
														'WeixinJSBridgeReady',
														onBridgeReady);
												document
														.attachEvent(
																'onWeixinJSBridgeReady',
																onBridgeReady);
											}
										} else {
											onBridgeReady(JSON.parse("{" + data
													+ "}"));
										}

									}

								})

						$("#sub").attr("disabled", true);

					});
});

function onBridgeReady(datas) {

	WeixinJSBridge
			.invoke(
					'getBrandWCPayRequest',
					datas,
					function(res) {

						WeixinJSBridge.log(res.err_msg);
						if (res.err_msg == "get_brand_wcpay_request:ok") {

							alert("支付成功!");
							window.location.href = "<%=basePath%>cg/pay/goPayResult.do?order_id="
									+ orderid + "&orderno=" + orderno;

						} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
							alert("用户取消支付!");
							window.location.href = "<%=basePath%>cg/pay/goPayResult.do?order_id="
									+ orderid + "&orderno=" + orderno;
						} else {
							alert("支付失败!");
							window.location.href = "<%=basePath%>cg/pay/goPayResult.do?order_id="
									+ orderid + "&orderno=" + orderno;
						}
					});
}
