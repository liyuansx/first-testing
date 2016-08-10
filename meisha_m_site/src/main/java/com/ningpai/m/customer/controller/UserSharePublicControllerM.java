package com.ningpai.m.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ningpai.comment.bean.Share;
import com.ningpai.comment.bean.ShareType;
import com.ningpai.comment.bean.UserSharePublic;
import com.ningpai.comment.service.CommentServiceMapper;
import com.ningpai.comment.service.ShareService;
import com.ningpai.comment.service.ShareTypeService;
import com.ningpai.comment.service.UserSharePublicServiceMapper;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.util.PageBean;

/**
 * 
 * @author wangding 时间：2016年5月12日 上午10:19:39 功能:手机版分享功能
 */
@Controller
public class UserSharePublicControllerM {
	@Resource(name = "shareServiceNew")
	private ShareService shareServiceMapper;
	@Resource(name = "shareTypeServiceMapper")
	private ShareTypeService sharetypeService;
	@Resource(name = "userSharePublicServiceMapper")
	private UserSharePublicServiceMapper userSharePublicServiceMapper;
	@Resource(name = "SeoService")
	private SeoService seoService;
	@Resource(name = "customerServiceMapper")
	private CustomerServiceMapper customerServiceMapper;
	
	private CommentServiceMapper commentServiceMapper;

	public SeoService getSeoService() {
		return seoService;
	}

	public void setSeoService(SeoService seoService) {
		this.seoService = seoService;
	}

	public ShareService getShareServiceMapper() {
		return shareServiceMapper;
	}

	public void setShareServiceMapper(ShareService shareServiceMapper) {
		this.shareServiceMapper = shareServiceMapper;
	}

	public ShareTypeService getSharetypeService() {
		return sharetypeService;
	}

	public void setSharetypeService(ShareTypeService sharetypeService) {
		this.sharetypeService = sharetypeService;
	}

	public UserSharePublicServiceMapper getUserSharePublicServiceMapper() {
		return userSharePublicServiceMapper;
	}

	public void setUserSharePublicServiceMapper(
			UserSharePublicServiceMapper userSharePublicServiceMapper) {
		this.userSharePublicServiceMapper = userSharePublicServiceMapper;
	}

	public CommentServiceMapper getCommentServiceMapper() {
		return commentServiceMapper;
	}

	@Resource(name = "commentServiceMapper")
	public void setCommentServiceMapper(
			CommentServiceMapper commentServiceMapper) {
		this.commentServiceMapper = commentServiceMapper;
	}

	@RequestMapping("/mobileusershare")
	public ModelAndView userSharingM(HttpServletRequest request,
			PageBean pageBean, String sharetypeId) {
		Long stId = Long
				.parseLong(sharetypeId == null || sharetypeId == "" ? "0"
						: sharetypeId);
		// 当前登录成功的会员ID
		Long custId = this.takeCustIdFromRequest(request);
		// 设置访问路径
		pageBean.setUrl("mobileusershare.htm"); 
		pageBean.setPageSize(12);
		PageBean pb = userSharePublicServiceMapper.selectAllUserShare(pageBean,
				custId, stId);
		List<ShareType> sharetypes = sharetypeService.getFrontall();
		sharetypes = getList(2, sharetypes);
		ModelAndView mav = new ModelAndView("usershare/userSharing");
		mav.addObject("pb", pb);
		mav.addObject("sharetypes", sharetypes);
		mav.addObject("sharetypeId", stId);
		mav.addObject("isfbBiji", 1);
		return seoService.getCurrSeo(mav);
	}
	
	@RequestMapping("/mobilesingleusershare")
	public ModelAndView singleuserSharingM(HttpServletRequest request,
			PageBean pageBean, String sharetypeId) {
		Long stId = Long
				.parseLong(sharetypeId == null || sharetypeId == "" ? "0"
						: sharetypeId);
		// 当前登录成功的会员ID
		Long custId = this.takeCustIdFromRequest(request);
		// 设置访问路径
		pageBean.setUrl("mobilesingleusershare.htm"); 
		pageBean.setPageSize(12);
		PageBean pb =userSharePublicServiceMapper.selectUserShareBycustId(pageBean, custId);
		List<ShareType> sharetypes = sharetypeService.getFrontall();
		sharetypes = getList(2, sharetypes);
		ModelAndView mav = new ModelAndView("usershare/singleuserSharing");
		mav.addObject("pb", pb);
		mav.addObject("sharetypes", sharetypes);
		mav.addObject("sharetypeId", stId);
		mav.addObject("isfbBiji", 1);
		return seoService.getCurrSeo(mav);
	}
	
	@RequestMapping("/mobilesingleuserthemeshare")
	public ModelAndView singleuserThemeShareM(HttpServletRequest request,
			PageBean pageBean, String sharetypeId) {
		Long stId = Long
				.parseLong(sharetypeId == null || sharetypeId == "" ? "0"
						: sharetypeId);
		// 当前登录成功的会员ID
		Long custId = this.takeCustIdFromRequest(request);
		// 设置访问路径
		pageBean.setUrl("mobileusershare.htm");
		pageBean.setPageSize(12);
		PageBean pb = userSharePublicServiceMapper.selectUserShareBycustId(pageBean, custId);
		ShareType sharetype=sharetypeService.getShareTypeById(Integer.parseInt(sharetypeId));
		ModelAndView mav = new ModelAndView("usershare/singleuserThemeSharing");
		mav.addObject("pb", pb);
		mav.addObject("sharetype", sharetype);
		mav.addObject("sharetypeId", stId);
		mav.addObject("isfbBiji", 1);
		return seoService.getCurrSeo(mav);
	}
	/**
	 * 手机版分享详情
	 * 
	 * @param request
	 * @param commentId
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/mobilesharedetail")
	public ModelAndView sharedetailM(HttpServletRequest request,
			Long commentId, Long customerId) {
		ModelAndView mav = new ModelAndView("usershare/shareDetail");
		 Map<String, Object> maps = new HashMap<>();
	        // 当前登录成功的会员ID
	        Long custId = this.takeCustIdFromRequest(request);
	        // 当前登录成功的会员信息
	        CustomerAllInfo customer = customerServiceMapper
	                .selectByPrimaryKey(customerId);
	        UserSharePublic userSharePublic = userSharePublicServiceMapper.selectByPublicUserIdAndCustId(commentId, custId);
	        //通过用户分享Id查询晒单
	        Share share = userSharePublicServiceMapper.selectShareByPublicUserId(commentId);
	        //通过晒单Id查询评论图片集合
	        List<Object> comentImgs = null;
	        if (share != null) {
	            comentImgs = userSharePublicServiceMapper.queryAllImgsByShareId(share.getShareId());
	        }
	        List<Object> VComent = userSharePublicServiceMapper.selectVCommentByPublicUserId(commentId);
	        maps.put("custId", custId);
	        maps.put("customer", customer);
	        maps.put("comment", userSharePublic);
	        maps.put("comentImgs", comentImgs);
	        maps.put("listVComent", VComent);
	        maps.put("share", share);
	        mav.addObject("map", maps);
		return seoService.getCurrSeo(mav);
	}
	
	
	@RequestMapping("/mobileuserthemeshare")
	public ModelAndView userThemeShareM(HttpServletRequest request,
			PageBean pageBean, String sharetypeId) {
		Long stId = Long
				.parseLong(sharetypeId == null || sharetypeId == "" ? "0"
						: sharetypeId);
		// 当前登录成功的会员ID
		Long custId = this.takeCustIdFromRequest(request);
		// 设置访问路径
		pageBean.setUrl("mobileusershare.htm");
		pageBean.setPageSize(12);
		PageBean pb = userSharePublicServiceMapper.selectAllUserShare(pageBean,
				custId, stId);
		ShareType sharetype=sharetypeService.getShareTypeById(Integer.parseInt(sharetypeId));
		ModelAndView mav = new ModelAndView("usershare/userThemeSharing");
		mav.addObject("pb", pb);
		mav.addObject("sharetype", sharetype);
		mav.addObject("sharetypeId", stId);
		mav.addObject("isfbBiji", 1);
		return seoService.getCurrSeo(mav);
	}

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
	 * 获取集合数据
	 * 
	 * @param num获取是数量
	 * @param list
	 * @return 王定修改注释
	 */
	private List<ShareType> getList(int num, List<ShareType> list) {
		if (list == null || list.size() <= num) {
			return list;
		}
		List<ShareType> newlist = new ArrayList<ShareType>();
		for (int i = 0; i < num; i++) {
			newlist.add(list.get(i));
		}
		return newlist;
	}
}
