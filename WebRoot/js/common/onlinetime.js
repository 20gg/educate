$(function(){
	var obj=document.getElementById("checktimeone");
	if(obj){
		checkTimeall();
		setInterval(checkTimeall, 500000);
	}
	
});