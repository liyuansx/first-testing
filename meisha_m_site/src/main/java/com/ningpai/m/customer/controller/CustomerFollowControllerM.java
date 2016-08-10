package com.ningpai.m.customer.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.BrowserecordService;
import com.ningpai.m.customer.service.CustomerFollowService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.util.PageBean;

@Controller
public class CustomerFollowControllerM {
    // 收藏service
    private CustomerFollowService customerFollowService;
	@Resource(name = "SeoService")
	private SeoService seoService;
	@Resource(name = "customerServiceMapper")
	private CustomerServiceMapper customerServiceMapper;
	
    private BrowserecordService browserecordService;
    
	public SeoService getSeoService() {
		return seoService;
	}
	public void setSeoService(SeoService seoService) {
		this.seoService = seoService;
	}
	public CustomerServiceMapper getCustomerServiceMapper() {
		return customerServiceMapper;
	}
	public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
		this.customerServiceMapper = customerServiceMapper;
	}
	public CustomerFollowService getCustomerFollowService() {
		return customerFollowService;
	}
    @Resource(name = "customerFollowServiceSite")
	public void setCustomerFollowService(CustomerFollowService customerFollowService) {
		this.customerFollowService = customerFollowService;
	}
    
    public BrowserecordService getBrowserecordService() {
        return browserecordService;
    }

    @Resource(name = "browserecordService")
    public void setBrowserecordService(BrowserecordService browserecordService) {
        this.browserecordService = browserecordService;
    }
    /**
     * 心愿单控制器
     * @param request
     * @param pageBean
     * @return
     */
    @RequestMapping("/wishlist")
	public ModelAndView WishListM(HttpServletRequest request,
			PageBean pb,String date) { 
    	 Long custId = this.takeCustIdFromRequest(request);
		 Map<String, Object> paramMap = new HashMap<>();
	        Map<String, Object> resultMap = new HashMap<>();
	        pb.setUrl("wishlist.htm");
	        pb.setPageSize(12);
	        ModelAndView mav = null;
	        try {
	            // 验证登录
	            if (custId!=null && custId!=0) {
	                paramMap.put("customerId",
	                        (Long) request.getSession().getAttribute("customerId"));
	                paramMap.put("date", date);
	                resultMap.put("date", date);
	                mav = new ModelAndView("customer/follow");
	                mav.addObject("pb", customerFollowService
	                                .selectCustomerFollow(paramMap, pb));
	                mav.addAllObjects(resultMap);
	            }else{
	            	mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
	            }
		        return seoService.getCurrSeo(mav);
	        } finally {
	            mav = null;
	        }
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
}
