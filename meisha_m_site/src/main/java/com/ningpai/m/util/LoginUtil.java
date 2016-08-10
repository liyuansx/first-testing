/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.util;

import javax.servlet.http.HttpServletRequest;

public final class LoginUtil {
    
    private LoginUtil(){
    }

    /**
     * 验证登录
     * 
     * @param request
     * @return false 未登录 true已登录
     */
    public static boolean checkLoginStatus(HttpServletRequest request) {
        if (request.getSession().getAttribute("cust") == null) {
            return false;
        }
        return true;
    }
}
