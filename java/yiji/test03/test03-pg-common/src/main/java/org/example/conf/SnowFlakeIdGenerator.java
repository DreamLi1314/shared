package org.example.conf;

import org.example.service.SnowFlakeService;
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
                                Object o)
      throws HibernateException
   {
      return SnowFlakeService.getBean().nextId();
   }
}
