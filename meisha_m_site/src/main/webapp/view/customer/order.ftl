<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
	<meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#if (sys.bsetName)??>
    	<title>我的订单-${(sys.bsetName)!''}</title>
    	<input type="hidden" id="bsetName" value="${(sys.bsetName)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    <#else>
	    <title>我的订单-${(seo.mete)!''}</title>
	    <input type="hidden" id="bsetName" value="${(seo.mete)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    </#if>
	
	<link rel="stylesheet" href="${basePath}/css/normalize.css" />
	<link rel="stylesheet" href="${basePath}/css/order.css" />
	<link rel="stylesheet" href="${basePath}/css/build/order.debug.css" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
	
	<link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]> 
      <script src="${basePath}/js/html5shiv.min.js"></script>
      <script src="${basePath}/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  
	<div class="m_head" id="top">
		<a href="javascript:;"onclick="javascript:history.go(-1)">
			<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
		</a>
		<a href="${basePath}/main.html">
			<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
		</a>
		<div class="search_text">我的订单</div>
		<div class="line1"></div>
	</div>
	
	<div class="m_nav order_filter">
		<a href="javascript:;" >
			<div class="or_all <#if (!type?? || type == '5') >or_selected </#if>" data-val="5">全部</div>
		</a>
		<a href="javascript:;">
			<div class="or_all <#if (type?? && type == '0') >or_selected </#if>" data-val="0">待付款</div>
		</a>
		<a href="javascript:;">
			<div class="or_all <#if (type?? && type == '2') >or_selected </#if>" data-val="2">待发货</div>
		</a>
		<a href="javascript:;">
			<div class="or_all <#if (type?? && type == '3') >or_selected </#if>" data-val="3">待收货</div>
		</a>
		<a href="javascript:;">
			<div class="or_all <#if (type?? && type == '4') >or_selected </#if>" data-val="4">待评价</div>
		</a>

	</div>
	<div class="or_banner"></div>
	<#if (pb.list?size!=0)>
        <#list pb.list as order>
        	<#assign cFlag=0 />
		        <#list order.goods as good>
		        	<#if good.evaluateFlag== '0'>
		                 <#assign cFlag=cFlag+1 />
		            </#if>
		         </#list>
				<div class="or_list">
					<div class="or_title">
						<div class="or_state">
						<#if order.orderStatus??>
								<#if order.orderStatus=="0">
									未付款
								<#elseif (order.orderStatus=="1" | order.orderStatus=="5" | order.orderStatus=="6") >
									待发货
								<#elseif order.orderStatus=="2">
									待收货
								<#elseif (order.orderStatus=="3" & cFlag>0) >
	                            	待评价
								<#elseif order.orderStatus=="3">
									已完成
								<#elseif (order.orderStatus=="4") >
									已取消
                                <#elseif order.orderStatus=="7">
                                  	  退货审核中
                                <#elseif order.orderStatus=="8">
                                   	 同意退货
                                <#elseif order.orderStatus=="9">
                                                                                           拒绝退货
                                <#elseif order.orderStatus=="10">
                                                                                           待商家收货
                                <#elseif order.orderStatus=="11">
                                   	 退货结束
                                <#elseif order.orderStatus=="12">
                                   	 退款审核中
                                <#elseif order.orderStatus=="13">
                                                                                           拒绝退款
                                <#elseif order.orderStatus=="14">
                                  	  已提交退货审核
                                <#elseif order.orderStatus=="16">
                                                                                            商家收货失败
                                <#elseif order.orderStatus=="17">
                                                                                            已退款
								</#if>
							</#if></div>
						<div class="or_num">订单号：${order.orderNo}</div>
						<div class="or_time">${order.addTime?string('yyyy-MM-dd HH:mm:ss')}</div>
						<a href="#back">
							<img src="${basePath}/img/order/jiantou@2x.png" class="jiantou"></img>
						</a>
					</div>
					
					<#assign cFlag=0 /> 
			        <#list order.goods as good>
			        	<#if good.evaluateFlag== '0'>
			                 <#assign cFlag=cFlag+1 />
			            </#if>
						<a href="${basePath}/customer/detail-${order.orderId}.html">
						<div class="or_content">
							<img src="${(good.goodsImg)!''}" class="chanpin"></img>
							<div class="Opium">${good.goodsName!''}</div>
							<div class="price"><#if good.goodsPrice??>${good.goodsPrice?string('0.00')}</#if>￥</div>
							<div class="number">×${good.goodsNum}</div>
						</div>
						</a>
					</#list>

					<div class="heji">
						<div class="hj_heji">合计：</div>
						<div class="hj_price"><#if order.moneyPaid??>
										${order.moneyPaid?string('0.00')}
									 </#if>￥</div>
									 
						<#if order.orderStatus??>
							<#if order.orderStatus=="0">
								
								<a href="${basePath}/orderdetailpay-${order.orderId}.html">
									<div class="btn_pingjia">去付款</div>
								</a>
								
								<a  href="javascript:;" onClick="cancelOrder('${basePath}/customer/cancelorder-myorder-${order.orderId}.html')">
									<div class="btn_checkorder" style="background: #999;">取消</div>
								</a>
							<#elseif (order.orderStatus=="1"| order.orderStatus=="5" | order.orderStatus=="6") >
								<a href="${basePath}/customer/detail-${order.orderId}.html" class="">
									<div class="btn_checkorder">查看订单</div>
								</a>
							<#elseif (order.orderStatus=="2")>
								<a href="${basePath}/customer/detail-${order.orderId}.html" class="">
									<div class="btn_pingjia">查看订单</div>
								</a>
								<a  href="javascript:;" class="">
      								<div class="btn_pingjia">查看物流</div>
      							</a>
      							<#--
      							<a  href="javascript:;" onClick="comfirmgoods('${basePath}/customer/comfirmofgoods-myorder-${order.orderId}.html')" class="">
      								<div class="btn_pingjia">确认收货</div>
      							</a>
								-->
		          			<#elseif (order.orderStatus=="3" & cFlag>0)>
      							<a  href="${basePath}/comment-${order.orderId}.html" class="">
      								<div class="btn_pingjia">评价商品</div>
      							</a>
      							<a href="${basePath}/customer/detail-${order.orderId}.html">
									<div class="btn_checkorder">查看订单</div>
								</a>
							<#elseif (order.orderStatus=="3") >
								<a href="${basePath}/customer/detail-${order.orderId}.html" class="">
									<div class="btn_checkorder">查看订单</div>
								</a>
							<#elseif (order.orderStatus=="4") >
								<a href="${basePath}/customer/detail-${order.orderId}.html" class="">
									<div class="btn_checkorder">查看订单</div>
								</a>
							</#if>
						</#if>
					</div>
				</div>
				<div class="or_banner"></div>
		</#list>
	</#if>

  	<#--
    <div class="order_filter">
      <ul>
        <li <#if (!type?? || type == '5') >class="current"</#if> data-val="5"><a href="javascript:;">全部</a></li>
        <li <#if (type?? && type == '0')>class="current"</#if> data-val="0"><a href="javascript:;">未付款</a></li>
        <li <#if (type?? && type == '2')>class="current"</#if> data-val="2"><a href="javascript:;">待发货</a></li>
        <li <#if (type?? && type == '3')>class="current"</#if> data-val="3"><a href="javascript:;">待收货</a></li>
        <li <#if (type?? && type == '4')>class="current"</#if> data-val="4"><a href="javascript:;">待评价</a></li>
      </ul>
    </div>
    <div class="order_list">
       <#if (pb.list?size!=0)>
	        <#list pb.list as order>
	        	<#assign cFlag=0 />
			      <div class="order_item">
			        <div class="order_title">
			        <#list order.goods as good>
			        	<#if good.evaluateFlag== '0'>
			                 <#assign cFlag=cFlag+1 />
			            </#if>
			         </#list>
			          <span class="status pull-right">
							<#if order.orderStatus??>
								<#if order.orderStatus=="0">
									未付款
								<#elseif (order.orderStatus=="1" | order.orderStatus=="5" | order.orderStatus=="6") >
									待发货
								<#elseif order.orderStatus=="2">
									待收货
								<#elseif (order.orderStatus=="3" & cFlag>0) >
	                            	待评价
								<#elseif order.orderStatus=="3">
									已完成
								<#elseif (order.orderStatus=="4") >
									已取消
                                <#elseif order.orderStatus=="7">
                                    退货审核中
                                <#elseif order.orderStatus=="8">
                                    同意退货
                                <#elseif order.orderStatus=="9">
                                    拒绝退货
                                <#elseif order.orderStatus=="10">
                                    待商家收货
                                <#elseif order.orderStatus=="11">
                                    退货结束
                                <#elseif order.orderStatus=="12">
                                    退款审核中
                                <#elseif order.orderStatus=="13">
                                    拒绝退款
                                <#elseif order.orderStatus=="14">
                                    已提交退货审核
                                <#elseif order.orderStatus=="16">
                                    商家收货失败
                                <#elseif order.orderStatus=="17">
                                    已退款
								</#if>
							</#if>
			          </span>
			          <h4>订单号：${order.orderNo}</h4>
			        </div>
			        <#assign cFlag=0 />
			        <#list order.goods as good>
			        	
			        	<#if good.evaluateFlag== '0'>
			                 <#assign cFlag=cFlag+1 />
			            </#if>
				      <#if (good_index + 1<=2)>
				        <div class="order_good">
				          <a href="${basePath}/customer/detail-${order.orderId}.html">
					          <img width="50" height="50" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
					          <h5 class="name"><span style="display:block;height:37px;overflow:hidden;">${good.goodsName!''}</span>
					          	<small>
					          		<#if (good.specVo??)>
										<#list good.specVo as spec>
											<#if spec_index=0>
												${(spec.spec.specName)!''}:${(spec.specValueRemark)!''}
											</#if>
										</#list>
									</#if>
					          	</small></h5>
					          <p class="pull-right text-right">
					              <strong>￥<#if good.goodsPrice??>
												${good.goodsPrice?string('0.00')}
											</#if> </strong><br>
					              <span class="light">×${good.goodsNum}</span>
					          </p>
				          </a>
				        </div>
				        </#if>
				         <#if (good_index + 1>2)>
					        <div class="order_good none">
					           <a  href="${basePath}/customer/detail-${order.orderId}.html">
						          <img width="50" height="50" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
						          <h5 class="name"><span style="display:block;height:37px;overflow:hidden;">${good.goodsName!''}</span>
						          <small>
					          		<#if (good.specVo??)>
										<#list good.specVo as spec>
											<#if spec_index=0>
												${spec.spec.specName}:${spec.specValueRemark}
											</#if>
										</#list>
									</#if>

						          </small></h5>
						          <p class="pull-right text-right">
						              <strong>￥
							                  <#if good.goodsPrice??>
											   	${good.goodsPrice?string('0.00')}
											  </#if> 
									  </strong><br>
						              <span class="light">×${good.goodsNum}</span>
						          </p>
					          </a>
					        </div>
				        </#if>
			        </#list>
			        <#if (order.goods?size>2)>
			        	<a class="more_order_goods" href="javascript:;">显示其余的${order.goods?size-2}件商品</a>
			        </#if>
			        <div class="order_goods_total">
			          <p>实付:<strong>￥
			          				<#if order.moneyPaid??>
										${order.moneyPaid?string('0.00')}
									 </#if>
							</strong></p>
			          <p>运费:<strong>￥<#if order.shippingFee??> ${(order.shippingFee)?string('0.00')} <#else>0.00</#if></strong></p>
			          <p>共<strong>${order.goods?size}</strong>件商品</p>
			        </div>
			        <div class="order_operate text-right">
			        		<#if order.orderStatus??>
								<#if order.orderStatus=="0"&&order.payId==0>
									<a href="javascript:;" class="btn btn-default" onClick="cancelOrder('${basePath}/customer/cancelorder-myorder-${order.orderId}.html')">取消订单</a>
			          				<a href="${basePath}/orderdetailpay-${order.orderId}.html" class="btn btn-warning">立即支付</a>
								<#elseif (order.orderStatus=="1"| order.orderStatus=="5" | order.orderStatus=="6") >
									<a href="${basePath}/customer/detail-${order.orderId}.html" class="btn btn-default">查看订单</a>
								<#elseif (order.orderStatus=="2")>
		
			          				<a href="javascript:;" class="btn btn-warning" onClick="comfirmgoods('${basePath}/customer/comfirmofgoods-myorder-${order.orderId}.html')">确认收货</a>
								<#elseif (order.orderStatus=="3" & cFlag>0)>
          							<a  href="${basePath}/comment-${order.orderId}.html" class="btn btn-default">评价商品</a>
								<#elseif (order.orderStatus=="3") >
									<a href="${basePath}/customer/detail-${order.orderId}.html" class="btn btn-default">查看订单</a>
								<#elseif (order.orderStatus=="4") >
									<a href="${basePath}/customer/detail-${order.orderId}.html" class="btn btn-default">查看订单</a>
								</#if>
							</#if>
			        </div>
			      </div>
			   </#list>
			<#else>
				<h5 style="color:red;text-align:center;">暂无订单！</h5>
           </#if>
    </div>
    
    <div class="foot">
      <p>由${(mobSiteBasic.technicalSupport)!''}提供技术支持</p>
    </div>
    -->
    <!--引入底部-->
    <#--
	<#include '/common/smart_menu.ftl' />
	-->
	<div class="modal fade" role="dialog" id="cancel_order">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">商城消息</h4>
          </div>
          <div class="modal-body">
        	<div class="dia_intro no_tc pt30" style="height:170px;/*margin-left:20px;*/">
        		<img class="vm mr10" alt="" src="../images/q_mark.png" />
            	<em>取消订单原因:</em><br>
            	
            	<#--<div class="mn_sel"></div>
            	<div class="selCont">
            		<label><input class="vm mr5" name="res" type="radio" value="现在不想买" />现在不想买</label>
            		<label><input class="vm mr5" name="res" type="radio" value="商品价格较贵" />商品价格较贵</label>
            		<label><input class="vm mr5" name="res" type="radio" value="价格波动"/>价格波动</label>
            		<label><input class="vm mr5" name="res" type="radio" value="商品缺货"/>商品缺货</label>
            		<label><input class="vm mr5" name="res" type="radio" value="重复下单"/>重复下单</label>
            		<label><input class="vm mr5" name="res" type="radio" value="添加或删除商品"/>添加或删除商品</label>
            		<label><input class="vm mr5" name="res" type="radio" value="收货人信息有误"/>收货人信息有误</label>
            		<label><input class="vm mr5" name="res" type="radio" value="发票信息有误/发票未开"/>发票信息有误/发票未开</label>
            		<label><input class="vm mr5" name="res" type="radio" value="送货时间过长"/>送货时间过长</label>
            		<label><input class="vm mr5" name="res" id="other_yy" type="radio" value="其他原因" />其他原因</label>
            	</div>
            	<div class="err_tip" style="color:red;width: 350px;text-align:right;margin-top: 10px;display:none;">请选择取消原因!</div>-->
            	<#----> <textarea class="sel_txa" id="sel_txa" style="width:98%;"></textarea>
            	<p id="titlereason" style="color:red;"></p>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="clearmess()">取消</button>
            <button type="button" class="btn btn-primary" onClick="changeUrl();">确定</button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="modal fade" role="dialog" id="confirm_order">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">商城消息</h4>
          </div>
          <div class="modal-body">
            <p>确定确认收货吗？小心钱货两空哦！</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="comfirmGoodsSucc()">确定</button>
          </div>
        </div>
      </div>
    </div>
	
 
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${basePath}/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/jquery.keleyi.js"></script>
    <script src="${basePath}/js/customer/memberorder.js"></script>
    <script src="${basePath}/js/customer/wxforward.js"></script>
    <script>
	  $(function(){
		FastClick.attach(document.body);
		$("#keleyi-menu").keleyi({
		  width: '100%',
		  item_background_color: '#FAFAFA',
		  item_background_color_hover: '#FAFAFA',
		  item_margin: '0',
		  bar_background_color: '#FAFAFA'
		});
		$('.more_order_goods').click(function(){
			$(this).prevAll().show();
			$(this).remove();
		});
	  });
	  
	</script>
  </body>
</html>
