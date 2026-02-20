package com.mlog.yiji.esdemo1.service;

import com.mlog.yiji.esdemo1.vo.GeoVo;

import java.io.IOException;
import java.util.List;

public interface GeoService {

   String DISTRICT_INDEX_NAME = "district_geo";
   String DISTRICT_TYPE = "geo_d";

   String GLOBAL_INDEX_NAME = "global_geo";
   String GLOBAL_TYPE = "geo_g";

   List<GeoVo> searchGeoBoundingBox(Double zoom,
                                    Double top, Double left, Double bottom, Double right) throws IOException;
}
