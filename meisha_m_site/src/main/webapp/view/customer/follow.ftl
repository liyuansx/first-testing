<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
	<meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/wishlist.css" />
	<link rel="stylesheet" href="${basePath}/css/build/wish.list.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/home/iconfont.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
	<link rel="stylesheet" href="${basePath}/css/swiper-3.3.1.min.css">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]> 
      <script src="${basePath}/js/html5shiv.min.js"></script>
      <script src="${basePath}/js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
	</style>
  </head>
  <body>
	<div class="head-wrapper">
			<a href="javascript:;"onclick="javascript:history.go(-1)"  class="back-link">
				<img src="${basePath}/img/sharedetail/back@2x_02.png" class="img-link img-f" />
			</a>
			<span class="head-center">心愿单</span>
			<a href="${basePath}/main.html">
			<img src="${basePath}/img/sharedetail/shouye@2x.png" class="img-link img-r">
			</a>
	</div>
	<div class="line-space"></div>
	<div class="type-container">
		<div class="type-item choose-item" datatype="shoucang">
			<a href="#">收藏的</a>
		</div>
		<div class="type-item" datatype="buyed">
			<a href="#">购买过的</a>
		</div>
	</div>
	<div class="clear-float"></div>
	
	<div class="good-container">
			<ul class="good-ul" id="shoucang">
				<#if pb.list??&&(pb.list?size!=0)>
                        <#list pb.list as follow>
							<#if follow.good??>
								<li class="good-li">
									<div class="good-item">
										<a href="${basePath}/item/${follow.goodsId}.html"><img src="<#if follow.good.goodsImg??>${follow.good.goodsImg}</#if>" class="good-item-img"/></a>
										<div class="good-content"><a href="#" class="text-link"><#if follow.good??>
										<#if (follow.good.goodsName?length>20)>${follow.good.goodsName?substring(0,20)}
				                         <#else>
				                            ${follow.good.goodsName}
				                         </#if></#if></a></div>
										<div class="good-qk">
											<div class="good-price"><span><#if follow.good??>${follow.good.goodsPrice?string('0.00')}</#if></span>￥</div>
											<div class="good-sell-num">还剩<span><#if follow.good.goodStock?? >
                                                    <#if (follow.good.goodStock>0)>${follow.good.goodStock}<#else>0</#if></#if></span>件</div>
										</div>
										 <input type="hidden" value="${follow.goodsId}" />
									</div>
								</li>
                        	</#if>
                        </#list>
						</ul>
                        <#else>
                     	 <div class="default-container good-ul" id="shoucang">
							<img src="${basePath}/img/wish/aixin@2x.png" class="love-img"/>
							<div class="info-text">你还没有喜欢的东东呢，赶紧去逛逛吧</div>
							<div class="footer-btn">
								<a href="#"><img src="${basePath}/img/wish/temai@2x.png" class="go-img"/></a>
							</div>
						</div>
                        </#if>

						<div class="default-container good-ul hidden" id="buyed">
							<img src="${basePath}/img/wish/aixin@2x.png" class="love-img"/>
							<div class="info-text">你还没有喜欢的东东呢，赶紧去逛逛吧</div>
							<div class="footer-btn">
								<a href="#"><img src="${basePath}/img/wish/temai@2x.png" class="go-img"/></a>
							</div>
						</div>
	
    <script src="${basePath}/js/jquery.js"></script>
	<script src="${basePath}/js/wish/main.js"></script>
  </body>
</html>
