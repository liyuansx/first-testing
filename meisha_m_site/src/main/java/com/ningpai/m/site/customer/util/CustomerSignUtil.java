package com.ningpai.m.site.customer.util;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
/**
 * 用户签名工具类
 * @author kidd
 *
 */
public class CustomerSignUtil {
	/**
	 * 簽名
	 * @param data
	 * @return
	 */
	public static String signString(Map<String, String> data) {

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

		return sb.toString() + Constant.securityCheckKey;
	}
}
