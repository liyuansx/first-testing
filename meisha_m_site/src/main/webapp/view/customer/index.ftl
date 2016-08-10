<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <#if (sys.bsetName)??>
    	<title>个人中心-${(sys.bsetName)!''}</title>
    	<input type="hidden" id="bsetName" value="${(sys.bsetName)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    <#else>
	    <title>个人中心-${(seo.mete)!''}</title>
	    <input type="hidden" id="bsetName" value="${(seo.mete)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    </#if>

	<#assign basePath=request.contextPath>
    <!-- Bootstrap -->
    <link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
    
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/personInfo.css" />
	<link rel="stylesheet" href="${basePath}/css/build/personInfo.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/magnific-popup.css" />
	<!--<script src="${basePath}/js/flexible.debug.js"></script>
	<script src="${basePath}/js/flexible_css.debug.js"></script>-->
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
  </head>
  <body>
    <#--顶部start-->
	<div class="m_head">
		<img src="${basePath}/img/personInfo/gerenxinxi@2x_02.png" class="headbg"></img>
		<a href="javascript:;"onclick="javascript:history.go(-1)">
			<img src="${basePath}/img/personInfo/back@2x_02.png" class="tubiao1"></img>
		</a>
		<a href="${basePath}/main.html">
			<img src="${basePath}/img/personInfo/shouye@2x.png" class="tubiao2"></img>
		</a>
		<img src="${(cust.customerImg)!'${basePath}/images/avatar.jpg'}" class="touxiang"></img>
		<div class="nickname">${cust.customerNickname}</div>
	</div>
	<#--顶部end-->
	<div class="m_nav">
		<a href="${basePath}/customer/myorder.html" class="m_item">
			<img src="${basePath}/img/personInfo/tubiao@2x_08.png" class="tubiao3"></img>
			<div class="order">全部订单</div>
		</a>
		<a href="${basePath}/customer/myorder-0-1.html" class="m_item">
			<img src="${basePath}/img/personInfo/tubiao@2x_10.png" class="tubiao3"></img>
			<div class="order">待付款</div>
		</a>
		<a href="${basePath}/customer/myorder-2-1.html" class="m_item">
			<img src="${basePath}/img/personInfo/tubiao@2x_12.png" class="tubiao3"></img>
			<div class="order">待发货</div>
		</a>
		<a href="${basePath}/customer/myorder-3-1.html" class="m_item">
			<img src="${basePath}/img/personInfo/tubiao@2x_14.png" class="tubiao3"></img>
			<div class="order">待收货</div>
		</a>
		<a href="${basePath}/customer/myorder-4-1.html" class="m_item">
			<img src="${basePath}/img/personInfo/tuihuo@2x.png" class="tubiao3"></img>
			<div class="order">退货退款</div>
		</a>
	</div>
	<div class="m_banner"></div>
	
	<#--底部-->
	<div class="m_back">
		<a href="${basePath}/customer/coupon.html">
			<div class="youhui">我的优惠券</div>
			<img src="${basePath}/img/personInfo/tubiao@2x_24.png" class="tubiao4"></img>
		</a>
	</div>
	
	<div class="m_back">
		<a href="${basePath}/addresslist.htm">
			<div class="youhui">收货地址管理</div>
			<img src="${basePath}/img/personInfo/tubiao@2x_24.png" class="tubiao4"></img>
		</a>
	</div>
	<div class="m_back">
		<a href="${basePath}/wishlist.html">
			<div class="youhui">心愿单</div>
			<img src="${basePath}/img/personInfo/tubiao@2x_24.png" class="tubiao4"></img>
		</a>
	</div>
	<div class="m_back">
		<a href="${basePath}/mobilesingleusershare.html">
			<div class="youhui">我的分享</div>
			<img src="${basePath}/img/personInfo/tubiao@2x_24.png" class="tubiao4"></img>
		</a>
	</div>
	<div class="m_back">
		<a href="#tel-modal" class="popup-modal">
			<div class="youhui">客服电话</div>
			<img src="${basePath}/img/personInfo/tubiao@2x_24.png" class="tubiao4"></img>
		</a>
	</div>
		
    <#if !isWx??>
	     <div class="btn_logout logout">退出登录状态</div>
    </#if>

   	<div id="fade" class="black_overlay"></div>
	<div id="tel-modal" class="white-popup-block mfp-hide ">
		<div class="delete_addr">客服电话：0755-28888888</div>
		<div class="quxiao cancel">确定</div>
	</div>
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${basePath}/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/customer/wxforward.js"></script>
    <script src="${basePath}/js/jquery.magnific-popup.min.js"></script>
    <script>
	  $(function(){
		FastClick.attach(document.body);
		$("#keleyi-menu").keleyi({
		  width: '100%',
		  item_background_color: '#FAFAFA',
		  item_background_color_hover: '#FAFAFA',
		  item_margin: '0',
		  bar_background_color: '#FAFAFA'
		});
		$(".logout").click(function(){
			window.location.href="${basePath}/logout.html";
		});
		
		 $('.popup-modal').magnificPopup({
			type: 'inline',
			preloader: false,
			focus: '#username',
			modal: true
		});
		
		$(document).on('click', '.cancel', function (e) {
			e.preventDefault();
			$.magnificPopup.close();
			$("#deladdressId").val("");
		});
		
	  });
	</script>
  </body>
</html>
