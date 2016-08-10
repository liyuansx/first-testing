/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.order.controller;
import com.ningpai.m.site.customer.util.*;
import com.ningpai.common.safe.CSRFTokenManager;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.common.util.alipay.config.AlipayConfig;
import com.ningpai.common.util.alipay.util.AlipayNotify;
import com.ningpai.common.util.alipay.util.AlipaySubmitM;
import com.ningpai.common.util.alipay.util.UtilDate;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.freighttemplate.service.FreightTemplateService;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.goods.vo.GoodsProductVo;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerAddressService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.order.bean.OrderAddress;
import com.ningpai.m.order.service.OrderMService;
import com.ningpai.m.order.service.OrderPayService;
import com.ningpai.m.shoppingcart.bean.ShoppingCart;
import com.ningpai.m.shoppingcart.bean.ShoppingCartWareUtil;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.m.site.customer.util.Constant;
import com.ningpai.m.site.customer.util.CustomerSignUtil;
import com.ningpai.m.site.customer.util.Http;
import com.ningpai.m.site.customer.util.MD5;
import com.ningpai.m.weixin.util.WXSendMSG;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.service.OrderService;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.system.bean.DeliveryPoint;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.dao.IExpressConfDao;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.PayService;
import com.ningpai.system.service.PaymentService;
import com.ningpai.system.service.impl.ExpressConfBizImpl;
import com.ningpai.util.MyLogger;
import com.ningpai.util.ShoppingCartConstants;
import com.site.lookup.util.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单控制器
 * 
 * @author NINGPAI-LIH
 * @since 2014年4月14日 下午5:35:48
 * @version 1.0
 */
@Controller
public class OrderMController {
    private PayService payService;
    private ExpressConfBizImpl expressConfBizImpl;
    private ShoppingCartService shoppingCartService;
    private OrderMService siteOrderService;
    private MarketingService marketingService;
    private OrderService orderser;
    private GoodsProductService goodsProductService;
    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper addressService2;

    
    
    @Resource(name = "customerServiceM")
    private CustomerService customerService;
    
	@Resource(name = "OrderPayService")
    private OrderPayService orderPayService;

    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;

    // 自提点
    @Resource(name = "DeliveryPointService")
    private DeliveryPointService deliveryPointService;

    @Resource(name = "PaymentService")
    PaymentService paymentService;

    /** 配送方式设置数据操作类 */
    @Resource(name = "expressConfDaoImpl")
    private IExpressConfDao expressConfDaoImpl;
    
    @RequestMapping("/checkisRealnameM")
    @ResponseBody
    public  String checkisRealname(HttpServletRequest request){
        Long customerId = (Long) request.getSession().getAttribute("customerId");
        com.ningpai.other.bean.CustomerAllInfo customer= addressService2.selectByPrimaryKey(customerId);
        return customer.getRealFlag();
    }
    
    
    @RequestMapping("/checkRealname")
    @ResponseBody
    public String checkRealname(HttpServletRequest request,String realName,String certNo){
    		Map<String, String> data = new HashMap<String, String>();
    		data.put("partnerId", Constant.partnerId);
    		data.put("signType", Constant.signType);
    		data.put("returnUrl", Constant.returnUrl);
    		data.put("notifyUrl", Constant.notifyUrl);
    		data.put("protocol", "httpPost");
    		// 用时分秒生成一个20-40位的orderNo，推荐8位日期+8-12位业务序号
    		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssssSSSS");
    		String orderNo = df.format(new Date());
    		data.put("orderNo", orderNo);

//    		data = singlePaymentUpload(data);// <========================调用服务API相对应的方法
    		data.put("service", "realNameQuery");
            data.put("realName", realName);
            data.put("certNo", certNo);
    		String signString = CustomerSignUtil.signString(data);// <========生成待签名字符串

    		String sign = MD5.MD5(signString);// <=======================签名
    		data.put("sign", sign);
    		// forLink(data);// <=======生成链接s
    		String result = Http.postString(data, Constant.gatewayUrl);
    		result= realnameResult(result);
    		if("pass".equals(result)){
    			  Long customerId = (Long) request.getSession().getAttribute("customerId");
    			  com.ningpai.other.bean.CustomerAllInfo customer=addressService2.selectByPrimaryKey(customerId);
    			  customer.setRealFlag("1");
    			  customer.setInfoRealname(realName);
    			  customer.setInfoCardid(certNo);
    			  addressService2.updateCustomerAllInfoById(customer);
    		}
    	return result;
    }
    
    /**
     * 解析返回值
     * @param myString
     * @return
     */
    private  String realnameResult(String myString){
		String texts[]=myString.split(",");
		for(int i=0;i<texts.length;i++){
			if(texts[i].startsWith("\"realNameQueryResult")){
				String results[]=texts[i].split(":");
				return results[1].replace("\"", "");
			}
		}
		return null;
	}

    @RequestMapping("/getMexpressprice")
    @ResponseBody
    public String getExpressPrice(Long addressId,HttpServletRequest request,Long distributionId){
        //用户编号
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        String thirdId = request.getParameter("thirdIds");
        String cartId = request.getParameter("cartIds");
        String[] thirdIds = thirdId.split(",");
        String[] cartIds = cartId.split(",");
        //获取 收获地址城市
        CustomerAddress ca = addressService.queryCustomerAddressById(addressId, customerId);
        if(ca==null){
            return "0";
        }
        BigDecimal price = new BigDecimal(0);
        //判断商家订单
        for(int i=0;i<thirdIds.length;i++){
            if(thirdIds[i]!=null&&!"".equals(thirdIds[i])){
                //获取该订单的购物车Id
                List<Long> shopIds = new ArrayList<Long>();
                if(thirdIds[i].equals("0")){
                    for(int j=0;j<cartIds.length;j++){
                        //获取购物车商品个数和重量
                        String s = cartIds[j];
                        String[] ids = s.split("-");
                        if(ids!=null&&ids.length!=0){
                            for(String id : ids){
                                shopIds.add(Long.valueOf(id));
                            }
                        }
                    }
                }else{
                    //获取购物车商品个数和重量
                    String s = cartIds[i];
                    String[] ids = s.split("-");
                    if(ids!=null&&ids.length!=0){
                        for(String id : ids){
                            shopIds.add(Long.valueOf(id));
                        }
                    }
                }
                //添加购物车id
                Long[] box=new Long[shopIds.size()];
                for (int j = 0; j < shopIds.size(); j++) {
                    box[j]=shopIds.get(j);
                }
                List<ShoppingCart> shlist = shoppingCartService.searchByProduct(request, box);
                BigDecimal weight = new BigDecimal(0);
                Integer nums = 0;
                if(shlist!=null&& !shlist.isEmpty()){
                    for(ShoppingCart sc : shlist){
                        //判断是否是套装
                        if(sc.getFitId()==null){
                            //如果是普通商品，执行普通商品的方法
                            GoodsProductVo goodsProduct=goodsProductService.queryByPrimaryId(sc.getGoodsInfoId());
                            if(goodsProduct!=null && "0".equals(goodsProduct.getIsMailBay())){
                                weight = weight.add(goodsProduct.getGoodsInfoWeight().multiply(new BigDecimal(sc.getGoodsNum())));
                                nums += Integer.parseInt(sc.getGoodsNum().toString());
                            }
                        }else{
                            //套装运费计算
                            GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(sc.getFitId());
                            //遍历套装下的商品
                            for (int j = 0; j < goodsGroupVo.getProductList().size(); j++) {
                                weight = weight.add(goodsGroupVo.getProductList().get(j).getProductDetail().getGoodsInfoWeight().multiply(new BigDecimal(sc.getGoodsNum())));
                                nums += Integer.parseInt(sc.getGoodsNum().toString());
                            }
                        }
                    }
                }
                //邮费计算
                price = price.add(freightTemplateService.getExpressPrice(distributionId,ca.getCity().getCityId(),Long.valueOf(thirdIds[i]), nums, weight));
            }

        }

        //邮费
        return price.toString();
    }


    /**
     * 新运费计算流程
     *
     * @param addressId
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/getnewMexpressprice")
    @ResponseBody
    public Map<String ,Object> getnewexpressprice(Long addressId, HttpServletRequest request) {
        String[] shopCartIds = request.getParameterValues("shopIds[]");
        List<Long> cartList = new ArrayList<>();
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (shopCartIds != null && shopCartIds.length != 0) {
            for (String str : shopCartIds) {
                cartList.add(Long.parseLong(str));
            }
        }
        //获取 收获地址城市
        CustomerAddress ca = customerAddressService.queryCustomerAddressById(addressId,customerId);
        if (ca == null) {
            return null;
        }
        Map<String,Object> freightMap=null;
        if (CollectionUtils.isNotEmpty(cartList)) {
            if (addressId != null) {
                freightMap = shoppingCartService.getNewExpressPrice(ca.getCity().getCityId(), cartList);
            }
        }

        return freightMap;
    }
    /**
     * 跳转到更改发票页
     * @return
     */
    @RequestMapping("tochangeInvoice")
    public ModelAndView tochangeInvoice(HttpServletRequest request,String invoiceType,String invoiceTitle,Long ch_pay,Long  deliveryPointId,Long typeId,Long addressId){
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if(customerId==null){
            return  new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }else{
            ModelAndView mav=new ModelAndView("order/invoice").addObject("invoiceType",invoiceType).addObject("invoiceTitle",invoiceTitle);
            //支付类型
            mav.addObject("ch_pay",ch_pay);
            //物流方式
            mav.addObject("typeId",typeId);
            //收件地址
            mav.addObject("addressId",addressId);
            //自提点
            mav.addObject("deliveryPointId",deliveryPointId);
            return mav;
        }

    }

    /**
     * 跳转到订单确认页
     * 
     * @param box 购物车id
     * @param request
     *        促销id
     *        第三方标识
     * @param
     * @param addressId
     *        地址id
     * @param typeId
     *        0：快递配送 1：上面自提
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/suborder")
    public ModelAndView subOrder(Long[] box,String invoiceTitle,String invoiceType, HttpServletRequest request,Long  deliveryPointId,Long ch_pay, Long ch_express,  Long addressId, Long typeId, OrderAddress orderAddress,String codeNo) throws UnsupportedEncodingException {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if(box!=null){
            request.getSession().setAttribute("box",box);
        }else{
            box=(Long[])request.getSession().getAttribute("box");
        }
        if (customerId == null) {
            return new ModelAndView(new RedirectView("../login.html"));
        }
        CustomerAddress customerAddress = null;
        // 查询收货地址
        if(customerId!=null){
            customerAddress = addressService.queryDefaultAddr(customerId);
        }
        if(null==customerAddress){
            //查询上一次收货地址
            customerAddress = addressService.selectByCIdFirst(customerId);
        }
        Map<String, Object> map = shoppingCartService.newsubOrder(request, box, customerAddress);
        // 获取购物车列表
        List<ShoppingCart> shoplist = (List<ShoppingCart>) map.get("shoplist");
        if (shoplist == null || shoplist.isEmpty()) {
            return new ModelAndView(new RedirectView("../myshoppingmcart.html"));
        }
        DeliveryPoint deliveryPoint = null;
        // 微信收货地址为空
        if (orderAddress.getAddressName() == null || orderAddress.getAddressPhone() == null || orderAddress.getAddressDetail() == null || orderAddress.getAddressDetailInfo() == null) {
         if(customerAddress!=null){
             //给订单地址赋值
             orderAddress=new OrderAddress();
             orderAddress.setAddressId(customerAddress.getAddressId());
             orderAddress.setAddressDetailInfo(customerAddress.getAddressDetail());
             orderAddress.setAddressName(customerAddress.getAddressName());
             orderAddress.setAddressPhone(customerAddress.getAddressMoblie());
             orderAddress.setAddressDetail(customerAddress.getProvince().getProvinceName()+customerAddress.getCity().getCityName()+customerAddress.getDistrict().getDistrictName());
             //kidd新加备注
             orderAddress.setProviceFirstStageName(customerAddress.getProvince().getProvinceName());
             orderAddress.setAddressCitySecondStageName(customerAddress.getCity().getCityName());
             orderAddress.setAddressCountiesThirdStageName(customerAddress.getDistrict().getDistrictName());
         }
	        if (typeId == null || typeId == 0) {
	            // 表示快递方式选择为快递配送
	            typeId = 0L;
	        } else {
	            // 标识配送方式为快递配送
	            deliveryPoint = deliveryPointService.getDeliveryPoint(deliveryPointId);
	        }
        } else {
            // 城市
            orderAddress.setAddressCitySecondStageName(new String(orderAddress.getAddressCitySecondStageName().getBytes("ISO-8859-1"), ConstantUtil.UTF));
            // 区县
            orderAddress.setAddressCountiesThirdStageName(new String(orderAddress.getAddressCountiesThirdStageName().getBytes("ISO-8859-1"), ConstantUtil.UTF));
            // 详细地址
            orderAddress.setAddressDetail(new String(orderAddress.getAddressDetail().getBytes("ISO-8859-1"), ConstantUtil.UTF));
            // 省市县详细地址
            orderAddress.setAddressDetailInfo(new String(orderAddress.getAddressDetailInfo().getBytes("ISO-8859-1"), ConstantUtil.UTF));
            // 收货人名称
            orderAddress.setAddressName(new String(orderAddress.getAddressName().getBytes("ISO-8859-1"), ConstantUtil.UTF));
            // 收货人手机
            orderAddress.setAddressPhone(new String(orderAddress.getAddressPhone().getBytes("ISO-8859-1"), ConstantUtil.UTF));
            // 省
            orderAddress.setProviceFirstStageName(new String(orderAddress.getProviceFirstStageName().getBytes("ISO-8859-1"), ConstantUtil.UTF));
        }
        ModelAndView mav=null;
//        if(customerAddress==null){
//            mav= new ModelAndView("order/new_add_address");
//        }else{
//            mav= new ModelAndView("order/suborder");
//        }
        mav= new ModelAndView("order/suborder");
        //优惠券
        Coupon coupon=null;
        if(StringUtils.isNotEmpty(codeNo)){
            coupon=couponService.selectCouponByCodeNo(codeNo);
        }
        //用户地址列表
        mav.addObject("addresses", customerAddressService.queryCustomerAddress(customerId));
        //发票类型
        mav.addObject("invoiceType",invoiceType);
        //发票抬头
        mav.addObject("invoiceTitle",invoiceTitle);
        //自提点
        mav.addObject("deliveryPointId",deliveryPointId);
        //运费模板
        mav.addObject("ch_express",ch_express).addObject("coupon",coupon);
        mav.addObject("map", map).addObject("sx", request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME)).addObject("customer", customerAddress).addObject("typeId", typeId).addObject("dps", deliveryPoint).addObject("ch_pay", ch_pay).addObject("payList", paymentService.selectAllForSite()).addObject("orderAddress", orderAddress);
      
        return seoService.getCurrSeo(mav);
    }

    /**
     * 设置配送方式和支付方式
     * @return
     */
    @RequestMapping("toFreightAndPay")
    public ModelAndView toFreightAndPay(HttpServletRequest request,Long typeId,Long addressId,Long ch_pay,Long deliveryPointId){
        Long customerId=(Long)request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        if (customerId == null) {
            return new ModelAndView(new RedirectView("../login.html"));
        }
        if(customerId!=null){
            if(typeId==null){
                typeId=0L;
            }
            CustomerAddress customerAddress = null;
            // 查询收货地址
            if(customerId!=null){
                customerAddress = addressService.queryDefaultAddr(customerId);
            }
            if(null==customerAddress){
                //查询上一次收货地址
                customerAddress = addressService.selectByCIdFirst(customerId);
            }
           Long[] box=(Long[])request.getSession().getAttribute("box");
            //查询货品信息
            Map<String, Object> map = shoppingCartService.newsubOrder(request, box, customerAddress);
            ModelAndView mav=new ModelAndView("order/payType");
            mav.addObject("map", map).addObject("sx", request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME)).addObject("typeId",typeId).addObject("ch_pay2", request.getSession().getAttribute("ch_pay")).addObject("payList", paymentService.selectAllForSite());
            //订单收货地址
            mav.addObject("addressId", addressId);
            //支付方式
            mav.addObject("ch_pay",ch_pay);
            //自提点id
            mav.addObject("deliveryPointId",deliveryPointId);
            //获取自提点对象的信息
            mav.addObject("expressConf",expressConfDaoImpl.getExpressConfByUsedField());
            //订单收货地址
            CustomerAddress address;
            // 查询收货地址
            if (addressId != null) {
                address = addressService.queryCustomerAddressById(addressId, customerId);
            } else {
                address = addressService.selectByCIdFirst((Long) customerId);
            }
            //收货地址当前城市所有的自提点
            List<DeliveryPoint> dps=null;
            if(address!=null){
                dps=deliveryPointService.selectDeliveryPointBycityId(Long.valueOf(address.getInfoCity()));
            }
            mav.addObject("dps",dps);
            return seoService.getCurrSeo(mav);
        }else {
            return new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
    }

    /**
     * 查看个人所有的购物券
     * @return
     */
    @RequestMapping("tocouponlist")
    public ModelAndView tocouponlist(HttpServletRequest request,Long typeId,Long addressId,Long ch_pay,Long deliveryPointId,String codeNo){
        Long customerId=(Long)request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        if (customerId == null) {
            return new ModelAndView(new RedirectView("../login.html"));
        }
        if(customerId!=null){
            if(typeId==null){
                typeId=0L;
            }
            Long[] box=(Long[])request.getSession().getAttribute("box");
            // 查询地区
            ShoppingCartWareUtil wareUtil = shoppingCartService.selectPNameByParam(request);
            ModelAndView mav=new ModelAndView("order/couponlist");
            mav.addObject("sx", request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME)).addObject("typeId",typeId).addObject("ch_pay2", request.getSession().getAttribute("ch_pay"));
            //订单收货地址
            mav.addObject("addressId",addressId);
            //支付方式
            mav.addObject("ch_pay",ch_pay);
            //自提点id
            mav.addObject("deliveryPointId",deliveryPointId);
            mav.addObject("codeNo",codeNo);
            //订单收货地址
            List<Coupon> couponlist = shoppingCartService.getUsedCouponlist(request,box);
            mav.addObject("couponlist",couponlist);
            return seoService.getCurrSeo(mav);
        }else {
            return new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
    }


    //查看货品详情
    @RequestMapping("toproductsList")
    public ModelAndView toproductsList(HttpServletRequest request){
        Long customerId=(Long)request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        if (customerId == null) {
            return new ModelAndView(new RedirectView("../login.html"));
        }
        if(customerId!=null){
            CustomerAddress customerAddress = null;
            // 查询收货地址
            if(customerId!=null){

                customerAddress = addressService.queryDefaultAddr(customerId);
            }
            if(null==customerAddress){
                //查询上一次收货地址
                customerAddress = addressService.selectByCIdFirst(customerId);
            }
            Long[] box=(Long[])request.getSession().getAttribute("box");
            //查询货品信息
            Map<String, Object> map = shoppingCartService.newsubOrder(request, box, customerAddress);
            ModelAndView mav=new ModelAndView("order/productsList");
            mav.addObject("map",map);
            return seoService.getCurrSeo(mav);
        }else {
            return new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
    }
    /**
     * 查询订单优惠
     * 
     * @param goodsIds
     * @return int
     */
    @RequestMapping("/selectordermarketingbygoodsids")
    @ResponseBody
    public List<Marketing> selectordermarketingbygoodsids(Long[] goodsIds) {
        return null;
    }

    /**
     * 修改优惠
     * 
     * @param shoppingCartId
     * @param orderMarketId
     * @return int
     */
    @RequestMapping("/changeshoppingcartordermarkets")
    @ResponseBody
    public int changeshoppingcartordermarkets(Long shoppingCartId, Long orderMarketId) {
        return 0;
    }

    /**
     * 提交订单
     * 
     * @param shoppingCartId
     *            所购买的的货品ID
     * @param codeNo
     *            订单使用的优惠券
     * @param addressId
     *            收货地址ID
     * @param ch_pay
     *            付款方式
     * @param ch_express
     *            配送方式ID
     * @param  deliveryPointId
     *              自提点Id
     *@param  point
     *              兑换积分
     * @return ModelAndView
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/submitorder")
    public ModelAndView submitOrder(HttpServletRequest request,Long point,
                                    String invoiceType,String invoiceTitle,
                                    Long  deliveryPointId, Long[] shoppingCartId,
                                    String codeNo, Long addressId, Long ch_pay,
                                    Long ch_express,
                                    HttpServletResponse response, Long[] marketingId,
                                    Long[] thirdId,
                                    Long typeId, OrderAddress orderAddress) throws UnsupportedEncodingException {

        if(point!=null && point < 0) {
            throw new RuntimeException("参数格式不正确！");
        }
        //用户编号
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        //是否登录
        if (customerId == null) {
            //返回登录页
            return new ModelAndView(new RedirectView("../login.html"));
        }
        // 当前选择的地址
        ShoppingCartWareUtil wareUtil = shoppingCartService.selectPNameByParam(request);
        List<Order> orders = null;
        //保存订单
        orders = siteOrderService.newsubmitOrder(point, invoiceType, invoiceTitle, request, shoppingCartId, typeId, orderAddress, deliveryPointId);
        
        //返回购物车
        if (orders == null) {
            return new ModelAndView(new RedirectView(request.getContextPath()+ "/myshoppingmcart.htm"));
        }
        Order order = orders.get(0);
        //王定修改注释
        request.getSession().removeAttribute("box");
        // 推送消息
        orderPayService.sendOrderRe(order, request, response);
        for(int i=0;i<orders.size();i++){
            Order or = orderPayService.queryGoodsProducts(orders.get(i).getOrderId());
            orders.set(i,or);
        }
        //订单成功页
        ModelAndView mav = new ModelAndView("order/check_out_de");
        //订单信息
        mav.addObject("orders", orders);
        mav.addObject("order", orders.get(0));
        //支付方式列表
        mav.addObject("paylist", payService.queryAllPaySet());
        return seoService.getCurrSeo(mav);
    }

    /**
     * 去付款
     * 
     * @param orderId
     * @return ModelAndView
     */
    @RequestMapping("gopayorder")
    public ModelAndView gopayorder(Long orderId) {
        ModelAndView mav = new ModelAndView("order/orderdetail").addObject("order", siteOrderService.getPayOrder(orderId)).addObject("paylist", payService.queryAllPaySet());
        return seoService.getCurrSeo(mav);
    }

    /**
     * 确认付款
     * 
     * @param orderId
     * @param request
     * @return ModelAndView
     */
    @RequestMapping("/payorder")
    public ModelAndView payOrder(Long orderId, String orderCode, HttpServletRequest request, Long payId, HttpServletResponse response) {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {
            return new ModelAndView(new RedirectView("../login.html"));
        }
        // 返回订单商品的商品名称，取第一个
        String goodsName = orderser.queryGoodsInfoName(orderId);
        ModelAndView mav = new ModelAndView();
        Order order = siteOrderService.getPayOrder(orderId);
        // 查询使用的支付信息
        Pay p = payService.findByPayId(payId);
        if (p != null) {
            if ("1".equals(p.getPayType())) {
                /*** 更新支付id ***/
                Order ordVo = new Order();
                ordVo.setPayId(payId);

                AlipayConfig.partner = p.getApiKey();
                AlipayConfig.key = p.getSecretKey();
                // 支付宝网关地址
                String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

                // //////////////////////////////////调用授权接口alipay.wap.trade.create.direct获取授权码token//////////////////////////////////////

                // 卖家支付宝帐户
                String seller_email = p.getPayAccount();
                // 必填
                // 商户订单号
                String out_trade_no = order.getOrderCode();
                // 商户网站订单系统中唯一订单号，必填
                // 订单名称
                String subject = goodsName;
                // 必填
                // 付款金额
                String total_fee = order.getOrderPrice().toString();
                // 需以http://开头的完整路径，例如：http://localhost/myorder.html
                // 防钓鱼时间戳
                // 非局域网的外网IP地址，如：221.0.0.1
                // 返回格式
                String format = "xml";
                // 必填，不需要修改

                // 返回格式
                String v = "2.0";
                // 必填，不需要修改

                // 请求号
                String req_id = UtilDate.getOrderNum();
                // 必填，须保证每次请求都是唯一

                // req_data详细信息

                // 需http://格式的完整路径，不能加?id=123这类自定义参数

                // 页面跳转同步通知页面路径
                String call_back_url = p.getPayComment()+"paysucccess.htm";
                // 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

                // 操作中断返回地址
                String merchant_url = p.getPayComment();
                // 用户付款中途退出返回商户的地址。需http://格式的完整路径，不允许加?id=123这类自定义参数

                // 服务器异步通知页面路径
                // 需http://格式的完整路径，不能加?id=123这类自定义参数
                String notify_url = p.getPayUrl() + "mobile/paysucccessybm.htm";

                // 商户网站订单系统中唯一订单号，必填
                // 请求业务参数详细
                String req_dataToken = "<direct_trade_create_req><notify_url>" + notify_url + "</notify_url><call_back_url>" + call_back_url + "</call_back_url><seller_account_name>" + seller_email + "</seller_account_name><out_trade_no>" + out_trade_no + "</out_trade_no><subject>" + subject + "</subject><total_fee>" + total_fee + "</total_fee><merchant_url>" + merchant_url + "</merchant_url></direct_trade_create_req>";
                // 必填
                // 把请求参数打包成数组
                Map<String, String> sParaTempToken = new HashMap<String, String>();
                sParaTempToken.put("service", "alipay.wap.trade.create.direct");
                sParaTempToken.put("partner", AlipayConfig.partner);
                sParaTempToken.put("_input_charset", AlipayConfig.input_charset);
                sParaTempToken.put("sec_id", AlipayConfig.sign_type);
                sParaTempToken.put("format", format);
                sParaTempToken.put("v", v);
                sParaTempToken.put("req_id", req_id);
                sParaTempToken.put("req_data", req_dataToken);

                // 建立请求
                String sHtmlTextToken = "";
                try {
                    sHtmlTextToken = AlipaySubmitM.buildRequest(ALIPAY_GATEWAY_NEW, "", "", sParaTempToken);
                } catch (Exception e2) {
                    Customer cust = (Customer) request.getSession().getAttribute("cust");
                    OperaLogUtil.addOperaException(cust.getCustomerUsername(), e2, request);
                }
                // URLDECODE返回的信息
                try {
                    sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, AlipayConfig.input_charset);
                } catch (UnsupportedEncodingException e1) {
                    Customer cust = (Customer) request.getSession().getAttribute("cust");
                    OperaLogUtil.addOperaException(cust.getCustomerUsername(), e1, request);
                }
                // 获取token
                String request_token = "";
                try {
                    request_token = AlipaySubmitM.getRequestToken(sHtmlTextToken);
                } catch (Exception e) {
                    Customer cust = (Customer) request.getSession().getAttribute("cust");
                    OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
                }
                // //////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////

                // 业务详细
                String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
                // 必填

                // 把请求参数打包成数组
                Map<String, String> sParaTemp = new HashMap<String, String>();
                sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
                sParaTemp.put("partner", AlipayConfig.partner);
                sParaTemp.put("_input_charset", AlipayConfig.input_charset);
                sParaTemp.put("sec_id", AlipayConfig.sign_type);
                sParaTemp.put("format", format);
                sParaTemp.put("v", v);
                sParaTemp.put("req_data", req_data);

                // 建立请求
                String sHtmlText = AlipaySubmitM.buildRequest(ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认");
                // 建立请求
                mav.addObject("sHtmlText", sHtmlText);
                mav.setViewName("order/netbank");
            } else {
                // 获取openid

                String ip = request.getRemoteAddr();
                // 订单信息
                mav.addObject("order", order);
                // ip地址
                mav.addObject("ip", ip);
                // 订单id
                mav.addObject(ConstantUtil.ORDERID, orderId);
                // 商品名称
                mav.addObject("goodsInfoName", goodsName);
                // 订单价格
                mav.addObject("payPrice", order.getOrderPrice().toString().replace(".", ""));
                // 回调函数
                mav.addObject("callBackUrl", "http://shop.ningpai.com/mobile/wxpaysuc.htm");

                mav.setViewName("pay/pay");
            }
        }
        return seoService.getCurrSeo(mav);

    }
    
    /**
     * 获取微信参数
     * @param orderId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getwxparam")
    @ResponseBody
    public Map<String, Object> getWXParam(Long orderId,HttpServletRequest request,HttpServletResponse response){
        //微信配置
        Pay pay=payService.findByPayId(37L);
        //订单
        Order order=orderser.getPayOrder(orderId);
        //商品名称
        String goodsName = orderser.queryGoodsInfoName(orderId);
        return siteOrderService.getWXUrl(request, response, order, pay,goodsName);
    }

    /**
     * 支付成功
     * 
     * @param request
     * @param agr1
     * @return ModelAndView
     */
    @RequestMapping("/paysucccess")
    public ModelAndView paySuccess(HttpServletRequest request, HttpServletResponse agr1, HttpServletResponse response) {
        Pay p=payService.findByPayId(25L);
        //设置商户号
        AlipayConfig.partner = p.getApiKey();
        //设置商户秘钥
        AlipayConfig.key = p.getSecretKey();
        Map<String, String> params = new HashMap<String, String>();
        // 获取返回的Map
        Map<?, ?> requestParams = request.getParameterMap();
        // 循环取出
        for (@SuppressWarnings("rawtypes")
        Map.Entry entrySet : requestParams.entrySet()) {
            String[] values = (String[]) entrySet.getValue();
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr.concat(values[i]) : valueStr.concat(values[i]).concat(",");
            }
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), ConstantUtil.UTF);
                // 根据名字 和value 存入Map
                params.put(entrySet.getKey().toString(), valueStr);
            } catch (UnsupportedEncodingException e) {
                Customer cust = (Customer) request.getSession().getAttribute("cust");
                OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
            }
        }

        // 订单号
        String orderCode = request.getParameter("out_trade_no");
        // 重新将session登入
        Order or = siteOrderService.getPayOrderByCode(orderCode);
        // 更新订单状态
        if(or.getOrderStatus().equals("0")){
            siteOrderService.payOrder(or.getOrderId());
        }
        if ("2".equals(or.getOrderMType())) {
            Customer customer = customerServiceMapper.queryCustomerInfo(or.getCustomerId());
            // 返回订单商品的商品名称，取第一个
            String goodsName = orderser.queryGoodsInfoName(or.getOrderId());
            // 微信发货通知
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put(ConstantUtil.OPENID, customer.getCustomerUsername());
            paraMap.put("orderNo", or.getOrderCode());
            paraMap.put("orderPrice", or.getOrderPrice());
            paraMap.put("goodsName", goodsName);
            paraMap.put(ConstantUtil.ORDERID, or.getOrderId());
            WXSendMSG.sendWxMsgForOrderPaySucc(paraMap, request, response);
        }
        if ("4".equals(or.getOrderCargoStatus())) {
            orderser.modifyOrderByKey(or.getOrderId(), "2");
        }
        ModelAndView mav = new ModelAndView("order/pay_success").addObject("order", or);
        return seoService.getCurrSeo(mav);
    }

    /**
     * 微信回调
     * 
     * @param request
     * @throws IOException 
     */
    @RequestMapping("/wxpaysuc")
    public void wxpaySuc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        // xml解析
        Document document;
        try {
            document = DocumentHelper.parseText(sb.toString());
            Element root = document.getRootElement();
            //微信号
            String appId="";
            //订单号
            String outTradeNo="";
            //返回结果
            String resultCode="";
            //商户编号
            String mId="";
            List<Element> elements = root.elements();
            for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
                Element element = it.next();
                if ("appid".equals(element.getName())){
                    appId=element.getText(); 
                }else if ("bank_type".equals(element.getName())){
                    element.getText(); 
                }else if ("mch_id".equals(element.getName())){
                    mId=element.getText(); 
                }else if ("result_code".equals(element.getName())){
                    resultCode=element.getText(); 
                }else if ("out_trade_no".equals(element.getName())){
                    outTradeNo=element.getText(); 
                }
            }
            Order or = siteOrderService.getPayOrderByCode(outTradeNo);
            Pay pay=payService.findByPayId(37L);
            //判断返回结果
            if("SUCCESS".equals(resultCode)){
                //判断商户号与财付通账号是否匹配
                if(appId.equals(pay.getApiKey())&&mId.equals(pay.getPartner())){
                    //判断订单状态
                    if(or.getOrderStatus().equals("0")){
                        siteOrderService.payOrder(or.getOrderId());
                        sendSucess(response, "SUCCESS");
                    }
                }
            }
        } catch (DocumentException e1) {
            LOGGER.error("微信支付错误："+e1);
            sendSucess(response, "FAIL");
        }
    }

    public void sendSucess(HttpServletResponse response,String msg) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.flush();
        out.close();
    }

    /**
     * 支付成功
     * 
     * @param request
     * @param agr1
     * @return ModelAndView
     */
    @RequestMapping("/paysucccesswx")
    public ModelAndView paySuccessWx(HttpServletRequest request, HttpServletResponse agr1, String orderCodex) {
        // 重新将session登入
        Order or = siteOrderService.getPayOrderByCode(orderCodex);
        ModelAndView mav = new ModelAndView("order/pay_success").addObject("order", or);
        return seoService.getCurrSeo(mav);

    }

    /**
     * 根据商品id查询购买过该商品的记录
     * 
     * @return
     */
    @RequestMapping("/addcartsuc")
    public ModelAndView addCartSuc(Long goodsInfoId) {
        List<GoodsProductVo> goodsProductVos = orderser.queryGoodsProductVoByOrderGoods(goodsInfoId);
        return seoService.getCurrSeo(new ModelAndView(ShoppingCartConstants.ADDCART).addObject("list", goodsProductVos));
    }

    /**
     * 根据订单id进行支付
     * 
     * @param orderId
     *            订单id
     * @return
     */
    @RequestMapping("/orderdetailpay")
    public ModelAndView orderDetailPay(Long orderId) {
        Order order = orderPayService.queryGoodsProducts(orderId);
        ModelAndView mav = new ModelAndView("order/check_out_de2");
        mav.addObject("order", order);
        mav.addObject("paylist", payService.queryAllPaySet());
        mav.addObject("coupon", couponService.selectCouponByCodeNo(order.getCouponNo()));
        return seoService.getCurrSeo(mav);
    }

    /**
     * 支付宝回调
     * 
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/paysucccessybm")
    public void paySuccessyb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取支付宝信息
        Pay p=payService.findByPayId(25L);

        //设置商户号
        AlipayConfig.partner = p.getApiKey();
        //设置商户秘钥
        AlipayConfig.key = p.getSecretKey();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            params.put(name, valueStr);
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

        // RSA签名解密
        if ("0001".equals(AlipayConfig.sign_type)) {
            params = AlipayNotify.decrypt(params);
        }
        Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
        Element root = doc_notify_data.getRootElement();
        Element element=root.element("out_trade_no");
        System.out.print("订单编号"+element.getText());
        Element status_element=root.element("trade_status");
        String out_trade_no = "";
        String trade_status = "";
        if(element!=null&&!"".equals(element)){
            // 商户订单号
            out_trade_no = element.getText();
        }

        if(status_element!=null&&!"".equals(status_element)){
            // 商户订单号
            trade_status =status_element.getText();
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if (AlipayNotify.verifyNotify(params)) {// 验证成功
            // ////////////////////////////////////////////////////////////////////////////////////////
            // 请在这里加上商户的业务逻辑程序代码

            // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if ("TRADE_FINISHED".equals(trade_status)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序
                // 注意：
                // 该种交易状态只在两种情况下出现
                // 1、开通了普通即时到账，买家付款成功后。
                // 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
                Order or = siteOrderService.getPayOrderByCode(out_trade_no);
                if ("0".equals(or.getOrderStatus())) {
                    // 更新订单状态
                    siteOrderService.payOrder(or.getOrderId());
                }
                System.out.println("success"); // 请不要修改或删除
            } else if ("TRADE_SUCCESS".equals(trade_status)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序
                // 注意：
                // 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
                Order or = siteOrderService.getPayOrderByCode(out_trade_no);
                if ("0".equals(or.getOrderStatus())) {
                    // 更新订单状态
                    siteOrderService.payOrder(or.getOrderId());
                }
                sendSucess(response, "success");
            }
            // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            // ////////////////////////////////////////////////////////////////////////////////////////
        } else {// 验证失败
            sendSucess(response, "fail");
        }

    }

    
    /**
     * 查询所有的支付方式
     * 
     * @return 查询到的支付方式的列表
     */
    @RequestMapping("/queryAllPaySet")
    @ResponseBody
    public List<Object> queryAllPaySet() {
        return this.payService.queryAllPaySet();
    }

    private static final MyLogger LOGGER=new MyLogger(OrderMController.class);
    public PayService getPayService() {
        return payService;
    }

    @Resource(name = "payService")
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    public ExpressConfBizImpl getExpressConfBizImpl() {
        return expressConfBizImpl;
    }

    @Resource(name = "expressConfBizImpl")
    public void setExpressConfBizImpl(ExpressConfBizImpl expressConfBizImpl) {
        this.expressConfBizImpl = expressConfBizImpl;
    }

    public ShoppingCartService getShoppingCartService() {
        return shoppingCartService;
    }

    @Resource(name = "ShoppingCartService")
    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public OrderMService getSiteOrderService() {
        return siteOrderService;
    }

    @Resource(name = "OrderMService")
    public void setSiteOrderService(OrderMService siteOrderService) {
        this.siteOrderService = siteOrderService;
    }

    public MarketingService getMarketingService() {
        return marketingService;
    }

    @Resource(name = "MarketingService")
    public void setMarketingService(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    public OrderService getOrderser() {
        return orderser;
    }

    @Resource(name = "OrderService")
    public void setOrderser(OrderService orderser) {
        this.orderser = orderser;
    }

    public GoodsProductService getGoodsProductService() {
        return goodsProductService;
    }

    @Resource(name = "GoodsProductService")
    public void setGoodsProductService(GoodsProductService goodsProductService) {
        this.goodsProductService = goodsProductService;
    }
    
    public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
    @Resource(name = "customerAddressServiceM")
    private CustomerAddressService addressService;

    @Resource(name = "SeoService")
    private SeoService seoService;
    @Resource(name="FreightTemplateService")
    private FreightTemplateService freightTemplateService;
    @Resource(name="GoodsGroupService")
    private GoodsGroupService goodsGroupService;


    @Resource(name="customerAddressServiceM")
    private CustomerAddressService customerAddressService;

}
