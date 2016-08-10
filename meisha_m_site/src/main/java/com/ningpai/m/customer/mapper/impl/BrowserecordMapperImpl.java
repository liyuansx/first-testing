/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.mapper.impl;

import com.ningpai.m.customer.mapper.BrowserecordMapper;
import com.ningpai.m.customer.vo.Browserecord;
import com.ningpai.manager.base.BasicSqlSupport;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @see com.ningpai.site.customer.mapper.BrowserecordMapper
 * @since 2014年4月12日 下午4:57:39
 */
@Repository("browserecordMapper")
public class BrowserecordMapperImpl extends BasicSqlSupport implements
        BrowserecordMapper {
    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#deleteByPrimaryKey
     * (java.lang.Long)
     */
    @Override
    public int deleteByPrimaryKey(Map<String, Object> map) {
        return this.update("com.ningpai.site.customer.dao.BrowserecordMapper.deleteByPrimaryKey", map);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#deleteByPrimaryKey
     * (java.lang.Long)
     */
    @Override
    public int deleteByTime(Map<String, Object> map) {
        return this.update("com.ningpai.site.customer.dao.BrowserecordMapper.deleteByTime", map);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#insert(com.ningpai
     * .site.customer.bean.Browserecord)
     */
    @Override
    public int insert(Browserecord record) {
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#insertSelective(com
     * .ningpai.site.customer.bean.Browserecord)
     */
    @Override
    public int insertSelective(Browserecord record) {
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#selectByPrimaryKey
     * (java.lang.Long)
     */
    @Override
    public Browserecord selectByPrimaryKey(Long likeId) {
        return null;
    }

    /*
     * 
     * 
     * @see com.ningpai.site.customer.mapper.BrowserecordMapper#
     * updateByPrimaryKeySelective(com.ningpai.site.customer.bean.Browserecord)
     */
    @Override
    public int updateByPrimaryKeySelective(Browserecord record) {
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#updateByPrimaryKey
     * (com.ningpai.site.customer.bean.Browserecord)
     */
    @Override
    public int updateByPrimaryKey(Browserecord record) {
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#selectBrowserecord
     * (java.lang.Long)
     */
    @Override
    public List<Browserecord> selectBrowserecord(Long customerId) {
        return this
                .selectList(
                        "com.ningpai.site.customer.dao.BrowserecordMapper.selectBrowserecord",
                        customerId);
    }

    /*
     *
     *
     * @see
     * com.ningpai.site.customer.mapper.BrowserecordMapper#deleteByPrimaryKey
     * (java.lang.Long)
     */
    @Override
    public Integer queryAtteHistByCustIdAndProId(Map<String, ?> map) {
        return this
                .selectOne(
                        "com.ningpaihsite.goods.dao.GoodsProductMapper.queryAtteHistByCustIdAndProId",
                        map);
    }

}
