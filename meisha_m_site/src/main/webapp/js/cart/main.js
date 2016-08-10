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
	$(document).on('click', '.quxiao', function(e) {
		e.preventDefault();
		$.magnificPopup.close();
	});
	$(document).on('click', '.del-cancel', function(e) {
		e.preventDefault();
		$.magnificPopup.close();
	});
	
	//确定删除
	$(document).on('click', '.del-ok', function(e) {
		//确定删除
		delc();
		e.preventDefault();
		$.magnificPopup.close();
	});
	$(".popup-modal").click(function(){
		$('#shoppingCartId').val($(this).attr("cartdata"));
		$('#goodsInfoId').val($(this).attr("gooddata"));
		
	});
	
//	商品数目增减
//	$(".minus").click(function() {
//		var value = $(this).next().html();
//		$(this).next().next().addClass("add_active");
//		if(value>1){
//			value --;
//		}
//		if(value>2){
//			$(this).addClass("add_active");
//		}else{
//			$(this).removeClass("add_active");
//		}
//		$(this).next().html(value);
//	});
	
});