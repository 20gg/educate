//document.write("<link href='../../../css/tan_c.css'>"+"<" + "/link>");
$.ajax({
	type:"post",
	url:"http://www.daodaketang.com/educate/wx/ajaxIsSub.do",
	data:{openid:openid},
	dataType:"text",
	success:function(data){
		
			
		 
		if(data=='400'){
			
			
			//未关注
			html="<div style='position:fixed; top:0px; left:0px; width:100%; height:60px; background:url(http://www.daodaketang.com/educate/images/bg_03.png)no-repeat;"
				+"background-size:100%; z-index: 100000;' id='t_c'><div onclick='hide_tc();'  style='width:13%; height:60px; position:absolute;"
				+"left:0px;'></div><div  style='width:24%; height:60px; position:absolute; right:0px;'onclick='go_gz();' ></div><div style='clear:both;'></div></div>";
			
			$('body').append(html);
		}else{
			if($("#t_c")){
				$("#t_c").hide();
			}
			
		}
		
	}
	
	
})




	



function  hide_tc(){
	
	$("#t_c").hide();
	
}

function go_gz(){
	
	window.location.href='http://www.daodaketang.com/educate/jsp/wx/art/new_file.html';
}


