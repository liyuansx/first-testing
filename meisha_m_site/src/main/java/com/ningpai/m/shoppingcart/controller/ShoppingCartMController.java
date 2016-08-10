/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.shoppingcart.controller;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.shoppingcart.bean.ShoppingCart;
import com.ningpai.m.shoppingcart.bean.ShoppingCartWareUtil;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.util.PageBean;
import com.ningpai.util.ShoppingCartConstants;

/**
 * 购物车控制器
 * 
 * @author NINGPAI-LIH
 *
 */
@Controller
public class ShoppingCartMController {

	/**
	 * 查询购物车
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("myshoppingmcart")
	public ModelAndView shoppingCart(HttpServletRequest request,
			PageBean pageBean, HttpServletResponse response) {
		ShoppingCartWareUtil wareUtil = null;
		ModelAndView mav = null;
		request.getSession().setAttribute("tok", UUID.randomUUID().toString());
		try {
			// 获取地址信息
			wareUtil = shoppingCartService.selectPNameByParam(request);
			mav = new ModelAndView(ShoppingCartConstants.SHOPPINGMCARTLIST)
					.addObject(
							"pb",
							shoppingCartService.selectShoppingCart(request,
									wareUtil, pageBean, response))
					.addObject("pro", marketingService.selectAll())
					.addObject("sx",
							request.getSession().getAttribute("tok").toString());
			return seoService.getCurrSeo(mav);
		} finally {
			wareUtil = null;
			mav = null;
		}
	}

	/**
	 * 修改购物数量
	 * 
	 * @param shoppingCartId
	 * @param num
	 * @return int
	 */
	@RequestMapping("changeshoppingmcartbyid")
	@ResponseBody
	public int changeShoppingCartById(Long shoppingCartId, Long num) {
		return shoppingCartService.changeShoppingCartById(shoppingCartId, num);
	}

	/**
	 * 删除购物车商品
	 * 
	 * @param request
	 * @return int
	 */
	@RequestMapping("delshoppingcartbyid")
	@ResponseBody
	public int delShoppingCartById(Long shoppingCartId, Long goodsInfoId,
			HttpServletRequest request, HttpServletResponse response) {
		return shoppingCartService.delShoppingCartById(shoppingCartId,
				goodsInfoId, request, response);
	}
	/**
	 * 添加购物车的控制器
	 * 
	 * @param goodsNum
	 *            购买货品的数量
	 * @param productId
	 *            货品ID
	 * @param request
	 *            请求对象
	 * @return true 添加成功 false 添加失败
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/addProductToShopmCar")
	@ResponseBody
	public boolean addProductToShopCar(Long goodsNum, Long productId,
			HttpServletRequest request, Long distinctId,
			HttpServletResponse response, Long fitId)
			throws UnsupportedEncodingException {
		ShoppingCart shoppingCart = new ShoppingCart();
		try {
			shoppingCart.setDistinctId(null == distinctId ? 1103L : distinctId);
			shoppingCart.setGoodsInfoId(productId);
			shoppingCart.setGoodsNum(goodsNum);
			return this.shoppingCartService.addShoppingCart(shoppingCart,
					request, response) >= 1 ? true : false;
		} finally {
			shoppingCart = null;
		}
	}

	/**
	 * 立即加入购物车
	 * 
	 * @param goodsNum
	 *            购买货品的数量
	 * @param productId
	 *            货品ID
	 * @param request
	 *            请求对象
	 * @return true 添加成功 false 添加失败
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/addproducttoshopmcarl")
	public ModelAndView addProductToShopCarL(Long goodsNum, Long productId,
			HttpServletRequest request, Long distinctId,
			HttpServletResponse response, Long fitId)
			throws UnsupportedEncodingException {
		ShoppingCart shoppingCart = new ShoppingCart();
		try {
			shoppingCart.setDistinctId(null == distinctId ? 1103L : distinctId);
			shoppingCart.setGoodsInfoId(productId);
			shoppingCart.setGoodsNum(goodsNum);
			Long custId = (Long) request.getSession()
					.getAttribute("customerId");
			ModelAndView mav = null;
			if (custId == null) {
				mav = new ModelAndView("redirect:/login.html?url=/item/"
						+ productId + ".html");
				return seoService.getCurrSeo(mav);
			}
			shoppingCart.setCustomerId(custId);
			Long shopping = shoppingCartService.selectLastId(shoppingCart,
					response, request);
			if (shopping == 0) {
				mav = seoService.getCurrSeo(new ModelAndView(new RedirectView(
						"order/myshoppingmcart.html")));
			} else {
				mav = seoService.getCurrSeo(new ModelAndView(new RedirectView(
						"order/suborder.html")).addObject("box", shopping));
			}
			return seoService.getCurrSeo(mav);
		} finally {
			shoppingCart = null;
		}
	}

	/**
	 * 添加购物车的控制器
	 * 
	 * @param goodsNum
	 *            购买货品的数量
	 * @param productId
	 *            货品ID
	 * @param request
	 *            请求对象
	 * @return 添加购物车成功页面
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/addproducttoshopcarnew")
	public ModelAndView addProductToShopCarNew(Long goodsNum, Long productId,
			HttpServletRequest request, Long distinctId,
			HttpServletResponse response, Long fitId)
			throws UnsupportedEncodingException {
		ShoppingCart shoppingCart = new ShoppingCart();
		try {
			shoppingCart.setDistinctId(null == distinctId ? 1103L : distinctId);
			shoppingCart.setGoodsInfoId(productId);
			shoppingCart.setGoodsNum(goodsNum);
			this.shoppingCartService.addShoppingCart(shoppingCart, request,
					response);
			return seoService.getCurrSeo(new ModelAndView(new RedirectView(
					"addcartsuc.htm")).addObject("goodsInfoId", productId));
		} finally {
			shoppingCart = null;
		}
	}

	@Resource(name = "ShoppingCartService")
	private ShoppingCartService shoppingCartService;

	@Resource(name = "MarketingService")
	private MarketingService marketingService;

	@Resource(name = "SeoService")
	private SeoService seoService;

}
