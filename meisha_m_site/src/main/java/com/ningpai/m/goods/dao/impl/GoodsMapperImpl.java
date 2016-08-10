/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ningpai.goods.bean.Goods;
import com.ningpai.m.goods.dao.GoodsMapper;
import com.ningpai.m.goods.vo.GoodsDetailVo;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月8日 下午6:07:33
 * @version
 */
@Repository("HsiteGoodsMapper")
public class GoodsMapperImpl extends BasicSqlSupport implements GoodsMapper {
    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#searchGoods(java.util.Map)
     */
    public List<Object> searchGoods(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsMapper.searchGoods", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#searchTotalCount(java.util.Map)
     */
    public Integer searchTotalCount(Map<String, Object> map) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsMapper.searchTotalCount", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#queryStockByGoodsId(java.lang.Long)
     */
    public Integer queryStockByGoodsId(Long goodsId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsMapper.queryStockByGoodsId", goodsId);
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#queryNewInfoTopThree(java.util.Map)
     */
    public List<Object> queryNewInfoTopThree(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsMapper.queryNewInfo", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#queryGoodsListByCatId(java.util.Map)
     */
    public List<Object> queryGoodsListByCatId(Map<String, Object> map) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsMapper.queryGoodsListByCatId", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#searchTotalCountByCatId(java.util.Map)
     */
    public Integer searchTotalCountByCatId(Map<String, Object> map) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsMapper.searchTotalCountByParamAndCatId", map);
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#queryGoodsDetailVoByGoodsId(java.lang .Long)
     */
    public GoodsDetailVo queryGoodsDetailVoByGoodsId(Long goodsId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsMapper.queryGoodsDetailVoByGoodsId", goodsId);
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsMapper#queryPromGoodsByCatIds(java.util.Map)
     */
    public List<Goods> queryPromGoodsByCatIds(Map<String, Object> map) {
        return null;
    }

}
