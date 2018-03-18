var menu_choose = 1;

var menu_path = "http://www.daodaketang.com/educate/";

var menu_img1_a = menu_path+"img/wx_menu/home1.png";
var menu_img1_b = menu_path+"img/wx_menu/home2.png";

var menu_img2_a = menu_path+"/img/wx_menu/tl1.png";
var menu_img2_b = menu_path+"img/wx_menu/tl2.png"; 

var menu_img3_a = menu_path+"img/wx_menu/dy1.png";
var menu_img3_b = menu_path+"img/wx_menu/dy2.png"; 

var menu_img4_a = menu_path+"img/wx_menu/money1.png";
var menu_img4_b = menu_path+"img/wx_menu/money2.png"; 

var menu_img5_a = menu_path+"img/wx_menu/me1.png";
var menu_img5_b = menu_path+"img/wx_menu/me1.png"; 



var menu_text1 = "";
var menu_text2 = "";
var menu_text3 = "";
var menu_text4 = "";
var menu_text5 = "";

if(menu_choose == 1){ 
	menu_img1_a = menu_img1_b; 
	menu_text1 = " menu_green";
	
}else if(menu_choose == 2){ 
	menu_img2_a = menu_img2_b;  
	menu_text2 = " menu_green";
	
}else if(menu_choose == 3){ 
	menu_img3_a = menu_img3_b;  
	menu_text3 = " menu_green";
	
}else if(menu_choose == 4){ 
	menu_img4_a = menu_img4_b;  
	menu_text4 = " menu_green";
	
}else if(menu_choose == 5){ 
	menu_img5_a = menu_img5_b;  
	menu_text5 = " menu_green";
	
}

document.write("<link rel='stylesheet' href='"+menu_path+"css/wx_menu.css'>");

var menu_html = "<div class='wx_menu_page'>"
	+ "<div class='wx_menu_one' onclick='menu1();'><div class='menu_s'><img src='"+menu_img1_a+"'></div> <div class='menu_x "+menu_text1+"'>首页</div> </div>"
	+ "<div class='wx_menu_one' onclick='go_taolun();'><div class='menu_s'><img src='"+menu_img2_a+"'></div> <div class='menu_x "+menu_text2+"'>你问我答</div> </div>"
	+ "<div class='wx_menu_one' onclick='go_my_order();'><div class='menu_c'><img src='"+menu_img3_a+"'></div> <div class='menu_cx "+menu_text3+"'>我已订阅</div> </div>"
	+ "<div class='wx_menu_one' onclick='go_scholarship();'><div class='menu_s'><img src='"+menu_img4_a+"'></div> <div class='menu_x "+menu_text4+"'>边学边赚</div> </div>"
	+ "<div class='wx_menu_one' onclick='go_personal();'><div class='menu_s'><img src='"+menu_img5_a+"'></div> <div class='menu_x "+menu_text5+"'>我的</div> </div></div>";


var body = document.body;
var div = document.createElement("div");
//div.id = "mDiv";
div.innerHTML = menu_html;
body.appendChild(div);

function go_taolun(){
	
	window.location.href=menu_path+'jsp/wx/post/post_area.html';
	
}

function go_my_order(){
	
	 
	window.location.href=menu_path+'jsp/wx/Course_Order/Course_Order.html';
	
}


function go_scholarship(){
		
		window.location.href=menu_path+'jsp/wx/Scholarship/Scholarship.html?v=0.4';
	}

function go_personal(){
	
	window.location.href=menu_path+'jsp/wx/Person_Center/Personal.html?v=0.3';
}