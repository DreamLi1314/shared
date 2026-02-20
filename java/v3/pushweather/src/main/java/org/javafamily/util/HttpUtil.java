package org.javafamily.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Http(s) Util Class
 */
public final class HttpUtil {
   private HttpUtil() {
   }

   private static String baseHref() {
      return "http://api.avatardata.cn";
   }

   public static String parseUrl(String url, Map<String, String> params) {
      if(url == null) {
         url = "";
      }

      StringBuilder sb = new StringBuilder(url);

      if(url.indexOf("?") < 0) {
         sb.append("?");
      }

      sb.append(parseParams(REQUIRED_PARAMS));

      if(params != null && params.size() > 0) {
         sb.append("&");
         String paramStr = parseParams(params);
         sb.append(paramStr);
      }

      return sb.toString();
   }

   private static String parseParams(Map<String, String> params) {
      return params.entrySet().stream()
         .map(entry -> {
            try {
               return entry.getKey() + "=" + URLEncoder.encode(URLDecoder.decode(entry.getValue(), "utf-8"), "gbk");
            } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
            }

            return null;
         })
         .filter(kv -> kv != null && !kv.isEmpty())
         .collect(Collectors.joining("&"));
   }

   private static RequestConfig requestConfig() {
      return RequestConfig.custom().setConnectTimeout(30000)//设置连接超时时间
         .setConnectionRequestTimeout(30000)//从连接池获取连接超时时间
         .setSocketTimeout(30000)//设置读取数据超时时间
         .build();
   }

   public static String get(String api, Map<String, String> params) throws Exception {
      CloseableHttpClient httpclient = HttpClients.createDefault();
      String url = baseHref() + parseUrl(api, params);

      HttpGet httpGet = new HttpGet(url);
      httpGet.setConfig(requestConfig());
      CloseableHttpResponse response = httpclient.execute(httpGet);

      try {
         HttpEntity entity = response.getEntity();

         return EntityUtils.toString(entity);
      } finally {
         response.close();
      }
   }

   private static Map<String, String> REQUIRED_PARAMS;

   static {
      REQUIRED_PARAMS = new HashMap<>();

      REQUIRED_PARAMS.put("key", "8741319d614f47f39ea7618fc4ce08f2");
   }
}
