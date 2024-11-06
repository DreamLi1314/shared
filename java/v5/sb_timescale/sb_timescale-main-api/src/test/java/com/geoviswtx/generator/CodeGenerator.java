
package com.geoviswtx.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.zaxxer.hikari.HikariDataSource;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class CodeGenerator {

   // 包名
   private static final String BASE_PACKAGE = "com.geoviswtx";
   // 模块名
   private static final String MODULE = "auto";
   // 要包含的表名
   private static final String[] TABLES = new String[]{
      "t_user",
      "t_rule"
   };
   // 注释中的作者信息
   private static final String AUTHOR = "Jack Li";

   @Value("${mybatis-plus.global-config.db-config.table-prefix}")
   private String tablePrefix;

   @Autowired
   private DataSource dataSource;

   @Test
   void generator() {
      HikariDataSource source = (HikariDataSource) dataSource;

      // 代码生成器
      AutoGenerator mpg = new AutoGenerator();

      // 全局配置
      GlobalConfig gc = new GlobalConfig();
      String projectPath = System.getProperty("user.dir");
      gc.setOutputDir(projectPath + "/src/main/java");
      gc.setAuthor(AUTHOR);
      gc.setOpen(false);
      gc.setSwagger2(true); // 实体属性 Swagger2 注解
      // 是否覆盖已经存在的文件
      gc.setFileOverride(false);
      mpg.setGlobalConfig(gc);

      // 数据源配置
      DataSourceConfig dsc = new DataSourceConfig();
      dsc.setUrl(source.getJdbcUrl());
      // dsc.setSchemaName("public");
      dsc.setDriverName(source.getDriverClassName());
      dsc.setUsername(source.getUsername());
      dsc.setPassword(source.getPassword());
      mpg.setDataSource(dsc);

      // 包配置
      PackageConfig pc = new PackageConfig();
      pc.setModuleName(MODULE);
      pc.setParent(BASE_PACKAGE);
      mpg.setPackageInfo(pc);

      // 自定义配置
      InjectionConfig cfg = new InjectionConfig() {
         @Override
         public void initMap() {
            // to do nothing
         }
      };

      // 配置模板
      TemplateConfig templateConfig = new TemplateConfig();

      // 配置自定义输出模板
      //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
      // templateConfig.setEntity("templates/entity2.java");
      // templateConfig.setService();
      // templateConfig.setController();

      templateConfig.setXml(null);
      mpg.setTemplate(templateConfig);

      // 策略配置
      StrategyConfig strategy = new StrategyConfig();
      strategy.setNaming(NamingStrategy.underline_to_camel);
      strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//      strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
      strategy.setEntityLombokModel(true);
      strategy.setRestControllerStyle(true);
      // 公共父类
//      strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
      // 写于父类中的公共字段
//      strategy.setSuperEntityColumns("id");
      strategy.setInclude(TABLES);
      strategy.setControllerMappingHyphenStyle(true);
      strategy.setTablePrefix(tablePrefix);
      mpg.setStrategy(strategy);
//      mpg.setTemplateEngine(new FreemarkerTemplateEngine());
      mpg.execute();
   }


}
