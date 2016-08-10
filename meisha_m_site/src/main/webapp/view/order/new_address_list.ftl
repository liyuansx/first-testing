<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
  	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/build/address.debug.css" />
	<link rel="stylesheet" href="${basePath}/css/magnific-popup.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>

    <meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#if (sys.bsetName)??>
    	<title>${(sys.bsetName)!''}</title>
    	<input type="hidden" id="bsetName" value="${(sys.bsetName)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    <#else>
	    <title>${(seo.mete)!''}</title>
	    <input type="hidden" id="bsetName" value="${(seo.mete)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    </#if>
    <script src="${basePath}/js/jquery.js"></script>
</head>
        <body style="background:#FFFFFF;">
        	<!--头部-->
			<div class="m_head" id="top">
				<a href="javascript:history.go(-1);" >
					<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
				</a>
				<a href="${basePath}/main.html">
					<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
				</a>
				<div class="search_text">地址管理</div>
				<div class="line1"></div>
			</div>
			<#--新增地址-->
			<div class="new_address">
				<a href="${basePath}/toAddAddress.htm">
					<div class="addr_text">新增地址</div>
					<img src="${basePath}/img/address/youjiantou@2x_03.png" class="youjiantou"></img>
				</a>
			</div>
			<div class="addr_banner"></div>
			<#if addresses??>
				<#list addresses as address>
					<div class="addr_item">
						<div class="addr_content">
							<div class="addr_name">${address.addressName!''}</div>
							<div class="addr_phone">${address.addressMobile!''} </div>
							<a href="${basePath}/toupdateAddress.htm?addressId=${address.addressId}">
								<img src="${basePath}/img/address/xiezi@2x_03.png" class="xiezi"></img>
							</a>
						</div>
						<div class="addr_xijie">
						<#if (address.addressDetail?length>20)>
	                            ${address.addressDetail?substring(0,20)}..
	                    <#else>
	                            ${address.addressDetail}
	                    </#if>
						</div>
						<div class="addr_content">
							<a href="javascript:;" class="addressId" datachoose="${address.addressId}">
								<div class="addr_defaut <#if address.isDefault=='1'>addr_selected</#if>"><div class="default_circle"></div>设为默认</div>
							</a>
	
							<#--${basePath}/delAddress.htm?addressId=${address.addressId}-->
							<a href="#test-modal" class="popup-modal" dataaddressId="${address.addressId}">
								<img src="${basePath}/img/address/laji@2x.png" class="delete"></img>
							</a>
							<#--s
							<#if address.isDefault=='1'>
								<a href="javascript:;" class="popup-modal" dataaddressId="${address.addressId}">
									<img src="${basePath}/img/address/laji@2x.png" class="delete"></img>
								</a>
							<#else></#if>-->
						</div>
						<div class="addr_banner"></div>
					</div>
				</#list>
			</#if>
			<div id="fade" class="black_overlay"></div>
			<div id="test-modal" class="white-popup-block mfp-hide ">
				<div class="delete_addr">确定要删除该地址吗？</div>
				<div class="quxiao cancel">取消</div>
				<div class="line5"></div>
				<div class="quxiao enter">确定</div>
			</div>
			<#--更新地址-->
	        <form id="subForm" method="post" action="${basePath}/changeAddress.htm">
	            <input type="hidden" name="addressId" id="addressId">
	        </form>
	        <#--删除地址-->
	        <form id="delForm" method="post" action="${basePath}/delAddress.htm">
	            <input type="hidden" name="addressId" id="deladdressId">
	        </form>
	        <script src="${basePath}/js/jquery-1.11.1.min.js"></script>
	        <#--<script src="${basePath}/js/mui.min.js"></script>
	        <script src="${basePath}/js/app.js"></script>-->
	        <script src="${basePath}/js/jquery.magnific-popup.min.js"></script>
			<script src="${basePath}/js/address/main.js"></script>
	        <script>
		        $(function(){
		            $(".addressId").click(function(){
		                $("#addressId").val($(this).attr("datachoose"));
		               $("#subForm").submit();
		            })
		        })
	        </script>
        </body>
</html>