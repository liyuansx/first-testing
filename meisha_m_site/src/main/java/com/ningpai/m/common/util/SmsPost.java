/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.ningpai.util.MSMSendUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.m.common.bean.Sms;
import com.ningpai.system.service.BasicSetService;

/**
 * 短信验证码辅助类
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年6月12日 下午3:44:51
 * @version 0.0.1
 */
@Service
public class SmsPost {

    private SmsPost() {

    }

    /**
     * 短信发送
     * 
     * @param sms
     *            接口帮助类
     * @return
     * @throws IOException
     */
    public static boolean sendPost(Sms sms) throws IOException {
        sms.setMsgContext("你本次的验证码是："+sms.getMsgContext()+"。");
        return MSMSendUtil.sendMsm("", sms.getLoginName(), sms.getPassword(), sms.getHttpUrl(), new String[]{sms.getSendSim()}, sms.getMsgContext());
    }

    /**
     * zyer send
     * 
     * @param sms
     * @return
     * @throws IOException
     */
    private static boolean meriSSend(Sms sms) throws IOException {
        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using java.net.URL and //java.net.URLConnection http://sdk.zyer.cn/SmsService/SmsService.asmx/SendEx
         */
        URL url;
        url = new URL("http://222.185.228.25:8000/msm/sdk/http/sendsmsutf8.jsp");
        URLConnection connection = url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        /**
         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
         */
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), ConstantUtil.UTF);
        String param = "";
        param += "username=" + sms.getLoginName();
        param += "&scode=" + sms.getPassword();
        param += "&content=@1@=" + basicSetService.findBasicSet().getBsetName() + ",@2@=" + sms.getMsgContext();
        param += "&tempid=MB-2013102300";
        param += "&mobile=" + sms.getSendSim();
        // post的关键所在！
        out.write(param);
        // remember to clean up
        out.flush();
        out.close();
        /**
         * 这样就可以发送一个看起来象这样的POST： POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT: text/plain Content-type: application/x-www-form-urlencoded Content-length: 99 username=bob password=someword
         */
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine;
        StringBuilder sTotalString = new StringBuilder();
        sCurrentLine = "";
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString.append(sCurrentLine);
            // sTotalString.append("\r\n");
        }

        if (Pattern.compile("^ 0#[0-9]*#[0-9]*$").matcher(sTotalString.toString()).find()) {
            return true;
        }
        return false;
    }

    /**
     * meisheng send
     * 
     * @param sms
     * @return
     * @throws IOException
     */
    public static boolean zyerSend(Sms sms) throws IOException {
        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using java.net.URL and //java.net.URLConnection http://sdk.zyer.cn/SmsService/SmsService.asmx/SendEx
         */
        URL url = new URL(sms.getHttpUrl());
        URLConnection connection = url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        /**
         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
         */
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GB2312");
        String param = "";
        param += "LoginName=" + sms.getLoginName();
        param += "&Password=" + sms.getPassword();
        param += "&SendSim=" + sms.getSendSim();
        param += "&SmsKind=" + sms.getSmsKind();
        param += "&ExpSmsId=";
        param += "&MsgContext=" + "您好," + basicSetService.findBasicSet().getBsetName() + "提醒您,您本次的验证码是" + sms.getMsgContext() + "。【宁派科技】";
        // post的关键所在！
        out.write(param);
        // remember to clean up
        out.flush();
        out.close();
        /**
         * 这样就可以发送一个看起来象这样的POST： POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT: text/plain Content-type: application/x-www-form-urlencoded Content-length: 99 username=bob password=someword
         */
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine;
        StringBuilder sTotalString = new StringBuilder();
        sCurrentLine = "";
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString.append(sCurrentLine);
            sTotalString.append("\r\n");
        }
        String[] s = sTotalString.toString().split("<Code>0</Code>");
        if (s.length > 1) {
            return true;
        }
        return false;
    }

    /**
     * @param sms
     * @return
     */
    public static boolean sendPostAA(Sms sms) throws IOException {

        String param = sms.getHttpUrl();
        param += "?name=" + sms.getLoginName();
        param += "&pwd=" + sms.getPassword();
        param += "&content=" + "这是" + basicSetService.findBasicSet().getBsetName() + "给您发送的校验码：" + sms.getMsgContext() + "，打死不能告诉别人哦！【宁派科技】";
        param += "&pid=" + 52;
        param += "&dest=" + sms.getSendSim();
        PostMethod getMethod = new PostMethod(param);
        HttpClient client = new HttpClient();
        client.executeMethod(getMethod);
        return false;
    }

    /** 站点信息业务接口 */
    private static BasicSetService basicSetService;

    public BasicSetService getBasicSetService() {
        return basicSetService;
    }

    @Resource(name = "basicSetService")
    public void setBasicSetService(BasicSetService basicSetService) {
        SmsPost.basicSetService = basicSetService;
    }

}
