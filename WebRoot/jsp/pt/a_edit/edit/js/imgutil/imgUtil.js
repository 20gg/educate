
/**
 * 调用方法:
 * imgUtil.init(function(){
		
		//alert(JSON.stringify(this.chooseArr));
		
	});
 */


var imgUtil = {};
imgUtil.outputFun={};
imgUtil.data = null;
imgUtil.chooseArr = [];
imgUtil.nowpath = "";
imgUtil.closeShow = function() {
	$(".imgmodal").remove();
};

imgUtil.init = function(obj) {
	
	this.outputFun=obj;
	this.showimg({
		imgpath : "/img/"
	});
}
imgUtil.showimg = function(dirobj) {
	var obj = this;
	$(".imgmodal").remove();

	$
			.ajax({

				type : "post",
				url : getRootPath() + "/img/queryImg.do",
				data : dirobj,
				dataType : "json",
				success : function(data) {
					obj.data = data;
					var html = '';

					html += '<div class="imgmodal" >';
					html += '<div class="bgcover" onclick="imgUtil.closeShow()"></div>';
					html += '<div class="imgmain" >';
					html += '<div class="imgtitle">我的图片<span class="closeimg" onclick="imgUtil.closeShow()">×</span></div>';

					if (data != null && data.list != null
							&& data.list.length > 0) {
						html += '<div class="imgarea">';
						var list = data.list;
						for ( var i = 0, len = list.length; i < len; i++) {
							var one = list[i];
							if (one.type == "file") {
								html += '<div class="oneimg" onclick="imgUtil.chooseImg(this)" data="'
										+ one.name + '">';
								html += '<img src="' + getRootPath() + one.name
										+ '">';
								html += '<span>'+one.fileName+'</span>';
								html += '<div class="arrow"><div class="arrowfont"></div></div>';
								html += '</div>';

							} else {

								html += '<div class="onedir"  ondblclick="imgUtil.showSubImg(\''
										+ one.name + "/" + '\')">';
								html += '<img src="' + getRootPath()
										+ '/jsp/pt/a_edit/edit/js/imgutil/dir.jpg">';
								html += '<span>' + one.name + "/" + '</span>';
								html += '<div class="arrow"><div class="arrowfont"></div></div>';
								html += '</div>';
							}

						}

						if (dirobj && dirobj.imgpath) {

							imgUtil.nowpath = dirobj.imgpath;

							var arr = dirobj.imgpath.split("/");
							var len = arr.length;
							if (len > 3) {
								var pstr = "";
								for ( var j = 0; j < len - 2; j++) {
									pstr += arr[j] + "/";
								}

								html += '<div class="onetuichu"  onclick="imgUtil.showSubImg(\''
										+ pstr + '\')">';

								html += '</div>';
							}

						}

						html += '</div>';

						html += '<div class="weiimg">';

						html += '<div class="obtn  addbtning" onclick="imgUtil.openfile()">上传图片</div>';

						html += '<div class="pagebtns">';
						html += '<span class="fpage">共' + data.rowCount
								+ '个</span>';
						html += '<span class="fpage">' + data.pageNum + '/'
								+ data.pageCount + '页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage(1)">首页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage('
								+ (parseInt(data.pageNum) - 1)
								+ ')">上一页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage('
								+ (parseInt(data.pageNum) + 1)
								+ ')">下一页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage('
								+ parseInt(data.pageCount) + ')">尾页</span>';
						html += '<input type="text" class="fpage fbtn" value="'
								+ data.pageNum + '" >';
						html += '<span class="fpage fbtn" id="gopagenum"  onclick="imgUtil.gopage2(this)">跳转</span>';

						html += '</div>';

						html += '</div>';

					} else {

						html += '<div class="imgarea">';

						html += '</div>';

						html += '<div class="weiimg">';

						html += '<div class="obtn  addbtning" onclick="imgUtil.openfile()">上传图片</div>';

						html += '</div>';

					}

					html += '<div class="obtn choosebtn"  onclick="imgUtil.returnImg()">确定</div>';
					html += '<div class="obtn cancelbtn" onclick="imgUtil.closeShow()">取消</div>';
					html += '<div class="obtn delbtning" onclick="imgUtil.delimg()">删除</div>';
					html += '</div>';

					html += '<div id="filediv">';
					html += '<form id="imgfrom" >';
					html += '<input type="hidden" id="pathhiden" name="imgpath">';
					html += '<input type="file" name="file" id="uploadimg"  accept="image/*"  onchange="imgUtil.ajaxUploadImg(this)">';

					html += '</form>';
					html += '</div>';

					html += '</div>';

					$("body").append(html);

				}

			});

};

imgUtil.gopage = function(num) {
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

	this.showimg({
		page : num,
		imgpath : this.nowpath
	});

};

imgUtil.gopage2 = function(obj) {
	var num = $(obj).closest("div").find("input").val();
	this.gopage(num);
};
imgUtil.showSubImg = function(dir) {

	this.showimg({
		imgpath : dir
	});
};
imgUtil.chooseImg = function(obj) {

	$(obj).toggleClass("checkon");

};

imgUtil.openfile = function() {

	var obj = document.getElementById("uploadimg");
	$(document.getElementById("pathhiden")).val(this.nowpath);

	$(obj).trigger("click");

};
imgUtil.ajaxUploadImg = function(obj) {
	var form = $(obj).closest("#imgfrom");

	var file = $(obj).val();
	if (file == "") {
		return;
	}

	var formData = new FormData(form[0]);

	$.ajax({
		type : "post",
		url : getRootPath() + "/img/uploadImg.do",
		data : formData,
		dataType : 'text',
		cache : false,
		processData : false,
		contentType : false,
		success : function(data) {
			if (data != null) {
				error("上传成功");
			}

			$(document.getElementById("gopagenum")).trigger("click");

		},

	});

};

imgUtil.delimg = function() {

	var arr = [];
	$(".oneimg.checkon").each(function() {

		arr.push($(this).attr("data"));

	});
	$.ajax({
		type : "post",
		url : getRootPath() + "/img/delimg.do",
		data : {
			arr : JSON.stringify(arr)
		},
		dataType : "text",
		success : function(data) {
			if (data != null) {
				error("删除成功");
			}

			$(document.getElementById("gopagenum")).trigger("click");
		}

	});

};

imgUtil.returnImg = function() {

	var arr = [];
	$(".oneimg.checkon").each(function() {

		arr.push($(this).attr("data"));

	});
	this.chooseArr = arr;
	$(".imgmodal").remove();
	
	this.outputFun();
	

	//alert(JSON.stringify(arr));
};