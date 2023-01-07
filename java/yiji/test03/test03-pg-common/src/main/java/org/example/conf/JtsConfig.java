package org.example.conf;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.utils.GisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jack Li
 * @date 2022/3/22 7:44 下午
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class JtsConfig {

   @Bean
   public JtsModule jtsModule(ObjectMapper objectMapper) {
      final JtsModule jtsModule = new JtsModule(GisUtils.getGeometryFactory());

      objectMapper.registerModule(jtsModule);

      return jtsModule;
   }

}
