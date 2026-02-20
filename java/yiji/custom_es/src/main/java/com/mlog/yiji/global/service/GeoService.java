package com.mlog.yiji.global.service;

import com.mlog.yiji.global.vo.GeoVo;
import com.mlog.yiji.global.vo.PointVo;

import java.io.IOException;
import java.util.List;

public interface GeoService {

   String DISTRICT_INDEX_NAME = "district_geo";
   String DISTRICT_TYPE = "geo_d";

   String GLOBAL_INDEX_NAME = "global_geo";
   String GLOBAL_TYPE = "geo_g";

   List<GeoVo> searchGeoBoundingBox(Double zoom,
                                    Double top, Double left, Double bottom, Double right) throws IOException;


   List<PointVo> likeQueryAllGeo(String district) throws Exception;
}
