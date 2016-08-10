// Empty JS for your own code to be here
//幻灯
$(document).ready(function() {
	$("#fade").click(function() {
		$("#fade").css("display", "none");
		$("#light").css("display", "none");
	});
	$(".start_modal").click(function() {
//		$("#fade").css("display", "block");
//		$("#light").css("display", "block");
		console.log('lian')
	});
	var temp;
	$(".pinpai").click(function() {
		$(".pinpai").removeClass("pinpai_selected");
		$(this).addClass("pinpai_selected");
		var get = '#'+$(this).data("search");
//		console.log(get);
		$(temp).css("display","none");
		$(get).css("display","block");
		temp = get;
//		console.log(get)
	});
	
	$('.start_modal').magnificPopup({
		type: 'inline',
		preloader: false,
		modal:false,
		alignTop:true,
		closeBtnInside:false
	});
});