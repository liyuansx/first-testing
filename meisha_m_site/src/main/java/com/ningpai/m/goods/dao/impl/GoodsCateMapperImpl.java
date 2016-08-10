/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ningpai.goods.bean.GoodsCate;
import com.ningpai.m.goods.dao.GoodsCateMapper;
import com.ningpai.m.goods.vo.GoodsBreadCrumbVo;
import com.ningpai.m.goods.vo.GoodsCateVo;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * 商品分类DAO实现
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月8日 下午5:49:41
 * @version 1.0
 */
@Repository("HsiteGoodsCateMapper")
public class GoodsCateMapperImpl extends BasicSqlSupport implements GoodsCateMapper {
    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsCateMapper#queryAllCate()
     */
    public List<GoodsCateVo> queryAllCate() {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsCateMapper.queryAllCate");
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsCateMapper#queryTopParent()
     */
    public GoodsCateVo queryTopParent() {
        return (GoodsCateVo) this.selectList("com.ningpaimsite.goods.dao.GoodsCateMapper.queryTopParent");
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.dao.GoodsCateMapper#queryCateVoByCatId(java.lang.Long)
     */
    public GoodsCateVo queryCateVoByCatId(Long catId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsCateMapper.queryCateVoByCatId", catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsCateMapper#querySonCateByCatId(java.lang .Long)
     */
    public List<GoodsCate> querySonCateByCatId(Long catId) {
        return this.selectList("com.ningpaimsite.goods.dao.GoodsCateMapper.querySonCateByCatId", catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsCateMapper#queryCateByPrimaryKey(java .lang.Long)
     */
    public GoodsCate queryCateByPrimaryKey(Long catId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsCateMapper.selectByPrimaryKey", catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsCateMapper#queryCateAndParCateByCatId (java.lang.Long)
     */
    public GoodsCateVo queryCateAndParCateByCatId(Long catId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsCateMapper.queryCateAndParCateByCatId", catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsCateMapper#queryBreadCrubByCatId(java.lang.Long)
     */
    public GoodsBreadCrumbVo queryBreadCrubByCatId(Long catId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsCateMapper.queryBreadCrubByCatId", catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsCateMapper#queryFirstCatIdByFirstCatId(java.lang.Long)
     */
    public Long queryFirstCatIdByFirstCatId(Long catId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsCateMapper.queryFirstCatIdByFirstCatId", catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsCateMapper#queryParentIdBySecondCatId(java.lang.Long)
     */
    public Long queryParentIdBySecondCatId(Long catId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsCateMapper.queryParentIdBySecondCatId", catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.GoodsCateMapper#queryFirstSonCatIdBySecondCatId(java.lang.Long)
     */
    public Long queryFirstSonCatIdBySecondCatId(Long catId) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsCateMapper.queryFirstSonCatIdBySecondCatId", catId);
    }

}
