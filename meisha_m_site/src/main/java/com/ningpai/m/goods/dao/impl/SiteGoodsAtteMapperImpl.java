/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ningpai.m.goods.bean.SiteGoodsAtte;
import com.ningpai.m.goods.dao.SiteGoodsAtteMapper;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * 商品关注DAO实现
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月12日 下午4:26:55
 * @version 1.0
 */
@Repository("SiteGoodsAtteMapper")
public final class SiteGoodsAtteMapperImpl extends BasicSqlSupport implements SiteGoodsAtteMapper {
    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.SiteGoodsAtteDao#saveGoodsAtte(com.ningpai .site.goods.bean.SiteGoodsAtte)
     */
    public int saveGoodsAtte(SiteGoodsAtte siteGoodsAtte) {
        return this.insert("com.ningpaimsite.goods.dao.GoodsProductMapper.saveGoodsAtte", siteGoodsAtte);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.dao.SiteGoodsAtteMapper#queryAtteHist(java.util .Map)
     */
    public Integer queryAtteHistByCustIdAndProId(Map<String, ?> map) {
        return this.selectOne("com.ningpaimsite.goods.dao.GoodsProductMapper.queryAtteHistByCustIdAndProId", map);
    }

	@Override
	public int deleteGoodsAtte(Map<String, ?> map) {
		return this.delete("com.ningpaimsite.goods.dao.GoodsProductMapper.deleteGoodsAtte", map);
	}

}
