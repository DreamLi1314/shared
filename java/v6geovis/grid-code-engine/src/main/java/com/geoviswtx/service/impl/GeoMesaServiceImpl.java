package com.geoviswtx.service.impl;

import com.geoviswtx.common.date.DateUtil;
import com.geoviswtx.domain.FlyingFeature;
import com.geoviswtx.dto.BasicFlightInfoEntity;
import com.geoviswtx.geo.GisUtils;
import com.geoviswtx.service.GeoMesaService;
import org.geotools.api.data.*;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Jack Li
 * @date 2025/4/28 17:00
 * @description
 */
@Service
public class GeoMesaServiceImpl implements GeoMesaService {

   private final DataStore dataStore;

   public GeoMesaServiceImpl(DataStore dataStore) {
      this.dataStore = dataStore;
   }

   @Override
   public void createSchema(String typeName) throws Exception {
      // 创建空间类型
      SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
      builder.setName(typeName);
      builder.add("uas_id", String.class);
      builder.add("uas_model", String.class);
      builder.add("uas_name", String.class);
      builder.add("geom", Point.class, GisUtils.getGeometryFactory().getSRID()); // 几何字段（WGS84坐标）
      builder.add("height", Float.class);      // 属性字段
      builder.add("dtg", Date.class);   // 时间字段 - 数据时间

      builder.setDefaultGeometry("geom");
      // 设置默认时间字段（用于时空索引）
      builder.userData("geomesa.index.dtg", "dtg");

      // Cassandra 特定配置
      builder.userData("geomesa.table.sharding", "true");  // 启用分片
      builder.userData("geomesa.indices.enabled", "z3");   // 使用Z3索引

      SimpleFeatureType sft = builder.buildFeatureType();
      dataStore.createSchema(sft);
   }

   @Override
   public String ingestData(String typeName, BasicFlightInfoEntity dto) throws Exception {
      FlyingFeature feature = new FlyingFeature();

      try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer =
              dataStore.getFeatureWriterAppend(typeName, Transaction.AUTO_COMMIT))
      {
         SimpleFeature simpleFeature = writer.next();

         simpleFeature.setAttribute("uas_id", dto.getUasID());
         simpleFeature.setAttribute("uas_model", dto.getUasModel());
         simpleFeature.setAttribute("geom", GisUtils.createPoint(dto.getLon(), dto.getLat()));
         simpleFeature.setAttribute("height", dto.getHeight() != null ? dto.getHeight().floatValue() : 0);
         simpleFeature.setAttribute("dtg", DateUtil.toDateTime(dto.getTime()));

         writer.write();

         return simpleFeature.getID();
      }
   }

   @Override
   public String queryByBoundingBox(String typeName, double minX, double minY, double maxX, double maxY) throws Exception {
      return "";
   }
}
