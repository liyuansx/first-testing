/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.dao.impl;

import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.goods.vo.ListFinalBuyVo;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 货品DAO实现
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月8日 下午6:21:16
 * @version 1.0
 */
@Repository("HsiteGoodsProductMapper")
public class GoodsProductMapperImpl extends BasicSqlSupport implements GoodsProductMapper {
    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsProductMapper#queryProductByGoodsId(java.lang .Long)
     */
    public List<Object> queryProductByGoodsId(Long goodsId) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryProductByGoodsId", goodsId);
    }


    @Override
    public GoodsProduct queryByGoodsInfoDetail(Long goodsInfoId) {
        return this.selectOne("com.ningpai.goods.dao.GoodsProductMapper.queryByGoodsInfoDetail", goodsInfoId);
    }
    @Override
    public List<GoodsProductVo> queryDetailByGroupId(Long groupId) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryDetailByGroupId", groupId);
    }
    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsProductMapper#queryPrductByProductId(java. lang.Long)
     */
    public GoodsProductVo queryPrductByProductId(Long productId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsProductMapper.queryPrductByProductId", productId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryTopSalesInfoByCatIds (java.util.Map)
     */
    public List<GoodsProduct> queryTopSalesInfoByCatIds(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryTopSalesInfoByCatIds", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryHotSalesByCatId(java.util.Map)
     */
    public List<GoodsProduct> queryHotSalesByCatId(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryHotSalesByCatId", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryTopNewInfoByCatIds (java.util.Map)
     */
    public List<GoodsProduct> queryTopNewInfoByCatIds(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryTopNewInfoByCatIds", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryDetailByProductId( java.lang.Long)
     */
    public GoodsProductVo queryDetailByProductId(Long productId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsProductMapper.queryDetailByProductId", productId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryFirstProductByGoodsId (java.lang.Long)
     */
    public GoodsProduct queryFirstProductByGoodsId(Long goodsId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsProductMapper.queryFirstProductByGoodsId", goodsId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryGroupProductByProductId (java.lang.Long)
     */
    public List<GoodsProduct> queryGroupProductByProductId(Long productId) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryGroupProductByProductId", productId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryProductsByProductIds(java.util.Map)
     */
    public List<GoodsProductVo> queryProductsByProductIds(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryProductsByProductIds", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#saveGoodsBrow(java.util.Map)
     */
    public int saveGoodsBrow(Map<String, Object> map) {
        return this.insert("com.ningpaimsite.goods.dao.GoodsProductMapper.saveGoodsBrow", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#saveAskComment(java.util.Map)
     */
    public int saveAskComment(Map<String, Object> map) {
        return this.insert("com.ningpaimsite.goods.dao.GoodsProductMapper.saveAskComment", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#browCatFinalBuyAndPrecent(java.util.Map)
     */
    public List<ListFinalBuyVo> browCatFinalBuyAndPrecent(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.browCatFinalBuyAndPrecent", map);
    }

    /*
     * + 
     * 
     * @see com.ningpai.site.goods.dao.GoodsProductMapper#queryTopSalesInfoByProductId(java.util.Map)
     */
    public List<GoodsProduct> queryTopSalesInfoByProductId(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsProductMapper.queryTopSalesInfoByProductId", map);
    }

    @Override
    public int insertExchangeCusmomer(Map<String, Object> map) {
        return this.insert("com.ningpaimsite.goods.dao.GoodsProductMapper.insertExchangeCusmomer", map);
    }

}
