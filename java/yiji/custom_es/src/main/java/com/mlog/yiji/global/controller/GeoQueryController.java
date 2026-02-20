package com.mlog.yiji.global.controller;

import com.mlog.yiji.global.service.GeoService;
import com.mlog.yiji.global.vo.GeoVo;
import com.mlog.yiji.global.vo.PointVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class GeoQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoQueryController.class);
    private final GeoService geoService;

    public GeoQueryController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/api/query/geo")
    public List<GeoVo> queryCustomGlobalGeo(@RequestParam("zoom") Double zoom,
                                            @RequestParam("minLon") Double minLon,
                                            @RequestParam("minLat") Double minLat,
                                            @RequestParam("maxLon") Double maxLon,
                                            @RequestParam("maxLat") Double maxLat) throws IOException {
        // query
        List<GeoVo> geoVos = null;
        LOGGER.info("====参数信息：zoom=" + zoom + "；maxLat=" + maxLat + "；minLon=" + minLon + "；minLat=" + minLat + "；maxLon=" + maxLon + "====");
        Long startTime = System.currentTimeMillis();
        LOGGER.info("====获取全球地理位置信息接口开始时间：" + startTime + "====");
        geoVos = geoService.searchGeoBoundingBox(zoom,
                maxLat, minLon, minLat, maxLon);
        Long endTime = System.currentTimeMillis();
        LOGGER.info("====获取全球地理位置信息接口结束时间：" + endTime + "====");
        LOGGER.info("====获取全球地理位置信息用时:" + (endTime - startTime) + "毫秒====");
        return geoVos;
    }

    @GetMapping("/api/like/district")
    public List<PointVo> likeQueryAllGeo(@RequestParam("district") String district)
       throws Exception
    {
        return geoService.likeQueryAllGeo(district);
    }

}
