package com.ningpai.m.site.customer.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public final class Http {
	 public static String postString(Map<String, String> data,String postUrl) {

	        try {

	            URL url = new URL(postUrl);
	            HttpURLConnection con;
	            con = (HttpURLConnection) url.openConnection();
	            con.setRequestMethod("POST");
	            con.setDoOutput(true);
	            con.setDoInput(true);
	            OutputStream out = con.getOutputStream();

	            StringBuilder sb = new StringBuilder();
	            for (Entry<String, String> entry : data.entrySet()) {
	                sb.append(entry.getKey() + "="
	                        + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
	            }
	            sb.deleteCharAt(sb.length() - 1);
//	            System.out.println("0");
	            System.out.println(sb.toString());
	            out.write(sb.toString().getBytes());
	            
	            InputStream in = con.getInputStream();

	            int responseCode = con.getResponseCode();

	            if (responseCode == 200) {
	                System.out.println("链接成功!");
	            } else {
	                System.out.println("链接异常!  链接状态码:" + responseCode);
	            }

	            // 定义BufferedReader拼接结果字符串
	            StringBuffer responseResult = new StringBuffer();

	            // 定义BufferedReader输入流来读取URL的ResponseData
	            BufferedReader bufferedReader = new BufferedReader(
	                    new InputStreamReader(con.getInputStream()));
	            String line;
	            while ((line = bufferedReader.readLine()) != null) {
	                responseResult.append(line);
	            }

//	            System.out.println("执行结果:"+ "\n" + formatJson(responseResult.toString()) + "\n");
//	            System.out.println(responseResult.toString());
	            return responseResult.toString();

	        } catch (Exception e) {
	            throw new RuntimeException("提交表单失败");
	        }

	    }
}
