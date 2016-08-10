<#assign basePath=request.contextPath>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
  	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/sharetheme.css" />
	<link rel="stylesheet" href="${basePath}/css/build/share.theme.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/home/iconfont.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <link rel="stylesheet" href="${basePath}/css/swiper-3.3.1.min.css">
    <script src="${basePath}/js/jquery.js"></script>
    <title>主题分享</title>
  </head>
	<body>
	 	<div class="head-wrapper">
			<a href="javascript:;"onclick="javascript:history.go(-1)" class="back-link">
				<img src="${basePath}/img/sharedetail/back@2x_02.png" class="img-link img-f" />
			</a>
			<span class="head-center">${sharetype.sharetypeName}SHARE</span>
			<a href="${basePath}/main.html">
			<img src="${basePath}/img/sharedetail/shouye@2x.png" class="img-link img-r">
			</a>
		</div>
		<div class="line-space"></div>
		
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