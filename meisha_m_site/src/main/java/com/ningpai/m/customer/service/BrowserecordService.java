/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service;

import java.util.Date;
import java.util.List;

import com.ningpai.m.customer.vo.Browserecord;

/**
 * 浏览记录接口
 *
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @since 2014年4月12日 下午4:42:47
 */
public interface BrowserecordService {
    /**
     * 查询浏览记录
     *
     * @param customerId 会员编号
     * @return List<Browserecord> {@link java.util.List}
     */
    List<Browserecord> selectBrowserecord(Long customerId);

    boolean checkAtte(Long custId, Long goodsId);

    /**
     * 根据主键删除
     *
     * @param likeId 浏览编号
     * @return 0失败 1成功
     */
    int deleteByPrimaryKey(Long likeId, Long customerId);

    /**
     * 根据时间删除浏览记录
     *
     * @param createTime 浏览时间
     * @return 0失败 1成功
     */
    int deleteByTime(Date date, Date afterDay, Long customerId);
}
