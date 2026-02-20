package com.geoviswtx.domain;

import lombok.Data;
import org.geotools.api.feature.simple.SimpleFeature;
import org.locationtech.jts.geom.Point;

import java.util.*;

/**
 * @author Jack Li
 * @date 2025/4/28 16:53
 * @description
 */
@Data
public class FlyingFeature {
   private String id;
   private String uasId;
   private Point pos;
   private Float height;
   private Date datetime;
   private Map<String, Object> attributes = new HashMap<>();

   public static FlyingFeature fromSimpleFeature(SimpleFeature feature) {
      FlyingFeature sf = new FlyingFeature();
      sf.setId(feature.getID());
      sf.setUasId((String) feature.getAttribute("uasId"));
      sf.setPos((Point) feature.getAttribute("pos"));
      sf.setHeight((Float) feature.getAttribute("height"));
      sf.setDatetime((Date) feature.getAttribute("datetime"));
      // 添加其他属性
      feature.getProperties().forEach(p -> {
         if (!p.getName().getLocalPart().equals("uasId") &&
            !p.getName().getLocalPart().equals("pos") &&
            !p.getName().getLocalPart().equals("height") &&
            !p.getName().getLocalPart().equals("datetime"))
         {
            sf.getAttributes().put(p.getName().getLocalPart(), p.getValue());
         }
      });

      return sf;
   }
}
