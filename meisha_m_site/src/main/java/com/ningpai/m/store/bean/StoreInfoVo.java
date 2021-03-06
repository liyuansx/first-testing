/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.store.bean;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.marketing.bean.Marketing;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 店铺信息 Bean
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年5月5日 下午5:27:01
 * @version 0.0.1
 */
public class StoreInfoVo {

    // 店铺编号
    private Long storeId;
    // 客户编号
    private Long customerid;
    // 店铺名称
    private String storeName;
    // 店铺开通状态 1：已开通 0：未开通
    private String storeStatus;
    // 公司名称
    private String companyName;

    // 公司创建时间
    private String companyCreTime;

    // 公司地域 省;市;区
    private String companyAddr;
    private String companyAddrId;

    // 公司详细地址
    private String companyAddrDel;

    // 公司电话
    private String companyTel;

    // 员工总数
    private Long companyEmpNum;

    // 注册资金
    private BigDecimal companyCapital;

    // 电子邮箱
    private String companyEmail;

    // 企业类型 1:生产厂商 2:品牌商 3:代理商 4:经销商
    private String companyType;

    // 联系人姓名
    private String companyContactName;

    // 联系人电话
    private String companyContactTel;

    // 调研资料电子表存储地址
    private String companyResearchUrl;

    // SKU数量合计
    private BigDecimal companySku;

    // 平均客单价1: 0-100 2: 100-200 3: 200-300 4: 300-400 5: 400-500 6: 500-1000 7:
    // 1000以上
    private String companyAvg;

    // 退货地址
    private String returnAddr;

    // 退货邮编
    private String returnMail;

    // 退货联系人
    private String returnContactName;

    // 退货联系电话
    private String returnContactTel;

    // 营业执照号
    private String bussId;

    // 营业执照所在地 省;市;区
    private String bussAddr;
    private String bussAddrId;

    // 营业执照有效期
    private String bussDate;

    // 营业执照电子版存储地址
    private String bussUrl;

    // 经营范围 1:生产厂商 2:品牌商 3:代理商 4:经销商
    private String bussRange;

    // 组织机构代码
    private String bussDeptNo;

    // 税务登记证号
    private String bussTaxRegistId;

    // 税务登记证Url
    private String bussTaxRegistUrl;

    // 纳税人识别号
    private String bussTaxPayerId;

    // 纳税人资格证URL
    private String bussTaxCredUrl;

    // 税务类型
    private String bussTaxType;

    // 税务类型税码
    private String bussTaxTypeId;

    // 法人代表
    private String bussLegalName;

    // 银行开户名
    private String bankUsername;

    // 公司银行账号
    private String bankCardId;

    // 开户银行所在地 省;市;区
    private String bankAddr;
    private String bankAddrId;

    // 开户行支行名称
    private String bankName;

    // 开户支行联行号
    private String bankId;

    // 审核状态 0:审核中 1:已审核 2:未通过
    private String checkStatus;

    // 合同电子版存储地址
    private String contractUrl;

    // 创建时间
    private Date createTime;

    // 修改时间
    private Date modTime;

    // 店铺到期时间
    private Date expiryTime;

    // 删除标志 1：删除 0：未删除
    private String delFlag;
    // 银行开户许可证URL
    private String bankUrl;
    // 法人身份证URL
    private String cardUrl;
    // 法人身份证号码
    private String bussLegalCardId;
    // 资料提交
    private String isSubmit;
    // 打回原因
    private String refuseContent;

    // 是否显示商家首页 0否 1是
    private String isStoreIndex;
    // 结算周期
    private String billingCycle;

    // 店铺承诺
    private String storePromise;

    // 旗舰店
    private String storeQi;

    private Long collectionSellerId;

    private int sumPoint;

    // 商家积分
    private int storePoint;

    // 商家账户余额
    private BigDecimal storeBalance;

    // 客服QQ
    private String serviceQq;

    // 是否为供应商 1:是供应商 0:不是供应商
    private String isSupplier;

    // 店铺被关注的状态
    private int isCollection;

    /* 店铺街 */
    // 优惠券
    List<Coupon> couponlist = new ArrayList<>();

    // 促销
    List<Marketing> marketinglist = new ArrayList<>();

    // 该店铺下面所有的货品信息
    List<GoodsProduct> storeProductList = new ArrayList<>();

    // 该店铺下面广告信息
    List<StoreStreetThirdImage> store_images = new ArrayList<>();

    // 该店铺最近指定时间内上架的商品数量
    private Long storeNewProcudtCount;

    public List<StoreStreetThirdImage> getStore_images() {
        return store_images;
    }

    public void setStore_images(List<StoreStreetThirdImage> store_images) {
        this.store_images = store_images;
    }

    public Long getStoreNewProcudtCount() {
        return storeNewProcudtCount;
    }

    public void setStoreNewProcudtCount(Long storeNewProcudtCount) {
        this.storeNewProcudtCount = storeNewProcudtCount;
    }

    public List<GoodsProduct> getStoreProductList() {
        return storeProductList;
    }

    public void setStoreProductList(List<GoodsProduct> storeProductList) {
        this.storeProductList = storeProductList;
    }

    public List<Marketing> getMarketinglist() {
        return marketinglist;
    }

    public void setMarketinglist(List<Marketing> marketinglist) {
        this.marketinglist = marketinglist;
    }

    public List<Coupon> getCouponlist() {
        return couponlist;
    }

    public void setCouponlist(List<Coupon> couponlist) {
        this.couponlist = couponlist;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public String getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(String isSupplier) {
        this.isSupplier = isSupplier;
    }

    public String getServiceQq() {
        return serviceQq;
    }

    public void setServiceQq(String serviceQq) {
        this.serviceQq = serviceQq;
    }

    public int getStorePoint() {
        return storePoint;
    }

    public void setStorePoint(int storePoint) {
        this.storePoint = storePoint;
    }

    public BigDecimal getStoreBalance() {
        return storeBalance;
    }

    public void setStoreBalance(BigDecimal storeBalance) {
        this.storeBalance = storeBalance;
    }

    public int getSumPoint() {
        return sumPoint;
    }

    public void setSumPoint(int sumPoint) {
        this.sumPoint = sumPoint;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getIsStoreIndex() {
        return isStoreIndex;
    }

    public void setIsStoreIndex(String isStoreIndex) {
        this.isStoreIndex = isStoreIndex;
    }

    public String getRefuseContent() {
        return refuseContent;
    }

    public void setRefuseContent(String refuseContent) {
        this.refuseContent = refuseContent;
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getBussLegalCardId() {
        return bussLegalCardId;
    }

    public void setBussLegalCardId(String bussLegalCardId) {
        this.bussLegalCardId = bussLegalCardId;
    }

    public String getCardUrl() {
        return cardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public String getCompanyAddrId() {
        return companyAddrId;
    }

    public void setCompanyAddrId(String companyAddrId) {
        this.companyAddrId = companyAddrId;
    }

    public String getBussAddrId() {
        return bussAddrId;
    }

    public void setBussAddrId(String bussAddrId) {
        this.bussAddrId = bussAddrId;
    }

    public String getBankAddrId() {
        return bankAddrId;
    }

    public void setBankAddrId(String bankAddrId) {
        this.bankAddrId = bankAddrId;
    }

    public String getBussTaxRegistUrl() {
        return bussTaxRegistUrl;
    }

    public void setBussTaxRegistUrl(String bussTaxRegistUrl) {
        this.bussTaxRegistUrl = bussTaxRegistUrl;
    }

    public String getBussTaxCredUrl() {
        return bussTaxCredUrl;
    }

    public void setBussTaxCredUrl(String bussTaxCredUrl) {
        this.bussTaxCredUrl = bussTaxCredUrl;
    }

    public String getCompanyCreTime() {
        return companyCreTime;
    }

    public void setCompanyCreTime(String companyCreTime) {
        this.companyCreTime = companyCreTime;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyAddrDel() {
        return companyAddrDel;
    }

    public void setCompanyAddrDel(String companyAddrDel) {
        this.companyAddrDel = companyAddrDel;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public Long getCompanyEmpNum() {
        return companyEmpNum;
    }

    public void setCompanyEmpNum(Long companyEmpNum) {
        this.companyEmpNum = companyEmpNum;
    }

    public BigDecimal getCompanyCapital() {
        return companyCapital;
    }

    public void setCompanyCapital(BigDecimal companyCapital) {
        this.companyCapital = companyCapital;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyContactName() {
        return companyContactName;
    }

    public void setCompanyContactName(String companyContactName) {
        this.companyContactName = companyContactName;
    }

    public String getCompanyContactTel() {
        return companyContactTel;
    }

    public void setCompanyContactTel(String companyContactTel) {
        this.companyContactTel = companyContactTel;
    }

    public String getCompanyResearchUrl() {
        return companyResearchUrl;
    }

    public void setCompanyResearchUrl(String companyResearchUrl) {
        this.companyResearchUrl = companyResearchUrl;
    }

    public BigDecimal getCompanySku() {
        return companySku;
    }

    public void setCompanySku(BigDecimal companySku) {
        this.companySku = companySku;
    }

    public String getCompanyAvg() {
        return companyAvg;
    }

    public void setCompanyAvg(String companyAvg) {
        this.companyAvg = companyAvg;
    }

    public String getReturnAddr() {
        return returnAddr;
    }

    public void setReturnAddr(String returnAddr) {
        this.returnAddr = returnAddr;
    }

    public String getReturnMail() {
        return returnMail;
    }

    public void setReturnMail(String returnMail) {
        this.returnMail = returnMail;
    }

    public String getReturnContactName() {
        return returnContactName;
    }

    public void setReturnContactName(String returnContactName) {
        this.returnContactName = returnContactName;
    }

    public String getReturnContactTel() {
        return returnContactTel;
    }

    public void setReturnContactTel(String returnContactTel) {
        this.returnContactTel = returnContactTel;
    }

    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }

    public String getBussAddr() {
        return bussAddr;
    }

    public void setBussAddr(String bussAddr) {
        this.bussAddr = bussAddr;
    }

    public String getBussDate() {
        return bussDate;
    }

    public void setBussDate(String bussDate) {
        this.bussDate = bussDate;
    }

    public String getBussDeptNo() {
        return bussDeptNo;
    }

    public void setBussDeptNo(String bussDeptNo) {
        this.bussDeptNo = bussDeptNo;
    }

    public String getBussTaxRegistId() {
        return bussTaxRegistId;
    }

    public void setBussTaxRegistId(String bussTaxRegistId) {
        this.bussTaxRegistId = bussTaxRegistId;
    }

    public String getBussTaxPayerId() {
        return bussTaxPayerId;
    }

    public void setBussTaxPayerId(String bussTaxPayerId) {
        this.bussTaxPayerId = bussTaxPayerId;
    }

    public String getBussTaxType() {
        return bussTaxType;
    }

    public void setBussTaxType(String bussTaxType) {
        this.bussTaxType = bussTaxType;
    }

    public String getBussTaxTypeId() {
        return bussTaxTypeId;
    }

    public void setBussTaxTypeId(String bussTaxTypeId) {
        this.bussTaxTypeId = bussTaxTypeId;
    }

    public String getBussLegalName() {
        return bussLegalName;
    }

    public void setBussLegalName(String bussLegalName) {
        this.bussLegalName = bussLegalName;
    }

    public String getBussUrl() {
        return bussUrl;
    }

    public void setBussUrl(String bussUrl) {
        this.bussUrl = bussUrl;
    }

    public String getBussRange() {
        return bussRange;
    }

    public void setBussRange(String bussRange) {
        this.bussRange = bussRange;
    }

    public String getBankUsername() {
        return bankUsername;
    }

    public void setBankUsername(String bankUsername) {
        this.bankUsername = bankUsername;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public Date getCreateTime() {
        if (this.createTime != null) {
            return new Date(this.createTime.getTime());
        } else {
            return null;
        }
    }

    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            Date timeTemp = (Date) createTime.clone();
            if (timeTemp != null) {
                this.createTime = timeTemp;
            }
        }
    }

    public Date getModTime() {
        if (this.modTime != null) {
            return new Date(this.modTime.getTime());
        } else {
            return null;
        }
    }

    public void setModTime(Date modTime) {
        if (modTime != null) {
            Date timeTemp = (Date) modTime.clone();
            if (timeTemp != null) {
                this.modTime = timeTemp;
            }
        }
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getStorePromise() {
        return storePromise;
    }

    public void setStorePromise(String storePromise) {
        this.storePromise = storePromise;
    }

    public String getStoreQi() {
        return storeQi;
    }

    public void setStoreQi(String storeQi) {
        this.storeQi = storeQi;
    }

    public Long getCollectionSellerId() {
        return collectionSellerId;
    }

    public void setCollectionSellerId(Long collectionSellerId) {
        this.collectionSellerId = collectionSellerId;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
}
