//条件筛选
$(document).ready(function() {
	$(".filter-item").click(function(){
		$(".item-selected").removeClass("item-selected");
		$(this).addClass("item-selected");
		var datatype="#"+$(this).attr("data-type");
		$("#container-warper").addClass("container-warper");
		$("#mask-bg").show();
		$("#filter-type-brand").show();
		$(".choose-type").hide();
		$(datatype).addClass("choose-type");
		$(datatype).show();
		$("body").attr("height","100%"); 
		$("body").addClass("hidden-scroll");
		$(window).bind('scroll');
		$(window).scroll(function() {
			$(this).scrollTop(0);
		});
	});
	
	$("#mask-bg").click(function(){
		$(window).unbind('scroll');
		$("#container-warper").removeClass("container-warper");
		$("#filter-type-brand").hide();
		$(this).hide();
	});
});
