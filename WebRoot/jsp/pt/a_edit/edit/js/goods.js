
$(function(){
	


	//商品
	$("#searchgoods").click(function(){
		
		var datainfo={};

	


		var type=parseInt($("#search_kind").val());//1按商品名1按货号
		
		if(type==1){
			
			datainfo.gname=$("#search_goods_info").val();
		}else{
			
			datainfo.gcode=$("#search_goods_info").val();
		}
		var goods=ED.data.models[ED.nowIndex].goods;
		if(goods!=null&&goods.length>0){
			
			var has="";
			for(var i=0;i<goods.length;i++){
				if(goods[i].gid!=null&&goods[i].gid.trim()!=""){
					has+=goods[i].gid+",";
					
				}
				
			}
			if(has!=""){
				has=has.substring(0, has.length-1);
			}
			
			datainfo.hasgoods=has;
			
		}
	ajaxgoods(datainfo);

	});




	$("#goodsmenu").on("click","#gopage",function(){
		
		var nowpn=parseInt($("#pagenum").val());
		
		
		
		var datainfo={};
		
		

		datainfo.page=nowpn;
		
		
		var type=parseInt($("#search_kind").val());//1按商品名1按货号
		
		if(type==1){
			
			datainfo.gname=$("#search_goods_info").val();
		}else{
			
			datainfo.gcode=$("#search_goods_info").val();
		}
		var goods=ED.data.models[ED.nowIndex].goods;
		if(goods!=null&&goods.length>0){
			
			var has="";
			for(var i=0;i<goods.length;i++){
				if(goods[i].gid!=null&&goods[i].gid.trim()!=""){
					has+=goods[i].gid+",";
					
				}
				
			}
			if(has!=""){
				has=has.substring(0, has.length-1);
			}
			datainfo.hasgoods=has;
			
		}
		ajaxgoods(datainfo);
		


	});


		
		$("#addU").on("click",function(){
			
			var goods=[];
			
			
			
			$(".cg_one:checked").each(function(){
				var one={};
				one.g=$(this).val();
				var dom=$(this).closest("tr");
				one.n=dom.find(".cnum").val();
				one.name=dom.find(".goodsname").text();
				goods.push(one);
				modelinfo.push(one);
			});
			
			if(goods.length<1){
				
				
				layer.msg('请选择商品',{


					time:1500
					
				});
				return;
			}
			
		
			
			var html="";
			for(var i=0;i<modelinfo.length;i++){
				var one=modelinfo[i];
				html+="<div class='oneg' data="+one.g+" style='color:green;font-size:12px;display:box;float:left;width:100%'>"+one.name+","+one.n+"<span class='btn btn-danger' onclick='deletethis(this)'>删除</span></div>";
				
			}
			
			
			$("#modelinfo").html(html);
			
			
			$("#choosegoods").modal("hide");
			
			
			
			
			
		
			
			
		});
	
	
	
	
});



/**
 * 查找商品
 * 
 * @param datainfo
 */
function ajaxgoods(datainfo){
	
	$.ajax({
		type:"post",
		url:getRootPath()+"/goods/queryAllSku.do",
		data:datainfo,
		dataType:"json",
		success:function(data){
			var list=data.list;
			var html='';
			for(var i=0,len=list.length;i<len;i++){
				var  one=list[i];
				var osid=parseInt(one.offshopid);
				var img_list=one.listimg;
			
				html+='<tr data=\''+JSON.stringify(one)+'\'>';
				html+='<td><input type="checkbox" name="goodsone" class="goodonec" value='+one.goodsid+' /></td>';
				html+='<td class="goodsname" style="color:#0077DD;font-size:13px">'+one.goodsname+'</td>';
				
				html+='<td><img src='+getRootPath()+img_list+' style="width:30px;height:30px;border-radius:2px;"></td>';
				
				if(osid==0){
					
					html+='<td>￥'+(one.saleprice/100).toFixed(2)+'</td>';
				}else{
					var price=parseInt(one.saleprice);
					var pricetype=parseInt(one.pricetype);
					
					if(pricetype==1){
						//增加
						price=price+parseInt(one.offprice);
						
					}else{
						price=price-parseInt(one.offprice);
					}
					
					html+='<td>￥'+(price/100).toFixed(2)+'</td>';
				}
				html+='</tr>';
				
			}
			if(data.pageCount>1){
				
				var nowpn=parseInt(data.pageNum);
				html+='<tr><td></td><td colspan="3" style="text-align:center;">';
				html+='<div class="btn btn-default pagebtn" onclick="gopage(1)">首页</div>';
				html+='<div class="btn btn-default pagebtn" onclick=gopage('+(nowpn-1)+')>上一页</div>';
				html+='<div class="btn pagebtn">'+nowpn+'/'+data.pageCount+'</div>';
				html+='<div class="btn btn-default pagebtn" onclick=gopage('+(nowpn+1)+')>下一页</div>';
				html+='<div class="btn btn-default pagebtn" onclick="gopage('+data.pageCount+')">末页</div>';
				html+='<input class="pagebtn" style="height:30px;lin-height:30px;" type="text" id="pagenum" value="'+nowpn+'" onkeyup="value=value.replace(/[^0-9]/g,\'\')">';
				html+='<div class="btn btn-default pagebtn" id="gopage">跳转</div><td></tr>';
				
			
				
				
				
			}
			
			
			$("#choosegoods").modal("show");
			
			$("#goodsmenu").html(html);
			
		}
		
	});
}

function gopage(page){
	
	
	
	var nowpn=parseInt(page);
	if(nowpn<1){
		nowpn=1;
	}
	
	
	var datainfo={};
	
		

	datainfo.page=nowpn;
	
	
	var type=parseInt($("#search_kind").val());//1按商品名1按货号
	
	if(type==1){
		
		datainfo.gname=$("#search_goods_info").val();
	}else{
		
		datainfo.gcode=$("#search_goods_info").val();
	}
	var goods=ED.data.models[ED.nowIndex].goods;
	if(goods!=null&&goods.length>0){
		
		var has="";
		for(var i=0;i<goods.length;i++){
			if(goods[i].gid!=null&&goods[i].gid.trim()!=""){
				has+=goods[i].gid+",";
				
			}
			
		}
		if(has!=""){
			has=has.substring(0, has.length-1);
		}
		
		datainfo.hasgoods=has;
		
	}
	ajaxgoods(datainfo);
}
