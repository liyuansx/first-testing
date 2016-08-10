/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.mapper.impl;

import com.ningpai.m.customer.mapper.CustomerFollowMapper;
import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.site.customer.bean.CustomerFollow;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @see com.ningpai.site.customer.mapper.CustomerFollowMapper
 * @since 2014年4月14日 下午2:29:11
 */
@Repository("customerFollowMapperSite")
public class CustomerFollowMapperImpl extends BasicSqlSupport implements
        CustomerFollowMapper {

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.CustomerFollowMapper#deleteByPrimaryKey
     * (java.lang.Long)
     */
    @Override
    public int deleteByPrimaryKey(Map<String, Object> map) {
        return this
                .update("com.ningpai.site.customer.dao.CustomerFollowMapper.deleteByPrimaryKey",
                        map);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.CustomerFollowMapper#insert(com.ningpai
     * .customer.bean.CustomerFollow)
     */
    @Override
    public int insert(CustomerFollow record) {
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.CustomerFollowMapper#insertSelective
     * (com.ningpai.customer.bean.CustomerFollow)
     */
    @Override
    public int insertSelective(CustomerFollow record) {
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.CustomerFollowMapper#selectByPrimaryKey
     * (java.lang.Long)
     */
    @Override
    public CustomerFollow selectByPrimaryKey(Long followId) {
        return null;
    }

    /*
     * 
     * 
     * @see com.ningpai.site.customer.mapper.CustomerFollowMapper#
     * updateByPrimaryKeySelective(com.ningpai.customer.bean.CustomerFollow)
     */
    @Override
    public int updateByPrimaryKeySelective(CustomerFollow record) {
        return 0;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.mapper.CustomerFollowMapper#updateByPrimaryKey
     * (com.ningpai.customer.bean.CustomerFollow)
     */
    @Override
    public int updateByPrimaryKey(CustomerFollow record) {
        return 0;
    }

    /*
     * 
     * 
     * @see com.ningpai.site.customer.mapper.CustomerFollowMapper#
     * selectCustFollowByCustId(long)
     */
    @Override
    public List<Object> selectCustFollowByCustId(Map<String, Object> paramMap) {
        return this
                .selectList(
                        "com.ningpai.site.customer.dao.CustomerFollowMapper.selectCustFollowByCustId",
                        paramMap);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.customer.mapper.CustomerFollowMapper#
     * selectCustomerFollowCount(java.lang.Long)
     */
    @Override
    public Long selectCustomerFollowCount(Long customerId) {
        return this
                .selectOne(
                        "com.ningpai.site.customer.dao.CustomerFollowMapper.selectCustFollowCount",
                        customerId);
    }

    /**
     * 商品列表专用 查询当前会员是否
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<String> selectCustFollowByCustIdForList(Map<String, Object> paramMap) {
        return this
                .selectList(
                        "com.ningpai.site.customer.dao.CustomerFollowMapper.selectCustFollowByCustIdForList",
                        paramMap);
    }

}
