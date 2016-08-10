<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <#if (sys.bsetName)??>
    	<title>登录-${(sys.bsetName)!''}</title>
    <#else>
	    <title>登录-${(seo.mete)!''}</title>
    </#if>

    <!-- Bootstrap -->
    <link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}/css/idangerous.swiper.css" rel="stylesheet">
    <link href="${basePath}/css/style.css" rel="stylesheet">
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/login.css" />
	<link rel="stylesheet" href="${basePath}/css/build/login.debug.css" />
	
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
			<img src="${basePath}/img/login/back@2x_02.png" class="iconfont_back"></img>
		</a>
		<div class="login">登录</div>
		<a href="${basePath}/register.html">
			<div class="register">注册</div>
		</a>
		<hr />
	</div>
	
	<div class="m_login">
		<form role="form">
		<img class="iconfont_renwu" src="${basePath}/img/login/renwu@2x_06.png"></img>
		<input type="text" id="log_user" placeholder="请输入手机号/用户名/邮箱" class="username userinput" />
		<a href="javascript:;" class="info_ts"><img src="img/login/default_ts.png" class="user_ts show_tip hidden" /></a>
		<div class="line1"></div> 
		<input type='hidden' id="urlhide" value="${url}" />
		<img class="mima" src="${basePath}/img/login/mima@2x_06.png"></img>
		<input type="password" placeholder="请输入6-16位密码" class="password userinput" id="log_psd" oncopy="return false;" oncut="return false;" onpaste="return false"/>
		<a href="javascript:;" class="info_ts"><img src="img/login/default_ts.png" class="pwd_ts show_tip hidden" /></a>
		<div class="line2"></div>
		<p class="help-block" id="pwd_err" ></p>
		<p class="help-block" id="lu_err"></p>
		<div class="error-info hidden" id="error-info">
			<img src="img/login/error_ts.png" class="error_logo"/><span class="error-tip">密码错误</span>
		</div>
		<a href="${basePath}/valididentity.html">
			<div class="findpassword">找回密码</div>
		</a>
		<a href="${basePath}/register.html">
			<div class="gotoreg">没有吗？ 快点点击这里</div>
		</a>
		<div class="btn_login" onclick="login()">登录</div>
		</form>
		<div class="line3"></div>
		<div class="hezuo">或使用合作账号登录</div>
		<a href="#qq">
			<img src="${basePath}/img/login/iconfont_qq@2x.png" class="iconfont_qq"></img>
			<div class="qq_login">QQ</div>
		</a>
		<a href="#weixin">
			<img src="${basePath}/img/login/iconfont_weibiaoti@2x.png" class="iconfont_wx"></img>
			<div class="wx_login">微信</div>
		</a>
		<a href="#weibo">
			<img src="${basePath}/img/login/iconfont_weibo@2x.png" class="iconfont_wb"></img>
			<div class="wb_login">微博</div>
		</a>
	</div>
	<script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <#--
    <div class="login_title">
      <h4>欢迎登录${(mobSiteBasic.ename)!''}</h4>
    </div>
    <div class="login_box">
      <div class="login_form">
        <form role="form">
          <div class="form-group form-group-lg">
            <input type="text" class="form-control" id="log_user" placeholder="${(mobSiteBasic.ename)!''}账号/邮箱/手机号码">
            <p class="help-block" id="lu_err"></p>
          </div>
          <input type='hidden' id="urlhide" value="${url}" />
          <div class="form-group form-group-lg">
            <input type="password" class="form-control" id="log_psd" placeholder="密码" oncopy="return false;" oncut="return false;" onpaste="return false">
            <p class="help-block" id="pwd_err"></p>
          </div>
        </form>
      </div>
      <p class="light">登录即表示您同意我们的<a class="orange"  onclick="xieyi()">用户协议</a></p>
      <a class="sub_btn" href="javascript:void(0)" onclick="login()">登录</a>
      <p class="text-right light">
        <a class="light" href="${basePath}/register.html">立即注册</a>
        <span>|</span>
        <a class="light" href="${basePath}/valididentity.html">忘记密码？</a>
      </p>
    </div>
    <div class="foot">
      <p>由${(mobSiteBasic.technicalSupport)!''}提供技术支持</p>
    </div>
    <#include '/common/smart_menu.ftl' />
    -->
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${basePath}/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/login/login.js"></script>
    <script>
	  $(function(){
		$('.fill_item input').focus(function(){
		  $(this).parent().next().css('borderColor','#f0375e');
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
		
		$(".show_tip").click(function(){
			$(this).parent(".info_ts").prev(".userinput").val("");
			$(this).addClass("hidden");
		});
	  });
	</script>
  </body>
</html>
