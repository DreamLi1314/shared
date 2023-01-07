package org.example.yiji.dolphin;

import com.mlog.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.TimeZone;

@SpringBootApplication
public class AuthApplication {

  private static final Logger logger = LoggerFactory.getLogger(AuthApplication.class);
  public static void main(String[] args) {
    // setting default time zone
    TimeZone.setDefault(TimeZone.getTimeZone(Tool.DEFAULT_TIME_ZONE_STR));

    ConfigurableApplicationContext context = SpringApplication.run(AuthApplication.class, args);
//    DocsConfig config = new DocsConfig();
//    config.setProjectPath("D:\\Work\\WorkSpace\\yiji\\dolphin\\yiji-dolphin-auth-single");//项目根目录
//    config.setProjectName("用户认证");//项目名称
//    config.setApiVersion("V1.0");//声明该API的版本
//    config.setDocsPath("C:\\Users\\林橘涂\\Desktop");//生成API 文档所在目录
//    config.setAutoGenerate(Boolean.TRUE);//配置自动生成
//    config.addPlugin(new MarkdownDocPlugin());//生成markdown
//    Docs.buildHtmlDocs(config);//执行生成文档
    logger.info("用户权限认证系统启动成功");
  }

}
