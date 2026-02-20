package com.geoviswtx.conf;

import com.geoviswtx.domain.BaseEntity;
import com.geoviswtx.service.SnowFlakeService;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author Jack Li
 * @date 2022/6/30 下午5:17
 * @description
 */
public class SnowFlakeIdGenerator implements IdentifierGenerator, Configurable {

   @Override
   public void configure(Type type,
                         Properties properties,
                         ServiceRegistry serviceRegistry)
      throws MappingException
   {
   }

   @Override
   public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
                                Object object)
      throws HibernateException
   {
      Serializable id = sharedSessionContractImplementor.getEntityPersister(null, object)
              .getClassMetadata().getIdentifier(object, sharedSessionContractImplementor);

      // 如果对象已经有 id，则直接返回该 id
      try {
         if (id != null) {
            return Long.parseLong(id.toString());
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }

      return SnowFlakeService.getBean().nextId();
   }
}
