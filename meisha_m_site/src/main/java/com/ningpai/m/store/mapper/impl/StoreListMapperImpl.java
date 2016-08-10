package com.ningpai.m.store.mapper.impl;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.m.store.bean.StoreStreetThirdImage;
import com.ningpai.m.store.mapper.StoreListMapper;
import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.thirdaudit.bean.GoodsCateGory;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghailong on 2015/6/9.
 */
@Repository("storeListMapper")
public class StoreListMapperImpl extends BasicSqlSupport implements StoreListMapper {

    @Override
    public int deleteController(Long storeId, Long customerId) {
        Map<String,Object> map = new HashMap<String,Object>();
        //店铺ID
        map.put("storeId",storeId);
        //会员ID
        map.put("customerId",customerId);
        return this.delete("com.ningpai.mybatis.mapper.StoreListMapper.deleteController", map);
    }

    @Override
    public List<GoodsCateGory> selectgoodscatebyone() {
        return selectList("com.ningpai.web.dao.GoodsCateGoryMapper.selectgoodscatebyone");
    }

    @Override
    public Integer IsCollection(Long storeId, Long customerId) {
        Map<String,Object> map = new HashMap<String,Object>();
        //店铺ID
        map.put("storeId",storeId);
        //会员ID
        map.put("customerId",customerId);
        return this.selectOne("com.ningpai.mybatis.mapper.StoreListMapper.IsCollection", map);
    }

    @Override
    public List<Object> StoreNewProcudtList(Long storeId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("storeId",storeId);
        return this.selectList("com.ningpai.mybatis.mapper.StoreListMapper.setStoreNewProcudtList", map);
    }

    @Override
    public int addCollectionSeller(Long customerId, Long storeId) {
        Map<String,Object> map = new HashMap<String,Object>();
        //店铺ID
        map.put("storeId",storeId);
        //会员ID
        map.put("customerId",customerId);
        //创建时间
        map.put("newdate",new Date());
        //是否删除
        map.put("del",'0');
        return this.insert("com.ningpai.mybatis.mapper.StoreListMapper.addCollectionSeller", map);
    }

    @Override
    public List<StoreStreetThirdImage> selectStoreStreetListImage(Long storeId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("storeId",storeId);
        return this.selectList("com.ningpai.mybatis.mapper.StoreListMapper.selectStoreStreetListImage",map);
    }

    @Override
    public Integer selectStoreNum( Map<String,Object> map) {
        return this.selectOne("com.ningpai.mybatis.mapper.StoreListMapper.selectStoreNum",map);
    }

    /**
     *
     * @param storeId 店铺ID
     * @return
     */
    @Override
    public Long selectStoreNewUpProductCount(Long storeId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("storeId",storeId);
        return this.selectOne("com.ningpai.mybatis.mapper.StoreListMapper.selectStoreNewUpProductCount",map);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Object> selectStoreList(Map<String,Object> map) {
            return this.selectList("com.ningpai.mybatis.mapper.StoreListMapper.selectStoreList",map);
    }


    /**
     *
     * @param storeCustomerId
     * @return
     */
    @Override
    public List<Marketing> selectMarketingByBusinessId(Long storeCustomerId) {
        Map<String,Object> map = new HashMap<String,Object>();
        //0:商品促销 1：订单促销
        map.put("marketingType","0");
        //商家ID
        map.put("businessId",storeCustomerId);
        return this.selectList("com.ningpai.mybatis.mapper.StoreListMapper.selectMarketingByBusinessId",map);
    }

    /**
     *
     * @param storeId
     * @return
     */
    @Override
    public List<Coupon> selectCouponByBusinessId(Long storeId) {
        Map<String,Object> map = new HashMap<String,Object>();
        //商家ID
        map.put("businessId",storeId);
        return this.selectList("com.ningpai.mybatis.mapper.StoreListMapper.selectCouponByBusinessId",map);
    }
}
