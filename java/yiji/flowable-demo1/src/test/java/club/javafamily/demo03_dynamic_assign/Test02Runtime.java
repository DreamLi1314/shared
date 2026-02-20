package club.javafamily.demo03_dynamic_assign;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test02Runtime {

   ProcessEngineConfiguration configuration;

   @BeforeEach
   public void init() {
      configuration = new StandaloneProcessEngineConfiguration();

      // 配置数据库
      configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
      configuration.setJdbcUsername("root");
      configuration.setJdbcPassword("Yiji0713!");
      configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/db_flowable1?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true");

      configuration.setDatabaseSchemaUpdate(
         ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
   }

   @Test
   void testRunProcess() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final RuntimeService runtimeService = processEngine.getRuntimeService();

      Map<String, Object> vars = new HashMap<>();

      vars.put("requestUser", "普通员工");
      vars.put("approverUser", "总经理");

      final ProcessInstance holidayRequest
         = runtimeService.startProcessInstanceByKey("holiday-ui-new", vars);

      // holiday-ui-new:1:35004
      System.out.println(holidayRequest.getProcessDefinitionId());

      // null
      System.out.println(holidayRequest.getActivityId());

      // 37501
      System.out.println(holidayRequest.getId());

   }
}
