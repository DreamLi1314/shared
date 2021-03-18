package com.mlog.yiji.esdemo1.controller;

import com.mlog.yiji.esdemo1.enums.QueryLevel;
import com.mlog.yiji.esdemo1.service.GeoService;
import com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil;
import com.mlog.yiji.esdemo1.vo.GeoVo;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class GeoQueryController {

   private final GeoService geoService;

   public GeoQueryController(GeoService geoService) {
      this.geoService = geoService;
   }

   @GetMapping("/api/query/geo")
   public List<GeoVo> queryCustomGlobalGeo(@RequestParam("zoom") Double zoom,
                                           @RequestParam("minLon") Double minLon,
                                           @RequestParam("minLat") Double minLat,
                                           @RequestParam("maxLon") Double maxLon,
                                           @RequestParam("maxLat") Double maxLat)
      throws IOException
   {
      // query
      return geoService.searchGeoBoundingBox(zoom,
         maxLat, minLon, minLat, maxLon);
   }

}
