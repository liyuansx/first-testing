<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
    <#if (sys.bsetName)??>
    	<title>注册-${(sys.bsetName)!''}</title>
    <#else>
	    <title>注册-${(seo.mete)!''}</title>
    </#if>
	<#assign basePath=request.contextPath>
	
	<link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}/css/idangerous.swiper.css" rel="stylesheet">
    <link href="${basePath}/css/style.css" rel="stylesheet">
    
  	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/register.css" />
	<link rel="stylesheet" href="${basePath}/css/build/register.debug.css" />
	
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
		<a href="javascript:;" onclick="javascript:history.go(-1)">
			<img src="${basePath}/img/login/back@2x_02.png" class="iconfont_back"></img>
		</a>
		<div class="login">注册</div>
		<hr class="line1"/>
	</div>
	
	<div class="m_input">
		<form role="form" method="post">
		<input type="text" placeholder="请输入11位手机号" class="mobileNum userinput" name="customerUsername" id="mob"/>
		<a href="javascript:;" class="info_ts"><img src="img/login/default_ts.png" class="user_ts show_tip hidden" /></a>
		<p class="help-block"></p>
		<input type="text" placeholder="输入验证码" class="yanzheng" name="code" id="mc"/>
		<div class="btn_yz" id="mc_btn" onClick="return false;">获取验证码</div>
		<p class="help-block"></p>
		<input type="password" placeholder="6-16位字母和数字的密码" class="password userinput" name="customerPassword"  id="pd"/>
		<div class="xieyi">注册即接受麦港网使用协议和社区使用协议</div>
		<div class="error-info hidden" id="error-info">
			<img src="img/login/error_ts.png" class="error_logo"/><span class="error-tip">密码错误</span>
		</div>
		<a href="javascript:;" class="info_ts"><img src="img/login/default_ts.png" class="pwd_ts show_tip hidden" id="pwd_ts" /></a>
		<div class="btn_login reg" href="javascript:void(0);">注册</div>
		</form>
	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${basePath}/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/customer/asvalidate.js"></script>
    <script src="${basePath}/js/customer/allvalid.js"></script>
    <script>
	  $(function(){
		FastClick.attach(document.body);
		$('.fill_item input').focus(function(){
		  $(this).parent().next().css('borderColor','#EB6122');
		});
		
		$('.fill_item input').blur(function(){
		  $(this).parent().next().css('borderColor','#EEEEEE');
		});
		
		$("#keleyi-menu").keleyi({
		  width: '100%',
		  item_background_color: '#FAFAFA',
		  item_background_color_hover: '#FAFAFA',
		  item_margin: '0',
		  bar_background_color: '#FAFAFA'
		});
		
		$(".userinput").focus(function(){
			$(this).next(".info_ts").find("img").removeClass("hidden");
		});
		
		$("#pd").focus(function(){
			$("#pwd_ts").removeClass("hidden");
		});
		
		$(".show_tip").click(function(){
			$(this).parent(".info_ts").prev(".userinput").val("");
			$(this).addClass("hidden");
		});
		$("#pwd_ts").click(function(){
			$("#pd").val("");
			$(this).addClass("hidden");
		});
	  });
	  document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) { // enter 键
			$(".reg").click();
		}
	 };
	</script>
  </body>
</html>
