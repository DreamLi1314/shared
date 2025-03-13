package cn.crabc.core.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@MapperScan({"cn.crabc.core.*.mapper"})
@ComponentScan("cn.crabc.core")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("接口管理平台启动成功!!!");
    }
}
