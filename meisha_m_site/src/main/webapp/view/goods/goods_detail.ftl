<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#if (sys.bsetName)??>
    	<title><#if map.detailBean.productVo.goods.goodsSeoTitle?length &gt; 0> ${map.detailBean.productVo.goods.goodsSeoTitle!''}<#else>${map.detailBean.productVo.productName!''}</#if>${sys.bsetName}</title>
    <#else>
	    <title><#if map.detailBean.productVo.goods.goodsSeoTitle?length &gt; 0> ${map.detailBean.productVo.goods.goodsSeoTitle!''}<#else>${map.detailBean.productVo.productName!''}</#if>${seo.mete}</title>
    </#if>
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/build/xiangqing.debug.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
	<link rel="stylesheet" href="${basePath}/css/swiper-3.3.1.min.css">
	
	<!-- Bootstrap -->
    <link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}/css/idangerous.swiper.css" rel="stylesheet">
    <link href="${basePath}/css/style.css" rel="stylesheet">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]> 
	<script src="${basePath}/js/html5shiv.min.js"></script>
	<script src="${basePath}/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  	<div class="m_head" id="top">
		<a href="javascript:;"onclick="javascript:history.go(-1)">
			<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
		</a>
		<a href="${basePath}/main.html">
			<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
		</a>				
		<div class="search_text">
			<#if (map.detailBean.productVo.productName?length>5)>
		        ${map.detailBean.productVo.productName?substring(0,5)}
		     <#else>
		        ${map.detailBean.productVo.productName}
		     </#if>
		</div>
		<div class="line1"></div>
	</div>
	
	<!--幻灯片-->
	  <div class="roll_banner">
	      <div class="swiper-container">
	      	<div class="swiper-wrapper">
	      		<#if map.detailBean.productVo.imageList??>
		      		<#list map.detailBean.productVo.imageList as image>
		      			<div class="swiper-slide"><a href="#"><img src="${image.imageBigName!''}"></a></div>
					</#list>
				</#if>
	        </div>
	        <div class="swiper-pagination"></div>
	      </div>
    </div><!-- /roll_banner -->
    
	<div class="xiejie_container">
		<input type="hidden" id="goodsId" value="${map.detailBean.productVo.goodsId}" />
      	<input type="hidden" id="productId" value="${map.detailBean.productVo.goodsInfoId}" />
      	<input type="hidden" id="brandId" value="${map.detailBean.productVo.goods.brandId}" />
     	<input type="hidden" id="productStock" value="${map.detailBean.productVo.goodsInfoStock}" />
     	<input type="hidden" id="catId" value="${map.detailBean.productVo.goods.catId}" />
    	<input type="hidden" id="goodsInfoAdded" value="${map.detailBean.productVo.goodsInfoAdded}" />
    	
		<div class="Sensibio">${map.detailBean.productVo.productName!''}</div>
		<div class="price_69">￥${map.detailBean.productVo.goodsInfoPreferPrice?string("0.00")}</div>
		<div class="price_old">￥${map.detailBean.productVo.goodsInfoMarketPrice?string("0.00")}</div>
		<img src="${basePath}/img/xiangqing/group@2x.png" class="group"></img>
		<img src="${basePath}/img/xiangqing/sale@2x.png" class="sale"></img>
		<div class="time_04">剩04天19：09：32</div>
		<div class="buy_783">88人已购买</div>
		<input type="hidden" value="1" class="count_num product_count">
		
		<div class="hide">
			<#if (map.detailBean.productVo.specVo??)>
				<#list map.detailBean.productVo.specVo as spec>
					<input type="hidden" class="chooseParamId" value="${spec.goodsSpecDetail.specDetailId}" data-spec="${spec.spec.specName}" data-detail="${spec.specValueRemark!''}" />
				</#list>
			</#if>
		</div>
	</div>
	
	<div class="banner_20"></div>
	<div class="shangpin">
		<a href="javascript:;">
			<div class="jieshao jieshao_selected" datatype="details">商品介绍 </div>
		</a>
		<a href="javascript:;">
			<div class="jieshao" datatype="profile">规格参数 </div>
		</a>
		<a href="javascript:;">
			<div class="jieshao" datatype="comment">商品评论 </div>
		</a>
		<div class="line2"></div>
		<!--商品介绍开始-->
	</div>
	<div id="introduce">
		<div class="dateil" id="details">
			<div class="introduce_container">
				<a href="#detail1"><div class="fangfa fangfa_selected" datatype="detail1">商品详情 </div></a>
				<a href="#detail2"><div class="fangfa" datatype="detail2">使用方法 </div></a>
				<a href="#detail3"><div class="fangfa" datatype="detail3">商品实拍</div></a>
				<a href="#detail4"><div class="fangfa" datatype="detail4">特别说明 </div></a>
			</div>
			<div class="newdetail" id="detail1">
				 ${map.detailBean.productVo.goods.mobileDesc!''}
			</div>
			<div class="newdetail" id="detail2">
				 ${map.detailBean.productVo.goods.useMethod!''}
			</div>
			<div class="newdetail" id="detail3">
				 ${map.detailBean.productVo.goods.realPicture!''}
			</div>
			<div class="newdetail" id="detail4">
				 ${map.detailBean.productVo.goods.specDetail!''}
			</div>
		</div>
	</div>
	<div class="dateil hidden-new" id="profile">
		 <table class="table table-striped table-bordered" width="100%">
	           <#list map.detailBean.expandPrams as expandParam>
	           		<tr>
		                <td width="30%" align="right"><#if expandParam.expandParamVo??>${expandParam.expandParamVo.expandparamName}</#if></td>
		                <td><#if expandParam.expangparamValue??>${expandParam.expangparamValue.expandparamValueName}</#if></td>
		            </tr>
	           </#list>
	      </table>
    </div>
    
    <div class="dateil hidden-new" id="comment">
		  <div class="comment_filter">
	        <a class="cur load_comment" role="0" href="javascript:;">好评(<span class="hao_count">0</span>)</a>
	        <a class="load_comment" role="1" href="javascript:;">中评(<span class="zhong_count">0</span>)</a>
	        <a class="load_comment" role="2" href="javascript:;">差评(<span class="cha_count">0</span>)</a>
	      </div>
	      <div class="comment_list comment_item_list">
	      </div>
	      <div class="pages comment_pages container-fluid">
	      </div>
    </div>
    
	<div class="m_bottom">
		<a href="javascript:;" class="add_sc" productid="${map.detailBean.productVo.goodsInfoId}">
			<img src="${basePath}/img/xiangqing/harz_keine_farbe@2x.png" class="harz_mit_farbe"></img>
		</a>
		<a href="javascript:;" class="buy_now">
			<div class="btn_buy">立即购买</div>
		</a>
		<a href="javascript:;" class="detail_add_cart detail_add_cart1" productId="${map.detailBean.productVo.goodsInfoId}">
			<div class="btn_cart">加入购物车</div>
		</a>
		<a href="${basePath}/myshoppingmcart.html" class="gouwuche1">
			<img src="${basePath}/img/xiangqing/cart@2x.png" class="harz_mit_farbe"></img>
		</a>
		<span class="hongdian" style="color: #fff;font-size: 0.3rem;
    position: absolute; background-color: #E31436;border-radius: 50%;right: 0.4rem;bottom: 0.5rem; width: 0.4rem; height: 0.4rem;line-height: 0.4rem; padding-left: 0.12rem;">0</span>
	</div>
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
    <script src="${basePath}/js/goods/goods_detail.js"></script>
    <#--<script src="${basePath}/js/customer/wxforward.js"></script>-->
    <script src="${basePath}/js/swiper-3.3.1.min.js"></script>
    <script src="${basePath}/js/jquery.fly.min.js"></script>
	<script src="${basePath}/js/xiangqing/main.js"></script>

    <script>
        $(function(){
            FastClick.attach(document.body);
            $('.roll_banner,.roll_banner img').css('height',$(window).width() + 'px');
            var mySwiper = new Swiper('.swiper-container',{
                pagination: '.swiper-pagination',
                loop:true,
                grabCursor: true,
                autoplay : 3000
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
			
			$(".add_sc").click(function (){
				 var productid=$(this).attr("productid");
				 $.post("${basePath}/saveAtte.htm", { productId: productid },
					   function(data){
					  		if(data==-2){
					  			alert("请登录之后再关注");
					  		}else if(data==1){
					  			//关注成功
					  			$(".add_sc").find("img").attr("src","${basePath}/img/xiangqing/harz_mit_farbe@2x.png");
					  		}else{
					  			//取消关注
					  			$(".add_sc").find("img").attr("src","${basePath}/img/xiangqing/harz_keine_farbe@2x.png");
					  		}
					   });
			});
			
            <!-- 加载规格值区域 -->
            var productList=null;
            $.get("${basePath}/all/"+$("#goodsId").val()+".html",function(data){
                productList=data;
                <!-- 把查询到的数据传递到js方法中 -->
                loadAllProduct(productList);
                loadChooseParam();
            });
            <!-- 加载商品促销的信息 -->
            loadGoodsMark();
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
        function WeiXinAddContact(name){
            WeixinJSBridge.invoke("addContact", {webtype: "1",username: name}, function(e) {
                WeixinJSBridge.log(e.err_msg);
            //e.err_msg:add_contact:added 已经添加
            // e.err_msg:add_contact:cancel 取消添加
            // e.err_msg:add_contact:ok 添加成功
           if(e.err_msg == 'add_contact:added' || e.err_msg == 'add_contact:ok'){
              //关注成功，或者已经关注过
            }
           })
        }
		
        var dataForWeixin={
            MsgImg:"<#if  map.detailBean.productVo.imageList?? && map.detailBean.productVo.imageList?size &gt; 0 >${map.detailBean.productVo.imageList[0].imageInName!''}</#if>",
            url:"http://shop.ningpai.com/mobile/getwxcode3.htm?toUrl=item/${map.detailBean.productVo.goodsInfoId}.html",
            title:"发现一个好商品",
            desc:"${map.detailBean.productVo.productName!''}",
            callback:function(
                    //这里是分享成功后的回调功能
            ){}
        };
        
        
	</script>
  </body>
</html>
