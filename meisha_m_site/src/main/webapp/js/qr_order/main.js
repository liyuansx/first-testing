// Empty JS for your own code to be here
//幻灯
$(document).ready(function() {
	$(".addr_defaut").click(function() {
		$(".addr_defaut").removeClass("addr_selected");
		$(this).addClass("addr_selected");
	});
	$('.popup-modal').magnificPopup({
		type: 'inline',
		preloader: false,
		focus: '#username',
		modal: true
	});
	//	地址弹框
	$('.addr_modal').magnificPopup({
		type: 'inline',
		preloader: false,
		fixedContentPos:true,
		focus: '#username',
		modal: true
	});
	$(document).on('click', '.quxiao', function(e) {
		e.preventDefault();
		$.magnificPopup.close();
	});
	$(document).on('click', '.cancel', function(e) {
		e.preventDefault();
		$.magnificPopup.close();
	});
	
	$(document).on('click','.modal_item',function(e){
		$(".modal_select").removeClass("modal_select");
		$(this).addClass("modal_select");
		$("#addressId").val($(this).attr("addressId"));
		$("#addName").html($(this).attr("addressName")+" "+$(this).attr("addressMoblie"));
		$("#addDetail").html($(this).attr("addressDetail"));
		$("#streetDetail").html($(this).attr("addressDetailInfo"));
	});
	
	//	商品数目增减
	$(".gou").click(function(){
		$(".gou").removeClass("gou_select");
		$(this).addClass("gou_select");
	})
	$(".minus").click(function() {
		var value = $(this).next().html();

		$(this).next().next().addClass("add_active");
		if (value > 1) {
			value--;
		}
		if (value > 2) {
			$(this).addClass("add_active");
		} else {
			$(this).removeClass("add_active");
		}
		$(this).next().html(value);
	});
	$(".add").click(function() {
		var value = $(this).prev().html()
		$(this).prev().prev().addClass("add_active");
		if (value < 10) {
			value++;
		} else {
			$(this).removeClass("add_active");
		}
		$(this).prev().html(value);
	});
});