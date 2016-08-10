<!DOCTYPE html>
<html lang="zh-cn">
<#assign basePath=request.contextPath>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
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
<link rel="stylesheet" href="${basePath}/css/normalize.css" />
<link rel="stylesheet" href="${basePath}/css/build/qr_order.debug.css" />
<link rel="stylesheet" href="${basePath}/css/magnific-popup.css" />
<link rel="stylesheet" href="${basePath}/css/qr_order/iconfont.css" />
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
<script type="text/javascript" src="${basePath}/js/shoppingcart/jsOperation.js"></script>

</script>
</head>
	<body>
	    <!--计算总商品数量-->
		<#assign num=0>
		<!--计算boss商品数量-->
		<#assign bossNum=0>
		<!--记录第三方商品数量-->
		<#assign thirdNum=0>
		<!--商品优惠价格-->
		<#assign preprice=0>
		<!--商品市场价格-->
		<#assign marketprice=0>
		<#if map??&& map.orderMarketings??&&map.shoplist??>
		    <#list  map.orderMarketings as orderMarketings>
			    <#list map.shoplist as cart>
			        <#if cart.goodsDetailBean.productVo.thirdId?? && cart.goodsDetailBean.productVo.thirdId==orderMarketings.thirdId>
			            <#if orderMarketings.thirdId!=0>
			                <#assign thirdNum=thirdNum+1>
			            <#elseif orderMarketings.thirdId==0>
			                <#assign bossNum=bossNum+1>
			            </#if>
			        </#if>
			    </#list>
			</#list>
		</#if>
		
	<form action="${basePath}/submitorder.htm" method="post" id="subForm">
        <input type="hidden"name="ch_pay" value="${ch_pay!'1'}"/>
        <input type="hidden"name="ch_paythird" value="1"/>
        <input type="hidden" id="bosssumPrice" value="<#if map??>${map.bossSumPrice!0}</#if>"/>
        <input type="hidden"name="typeId" value="${typeId!'0'}" id="typeId"/>
        <input type="hidden"name="codeNo" value="<#if coupon??>${coupon.codeNo!''}</#if>" id="typeId"/>
        <#if coupon??>
        <input type="hidden" value="${coupon.couponRulesType!''}" id="couponType"/>
        <input type="hidden" value="<#if coupon.couponStraightDown??>${coupon.couponStraightDown.downPrice!0}</#if>" id="downPrice"/>
        <input type="hidden" value="<#if coupon.couponFullReduction??> ${coupon.couponFullReduction.reductionPrice!0}</#if>" id="reductionPrice"/>
        </#if>
        
        <input type="hidden"name="deliveryPointId" value="${deliveryPointId!'0'}" id="deliveryPointId"/>
        <input type="hidden" value="${invoiceType!'0'}" name="invoiceType" id="invoiceType">
        <input type="hidden" value="${invoiceTitle!''}" name="invoiceTitle" id="invoiceTitle">
        <input type="hidden" value="${orderAddress.addressDetailInfo!''}" name="addressDetailInfo" id="addressDetailInfo">
		
		<div class="m_head" id="top">
			<a href="javascript:history.go(-1);">
				<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
			</a>
			<a href="${basePath}/main.html">
				<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
			</a>
			<div class="search_text">确认订单</div>
			<div class="line1"></div>
		</div>
		<#if orderAddress.addressDetailInfo??>
			<input type="hidden"name="addressId" value="${orderAddress.addressId!''}" id="addressId"/>
			<div id="some_address" style="display: block;">
				<div class="address">
					<div class="addr_border">
						<div class="addr_text">
							<p id="addName">${(orderAddress.addressName)!""} ${(orderAddress.addressPhone)!""}</p>
							<#if orderAddress.addressDetailInfo??>
							<p id="addDetail">${(orderAddress.proviceFirstStageName)!""}-${(orderAddress.addressCitySecondStageName)!""}-${(orderAddress.addressCountiesThirdStageName)!""}</p>
							<p id="streetDetail">
								<#if (orderAddress.addressDetailInfo?length>20)>
		                            ${orderAddress.addressDetailInfo?substring(0,10)}..
		                         <#else>
		                            ${orderAddress.addressDetailInfo}
		                         </#if>
	                         </p>
							<#else>
							<p> 地址值为空 </p>
							</#if>
							
	
						</div>
						<a href="#addr_modal" class="addr_modal">
							<img src="${basePath}/img/qr_order/jiantou.png" class="jiantou"></img>
						</a>
					</div>
				</div>
			</div>
			<#else>
			<div id="no_address" style="display: block;">
				<div class="address">
					<a href="${basePath}/addresslist.htm">
						<div class="add_address">+新增收货地址</div>
					</a>
				</div>
			</div>
		 </#if>
		<!--发货-->
		<div class="fahuo">
			<div class="bs_fahuo">保税仓发货</div>
		</div>
		<!--订单Item-->
		 <#list map.orderMarketings as orderMarketings>
            <#list map.shoplist as cart>
                <#if cart.goodsDetailBean.productVo.thirdId?? && cart.goodsDetailBean.productVo.thirdId==orderMarketings.thirdId>
					
					<div class="order_item">
						<img src="${cart.goodsDetailBean.productVo.goodsInfoImgId}" class="bed"></img>
						<input type = "hidden" value = "${orderMarketings.thirdId}"name = "thirdId" />
                        <input type="hidden" value="${cart.shoppingCartId}" name="shoppingCartId">
						<div class="bed_title">
							 <#if (cart.goodsDetailBean.productVo.productName?length>20)>
	                            ${cart.goodsDetailBean.productVo.productName?substring(0,20)}
	                         <#else>
	                            ${cart.goodsDetailBean.productVo.productName}
	                         </#if>
						</div>
						<div class="bed_num">${cart.goodsNum}</div>
						<div class="bed_price">${(cart.goodsDetailBean.productVo.goodsInfoPreferPrice?number)?string("0.00")}￥</div>
						<#assign num=num+(cart.goodsNum)?number>
						<#assign preprice=preprice+((cart.goodsDetailBean.productVo.goodsInfoMarketPrice?number-cart.goodsDetailBean.productVo.goodsInfoPreferPrice?number)*cart.goodsNum) >
						<#assign marketprice=marketprice+((cart.goodsDetailBean.productVo.goodsInfoMarketPrice?number)*cart.goodsNum)?number>
					</div>	
		 		</#if>
            </#list>
        </#list>
		
		<!--banner-->
		<div class="banner"></div>
		
		<!--结算-->
		<div class="jiesuan">
			<div class="bs_jiesuan">结算</div>
		</div>
		<input type = "hidden" value = "${map.sumPrice}" id = "sumPrice" / >
		<div class="sp_price">
			<div class="sp_title">商品价格 </div>
			<div class="sp_jiage">${map.sumPrice?string("0.00")} ￥ </div>
		</div>
		
		<div class="sp_price">
			<div class="sp_title">商品活动价格 </div>
			<div class="sp_jiage">${marketprice?string("0.00")}￥ </div>
		</div>
		
		<div class="sp_price">
			<div class="sp_title">运费 </div>
			<div class="sp_jiage" style="color: #999;">00.00￥ </div>
		</div>
		
		<div class="sp_price">
			<div class="sp_title">税费 </div>
			<div class="sp_jiage">0.00￥</div>
		</div>
		
		<div class="sp_price" style="margin-bottom: 20px;">
			<div class="sp_title">应付总额 </div>
			<div class="sp_jiage" style="color: #e31436;"><span id="payPrice">${map.sumPrice?string("0.00")}</span>￥</div>
		</div>
		<!--banner-->
		<div class="banner"></div>
		
		<!--支付-->
		<div class="jiesuan">
			<div class="bs_jiesuan">支付方式</div>
		</div>
		
		<!--支付Item-->
		<div class="pay_item">
			<div class="zhifubao iconfont">&#xe6ec;</div>
			<div class="pay_title">
				<p style="font-size:0.342rem;">支付宝</p>
				<p style="color:#999;font-size:0.299rem;">支付最便捷，可银行卡支付</p>
			</div>
			<a>
				<div class="gou gou_select iconfont">&#xe623;</div>
			</a>
		</div>
		<input type="hidden" value="1" />
		
		
		<#--支付Item
		<div class="pay_item">
			<div class="weixin iconfont">&#xe60c;</div>
			<div class="pay_title">
				<p>微信支付</p>
				<p>快速简单，提高生活水平</p>
			</div>
			<a>
				<div class="gou iconfont">&#xe623;</div>
			</a>
		</div>
		-->

		<!--实际支付-->
		<div class="sj_pay">
			<div class="pay_text">
				<p style="font-weight:bold;">
					实付款：<span id="payPrice">${map.sumPrice?string("0.00")}</span><span style="color: #e31436;">￥</span>
				</p>
				<p class="jieshen">为您节省：${preprice?string("0.00")}￥</p>
			</div>			
				<a href="javascript:;" onclick="checkForm()" class="popup-modal">   

				<div class="btn_login" >提交订单</div>
				</a>
		</div>
		
		<!--底层-->
		<div class="bottom_padding"></div>
		<!--弹框-->
		<div id="test-modal" class="white-popup-block mfp-hide">
			<div class="delete_addr">请添加收货地址！</div>
			<div class="quxiao"> </div>
			<!--<div class="line5"></div>-->
			<div class="quxiao">关闭</div>
		</div>
		<!--弹框-->
		<div id="addr_modal" class="addr-popup-block mfp-hide ">
			<div class="addr_btn">
				<a href="#">
					<p class="modal_quxiao cancel">取消</p>
				</a>
				<a href="#">
					<p class="modal_sure cancel">确定</p>
				</a>
			</div>
			<div class="tiaoxingma"></div>
			
			<#list addresses as address>
				<div class="modal_item <#if orderAddress.addressDetailInfo??><#if orderAddress.addressId==address.addressId>modal_select</#if></#if>" 
					addressId="${address.addressId!''}" addressName="${(address.addressName)!""}" addressMoblie="${address.addressMoblie}" 
					addressDetail="${(address.province.provinceName)!''}${(address.city.cityName)!''}${(address.district.districtName)!''}${(address.street.streetName)!''}" addressDetailInfo="${(address.addressDetail)!''}">
					<div class="modal_addr">
						<p>${(address.addressName)!""}&nbsp; ${address.addressMoblie}</p>
						<p>${(address.province.provinceName)!''} - ${(address.city.cityName)!''} - ${(address.district.districtName)!''}</p>
						<div class="addr_detail">
						<#if (address.addressDetail?length>20)>
	                            ${address.addressDetail?substring(0,18)}..
	                    <#else>
	                            ${address.addressDetail}
	                    </#if>
						</div>
					</div>
					<a href="${basePath}/toupdateAddress.htm?addressId=${address.addressId}" dataId="${address.addressId}">
						<p class="modal_edit">编辑</p>
					</a>
				</div>
			</#list>

			<div class="modal_padding"></div>
			<a href="${basePath}/toAddAddress.htm">
				<div class="add_new_addr">新增地址</div>
			</a>
		</div>
		</form>
		<#-- 实名验证弹出框       -->
	<div class="qa_bg" style="display:none" ></div>
		<div class="qa_dialog" style="display:none">
			<div class="qa_dialog_head">实名认证</div>
			<p class="qa_dialog_note">根据海关要求，购买跨境商品需要对认购人进行实名认证</p>
			<div class="qa_dialog_name">
				<p class="qa_dialog_name_p">订购人姓名</p>
				<input class="qa_dialog_name_input" placeholder="请填写您的真实姓名" id="realName"/>
				<div style="clear: both;"></div>
				<p class="qa_dialog_name_note" style="display: none;">你输入的真实姓名有误，请重新输入</p>
			</div>
			<div class="qa_dialog_num">
				<p class="qa_dialog_num_p">身份证号码</p>
				<input class="qa_dialog_num_input" placeholder="请填写您的身份证号码" id="certNo"/>
				<div style="clear: both;"></div>
				<p class="qa_dialog_num_note" style="display: none;">你输入的身份证号码有误，请重新输入</p>
			</div>
			<div class="qa_btns">
				<div class="cancel_btn">取消</div>
				<div class="submit_btn" onclick="checkRealname()">认证</div>
				
			</div>
		</div>
		<#--
		<form action="${basePath}/submitorder.htm" method="post" id="subForm">
		        <input type="hidden"name="ch_pay" value="${ch_pay!'1'}"/>
		        <input type="hidden"name="ch_paythird" value="1"/>
		        <input type="hidden" id="bosssumPrice" value="<#if map??>${map.bossSumPrice!0}</#if>"/>
		        <input type="hidden"name="typeId" value="${typeId!'0'}" id="typeId"/>
		        <input type="hidden"name="codeNo" value="<#if coupon??>${coupon.codeNo!''}</#if>" id="typeId"/>
		        <#if coupon??>
		        <input type="hidden" value="${coupon.couponRulesType!''}" id="couponType"/>
		        <input type="hidden" value="<#if coupon.couponStraightDown??>${coupon.couponStraightDown.downPrice!0}</#if>" id="downPrice"/>
		        <input type="hidden" value="<#if coupon.couponFullReduction??> ${coupon.couponFullReduction.reductionPrice!0}</#if>" id="reductionPrice"/>
		        </#if>
		        
		        <input type="hidden"name="deliveryPointId" value="${deliveryPointId!'0'}" id="deliveryPointId"/>
		        <input type="hidden" value="${invoiceType!'0'}" name="invoiceType" id="invoiceType">
		        <input type="hidden" value="${invoiceTitle!''}" name="invoiceTitle" id="invoiceTitle">
			    <#if orderAddress??>
			    <input type="hidden"name="addressId" value="${orderAddress.addressId!''}" id="addressId"/>
			        <div class="mui-panel order-panel address-item">
			            <div class="panel-item">
			                    <span class="address-info"><i class="fa fa-user"></i>${(orderAddress.addressName)!""}</span>
			                    <span class="address-info"><i class="fa fa-mobile"></i>	${(orderAddress.addressPhone)!""}</span>
			                    <p>${(orderAddress.addressDetail)!""}&nbsp;
			                    ${(orderAddress.addressDetailInfo)!""}</p>
					    <a href="${basePath}/addresslist.htm">
					                <i class="fa fa-angle-right"></i>
					    </a>
			            </div>
			        </div>
			    </#if>
		
		        <div class="mui-panel order-panel">
		            <div class="panel-item">
	                    <div class="mt-new">
	                        <#if payList??>
	                         <#list payList as pl>
	                            <#if pl.isOpen=="1">
	                                <#if ch_pay??>
	                                        <#if ch_pay==pl.paymentId>
	                                        ${pl.name}
	                                        </#if>
	                                <#else>
	                                    <#if pl_index==0>
	                                    ${pl.name}
	                                    </#if>
	                                </#if>
	                            </#if>
	                         </#list>
	                        </#if>
	                    </div>
		
			<#if map?? && map.freightlist?size!=0 &&typeId==0>
		    <#list map.freightlist as freight>
		          <div class="mc">
		       		 <#if freight_index==0>${freight.freightTemplateName}</#if>
		          </div>
		     </#list>
	         <div class="mc">平台会再24小时内发货，请注意查看！</div>
		        <#elseif typeId==1>
		        <div class="mc">
		     		   上门自提
		        </div>
		        <div class="mc"><#if dps??>${dps.temp3!''}${dps.address!''}</#if></div>
		        </#if>
		
				<a href="${basePath}/toFreightAndPay.htm?addressId=${orderAddress.addressId}&typeId=${typeId!''}&ch_pay=${ch_pay!''}&deliveryPointId=${deliveryPointId!''}&codeNo=<#if coupon??>${coupon.codeNo!''}</#if>">
				<i class="fa fa-angle-right"></i>
				</a>
		            </div>
		        </div>
		
				<div class="mui-panel order-panel pros-item">
				<div class="panel-item mui-clearfix">
				                    <div class="mt-new">纸质发票：<span id="invoiceTitle">${invoiceTitle!'不使用'}</span> </div>
				                    <div class="mc" id="invoiceType">${invoiceType!''}</div>
				
				    <a href="${basePath}/tochangeInvoice.htm?invoiceTitle=${invoiceTitle!''}&invoiceType=${invoiceType!''}&addressId=${orderAddress.addressId}&typeId=${typeId!''}&ch_pay=${ch_pay!''}&deliveryPointId=${deliveryPointId!''}&codeNo=<#if coupon??>${coupon.codeNo!''}</#if>">
				    <i class="fa fa-angle-right"></i>
				    </a>
				</div>
				</div>
		
		        <div class="mui-panel order-panel pros-item">
		            <div class="panel-item mui-clearfix">
		                    <div class="sitem-l">
		                    <#list map.orderMarketings as orderMarketings>
		                        <#list map.shoplist as cart>
		                            <#if cart.goodsDetailBean.productVo.thirdId?? && cart.goodsDetailBean.productVo.thirdId==orderMarketings.thirdId>
		                            <img src="${cart.goodsDetailBean.productVo.goodsInfoImgId}" alt=""/>
		                            <input type = "hidden" value = "${orderMarketings.thirdId}"name = "thirdId" />
		                            <input type="hidden" value="${cart.shoppingCartId}" name="shoppingCartId">
		                                <#assign num=num+(cart.goodsNum)?number>
		                            </#if>
		                        </#list>
		                    </#list>
		                    </div>
					<a href="${basePath}/toproductsList.htm" >
	                    <div class="sitem-r">共${num}件</div>
	               		<i class="fa fa-angle-right"></i>
					</a>
		            </div>
		        </div>
		
		        <div class="mui-panel order-panel">
		          <div class="panel-item mui-clearfix">
		            <div class="sitem-l" >
		            	优惠券
					<#if coupon??&&coupon.couponName??><span class="tip">${coupon.couponName}</span></#if>
		            </div>
					<a style="display:inline" href="${basePath}/tocouponlist.htm?addressId=${orderAddress.addressId}&typeId=${typeId!''}&ch_pay=${ch_pay!''}&deliveryPointId=${deliveryPointId!''}&codeNo=<#if coupon??>${coupon.codeNo!''}</#if>">
					<div class="sitem-r">选择</div>
					     <i class="fa fa-angle-right"></i>
					</a>
		            </div>
		            <div class="panel-item mui-clearfix switch-item"<#if bossNum==0>style="display:none"</#if> >
		                <input type="hidden" value="${map.customerPoint!'0'}" id="pointSum">
		                 <input type="hidden" value="${map.pointSet!'0'}" id="pointSet">
		            <div class="sitem-l">
		           	   您有${map.customerPoint!'0'}积分
		            <span class="mc">
		        	    本次可用<b id="muqianjifen" style="font-size:16px"></b>积分，使用
		            <input class="integral-input" type="text"name="point" id="point" onblur="checkjifen()" />
		          	  积分
		            </span> <br/><span id="errorPoint" class="mc" style="color:red;"></span>
		            </div>
		            <div class="sitem-l">
		                <span class="mc"> 每10积分兑换人民币￥${map.pointSet}元。</span>
		            </div>
		            </div>
		        </div>
				<input type = "hidden" value = "${map.sumPrice}" id = "sumPrice" / >
		        <div class="mui-panel order-panel total-item">
		            <div class="mui-clearfix">
		                <div class="sitem-l">商品金额</div>
					<div class= "sitem-r" >${map.sumPrice?string("0.00")}</div>
		            </div>
		            <div class="mui-clearfix">
		                <div class="sitem-l">运费</div>
		                <div class="sitem-r" id="ep">5.00</div>
		            </div>
		        <#if bossNum!=0>
		            <div class="mui-clearfix">
		            <div class="sitem-l">积分兑换</div>
		            <div class="sitem-r" id="pointPrice">-0.00</div>
		            </div>
		        </#if>
		        <div class="mui-clearfix">
		        <div class="sitem-l">优惠券</div>
		        <div class="sitem-r" id="couponprice">-0.00</div>
		        </div>
				<div class="mui-clearfix" style="height:30px">
		        </div>
		        <input type="hidden"  />
			    <div class="pay-bar mui-clearfix">
			        <input type="hidden" id="payPrice2"/>
			        <div class="pay-cont">
			            实付款：&yen;&nbsp;
			            <span id="payPrice"></span>
			        </div>
			        <button class="mui-btn mui-btn-danger" type="button" onclick="checkForm()">提交订单</button>
			    </div>
		</form>-->
		
		<script src="${basePath}/js/mui.min.js"></script>
		<script src="${basePath}/js/app.js"></script>
		
		<script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
		
		<script src="${basePath}/js/jquery.magnific-popup.min.js"></script>
		<script src="${basePath}/js/qr_order/main.js"></script> 
	<script>
		function checkRealname(){
		
		var realName=$("#realName").val();
		var certNo=$("#certNo").val();
		var flag=validIdnum(certNo);
		if(realName.length == 0 || certNo.length == 0){
			alert("空值！");
		}
		else{
			if(flag){
//				alert("身份证格式正确");
				 $.post("${basePath}/checkRealname.html",
				        {realName: realName, certNo: certNo},
				        function (data) {
				      		if(data=="pass"){
				      			alert("身份证认证成功");
				      			$(".qa_bg").hide();
								$(".qa_dialog").hide();
				      			
				      		}else{
				      			alert("身份证认证失败");
				      		}
				        });
			}else{
				alert("身份证格式错误");
			}
			//名字。身份证号码正则表达式 验证
		}
	}
	
	
	$(document).ready(function(){
	$(".submit_btn").click(function(){
//		$(".qa_dialog_name_note").show();
//		$(".qa_dialog_num_note").show();
	});
	$(".cancel_btn").click(function(){
		$(".qa_bg").hide();
		$(".qa_dialog").hide();
	});
	
});
	
	    var isflag=true;
	    var subNum=0;
	    
	    function checkForm(){  
	    window.location.reload(true);
	      if( isflag && subNum==0 ){
	          subNum=subNum+1;
	          if ($("#addressDetailInfo").val()=="")
	          	alert("请添加收货地址");
	          else{
	      	  $("#subForm").submit();
	      	  }
	      }
	    }
	    
	    function realname(){
	    	$.get("${basePath}/checkisRealnameM",function(data){
     		    if(data ==0){
     		  	 $(".qa_dialog").show();
     		  	 $(".qa_bg").show();
     		    }
     		    else
           });
        }
	    //使用积分兑换    
	    function checkjifen(){
	            isflag=true;
	            var point=$("#point").val();//使用的积分
	            var pointSet=$("#pointSet").val();//积分兑换规则
	             var bossSumPrice=$('#bosssumPrice').val();	//boss商品总金额
	            var keyishiyong = accDiv(accMul(bossSumPrice,10),pointSet);
	            keyishiyong=parseInt(parseInt(accDiv(parseInt(keyishiyong),10))+"0");
	            var pointSum=$("#pointSum").val();//用户的总积分
	            var payPrice=$("#payPrice2").val();//支付金额
	            var pointPrice=point*pointSet; //
	            if(!isNaN(point)&&point>=0){
	                if(Subtr(pointSum,point)<0){
	                    isflag=false;
	                    //积分不足
	                    $("#errorPoint").html("您的积分不足！");
	                    $("#point").val("");
	                }else if(point %10 !=0){
	                    isflag=false;
	                    $("#errorPoint").html('注：兑换的积分必须为10的倍数!');
	                    $('#point').val('');
	                }
	                else if(Subtr(bossSumPrice,accDiv(pointPrice,10))<0){
	                    isflag=false;
	                    //积分兑换金额大于订单金额
	                    $("#errorPoint").html("注：此次订单价格最多可以使用【"+keyishiyong+"】的积分！");
	                    $("#point").val("");
	                }else{
	                    $("#errorPoint").html("");
	                    $("#pointPrice").html("-"+accDiv(pointPrice,10));
	                    $("#payPrice").html(Subtr(payPrice,accDiv(pointPrice,10)));
	                }
	            }else{
	                isflag=false;
	                $("#errorPoint").html("请填写正整数！");
	                $("#point").val("");
	            }
	        return isflag;
	    }
	    $(function(){
	        var pointSet=$("#pointSet").val();//积分兑换规则
	        var bossSumPrice=$('#bosssumPrice').val();	//boss商品总金额
	        var keyishiyong = accDiv(accMul(bossSumPrice,10),pointSet);
	        keyishiyong=parseInt(parseInt(accDiv(parseInt(keyishiyong),10))+"0");
	        var pointSum=$("#pointSum").val();//用户的总积分
	        if(bossSumPrice>0){
	            if(pointSum<keyishiyong){
	                $('#muqianjifen').html(pointSum);          //设置目前可用的积分
	            }else{
	                $('#muqianjifen').html(keyishiyong);          //设置目前可用的积分
	            }
	        }else{
	            $('#muqianjifen').html(0);          //设置目前可用的积分
	        }
	
	        var yfprice = 0;
	        //boss运费
	        var bossyfprice=0;
	        var payprice =$("#sumPrice").val();;
	        var addsId = $("#addressId").val();
	        var boxs=new Array();
	        //获取订单数量
	        $('input[name="shoppingCartId"]').each(function () {
	            boxs.push(parseInt($(this).val()));
	        });
	
	        //计算此次购买商品总运费价格
	            $.ajax({
	                type:"post",
	                url: "${basePath}/getnewMexpressprice.htm",
	                async : false,
	                data: {addressId: addsId, shopIds: boxs},
	                success:function(data){
	                    //key值为Map的键值
	                    $.each(data,function(key,value){
	                        if(data!=null&&data!=''){
	                            //总运费
	                            if(key=="freightmoney"){
	                                yfprice = accAdd(value, yfprice);
	                            }
	                            //boss运费
	                            if(key=="bossfreight"){
	                                bossyfprice = accAdd(value, bossyfprice);
	                            }
	                        }
	                    });
	                }
	            });
	        var paysumprice=0;
	        var thirdprice=Subtr(yfprice,bossyfprice);
	        //上门自提时免运费
	        if($("#typeId").val()==1){
	            $("#ep").html("+"+thirdprice);
	            $("#payPrice").html(accAdd(payprice,thirdprice));
	            $("#payPrice2").val(accAdd(payprice,thirdprice));
	            paysumprice=accAdd(payprice,thirdprice);
	        }else{
	            $("#ep").html("+"+yfprice);
	            $("#payPrice").html(accAdd(payprice,yfprice));
	            $("#payPrice2").val(accAdd(payprice,yfprice));
	            paysumprice=accAdd(payprice,yfprice);
	        }
	        //使用购物券类型 1:直降 2:满减
	        var couponType=$("#couponType").val();
	        //购物券直降金额
	        var downPrice=$("#downPrice").val();
	        //购物券满减金额
	        var reductionPrice=$("#reductionPrice").val();
	        if(couponType==1){
	            $("#couponprice").html("-"+downPrice);
	            $("#payPrice").html(Subtr(paysumprice,downPrice));
	            $("#payPrice2").val(Subtr(paysumprice,downPrice));
	        }
	        if(couponType==2){
	            $("#couponprice").html("-"+reductionPrice);
	            $("#payPrice").html(Subtr(paysumprice,reductionPrice));
	            $("#payPrice2").val(Subtr(paysumprice,reductionPrice));
	        }
	    })
	    
	    var flagId = false;
function validIdnum(idnum) {
	var reg = /^([1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3})|([1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X))$/;
//	var reg = /^[1-9]([0-9]{14}|[0-9]{17})$/;
	if (idnum == null || idnum == "") {
		$("#idnum_msg").html("");
		$("#idnum").removeClass("text_error");
		$("#idnum_msg").removeClass("tips_error");
		$("#idnum_img").hide();
		return true;
	}
	if (!reg.test(idnum)) {
		$("#idnum_msg").html("身份证号码格式不正确");
		$("#idnum").addClass("text_error");
		
		$("#idnum_msg").addClass("tips_error");
		$("#idnum_img").prop("src", "../images/error-ico.png");
		$("#idnum_img").show();
		return false;
	}
	$("#idnum_msg").html("身份证号码格式正确").addClass("tips_succ");
	$("#idnum").removeClass("text_error");
	
	$("#idnum_msg").removeClass("tips_error");
	$("#idnum_img").prop("src", "../images/succ-ico.png");
	$("#idnum_img").show();
	flagId = true;
	return true;
}
	</script>
	</body>
</html>

