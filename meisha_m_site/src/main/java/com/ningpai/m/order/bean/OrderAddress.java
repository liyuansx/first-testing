/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.order.bean;

/**
 * 微信收货地址辅助类
 * 
 * @author NINGPAI-LIH
 * @since 2014年11月6日15:40:03
 * 
 */
public class OrderAddress {
    //id
    private Long addressId;
    // 收货人名称
    private String addressName;

    // 收货人电话
    private String addressPhone;

    // 收货街道信息
    private String addressDetail;

    // 详细地址
    private String addressDetailInfo;

    // 省
    private String proviceFirstStageName;

    // 市
    private String addressCitySecondStageName;

    // 区县
    private String addressCountiesThirdStageName;

    public String getProviceFirstStageName() {
        return proviceFirstStageName;
    }

    public void setProviceFirstStageName(String proviceFirstStageName) {
        this.proviceFirstStageName = proviceFirstStageName;
    }

    public String getAddressCitySecondStageName() {
        return addressCitySecondStageName;
    }

    public void setAddressCitySecondStageName(String addressCitySecondStageName) {
        this.addressCitySecondStageName = addressCitySecondStageName;
    }

    public String getAddressCountiesThirdStageName() {
        return addressCountiesThirdStageName;
    }

    public void setAddressCountiesThirdStageName(String addressCountiesThirdStageName) {
        this.addressCountiesThirdStageName = addressCountiesThirdStageName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressDetailInfo() {
        return addressDetailInfo;
    }

    public void setAddressDetailInfo(String addressDetailInfo) {
        this.addressDetailInfo = addressDetailInfo;
    }

    public  void setAddressId(Long addressId){
        this.addressId=addressId;
    }

    public Long getAddressId(){
        return this.addressId;
    }

}
