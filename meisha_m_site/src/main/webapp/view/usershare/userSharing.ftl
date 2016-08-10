<#assign basePath=request.contextPath>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
  	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/share.css" />
	<link rel="stylesheet" href="${basePath}/css/build/share.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/home/iconfont.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <link rel="stylesheet" href="${basePath}/css/swiper-3.3.1.min.css">
      <script src="${basePath}/js/jquery.js"></script>
  </head>
	<body>
	 	<a href="#" id="top">
			<img class="MAIGANG" src="${basePath}/img/home/MAIGANG@3x.png"></img>
		</a>
		<hr class="m_hr" />
		<div id="dingbu" class="dingbufix">
			<div class="m_nav">
				<a href="${basePath}/customer/index.html">
					<div class="m_icon iconfont">&#xe604;</div>
				</a>
				<a href="${basePath}/main.html">
					<div class="recommend "><span>推荐</span></div>
				</a>
				<a href="${basePath}/mzlist/4358.html">
					<div class="recommend"><span>美妆</span></div>
				</a>
				<a href="${basePath}/mylist/4457.html">
					<div class="recommend"><span>母婴</span></div>
				</a>
				<a href="${basePath}/mobileusershare.html">
					<div class="recommend"><span class="recommend-selected">分享</span></div>
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
		
		<div class="swiper-container" style="margin-top: 40px;">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img class="banner1_750_380" src="${basePath}/img/share/lunbo@2x.png"></img>
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
				
		<#if sharetypes?? && (sharetypes?size>0)>
		<div class="share-container">
			<#list sharetypes as sharetype>
				<#if sharetype_index<2>
				<div class="share-type-item">
					<a href="${basePath}/mobileusershare/${sharetype.sharetypeId}.html">
						<img src="${sharetype.sharetypeImg}" class="type-img" />
					</a>
				</div>
				</#if>
			</#list> 
			<input type="hidden"  id="choosetype" />
		</div>
		</#if>

		<div class="img_adv">
			<a href="javascript:;"><img src="${basePath}/img/share/renmenfenxiang.png" class="type-title"></a>
		</div>
		<div class="share-box">
		<#if pb.list?? && (pb.list?size>0)>
			<ul class="share-ul">
			<#list pb.list as comment>
				<li class="share-item">
					<div class="share-item-wrap">
						 <#if comment.share??>
                             <a href="${basePath}/mobilesharedetail.htm?commentId=${comment.publicUserId}&customerId=${comment.customerId}" class="fx_pic_link cominShare" commentContent="${comment.publicUserShareContent}" customer_id="${comment.customerId}" comment_id="${comment.publicUserId}"><img src="${comment.share.shareImgs[0].imageName}" class="item-img"/></a>
                         <#else>
                             <a href="${basePath}/mobilesharedetail.htm?commentId=${comment.publicUserId}&customerId=${comment.customerId}" class="fx_pic_link cominShare" commentContent="${comment.publicUserShareContent}" customer_id="${comment.customerId}" comment_id="${comment.publicUserId}"><img src="" class="item-img"/></a>
                         </#if>
						<div class="item-name">
							<a href="${basePath}/mobilesharedetail.htm?commentId=${comment.publicUserId}&customerId=${comment.customerId}">
							<#if (comment.publicUserShareContent?length>30)>
	                            ${comment.publicUserShareContent?substring(0,30)}
	                         <#else>
	                            ${comment.publicUserShareContent}
	                         </#if>
							<a>
						</div>
						<div class="item-time">
							${comment.publishTime?string('yyyy-MM-dd HH:mm:ss')}
						</div>
						<div class="space-dot-line"></div>
						<div class="item-footer">
							<img src="<#if comment.customer.customerImg??>${comment.customer.customerImg}<#else>${basePath}/img/share/touxiang@2x.png</#if>" class="img-logo" />
							<div class="item-fr">
								<img src="${basePath}/img/share/like@2x.png" class="like-img" />
								<span class="link-num"><span><#if comment.publicUserShareLike??>${comment.publicUserShareLike}<#else>0</#if></span>人喜欢</span>
							</div>
						</div>
					</div>
				</li>
			 </#list>
			</ul>
		 </#if>
		</div>
		
	<script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/jquery.lazyload.js"></script>
    <script src="${basePath}/js/swiper-3.3.1.min.js"></script>
    <script src="${basePath}/js/stickUp.js"></script>
    <script src="${basePath}/js/main.js"></script>
    <script type="text/javascript">
    	 $(document).ready(function(){
    	 	$(".item-time").each(function(){
			    var date1=new Date($(this).text());  //开始时间
				var date2=new Date();    //结束时间
				var date3=date2.getTime()-date1.getTime();  //时间差的毫秒数
				//计算出相差天数
				var days=Math.floor(date3/(3600*1000));
				$(this).html(days+"个小时前");
			 });
    	 });
    </script>
</body>
</html>