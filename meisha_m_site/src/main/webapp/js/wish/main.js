// Empty JS for your own code to be here
//幻灯
$(document).ready(function() {
	$(".type-item").click(function(){
		$(".choose-item").removeClass("choose-item");
		$(this).addClass("choose-item");
		var datatype="#"+$(this).attr("datatype");
		$(".hidden").removeClass("hidden");
		$(".good-ul").addClass("hidden");
		$(datatype).removeClass("hidden");
	});
});
