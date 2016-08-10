/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.controller;

import com.ningpai.comment.service.CommentServiceMapper;
import com.ningpai.goods.bean.EsGoodsInfo;
import com.ningpai.goods.bean.GoodsBrand;
import com.ningpai.goods.bean.GoodsImage;
import com.ningpai.goods.bean.LunboInfo;
import com.ningpai.goods.service.GoodsElasticSearchService;
import com.ningpai.goods.service.GoodsOpenSpecService;
import com.ningpai.goods.service.GoodsBrandService;
import com.ningpai.goods.service.GoodsTypeBrandService;
import com.ningpai.goods.service.LunboGoodService;
import com.ningpai.goods.service.LunboInfoService;
import com.ningpai.goods.service.LunboTypeService;
import com.ningpai.goods.util.GoodsIndexConstant;
import com.ningpai.goods.util.SearchPageBean;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.goods.bean.GoodsDetailBean;
import com.ningpai.m.goods.service.*;
import com.ningpai.m.goods.util.GoodsSiteSearchBean;
import com.ningpai.m.goods.util.ValueUtil;
import com.ningpai.m.goods.vo.GoodsListScreenVo;
import com.ningpai.m.goods.vo.GoodsTypeVo;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.util.StringCommonUtil;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月8日 下午2:15:52
 * @version
 */
@Controller
public final class GoodsSiteController {

	/**
	 * 日志
	 * */
	public static final MyLogger LOGGER = new MyLogger(
			GoodsSiteController.class);

	/**
	 * 静态变量ditinctId
	 * */
	private static final String DISTINCTID = "distinctId";
	/**
	 * 静态变量ditinctId
	 * */
	private static final String SEARCHBEAN = "searchBean";
	/**
	 * 静态变量ditinctId
	 * */
	private static final String PARAMS = "params";
	/**
	 * 静态变量ditinctId
	 * */
	private static final String GOODS_LIST = "goods/goods_list";
	/**
	 * 静态变量ditinctId
	 * */
	private static final String STOREID = "storeId";
	/**
	 * 静态变量ditinctId
	 * */
	private static final String WIDTH = "width";

	private GoodsCateService goodsCateService;
	private GoodsTypeService goodsTypeService;
	private GoodsService goodsService;
	private GoodsProductService productService;
	private MarketingService marketingService;
	private SiteGoodsAtteService goodsAtteService;
	private CommentServiceMapper commentServiceMapper;
	private GoodsOpenSpecService goodsOpenSpecService;
	@Resource(name = "SeoService")
	private SeoService seoService;
	@Resource(name = "DefaultAddressService")
	private DefaultAddressService addressService;
	@Resource(name = "GoodsBrandService")
	private GoodsBrandService goodsBrandService;
	@Resource(name = "GoodsTypeBrandService")
	private GoodsTypeBrandService goodsTypeBrandService;
	
	@Resource(name = "lunboInfoService")
	private LunboInfoService lunboInfoService;

	@Resource(name = "lunboTypeService")
	private LunboTypeService lunboTypeService;

	@Resource(name = "lunboGoodService")
	private LunboGoodService lunboGoodService;
	
	

	/**
	 * 从请求中取出登陆的会员iD
	 * 
	 * @param request
	 *            请求对象
	 * @return 拿出的会员Id
	 */
	public Long takeCustIdFromRequest(HttpServletRequest request) {
		return (Long) request.getSession().getAttribute("customerId");
	}

	/**
	 * 根据分类查询商品列表
	 * 
	 * @param cid
	 *            分类ID
	 */
	@RequestMapping("/list")
	public ModelAndView goodsList(Long cid, PageBean pb,
			GoodsSiteSearchBean searchBean, String[] params,
			HttpServletRequest request) {
		pb.setPageSize(20);
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsTypeVo typeVo = null;
		List<GoodsListScreenVo> screenVo = null;
		try {
			screenVo = this.goodsService.calcScreenParam(params, typeVo);
			map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
			map.put("nowcate", goodsCateService.queryCateByCatId(cid));
			Long dId = addressService.getDefaultIdService();
			if (dId == null) {
				dId = 1103L;
			}
			map.put("pb", this.goodsService.searchGoods(pb, searchBean, cid,
					params, dId.toString()));
			map.put("distinctId", dId.toString());
			map.put("searchBean", searchBean);
			map.put("params", screenVo);
			ModelAndView mav = new ModelAndView("goods/goods_list");
			mav.addObject(ValueUtil.MAP, map);
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
			typeVo = null;
			screenVo = null;
		}
	}

	/**
	 * 手机版美妆馆商品列表
	 * 
	 * @param cid
	 * @param pb
	 * @param searchBean
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping("/mzlist")
	public ModelAndView mzgoodsList(Long cid, PageBean pb,
			GoodsSiteSearchBean searchBean, String[] params,
			HttpServletRequest request) {
		pb.setPageSize(20);
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsTypeVo typeVo = null;
		List<GoodsListScreenVo> screenVo = null;
		try {
			GoodsTypeVo goodtype=goodsTypeService.queryGoodsTypeByCatId(cid);
			List<com.ningpai.m.goods.vo.GoodsTypeBrandVo> brands=goodtype.getBrands();
			screenVo = this.goodsService.calcScreenParam(params, typeVo);
			map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
			map.put("nowcate", goodsCateService.queryCateByCatId(cid));
			Long dId = addressService.getDefaultIdService();
			if (dId == null) {
				dId = 1103L;
			}
			map.put("pb", this.goodsService.searchGoods(pb, searchBean, cid,
					params, dId.toString()));
			map.put("distinctId", dId.toString());
			map.put("searchBean", searchBean);
			map.put("params", screenVo);
			map.put("brands", brands);
			ModelAndView mav = new ModelAndView("goods/mzgoods_list");
			mav.addObject(ValueUtil.MAP, map);
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
			typeVo = null;
			screenVo = null;
		}
	}

	/**
	 * 手机版品牌馆展示
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping("/brandview")
	public ModelAndView brandview(PageBean pb, Long brandId, String[] params,
			GoodsSiteSearchBean searchBean, HttpServletRequest request) {
		pb.setPageSize(8);
		Map<String, Object> map = new HashMap<String, Object>();
//		GoodsTypeVo typeVo = null;
		List<GoodsListScreenVo> screenVo = null;
		GoodsBrand brand=goodsBrandService.queryBrandById(brandId);
		if (params == null) {
			params = new String[1];
			// 添加品牌条件
			params[0] = "b" + brandId;
		}
		try {
			Long dId = addressService.getDefaultIdService();
			if (dId == null) {
				dId = 1103L;
			}
			map.put("pb", this.goodsService.searchGoods(pb, searchBean, null,
					params, dId.toString()));
			map.put("distinctId", dId.toString());
			map.put("searchBean", searchBean);
			map.put("params", screenVo);
			map.put("brand", brand);
			ModelAndView mav = new ModelAndView("goods/brand_view");
			mav.addObject(ValueUtil.MAP, map);
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
//			typeVo = null;
			screenVo = null;
		}
	}
	
	
	/**
	 * 手机版类别下商品展示
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping("/typeview")
	public ModelAndView typeview(PageBean pb, Long cid, String[] params,
			GoodsSiteSearchBean searchBean, HttpServletRequest request) {
		pb.setPageSize(8);
		Map<String, Object> map = new HashMap<String, Object>();
		List<GoodsListScreenVo> screenVo = null;
		try {
			Long dId = addressService.getDefaultIdService();
			if (dId == null) {
				dId = 1103L;
			}
			map.put("pb", this.goodsService.searchGoods(pb, searchBean, cid,
					params, dId.toString()));
			map.put("distinctId", dId.toString());
			map.put("searchBean", searchBean);
			map.put("params", screenVo);
			map.put("nowcate", goodsCateService.queryCateByCatId(cid));
			ModelAndView mav = new ModelAndView("goods/type_view");
			mav.addObject(ValueUtil.MAP, map);
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
			screenVo = null;
		}
	}
	
	

	/**
	 * 母婴馆商品列表
	 * 
	 * @param cid
	 * @param pb
	 * @param searchBean
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping("/mylist")
	public ModelAndView mygoodsList(Long cid, PageBean pb,
			GoodsSiteSearchBean searchBean, String[] params,
			HttpServletRequest request) {
		pb.setPageSize(20);
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsTypeVo typeVo = null;
		List<GoodsListScreenVo> screenVo = null;
		try {
			GoodsTypeVo goodtype=goodsTypeService.queryGoodsTypeByCatId(cid);
			List<com.ningpai.m.goods.vo.GoodsTypeBrandVo> brands=goodtype.getBrands();
			screenVo = this.goodsService.calcScreenParam(params, typeVo);
			map.put("brands", brands);
			map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
			map.put("nowcate", goodsCateService.queryCateByCatId(cid));
			Long dId = addressService.getDefaultIdService();
			if (dId == null) {
				dId = 1103L;
			}
			map.put("pb", this.goodsService.searchGoods(pb, searchBean, cid,
					params, dId.toString()));
			map.put("distinctId", dId.toString());
			map.put("searchBean", searchBean);
			map.put("params", screenVo);
			ModelAndView mav = new ModelAndView("goods/mygoods_list");
			mav.addObject(ValueUtil.MAP, map);
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
			typeVo = null;
			screenVo = null;
		}
	}

	/**
	 * 根据Id查询店铺所有商品列表
	 *
	 * @param searchBean
	 *            storeId
	 *
	 */
	@RequestMapping("/allGoodsListByStoreId")
	public ModelAndView allGoodsListByStoreId(PageBean pb,
			GoodsSiteSearchBean searchBean, String[] params,
			HttpServletRequest request) {
		// 显示行数
		pb.setPageSize(20);
		// 创建容器
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsTypeVo typeVo = null;
		List<GoodsListScreenVo> screenVo = null;
		try {
			screenVo = this.goodsService.calcScreenParam(params, typeVo);
			map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
			// 地区
			Long dId = addressService.getDefaultIdService();
			// 设置默认地址
			if (dId == null) {
				dId = 1103L;
			}
			// 查询货品列表
			map.put("pb", this.goodsService.searchGoods(pb, searchBean, null,
					params, dId.toString()));
			map.put("distinctId", dId.toString());
			map.put("searchBean", searchBean);
			map.put("params", screenVo);
			ModelAndView mav = new ModelAndView("goods/goods_list");
			mav.addObject(ValueUtil.MAP, map);
			// 商家编号
			if (searchBean.getStoreId() == null) {
				mav.addObject("storeId", 0);
			} else {
				mav.addObject("storeId", searchBean.getStoreId());
			}
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
			typeVo = null;
			screenVo = null;
		}
	}

	/**
	 * 商品搜索
	 * 
	 * @param params
	 *            参数
	 * @param pb
	 *            分页工具累
	 * @param siteSearchBean
	 *            搜索实体类
	 * @param request
	 * @param response
	 * @return
	 */
	// @RequestMapping("/searchProduct")
	public ModelAndView searchProduct(String[] params, PageBean pb,
			GoodsSiteSearchBean siteSearchBean, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		pb.setPageSize(20);
		// GoodsTypeVo typeVo = null;
		// List<GoodsListScreenVo> screenVo = null;
		try {
			// screenVo = this.goodsService.calcScreenParam(params, typeVo);
			// map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
			// 字符集转换为utf-8
			// String title =
			// StringCommonUtil.changeToUtf8(siteSearchBean.getTitle());
			// String title=new
			// String(siteSearchBean.getTitle().getBytes(Charset.forName("ISO-8859-1")),"utf-8");
			// 特殊字符转义
			String title = StringCommonUtil
					.escapeCharacterEntities(siteSearchBean.getTitle());
			siteSearchBean.setTitle(title);
			Long dId = addressService.getDefaultIdService();
			if (dId == null) {
				dId = 1103L;
			}
			// 搜索结果
			map.put("pb", this.goodsService.searchGoods(pb, siteSearchBean));
			// 地址
			map.put("distinctId", dId.toString());
			// 搜索类
			map.put("searchBean", siteSearchBean);
			// map.put("params", screenVo);
			// map.put("title", siteSearchBean.getTitle());
			// 返回页面
			ModelAndView mav = new ModelAndView("goods/goods_search");
			// 搜索内容
			mav.addObject("title", siteSearchBean.getTitle());
			// 排序
			mav.addObject("sort", siteSearchBean.getSort());
			mav.addObject(ValueUtil.MAP, map);
			// 商家编号
			if (siteSearchBean.getStoreId() == null) {
				mav.addObject("storeId", 0);
			} else {
				mav.addObject("storeId", siteSearchBean.getStoreId());
			}
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
			// typeVo = null;
			// screenVo = null;
		}
	}

	/**
	 * 基于eslaticsearch的搜索
	 * 
	 * @param request
	 * @param pageBean
	 * @param params
	 * @param keyWords
	 * @param sort
	 *            排序
	 * @param storeId
	 *            店铺ID
	 * @return
	 */
	@RequestMapping("/searchProduct")
	public ModelAndView esearchProduct(HttpServletRequest request,
			SearchPageBean<EsGoodsInfo> pageBean, String[] params,
			String[] brands,String keyWords, String sort, Long storeId, String showStock,
			String isThird) {
		pageBean.setPageSize(20);
		// 索引
		String[] indices = new String[] { GoodsIndexConstant.PRODUCT_INDEX_NAME };
		// 类型
		String[] types = new String[] { GoodsIndexConstant.PRODUCT_TYPE };
		// 地区
		Long distinctId = null;
		/* 如果session中的地区ID为空,就设置为默认江苏南京 */
		if (null != request.getSession().getAttribute(DISTINCTID)) {
			distinctId = Long.parseLong(request.getSession()
					.getAttribute(DISTINCTID).toString());
		} else {
			distinctId = addressService.getDefaultIdService();
			if (distinctId == null) {
				distinctId = 1103L;
			}
		}
		// 根据地区ID查询关联仓库ID
		Long wareId = productService.selectWareIdByDistinctId(distinctId);
		Long[] wares = wareId == null ? null : new Long[] { wareId };
		// 商品搜索结果
		Map<String, Object> resultMap = goodsElasticSearchServivice
				.searchGoods(pageBean, wares, indices, types, keyWords, brands,
						null, params, sort, null, null,
						(storeId != null && 0 == storeId) ? null : storeId,
						null, showStock, "1", isThird);
		// 拼装返回结构
		ModelAndView model = new ModelAndView("goods/goods_es_search");
		// 搜索条件
		model.addObject("keyWords", keyWords);
		model.addObject("sort", sort == null ? "" : sort);
		model.addObject(STOREID, storeId == null ? 0L : storeId);
		model.addObject("brands",brands);
		// 查询的地区
		model.addObject(DISTINCTID, distinctId);
		// 查询的仓库
		model.addObject("wareId", wareId);
		// 页面数据
		model.addObject("map", resultMap);
		return seoService.getCurrSeo(model);
	}

	/**
	 * 查询所有移动版商品列表
	 * 
	 * @param params
	 *            分类ID
	 */
	@RequestMapping("/allpro")
	public ModelAndView allGoodsList(PageBean pb,
			GoodsSiteSearchBean searchBean, String[] params,
			HttpServletRequest request) {
		pb.setPageSize(20);
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsTypeVo typeVo = null;
		List<GoodsListScreenVo> screenVo = null;
		try {
			screenVo = this.goodsService.calcScreenParam(params, typeVo);
			map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
			Long dId = addressService.getDefaultIdService();
			if (dId == null) {
				dId = 1103L;
			}
			if (searchBean.getStoreId() == null) {

			}
			map.put("pb", this.goodsService.searchGoods(pb, searchBean, null,
					params, dId.toString()));
			map.put("distinctId", dId.toString());
			map.put("searchBean", searchBean);
			map.put("params", screenVo);
			ModelAndView mav = new ModelAndView("goods/goods_list");
			mav.addObject(ValueUtil.MAP, map);
			if (searchBean.getStoreId() == null) {
				mav.addObject("storeId", 0);
			} else {
				mav.addObject("storeId", searchBean.getStoreId());
			}
			return seoService.getCurrSeo(mav);
		} finally {
			map = null;
			typeVo = null;
			screenVo = null;
		}
	}

	/**
	 * 商品详情页
	 * 
	 * @param productId
	 * @author lih 货品ID
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/productdetail")
	public ModelAndView goodsDetail(Long productId, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		Long dId = addressService.getDefaultIdService();
		// 判断是否有默认地址
		if (dId == null) {
			dId = 1103L;
		}
		/* 地址默认使用南京 */
		map.put("distinctId", dId);
		GoodsDetailBean detailBean = null;
		try {
			// 查询商品详情
			detailBean = this.productService.queryDetailBeanByProductId(
					productId, dId);
			// 判断商品是否下架
			if (null != detailBean) {
				// 限购
				detailBean = cartService.forPurchasing(
						detailBean,
						(Long) request.getSession().getAttribute(
								CustomerConstantStr.CUSTOMERID));
				// 重新为图片排序，默认图片放到第一个
				if (null != detailBean.getProductVo()) {
					List<GoodsImage> imageList = detailBean.getProductVo()
							.getImageList();
					if (CollectionUtils.isNotEmpty(imageList)) {
						for (int i = 0; i < imageList.size(); i++) {
							if ("1".equals(imageList.get(i).getGoodsImgSort()
									.toString())) {
								GoodsImage go = imageList.get(0);
								imageList.set(0, imageList.get(i));
								imageList.set(i, go);
							}
						}
						detailBean.getProductVo().setImageList(imageList);
					}
				}
				// 设置商品
				map.put("detailBean", detailBean);
				// s设置规格参数
				map.put("openSpec", this.goodsOpenSpecService
						.queryOpenListByGoodsId(detailBean.getProductVo()
								.getGoodsId()));
				ModelAndView mav = new ModelAndView("goods/goods_detail",
						ValueUtil.MAP, map);
				return seoService.getCurrSeo(mav);
			} else {
				// 返回异常页面
				return seoService.getCurrSeo(new ModelAndView("goods/no_exit"));
			}
		} catch (Exception e) {
			// 返回异常页面
			return seoService.getCurrSeo(new ModelAndView("goods/no_exit"));
		} finally {
			detailBean = null;
		}
	}

	/**
	 * 根据商品ID查询所有的货品
	 * 
	 * @param goodsId
	 *            商品ID
	 * @return 查询到的货品列表
	 */
	@RequestMapping("/queryProductListByGoodsId")
	@ResponseBody
	public List<Object> queryAllProductByGoodsId(Long goodsId) {
		return this.productService.queryAllProductListByGoodsId(goodsId);
	}

	/**
	 * 根据参数查询商品促销信息
	 * 
	 * @param goodsInfoId
	 *            货品ID
	 * @param brandId
	 *            品牌ID
	 * @param cateId
	 *            分类ID
	 * @return 查询到的促销集合
	 */
	@RequestMapping("/queryGoodsMarket")
	@ResponseBody
	public List<Marketing> queryProductMarketBySomeId(Long goodsInfoId,
			Long brandId, Long cateId) {
		// 查询并返回
		return this.marketingService.selectMarketingByGoodsInfoId(goodsInfoId,
				brandId, cateId);
	}

	/**
	 * 保存商品关注
	 * 
	 * @param productId
	 *            会员iD
	 * @param productId
	 *            商品ID
	 */
	@RequestMapping("/saveAtte")
	@ResponseBody
	public int saveAtte(Long productId, HttpServletRequest request) {
		Long custId = this.takeCustIdFromRequest(request);
		if (null != custId) {
			int result =-1;
			if(goodsAtteService.checkAtte(custId, productId)){
				this.goodsAtteService.deleteGoodAtte(custId, productId);
				result=-1;
			}else{
				this.goodsAtteService.saveGoodsAtte(custId, productId);
				result=1;
			}
			return result;
		} else {
			/* 未登录返回 */
			return -2;
		}
	}

	/**
	 * 根据货品编号查询商品的评论
	 * 
	 * @return 分页对象
	 */
	@RequestMapping("/queryProducCommentForDetail")
	@ResponseBody
	public PageBean queryProductComment(PageBean pb, Long productId,
			String title, Character type, String askType) {
		if (null != askType && !"".equals(askType)) {
			return this.commentServiceMapper.selectCommByGoodsId(pb, productId,
					type, title, askType);
		} else {
			return this.commentServiceMapper.selectCommByGoodsId(pb, productId,
					type, title);
		}
	}

	@RequestMapping("/mobilelunbodetail")
	public ModelAndView lunbodetailM(HttpServletRequest request, int lunboId) {
		LunboInfo lunboinfo=lunboInfoService.findLunboInfoById(lunboId);
		ModelAndView mav = new ModelAndView("lunbo/lunbodetail");
		mav.addObject("lunboinfo", lunboinfo);
		return mav;
	}

	// /**
	// * 查询所有的商品评论
	// * @param productId 货品ID
	// * @param request 请求对象
	// */
	// @RequestMapping("/allComment")
	// public ModelAndView allComment(Long productId,HttpServletRequest
	// request){
	// Map<String, Object> map = new HashMap<String, Object>();
	// Long distinctId = null;
	// /*如果session中的地区ID为空,就设置为默认江苏南京*/
	// if (null !=request.getSession().getAttribute("distinctId") ) {
	// distinctId =
	// Long.parseLong(request.getSession().getAttribute("distinctId").toString());
	// }else{
	// distinctId = 1103L;
	// }
	// map.put("distinctId", distinctId);
	// GoodsDetailBean detailBean =
	// this.productService.queryDetailBeanByProductId(productId,distinctId);
	// try {
	// if (null != detailBean.getProductVo()) {
	// map.put("detailBean", detailBean);
	// /*查询商品关联的标签*/
	// map.put("tags",
	// this.goodsPub.getGoodsReleTagService().queryreleListByProductId(detailBean.getProductVo().getGoodsInfoId()));
	// ModelAndView mav = new ModelAndView("comment/comment", ValueUtil.MAP,
	// map);
	// return mav;
	// } else {
	// return null;
	// }
	// } finally {
	// detailBean = null;
	// map = null;
	// distinctId = null;
	// }
	// }

	@Resource(name = "ShoppingCartService")
	private ShoppingCartService cartService;

	public GoodsProductService getProductService(Long askId) {
		return productService;
	}

	@Resource(name = "HsiteGoodsProductService")
	public void setProductService(GoodsProductService productService) {
		this.productService = productService;
	}

	public GoodsService getGoodsService() {
		return goodsService;
	}

	@Resource(name = "HsiteGoodsService")
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public GoodsTypeService getGoodsTypeService() {
		return goodsTypeService;
	}

	@Resource(name = "HsiteGoodsTypeService")
	public void setGoodsTypeService(GoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}

	public GoodsCateService getGoodsCateService() {
		return goodsCateService;
	}

	@Resource(name = "HsiteGoodsCateService")
	public void setGoodsCateService(GoodsCateService goodsCateService) {
		this.goodsCateService = goodsCateService;
	}

	public MarketingService getMarketingService() {
		return marketingService;
	}

	@Resource(name = "MarketingService")
	public void setMarketingService(MarketingService marketingService) {
		this.marketingService = marketingService;
	}

	public SiteGoodsAtteService getGoodsAtteService() {
		return goodsAtteService;
	}

	@Resource(name = "SiteGoodsAtteService")
	public void setGoodsAtteService(SiteGoodsAtteService goodsAtteService) {
		this.goodsAtteService = goodsAtteService;
	}

	public CommentServiceMapper getCommentServiceMapper() {
		return commentServiceMapper;
	}

	@Resource(name = "commentServiceMapper")
	public void setCommentServiceMapper(
			CommentServiceMapper commentServiceMapper) {
		this.commentServiceMapper = commentServiceMapper;
	}

	/**
	 * 在cookie里添加搜索记录
	 * 
	 * @author NINGPAI-WangHaiYang
	 */
	// private void addCookieToSearchProduct(String title, HttpServletRequest
	// request,HttpServletResponse response){
	//
	// log.debug("=================================="+title);
	// //生命周期
	// int maxAge = 60*60*24*3;
	// //获取搜索历史
	// Cookie cookie = NpCookieUtil.getCookieByName(request,
	// SearchCookieController.COOKIENAME);
	// String list = null;
	// //添加搜索历史
	// if(null != cookie){
	// try {
	// list = URLDecoder.decode(cookie.getValue(),ConstantUtil.UTF);
	// //判断要添加的搜索历史是否存在
	// if(list.indexOf(title)==-1){
	// list = title + ","+ list;
	// }
	//
	// } catch (UnsupportedEncodingException e) {
	// log.error(e.getLocalizedMessage());
	// }
	// }else{
	// list = title + ",";
	// }
	// try {
	// // NpCookieUtil.addCookie(response, SearchCookieController.COOKIENAME,
	// list, maxAge);
	// } catch (UnsupportedEncodingException e) {
	// log.error(e.getLocalizedMessage());
	// }
	// }
	public GoodsOpenSpecService getGoodsOpenSpecService() {
		return goodsOpenSpecService;
	}

	@Resource(name = "GoodsOpenSpecService")
	public void setGoodsOpenSpecService(
			GoodsOpenSpecService goodsOpenSpecService) {
		this.goodsOpenSpecService = goodsOpenSpecService;
	}

	/** es搜索处理service **/
	@Resource(name = "GoodsElasticSearchService")
	private GoodsElasticSearchService goodsElasticSearchServivice;

	public GoodsBrandService getGoodsBrandService() {
		return goodsBrandService;
	}

	public void setGoodsBrandService(GoodsBrandService goodsBrandService) {
		this.goodsBrandService = goodsBrandService;
	}

	public GoodsTypeBrandService getGoodsTypeBrandService() {
		return goodsTypeBrandService;
	}

	public void setGoodsTypeBrandService(GoodsTypeBrandService goodsTypeBrandService) {
		this.goodsTypeBrandService = goodsTypeBrandService;
	}

	public LunboInfoService getLunboInfoService() {
		return lunboInfoService;
	}

	public void setLunboInfoService(LunboInfoService lunboInfoService) {
		this.lunboInfoService = lunboInfoService;
	}

	public LunboTypeService getLunboTypeService() {
		return lunboTypeService;
	}

	public void setLunboTypeService(LunboTypeService lunboTypeService) {
		this.lunboTypeService = lunboTypeService;
	}

	public LunboGoodService getLunboGoodService() {
		return lunboGoodService;
	}

	public void setLunboGoodService(LunboGoodService lunboGoodService) {
		this.lunboGoodService = lunboGoodService;
	}
	
	
}
