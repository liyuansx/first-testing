/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.weixin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ningpai.m.weixin.bean.ThreePart;
import com.ningpai.m.weixin.dao.ThreePartMapper;
import com.ningpai.m.weixin.service.ThreePartService;

/**
 * @see com.ningpai.m.weixin.service.ThreePartService
 * @author NINGPAI-zhangqiang
 * @since 2014年8月26日 下午6:05:44
 * @version 0.0.1
 */
@Service("threePartServiceM")
public class ThreePartServiceImpl implements ThreePartService {

    @Override
    public ThreePart selectThreePart(String openid) {
        return threePartMapper.selectThreePart(openid);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.threepart.service.ThreePartService#insertThreePart(com .ningpai.site.threepart.bean.ThreePart)
     */
    @Override
    public int insertThreePart(ThreePart tp) {
        return threePartMapper.insertThreePart(tp);
    }

    private ThreePartMapper threePartMapper;

    public ThreePartMapper getThreePartMapper() {
        return threePartMapper;
    }

    @Resource(name = "threePartMapperM")
    public void setThreePartMapper(ThreePartMapper threePartMapper) {
        this.threePartMapper = threePartMapper;
    }

}
