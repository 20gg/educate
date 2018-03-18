// js获取项目根路径，如： http://localhost:8088/labms_s
function getRootPath() {
	// 获取当前网址，如：
	// http://localhost:8080/labms_s/navigation/files/sysmanagement/main.jsp?url=sys_equi_number.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： /ems/navigation/files/sysmanagement/main.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，"http://localhost:8080"
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/jquery
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	// 返回"/ems/navigation/files/sysmanagement/"
	var forwordPath = pathName.substring(0, pathName.length - 8);
	// return (localhostPaht + forwordPath);
	return (localhostPaht + projectName);
}
var bpath= getRootPath();

function jspost(URL, PARAMS) {
    console.info(PARAMS);
    var temp = document.createElement("form");
    temp.action = URL;
    temp.method = "post";
    temp.style.display = "none";
    if (PARAMS["new_window"] != null) {
        openWindow(PARAMS["new_window"]);
        temp.target = PARAMS["new_window"];

    }
    if (PARAMS["target"] != null) {
        temp.target = PARAMS["target"];
    }
    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    return temp;

}

var timer2 ,url_timer3;
function error(info) {
    if ($(".urlsuccess").length == 0) {
        var html = '<p class="urlsuccess" style="  display: inline-block; position: fixed;  top: 35%; z-index:1110;cursor:default;   border-radius: 3px; -webkit-border-radius: 3px;  white-space: nowrap; color: #fff;font-size: 0.875rem; background: rgba(0, 0, 0, 0.60);text-align: center;overflow-x: hidden;overflow-y: auto;"> <span id="ajaxinfo" style="  display: block;font-size: 17px;cursor:default; padding:12px 25px"></span></p>';
        $("body").append(html);
    }
    $("#ajaxinfo").html(info);
    var width = $(document.body).width();
    var pwidth = $(".urlsuccess").css("width").substring(0, $(".urlsuccess").css("width").length - 2);
    $(".urlsuccess").css("left", ( width - pwidth) / 2);
    if (window.browser == "chrome" || window.browser == "firefox") {
        $(".urlsuccess").css("animate", "scaleUp  0.4s");
        $(".urlsuccess").css("-webkit-animation", "scaleUp  0.4s");
    } else {
        $(".urlsuccess").fadeIn(400);
    }
    if (timer2) {
        clearInterval(timer2);
    }
    timer2 = setInterval(dis, 1500);
    function dis() {
        if (window.browser == "chrome" || window.browser == "firefox") {
            $(".urlsuccess").css("animate", "scaleDown  0.4s");
            $(".urlsuccess").css("-webkit-animation", "scaleDown  0.4s");
            url_timer3 = setInterval(function () {
                $(".urlsuccess").remove();
                clearInterval(url_timer3);
            }, 300);
        } else {
            $(".urlsuccess").fadeOut(5000);
            $(".urlsuccess").remove();
        }
        clearInterval(timer2);
    }
}



function orderone(url){


	window.open(url,"goorder",'height=660, width=400, top=50, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no'); 
	
	
	
}


function getCookie(name) {  
    //取出cookie   
    var strCookie = document.cookie;  
    //cookie的保存格式是 分号加空格 "; "  
    var arrCookie = strCookie.split("; ");  
    for ( var i = 0; i < arrCookie.length; i++) {  
        var arr = arrCookie[i].split("=");  
        if (arr[0] == name&&arr[1]!="") {  
            return decodeURIComponent(arr[1]);  
        }  
    }  
    return null;  
};

function GetQueryString(name)
{
    /* var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); 
     return null;*/
	 var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	         var r = window.location.search.substr(1).match(reg);
	         if (r != null)return decodeURI(r[2]);   //对参数进行decodeURI解码
	          return null;
}
