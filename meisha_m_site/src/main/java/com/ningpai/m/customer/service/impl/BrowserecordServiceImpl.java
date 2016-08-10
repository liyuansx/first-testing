/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service.impl;

import org.springframework.stereotype.Service;

import com.ningpai.m.customer.mapper.BrowserecordMapper;
import com.ningpai.m.customer.service.BrowserecordService;
import com.ningpai.m.customer.vo.Browserecord;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NINGPAI-zhangqiang
 * @see com.ningpai.site.customer.service.BrowserecordService
 * @since 2014年4月12日 下午4:49:04
 */
@Service("browserecordService")
public class BrowserecordServiceImpl implements BrowserecordService {

    // spring注解
    private BrowserecordMapper browserecordMapper;

    /*
     * 
     * 
     * @see
     * com.ningpai.site.customer.service.BrowserecordService#selectBrowserecord
     * (java.lang.Long)
     */
    @Override
    public List<Browserecord> selectBrowserecord(Long customerId) {
        return browserecordMapper.selectBrowserecord(customerId);
    }

    @Override
    public boolean checkAtte(Long custId, Long goodsId) {
        Map<String, Long> map = new HashMap<String, Long>();
        try {
            map.put("custId", custId);
            map.put("productId", goodsId);
            return this.browserecordMapper.queryAtteHistByCustIdAndProId(map) > 0 ? true
                    : false;
        } finally {
            map = null;
        }
    }

    /*
     * @see
     * com.ningpai.site.customer.service.BrowserecordService#deleteByPrimaryKey
     * (java.lang.Long)
     */
    @Override
    public int deleteByPrimaryKey(Long likeId, Long customerId) {
        // 创建封装容器
        Map<String, Object> map = new HashMap<>();
        // 封装参数
        map.put("likeId", likeId);
        map.put("customerId", customerId);
        // 删除浏览记录
        return browserecordMapper.deleteByPrimaryKey(map);
    }

    /*
     * @see
     * com.ningpai.site.customer.service.BrowserecordService#deleteByPrimaryKey
     * (java.lang.Long)
     */
    public int deleteByTime(Date date, Date afterDay, Long customerId) {
        // 创建封装容器
        Map<String, Object> map = new HashMap<>();
        // 封装参数
        map.put("date", date);
        map.put("afterDay", afterDay);
        map.put("customerId", customerId);
        //更新浏览记录状态
        return browserecordMapper.deleteByTime(map);
    }

    public BrowserecordMapper getBrowserecordMapper() {
        return browserecordMapper;
    }

    @Resource(name = "browserecordMapper")
    public void setBrowserecordMapper(BrowserecordMapper browserecordMapper) {
        this.browserecordMapper = browserecordMapper;
    }

}
