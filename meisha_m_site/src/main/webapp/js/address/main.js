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
	$('.popup-modal').click(function(){
		$("#deladdressId").val($(this).attr("dataaddressId"));
	});
	$(document).on('click', '.cancel', function (e) {
		e.preventDefault();
		$.magnificPopup.close();
		$("#deladdressId").val("");
	});
	$(document).on('click', '.enter', function (e) {
       	$("#delForm").submit();
		e.preventDefault();
		$.magnificPopup.close();
	});
});