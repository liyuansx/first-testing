/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.shoppingcart.dao.impl;

import java.util.List;
import java.util.Map;

import com.ningpai.m.shoppingcart.bean.StoreTemp;
import org.springframework.stereotype.Repository;

import com.ningpai.m.shoppingcart.bean.ShoppingCart;
import com.ningpai.m.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * @author ggn
 * 
 */
@Repository("ShoppingCartMapper")
public class ShoppingCartMapperImpl extends BasicSqlSupport implements
        ShoppingCartMapper {

    /*
     * 
     * 
     * @see
     * com.ningpai.shoppingcart.dao.ShoppingCartMapper#shoppingCart(java.lang
     * .Long)
     */
    public List<Object> shoppingCart(Map<String, Object> paramMap) {
        return this.selectList(
                        "com.ningpai.web.dao.ShoppingCartMapper.shoppingCart",
                        paramMap);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#shoppingCartCount
     * (java.util.Map)
     */
    public int shoppingCartCount(Map<String, Object> paramMap) {
        return this.selectOne(
                "com.ningpai.web.dao.ShoppingCartMapper.shoppingCartCount",
                paramMap);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#shoppingCartMini
     * (java.lang.Long)
     */
    @Override
    public List<ShoppingCart> shoppingCartMini(Long customerId) {
        return this.selectList(
                "com.ningpai.web.dao.ShoppingCartMapper.shoppingCartMini",
                customerId);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#delShoppingCartById
     * (java.lang.Long)
     */
    @Override
    public int delShoppingCartById(Long shoppingCartId) {
        return this.update(
                "com.ningpai.web.dao.ShoppingCartMapper.delShoppingCartById",
                shoppingCartId);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#changeShoppingCartById
     * (com.ningpai.site.shoppingcart.bean.ShoppingCart)
     */
    @Override
    public int changeShoppingCartById(ShoppingCart sc) {
        return this.update("com.ningpai.web.dao.ShoppingCartMapper.changeShoppingCartById",
                        sc);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#changeShoppingCartMarket
     * (com.ningpai.site.shoppingcart.bean.ShoppingCart)
     */
    @Override
    public int changeShoppingCartMarket(ShoppingCart sc) {
        return this.update("com.ningpai.web.dao.ShoppingCartMapper.changeShoppingCartMarket",
                        sc);
    }

    @Override
    public List<StoreTemp> selectStoreTempByshopcartIds(List<Long> shopcartIds) {
        return this.selectList("com.ningpai.web.dao.ShoppingCartMapper.selectStoreTempByshopcartIds", shopcartIds);
    }

    @Override
    public List<ShoppingCart> shopCartListByIds(List<Long> list) {

        return this.selectList("com.ningpai.web.dao.ShoppingCartMapper.shopCartListByIds", list);
    }

    @Override
    public List<ShoppingCart> shopCartListGroupByMarkettingId(List<Long> list) {
        return this.selectList(
                "com.ningpai.web.dao.ShoppingCartMapper.shopCartListGroupByMarkettingId",
                list);
    }
    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#shoppingCartListByIds
     * (java.util.List)
     */
    @Override
    public List<ShoppingCart> shoppingCartListByIds(List<Long> list) {
        return this.selectList(
                "com.ningpai.web.dao.ShoppingCartMapper.shoppingCartListByIds",
                list);
    }


    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#addShoppingCart(
     * com.ningpai.site.shoppingcart.bean.ShoppingCart)
     */
    @Override
    public int addShoppingCart(ShoppingCart shoppingCart) {
        return this.insert(
                "com.ningpai.web.dao.ShoppingCartMapper.addShoppingCart",
                shoppingCart);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#deleteShoppingCartByIds
     * (java.util.List)
     */
    @Override
    public int deleteShoppingCartByIds(List<Long> list) {
        return this.update("com.ningpai.web.dao.ShoppingCartMapper.deleteShoppingCartByIds",
                        list);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#selectCountByReady
     * (com.ningpai.site.shoppingcart.bean.ShoppingCart)
     */
    @Override
    public int selectCountByReady(ShoppingCart shoppingCart) {
        return this.selectOne(
                "com.ningpai.web.dao.ShoppingCartMapper.selectCountByReady",
                shoppingCart);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#updateShoppingCart
     * (com.ningpai.site.shoppingcart.bean.ShoppingCart)
     */
    @Override
    public int updateShoppingCart(ShoppingCart shoppingCart) {
        return this.update(
                "com.ningpai.web.dao.ShoppingCartMapper.updateShoppingCart",
                shoppingCart);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#selectSumByCustomerId
     * (java.lang.Long)
     */
    @Override
    public int selectSumByCustomerId(Long customerId) {
        return this.selectOne(
                "com.ningpai.web.dao.ShoppingCartMapper.selectSumByCustomerId",
                customerId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#
     * changeShoppingCartOrderMarket
     * (com.ningpai.site.shoppingcart.bean.ShoppingCart)
     */
    @Override
    public int changeShoppingCartOrderMarket(ShoppingCart shoppingCart) {
        return this.update("com.ningpai.web.dao.ShoppingCartMapper.changeShoppingCartOrderMarket",
                        shoppingCart);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.shoppingcart.dao.ShoppingCartMapper#selectPNameByParam
     * (java.lang.Long)
     */
    @Override
    public String selectPNameByParam(Long districtId) {
        return this.selectOne(
                "com.ningpai.web.dao.ShoppingCartMapper.selectPNameByParam",
                districtId);
    }

   /*
    * 
    * @see com.ningpai.m.shoppingcart.dao.ShoppingCartMapper#selectLastId(com.ningpai.m.shoppingcart.bean.ShoppingCart)
    */
    @Override
    public Long selectLastId(ShoppingCart shoppingCart) {
        return this.selectOne(
                "com.ningpai.web.dao.ShoppingCartMapper.selectLastId",shoppingCart);
    }

    /*
     * 
     * @see com.ningpai.m.shoppingcart.dao.ShoppingCartMapper#selectShopingByParam(com.ningpai.m.shoppingcart.bean.ShoppingCart)
     */
    @Override
    public ShoppingCart selectShopingByParam(ShoppingCart shoppingCart) {
        return this.selectOne(
                "com.ningpai.web.dao.ShoppingCartMapper.selectShopingByParam",shoppingCart);
    }

}
