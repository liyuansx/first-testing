<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
    <#if (sys.bsetName)??>
    	<title>设置新密码-${(sys.bsetName)!''}</title>
    <#else>
	    <title>设置新密码-${(seo.mete)!''}</title>
    </#if>
	<#assign basePath=request.contextPath>
	
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/build/forgot_pwd_2.css" />
    <link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]> 
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
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
		<div class="forget_text">设置新密码</div>
		<div class="line1"></div>
	</div>
	
	<form role="form" method="post">
		<div class="pwd">
			<img class="pwd_icon" src="${basePath}/img/login/pwd_icon.png"/>
			<input  value="" placeholder="请输入密码" type="password" name="userKey" id="pd" class="new-pwd"/>
			<img class="pwd_clear" src="${basePath}/img/login/btn_clear_grey.png" style="display: none;"/>
		</div>
		<div class="pwd">
			<img class="pwd_icon" src="${basePath}/img/login/pwd_icon.png"/>
			<input  value="" placeholder="确认密码" type="password" id="pdt" class="new-pwd"/>
			<img class="pwd_clear" src="${basePath}/img/login/btn_clear_grey.png" style="display: none;"/>
		</div>
		<div class="error-info hidden" id="pwd_error">
			<img src="${basePath}/img/login/error_ts.png" class="error_logo"/><span class="error-tip">密码错误</span>
		</div>
		<div class="btn_submit p_next" href="javascript:void(0);">提交</div>
	</form>

    <#--
    <div class="login_title">
      <h4>设置新密码</h4>
    </div>
    <div class="reg_form wrap">
      <form role="form" method="post">
         <div class="form-group form-group-lg">
          <input type="password" name="userKey" class="form-control" id="pd" placeholder="设置新密码">
          <p class="help-block"></p>
        </div>
        <div class="form-group form-group-lg">
          <input type="password" class="form-control" id="pdt" placeholder="再次输入密码">
          <p class="help-block"></p>
        </div>
        <a class="sub_btn p_next" href="javascript:void(0);">下一步</a>
      </form>
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
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
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
	  });
	</script>
  </body>
</html>
