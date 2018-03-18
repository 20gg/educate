 
var dir="";
var dir2="";
var key="daoda";
var us="daoda2";

var signs="";
function  back_url(bf_url){
	var t=to_t();
	 
	for(var ii=0;ii<bf_url.length;ii++){
		
		if(bf_url[ii].definition==230){
			
			dir=parse_url(dir2);				
			dir2=bf_url[ii].url;
			
			 
		}
		
	} 
	signs=md5(key+dir+t+us);
	 
	 
	return dir2+"?t="+t+"&us="+us+"&sign="+signs;
}


function  back_url2(bf_url2){
	var t=to_t();
	  
			dir=parse_url(dir2);				
			dir2=bf_url2;
			
		 
	signs=md5(key+dir+t+us);
	 
	 
	return dir2+"?t="+t+"&us="+us+"&sign="+signs;
}


function to_t(){
	
	var d = new Date();
     
	
 return parseInt(d.getTime()/1000+7200).toString(16);
	
}

function parse_url(_url){
	var str=_url.split(".com")[1];
	var ss=str.split("/");
	var num=0;
	var res="";
	for(var i=0,len=ss.length;i<len;i++){
		var one=ss[i];
		if(one.trim()!=""){
			res+="/"+one.trim();
			num++;
			
			if(num>1){
				break;
			}
		}
		
	}
	return res+"/";
	}

 