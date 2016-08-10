/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ningpai.goods.bean.GoodsCate;
import com.ningpai.m.goods.dao.GoodsCateMapper;
import com.ningpai.m.goods.service.GoodsCateService;
import com.ningpai.m.goods.vo.GoodsBreadCrumbVo;
import com.ningpai.m.goods.vo.GoodsCateVo;

/**
 * 商品分类Service
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年1月13日 下午3:34:01
 * @version 1.0
 */
@Service("HsiteGoodsCateService")
public class GoodsCateServiceImpl implements GoodsCateService {
    private GoodsCateMapper goodsCateMapper;

    public GoodsCateMapper getGoodsCateMapper() {
        return goodsCateMapper;
    }

    @Resource(name = "HsiteGoodsCateMapper")
    public void setGoodsCateMapper(GoodsCateMapper goodsCateMapper) {
        this.goodsCateMapper = goodsCateMapper;
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.service.GoodsCateService#queryAllCate()
     */
    public GoodsCateVo queryAllCate() {
        GoodsCateVo topParent = null;
        List<GoodsCateVo> allCate = null;
        try {
            topParent = this.goodsCateMapper.queryTopParent();
            allCate = this.goodsCateMapper.queryAllCate();
            topParent.setCateVos(this.calcCateVo(topParent.getCatId(), allCate));
            return topParent;
        } finally {
            topParent = null;
            allCate = null;
        }
    }

    /**
     * 递归判断
     * 
     * @param parentId
     *            父分类ID
     * @param allCateList
     *            所有的分类列表
     * @return 排序好的集合
     */
    public List<GoodsCateVo> calcCateVo(Long parentId, List<GoodsCateVo> allCateList) {
        List<GoodsCateVo> cateVoList = new ArrayList<GoodsCateVo>();
        // 需要返回的分类实体
        GoodsCateVo cateVo = null;
        // 返回的分类实体的所有子分类
        List<GoodsCateVo> allSonCate = null;
        // 循环中的迭代分类
        GoodsCateVo cate = null;
        try {
            // 进行递归
            for (int i = 0; i < allCateList.size(); i++) {
                if (parentId.equals(allCateList.get(i).getCatParentId())) {
                    // 必须在这里new对象 否则会覆盖原先的数据
                    cateVo = new GoodsCateVo();
                    cate = allCateList.get(i);
                    cateVo.setCatId(cate.getCatId());
                    cateVo.setCatName(cate.getCatName());
                    cateVo.setCatParentId(cate.getCatParentId());
                    cateVo.setCatSort(cate.getCatSort());
                    cateVo.setTypeId(cate.getTypeId());
                    cateVo.setCatDelflag(cate.getCatDelflag());
                    cateVo.setCatGrade(cate.getCatGrade());
                    cateVo.setCatSeoDesc(cate.getCatSeoDesc());
                    cateVo.setCatSeoKeyword(cate.getCatSeoKeyword());
                    cateVo.setCatSeoTitle(cate.getCatSeoTitle());
                    // 执行递归
                    allSonCate = calcCateVo(cate.getCatId(), allCateList);
                    cateVo.setCateVos(allSonCate);
                    cateVoList.add(cateVo);
                }
            }
            // 返回计算好的所有的子分类列表
            return cateVoList;
        } finally {
            // 置空所有参数
            cateVoList = null;
            cateVo = null;
            allSonCate = null;
            cate = null;
        }
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.service.GoodsCateService#queryCateByCatId(java.lang .Long)
     */
    public GoodsCateVo queryCateByCatId(Long catId) {
        GoodsCateVo topParent = null;
        try {
            topParent = this.goodsCateMapper.queryCateVoByCatId(catId);
            topParent.setParentCat(this.goodsCateMapper.queryCateByPrimaryKey(topParent.getCatParentId()));
            return topParent;
        } finally {
            topParent = null;
        }
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.service.GoodsCateService#queryCateById(java.lang.Long)
     */
    public GoodsCateVo queryCateById(Long catId) {
        GoodsCateVo topParent = null;
        List<GoodsCateVo> allCate = null;
        try {
            topParent = this.goodsCateMapper.queryCateVoByCatId(catId);
            allCate = this.goodsCateMapper.queryAllCate();
            topParent.setCateVos(this.calcCateVo(topParent.getCatId(), allCate));
            return topParent;
        } finally {
            topParent = null;
            allCate = null;
        }
    }

    /*
     * 
     * 
     * @see com.ningpai.goods.service.GoodsCateService#calcAllSonCatIds(com.ningpai .goods.vo.GoodsCateVo, java.util.List)
     */
    public Long calcAllSonCatIds(GoodsCateVo cateVo, List<Long> catIds) {
        catIds.add(cateVo.getCatId());
        Long catId;
        try {
            if (null != cateVo.getCateVos() && cateVo.getCateVos().size() > 0) {
                for (int i = 0; i < cateVo.getCateVos().size(); i++) {
                    catId = this.calcAllSonCatIds(cateVo.getCateVos().get(i), catIds);
                    if (null != catId) {
                        catIds.add(catId);
                    }
                }
            }
            return null;
        } finally {
            catId = null;
        }
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.service.GoodsCateService#queryCatIdsByCatId(java.lang.Long)
     */
    public List<GoodsCate> queryCatIdsByCatId(Long catId) {
        List<GoodsCate> list = this.goodsCateMapper.querySonCateByCatId(catId);
        try {
            list.add(0, this.goodsCateMapper.queryCateByPrimaryKey(catId));
            return list;
        } finally {
            list = null;
        }
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.service.GoodsCateService#queryCateAndParCateByCatId(java.lang.Long)
     */
    public GoodsCateVo queryCateAndParCateByCatId(Long catId) {
        return this.goodsCateMapper.queryCateAndParCateByCatId(catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.service.GoodsCateService#queryBreadCrubByCatId(java.lang.Long)
     */
    public GoodsBreadCrumbVo queryBreadCrubByCatId(Long catId) {
        return this.goodsCateMapper.queryBreadCrubByCatId(catId);
    }

    /*
     * 
     * 
     * @see com.ningpai.site.goods.service.GoodsCateService#calcCatUrl(java.lang.Long, java.lang.String)
     */
    public String calcCatUrl(Long catId, String level) {
        String url = "";
        if ("1".equals(level)) {
            url = this.goodsCateMapper.queryFirstCatIdByFirstCatId(catId) + "-" + catId.toString();
        } else if ("2".equals(level)) {
            url = this.goodsCateMapper.queryFirstSonCatIdBySecondCatId(catId) + "-" + this.goodsCateMapper.queryParentIdBySecondCatId(catId).toString();
        }
        return url;
    }

}
