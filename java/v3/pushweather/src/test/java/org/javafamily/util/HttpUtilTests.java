package org.javafamily.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtilTests {

   @Test
   public void testGet() throws Exception {
      String api = "/Weather/Query";
      Map<String, String> params = new HashMap<>();

      params.put("cityname", "西安");

      String result = HttpUtil.get(api, params);

      System.out.println(result);

      Assertions.assertNotNull(result);
   }

}
