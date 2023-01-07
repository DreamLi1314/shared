# Mybatis-plus demo

> 集成 mybatis-plus 脚手架, 集成了 pg, redis, junit, docker 容器化.
> 并在 junit 中集成了 `mybatis-plus-generator` 支持 CRUD 代码的 service, mapper, entity, controller 的自动生成

## 开发流程

* 修改 application.yml 中的以下属性:
    * `spring.application.name` 应用名称
    * `mybatis-plus.global-config.db-config.table-prefix` 为数据库表名前缀, 建议保持一致
* `pom.xml` 中修改 docker 镜像名称
* 在 `src/main/java/resources/init.sql/` 下定义表的定义及初始化 ddl
* 执行 `src/test/java/com.mlog.blog.generator.CodeGenerator` 自动生成 CRUD
 代码(注意修改包名, 修改的部分已经在文件头中以常量声明)
* enjoy your coding



