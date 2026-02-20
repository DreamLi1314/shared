package com.geoviswtx.consumer;

import com.alibaba.fastjson.JSONObject;
import com.geoviswtx.config.GeoMesaConfig;
import com.geoviswtx.dto.BasicFlightInfoEntity;
import com.geoviswtx.service.GeoMesaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

   private final GeoMesaService geoMesaService;

   public KafkaConsumerConfig(GeoMesaService geoMesaService) {
      this.geoMesaService = geoMesaService;
   }

   @KafkaListener(topics = "shenzhen_uss01")
   public void listen(ConsumerRecord<String, String> record)
      throws Exception
   {
      System.out.println("Received message: " + record);

      String value = record.value();
      //解析入库
      JSONObject basicJson = JSONObject.parseObject(value);
      BasicFlightInfoEntity localBasicFlight
         = basicJson.toJavaObject(BasicFlightInfoEntity.class);

      geoMesaService.ingestData(GeoMesaConfig.TABLE_NAME, localBasicFlight);
   }
}

