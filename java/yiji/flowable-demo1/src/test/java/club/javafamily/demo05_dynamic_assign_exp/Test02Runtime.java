package club.javafamily.demo05_dynamic_assign_exp;

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

      vars.put("tripUser", "普通员工 2 号");
      vars.put("deptAprover", "部门经理");
      vars.put("leaderAprover", "总经理");
      vars.put("financeApprover", "财务总监");
      vars.put("num", 2);

      final ProcessInstance processInstance
         = runtimeService.startProcessInstanceByKey("business-trip", vars);

      // business-trip:1:52504
      System.out.println(processInstance.getProcessDefinitionId());

      // null
      System.out.println(processInstance.getActivityId());

      // 55001
      System.out.println(processInstance.getId());

   }
}
