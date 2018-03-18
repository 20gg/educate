
/**
 * 轮播图
 * 
 */


$('.swiper-container').each(function(){
	var pagi = null, time = null, loo = null;

	if ($(this).find(".swiper-wrapper .swiper-slide").length > 1) {
		pagi =$(this).find(".swiper-pagination");
		time = "2500";
		loo = true;
	} else {
		pagi = null;
		time = null;
		loo = null;
	}
	new Swiper($(this).find('.swiper-container'), {
		pagination : pagi,
		paginationClickable : true,
		spaceBetween : 0,
		centeredSlides : true,
		autoplay : time,
		loop : loo,
		autoplayDisableOnInteraction : false
	});
});


