package club.javafamily.sbflowable.conf;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
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
      final JtsModule jtsModule = new JtsModule(new GeometryFactory(new PrecisionModel(), 4326));

      objectMapper.registerModule(jtsModule);

      return jtsModule;
   }

}
