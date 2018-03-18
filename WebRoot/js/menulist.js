		var ME={};
		ME.clickObj={};

		ME.init=function(m) {
			this.menus=m;
			this.setMenu();
			var width=$("body").width()-200;
			
			
			
		
		var w=$("body").width()-103;
		var h=$("body").height();
		$("#rightmain").css("width",w+"px");
		$("#rightmain").css("height",h+"px");
		$("#rightmain").css("top","0");
		//document.getElementById("rightmain").src=getRootPath()+"/main.jsp";
		
		$("#secondMenus").css("display","none");
		
		}

		ME.setMenu=function() {
			if (!this.menus) return;
			var html = "";
			for (var i = 0; i < this.menus.length; i++) {
				var one = this.menus[i];
				if(one.grade==1){
					html += "<div class='menu' data='"+i+"' ><span class='fa "+one.menu_img+"'></span>" + one.menu_name + "</div>";
					
				}
				
			}
			$("#menulist").append(html);

		}

		window.onload = function() { 
			
		
			
			//滚动事件
		$("body").on("mousewheel DOMMouseScroll","#menulist,.twomenus",function (e) {
    
    var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) ||  // chrome & ie
                (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1));              // firefox
		var s1=$("#leftmain").height();
    	var s2=$(this).height()+60-s1;//允许上移的高度
 		var s=0;
    	var now=$(this).offset().top;
    	
    	//alert(now)
   			if (delta > 0) {
        // 向上滚
      		if(e.originalEvent.wheelDelta&&e.originalEvent.wheelDelta>0){
        		s=e.originalEvent.wheelDelta;
        	}else{
        		s=(-1)*e.originalEvent.detail*10;
        	}
        	
        	var  cha=0-now;
        	if(now<0){
        		
        		if(s>cha){
        			$(this).css("top",0);
        		}else{
        				var s=now+s;
        			$(this).css("top",s);
        		}
        	}
        		
        		
    		} else if (delta < 0) {
        // 向下滚
        	if(e.originalEvent.wheelDelta&&e.originalEvent.wheelDelta<0){
        		s=e.originalEvent.wheelDelta;
        	}else{
        		s=(-1)*e.originalEvent.detail*10;
        	}
        	var nowz=(-1)*now;
        	
        	if(nowz<s2){
        		s=now+s;
        		
         		$(this).css("top",s);
        	}
        	
      
      
    			}
});

	//菜单点击事件
		$("#menulist").on("click",".menu",function(){
			var index=parseInt($(this).attr("data"));
			var one=ME.menus[index];
			ME.clickObj=one;
			$(".menu").removeClass("checkon");
			$(this).toggleClass("checkon");
			$("#secondMenus").css("display","none");
			if(parseInt(one.is_url)==1){
				//无下一页
					var w=$("body").width()-103;
				var h=$("body").height();
				$("#rightmain").css("width",w+"px");
				$("#rightmain").css("height",h+"px");
				$("#rightmain").css("top","0");
				document.getElementById("rightmain").src=one.menu_url;
			}else{
			
					var html="<div class='twomenus'><div class='oneheadmenu'>"+one.menu_name+"</div>";
					for(var i=0;i<ME.menus.length;i++){
						
						var two = ME.menus[i];
						if(two.grade==2&&two.parent_id==one.menu_id){
							html += "<div class='twomenu'  data='"+i+"' >" + two.menu_name;
							
						
							
									
							html+="</div>";
							
						}
						
						
						
						}
			
					html+="</div>";
					$("#secondMenus").html(html)
					$("#secondMenus").css("display","block");
					var width=$("body").width()-203;
					var height=$("body").height();
					$("#rightmain").css("width",width+"px");
					$("#rightmain").css("height",height+"px");
					$("#rightmain").css("top","0");
				
					$("#secondMenus").find(".twomenu").eq(0).trigger("click");
				
				
			}
			
		});

			$("#secondMenus").on("click",".twomenu",function(){
					var index=parseInt($(this).attr("data"));//
					var one=ME.menus[index];
					
					$(".twomenu").removeClass("checkon");
					$(this).toggleClass("checkon");
					
					if(parseInt(one.is_url)==1){
				//无下一页
				var width=$("body").width()-203;
				var height=$("body").height();
				
				$("#rightmain").css("width",width+"px");
				$("#rightmain").css("height",height+"px");
					$("#rightmain").css("top","0");
				document.getElementById("rightmain").src=one.menu_url;
			}
					
		
			});
			
			
			
			


		}		





