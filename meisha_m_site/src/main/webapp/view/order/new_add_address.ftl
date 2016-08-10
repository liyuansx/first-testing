<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
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
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/customer/address.js"></script>
    <script src="${basePath}/js/customer.js"></script>

    <link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/address_new.css" />
	<link rel="stylesheet" href="${basePath}/css/build/address_new.debug.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>

  </head>
  <body>
  
  <!--头部-->
	<div class="m_head" id="top">
	 	<input type="hidden" id="basePath"  value="${basePath}"/>
		<a href="javascript:history.go(-1);">
			<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
		</a>
		<a href="${basePath}/main.html">
			<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
		</a>
		<div class="search_text">新增地址</div>
		<div class="line1"></div>
	</div>
	 <form action="${basePath}/orderaddaddress.htm" id="addForm" method="post">
		<div class="addr_content">
			<div class="sh_person">*收货人</div>
			<input type="text" placeholder="请输入收货人" class="shouhuo" name="addressName" id="addressName"/>
			<div class="line2"></div>
		</div>
		<div class="addr_content">
			<div class="sh_person">*手机号码</div>
			<input type="text" placeholder="请输入手机号码" class="shouhuo"  name="addressMoblie" id="addressMoblie"/>
			<div class="line2"></div>
		</div>
		<div class="addr_content">
			<div class="sh_person">*省市地址 </div>
				<select class="diqu" name="infoProvince"  id="infoProvince">
				</select>
				<div class="dq_houzui">省 </div>
				<select class="diqu" name="infoCity"  id="infoCity">
				</select>
				<div class="dq_houzui">市 </div>
				<select class="diqu" name="infoCounty"  id="infoCounty">
				</select>
				<div class="dq_houzui">区/县 </div>
			<div class="line2"></div>
		</div>
		
		<div class="addr_content">
			<div class="sh_person">*详细地址</div>
			<input type="text" placeholder="请输入详细地址" class="shouhuo"  name="addressDetail" id="addressDetail"/>
			<div class="line2"></div>
		</div>
		<div class="addr_content">
			<div class="sh_person"></div>
			<input type="text" placeholder="" class="shouhuo" />
			<div class="line2"></div>
		</div>
		<div class="addr_content">
			<div class="sh_person">身份证</div>
			<input type="text" placeholder="请输入收货人身份证号" class="shouhuo" />
			<div class="line2"></div>
		</div>
		<div class="btn_login" onclick="checkForm()">完成</div>
	 </form>
  <#--
  <div class="mui-appbar">
      <h2 class="mui-text-center">添加收货人信息</h2>
      <input type="hidden" id="basePath"  value="${basePath}"/>
      <a href="javascript:;" class="back-btn"><i class="fa fa-angle-left"></i></a>
  </div>
  <div class="wrap">
      <div class="mui-container">
          <form action="${basePath}/orderaddaddress.htm" id="addForm" method="post">
              <legend>收货人地址</legend>
              <div class="mui-form-group" >
                  <input type="text" class="mui-form-control"name="addressName" id="addressName" value="" required/>
                  <label class="mui-form-floating-label" >收货人姓名</label>
              </div>
              <div class="mui-form-group"> 
                  <input type="text" class="mui-form-control" name="addressMoblie" id="addressMoblie" value="" required/>
                  <label class="mui-form-floating-label">收货人手机号</label>
              </div>
              <div class="mui-form-group mui-select">
                  <select name="infoProvince" class="form-control" id="infoProvince">
                  </select>
                  <label>省份</label>
              </div>
              <div class="mui-form-group mui-select">
                  <select name="infoCity"  class="form-control" id="infoCity">
                  </select>
                  <label>城市</label>
              </div>
              <div class="mui-form-group mui-select">
                  <select name="infoCounty"  class="form-control" id="infoCounty">
                  </select>
                  <label>区县</label>
              </div>
              <div class="mui-form-group">
                  <textarea class="mui-form-control"name="addressDetail" id="addressDetail" required></textarea>
                  <label class="mui-form-floating-label">地址信息</label>
              </div>
              <button class="mui-btn mui-btn-danger" type="button" onclick="checkForm()">下一步</button>
          </form>
      </div>
  </div>
  -->
  </body>
  <script>
      //验证收件人电话
      var tel="^[0-9\-()（）]{7,18}$";
      //验证表单
      function checkForm(){
           var bl=true;
          //收件人姓名
          if( $("#addressName").val().trim()==''){
              $("#addressName").parent().addClass("error");
              bl=false;
          }else if(! /^([\u4e00-\u9fa5]+|([a-z]+\s?)+)$/.test( $("#addressName").val())){
              $("#addressName").parent().addClass("error");
              bl=false;
          }else{
              $("#addressName").parent().removeClass("error");
          }
          //收件人电话
          if($("#addressMoblie").val().trim()==''){
              $("#addressMoblie").parent().addClass("error");
              bl=false;
          }else if(!(/^(13|14|15|18)\d{9}$/.test( $("#addressMoblie").val()))){
              $("#addressMoblie").parent().addClass("error");
              bl=false;
          }else{
              $("#addressMoblie").parent().removeClass("error");

          }
          //详细地址
          if($("#addressDetail").val().trim()==''){
              $("#addressDetail").parent().addClass("error");
              bl=false;
          }else if($("#addressDetail").val().trim().length>100){
              $("#addressDetail").parent().addClass("error");
              bl=false;
          }else{
              $("#addressDetail").parent().removeClass("error");

          }
          //地区
          if($("#infoCounty").val().trim()==''){
              bl=false;
          }
          if(bl){
              //添加收件地址
              $("#addForm").submit();
          }
      }
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
          //检查收件人姓名
          $("#addressName").blur(function(){
              if($(this).val().trim()==''){
                $("#addressName").parent().addClass("error");

              }else if(! /^([\u4e00-\u9fa5]+|([a-z]+\s?)+)$/.test( $("#addressName").val())){
                  $("#addressName").parent().addClass("error");
              }else{
                  $("#addressName").parent().removeClass("error");
              }
          });
          //检查收件人电话
          $("#addressMoblie").blur(function(){
              if($("#addressMoblie").val().trim()==''){
                  $("#addressMoblie").parent().addClass("error");
              }else if(!(/^(13|14|15|18)\d{9}$/.test( $("#addressMoblie").val()))){
                  $("#addressMoblie").parent().addClass("error");
              }else{
                  $("#addressMoblie").parent().removeClass("error");

              }
          });
          //验证详细地址
          $("#addressDetail").blur(function(){
              if($("#addressDetail").val().trim()==''){
                  $("#addressDetail").parent().addClass("error");
              }else if($("#addressDetail").val().trim().length>100){
                  $("#addressDetail").parent().addClass("error");
              }else{
                  $("#addressDetail").parent().removeClass("error");

              }
          });
      });
      loadProvice();
      selectLocationOption($("#Province").val(),$("#City").val(),$("#County").val(),$("#Street").val(),'infoProvince','infoCity','infoCounty','infoStreet');
  </script>
</html>