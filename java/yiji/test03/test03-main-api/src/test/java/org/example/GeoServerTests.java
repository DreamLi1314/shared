package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author Jack Li
 * @date 2023/10/18 下午8:25
 * @description
 */
public class GeoServerTests {

   @Test
   void test() throws Exception {
      String geoserverUrl = "http://10.1.109.141:18080/geoserver";
      String userName = "admin";
      String pwd = "iHPRRw1afc9zMfq";
      String workspace = "gzny";
      String storeName = "gengdi_10m";
      String layerName = " gzny:贵州省耕地10m";
      String tiffFilePath = "/Users/dreamli/Workspace/shared/java/yiji/test03/asset/GH_P_01_360000_01.tif";

      // 创建CredentialsProvider以提供用户名和密码
      CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
      credentialsProvider.setCredentials(AuthScope.ANY,
         new UsernamePasswordCredentials(userName, pwd));

      // 创建HttpClientBuilder并设置CredentialsProvider
      HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider);

      CloseableHttpClient httpClient = httpClientBuilder.build();

      try {
         // 创建POST请求
         HttpPost postRequest = new HttpPost(geoserverUrl + "/rest/workspaces/" + workspace + "/coveragestores/" + storeName + "/file.geotiff");

         // 构建文件实体
         File tifFile = new File(tiffFilePath);

         // 构建请求实体
         HttpEntity entity = MultipartEntityBuilder.create()
            .addBinaryBody("file", tifFile, ContentType.create("image/tiff"), tifFile.getName())
            .build();


         // 设置请求实体
         postRequest.setEntity(entity);

         // 发送请求
         HttpResponse response = httpClient.execute(postRequest);

         // 获取响应实体
         HttpEntity responseEntity = response.getEntity();

         // 解析响应
         String responseString = EntityUtils.toString(responseEntity);
         System.out.println(responseString);

         // 关闭响应实体
         EntityUtils.consume(responseEntity);
      } finally {
         // 关闭HTTP客户端
         httpClient.close();
      }
   }

}
