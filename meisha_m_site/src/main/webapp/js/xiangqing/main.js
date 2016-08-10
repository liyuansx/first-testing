// Empty JS for your own code to be here
//幻灯
$(document).ready(function() {
	var swiper = new Swiper('.swiper-container', {
		pagination: '.swiper-pagination',
		paginationClickable: true,
		spaceBetween: 30,
	});
	
	$(document).scroll(function(e) {
		// 返回顶部
		var height = $(document).scrollTop();
		if (height >= 200) {
			$('#top-link-block').removeClass('hidden');
		} else {
			$('#top-link-block').addClass('hidden');
		}
	});
	
	$(".jieshao").click(function(){
		$(".jieshao_selected").removeClass("jieshao_selected");
		$(this).addClass("jieshao_selected");
		var idtype= "#"+$(this).attr("datatype");
		$(".hidden-new").removeClass("hidden-new");
		$(".dateil").addClass("hidden-new");
		$(idtype).removeClass("hidden-new");
		//加载评论内容
		loadComment(1,0);
	});
	
	$(".fangfa").click(function(){
		$(".fangfa_selected").removeClass("fangfa_selected");
		$(this).addClass("fangfa_selected");
//		var idtype= "#"+$(this).attr("datatype");
//		$(".hidden-new2").removeClass("hidden-new2");
//		$(".newdetail").addClass("hidden-new2");
//		$(idtype).removeClass("hidden-new2");
		//加载评论内容
		loadComment(1,0);
	});
	
	var add_to_cart = 0;
	$('.detail_add_cart').on('click', function() {
		if(add_to_cart<9){
			add_to_cart++;
		}else{
			alert("最多只能选择9个商品");
			return;
		}
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
				left: offset.left+700*width/750,
				top: v,
				width: 0*width/750,
				height: 0*width/750
			}
		});
		$(".hongdian").html(add_to_cart);
//		flyer.destroy(); //移除dom
//		flyer.addClass(".display_none"); 
	});
	
	var addCartNum=0;
	$(".detail_add_cart1").click(function(){
		 $.get("../addproducttoshopcarnew.htm?goodsNum=1&productId="+$(this).attr("productId")+"&distinctId=1103",
			function(data,status){
		  });
	});
});
