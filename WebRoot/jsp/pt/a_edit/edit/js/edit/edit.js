var ED = {};
// 模块组:类型:type,显示方法:showFun,编辑工具:editFun
ED.MODELS = [ {
	type : 1,
	name : "头信息"
}, {
	type : 2,
	name : "公告栏"
}, {
	type : 3,
	name : "菜单导航"
}, {
	type : 4,
	name : "空行"
}, {
	type : 5,
	name : "课程列表"
}, {
	type : 6,
	name : "多图轮播"
}, {
	type : 7,
	name : "标题"
}, {
	type : 8,
	name : "图片"
}, {
	type : 9,
	name : "专题列表"
}, {
	type : 10,
	name : "辅助线"
}, {
	type : 11,
	name : "排行"
}/*, {
	type : 12,
	name : "尾部菜单"
}*/ ];

ED.pid = 0;
ED.nowIndex = 0;
ED.nowObj = {};
ED.dragModel = {};
ED.data = {};// 页面数据结构
ED.data.title = "页面标题";
ED.data.desc = "页面描述";
ED.data.bgcolor = "#F0F0F0"; // 背景色
/**
 * 1搜索栏2banner3菜单导航 4空行 5领券6多图轮播 7标题 8图片9商品10辅助线 11文本导航 12尾部菜单
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
		
		
		$($(".neededit")[this.nowIndex]).toggleClass("checkin");
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
	if (ED.pid!=null) {
		d.pid = ED.pid;
	}

	var str = JSON.stringify(this.data);
	d.data = str;
	$.ajax({
		type : "post",
		url : bpath + "/aedit/createPage.do",
		data : d,
		dataType : "text",
		success : function(data) {
			if (data != "-400") {
				alert("修改成功");
				ED.pid = parseInt(data);

			}

		}
	});

};
/**
 * 修改
 * 
 */
ED.updatePage = function() {
	var d = {};
	if (ED.pid!=null&&ED.pid!="") {
		d.pid = ED.pid;
	}

	var str = JSON.stringify(this.data);
	d.data = str;
	
	alert(d)
	$.ajax({
		type : "post",
		url : bpath + "/aedit/createPage.do",
		data : d,
		dataType : "text",
		success : function(data) {
			if (data != "-400") {
				alert("修改成功");
				ED.pid = parseInt(data);

			}

		}
	});

};
/**
 * 预览
 * 
 */
ED.ylPage = function() {
	var d = {};
	if (ED.pid > 0) {
		d.pid = ED.pid;
	}

	var str = JSON.stringify(this.data);
	d.data = str;
	$.ajax({
		type : "post",
		url : bpath + "/aedit/createPage.do",
		data : d,
		dataType : "text",
		success : function(data) {
			
			orderone(bpath + "/aedit/showpage.do?pid=592646e2ccf5c630560f7055");

		}
	});

};
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

ED.addWeiBtnRow = function(obj) {

	var html = "";
	var i = $(obj).siblings(".row").length;
	html += "<div class='row'  >";

	html += "<div class='col-xs-3'><input type='checkbox' onclick='ED.changeWeiBtnsInfo();'  name='oneWeicd'  />导航"
			+ (i + 1) + "</div>";
	html += "<div class='col-xs-9'><input type='text' class='cdnameedit' value='' onblur='ED.changeCDName(this);'/></div>";

	html += "<div class='col-xs-3'></div>";

	html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeCDUrl(this);' class='urlinfo' url=''>请选择链接</span></div>";

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
		if (dom.find(".cdnameedit").val())
			name = dom.find(".cdnameedit").val();
		if (dom.find(".urlinfo").attr("url"))
			url = dom.find(".urlinfo").attr("url");

		btns.push({
			name : name,
			url : url
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
			+ bpath
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
		if (dom.find(".imgone").attr("data"))
			img = dom.find(".imgone").attr("data");
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
 * 图片kind 
 */
ED.chooseImgkind = function(OBJ) {
	var kind=$(OBJ).closest("div").find("input[name='kind']:checked").val();
	
	
	var imgs = [];
	var index = this.nowIndex;
	$("#divmain input[name='oneImg']:checked").each(function() {

		var dom = $(this).closest(".row");

		var img = "";
		var url = "";
		if (dom.find(".imgone").attr("data"))
			img = dom.find(".imgone").attr("data");
		if (dom.find(".urlinfo").attr("url"))
			url = dom.find(".urlinfo").attr("url");

		imgs.push({
			img : img,
			url : url
		});

	});
	this.data.models[index].imgs = imgs;
	this.data.models[index].kind=kind;
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
 * 修改名称
 * 
 */
ED.changeCDName = function(obj) {
	this.nowObj = obj;
	var dom = this.data.models[this.nowIndex];
	var type = parseInt(dom.type);
	if (type == 3) {

		ED.changeCDInfo();
	} else if (type == 7 || type == 11) {
		dom.name = $(obj).val();
		this.showPage();
	} else if (type == 12) {
		this.changeWeiBtnsInfo();
	}

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
		if (dom.find("img").attr("data"))
			icon = dom.find("img").attr("data");
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

/**
 * 图片工具
 * 
 * @param obj
 */
ED.changeImg = function(obj) {
	this.nowObj = obj;
	// 打开图片上传器
	imgUtil.init(function(){
		if(this.chooseArr.length==1){

			$(obj).attr("src", bpath + this.chooseArr[0]);
			$(obj).attr("data", this.chooseArr[0]);
			$(obj).siblings(".imgBTN").css("display", "block");
		}
		//alert(JSON.stringify(this.chooseArr));
		
	});

};

/**
 * 图标
 */
ED.sureImgOne3 = function(obj) {

	$(obj).closest(".imgBTN").css("display", "none");
	
	this.changeCDInfo();

};
ED.cancelImgOne3 = function(obj) {
	var index = this.nowIndex;
	$(obj).closest(".imgBTN").css("display", "none");
	var icon = this.data.models[index].cd[$(obj).closest(".row").index()].icon;
	if(icon!=null){
		var o=	$(obj).closest(".col-xs-9").find("img");
		o.attr("src", bpath+icon);
		o.attr("data",icon);
	}

	this.changeCDInfo();

};

/**
 * 图片
 */

ED.sureImgOne = function(obj) {

	var index = this.nowIndex;
	$(obj).closest(".imgBTN").css("display", "none");
	this.data.models[index].img = $(obj).closest(".col-xs-9").find("img").attr(
			"data");
	this.showPage();

};
ED.sureImgTwo = function(obj) {

	$(obj).closest(".imgBTN").css("display", "none");

	this.checkImgList();

};

ED.cancelImgOne = function(obj) {
	var index = this.nowIndex;
	$(obj).closest(".imgBTN").css("display", "none");
	var img = this.data.models[index].img;
	if (img == null || img == "")
		img = bpath + "/a_edit/edit/img/common/changebg.jpg";
	$(obj).closest(".col-xs-9").find("img").attr("src", img);

};
ED.cancelImgTwo = function(obj) {
	var index = this.nowIndex;
	var imgs = this.data.models[index].imgs;

	var nowobj = this.nowObj;

	$(obj).closest(".imgBTN").css("display", "none");
	var img = imgs[$(nowobj).closest(".row").index()].img;
	if (img == null || img == "")
		img = bpath + "/a_edit/edit/img/common/changebg.jpg";
	$(obj).closest(".col-xs-9").find("img").attr("src", img);

};

// ////////////////////////////////////////////

ED.changeColor = function(obj) {
	var index = this.nowIndex;
	var domModel = this.data.models[index];
	var type = parseInt(domModel.type);

	var dom = document.getElementById('color');
	if (type == 1) {
		dom.value = domModel.bgcolor;
	} else if (type == 7 || type == 10 || type == 11) {
		dom.value = domModel.color;
	}

	this.nowObj = obj;
	dom.click();

};
ED.chooseColor = function(obj) {
	var index = this.nowIndex;
	var domModel = this.data.models[index];
	var type = parseInt(domModel.type);
	if (type == 1) {
		$(this.nowObj).css("background", obj.value);

		domModel.bgcolor = obj.value;
		this.showPage();
	} else if (type == 7 || type == 10 || type == 11) {
		$(this.nowObj).css("background", obj.value);

		domModel.color = obj.value;
		this.showPage();

	}

};

/**
 * 删除元素
 */
ED.deleteIndex = function(index) {
	this.data.models.splice(index, 1);
	this.showPage();

};
/**
 * 添加元素
 * 
 */
ED.createModel = function(type, index) {
	var model = {};
	switch (type) {
	case 1:
		model = {
			type : 1,
			bgcolor : "#F3F3F3"
		};
		break;
	case 2:
		model = {
			type : 2,
			title:"公告内容",
			time:'',
			img : bpath + '/a_edit/edit/img/common/changebg.jpg',
			
			url:''
	

		};

		break;
	case 3:
		model = {
			type : 3,
			cd : [ {
				icon : 1,
				name : '菜单名称',
				url : ''
			}, {
				icon : 2,
				name : '菜单名称',
				url : ''
			}, {
				icon : 3,
				name : '菜单名称',
				url : ''
			}, {
				icon : 4,
				name : '菜单名称',
				url : ''
			} ]
		};

		break;
	case 4:
		model = {
			type : 4,// height:没写，用默认值10px
			height : 10
		};
		break;
	case 5:
		model = {
			type : 5,
			kind:"1",//1视频2音频
			courses : [ {
				img :  '/jsp/pt/a_edit/edit/img/kecheng.jpg',
				c_no : '',
				c_name:"课程名称"
			}, {
				img : '/jsp/pt/a_edit/edit/img/kecheng.jpg',
				c_no : '',
				c_name:"课程名称"
			}, {
				img : '/jsp/pt/a_edit/edit/img/kecheng.jpg',
				c_no : '',
				c_name:"课程名称"
			} ]
		};
		break;
	case 6:
		model = {
			type : 6,
			imgs : [ {
				img : bpath + '/a_edit/edit/img/common/changebg.jpg',
				url : '',
				title : ''
			}, {
				img : bpath + '/a_edit/edit/img/common/changebg.jpg',
				url : '',
				title : ''
			} ]

		};
		break;

	case 7:
		model = {
			type : 7,
			img : bpath + '/a_edit/edit/images/onetitle/title.jpg',
			name : '标题',
			color : '#360000'
		};
		break;
	case 8:

		model = {
			type : 8,
			kind:1,//1单张2两张并列，3张
			imgs:[{
				img : bpath + '/a_edit/edit/img/common/changebg.jpg',
				url : ''
				
			}
			      
			      ]
			
		};

		break;
	case 9:

		model = {
			title:"专题学习",
			type : 9, // 商品区域
			kind : 4, // 1大图，2小图3，一大两小4列表5
			
		
			goods : [ {
				special_no : '',
				
				price : '商品价格',
				text:'描述',
				bq:'标签',//标签
				img : bpath + '/jsp/pt/a_edit/edit/img/123.jpg',
				special_name : '商品名称',
				teacher:'主讲人',
				num:'已订阅数'
			} ,{
				special_no : '',
				
				price : '商品价格',
				text:'描述',
				bq:'标签',//标签
				img : bpath + '/jsp/pt/a_edit/edit/img/123.jpg',
				special_name : '商品名称',
				teacher:'主讲人',
				num:'已订阅数'
			}, {
				special_no : '',
				
				price : '商品价格',
				text:'描述',
				bq:'标签',//标签
				img : bpath + '/jsp/pt/a_edit/edit/img/123.jpg',
				special_name : '商品名称',
				teacher:'主讲人',
				num:'已订阅数'
			} ]
		};

		break;
	case 10:
		model = {
			type : 10,
			kind : 2, // 1实线2虚线
			color : '#360000'
		};
		break;
	case 11:
		model = {
			type : 11,
			goods:[{
				kind:1,//1专题2课程
				img:'/jsp/pt/a_edit/edit/img/hot/hot_01.png',
				id:'',
				name:'专题一',
				pm:'1',//排名,
				type:1
				
			},{
				kind:1,//1专题2课程
				img:'/jsp/pt/a_edit/edit/img/hot/hot_02.png',
				id:'',
				name:'专题二',
				pm:'2',//排名
				type:1
			},{
				kind:2,//1专题2课程
				img:'/jsp/pt/a_edit/edit/img/hot/hot_03.jpg',
				id:'',
				name:'课程三',
				pm:'3',//排名
				type:0
			}
			       
			       
		]
		
		};
		break;
	case 12:
		model = {
			type : 12,
			btns : [ {
				name : '菜单一',
				url : '',
				icon : ''
			}, {
				name : '菜单二',
				url : '',
				icon : ''
			}, {
				name : '菜单三',
				url : '',
				icon : ''
			} ]
		};
		break;

	}

	this.data.models.push(model);
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
	$("#editarea").html(ED.createAddBtn());
};

/**
 * 模块编辑工具
 * 
 */
ED.MODELS[0].editFun = function(model) {
	var html = "";
	html += "<div class='row' >";
	html += "<div class='col-xs-3'>背景颜色</div>";
	html += "<div class='col-xs-9'><span id='colordiv'  style='background:"
			+ model.bgcolor
			+ "'  onclick='ED.changeColor(this);'></span></div>";
	html += "</div>";
	return html;

};
ED.MODELS[1].editFun = function(model) {
	var html = "";
	html += "<div class='row' >";
/*	html += "<div class='col-xs-3'>背景图片</div>";
	html += "<div class='col-xs-9'><img id='imgdiv' title='点击切换图片' style='width:150px;' src='"
			+ model.img
			+ "'  onclick='ED.changeImg(this)'/><div class='imgBTN'><button class='sureBTN' onclick='ED.sureImgOne(this)'>确定</button><button class='cancelBTN' onclick='ED.cancelImgOne(this)'>取消</button></div></div>";
*/
	

	html += "<div class='col-xs-3'>开始时间</div>";
	
	html += "<div class='col-xs-9'><input  type='text' onblur='ED.updateTime(this)' class='stime'  value='"+model.time+"'  readonly='readonly placeholder='开始时间'  onclick=\"layui.laydate({elem: this,festival: true,istime: true, format: 'YYYY-MM-DD hh:mm:ss'})\"> </div>";
	
	
	html += "<div class='col-xs-3'>公告内容</div>";
	
	html += "<div class='col-xs-9'><input type='text' onkeyup='ED.updateTitle(this)' value='"+model.title+"'></div>";
	
	html += "<div class='col-xs-3'>链接内容</div>";
	var url="请输入链接";
	if(model.url!=null&&model.url!=""){
		url=model.url
	}
	
	html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeGGUrl(this);' class='urlinfo' url='"
		+ model.url + "'>" + url + "</span></div>";
	
	/*html += "<div class='col-xs-12' style='text-align:left'><input type='checkbox' name='islogo' ";
	if (model.islogo && model.islogo == 1) {
		html += "checked";
	}
	html += " onclick='ED.showThisLogo(this)'/>显示logo</div>";
	var logoleft = 0;
	if (model.logoleft) {
		logoleft = model.logoleft;
	}
	html += "<div class='col-xs-3'>logo位置</div>";
	html += "<div class='col-xs-9'><div class='progress progress-striped slider' onclick='ED.controlHeight2(this)'>"
			+ "<div class='progress-bar'  role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: "
			+ logoleft
			+ "%;' >"
			+ "<span class='sr-only'>PX</span></div></div><span id='resbar'>("
			+ logoleft + "%)</span></div>";*/


	
	
	html += "</div>";

	return html;
};



//公告链接
ED.changeGGUrl=function(obj){
	
	var url=$(obj).attr("url");
	var index=this.nowIndex;
	
	
	
	
	
	urlUtil.init(function(){
		if(this.returnUrl){
			
			$(obj).attr("url",this.returnUrl);
			$(obj).html(this.returnUrl);
			ED.data.models[index].url=this.returnUrl;
			ED.showPage();
			
		}
		
		
		
	}, url, obj);
	
	
};

/**
 * 修改菜单链接
 * 
 */
ED.changeCDUrl = function(obj) {
	
	this.nowObj = obj;
	
	var url=$(obj).attr("url");
	
	urlUtil.init(function(){
		
		if(urlUtil.returnUrl){
			
			$(obj).attr("url",urlUtil.returnUrl);
			$(obj).html(urlUtil.returnUrl);
			ED.changeCDInfo();
			
		}
		
		
		
	}, url, obj);

};
/**
 * changeImgUrl
 * 图片链接
 */
ED.changeImgUrl = function(obj) {
	
	this.nowObj = obj;
	
	var url=$(obj).attr("url");
	
	urlUtil.init(function(){
		
		if(urlUtil.returnUrl){
			
			$(obj).attr("url",urlUtil.returnUrl);
			$(obj).html(urlUtil.returnUrl);
			ED.checkImgList();
			
		}
		
		
		
	}, url, obj);

};


//公告内容变化
ED.updateTitle=function(obj){
	this.data.models[this.nowIndex].title=$(obj).val();
	
	this.data.models[this.nowIndex].time=$(obj).closest(".row").find(".stime").val();
	
	
	this.showPage();
	checkTimeall();
};
ED.updateTime=function(obj){
	
	this.data.models[this.nowIndex].time=$(obj).val();
	
	
	this.showPage();
	checkTimeall();
};



ED.MODELS[2].editFun = function(model) {
	var html = "";
	var cds = model.cd;
	var size = cds.length;
	for ( var j = 0; j < size; j++) {
		var onej = cds[j];
		var url = "请选择链接";
		if (onej.url != null && onej.url != "") {

			url = onej.url;
		}

		html += "<div class='row'  >";

		html += "<div class='col-xs-3'><input type='checkbox' onclick='ED.changeCDInfo();'  name='onecd' checked />菜单"
				+ (j + 1) + "</div>";
	
	
		html += "<div class='col-xs-9'>";
		html+="<img class='imgicon' data='"+onej.icon+"' src='"+bpath+onej.icon+"' onclick='ED.changeImg(this);'/><div class='imgBTN'><button class='sureBTN' onclick='ED.sureImgOne3(this)'>确定</button><button class='cancelBTN' onclick='ED.cancelImgOne3(this)'>取消</button></div><br>";
		
		html+="<input type='text' class='cdnameedit' value='"
				+ onej.name + "' onblur='ED.changeCDName(this);'/></div>";

		html += "<div class='col-xs-3'></div>";

		html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeCDUrl(this);' class='urlinfo' url='"
				+ onej.url + "'>" + url + "</span></div>";

		html += "</div>";

	}
	var cha = 5 - size;
	if (cha > 0) {
		for ( var x = 0; x < cha; x++) {
			size++;
			html += "<div class='row' >";

			html += "<div class='col-xs-3'><input type='checkbox' onclick='ED.changeCDInfo();'  name='onecd' />菜单"
					+ size + "</div>";
			html += "<div class='col-xs-9'>";
			html+="<img class='imgicon'  onclick='ED.changeImg(this);'/><div class='imgBTN'><button class='sureBTN' onclick='ED.sureImgOne3(this)'>确定</button><button class='cancelBTN' onclick='ED.cancelImgOne3(this)'>取消</button></div><br>";
			
			html+=" <input type='text' class='cdnameedit' placeholder='请输入名称' onblur='ED.changeCDName(this);'/></div>";

			html += "<div class='col-xs-3'></div>";

			html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeCDUrl(this);' class='urlinfo'>请选择链接</span></div>";

			html += "</div>";

		}
	}

	return html;

};

ED.MODELS[3].editFun = function(model) {
	var html = "";
	var h = model.height;

	html += "<div class='row' >";
	html += "<div class='col-xs-3'>高度</div>";
	html += "<div class='col-xs-9'><div class='progress progress-striped slider' onclick='ED.controlHeight(this)'>"
			+ "<div class='progress-bar'  role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: "
			+ h
			+ "%;' >"
			+ "<span class='sr-only'>PX</span></div></div><span id='resbar'>("
			+ h + "px)</span></div>";
	html += "</div>";

	return html;
};

ED.MODELS[4].editFun = function(model) {

	var html = "";
	var courses = model.courses;
	
	
	var realGoods = [];
	for ( var i = 0; i < courses.length; i++) {
		var oneg = courses[i];
		if (oneg.c_no != null&&oneg.c_no != "") {
			realGoods.push(oneg);
		}
	}
	html += "<div class='row' >";

	html += "<div class='col-xs-3'>添加课程</div>";
	html += "<div class='col-xs-9' style='padding:0'>";
	if (realGoods != null && realGoods.length > 0) {
		for ( var j = 0; j < realGoods.length; j++) {
			var onej = realGoods[j];
			html += "<div class='soneg onegood'><img class='imgg' src='"+ onej.img
					+ "' ><img class='delg' src='"+ bpath+ "/jsp/pt/a_edit/edit/img/btn_close02.gif' onclick='ED.delthisGood(this)' ></div>";
		}

	}

	html += "<div class='soneg addonegood'  onclick='ED.addoneGood(this)'>+</div>";

	html += "</div></div>";
	// kind
	var kind = parseInt(model.kind);

	html += "<div class='row' >";

	html += "<div class='col-xs-3'>课程类型</div>";
	html += "<div class='col-xs-9' style='padding:0'><input type='radio' onclick='ED.changeCorseKind()' name='kind' value='1' ";
	if (kind == 1) {
		html += "checked";
	}
	html += " />视频&nbsp;&nbsp;<input type='radio' onclick='ED.changeCorseKind()'  name='kind' value='0' ";
	if (kind == 2) {
		html += "checked";
	}
	html += " />音频 </div>";

	html += "</div>";

	
	
	html += "</div>";

	return html;
};
ED.MODELS[5].editFun = function(model) {
	var html = "";
	var imgs = model.imgs;
	for ( var j = 0; j < imgs.length; j++) {
		var onej = imgs[j];
		var url = onej.url;
		if (url == null || url.trim() == "") {
			url = "请选择链接";
		}

		html += "<div class='row'  >";

		html += "<div class='col-xs-3'><input type='checkbox' onclick='ED.checkImgList();'  name='oneImg' checked />图片"
				+ (j + 1) + "</div>";
		html += "<div class='col-xs-9'><img src='"
				+ bpath+onej.img
				+ "' style='width:120px;' data='"+onej.img+"'  title='点击切换' class='imgone' onclick='ED.changeImg(this);'/><div class='imgBTN'><button class='sureBTN' onclick='ED.sureImgTwo(this)'>确定</button><button class='cancelBTN' onclick='ED.cancelImgTwo(this)'>取消</button></div></div>";
		html += "<div class='col-xs-3'></div>";
		html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeImgUrl(this);' class='urlinfo' url='"
				+ onej.url + "'>" + url + "</span></div>";

		html += "</div>";

	}

	html += "<div class='addImgDIV' onclick='ED.addImgDivRow(this)'>+</div>";
	return html;
};
ED.MODELS[6].editFun = function(model) {
	var html = "";

	var img = model.img;
	var name = model.name;
	var color = model.color;
	html += "<div class='row'  >";

	html += "<div class='col-xs-3'>背景图片</div>";
	html += "<div class='col-xs-9'><img src='"
			+ bpath+img
			+ "' style='width:120px;' data='"+img+"' title='点击切换背景图片' class='bgimgchange'   onclick='ED.changeImg(this);'/><div class='imgBTN'><button class='sureBTN' onclick='ED.sureImgOne(this)'>确定</button><button class='cancelBTN' onclick='ED.cancelImgOne(this)'>取消</button></div></div>";

	html += "</div>";

	html += "<div class='row'  >";
	html += "<div class='col-xs-3'>标题文本</div>";
	html += "<div class='col-xs-9'><input type='text' class='cdnameedit' value='"
			+ name + "' onblur='ED.changeCDName(this);'/></div>";
	html += "</div>";
	html += "<div class='row' >";
	html += "<div class='col-xs-3'>文字颜色</div>";
	html += "<div class='col-xs-9'><span id='colordiv'  style='background:"
			+ color + "'  onclick='ED.changeColor(this);'></span></div>";
	html += "</div>";

	return html;

};
// 图片
ED.MODELS[7].editFun = function(model) {
	var html = "";
	
	var kind=parseInt(model.kind);//1
	html+="<div class='row' >";
	html+="<div class='col-xs-3'>图片布局</div>";
	html+="<div class='col-xs-9'>";
	
	html+="<input type='radio' onclick='ED.chooseImgkind(this);'  name='kind' value=1 ";
	if(kind==1){
		html+=" checked ";	
	}
	
	html+=" /> 大图 &nbsp;&nbsp;&nbsp;";
	
	html+="<input type='radio' onclick='ED.chooseImgkind(this);'  name='kind' value=2 ";
	if(kind==2){
		html+=" checked ";	
	}
	
	html+=" /> 一行两个 &nbsp;&nbsp;&nbsp;";
	
	
	html+="</div></div>";
	
	var imgs = model.imgs;
	for ( var j = 0; j < imgs.length; j++) {
		var onej = imgs[j];
		var url = onej.url;
		if (url == null || url.trim() == "") {
			url = "请选择链接";
		}

		html += "<div class='row'  >";

		html += "<div class='col-xs-3' ><input type='checkbox' onclick='ED.checkImgList();'  name='oneImg' checked />图片"
				+ (j + 1) + "</div>";
		html += "<div class='col-xs-9'><img src='"
				+bpath+onej.img
				+ "' style='width:120px;' data='"+onej.img+"'  title='点击切换' class='imgone' onclick='ED.changeImg(this);'/><div class='imgBTN'><button class='sureBTN' onclick='ED.sureImgTwo(this)'>确定</button><button class='cancelBTN' onclick='ED.cancelImgTwo(this)'>取消</button></div></div>";
		html += "<div class='col-xs-3'></div>";
		html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeImgUrl(this);' class='urlinfo' url='"
				+ onej.url + "'>" + url + "</span></div>";

		html += "</div>";

	}

	html += "<div class='addImgDIV' onclick='ED.addImgDivRow(this)'>+</div>";

	
	
	
	return html;
};
// 商品
ED.MODELS[8].editFun = function(model) {
	var html = "";
	var goods = model.goods;
	var realGoods = [];
	
	for ( var i = 0; i < goods.length; i++) {
		var oneg = goods[i];
		if (oneg.special_no != "" && oneg.special_no != null) {
			realGoods.push(oneg);
		}
	}
	html += "<div class='row' >";

	html += "<div class='col-xs-3'>添加专题</div>";
	html += "<div class='col-xs-9' style='padding:0'>";
	if (realGoods != null && realGoods.length > 0) {
		for ( var j = 0; j < realGoods.length; j++) {
			var onej = realGoods[j];
			html += "<div class='soneg onegood'><img class='imgg' src='"
					+ onej.img
					+ "'><img class='delg' src='"
					+ bpath
					+ "/jsp/pt/a_edit/edit/img/btn_close02.gif' onclick='ED.delthisGood2(this)' ></div>";
		}

	}

	html += "<div class='soneg addonegood'  onclick='ED.addoneGood2(this)'>+</div>";

	html += "</div></div>";
	// kind
	var kind = parseInt(model.kind);

	html += "<div class='row' >";

	html += "<div class='col-xs-3'>列表样式</div>";
	html += "<div class='col-xs-9' style='padding:0'><input type='radio' onclick='ED.changeGoodKind()' name='kind' value='1' disabled ";
	if (kind == 1) {
		html += "checked";
	}
	html += " />大图&nbsp;&nbsp;<input type='radio' onclick='ED.changeGoodKind()'  name='kind' value='2' disabled";
	if (kind == 2) {
		html += "checked";
	}
	html += " />小图&nbsp;&nbsp;<input type='radio' onclick='ED.changeGoodKind()'  name='kind' value='3' disabled";
	if (kind == 3) {
		html += "checked";
	}
	html += " />一大两小&nbsp;&nbsp;<input type='radio' onclick='ED.changeGoodKind()' name='kind' value='4' ";
	if (kind == 4) {
		html += "checked";
	}
	html += " />列表 </div>";

	html += "</div>";

	



	return html;
};

// ////////////////商品模块///////////
ED.changeHasPrice = function(obj) {
	if ($(obj).prop("checked") == true) {
		this.data.models[this.nowIndex].hasPrice = $(obj).val();
	} else {
		this.data.models[this.nowIndex].hasPrice = -1;
	}
	this.showPage();
};
ED.changeHasName = function(obj) {
	if ($(obj).prop("checked") == true) {
		this.data.models[this.nowIndex].hasName = $(obj).val();
	} else {
		this.data.models[this.nowIndex].hasName = -1;
	}
	this.showPage();
};
ED.changeHasCart = function(obj) {
	if ($(obj).prop("checked") == true) {
		this.data.models[this.nowIndex].cart = $(obj).val();
		$(obj).closest(".row").find(".col-xs-11").css("display", "block");

	} else {
		this.data.models[this.nowIndex].cart = -1;
		$(obj).closest(".row").find(".col-xs-11").css("display", "none");
	}
	this.showPage();
};
ED.changeCartType = function(obj) {
	var type = $("input[name='" + obj + "']:checked").val();
	this.data.models[this.nowIndex].carttype = type;
	this.showPage();
};
ED.changeGoodKind = function(obj) {
	var kind = $("input[name='kind']:checked").val();
	this.data.models[this.nowIndex].kind = kind;
	this.showPage();
};

ED.changeCorseKind= function(obj) {
	var kind = $("input[name='kind']:checked").val();
	this.data.models[this.nowIndex].kind = kind;
	this.showPage();
};


//删除该课程
ED.delthisGood = function(obj) {

	if (window.confirm("你确定删除该课程吗?")) {
		var dom = $(obj).closest(".onegood");
		var i = dom.index();
		
		
		
		this.data.models[this.nowIndex].courses.splice(i, 1);
		dom.remove();
		/*var courses = this.data.models[this.nowIndex].courses;
		var g = [];
		for ( var j = 0; j < courses.length; j++) {
			if (courses[j].c_no != null && courses[j].c_no != "") {
				g.push(courses[j]);
			}

		}
		if (g.length > 0) {
			this.data.models[this.nowIndex].courses = g;
		}*/

		this.showPage();

	}

};
//添加课程
ED.addoneGood = function(obj) {
	this.nowObj = obj;
	// 打开图片选择器
	var datainfo = {};
	datainfo.page = 1;
	datainfo.type = 2;
	
	var kind=$("input[name='kind']:checked").val();
	if(kind){
		datainfo.kind=kind;//1视频0音频
	}
	
	
	var courses = ED.data.models[ED.nowIndex].courses;
	if (courses != null && courses.length > 0) {

		var has = [];
		for ( var i = 0; i < courses.length; i++) {
			if (courses[i].c_no != null && courses[i].c_no.trim() != "") {
				has.push(courses[i].c_no);

			}

		}
		

		datainfo.goods = JSON.stringify(has);

	}
	
	goodsUtil.init(function(){
		
		//alert(JSON.stringify(this.chooseArr));
		if(this.chooseArr&&this.chooseArr.length>0){
			
			//清除空的
			
			
			var html="";
		
			
			for(var i=0,len=this.chooseArr.length;i<len;i++){
				var oj=this.chooseArr[i];
				
				var one={};
				one.c_no=oj.c_no;
				one.img=oj.img;
				one.c_name=oj.c_name;
				courses.push(one);
				
				html+="<div class='soneg onegood'><img class='imgg' src='"+ one.img+ "' ><img class='delg' src='"
			+ bpath
			+ "/jsp/pt/a_edit/edit/img/btn_close02.gif' onclick='ED.delthisGood(this)' ></div>";
				
			}
			
			
			$(obj).before(html);
			
		}
		
		var realGoods = [];
		for ( var i = 0; i < courses.length; i++) {
			var oneg = courses[i];
			if (oneg.c_no != null&&oneg.c_no != "") {
				realGoods.push(oneg);
			}
		}
		
		
		 ED.data.models[ED.nowIndex].courses= realGoods;
		 ED.showPage();
		
		
	}, datainfo);
	

};

//添加专题
ED.addoneGood2=function(obj) {
	this.nowObj = obj;
	// 打开图片选择器
	var datainfo = {};
	datainfo.page = 1;
	datainfo.type = 3;
	
	
			
			
	
	var goods = ED.data.models[ED.nowIndex].goods;
	if (goods != null && goods.length > 0) {

		var has = [];
		for ( var i = 0; i < goods.length; i++) {
			if (goods[i].special_no != null && goods[i].special_no.trim() != "") {
				has.push(goods[i].special_no);

			}

		}
		

		datainfo.goods = JSON.stringify(has);

	}
	
	goodsUtil.init(function(){
		
		//alert(JSON.stringify(this.chooseArr));
		if(this.chooseArr&&this.chooseArr.length>0){
			
			//清除空的
			
			
			var html="";
		
			
			for(var i=0,len=this.chooseArr.length;i<len;i++){
				var oj=this.chooseArr[i];
				
				var one={};
				one.special_no=oj.special_no;
				one.img=oj.img;
				
				one.special_name=oj.special_name;
				
				one.teacher=oj.teacher;
				if(oj.text){
					one.text=oj.text;//'描述'
				}
				
				
				one.price=(parseFloat(oj.price)/100).toFixed(2);
				
				one.num=oj.watch;
				
				if(oj.bq!=null&&oj.bq!=""){
					one.bq=oj.bq;//'标签
				}
				
				
				
				
				goods.push(one);
				
				html+="<div class='soneg onegood'><img class='imgg' src='"+ one.img+ "' ><img class='delg' src='"
			+ bpath
			+ "/jsp/pt/a_edit/edit/img/btn_close02.gif' onclick='ED.delthisGood2(this)' ></div>";
				
			}
			
			
			$(obj).before(html);
			
		}
		
		var realGoods = [];
		for ( var i = 0; i < goods.length; i++) {
			var oneg = goods[i];
			if (oneg.special_no != null&&oneg.special_no != "") {
				realGoods.push(oneg);
			}
		}
		
		
		 ED.data.models[ED.nowIndex].goods= realGoods;
		 ED.showPage();
		
		
	}, datainfo);
	

};

//删除该专题
ED.delthisGood2 = function(obj) {

	if (window.confirm("你确定删除该专题吗?")) {
		var dom = $(obj).closest(".onegood");
		var i = dom.index();
		
		
		
		this.data.models[this.nowIndex].goods.splice(i, 1);
		dom.remove();
		/*var courses = this.data.models[this.nowIndex].courses;
		var g = [];
		for ( var j = 0; j < courses.length; j++) {
			if (courses[j].c_no != null && courses[j].c_no != "") {
				g.push(courses[j]);
			}

		}
		if (g.length > 0) {
			this.data.models[this.nowIndex].courses = g;
		}*/

		this.showPage();

	}

};




//删除该专题
ED.delthisGood3 = function(obj) {

	if (window.confirm("你确定删除该排行吗?")) {
		var dom = $(obj).closest(".onegood");
		var i = dom.index();
		
		
		
		this.data.models[this.nowIndex].goods.splice(i, 1);
		dom.remove();
		
		this.showPage();

	}

};
	
		//添加排行
		ED.addoneGood3=function(obj) {
			this.nowObj = obj;
			// 打开图片选择器
			var datainfo = {};
			datainfo.page = 1;
			datainfo.type = 4;//排行
			
			
					
					
			
			var goods = ED.data.models[ED.nowIndex].goods;
		
			
			goodsUtil.init(function(){
				
				//alert(JSON.stringify(this.chooseArr));
				if(this.chooseArr&&this.chooseArr.length>0){
					
					//清除空的
					
					
					var html="";
				
					
					for(var i=0,len=this.chooseArr.length;i<len;i++){
						var oj=this.chooseArr[i];
						
						var one={};
						one.id=oj.id;
						one.img=oj.img;
						
						one.name=oj.name;
						
						one.pm=oj.pm;//排名
						
						if(oj.c_no!=null&&oj.c_no!=""){
							one.kind=2;//1专题2课程
							if(oj.type){
								one.type=oj.type;
							}else{
								one.type=1;
							}
							
						}else{
							one.kind=1;
							one.type=1;
						}
						
						
						
						goods.push(one);
						
						html+="<div class='soneg onegood'><img class='imgg' src='"+ one.img+ "' ><img class='delg' src='"
					+ bpath
					+ "/jsp/pt/a_edit/edit/img/btn_close02.gif' onclick='ED.delthisGood3(this)' ></div>";
						
					}
					
					
					$(obj).before(html);
					
				}
				
				var realGoods = [];
				for ( var i = 0; i < goods.length; i++) {
					var oneg = goods[i];
					if (oneg.id != null&&oneg.id != "") {
						realGoods.push(oneg);
					}
				}
				
				
				 ED.data.models[ED.nowIndex].goods= realGoods;
				 ED.showPage();
				
				
			}, datainfo);
			

		};
// //////////////////////////

// 辅助线
ED.MODELS[9].editFun = function(model) {

	var html = "";
	var kind = parseInt(model.kind);
	var color = model.color;
	if (color == null || color == "")
		color = '#360000';

	html += "<div class='row'  >";
	html += "<div class='col-xs-3'>类型</div>";
	html += "<div class='col-xs-9'>	<input type='radio' onclick='ED.changeLineKind();' name='linekind' value=1 ";
	if (kind == 1) {
		html += "checked";
	}
	html += " />实线<input onclick='ED.changeLineKind();' type='radio' name='linekind' value=2 ";
	if (kind == 2) {
		html += "checked";
	}
	html += ">虚线</div>";
	html += "</div>";
	html += "<div class='row' >";
	html += "<div class='col-xs-3'>颜色</div>";
	html += "<div class='col-xs-9'><span id='colordiv'  style='background:"
			+ color + "'  onclick='ED.changeColor(this);'></span></div>";
	html += "</div>";
	return html;
};
ED.MODELS[10].editFun = function(model) {
	var html = "";
	var goods = model.goods;
	
	
	var realGoods = [];
	for ( var i = 0; i < goods.length; i++) {
		var oneg = goods[i];
		if (oneg.id != null&&oneg.id != "") {
			realGoods.push(oneg);
		}
	}
	
	html += "<div class='row' >";

	html += "<div class='col-xs-3'>选择热门课程/专题</div>";
	html += "<div class='col-xs-9' style='padding:0'>";
	if (realGoods != null && realGoods.length > 0) {
		for ( var j = 0; j < realGoods.length; j++) {
			var onej = realGoods[j];
			html += "<div class='soneg onegood'><img class='imgg' src='"
					+ onej.img
					+ "' ><img class='delg' src='"
					+ bpath
					+ "/jsp/pt/a_edit/edit/img/btn_close02.gif' onclick='ED.delthisGood3(this)' ></div>";
		}

	}

	html += "<div class='soneg addonegood'  onclick='ED.addoneGood3(this)'>+</div>";

	html += "</div></div>";
	


	
	
	html += "</div>";

	return html;

};
/*ED.MODELS[11].editFun = function(model) {
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

		html += "<div class='col-xs-3'></div>";

		html += "<div class='col-xs-9'>链接:<span style='color:blue' onclick='ED.changeCDUrl(this);' class='urlinfo' url='"
				+ onej.url + "'>" + url + "</span></div>";

		html += "</div>";

	}

	html += "<div class='addImgDIV' onclick='ED.addWeiBtnRow(this)'>+</div>";
	return html;
};*/
/**
 * 
 * 模块页面显示工具
 * 
 */

ED.MODELS[0].showFun = function(one) {
	return '<div class="neededit post_head " ><div class="head_a"><img id="head_pic"  src="'+bpath+'/img/post/head.png"></div><div class="head_b" id="user_name">小不点</div><div class="head_c" onclick="go_vip();"> <button id="sj_but" class="button_one">会员升级<tton> </div><div class="editbtns">  <span class="delbtn">删除</span></div></div>';
};
ED.MODELS[1].showFun = function(one) {
	var html = "";
	html += '<div class="textdiv drag neededit"><b class="dragdo"></b>';
	html+='<div class="texttou"><img src="'+bpath+'/jsp/pt/a_edit/edit/img/live.png" ><b id="checktimeone"  stime="'+one.time+'">直播未开始</b></div>';
	
	html += '<a ><p style="color:'
			+ one.color
			+ ';">'
			+ one.title
			+ '</p></a><div class="editbtns">  <span class="delbtn">删除</span></div></div>';

	return html;

};
ED.MODELS[2].showFun = function(one) {
	var html = '<div class="caidan drag neededit"><b class="dragdo"></b>';
	if (one.cd != null && one.cd.length > 0) {
		var widthstr = 100 / one.cd.length;
		for ( var j = 0; j < one.cd.length; j++) {
			var onej = one.cd[j];
			html += '<div class="onecd " style="width: ' + widthstr
			+ '%;">';

			html += '<img src="'+bpath+onej.icon+'" class="icons">';
			html += '<div class="cdname">' + onej.name + '</div>';
			html += '<div class="bordercd"></div></div>';
		}
	}
	html += '<div class="editbtns">  <span class="delbtn">删除</span></div></div>';
	return html;
};
ED.MODELS[3].showFun = function(one) {
	var html = "";
	if (one.height) {
		html += '<div class="jgdiv neededit" style="height:' + one.height
				+ 'px">';
	} else {
		html += '<div class="jgdiv neededit" style="height:0px">';
	}
	html += '<div class="editbtns">  <span class="delbtn">删除</span></div></div>';
	return html;
};
ED.MODELS[4].showFun = function(one) {
	var kind=parseInt(one.kind);//1视频2音频
	if(kind==1){
		
		
		var html = '<div class="couponlist drag neededit"><b class="dragdo"></b>';
		
		if (one.img != null && one.img.trim() != "") {
			html += '<img src="'+ one.img + '" class="bgimg"/>';
		}
		html+='<div class="couponmian">';
		
		if (one.courses != null && one.courses.length > 0) {
		
			
			for ( var j = 0; j < one.courses.length; j++) {
				var onej = one.courses[j];
				html += '<div class="couponone img'+(j+1)+'" style="width:30%;" >';
				html += '<div class="imgdiv"><img src="' +onej.img + '" /><img class="videoicon" src="'+bpath+'/jsp/pt/a_edit/edit/img/hot/video.png"></div>';
				
				if(onej.c_name.length<10){
					html+='<b>'+onej.c_name+'</b>';
				}else{
					
					html+='<b>'+onej.c_name.substring(0,9)+".."+'</b>';
					
				}
				
				
				html+='</div>';
			}

		}
		html+='</div>';
		html += '<div class="editbtns">  <span class="delbtn">删除</span></div></div>';
		return html;
		
		
		
		
	}else{
			var html = '<div class="courselist drag neededit"><b class="dragdo"></b> <div class="maincourse">';
		
		if (one.img != null && one.img.trim() != "") {
			html += '<img src="' +bpath+ one.img + '" class="bgimg"/>';
		}
		
		
		if (one.courses != null && one.courses.length > 0) {
				html+='<div class="leftcorse" style="width:100%">';
			
			for ( var j = 0; j < one.courses.length; j++) {
				var onej = one.courses[j];
				html += '<div class="courseone"  >';
				html += '<img src="' + bpath + '/jsp/pt/a_edit/edit/img/sanjiao.png" />';
				if(onej.c_name.length<25){
					html+='<b>'+onej.c_name+'</b>';
				}else{
					
					html+='<b>'+onej.c_name.substring(0,25)+".."+'</b>';
					
				}
				html+='</div>';
			}
			html+='</div>';
			/*html+='<div class="leftcorse">';
			
			for ( var j = 0; j < one.courses.length; j++) {
				var onej = one.courses[j];
				html += '<div class="courseone"  >';
				html += '<img src="' + bpath + '/jsp/pt/a_edit/edit/img/sanjiao.png" />';
				if(onej.c_name.length<15){
					html+='<b>'+onej.c_name+'</b>';
				}else{
					
					html+='<b>'+onej.c_name.substring(0,15)+".."+'</b>';
					
				}
				html+='</div>';
			}
			html+='</div>';
			
			html+='<div class="rightcorse">';
			html += '<img class="startVoice" src="' +bpath+ '/jsp/pt/a_edit/edit/img/index_start2.png" />';
			html +='<b><img src="'+bpath+'/jsp/pt/a_edit/edit/img/index_erji.png" class="icon"><span>222209</span></b>';
			html+='</div>';*/
			
		}
		html+="</div>";
		html+='<b class="commonb">查看全部</b>';
		
		html += '<div class="editbtns">  <span class="delbtn">删除</span></div></div>';
		return html;
		
		
		
		
		
	}
	

	
};
ED.MODELS[5].showFun = function(one) {
	var html = '<div class="piclist app-field clearfix drag neededit" mod="banner" effect="" loop="loop" style="position: relative;"><b class="dragdo"></b><div class="control-group"><div class="swiper-container"><ul class="swiper-wrapper">';
	if (one.imgs != null && one.imgs.length > 0) {
		for ( var j = 0; j < one.imgs.length; j++) {
			var onej = one.imgs[j];
			html += '<li class="swiper-slide">';
			html += '<a  title="' + onej.title
					+ '" class="custom-url"><img src="' +bpath+ onej.img
					+ '"></a></li>';
		}
	}
	html += '</ul><div class="swiper-pagination"></div></div></div> <div class="editbtns">  <span class="delbtn">删除</span></div></div>';
	return html;
};
ED.MODELS[6].showFun = function(one) {
	var html = '<div class="onetitle drag neededit"><b class="dragdo"></b>';
	html += '<img src="' +bpath+ one.img + '" />';
	html += '<h2 style="color:'
			+ one.color
			+ ';">'
			+ one.name
			+ '</h2><div class="editbtns">  <span class="delbtn">删除</span></div></div>';
	return html;
};
ED.MODELS[7].showFun = function(one) {
	
	
	var kind=one.kind;
	if(kind==null){
		kind=1;
	}else{
		kind=parseInt(kind);
	}
	
	var imgs=one.imgs;
	var html='<div class="tqmarea drag neededit ';
	
	if(kind==1){
		html+=' imglist1 ';

	}else if(kind==2){
		
		
		html+=' imglist2 ';
		
		
	}else if(kind==3){
		html+=' imglist3 ';
	}else{
		
		html+=' imglist1 ';
	}
	
	html+='"  style="display:inline-block" ><b class="dragdo"></b>';
	
	
	for(var i=0;i<imgs.length;i++){
		var onej=imgs[i];
		html+='<a class="imgs oimg'+(i+1)+' "><img src="'
			+ bpath+onej.img
			+ '" /></a>';
		
	}
	
	
	html+='<div class="editbtns">  <span class="delbtn">删除</span></div></div>';
	return html;
	
	
	
	
	
};
/**
 * 商品 kind: 2, //1大图，2小图3，一大两小4列表 cart: 1, //1显示加入购物车-1不显示加入购物车 carttype: 1,
 * //购物车风格 hasName:1,//1显示名称,-1不显示 hasPrice:1,//1显示价格，-1不显示价格
 */
ED.MODELS[8].showFun = function(one) {
	
	
	
	
	var kind = parseInt(one.kind);
	
	var title=one.title;
	
	var html = '<div class="goodsarea app-field clearfix drag neededit" mod="shoppingList" style="position: relative;"><b class="dragdo"></b><div class="control-group">';
	
	
	html += '<div class="title">'+title+'<b><img src="'+bpath+'/jsp/pt/a_edit/edit/img/genghuan.png"  />换一换</b></div>';
	
	

	if (kind == 4) {
		html += '<ul class="sc-goods-list clearfix card pic list">';
	} else {
		html += '<ul class="sc-goods-list clearfix card pic">';
	}

	if (one.goods != null && one.goods.length > 0) {

		for ( var j = 0; j < one.goods.length; j++) {
			var onej = one.goods[j];
			
			
		
			
			
			

			if (kind == 3 && j % 3 == 0) {
				html += '<li class="goods-card card big-pic">';
			} else if (kind == 1) {
				html += '<li class="goods-card card big-pic">';
			} else {

				html += '<li class="goods-card card small-pic">';
			}
			// onclick="gointoGoodsInfo(' + onej.gid + ')"
			html += '<a  class="js-goods clearfix">';
			html += '<div class="photo-block"><img class="goods-photo js-goods-lazy" tmp="" src="'
					+onej.img + '" data-echo=""><img class="videoicon" src="'+bpath+'/jsp/pt/a_edit/edit/img/hot/video.png"></div>';
			if (kind == 4) {
				html += '<div class="info  btn1 info-price info-no-price">';
			} else {
				html += '<div class="info  clearfix  btn1 info-price info-no-price">';
			}

			//名称
			html += '<p class="goods-title toutitle">';
			if(onej.special_name.length<10){
				html+=onej.special_name;
			}else{
				html+=onej.special_name.substring(0,9)+"..";
			}
			
			html+='</p>';
			if(onej.text){
				//描述
				html += '<p class="goods-title">';	
				
				if(onej.text.length<11){
					html+=onej.text;
				}else{
					html+=onej.text.substring(0,10)+"...";
				}
				
				html+='</p>';
				
			}
			
			//价格
			html += '<p class="goods-title price">￥' + onej.price
			+ '</p>';
			if(onej.bq){
				//标签
				html += '<p class="goods-title"><span class="bq">' + onej.bq + '</span></p>';
				
			}
			
			
			//主讲
		
			html += '<p class="goods-title weizhujiang">主讲:' + onej.teacher + '</p>';

			html += '</div></a>';

			html += '<label class="goodsbtn" style="right: 10px;">'+onej.num+'人订阅</label>';

			html += '</li>';

		}

	}
	html += '</ul></div>';
	html +='<div class="weiquankan"><span>查看全部</span></div>';
	
	html+='<div class="editbtns">  <span class="delbtn">删除</span></div></div>';

	return html;
};
ED.MODELS[9].showFun = function(one) {
	var html = "";
	var kind = parseInt(one.kind);
	if (kind == 1) {
		html += '	<div class="xxdiv drag neededit" style="border:1px solid '
				+ one.color + '"><b class="dragdo"></b>';
	} else {
		html += '	<div class="xxdiv drag neededit" style="border-color:'
				+ one.color + ';"><b class="dragdo"></b> ';
	}
	html += '<div class="editbtns">  <span class="delbtn">删除</span></div></div>';
	return html;
};
ED.MODELS[10].showFun = function(one) {
	var html = "";
	html += '<div class="hotdiv drag neededit"><b class="dragdo"></b>';
	
	html+='<div class="hottou"><span>热门排行榜</span><b>查看全部</b></div>';
	html += '<div class="hotdivgoods">';
	if (one.goods != null && one.goods.length > 0) {
	
		
		for ( var j = 0; j < one.goods.length; j++) {
			var onej = one.goods[j];
			html += '<div class="hotone img'+(j+1)+'" style="width:30%;" >';
			html += '<div class="imgdiv"><img src="' + onej.img + '" /><img class="videoicon" src="'+bpath+'/jsp/pt/a_edit/edit/img/hot/';
			
			if(parseInt(onej.type)==1){
				html+='video.png';
			}else{
				html+='audio.png';
			}
			
			
			
			
			html+='"></div> <span class="pmspan">'+onej.pm+'</span>';
			
			if(onej.name.lengt<7){
				html+='<b>'+onej.name+'</b>';
			}else{
				html+='<b>'+onej.name.substring(0,6)+".."+'</b>';
			}
			
			
			
			html+='</div>';
		}

	}
	
	html+='</div>';
	
	html += '<div class="editbtns">  <span class="delbtn">删除</span></div></div>';

	
	

		
		
		
	return html;
};

/*ED.MODELS[11].showFun = function(one) {
	var html = "";
	html += '<div class="nav neededit" ><b class="dragdo"></b><nav><div id="nav_ul" class="nav_"><ul class="box">';
	if (one.btns != null) {
		for ( var j = 0; j < one.btns.length; j++) {
			var onej = one.btns[j];
			html += '<li onclick="window.location.href=\"' + onej.url
					+ '\""><a class=""><label style="font-size: 12px;">'
					+ onej.name + '</label></a></li>';
		}
	}
	html += '</ul></div></nav><div class="editbtns">  <span class="delbtn">删除</span></div></div>';
	return html;
};*/

//<span class="addbtn">添加</span>
