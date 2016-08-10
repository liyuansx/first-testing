<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
    <#if (sys.bsetName)??>
    	<title><#if map.nowcate?? >${map.nowcate.catName}<#else>所有商品</#if>列表页--${sys.bsetName}</title>
    <#else>
	    <title><#if map.nowcate?? >${map.nowcate.catName}<#else>所有商品</#if>列表页--${seo.mete}</title>
    </#if>
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/mztheme.css" />
	<link rel="stylesheet" href="${basePath}/css/build/mz.theme.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/home/iconfont.css" />
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
	<!--<meta name="flexible" content="initial-dpr=1" />-->
	<link rel="stylesheet" href="${basePath}/css/swiper-3.3.1.min.css">
	
    <!--[if lt IE 9]> 
      <script src="${basePath}/js/html5shiv.min.js"></script>
      <script src="${basePath}/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
		<div class="head-wrapper">
			<a href="javascript:;"onclick="javascript:history.go(-1)" class="back-link">
				<img src="${basePath}/img/sharedetail/back@2x_02.png" class="img-link img-f" />
			</a>
			<span class="head-center">${map.nowcate.catName}</span>
			<a href="${basePath}/main.html">
				<img src="${basePath}/img/sharedetail/shouye@2x.png" class="img-link img-r">
			</a>
		</div>
		<div class="line-space"></div>
		<a href="#">
			<img  src="${basePath}/img/beauty/lunbo@2x.png" class="brand-img"/>
		</a>
		<div class="" id="container-warper"> 
			<div class="brand-f">
				<ul class="brand-ul">
					<li class="bt-item">
						<a href="#" class="filter-item item-selected" data-type="show1">品牌</a>
					</li>
					<li class="bt-item item-space">
						<a href="#" class="filter-item" data-type="show2">价格</a>
					</li>
					<li class="bt-item item-space">
						<a href="#" class="filter-item" data-type="show3">功效</a>
					</li>
				</ul>
			</div>
			<div class="container-filter" id="filter-type-brand" style="display: none;">
				<div class="clear-float"></div>
				<div class="space-line"></div>
				<ul class="detail-ul choose-type" id="show1">
					<li class="detail-type">
						<a href="#">
							雅诗兰黛
						</a>
					</li>
					<li class="detail-type">
						<a href="#">
							资生堂
						</a>
					</li>
					
				</ul>
				<ul class="detail-ul" id="show2" style="display: none;">
					<li class="detail-type">
						<a href="#">
							0-59元
						</a>
					</li>
					<li class="detail-type">
						<a href="#">
							60-119元
						</a>
					</li>
					<li class="detail-type">
						<a href="#">
							120-299元
						</a>
					</li>
					
				</ul>
				<ul class="detail-ul" id="show3" style="display: none;">
					<li class="detail-type">
						<a href="#">
							保湿保水
						</a>
					</li>
					<li class="detail-type">
						<a href="#">
							防晒护肤
						</a>
					</li>
					<li class="detail-type">
						<a href="#">
							化妆粉底
						</a>
					</li>
				</ul>
				<div class="clear-float"></div>
				<div class="space-line"></div>
				<div class="footer-load">
					<a href="#" class="load-more">
						下拉查看更多
					</a>
				</div>
			</div>
		</div>
		<div class="page-mask" id="mask-bg" style="display: none;"></div>
		
		
		<#if map.pb??>
			<#list map.pb.list as product>
				<a href="${basePath}/item/${product.goodsInfoId}.html">
					<div class="m_hot">
						
						<img class="hot061590" src="<#if product.goodsInfoImgId??>${product.goodsInfoImgId}</#if>" />
						<div class="Opium">
							 <#if (product.productName?length>30)>
	                            ${product.productName?substring(0,30)}
	                         <#else>
	                            ${product.productName}
	                         </#if>
						</div>
						<div class="price146">
							${product.goodsInfoMarketPrice}￥
						</div>
						<div class="price79">
							${product.goodsInfoPreferPrice}￥
						</div>
						<a href="${basePath}/item/${product.goodsInfoId}.html">
							<img class="buy" src="${basePath}/img/home/buy@3x.png"></img>
						</a>
					</div>
				</a>
			</#list>
		</#if>
		
		<img class="gouwuche1" src="${basePath}/img/home/shopping@3x.png"></img>
		<a href="#top" id="top-link-block" class="hidden">
			<img class="top" src="${basePath}/img/home/top@3x.png"></img>
		</a>
		
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${basePath}/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/jquery.lazyload.js"></script>
    <script src="${basePath}/js/goods/goods_list.js"></script>
    <script src="${basePath}/js/flushMenu.js"></script>
  	<script src="${basePath}/js/touchmove.js"></script>
  	<script src="${basePath}/js/swiper-3.3.1.min.js"></script>
	<script src="${basePath}/js/stickUp.js"></script>
	<script src="${basePath}/js/main.js"></script>
    <script src="${basePath}/js/brand/brand-type.js"></script>
    	
    <script>
	  $(function(){
		FastClick.attach(document.body);
		$('.cate').click(function(){
		  if($(this).attr('class').indexOf('hover')>=0){
			$(this).removeClass('cate_hover');
		  }
		  else{
			$(this).addClass('cate_hover');
		  }
		});
		$("#keleyi-menu").keleyi({
		  width: '100%',
		  item_background_color: '#FAFAFA',
		  item_background_color_hover: '#FAFAFA',
		  item_margin: '0',
		  bar_background_color: '#FAFAFA'
		});
		$('img.lazy').lazyload({
		  palceholder : 'images/loading.gif',
		  effect : 'fadeIn'
		});
		<!-- 以下是分享部分 -->
		 var onBridgeReady=function(){  
	   //发送给朋友  
	   WeixinJSBridge.on('menu:share:appmessage', function(argv){  
		  WeixinJSBridge.invoke('sendAppMessage',{  
			 "img_url":dataForWeixin.MsgImg,  
			 "img_width":"120",  
			 "img_height":"120",  
			 "link":dataForWeixin.url,  
			 "desc":dataForWeixin.desc,  
			 "title":dataForWeixin.title  
		  }, function(res){(dataForWeixin.callback)();});  
	   });  
	   //发送到朋友圈  
	   WeixinJSBridge.on('menu:share:timeline', function(argv){ 
		  WeixinJSBridge.invoke('shareTimeline',{  
			 "img_url":dataForWeixin.MsgImg,  
			 "img_width":"120",  
			 "img_height":"120",  
			 "link":dataForWeixin.url,  
			 "desc":dataForWeixin.desc,  
			 "title":dataForWeixin.title  
		  }, function(res){(dataForWeixin.callback)();});});  
		};  
		if(document.addEventListener){  
		   document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);  
		}else if(document.attachEvent){  
		   document.attachEvent('WeixinJSBridgeReady'   , onBridgeReady);  
		   document.attachEvent('onWeixinJSBridgeReady' , onBridgeReady);  
		}  
	  });
	  
	  var dataForWeixin={ 
	   MsgImg:"http://mobile.ningpai.com/app/h5/images/intro.jpg", 
	   url:"<#if map.nowcate?? >http://shop.ningpai.com/mobile/getwxcode3.htm?toUrl=list/${map.nowcate.catId}.html<#else>http://shop.ningpai.com/mobile/getwxcode3.htm?toUrl=list/allproduct.html</#if>",  
	   title:"发现一些好商品",  
	   desc:"<#if map.nowcate?? >${map.nowcate.catName}<#else>所有商品</#if>",
	   callback:function(  
		//这里是分享成功后的回调功能  
	   ){}  
	};
	  		
	</script>
	<script>
	 $(function(){
          $("#searchBtn").click(function(){
              $("#storeId").val($(".storeId").val());
              if($("#title").val()==''){
                  $("#title").val($("#title").attr("placeholder"));
              }
              $("#searchProductForm").submit();
          })
      })
	</script>
  </body>
</html>
