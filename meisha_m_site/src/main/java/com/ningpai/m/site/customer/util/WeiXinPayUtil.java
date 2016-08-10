package com.ningpai.m.site.customer.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.ningpai.order.bean.Order;
import com.ningpai.system.bean.Pay;

public class WeiXinPayUtil {
	/**
	 * 微信跳转支付
	 */
	public static String weixin() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("partnerId", "20151207020009512588");
		data.put("signType", Constant.signType);
		data.put("returnUrl", "http://120.24.214.184/returnUrl");
		data.put("notifyUrl", "http://120.24.214.184/notifyUrl");
		data.put("protocol", "httpPost");

		// 用时分秒生成一个20-40位的orderNo，推荐8位日期+8-12位业务序号
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssssSSSS");
		String orderNo = df.format(new Date());
//		data.put("orderNo", orderNo);
		data.put("orderNo", "201607163131291305");
//		data.put("outOrderNo", orderNo);  201607163131291305
		data.put("outOrderNo", "201607163131291305");
		data.put("service", "commonWchatTradeRedirect");
		data.put("sellerUserId", "20140411020055684571");
		data.put("tradeAmount", "50.36");
		// data.put("uiStyle", "手机web版");
		data.put("goodsClauses", "[{\"name\":\"微信跳转支付测试\"}]");
		data.put("version", "1.0");
//		String signString = signString(data, "c9cef22553af973d4b04a012f9cb8ea8");// <========生成待签名字符串
		String signString = signString(data, "7ecf02ac8e8d7c41082f2481fb1e105d");// <========生成待签名字符串
		String sign = MD5.MD5(signString);// <=======================签名
		data.put("sign", sign);
		// forLink(data);// <=======生成链接
		String result = Http.postString(data,"https://api.yiji.com/gateway.html");
		System.out.println(result);
		return result;
	}
	
	public static String signString(Map<String, String> data, String securityCheckKey) {

		TreeMap<String, String> treeMap = new TreeMap<String, String>(data);

		// Map<String, String> treeMap = new HashMap<String,String>(data);

		StringBuilder sb = new StringBuilder();

		for (Entry<String, String> entry : treeMap.entrySet()) {
			if (entry.getKey().equals("sign")) {
				continue;
			}
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);

		// System.out.println("待签名字符串1:" + sb.toString() + securityCheckKey +
		// "\n");

		return sb.toString() + securityCheckKey;
	}
}
