var goodsUtil = {};
goodsUtil.outputFun={};
goodsUtil.data = null;
goodsUtil.chooseArr = [];
goodsUtil.nowpath = "";
//查找条件
goodsUtil.chooseData={type:2,page:1};//type:1课程2免费课程3专题4排行,goods:排除的商品 ，likename:模糊搜索,page 第几页
goodsUtil.closeShow = function() {
	$(".goodsmodal").remove();
};

goodsUtil.init = function(fun,datainfo) {
	
	this.outputFun=fun;
	if(datainfo){
		this.chooseData=datainfo;
		this.showGoods(datainfo);//type:1课程2免费课程3专题4排行,goods:排除的商品 ，likename:模糊搜索
	}else{
		this.showGoods(this.chooseData);
	}
	
};
goodsUtil.showGoods = function(datainfo) {
	var obj = this;
	$(".goodsmodal").remove();

	$
			.ajax({

				type : "post",
				url : getRootPath() + "/goodsutil/queryAllGoods.do",
				data : datainfo,
				dataType : "json",
				success : function(data) {
					obj.data = data;
					var html = '';
					
					
					
					
					html += '<div class="goodsmodal" >';
					html += '<div class="bgcover" onclick="goodsUtil.closeShow()"></div>';
					html += '<div class="imgmain" >';
					html += '<div class="imgtitle">';
					var type=parseInt(datainfo.type);
					if(type==1){
						html += '课程';
					}else if(type==2){
						html += '免费课程';
					}else if(type==3){
						html += '专题';
					}else if(type==4){
						html += '排行';
					}
					
					html +='<span class="closeimg" onclick="goodsUtil.closeShow()">×</span></div>';

					if (data != null && data.list != null
							&& data.list.length > 0) {
						html += '<div class="imgarea">';
						var list = data.list;
						for ( var i = 0, len = list.length; i < len; i++) {
							var one = list[i];
							if(type==4){
								
								one.pm=(i+1);
								
							}
								html += '<div class="oneimg" onclick="goodsUtil.chooseImg(this)" data=\''+JSON.stringify(one)+'\'  >';
								html += '<img src="' + one.img
										+ '">';
								html += '<span style="font-size:10px">'
								if(one.name.length<10){
									html+=one.name;
								}else{
									html+=one.name.substring(0,9)+"..";
								}
							
							html+='</span>';
							
							if(type==4){
								html += '<b style="font-size:20px;color:red;position:absolute;top:0;left:0">';
									html +=(i+1);
								html+='</b>';
							
								
							}
							
								
								
								html += '<div class="arrow"><div class="arrowfont"></div></div>';
								html += '</div>';

							

						}

						

						html += '</div>';

						html += '<div class="weiimg">';

						

						html += '<div class="pagebtns">';
						html += '<span class="fpage">共' + data.rowCount
								+ '个</span>';
						html += '<span class="fpage">' + data.pageNum + '/'
								+ data.pageCount + '页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage(1)">首页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage('
								+ (parseInt(data.pageNum) - 1)
								+ ')">上一页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage('
								+ (parseInt(data.pageNum) + 1)
								+ ')">下一页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage('
								+ parseInt(data.pageCount) + ')">尾页</span>';
						html += '<input type="text" class="fpage fbtn" value="'
								+ data.pageNum + '" >';
						html += '<span class="fpage fbtn" id="gopagenum"  onclick="goodsUtil.gopage2(this)">跳转</span>';

						html += '</div>';

						html += '</div>';

					}else{
						html += '<div class="imgarea"><span style="display:block;width:100%;text-align:center;line-height:100px">未找到相关课程/专题</span>';
						html += '</div>';
					}

					html += '<div class="obtn choosebtn"  onclick="goodsUtil.returnImg()">确定</div>';
					html += '<div class="obtn cancelbtn" onclick="goodsUtil.closeShow()">取消</div>';
				
					html += '</div>';

					html += '<div id="filediv">';
					html += '<form id="imgfrom" >';
					html += '<input type="hidden" id="pathhiden" name="imgpath">';
					html += '<input type="file" name="file" id="uploadimg"  accept="image/*"  onchange="goodsUtil.ajaxUploadImg(this)">';

					html += '</form>';
					html += '</div>';

					html += '</div>';

					$("body").append(html);

				}

			});

};

goodsUtil.gopage = function(num) {
	if (num == null) {
		num == 1;
	} else {
		if (parseInt(num) < 1) {
			num = 1;
		}
		if (parseInt(num) > parseInt(this.data.pageCount)) {
			num = parseInt(this.data.pageCount);
		}

	}
	this.chooseData.page=num;
	this.showimg(this.chooseData);

};

goodsUtil.gopage2 = function(obj) {
	var num = $(obj).closest("div").find("input").val();
	this.gopage(num);
};

goodsUtil.chooseImg = function(obj) {

	$(obj).toggleClass("checkon");

};




goodsUtil.returnImg = function() {

	var arr = [];
	$(".oneimg.checkon").each(function() {

		arr.push(JSON.parse($(this).attr("data")));

	});
	this.chooseArr = arr;
	$(".goodsmodal").remove();
	
	this.outputFun();
	

	//alert(JSON.stringify(arr));
};