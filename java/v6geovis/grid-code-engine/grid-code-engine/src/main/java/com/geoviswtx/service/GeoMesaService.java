package com.geoviswtx.service;

/**
 * @author Jack Li
 * @date 2025/4/28 16:58
 * @description
 */
public interface GeoMesaService {

   void createSchema(String typeName) throws Exception;
   String ingestData(String typeName, String jsonData) throws Exception;
   String queryByBoundingBox(String typeName, double minX, double minY, double maxX, double maxY) throws Exception;

}
