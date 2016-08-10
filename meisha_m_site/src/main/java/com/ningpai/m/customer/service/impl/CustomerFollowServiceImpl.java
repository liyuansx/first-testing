/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service.impl;

import com.ningpai.m.customer.mapper.CustomerFollowMapper;
import com.ningpai.m.customer.service.CustomerFollowService;
import com.ningpai.util.PageBean;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @see com.ningpai.site.customer.service.CustomerFollowService
 * @since 2014年4月14日 下午2:28:22
 */
@Service("customerFollowServiceSite")
public class CustomerFollowServiceImpl implements CustomerFollowService {
	
    // spring 注入
    private CustomerFollowMapper customerFollowMapper;
    
    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.service.CustomerFollowService#selectBrowserecord
     * (java.lang.Long)
     */
    @Override
    public PageBean selectCustomerFollow(Map<String, Object> paramMap,
                                         PageBean pb) {
        Long count = customerFollowMapper
                .selectCustomerFollowCount((Long) paramMap.get("customerId"));
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        if (pb.getPageNo() > pb.getLastPageNo()) {
            pb.setPageNo(pb.getLastPageNo());
        }
        if (pb.getPageNo() == 0) {
            pb.setPageNo(1);
        }
        paramMap.put("startRowNum", pb.getStartRowNum());
        paramMap.put("endRowNum", pb.getEndRowNum());
        pb.setList(customerFollowMapper.selectCustFollowByCustId(paramMap));
        return pb;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.service.CustomerFollowService#deleteFollow(
     * java.lang.Long)
     */
    @Override
    public int deleteFollow(Long followId, Long customerId) {
        Map<String, Object> map = new HashMap<>();
        // 封装参数
        map.put("followId", followId);
        map.put("customerId", customerId);
        return customerFollowMapper.deleteByPrimaryKey(map);
    }

    public CustomerFollowMapper getCustomerFollowMapper() {
        return customerFollowMapper;
    }

    @Resource(name = "customerFollowMapperSite")
    public void setCustomerFollowMapper(
            CustomerFollowMapper customerFollowMapper) {
        this.customerFollowMapper = customerFollowMapper;
    }
    	
    
    /**
     * 商品列表专用 查询当前会员是否
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<String> selectCustomerFollowForList(Map<String, Object> paramMap) {
        //查询按条件查询消费记录
        return customerFollowMapper.selectCustFollowByCustIdForList(paramMap);

    }

}
