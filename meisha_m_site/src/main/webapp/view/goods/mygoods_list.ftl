<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#if (sys.bsetName)??>
    	<title><#if map.nowcate?? >${map.nowcate.catName}<#else>所有商品</#if>列表页--${sys.bsetName}</title>
    <#else>
	    <title><#if map.nowcate?? >${map.nowcate.catName}<#else>所有商品</#if>列表页--${seo.mete}</title>
    </#if>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/build/mobile.header.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/muying.css" />
	<link rel="stylesheet" href="${basePath}/css/build/muying.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/home/iconfont.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
	<!--<meta name="flexible" content="initial-dpr=1" />-->
	<link rel="stylesheet" href="${basePath}/css/swiper-3.3.1.min.css">
	
    <!--[if lt IE 9]> 
      <script src="${basePath}/js/html5shiv.min.js"></script>
      <script src="${basePath}/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  		<#--头部start-->
 		<a href="#" id="top">
			<img class="MAIGANG" src="${basePath}/img/home/MAIGANG@3x.png"></img>
		</a>
		<hr class="m_hr" />
		<div id="dingbu" class="dingbufix">
			<div class="m_nav">
				<a href="#">
					<div class="m_icon iconfont">&#xe604;</div>
				</a>
				<a href="${basePath}/main.html">
					<div class="recommend"><span>推荐</span></div>
				</a>
				<a href="${basePath}/mzlist/4358.html">
					<div class="recommend"><span>美妆</span></div>
				</a>
				<a href="${basePath}/mylist/4457.html">
					<div class="recommend"><span class="recommend-selected">母婴</span></div>
				</a>
				<a href="${basePath}/mobileusershare.html">
					<div class="recommend"><span>分享</span></div>
				</a>
				<a href="#">
					<div class="recommend"><span>特色</span></div>
				</a>
				<a href="#">
					<div class="m_icon iconfont cart_bolder">&#xe622;</div>
				</a>
			</div>
			<div class="search">
			 <form action="${basePath}/searchProduct.htm" method="post" id="searchProductForm" style="margin-bottom:0;">
              <input type="text" value="<#if keyWords??>${keyWords}</#if>" class="m_search" placeholder="喜宝" id="title" name="keyWords"/>
              <input type="hidden" value="0" name="storeId" id="storeId">
              <a href="#" id="searchBtn">
					<div class="search_icon iconfont">&#xe687;</div>
			  </a>
         	 </form>
			</div>
		</div>
		<#--头部end-->
		<div class="swiper-container" style="margin-top: 60px;">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img class="banner1_750_380" src="${basePath}/img/beauty/banner@3x.png"></img>
				</div>
				<div class="swiper-slide">
					<img class="banner1_750_380" src="${basePath}/img/beauty/banner2@3x.png"></img>
				</div>
				<div class="swiper-slide">
					<img class="banner1_750_380" src="${basePath}/img/beauty/banner3@3x.png"></img>
				</div>
			</div>
			<div class="swiper-pagination"></div>
		</div>
		<img class="shopping" src="${basePath}/img/muying/remenfenlei@3x.png"></img>
		<div class="type-warper">
			<a href="${basePath}/typeview/4520.html">
				<div class="type-item">
					<img src="${basePath}/img/muying/znk@3x.png" class="type-img"/>
					<div class="type-title">纸尿裤</div>
				</div>
			</a>
			<a href="${basePath}/typeview/4532.html">
				<div class="type-item item-fl">
					<img src="${basePath}/img/muying/yexh@3x.png" class="type-img"/>
					<div class="type-title">婴儿洗护</div>
				</div>
			</a>
			<a href="${basePath}/typeview/4531.html">
				<div class="type-item item-fl">
					<img src="${basePath}/img/muying/bbyp@3x.png" class="type-img"/>
					<div class="type-title">宝宝用品</div>
				</div>
			</a>
			<a href="${basePath}/typeview/4460.html">
				<div class="type-item item-fl"> 
					<img src="${basePath}/img/muying/fushi@2x.png" class="type-img"/>
					<div class="type-title">营养副食</div>
				</div>
			</a>
		</div>
		<div class="clear-float"></div>
		<img class="shopping" src="${basePath}/img/muying/rexiaopinpai@3x.png"></img>
		<div class="space-line"></div>
		<ul class="brand-ul">
			<#if (map.brands??) && (map.brands?size>0) >
				<#list map.brands as brandtype>
					<#if (brandtype_index<8) >
						<li class="brand-item">
							<a href="${basePath}/brandview/${brandtype.brand.brandId}.html">
								<img class="brand-img"  src="<#if brandtype.brand.brandLogo??>${brandtype.brand.brandLogo}</#if>"></img>
							</a>
						</li>
					</#if>
				</#list>
			</#if>
		</ul>
		<div class="clear-float"></div>
		<img class="shopping danpin-head" src="${basePath}/img/muying/rexiaodanpin@3x.png"></img>
		
		<!--热销单品-->	
		<#if map.pb.list??>
	      <#list map.pb.list as product>
		      	<a href="${basePath}/item/${product.goodsInfoId}.html">
					<div class="hot_danpin">
						<img class="hushoushuang" src="<#if  product.imageList?? && product.imageList?size &gt; 0 >${product.imageList[0].imageBigName!''}</#if>"></img>
						<noscript><img alt="" src="<#if  product.imageList?? && product.imageList?size &gt; 0 >${product.imageList[0].imageBigName!''}</#if>"></noscript>
						<div class="hot_danpan_text">
							${product.productName!''}
						</div>
						<div class="hot_danpan_text_146">
							${product.goodsInfoMarketPrice?string('0.00')}￥
						</div>
						<div class="hot_danpan_text_79">
							${product.warePrice?string('0.00')}￥
						</div>
						<a href="#order">
							<img class="qugoumai" src="${basePath}/img/beauty/qugoumai@3x.png"></img>
						</a>
					</div>
				</a>
	      </#list>
        </#if>

		<!--购物车-->
		<img class="gouwuche1" src="${basePath}/img/home/shopping@3x.png"></img>
		<!--返回顶部-->
		<a href="#top" id="top-link-block" class="hidden">
			<img class="top" src="${basePath}/img/home/top@3x.png"></img>
		</a>
 	<#--
    <div class="goods_list container-fluid">
        <#if map.pb.list??>
      <#list map.pb.list as product>
      	  <div class="col-xs-6">
	        <a href="${basePath}/item/${product.goodsInfoId}.html">
	          <img class="lazy" alt="" data-original="<#if  product.imageList?? && product.imageList?size &gt; 0 >${product.imageList[0].imageBigName!''}</#if>">
	          <noscript><img alt="" src="<#if  product.imageList?? && product.imageList?size &gt; 0 >${product.imageList[0].imageBigName!''}</#if>"></noscript>
	          <span>${product.productName!''}</span>
	          <span>￥${product.warePrice?string('0.00')}</span>
	        </a> 
	      </div>
      </#list>
        </#if>
    </div>
    
    
    <div class="pages container-fluid">
      <#if (map.pb.pageNo==1)>
           <div class="col-xs-6">
      		 <a class="disabled"  href="javascript:;">&lt;&nbsp;上一页</a>
      	   </div>
      <#else>
      		<div class="col-xs-6">
      			<a class="changePages" pages="${map.pb.prePageNo}" href="javascript:;">&lt;&nbsp;上一页</a>
      		</div>
      </#if>
      <#if (map.pb.lastPageNo > map.pb.pageNo)>
           
           <div class="col-xs-6">
      			<a class="changePages" pages="${map.pb.nextPageNo}" href="javascript:;">下一页&nbsp;&gt;</a>
     	   </div>
      <#else>
			<div class="col-xs-6">
      			<a class="disabled" href="javascript:;">下一页&nbsp;&gt;</a>
      		</div>
	  </#if>
    </div>
    
   <div class="hide">
		<form action="${basePath}/<#if storeId??&&storeId!=0>allGoodsListByStoreId.htm?storeId=${storeId}<#else>list/<#if map.nowcate?? >${map.nowcate.catId}<#else>allproduct</#if>.html</#if>" id="searchForm" method="post">
			<div class="filterList">
				<ul class="clearfix">
					<input type="hidden" name="pageNo" class="pageNo" value="${map.pb.pageNo}">
					<input type="hidden" name="sort" class="list_sort" value="${map.searchBean.sort!''}">
					<input type="hidden" name="showStock" class="show_stock" value="${map.searchBean.showStock!''}">
				</ul>
			</div>
		</form>
	</div>
	
    <div class="foot">
      <p>由${(mobSiteBasic.technicalSupport)!''}提供技术支持</p>
    </div>
    <#-- 
    <#include "../common/smart_menu.ftl" />   -->
    <!-- 引用公共脚部 -->

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
