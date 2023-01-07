package org.example.service;

import com.mlog.utils.lang.IdUtil;
import com.mlog.utils.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Jack Li
 * @date 2022/6/30 下午5:34
 * @description
 */
@Component
@Slf4j
public class SnowFlakeService implements ApplicationContextAware {

   @Value("${mlog.snowflake.maxDcId:31}")
   private Long maxDcId;

   @Value("${mlog.snowflake.maxWorkerId:31}")
   private Long maxWorkerId;

   private static ApplicationContext _applicationContext;

   private Snowflake snowflake;

   @PostConstruct
   public void init() {
      final long dcId = IdUtil.getDataCenterId(maxDcId);

      this.snowflake = new Snowflake(
         IdUtil.getWorkerId(dcId, maxWorkerId), dcId);
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      SnowFlakeService._applicationContext = applicationContext;
   }

   public static SnowFlakeService getBean() {
      return _applicationContext.getBean(SnowFlakeService.class);
   }

   public long nextId() {
      return snowflake.nextId();
   }

   public String nextIdStr() {
      return snowflake.nextIdStr();
   }
}
