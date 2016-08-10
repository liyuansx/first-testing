$(document).scroll(function(e) {
	if ($(document).scrollTop() >= 200) {
		$("#item-footer").addClass("fix-bottom");
	} else {
		$("#item-footer").removeClass("fix-bottom");
	}
});