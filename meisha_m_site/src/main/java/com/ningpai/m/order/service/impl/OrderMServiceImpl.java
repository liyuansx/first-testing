/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.order.service.impl;

import com.ningpai.common.kuaidi.KuaiDiUtil;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.freighttemplate.bean.FreightExpress;
import com.ningpai.freighttemplate.bean.FreightTemplate;
import com.ningpai.freighttemplate.dao.ExpressInfoMapper;
import com.ningpai.freighttemplate.dao.FreightExpressMapper;
import com.ningpai.freighttemplate.dao.FreightTemplateMapper;
import com.ningpai.freighttemplate.dao.SysLogisticsCompanyMapper;
import com.ningpai.freighttemplate.service.FreightTemplateService;
import com.ningpai.gift.bean.Gift;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.dao.ProductWareMapper;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.util.CalcStockUtil;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.m.customer.service.CustomerAddressService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.order.bean.OrderAddress;
import com.ningpai.m.order.service.OrderMService;
import com.ningpai.m.order.util.OrderContainerUtil;
import com.ningpai.m.shoppingcart.bean.ShoppingCart;
import com.ningpai.m.shoppingcart.bean.ShoppingCartWareUtil;
import com.ningpai.m.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.m.shoppingcart.util.ShopCartValueUtil;
import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.marketing.bean.*;
import com.ningpai.marketing.dao.PreDiscountMarketingMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.*;
import com.ningpai.order.dao.*;
import com.ningpai.order.service.OrderService;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.system.bean.DeliveryPoint;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.IExpressConfBiz;
import com.ningpai.util.MyLogger;
import com.ningpai.util.UtilDate;
import com.ningpai.wxpay.utils.GetWxOrderno;
import com.ningpai.wxpay.utils.RequestHandlerUtil;
import com.ningpai.wxpay.utils.Sha1Util;
import com.ningpai.wxpay.utils.TenpayUtil;
import com.site.lookup.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单支付service实现
 * 
 * @author NINGPAI-LIH
 * 
 */
@Service("OrderMService")
public class OrderMServiceImpl extends BasicSqlSupport implements OrderMService {

    public Order getThirdOrderPrice(Long thirdId, String codeNo, Long amount,
            Long customerId, String orderCode, Long cityId, Long distinctId,
            List<ShoppingCart> shoplist, boolean typeIdflag) {

        Order order = new Order();
        Map<String, Object> map = shoppingCartService.getEveryThirdPriceMap(
                thirdId, shoplist, distinctId);
        // 订单交易总金额
        BigDecimal sumPrice = BigDecimal.valueOf(Double.valueOf(map.get(
                "sumPrice").toString()));
        // 原总金额
        BigDecimal sumOldPrice = BigDecimal.valueOf(Double.valueOf(map.get(
                "sumOldPrice").toString()));
        // boss交易价格
        BigDecimal bossSumPrice = BigDecimal.valueOf(Double.valueOf(map.get(
                "bossSumPrice").toString()));
        // 总优惠金额
        BigDecimal prePrice = sumOldPrice.subtract(sumPrice);
        // 判断库存
        String stock = map.get("stock").toString();
        // 有商品存在无货返回
        if ("0".equals(stock)) {
            return null;
        }
        if (codeNo != null && !"".equals(codeNo)) {
            Coupon coupon = couponService.selectCouponByCodeNo(codeNo);

            if (coupon != null) {
                if (thirdId.equals(coupon.getBusinessId())) {

                    // 直降
                    if ("1".equals(coupon.getCouponRulesType())) {
                        // 计算交易价格减去金额
                        sumPrice = sumPrice.subtract(coupon
                                .getCouponStraightDown().getDownPrice());
                        // 优惠金额+金额
                        prePrice = prePrice.add(coupon.getCouponStraightDown()
                                .getDownPrice());

                    }
                    // 满减
                    if ("2".equals(coupon.getCouponRulesType())) {
                        // 计算交易价格减去金额
                        sumPrice = sumPrice.subtract(coupon
                                .getCouponFullReduction().getReductionPrice());
                        // 优惠金额+金额
                        prePrice = prePrice.add(coupon.getCouponFullReduction()
                                .getReductionPrice());

                    }
                }
            }
        }
        // boss商品兑换积分

        /* 积分兑换订单金额 */
        if (0 == thirdId) {
            if (null != amount && amount != 0) {

                // 获得当前用户的积分信息
                CustomerPoint customerPoint = couponService
                        .selectCustomerPointByCustomerId(customerId);
                // 积分兑换规则
                PointSet pointSet = this.couponService.selectPointSet();
                // 根据积分兑换规则 计算积分兑换金额

                Double bossjifen = Double.valueOf(bossSumPrice
                        .divide(pointSet.getConsumption())
                        .multiply(BigDecimal.TEN)
                        .setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());

                if (bossjifen < amount) {
                    return null;
                }

                if (null != pointSet) {
                    // 转换类型
                    BigDecimal zhuanhuan = new BigDecimal(amount);
                    // 根据积分兑换规则 计算积分兑换金额
                    BigDecimal disparityPrice = zhuanhuan.multiply(pointSet
                            .getConsumption());
                    disparityPrice = disparityPrice.divide(new BigDecimal(10));
                    // 对兑换处的价格进行四舍五入
                    BigDecimal jiFenDuiHuan = disparityPrice.setScale(2,
                            BigDecimal.ROUND_HALF_UP);
                    // 计算最后订单金额
                    sumPrice = sumPrice.subtract(jiFenDuiHuan);
                    // 对兑换处的价格进行四舍五入
                    sumPrice = sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

                    // 修改会员的积分
                    this.updatePoint(customerId, amount);
                    this.insertExchangeCusmomer(customerId, orderCode, amount,
                            disparityPrice, pointSet.getConsumption());
                }

            }
        }

        // 计算运费
        BigDecimal expressPrice = BigDecimal.ZERO;
        if (typeIdflag) {
            List<ShoppingCart> nobaoyou = shoppingCartService
                    .getNobaoyouShoppingcarts(shoplist);
            expressPrice = shoppingCartService.getEverythirdExpressPrice(
                    thirdId, cityId, nobaoyou);
        }
        if (sumPrice.compareTo(BigDecimal.ZERO) == -1) {
            sumPrice = BigDecimal.valueOf(0);
        }
        // 计算交易价格加上运费
        sumPrice = sumPrice.add(expressPrice);
        order.setOrderPrice(sumPrice);
        order.setOrderOldPrice(sumOldPrice);
        order.setOrderPrePrice(prePrice);
        order.setExpressPrice(expressPrice);

        return order;
    }

    /**
     * 新流程订单提交
     * 
     * @param duiHuanJiFen
     * @param invoiceType
     * @param invoiceTitle
     * @param request
     * @param shoppingCartId
     * @param typeId
     * @param orderAddress
     * @param deliveryPointId
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    @Transactional
    public List<Order> newsubmitOrder(Long duiHuanJiFen, String invoiceType,
            String invoiceTitle, HttpServletRequest request,
            Long[] shoppingCartId, Long typeId, OrderAddress orderAddress,
            Long deliveryPointId) throws UnsupportedEncodingException {
        // 优惠劵码
        String codeNo = request.getParameter("codeNo");
        Long custAddress;
        if (StringUtils.isNotEmpty(request.getParameter("addressId"))) {
            custAddress = Long.parseLong(request.getParameter("addressId"));
        } else {
            return null;
        }
        Long ch_pay = 1L;
        if (StringUtils.isNotEmpty(request.getParameter("ch_pay"))) {
            ch_pay = Long.valueOf(request.getParameter("ch_pay"));
        }
        // 第三方支付方式目前写死只支持在线支付
        Long ch_paythird = 1L;
        // if(StringUtils.isNotEmpty(request.getParameter("ch_paythird"))){
        // ch_paythird= Long.valueOf(request.getParameter("ch_paythird"));
        // }

        // 当前登录成功的会员
        Long customerId = (Long) request.getSession().getAttribute(
                CustomerConstantStr.CUSTOMERID);
        CustomerAddress ca = null;
        Long distinctId = null;

        // 查询收货地址
        if (customerId != null) {

            ca = addressService.queryDefaultAddr(customerId);
            if (ca != null && ca.getDistrict() != null) {
                distinctId = ca.getDistrict().getDistrictId();
            }
        }
        List<Object> list = new ArrayList<Object>();
        // 仓库信息
        List<CalcStockUtil> calcStockUtils = new ArrayList<CalcStockUtil>();
        // 获取所有所有选中商品信息
        List<ShoppingCart> cartlist = shoppingCartMapper
                .shopCartListByIds(Arrays.asList(shoppingCartId));

        if (CollectionUtils.isEmpty(cartlist)) {
            return null;
        }
        Map<Long, Object> thirdIdMap = new HashMap<>();
        for (int i = 0; i < cartlist.size(); i++) {
            if (cartlist.get(i).getFitId() == null) {
                thirdIdMap.put(cartlist.get(i).getThirdId(), "");
            } else {

                // 如果商品是套装
                GoodsGroupVo goodsGroupVo = goodsGroupService
                        .queryVoByPrimaryKey(((ShoppingCart) cartlist.get(i))
                                .getFitId());
                if (null != goodsGroupVo) {

                    thirdIdMap.put(goodsGroupVo.getThirdId(), "");
                    cartlist.get(i).setGoodsGroupVo(goodsGroupVo);
                    cartlist.get(i).setThirdId(goodsGroupVo.getThirdId());
                }
            }
        }

        // 主订单号
        String orderOldCode = UtilDate.mathString(new Date());
        List<Order> maps = new ArrayList<Order>();

        for (Long thirdId : thirdIdMap.keySet()) {
            List<Coupon> coupons = new ArrayList<Coupon>();
            // 查询购买的商品
            List<OrderGoods> oglist = new ArrayList<OrderGoods>();
            Order order = new Order();
            if (cartlist != null && !cartlist.isEmpty()) {

                for (int i = 0; i < cartlist.size(); i++) {
                    if (cartlist.get(i).getFitId() != null) {
                        OrderGoods og = null;
                        // 获取此套装下的所有货品
                        List<GoodsProductVo> goodsProducts = goodsProductMapper
                                .queryDetailByGroupId(cartlist.get(i)
                                        .getFitId());
                        for (int j = 0; j < goodsProducts.size(); j++) {
                            CalcStockUtil calcStockUtil = new CalcStockUtil();
                            og = new OrderGoods();
                            og.setGoodsInfoNum(cartlist.get(i).getGoodsNum());
                            calcStockUtil.setIsThird(goodsProducts.get(j)
                                    .getIsThird());
                            calcStockUtil.setDistinctId(distinctId);
                            // 商品id
                            calcStockUtil.setProductId(goodsProducts.get(j)
                                    .getGoodsInfoId());
                            // 减去库存
                            calcStockUtil.setStock(Integer.parseInt(og
                                    .getGoodsInfoNum().toString()));
                            calcStockUtils.add(calcStockUtil);
                            og.setDelFlag("0");
                            og.setBuyTime(new Date());
                            og.setEvaluateFlag("0");
                            og.setGoodsInfoPrice(BigDecimal.valueOf(0));
                            // 原始价格
                            og.setGoodsInfoOldPrice(goodsProducts.get(j)
                                    .getGoodsInfoPreferPrice());
                            og.setGoodsInfoId(cartlist.get(i).getGoodsGroupVo()
                                    .getProductList().get(j).getProductDetail()
                                    .getGoodsInfoId());
                            og.setGoodsId(cartlist.get(i).getGoodsGroupVo()
                                    .getProductList().get(j).getProductDetail()
                                    .getGoodsId());
                            // 最终售价
                            og.setGoodsInfoPrice(og.getGoodsInfoOldPrice()
                                    .subtract(
                                            cartlist.get(i).getGoodsGroupVo()
                                                    .getGroupPreferamount()));
                            // 小计金额
                            og.setGoodsInfoSumPrice(og.getGoodsInfoPrice()
                                    .multiply(
                                            BigDecimal.valueOf(cartlist.get(i)
                                                    .getGoodsNum())));
                            oglist.add(og);

                        }
                    } else {

                        // 查询商品详细
                        cartlist.get(i)
                                .setGoodsDetailBean(
                                        siteGoodsProductService
                                                .queryDetailBeanByProductId(
                                                        ((ShoppingCart) cartlist
                                                                .get(i))
                                                                .getGoodsInfoId(),
                                                        ((ShoppingCart) cartlist
                                                                .get(i))
                                                                .getDistinctId()));
                        // 查询购物车里选择的促销信息
                        cartlist.get(i).setMarketing(
                                marketService.marketingDetail(cartlist.get(i)
                                        .getMarketingActivityId(), cartlist
                                        .get(i).getGoodsInfoId()));

                        if (thirdId.equals(cartlist.get(i).getGoodsDetailBean()
                                .getProductVo().getThirdId())) {

                            if (thirdId == 0 && distinctId != null) {
                                // 查询库存
                                ProductWare productWare = productWareService
                                        .queryProductWareByProductIdAndDistinctId(
                                                cartlist.get(i)
                                                        .getGoodsDetailBean()
                                                        .getProductVo()
                                                        .getGoodsInfoId(),
                                                distinctId);
                                if (productWare != null) {
                                    // 设置商品库存
                                    ((ShoppingCart) cartlist.get(i))
                                            .getGoodsDetailBean()
                                            .getProductVo()
                                            .setGoodsInfoStock(
                                                    productWare.getWareStock());
                                    ((ShoppingCart) cartlist.get(i))
                                            .getGoodsDetailBean()
                                            .getProductVo()
                                            .setGoodsInfoPreferPrice(
                                                    productWare.getWarePrice());
                                    if (productWare.getWareStock() <= 0) {
                                        return null;
                                    }

                                }
                            }
                            OrderGoods og = new OrderGoods();
                            og.setGoodsInfoNum(cartlist.get(i).getGoodsNum());
                            og.setDelFlag("0");
                            og.setBuyTime(new Date());
                            // 设置收货地区
                            og.setDistinctId(distinctId);
                            og.setEvaluateFlag("0");
                            // 设置商品原价
                            og.setGoodsInfoOldPrice(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoPreferPrice());
                            og.setGoodsInfoPrice(og.getGoodsInfoOldPrice());

                            // 设置货品总价格（数量*价格）
                            og.setGoodsInfoSumPrice(og.getGoodsInfoOldPrice()
                                    .multiply(
                                            BigDecimal.valueOf(cartlist.get(i)
                                                    .getGoodsNum())));
                            // 设置货品Id
                            og.setGoodsInfoId(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoId());
                            // 设置商品ID
                            og.setGoodsId(cartlist.get(i).getGoodsDetailBean()
                                    .getProductVo().getGoodsId());
                            // 折扣促销id
                            og.setGoodsMarketingId(cartlist.get(i)
                                    .getMarketingId());
                            // 其他促销id
                            og.setGoodsActiveMarketingId(cartlist.get(i)
                                    .getMarketingActivityId());
                            Marketing market = cartlist.get(i).getMarketing();
                            CalcStockUtil calcStockUtil = new CalcStockUtil();
                            calcStockUtil.setIsThird(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getIsThird());
                            calcStockUtil.setDistinctId(distinctId);
                            // 商品id
                            calcStockUtil.setProductId(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoId());
                            // 减去库存
                            calcStockUtil.setStock(Integer.parseInt(og
                                    .getGoodsInfoNum().toString()));
                            calcStockUtils.add(calcStockUtil);

                            oglist.add(og);
                        }
                    }
                }

                // 封装物流信息
                OrderExpress oe = new OrderExpress();
                // 当为在线支付且是上门自提时运费是没有的
                boolean typeIdflag = true;
                if (typeId != null && typeId == 1
                        && "0".equals(thirdId.toString()) && ch_pay != null
                        && ch_pay == 1) {
                    typeIdflag = false;
                    // 上门自提
                    oe.setExpressTypeId(1L);
                    // 上门自提地址
                    DeliveryPoint point = deliveryPointService
                            .getDeliveryPoint(deliveryPointId);
                    order.setShoppingAddrId(deliveryPointId);
                    order.setShippingProvince(point.getTemp1());
                    order.setShippingCity(point.getTemp2());
                    order.setShippingCounty(point.getTemp3());
                    order.setShippingAddress(point.getAddress());
                    order.setShippingPerson(point.getName());
                    // 设置配送方式
                    order.setOrderExpressType("1");
                } else {

                    typeIdflag = true;
                    // 设置配送方式
                    order.setOrderExpressType("0");
                    if (orderAddress.getAddressName() == null
                            || orderAddress.getAddressPhone() == null
                            || orderAddress.getAddressDetail() == null
                            || orderAddress.getAddressDetailInfo() == null) {

                        order.setShoppingAddrId(custAddress);
                        order.setShippingProvince(ca.getProvince()
                                .getProvinceName());
                        order.setShippingCity(ca.getCity().getCityName());
                        order.setShippingCounty(ca.getDistrict()
                                .getDistrictName());
                        order.setShippingAddress(ca.getAddressDetail());
                        order.setShippingPerson(ca.getAddressName());
                        order.setShippingPhone(ca.getAddressPhone());
                        order.setShippingMobile(ca.getAddressMoblie());

                        order.setShippingPostcode(ca.getAddressZip());
                    } else {
                        // 微信收货地址
                        orderAddress.setProviceFirstStageName(new String(
                                orderAddress.getProviceFirstStageName()
                                        .getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        orderAddress.setAddressCitySecondStageName(new String(
                                orderAddress.getAddressCitySecondStageName()
                                        .getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        order.setShippingProvince(orderAddress
                                .getProviceFirstStageName().substring(
                                        0,
                                        orderAddress.getProviceFirstStageName()
                                                .length() - 1));
                        order.setShippingCity(orderAddress
                                .getAddressCitySecondStageName()
                                .substring(
                                        0,
                                        orderAddress
                                                .getAddressCitySecondStageName()
                                                .length() - 1));
                        order.setShippingCounty(new String(orderAddress
                                .getAddressCountiesThirdStageName().getBytes(
                                        "ISO-8859-1"), ConstantUtil.UTF));
                        order.setShippingAddress(new String(orderAddress
                                .getAddressDetailInfo().getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        order.setShippingMobile(new String(orderAddress
                                .getAddressPhone().getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        order.setShippingPerson(new String(orderAddress
                                .getAddressName().getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                    }

                    // 查询物流模板信息 根据thirdId 查询默认的模板
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("freightIsDefault", "1");
                    paramMap.put("freightThirdId", thirdId);
                    // 获取默认模板
                    FreightTemplate ft = freightTemplateMapper
                            .selectFreightTemplateSubOrder(paramMap);
                    if (ft == null) {
                        return null;
                    }
                    // 配送方式名称
                    oe.setExpressTypeName(ft.getFreightTemplateName());
                    Long comId = null;
                    String comName = "";
                    // 获取物流信息
                    FreightExpress fe = freightExpressMapper
                            .selectTemplateExpress(ft.getFreightTemplateId())
                            .get(0);
                    if (thirdId.equals(0L)) {

                        fe.setSysLogisticsCompany(sysLogisticsCompanyMapper
                                .selectCompanyById(fe.getLogComId()));
                        comId = fe.getSysLogisticsCompany().getLogComId();
                        comName = fe.getSysLogisticsCompany().getName();
                    } else {

                        comId = expressInfoMapperThird.selectByshoreExpId(
                                fe.getLogComId()).getShoreExpId();
                        comName = expressInfoMapperThird.selectByshoreExpId(
                                fe.getLogComId()).getExpName();
                    }

                    if (fe != null) {
                        oe.setExpressId(comId);
                        oe.setExpressName(comName);
                    }
                    oe.setDelFlag("0");
                    // 配送方式 0 快递配送 1 上门自提
                    oe.setExpressTypeId(0L);

                }

                // 随机数
                int randomNum = (int) (Math.random() * 100);
                // 子订单号
                String orderCode = UtilDate.mathString(new Date()) + randomNum;
                order.setOrderCode(orderCode);
                order.setOrderOldCode(orderOldCode);
                // 插入订单商品
                order.setOrderGoodsList(oglist);

                // 发票信息String invoiceType,String invoiceTitle,
                if (!"0".equals(invoiceType)) {
                    // 普通发票
                    order.setInvoiceType("1");
                    // 发票内容
                    order.setInvoiceContent(invoiceType);
                    if (invoiceTitle != null) {
                        // 发票抬头
                        order.setInvoiceTitle(invoiceTitle);
                    }
                } else {
                    order.setInvoiceType("0");
                }

                // order.setInvoiceContent(invoiceContent);
                order.setDelFlag("0");

                // 设置为手机订单 ，如果是微信，则设置为微信订单
                order.setOrderMType("2");

                // 订单状态
                order.setOrderStatus("0");

                Order orderpirce = getThirdOrderPrice(thirdId, codeNo,
                        duiHuanJiFen, customerId, orderCode, ca.getCity()
                                .getCityId(), ca.getDistrict().getDistrictId(),
                        cartlist, typeIdflag);
                if (orderpirce == null) {
                    return null;
                }
                // 订单兑换积分
                order.setOrderIntegral(duiHuanJiFen);
                // 总金额
                order.setOrderPrice(orderpirce.getOrderPrice());
                // 原始总额
                order.setOrderOldPrice(orderpirce.getOrderOldPrice());
                // 总优惠金额
                order.setOrderPrePrice(orderpirce.getOrderPrePrice());
                // 运费
                order.setExpressPrice(orderpirce.getExpressPrice());
                order.setBusinessId(Long.valueOf(thirdId.toString()));
                // 如果为2，表示货到付款
                if (thirdId == 0) {
                    if (ch_pay == 2) {
                        // 货到付款
                        order.setOrderLinePay("0");
                    } else {

                        // 在线支付
                        order.setOrderLinePay("1");
                    }
                    // 支付方式
                    order.setPayId(ch_pay);
                } else {
                    // 第三方支付方式
                    order.setPayId(ch_paythird);
                }
                // 订单使用的优惠券
                order.setCouponNo(codeNo);
                order.setCustomerId(customerId);
                order.setCreateTime(new Date());

                // 插入订单主表
                int f = orderService.insertOrder(order);
                // 插叙返回的ID
                if (f == 1) {

                    Long orderId = orderService.selectLastId();

                    // 修改优惠券已经使用
                    couponNoService.updateCodeIsUse(codeNo, orderCode);
                    order.setOrderId(orderId);
                    maps.add(order);
                    // 插入物流信息
                    oe.setOrderId(orderId);
                    orderExpressMapper.insertOrderExpress(oe);
                    // 循环设置货品级联ID信息
                    if (oglist != null && !oglist.isEmpty()) {
                        for (int i = 0; i < oglist.size(); i++) {
                            oglist.get(i).setOrderId(orderId);
                            // 插入货品
                            orderGoodsMapper
                                    .insertOrderGoodsInfo(oglist.get(i));
                            // 获取货品级联ID
                            Long orderGoodsId = orderGoodsMapper.selectLastId();
                            if ("1".equals(oglist.get(i).getHaveCouponStatus())) {
                                List<OrderGoodsInfoCoupon> clist = oglist
                                        .get(i).getOrderGoodsInfoCouponList();
                                if (clist != null && !clist.isEmpty()) {
                                    for (int j = 0; j < clist.size(); j++) {
                                        clist.get(j).setOrderGoodsId(
                                                orderGoodsId);
                                    }
                                    // 批量插入订单商品的赠送优惠券信息
                                    orderGoodsInfoCouponMapper
                                            .insertOrderInfoCoupon(clist);
                                }
                            }

                            if ("1".equals(oglist.get(i).getHaveGiftStatus())) {
                                List<OrderGoodsInfoGift> glist = oglist.get(i)
                                        .getOrderGoodsInfoGiftList();
                                if (glist != null && !glist.isEmpty()) {
                                    for (int j = 0; j < glist.size(); j++) {
                                        glist.get(j).setOrderGoodsId(
                                                orderGoodsId);
                                    }
                                    // 批量插入订单商品的赠送赠品信息
                                    orderGoodsInfoGiftMapper
                                            .insertOrderInfoGift(glist);
                                }
                            }

                        }
                    }
                }
            }

        }

        // 修改所有购买商品为已经删除
        shoppingCartService.deleteShoppingCartByIds(request, shoppingCartId);
        try {
            goodsProductService.minStock(calcStockUtils);
            return maps;
        } finally {

        }

    }

    @Override
    @Transactional
    public List<Order> submitOrder(Long duiHuanJiFen, String invoiceType,
            String invoiceTitle, HttpServletRequest request,
            Long[] shoppingCartId, String codeNo, Long custAddress,
            Long ch_pay, Long ch_express, Long[] marketingId, Long[] thirdIds,
            ShoppingCartWareUtil cartWareUtil, String customerRemark,
            Long typeId, OrderAddress orderAddress, Long deliveryPointId)
            throws UnsupportedEncodingException {
        List<ShoppingCart> cartlist1 = shoppingCartService.searchByProduct(
                request, shoppingCartId);
        List<Object> list = new ArrayList<Object>();
        List<CalcStockUtil> calcStockUtils = new ArrayList<CalcStockUtil>();

        if (cartlist1 != null && !cartlist1.isEmpty()) {
            for (ShoppingCart sc : cartlist1) {
                if (sc.getFitId() == null) {
                    list.add(sc.getGoodsDetailBean().getProductVo()
                            .getThirdId());
                } else {
                    if (sc.getGoodsGroupVo().getIsThird() != null) {

                        list.add(Long.parseLong(sc.getGoodsGroupVo()
                                .getIsThird()));
                    } else {
                        list.add(0);
                    }
                }
            }

            for (int q = 0; q < list.size() - 1; q++) {
                for (int p = list.size() - 1; p > q; p--) {
                    if (list.get(p).equals(list.get(q))) {
                        list.remove(p);
                    }
                }

            }
        }
        // 主订单号
        String orderOldCode = UtilDate.mathString(new Date());
        List<Order> maps = new ArrayList<Order>();

        for (Object thirdId : list) {
            // 购物车内所有的商家id
            List<Gift> gifts = new ArrayList<Gift>();
            List<Coupon> coupons = new ArrayList<Coupon>();
            // 查询购买的商品
            List<OrderGoods> oglist = new ArrayList<OrderGoods>();
            Order order = new Order();
            List<ShoppingCart> cartlist = shoppingCartService.searchByProduct(
                    request, shoppingCartId);
            if (cartlist != null && !cartlist.isEmpty()) {

                for (int i = 0; i < cartlist.size(); i++) {
                    if (cartlist.get(i).getFitId() != null) {
                        OrderGoods og = null;
                        for (int j = 0; j < cartlist.get(i).getGoodsGroupVo()
                                .getProductList().size(); j++) {
                            og = new OrderGoods();
                            og.setGoodsInfoNum(cartlist.get(i).getGoodsNum());
                            og.setDelFlag("0");
                            og.setBuyTime(new Date());
                            og.setEvaluateFlag("0");
                            og.setGoodsInfoPrice(BigDecimal.valueOf(0));
                            if (j > 0) {
                                og.setGoodsInfoOldPrice(BigDecimal.valueOf(0));
                            } else {
                                og.setGoodsInfoOldPrice(cartlist.get(i)
                                        .getGoodsGroupVo()
                                        .getGroupPreferamount());
                            }
                            og.setGoodsInfoId(cartlist.get(i).getGoodsGroupVo()
                                    .getProductList().get(j).getProductDetail()
                                    .getGoodsInfoId());
                            og.setGoodsId(cartlist.get(i).getGoodsGroupVo()
                                    .getProductList().get(j).getProductDetail()
                                    .getGoodsId());
                            og.setGoodsInfoPrice(og.getGoodsInfoOldPrice());
                            // 最终售价
                            og.setGoodsInfoPrice(og.getGoodsInfoOldPrice());
                            // 小计金额
                            og.setGoodsInfoSumPrice(og.getGoodsInfoPrice()
                                    .multiply(
                                            BigDecimal.valueOf(cartlist.get(i)
                                                    .getGoodsNum())));
                            oglist.add(og);

                        }
                    } else {

                        if (thirdId.equals(cartlist.get(i).getGoodsDetailBean()
                                .getProductVo().getThirdId())) {
                            // 查询库存
                            ProductWare productWare = productWareService
                                    .queryProductWareByProductIdAndDistinctId(
                                            cartlist.get(i)
                                                    .getGoodsDetailBean()
                                                    .getProductVo()
                                                    .getGoodsInfoId(),
                                            cartWareUtil.getDistrictId());
                            if (productWare != null) {
                                // 设置商品库存
                                ((ShoppingCart) cartlist.get(i))
                                        .getGoodsDetailBean()
                                        .getProductVo()
                                        .setGoodsInfoStock(
                                                productWare.getWareStock());
                                ((ShoppingCart) cartlist.get(i))
                                        .getGoodsDetailBean()
                                        .getProductVo()
                                        .setGoodsInfoPreferPrice(
                                                productWare.getWarePrice());
                            } else {
                                // 如果没有，则设置库存为0
                                ((ShoppingCart) cartlist.get(i))
                                        .getGoodsDetailBean()
                                        .getProductVo()
                                        .setGoodsInfoStock(
                                                ShopCartValueUtil.WARECOUNT);
                            }
                            OrderGoods og = new OrderGoods();
                            og.setGoodsInfoNum(cartlist.get(i).getGoodsNum());
                            og.setDelFlag("0");
                            og.setBuyTime(new Date());
                            og.setDistinctId(cartWareUtil.getDistrictId());
                            og.setEvaluateFlag("0");
                            og.setGoodsInfoOldPrice(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoPreferPrice());
                            og.setGoodsInfoId(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoId());
                            og.setGoodsId(cartlist.get(i).getGoodsDetailBean()
                                    .getProductVo().getGoodsId());
                            og.setGoodsMarketingId(cartlist.get(i)
                                    .getMarketingId());
                            og.setGoodsActiveMarketingId(cartlist.get(i)
                                    .getMarketingActivityId());
                            Marketing market = cartlist.get(i).getMarketing();
                            CalcStockUtil calcStockUtil = new CalcStockUtil();
                            calcStockUtil.setIsThird(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getIsThird());
                            calcStockUtil.setDistinctId(cartWareUtil
                                    .getDistrictId());
                            // 商品id
                            calcStockUtil.setProductId(cartlist.get(i)
                                    .getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoId());
                            // 减去库存
                            calcStockUtil.setStock(Integer.parseInt(og
                                    .getGoodsInfoNum().toString()));
                            calcStockUtils.add(calcStockUtil);
                            // 货品赠送的优惠券
                            List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = new ArrayList<OrderGoodsInfoCoupon>();

                            // 货品赠送的赠品
                            List<OrderGoodsInfoGift> orderGoodsInfoGiftList = new ArrayList<OrderGoodsInfoGift>();
                            if (market != null) {
                                // 促销
                                og.setGoodsMarketingId(market.getMarketingId());
                                // 直降
                                if ("1".equals(market.getCodexType())) {
                                    PriceOffMarketing pm = market
                                            .getPriceOffMarketing();
                                    // 数量
                                    BigDecimal num = BigDecimal.valueOf(og
                                            .getGoodsInfoNum());
                                    // 优惠价格
                                    og.setGoodsCouponPrice(BigDecimal
                                            .valueOf(0));
                                    if (og.getGoodsInfoOldPrice()
                                            .subtract(pm.getOffValue())
                                            .signum() <= 0) {
                                        og.setGoodsInfoOldPrice(new BigDecimal(
                                                "0.01"));
                                    } else {
                                        og.setGoodsInfoOldPrice(og
                                                .getGoodsInfoOldPrice()
                                                .subtract(pm.getOffValue()));
                                    }
                                    // 最终售价
                                    og.setGoodsInfoPrice(og
                                            .getGoodsInfoOldPrice());
                                    // 小计金额
                                    og.setGoodsInfoSumPrice(og
                                            .getGoodsInfoPrice().multiply(num));
                                    og.setHaveCouponStatus("0");
                                    og.setHaveGiftStatus("0");
                                }

                                // 买折扣 折扣多少
                                if ("4".equals(market.getCodexType())) {
                                    DiscountMarketing dm = market
                                            .getDiscountMarketing();
                                    // 数量
                                    BigDecimal num = BigDecimal.valueOf(og
                                            .getGoodsInfoNum());
                                    BigDecimal a = BigDecimal.valueOf(1);
                                    // 总优惠 优惠金额=原价*折扣*数量
                                    og.setGoodsCouponPrice(BigDecimal
                                            .valueOf(0));
                                    // 最终售价 = 原价-原价*(1-折扣)
                                    og.setGoodsInfoOldPrice(og
                                            .getGoodsInfoOldPrice()
                                            .subtract(
                                                    og.getGoodsInfoOldPrice()
                                                            .multiply(
                                                                    a.subtract(dm
                                                                            .getDiscountValue()))));
                                    if ((og.getGoodsInfoOldPrice()
                                            .subtract(new BigDecimal(0.01)))
                                            .signum() <= 0) {
                                        og.setGoodsInfoOldPrice(new BigDecimal(
                                                0.01));
                                    }
                                    og.setGoodsInfoPrice(og
                                            .getGoodsInfoOldPrice());

                                    // 小计金额
                                    og.setGoodsInfoSumPrice(og
                                            .getGoodsInfoPrice().multiply(num));
                                    og.setHaveCouponStatus("0");
                                    og.setHaveGiftStatus("0");
                                }
                                // 满减优惠
                                if ("5".equals(market.getCodexType())) {
                                    FullbuyReduceMarketing fm = market
                                            .getFullbuyReduceMarketing();

                                    // 数量
                                    BigDecimal num = BigDecimal.valueOf(og
                                            .getGoodsInfoNum());
                                    // 最终售价
                                    og.setGoodsInfoPrice(og
                                            .getGoodsInfoOldPrice());
                                    if (og.getGoodsInfoPrice().multiply(num)
                                            .subtract(fm.getFullPrice())
                                            .longValue() >= 0) {
                                        if (og.getGoodsInfoPrice()
                                                .multiply(num)
                                                .subtract(fm.getReducePrice())
                                                .signum() > 0) {
                                            // 优惠金额
                                            og.setGoodsCouponPrice(fm
                                                    .getReducePrice());
                                            // 小计金额
                                            og.setGoodsInfoSumPrice(og
                                                    .getGoodsInfoPrice()
                                                    .multiply(num)
                                                    .subtract(
                                                            fm.getReducePrice()));
                                        } else {
                                            // 优惠金额
                                            og.setGoodsCouponPrice(og
                                                    .getGoodsInfoPrice()
                                                    .multiply(num)
                                                    .subtract(
                                                            new BigDecimal(0.01)));
                                            og.setGoodsInfoSumPrice(og
                                                    .getGoodsInfoPrice()
                                                    .multiply(num));
                                        }
                                    } else {
                                        // 优惠金额
                                        og.setGoodsCouponPrice(BigDecimal
                                                .valueOf(0));
                                        // 小计金额
                                        og.setGoodsInfoSumPrice(og
                                                .getGoodsInfoPrice().multiply(
                                                        num));
                                    }

                                    og.setHaveCouponStatus("0");
                                    og.setHaveGiftStatus("0");

                                }

                                // 满折
                                if ("8".equals(market.getCodexType())) {
                                    FullbuyDiscountMarketing fdm = market
                                            .getFullbuyDiscountMarketing();
                                    // 数量
                                    BigDecimal num = BigDecimal.valueOf(og
                                            .getGoodsInfoNum());
                                    BigDecimal a = BigDecimal.valueOf(1);
                                    // 最终售价 原价-（原价 * 折扣）
                                    BigDecimal sums = og.getGoodsInfoOldPrice()
                                            .multiply(num);
                                    if (sums.subtract(fdm.getFullPrice())
                                            .longValue() >= 0) {
                                        // 价格
                                        og.setGoodsInfoPrice(og
                                                .getGoodsInfoOldPrice());
                                        BigDecimal sumPrice = og
                                                .getGoodsInfoPrice().multiply(
                                                        num);
                                        BigDecimal yhprice = (og
                                                .getGoodsInfoPrice()
                                                .multiply(num)).multiply(a
                                                .subtract(fdm
                                                        .getFullbuyDiscount()));
                                        yhprice = yhprice.setScale(2,
                                                BigDecimal.ROUND_HALF_UP);
                                        // 小计
                                        if ((sumPrice.subtract(yhprice)
                                                .subtract(new BigDecimal("0.01")))
                                                .signum() <= 0) {
                                            og.setGoodsInfoSumPrice(new BigDecimal(
                                                    "0.01"));
                                            og.setGoodsCouponPrice(og
                                                    .getGoodsInfoPrice()
                                                    .multiply(num)
                                                    .subtract(
                                                            new BigDecimal(0.01)));
                                        } else {
                                            og.setGoodsInfoSumPrice(sumPrice
                                                    .subtract(yhprice));
                                            // 优惠金额 优惠金额 = (（1-折扣）*总价格)
                                            og.setGoodsCouponPrice(yhprice);
                                        }
                                    } else {
                                        // 价格
                                        og.setGoodsInfoPrice(og
                                                .getGoodsInfoOldPrice());
                                        // 小计
                                        og.setGoodsInfoSumPrice(og
                                                .getGoodsInfoPrice().multiply(
                                                        num));

                                        // 优惠金额
                                        og.setGoodsCouponPrice(BigDecimal
                                                .valueOf(0));
                                    }

                                    og.setHaveCouponStatus("0");
                                    og.setHaveGiftStatus("0");

                                }

                                // 满赠赠品
                                if (market.getFullbuyPresentMarketing() != null) {
                                    if ("6".equals(market.getCodexType())) {
                                        // 数量
                                        BigDecimal num = BigDecimal.valueOf(og
                                                .getGoodsInfoNum());
                                        BigDecimal bd = BigDecimal.valueOf(0);
                                        // 优惠价格
                                        og.setGoodsCouponPrice(bd);
                                        // 最终售价
                                        og.setGoodsInfoPrice(og
                                                .getGoodsInfoOldPrice()
                                                .subtract(bd));
                                        // 小计金额
                                        og.setGoodsInfoSumPrice(og
                                                .getGoodsInfoPrice().multiply(
                                                        num));

                                    }
                                    if (og.getGoodsInfoSumPrice()
                                            .subtract(
                                                    market.getFullbuyPresentMarketing()
                                                            .getFullPrice())
                                            .longValue() >= 0) {
                                        List<Gift> giftlist = market
                                                .getGiftList();
                                        if (giftlist != null
                                                && !giftlist.isEmpty()) {
                                            og.setHaveGiftStatus("1");
                                            for (Gift gift : giftlist) {
                                                OrderGoodsInfoGift ogi = new OrderGoodsInfoGift();
                                                ogi.setGiftId(gift.getGiftId());
                                                orderGoodsInfoGiftList.add(ogi);
                                            }
                                        } else {
                                            og.setHaveGiftStatus("0");
                                        }
                                    } else {
                                        og.setHaveGiftStatus("0");
                                    }
                                    og.setHaveCouponStatus("0");

                                }
                                // 满赠优惠券
                                if (market.getFullbuyCouponMarketing() != null) {
                                    if ("7".equals(market.getCodexType())) {
                                        // 数量
                                        BigDecimal num = BigDecimal.valueOf(og
                                                .getGoodsInfoNum());
                                        BigDecimal bd = BigDecimal.valueOf(0);
                                        // 优惠价格
                                        og.setGoodsCouponPrice(bd);
                                        // 最终售价
                                        og.setGoodsInfoPrice(og
                                                .getGoodsInfoOldPrice()
                                                .subtract(bd));
                                        // 小计金额
                                        og.setGoodsInfoSumPrice(og
                                                .getGoodsInfoPrice().multiply(
                                                        num));

                                    }
                                    if (og.getGoodsInfoSumPrice()
                                            .subtract(
                                                    market.getFullbuyCouponMarketing()
                                                            .getFullPrice())
                                            .longValue() >= 0) {
                                        // 查询所有此促销的赠品级联list
                                        List<Coupon> clist = market
                                                .getCouponList();

                                        if (clist != null && !clist.isEmpty()) {
                                            og.setHaveCouponStatus("1");
                                            for (Coupon coupon : clist) {
                                                OrderGoodsInfoCoupon oic = new OrderGoodsInfoCoupon();
                                                oic.setCouponId(coupon
                                                        .getCouponId());
                                                Coupon cp = couponService
                                                        .selectOneCouponNoByCouponIdAndUpdateNoIsGet(
                                                                coupon.getCouponId(),
                                                                (Long) request
                                                                        .getSession()
                                                                        .getAttribute(
                                                                                "customerId"));
                                                oic.setCouponNo(cp.getCodeNo());
                                                orderGoodsInfoCouponList
                                                        .add(oic);
                                            }
                                        } else {
                                            og.setHaveCouponStatus("0");
                                        }

                                    } else {
                                        og.setHaveCouponStatus("0");
                                    }
                                    og.setHaveGiftStatus("0");

                                }
                                // 买送赠品
                                if (market.getGiftList() != null
                                        && market.getFullbuyPresentMarketing() == null) {

                                    if ("2".equals(market.getCodexType())) {
                                        // 数量
                                        BigDecimal num = BigDecimal.valueOf(og
                                                .getGoodsInfoNum());

                                        BigDecimal bd = BigDecimal.valueOf(0);
                                        // 优惠价格
                                        og.setGoodsCouponPrice(bd);
                                        // 最终售价
                                        og.setGoodsInfoPrice(og
                                                .getGoodsInfoOldPrice()
                                                .subtract(bd));
                                        // 小计金额
                                        og.setGoodsInfoSumPrice(og
                                                .getGoodsInfoPrice().multiply(
                                                        num));

                                    }
                                    List<Gift> giftlist = market.getGiftList();
                                    if (giftlist != null && !giftlist.isEmpty()) {
                                        og.setHaveGiftStatus("1");
                                        for (Gift gift : giftlist) {
                                            OrderGoodsInfoGift ogi = new OrderGoodsInfoGift();
                                            ogi.setGiftId(gift.getGiftId());
                                            orderGoodsInfoGiftList.add(ogi);
                                        }
                                    } else {
                                        og.setHaveGiftStatus("0");
                                    }
                                    og.setHaveCouponStatus("0");

                                }

                                // 买送优惠券
                                if (market.getCouponList() != null
                                        && market.getFullbuyCouponMarketing() == null) {
                                    if ("3".equals(market.getCodexType())) {
                                        // 数量
                                        BigDecimal num = BigDecimal.valueOf(og
                                                .getGoodsInfoNum());

                                        BigDecimal bd = BigDecimal.valueOf(0);
                                        // 优惠价格
                                        og.setGoodsCouponPrice(bd);
                                        // 最终售价
                                        og.setGoodsInfoPrice(og
                                                .getGoodsInfoOldPrice()
                                                .subtract(bd));
                                        // 小计金额
                                        og.setGoodsInfoSumPrice(og
                                                .getGoodsInfoPrice().multiply(
                                                        num));

                                    }
                                    // 查询所有此促销的赠品级联list
                                    List<Coupon> clist = market.getCouponList();
                                    if (clist != null && !clist.isEmpty()) {
                                        og.setHaveCouponStatus("1");
                                        for (Coupon coupon : clist) {
                                            OrderGoodsInfoCoupon oic = new OrderGoodsInfoCoupon();
                                            oic.setCouponId(coupon
                                                    .getCouponId());
                                            Coupon cp = couponService
                                                    .selectOneCouponNoByCouponIdAndUpdateNoIsGet(
                                                            coupon.getCouponId(),
                                                            (Long) request
                                                                    .getSession()
                                                                    .getAttribute(
                                                                            "customerId"));
                                            oic.setCouponNo(cp.getCodeNo());
                                            orderGoodsInfoCouponList.add(oic);
                                        }
                                    } else {
                                        og.setHaveCouponStatus("0");
                                    }

                                    og.setHaveGiftStatus("0");
                                }
                                //
                                BigDecimal bd = BigDecimal.valueOf(0);
                                // 数量
                                BigDecimal num = BigDecimal.valueOf(og
                                        .getGoodsInfoNum());
                                // 最终售价 原价-（原价 * 折扣）
                                og.setGoodsInfoPrice(og.getGoodsInfoOldPrice());
                                // 小计金额
                                og.setGoodsInfoSumPrice(og.getGoodsInfoPrice()
                                        .multiply(num));

                                // 优惠金额 优惠金额 = 数量 *（原价*折扣）
                                og.setGoodsCouponPrice(bd);
                                og.setHaveCouponStatus("0");
                                og.setHaveGiftStatus("0");
                            } else {
                                BigDecimal bd = BigDecimal.valueOf(0);
                                // 数量
                                BigDecimal num = BigDecimal.valueOf(og
                                        .getGoodsInfoNum());
                                // 最终售价 原价-（原价 * 折扣）
                                og.setGoodsInfoPrice(og.getGoodsInfoOldPrice());
                                // 小计金额
                                og.setGoodsInfoSumPrice(og.getGoodsInfoPrice()
                                        .multiply(num));

                                // 优惠金额 优惠金额 = 数量 *（原价*折扣）
                                og.setGoodsCouponPrice(bd);
                                og.setHaveCouponStatus("0");
                                og.setHaveGiftStatus("0");
                            }
                            og.setOrderGoodsInfoCouponList(orderGoodsInfoCouponList);
                            og.setOrderGoodsInfoGiftList(orderGoodsInfoGiftList);
                            oglist.add(og);
                        }
                    }
                }
                // 订单交易总金额
                BigDecimal sumPrice = BigDecimal.valueOf(0);
                // 原始总计额
                BigDecimal sumOldPrice = BigDecimal.valueOf(0);
                // 总优惠金额
                BigDecimal prePrice = BigDecimal.valueOf(0);
                if (oglist != null && !oglist.isEmpty()) {
                    for (int i = 0; i < oglist.size(); i++) {
                        sumPrice = sumPrice.add(oglist.get(i)
                                .getGoodsInfoSumPrice());
                        BigDecimal num = BigDecimal.valueOf(oglist.get(i)
                                .getGoodsInfoNum());
                        sumOldPrice = sumOldPrice.add(oglist.get(i)
                                .getGoodsInfoOldPrice().multiply(num));
                        if (oglist.get(i).getGoodsCouponPrice() != null) {
                            prePrice = prePrice.add(oglist.get(i)
                                    .getGoodsCouponPrice());
                        }
                    }
                }
                OrderMarketing orderMarketing = null;
                // 订单促销
                for (int i = 0; i < thirdIds.length; i++) {
                    if ((Long) thirdId == (long) thirdIds[i]) {
                        boolean isT = false;
                        if (isT) {
                            orderMarketing = new OrderMarketing();
                            orderMarketing.setMarketingId(marketingId[i]);

                            Marketing market = marketingService
                                    .marketingDetail(marketingId[i]);
                            orderMarketing.setMarketingType(market
                                    .getCodexType());
                            if (market != null) {
                                // 直降
                                if ("1".equals(market.getCodexType())) {
                                    PriceOffMarketing pm = market
                                            .getPriceOffMarketing();
                                    sumPrice = sumPrice.subtract(pm
                                            .getOffValue());
                                    prePrice = prePrice.add(pm.getOffValue());
                                }

                                // 买折扣 折扣多少
                                if ("4".equals(market.getCodexType())) {
                                    DiscountMarketing fm = market
                                            .getDiscountMarketing();
                                    prePrice = prePrice.add(sumPrice
                                            .subtract(sumPrice.multiply(fm
                                                    .getDiscountValue())));
                                    sumPrice = sumOldPrice.multiply(fm
                                            .getDiscountValue());
                                }
                                // 满减优惠
                                if ("5".equals(market.getCodexType())) {
                                    FullbuyReduceMarketing bm = market
                                            .getFullbuyReduceMarketing();
                                    BigDecimal sum = BigDecimal.valueOf(0);
                                    sum = sum.add(sumPrice);
                                    if (sum.subtract(bm.getFullPrice())
                                            .longValue() > 0) {
                                        sumPrice = sumPrice.subtract(bm
                                                .getReducePrice());
                                        prePrice = prePrice.add(prePrice);

                                    }
                                }

                                // 满折
                                if ("8".equals(market.getCodexType())) {
                                    FullbuyDiscountMarketing fd = market
                                            .getFullbuyDiscountMarketing();
                                    BigDecimal sum = BigDecimal.valueOf(0);
                                    sum = sum.add(sumPrice);
                                    if (sum.subtract(fd.getFullPrice())
                                            .longValue() > 0) {
                                        prePrice = prePrice
                                                .add(sumPrice.subtract(sumPrice.multiply(fd
                                                        .getFullbuyDiscount())));
                                        sumPrice = sumOldPrice.multiply(fd
                                                .getFullbuyDiscount());
                                    }
                                }
                                // 买送赠品
                                if (market.getGiftList() != null) {
                                    gifts = market.getGiftList();
                                    orderMarketing.setHaveGiftStatus("1");
                                }

                                // 买送优惠券
                                if (market.getCouponList() != null) {
                                    coupons = market.getCouponList();
                                    orderMarketing.setHaveCouponStatus("1");
                                }
                                // 满赠赠品
                                if (market.getFullbuyPresentMarketing() != null) {
                                    BigDecimal sum = BigDecimal.valueOf(0);
                                    sum = sum.add(sumPrice);
                                    if (sum.subtract(
                                            market.getFullbuyPresentMarketing()
                                                    .getFullPrice())
                                            .longValue() > 0) {
                                        gifts = market.getGiftList();
                                        orderMarketing.setHaveGiftStatus("1");

                                    } else {
                                        gifts = null;
                                        orderMarketing.setHaveGiftStatus("0");
                                    }

                                }
                                // 满赠优惠券
                                if (market.getFullbuyCouponMarketing() != null) {
                                    BigDecimal sum = BigDecimal.valueOf(0);
                                    sum = sum.add(sumPrice);
                                    if (sum.subtract(
                                            market.getFullbuyCouponMarketing()
                                                    .getFullPrice())
                                            .longValue() > 0) {
                                        coupons = market.getCouponList();
                                        orderMarketing.setHaveCouponStatus("1");

                                    } else {
                                        gifts = null;
                                        orderMarketing.setHaveGiftStatus("0");
                                    }

                                }

                            }

                        }

                    }
                }
                // 计算商品运费模板金额
                int num = 0;
                BigDecimal weight = new BigDecimal(0);
                Long distributionId = null;
                if (request.getParameter("distributionId") != null) {
                    distributionId = ("0").equals(thirdId.toString()) ? Long
                            .valueOf(request.getParameter("distributionId"))
                            : null;
                } else {
                    distributionId = null;
                }
                // 快递配送地址
                CustomerAddress ca = addressService.queryCustomerAddressById(
                        custAddress,
                        (Long) request.getSession().getAttribute(
                                CustomerConstants.CUSTOMERID));
                // 封装物流信息
                OrderExpress oe = new OrderExpress();
                if (typeId == 1 && "0".equals(thirdId.toString())) {
                    // 上门自提
                    oe.setExpressTypeId(1L);
                    order.setExpressPrice(new BigDecimal(0));
                } else {
                    // 判断是否包邮
                    Long baoyou = 0L;
                    // 计算运费
                    BigDecimal expressPrice = new BigDecimal(0);
                    if (cartlist1 != null && !cartlist1.isEmpty()) {
                        for (ShoppingCart sc : cartlist1) {
                            if (sc.getFitId() == null) {
                                if (thirdId.equals(sc.getGoodsDetailBean()
                                        .getProductVo().getThirdId())) {
                                    // 判断是否包邮
                                    baoyou = marketService
                                            .queryByCreatimeMarketings(
                                                    sc.getGoodsInfoId(), 6L,
                                                    sc.getGoodsDetailBean()
                                                            .getProductVo()
                                                            .getGoods()
                                                            .getCatId(),
                                                    sc.getGoodsDetailBean()
                                                            .getBrand()
                                                            .getBrandId());
                                    if (baoyou == 0) {
                                        num += Integer.parseInt(sc
                                                .getGoodsNum().toString());
                                        GoodsProductVo g = siteGoodsProductService
                                                .queryProductByProductId(sc
                                                        .getGoodsInfoId());
                                        weight = weight
                                                .add(g.getGoodsInfoWeight()
                                                        .multiply(
                                                                new BigDecimal(
                                                                        sc.getGoodsNum())));
                                        // 1：此商品卖家包邮 0：此商品买家自费
                                        if (g.getIsMailBay() == 0) {
                                            // 获取商品的快递价格
                                            expressPrice = freightTemplateService
                                                    .getExpressPrice(
                                                            distributionId,
                                                            ca.getCity()
                                                                    .getCityId(),
                                                            Long.valueOf(thirdId
                                                                    .toString()),
                                                            num, weight);
                                        }
                                    }
                                }
                            } else {
                                if (thirdId.equals(sc.getGoodsGroupVo()
                                        .getThirdId())) {
                                    // 套装运费计算
                                    GoodsGroupVo goodsGroupVo = goodsGroupService
                                            .queryVoByPrimaryKey(sc.getFitId());
                                    for (int j = 0; j < goodsGroupVo
                                            .getProductList().size(); j++) {
                                        weight = weight
                                                .add(goodsGroupVo
                                                        .getProductList()
                                                        .get(j)
                                                        .getProductDetail()
                                                        .getGoodsInfoWeight()
                                                        .multiply(
                                                                new BigDecimal(
                                                                        sc.getGoodsNum())));
                                        num += Integer.parseInt(sc
                                                .getGoodsNum().toString());
                                    }
                                    expressPrice = freightTemplateService
                                            .getExpressPrice(distributionId, ca
                                                    .getCity().getCityId(),
                                                    Long.valueOf(thirdId
                                                            .toString()), num,
                                                    weight);
                                }
                            }
                        }

                    }

                    FreightExpress fe = null;
                    Long comId = null;
                    String comName = "";
                    if (distributionId != null
                            && "0".equals(thirdId.toString())) {
                        // 获取物流信息
                        fe = freightExpressMapper
                                .selectFreightExpressByDistributionId(distributionId);
                        comId = fe.getSysLogisticsCompany().getLogComId();
                        comName = fe.getSysLogisticsCompany().getName();
                    } else {

                        // 获取物流信息 获取第三方默认物流信息
                        fe = freightTemplateService
                                .selectFreightExpressByDistriThirdId((Long) thirdId);
                        if (fe.getExpress() != null) {
                            comId = fe.getExpress().getShoreExpId();
                            comName = fe.getExpress().getExpName();
                        }

                    }

                    if (fe != null) {
                        oe.setExpressId(comId);
                        oe.setExpressName(comName);
                    }
                    oe.setDelFlag("0");
                    // 配送方式 0 快递配送 1 上门自提
                    oe.setExpressTypeId(0L);
                    order.setExpressPrice(expressPrice);
                }

                // 再次计算金额
                sumOldPrice = sumOldPrice.add(order.getExpressPrice());
                sumPrice = sumPrice.add(order.getExpressPrice());

                if (codeNo != null && !"".equals(codeNo)) {
                    Coupon coupon = couponService.selectCouponByCodeNo(codeNo);

                    if (coupon != null) {
                        // 直降
                        if ("1".equals(coupon.getCouponRulesType())) {
                            // 计算交易价格减去金额
                            sumPrice = sumPrice.subtract(coupon
                                    .getCouponStraightDown().getDownPrice());
                            // 优惠金额+金额
                            prePrice = prePrice.add(coupon
                                    .getCouponStraightDown().getDownPrice());
                        }
                        // 满减
                        if ("2".equals(coupon.getCouponRulesType())) {
                            // 计算交易价格减去金额
                            sumPrice = sumPrice.subtract(coupon
                                    .getCouponFullReduction()
                                    .getReductionPrice());
                            // 优惠金额+金额
                            prePrice = prePrice.add(coupon
                                    .getCouponFullReduction()
                                    .getReductionPrice());
                        }

                    }

                }

                if (typeId == 1 && "0".equals(thirdId.toString())) {
                    // 上面自提地址
                    DeliveryPoint point = deliveryPointService
                            .getDeliveryPoint(deliveryPointId);
                    order.setShoppingAddrId(deliveryPointId);
                    order.setShippingProvince(point.getTemp1());
                    order.setShippingCity(point.getTemp2());
                    order.setShippingCounty(point.getTemp3());
                    order.setShippingAddress(point.getAddress());
                    order.setShippingPerson(point.getName());
                    // 设置配送方式
                    order.setOrderExpressType("1");
                } else {
                    if (orderAddress.getAddressName() == null
                            || orderAddress.getAddressPhone() == null
                            || orderAddress.getAddressDetail() == null
                            || orderAddress.getAddressDetailInfo() == null) {

                        order.setShoppingAddrId(custAddress);
                        order.setShippingProvince(ca.getProvince()
                                .getProvinceName());
                        order.setShippingCity(ca.getCity().getCityName());
                        order.setShippingCounty(ca.getDistrict()
                                .getDistrictName());
                        order.setShippingAddress(ca.getAddressDetail());
                        order.setShippingPerson(ca.getAddressName());
                        order.setShippingPhone(ca.getAddressPhone());
                        order.setShippingMobile(ca.getAddressMoblie());

                        order.setShippingPostcode(ca.getAddressZip());
                    } else {
                        // 微信收货地址
                        orderAddress.setProviceFirstStageName(new String(
                                orderAddress.getProviceFirstStageName()
                                        .getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        orderAddress.setAddressCitySecondStageName(new String(
                                orderAddress.getAddressCitySecondStageName()
                                        .getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        order.setShippingProvince(orderAddress
                                .getProviceFirstStageName().substring(
                                        0,
                                        orderAddress.getProviceFirstStageName()
                                                .length() - 1));
                        order.setShippingCity(orderAddress
                                .getAddressCitySecondStageName()
                                .substring(
                                        0,
                                        orderAddress
                                                .getAddressCitySecondStageName()
                                                .length() - 1));
                        order.setShippingCounty(new String(orderAddress
                                .getAddressCountiesThirdStageName().getBytes(
                                        "ISO-8859-1"), ConstantUtil.UTF));
                        order.setShippingAddress(new String(orderAddress
                                .getAddressDetailInfo().getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        order.setShippingMobile(new String(orderAddress
                                .getAddressPhone().getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                        order.setShippingPerson(new String(orderAddress
                                .getAddressName().getBytes("ISO-8859-1"),
                                ConstantUtil.UTF));
                    }
                    order.setCustomerRemark(customerRemark);
                    // 设置配送方式
                    order.setOrderExpressType("0");
                }

                // 子订单号
                String orderCode = UtilDate.mathString(new Date());

                // 插入订单促销id
                order.setOrderCode(orderCode);
                order.setOrderOldCode(orderOldCode);
                // 插入订单商品
                order.setOrderGoodsList(oglist);
                // 支付方式
                order.setPayId(ch_pay);
                // 发票信息String invoiceType,String invoiceTitle,
                order.setInvoiceType("1");
                order.setInvoiceTitle(invoiceTitle);
                order.setInvoiceContent(invoiceType);
                order.setDelFlag("0");

                // 设置为手机订单 ，如果是微信，则设置为微信订单
                order.setOrderMType("2");
                // 如果为2，表示货到付款，订单状态改为已付款
                if (ch_pay == 2) {
                    // 订单状态改为已付款
                    order.setOrderStatus("1");
                } else {
                    // 订单状态
                    order.setOrderStatus("0");
                }
                // 订单优惠金额
                // 总金额

                // boss商品兑换积分
                if (thirdId.equals(0L)) {
                    /* 积分兑换订单金额 */
                    if (null != duiHuanJiFen) {
                        // 更新当前会员的总积分信息 当前登录成功的会员
                        Long customerId = (Long) request.getSession()
                                .getAttribute(CustomerConstantStr.CUSTOMERID);
                        // 获得当前用户的积分信息
                        CustomerPoint customerPoint = couponService
                                .selectCustomerPointByCustomerId(customerId);
                        // 判断当前积分是否够兑换
                        if (customerPoint.getPointSum() < duiHuanJiFen) {
                            return null;
                        }
                        // 积分兑换规则
                        PointSet pointSet = this.couponService.selectPointSet();
                        if (null != pointSet) {
                            // 转换类型
                            BigDecimal zhuanhuan = new BigDecimal(duiHuanJiFen);
                            // 根据积分兑换规则 计算积分兑换金额
                            BigDecimal disparityPrice = zhuanhuan
                                    .multiply(pointSet.getConsumption());
                            //
                            disparityPrice = disparityPrice
                                    .divide(new BigDecimal(10));
                            // 对兑换处的价格进行四舍五入
                            BigDecimal jiFenDuiHuan = disparityPrice.setScale(
                                    2, BigDecimal.ROUND_HALF_UP);
                            // 计算最后订单金额
                            sumPrice = sumPrice.subtract(jiFenDuiHuan);
                            // 对兑换处的价格进行四舍五入
                            sumPrice = sumPrice.setScale(2,
                                    BigDecimal.ROUND_HALF_UP);

                            // 修改会员的积分
                            this.updatePoint(customerId, duiHuanJiFen);
                            this.insertExchangeCusmomer(customerId, orderCode,
                                    duiHuanJiFen, disparityPrice,
                                    pointSet.getConsumption());
                        }

                    }
                }
                // 总金额
                order.setOrderPrice(sumPrice);
                // 原始总额
                order.setOrderOldPrice(sumOldPrice);
                // 总优惠金额
                order.setOrderPrePrice(prePrice);
                order.setBusinessId(Long.valueOf(thirdId.toString()));
                // 订单使用的优惠券
                order.setCouponNo(codeNo);
                order.setCustomerId((Long) request.getSession().getAttribute(
                        "customerId"));
                order.setCreateTime(new Date());

                // 插入订单主表
                int f = orderService.insertOrder(order);
                // 插叙返回的ID
                if (f == 1) {

                    Long orderId = orderService.selectLastId();
                    if (orderMarketing != null) {
                        orderMarketing.setOrderId(orderId);
                        orderService.insertSelective(orderMarketing);
                        if (!gifts.isEmpty()) {
                            Long lastOrderMarketId = orderService
                                    .selectOrderMarketLastId();
                            List<OrderGift> orderG = new ArrayList<OrderGift>();
                            OrderGift orderGift = null;
                            for (int i = 0; i < gifts.size(); i++) {
                                orderGift = new OrderGift();
                                orderGift.setGiftId(gifts.get(i).getGiftId());
                                orderGift
                                        .setOrderMarketingId(lastOrderMarketId);
                                orderG.add(orderGift);
                            }
                            orderService.insertOrderInfoGift(orderG);
                        }
                        if (!coupons.isEmpty()) {
                            Long lastOrderMarketId = orderService
                                    .selectOrderMarketLastId();
                            List<OrderCoupon> orderCoupons = new ArrayList<OrderCoupon>();
                            OrderCoupon orderCoupon = null;
                            for (int i = 0; i < coupons.size(); i++) {
                                orderCoupon = new OrderCoupon();

                                CouponNo couponNo = couponNoService
                                        .selectNoByCouponIdByStatus(coupons
                                                .get(i).getCouponId());
                                if (couponNo != null) {
                                    orderCoupon.setCouponId(coupons.get(i)
                                            .getCouponId());
                                    orderCoupon
                                            .setOrderMarketingId(lastOrderMarketId);
                                    orderCoupon.setCouponNo(couponNo
                                            .getCodeNo());
                                    orderCoupons.add(orderCoupon);
                                }
                            }
                            if (!orderCoupons.isEmpty()) {
                                orderService.insertCouponInfoGift(orderCoupons);
                            }
                        }
                    }
                    // 修改优惠券已经使用
                    couponNoService.updateCodeIsUse(codeNo, orderCode);
                    order.setOrderId(orderId);
                    maps.add(order);
                    // 插入物流信息
                    oe.setOrderId(orderId);
                    orderExpressMapper.insertOrderExpress(oe);
                    // 循环设置货品级联ID信息
                    if (oglist != null && !oglist.isEmpty()) {
                        for (int i = 0; i < oglist.size(); i++) {
                            oglist.get(i).setOrderId(orderId);
                            // 插入货品
                            orderGoodsMapper
                                    .insertOrderGoodsInfo(oglist.get(i));
                            // 获取货品级联ID
                            Long orderGoodsId = orderGoodsMapper.selectLastId();
                            if ("1".equals(oglist.get(i).getHaveCouponStatus())) {
                                List<OrderGoodsInfoCoupon> clist = oglist
                                        .get(i).getOrderGoodsInfoCouponList();
                                if (clist != null && !clist.isEmpty()) {
                                    for (int j = 0; j < clist.size(); j++) {
                                        clist.get(j).setOrderGoodsId(
                                                orderGoodsId);
                                    }
                                    // 批量插入订单商品的赠送优惠券信息
                                    orderGoodsInfoCouponMapper
                                            .insertOrderInfoCoupon(clist);
                                }
                            }

                            if ("1".equals(oglist.get(i).getHaveGiftStatus())) {
                                List<OrderGoodsInfoGift> glist = oglist.get(i)
                                        .getOrderGoodsInfoGiftList();
                                if (glist != null && !glist.isEmpty()) {
                                    for (int j = 0; j < glist.size(); j++) {
                                        glist.get(j).setOrderGoodsId(
                                                orderGoodsId);
                                    }
                                    // 批量插入订单商品的赠送赠品信息
                                    orderGoodsInfoGiftMapper
                                            .insertOrderInfoGift(glist);
                                }
                            }

                        }
                    }
                }
            }

        }

        // 修改所有购买商品为已经删除
        shoppingCartService.deleteShoppingCartByIds(request, shoppingCartId);
        try {
            goodsProductService.minStock(calcStockUtils);
            return maps;
        } finally {
            calcStockUtils = null;
            cartlist1 = null;
            list = null;
            maps = null;
            marketingId = null;

        }

    }

    /**
     *
     * @param customerId
     *            会员ID
     * @param orderCode
     *            订单单号
     * @param duiHuanJiFen
     *            兑换的积分
     * @param disparityPrice
     *            兑换的几个
     * @param consumption
     *            积分兑换规则
     * @return
     */
    public int insertExchangeCusmomer(Long customerId, String orderCode,
            Long duiHuanJiFen, BigDecimal disparityPrice, BigDecimal consumption) {
        int result = 0; // 保存执行的结果
        // 新增积分兑换信息
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("customerId", customerId);
            map.put("exchangePoint", duiHuanJiFen);
            map.put("exchangeTime", new Date());
            map.put("orderCode", orderCode);
            map.put("disparityPrice", disparityPrice);
            map.put("consumption", consumption);
            result = siteGoodsProductService.insertExchangeCusmomer(map);
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return result;
    }

    /***
     * 修改会员总积分
     * 
     * @param customerId
     * @param point
     * @return
     */
    public int updatePoint(Long customerId, Long point) {
        int result = 0; // 保存执行的结果
        // 根据会员ID获取积分对象
        CustomerPoint customerPoint = couponService
                .selectCustomerPointByCustomerId(customerId);
        if (null != customerPoint) {
            // 更新当前登录会员的总积分
            customerPoint.setPointSum(customerPoint.getPointSum() - point);
            // 更新积分对象
            result = couponService.updateCustomerPoint(customerPoint);
        }
        return result;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.order.service.SiteOrderService#payOrder(java.lang.Long)
     */
    @Override
    public int payOrder(Long orderId) {
        return orderService.payOrder(orderId);
    }

    /*
     * 
     * 
     * @see com.ningpai.order.service.OrderService#getExpressNo(java.lang.Long)
     */
    public List<OrderContainerUtil> getExpressNo(Long orderId) {
        List<OrderContainerUtil> containerUtils = new ArrayList<OrderContainerUtil>();
        // 返回运送方式
        List<OrderContainerRelation> expressList = relationMapper
                .getExpressNo(orderId);
        // 订单所属的配送方式
        OrderExpress express = orderExpressMapper.selectOrderExpress(orderId);
        String kuaiDiName = iExpressConfBiz
                .queryKuaidi100CodeByExpressId(express.getExpressId());
        // 订单包裹和物流信息
        List<OrderContainerRelation> containerRelations = orderService
                .queryContainerRalation(orderId);
        // 订单单个包裹
        OrderContainerUtil order = null;
        OrderExpress orderExpress = orderExpressMapper
                .selectOrderExpress(orderId);
        // 订单所属的配送信息
        for (int i = 0; i < expressList.size(); i++) {
            String kuaidi = KuaiDiUtil.execLookKuaiDi(kuaiDiName, expressList
                    .get(i).getExpressNo());
            order = new OrderContainerUtil();
            order.setContainerRelations(containerRelations.get(i));
            order.setExpressName(orderExpress.getExpressName());
            order.setExpress(kuaidi);
            containerUtils.add(order);
        }

        try {
            return containerUtils;
        } finally {
            order = null;
            containerUtils = null;
            expressList = null;
            kuaiDiName = null;
            express = null;
        }
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.order.service.SiteOrderService#getPayOrder(java.lang
     * .Long)
     */
    @Override
    public Order getPayOrder(Long orderId) {
        return orderService.getPayOrder(orderId);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.order.service.SiteOrderService#getPayOrderByCode(java
     * .lang.String)
     */
    @Override
    public Order getPayOrderByCode(String orderCode) {
        return orderService.getPayOrderByCode(orderCode);
    }

    /*
     * 
     * @see com.ningpai.m.order.service.OrderMService#getWXUrl()
     */
    @Override
    public Map<String, Object> getWXUrl(HttpServletRequest request,
            HttpServletResponse response, Order order, Pay pay, String goodsName) {
        // 网页授权后获取传递的参数
        String userId = request.getParameter("userId");
        // 金额转化为分为单位
        String finalmoney = String.format("%.2f", order.getOrderPrice());
        finalmoney = finalmoney.replace(".", "");

        // 商户相关资料
        String appid = pay.getApiKey();
        String appsecret = pay.getSecretKey();
        String partner = pay.getPartner();
        String partnerkey = pay.getPartnerKey();
        String openId = "";
        openId = request.getSession().getAttribute("openid").toString();

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mchId = partner;
        // 子商户号 非必输
        // String sub_mch_id="";
        // 随机数
        String nonceStr = strReq;
        // 商品描述
        // String body = describe;

        // 商品描述根据情况修改
        String body = goodsName;
        // 附加数据
        String attach = userId;
        // 商户订单号
        String outTradeNo = order.getOrderCode();
        int intMoney = Integer.parseInt(finalmoney);

        // 总金额以分为单位，不带小数点
        int totalFee = intMoney;
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 订 单 生 成 时 间 非必输
        // 订单失效时间 非必输
        // 商品标记 非必输
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = pay.getBackUrl();

        String trade_type = "JSAPI";
        String openid = openId;
        // 非必输
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mchId);
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("body", body);
        packageParams.put("attach", attach);
        packageParams.put("out_trade_no", outTradeNo);

        // 这里写的金额为1 分到时修改
        packageParams.put("total_fee", String.valueOf(totalFee));
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);

        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", openid);

        RequestHandlerUtil reqHandler = new RequestHandlerUtil(request,
                response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
                + mchId + "</mch_id>" + "<nonce_str>" + nonceStr
                + "</nonce_str>" + "<sign>" + sign + "</sign>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<out_trade_no>"
                + outTradeNo
                + "</out_trade_no>"
                +
                // 金额，这里写的1 分到时修改
                "<total_fee>"
                + totalFee
                + "</total_fee>"
                +
                // "<total_fee>"+finalmoney+"</total_fee>"+
                "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                + "<notify_url>" + notify_url + "</notify_url>"
                + "<trade_type>" + trade_type + "</trade_type>" + "<openid>"
                + openid + "</openid>" + "</xml>";
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepayId = "";
        try {
            prepayId = GetWxOrderno.getPayNo(createOrderURL, xml);
            if ("".equals(prepayId)) {
                request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e1) {
            LOGGER.info(e1);
            prepayId = null;
        }
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonceStr;
        String prepay_id2 = "prepay_id=" + prepayId;
        String packages = prepay_id2;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", appid2);
        map.put("timeStamp", timestamp);
        map.put("nonceStr", nonceStr2);
        map.put("package", packages);
        map.put("sign", finalsign);
        return map;
    }

    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(OrderMServiceImpl.class);

    @Resource(name = "customerAddressServiceM")
    private CustomerAddressService addressService;

    @Resource(name = "ShoppingCartService")
    private ShoppingCartService shoppingCartService;

    @Resource(name = "MarketingService")
    private MarketingService marketingService;
    @Resource(name = "HsiteGoodsProductMapper")
    private GoodsProductMapper goodsProductMapper;

    @Resource(name = "ProductWareMapper")
    private ProductWareMapper productWareMapper;

    @Resource(name = "FreightTemplateMapper")
    private FreightTemplateMapper freightTemplateMapper;

    @Resource(name = "PreDiscountMarketingMapper")
    private PreDiscountMarketingMapper preDiscountMarketingMapper;

    @Resource(name = "OrderContainerRelationMapper")
    private OrderContainerRelationMapper relationMapper;

    @Resource(name = "expressConfBizImpl")
    private IExpressConfBiz iExpressConfBiz;

    @Resource(name = "ProductWareService")
    private ProductWareService productWareService;
    @Resource(name = "ShoppingCartMapper")
    private ShoppingCartMapper shoppingCartMapper;

    @Resource(name = "GoodsProductService")
    private com.ningpai.goods.service.GoodsProductService goodsProductService;

    @Resource(name = "OrderService")
    private OrderService orderService;

    @Resource(name = "OrderExpressMapper")
    private OrderExpressMapper orderExpressMapper;

    @Resource(name = "OrderGoodsMapper")
    private OrderGoodsMapper orderGoodsMapper;

    @Resource(name = "OrderGoodsInfoCouponMapper")
    private OrderGoodsInfoCouponMapper orderGoodsInfoCouponMapper;

    @Resource(name = "OrderGoodsInfoGiftMapper")
    private OrderGoodsInfoGiftMapper orderGoodsInfoGiftMapper;

    @Resource(name = "SysLogisticsCompanyMapper")
    private SysLogisticsCompanyMapper sysLogisticsCompanyMapper;

    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "CouponNoService")
    private CouponNoService couponNoService;

    @Resource(name = "expressInfoMapperThird")
    private ExpressInfoMapper expressInfoMapperThird;

    // 自提点
    @Resource(name = "DeliveryPointService")
    private DeliveryPointService deliveryPointService;
    @Resource(name = "MarketingService")
    private MarketingService marketService;
    @Resource(name = "GoodsGroupService")
    private GoodsGroupService goodsGroupService;
    @Resource(name = "FreightTemplateService")
    private FreightTemplateService freightTemplateService;
    @Resource(name = "HsiteGoodsProductService")
    private GoodsProductService siteGoodsProductService;
    @Resource(name = "FreightExpressMapper")
    private FreightExpressMapper freightExpressMapper;

}
