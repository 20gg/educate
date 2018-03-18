var ED = {};
// 模块组:类型:type,显示方法:showFun,编辑工具:editFun
ED.MODELS = [  {
	type : 1,
	name : "店铺导航"
} ];

ED.pid = 0;
ED.nowIndex = 0;
ED.nowObj = {};
ED.dragModel = {};
ED.data = {};// 页面数据结构
ED.data.title = "页面标题";
ED.data.desc = "页面描述";
ED.data.bgcolor = "#F0F0F0"; // 背景色
/**
 * 1搜索栏2banner3菜单导航 4空行 5领券6多图轮播 7标题 8图片9商品10辅助线 11文本导航 12店铺导航
 */
ED.data.models = [];// 页面模块

ED.setTitle = function(text) {
	if (text != null && text != "" && text.length > 0)
		$("#title h3").text(text);
};
ED.setBgColor = function(text) {
	if (text)
		$("#target").css('background-color', text);
};
ED.setModels = function(models) {
	var html = "";

	if (this.data.models && this.data.models.length > 0) {

		for ( var i = 0; i < this.data.models.length; i++) {
			var one = this.data.models[i];
			var type = parseInt(one.type);
			html += this.MODELS[(type - 1)].showFun(one);

			$("#target").html(html);

		}
	}
};

/**
 * 
 * 根据data数据显示到屏幕上面
 */
ED.showPage = function() {
	if (this.data) {
		ED.setTitle(this.data.title);
		ED.setBgColor(this.data.bgcolor);
		ED.setModels(this.data.bgcolor);

	}

};

/**
 * 创建页面
 * 
 */
ED.createPage = function() {
	var d = {};
	if (ED.pid > 0) {
		d.pid = ED.pid;
	}

	var str = JSON.stringify(this.data);
	d.data = str;
	$.ajax({
		type : "post",
		url : getRootPath() + "/aedit/createPage.do",
		data : d,
		dataType : "json",
		success : function(data) {
			if (data != "-400") {
				alert("创建成功");
				ED.pid = parseInt(data);

			}

		}
	});

};
/**
 * 修改
 * 
 */
ED.updateBTNS = function() {
	var d = {};
	

	var str = JSON.stringify(this.data);
	d.data = str;
	
	jspost( getRootPath() + "/aedit/updateBTNS.do",d);
	

};
/**
 * 预览
 * 
 */

// 点击编辑
ED.clickDo = function(str) {

	$("#phone").on("click", str, function() {
		$(".neededit").removeClass("checkin");
		$("#title").removeClass("checkin");
		if (str == ".neededit") {

			$(this).toggleClass("checkin");
			var index = $(this).index();

			// 打开编辑界面
			ED.editMain(index);
		} else if (str == "#title") {

			$(this).toggleClass("checkin");

			// 打开编辑界面
			ED.editMain2($(this));

		}

	});
};

ED.editMain2 = function(dom) {
	this.nowIndex = -1;// 当前编辑对象;

	var dom = $("#title");

	var X = dom.offset().top;
	var Y = dom.offset().left + 400;
	var editDiv = $("#editDiv");
	editDiv.offset({
		top : X,
		left : Y
	});
	editDiv.css({
		'display' : 'block',
		'min-height' : '60px',
		'width' : '500px',
		'border' : '1px solid #666',
		'background' : '#F0F0F0',
		'box-sizing' : 'border-box',
		'-webkit-box-sizing' : 'border-box',
		'-ms-box-sizing' : 'border-box',
		'-moz-box-sizing' : 'border-box',
		'border-radius' : '8px',
	});

	// 头
	var html = ED.createEditDIV2(this.data.title, this.data.desc);
	$("#divmain").html(html);

};
ED.editMain = function(index) {
	this.nowIndex = index;// 当前编辑对象;

	var dom = $($(".neededit")[index]);

	var X = $(dom).offset().top;
	var Y = $(dom).offset().left + 400;
	var editDiv = $("#editDiv");
	editDiv.offset({
		top : X,
		left : Y
	});
	editDiv.css({
		'display' : 'block',
		'min-height' : '60px',
		'width' : '400px',
		'border' : '1px solid #666',
		'background' : '#F0F0F0',
		'box-sizing' : 'border-box',
		'-webkit-box-sizing' : 'border-box',
		'-ms-box-sizing' : 'border-box',
		'-moz-box-sizing' : 'border-box',
		'border-radius' : '8px',
	});

	var model = this.data.models[index];

	var html = ED.createEditDIV(model);
	$("#divmain").html(html);

};

ED.createEditDIV2 = function(title, desc) {

	var html = "";

	html += "<div class='row'  >";
	html += "<div class='col-xs-3'>标题</div>";
	html += "<div class='col-xs-9'><input type='text' class='pagetitle' value='"
			+ title + "' onblur='ED.changePagehead(this);'/></div>";
	html += "</div>";
	html += "<div class='row'  >";
	html += "<div class='col-xs-3'>描述</div>";
	html += "<div class='col-xs-9'><input type='text' class='pagedesc' value='"
			+ desc + "' onblur='ED.changePagehead(this);'/></div>";
	html += "</div>";

	return html;

};

ED.changePagehead = function(obj) {
	if ($(obj).hasClass("pagetitle")) {

		this.data.title = $(obj).val();
	} else {
		this.data.desc = $(obj).val();
	}
	this.showPage();

};
/**
 * 修改模版界面
 * 
 * 1搜索栏2banner3菜单导航 4空行 5领券6多图轮播 7标题 8图片9商品10辅助线 11文本导航 12尾部菜单
 */
ED.createEditDIV = function(model) {
	var type = parseInt(model.type);
	return this.MODELS[(type - 1)].editFun(model);

};
ED.clipIimg=function(obj){
	
	this.nowObj=obj;
	
	$('#app_upload').trigger('click');
};

ED.addWeiBtnRow = function(obj) {

	var html = "";
	var i = $(obj).siblings(".row").length-1;
	html += "<div class='row'  >";

	html += "<div class='col-xs-3'><input type='checkbox' onclick='ED.changeWeiBtnsInfo();'  name='oneWeicd'  />导航"
			+ (i + 1) + "</div>";
	html += "<div class='col-xs-9'><input type='text' class='cdnameedit' value='' onblur='ED.changeCDName(this);'/></div>";

	html += "<div class='col-xs-3'>链接:</div>";

	html += "<div class='col-xs-9'><span style='color:blue' onclick='ED.changeCDUrl(this);' class='urlinfo' url=''>请选择链接</span></div>";
	html += "<div class='col-xs-3'>图标:</div>";
	
		html += "<div class='col-xs-9'><img src='' class='' style='width:30px;min-height:30px;'  onclick=\"ED.clipIimg(this)\"></div>";
	
	html += "</div>";
	$(obj).before(html);
};

/**
 * 尾部菜单
 * 
 */
ED.changeWeiBtnsInfo = function() {

	var btns = [];
	var index = this.nowIndex;
	$("#divmain input[name='oneWeicd']:checked").each(function() {

		var dom = $(this).closest(".row");

		var name = "";
		var url = "";
		var icon="";
		if (dom.find(".cdnameedit").val())
			name = dom.find(".cdnameedit").val();
		if (dom.find(".urlinfo").attr("url"))
			url = dom.find(".urlinfo").attr("url");
		if (dom.find("img").attr("icon"))
			icon=dom.find("img").attr("icon");

		btns.push({
			name : name,
			url : url,
			icon:icon
		});

	});
	this.data.models[index].btns = btns;
	this.showPage();
};

ED.changeLineKind = function() {
	var kind = parseInt($("#divmain input[name='linekind']:checked").val());
	this.data.models[this.nowIndex].kind = kind;

	this.showPage();
};

ED.addImgDivRow = function(obj) {
	var html = "";
	var i = $(obj).siblings(".row").length;
	html += "<div class='row' >";

	html += "<div class='col-xs-3'><input type='checkbox' onclick='ED.checkImgList();'  name='oneImg'  />图片"
			+ (i + 1) + "</div>";
	html += "<div class='col-xs-9'><img src='"
			+ getRootPath()
			+ "/a_edit/edit/img/common/changebg.jpg' style='width:120px;'  title='点击切换' class='imgone' onclick='ED.changeImg(this);'/><div class='imgBTN'><button class='sureBTN' onclick='ED.sureImgTwo(this)'>确定</button><button class='cancelBTN' onclick='ED.cancelImgTwo(this)'>取消</button></div></div>";
	html += "<div class='col-xs-3'></div>";
	html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeCDUrl(this);' class='urlinfo' url=''>请添加图片</span></div>";

	html += "</div>";
	$(obj).before(html);

};

/**
 * 判断图片
 * 
 */
ED.checkImgList = function() {
	var imgs = [];
	var index = this.nowIndex;
	$("#divmain input[name='oneImg']:checked").each(function() {

		var dom = $(this).closest(".row");

		var img = "";
		var url = "";
		if (dom.find(".imgone").attr("src"))
			img = dom.find(".imgone").attr("src");
		if (dom.find(".urlinfo").attr("url"))
			url = dom.find(".urlinfo").attr("url");

		imgs.push({
			img : img,
			url : url
		});

	});
	this.data.models[index].imgs = imgs;
	this.showPage();

};

/**
 * 选择券领取任务
 * 
 */
ED.chooseRoll = function(obj) {

	alert("开发中。。。");

};

/**
 * 选择券领取任务
 * 
 */
ED.checkRoll = function() {

	var coupons = [];
	var index = this.nowIndex;
	$("#divmain input[name='oneRoll']:checked").each(function() {

		var dom = $(this).closest(".row");

		var img = "";
		var rid = "";
		if (dom.find(".rollone").attr("src"))
			img = dom.find(".rollone").attr("src");
		if (dom.find(".rollone").attr("rid"))
			rid = dom.find(".rollone").attr("rid");

		coupons.push({
			img : img,
			rid : rid
		});

	});
	this.data.models[index].coupons = coupons;
	this.showPage();

};

/**
 * 修改高度
 * 
 */
ED.controlHeight = function(obj) {
	var index = this.nowIndex;
	var dom = this;
	obj.onmousedown = function(e) {
		var x = (e || b.event).clientX;
		var s = x - $(this).offset().left;
		var max = this.offsetWidth;
		var a = parseInt(100 * s / max);
		$(".progress-bar").css("width", a + "%");
		$(this).closest(".col-xs-9").find("#resbar").html(a + "PX");
		dom.data.models[index].height = a;
		dom.showPage();
	};
};
ED.controlHeight2 = function(obj) {
	var index = this.nowIndex;
	var dom = this;
	obj.onmousedown = function(e) {
		var x = (e || b.event).clientX;
		var s = x - $(this).offset().left;
		var max = this.offsetWidth;
		var a = parseInt(100 * s / max);
		$(".progress-bar").css("width", a + "%");
		$(this).closest(".col-xs-9").find("#resbar").html(a + "%");
		dom.data.models[index].logoleft = a;
		dom.showPage();
	};
};

/**
 * 修改链接
 * 
 */
ED.changeCDUrl = function(obj) {
	$("#searchUrlType").val("");
	this.nowObj = obj;
	openUrlModel();

};
ED.changeTypeURL = function(obj) {
	var type = $(obj).val();
	if (type != "" && parseInt(type) == 999) {
		$("#urllist")
				.html(
						"<span class='form-control' style='width:100px;font-size:12px;float:left'>请填写地址:</span><input type='text' class='form-control' style='width:250px;float:left' onblur='ED.chooseUrlByModel2(this);' placeholder='例如:http://www.chuange.cn' />");

	} else {

		var datainfo = {};

		datainfo.type = type;

		openUrlModel(datainfo);
	}
};
/**
 * 选择链接
 * 
 */
ED.chooseUrlByModel = function(url) {
	$("#chooseUrls").modal("hide");
	$(this.nowObj).html(url);
	$(this.nowObj).attr("url", url);

	this.changeWeiBtnsInfo();

};
ED.chooseUrlByModel2 = function(obj) {
	$("#chooseUrls").modal("hide");
	$(this.nowObj).attr("url", $(obj).val());
	$(this.nowObj).html(url);
	this.changeWeiBtnsInfo();

};

/**
 * 修改名称
 * 
 */
ED.changeCDName = function(obj) {
	this.nowObj = obj;
	
	this.changeWeiBtnsInfo();

};

/**
 * 选择图标
 * 
 */
ED.changeCDIcon = function(obj) {
	$(".chooseicon").removeClass("checkon");
	this.nowObj = obj;
	var icon = $(obj).attr("icon");
	if (icon != null && icon != "" && parseInt(icon) > 0) {
		$(".icon" + icon).toggleClass("checkon");
	}
	$("#chooseIcon").modal("show");

};

/**
 * 选择菜单
 * 
 * @param obj
 */
ED.changeCDInfo = function() {
	var cd = [];
	var index = this.nowIndex;
	$("#divmain input[name='onecd']:checked").each(function() {

		var dom = $(this).closest(".row");
		var icon = 0;
		var name = "";
		var url = "";
		if (dom.find(".icondiv").attr("icon"))
			icon = dom.find(".icondiv").attr("icon");
		if (dom.find(".cdnameedit").val())
			name = dom.find(".cdnameedit").val();
		if (dom.find(".urlinfo").attr("url"))
			url = dom.find(".urlinfo").attr("url");

		cd.push({
			icon : icon,
			name : name,
			url : url
		});

	});
	this.data.models[index].cd = cd;
	this.showPage();
};




// ////////////////////////////////////////////

ED.changeColor = function(obj) {
	var index = this.nowIndex;
	var domModel = this.data.models[index];
	var type = parseInt(domModel.type);

	var dom = document.getElementById('color');
	dom.value = domModel.color;

	this.nowObj = obj;
	dom.click();

};
ED.chooseColor = function(obj) {
	var index = this.nowIndex;
	var domModel = this.data.models[index];
	$(this.nowObj).css("background",obj.value);
	domModel.color = obj.value;
	this.showPage();

};

/**
 * 删除元素
 */
ED.deleteIndex = function(index) {
	this.data.models.splice(index, 1);
	this.showPage();

};


ED.createAddBtn = function() {
	var m = this.MODELS;
	var html = "";
	for ( var i = 0; i < m.length; i++) {
		var one = m[i];
		html += '<div class="editbtn" data="' + one.type + '">' + one.name
				+ '</div>';
	}
	return html;

};

ED.showThisLogo = function(obj) {

	if ($(obj).prop("checked") == true) {

		this.data.models[this.nowIndex].islogo = 1;

	} else {

		this.data.models[this.nowIndex].islogo = -1;
	}

	this.showPage();
};
ED.showThisSname = function(obj) {

	if ($(obj).prop("checked") == true) {

		this.data.models[this.nowIndex].issname = 1;

	} else {

		this.data.models[this.nowIndex].issname = -1;
	}

	this.showPage();
};

/**
 * 
 * 初始化
 * 
 */
ED.init = function() {

	ED.showPage();
	ED.clickDo(".neededit");
	ED.clickDo("#title");
	/*$("#editarea").html(ED.createAddBtn());*/
};

/**
 * 模块编辑工具
 * 
 */




ED.MODELS[0].editFun = function(model) {
	var html = "";
	var btns = model.btns;
	var size = btns.length;
	for ( var j = 0; j < size; j++) {
		var onej = btns[j];
		var url = onej.url;
		if (url == null || url == "")
			url = "请选择链接";

		html += "<div class='row'  >";

		html += "<div class='col-xs-3'><input type='checkbox' onclick='ED.changeWeiBtnsInfo();'  name='oneWeicd' checked />导航"
				+ (j + 1) + "</div>";
		html += "<div class='col-xs-9'><input type='text' class='cdnameedit' value='"
				+ onej.name + "' onblur='ED.changeCDName(this);'/></div>";

		html += "<div class='col-xs-3'>链接:</div>";

		html += "<div class='col-xs-9'><span style='color:blue' onclick='ED.changeCDUrl(this);' class='urlinfo' url='"
				+ onej.url + "'>" + url + "</span></div>";
		
		html += "<div class='col-xs-3'>图标:</div>";
		if(onej.icon&&onej.icon!=""){
			html += "<div class='col-xs-9'><img src='"+getRootPath()+onej.icon+"' class='' style='width:30px;min-height:30px;' onclick=\"ED.clipIimg(this)\" icon='"+onej.icon+"'></div>";
		}else{
			html += "<div class='col-xs-9'><img src='' class='' style='width:30px;min-height:30px;' onclick=\"ED.clipIimg(this)\" icon='"+onej.icon+"'></div>";
		}
		

		html += "</div>";

	}

	html += "<div class='addImgDIV' onclick='ED.addWeiBtnRow(this)'>+</div>";
	html += "<div class='row'><div class='col-xs-3'>文字颜色</div>";
	html += "<div class='col-xs-9'><span id='colordiv'  style='background:"
	+ model.color
	+ "'  onclick='ED.changeColor(this);'></span></div></div>  ";
	return html;
};
/**
 * 
 * 模块页面显示工具
 * 
 */



ED.MODELS[0].showFun = function(one) {
	var html = "";
	html += '<div class="nav neededit" ><b class="dragdo"></b><nav><div id="nav_ul" class="nav_"><ul class="box">';
	if (one.btns != null) {
		for ( var j = 0; j < one.btns.length; j++) {
			var onej = one.btns[j];
			html += '<li onclick="window.location.href=\"' + onej.url
					+ '\"">';
		
			html+='<a class="">';
			
			html+='<img src="'+getRootPath()+onej.icon+'" style="width:25px;margin-top:3px">';
			
			html+='<label style="font-size: 12px;width:100%;';
			
			if(one.color!=null&&one.color!=""){
				html+="color:"+one.color;
			}
			
			html+='">'+ onej.name + '</label></a></li>';
		}
	}
	html += '</ul></div></nav></div>';
	return html;
};

