// Empty JS for your own code to be here
//幻灯
$(document).ready(function() {
	
	
	$(document).scroll(function(e) {
		// 返回顶部
		var height = $(document).scrollTop();
		if (height >= 200) {
			$('#top-link-block').removeClass('hidden');
		} else {
			$('#top-link-block').addClass('hidden');
		}
	});
	var add_to_cart = 0;
	$('.kaufen, .btn_cart').on('click', function() {
		add_to_cart++;
		var offset = $(".gouwuche1").offset();
		var height = $(document).scrollTop();
		var v = offset.top - height;
		var width =  document.body.clientWidth;
		console.log( document.body.clientWidth);
		var flyer = $('<img class="u-flyer" src="../img/lunbo/shopping@3x.png"/>');
		flyer.fly({
			start: {
				left: event.pageX,
				top: event.pageY - height
			},
			end: {
				left: offset.left+60*width/750,
				top: v,
				width: 20*width/750,
				height: 20*width/750
			}
		});
		$(".hongdian").html(add_to_cart);
//		flyer.destroy(); //移除dom
//		flyer.addClass(".display_none"); 
	});
	
	var addCartNum=0;
	$(".add_cart1").click(function(){
		 $.get("../addproducttoshopcarnew.htm?goodsNum=1&productId="+$(this).attr("productId")+"&distinctId=1103",
			function(data,status){
		  });
	});
	
	$(".tuijian").click(function(){
		$(".tuijian_selected").removeClass("tuijian_selected");
		$(this).addClass("tuijian_selected");
	});

});