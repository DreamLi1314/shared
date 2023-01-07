项目说明
====
1.application.yml
---
```
server:
  port: 8089 #端口号

spring:
  datasource: #pg数据库
    primary:
      jdbc-url: jdbc:postgresql://localhost:5432/dolphin?characterEncoding=utf8&useSSL=true
      username: postgres
      password: 123456
      driver-class-name: org.postgresql.Driver
    secondary:
      jdbc-url: jdbc:postgresql://localhost:5432/auth?characterEncoding=utf8&useSSL=true
      username: postgres
      password: 123456
      driver-class-name: org.postgresql.Driver
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update

  redis: #redis数据库
    host: localhost
    port: 6379
    database: 0
    password: 123456

  mail: #邮箱
    host: smtp.163.com
    username: xxx@163.com
    password: xxx
    properties:
      mail:
        smtp:
          port: 465
          starttls:
            enable: true
            required: true
          ssl:
            enable: true
    from: xxx@163.com
    default-encoding: utf-8
```

2.JApiDocs接口文档(main方法下)
---
```
DocsConfig config = new DocsConfig();
config.setProjectPath("path");//项目根目录
config.setProjectName("name");//项目名称
config.setApiVersion("V1.0");//声明该API的版本
config.setDocsPath("D:\\Test");//生成API文档所在目录
config.setAutoGenerate(Boolean.TRUE);//配置自动生成
config.addPlugin(new MarkdownDocPlugin());//生成markdown
Docs.buildHtmlDocs(config);//执行生成文档
```

使用说明
====

1.添加依赖
---
```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.session</groupId>
  <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

2.配置application.yml中redis属性
---
```
spring:
    redis:
        host: 127.0.0.1
        port: 6379
        database: 0
        password: 123456
```

3.配置springsession
---
将`com.yiji.dolphin.config.redis`包下的`SessionConfig`类配置到需要使用springsession的项目中

4.配置拦截器
---
将`com.yiji.dolphin.config`包下的`MlogInterceptor`类配置到项目中
* 类中`public static final String TOKEN_HEADER = "Authentication";`为token的名称,根据实际情况修改
* 文件中涉及到`com.yiji.dolphin.common.ResultCode`和`com.yiji.dolphin.common.ResultMsg`两个java文件,根据实际情况修改

5./identity/login登录
---

可参照demo.zip
---

END
---