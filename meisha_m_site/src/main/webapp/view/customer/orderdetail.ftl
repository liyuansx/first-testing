<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<#assign basePath=request.contextPath>
    <meta charset="utf-8">
    <meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<meta name="keywords" content="${seo.meteKey}">
    <meta name="description" content="${seo.meteDes}">
    <#if (sys.bsetName)??>
    	<title>订单详情-${(sys.bsetName)!''}</title>
    	<input type="hidden" id="bsetName" value="${(sys.bsetName)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    <#else> 
	    <title>订单详情-${(seo.mete)!''}</title>
	    <input type="hidden" id="bsetName" value="${(seo.mete)!''}">
    	<input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
    </#if>
    <link rel="stylesheet" href="${basePath}/css/normalize.css">
	<link rel="stylesheet" href="${basePath}/css/build/order_detail.css"/>
	<script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]> 
	<script src="${basePath}/js/html5shiv.min.js"></script>
	<script src="${basePath}/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  <!--头部-->
		<div class="m_head" id="top">
			<a href="javascript:;"onclick="javascript:history.go(-1)">
				<img src="${basePath}/img/personInfo/back@2x_02.png" class="back"></img>
			</a>
			<a href="${basePath}/main.html">
				<img src="${basePath}/img/personInfo/shouye@2x.png" class="shouye"></img>
			</a>
			<div class="search_text">查看物流</div>
			<div class="line1"></div>
		</div>
		<#if order??>
			<div class="status_bg">
				<div class="status_detail">
					<p class="order_name">跨境直邮订单</p>
					<p class="order_num"><span>订单号:&nbsp</span><span>
						<#if order.orderNo??>
			                 ${order.orderNo}
			            </#if>
					</span></p>
					<p class="reciver"><span>收货人:
							 <#if order.shippingPerson??>
								${order.shippingPerson}
							 </#if>&nbsp</span>
							<span>
							 <#if order.shippingMobile??>
								${order.shippingMobile?default('')}
							 </#if>
							</span></p>
					<p class="province">&ensp;<#if order.shippingProvince??>${order.shippingProvince?default('')}-</#if>
					<#if order.shippingCity??>${order.shippingCity?default('')}-</#if>
					<#if order.shippingCounty??>${order.shippingCounty?default('')}</#if></p>
					<p class="address">&ensp;<#if order.shippingAddress??>${order.shippingAddress?default('')}</#if></p>
				</div>
			</div>
			<div class="progress">
				<img class="progress_img" src="${basePath}/img/order/dingdan_2.png"/>
				<div class="progress_p">
					<p class="progress_paid">已付款</p>
					<p class="progress_order">麦港出单</p>
					<p class="progress_pick">已拣货</p>
					<p class="progress_deliver">已发货</p>
					<p class="progress_finish">已完成</p>
				</div>
			</div>
			<div class="line"></div>
			<div class="order_detail">
					<div class="order_time">
						<#if order.addTime??>
							 ${order.addTime?string("yyyy-MM-dd HH:mm:ss")}
						</#if>
					</div>
					
					<div class="order_unit">
						<img class="product_img" src="${basePath}/img/order/shoushuang@2x.png"/>
						<div class="prodect_p">
							<p class="product_name">BLACK OPIUM 法国平价护手霜 125ml 法国平价护手霜 125ml</p>
							<p class="product_price">79.00￥<span class="product_price_pre">82.00￥</span><span class="product_num">x1</span></p>
							<div class="refund_btn">退款</div>
						</div>
					</div>  
					<#assign cFlag=0 />	
      				<#list order.goods as good>
			        	<#if good.evaluateFlag== '0'>
			                 <#assign cFlag=cFlag+1 />
			            </#if>
			         </#list>
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
						</#if>
					</#if>
      
		     <div class="order_details_info">
		       <#list order.goods as good>
			      <div class="order_details_good">
			        <a class="order_good_link"  href="${basePath}/item/${good.goodsId}.html">
							          <img width="50" height="50" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
							          <h5 class="name"><span style="display:block;height:32px;overflow:hidden;">${good.goodsName}</span>
							          	<small>
							          			<#if (good.specVo??)>
													<#list good.specVo as spec>
														<#if spec_index=0>
															${spec.spec.specName!''}:${spec.specValueRemark!''}
														</#if>
													</#list>
												</#if>
									     </small>
									  </h5>
				          <p class="pull-right text-right">
				             <strong>￥<#if good.goodsPrice??>
												${good.goodsPrice?string('0.00')}
											</#if> </strong><br>
					              <span class="light">×${good.goodsNum}</span>
				          </p>
			          </a>
			      </div>
		      </#list>
			</div>
			<p class="shipping"><span class="shipping_name">运费</span><span class="shipping_cost"><#if order.shippingFee??> ${(order.shippingFee)?string('0.00')} <#else>0.00</#if>￥</span></p>
			<div style="clear: left;"></div>
			<p class="paid"><span class="pay_name">实付款</span><span class="pay_cost"><#if order.moneyPaid??> ${(order.moneyPaid)?string('0.00')}<#else>0.00</#if>￥</span></p>
		    <div class="footer_btns">
		    	<div class="footer_submit_btn">确认收货</div>
		    	<div class="footer_check_btn">查看物流</div>
		    </div>
	    <#else>
	    	<#--没有订单状态-->
			
	    </#if>
  <#--
    <#if order??>
    <div class="order_details_status">
      <span class="glyphicon glyphicon-list-alt"></span>
      <h4>
      	<#assign cFlag=0 />	
      	<#list order.goods as good>
			        	<#if good.evaluateFlag== '0'>
			                 <#assign cFlag=cFlag+1 />
			            </#if>
			         </#list>
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
								</#if>
							</#if>
      
      		<#--
			等待收货-->
<#--
      </h4>
      <p>订单号：
      		<#if order.orderNo??>
                 ${order.orderNo}
            </#if></p>
      <p>订单金额(含运费)：￥<#if order.moneyPaid??> ${(order.moneyPaid)?string('0.00')}<#else>0.00</#if></p>
      <p>运费：￥<#if order.shippingFee??> ${(order.shippingFee)?string('0.00')} <#else>0.00</#if></p>
    </div>
    
    <div class="order_details_consignee">
    <#if order.orderExpressType??>
    <span class="glyphicon glyphicon-map-marker"></span>
    	<#if order.orderExpressType==1>
	    	<h4>
	        <span>[自提点]<#if order.shippingPerson??>
							${order.shippingPerson}
						</#if></span>
	      </h4>

	      <p id="mapaddress"><#if order.shippingProvince??>${order.shippingProvince?default('')}</#if><#if order.shippingCity??>${order.shippingCity?default('')}</#if><#if order.shippingCounty??>${order.shippingCounty?default('')}</#if><#if order.shippingAddress??>${order.shippingAddress?default('')}</#if></p>
		<#else>
		      <h4>
		        <span>收货人：
						<#if order.shippingPerson??>
							${order.shippingPerson}
						</#if>
				</span>
		        <span class="pull-right">
		                <#if order.shippingMobile??>
							${order.shippingMobile?default('')}
						</#if>
		        </span>
		      </h4>
		      <p>
		      <#if order.shippingProvince??>
		                                    ${order.shippingProvince?default('')}-</#if><#if order.shippingCity??>${order.shippingCity?default('')}-</#if><#if order.shippingCounty??>${order.shippingCounty?default('')}-</#if><#if order.shippingAddress??>${order.shippingAddress?default('')}</#if>
		                                
			  </p>
    	</#if>
    	
    </#if>
     
    </div>
   
   	<#if order.orderStatus??>
   		<#if (order.orderStatus=="2"|order.orderStatus=="3")>
			<#--物流信息-->
			<#--
		    <div class="express_info_simple">
		      <span></span>
		      <a href="http://www.kuaidi100.com/">
		        <h4>物流信息</h4>
		       
		       <#if order.expressno??>
		       		<#list order.expressno as express>
		       			 <p class="green">物流单号:${(express.expressNo)!'   无单号'}</p>
		       		</#list>
		       </#if>
		      </a>
		    </div>
   		</#if>
   	</#if>
    
    <div class="order_details_info">
      <#list order.goods as good>
      <div class="order_details_good">
        <a class="order_good_link"  href="${basePath}/item/${good.goodsId}.html">
				          <img width="50" height="50" title="${(good.goodsName)!''}" alt="${(good.goodsName)!''}" src="${(good.goodsImg)!''}" />
				          <h5 class="name"><span style="display:block;height:32px;overflow:hidden;">${good.goodsName}</span>
				          	<small>
				          			<#if (good.specVo??)>
										<#list good.specVo as spec>
											<#if spec_index=0>
												${spec.spec.specName!''}:${spec.specValueRemark!''}
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
      </#list>
      <div class="order_details_total container-fluid">
        <div class="row">
          <div class="col-xs-6">
            <p class="text-left">运费：</p>
          </div>
          <div class="col-xs-6">
            <p class="text-right"><strong>￥<#if order.shippingFee??> ${(order.shippingFee)?string('0.00')} <#else>0.00</#if></strong></p>
          </div>
        </div>
        <div class="row">
          <div class="col-xs-6">
            <p class="text-left">实付款(含运费)：</p>
          </div>
          <div class="col-xs-6">
            <p class="text-right orange"><strong>￥<#if order.moneyPaid??> ${(order.moneyPaid)?string('0.00')}<#else>0.00</#if></strong></p>
          </div>
        </div>
      </div>
      <div class="number_info">
        <h5>支付及发票信息</h5>
        <p class="light">支付方式：
        		<#if order.payId??>
                      <#if order.payId==2>
							货到付款	                                  	
                      <#else>
                         	在线支付
                      </#if>
                </#if></p>
        <p class="light">成交日期：
        		<#if order.addTime??>
					 ${order.addTime?string("yyyy-MM-dd HH:mm:ss")}
				</#if>
        </p>
          <#if order.orderIntegral??>
              <p class="light">使用积分：

                 ${order.orderIntegral}

              </p>
          </#if>
          <#if order.invoiceType??>
                <p class="light">发票类型：
                            <#if order.invoiceType=='1'>
                                    普通
                            <#elseif order.invoiceType=='2'>
                                     增值
                            </#if>
                </p>
                <p class="light">发票抬头：
                        <#if order.invoiceTitle??>
                             ${order.invoiceTitle?default('')}
                        </#if>
                </p>
                <p class="light">发票内容：
                        <#if order.invoiceContent??>
                             ${order.invoiceContent?default('')}
                        </#if>
                </p>
          </#if>
      </div>
    </div><!-- /order_details_info -->
<#--
    <div class="foot">
      <p>由${(mobSiteBasic.technicalSupport)!''}提供技术支持</p>
    </div><!-- /foot -->
    <#--
    <div class="order_details_ctrl text-right">
      <a href="${basePath}/customer/myorder.html" class="btn btn-default">返回列表</a>
     	<#if order.orderStatus??>
								<#if order.orderStatus=="0">
			          				<a href="javascript:;" class="btn btn-default" onClick="cancelOrder('${basePath}/customer/cancelorder-myorder-${order.orderId}.html')">取消订单</a>
			          				<a href="${basePath}/orderdetailpay-${order.orderId}.html" class="btn btn-warning">立即支付</a>
								<#elseif order.orderStatus=="2">
			          				<a href="javascript:;" class="btn btn-warning" onClick="comfirmgoods('${basePath}/customer/comfirmofgoods-myorder-${order.orderId}.html')">确认收货</a>
								<#elseif (order.orderStatus=="3" & cFlag>0)>
          							<a  href="${basePath}/comment-${order.orderId}.html" class="btn btn-default">评价商品</a>
								</#if>
							</#if>
    </div>
	 <#else>
    	<div style="width: 100%;text-align:center;padding-top:100px;">
		    	<div class="notice2">
		        	订单不存在！<span>
		        </div>
		        <div class="notice3">
		            <strong><span id="time">5</span>秒自动进入<a href="${basePath}/main.html">“首页”</a></strong></span> 
		        </div>
		    </div>
    </#if>
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
     			<#--
            	<textarea class="sel_txa" id="sel_txa" style="width:98%;"></textarea>
            	<p id="titlereason" style="color:red;"></p>
            	<#--<div class="input_tip" style="color:red;text-align:right;margin-top: 10px;display:none;"></div>-->
            <#--</div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="clearmess()">取消</button>
            <button type="button" class="btn btn-primary" onClick="changeUrl();">确定</button>
          </div>
        </div>
      </div>
    </div>
    -->

<#--
    <div class="modal fade" role="dialog" id="confirm_order">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">商城消息</h4>
          </div>
          <div modal-body>
            <p>确定确认收货吗？小心钱货两空哦！</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="comfirmGoodsSucc()">确定</button>
          </div>
        </div>
      </div>
    </div><!-- /.modal -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${basePath}/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${basePath}/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/fastclick.min.js"></script>
    <script src="${basePath}/js/idangerous.swiper-2.1.min.js"></script>
    <script src="${basePath}/js/customer/memberorder.js"></script>
    <script src="${basePath}/js/customer/wxforward.js"></script>
    <script>

	  $(function(){
		FastClick.attach(document.body);
	  });
	  setTimeout(countDown, 1000);

	function countDown(){
		var time = $("#time").text();
		$("#time").text(time - 1);
		if (time == 1) {
			location.href='${basePath}/main.html';
		} else {
			setTimeout(countDown, 1000);
		}
	}

	</script>
  </body>
</html>
