<#assign basePath=request.contextPath>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/sharedetail.css" />
	<link rel="stylesheet" href="${basePath}/css/build/sharedetail.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/home/iconfont.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <link rel="stylesheet" href="${basePath}/css/swiper-3.3.1.min.css">
    <script src="${basePath}/js/jquery.js"></script>
  </head>
	<body>
	<div class="head-wrapper">
		<a href="javascript:;"onclick="javascript:history.go(-1)" class="back-link">
		<img src="${basePath}/img/sharedetail/fanhui@2x.png" class="img-link img-f"/>
		</a>
		<span class="head-center"><#if (map.comment.publicUserShareContent?length>8)>
	                            ${map.comment.publicUserShareContent?substring(0,8)}
	                         <#else>
	                            ${map.comment.publicUserShareContent}
	                         </#if></span>
		<img src="${basePath}/img/sharedetail/share@2x.png" class="img-link img-r">
	</div>
	<div class="line-space"></div>
	<div class="item-head">
		<div class="item-head-l">
		<a href="#">
			<img src="<#if (map.customer.customerImg)??>${map.customer.customerImg}<#else>${basePath}/img/sharedetail/logo-img.png</#if>" class="circle-img"/>
		</a>
		</div>
		<div class="item-head-r"><span class="text-time">${map.comment.publishTime?string('yyyy-MM-dd HH:mm:ss')}</span></div>
	</div>
	<div class="clear-float"></div>
	<div class="swiper-container" style="margin-top: 30px;">
		<#if (map.comentImgs)?? && (map.comentImgs?size>0)>
		<div class="swiper-wrapper">
			 <#list map.comentImgs as img>
				<div class="swiper-slide">
					<img class="banner1_750_750" src="${img.imageName}"></img>
				</div>
			 </#list>
		</div>
		</#if>
		<div class="swiper-pagination"></div>
	</div>
	<div class="text-content">
		${map.comment.publicUserShareContent}
	</div>
	<div class="line-space-d"></div>
	<div class="item-detail-footer" id="item-footer">
		<div class="like-warper">
			<a href="#">
			<img src="${basePath}/img/sharedetail/like@2x.png" class="img-footer"/>
			</a>
			<span class="like-num"><#if map.comment.publicUserShareLike??>${map.comment.publicUserShareLike}<#else>0</#if></span>
		</div>
		
		<div class="pinlun-warper">
			<a href="#">
			<img src="${basePath}/img/sharedetail/pinglun@2x.png" class="img-footer"/>
			</a>
			<span class="like-num"><#if map.comment.pulbicUserShareNumber??>${map.comment.pulbicUserShareNumber}<#else>0</#if>人评论</span>
		</div>
		<div class="add-sc">
			<a href="#">
			<img src="${basePath}/img/sharedetail/add@2x.png" class="img-right"/>
			</a>
		</div>
	</div>
	<div class="clear-float"></div>
	<div class="line-space-d"></div>
	<ul class="pl-ul">
		<#if (map.listVComent)?? && (map.listVComent?size>0)>
			<#list map.listVComent as comment>
				<li class="pl-item">
					<div class="item-warper">
						<div class="item-warper-left">
							<img src="<#if comment.customer.customerImg??>${comment.customer.customerImg}<#else>${basePath}/img/sharedetail/logo-img.png</#if>" class="img-user-logo"/>
						</div>
						<div class="item-warper-right">
							<div class="pinlun-name">
								<span class="name-title"><#if comment.customer.customerNickname??>${comment.customer.customerNickname}<#else>${comment.customer.customerUsername}</#if></span><span class="text-time">${comment.vPublicUserShareTime?string('yyyy-MM-dd HH:mm:ss')}</span>
							</div>
							<div class="pinlun-content">
								${comment.vPublicUserShareContent}
							</div>
						</div>
					</div>
					<div class="clear-float"></div>
				</li>
			</#list>
		</#if>
		</ul>
		<div class="space-height"></div>
		
	<script src="${basePath}/js/swiper-3.3.1.min.js"></script>
	<script src="${basePath}/js/sharedetail.js"></script>
    <script type="text/javascript">
    	 $(document).ready(function(){
    	 	$(".text-time").each(function(){
			    var date1=stringToTime($(this).html());  //开始时间
				var date2=new Date();    //结束时间
				var date3=date2.getTime()-date1;  //时间差的毫秒数
				//计算出相差天数
				var days=Math.floor(date3/(3600*1000));
				$(this).html(days+"个小时前");
			 });
    	 });
    	 
 		 //字符串转时间戳
    	 function stringToTime(string) {
			    var f = string.split(' ', 2);
			    var d = (f[0] ? f[0] : '').split('-', 3);
			    var t = (f[1] ? f[1] : '').split(':', 3);
			    return (new Date(
			   parseInt(d[0], 10) || null,
			   (parseInt(d[1], 10) || 1) - 1,
			parseInt(d[2], 10) || null,
			parseInt(t[0], 10) || null,
			parseInt(t[1], 10) || null,
			parseInt(t[2], 10) || null
			)).getTime();
			}
    </script>
</body>
</html>