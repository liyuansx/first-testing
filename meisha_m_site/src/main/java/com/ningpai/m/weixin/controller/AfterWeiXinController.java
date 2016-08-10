/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.weixin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.dao.CustomerMapper;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.weixin.bean.ThreePart;
import com.ningpai.m.weixin.service.ThreePartService;
import com.ningpai.m.weixin.util.WeiXinUtil;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.other.util.IPAddress;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.service.AuthService;
import com.ningpai.system.service.PayService;

/**
 * 微信回调
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年8月26日 下午4:14:20
 * @version 0.0.1
 */
@Controller
public class AfterWeiXinController {
    /**
     * 获取微信用户Code
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws HttpException
     */
    @RequestMapping("getwxtoken")
    protected ModelAndView getWXToken(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // 获取code
        String code = request.getParameter("code");
        if (code == null) {
            return new ModelAndView("redirect:/main.html");
        }
        // 获取微信接口AppId...
        Auth auth = authService.findAuthByAuthType("7");
        if (auth != null) {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + auth.getAuthClientId()
                    + "&secret="
                    + auth.getAuthClientSecret()
                    + "&code="
                    + code
                    + "&grant_type=authorization_code";
            GetMethod getTokenMethod = new GetMethod(url);
            HttpClient clientToken = new HttpClient();
            getTokenMethod.getParams().setContentCharset("utf-8");
            String res = "";
            Map<String, String> resultMap = null;
            try {
                // 获取token
                clientToken.executeMethod(getTokenMethod);
                res = getTokenMethod.getResponseBodyAsString();
                resultMap = WeiXinUtil.getWeiToken(res);
                if (resultMap == null) {
                    return throwNullPointer(request);
                }
                url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                        + resultMap.get("access_token") + "&openid="
                        + resultMap.get("openid") + "&lang=zh_CN";
                GetMethod getTokenInfo = new GetMethod(url);
                HttpClient clientInfo = new HttpClient();
                getTokenInfo.getParams().setContentCharset("utf-8");
                res = "";
                try {
                    getWXLogin(request, code, resultMap, getTokenInfo,
                            clientInfo);
                } catch (Exception e) {
                    // 获取token失败
                    OperaLogUtil.addOperaException("Getting userinfo failed!",
                            e, request);
                    return new ModelAndView("redirect:/main.html");
                }

            } catch (Exception e) {
                // 发送获取token请求失败
                OperaLogUtil.addOperaException(
                        "Sending getwxtoken request failed!", e, request);
                return new ModelAndView("redirect:/main.html");
            }
        } else {
            try {
                // 微信接口数据未获取 throw NullPointerException...
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException("Getting WEIXIN set failed!", e,
                        request);
                return new ModelAndView("redirect:/main.html");
            }
        }
        return new ModelAndView("redirect:/addcouponpx.htm");
    }

    private ModelAndView throwNullPointer(HttpServletRequest request) {
        try {
            // 获取数据失败 throw NullPointerException ...
            throw new NullPointerException();
        } catch (Exception e) {
            // 获取token失败
            OperaLogUtil.addOperaException("Getting token failed!", e, request);
            return new ModelAndView("redirect:/main.html");
        }
    }

    /**
     * 获取微信用户Code
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws HttpException
     */
    @RequestMapping("getwxtoken1")
    protected ModelAndView getWXToken1(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // 获取code
        String code = request.getParameter("code");
        if (code == null) {
            return new ModelAndView("redirect:/main.html");
        }
        // 获取微信接口AppId...
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null) {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + auth.getAuthClientId()
                    + "&secret="
                    + auth.getAuthClientSecret()
                    + "&code="
                    + code
                    + "&grant_type=authorization_code";
            GetMethod getTokenMethod = new GetMethod(url);
            HttpClient clientToken = new HttpClient();
            getTokenMethod.getParams().setContentCharset("utf-8");
            String res = "";
            Map<String, String> resultMap = null;
            try {
                // 获取token
                clientToken.executeMethod(getTokenMethod);
                res = getTokenMethod.getResponseBodyAsString();
                resultMap = WeiXinUtil.getWeiToken(res);
                if (resultMap == null) {
                    return throwNullPointer(request);
                }
                url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                        + resultMap.get("access_token") + "&openid="
                        + resultMap.get("openid") + "&lang=zh_CN";
                GetMethod getTokenInfo = new GetMethod(url);
                HttpClient clientInfo = new HttpClient();
                getTokenInfo.getParams().setContentCharset("utf-8");
                res = "";
                try {
                    getWXLogin(request, code, resultMap, getTokenInfo,
                            clientInfo);
                } catch (Exception e) {
                    // 获取token失败
                    OperaLogUtil.addOperaException("Getting userinfo failed!",
                            e, request);
                    return new ModelAndView("redirect:/main.html");
                }

            } catch (Exception e) {
                // 发送获取token请求失败
                OperaLogUtil.addOperaException(
                        "Sending getwxtoken request failed!", e, request);
                return new ModelAndView("redirect:/main.html");
            }
        } else {
            try {
                // 微信接口数据未获取 throw NullPointerException...
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException("Getting WEIXIN set failed!", e,
                        request);
                return new ModelAndView("redirect:/main.html");
            }
        }
        return new ModelAndView("redirect:/main.html");
    }

    /**
     * 获取微信用户Code
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws HttpException
     */
    @RequestMapping("getwxtoken2")
    protected ModelAndView getWXToken2(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // 获取code
        String code = request.getParameter("code");
        if (code == null) {
            return new ModelAndView("redirect:/main.html");
        }
        // 获取微信接口AppId...
        Auth auth = authService.findAuthByAuthType("7");
        if (auth != null) {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + auth.getAuthClientId()
                    + "&secret="
                    + auth.getAuthClientSecret()
                    + "&code="
                    + code
                    + "&grant_type=authorization_code";
            GetMethod getTokenMethod = new GetMethod(url);
            HttpClient clientToken = new HttpClient();
            getTokenMethod.getParams().setContentCharset("utf-8");
            String res = "";
            Map<String, String> resultMap = null;
            try {
                // 获取token
                clientToken.executeMethod(getTokenMethod);
                res = getTokenMethod.getResponseBodyAsString();
                resultMap = WeiXinUtil.getWeiToken(res);
                if (resultMap == null) {
                    return throwNullPointer(request);
                }
                url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                        + resultMap.get("access_token") + "&openid="
                        + resultMap.get("openid") + "&lang=zh_CN";
                GetMethod getTokenInfo = new GetMethod(url);
                HttpClient clientInfo = new HttpClient();
                getTokenInfo.getParams().setContentCharset("utf-8");
                res = "";
                try {
                    getWXLogin(request, code, resultMap, getTokenInfo,
                            clientInfo);
                } catch (Exception e) {
                    // 获取token失败
                    OperaLogUtil.addOperaException("Getting userinfo failed!",
                            e, request);
                    return new ModelAndView("redirect:/main.html");
                }

            } catch (Exception e) {
                // 发送获取token请求失败
                OperaLogUtil.addOperaException(
                        "Sending getwxtoken request failed!", e, request);
                return new ModelAndView("redirect:/main.html");
            }
        } else {
            try {
                // 微信接口数据未获取 throw NullPointerException...
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException("Getting WEIXIN set failed!", e,
                        request);
                return new ModelAndView("redirect:/main.html");
            }
        }
        return new ModelAndView("redirect:/"
                + request.getSession().getAttribute("otherPayUrl"));
    }

    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    @Resource(name = "authService")
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    private CustomerMapper customerMapper;

    private CustomerServiceMapper customerServiceMapper;

    private CustomerService customerService;

    public CustomerService getCustomerService() {
        return customerService;
    }

    @Resource(name = "customerServiceM")
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerServiceMapper getCustomerServiceMapper() {
        return customerServiceMapper;
    }

    @Resource(name = "customerServiceMapper")
    public void setCustomerServiceMapper(
            CustomerServiceMapper customerServiceMapper) {
        this.customerServiceMapper = customerServiceMapper;
    }

    public CustomerMapper getCustomerMapper() {
        return customerMapper;
    }

    @Resource(name = "customerMapper")
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    private ThreePartService threePartService;

    public ThreePartService getThreePartService() {
        return threePartService;
    }

    @Resource(name = "threePartServiceM")
    public void setThreePartService(ThreePartService threePartService) {
        this.threePartService = threePartService;
    }

    private void getWXLogin(HttpServletRequest request, String code,
            Map<String, String> resultMap, GetMethod getTokenInfo,
            HttpClient clientInfo) throws IOException {
        String res;
        Map<String, String> userMap;
        clientInfo.executeMethod(getTokenInfo);
        res = getTokenInfo.getResponseBodyAsString();
        // 成功获取微信用户信息 下面开始解析用户信息
        userMap = WeiXinUtil.getWeiXinInfo(res);
        // 验证存在 用户已存在 直接登录 用户不存在 则执行注册流程
        ThreePart tp = threePartService
                .selectThreePart(resultMap.get("openid"));
        if (tp != null) {
            // 获取用户信息
            Customer cus = customerService.selectByPrimaryKey(tp
                    .getThreePartMemberId());
            request.getSession().setAttribute("cust", cus);
            request.getSession()
                    .setAttribute("customerId", cus.getCustomerId());
            request.getSession().setAttribute("isWx", "1");
        } else {
            // 注册
            CustomerAllInfo allInfo = new CustomerAllInfo();
            allInfo.setLoginIp(IPAddress.getIpAddr(request));
            allInfo.setCustomerUsername(resultMap.get("openid"));
            allInfo.setCustomerPassword("");
            allInfo.setCustomerNickname(userMap.get("nickname"));
            allInfo.setInfoGender(userMap.get("sex"));
            allInfo.setCustomerImg(userMap.get("headimgurl").toString());
            // 将微信用户添加到会员列表中
            if (customerServiceMapper.addCustomer(allInfo) == 1) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("username", resultMap.get("openid"));
                paramMap.put("password", "");
                Customer customer = customerMapper
                        .selectCustomerByNamePwd(paramMap);
                tp = new ThreePart();
                tp.setThreePartUid(resultMap.get("openid"));
                tp.setThreePartToken(resultMap.get("access_token"));
                tp.setThreePartMemberId(customer.getCustomerId());
                threePartService.insertThreePart(tp);
            }
            Customer cus = customerService.selectByPrimaryKey(tp
                    .getThreePartMemberId());
            request.getSession().setAttribute("cust", cus);
            request.getSession()
                    .setAttribute("customerId", cus.getCustomerId());
            request.getSession().setAttribute("isWx", "1");
        }
        Pay pay = payService.findByPayId(37L);
        request.getSession().setAttribute("accessToken",
                resultMap.get("access_token"));
        request.getSession().setAttribute("appid", pay.getApiKey());
        request.getSession().setAttribute("state",
                request.getParameter("state"));
        // 微信授权code
        request.getSession().setAttribute("code", code);
        request.getSession().setAttribute("openid", resultMap.get("openid"));
    }

    @Resource(name = "payService")
    private PayService payService;
}
