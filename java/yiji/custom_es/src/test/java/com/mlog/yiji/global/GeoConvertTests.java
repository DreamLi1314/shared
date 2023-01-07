package com.mlog.yiji.global;

import org.elasticsearch.common.geo.GeoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GeoConvertTests {

   @Test
   public void testConvertLon() {
      double lon = 190D;
      double nlon = GeoUtils.normalizeLon(lon);
      System.out.println(nlon);
   }

}
