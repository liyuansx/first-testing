/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service;

import java.util.List;
import java.util.Map;

import com.ningpai.util.PageBean;

/**
 * 收藏商品Service
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年4月14日 下午2:05:49
 * @version 0.0.1
 */
public interface CustomerFollowService {
    /**
     * 查询收藏记录
     * 
     * @param customerId
     *            会员编号
     * @return List<Browserecord> {@link java.util.List}
     */
    PageBean selectCustomerFollow(Map<String, Object> paramMap, PageBean pb);

    /**
     * 取消关注
     * 
     * @param followId
     *            关注编号
     * @return 0 失败 1成功
     */
    int deleteFollow(Long followId,Long customerId);
    

    /**
     * 商品列表专用 查询当前会员是否
     *
     * @param paramMap
     * @return
     */
    List<String> selectCustomerFollowForList(Map<String, Object> paramMap);

}
