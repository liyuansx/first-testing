<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/build/forgot_pwd.css" />
	<link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
  </head>
  <body>
  
		<!--头部-->
		<div class="m_head" id="top">
			<a href="#back">
				<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
			</a>
			<a href="#home">
				<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
			</a>
			<div class="forget_text">找回密码</div>
			<div class="line1"></div>
		</div>
		
		<div class="m_input">
		<form role="form" method="post">
		<input type="text" placeholder="请输入11位手机号" class="mobileNum userinput" name="customerUsername" id="mob"/>
		<a href="javascript:;" class="info_ts"><img src="img/login/default_ts.png" class="user_ts show_tip hidden" /></a>
		<p class="help-block"></p>
		<input type="text" placeholder="输入验证码" class="yanzheng" name="code" id="mc"/>
		<div class="btn_yz" id="mc_btn" onClick="return false;">获取验证码</div>
		<p class="help-block"></p>
		<div class="error-info hidden" id="error-info">
			<img src="${basePath}/img/login/error_ts.png" class="error_logo"/><span class="error-tip">密码错误</span>
		</div>
		<a href="javascript:;" class="info_ts"><img src="img/login/default_ts.png" class="pwd_ts show_tip hidden" id="pwd_ts" /></a>
		<div class="btn_login v_next" href="javascript:void(0);">下一步</div>
		</form>
	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${basePath}/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/findpwd/asvalidate.js"></script>
    <script src="${basePath}/js/findpwd/allvalid.js"></script>
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
		
		$("#mob").bind("input propertychange", function() {
			if(document.getElementById('mob').value.length==0){
			$(".accounts_clear").hide();
			}else
			$(".accounts_clear").show();
		});
		
		$("#pwd_num").bind("input propertychange", function() {
			if(document.getElementById('pwd_num').value.length==0){
			$(".pwd_clear").hide();
			}else
			$(".pwd_clear").show();
		});
	  });
	  
	  document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) { // enter 键
			$(".v_next").click();
		}
	 };
	</script>
  </body>
</html>
