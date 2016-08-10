package com.ningpai.m.weixin.dao;

import com.ningpai.m.weixin.bean.WXGroup;

/**
 * 微信群发租Mapper
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年9月3日 下午3:25:25
 * @version0.0.1
 */
public interface WXGroupMapper {

    int deleteByPrimaryKey(Long wxId);

    int insert(WXGroup record);

    int insertSelective(WXGroup record);

    WXGroup selectByPrimaryKey(Long wxId);

    int updateByPrimaryKeySelective(WXGroup record);

    int updateByPrimaryKey(WXGroup record);

    /**
     * 检查OpenID
     * 
     * @param openId
     * @return
     */
    Long checkOpenIdExist(String openId);
}
