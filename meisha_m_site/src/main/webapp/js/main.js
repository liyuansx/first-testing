// Empty JS for your own code to be here
//幻灯
$(document).ready(function() {
	var swiper = new Swiper('.swiper-container', {
		pagination: '.swiper-pagination',
		paginationClickable: true,
		spaceBetween: 30,
	});
	//	$('#top-link-block').addClass('hidden');
	$(document).scroll(function(e) {

		// 返回顶部
		var height = $(document).scrollTop();
		if (height >= 200) {
			$('#top-link-block').removeClass('hidden');
			$('#dingbu').addClass('dingbufloat');
		} else {
			$('#top-link-block').addClass('hidden');
			$('#dingbu').removeClass('dingbufloat');
		}
		// 固定导航
		//		var nav =$("#dingbu").offset().top;
		//		console.log('nav' + nav);
		//		if (height <= 90) {
		//			$("#dingbu").removeClass('dingbufix');
		//		} else {
		//			$('#dingbu').addClass('dingbufix');
		//		}

	});

});

jQuery(function($) {
	$(document).ready(function() {
		//enabling stickUp on the '.navbar-wrapper' class 
		$('#dingbu').stickUp();
	});
});