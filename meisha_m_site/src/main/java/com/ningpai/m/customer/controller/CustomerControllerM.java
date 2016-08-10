/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.util.LoginUtil;

/**
 * 手机端会员控制层
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年8月20日 上午10:16:32
 * @version 0.0.1
 */
@Controller
public class CustomerControllerM {

    // spring 注解 会员service
    private CustomerService customerService;

    /**
     * 会员中心
     * 
     * @return {@link ModelAndView}
     */
    @RequestMapping("/customer/indexm")
    public ModelAndView index(HttpServletRequest request) {
        // 跳转个人中心
        ModelAndView mav = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 验证登录
            if (LoginUtil.checkLoginStatus(request)) {
                request.getSession().setAttribute("cust", customerService.selectByPrimaryKey((Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID)));
                mav = new ModelAndView(CustomerConstants.CUSTOMERINDEX);
                mav.addAllObjects(resultMap);
            } else {
                mav = new ModelAndView("redirect:/login.html?url=/customer/index.html");
            }
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
            resultMap = null;
        }
    }

    /**
     * 发送手机验证码
     * 
     * @throws IOException
     */
    @RequestMapping("/sendcode")
    @ResponseBody
    public int sendcode(HttpServletRequest request, String moblie) throws IOException {
        return customerService.sendPost(request, moblie);
    }

    /**
     * 检查手机验证码
     * 
     * @return 0不同 1相同
     * @throws IOException
     */
    @RequestMapping("validate/getMCode")
    @ResponseBody
    public int getMCode(HttpServletRequest request, String code) throws IOException {
        return customerService.getMCode(request, code);
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    @Resource(name = "customerServiceM")
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Resource(name = "SeoService")
    private SeoService seoService;

}
