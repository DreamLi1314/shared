package com.jack.user.config;/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.jack.ticket.service.TicketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfiguration {

   /**
    * 创建 ApplicationConfig 对象.
    * 将其加入 IOC 并不是必须的, 因为只有在获取远程对象时才需要该实例,
    * 因此, 当远程服务较多需要多次获取时将该实例加入 IOC 才显得有必要,
    * 当然, 你也可以将其作为成员属性持有复用.
    */
   @Bean
   public ApplicationConfig applicationConfig() {
      ApplicationConfig application = new ApplicationConfig();
      application.setName("consumer-user");

      return application;
   }

   /**
    * 创建 RegistryConfig 以配置注册中心信息.
    * @see DubboConfiguration#applicationConfig() 同样的加入 IOC 不是必须的
    */
   @Bean
   public RegistryConfig registryConfig() {
      RegistryConfig registry = new RegistryConfig();
      registry.setAddress("zookeeper://127.0.0.1:2181");

      return registry;
   }

   /**
    * 获取远程服务对象.
    * 实际上, 如果远程对象只调用一次, 也没有必要加入 IOC, 在使用时获取一次就好了,
    * 但是大部分场景都是需要多次重复调用的, 而且获取远程服务对象的代价昂贵, 所以,
    * 将其加入 IOC 容器以复用很明显是较好实践, 获取可以自行缓存.
    */
   @Bean
   public TicketService ticketService() {
      // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
      ReferenceConfig<TicketService> reference = new ReferenceConfig<>();
      reference.setApplication(applicationConfig());
      // 多个注册中心可以用setRegistries()
      reference.setRegistry(registryConfig());
      reference.setInterface(TicketService.class);

      // 和本地bean一样使用 TicketService
      // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
      TicketService ticketService = reference.get();

      return ticketService;
   }
}
