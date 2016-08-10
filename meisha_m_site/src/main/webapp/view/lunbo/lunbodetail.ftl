<#assign basePath=request.contextPath>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
  	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/lunbo.css" />
	<link rel="stylesheet" href="${basePath}/css/build/home.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/build/lunbo.debug.css" />
	<!--<script src="${basePath}/js/flexible.debug.js"></script>
	<script src="${basePath}/js/flexible_css.debug.js"></script>-->
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <script src="${basePath}/js/jquery.js"></script>
    <title>夏日小清新单品</title>
  </head>
	<body>
	 	<!--头部-->
		<div class="m_header">
			<a href="javascript:;" onclick="javascript:history.go(-1)">
				<img src="${basePath}/img/lunbo/back@2x_02.png" class="back"></img>
			</a>
			<div class="xiari">${lunboinfo.lunboname}</div>
			<a href="${basePath}/main.html">
				<img src="${basePath}/img/lunbo/shouye@2x.png" class="shouye"></img>
			</a>
			<hr class="line1" />
		</div>
		<!--头部-->
		<img src="${lunboinfo.imgpath}" class="banner"></img>
		<div class="baokuan">
			<#list lunboinfo.lunbotypes as  lunbotype>
					<#if lunbotype_index == 0>
						<a href="#nav${lunbotype_index}" class="nav${lunbotype_index}">
							<div class="tuijian tuijian_selected" style="margin-left: 20px;">${lunbotype.name}</div>
						</a>
						<#else>
						<a href="#nav${lunbotype_index}" class="nav${lunbotype_index}">
							<div class="tuijian">${lunbotype.name}</div>
						</a>
					</#if>
			</#list>
		</div>
		<#list lunboinfo.lunbotypes as  lunbotype>
				<#if lunbotype_index==0>
					<#list lunbotype.lunboGoods as lunbogood>
					<!--爆款推荐-->
					<div class="xuehuaxiu" style="margin-top: 0;">
						<a href="${basePath}/item/${lunbogood.good.goodsInfoId}.html">
							<img src="${lunbogood.imgpath}" class="xuehua"></img>
							<div class="Sulwhasoo">
								<#if (lunbogood.good.goodsInfoName?length>20)>
	                            ${lunbogood.good.goodsInfoName?substring(0,20)}
	                         <#else>
	                            ${lunbogood.good.goodsInfoName}
	                         </#if></div>
							<div class="baixi">
								<#if (lunbogood.good.goodsInfoSubtitle?length>15)>
	                            ${lunbogood.good.goodsInfoSubtitle?substring(0,15)}
	                         <#else>
	                            ${lunbogood.good.goodsInfoSubtitle}
	                         </#if>
							</div>
							<div class="price169">￥${lunbogood.good.goodsInfoPreferPrice?string('0.00')}</div>
							<div class="price328">￥${lunbogood.good.goodsInfoMarketPrice?string('0.00')}</div>
							<hr class="line2" />
						</a>
						<a href="#cart">
							<span class="new_add_cart kaufen add_cart1"  productId="${lunbogood.good.goodsInfoId}">加入购物车</span>
						</a>
					</div>
					</#list>
				<#elseif lunbotype_index==1>
					<!--护肤必备-->
					<div id="nav${lunbotype_index}" class="hufu" style="margin-top: 35px;">
			
						<img src="${basePath}/img/lunbo/mitte@2x.png" class="mitte"></img>
						<div class="hufu_text">
							<div class="text_center">
								${lunbotype.name}
							</div>
						</div>
					</div>
					<div class="m_content">
					<#list lunbotype.lunboGoods as lunbogood>
					<div class="Saint">
						<a href="${basePath}/item/${lunbogood.good.goodsInfoId}.html">
							<img src="${lunbogood.imgpath}" class="CIV"></img>
							<div class="Laurent"><#if (lunbogood.good.goodsInfoName?length>10)>
	                            ${lunbogood.good.goodsInfoName?substring(0,10)}
	                         <#else>
	                            ${lunbogood.good.goodsInfoName}
	                         </#if> </div>
							<div class="price790">${lunbogood.good.goodsInfoPreferPrice?string('0.00')}￥</div>
		
						</a>
						<div class="btn_cart add_cart1" productId="${lunbogood.good.goodsInfoId}">加入购物车</div>
					</div>
					</#list>
					</div>
					<#elseif lunbotype_index==2>
					<!--护肤必备-->
					<div id="nav${lunbotype_index}" class="hufu" style="margin-top: 35px;">
						<img src="${basePath}/img/lunbo/mitte@2x.png" class="mitte"></img>
						<div class="hufu_text">
							<div class="text_center">${lunbotype.name}</div>
						</div>
					</div>
					<div class="m_content">
					<#list lunbotype.lunboGoods as lunbogood>
						<div class="Saint">
							<a href="${basePath}/item/${lunbogood.good.goodsInfoId}.html">
								<img src="${lunbogood.imgpath}" class="CIV"></img>
								<div class="Laurent"><#if (lunbogood.good.goodsInfoName?length>10)>
		                            ${lunbogood.good.goodsInfoName?substring(0,10)}
		                         <#else>
		                            ${lunbogood.good.goodsInfoName}
		                         </#if> </div>
								<div class="price790">${lunbogood.good.goodsInfoPreferPrice?string('0.00')}￥</div>
							</a>
							<div class="btn_cart add_cart1" productId="${lunbogood.good.goodsInfoId}">加入购物车</div>
						</div>
					</#list>
					</div>
					<#else>
					<!--护肤必备-->
					<div id="nav${lunbotype_index}" class="hufu" style="margin-top: 35px;">

						<img src="${basePath}/img/lunbo/mitte@2x.png" class="mitte"></img>
						<div class="hufu_text">
							<div class="text_center">${lunbotype.name}</div>
						</div>
					</div>
					<div class="m_content">
					<#list lunbotype.lunboGoods as lunbogood>
						<div class="Saint">
							<a href="${basePath}/item/${lunbogood.good.goodsInfoId}.html">
								<img src="${lunbogood.imgpath}" class="CIV"></img>
								<div class="Laurent"><#if (lunbogood.good.goodsInfoName?length>10)>
		                            ${lunbogood.good.goodsInfoName?substring(0,10)}
		                         <#else>
		                            ${lunbogood.good.goodsInfoName}
		                         </#if> </div>
								<div class="price790">${lunbogood.good.goodsInfoPreferPrice?string('0.00')}￥</div>
							</a>
							<div class="btn_cart add_cart1" productId="${lunbogood.good.goodsInfoId}">加入购物车</div>
						</div>
					</#list>
					</div>
				</#if>
		</#list>

		<a href="${basePath}/myshoppingmcart.html">
		<img class="gouwuche1" src="${basePath}/img/home/shopping@3x.png"></img>
		</a>
		<a href="#top" id="top-link-block" class="hidden">
			<img class="top" src="${basePath}/img/home/top@3x.png"></img>
		</a>
		<div class="hongdian">0</div>
		
		<script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
		<script src="${basePath}/js/jquery.fly.min.js"></script>
		<script src="${basePath}/js/lunbo/main.js"></script>
</body>
</html>