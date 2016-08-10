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
    <link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/build/search.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/search.css" />
	<link rel="stylesheet" href="${basePath}/css/magnific-popup.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
	<script src="${basePath}/js/html5shiv.min.js"></script>
	<script src="${basePath}/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<div class="m_head">
		<a href="javascript:;"onclick="javascript:history.go(-1)">
			<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
		</a>
		<a href="${basePath}/main.html">
			<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
		</a>
		<div class="search_text"><#if keyWords??>${keyWords}<#else>搜索</#if></div>
		<div class="line1"></div>
	</div>
	<input class="storeId" type="hidden" value="${storeId}">
	
	<div class="search_container">
		<div class="search_content">
			<a href="#test-modal" class="start_modal">
				<div class="pinpai pinpai_selected" data-search="pinpai">品牌</div>
			</a>
			<div class="line2"></div>
			<a href="#test-modal" class="start_modal">
				<div class="pinpai" data-search="fenlei">分类</div>
			</a>
			<div class="line2"></div>
			<a href="#test-modal" class="start_modal">
				<div class="pinpai" data-search="price">价格</div>
			</a>
			<div class="line2"></div>
			<a href="#test-modal" class="start_modal">
				<div class="pinpai" data-search="gongxiao">功效</div>
			</a>
		</div>
	</div>
	
	<div class="pro_container">
		<!--产品列表-->
		<#if (map.pb.data??) && (map.pb.data?size>0) >
			<#list map.pb.data as product>
		    <#--设置该商品的价格与库存-->
		        <#assign productPrice=product.goodsInfoPreferPrice>
		        <#assign productmarketPrice=product.goodsInfoMarketPrice>
		        <#assign stock=product.goodsInfoStock>
		        <#if product.wareList??&&product.wareList?size &gt;0>
		            <#assign productPrice=product.wareList[0].warePrice>
		            <#assign stock=product.wareList[0].wareStock>
		        </#if>
		        <div class="pro_content">
					<a href="${basePath}/item/${product.goodsInfoId}.html">
						<img src="<#if  product.goodsInfoImgId?? >${product.goodsInfoImgId!''}</#if>" class="alima"></img>
						<div class="Yves">
							<#if (product.goodsInfoName?length>12)>
	                            ${product.goodsInfoName?substring(0,12)}
	                         <#else>
	                            ${product.goodsInfoName}
	                         </#if>
						</div>
						<div class="price123">${productmarketPrice?string('0.00')}￥</div>
						<div class="price98">${productPrice?string('0.00')}￥</div>
						<a href="${basePath}/item/${product.goodsInfoId}.html">
							<img src="${basePath}/img/search/jiarugouwuche@2x.png" class="jiarugouwuche"></img>
						</a>
					</a>
				</div>
			</#list>
		<#else>
		<#--没有搜索到商品时的显示页面-->
		<img class="search_none_icon" src="${basePath}/img/search/search_none.png" />
		<p class="search_none_p">抱歉，没有找到相关产品</p>
		<a href="${basePath}/main.html"><div class="search_none_btn">逛逛今日团购</div></a>
		</#if>
	</div>
	
	<div id="test-modal" class="white-popup-block mfp-hide ">
			<div id="pinpai" data-search ="pinpai" style="display: none;">
				<#if (brands??) && (brands?size>0)>
						<a href="javascript:;" class="selectbrand" dataname="0">
							<div class="xuehua_text">全部品牌</div>
						</a>
					<#list brands as selbrand>
						<a href="javascript:;"  dataname="${selbrand}">
							<div class="xuehua_text" style="color:#e31436;">${selbrand}</div>
						</a>
					</#list>
				<#else>
					<#list map.brands as brand>
						<a href="javascript:;" class="selectbrand" dataname="${brand.brandName}">
							<div class="xuehua_text">${brand.brandName}</div>
						</a>
					</#list>
				</#if>
			</div>

			<div class="line5"></div>
			<a href="#cart">
				<div class="cha_more">查看更多</div>
			</a>
		</div>
	<div class="hide">
	    <form action="searchProduct.htm" id="searchForm" method="post">
	        <div class="filterList">
	            <ul class="clearfix">
					<#if storeId??&&storeId!=0>
		                <input type="hidden" name="storeId" value="${storeId}">
					</#if>
	                <input type="hidden" name="title" class="title" value="${keyWords!''}">
	                <input type="hidden" name="pageNo" class="pageNo" value="${map.pb.pageNo}">
	                <input type="hidden" name="sort" class="list_sort" value="${sort!''}">
	                <input type="hidden" name="showStock" class="show_stock" value="${showStock!''}">
	                <div id="brandsdata">
	                	<input type="hidden" name="brands" class="brands" value="" />
	                </div>
	            </ul>
	        </div>
	    </form>
	</div>
<#--新加头部－加搜索-->
<#--
<#include "/main/search.ftl"/>
<input class="storeId" type="hidden" value="${storeId}">
<div class="goods_filter container-fluid">
    <div class="col-xs-3">
        <a val="5D" attr="${sort!''}" class="change_sort <#if sort='' || sort='5D'>cur</#if>" href="javascript:;">默认</a>
    </div>
    <div class="col-xs-3">
        <a val="2D" attr="${sort!''}" class="change_sort <#if sort='22D' || sort='2D'>cur</#if> <#if sort='22D'> s_up<#elseif sort='2D'> s_down</#if>" href="javascript:;">销量</a>
    </div>
    <div class="col-xs-3">
        <a val="1D" attr="${sort!''}" class="change_sort <#if sort='11D' || sort='1D'>cur</#if> <#if sort='11D'> s_up<#elseif sort='1D'> s_down</#if>" href="javascript:;">价格</a>
    </div>
    <div class="col-xs-3">
        <a val="4D" attr="${sort!''}" class="change_sort <#if sort='44D' || sort='4D'>cur</#if> <#if sort='44D'>s_up<#elseif sort='4D'>s_down</#if>" href="javascript:;">人气</a>
    </div>
</div>
<div class="goods_list container-fluid">
<#if map.pb.data??>
	<#list map.pb.data as product>
    <#--设置该商品的价格与库存-->
		<#--
        <#assign productPrice=product.goodsInfoPreferPrice>
        <#assign stock=product.goodsInfoStock>
        <#if product.wareList??&&product.wareList?size &gt;0>
            <#assign productPrice=product.wareList[0].warePrice>
            <#assign stock=product.wareList[0].wareStock>
        </#if>
        <div class="col-xs-6">
            <a href="${basePath}/item/${product.goodsInfoId}.html">
                <img class="lazy" alt="" data-original="<#if  product.imgList?? && product.imgList?size &gt; 0 >${product.imgList[0].imageBigName!''}</#if>">
                <noscript><img alt="" src="<#if  product.imgList?? && product.imgList?size &gt; 0 >${product.imgList[0].imageBigName!''}</#if>"></noscript>
                <span>${product.goodsInfoName!''}</span>
                <span>￥${productPrice?string('0.00')}</span>
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
    <form action="searchProduct.htm" id="searchForm" method="post">
        <div class="filterList">
            <ul class="clearfix">
			<#if storeId??&&storeId!=0>
                <input type="hidden" name="storeId" value="${storeId}">
			</#if>
                <input type="hidden" name="title" class="title" value="${keyWords!''}">
                <input type="hidden" name="pageNo" class="pageNo" value="${map.pb.pageNo}">
                <input type="hidden" name="sort" class="list_sort" value="${sort!''}">
                <input type="hidden" name="showStock" class="show_stock" value="${showStock!''}">
            </ul>
        </div>
    </form>
</div>
<div class="foot">
    <p>由${(mobSiteBasic.technicalSupport)!''}提供技术支持</p>
</div>
<#include "../common/smart_menu.ftl" />
-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${basePath}/js/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${basePath}/js/bootstrap.min.js"></script>
<script src="${basePath}/js/fastclick.min.js"></script>
<script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
<script src="${basePath}/js/jquery.keleyi.js"></script>
<script src="${basePath}/js/jquery.lazyload.js"></script>
<script src="${basePath}/js/goods/goods_list.js"></script>
<script src="${basePath}/js/jquery.magnific-popup.min.js"></script>
<script src="${basePath}/js/search/main.js"></script>

<!-- <script src="${basePath}/js/customer/wxforward.js"></script>-->
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
        
        //搜索条件
        $(".selectbrand").click(function (){
	    	   var brands=$(this).attr("dataname");
	    	   if(brands == "0"){
	    	   	 $("#brandsdata").html("");
	    	   }else{
	    	   	 var htmldata="<input type='hidden' name='brands' class='brands' value='"+brands+"' />";
	    	   	 $("#brandsdata").html(htmldata);
	    	   }
	    		 $("#searchForm").submit();
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
</body>
</html>
